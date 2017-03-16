package mvp.com.zmvp.rest.api;


import java.util.Map;

import mvp.com.zmvp.rest.ApiConstants;
import mvp.com.zmvp.rest.response.BaseResponse;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


/**
 * User API接口
 */
public interface ApiServiceUser<T> {

    /**
     * 请求注册
     */
    @FormUrlEncoded
    @POST(ApiConstants.ACTION_USER_REGISTER)
    Call<T> requestUserRegister(@FieldMap Map<String, String> map);

    /**
     * 请求密码找回验证码
     */
    @FormUrlEncoded
    @POST(ApiConstants.ACTION_USER_FORGOTPWD)
    Call<T> requestUserForgetPwd(@FieldMap Map<String, String> map);

    /**
     * 请求修改密码
     */
    @FormUrlEncoded
    @POST(ApiConstants.ACTION_USER_MODIFYPWD)
    Call<T> requestUserChangePwd(@FieldMap Map<String, String> map);

    /**
     * 请求更换手机短信验证码
     */
    @FormUrlEncoded
    @POST(ApiConstants.ACTION_USER_REPLACE_PHONE_SEND_SMS)
    Call<T> requestReplacePhoneSmsCode(@FieldMap Map<String, String> map);

    /**
     * 请求找回密碼短信验证码
     */
    @FormUrlEncoded
    @POST(ApiConstants.ACTION_USER_FORGOTPWD_SEND_SMS)
    Call<T> requestFindPwdSmsCode(@FieldMap Map<String, String> map);

    /**
     * 请求注册短信验证码
     */
    @FormUrlEncoded
    @POST(ApiConstants.ACTION_USER_REGIST_SEND_SMS)
    Call<T> requestRegistSmsCode(@FieldMap Map<String, String> map);

    /**
     * 请求修改邮箱
     */
    @FormUrlEncoded
    @POST(ApiConstants.ACTION_CONNECTOR_BIND_EMAIL)
    Call<T> requestUserBindEmail(@FieldMap Map<String, String> map);

    /**
     * 请求修改手机
     */
    @FormUrlEncoded
    @POST(ApiConstants.ACTION_USER_MODIFY_PHONE)
    Call<T> requestUserReplacePhone(@FieldMap Map<String, String> map);

    /**
     * 请求上传用户头像地址
     */
    @FormUrlEncoded
    @POST(ApiConstants.ACTION_USER_HEAD_PATH)
    Call<T> requestUserHeadPath(@FieldMap Map<String, String> map);

}
