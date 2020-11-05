package com.modularity.project.banner

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.modularity.common.base.BaseActivity
import com.modularity.common.expand.banner.BannerView
import com.modularity.common.expand.banner.holder.BannerHolder
import com.modularity.common.expand.banner.holder.IBannerHolderCreator
import com.modularity.common.utils.managers.manager.*
import com.modularity.project.R
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.EasyPermissions.PermissionCallbacks

class BannerActivity : BaseActivity(), PermissionCallbacks {

    private val mLocalUrl = mutableListOf<Int>()
    private val mNetWorkUrl = mutableListOf<String>()
    private var mBannerView: BannerView<LocalBannerHolder>? = null

    // 所需的全部权限
    private val mPermissions =
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.RECORD_AUDIO
            )
        } else {
            TODO("VERSION.SDK_INT < JELLY_BEAN")
        }
//    private val images = arrayOf(
//        "http://img2.3lian.com/2014/f2/37/d/40.jpg",
//        "http://d.3987.com/sqmy_131219/001.jpg",
//        "http://img2.3lian.com/2014/f2/37/d/39.jpg",
//        "http://f.hiphotos.baidu.com/image/h%3D200/sign=1478eb74d5a20cf45990f9df460b4b0c/d058ccbf6c81800a5422e5fdb43533fa838b4779.jpg",
//        "http://f.hiphotos.baidu.com/image/pic/item/09fa513d269759ee50f1971ab6fb43166c22dfba.jpg"
//    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.banner_activity)
        BarManager.setStatusBarVisibility(this, false)
        initViews()
//        startNetWorkBanner()
        startLocalBanner()
        requiresPermission()
    }

    private fun requiresPermission() {
        if (EasyPermissions.hasPermissions(this, *mPermissions)) {
            // 已经有权限，进行相关操作
            LogManager.i("权限都有")
        } else {
            // 没有权限，进行权限请求
            EasyPermissions.requestPermissions(
                this,
                "没有相关权限",
                0,
                *mPermissions
            )
        }
    }

    override fun initViews() {
        super.initViews()
        mLocalUrl.add(R.mipmap.banner_1)
        mLocalUrl.add(R.mipmap.banner_2)
        mBannerView?.setPointViewVisible(false)
        mBannerView?.startTurning(3000)
    }

    private fun startLocalBanner() {
        mBannerView?.setPages(object : IBannerHolderCreator {
            override fun createHolder(itemView: View?): BannerHolder<*> {
                return LocalBannerHolder(itemView)
            }

            override fun getLayoutId(): Int {
                return R.layout.banner_local
            }

        }, mLocalUrl as List<Any>?)
        mBannerView?.setOnItemClickListener {
            NetworkManager.isAvailableAsync {
                ThreadManager.runOnUiThread {
                    if (it) {
//                        startActivity(Intent(this, TestActivity::class.java))
                    } else {
                        AlertDialog.Builder(this)
                            .setCancelable(false)
                            .setTitle("提示")
                            .setMessage("设备网络连接异常")
                            .setPositiveButton("设置") { dialog, _ ->
                                dialog?.dismiss()
                                NetworkManager.openWirelessSettings()
                                finish()
                            }.setNegativeButton("取消") { dialog, _ ->
                                dialog?.dismiss()
                            }
                            .create().show()
                    }
                }
            }
        }
    }

    private fun startNetWorkBanner() {
        /*mNetWorkUrl.addAll(images)
        bannerView.setPages(object : IBannerHolderCreator {
            override fun createHolder(itemView: View?): BannerHolder<*> {
                return NetWorkBannerHolder(itemView)
            }

            override fun getLayoutId(): Int {
                return R.layout.banner_network
            }

        }, mNetWorkUrl as List<Any>?)
        bannerView.setOnItemClickListener {
            ToastManager.showLong("net:$it")
        }*/
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        LogManager.i(perms.toString())
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }
}
