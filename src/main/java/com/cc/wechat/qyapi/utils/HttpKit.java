package com.cc.wechat.qyapi.utils;

import com.cc.wechat.qyapi.api.Constants;
import com.cc.wechat.qyapi.WeChatException;
import com.cc.wechat.qyapi.WeChatMsg;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author carlosxiao
 */
public class HttpKit {

    private static Logger logger = LoggerFactory.getLogger(HttpKit.class);

    /**
     * 发送Get请求
     *
     * @param url
     * @return
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @throws KeyManagementException
     */
    public static String get(String url) throws NoSuchAlgorithmException, NoSuchProviderException, IOException, KeyManagementException {
        StringBuffer bufferRes;
        TrustManager[] tm = {new MyX509TrustManager()};
        SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
        sslContext.init(null, tm, new java.security.SecureRandom());
        // 从上述SSLContext对象中得到SSLSocketFactory对象
        SSLSocketFactory ssf = sslContext.getSocketFactory();

        logger.debug("get request url: {}", url);
        URL urlGet = new URL(url);
        HttpsURLConnection http = (HttpsURLConnection) urlGet.openConnection();
        // 连接超时
        http.setConnectTimeout(25000);
        // 读取超时 --服务器响应比较慢，增大时间
        http.setReadTimeout(25000);
        http.setRequestMethod("GET");
        http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        http.setSSLSocketFactory(ssf);
        http.setDoOutput(true);
        http.setDoInput(true);
        http.connect();

        InputStream in = http.getInputStream();
        BufferedReader read = new BufferedReader(new InputStreamReader(in, Constants.DEFAULT_CHARSET));
        String valueString = null;
        bufferRes = new StringBuffer();
        while ((valueString = read.readLine()) != null) {
            bufferRes.append(valueString);
        }
        in.close();
        if (http != null) {
            // 关闭连接
            http.disconnect();
        }
        return bufferRes.toString();
    }

    /**
     * 发送Post请求
     *
     * @param url
     * @param params
     * @return
     * @throws IOException
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public static String post(String url, String params) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
        StringBuffer bufferRes;
        TrustManager[] tm = {new MyX509TrustManager()};
        SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
        sslContext.init(null, tm, new java.security.SecureRandom());
        // 从上述SSLContext对象中得到SSLSocketFactory对象
        SSLSocketFactory ssf = sslContext.getSocketFactory();

        logger.debug("post request url: {}, params: {}", url, params);
        URL urlGet = new URL(url);
        HttpsURLConnection http = (HttpsURLConnection) urlGet.openConnection();
        // 连接超时
        http.setConnectTimeout(25000);
        // 读取超时 --服务器响应比较慢，增大时间
        http.setReadTimeout(25000);
        http.setRequestMethod("POST");
        http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        http.setSSLSocketFactory(ssf);
        http.setDoOutput(true);
        http.setDoInput(true);
        http.connect();

        OutputStream out = http.getOutputStream();
        out.write(params.getBytes(Constants.DEFAULT_CHARSET));
        out.flush();
        out.close();

        InputStream in = http.getInputStream();
        BufferedReader read = new BufferedReader(new InputStreamReader(in, Constants.DEFAULT_CHARSET));
        String valueString;
        bufferRes = new StringBuffer();
        while ((valueString = read.readLine()) != null) {
            bufferRes.append(valueString);
        }
        in.close();
        if (http != null) {
            // 关闭连接
            http.disconnect();
        }
        return bufferRes.toString();
    }

    public static Map<String, Object> getToken(String url, Map<String, Object> params) throws KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException, IOException {
        String jsonStr = get(initParams(url, params));
        JSONObject obj = JSONObject.fromObject(jsonStr);
        Integer errCode = (Integer) obj.get("errcode");
        if (errCode != null && errCode != 0) {
            String errMsg = WeChatMsg.map.get(errCode);
            logger.info("获取访问凭证微信接口错误 :" + errMsg);
            throw new WeChatException(String.valueOf(errCode), errMsg);
        }
        return obj;
    }

    /**
     * @param url
     * @param params
     * @return
     */
    public static String initParams(String url, Map<String, Object> params) {
        if (null == params || params.isEmpty()) {
            return url;
        }
        StringBuilder sb = new StringBuilder(url);
        if (url.indexOf("?") == -1) {
            sb.append("?");
        } else {
            sb.append("&");
        }
        boolean first = true;
        for (Entry<String, Object> entry : params.entrySet()) {
            if (first) {
                first = false;
            } else {
                sb.append("&");
            }
            String key = entry.getKey();
            String value = String.valueOf(entry.getValue());
            sb.append(key).append("=");

            if (value != null && value.length() > 0) {
                try {
                    sb.append(URLEncoder.encode(value, Constants.DEFAULT_CHARSET));
                } catch (UnsupportedEncodingException e) {
                    logger.error(e.getMessage());
                }
            }
        }
        return sb.toString();
    }

