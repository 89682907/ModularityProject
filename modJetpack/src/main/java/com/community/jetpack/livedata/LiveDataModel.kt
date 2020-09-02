package com.community.jetpack.livedata

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class LiveDataModel : ViewModel() {
    val mInfoEvent = MutableLiveData<String>()

    fun senData() {
        var i = 0
        Thread {
            run {
                while (i <= 50) {
                    mInfoEvent.postValue((i++.toString() + " : " + Thread.currentThread().name))
                    Thread.sleep(1000)
                }
            }
        }.start()

//        for (index in 1..10){
////            mInfoEvent.postValue("senData: $index")
//            mInfoEvent.value = "senData: $index"
//        }
    }
}