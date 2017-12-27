package com.modularity.face.camera;


import android.content.Context;

import android.hardware.Camera;
import android.hardware.Camera.FaceDetectionListener;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.modularity.face.IFaceStatics;
import com.modularity.face.R;


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
                        mt.what = IFaceStatics.FACE_TAKE_PHOTO;
                        mt.obj = faces;
                        mt.sendToTarget();
                        mFrequency = 0;
                    }
                }
            }
        } else if (faces.length > 1) {
            mFrequency = 0;
            Toast.makeText(mContext,mContext.getString(R.string.face_recognition_mutil),Toast.LENGTH_SHORT).show();
        } else {
            mFrequency = 0;
            Toast.makeText(mContext,mContext.getString(R.string.face_not_in_rect),Toast.LENGTH_SHORT).show();
        }

    }


}
