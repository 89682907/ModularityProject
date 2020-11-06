package com.modularity.x.camera.enums;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.modularity.x.camera.enums.MediaType.TYPE_PICTURE;
import static com.modularity.x.camera.enums.MediaType.TYPE_VIDEO;

@IntDef(value = {TYPE_PICTURE, TYPE_VIDEO})
@Retention(value = RetentionPolicy.SOURCE)
public @interface MediaType {

    /**
     * Picture
     */
    int TYPE_PICTURE = 0;

    /**
     * Video
     */
    int TYPE_VIDEO = 1;
}
