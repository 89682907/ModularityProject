package com.modularity.x.camera.preview;


public interface CameraPreviewCallback {

    /**
     * The method will be called when the preview is available.
     *
     * @param cameraPreview the camera preview.
     */
    void onAvailable(CameraPreview cameraPreview);
}
