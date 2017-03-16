package mvp.com.zmvp.view;

import mvp.com.zmvp.presenter.DataListPresenter;
import mvp.com.zmvp.view.base.IPresenterView;

/**
 * Created by zz on 2017/2/27.
 */

public interface IDataListView extends IPresenterView<DataListPresenter> {

    void setProgressIndicator(boolean active);

    void showProgress(boolean isShow);

}
