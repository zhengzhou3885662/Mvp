package mvp.com.zmvp.presenter.impl;

import mvp.com.zmvp.presenter.DataDetailPresenter;
import mvp.com.zmvp.presenter.base.IBasePresenter;
import mvp.com.zmvp.view.IDataDetailView;

/**
 * Created by zz on 2017/1/9.
 */

public class DataDetailPresenterImpl extends IBasePresenter implements DataDetailPresenter {

    public DataDetailPresenterImpl(IDataDetailView view) {
        super(view);
        view.setPresenter(this);
    }


    @Override
    public void showDetail() {

    }

    @Override
    public void hideDetail() {

    }

    @Override
    public void setTitle() {

    }
}
