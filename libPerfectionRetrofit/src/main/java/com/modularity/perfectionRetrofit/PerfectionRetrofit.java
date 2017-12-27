
package com.modularity.perfectionRetrofit;

import android.content.Context;

import android.text.TextUtils;
import android.util.Log;


import com.modularity.perfectionRetrofit.base.BaseApiService;
import com.modularity.perfectionRetrofit.base.BaseInterceptor;
import com.modularity.perfectionRetrofit.base.BaseSubscriber;
import com.modularity.perfectionRetrofit.cache.CacheInterceptor;
import com.modularity.perfectionRetrofit.cache.CacheInterceptorOffline;
import com.modularity.perfectionRetrofit.exception.PerfectionException;
import com.modularity.perfectionRetrofit.https.PerfectionHttpsFactory;
import com.modularity.perfectionRetrofit.https.TrustAllHostnameVerifier;
import com.modularity.perfectionRetrofit.https.TrustAllManager;
import com.modularity.perfectionRetrofit.util.PerfectionUtils;

import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
//import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;
import retrofit2.http.FieldMap;

public final class PerfectionRetrofit {

    private BaseApiService        mBaseApiService;
    private Retrofit              mRetrofit;
    private ObservableTransformer mExceptTransformer;
    private BaseSubscriber        mSubscriber;

    private PerfectionRetrofit(Retrofit retrofit, BaseApiService apiManager) {
        this.mBaseApiService = apiManager;
        this.mRetrofit = retrofit;
    }

    /**
     * 创建 ApiService
     */
    public <T> T create(final Class<T> service) {
        return mRetrofit.create(service);
    }

    /**
     * MethodHandler
     */
    private List<Type> methodHandler(Type[] types) {
        List<Type> needTypes = new ArrayList<>();
        for (Type paramType : types) {
            if (paramType instanceof ParameterizedType) {
                Type[] parenTypes = ((ParameterizedType) paramType).getActualTypeArguments();
                for (Type childType : parenTypes) {
                    needTypes.add(childType);
                    if (childType instanceof ParameterizedType) {
                        Type[] childTypes = ((ParameterizedType) childType).getActualTypeArguments();
                        Collections.addAll(needTypes, childTypes);
                    }
                }
            }
        }
        return needTypes;
    }

    private <T> Type classType(PerfectionCallBack<T> callBack) {
        Type type = null;
        Type[] types = callBack.getClass().getGenericInterfaces();
        if (!methodHandler(types).isEmpty()) {
            type = methodHandler(types).get(0);
        }
        return type;
    }


    private ObservableTransformer schedulersTransformer = new ObservableTransformer() {
        @Override
        public ObservableSource apply(Observable upstream) {
            return upstream.subscribeOn(Schedulers.io())//发生
                    .unsubscribeOn(Schedulers.io())//解除
                    .observeOn(AndroidSchedulers.mainThread());//回调
        }
    };

    private static class HttpResponseFunc<T> implements Function<Throwable, Observable<T>> {

        @Override
        public Observable<T> apply(Throwable throwable) throws Exception {
            return Observable.error(PerfectionException.handleException((Exception) throwable));
        }
    }


    @SuppressWarnings("unchecked")
    private <T> ObservableTransformer<T, T> handleErrTransformer() {
        if (mExceptTransformer == null) {
            mExceptTransformer = new ObservableTransformer<T, T>() {

                @Override
                public ObservableSource<T> apply(Observable<T> upstream) {
                    return upstream.onErrorResumeNext(new HttpResponseFunc<>());
                }
            };
        }
        return mExceptTransformer;
    }


    /**
     * get提交返回自定义bean
     */
    @SuppressWarnings("unchecked")
    public <T> void requestGet(String url, Map<String, Object> maps, PerfectionCallBack<T> callBack) {
        mSubscriber = new PerfectionSubscriber(classType(callBack), callBack);
        mBaseApiService.requestGet(url, maps)
                .compose(schedulersTransformer)
                .compose(handleErrTransformer())
                .subscribe(mSubscriber);
    }


    /**
     * get
     */
    @SuppressWarnings("unchecked")
    public void get(String url, Map<String, Object> maps, BaseSubscriber<ResponseBody> subscriber) {
        mSubscriber = subscriber;
        mBaseApiService.requestGet(url, maps)
                .compose(schedulersTransformer)
                .compose(handleErrTransformer())
                .subscribe(subscriber);
    }

    /**
     * post提交返回自定义bean
     */
    @SuppressWarnings("unchecked")
    public <T> void requestPost(final String url, @FieldMap(encoded = true) Map<String, Object> parameters, PerfectionCallBack<T> callBack) {
        mSubscriber = new PerfectionSubscriber(classType(callBack), callBack);
        mBaseApiService.requestPost(url, parameters)
                .compose(schedulersTransformer)
                .compose(handleErrTransformer())
                .subscribe(mSubscriber);
    }

