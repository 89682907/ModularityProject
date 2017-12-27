package com.modularity.common.base;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;


public abstract class BaseActivity extends AppCompatActivity {

    public final String  TAG      = this.getClass().getSimpleName();
    protected    boolean isNormal = true; //适配特殊机型所用-是否正常进入 true表示是正常进入 false表示非正常进入

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {// 非正常关闭
            restoreConstants(savedInstanceState);
            isNormal = false;
        }
        setStatuBarStyle(true);
        BaseAppManager.getInstance().addActivity(this);

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * 初始化view
     */
    protected void initViews() {
    }

    /**
     * 初始化数据
     */
    protected void initData() {
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    /**
     * 读取数据-->非正常状态下使用
     */
    protected void restoreConstants(Bundle savedInstanceState) {
    }


    @Override
    public void finish() {
        super.finish();
        BaseAppManager.getInstance().removeActivity(this);
    }


    /**
     * 设置沉浸式状态栏颜色
     */
    public void setStatusBarTintColor(String barColor) {
        // 4.4及以上版本开启
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            // 自定义颜色
            SystemBarTintManager tintManager = new SystemBarTintManager(this);

            tintManager.setTintColor(Color.parseColor(barColor));

            // enable status bar tint
            tintManager.setStatusBarTintEnabled(true);
            // enable navigation bar tint
//            tintManager.setNavigationBarTintEnabled(true);
        }
    }


    @TargetApi(19)
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

    public void setStatusBarColor(int colorResId) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = this.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(this.getResources().getColor(colorResId));

                //底部导航栏
                //window.setNavigationBarColor(activity.getResources().getColor(colorResId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 将状态栏颜色变为深色
     *
     * @param dark true
     */
    public void setStatuBarStyle(boolean dark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            if (dark) {
                decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                // We want to change tint color to white again.
                // You can also record the flags in advance so that you can turn UI back completely if
                // you have set other flags before, such as translucent or full screen.
                decor.setSystemUiVisibility(0);
            }
//            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

}
