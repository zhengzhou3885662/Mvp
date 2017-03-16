package mvp.com.zmvp.ui.activity.base;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

/**
 * Created by zz on 2017/1/9.
 * Activity UI 项目通用基类
 */

public abstract class BaseActivity extends AndBaseActivity  {

    /**
     * 点击事件隐藏输入法弹出框
     */
    private boolean isTouchOutHideInput = true;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    /**
     * 在点击屏幕控件时隐藏输入法弹出框，默认状态 true
     *
     * @param touchOutHideInput
     */
    public void setTouchOutHideInput(boolean touchOutHideInput) {
        isTouchOutHideInput = touchOutHideInput;
    }


    /**
     * 触发ACTION_DOWN 事件时，如果不是EditText控件时，隐藏弹出键盘
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (isTouchOutHideInput && ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (isShouldHideInput(view, ev)) {
                hideInputMethodManager(view);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 0
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param view
     * @param event
     * @return
     */
    static boolean isShouldHideInput(View view, MotionEvent event) {
        if (null != view && view instanceof EditText) {
            int[] location = {0, 0};
            view.getLocationInWindow(location);
            int left = location[0], top = location[1], bottom = top + view.getHeight(), right = left + view.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

}
