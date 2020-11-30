package com.modularity.x.camera.config.calculator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.modularity.x.camera.config.size.AspectRatio;
import com.modularity.x.camera.config.size.Size;
import com.modularity.x.camera.enums.CameraType;
import com.modularity.x.camera.enums.MediaQuality;

import java.util.List;

public interface CameraSizeCalculator {

    /**
     * Initialize values of calculator.
     *
     * @param expectAspectRatio expect aspect ratio
     * @param expectSize        expect size
     * @param mediaQuality      expect media quality
     * @param previewSizes      support preview sizes
     * @param pictureSizes      support picture sizes
     * @param videoSizes        support video sizes
     */
    void init(@NonNull AspectRatio expectAspectRatio,
              @Nullable Size expectSize,
              @MediaQuality int mediaQuality,
              @NonNull List<Size> previewSizes,
              @NonNull List<Size> pictureSizes,
              @NonNull List<Size> videoSizes);

    /**
     * Change expect aspect ratio. You can implement this method to get the new desired
     * aspect ratio and clear the calculated values cache. Anyway this is the method
     * we used to notify you the camera state changed.
     * <p>
     * See also,
     *
     * @param expectAspectRatio the new expect aspect ratio
     * @see #changeExpectSize(Size)
     * @see #changeMediaQuality(int)
     */
    void changeExpectAspectRatio(@NonNull AspectRatio expectAspectRatio);

    /**
     * Change expect size
     *
     * @param expectSize the new expect size
     */
    void changeExpectSize(@Nullable Size expectSize);

    /**
     * Change expect media quality
     *
     * @param mediaQuality the new expect media quality
     */
    void changeMediaQuality(@MediaQuality int mediaQuality);

    /**
     * Get calculated picture size
     *
     * @param cameraType camera type, aka, camera1 or camera2
     * @return the picture size
     */
    Size getPictureSize(@CameraType int cameraType);

    /**
     * Get calculated picture preview size
     *
     * @param cameraType camera type, aka, camera1 or camera2
     * @return the picture preview size
     */
    Size getPicturePreviewSize(@CameraType int cameraType);

    /**
     * Get calculated video size
     *
     * @param cameraType camera type, aka, camera1 or camera2
     * @return the video size
     */
    Size getVideoSize(@CameraType int cameraType);

    /**
     * Get calculated video preview size
     *
     * @param cameraType camera type, aka, camera1 or camera2
     * @return the video preview size
     */
    Size getVideoPreviewSize(@CameraType int cameraType);

}
