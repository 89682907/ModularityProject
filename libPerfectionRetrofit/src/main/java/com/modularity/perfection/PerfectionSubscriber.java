package com.modularity.perfection;

import android.util.Log;

import com.modularity.perfection.exception.PerfectionException;
import com.modularity.perfection.exception.PerfectionThrowable;
import com.modularity.perfection.base.BaseSubscriber;
import com.modularity.perfectionRetrofit.BuildConfig;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory;
import com.tamic.novate.util.ReflectionUtil;


import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Objects;

import okhttp3.ResponseBody;

class PerfectionSubscriber<T> extends BaseSubscriber<ResponseBody> {
    private PerfectionCallBack<T> mCallBack;

    PerfectionSubscriber(PerfectionCallBack<T> callBack) {
        this.mCallBack = callBack;
    }


    public void onStart() {
        if (this.mCallBack != null) {
            this.mCallBack.onStart();
        }

    }

    public void onError(PerfectionThrowable e) {
        this.onComplete();
        if (this.mCallBack != null) {
            this.mCallBack.onError(e);
        }

    }

    public void onNext(ResponseBody responseBody) {
        try {
            byte[] bytes = responseBody.bytes();
            String jsStr = new String(bytes);
            if (BuildConfig.DEBUG) {
                Log.i(Perfection.Builder.LOG_TAG, "ResponseValue:" + jsStr);
            }
            parseByMosh(jsStr);
        } catch (Exception var4) {
            var4.printStackTrace();
            this.onError(PerfectionException.handleException(var4));
        }
    }

//    private void parseByFastJson(String jsStr) {
//        if (this.mCallBack != null) {
//            this.mCallBack.onSuccess(JSON.parseObject(jsStr, this.mClassType));
//        }
//    }

    private void parseByMosh(String jsStr) throws PerfectionThrowable {
        if (this.mCallBack != null) {
            Type classType = Objects.requireNonNull(ReflectionUtil.getParameterizedTypes(mCallBack))[0];
            String typeName = ReflectionUtil.getClassName(classType);
            if (BuildConfig.DEBUG) {
                Log.i(Perfection.Builder.LOG_TAG, "ClassType:" + typeName);
            }
            if ("java.lang.String".equals(typeName)) {
                this.mCallBack.onSuccess((T) jsStr);
            } else {
                Moshi moshi = new Moshi.Builder()
                        .add(new KotlinJsonAdapterFactory())
                        .build();
                JsonAdapter<T> jsonAdapter = moshi.adapter(classType);
                try {
                    this.mCallBack.onSuccess(jsonAdapter.fromJson(jsStr));
                } catch (IOException e) {
                    throw PerfectionException.handleException(new Exception("数据解析失败IO[" + jsStr + "]"));
                }
            }
        } else {
            throw PerfectionException.handleException(new Exception("数据解析失败[" + jsStr + "]"));
        }
    }

    public void onComplete() {
        if (this.mCallBack != null) {
            this.mCallBack.onComplete();
        }
    }
}
