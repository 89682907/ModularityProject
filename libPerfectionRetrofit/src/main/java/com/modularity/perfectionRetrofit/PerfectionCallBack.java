package com.modularity.perfectionRetrofit;

import com.modularity.perfectionRetrofit.exception.PerfectionThrowable;

public interface PerfectionCallBack<T> {
     void onStart();

     void onComplete();

     void onSuccess(T resData);

     void onError(PerfectionThrowable err);
}
