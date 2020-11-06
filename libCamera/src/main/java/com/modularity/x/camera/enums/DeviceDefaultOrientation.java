package com.modularity.x.camera.enums;


import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({DeviceDefaultOrientation.ORIENTATION_PORTRAIT, DeviceDefaultOrientation.ORIENTATION_LANDSCAPE})
@Retention(RetentionPolicy.SOURCE)
public @interface DeviceDefaultOrientation {
    int ORIENTATION_PORTRAIT  = 0x01;
    int ORIENTATION_LANDSCAPE = 0x02;
}
