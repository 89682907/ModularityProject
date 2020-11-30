package com.modularity.x.camera.config.creator.impl;

import android.content.Context;
import android.os.Build;
import android.view.ViewGroup;

import com.modularity.x.camera.config.creator.CameraPreviewCreator;
import com.modularity.x.camera.preview.CameraPreview;
import com.modularity.x.camera.preview.impl.SurfacePreview;
import com.modularity.x.camera.preview.impl.TexturePreview;


public class CameraPreviewCreatorImpl implements CameraPreviewCreator {

    /**
     * The default implementation for {@link CameraPreview}.
     * If the app version >= 14, {@link android.view.TextureView} will be used,
     * else {@link android.view.SurfaceView} will be used.
     *
     * @param context the context to create the preview view.
     * @param parent  the parent view of the preview.
     * @return CameraPreview object.
     */
    @Override
    public CameraPreview create(Context context, ViewGroup parent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            return new TexturePreview(context, parent);
        }
        return new SurfacePreview(context, parent);
    }
}
