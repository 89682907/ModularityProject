package com.modularity.x.camera.listener;

import com.modularity.x.camera.annotation.KeepNotProguard;

@KeepNotProguard
public interface OnOrientationChangedListener {

    void onOrientationChanged(int degree);
}
