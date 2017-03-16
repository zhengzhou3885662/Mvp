package mvp.com.zmvp.ui.activity.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import me.yokeyword.fragmentation.SupportActivity;
import mvp.com.zmvp.R;
import mvp.com.zmvp.ui.listener.IUiInit;
import mvp.com.zmvp.widgets.SystemBarTintManager;

/**
 * Created by zz on 2017/1/9.
 * 所有通用基类
 */

abstract class AndBaseActivity extends SupportActivity implements IUiInit {
    /**
     * 检查写入权限
     */
    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;

    /**
     * 检查读取权限
     */
    protected static final int REQUEST_STORAGE_WRITE_ACCESS_PERMISSION = 102;

    /**
     * 检查拍照录像权限
     */
    protected static final int REQUEST_STORAGE_CAMERA_ACCESS_PERMISSION = 110;


    /**
     * 支持状态栏统一颜色
     */
    private SystemBarTintManager mTintManager;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTranslucentStatus();
        setContentView(getLayoutID());
    }


    /**
     * 统一状态栏颜色
     */
    private void initTranslucentStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            mTintManager = new SystemBarTintManager(this);
            mTintManager.setStatusBarTintEnabled(true);
            setStatusBarTintResource(R.color.colorPrimaryDark);
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public void setStatusBarTintResource(int res) {
        if (null != mTintManager) {
            mTintManager.setStatusBarTintResource(res);
        }
    }

    /**
     * 设置顶部状态栏颜色，默认为灰色透明
     *
     * @param drawable
     */
    public void setStatusBarTintDrawable(Drawable drawable) {
        if (null != mTintManager) {
            mTintManager.setStatusBarTintDrawable(drawable);
        }
    }


    /**
     * 隐藏键盘输入法
     *
     * @param view
     */
    public void hideInputMethodManager(View view) {
        InputMethodManager mInputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mInputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    /**
     * Requests given permission.
     * If the permission has been denied previously, a Dialog will prompt the user to grant the
     * permission, otherwise it is requested directly.
     */
    protected void requestPermission(final String permission, String rationale, final int requestCode) {
        requestPermission(permission, rationale, requestCode, null);
    }

    protected void requestPermission(final String permission, String rationale, final int requestCode, DialogInterface.OnClickListener onCancelLis) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            showAlertDialog(getString(R.string.permission_title_rationale), rationale,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(AndBaseActivity.this, new String[]{permission}, requestCode);
                        }
                    }, getString(R.string.confirm), onCancelLis, getString(R.string.cancel));
        } else {
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        }
    }


    /**
     * This method shows dialog with given title & message.
     * Also there is an option to pass onClickListener for positive & negative button.
     *
     * @param title                         - dialog title
     * @param message                       - dialog message
     * @param onPositiveButtonClickListener - listener for positive button
     * @param positiveText                  - positive button text
     * @param onNegativeButtonClickListener - listener for negative button
     * @param negativeText                  - negative button text
     */
    protected void showAlertDialog(@Nullable String title, @Nullable String message,
                                   @Nullable DialogInterface.OnClickListener onPositiveButtonClickListener,
                                   @NonNull String positiveText,
                                   @Nullable DialogInterface.OnClickListener onNegativeButtonClickListener,
                                   @NonNull String negativeText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton(positiveText, onPositiveButtonClickListener);
        builder.setNegativeButton(negativeText, onNegativeButtonClickListener);
        builder.show();
//        mAlertDialog = builder.show();
    }

    protected void showAlertDialog(@Nullable String message,
                                   @Nullable DialogInterface.OnClickListener onPositiveButtonClickListener,
                                   @NonNull String positiveText,
                                   @Nullable DialogInterface.OnClickListener onNegativeButtonClickListener,
                                   @NonNull String negativeText) {
        showAlertDialog("", message, onPositiveButtonClickListener, positiveText, onNegativeButtonClickListener, negativeText);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        if (mAlertDialog != null && mAlertDialog.isShowing()) {
//            mAlertDialog.dismiss();
//        }
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (mPresenter != null) {
//            mPresenter.detachView();
//        }
//    }

}
