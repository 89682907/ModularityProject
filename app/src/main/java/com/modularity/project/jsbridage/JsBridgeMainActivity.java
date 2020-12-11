package com.modularity.project.jsbridage;


import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.lk.community.jsbridge.BridgeHandler;
import com.lk.community.jsbridge.CallBackFunction;
import com.lk.community.jsbridge.DefaultHandler;
import com.lk.community.view.WebView;
import com.modularity.common.base.BaseActivity;
import com.modularity.common.utils.managers.manager.BarManager;
import com.modularity.common.utils.managers.manager.ToastManager;
import com.modularity.project.R;

public class JsBridgeMainActivity extends BaseActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_js_bridge_main);
        BarManager.setStatusBarVisibility(this, false);
        mWebView = findViewById(R.id.webView);
//        mWebView.loadUrl("file:///android_asset/jsbridage_demo.html");
        mWebView.loadUrl("https://m.laikang.com/qa/shangMirrorQa/#/list");
        jsToJavaMethod();
        jsToJavaMethodDefault();
        findViewById(R.id.button).setOnClickListener(view -> javaToJsMethod());
        findViewById(R.id.button2).setOnClickListener(view -> javaToJsDefault());
    }

    /**
     * Java提供方法给JS调用
     * java注册一个方法给js调用
     * <p>
     * JS端调用该方法
     * WebViewJavascriptBridge.callHandler(
     * 'submitFromWeb'
     * , {'param': str1}
     * , function(responseData) {
     * document.getElementById("show").innerHTML = "send get responseData from java, data = " + responseData
     * }
     * );
     */
    private void jsToJavaMethod() {
        mWebView.registerHandler("submitFromWeb", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i("jishen", "web传过来的数据" + data);
                ToastManager.showLong("web传过来的数据submitFromWeb:" + data);
                function.onCallBack("method回调数据给web");
            }
        });
    }

    /**
     * Java提供方法给JS调用
     * 使用默认handler 不用自定义方法
     * <p>
     * JS端发送消息
     * window.WebViewJavascriptBridge.send(
     * data
     * , function(responseData) {
     * document.getElementById("show").innerHTML = "repsonseData from java, data = " + responseData
     * }
     * );
     */
    private void jsToJavaMethodDefault() {
        //Java端设置handler
        mWebView.setDefaultHandler(new DefaultHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i("jishen", "web传过来的数据" + data);
                ToastManager.showLong("web传过来的数据DefaultHandler:" + data);
                function.onCallBack("default回调数据给web");
            }
        });
    }

    /**
     * JS提供方法给java调用
     * JS端
     * WebViewJavascriptBridge.registerHandler("functionInJs", function(data, responseCallback) {
     * document.getElementById("show").innerHTML = ("data from Java: = " + data);
     * var responseData = "Javascript Says Right back aka!";
     * responseCallback(responseData);
     * });
     */
    private void javaToJsMethod() {
        User user = new User();
        Location location = new Location();
        location.address = "SDU";
        user.location = location;
        user.name = "大头鬼";

        mWebView.callHandler("functionInJs", new Gson().toJson(user), new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                Log.i("jishen", data);
                ToastManager.showLong("functionInJs:" + data);
            }
        });
    }

    /**
     * JS监听Java端的消息
     * bridge.init(function(message, responseCallback) {
     * console.log('JS got a message', message);
     * var data = {
     * 'Javascript Responds': 'Wee!'
     * };
     * console.log('JS responding with', data);
     * responseCallback(data);
     * });
     */

    private void javaToJsDefault() {
        mWebView.send("hello", new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                Log.i("jishen", "javaToJsDefault:" + data);
                ToastManager.showLong("javaToJsDefault:" + data);
            }
        });
    }

    /**
     * 该库会向JS中的window对象中注入WebViewJavascriptBridge对象，
     * 所以在js中，在使用WebViewJavascriptBridge之前，必须检测WebViewJavascriptBridge是否存在。
     * 如果WebViewJavascriptBridge未退出，则可以侦听WebViewJavascriptBridgeReady事件
     * <p>
     * if (window.WebViewJavascriptBridge) {
     * //do your work here
     * } else {
     * document.addEventListener(
     * 'WebViewJavascriptBridgeReady'
     * , function() {
     * //do your work here
     * },
     * false
     * );
     * }
     */

    static class Location {
        String address;
    }

    static class User {
        String   name;
        Location location;
    }

}