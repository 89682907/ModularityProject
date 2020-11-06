package com.modularity.x.camera.enums;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.modularity.x.camera.enums.CameraFace.FACE_FRONT;
import static com.modularity.x.camera.enums.CameraFace.FACE_REAR;

@IntDef(value = {FACE_REAR, FACE_FRONT})
@Retention(value = RetentionPolicy.SOURCE)
public @interface CameraFace {

    /** Rear camera */
    int FACE_REAR           = 0x0000;

    /** Front camera */
    int FACE_FRONT          = 0x0001;
}
