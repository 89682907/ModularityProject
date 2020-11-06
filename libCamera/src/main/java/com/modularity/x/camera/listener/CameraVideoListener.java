package com.modularity.x.camera.listener;

import java.io.File;

public interface CameraVideoListener {

    void onVideoRecordStart();

    void onVideoRecordStop(File file);

    void onVideoRecordError(Throwable throwable);
}
