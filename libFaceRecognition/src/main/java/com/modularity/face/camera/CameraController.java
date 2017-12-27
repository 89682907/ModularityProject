package com.modularity.face.camera;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.hardware.Camera.Size;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;

import com.modularity.face.IFaceStatics;
import com.modularity.face.util.CamParaUtil;
import com.modularity.face.util.FaceCheckUtil;
import com.modularity.face.util.FileUtil;
import com.modularity.face.util.ImageUtil;

import java.io.IOException;
import java.util.List;


/**
 * 人脸识别相机控制类
 */
public class CameraController {
    private static final String sTAG = "CameraController";
    private static CameraController sCameraInterface;

    private boolean myPreviewing = false;
    private int     mCameraId    = -1;

    private float mRotate;
    private int   mRectWidth;
    private int   mRectHeight;

    private Camera            mCamera;
    private Camera.Parameters mParams;
    private FaceCheckListener mCheckFacePictureListener;
    private GoogleFaceDetect  mGoogleFaceDetect;
    private Handler           mHandler;

    interface CamOpenOverCallback {
        void cameraHasOpened();
    }

    private CameraController() {
    }

    public static synchronized CameraController getInstance() {
        if (sCameraInterface == null) {
            sCameraInterface = new CameraController();
        }
        return sCameraInterface;
    }

    /**
     * 打开Camera
     */
    public void doOpenCamera(CamOpenOverCallback callback, int cameraId) {
        Log.i(sTAG, "Camera open....");
        try {
            mCamera = Camera.open(cameraId);
            mCameraId = cameraId;
            if (callback != null) {
                callback.cameraHasOpened();
            }
        } catch (Exception e) {
            Log.i(sTAG, "Camera open exception:" + e.getMessage());
        }
    }

