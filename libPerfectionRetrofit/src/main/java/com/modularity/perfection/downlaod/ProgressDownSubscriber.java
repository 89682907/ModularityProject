package com.modularity.perfection.downlaod;

import com.modularity.perfection.downlaod.progress.DownloadProgressListener;
import com.modularity.perfection.downlaod.progress.PerfectionDownProgressListener;
import com.modularity.perfection.exception.PerfectionThrowable;
import com.modularity.perfection.base.BaseSubscriber;

import java.lang.ref.WeakReference;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;

/**
 * 用于在Http请求开始时，自动显示一个ProgressDialog
 * 在Http请求结束是，关闭ProgressDialog
 * 调用者自己对请求数据进行处理
 */
public class ProgressDownSubscriber extends BaseSubscriber implements DownloadProgressListener {
    //弱引用结果回调
    private WeakReference<PerfectionDownProgressListener> mSubscriberOnNextListener;
    /*下载数据*/
    private DownInfo                                      mDownInfo;

    public ProgressDownSubscriber(DownInfo downInfo) {
        this.mSubscriberOnNextListener = new WeakReference<>(downInfo.getListener());
        this.mDownInfo = downInfo;
    }

    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    public void onStart() {
        if (mSubscriberOnNextListener.get() != null) {
            mSubscriberOnNextListener.get().onStart();
        }
        mDownInfo.setState(DownState.START);
    }

    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     */

    @Override
    public void onError(PerfectionThrowable e) {
        /*停止下载*/
        PerfectionDownloader.getInstance().stopDown(mDownInfo);
        if (mSubscriberOnNextListener.get() != null) {
            mSubscriberOnNextListener.get().onError(e);
        }
        mDownInfo.setState(DownState.ERROR);
    }


    /**
     * 完成，隐藏ProgressDialog
     */
    @Override
    public void onComplete() {
        if (mSubscriberOnNextListener.get() != null) {
            mSubscriberOnNextListener.get().onComplete();
        }
        mDownInfo.setState(DownState.FINISH);
    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     */
    @Override
    public void onNext(Object o) {
        if (mSubscriberOnNextListener.get() != null) {
            mSubscriberOnNextListener.get().onNext(o);
        }
    }


    @Override
    public void update(long read, long count, boolean done) {
        if (mDownInfo.getCountLength() > count) {
            read = mDownInfo.getCountLength() - count + read;
        } else {
            mDownInfo.setCountLength(count);
        }
        mDownInfo.setReadLength(read);
        if (mSubscriberOnNextListener.get() != null) {
            /*接受进度消息，造成UI阻塞，如果不需要显示进度可去掉实现逻辑，减少压力*/
            Observable.just(read).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            /*如果暂停或者停止状态延迟，不需要继续发送回调，影响显示*/
                            if (mDownInfo.getState() == DownState.PAUSE || mDownInfo.getState() == DownState.STOP)
                                return;
                            mDownInfo.setState(DownState.DOWN);
                            mSubscriberOnNextListener.get().updateProgress(aLong, mDownInfo.getCountLength());
                        }
                    });
        }
    }
}