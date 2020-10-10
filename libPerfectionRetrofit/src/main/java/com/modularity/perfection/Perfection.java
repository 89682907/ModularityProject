package com.modularity.perfection;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.modularity.perfection.exception.PerfectionException;
import com.modularity.perfection.base.BaseApiService;
import com.modularity.perfection.base.BaseInterceptor;
import com.modularity.perfection.base.BaseSubscriber;
import com.modularity.perfection.cache.CacheInterceptor;
import com.modularity.perfection.cache.CacheInterceptorOffline;
import com.modularity.perfection.https.PerfectionHttpsFactory;
import com.modularity.perfection.util.PerfectionUtils;


import java.io.File;
import java.net.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.MultipartBody.Part;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.Converter.Factory;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.http.FieldMap;

import static com.modularity.perfection.statics.IPerfectionRetrofitStatics.DEFAULT_CACHE_MAX_SIZE;
import static com.modularity.perfection.statics.IPerfectionRetrofitStatics.DEFAULT_KEEP_ALIVE_DURATION;
import static com.modularity.perfection.statics.IPerfectionRetrofitStatics.DEFAULT_MAX_IDLE_CONNECTIONS;
import static com.modularity.perfection.statics.IPerfectionRetrofitStatics.DEFAULT_TIMEOUT;
import static com.modularity.perfection.cache.CacheInterceptor.MAX_STALE;

public final class Perfection {
    private BaseApiService        mBaseApiService;
    private Retrofit              mRetrofit;
    private ObservableTransformer mExceptTransformer;
    private BaseSubscriber        mSubscriber;
    private ObservableTransformer schedulersTransformer;

