package com.modularity.perfection.downlaod.progress;

/**
 * 下载过程中的回调处理
 */
public abstract class PerfectionDownProgressListener<T> {
    /**
     * 成功后回调方法
     */
    public abstract void onNext(T t);

    /**
     * 开始下载
     */
    public abstract void onStart();

    /**
     * 完成下载
     */
    public abstract void onComplete();


    /**
     * 下载进度
     */
    public abstract void updateProgress(long readLength, long countLength);

    /**
     * 失败或者错误方法
     * 主动调用，更加灵活
     */
    public void onError(Throwable e) {
    }

    /**
     * 暂停下载
     */
    public void onPause() {
    }

    /**
     * 停止下载销毁
     */
    public void onStop() {
    }
}
