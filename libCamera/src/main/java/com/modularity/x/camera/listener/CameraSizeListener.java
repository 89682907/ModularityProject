package com.modularity.x.camera.listener;

import com.modularity.x.camera.annotation.KeepNotProguard;
import com.modularity.x.camera.config.size.Size;

@KeepNotProguard
public interface CameraSizeListener {

    void onPreviewSizeUpdated(Size previewSize);

    void onVideoSizeUpdated(Size videoSize);

    void onPictureSizeUpdated(Size pictureSize);
}
