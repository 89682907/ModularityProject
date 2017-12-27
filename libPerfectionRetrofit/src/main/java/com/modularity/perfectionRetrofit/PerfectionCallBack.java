package com.modularity.perfectionRetrofit;


import com.modularity.perfectionRetrofit.exception.PerfectionThrowable;

/**
 * 请求回调
 * Created by jishen on 2017/1/22.
 */

public interface  PerfectionCallBack<T> {
     void onStart();
     void onComplete();
     void onSuccess(T data);
     void onError(PerfectionThrowable e);
}
