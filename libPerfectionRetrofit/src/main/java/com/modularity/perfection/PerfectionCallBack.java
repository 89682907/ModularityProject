package com.modularity.perfection;

import com.modularity.perfection.exception.PerfectionThrowable;

public abstract class PerfectionCallBack<T> {
     public void onStart(){}

     public void onComplete(){}

     public void onSuccess(T resData){}

     public void onError(PerfectionThrowable err){}
}
