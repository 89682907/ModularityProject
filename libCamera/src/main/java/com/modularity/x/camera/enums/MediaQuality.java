package com.modularity.x.camera.enums;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.modularity.x.camera.enums.MediaQuality.QUALITY_AUTO;
import static com.modularity.x.camera.enums.MediaQuality.QUALITY_HIGH;
import static com.modularity.x.camera.enums.MediaQuality.QUALITY_HIGHEST;
import static com.modularity.x.camera.enums.MediaQuality.QUALITY_LOW;
import static com.modularity.x.camera.enums.MediaQuality.QUALITY_LOWEST;
import static com.modularity.x.camera.enums.MediaQuality.QUALITY_MEDIUM;


@IntDef(value = {QUALITY_AUTO, QUALITY_LOWEST, QUALITY_LOW, QUALITY_MEDIUM, QUALITY_HIGH, QUALITY_HIGHEST})
@Retention(value = RetentionPolicy.SOURCE)
public @interface MediaQuality {

    /**
     * Auto
     */
    int QUALITY_AUTO = 0;

    /**
     * Lowest quality
     */
    int QUALITY_LOWEST = 1;

    /**
     * Low quality
     */
    int QUALITY_LOW = 2;

    /**
     * Medium quality
     */
    int QUALITY_MEDIUM = 3;

    /**
     * High quality
     */
    int QUALITY_HIGH = 4;

    /**
     * Highest quality
     */
    int QUALITY_HIGHEST = 5;
}
