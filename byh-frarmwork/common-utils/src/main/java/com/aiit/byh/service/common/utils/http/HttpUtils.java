package com.aiit.byh.service.common.utils.http;

import com.aiit.byh.service.common.utils.config.ConfigUtil;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * @Project_name kuyin-gt-com.iflytek.kuyin.service.common.redis.service
 * @Author cwma【cwma@iflytek.com】
 * @Date 2018/2/27 12:02
 */
public class HttpUtils {

    private static int timeout = ConfigUtil.getInt("http.connect.timeout", 5000);

    private static class LazyHolder {
        static final HttpUtils INSTANCE = new HttpUtils();
    }

    /**
     * 单例
     *
     * @return
     */
    public static HttpUtils getInstance() {
        return HttpUtils.LazyHolder.INSTANCE;
    }

    public boolean get(String urlString, Map<String, String> headers, StringBuilder result) throws Exception {
        HttpURLConnection connection = null;
        InputStream is = null;
        BufferedReader br = null;

        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(timeout);
            connection.setConnectTimeout(timeout);

            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    connection.addRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            connection.setRequestMethod("GET");

            is = connection.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            String readLine = null;
            while ((readLine = br.readLine()) != null) {
                result.append(readLine);
            }
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (is != null) {
                    is.close();
                }
                if (connection != null) {
                    connection.disconnect();
                }
            } catch (Exception e) {
                throw new Exception("http 请求异常", e);
            }
        }

        return true;
    }

    public boolean post(String urlString, byte[] data, Map<String, String> headers, StringBuilder result) throws Exception {
        HttpURLConnection connection = null;
        InputStream is = null;
        BufferedReader br = null;

        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(timeout);
            connection.setConnectTimeout(timeout);

            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    connection.addRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            OutputStream out = connection.getOutputStream();
            out.write(data);
            out.flush();
            out.close();

            is = connection.getInputStream();
            br = new BufferedReader(new InputStreamReader(is, "utf-8"));
            String readLine = null;
            while ((readLine = br.readLine()) != null) {
                result.append(readLine);
            }
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (is != null) {
                    is.close();
                }
                if (connection != null) {
                    connection.disconnect();
                }
            } catch (Exception e) {
                throw new Exception("http 请求异常", e);
            }
        }

        return true;
    }

    public boolean put(String urlString, byte[] data, Map<String, String> headers, StringBuilder result) throws Exception {
        HttpURLConnection connection = null;
        InputStream is = null;
        BufferedReader br = null;

        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(timeout);
            connection.setConnectTimeout(timeout);

            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    connection.addRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            connection.setRequestMethod("PUT");
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            OutputStream out = connection.getOutputStream();
            out.write(data);
            out.flush();
            out.close();

            is = connection.getInputStream();
            br = new BufferedReader(new InputStreamReader(is, "utf-8"));
            String readLine = null;
            while ((readLine = br.readLine()) != null) {
                result.append(readLine);
            }
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (is != null) {
                    is.close();
                }
                if (connection != null) {
                    connection.disconnect();
                }
            } catch (Exception e) {
                throw new Exception("http 请求异常", e);
            }
        }

        return true;
    }

    public boolean delete(String urlString, Map<String, String> headers, StringBuilder result) throws Exception {
        HttpURLConnection connection = null;
        InputStream is = null;
        BufferedReader br = null;
        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(timeout);
            connection.setConnectTimeout(timeout);
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    connection.addRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            connection.setRequestMethod("DELETE");

            is = connection.getInputStream();
            br = new BufferedReader(new InputStreamReader(is, "utf-8"));
            String readLine = null;
            while ((readLine = br.readLine()) != null) {
                result.append(readLine);
            }
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (is != null) {
                    is.close();
                }
                if (connection != null) {
                    connection.disconnect();
                }
            } catch (Exception e) {
                throw new Exception("http 请求异常", e);
            }
        }
        return true;
    }

    public byte[] download(String reqUrl, String range, byte[] content) throws Exception {
        HttpURLConnection connection = null;
        InputStream is = null;
        try {
            URL url = new URL(reqUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(timeout);
            connection.setConnectTimeout(timeout);
            if (range != null) {
                connection.addRequestProperty(HttpHeaders.CONTENT_RANGE, range);
            }
            connection.setRequestMethod("GET");
            if (connection.getResponseCode() == HttpStatus.SC_OK) {
                is = connection.getInputStream();
                content = IOUtils.toByteArray(is);
            }
        } finally {
            if (is != null) {
                is.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return content;
    }
}
