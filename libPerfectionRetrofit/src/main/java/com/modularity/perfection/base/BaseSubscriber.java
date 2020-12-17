
package com.modularity.perfection.base;


import com.modularity.perfection.annotation.KeepNotProguard;
import com.modularity.perfection.exception.PerfectionException;
import com.modularity.perfection.exception.PerfectionThrowable;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;


/**
 * BaseSubscriber
 */
@KeepNotProguard
public abstract class BaseSubscriber<T> implements Observer<T> {

    private Disposable mDisposable;

    /**
     * 做请求前处理
     */
    public abstract void onStart();

    @Override
    public void onSubscribe(Disposable d) {
        mDisposable = d;
        onStart();
    }

    /**
     * 请求异常
     */
    @Override
    public void onError(Throwable e) {
        if (e instanceof PerfectionThrowable) {
            onError((PerfectionThrowable) e);
        } else {
            onError(new PerfectionThrowable(e, PerfectionException.ERROR.UNKNOWN));
        }
    }


    public abstract void onError(PerfectionThrowable e);

    public void disposable() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

}
