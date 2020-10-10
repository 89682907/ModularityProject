package com.modularity.common.utils.global;


import android.annotation.SuppressLint;
import android.content.Context;

import com.modularity.common.utils.managers.manager.Managers;

import java.util.HashMap;
import java.util.Map;


public class Global {
    private Context             mContext;
    private Map<String, Object> mMapParams;

    private Global() {
        mMapParams = new HashMap<>();
    }


    public void setContext(Context mContext) {
        this.mContext = mContext.getApplicationContext();
    }

    public Context getContext() {
        return mContext == null ? Managers.getApp() : mContext;
    }

    public void setParams(String key, Object params) {
        mMapParams.put(key, params);
    }

    public Object getParams(String key) {
        return mMapParams.get(key);
    }

    private static class GlobalParamsHolder {
        @SuppressLint("StaticFieldLeak")
        private static final Global INSTANCE = new Global();
    }

    public static Global getInstance() {
        return Global.GlobalParamsHolder.INSTANCE;
    }
}
