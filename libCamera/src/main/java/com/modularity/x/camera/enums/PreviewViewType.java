package com.modularity.x.camera.enums;

import androidx.annotation.IntDef;

import com.modularity.x.camera.annotation.KeepNotProguard;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.modularity.x.camera.enums.PreviewViewType.SURFACE_VIEW;
import static com.modularity.x.camera.enums.PreviewViewType.TEXTURE_VIEW;

@IntDef(value = {SURFACE_VIEW, TEXTURE_VIEW})
@Retention(value = RetentionPolicy.SOURCE)
@KeepNotProguard
public @interface PreviewViewType {

    /**
     * {@link android.view.SurfaceView} will be used
     */
    int SURFACE_VIEW = 0;

    /**
     * {@link android.view.TextureView} will be used
     */
    int TEXTURE_VIEW = 1;
}