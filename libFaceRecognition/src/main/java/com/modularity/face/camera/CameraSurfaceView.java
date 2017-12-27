package com.modularity.face.camera;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.Camera.CameraInfo;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;



/**
 * 人脸识别SurfaceView
 */
public class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private static final String sTAG = "CameraSurfaceView";

    private Context       mContext;
    private SurfaceHolder mSurfaceHolder;

    public CameraSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mSurfaceHolder = getHolder();
        mSurfaceHolder.setFormat(PixelFormat.TRANSPARENT);//translucent半透明 transparent透明
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mSurfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.i(sTAG, "surfaceCreated...");
        CameraController.getInstance().doOpenCamera(null, CameraInfo.CAMERA_FACING_FRONT);
        CameraController.getInstance().setCameraDisplayOrientation((Activity) mContext, CameraController.getInstance().getCameraId(), CameraController.getInstance().getCameraDevice());
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.i(sTAG, "surfaceChanged...");
        CameraController.getInstance().doStartPreview(mSurfaceHolder, 1.333f);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i(sTAG, "surfaceDestroyed...");
        CameraController.getInstance().doStopCamera();
    }

}
