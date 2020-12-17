package com.modularity.x.camera.config.creator;

import android.content.Context;
import android.view.ViewGroup;

import com.modularity.x.camera.annotation.KeepNotProguard;
import com.modularity.x.camera.preview.CameraPreview;

@KeepNotProguard
public interface CameraPreviewCreator {

    /**
     * Method used to create {@link CameraPreview}.
     *
     * @param context the context to create the preview view.
     * @param parent  the parent view of the preview.
     * @return CameraPreview object.
     */
    CameraPreview create(Context context, ViewGroup parent);
}
