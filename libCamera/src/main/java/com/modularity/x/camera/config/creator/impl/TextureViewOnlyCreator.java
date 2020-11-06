package com.modularity.x.camera.config.creator.impl;

import android.content.Context;
import android.view.ViewGroup;

import com.modularity.x.camera.config.creator.CameraPreviewCreator;
import com.modularity.x.camera.preview.CameraPreview;
import com.modularity.x.camera.preview.impl.TexturePreview;

public class TextureViewOnlyCreator implements CameraPreviewCreator {

    @Override
    public CameraPreview create(Context context, ViewGroup parent) {
        return new TexturePreview(context, parent);
    }
}
