package com.modularity.perfectionRetrofit.util;

import android.net.Uri;
import android.os.Looper;

import java.io.File;

import io.reactivex.rxjava3.annotations.NonNull;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PerfectionUtils {

    private static final String MULTIPART_FORM_DATA = "multipart/form-data";

    public static <T> T checkNotNull(T object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
        return object;
    }

    public static boolean checkMain() {
        return Thread.currentThread() == Looper.getMainLooper().getThread();
    }

    public static RequestBody createJson(String jsonString) {
        checkNotNull(jsonString, "json not null!");
        return RequestBody.create(jsonString, MediaType.parse("application/json; charset=utf-8"));
    }

    public static RequestBody createText(String text) {
        checkNotNull(text, "text not null!");
        return RequestBody.create(text, MediaType.parse("text/plain"));
    }

    public static RequestBody createFile(String name) {
        checkNotNull(name, "name not null!");
        return RequestBody.create(name, MediaType.parse("multipart/form-data; charset=utf-8"));
    }


    public static RequestBody createFile(File file) {
        checkNotNull(file, "file not null!");
        return RequestBody.create(file, MediaType.parse("multipart/form-data; charset=utf-8"));
    }


    @NonNull
    public static RequestBody createImage(File file) {
        checkNotNull(file, "file not null!");
        return RequestBody.create(file, MediaType.parse("image/jpg; charset=utf-8"));
    }

    @NonNull
    public static RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(descriptionString, MediaType.parse(MULTIPART_FORM_DATA));
    }

    @NonNull
    public static MultipartBody.Part createPart(String partName, File file) {
        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(file, MediaType.parse(MULTIPART_FORM_DATA));
        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        File file = PerfectionFileUtil.getUirFile(fileUri);
        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(file, MediaType.parse(MULTIPART_FORM_DATA));
        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }
}
