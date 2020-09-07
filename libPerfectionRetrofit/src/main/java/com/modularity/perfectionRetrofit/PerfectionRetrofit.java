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
import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.MultipartBody.Part;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import okhttp3.logging.HttpLoggingInterceptor.Logger;
import retrofit2.Retrofit;
import retrofit2.Converter.Factory;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
//import retrofit2.converter.fastjson.FastJsonConverterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.http.FieldMap;

import static com.modularity.perfectionRetrofit.IPerfectionRetrofitStatics.DEFAULT_CACHE_MAX_SIZE;
import static com.modularity.perfectionRetrofit.IPerfectionRetrofitStatics.DEFAULT_KEEP_ALIVE_DURATION;
import static com.modularity.perfectionRetrofit.IPerfectionRetrofitStatics.DEFAULT_MAX_IDLE_CONNECTIONS;
import static com.modularity.perfectionRetrofit.IPerfectionRetrofitStatics.DEFAULT_TIMEOUT;
import static com.modularity.perfectionRetrofit.cache.CacheInterceptor.MAX_STALE;

@SuppressWarnings("unchecked")
public final class PerfectionRetrofit {
    private BaseApiService        mBaseApiService;
    private Retrofit              mRetrofit;
    private ObservableTransformer mExceptTransformer;
    private BaseSubscriber        mSubscriber;
    private ObservableTransformer schedulersTransformer;

