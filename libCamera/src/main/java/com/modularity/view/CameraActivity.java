package com.modularity.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.modularity.ICameraStatics;
import com.modularity.camera.CameraController;
import com.modularity.camera.CameraSurfaceView;
import com.modularity.camera.R;
import com.modularity.camera.TakePhotoListener;
import com.modularity.common.base.BaseActivity;
import com.modularity.common.utils.managers.manager.BarManager;
import com.modularity.util.DisplayUtil;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class CameraActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks, TakePhotoListener, View.OnClickListener {

    private CameraSurfaceView mSurfaceView        = null;
    //    private MainHandler       mMainHandler        = null;
    private MaskView          mMaskView           = null;
    private Point             mRectPictureSize    = null;
    private ImageButton       mShutterImageButton = null;

    private int mCenterRectWidth  = 280; //单位是dip
    private int mCenterRectHeight = 280;//单位是dip

    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_main_activity);
        BarManager.setNavBarVisibility(this, false);
        BarManager.setStatusBarVisibility(this, false);
        init();
        permissionsCheck(true);
    }

    public void init() {
        mShutterImageButton = findViewById(R.id.btn_shutter);
        mShutterImageButton.setOnClickListener(this);

        mSurfaceView = findViewById(R.id.camera_surfaceview);
//        mMainHandler = new MainHandler();
//        CameraController.getInstance().initGoogleFaceDetect(this, mMainHandler);
//        mMaskView = findViewById(R.id.view_mask);
//        Rect screenCenterRect = createCenterScreenRect(DisplayUtil.dip2px(this, mCenterRectWidth), DisplayUtil.dip2px(this, mCenterRectHeight));
//        mMaskView.setCenterRect(screenCenterRect);
    }

    private void initViewParams() {
        ViewGroup.LayoutParams params = mSurfaceView.getLayoutParams();
        Point p = DisplayUtil.getScreenMetrics(this);
        params.width = p.x;
        params.height = p.y;
        mSurfaceView.setLayoutParams(params);
    }

    private void permissionsCheck(boolean isCreate) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M || EasyPermissions.hasPermissions(this, PERMISSIONS)) {
            if (isCreate) {
                initViewParams();
            } else {
                startRecognition();
            }
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.camera_request_permissions), 0, PERMISSIONS);
        }
    }

    @Override
    public void onTakePhoto(String filepath) {
        Toast.makeText(this, "图片路径:" + filepath, Toast.LENGTH_LONG).show();
        finish();
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if (perms.size() == PERMISSIONS.length) {
            initViewParams();
        } else {
            finish();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        permissionsCheck(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        CameraController.getInstance().stopGoogleFaceDetect();
    }

    /**
     * 开始识别
     */
    private void startRecognition() {
//        CameraController.getInstance().stopGoogleFaceDetect();
//        mMainHandler.sendEmptyMessageDelayed(ICameraStatics.CAMERA_HAS_STARTED_PREVIEW, 1000);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        CameraController.getInstance().doStopCamera();
//        CameraController.getInstance().stopGoogleFaceDetect();
//        if (mMainHandler != null) {
//            mMainHandler.removeCallbacksAndMessages(null);
//        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_shutter) {
//            takeRectPicture();
            takePicture();
        }
    }

    private void takePicture() {
        CameraController.getInstance().takePhoto(this);
    }

    private void takeRectPicture() {
        if (mRectPictureSize == null) {
            mRectPictureSize = createCenterPictureRect(DisplayUtil.dip2px(this, mCenterRectWidth), DisplayUtil.dip2px(this, mCenterRectHeight));
        }
        CameraController.getInstance().takeRectPhoto(this, mRectPictureSize.x, mRectPictureSize.y);
    }


    /**
     * 生成拍照后图片的中间矩形的宽度和高度
     *
     * @param w 屏幕上的矩形宽度，单位px
     * @param h 屏幕上的矩形高度，单位px
     * @return
     */
    private Point createCenterPictureRect(int w, int h) {

        int wScreen = DisplayUtil.getScreenMetrics(this).x;
        int hScreen = DisplayUtil.getScreenMetrics(this).y;
        int wSavePicture = CameraController.getInstance().doGetPictureSize().y; //因为图片旋转了，所以此处宽高换位
        int hSavePicture = CameraController.getInstance().doGetPictureSize().x; //因为图片旋转了，所以此处宽高换位
        float wRate = (float) (wSavePicture) / (float) (wScreen);
        float hRate = (float) (hSavePicture) / (float) (hScreen);
        float rate = (wRate <= hRate) ? wRate : hRate;//也可以按照最小比率计算

        int wRectPicture = (int) (w * wRate);
        int hRectPicture = (int) (h * hRate);
        return new Point(wRectPicture, hRectPicture);

    }

    /**
     * 生成屏幕中间的矩形
     *
     * @param w 目标矩形的宽度,单位px
     * @param h 目标矩形的高度,单位px
     *          =
     */
    private Rect createCenterScreenRect(int w, int h) {
        int x1 = DisplayUtil.getScreenMetrics(this).x / 2 - w / 2;
        int y1 = DisplayUtil.getScreenMetrics(this).y / 2 - h / 2;
        int x2 = x1 + w;
        int y2 = y1 + h;
        return new Rect(x1, y1, x2, y2);
    }

    @SuppressLint("HandlerLeak")
    class MainHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ICameraStatics.CAMERA_HAS_STARTED_PREVIEW:
//                    CameraController.getInstance().startGoogleFaceDetect();
                    break;
                case ICameraStatics.TAKE_PHOTO:
                    takeRectPicture();
                    break;
                case ICameraStatics.SHOW_PHOTO_BUTTON:
                    mShutterImageButton.setVisibility(View.VISIBLE);
                    break;
            }
            super.handleMessage(msg);
        }

    }
}
