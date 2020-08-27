package com.modularity.perfectionRetrofit;

import android.util.Log;

//import com.alibaba.fastjson.JSON;
import com.modularity.perfectionRetrofit.base.BaseSubscriber;
import com.modularity.perfectionRetrofit.exception.PerfectionException;
import com.modularity.perfectionRetrofit.exception.PerfectionThrowable;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory;


import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;

class PerfectionSubscriber<T> extends BaseSubscriber<ResponseBody> {
    private PerfectionCallBack<T> mCallBack;
    private Type                  mClassType;

    PerfectionSubscriber(Type mClassType, PerfectionCallBack<T> callBack) {
        this.mCallBack = callBack;
        this.mClassType = mClassType;
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
                Log.i("Retrofit", "ResponseValue:" + jsStr);
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

    private void parseByMosh(String jsStr) throws IOException, PerfectionThrowable {
        if (this.mCallBack != null) {
            Moshi moshi = new Moshi.Builder()
                    .add(new KotlinJsonAdapterFactory())
                    .build();
            JsonAdapter<T> jsonAdapter = moshi.adapter(mClassType);
            this.mCallBack.onSuccess(jsonAdapter.fromJson(jsStr));
        } else {
            throw PerfectionException.handleException(null);
        }
    }

    public void onComplete() {
        if (this.mCallBack != null) {
            this.mCallBack.onComplete();
        }

    }
}
