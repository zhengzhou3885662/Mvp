package mvp.com.zmvp.ui.fragment.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.yokeyword.fragmentation.SupportFragment;
import mvp.com.zmvp.R;
import mvp.com.zmvp.ui.listener.IUiInit;

/**
 * Created by zz on 2017/1/9.
 * 基础通用内容 Fragment
 */

abstract class AndBaseFragment extends SupportFragment implements IUiInit{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
