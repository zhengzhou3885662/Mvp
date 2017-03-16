package mvp.com.zmvp.presenter.impl;

import java.util.ArrayList;

import mvp.com.zmvp.rest.RemoteListener;
import mvp.com.zmvp.rest.api.impl.ApiServiceUserImpl;
import mvp.com.zmvp.rest.response.UserInfoResponse;
import mvp.com.zmvp.presenter.MainPresenter;
import mvp.com.zmvp.presenter.base.IBasePresenter;
import mvp.com.zmvp.view.IMainView;
import mvp.com.zmvp.view.base.IPresenterView;
import retrofit2.Call;

/**
 * Created by zz on 2017/1/9.
 */

public class MainPresenterImpl extends IBasePresenter implements MainPresenter {
//    PersonImpl mMode;

    private Call mCallFindPwd;

    public MainPresenterImpl(IPresenterView view) {
        super(view);
        view.setPresenter(this);
//        mMode = mode;
    }


    @Override
    public void presenterData1() {
        if (isViewAttached()) {
            ((IMainView) getView()).showProgress(true);
        }
    }

    @Override
    public void presenterData2(ArrayList<String> datas) {
        if (isViewAttached()) {
            ((IMainView) getView()).showProgress(false);
        }
    }


    private RemoteListener remoteListener;

    /**
     * 找回密码
     *
     * @param phone
     */
    public void requestTaskFindPwd(String phone) {
//        if (null != mCallFindPwd) {
//            return;
//        }
//        mCallFindPwd = ApiServiceUserImpl.requestFindPwdSmsCode(phone);
//        mCallFindPwd.enqueue(new Callback<UserInfoResponse>() {
//            @Override
//            public void onResponse(Call<UserInfoResponse> call, Response<UserInfoResponse> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<UserInfoResponse> call, Throwable t) {
//
//            }
//        });


        if (null != remoteListener) {
            return;
        }

        remoteListener = new RemoteListener<UserInfoResponse>() {
            @Override
            public void onResponse(UserInfoResponse response) {

            }

            @Override
            public void onFailure(Throwable t) {

            }
        };

        ApiServiceUserImpl.requestFindPwdSmsCode(phone, remoteListener);


//        ApiServiceUserImpl.requestFindPwdSmsCode(phone, new RemoteListener<UserInfoResponse>() {
//            @Override
//            public void onResponse(UserInfoResponse response) {
//
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//
//            }
//        });
    }

}
