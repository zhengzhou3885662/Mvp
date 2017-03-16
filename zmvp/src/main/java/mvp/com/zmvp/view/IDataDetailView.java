package mvp.com.zmvp.view;

import mvp.com.zmvp.presenter.DataDetailPresenter;
import mvp.com.zmvp.view.base.IPresenterView;

/**
 * Created by zz on 2017/2/27.
 */

public interface IDataDetailView extends IPresenterView<DataDetailPresenter> {

    void setProgressIndicator(boolean active);

    void showProgress(boolean isShow);

}