    /**
     * 开启预览
     */
    public void doStartPreview(SurfaceHolder holder, float previewRate) {
        Log.i(sTAG, "doStartPreview...");
        if (myPreviewing && mCamera != null) {
            mCamera.stopPreview();
            return;
        }
        if (mCamera != null) {
            mParams = mCamera.getParameters();
            mParams.setPictureFormat(PixelFormat.JPEG);//设置拍照后存储的图片格式
            //设置PreviewSize和PictureSize
            Size pictureSize = CamParaUtil.getInstance().getPropPictureSize(mParams.getSupportedPictureSizes(), previewRate, 800);
            mParams.setPictureSize(pictureSize.width, pictureSize.height);
            Size previewSize = CamParaUtil.getInstance().getPropPreviewSize(mParams.getSupportedPreviewSizes(), previewRate, 800);
            mParams.setPreviewSize(previewSize.width, previewSize.height);

            List<String> focusModes = mParams.getSupportedFocusModes();
            if (focusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)) {
                mParams.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
            } else if (focusModes.size() == 1 && Camera.Parameters.FOCUS_MODE_AUTO.equals(focusModes.get(0))) {
                if (mHandler != null) {
                    Message m = mHandler.obtainMessage();
                    m.what = IFaceStatics.FACE_SHOW_PHOTO_BUTTON;
                    m.sendToTarget();
                }
            }
            mCamera.setParameters(mParams);

            try {
                mCamera.setPreviewDisplay(holder);
                mCamera.startPreview();//开启预览
            } catch (IOException e) {
                e.printStackTrace();
            }

            myPreviewing = true;
//            mPreviwRate = previewRate;
            mParams = mCamera.getParameters(); //重新get一次
            Log.i(sTAG, "最终设置:PreviewSize--With = " + mParams.getPreviewSize().width + "Height = " + mParams.getPreviewSize().height);
            Log.i(sTAG, "最终设置:PictureSize--With = " + mParams.getPictureSize().width + "Height = " + mParams.getPictureSize().height);
        }
    }

    /**
     * 停止预览，释放Camera
     */
    public void doStopCamera() {
        if (null != mCamera) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            myPreviewing = false;
            mCamera.release();
            mCamera = null;
        }
    }


    public void setCameraDisplayOrientation(Activity activity, int cameraId, Camera camera) {
        try {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(cameraId, info);
            int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
            int degrees = 0;
            switch (rotation) {
                case Surface.ROTATION_0:
                    degrees = 0;
                    break;
                case Surface.ROTATION_90:
                    degrees = 90;
                    break;
                case Surface.ROTATION_180:
                    degrees = 180;
                    break;
                case Surface.ROTATION_270:
                    degrees = 270;
                    break;
            }
            int result;
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                result = (info.orientation + degrees) % 360;
                result = (360 - result) % 360;   // compensate the mirror
            } else {
                // back-facing
                result = (info.orientation - degrees + 360) % 360;
            }
            camera.setDisplayOrientation(result);
            mRotate = result;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Point doGetPictureSize() {
        Size s = mCamera.getParameters().getPictureSize();
        return new Point(s.width, s.height);
    }


    /**
     * 获取Camera.Parameters
     */
    private Camera.Parameters getCameraParams() {
        if (mCamera != null) {
            mParams = mCamera.getParameters();
        }
        return mParams;
    }

    /**
     * 获取Camera实例
     */
    public Camera getCameraDevice() {
        return mCamera;
    }


    public int getCameraId() {
        return mCameraId;
    }


    /*为了实现拍照的快门声音及拍照保存照片需要下面三个回调变量*/
    //快门按下的回调，在这里我们可以设置类似播放“咔嚓”声之类的操作。默认的就是咔嚓。
    private ShutterCallback mShutterCallback = new ShutterCallback() {
        public void onShutter() {
            Log.i(sTAG, "myShutterCallback:onShutter...");
        }
    };

    // 拍摄的未压缩原数据的回调,可以为null
    private PictureCallback mRawCallback         = new PictureCallback() {

        public void onPictureTaken(byte[] data, Camera camera) {
            Log.i(sTAG, "myRawCallback:onPictureTaken...");

        }
    };
    //对jpeg图像数据的回调,最重要的一个回调
    private PictureCallback mJpegPictureCallback = new PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {
            Log.i(sTAG, "myJpegCallback:onPictureTaken...");
            Bitmap b = null;
            if (null != data) {
                b = BitmapFactory.decodeByteArray(data, 0, data.length);//data是字节数据，将其解析成位图
                if (mCamera != null) {
                    mCamera.stopPreview();
                }
                myPreviewing = false;
            }
            //保存图片到sdcard
            if (null != b) {
                //设置FOCUS_MODE_CONTINUOUS_VIDEO)之后，myParam.set("rotation", 90)失效。
                //图片竟然不能旋转了，故这里要旋转下
//				Bitmap rotaBitmap = ImageUtil.getRotateBitmap(b, 90.0f);
//				FileUtil.saveBitmap(rotaBitmap);

                Bitmap face = FaceCheckUtil.genFaceBitmap(ImageUtil.getRotateBitmap(b, -mRotate));
                if (face != null) {
                    String fileName = FileUtil.saveBitmap(face);
                    if (mCheckFacePictureListener != null) {
                        mCheckFacePictureListener.recognition(!TextUtils.isEmpty(fileName), fileName);
                    }
                } else {
                    Log.i(sTAG, "图片没识别出人脸");
                    if (mCheckFacePictureListener != null) {
                        mCheckFacePictureListener.recognition(false, "");
                    }
                }

            }
            if (mCamera != null) {
                //再次进入预览
                mCamera.startPreview();
            }
            myPreviewing = true;
        }
    };

    /**
     * 拍摄指定区域的Rect
     */
    PictureCallback mRectJpegPictureCallback = new PictureCallback()
            //对jpeg图像数据的回调,最重要的一个回调
    {
        public void onPictureTaken(byte[] data, Camera camera) {
            Log.i(sTAG, "myJpegCallback:onPictureTaken...");
            Bitmap b = null;
            if (null != data) {
                b = BitmapFactory.decodeByteArray(data, 0, data.length);//data是字节数据，将其解析成位图
                if (mCamera != null) {
                    mCamera.stopPreview();
                }
                myPreviewing = false;
            }
            //保存图片到sdcard
            if (null != b) {
                //设置FOCUS_MODE_CONTINUOUS_VIDEO)之后，myParam.set("rotation", 90)失效。
                //图片竟然不能旋转了，故这里要旋转下
                Bitmap rotaBitmap = ImageUtil.getRotateBitmap(b, -mRotate);
                int x = rotaBitmap.getWidth() / 2 - mRectWidth / 2;
                int y = rotaBitmap.getHeight() / 2 - mRectHeight / 2;
                Log.i(sTAG, "rotaBitmap.getWidth() = " + rotaBitmap.getWidth() + " rotaBitmap.getHeight() = " + rotaBitmap.getHeight());
                Bitmap rectBitmap = Bitmap.createBitmap(rotaBitmap, x, y, mRectWidth, mRectHeight);
                Bitmap face = FaceCheckUtil.genFaceBitmap(rectBitmap);
                if (face != null) {
                    String fileName = FileUtil.saveBitmap(rotaBitmap);
                    if (mCheckFacePictureListener != null) {
                        mCheckFacePictureListener.recognition(!TextUtils.isEmpty(fileName), fileName);
                    }
                } else {
                    Log.i(sTAG, "没识别出人脸");
                    if (mCheckFacePictureListener != null) {
                        mCheckFacePictureListener.recognition(false, "");
                    }
                }

            }
            if (mCamera != null) {
                //再次进入预览
                mCamera.startPreview();
            }
            myPreviewing = true;
            if (!b.isRecycled()) {
                b.recycle();
                b = null;
            }

        }
    };


    public void stopGoogleFaceDetect() {
        try {
            Camera.Parameters params = getCameraParams();
            if (mCamera != null && params != null && params.getMaxNumDetectedFaces() > 0) {
                mCamera.setFaceDetectionListener(null);
                mCamera.stopFaceDetection();
                Log.i(sTAG, "stopGoogleFaceDetect");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startGoogleFaceDetect() {
        try {
            Camera.Parameters params = getCameraParams();
            if (mCamera != null && params != null && params.getMaxNumDetectedFaces() > 0) {
                mCamera.setFaceDetectionListener(mGoogleFaceDetect);
                mCamera.startFaceDetection();
                Log.i(sTAG, "startGoogleFaceDetect");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void takePhoto(FaceCheckListener checkFacePictureListener) {
        mCheckFacePictureListener = checkFacePictureListener;
        if (myPreviewing && (mCamera != null)) {
            try {
                mCamera.takePicture(mShutterCallback, null, mJpegPictureCallback);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void takeRectPhoto(FaceCheckListener checkFacePictureListener, int w, int h) {
        mCheckFacePictureListener = checkFacePictureListener;
        if (myPreviewing && mCamera != null) {
            try {
                mRectWidth = w;
                mRectHeight = h;
                mCamera.takePicture(null, null, mRectJpegPictureCallback);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void initGoogleFaceDetect(Context context, Handler mMainHandler) {
        this.mHandler = mMainHandler;
        mGoogleFaceDetect = new GoogleFaceDetect(context, mMainHandler);
    }

}
