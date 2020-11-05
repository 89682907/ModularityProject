package com.modularity.project.banner

import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import com.modularity.common.expand.banner.holder.BannerHolder
import com.modularity.project.R
import com.modularity.project.glideExpand.GlideApp

class NetWorkBannerHolder(itemView: View?) : BannerHolder<String>(itemView) {

    private var imageView: ImageView? = null

    override fun initView(itemView: View?) {
        imageView = itemView?.findViewById(R.id.iv_banner_net)
    }

    override fun updateBanner(url: String) {
        if (imageView != null && !TextUtils.isEmpty(url)) {
            GlideApp.with(itemView).load(url).placeholder(0).centerCrop().into(imageView!!)
        }
    }

}