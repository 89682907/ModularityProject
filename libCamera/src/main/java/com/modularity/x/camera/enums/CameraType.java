package com.modularity.x.camera.enums;

import androidx.annotation.IntDef;

import com.modularity.x.camera.annotation.KeepNotProguard;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.modularity.x.camera.enums.CameraType.TYPE_CAMERA1;
import static com.modularity.x.camera.enums.CameraType.TYPE_CAMERA2;

@IntDef(value = {TYPE_CAMERA1, TYPE_CAMERA2})
@Retention(value = RetentionPolicy.SOURCE)
@KeepNotProguard
public @interface CameraType {

    /**
     * Camera1
     */
    int TYPE_CAMERA1 = 0x0100;

    /**
     * Camera2
     */
    int TYPE_CAMERA2 = 0x0200;
}
