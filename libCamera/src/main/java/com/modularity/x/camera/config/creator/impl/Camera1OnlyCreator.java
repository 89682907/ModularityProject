package com.modularity.x.camera.config.creator.impl;

import android.content.Context;

import com.modularity.x.camera.config.creator.CameraManagerCreator;
import com.modularity.x.camera.manager.CameraManager;
import com.modularity.x.camera.manager.impl.Camera1Manager;
import com.modularity.x.camera.preview.CameraPreview;

public class Camera1OnlyCreator implements CameraManagerCreator {

    @Override
    public CameraManager create(Context context, CameraPreview cameraPreview) {
        return new Camera1Manager(cameraPreview);
    }
}
