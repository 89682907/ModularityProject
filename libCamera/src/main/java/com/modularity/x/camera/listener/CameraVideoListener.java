package com.modularity.x.camera.listener;

import com.modularity.x.camera.annotation.KeepNotProguard;

import java.io.File;

@KeepNotProguard
public interface CameraVideoListener {

    void onVideoRecordStart();

    void onVideoRecordStop(File file);

    void onVideoRecordError(Throwable throwable);
}