    private PerfectionRetrofit(Retrofit retrofit, BaseApiService apiManager) {
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

    private List<Type> methodHandler(Type[] types) {
        List<Type> needTypes = new ArrayList();
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
        if (!this.methodHandler(types).isEmpty()) {
            type = this.methodHandler(types).get(0);
        }
        return type;
    }

    private <T> ObservableTransformer<T, T> handleErrTransformer() {
        if (this.mExceptTransformer == null) {
            this.mExceptTransformer = new ObservableTransformer<T, T>() {
                public ObservableSource<T> apply(Observable<T> upstream) {
                    return upstream.onErrorResumeNext(new PerfectionRetrofit.HttpResponseFunc());
                }
            };
        }

        return this.mExceptTransformer;
    }

    public <T> void requestGet(String url, Map<String, String> maps, PerfectionCallBack<T> callBack) {
        this.mSubscriber = new PerfectionSubscriber(classType(callBack), callBack);
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
        this.mSubscriber = new PerfectionSubscriber(classType(callBack), callBack);
        this.mBaseApiService.requestPost(url, requestBean).compose(schedulersTransformer).compose(handleErrTransformer()).subscribe(mSubscriber);
    }

    public <T> void requestForm(String url, @FieldMap(encoded = true) Map<String, String> fields, PerfectionCallBack<T> callBack) {
        this.mSubscriber = new PerfectionSubscriber(classType(callBack), callBack);
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
        this.mSubscriber = new PerfectionSubscriber(classType(callBack), callBack);
        this.mBaseApiService.requestPut(url, fields == null ? new HashMap<>() : fields).compose(schedulersTransformer).compose(handleErrTransformer()).subscribe(mSubscriber);
    }

    public <T> void uploadImage(String url, File file, PerfectionCallBack<T> callBack) {
        this.mSubscriber = new PerfectionSubscriber(classType(callBack), callBack);
        this.mBaseApiService.upLoadImage(url, PerfectionUtils.createImage(file)).compose(schedulersTransformer).compose(handleErrTransformer()).subscribe(mSubscriber);
    }

    public <T> void uploadFile(String url, File file, PerfectionCallBack<T> callBack) {
        this.mSubscriber = new PerfectionSubscriber(classType(callBack), callBack);
        this.mBaseApiService.uploadFile(url, PerfectionUtils.createFile(file)).compose(schedulersTransformer).compose(handleErrTransformer()).subscribe(mSubscriber);
    }

    public <T> void requestParamsAndFiles(String url, Map<String, RequestBody> paramMap, PerfectionCallBack<T> callBack) {
        this.mSubscriber = new PerfectionSubscriber(classType(callBack), callBack);
        this.mBaseApiService.requestParamsAndFiles(url, paramMap == null ? new HashMap<>() : paramMap).compose(schedulersTransformer).compose(handleErrTransformer()).subscribe(mSubscriber);
    }

    public <T> void requestParamsAndFile(String url, Map<String, RequestBody> paramMap, Part file, PerfectionCallBack<T> callBack) {
        this.mSubscriber = new PerfectionSubscriber(classType(callBack), callBack);
        this.mBaseApiService.requestParamsAndFile(url, paramMap == null ? new HashMap<>() : paramMap, file).compose(schedulersTransformer).compose(handleErrTransformer()).subscribe(mSubscriber);
    }

    public <T> void uploadFileWithDescription(String url, String description, File file, PerfectionCallBack<T> callBack) {
        this.mSubscriber = new PerfectionSubscriber(classType(callBack), callBack);
        this.mBaseApiService.uploadFile(url, PerfectionUtils.createPartFromString(description), PerfectionUtils.createPart(description, file)).compose(schedulersTransformer).compose(handleErrTransformer()).subscribe(mSubscriber);
    }

    public <T> void uploadFlies(String url, Map<String, File> files, PerfectionCallBack<T> callBack) {
        this.mSubscriber = new PerfectionSubscriber(classType(callBack), callBack);
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
        public static String LOG_TAG = "PerfectionRetrofit";

        private Boolean                       isLog   = false;
        private Boolean                       isCache = false;
        private Context                       mContext;
        private String                        mBaseUrl;
        private HostnameVerifier              mHostnameVerifier;
        private SSLSocketFactory              mSslSocketFactory;
        private ConnectionPool                mConnectionPool;
        private Factory                       mConverterFactory;
        private retrofit2.CallAdapter.Factory mCallAdapterFactory;
        private okhttp3.Call.Factory          mCallFactory;
        private okhttp3.OkHttpClient.Builder  mOkHttpBuilder;
        private OkHttpClient                  mOkHttpClient;
        private retrofit2.Retrofit.Builder    mRetrofitBuilder;


        public Builder() {
            this.mOkHttpBuilder = new okhttp3.OkHttpClient.Builder();
            this.mRetrofitBuilder = new retrofit2.Retrofit.Builder();
        }

        public PerfectionRetrofit.Builder client(OkHttpClient client) {
            this.mOkHttpClient = PerfectionUtils.checkNotNull(client, "client == null");
            return this;
        }

        public PerfectionRetrofit.Builder callFactory(okhttp3.Call.Factory factory) {
            this.mCallFactory = PerfectionUtils.checkNotNull(factory, "factory == null");
            return this;
        }

        public PerfectionRetrofit.Builder connectTimeout(int timeout) {
            return this.connectTimeout(timeout, TimeUnit.SECONDS);
        }

        public PerfectionRetrofit.Builder writeTimeout(int timeout) {
            return this.writeTimeout(timeout, TimeUnit.SECONDS);
        }

        public PerfectionRetrofit.Builder readTimeout(int timeout) {
            return this.readTimeout(timeout, TimeUnit.SECONDS);
        }

        public PerfectionRetrofit.Builder addLog(boolean showLog, String tag) {
            this.isLog = showLog;
            if (!TextUtils.isEmpty(tag)) {
                LOG_TAG = tag;
            }
            return this;
        }

        public PerfectionRetrofit.Builder addCache(Context context, boolean isCache) {
            this.isCache = isCache;
            this.mContext = context;
            return this;
        }

        public PerfectionRetrofit.Builder proxy(Proxy proxy) {
            this.mOkHttpBuilder.proxy(PerfectionUtils.checkNotNull(proxy, "proxy == null"));
            return this;
        }

        private PerfectionRetrofit.Builder writeTimeout(int timeout, TimeUnit unit) {
            if (timeout != -1) {
                this.mOkHttpBuilder.writeTimeout(timeout, unit);
            } else {
                this.mOkHttpBuilder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            }

            return this;
        }

        private PerfectionRetrofit.Builder readTimeout(int timeout, TimeUnit unit) {
            if (timeout != -1) {
                this.mOkHttpBuilder.readTimeout(timeout, unit);
            } else {
                this.mOkHttpBuilder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            }

            return this;
        }

        private PerfectionRetrofit.Builder connectTimeout(int timeout, TimeUnit unit) {
            if (timeout != -1) {
                this.mOkHttpBuilder.connectTimeout(timeout, unit);
            } else {
                this.mOkHttpBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            }

            return this;
        }

        public PerfectionRetrofit.Builder connectionPool(ConnectionPool connectionPool) {
            this.mConnectionPool = PerfectionUtils.checkNotNull(connectionPool, "mConnectionPool == null");
            return this;
        }

        public PerfectionRetrofit.Builder baseUrl(String baseUrl) {
            this.mBaseUrl = PerfectionUtils.checkNotNull(baseUrl, "mBaseUrl == null");
            return this;
        }

        public PerfectionRetrofit.Builder addConverterFactory(Factory factory) {
            this.mConverterFactory = factory;
            return this;
        }

        public PerfectionRetrofit.Builder addCallAdapterFactory(retrofit2.CallAdapter.Factory factory) {
            this.mCallAdapterFactory = factory;
            return this;
        }

        public PerfectionRetrofit.Builder addHeader(Map<String, String> headers) {
            if (headers != null && headers.size() > 0) {
                this.mOkHttpBuilder.addInterceptor(new BaseInterceptor(headers));
            }

            return this;
        }

        private PerfectionRetrofit.Builder addSSLSocketFactory(SSLSocketFactory sslSocketFactory) {
            this.mSslSocketFactory = sslSocketFactory;
            return this;
        }

        private PerfectionRetrofit.Builder addHostnameVerifier(HostnameVerifier hostnameVerifier) {
            this.mHostnameVerifier = hostnameVerifier;
            return this;
        }

        public PerfectionRetrofit.Builder addSSL(Context context, String[] hosts, int[] certificates) {
            this.mContext = context;
            if (hosts == null) {
                throw new NullPointerException("hosts == null");
            } else if (certificates == null) {
                throw new NullPointerException("ids == null");
            } else {
                this.addSSLSocketFactory(PerfectionHttpsFactory.getSSLSocketFactory(context, certificates));
                this.addHostnameVerifier(PerfectionHttpsFactory.getHostnameVerifier(hosts));
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
            this.mOkHttpBuilder.addNetworkInterceptor(cacheInterceptor);
            this.mOkHttpBuilder.addNetworkInterceptor(cacheInterceptorOffline);
            this.mOkHttpBuilder.addInterceptor(cacheInterceptorOffline);
            File mHttpCacheDirectory = new File(context.getCacheDir(), "perfection_http_cache");
            return new Cache(mHttpCacheDirectory, DEFAULT_CACHE_MAX_SIZE);
        }

        public PerfectionRetrofit build() {
            if (TextUtils.isEmpty(this.mBaseUrl)) {
                throw new IllegalStateException("Base URL required.");
            } else if (this.mOkHttpBuilder == null) {
                throw new IllegalStateException("mOkHttpBuilder required.");
            } else if (this.mRetrofitBuilder == null) {
                throw new IllegalStateException("mRetrofitBuilder required.");
            } else {
                this.mRetrofitBuilder.baseUrl(this.mBaseUrl);
                if (this.mConverterFactory == null) {
//                    this.mConverterFactory = FastJsonConverterFactory.create();
                    this.mConverterFactory = MoshiConverterFactory.create();
                }
                this.mRetrofitBuilder.addConverterFactory(this.mConverterFactory);

                if (this.mCallAdapterFactory == null) {
                    this.mCallAdapterFactory = RxJava3CallAdapterFactory.create();
                }
                this.mRetrofitBuilder.addCallAdapterFactory(this.mCallAdapterFactory);

                if (this.isLog) {
                    this.mOkHttpBuilder.addNetworkInterceptor((new HttpLoggingInterceptor(new Logger() {
                        public void log(String message) {
                            Log.i(LOG_TAG, message);
                        }
                    })).setLevel(Level.BODY));
                }

                if (this.mSslSocketFactory != null) {
                    this.mOkHttpBuilder.sslSocketFactory(this.mSslSocketFactory);
                } else {
                    TrustAllManager trustAllManager = new TrustAllManager();
                    this.mOkHttpBuilder.sslSocketFactory(PerfectionHttpsFactory.createSSLSocketFactory(trustAllManager), trustAllManager);
                }

                if (this.mHostnameVerifier != null) {
                    this.mOkHttpBuilder.hostnameVerifier(this.mHostnameVerifier);
                } else {
                    this.mOkHttpBuilder.hostnameVerifier(new TrustAllHostnameVerifier());
                }

                if (this.isCache) {
                    this.mOkHttpBuilder.cache(this.cache(this.mContext));
                }

                if (this.mConnectionPool == null) {
                    this.mConnectionPool = new ConnectionPool(DEFAULT_MAX_IDLE_CONNECTIONS, DEFAULT_KEEP_ALIVE_DURATION, TimeUnit.SECONDS);
                }

                this.mOkHttpBuilder.connectionPool(this.mConnectionPool);
                if (this.mCallFactory != null) {
                    this.mRetrofitBuilder.callFactory(this.mCallFactory);
                }

                if (this.mOkHttpClient == null) {
                    this.mOkHttpClient = this.mOkHttpBuilder.build();
                }

                this.mRetrofitBuilder.client(this.mOkHttpClient);
                Retrofit retrofit = this.mRetrofitBuilder.build();
                BaseApiService apiManager = retrofit.create(BaseApiService.class);
                return new PerfectionRetrofit(retrofit, apiManager);
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