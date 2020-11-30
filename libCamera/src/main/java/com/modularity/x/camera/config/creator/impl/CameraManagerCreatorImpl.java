package com.modularity.x.camera.config.creator.impl;

import android.content.Context;
import android.os.Build;

import com.modularity.x.camera.config.creator.CameraManagerCreator;
import com.modularity.x.camera.manager.CameraManager;
import com.modularity.x.camera.manager.impl.Camera1Manager;
import com.modularity.x.camera.manager.impl.Camera2Manager;
import com.modularity.x.camera.preview.CameraPreview;
import com.modularity.x.camera.util.CameraHelper;

public class CameraManagerCreatorImpl implements CameraManagerCreator {

    /**
     * The default implementation for {@link CameraManager}.
     * If the app version >= 21, the {@link android.hardware.camera2.CameraDevice} will be used,
     * else the {@link android.hardware.Camera} will be used.
     *
     * @param context       context
     * @param cameraPreview the {@link CameraPreview}
     * @return CameraManager object.
     */
    @Override
    public CameraManager create(Context context, CameraPreview cameraPreview) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && CameraHelper.hasCamera2(context)) {
            return new Camera2Manager(cameraPreview);
        }
        return new Camera1Manager(cameraPreview);
    }
}
