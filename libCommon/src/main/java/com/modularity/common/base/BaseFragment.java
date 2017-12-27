package com.modularity.common.base;


import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;


/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment {


    private View view;
    protected BaseActivity mActivity;

    protected abstract void initView(View view, Bundle savedInstanceState);

    //获取布局文件ID
    protected abstract int getLayoutId();

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        this.mActivity = (BaseActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(view==null){
            view=inflater.inflate(getLayoutId(), null);
            initView(view, savedInstanceState);
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }

        return view;
    }

    public void onResume() {
        super.onResume();

    }
    public void onPause() {
        super.onPause();
    }


    /**
     * 设置沉浸式状态栏颜色
     */
    public void setStatusBarTintColor(String barColor){
        // 4.4及以上版本开启
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            // 自定义颜色
            SystemBarTintManager tintManager = new SystemBarTintManager(getActivity());

            tintManager.setTintColor(Color.parseColor(barColor));

            // enable status bar tint
            tintManager.setStatusBarTintEnabled(true);
            // enable navigation bar tint
//            tintManager.setNavigationBarTintEnabled(true);
        }
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getActivity().getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }



}
