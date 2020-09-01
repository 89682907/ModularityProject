package com.community.jetpack.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.community.jetpack.R
import com.community.jetpack.base.ViewHolder
import com.community.jetpack.bean.JetMainItemBean
import com.community.jetpack.databinding.JetMainAdapterHolderLayoutBinding
import com.modularity.common.expand.recyclerWrapper.listener.OnRecyclerViewItemClickListener


class JetMainAdapter2 : RecyclerView.Adapter<ViewHolder<JetMainAdapterHolderLayoutBinding>>() {

    var mItemList = arrayListOf<JetMainItemBean>()
    var mItemClickListener: OnRecyclerViewItemClickListener? = null

    override fun getItemCount(): Int {
        return mItemList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<JetMainAdapterHolderLayoutBinding> {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.jet_main_adapter_holder_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder<JetMainAdapterHolderLayoutBinding>, position: Int) {
        holder.binging?.setVariable(com.community.jetpack.BR.mainItem, mItemList[position])
        holder.itemView.setOnClickListener {
            it.tag = mItemList[position]
            mItemClickListener?.onRecyclerViewItemClick(it)
        }
    }
}