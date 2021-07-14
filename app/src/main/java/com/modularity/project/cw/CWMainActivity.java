package com.modularity.project.cw;


import android.os.Bundle;

import com.modularity.common.utils.managers.manager.LogManager;
import com.modularity.lib.crosswalk.view.AbsXWalkActivity;
import com.modularity.project.R;

import org.xwalk.core.XWalkResourceClient;
import org.xwalk.core.XWalkView;

public class CWMainActivity extends AbsXWalkActivity {

    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    protected void onXWalkReady() {
        super.onXWalkReady();
        initClient();
        load();
    }

    private void initData() {
        url = getIntent().getStringExtra("url");
    }

    private void load() {
        xWalkView.loadUrl(url);
    }

    protected void initClient() {
        if (xWalkView != null) {
            xWalkView.setResourceClient(new XWalkResourceClient(xWalkView) {
                @Override
                public void onLoadStarted(XWalkView view, String url) {
                    super.onLoadStarted(view, url);
                    LogManager.iTag("jishen", "开始加载");
                }

                @Override
                public void onLoadFinished(XWalkView view, String url) {
                    super.onLoadFinished(view, url);
                    LogManager.iTag("jishen", "加载结束");
                }

                @Override
                public void onReceivedLoadError(XWalkView view, int errorCode, String description, String failingUrl) {
                    super.onReceivedLoadError(view, errorCode, description, failingUrl);
                    LogManager.iTag("jishen", "加载错误");
                }
            });
        }
    }

    @Override
    protected int contentViewId() {
        return R.layout.activity_cw;
    }

    @Override
    protected int xWalkViewId() {
        return R.id.xWalkView;
    }
}