    /**
     * post提交返回自定义bean
     */
    @SuppressWarnings("unchecked")
    public <T> void requestPost(final String url, Object requestBean, PerfectionCallBack<T> callBack) {
        mSubscriber = new PerfectionSubscriber(classType(callBack), callBack);
        mBaseApiService.requestPost(url, requestBean)
                .compose(schedulersTransformer)
                .compose(handleErrTransformer())
                .subscribe(mSubscriber);
    }


    /**
     * Post提交
     */
    @SuppressWarnings("unchecked")
    public void post(String url, @FieldMap(encoded = true) Map<String, Object> parameters, BaseSubscriber<ResponseBody> subscriber) {
        mSubscriber = subscriber;
        mBaseApiService.requestPost(url, parameters)
                .compose(schedulersTransformer)
                .compose(handleErrTransformer())
                .subscribe(subscriber);
    }


    /**
     * 表单提交，返回自定义bean
     */
    @SuppressWarnings("unchecked")
    public <T> void requestForm(String url, @FieldMap(encoded = true) Map<String, Object> fields, PerfectionCallBack<T> callBack) {
        mSubscriber = new PerfectionSubscriber(classType(callBack), callBack);
        mBaseApiService.postForm(url, fields)
                .compose(schedulersTransformer)
                .compose(handleErrTransformer())
                .subscribe(mSubscriber);
    }


    /**
     * 表单提交
     */
    @SuppressWarnings("unchecked")
    public void form(String url, @FieldMap(encoded = true) Map<String, Object> fields, BaseSubscriber<ResponseBody> subscriber) {
        mSubscriber = subscriber;
        mBaseApiService.postForm(url, fields)
                .compose(schedulersTransformer)
                .compose(handleErrTransformer())
                .subscribe(subscriber);
    }


    /**
     * 提交单个图片
     */
    @SuppressWarnings("unchecked")
    public <T> void uploadImage(String url, File file, PerfectionCallBack<T> callBack) {
        mSubscriber = new PerfectionSubscriber(classType(callBack), callBack);
        mBaseApiService.upLoadImage(url, PerfectionUtils.createImage(file))
                .compose(schedulersTransformer)
                .compose(handleErrTransformer())
                .subscribe(mSubscriber);
    }


    /**
     * 提交单个文件
     */
    @SuppressWarnings("unchecked")
    public <T> void uploadFlie(String url, File file, PerfectionCallBack<T> callBack) {
        mSubscriber = new PerfectionSubscriber(classType(callBack), callBack);
        mBaseApiService.uploadFile(url, PerfectionUtils.createFile(file))
                .compose(schedulersTransformer)
                .compose(handleErrTransformer())
                .subscribe(mSubscriber);
    }

    /**
     * 提交文件和参数
     */
    @SuppressWarnings("unchecked")
    public <T> void uploadFilesWithParams(String url, Map<String, Object> paramMap, Map<String, File> fileMap, PerfectionCallBack<T> callBack) {
        mSubscriber = new PerfectionSubscriber(classType(callBack), callBack);
        Map<String, RequestBody> filesBody = new HashMap<>();
        if (fileMap != null && fileMap.size() > 0) {
            for (Map.Entry<String, File> entry : fileMap.entrySet()) {
                String key = entry.getKey();
                File file = entry.getValue();
                if (file != null) {
                    filesBody.put(key, PerfectionUtils.createFile(file));
                }
            }
        }
        mBaseApiService.uploadFileWithPartMap(url, paramMap, filesBody)
                .compose(schedulersTransformer)
                .compose(handleErrTransformer())
                .subscribe(mSubscriber);
    }


    /**
     * 提交文件和文件描述
     */
    @SuppressWarnings("unchecked")
    public <T> void uploadFlieWithDescription(String url, String description, File file, PerfectionCallBack<T> callBack) {
        mSubscriber = new PerfectionSubscriber(classType(callBack), callBack);
        mBaseApiService.uploadFile(url, PerfectionUtils.createPartFromString(description), PerfectionUtils.createPart(description, file))
                .compose(schedulersTransformer)
                .compose(handleErrTransformer())
                .subscribe(mSubscriber);
    }

    /**
     * 提交多个文件
     */
    @SuppressWarnings("unchecked")
    public <T> void uploadFlies(String url, Map<String, File> files, PerfectionCallBack<T> callBack) {
        mSubscriber = new PerfectionSubscriber(classType(callBack), callBack);
        Map<String, RequestBody> filesBody = new HashMap<>();
        if (files != null && files.size() > 0) {
            for (Map.Entry<String, File> entry : files.entrySet()) {
                String key = entry.getKey();
                File file = entry.getValue();
                if (file != null) {
                    filesBody.put(key, PerfectionUtils.createFile(file));
                }
            }
        }
        mBaseApiService.uploadFiles(url, filesBody)
                .compose(schedulersTransformer)
                .compose(handleErrTransformer())
                .subscribe(mSubscriber);
    }