    Perfection() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private Perfection(Retrofit retrofit, BaseApiService apiManager) {
        this.schedulersTransformer = new ObservableTransformer() {
            public ObservableSource apply(Observable upstream) {
                return upstream.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
        this.mBaseApiService = apiManager;
        this.mRetrofit = retrofit;
    }

    public <T> T create(Class<T> service) {
        return this.mRetrofit.create(service);
    }

    private <T> ObservableTransformer<T, T> handleErrTransformer() {
        if (this.mExceptTransformer == null) {
            this.mExceptTransformer = new ObservableTransformer<T, T>() {
                public ObservableSource<T> apply(Observable<T> upstream) {
                    return upstream.onErrorResumeNext(new Perfection.HttpResponseFunc());
                }
            };
        }

        return this.mExceptTransformer;
    }

    public <T> void requestGet(String url, Map<String, String> maps, PerfectionCallBack<T> callBack) {
        this.mSubscriber = new PerfectionSubscriber(callBack);
        this.mBaseApiService.requestGet(url, maps == null ? new HashMap<>() : maps).compose(schedulersTransformer).compose(handleErrTransformer()).subscribe(mSubscriber);
    }

    /**
     * @deprecated
     */
    @Deprecated
    public void get(String url, Map<String, String> maps, BaseSubscriber<ResponseBody> subscriber) {
        this.mSubscriber = subscriber;
        this.mBaseApiService.requestGet(url, maps == null ? new HashMap<>() : maps).compose(schedulersTransformer).compose(handleErrTransformer()).subscribe(subscriber);
    }

    public <T> void requestPost(String url, Object requestBean, PerfectionCallBack<T> callBack) {
        this.mSubscriber = new PerfectionSubscriber(callBack);
        this.mBaseApiService.requestPost(url, requestBean).compose(schedulersTransformer).compose(handleErrTransformer()).subscribe(mSubscriber);
    }

    public <T> void requestForm(String url, @FieldMap(encoded = true) Map<String, String> fields, PerfectionCallBack<T> callBack) {
        this.mSubscriber = new PerfectionSubscriber(callBack);
        this.mBaseApiService.postForm(url, fields == null ? new HashMap<>() : fields).compose(schedulersTransformer).compose(handleErrTransformer()).subscribe(mSubscriber);
    }

    /**
     * @deprecated
     */
    @Deprecated
    public void post(String url, Object requestBean, BaseSubscriber<ResponseBody> subscriber) {
        this.mSubscriber = subscriber;
        this.mBaseApiService.requestPost(url, requestBean).compose(schedulersTransformer).compose(handleErrTransformer()).subscribe(subscriber);
    }

    /**
     * @deprecated
     */
    @Deprecated
    public void post(String url, @FieldMap(encoded = true) Map<String, String> parameters, BaseSubscriber<ResponseBody> subscriber) {
        this.mSubscriber = subscriber;
        this.mBaseApiService.requestPost(url, parameters == null ? new HashMap<>() : parameters).compose(schedulersTransformer).compose(handleErrTransformer()).subscribe(subscriber);
    }

    /**
     * @deprecated
     */
    @Deprecated
    public void form(String url, @FieldMap(encoded = true) Map<String, String> fields, BaseSubscriber<ResponseBody> subscriber) {
        this.mSubscriber = subscriber;
        this.mBaseApiService.postForm(url, fields == null ? new HashMap<>() : fields).compose(schedulersTransformer).compose(handleErrTransformer()).subscribe(subscriber);
    }

    public <T> void requestPut(String url, @FieldMap(encoded = true) Map<String, RequestBody> fields, PerfectionCallBack<T> callBack) {
        this.mSubscriber = new PerfectionSubscriber(callBack);
        this.mBaseApiService.requestPut(url, fields == null ? new HashMap<>() : fields).compose(schedulersTransformer).compose(handleErrTransformer()).subscribe(mSubscriber);
    }

    @Deprecated
    public <T> void uploadImage(String url, File file, PerfectionCallBack<T> callBack) {
        this.mSubscriber = new PerfectionSubscriber(callBack);
        this.mBaseApiService.upLoadImage(url, PerfectionUtils.createImage(file)).compose(schedulersTransformer).compose(handleErrTransformer()).subscribe(mSubscriber);
    }

    public <T> void uploadImage(String url, String key, File file, PerfectionCallBack<T> callBack) {
        this.mSubscriber = new PerfectionSubscriber(callBack);
        RequestBody requestFile = PerfectionUtils.createImage(file);
        MultipartBody.Part body = MultipartBody.Part.createFormData(key, file.getName(), requestFile);
        this.mBaseApiService.upLoadImage(url, body).compose(schedulersTransformer).compose(handleErrTransformer()).subscribe(mSubscriber);
    }

    public <T> void uploadFile(String url, File file, PerfectionCallBack<T> callBack) {
        this.mSubscriber = new PerfectionSubscriber(callBack);
        this.mBaseApiService.uploadFile(url, PerfectionUtils.createFile(file)).compose(schedulersTransformer).compose(handleErrTransformer()).subscribe(mSubscriber);
    }

    public <T> void requestParamsAndFiles(String url, Map<String, RequestBody> paramMap, PerfectionCallBack<T> callBack) {
        this.mSubscriber = new PerfectionSubscriber(callBack);
        this.mBaseApiService.requestParamsAndFiles(url, paramMap == null ? new HashMap<>() : paramMap).compose(schedulersTransformer).compose(handleErrTransformer()).subscribe(mSubscriber);
    }

    public <T> void requestParamsAndFile(String url, Map<String, RequestBody> paramMap, Part file, PerfectionCallBack<T> callBack) {
        this.mSubscriber = new PerfectionSubscriber(callBack);
        this.mBaseApiService.requestParamsAndFile(url, paramMap == null ? new HashMap<>() : paramMap, file).compose(schedulersTransformer).compose(handleErrTransformer()).subscribe(mSubscriber);
    }

    public <T> void uploadFileWithDescription(String url, String description, String fileKey, File file, PerfectionCallBack<T> callBack) {
        this.mSubscriber = new PerfectionSubscriber(callBack);
        this.mBaseApiService.uploadFile(url, PerfectionUtils.createPartFromString(description), PerfectionUtils.createPart(fileKey, file)).compose(schedulersTransformer).compose(handleErrTransformer()).subscribe(mSubscriber);
    }

    public <T> void uploadFlies(String url, Map<String, File> files, PerfectionCallBack<T> callBack) {
        this.mSubscriber = new PerfectionSubscriber(callBack);
        Map<String, RequestBody> filesBody = new HashMap();
        if (files != null && files.size() > 0) {
            for (Entry<String, File> stringFileEntry : files.entrySet()) {
                String key = stringFileEntry.getKey();
                File file = stringFileEntry.getValue();
                if (file != null) {
                    filesBody.put(key, PerfectionUtils.createFile(file));
                }
            }
        }
        this.mBaseApiService.uploadFiles(url, filesBody).compose(schedulersTransformer).compose(handleErrTransformer()).subscribe(mSubscriber);
    }

    public void cancelRequest() {
        if (this.mSubscriber != null) {
            this.mSubscriber.disposable();
        }

    }

    public static final class Builder {
        public static String               LOG_TAG = "Perfection";
        private       Boolean              isLog   = false;
        private       Boolean              isCache = false;
        private       Context              mContext;
        private       String               mBaseUrl;
        private       HostnameVerifier     mHostnameVerifier;
        private       SSLSocketFactory     mSslSocketFactory;
        private       ConnectionPool       mConnectionPool;
        private       Converter.Factory    mConverterFactory;
        private       CallAdapter.Factory  mCallAdapterFactory;
        private       Call.Factory         mCallFactory;
        private       OkHttpClient.Builder mOkHttpBuilder;
        private       OkHttpClient         mOkHttpClient;
        private       Retrofit.Builder     mRetrofitBuilder;


        public Builder() {
            mOkHttpBuilder = new OkHttpClient.Builder();
            mRetrofitBuilder = new Retrofit.Builder();
        }

        public Perfection.Builder client(OkHttpClient client) {
            mOkHttpClient = PerfectionUtils.checkNotNull(client, "client == null");
            return this;
        }

        public Perfection.Builder callFactory(okhttp3.Call.Factory factory) {
            mCallFactory = PerfectionUtils.checkNotNull(factory, "factory == null");
            return this;
        }

        public Perfection.Builder connectTimeout(int timeout) {
            if (timeout > 0) {
                mOkHttpBuilder.connectTimeout(timeout, TimeUnit.SECONDS);
            } else {
                mOkHttpBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            }
            return this;
        }

        public Perfection.Builder writeTimeout(int timeout) {
            if (timeout > 0) {
                mOkHttpBuilder.writeTimeout(timeout, TimeUnit.SECONDS);
            } else {
                mOkHttpBuilder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            }
            return this;
        }

        public Perfection.Builder readTimeout(int timeout) {
            if (timeout > 0) {
                mOkHttpBuilder.readTimeout(timeout, TimeUnit.SECONDS);
            } else {
                mOkHttpBuilder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            }
            return this;
        }

        public Perfection.Builder addLog(boolean showLog, String tag) {
            isLog = showLog;
            if (!TextUtils.isEmpty(tag)) {
                LOG_TAG = tag;
            }
            return this;
        }

        public Perfection.Builder addCache(Context context, boolean cache) {
            isCache = cache;
            mContext = context;
            return this;
        }

        public Perfection.Builder proxy(Proxy proxy) {
            mOkHttpBuilder.proxy(PerfectionUtils.checkNotNull(proxy, "proxy == null"));
            return this;
        }

        public Perfection.Builder connectionPool(ConnectionPool connectionPool) {
            mConnectionPool = PerfectionUtils.checkNotNull(connectionPool, "mConnectionPool == null");
            return this;
        }

        public Perfection.Builder baseUrl(String baseUrl) {
            mBaseUrl = PerfectionUtils.checkNotNull(baseUrl, "mBaseUrl == null");
            return this;
        }

        public Perfection.Builder addConverterFactory(Factory factory) {
            mConverterFactory = factory;
            return this;
        }

        public Perfection.Builder addCallAdapterFactory(CallAdapter.Factory factory) {
            mCallAdapterFactory = factory;
            return this;
        }

        public Perfection.Builder addHeader(Map<String, String> headers) {
            if (headers != null && headers.size() > 0) {
                mOkHttpBuilder.addInterceptor(new BaseInterceptor(headers));
            }

            return this;
        }

        /**
         * int[] certificates = {R.raw.myssl1, R.raw.myssl2,......}
         * int[] hosts = {"https:// you hosturl1", "https:// you hosturl2",......}
         *
         * @param certificates 是你的ssl证书文件在raw文件下的的id数组，项目中请放到raw资源文件下
         */
        public Perfection.Builder addSSL(Context context, String[] hosts, int[] certificates) {
            mContext = context;
            if (hosts == null) {
                throw new NullPointerException("hosts == null");
            } else if (certificates == null) {
                throw new NullPointerException("ids == null");
            } else {
                mSslSocketFactory = PerfectionHttpsFactory.getSSLSocketFactory(context, certificates);
                mHostnameVerifier = PerfectionHttpsFactory.getHostnameVerifier(hosts);
                return this;
            }
        }

        private Cache cache(Context context) {
            if (context == null) {
                throw new NullPointerException("context == null");
            }
            String cacheControlValue = String.format("max-age=%d".toLowerCase(), MAX_STALE);
            Interceptor cacheInterceptor = new CacheInterceptor(context, cacheControlValue);
            Interceptor cacheInterceptorOffline = new CacheInterceptorOffline(context, cacheControlValue);
            mOkHttpBuilder.addNetworkInterceptor(cacheInterceptor);
            mOkHttpBuilder.addNetworkInterceptor(cacheInterceptorOffline);
            mOkHttpBuilder.addInterceptor(cacheInterceptorOffline);
            return new Cache(new File(context.getCacheDir(), "perfection_http_cache"), DEFAULT_CACHE_MAX_SIZE);
        }

        public Perfection build() {
            if (TextUtils.isEmpty(mBaseUrl)) {
                throw new IllegalStateException("Base URL required.");
            } else if (mOkHttpBuilder == null) {
                throw new IllegalStateException("mOkHttpBuilder required.");
            } else if (mRetrofitBuilder == null) {
                throw new IllegalStateException("mRetrofitBuilder required.");
            } else {
                mRetrofitBuilder.baseUrl(mBaseUrl);
                if (mConverterFactory == null) {
                    mConverterFactory = MoshiConverterFactory.create();
                }
                mRetrofitBuilder.addConverterFactory(mConverterFactory);

                if (mCallAdapterFactory == null) {
                    mCallAdapterFactory = RxJava3CallAdapterFactory.create();
                }
                mRetrofitBuilder.addCallAdapterFactory(mCallAdapterFactory);

                if (isLog) {
                    mOkHttpBuilder.addNetworkInterceptor(new HttpLoggingInterceptor(message -> Log.i(LOG_TAG, message)).setLevel(Level.BODY));
                }

//                if (mSslSocketFactory != null) {
//                    mOkHttpBuilder.sslSocketFactory(mSslSocketFactory);
//                } else {
//                    mOkHttpBuilder.sslSocketFactory(PerfectionHttpsFactory.getDefaultSSLSocketFactory());
//                }

//                if (mHostnameVerifier != null) {
//                    mOkHttpBuilder.hostnameVerifier(mHostnameVerifier);
//                } else {
//                    mOkHttpBuilder.hostnameVerifier(PerfectionHttpsFactory.getDefaultHostnameVerifier());
//                }

                if (mSslSocketFactory != null) {
                    mOkHttpBuilder.sslSocketFactory(mSslSocketFactory);
                }

                if (mHostnameVerifier != null) {
                    mOkHttpBuilder.hostnameVerifier(mHostnameVerifier);
                }

                if (isCache) {
                    mOkHttpBuilder.cache(cache(mContext));
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

                this.mRetrofitBuilder.client(mOkHttpClient);
                Retrofit retrofit = mRetrofitBuilder.build();
                BaseApiService apiManager = retrofit.create(BaseApiService.class);
                return new Perfection(retrofit, apiManager);
            }
        }
    }

    private static class HttpResponseFunc<T> implements Function<Throwable, Observable<T>> {
        private HttpResponseFunc() {
        }

        public Observable<T> apply(Throwable throwable) throws Exception {
            return Observable.error(PerfectionException.handleException((Exception) throwable));
        }
    }
}