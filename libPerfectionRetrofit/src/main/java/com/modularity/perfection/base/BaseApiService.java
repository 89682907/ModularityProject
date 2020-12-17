package com.modularity.perfection.base;

import com.modularity.perfection.annotation.KeepNotProguard;

import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.QueryName;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

@KeepNotProguard
public interface BaseApiService {
    @POST
    Observable<ResponseBody> requestPost(@Url String var1, @Body Object var2);

    @GET
    Observable<ResponseBody> requestGet(@Url String var1, @QueryMap Map<String, String> var2);

    @Multipart
    @POST
    Observable<ResponseBody> upLoadImage(@Url String var1, @Part("image\"; filename=\"image.jpg") RequestBody var2);

    @Multipart
    @POST
    Observable<ResponseBody> upLoadImage(@Url String var1, @Part MultipartBody.Part file);

    @Multipart
    @POST
    Observable<ResponseBody> uploadFile(@Url String var1, @Part("description") RequestBody var2, @Part MultipartBody.Part var3);

    @Multipart
    @POST
    Observable<ResponseBody> uploadFiles(@Url String var1, @PartMap Map<String, RequestBody> var2);

    @Multipart
    @POST
    Observable<ResponseBody> uploadFile(@Url String var1, @Body RequestBody var2);

    @Multipart
    @POST
    Observable<ResponseBody> requestParamsAndFiles(@Url String var1, @PartMap Map<String, RequestBody> var2);

    @Multipart
    @POST
    Observable<ResponseBody> requestParamsAndFile(@Url String var1, @PartMap Map<String, RequestBody> var2, @Part okhttp3.MultipartBody.Part var3);

    @FormUrlEncoded
    @POST
    Observable<ResponseBody> postForm(@Url String var1, @FieldMap Map<String, String> var2);


    @Multipart
    @PUT
    Observable<ResponseBody> requestPut(@Url String var1, @PartMap Map<String, RequestBody> var2);

    @Streaming
    @GET
    Observable<ResponseBody> download(@Header("RANGE") String var1, @Url String var2);
}