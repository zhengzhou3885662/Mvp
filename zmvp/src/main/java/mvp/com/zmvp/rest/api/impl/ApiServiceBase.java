package mvp.com.zmvp.rest.api.impl;

import java.util.HashMap;
import java.util.Map;

import mvp.com.zmvp.rest.RemoteListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zz on 2017/2/28.
 */

public class ApiServiceBase {


    static Map<String, String> createMap() {
        return new HashMap<>();
    }


    /**
     * 封装自定义返回对象 ( 防止网络框架变化 )
     *
     * @param call
     * @param listener
     * @return
     */
    protected static <T> void enqueue(Call<T> call, final RemoteListener<T> listener) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (null != listener) {
                    listener.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                if (null != listener) {
                    listener.onFailure(t);
                }
            }
        });
    }
}