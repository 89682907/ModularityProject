package com.modularity.common.base;


import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
        setStateBarStyle(true);
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
    protected void onSaveInstanceState(@NonNull Bundle outState) {
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
     * 将状态栏颜色变为深色
     *
     * @param dark true
     */
    public void setStateBarStyle(boolean dark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            if (dark) {
                decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                decor.setSystemUiVisibility(0);
            }
        }
    }

}
