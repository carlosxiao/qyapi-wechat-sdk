package com.cc.wechat.qyapi;

/**
 * Created by carlosxiao
 */
public class WeChatException extends RuntimeException{

    private String code;

    private String message;

    public WeChatException(String message) {
        super(message);
        this.message = message;
    }

    public WeChatException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
