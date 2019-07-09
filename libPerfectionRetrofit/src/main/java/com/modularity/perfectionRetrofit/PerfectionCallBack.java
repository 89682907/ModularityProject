package com.modularity.perfectionRetrofit;

import com.modularity.perfectionRetrofit.exception.PerfectionThrowable;

public interface PerfectionCallBack<T> {
     void onStart();

     void onComplete();

     void onSuccess(T var1);

     void onError(PerfectionThrowable var1);
}
