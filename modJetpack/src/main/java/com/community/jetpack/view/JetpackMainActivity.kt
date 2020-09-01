package com.community.jetpack.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ToastUtils
import com.community.jetpack.R
import com.community.jetpack.adapter.JetMainAdapter2
import com.community.jetpack.bean.JetMainItemBean
import com.community.jetpack.livedata.LiveDataMainActivity
import com.community.jetpack.viewModel.JetMainListModel
import com.modularity.common.base.BaseActivity
import com.modularity.common.expand.recyclerWrapper.listener.OnRecyclerViewItemClickListener

class JetpackMainActivity : BaseActivity(), OnRecyclerViewItemClickListener {

    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: JetMainAdapter2? = null
    private var mJetMainListModel: JetMainListModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.jet_main_activity)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        mRecyclerView = findViewById(R.id.jetRV)
        mRecyclerView?.layoutManager = LinearLayoutManager(this)
        mRecyclerView?.adapter = jetMainAdapter()
    }

    private fun jetMainListModel(): JetMainListModel {
        if (mJetMainListModel === null) {
            mJetMainListModel = JetMainListModel()
        }
        return mJetMainListModel as JetMainListModel
    }

    private fun jetMainAdapter(): JetMainAdapter2 {
        if (mAdapter === null) {
            mAdapter = JetMainAdapter2()
            mAdapter?.mItemClickListener = this
            mAdapter?.mItemList = jetMainListModel().srcListData()
        }
        return mAdapter as JetMainAdapter2
    }

    override fun onRecyclerViewItemClick(view: View?) {
        val tag = view?.tag as JetMainItemBean
        if (tag.type == 1) {
            startActivity(Intent(this, LiveDataMainActivity::class.java))
        } else {
            ToastUtils.showLong(tag.name)
        }
    }

}