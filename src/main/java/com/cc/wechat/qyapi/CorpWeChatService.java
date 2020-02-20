package com.cc.wechat.qyapi;

import com.cc.wechat.qyapi.api.Constants;
import com.cc.wechat.qyapi.requestBean.ScheduleBean;
import com.cc.wechat.qyapi.utils.HttpKit;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 企业微信相关API
 *
 * @author carlosxiao
 */
public class CorpWeChatService {

    private String corpId;

    private String secret;

    private String agentId;

    static final int MAX_USER_MSG_SIZE = 1000;

    static final int MAX_USER_JOIN_SIZE = 2000;

    private static Logger logger = LoggerFactory.getLogger(CorpWeChatService.class);

    public CorpWeChatService(String corpId, String secret, String agentId) {
        this.corpId = corpId;
        this.secret = secret;
        this.agentId = agentId;
    }

    /*

    {
       "touser" : "UserID1|UserID2|UserID3",
       "toparty" : "PartyID1|PartyID2",
       "totag" : "TagID1 | TagID2",
       "msgtype" : "text",
       "agentid" : 1,
       "text" : {
           "content" : "你的快递已到，请携带工卡前往邮件中心领取。\n出发前可查看<a href=\"http://work.weixin.qq.com\">邮件中心视频实况</a>，聪明避开排队。"
       },
       "safe":0
    }

     */

    /**
     * 标签ID列表，多个接收者用‘|’分隔，最多支持100个。当touser为@all时忽略本参数
     *
     * @param tags
     */
    public void sendByTag(List<String> tags, String title, String content) {
        send(tags, title, content, "totag");
    }

    /**
     * 成员ID列表（消息接收者，多个接收者用‘|’分隔，最多支持1000个）。特殊情况：指定为@all，则向该企业应用的全部成员发送
     *
     * @param users
     */
    public void sendByUser(List<String> users, String title, String content) {
        send(users, title, content, "touser");
    }

    /**
     * 部门ID列表，多个接收者用‘|’分隔，最多支持100个。当touser为@all时忽略本参数
     *
     * @param partys
     */
    public void sendByParty(List<String> partys, String title, String content) {
        send(partys, title, content, "toparty");
    }

    public void send(List<String> receives, String title, String content, String type) {
        try {
            String token = HttpKit.getAccessToken(corpId, secret);
            Map<String, Object> paramMap = new HashMap<>(7);
            if (null == receives || receives.size() == 0) {
                logger.error("消息接收者不能为空");
                return;
            }
            if (receives.size() > MAX_USER_MSG_SIZE) {
                logger.error("消息接收者数量最多1000");
                return;
            }
            String touser = StringUtils.join(receives, "|");
            paramMap.put(type, touser);
            paramMap.put("msgtype", "textcard");
            paramMap.put("agentid", agentId);
            paramMap.put("safe", 0);
            Map<String, String> contentMap = new HashMap<>(4);
            contentMap.put("title", title);
            contentMap.put("description", content);
            contentMap.put("url", "#");
            contentMap.put("btntxt", "查看详情");

            paramMap.put("textcard", contentMap);
            JSONObject jsonObject = JSONObject.fromObject(paramMap);
            String result = HttpKit.post(Constants.SEND_APP_MSG + token, jsonObject.toString());
            logger.debug("send result: {}", result);
        } catch (Exception e) {
            logger.error("获取access token失败", e);
        }
    }


    /**
     * 创建日程
     * @param scheduleBean
     */
    public void addSchedule(ScheduleBean scheduleBean) {
        try {
            String token = HttpKit.getAccessToken(corpId, secret);
            Map<String, Object> paramMap = new HashMap<>(7);

            if (scheduleBean.getAttendees() == null || scheduleBean.getAttendees().size() == 0) {
                logger.error("参与人不能为空");
                return;
            }
            if (scheduleBean.getAttendees().size() > MAX_USER_JOIN_SIZE) {
                logger.error("参与人数量最多2000");
                return;
            }
            paramMap.put("schedule", scheduleBean);
            JSONObject jsonObject = JSONObject.fromObject(paramMap);
            String result = HttpKit.post(Constants.CREATE_SCHEDULE + token, jsonObject.toString());
            logger.debug("send result: {}", result);
            System.out.println("send body: {}" + jsonObject.toString());
            System.out.println("send result: {}" + result);
        } catch (Exception e) {
            logger.error("获取access token失败", e);
        }
    }
}
