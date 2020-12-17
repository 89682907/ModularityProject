package com.modularity.x.camera.listener;

import com.modularity.x.camera.annotation.KeepNotProguard;

/**
 * The finger move event listener on the focus maker.
 * You can add this callback to listen the event.
 */
@KeepNotProguard
public interface OnMoveListener {

    void onMove(boolean left);
}
