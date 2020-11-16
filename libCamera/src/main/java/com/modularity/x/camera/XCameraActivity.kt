package com.modularity.x.camera

import android.animation.Animator
import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.widget.*
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.modularity.R
import com.modularity.common.base.BaseActivity
import com.modularity.common.statics.IStatics
import com.modularity.common.utils.managers.manager.*
import com.modularity.x.camera.config.ConfigurationProvider
import com.modularity.x.camera.config.size.Size
import com.modularity.x.camera.config.size.SizeMap
import com.modularity.x.camera.enums.*
import com.modularity.x.camera.listener.*
import com.modularity.x.camera.util.ImageHelper
import java.io.File

class XCameraActivity : BaseActivity() {
    private var cameraView: CameraView? = null
    private var ivSwitch: AppCompatImageView? = null
    private var ivShot: AppCompatImageView? = null
    private var ivTypeSwitch: AppCompatImageView? = null
    private var tvInfo: AppCompatTextView? = null
    private var ivPreview: AppCompatImageView? = null
    private var seekBar: AppCompatSeekBar? = null
    private var ivFlash: AppCompatImageView? = null
    private var ivSetting: AppCompatImageView? = null
    private var scFocus: SwitchCompat? = null
    private var scVoice: SwitchCompat? = null
    private var scFlash: SwitchCompat? = null
    private var scTouchZoom: SwitchCompat? = null
    private var scTouchFocus: SwitchCompat? = null
    private var tvPreviewSizes: AppCompatTextView? = null
    private var tvPictureSizes: AppCompatTextView? = null
    private var tvVideoSizes: AppCompatTextView? = null
    private var tvSwitchCamera: AppCompatTextView? = null
    private var etVideoDuration: AppCompatEditText? = null
    private var drawer: DrawerLayout? = null
    private var isCameraRecording = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.camera_main_activity)
        BarManager.setStatusBarVisibility(this, false)
        initViews()
        configDrawer()
        configMain()
    }

    override fun initViews() {
        cameraView = findViewById(R.id.cameraView)
        ivSwitch = findViewById(R.id.iv_switch)
        ivShot = findViewById(R.id.iv_shot)
        ivTypeSwitch = findViewById(R.id.iv_type_switch)
        tvInfo = findViewById(R.id.tv_info)
        ivPreview = findViewById(R.id.iv_preview)
        seekBar = findViewById(R.id.seekBar)
        ivFlash = findViewById(R.id.iv_flash)
        ivSetting = findViewById(R.id.iv_setting)
        scVoice = findViewById(R.id.sc_voice)
        scFocus = findViewById(R.id.sc_focus)
        scFlash = findViewById(R.id.sc_flash)
        scTouchZoom = findViewById(R.id.sc_touch_zoom)
        scTouchFocus = findViewById(R.id.sc_touch_focus)
        tvPreviewSizes = findViewById(R.id.tv_preview_sizes)
        tvPictureSizes = findViewById(R.id.tv_picture_sizes)
        tvVideoSizes = findViewById(R.id.tv_video_sizes)
        tvSwitchCamera = findViewById(R.id.tv_switch_camera)
        etVideoDuration = findViewById(R.id.et_video_duration)
        drawer = findViewById(R.id.drawer)
    }

    private fun configDrawer() {
        scVoice?.isChecked = ConfigurationProvider.get().isVoiceEnable
        scVoice?.setOnCheckedChangeListener { _, isChecked ->
            cameraView?.isVoiceEnable = isChecked
        }
        scFocus?.isChecked = ConfigurationProvider.get().isAutoFocus
        scFocus?.setOnCheckedChangeListener { _, isChecked -> cameraView?.isAutoFocus = isChecked }
        scTouchFocus?.isChecked = true
        scTouchFocus?.setOnCheckedChangeListener { _, isChecked ->
            cameraView?.setUseTouchFocus(
                isChecked
            )
        }
        scTouchZoom?.isChecked = true
        scTouchZoom?.setOnCheckedChangeListener { _, isChecked ->
            cameraView?.setTouchZoomEnable(
                isChecked
            )
        }

        tvPreviewSizes?.setOnClickListener {
            showPopDialog(it, cameraView?.getSizes(CameraSizeFor.SIZE_FOR_PREVIEW)!!)
        }
        tvPictureSizes?.setOnClickListener {
            showPopDialog(it, cameraView?.getSizes(CameraSizeFor.SIZE_FOR_PICTURE)!!)
        }
        tvVideoSizes?.setOnClickListener {
            showPopDialog(it, cameraView?.getSizes(CameraSizeFor.SIZE_FOR_VIDEO)!!)
        }
    }

    private fun configMain() {
        ivSetting?.setOnClickListener {
            drawer?.openDrawer(GravityCompat.END)
        }
        ivFlash?.setImageResource(
            when (ConfigurationProvider.get().defaultFlashMode) {
                FlashMode.FLASH_AUTO -> R.drawable.camera_ic_flash_auto_white_24dp
                FlashMode.FLASH_OFF -> R.drawable.camera_ic_flash_off_white_24dp
                FlashMode.FLASH_ON -> R.drawable.camera_ic_flash_on_white_24dp
                else -> R.drawable.camera_ic_flash_auto_white_24dp
            }
        )
        ivFlash?.setOnClickListener {
            val mode = when (cameraView?.flashMode) {
                FlashMode.FLASH_AUTO -> FlashMode.FLASH_ON
                FlashMode.FLASH_OFF -> FlashMode.FLASH_AUTO
                FlashMode.FLASH_ON -> FlashMode.FLASH_OFF
                else -> FlashMode.FLASH_AUTO
            }
            cameraView?.flashMode = mode
            ivFlash?.setImageResource(
                when (mode) {
                    FlashMode.FLASH_AUTO -> R.drawable.camera_ic_flash_auto_white_24dp
                    FlashMode.FLASH_OFF -> R.drawable.camera_ic_flash_off_white_24dp
                    FlashMode.FLASH_ON -> R.drawable.camera_ic_flash_on_white_24dp
                    else -> R.drawable.camera_ic_flash_auto_white_24dp
                }
            )
        }

        seekBar?.animate()?.translationX(SizeManager.dp2px(320f).toFloat())
            ?.setListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    seekBar?.visibility = View.VISIBLE
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationStart(animation: Animator?) {
                    seekBar?.visibility = View.GONE
                }
            })
        seekBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val room = 1 + (cameraView?.maxZoom!! - 1) * (1.0f * progress / seekBar!!.max)
                cameraView?.zoom = room
                displayCameraInfo()
            }
        })

        ivSwitch?.setOnClickListener {
            cameraView?.switchCamera(
                if (cameraView?.cameraFace == CameraFace.FACE_FRONT)
                    CameraFace.FACE_REAR else CameraFace.FACE_FRONT
            )
        }
        ivTypeSwitch?.setOnClickListener {
            if (cameraView?.mediaType == MediaType.TYPE_PICTURE) {
                ivTypeSwitch?.setImageResource(R.drawable.camera_ic_videocam_white_24dp)
                cameraView?.mediaType = MediaType.TYPE_VIDEO
            } else {
                ivTypeSwitch?.setImageResource(R.drawable.camera_ic_photo_camera_white_24dp)
                cameraView?.mediaType = MediaType.TYPE_PICTURE
            }
            displayCameraInfo()
        }

        cameraView?.setMediaQuality(MediaQuality.QUALITY_HIGHEST)
        cameraView?.setOnMoveListener {
            ToastManager.showLong(if (it) "LEFT" else "RIGHT")
        }
        cameraView?.addCameraSizeListener(object : CameraSizeListener {
            override fun onPreviewSizeUpdated(previewSize: Size?) {
                LogManager.iTag("jishen", "onPreviewSizeUpdated : $previewSize")
                displayCameraInfo()
            }

            override fun onVideoSizeUpdated(videoSize: Size?) {
                LogManager.iTag("jishen", "onVideoSizeUpdated : $videoSize")
                displayCameraInfo()
            }

            override fun onPictureSizeUpdated(pictureSize: Size?) {
                LogManager.iTag("jishen", "onPictureSizeUpdated : $pictureSize")
                displayCameraInfo()
            }
        })

        ivShot?.setOnClickListener {
            if (cameraView?.mediaType == MediaType.TYPE_PICTURE) {
                takePicture()
            } else {
                takeVideo()
            }
        }

        cameraView?.addOrientationChangedListener { degree ->
            ivFlash?.rotation = degree.toFloat()
            ivSwitch?.rotation = degree.toFloat()
            ivTypeSwitch?.rotation = degree.toFloat()
            ivSetting?.rotation = degree.toFloat()
        }

        cameraView?.setCameraPreviewListener(object : CameraPreviewListener {
            private var frame: Int = 0
            override fun onPreviewFrame(data: ByteArray?, size: Size, format: Int) {
//                LogManager.iTag("jishen", "onPreviewFrame")
                if (frame % 50 == 0) {
                    frame = 1
                    try {
                        val light =
                            ImageHelper.convertYUV420_NV21toARGB8888(data, size.width, size.height)
                        if (light <= 30) {
                            LogManager.iTag("jishen", "太暗")
                        }
                        ivPreview?.setImageBitmap(
                            ImageHelper.convertNV21ToBitmap(
                                data,
                                size.width,
                                size.height
                            )
                        )
                        ivPreview?.rotation = 90f
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                frame++
            }
        })

        displayCameraInfo()
    }

    private fun showPopDialog(view: View, sizes: SizeMap): PopupMenu {
        val pop = PopupMenu(this, view)
        val list = mutableListOf<Size>()
        sizes.values.forEach { list.addAll(it) }
        list.forEach { pop.menu.add(it.toString()) }
        pop.gravity = Gravity.END
        pop.setOnMenuItemClickListener {
            val txt = it.title.substring(1, it.title.length - 1)
            val arr = txt.split(",")
            cameraView?.setExpectSize(Size.of(arr[0].trim().toInt(), arr[1].trim().toInt()))
            return@setOnMenuItemClickListener true
        }
        pop.show()
        return pop
    }

    override fun onResume() {
        super.onResume()
        openCamera()
    }

    private fun openCamera() {
        if (!cameraView?.isCameraOpened!!) {
            cameraView?.openCamera(object : CameraOpenListener {
                override fun onCameraOpened(cameraFace: Int) {
                    LogManager.iTag("jishen", "openCamera:$cameraFace")
                }

                override fun onCameraOpenError(throwable: Throwable?) {
                    LogManager.iTag("jishen", "onCameraOpenError:${throwable?.message}")
                    ToastManager.showLong("camera open error : $throwable")
                }
            })
        }
    }

    override fun onPause() {
        super.onPause()
        cameraView?.closeCamera {
            LogManager.iTag("jishen", "closeCamera : $it")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraView?.releaseCamera()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        LogManager.iTag("jishen", "onConfigurationChanged")
    }

    private fun takePicture() {
        val fileToSave = getSavedFile("jpg")
        cameraView?.takePicture(fileToSave, object : CameraPhotoListener {
            override fun onCaptureFailed(throwable: Throwable?) {
                ToastManager.showLong(throwable?.message)
            }

            override fun onPictureTaken(data: ByteArray?, picture: File) {
                ToastManager.showLong("saved to $fileToSave")
                cameraView?.resumePreview()
            }
        })
    }

    private fun takeVideo() {
        if (!isCameraRecording) {
            val seconds: Int = try {
                Integer.parseInt(etVideoDuration?.text.toString())
            } catch (ex: Exception) {
                0
            }
            cameraView?.setVideoDuration(seconds * 1000)
            val fileToSave = getSavedFile("mp4")
            cameraView?.startVideoRecord(fileToSave, object : CameraVideoListener {
                override fun onVideoRecordStart() {
                    ToastManager.showLong("video record start")
                    isCameraRecording = true
                }

                override fun onVideoRecordStop(file: File?) {
                    isCameraRecording = false
//          saveVideoToGallery(context, fileToSave, fileToSave.name)
                    ToastManager.showLong("saved to: $file")
                }

                override fun onVideoRecordError(throwable: Throwable?) {
                    isCameraRecording = false
                    ToastManager.showLong(throwable?.message)
                }
            })
        } else {
            cameraView?.stopVideoRecord()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun displayCameraInfo() {
        tvInfo?.text = "Camera Info\n" +
                "1.Preview Size: ${
                    cameraView?.getSize(CameraSizeFor.SIZE_FOR_PREVIEW)?.toString()
                }\n" +
                "2.Picture Size: ${
                    cameraView?.getSize(CameraSizeFor.SIZE_FOR_PICTURE)?.toString()
                }\n" +
                "3.Video Size: ${cameraView?.getSize(CameraSizeFor.SIZE_FOR_VIDEO)?.toString()}\n" +
                "4.Media Type (0:Picture, 1:Video): ${cameraView?.mediaType}\n" +
                "5.Zoom: ${cameraView?.zoom}\n" +
                "6.MaxZoom: ${cameraView?.maxZoom}\n" +
                "7.CameraFace: ${cameraView?.cameraFace}"
    }

    private fun getSavedFile(appendix: String): File {
        val appDir = File(IStatics.IPathStatics.CACHE_DIR, "XCamera")
        FileManager.createOrExistsDir(appDir.path)
        val fileName = "${System.currentTimeMillis()}.${appendix}"
        return File(appDir, fileName)
    }
}