package com.modularity.perfectionRetrofit.downlaod;


import com.modularity.perfectionRetrofit.base.BaseApiService;
import com.modularity.perfectionRetrofit.downlaod.progress.PerfectionDownProgressListener;

/**
 * apk下载请求数据基础类
 */

public class DownInfo {

    /*超时设置*/
    private int defaultTimeout = 10;
    /*存储位置*/
    private String                         savePath;
    /*下载url*/
    private String                         url;
    /*基础url*/
    private String                         baseUrl;
    /*文件总长度*/
    private long                           countLength;
    /*下载长度*/
    private long                           readLength;
    /*下载唯一的HttpService*/
    private BaseApiService                 service;
    /*回调监听*/
    private PerfectionDownProgressListener listener;
    /*下载状态*/
    private DownState                      state;


    public DownInfo(String url, PerfectionDownProgressListener listener) {
        setUrl(url);
        setBaseUrl(getBasUrl(url));
        setListener(listener);
    }

    public DownState getState() {
        return state;
    }

    public void setState(DownState state) {
        this.state = state;
    }

    public DownInfo(String url) {
        setUrl(url);
        setBaseUrl(getBasUrl(url));
    }

    public int getConnectionTime() {
        return defaultTimeout;
    }

    public void setConnectionTime(int connectionTime) {
        this.defaultTimeout = connectionTime;
    }

    public PerfectionDownProgressListener getListener() {
        return listener;
    }

    public void setListener(PerfectionDownProgressListener listener) {
        this.listener = listener;
    }

    public BaseApiService getService() {
        return service;
    }

    public void setService(BaseApiService service) {
        this.service = service;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public long getCountLength() {
        return countLength;
    }

    public void setCountLength(long countLength) {
        this.countLength = countLength;
    }


    public long getReadLength() {
        return readLength;
    }

    public void setReadLength(long readLength) {
        this.readLength = readLength;
    }

    /**
     * 读取baseurl
     */
    protected String getBasUrl(String url) {
        String head = "";
        int index = url.indexOf("://");
        if (index != -1) {
            head = url.substring(0, index + 3);
            url = url.substring(index + 3);
        }
        index = url.indexOf("/");
        if (index != -1) {
            url = url.substring(0, index + 1);
        }
        return head + url;
    }
}
