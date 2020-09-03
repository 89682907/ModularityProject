package com.community.jetpack.paging.local.viewModel

import android.app.Application
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.lifecycle.AndroidViewModel
import com.community.jetpack.paging.local.db.Cheese
import com.community.jetpack.paging.local.db.CheeseDb
import com.community.jetpack.paging.local.db.ioThread
import kotlinx.coroutines.flow.Flow

/**
 * ViewModel层提供[Flow]<[PagingData]>Cheese。
 */
class CheeseViewModel(app: Application) : AndroidViewModel(app) {
    private val dao = CheeseDb.get(app).cheeseDao()

    /**
     * [Pager]上可用的Kotlin [Flow]属性。Java开发人员应该使用“PagingRx”和“PagingLiveData”中可用的RxJava或LiveData扩展属性。
     */
    val allCheeses = Pager(
            PagingConfig(
                    pageSize = 20,

                    /**
                     * 如果占位符是启用的，PagedList将报告完整的大小，但有些项目可能会在onBind方法中为null (PagedListAdapter在加载数据时触发重新绑定)。
                     * 如果占位符被禁用，onBind将永远不会接收空值，但是更多的页面会接收空值加载时，滚动条会在新页面加载时抖动。你应该禁用占位符时禁用滚动条。
                     */
                    enablePlaceholders = true,

                    /**
                     * PagedList一次应该在内存中保存的最大项数。
                     */
                    maxSize = 200
            )
    ) {
        dao.allCheesesByName()
    }.flow

    fun insert(text: CharSequence) = ioThread {
        dao.insert(Cheese(id = 0, name = text.toString()))
    }

    fun remove(cheese: Cheese) = ioThread {
        dao.delete(cheese)
    }
}