    /**
     * key为access_token，value为jsapi_ticket
     */
    private static Map<String, String> ticketMap = new HashMap<String, String>();

    /**
     * key为access_token，value为jsapi_ticket过期时间
     */
    private static Map<String, Long> validityDateTicketMap = new HashMap<String, Long>();

    public static String getJsapiTicket(String accessToken) throws KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException, IOException {
        long nowDate = System.currentTimeMillis() / 1000;
        String jsapi_ticket = ticketMap.get(accessToken);
        long validityDate = validityDateTicketMap.get(accessToken) != null ? validityDateTicketMap.get(accessToken) : 0;
        if (jsapi_ticket == null || validityDate < nowDate) {
            // 重新获取新的jsapi_ticket
            String jsonStr = HttpKit.get(Constants.WECHAT_JSAPI_TICKET + accessToken);
            logger.info("jsapi_ticket jsonStr:" + jsonStr);
            JSONObject obj = JSONObject.fromObject(jsonStr);
            Integer errCode = (Integer) obj.get("errcode");
            if (errCode != null && errCode != 0) {
                String errMsg = WeChatMsg.map.get(errCode);
                logger.error("获取访问凭证微信接口错误 :" + errMsg);
                throw new WeChatException(String.valueOf(errCode), errMsg);
            }

            if (obj != null) {
                jsapi_ticket = obj.get("ticket").toString();
                validityDate = nowDate + Long.parseLong(obj.get("expires_in").toString());
            }

            ticketMap.put(accessToken, jsapi_ticket);
            validityDateMap.put(accessToken, validityDate);
        }

        return ticketMap.get(accessToken);
    }

    /**
     * 根据userId获取成员信息
     *
     * @return msg
     */
    public static Map<String, Object> findUserInfoById(String corpId, String secret, Map<String, Object> params) throws KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException, IOException {
        // 获取access_token
        String access_token = getAccessToken(corpId, secret);

        // 调用微信接口获取成员信息
        String jsonStr = HttpKit.get(HttpKit.initParams(Constants.FIND_USER_BYID + access_token, params));
        logger.info("return jsonStr:" + jsonStr);
        JSONObject obj = JSONObject.fromObject(jsonStr);
        Integer errCode = (Integer) obj.get("errcode");
        if (errCode != null && errCode != 0) {
            String errMsg = WeChatMsg.map.get(errCode);
            logger.error("获取访问凭证微信接口错误 :" + errMsg);
            throw new WeChatException(String.valueOf(errCode), errMsg);
        }

        return obj;
    }

    // key为corpsecret，value为access_token
    private static Map<String, String> tokenMap = new HashMap<String, String>();
    // key为corpsecret，value为access_token过期时间
    private static Map<String, Long> validityDateMap = new HashMap<String, Long>();

    public static String getAccessToken(String corPid, String corpsecret) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
        long nowDate = System.currentTimeMillis() / 1000;
        String access_token = tokenMap.get(corpsecret);
        long validityDate = validityDateMap.get(corpsecret) != null ? validityDateMap.get(corpsecret) : 0;
        if (access_token == null || validityDate < nowDate) {
            // 重新获取新的access_token
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("corpid", corPid);
            params.put("corpsecret", corpsecret);
            Map<String, Object> obj = getToken(Constants.WECHAT_ACCESS_TOKEN, params);
            if (obj != null) {
                access_token = obj.get("access_token").toString();
                validityDate = nowDate + Long.parseLong(obj.get("expires_in").toString());
            }

            tokenMap.put(corpsecret, access_token);
            validityDateMap.put(corpsecret, validityDate);
        }

        return tokenMap.get(corpsecret);
    }

    public static Map<String, Object> findUseridByCode(String access_token, Map<String, Object> params) throws KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException, IOException {
        // 调用微信接口获取成员信息
        String jsonStr = HttpKit.get(HttpKit.initParams(Constants.WECHAT_USERID_BYID + access_token, params));

        logger.info("return jsonStr:" + jsonStr);
        JSONObject obj = JSONObject.fromObject(jsonStr);
        Integer errCode = (Integer) obj.get("errcode");
        if (errCode != null && errCode != 0) {
            String errMsg = WeChatMsg.map.get(errCode);
            logger.error("获取访问凭证微信接口错误 :" + errMsg);
            throw new WeChatException(String.valueOf(errCode), errMsg);
        }

        return obj;
    }

}

/**
 * 证书管理
 */
class MyX509TrustManager implements X509TrustManager {

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType)
            throws CertificateException {
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType)
            throws CertificateException {
    }
}
