package com.modularity.perfectionRetrofit;

import com.alibaba.fastjson.JSON;
import com.modularity.perfectionRetrofit.base.BaseSubscriber;
import com.modularity.perfectionRetrofit.exception.PerfectionException;
import com.modularity.perfectionRetrofit.exception.PerfectionThrowable;

import java.lang.reflect.Type;

import okhttp3.ResponseBody;

/**
 * Created by jishen on 2017/1/22.
 */

class PerfectionSubscriber<T> extends BaseSubscriber<ResponseBody> {
    private PerfectionCallBack<T> mCallBack;
    private Type                  mClassType;

    PerfectionSubscriber(Type mClassType, PerfectionCallBack<T> callBack) {
        this.mCallBack = callBack;
        this.mClassType = mClassType;
    }


    @Override
    public void onStart() {
        if (mCallBack != null) {
            mCallBack.onStart();
        }
    }


    @Override
    public void onError(PerfectionThrowable e) {
        onComplete();//RxJava中onComplete()和onError()只会调用一个
        if (mCallBack != null) {
            mCallBack.onError(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onNext(ResponseBody responseBody) {
        try {
            byte[] bytes = responseBody.bytes();
            String jsStr = new String(bytes);
            parserFastJson(jsStr);
        } catch (Exception e) {
            e.printStackTrace();
            onError(PerfectionException.handleException(e));
        }

    }

    @Override
    public void onComplete() {
        if (mCallBack != null) {
            mCallBack.onComplete();
        }
    }

    @SuppressWarnings("unchecked")
    private void parserFastJson(String jsStr) {
        if (mCallBack != null) {
            mCallBack.onSuccess((T) JSON.parseObject(jsStr, mClassType));
        }
    }


}
