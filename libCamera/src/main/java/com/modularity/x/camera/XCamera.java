package com.modularity.x.camera;

import android.os.Build;

import com.modularity.common.utils.managers.manager.SPManager;
import com.modularity.x.camera.annotation.KeepNotProguard;
import com.modularity.x.camera.config.ConfigurationProvider;
import com.modularity.x.camera.config.creator.impl.Camera1OnlyCreator;
import com.modularity.x.camera.config.creator.impl.Camera2OnlyCreator;
import com.modularity.x.camera.config.creator.impl.CameraManagerCreatorImpl;
import com.modularity.x.camera.config.creator.impl.CameraPreviewCreatorImpl;
import com.modularity.x.camera.config.creator.impl.SurfaceViewOnlyCreator;
import com.modularity.x.camera.config.creator.impl.TextureViewOnlyCreator;

@KeepNotProguard
public class XCamera {
    public static int CAMERA_1       = 0;
    public static int CAMERA_2       = 1;
    public static int CAMERA_DEFAULT = 2;

    public static int PREVIEW_SURFACE = 0;
    public static int PREVIEW_TEXTURE = 1;
    public static int PREVIEW_DEFAULT = 2;

    private XCamera() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void init(int cameraOption, int previewOption) {
        switchToCameraOption(cameraOption);
        switchToPreviewOption(previewOption);
    }

    public static void switchToCameraOption(int option) {

        if (option == CAMERA_1) {
            ConfigurationProvider.get().setCameraManagerCreator(new Camera1OnlyCreator());
        } else if (option == CAMERA_2) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ConfigurationProvider.get().setCameraManagerCreator(new Camera2OnlyCreator());
            } else {
                ConfigurationProvider.get().setCameraManagerCreator(new Camera1OnlyCreator());
            }
        } else {
            ConfigurationProvider.get().setCameraManagerCreator(new CameraManagerCreatorImpl());
        }
        SPManager.getInstance().put("__camera_option", option);
    }

    public static void switchToPreviewOption(int option) {
        if (option == PREVIEW_SURFACE) {
            ConfigurationProvider.get().setCameraPreviewCreator(new SurfaceViewOnlyCreator());
        } else if (option == PREVIEW_TEXTURE) {
            ConfigurationProvider.get().setCameraPreviewCreator(new TextureViewOnlyCreator());
        } else {
            ConfigurationProvider.get().setCameraPreviewCreator(new CameraPreviewCreatorImpl());
        }
        SPManager.getInstance().put("__preview_option", option);
    }

}
