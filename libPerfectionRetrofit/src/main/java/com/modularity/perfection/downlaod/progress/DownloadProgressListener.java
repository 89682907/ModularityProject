package com.modularity.perfection.downlaod.progress;


import com.modularity.perfection.annotation.KeepNotProguard;

/**
 * 成功回调处理
 */
@KeepNotProguard
public interface DownloadProgressListener {
    /**
     * 下载进度
     */
    void update(long read, long count, boolean done);
}
