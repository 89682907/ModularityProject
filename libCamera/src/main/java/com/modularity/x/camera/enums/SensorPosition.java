package com.modularity.x.camera.enums;

import androidx.annotation.IntDef;

import com.modularity.x.camera.annotation.KeepNotProguard;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({SensorPosition.SENSOR_POSITION_UP, SensorPosition.SENSOR_POSITION_UP_SIDE_DOWN, SensorPosition.SENSOR_POSITION_LEFT, SensorPosition.SENSOR_POSITION_RIGHT, SensorPosition.SENSOR_POSITION_UNSPECIFIED})
@Retention(RetentionPolicy.SOURCE)
@KeepNotProguard
public @interface SensorPosition {

    int SENSOR_POSITION_UP           = 90;
    int SENSOR_POSITION_UP_SIDE_DOWN = 270;
    int SENSOR_POSITION_LEFT         = 0;
    int SENSOR_POSITION_RIGHT        = 180;
    int SENSOR_POSITION_UNSPECIFIED  = -1;
}

