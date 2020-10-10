package com.modularity.perfection;

import com.modularity.perfection.exception.PerfectionThrowable;

public abstract class PerfectionCallBack<T>{
     public void onStart(){ }

     public void onComplete(){}

     public abstract void onSuccess(T resData);

     public abstract void onError(PerfectionThrowable err);
}
