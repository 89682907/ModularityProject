package com.modularity.project.banner

import android.view.View
import android.widget.ImageView
import com.modularity.common.expand.banner.holder.BannerHolder
import com.modularity.project.R

class LocalBannerHolder(itemView: View?) : BannerHolder<Int>(itemView) {

    private var imageView: ImageView? = null

    override fun initView(itemView: View?) {
        imageView = itemView?.findViewById(R.id.iv_banner_local)
    }

    override fun updateBanner(ids: Int?) {
//        Log.i("jishen", "ids：$ids")
//        imageView?.setBackgroundResource(ids!!)
        imageView?.setBackgroundResource(R.color.kRedColor)
    }
}