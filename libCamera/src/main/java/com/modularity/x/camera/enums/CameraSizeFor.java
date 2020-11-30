package com.modularity.x.camera.enums;


import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.modularity.x.camera.enums.CameraSizeFor.SIZE_FOR_PICTURE;
import static com.modularity.x.camera.enums.CameraSizeFor.SIZE_FOR_PREVIEW;
import static com.modularity.x.camera.enums.CameraSizeFor.SIZE_FOR_VIDEO;

@IntDef(value = {SIZE_FOR_PREVIEW, SIZE_FOR_PICTURE, SIZE_FOR_VIDEO})
@Retention(value = RetentionPolicy.SOURCE)
public @interface CameraSizeFor {

    /**
     * Camera size for preview
     */
    int SIZE_FOR_PREVIEW = 0x0010;

    /**
     * Camera size for picture
     */
    int SIZE_FOR_PICTURE = 0x0020;

    /**
     * Camera size for video
     */
    int SIZE_FOR_VIDEO = 0x0040;
}
