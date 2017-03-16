package mvp.com.zmvp.rest;

public class ApiConstants {
    public static final String BASE_ADDRESS = "http://192.168.1.106:8180/";  // 测试环境

    public static final int MAX_PAGE_SIZE10 = 10;
    public static final int MAX_PAGE_SIZE15 = 15;
    public static final int MAX_PAGE_SIZE20 = 20;
    public static final int MAX_PAGE_SIZE30 = 30;


    public static final String ACTION_CONNECTOR_BIND_EMAIL = "connecter/v_1_0_0/connecter/applyforEmailBound";  //  绑定用户个人邮箱

    public static final String ACTION_USER_REPLACE_PHONE_SEND_SMS = "connecter/v_1_0_0/connecter/sendCaptchaForModifyPhone";   //  修改用户手机(请求短信验证码)
    public static final String ACTION_USER_FORGOTPWD_SEND_SMS = "connecter/v_1_0_0/connecter/sendCaptchaForFindPassword";      //  找回密码（请求短信验证码）
    public static final String ACTION_USER_REGIST_SEND_SMS = "connecter/v_1_0_0/connecter/sendCaptchaForRegister";    //  注册（请求短信验证码）

    public static final String ACTION_USER_MODIFY_PHONE = "user_center/v_1_0_0/user/modifyUserPhone";  //  修改手机号码
    public static final String ACTION_USER_FORGOTPWD = "user_center/v_1_0_0/user/findPassword";  //  个人用户找回密码
    public static final String ACTION_USER_REGISTER = "user_center/v_1_0_0/user/register";         //  账号注册
    public static final String ACTION_USER_MODIFYPWD = "user_center/v_1_0_0/user/modifyPassword";  //  修改密码
    public static final String ACTION_USER_QUERYINFO = "user_center/v_1_0_0/user/queryUserInfo";   //  用户信息

    public static final String ACTION_USER_HEAD_PATH = "user_center/v_1_0_0/user/modifyHead";            //  上传用户头像地址
    public static final String ACTION_USER_FEEDBACK = "user_center/v_1_0_0/user/feedback";              //  意见反馈
    public static final String ACTION_GET_CONTACT_HEAD = "user_center/v_1_0_0/user/getContactBriefInfo";  //  获取用户头像


}
