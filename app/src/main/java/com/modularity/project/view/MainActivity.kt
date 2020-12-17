package com.modularity.project.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import com.community.jetpack.view.JetpackMainActivity
import com.modularity.common.base.BaseActivity
import com.modularity.common.utils.managers.manager.LogManager
import com.modularity.common.utils.managers.manager.SDCardManager
import com.modularity.common.utils.managers.manager.SPManager
import com.modularity.mod_a.view.ModuleAActivity
import com.modularity.mod_b.ModuleBActivity
import com.modularity.mvvm.view.MVVMMainActivity
import com.modularity.project.R
import com.modularity.project.jsbridage.JsBridgeMainActivity
import com.modularity.project.tbs.TBSMainActivity
import com.modularity.x.camera.XCameraActivity
import com.modularity.x.camera.config.ConfigurationProvider
import com.modularity.x.camera.config.creator.impl.*

class MainActivity : BaseActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.btn_a).setOnClickListener(this)
        findViewById<View>(R.id.btn_b).setOnClickListener(this)
        findViewById<View>(R.id.btn_c).setOnClickListener(this)
        findViewById<View>(R.id.btn_signature).setOnClickListener(this)
        findViewById<View>(R.id.btn_jetpack).setOnClickListener(this)
        findViewById<View>(R.id.btn_jsbridge).setOnClickListener(this)
        findViewById<View>(R.id.btn_camera).setOnClickListener(this)
        findViewById<View>(R.id.btn_tts).setOnClickListener(this)
        findViewById<View>(R.id.btn_tbs).setOnClickListener(this)
        test()
    }

    private fun test() {
        LogManager.iTag(tag, SDCardManager.getSDCardExternalCacheDir(this))
        LogManager.iTag(tag, SDCardManager.getSDCardExternalFilesDir(this, ""))
        LogManager.iTag(tag, SDCardManager.getSDCardExternalFilesDir(this, "audio"))
        val loadView = findViewById<View>(R.id.load1)
        loadView.animation = AnimationUtils.loadAnimation(this, R.anim.zoom_in)
        val loadView2 = findViewById<View>(R.id.load2)
        loadView2.animation =
            AnimationUtils.loadAnimation(this, R.anim.zoom_in_2)
        val loadView3 = findViewById<View>(R.id.load3)
        loadView3.animation = AnimationUtils.loadAnimation(this, R.anim.zoom_in_3)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_a -> {
                moduleA()
            }
            R.id.btn_b -> {
                moduleB()
            }
            R.id.btn_c -> {
                moduleC()
            }
            R.id.btn_signature -> {
                libSignature()
            }
            R.id.btn_jetpack -> {
                modJetpack()
            }
            R.id.btn_tbs -> {
                tbs()
            }
            R.id.btn_jsbridge -> {
                modJsBridge()
            }
            R.id.btn_camera -> {
                libCamera()
            }
            R.id.btn_tts -> {
                val tts = TTSManager(this)
                tts.startTTS("我乃常山赵子龙")
            }
        }
    }

    private fun moduleA() {
        startActivity(Intent(this, ModuleAActivity::class.java))
    }

    private fun moduleB() {
        startActivity(Intent(this, ModuleBActivity::class.java))
    }

    private fun moduleC() {
        startActivity(Intent(this, MVVMMainActivity::class.java))
    }

    private fun libSignature() {
        startActivity(Intent(this, SignatureActivity::class.java))
    }

    private fun modJetpack() {
        startActivity(Intent(this, JetpackMainActivity::class.java))
    }


    private fun tbs() {
        startActivity(Intent(this, TBSMainActivity::class.java))
    }

    private fun modJsBridge() {
        startActivity(Intent(this, JsBridgeMainActivity::class.java))
    }

    private fun libCamera() {
        switchToCameraOption(1)
        switchToPreviewOption(2)
        startActivity(Intent(this, XCameraActivity::class.java))
    }

    private fun switchToCameraOption(option: Int) {
        ConfigurationProvider.get().cameraManagerCreator = when (option) {
            0 -> Camera1OnlyCreator()
            1 -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Camera2OnlyCreator()
            } else {
                Camera1OnlyCreator()
            }
            else -> CameraManagerCreatorImpl()
        }
        SPManager.getInstance().put("__camera_option", option)
    }

    private fun switchToPreviewOption(option: Int) {
        ConfigurationProvider.get().cameraPreviewCreator = when (option) {
            0 -> SurfaceViewOnlyCreator()
            1 -> TextureViewOnlyCreator()
            else -> CameraPreviewCreatorImpl()
        }
        SPManager.getInstance().put("__preview_option", option)
    }

    companion object {
        private const val tag = "jishen"
    }
}