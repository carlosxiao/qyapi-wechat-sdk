package com.cc.wechat.qyapi;

import com.cc.wechat.qyapi.requestBean.ScheduleBean;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class CorpWeChatServiceTest {

    public static void main(String[] args) {
        CorpWeChatService corpWeChatService = new CorpWeChatService("wx0694d8e24e0aacf1", "Y2NiJn2YdUXiNRagsFfQd_E9tC29DBUO57f129yw_g8", "1000032");
        List<String> users = new ArrayList<>();
        users.add("guodong");
        users.add("xiexuhua");
        corpWeChatService.sendByUser(users, "test","张大坑->>");
        List<String> partys = new ArrayList<>();
        partys.add("123456");


        List<String> tags = new ArrayList<>();
        tags.add("1"); // java 开发
        corpWeChatService.sendByTag(tags, "应用程序报警通知", "可用余额不足");
    }

    @Test
    public void testAddSchedule() {
        CorpWeChatService corpWeChatService = new CorpWeChatService("wx0694d8e24e0aacf1", "", "1000032");
        ScheduleBean scheduleBean = new ScheduleBean();
        List<ScheduleBean.AttendeesBean> attendeesBeans = new ArrayList<ScheduleBean.AttendeesBean>();
        scheduleBean.setSummary("会议助手自动生成"+ UUID.randomUUID().toString());
        scheduleBean.setDescription(UUID.randomUUID().toString());
        scheduleBean.setOrganizer("guodong");
        scheduleBean.setLocation("测试地点" + UUID.randomUUID().toString());
        scheduleBean.setStart_time(1582206749);
        scheduleBean.setEnd_time(1582210348);
        ScheduleBean.AttendeesBean user1 = new ScheduleBean.AttendeesBean();
        ScheduleBean.AttendeesBean user2 = new ScheduleBean.AttendeesBean();
        user1.setUserid("guodong");
        user2.setUserid("xiexuhua");
        attendeesBeans.add(user1);
        attendeesBeans.add(user2);
        scheduleBean.setAttendees(attendeesBeans);
        corpWeChatService.addSchedule(scheduleBean);
        assertEquals(1, 1);
    }

}
