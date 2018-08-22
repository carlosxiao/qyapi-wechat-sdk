package com.cc.wechat.qyapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by carlosxiao
 */
public class SignUtil {

    private static Logger logger = LoggerFactory.getLogger(SignUtil.class);

    /**
     * jsApi加密签名
     *
     * @param  jsapi_ticket JS接口访问凭证
     * @param  url 调用jsApi接口的当前页面URL
     * @return Map<String, String> 签名结果键值对
     * @throws Exception
     **/
    public static Map<String, String> jsApiSign(String jsapi_ticket, String url){

        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = create_nonce_str32();
        String timestamp = create_timestamp();
        String signatureUrl;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        signatureUrl = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + nonce_str +
                "&timestamp=" + timestamp +
                "&url=" + url;

        logger.info("signatureUrl:" + signatureUrl);

        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(signatureUrl.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            logger.error("签名失败: {}", e);
        } catch (UnsupportedEncodingException e) {
            logger.error("字符集不支持: {}", e);
        }

        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);

        return ret;
    }

    /**
     * 获取请求code的Url
     * @param  corpid 企业账号唯一凭证
     * @param  redirect_uri 回调url地址
     * @return String 请求code的Url
     * @throws Exception
     */
    public static String getCodeUrl(String corpid,String redirect_uri) throws Exception{

        Map<String, String> params = new HashMap<String, String>();
        params.put("appid", corpid);
        params.put("response_type", "code");
        params.put("redirect_uri", redirect_uri);
        params.put("scope", "snsapi_userinfo");
        String para = createSign(params, true);

        return Constants.WECHAT_CODE_URI + "?" + para + "state=STATE#wechat_redirect";
    }

    /**
     * 构造签名
     * @param params
     * @param encode
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String createSign(Map<String, String> params, boolean encode) throws UnsupportedEncodingException {
        Set<String> keysSet = params.keySet();
        Object[] keys = keysSet.toArray();
        Arrays.sort(keys);
        StringBuffer temp = new StringBuffer();
        boolean first = true;
        for (Object key : keys) {
            if (first) {
                first = false;
            } else {
                temp.append("&");
            }
            temp.append(key).append("=");
            Object value = params.get(key);
            String valueString = "";
            if (null != value) {
                valueString = value.toString();
            }
            if (encode) {
                temp.append(URLEncoder.encode(valueString, Constants.DEFAULT_CHARSET));
            } else {
                temp.append(valueString);
            }
        }
        return temp.toString();
    }

    public static String create_nonce_str32(){
        String result="";
        UUID uuid = java.util.UUID.randomUUID();
        String temp=uuid.toString();
        StringTokenizer token=new StringTokenizer(temp,"-");
        while(token.hasMoreTokens()){
            result+=token.nextToken();
        }
        return result;
    }

    /**
     * 获取当前时间戳
     * @return String 当前时间戳
     */
    public static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    /**
     * 字节数组转十六进制字符串
     * @param  hash 字节数组
     * @return String 十六进制字符串
     * @throws Exception
     */
    public static String byteToHex(byte[] hash){

        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
}
