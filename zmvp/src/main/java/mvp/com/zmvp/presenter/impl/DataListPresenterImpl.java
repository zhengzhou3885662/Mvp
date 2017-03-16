package mvp.com.zmvp.presenter.impl;

import java.util.ArrayList;

import mvp.com.zmvp.presenter.DataListPresenter;
import mvp.com.zmvp.presenter.base.IBasePresenter;
import mvp.com.zmvp.view.IDataListView;

/**
 * Created by zz on 2017/1/9.
 */

public class DataListPresenterImpl extends IBasePresenter implements DataListPresenter {

    public DataListPresenterImpl(IDataListView view) {
        super(view);
        view.setPresenter(this);
    }


    @Override
    public void updateListData() {

    }

    @Override
    public void updateTask(ArrayList<String> datas) {

    }
}
