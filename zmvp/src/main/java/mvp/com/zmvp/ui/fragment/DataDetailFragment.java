package mvp.com.zmvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import mvp.com.zmvp.R;
import mvp.com.zmvp.ui.fragment.base.BaseFragment;
import mvp.com.zmvp.presenter.DataDetailPresenter;
import mvp.com.zmvp.presenter.base.IAttachView;
import mvp.com.zmvp.view.IDataDetailView;
import mvp.com.zmvp.widgets.MeteorShowerView;

public class DataDetailFragment extends BaseFragment implements IDataDetailView {

    private DataDetailPresenter mPresenter;


    public static DataDetailFragment newInstance() {
        return new DataDetailFragment();
    }

    @Override
    public int getLayoutID() {
        return R.layout.fragment_detail;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView(inflater.inflate(getLayoutID(), container, false));
    }


    public View initView(View rootView) {
        final MeteorShowerView meteorView = (MeteorShowerView) rootView.findViewById(R.id.meteor_view);
        rootView.findViewById(R.id.btn_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meteorView.start();
            }
        });
        return rootView;
    }

    /**
     * 懒加载
     *
     * @param savedInstanceState
     */
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    /**
     * 进入场景动画
     *
     * @return
     */
    @Override
    protected FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }

    /**
     * 数据请求放在场景动画结束之后请求
     *
     * @param savedInstanceState
     */
    @Override
    protected void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (null != mPresenter && mPresenter instanceof IAttachView) {
            ((IAttachView) mPresenter).detachView();
        }
    }

    @Override
    public void setProgressIndicator(boolean active) {

    }

    @Override
    public void showProgress(boolean isShow) {

    }


    private void updateData() {
        if (null != mPresenter) {
            mPresenter.showDetail();
        }
    }

    @Override
    public void setPresenter(DataDetailPresenter presenter) {
        mPresenter = presenter;
    }
}
