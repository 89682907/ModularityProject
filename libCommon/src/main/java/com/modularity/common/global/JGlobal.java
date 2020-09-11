package com.modularity.common.global;


import android.annotation.SuppressLint;
import android.content.Context;

import com.modularity.common.utils.utilcode.util.Utils;

import java.util.HashMap;
import java.util.Map;


public class JGlobal {
    private Context mContext;
    private Map<String, Object> mMapParams;

    private JGlobal() {
        mMapParams = new HashMap<>();
    }


    public void setContext(Context mContext) {
        this.mContext = mContext.getApplicationContext();
    }

    public Context getContext() {
        return mContext == null ? Utils.getApp() : mContext;
    }

    public void setParams(String key, Object params) {
        mMapParams.put(key, params);
    }

    public Object getParams(String key) {
        return mMapParams.get(key);
    }

    private static class GlobalParamsHolder {
        @SuppressLint("StaticFieldLeak")
        private static final JGlobal INSTANCE = new JGlobal();
    }

    public static JGlobal getInstance() {
        return JGlobal.GlobalParamsHolder.INSTANCE;
    }
}
