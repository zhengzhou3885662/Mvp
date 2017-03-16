package mvp.com.zmvp.ui.activity;

import android.os.Bundle;

import mvp.com.zmvp.R;
import mvp.com.zmvp.ui.activity.base.BaseActivity;
import mvp.com.zmvp.ui.fragment.DataDetailFragment;
import mvp.com.zmvp.ui.fragment.DataListFragment;
import mvp.com.zmvp.presenter.DataDetailPresenter;
import mvp.com.zmvp.presenter.DataListPresenter;
import mvp.com.zmvp.presenter.impl.DataDetailPresenterImpl;
import mvp.com.zmvp.presenter.impl.DataListPresenterImpl;

/**
 * 搜索UI
 */
public class DataListActivity extends BaseActivity {

    private DataListPresenter mListPresenter;
    private DataDetailPresenter mDetailPresenter;

    @Override
    public int getLayoutID() {
        return R.layout.activity_data_list;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView(savedInstanceState);
    }


    public void initView(Bundle savedInstanceState) {
        if (null == savedInstanceState) {
            DataListFragment listFragment = DataListFragment.newInstance();
            mListPresenter = new DataListPresenterImpl(listFragment);

            DataDetailFragment detailFragment = DataDetailFragment.newInstance();
            mDetailPresenter = new DataDetailPresenterImpl(detailFragment);


            loadMultipleRootFragment(R.id.contentFrame, 0, listFragment, detailFragment);
        }
    }


}
