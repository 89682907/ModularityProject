package com.modularity.lib.crosswalk.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;

import com.modularity.lib.crosswalk.annotation.KeepNotProguard;

import org.xwalk.core.XWalkActivity;
import org.xwalk.core.XWalkSettings;
import org.xwalk.core.XWalkView;

@KeepNotProguard
public abstract class AbsXWalkActivity extends XWalkActivity {

    public XWalkView xWalkView;
    public XWalkSettings xWalkSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(contentViewId());
        xWalkView = findViewById(xWalkViewId());
    }

    @KeepNotProguard
    @SuppressLint("SetJavaScriptEnabled")
    protected void initSetting() {
        if (xWalkView != null) {
            xWalkSettings = xWalkView.getSettings();
            xWalkSettings.setDomStorageEnabled(true);
            xWalkSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
            xWalkSettings.setAllowFileAccess(true);
            xWalkSettings.setDatabaseEnabled(true);
            xWalkSettings.setJavaScriptEnabled(true);
        }
    }

    @KeepNotProguard
    @Override
    protected void onXWalkReady() {
        initSetting();
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        if (xWalkView != null && isXWalkReady()) {
//            xWalkView.pauseTimers();
//            xWalkView.onHide();
//        }
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (xWalkView != null && isXWalkReady()) {
//            xWalkView.resumeTimers();
//            xWalkView.onShow();
//        }
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (xWalkView != null && isXWalkReady()) {
            xWalkView.onDestroy();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (xWalkView != null && isXWalkReady()) {
            xWalkView.onNewIntent(intent);
        }
    }


    @KeepNotProguard
    protected abstract int contentViewId();

    @KeepNotProguard
    protected abstract int xWalkViewId();

}