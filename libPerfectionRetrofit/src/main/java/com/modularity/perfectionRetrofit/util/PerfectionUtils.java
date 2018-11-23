package com.modularity.perfectionRetrofit.util;

import android.net.Uri;
import android.os.Looper;

import java.io.File;

import io.reactivex.annotations.NonNull;
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
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString);
    }

    public static RequestBody createText(String text) {
        checkNotNull(text, "text not null!");
        return RequestBody.create(MediaType.parse("text/plain"), text);
    }

    public static RequestBody createFile(String name) {
        checkNotNull(name, "name not null!");
        return RequestBody.create(MediaType.parse("multipart/form-data; charset=utf-8"), name);
    }


    public static RequestBody createFile(File file) {
        checkNotNull(file, "file not null!");
        return RequestBody.create(MediaType.parse("multipart/form-data"), file);
    }


    @NonNull
    public static RequestBody createImage(File file) {
        checkNotNull(file, "file not null!");
        return RequestBody.create(MediaType.parse("image/jpg; charset=utf-8"), file);
    }

    @NonNull
    public static RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                MediaType.parse(MULTIPART_FORM_DATA), descriptionString);
    }

    @NonNull
    public static MultipartBody.Part createPart(String partName, File file) {
        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), file);
        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        File file = PerfectionFileUtil.getUirFile(fileUri);
        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), file);
        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }
}
