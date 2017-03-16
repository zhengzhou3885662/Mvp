package mvp.com.zmvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import mvp.com.zmvp.R;
import mvp.com.zmvp.ui.fragment.base.BaseFragment;
import mvp.com.zmvp.presenter.DataListPresenter;
import mvp.com.zmvp.presenter.base.IAttachView;
import mvp.com.zmvp.view.IDataListView;

public class DataListFragment extends BaseFragment implements IDataListView {

    private DataListPresenter mPresenter;


    public static DataListFragment newInstance() {
        return new DataListFragment();
    }

    @Override
    public int getLayoutID() {
        return R.layout.fragment_list;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView(inflater.inflate(getLayoutID(), container, false));
    }


    public View initView(View rootView) {
        final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
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


    @Override
    public void setPresenter(DataListPresenter presenter) {
        mPresenter = presenter;
    }


    private void updateData() {
        if (null != mPresenter) {
            mPresenter.updateListData();
        }
    }

}
