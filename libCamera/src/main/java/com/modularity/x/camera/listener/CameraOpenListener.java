package com.modularity.x.camera.listener;

import com.modularity.x.camera.enums.CameraFace;

public interface CameraOpenListener {

    void onCameraOpened(@CameraFace int cameraFace);

    void onCameraOpenError(Throwable throwable);
}