    /**
     * 取消请求
     */
    public void cancelRequest() {
        if (mSubscriber != null) {
            mSubscriber.disposable();
        }
    }

    public static final class Builder {

        private static final int  DEFAULT_TIMEOUT              = 20;
        private static final int  DEFAULT_MAX_IDLE_CONNECTIONS = 5;
        private static final long DEFAULT_KEEP_ALIVE_DURATION  = 8;
        private static final long DEFAULT_CACHE_MAX_SIZE       = 10 * 1024 * 1024;

        private Boolean isLog   = true;
        private Boolean isCache = false;

        private Context              mContext;
        private String               mBaseUrl;
        private HostnameVerifier     mHostnameVerifier;
        private SSLSocketFactory     mSslSocketFactory;
        private ConnectionPool       mConnectionPool;
        private Converter.Factory    mConverterFactory;
        private CallAdapter.Factory  mCallAdapterFactory;
        private okhttp3.Call.Factory mCallFactory;
        private OkHttpClient.Builder mOkHttpBuilder;
        private OkHttpClient         mOkHttpClient;
        private Retrofit.Builder     mRetrofitBuilder;

        private Builder() {
        }

        public Builder(Context context) {
            mContext = context.getApplicationContext();
            mOkHttpBuilder = new OkHttpClient.Builder();
            mRetrofitBuilder = new Retrofit.Builder();
        }

        /**
         * 指定自己的OKhttpClient
         */
        public Builder client(OkHttpClient client) {
            this.mOkHttpClient = PerfectionUtils.checkNotNull(client, "client == null");
            return this;
        }

        public Builder callFactory(okhttp3.Call.Factory factory) {
            this.mCallFactory = PerfectionUtils.checkNotNull(factory, "factory == null");
            return this;
        }

        /**
         * 设置连接超时时间
         */
        public Builder connectTimeout(int timeout) {
            return connectTimeout(timeout, TimeUnit.SECONDS);
        }

        /**
         * 设置写的超时时间
         */
        public Builder writeTimeout(int timeout) {
            return writeTimeout(timeout, TimeUnit.SECONDS);
        }

        /**
         * 设置读的超时时间
         */
        public Builder readTimeout(int timeout) {
            return readTimeout(timeout, TimeUnit.SECONDS);
        }

        /**
         * 打印LOG
         */
        public Builder addLog(boolean isLog) {
            this.isLog = isLog;
            return this;
        }


        /**
         * 是否打开缓存
         */
        public Builder addCache(boolean isCache) {
            this.isCache = isCache;
            return this;
        }

        /**
         * 设置代理
         */
        public Builder proxy(Proxy proxy) {
            mOkHttpBuilder.proxy(PerfectionUtils.checkNotNull(proxy, "proxy == null"));
            return this;
        }

        private Builder writeTimeout(int timeout, TimeUnit unit) {
            if (timeout != -1) {
                mOkHttpBuilder.writeTimeout(timeout, unit);
            } else {
                mOkHttpBuilder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            }
            return this;
        }


        private Builder readTimeout(int timeout, TimeUnit unit) {
            if (timeout != -1) {
                mOkHttpBuilder.readTimeout(timeout, unit);
            } else {
                mOkHttpBuilder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            }
            return this;
        }

        private Builder connectTimeout(int timeout, TimeUnit unit) {
            if (timeout != -1) {
                mOkHttpBuilder.connectTimeout(timeout, unit);
            } else {
                mOkHttpBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            }
            return this;
        }


        /**
         * 设置用于回收HTTP和HTTPS连接的连接池。
         */
        public Builder connectionPool(ConnectionPool connectionPool) {
            this.mConnectionPool = PerfectionUtils.checkNotNull(connectionPool, "mConnectionPool == null");
            return this;
        }

        /**
         * 设置baseUrl
         */
        public Builder baseUrl(String baseUrl) {
            this.mBaseUrl = PerfectionUtils.checkNotNull(baseUrl, "mBaseUrl == null");
            return this;
        }


        /**
         * 设置数据转换器
         * 默认是fastjson
         */
        public Builder addConverterFactory(Converter.Factory factory) {
            this.mConverterFactory = factory;
            return this;
        }

        /**
         * 设置回调adapter
         * 默认是rxjava
         */
        public Builder addCallAdapterFactory(CallAdapter.Factory factory) {
            this.mCallAdapterFactory = factory;
            return this;
        }


