package com.modularity.perfectionRetrofit.downlaod.progress;


/**
 * 成功回调处理
 */
public interface DownloadProgressListener {
    /**
     * 下载进度
     */
    void update(long read, long count, boolean done);
}
