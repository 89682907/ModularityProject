
package com.modularity.perfectionRetrofit.base;


import java.util.Map;

import io.reactivex.Observable;
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
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;


public interface BaseApiService {
    @POST()
    @FormUrlEncoded
    Observable<ResponseBody> requestPost(
            @Url() String url,
            @FieldMap Map<String, Object> maps);

    @POST()
    Observable<ResponseBody> requestPost(
            @Url() String url,
            @Body Object data);

    @GET()
    Observable<ResponseBody> requestGet(
            @Url String url,
            @QueryMap Map<String, Object> maps);


    @Multipart
    @POST()
    Observable<ResponseBody> upLoadImage(
            @Url() String url,
            @Part("image\"; filename=\"image.jpg") RequestBody requestBody);

    @Multipart
    @POST()
    Observable<ResponseBody> uploadFile(
            @Url String fileUrl,
            @Part("description") RequestBody description,
            @Part("files") MultipartBody.Part file);


    @POST()
    Observable<ResponseBody> uploadFiles(
            @Url() String url,
            @Body Map<String, RequestBody> maps);

    @POST()
    Observable<ResponseBody> uploadFile(
            @Url() String url,
            @Body RequestBody file);

    @Multipart
    @POST
    Observable<ResponseBody> requestParamsAndFiles(
            @Url() String url,
            @PartMap() Map<String, RequestBody> partMap);

    @Multipart
    @POST
    Observable<ResponseBody> requestParamsAndFile(
            @Url() String url,
            @PartMap() Map<String, RequestBody> partMap,
            @Part() MultipartBody.Part file);

    @FormUrlEncoded
    @POST()
    Observable<ResponseBody> postForm(
            @Url() String url,
            @FieldMap Map<String, Object> maps);

    /*断点续传下载接口*/
    @Streaming/*大文件需要加入这个判断，防止下载过程中写入到内存中*/
    @GET
    Observable<ResponseBody> download(@Header("RANGE") String start, @Url String url);

}


