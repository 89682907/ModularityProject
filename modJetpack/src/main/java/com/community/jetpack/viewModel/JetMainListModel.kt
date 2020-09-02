package com.community.jetpack.viewModel

import com.community.jetpack.bean.JetMainItemBean

class JetMainListModel {

    fun srcListData(): ArrayList<JetMainItemBean> {
        val list = arrayListOf<JetMainItemBean>()
        for (index in 1..10) {
            val bean = JetMainItemBean()
            when (index) {
                1 -> {
                    bean.name = "LiveData"
                }
                2 -> {
                    bean.name = "Paging"
                }
                else -> {
                    bean.name = "Item:$index"
                }
            }
            bean.type = index
            list.add(bean)
        }
        return list
    }
}