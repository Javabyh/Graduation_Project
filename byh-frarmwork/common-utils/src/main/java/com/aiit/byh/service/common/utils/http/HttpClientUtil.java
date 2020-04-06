package com.aiit.byh.service.common.utils.http;

import com.aiit.byh.service.common.utils.config.DiamondConfigUtil;
import org.apache.commons.codec.Charsets;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * http请求工具类
 * 
 * @author dsqin
 *
 * @deprecated use {@link HttpClientUtils}
 */
@Deprecated
public class HttpClientUtil {

	private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

	public static final String charset = "UTF-8";

	public static final String content_type_formed = "application/x-www-form-urlencoded";
	public static final String content_type_xml = "application/xml";
	public static final String content_type_json = "application/json";
	public static final String content_type_plain = "text/plain";

	private HttpClient httpClient;

	private static class LazyHolder {
		static final HttpClientUtil INSTANCE = new HttpClientUtil();
	}

	/**
	 * 单例
	 * 
	 * @return
	 */
	public static HttpClientUtil getInstance() {
		return LazyHolder.INSTANCE;
	}

	/**
	 * @author add config parameters by fgtian
	 * @config: httpclient.pool.max.total:配置连接池大小
	 * @config: httpclient.DefaultMaxPerRoute: default max per route
	 * @config: httpclient.connection.timeout: connection timeout(milliseconds)
	 * @config: httpclient.so.timeout: socket timeout(milliseconds)
	 */
	private HttpClientUtil() {
		PoolingClientConnectionManager cm = new PoolingClientConnectionManager();
		int maxTotal = DiamondConfigUtil.getInt("httpclient.pool.max.total", 200);
		cm.setMaxTotal(maxTotal);
		int defaultMaxPerRoute = DiamondConfigUtil.getInt("httpclient.DefaultMaxPerRoute", 150);
		cm.setDefaultMaxPerRoute(defaultMaxPerRoute);
		httpClient = new DefaultHttpClient(cm);
		int connectionTimeout = DiamondConfigUtil.getInt("httpclient.connection.timeout", 300000);
		httpClient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, connectionTimeout);
		int sotimeout = DiamondConfigUtil.getInt("httpclient.so.timeout", 300000);
		httpClient.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, sotimeout);
	}

	/**
	 * 执行GET请求
	 * 
	 * @param url
	 * @return
	 */
	public String invokeGet(String url) {
		ByteArrayOutputStream os = null;
		try {
			os = new ByteArrayOutputStream();
			if (!downloadFile(url, os)) {
				logger.error("***** 下载URL({})失败 *****", url);
				return null;
			} else {
				byte[] bytes = os.toByteArray();
				return new String(bytes, "utf-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("***** 下载URL({})失败:{} *****", url, e);
			return null;
		} finally {
			if (null != os) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
					logger.error("***** 下载URL({})时关闭输出流失败:{} *****", url, e);
				}
			}
		}
	}

	/***
	 * 下载文件，如果文件已经存在，则会覆盖
	 * 
	 * @param url
	 *            文件的URL
	 * @param fileFullPath
	 *            文件路径
	 * @return 成功/失败
	 */
	public boolean downloadFile(String url, String fileFullPath) {
		FileOutputStream os = null;
		BufferedOutputStream bos = null;
		try {
			os = new FileOutputStream(fileFullPath);
			bos = new BufferedOutputStream(os);
			return downloadFile(url, bos);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("***** 下载文件url={}, path={}异常:{} *****", url, fileFullPath, e);
			return false;
		} finally {
			if (null != bos) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
					logger.error("***** 关闭文件{}的BOS失败: {} *****", fileFullPath, e);
				}
			}
			if (null != os) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
					logger.error("***** 关闭文件{}失败: {} *****", fileFullPath, e);
				}
			}
		}
	}

	/**
	 * 下载文件
	 * 
	 * @param url
	 * @return
	 */
	public boolean downloadFile(String url, OutputStream os) {
		InputStreamReader isr = null;
		InputStream in = null;
		HttpGet httpGet = null;
		HttpResponse response = null;

		try {
			httpGet = new HttpGet(url);
			response = httpClient.execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				httpGet.abort();
				logger.warn("****http get请求失败,http返回码非200,http返回码:{},url:{}****", statusCode, url);
				return false;
			}
			if (response.getEntity() != null) {
				in = response.getEntity().getContent();
				BufferedInputStream bis = new BufferedInputStream(in);
				byte[] buffer = new byte[4096];
				int buffLen = buffer.length, bytes = 0;
				while ((bytes = bis.read(buffer, 0, buffLen)) > 0) {
					os.write(buffer, 0, bytes);
				}
				bis.close();
			}
			logger.info("****http get请求成功,url:{}****", url);
			return true;
		} catch (Exception ex) {
			logger.error("****http get请求处理异常,url:{}****", url, ex);
			return false;
		} finally {
			if (isr != null) {
				try {
					isr.close();
				} catch (IOException ex) {
					ex.printStackTrace();
					logger.error("****http get请求处理异常,url:{}****", url, ex);
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException ex) {
					ex.printStackTrace();
					logger.error("****http get请求处理异常,url:{}****", url, ex);
				}
			}
			if (response != null) {
				try {
					response.getEntity().getContent().close();
				} catch (Exception ex) {
					ex.printStackTrace();
					logger.error("****http get请求处理异常,url:{}****", url, ex);
				}
			}
		}
	}

	/**
	 * 执行get请求(支持header)
	 * 
	 * @param url url
	 * @param headers headers
	 * @return
	 */
	public String invokeGetWithHeader(String url, Map<String, String> headers) {
		HttpGet httpGet = null;
		HttpResponse response = null;

		try {
			httpGet = new HttpGet(url);
			if (null != headers && !headers.isEmpty()) {
				Set<String> keySet = headers.keySet();
				for (String key : keySet) {
					httpGet.addHeader(key, headers.get(key));
				}
			}
			response = httpClient.execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				httpGet.abort();
				logger.error("****http get请求失败,http返回码非200,,http返回码:{},url:{}****", statusCode, url);
				return null;
			}
			return EntityUtils.toString(response.getEntity(), "UTF-8");
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("****http get请求处理异常,url:{}****", url, ex);
			return null;
		} finally {
			try {
				if (null != response) {
					EntityUtils.consume(response.getEntity());
				}
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("***** Post URL={}，回收异常：{} *****", url, e.toString());
			}
		}
	}

	public Header[] invokeGetWithRespHeaders(String url, Map<String, String> headers, String contentType) {
		HttpGet httpGet = null;
		HttpResponse response = null;

		try {
			httpGet = new HttpGet(url);
			if (StringUtils.isNotBlank(contentType)) {
				httpGet.addHeader("Content-Type", contentType);
			}
			if (null != headers && !headers.isEmpty()) {
				Set<String> keySet = headers.keySet();
				for (String key : keySet) {
					httpGet.addHeader(key, headers.get(key));
				}
			}
			response = httpClient.execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				httpGet.abort();
				logger.error("****http get请求失败,http返回码非200,url:{}****", url);
				return null;
			}
			return response.getAllHeaders();
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("****http get请求处理异常,url:{}****", url, ex);
			return null;
		} finally {
			if (response != null) {
				try {
					response.getEntity().getContent().close();
				} catch (Exception ex) {
					ex.printStackTrace();
					logger.error("****http get请求处理异常,url:{}****", url, ex);
				}
			}
		}
	}

	public String invokePost(String url, Map<String, String> headers, String body, String contentType) {
		HttpEntity entity = new StringEntity(body, com.google.common.base.Charsets.UTF_8);
		return invokePost(url, headers, entity, contentType);
	}

	public String invokePost(String url, Map<String, String> headers, HttpEntity body, String contentType) {
		return invokePost(url, headers, body, contentType, null);
	}

	public String invokePost200(String url, Map<String, String> headers, HttpEntity body, String contentType,
			Map<String, String> respHeaders) {
		HttpResponse resp = invokePostWithResp(url, headers, body, contentType);
		if (null != resp) {
			try {
				int statusCode = resp.getStatusLine().getStatusCode();
				if (200 != statusCode) {
					return StringUtils.EMPTY;
				}

				InputStream in = null;
				InputStreamReader isr = null;

				if (null != respHeaders) {
					Header[] theHeaders = resp.getAllHeaders();
					if (null != theHeaders) {
						for (Header header : theHeaders) {
							respHeaders.put(header.getName(), header.getValue());
						}
					}
				}
				HttpEntity entity = resp.getEntity();
				if (entity == null) {
					logger.error("****HTTP访问地址出错，地址：{}, 返回错误！数据包为空。", url);
					return StringUtils.EMPTY;
				}

				StringBuilder sbuf = new StringBuilder();
				in = entity.getContent();
				isr = new InputStreamReader(in, "UTF-8");
				BufferedReader br = new BufferedReader(isr);
				String line = null;
				while ((line = br.readLine()) != null) {
					sbuf.append(line);
				}
				br.close();
				String reStr = sbuf.toString();
				Object[] log = { url, body.toString(), reStr };
				logger.info("接口调用：url={}, body={}, resp={}", log);
				return reStr;
			} catch (Exception e) {
				logger.error("****HTTP访问地址出错，地址：{}, 返回错误！数据包为空。", url);
			} finally {
				try {
					EntityUtils.consume(resp.getEntity());
				} catch (IOException e) {
					e.printStackTrace();
					logger.error("***** Post URL={}，回收异常：{} *****", url, e.toString());
				}
			}
		}

		return StringUtils.EMPTY;
	}

	public String invokePost(String url, Map<String, String> headers, HttpEntity body, String contentType,
			Map<String, String> respHeaders) {
		HttpResponse response = invokePostWithResp(url, headers, body, contentType);
		if (null != response) {
			InputStream in = null;
			InputStreamReader isr = null;
			try {
				if (null != respHeaders) {
					Header[] theHeaders = response.getAllHeaders();
					if (null != theHeaders) {
						for (Header header : theHeaders) {
							respHeaders.put(header.getName(), header.getValue());
						}
					}
				}
				HttpEntity entity = response.getEntity();
				if (entity == null) {
					logger.error("****HTTP访问地址出错，地址：{}, 返回错误！数据包为空。", url);
					return null;
				}

				StringBuilder sbuf = new StringBuilder();
				in = entity.getContent();
				isr = new InputStreamReader(in, "UTF-8");
				BufferedReader br = new BufferedReader(isr);
				String line = null;
				while ((line = br.readLine()) != null) {
					sbuf.append(line);
				}
				br.close();
				String reStr = sbuf.toString();
				Object[] log = { url, body.toString(), reStr };
				logger.info("接口调用：url={}, body={}, resp={}", log);
				return reStr;
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("***** 执行POST失败：url={}, 异常={} *****", url, e);
				return null;
			} finally {
				try {
					if (null != response) {
						EntityUtils.consume(response.getEntity());
					}
				} catch (IOException e) {
					e.printStackTrace();
					logger.error("***** Post URL={}，回收异常：{} *****", url, e.toString());
				}
				if (null != in) {
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
						logger.error("POST异常：url={}, 异常：{}", url, e);
					}
				}
				if (null != isr) {
					try {
						isr.close();
					} catch (IOException e) {
						e.printStackTrace();
						logger.error("POST异常：url={}, 异常：{}", url, e);
					}
				}
			}
		} else {
			return null;
		}
	}

	/**
	 * 对于没有返回报文的HTTP-POST请求
	 * 
	 * @param url
	 * @param headers
	 * @param body
	 * @param contentType
	 * @return
	 */
	public int invokePostNoResp(String url, Map<String, String> headers, HttpEntity body, String contentType) {
		HttpResponse resp = invokePostWithResp(url, headers, body, contentType);
		if (null != resp) {
			try {
				return resp.getStatusLine().getStatusCode();
			} finally {
				try {
					EntityUtils.consume(resp.getEntity());
				} catch (IOException e) {
					e.printStackTrace();
					logger.error("***** Post URL={}，回收异常：{} *****", url, e.toString());
				}
			}
		} else {
			return -1;
		}
	}

	public String invokePostWithFormData(String url,  Map<String, String> headers, Map<String, String> params) {

		if (StringUtils.isBlank(url)) {
			return null;
		}

		HttpPost httpPost = null;
		HttpResponse httpResponse = null;
		try {
			httpPost = new HttpPost(url);


			List<NameValuePair> pairs = null;
			if (params != null && !params.isEmpty()) {
				pairs = new ArrayList<NameValuePair>(params.size());
				for (Map.Entry<String, String> entry : params.entrySet()) {
					String value = entry.getValue();
					if (value != null) {
						pairs.add(new BasicNameValuePair(entry.getKey(), value));
					}
				}
			}

			if (null != headers) {
				Set<String> keySet = headers.keySet();
				for (String key : keySet) {
					httpPost.addHeader(key, headers.get(key));
				}
			}

			if (null != pairs && !pairs.isEmpty()) {
				httpPost.setEntity(new UrlEncodedFormEntity(pairs, Charsets.UTF_8.displayName()));
			}

			httpResponse = httpClient.execute(httpPost);

			int statusCode = httpResponse.getStatusLine().getStatusCode();

			if (200 != statusCode) {
				httpPost.abort();
				logger.error("****HTTP返回非200：Url={}; statusCode={}****", url, statusCode);
				return null;
			}

			HttpEntity httpEntity = httpResponse.getEntity();

			String response = null;
			if (null != httpEntity) {
				response = EntityUtils.toString(httpEntity, Charsets.UTF_8);
			}

			EntityUtils.consume(httpEntity);

			return response;

		} catch (Exception ex) {
			logger.error("****http请求异常,url:{}****", url, ex);
			return null;
		} finally {
			if (httpResponse != null) {
				try {
					httpResponse.getEntity().getContent().close();
				} catch (Exception ex) {
					logger.error("****http get请求处理异常,url:{}****", url, ex);
				}
			}
		}
	}


	public HttpResponse invokePostWithResp(String url, Map<String, String> headers, HttpEntity body,
			String contentType) {
		HttpPost httpPost = null;
		HttpResponse response = null;
		try {
			httpPost = new HttpPost(url);
			// set contenttype
			if (null != contentType) {
				httpPost.addHeader("Content-Type", contentType);
			}
			// headers
			if (null != headers) {
				Set<String> keySet = headers.keySet();
				for (String key : keySet) {
					httpPost.addHeader(key, headers.get(key));
				}
			}
			httpPost.setEntity(body);
			response = httpClient.execute(httpPost);
			Header[] hs = response.getAllHeaders();
			StringBuilder hsBuilder = new StringBuilder();
			if (null != hs) {
				for (Header header : hs) {
					hsBuilder.append(header.getName()).append("=").append(header.getValue()).append("\n");
				}
			}
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				httpPost.abort();
				logger.error("****HTTP返回非200：Url={}; " + "statusCode={};" + " Response-Headers={}****", url, statusCode,
						hsBuilder.toString());
				return response;
			}
			Object[] log = { url, body.toString(), hsBuilder.toString(), statusCode };
			logger.info("接口调用：url={}, body={}, resp-headers={}, status-code={}", log);
			return response;
		} catch (Exception e) {
			if (httpPost != null) {
				httpPost.abort();
			}
			logger.error("****接口调用：url={}, body={}, {}****", url, body.toString(), e);
			return null;
		} finally {
			// if(response != null) {
			// try {
			// EntityUtils.consume(response.getEntity());
			// } catch (IOException e) {
			// logger.error("接口调用：{}", e.toString());
			// }
			// }
		}
	}

}
