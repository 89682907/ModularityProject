package com.modularity.perfectionRetrofit;

import android.util.Log;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.modularity.perfectionRetrofit.base.BaseSubscriber;
import com.modularity.perfectionRetrofit.exception.PerfectionException;
import com.modularity.perfectionRetrofit.exception.PerfectionThrowable;
import java.lang.reflect.Type;
import okhttp3.ResponseBody;

class PerfectionSubscriber<T> extends BaseSubscriber<ResponseBody> {
    private PerfectionCallBack<T> mCallBack;
    private Type mClassType;
    private boolean isParserJson;

    PerfectionSubscriber(Type mClassType, PerfectionCallBack<T> callBack) {
        this.mCallBack = callBack;
        this.mClassType = mClassType;
        this.isParserJson = !mClassType.getClass().getName().equals("java.lang.String");
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

            if (this.isParserJson) {
                this.parserFastJson(jsStr);
            } else if (this.mCallBack != null) {
                this.mCallBack.onSuccess((T) jsStr);
            }
        } catch (Exception var4) {
            var4.printStackTrace();
            this.onError(PerfectionException.handleException(var4));
        }

    }

    public void onComplete() {
        if (this.mCallBack != null) {
            this.mCallBack.onComplete();
        }

    }

    private void parserFastJson(String jsStr) {
        if (this.mCallBack != null) {
            this.mCallBack.onSuccess(JSON.parseObject(jsStr, this.mClassType, new Feature[0]));
        }

    }
}
