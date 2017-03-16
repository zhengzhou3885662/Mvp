package mvp.com.zmvp.view;

import mvp.com.zmvp.presenter.MainPresenter;
import mvp.com.zmvp.view.base.IPresenterView;

/**
 * Created by zz on 2017/2/27.
 */

public interface IMainView extends IPresenterView<MainPresenter> {

    void setProgressIndicator(boolean active);

    void showProgress(boolean isShow);

}
