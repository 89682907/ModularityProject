package com.modularity.x.camera.config.creator.impl;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.modularity.x.camera.annotation.KeepNotProguard;
import com.modularity.x.camera.config.creator.CameraManagerCreator;
import com.modularity.x.camera.manager.CameraManager;
import com.modularity.x.camera.manager.impl.Camera2Manager;
import com.modularity.x.camera.preview.CameraPreview;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
@KeepNotProguard
public class Camera2OnlyCreator implements CameraManagerCreator {

    @Override
    public CameraManager create(Context context, CameraPreview cameraPreview) {
        return new Camera2Manager(cameraPreview);
    }
}
