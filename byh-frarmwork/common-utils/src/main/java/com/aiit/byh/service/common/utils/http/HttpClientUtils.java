package com.aiit.byh.service.common.utils.http;

import com.aiit.byh.service.common.utils.CommonUtils;
import com.aiit.byh.service.common.utils.config.ConfigUtil;
import com.aiit.byh.service.common.utils.http.exception.HttpStatusCodeException;
import org.apache.commons.io.Charsets;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * http客户端工具类
 *
 * @author dsqin
 * @datetime 2017/10/11
 */
public class HttpClientUtils {

    private CloseableHttpClient httpClient;

    private HttpClientUtils() {

        // http连接池中对一个独立的host最大连接数量
        //DripConfUtils
        int poolMaxPerRoute = ConfigUtil.getInt("http.max.per.route", 20);
        // http连接池中最大连接数量
        int poolMaxTotal = ConfigUtil.getInt("http.max.total", 50);
        // 从http连接池中获取连接最大超时时间
        int connectionRequestTimeout = ConfigUtil.getInt("http.connection.request.timeout", 1000);
        // 创建socket连接超时时间
        int connectTimeout = ConfigUtil.getInt("http.connect.timeout", 1000);
        // socket读取超时时间
        int socketTimeout = ConfigUtil.getInt("http.socket.timeout", 3000);
        // http请求最大重试次数
        final int retryMaxTimes = ConfigUtil.getInt("http.retry.max.times", 0);

        PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager();
        manager.setDefaultMaxPerRoute(poolMaxPerRoute);
        manager.setMaxTotal(poolMaxTotal);

        HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                if (executionCount <= retryMaxTimes) {
                    return true;
                }
                return false;
            }
        };

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .setConnectTimeout(connectTimeout)
                .setSocketTimeout(socketTimeout)
                .build();

        httpClient = HttpClients.custom().setConnectionManager(manager)
                .setRetryHandler(httpRequestRetryHandler)
                .setDefaultRequestConfig(requestConfig).build();
    }

    /**
     * 单例
     *
     * @return
     */
    public static HttpClientUtils getInstance() {
        return HttpClientUtils.LazyHolder.INSTANCE;
    }

    /**
     * get请求
     *
     * @param url
     * @return
     * @throws IOException
     * @throws HttpStatusCodeException
     */
    public String invokeGet2String(String url) throws IOException, HttpStatusCodeException {
        byte[] result = invokeGet(url, null, null);
        return byte2tring(result);
    }

    /**
     * get请求
     *
     * @param url
     * @return
     * @throws IOException
     * @throws HttpStatusCodeException
     */
    public byte[] invokeGet(String url) throws IOException, HttpStatusCodeException {
        return invokeGet(url, null, null);
    }

    /**
     * get请求
     *
     * @param url
     * @param headers
     * @param contentType
     * @return
     * @throws IOException
     * @throws HttpStatusCodeException
     */
    public byte[] invokeGet(String url, Map<String, String> headers, String contentType) throws IOException, HttpStatusCodeException {
        HttpGet httpGet = _buildHttpGet(url, headers, contentType);

        return _execute(httpGet);
    }

    /**
     * 构建http get
     *
     * @param url
     * @param headers
     * @param contentType
     * @return
     */
    private HttpGet _buildHttpGet(String url, Map<String, String> headers, String contentType) {
        HttpGet httpGet = new HttpGet(url);

        if (null != headers && !headers.isEmpty()) {
            Set<String> keys = headers.keySet();
            for (String key : keys) {
                httpGet.addHeader(key, headers.get(key));
            }
        }

        if (StringUtils.isNotBlank(contentType)) {
            httpGet.addHeader("Content-Type", contentType);
        }

        return httpGet;
    }

    /**
     * 构建http post
     *
     * @param url
     * @param headers
     * @param contentType
     * @return
     */
    private HttpPost _buildHttpPost(String url, Map<String, String> headers, String contentType) {
        HttpPost httpPost = new HttpPost(url);

        if (null != headers && !headers.isEmpty()) {
            Set<String> keys = headers.keySet();
            for (String key : keys) {
                httpPost.addHeader(key, headers.get(key));
            }
        }

        if (StringUtils.isNotBlank(contentType)) {
            httpPost.addHeader("Content-Type", contentType);
        }

        return httpPost;
    }

    /**
     * post请求
     *
     * @param url
     * @param headers
     * @param contentType
     * @param httpEntity
     * @return
     * @throws HttpStatusCodeException
     * @throws IOException
     */
    public String invokePost2String(String url, Map<String, String> headers, String contentType, HttpEntity httpEntity) throws HttpStatusCodeException, IOException {
        byte[] result = invokePost(url, headers, contentType, httpEntity);
        return byte2tring(result);
    }

    /**
     * post请求
     *
     * @param url
     * @param headers
     * @param contentType
     * @param httpEntity
     * @return
     * @throws HttpStatusCodeException
     * @throws IOException
     */
    public byte[] invokePost(String url, Map<String, String> headers, String contentType, HttpEntity httpEntity) throws HttpStatusCodeException, IOException {
        HttpPost httpPost = _buildHttpPost(url, headers, contentType);
        httpPost.setEntity(httpEntity);

        return _execute(httpPost);
    }

    /**
     * 执行http请求
     *
     * @param httpUriRequest
     * @return
     * @throws HttpStatusCodeException
     * @throws IOException
     */
    private byte[] _execute(HttpUriRequest httpUriRequest) throws HttpStatusCodeException, IOException {
        CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpUriRequest);

        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();

        HttpEntity responseEntity = closeableHttpResponse.getEntity();

        byte[] result = null;
        if (null != responseEntity) {
            result = EntityUtils.toByteArray(responseEntity);
        }

        if (HttpStatus.SC_OK != statusCode) {
            httpUriRequest.abort();
            throw new HttpStatusCodeException("http请求返回状态码非200", statusCode, result);
        }

        return result;
    }

    /**
     * post form data请求
     *
     * @param url
     * @param headers
     * @param contentType
     * @param formData
     * @return
     * @throws IOException
     * @throws HttpStatusCodeException
     */
    public byte[] invokePostFormData(String url, Map<String, String> headers, String contentType, Map<String, String> formData) throws IOException, HttpStatusCodeException {

        if (null == formData || formData.isEmpty()) {
            return null;
        }

        HttpPost httpPost = _buildHttpPost(url, headers, contentType);

        List<NameValuePair> pairs = new ArrayList<NameValuePair>(formData.size());
        for (Map.Entry<String, String> entry : formData.entrySet()) {
            String value = entry.getValue();
            if (value != null) {
                pairs.add(new BasicNameValuePair(entry.getKey(), value));
            }
        }

        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs, Charsets.UTF_8);
        httpPost.setEntity(urlEncodedFormEntity);

        return _execute(httpPost);
    }

    /**
     * http下载文件
     *
     * @param url
     * @param filePath
     * @return
     */
    public boolean file(String url, String filePath) {
        boolean result = false;
        HttpGet get = null;
        CloseableHttpResponse response = null;
        BufferedInputStream bis = null;
        File file = null;
        try {
            get = new HttpGet(url);
            get.addHeader("Referer", get.getURI().getScheme() + "://" + get.getURI().getAuthority());
            get.addHeader("Host", get.getURI().getAuthority());
            get.addHeader("Accept", "*/*");
            get.addHeader("Connection", "Keep-Alive");

            response = httpClient.execute(get);
            if (response.getStatusLine().getStatusCode() != 200) {
                return result = false;
            }
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return result = false;
            }

            // 文件流
            bis = new BufferedInputStream(entity.getContent());

            file = new File(filePath);
            // 检查文件目录是否存在，不存在时创建目录
            if (null != file.getParentFile() && !file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
                return result = false;
            }
            // 如果文件存在，先删除文件
            if (file.exists() && !file.delete()) {
                return result = false;
            }
            // 创建新文件
            if (!file.createNewFile()) {
                return result = false;
            }
            FileOutputStream fos = new FileOutputStream(file, false);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            byte[] buffer = new byte[1024];
            int read;
            while ((read = bis.read(buffer, 0, buffer.length)) != -1) {
                if (read > 0)
                    bos.write(buffer, 0, read);
            }
            bos.flush();
            bos.close();
            return result = true;
        } catch (Throwable e) {
            if (file != null) {
                try {
                    file.delete();
                } catch (Throwable ignored) {
                }
            }
            return result = false;
        } finally {
            close(get, null, response, null);
            try {
                if (bis != null) {
                    bis.close();
                }
            } catch (Throwable ignored) {
            }
            try {
                if (!result && file != null) {
                    file.delete();
                }
            } catch (Throwable ignored) {
            }
        }
    }

    /**
     * 参数拼接get请求
     *
     * @param url
     * @param params
     * @return
     * @throws IOException
     * @throws HttpStatusCodeException
     */
    public byte[] invokeGet(String url, Map<String, String> params) throws IOException, HttpStatusCodeException {
        if (CommonUtils.isEmpty(params)) {
            HttpGet httpGet = _buildHttpGet(url, null, null);
            return _execute(httpGet);
        }

        StringBuilder sb = new StringBuilder();
        sb.append(url);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (StringUtils.isBlank(entry.getValue())) {
                continue;
            }
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(UrlEncode.encodeUTF8(entry.getValue()));
            sb.append("&");
        }
        url = sb.toString().substring(0, sb.length() - 1);

        HttpGet httpGet = _buildHttpGet(url, null, null);
        return _execute(httpGet);
    }

    private void close(HttpGet get, HttpPost post, HttpResponse response, InputStreamReader isr) {
        if (get != null) {
            try {
                get.abort();
                get.releaseConnection();
            } catch (Throwable ignored) {
            }
        }
        if (post != null) {
            try {
                post.abort();
                post.releaseConnection();
            } catch (Throwable ignored) {
            }
        }
        if (response != null) {
            try {
                EntityUtils.consume(response.getEntity());
            } catch (Throwable ignored) {
            }
        }
        if (isr != null) {
            try {
                isr.close();
            } catch (Throwable ignored) {
            }
        }
    }

    /**
     * 结果转String
     *
     * @param bytes
     * @return
     */
    String byte2tring(byte[] bytes) {
        if (null == bytes || 0 >= bytes.length) {
            return null;
        }
        return new String(bytes, Charsets.UTF_8);
    }

    private static class LazyHolder {
        static final HttpClientUtils INSTANCE = new HttpClientUtils();
    }

}