        /**
         * 设置请求头参数
         */
        public Builder addHeader(Map<String, String> headers) {
            if (headers != null && headers.size() > 0) {
                mOkHttpBuilder.addInterceptor(new BaseInterceptor(headers));
            }
            return this;
        }


        /**
         * 设置https
         */
        private Builder addSSLSocketFactory(SSLSocketFactory sslSocketFactory) {
            this.mSslSocketFactory = sslSocketFactory;
            return this;
        }

        private Builder addHostnameVerifier(HostnameVerifier hostnameVerifier) {
            this.mHostnameVerifier = hostnameVerifier;
            return this;
        }

        /**
         * 设置支持https需要接入证书
         * int[] certificates = {R.raw.myssl1, R.raw.myssl2,......}
         * certificates是你的ssl证书文件的id，项目中请放到raw资源文件下
         * int[] hosts = {"https:// you hosturl2", "https:// you hosturl2",......}
         */
        public Builder addSSL(String[] hosts, int[] certificates) {
            if (hosts == null) throw new NullPointerException("hosts == null");
            if (certificates == null) throw new NullPointerException("ids == null");
            addSSLSocketFactory(PerfectionHttpsFactory.getSSLSocketFactory(mContext, certificates));
            addHostnameVerifier(PerfectionHttpsFactory.getHostnameVerifier(hosts));
            return this;
        }

        /**
         * setCache
         */
        private Cache cache() {
            String cacheControlValue = String.format("max-age=%d".toLowerCase(), CacheInterceptor.MAX_STALE);
            Interceptor cacheInterceptor = new CacheInterceptor(mContext, cacheControlValue);
            Interceptor cacheInterceptorOffline = new CacheInterceptorOffline(mContext, cacheControlValue);
            mOkHttpBuilder.addNetworkInterceptor(cacheInterceptor);
            mOkHttpBuilder.addNetworkInterceptor(cacheInterceptorOffline);
            mOkHttpBuilder.addInterceptor(cacheInterceptorOffline);
            File mHttpCacheDirectory = new File(mContext.getCacheDir(), "perfection_http_cache");
            return new Cache(mHttpCacheDirectory, DEFAULT_CACHE_MAX_SIZE);
        }

        public PerfectionRetrofit build() {
            if (TextUtils.isEmpty(mBaseUrl)) {
                throw new IllegalStateException("Base URL required.");
            }

            if (mOkHttpBuilder == null) {
                throw new IllegalStateException("mOkHttpBuilder required.");
            }

            if (mRetrofitBuilder == null) {
                throw new IllegalStateException("mRetrofitBuilder required.");
            }

            mRetrofitBuilder.baseUrl(mBaseUrl);

            if (mConverterFactory == null) {
//                mConverterFactory = GsonConverterFactory.create();
                mConverterFactory = FastJsonConverterFactory.create();
            }
            mRetrofitBuilder.addConverterFactory(mConverterFactory);

            if (mCallAdapterFactory == null) {
                mCallAdapterFactory = RxJava2CallAdapterFactory.create();
            }
            mRetrofitBuilder.addCallAdapterFactory(mCallAdapterFactory);

            if (isLog) {
                mOkHttpBuilder.addNetworkInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        Log.i("RETROFIT", message);
                    }
                }).setLevel(HttpLoggingInterceptor.Level.BODY));
            }

            if (mSslSocketFactory != null) {
                mOkHttpBuilder.sslSocketFactory(mSslSocketFactory);
            } else {
                //默认支持所有证书
                TrustAllManager trustAllManager = new TrustAllManager();
                mOkHttpBuilder.sslSocketFactory(PerfectionHttpsFactory.createSSLSocketFactory(trustAllManager), trustAllManager);
            }

            if (mHostnameVerifier != null) {
                mOkHttpBuilder.hostnameVerifier(mHostnameVerifier);
            } else {
                mOkHttpBuilder.hostnameVerifier(new TrustAllHostnameVerifier());
            }

            if (isCache) {
                mOkHttpBuilder.cache(cache());
            }

            if (mConnectionPool == null) {
                mConnectionPool = new ConnectionPool(DEFAULT_MAX_IDLE_CONNECTIONS, DEFAULT_KEEP_ALIVE_DURATION, TimeUnit.SECONDS);
            }
            mOkHttpBuilder.connectionPool(mConnectionPool);

            if (mCallFactory != null) {
                mRetrofitBuilder.callFactory(mCallFactory);
            }

            if (mOkHttpClient == null) {
                mOkHttpClient = mOkHttpBuilder.build();
            }
            mRetrofitBuilder.client(mOkHttpClient);

            Retrofit retrofit = mRetrofitBuilder.build();
            BaseApiService apiManager = retrofit.create(BaseApiService.class);
            return new PerfectionRetrofit(retrofit, apiManager);
        }
    }

}


