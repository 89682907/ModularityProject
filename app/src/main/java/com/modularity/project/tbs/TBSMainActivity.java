package com.modularity.project.tbs;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.modularity.common.base.BaseActivity;
import com.modularity.common.utils.managers.manager.LogManager;
import com.modularity.project.R;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

public class TBSMainActivity extends BaseActivity {

    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tbs_main_activity);
        webView = findViewById(R.id.webView);
//        webView.loadUrl("https://m.laikang.com/qa/test/#/list");
//        loadUrl("https://www.hao123.com");
//        loadUrl("file:///android_asset/es6.html");
//        loadUrl("https://m.laikang.com/pro/smartMedication/#/selfSmartDrugs/searchMedicine?userId=761");
        loadUrl("http://10.4.108.5:9529/qa/aioMirror/#/slowDiseaseVisits/question?type=1&userId=6948");
    }


    @SuppressLint("SetJavaScriptEnabled")
    private void loadUrl(String url){
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                webView.loadUrl(s);
                return true;
            }

            @Override
            public void onReceivedError(WebView webView, int i, String s, String s1) {
                LogManager.i("TBS_X5","网页加载失败");
            }
        });
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView webView, int i) {
                if (i == 100){
                    LogManager.i("TBS_X5","加载完成");
                }
            }
        });
    }

}
