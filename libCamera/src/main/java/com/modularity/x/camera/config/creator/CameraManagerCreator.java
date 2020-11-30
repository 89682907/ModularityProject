package com.modularity.x.camera.config.creator;

import android.content.Context;

import com.modularity.x.camera.manager.CameraManager;
import com.modularity.x.camera.preview.CameraPreview;

public interface CameraManagerCreator {

    /**
     * Method used to create {@link CameraManager}.
     *
     * @param context       the context
     * @param cameraPreview the {@link CameraPreview}
     * @return CameraManager object.
     */
    CameraManager create(Context context, CameraPreview cameraPreview);
}
