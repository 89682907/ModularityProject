package com.community.jetpack.base

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * 使用DataBing RecycleView AdapterHolder
 */
open class ViewHolder<T : ViewDataBinding>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val binging = DataBindingUtil.bind<T>(itemView)
}