
package com.modularity.perfectionRetrofit.cache;

import android.content.Context;
import android.text.TextUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class CacheInterceptor implements Interceptor {

    //set cache times is 3 days
    public static final  int MAX_STALE        = 60 * 60 * 24 * 3;
    // read from cache for 60 s
    private static final int MAX_STALE_ONLINE = 60;

    protected Context context;
    public    String  cacheControlValueOffline;
    public    String  cacheControlValueOnline;


    public CacheInterceptor(Context context) {
        this(context, String.format("max-age=%d", MAX_STALE_ONLINE));
    }

    public CacheInterceptor(Context context, String cacheControlValue) {
        this(context, cacheControlValue, String.format("max-age=%d", MAX_STALE));
    }

    public CacheInterceptor(Context context, String cacheControlValueOffline, String cacheControlValueOnline) {
        this.context = context;
        this.cacheControlValueOffline = cacheControlValueOffline;
        this.cacheControlValueOnline = cacheControlValueOnline;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        String cacheControl = originalResponse.header("Cache-Control");
        if (TextUtils.isEmpty(cacheControl) || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
                cacheControl.contains("must-revalidate") || cacheControl.contains("max-age") || cacheControl.contains("max-stale")) {
            return originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, max-age=" + MAX_STALE)
                    .build();

        } else {
            return originalResponse;
        }
    }

}
