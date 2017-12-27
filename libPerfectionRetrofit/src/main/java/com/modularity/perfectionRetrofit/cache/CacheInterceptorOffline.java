
package com.modularity.perfectionRetrofit.cache;

import android.content.Context;


import com.modularity.perfectionRetrofit.util.PerfectionNetworkUtil;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Request;
import okhttp3.Response;

public class CacheInterceptorOffline extends CacheInterceptor {
    public CacheInterceptorOffline(Context context) {
        super(context);
    }

    public CacheInterceptorOffline(Context context, String cacheControlValue) {
        super(context, cacheControlValue);
    }

    public CacheInterceptorOffline(Context context, String cacheControlValue, String cacheOnlineControlValue) {
        super(context, cacheControlValue, cacheOnlineControlValue);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!PerfectionNetworkUtil.isNetworkAvailable(context)) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .cacheControl(CacheControl.FORCE_NETWORK)
                    .build();
            Response response = chain.proceed(request);
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, only-if-cached, " + cacheControlValueOffline)
                    .build();
        }
        return chain.proceed(request);
    }
}
