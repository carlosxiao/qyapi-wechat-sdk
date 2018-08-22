package com.cc.wechat.qyapi;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by carlosxiao
 */
public class JsApiList {

    public static String jsApiList[] = {
            "checkJsApi",
            "onMenuShareTimeline",
            "onMenuShareAppMessage",
            "onMenuShareQQ",
            "onMenuShareWeibo",
            "hideMenuItems",
            "showMenuItems",
            "hideAllNonBaseMenuItem",
            "showAllNonBaseMenuItem",
            "translateVoice",
            "startRecord",
            "stopRecord",
            "onVoiceRecordEnd",
            "onRecordEnd",
            "playVoice",
            "pauseVoice",
            "stopVoice",
            "onVoicePlayEnd",
            "uploadVoice",
            "downloadVoice",
            "chooseImage",
            "previewImage",
            "uploadImage",
            "downloadImage",
            "getNetworkType",
            "openLocation",
            "getLocation",
            "hideOptionMenu",
            "showOptionMenu",
            "closeWindow",
            "scanQRCode",
            "chooseWXPay",
            "openProductSpecificView",
            "addCard",
            "chooseCard",
            "openCard"
    };

    /** ********************* 微信所有JS接口 ************************* */
    public static Map<String,String> map = new HashMap<String,String>();
    static {
        map.put("检查","checkJsApi");
        map.put("朋友圈","onMenuShareTimeline");
        map.put("朋友", "onMenuShareAppMessage");
        map.put("QQ", "onMenuShareQQ");
        map.put("微博", "onMenuShareWeibo");
        map.put("开始录音", "startRecord");
        map.put("停止录音", "stopRecord");
        map.put("监听录音停止", "onVoiceRecordEnd");
        map.put("播放语音", "playVoice");
        map.put("暂停语音", "pauseVoice");
        map.put("停止语音", "stopVoice");
        map.put("监听语音停止", "onVoicePlayEnd");
        map.put("上传语音", "uploadVoice");
        map.put("下载语音", "downloadVoice");
        map.put("选择图片", "chooseImage");
        map.put("预览图片", "previewImage");
        map.put("上传图片", "uploadImage");
        map.put("下载图片", "downloadImage");
        map.put("识别语音", "translateVoice");
        map.put("网络状态", "getNetworkType");
        map.put("查看位置", "openLocation");
        map.put("获取位置", "getLocation");
        map.put("隐藏菜单", "hideOptionMenu");
        map.put("显示菜单", "showOptionMenu ");
        map.put("批量隐藏按钮", "hideMenuItems");
        map.put("批量显示按钮", "showMenuItems");
        map.put("隐藏所有按钮", "hideAllNonBaseMenuItem");
        map.put("显示所有按钮", "showAllNonBaseMenuItem");
        map.put("关闭网页", "closeWindow");
        map.put("扫一扫", "scanQRCode");
        map.put("微信支付", "chooseWXPay");
        map.put("微信商品", "openProductSpecificView");
        map.put("添加卡券", "addCard");
        map.put("选择卡券", "chooseCard");
        map.put("查看卡券", "openCard");
    }
}
