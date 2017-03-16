package mvp.com.zmvp.rest.api.impl;

import java.util.Map;

import mvp.com.zmvp.rest.RemoteListener;
import mvp.com.zmvp.rest.RestClient;
import mvp.com.zmvp.rest.response.BaseResponse;
import retrofit2.Call;


/**
 * User 接口API
 */
public class ApiServiceUserImpl extends ApiServiceBase {

//    private static final String CLIENT_ID = "58c51cba-87a3-4d19-a34f-45731383ba90";
//    private static final String CLIENT_SECRET = "824e8f60-d85e-4a39-8742-dd1d779b35b5";

    /**
     * 请求用户注册
     */
    private static Call requestUserRegister(String userPhone, String password, String confirmPassword, String captcha) {
        Map<String, String> hashMap = createMap();
        hashMap.put("userPhone", userPhone);
        hashMap.put("password", password);
        hashMap.put("confirmPassword", confirmPassword);
        hashMap.put("captcha", captcha);
        return RestClient.getApiUser().requestUserRegister(hashMap);
    }

    /**
     * 请求用户找回密码
     */
    private static Call requestUserForgetPwd(String userPhone, String password, String confirmPassword, String captcha) {
        Map<String, String> hashMap = createMap();
        hashMap.put("userPhone", userPhone);
        hashMap.put("password", password);
        hashMap.put("confirmPassword", confirmPassword);
        hashMap.put("captcha", captcha);
        return RestClient.getApiUser().requestUserForgetPwd(hashMap);
    }

    /**
     * 请求用户修改密码
     */
    private static Call requestUserChangePwd(String userPhone, String originalPsw, String newPassword, String confirmPassword) {
        Map<String, String> hashMap = createMap();
        hashMap.put("userPhone", userPhone);
        hashMap.put("originalPsw", originalPsw);
        hashMap.put("newPassword", newPassword);
        hashMap.put("confirmPassword", confirmPassword);
        return RestClient.getApiUser().requestUserChangePwd(hashMap);
    }


    /**
     * 请求注册短信验证码
     */
    private static Call<BaseResponse> requestRegistSmsCode(String phone) {
        Map<String, String> hashMap = createMap();
        hashMap.put("phone", phone);
        return RestClient.getApiUser().requestRegistSmsCode(hashMap);
    }


    /**
     * 请求找回密碼短信验证码
     */
    public static void requestFindPwdSmsCode(String phone, RemoteListener listener) {
        Map<String, String> hashMap = createMap();
        hashMap.put("phone", phone);

        Call call = RestClient.getApiUser().requestFindPwdSmsCode(hashMap);
        enqueue(call, listener);
    }


    /**
     * 请求更换手机短信验证码
     */
    private static Call<BaseResponse> requestReplacePhoneSmsCode(String userPhone, String newUserPhone) {
        Map<String, String> hashMap = createMap();
        hashMap.put("userPhone", userPhone);
        hashMap.put("newUserPhone", newUserPhone);
        return RestClient.getApiUser().requestReplacePhoneSmsCode(hashMap);
    }

    /**
     * 请求绑定邮箱
     */
    private static Call requestUserBindEmail(String email) {
        Map<String, String> hashMap = createMap();
        hashMap.put("email", email);
        return RestClient.getApiUser().requestUserBindEmail(hashMap);
    }


    /**
     * 请求更换绑定手机
     */
    private static Call requestUserReplacePhone(String userPhone, String newUserPhone, String captcha) {
        Map<String, String> hashMap = createMap();
        hashMap.put("userPhone", userPhone);
        hashMap.put("newUserPhone", newUserPhone);
        hashMap.put("captcha", captcha);
        return RestClient.getApiUser().requestUserReplacePhone(hashMap);
    }


//    /**
//     * 请求上传头像
//     */
//    private static Call<IUploadRes> requestUploadHead(Context context, File file, Callback<IUploadRes> callback) {
//        HashMap<String, RequestBody> map = new HashMap<>();
////        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), "content1");
////        RequestBody description2 = RequestBody.create(MediaType.parse("text/plain"), "content2");
////        map.put("apiKey",description);
////        map.put("apiType", description2);
//        // create RequestBody instance from file
//        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file);
//        map.put("file\"; filename=\"" + file.getName(), requestFile);
//        Call call = RestClient.getApiUser().requestUploadHeadAPI(map);
//        return RestClientEntity.g().post(context, call, callback);
//    }
//
//    /**
//     * 请求上传资源
//     */
//    private static Call<String> requestComparisonORC(Context context, File file, Callback<String> callback) {
//        HashMap<String, RequestBody> map = new HashMap<>();
////        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), "content1");
////        RequestBody description2 = RequestBody.create(MediaType.parse("text/plain"), "content2");
////        map.put("apiKey",description);
////        map.put("apiType", description2);
//        // create RequestBody instance from file
//        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file);
//        map.put("face\"; filename=\"" + file.getName(), requestFile);
//
//        requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file);
//        map.put("back\"; filename=\"" + file.getName(), requestFile);
//
//        requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file);
//        map.put("head\"; filename=\"" + file.getName(), requestFile);
//
//        Call call = RestClient.g().getApiStringScalars().requestUploadHeadAPI(map);
//        return RestClientEntity.g().post(context, call, callback);
//    }


}