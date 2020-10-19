package com.modularity.camera;


import android.content.Context;

import android.hardware.Camera;
import android.hardware.Camera.FaceDetectionListener;
import android.os.Handler;
import android.os.Message;

import com.modularity.ICameraStatics;


/**
 * 人脸识别信息回调
 */
public class GoogleFaceDetect implements FaceDetectionListener {
    private Context mContext;
    private Handler mHandler;
    private int     mFrequency;

    public GoogleFaceDetect(Context c, Handler handler) {
        mContext = c;
        mHandler = handler;
    }

    @Override
    public void onFaceDetection(Camera.Face[] faces, Camera camera) {
        if (faces.length == 1) {
            mFrequency++;
            if (mFrequency > 40) {
                for (Camera.Face face : faces) {
                    if (face.score >= 50) {
                        Message mt = mHandler.obtainMessage();
                        mt.what = ICameraStatics.TAKE_PHOTO;
                        mt.obj = faces;
                        mt.sendToTarget();
                        mFrequency = 0;
                    }
                }
            }
        } else if (faces.length > 1) {
            mFrequency = 0;
//            Toast.makeText(mContext,mContext.getString(R.string.face_recognition_mutil),Toast.LENGTH_SHORT).show();
        } else {
            mFrequency = 0;
//            Toast.makeText(mContext,mContext.getString(R.string.face_not_in_rect),Toast.LENGTH_SHORT).show();
        }

    }


}
