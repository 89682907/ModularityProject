package com.modularity.x.camera.preview;

import android.graphics.SurfaceTexture;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.View;

import androidx.annotation.Nullable;

import com.modularity.x.camera.config.size.Size;
import com.modularity.x.camera.enums.PreviewViewType;


public interface CameraPreview {

    /**
     * Add callback to the camera preview.
     *
     * @param cameraPreviewCallback the callback interface.
     */
    void setCameraPreviewCallback(CameraPreviewCallback cameraPreviewCallback);

    /**
     * Get the surface from this preview that will be used for
     * {@link android.hardware.camera2.CameraDevice}
     *
     * @return surface
     */
    Surface getSurface();

    /**
     * Get the preview type the camera preview is based on.
     * This will be used to decide which method of {@link #getSurfaceHolder()}
     * and {@link #getSurfaceTexture()} will be called.
     *
     * @return the preview type
     */
    @PreviewViewType
    int getPreviewType();

    /**
     * Get {@link SurfaceHolder} from {@link android.view.SurfaceView}.
     *
     * @return SurfaceHolder object, Might be null if the preview view is
     * {@link android.view.TextureView}.
     */
    @Nullable
    SurfaceHolder getSurfaceHolder();

    /**
     * Get {@link SurfaceTexture} from {@link android.view.TextureView}.
     *
     * @return SurfaceTexture object. Might be null if the preview view is
     * {@link android.view.SurfaceView}.
     */
    @Nullable
    SurfaceTexture getSurfaceTexture();

    /**
     * Is the camera preview available now.
     *
     * @return true if available
     */
    boolean isAvailable();

    /**
     * Get the size of this camera preview.
     *
     * @return the size
     */
    Size getSize();

    /**
     * Get the real view that is displaying the camera data.
     *
     * @return the view
     */
    View getView();
}
