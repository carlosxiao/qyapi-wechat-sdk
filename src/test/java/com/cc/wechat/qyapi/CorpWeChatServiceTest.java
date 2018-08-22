package com.cc.wechat.qyapi;

import java.util.ArrayList;
import java.util.List;

public class CorpWeChatServiceTest {

    public static void main(String[] args) {
        CorpWeChatService corpWeChatService = new CorpWeChatService("232222", "vvvv--dddd", "1000000988");
        List<String> users = new ArrayList<>();
        users.add("11111");
        corpWeChatService.sendByUser(users, "test","张大坑->>");
        List<String> partys = new ArrayList<>();
        partys.add("123456");


        List<String> tags = new ArrayList<>();
        tags.add("1"); // java 开发
        corpWeChatService.sendByTag(tags, "应用程序报警通知", "可用余额不足");
    }
}
