package com.modularity.x.camera.listener;

import com.modularity.x.camera.annotation.KeepNotProguard;

import java.io.File;

@KeepNotProguard
public interface CameraPhotoListener {

    void onPictureTaken(byte[] data, File picture);

    void onCaptureFailed(Throwable throwable);
}
