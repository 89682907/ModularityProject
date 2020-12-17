package com.modularity.x.camera.listener;

import com.modularity.x.camera.annotation.KeepNotProguard;
import com.modularity.x.camera.enums.CameraFace;

@KeepNotProguard
public interface CameraOpenListener {

    void onCameraOpened(@CameraFace int cameraFace);

    void onCameraOpenError(Throwable throwable);
}
