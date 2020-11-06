package com.modularity.x.camera.config.creator.impl;

import android.content.Context;
import android.view.ViewGroup;

import com.modularity.x.camera.config.creator.CameraPreviewCreator;
import com.modularity.x.camera.preview.CameraPreview;
import com.modularity.x.camera.preview.impl.SurfacePreview;

public class SurfaceViewOnlyCreator implements CameraPreviewCreator {

    @Override
    public CameraPreview create(Context context, ViewGroup parent) {
        return new SurfacePreview(context, parent);
    }
}
