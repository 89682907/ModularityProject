package com.modularity.common.expand.adaptive;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

public class ViewFitForKeyboardManager {

    private ViewFitForKeyboardManager() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void assistActivity(Activity activity) {
        new ViewFitForKeyboardManager(activity);
    }

    private View mChildOfContent;
    private int usableHeightPrevious;
    private FrameLayout.LayoutParams frameLayoutParams;

    private ViewFitForKeyboardManager(Activity activity) {
        //Decorview里分为title和content，content即是承载我们setContentView方法的布局的根布局
        FrameLayout content = (FrameLayout) activity.findViewById(android.R.id.content);
        //mChildOfContent我们setContentView方法的布局
        mChildOfContent = content.getChildAt(0);
        //监听布局变化，任何界面变化都会触发该监听
        //软键盘弹起同样也会触发该监听
        mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                possiblyResizeChildOfContent(activity);
            }
        });
        frameLayoutParams = (FrameLayout.LayoutParams) mChildOfContent.getLayoutParams();
    }

    private void possiblyResizeChildOfContent(Activity activity) {
        int usableHeightNow = computeUsableHeight(activity);
        if (usableHeightNow != usableHeightPrevious) {
            int usableHeightSansKeyboard = mChildOfContent.getRootView().getHeight();
            //计算布局变化的高度
            int heightDifference = usableHeightSansKeyboard - usableHeightNow;
//            if (heightDifference > (usableHeightSansKeyboard / 4)) {
//                // keyboard probably just became visible
//                //如果布局变化的高度大于全屏高度的4分之一，则认为可能是键盘弹出，需要改变我们setContentView的布局高度
//                frameLayoutParams.height = usableHeightSansKeyboard - heightDifference;
//            } else {
//                // keyboard probably just became hidden
//                frameLayoutParams.height = usableHeightSansKeyboard;
//            }
            frameLayoutParams.height = usableHeightSansKeyboard - heightDifference;

            //布局改变后重绘
            mChildOfContent.requestLayout();
            usableHeightPrevious = usableHeightNow;
        }
    }

    //计算去掉键盘高度后的可用高度
    private int computeUsableHeight(Activity activity) {
        Rect r = new Rect();
        mChildOfContent.getWindowVisibleDisplayFrame(r);
//        return (r.bottom - r.top);// 全屏模式下： return r.bottom
//        if (NotchManager.hasNotchInScreen(activity)) {
//            return r.bottom - BarManager.getStatusBarHeight();
//        }
        return r.bottom - r.top;
    }

}
