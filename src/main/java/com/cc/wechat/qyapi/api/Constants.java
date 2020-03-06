package com.cc.wechat.qyapi.api;

/**
 * Created by carlosxiao
 */
public class Constants {

    // 默认字符集
    public static final String DEFAULT_CHARSET = "UTF-8";

    // 获取access_token接口
    public static final String WECHAT_ACCESS_TOKEN = "https://qyapi.weixin.qq.com/cgi-bin/gettoken";

    // 获取jsapi_ticket接口
    public static final String WECHAT_JSAPI_TICKET = "https://qyapi.weixin.qq.com/cgi-bin/get_jsapi_ticket?access_token=";

    // OAuth2.0方式获取code
    public static final String WECHAT_CODE_URI = "https://open.weixin.qq.com/connect/oauth2/authorize";

    // 根据code获取成员信息
    public static final String WECHAT_USERID_BYID = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=";

    // 上传多媒体文件链接
    public static final	String UPLOAD_MEDIA_URL = "https://qyapi.weixin.qq.com/cgi-bin/media/upload?access_token=";

    // 下载多媒体文件链接
    public static final	String DOWNLOAD_MEDIA_URL = "https://qyapi.weixin.qq.com/cgi-bin/media/get?access_token=";

    // 获取部门列表接口
    public static final String LIST_DEPARTMENT = "https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token=";

    // 创建部门接口
    public static final String CREATE_DEPARTMENT = "https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token=";

    // 修改部门接口
    public static final String UPDATE_DEPARTMENT = "https://qyapi.weixin.qq.com/cgi-bin/department/update?access_token=";

    // 删除部门接口
    public static final String DELETE_DEPARTMENT = "https://qyapi.weixin.qq.com/cgi-bin/department/delete?access_token=";

    // 创建成员接口
    public static final String CREATE_USER = "https://qyapi.weixin.qq.com/cgi-bin/user/create?access_token=";

    // 修改成员接口
    public static final String UPDATE_USER = "https://qyapi.weixin.qq.com/cgi-bin/user/update?access_token=";

    // 删除成员接口
    public static final String DELETE_USER = "https://qyapi.weixin.qq.com/cgi-bin/user/delete?access_token=";

    // 获取成员信息接口
    public static final String FIND_USER_BYID = "https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token=";

    // 获取部门下成员信息列表接口
    public static final String FIND_USERLIST_BYORGID = "https://qyapi.weixin.qq.com/cgi-bin/user/list?access_token=";

    // 全量覆盖部门信息接口
    public static final String REPLACE_ALL_DEPARTMENT = "https://qyapi.weixin.qq.com/cgi-bin/batch/replaceparty?access_token=";

    // 全量覆盖成员信息接口
    public static final String REPLACE_ALL_USER = "https://qyapi.weixin.qq.com/cgi-bin/batch/replaceuser?access_token=";

    // 获取异步任务结果接口
    public static final String FIND_ASYN_TASK = "https://qyapi.weixin.qq.com/cgi-bin/batch/getresult?access_token=";

    /**
     * 发送应用消息
     */
    public static final String SEND_APP_MSG = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=";

    /**
     * 创建日程
     */
    public static final String CREATE_SCHEDULE = "https://qyapi.weixin.qq.com/cgi-bin/oa/schedule/add?access_token=";
}
