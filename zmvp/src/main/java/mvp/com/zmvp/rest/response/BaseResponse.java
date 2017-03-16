package mvp.com.zmvp.rest.response;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class BaseResponse implements Serializable {
    private static final int CODE_SUCCESS = 100000; // 100000成功


    /**
     * 请求返回码
     */
    @Expose
    public int code;

    /**
     * 提示信息
     */
    @Expose
    public String message;


    /**
     * 请求成功
     *
     * @return
     */
    public boolean isSuccess() {
        return code == CODE_SUCCESS;
    }


    /**
     * 数据是否为null
     *
     * @return
     */
    public boolean isNullData() {
        return false;
    }

//    public static boolean processData(Context context, BaseResponse response) {
//        if (null != response) {
//            if (response.isSuccess() && !response.isNullData()) {
//                return true;
//            } else if (response.isTokenInvalid()) {
//                UserInfoEntity.exitAccountJumpLogin(context);
//            }
//        }
//        return false;
//    }
}
