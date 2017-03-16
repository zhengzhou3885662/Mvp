package mvp.com.zmvp.presenter.base;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import mvp.com.zmvp.view.base.IPresenterView;

/**
 * Created by zz on 2017/1/9.
 */

public abstract class IBasePresenter<T, V extends IPresenterView<T>> implements IAttachView<T, V> {

    protected Reference<V> mViewRef;

    protected IBasePresenter(V view) {
        attachView(view);
    }

    protected V getView() {
        return mViewRef.get();
    }

    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    @Override
    public void attachView(V v) {
        mViewRef = new WeakReference<>(v);
    }

    @Override
    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }


}
