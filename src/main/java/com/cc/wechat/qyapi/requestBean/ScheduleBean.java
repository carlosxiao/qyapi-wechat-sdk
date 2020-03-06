package com.cc.wechat.qyapi.requestBean;

import java.util.List;

public class ScheduleBean {

    /**
     * organizer : userid1
     * start_time : 1571274600
     * end_time : 1571320210
     * attendees : [{"userid":"userid2"}]
     * summary : test_summary
     * description : test_description
     * reminders : {"is_remind":1,"remind_before_event_secs":3600,"is_repeat":1,"repeat_type":7}
     * location : test_place
     * cal_id : wcjgewCwAAqeJcPI1d8Pwbjt7nttzAAA
     */

    private String organizer;
    private int start_time;
    private int end_time;
    private String summary;
    private String description;
    private RemindersBean reminders;
    private String location;
    private String cal_id;
    private List<AttendeesBean> attendees;

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public int getStart_time() {
        return start_time;
    }

    public void setStart_time(int start_time) {
        this.start_time = start_time;
    }

    public int getEnd_time() {
        return end_time;
    }

    public void setEnd_time(int end_time) {
        this.end_time = end_time;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RemindersBean getReminders() {
        return reminders;
    }

    public void setReminders(RemindersBean reminders) {
        this.reminders = reminders;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCal_id() {
        return cal_id;
    }

    public void setCal_id(String cal_id) {
        this.cal_id = cal_id;
    }

    public List<AttendeesBean> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<AttendeesBean> attendees) {
        this.attendees = attendees;
    }

    public static class RemindersBean {
        /**
         * is_remind : 1
         * remind_before_event_secs : 3600
         * is_repeat : 1
         * repeat_type : 7
         */

        private int is_remind;
        private int remind_before_event_secs;
        private int is_repeat;
        private int repeat_type;

        public int getIs_remind() {
            return is_remind;
        }

        public void setIs_remind(int is_remind) {
            this.is_remind = is_remind;
        }

        public int getRemind_before_event_secs() {
            return remind_before_event_secs;
        }

        public void setRemind_before_event_secs(int remind_before_event_secs) {
            this.remind_before_event_secs = remind_before_event_secs;
        }

        public int getIs_repeat() {
            return is_repeat;
        }

        public void setIs_repeat(int is_repeat) {
            this.is_repeat = is_repeat;
        }

        public int getRepeat_type() {
            return repeat_type;
        }

        public void setRepeat_type(int repeat_type) {
            this.repeat_type = repeat_type;
        }
    }

    public static class AttendeesBean {
        /**
         * userid : userid2
         */

        private String userid;

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }
    }
}
