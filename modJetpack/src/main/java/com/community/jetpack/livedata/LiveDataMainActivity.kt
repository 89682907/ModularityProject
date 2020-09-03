package com.community.jetpack.livedata

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.community.jetpack.R
import com.modularity.common.base.BaseActivity
import kotlinx.android.synthetic.main.jet_live_data_main_activity.*

class LiveDataMainActivity : BaseActivity() {
    private var mLiveDataModel: LiveDataModel? = null
    private var mObserver: Observer<String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.jet_live_data_main_activity)
        btnStart.setOnClickListener {
            start()
        }
        btnStop.setOnClickListener {
            stop()
        }
    }

    private fun start() {
        mObserver = Observer<String> {
            Log.i("jishen", "onChange:$it")
            tvInfo?.text = it.toString()
        }
//        mLiveDataModel = ViewModelProviders.of(this).get(LiveDataModel::class.java)
        mLiveDataModel = ViewModelProvider(this)[LiveDataModel::class.java]
        mLiveDataModel?.mInfoEvent?.observe(this, mObserver!!)
        mLiveDataModel?.senData()
    }

    private fun stop() {
        mLiveDataModel?.mInfoEvent?.removeObserver(mObserver!!)
    }
}