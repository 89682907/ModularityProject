package com.modularity.x.camera.enums;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.modularity.x.camera.enums.FlashMode.FLASH_AUTO;
import static com.modularity.x.camera.enums.FlashMode.FLASH_OFF;
import static com.modularity.x.camera.enums.FlashMode.FLASH_ON;

@IntDef({FLASH_ON, FLASH_OFF, FLASH_AUTO})
@Retention(RetentionPolicy.SOURCE)
public @interface FlashMode {

    /**
     * Flash on
     */
    int FLASH_ON = 0;

    /**
     * Flash off
     */
    int FLASH_OFF = 1;

    /**
     * Auto
     */
    int FLASH_AUTO = 2;
}
