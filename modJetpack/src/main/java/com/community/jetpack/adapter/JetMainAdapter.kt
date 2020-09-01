package com.community.jetpack.adapter

import androidx.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.community.jetpack.R
import com.community.jetpack.bean.JetMainItemBean
import com.community.jetpack.databinding.JetMainAdapterHolderLayoutBinding
import com.modularity.common.expand.recyclerWrapper.listener.OnRecyclerViewItemClickListener

@Deprecated("",replaceWith = ReplaceWith("JetMainAdapter2"))
class JetMainAdapter : RecyclerView.Adapter<JetMainAdapter.JetMainAdapterHolder>() {

    var mItemList = arrayListOf<JetMainItemBean>()
    var mItemClickListener: OnRecyclerViewItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JetMainAdapterHolder {
        val binding: JetMainAdapterHolderLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.jet_main_adapter_holder_layout, parent, false)
        return JetMainAdapterHolder(binding, parent)
    }

    override fun onBindViewHolder(holder: JetMainAdapterHolder, position: Int) {
        val binding = holder.mBinding
        binding?.setVariable(com.community.jetpack.BR.mainItem, mItemList[position])
        holder.itemView.setOnClickListener {
            it.tag = mItemList[position]
            mItemClickListener?.onRecyclerViewItemClick(it)
        }
    }

    override fun getItemCount(): Int {
        return mItemList.size
    }

    class JetMainAdapterHolder(binding: JetMainAdapterHolderLayoutBinding?, parent: View) : RecyclerView.ViewHolder(parent) {
        var mBinding: JetMainAdapterHolderLayoutBinding? = null

        init {
            this.mBinding = binding
        }
    }
}