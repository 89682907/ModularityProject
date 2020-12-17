package com.modularity.perfection.downlaod;


import com.modularity.perfection.annotation.KeepNotProguard;
import com.modularity.perfection.downlaod.exception.HttpTimeException;
import com.modularity.perfection.downlaod.exception.RetryWhenNetworkException;
import com.modularity.perfection.downlaod.progress.DownloadInterceptor;
import com.modularity.perfection.base.BaseApiService;
import com.modularity.perfection.https.PerfectionHttpsFactory;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * 下载处理类
 */
@KeepNotProguard
public class PerfectionDownloader {
    private volatile static PerfectionDownloader                    mDownManager;
    /*记录下载数据*/
    private                 Set<DownInfo>                           mInfoSet;
    /*回调sub队列*/
    private                 HashMap<String, ProgressDownSubscriber> mSubMap;
    /*单利对象*/

    private PerfectionDownloader() {
        mInfoSet = new HashSet<>();
        mSubMap = new HashMap<>();
    }

    /**
     * 获取单例
     */
    public static PerfectionDownloader getInstance() {
        if (mDownManager == null) {
            synchronized (PerfectionDownloader.class) {
                if (mDownManager == null) {
                    mDownManager = new PerfectionDownloader();
                }
            }
        }
        return mDownManager;
    }


    /**
     * 开始下载
     */
    public void startDown(final DownInfo info) {
        /*正在下载不处理*/
        if (info == null || mSubMap.get(info.getUrl()) != null) {
            return;
        }
        /*添加回调处理类*/
        ProgressDownSubscriber subscriber = new ProgressDownSubscriber(info);
        /*记录回调sub*/
        mSubMap.put(info.getUrl(), subscriber);
        /*获取service，多次请求公用一个service*/
        BaseApiService httpService;
        if (mInfoSet.contains(info)) {
            httpService = info.getService();
        } else {
            DownloadInterceptor interceptor = new DownloadInterceptor(subscriber);
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            //手动创建一个OkHttpClient并设置超时时间
            builder.connectTimeout(info.getConnectionTime(), TimeUnit.SECONDS);
            builder.addInterceptor(interceptor);

            //https默认支持所有证书
            builder.sslSocketFactory(PerfectionHttpsFactory.getDefaultSSLSocketFactory());
            builder.hostnameVerifier(PerfectionHttpsFactory.getDefaultHostnameVerifier());
            Retrofit retrofit = new Retrofit.Builder()
                    .client(builder.build())
                    .addConverterFactory(MoshiConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .baseUrl(info.getBaseUrl())
                    .build();
            httpService = retrofit.create(BaseApiService.class);
            info.setService(httpService);
        }
        /*得到rx对象-上一次下載的位置開始下載*/
        httpService.download("bytes=" + info.getReadLength() + "-", info.getUrl())
                /*指定线程*/
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                /*失败后的retry配置*/
                .retryWhen(new RetryWhenNetworkException())
                /*读取下载写入文件*/
                .map(new Function<ResponseBody, Object>() {
                    @Override
                    public Object apply(ResponseBody responseBody) throws Exception {
                        try {
                            writeCache(responseBody, new File(info.getSavePath()), info);
                        } catch (Exception e) {
                            /*失败抛出异常*/
                            throw new HttpTimeException(e.getMessage());
                        }
                        return info;
                    }
                })
                /*回调线程*/
                .observeOn(AndroidSchedulers.mainThread())
                /*数据回调*/
                .subscribe(subscriber);

    }


    /**
     * 停止下载
     */
    public void stopDown(DownInfo info) {
        if (info == null) return;
        info.setState(DownState.STOP);
        info.getListener().onStop();
        if (mSubMap.containsKey(info.getUrl())) {
            ProgressDownSubscriber subscriber = mSubMap.get(info.getUrl());
            if (subscriber != null) {
                subscriber.disposable();
            }
            mSubMap.remove(info.getUrl());
        }
        /*同步数据库*/
    }


    /**
     * 删除
     */
    public void deleteDown(DownInfo info) {
        stopDown(info);
        /*删除数据库信息和本地文件*/
    }


    /**
     * 暂停下载
     */
    public void pause(DownInfo info) {
        if (info == null) return;
        info.setState(DownState.PAUSE);
        info.getListener().onPause();
        if (mSubMap.containsKey(info.getUrl())) {
            ProgressDownSubscriber subscriber = mSubMap.get(info.getUrl());
            if (subscriber != null) {
                subscriber.disposable();
            }
            mSubMap.remove(info.getUrl());
        }
        /*这里需要讲info信息写入到数据中，可自由扩展，用自己项目的数据库*/
    }

    /**
     * 停止全部下载
     */
    public void stopAllDown() {
        for (DownInfo downInfo : mInfoSet) {
            stopDown(downInfo);
        }
        mSubMap.clear();
        mInfoSet.clear();
    }

    /**
     * 暂停全部下载
     */
    public void pauseAll() {
        for (DownInfo downInfo : mInfoSet) {
            pause(downInfo);
        }
        mSubMap.clear();
        mInfoSet.clear();
    }


    /**
     * 返回全部正在下载的数据
     */
    public Set<DownInfo> getInfoSet() {
        return mInfoSet;
    }


    /**
     * 写入文件
     */
    private void writeCache(ResponseBody responseBody, File file, DownInfo info) throws Exception {
        if (!Objects.requireNonNull(file.getParentFile()).exists())
            file.getParentFile().mkdirs();
        long allLength;
        if (info.getCountLength() == 0) {
            allLength = responseBody.contentLength();
        } else {
            allLength = info.getCountLength();
        }
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rwd");
        FileChannel channelOut = randomAccessFile.getChannel();
        MappedByteBuffer mappedBuffer = channelOut.map(FileChannel.MapMode.READ_WRITE, info.getReadLength(), allLength - info.getReadLength());
        byte[] buffer = new byte[1024 * 8];
        int len;
        while ((len = responseBody.byteStream().read(buffer)) != -1) {
            mappedBuffer.put(buffer, 0, len);
        }
        responseBody.byteStream().close();
        channelOut.close();
        randomAccessFile.close();
    }

}
