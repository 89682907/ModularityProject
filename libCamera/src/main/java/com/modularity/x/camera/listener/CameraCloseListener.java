package com.modularity.x.camera.listener;

import com.modularity.x.camera.enums.CameraFace;

public interface CameraCloseListener {

    void onCameraClosed(@CameraFace int cameraFace);
}
