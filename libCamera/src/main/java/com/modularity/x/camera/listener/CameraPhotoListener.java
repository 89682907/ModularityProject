package com.modularity.x.camera.listener;

import java.io.File;

public interface CameraPhotoListener {

    void onPictureTaken(byte[] data, File picture);

    void onCaptureFailed(Throwable throwable);
}
