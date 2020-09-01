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
 * A simple [AndroidViewModel] that provides a [Flow]<[PagingData]> of delicious cheeses.
 */
class CheeseViewModel(app: Application) : AndroidViewModel(app) {
    private val dao = CheeseDb.get(app).cheeseDao()

    /**
     * We use the Kotlin [Flow] property available on [Pager]. Java developers should use the
     * RxJava or LiveData extension properties available in `PagingRx` and `PagingLiveData`.
     */
    val allCheeses = Pager(
            PagingConfig(
                    /**
                     * A good page size is a value that fills at least a few screens worth of content on a
                     * large device so the User is unlikely to see a null item.
                     * You can play with this constant to observe the paging behavior.
                     *
                     * It's possible to vary this with list device size, but often unnecessary, unless a
                     * user scrolling on a large device is expected to scroll through items more quickly
                     * than a small device, such as when the large device uses a grid layout of items.
                     */
                    /**
                     * A good page size is a value that fills at least a few screens worth of content on a
                     * large device so the User is unlikely to see a null item.
                     * You can play with this constant to observe the paging behavior.
                     *
                     * It's possible to vary this with list device size, but often unnecessary, unless a
                     * user scrolling on a large device is expected to scroll through items more quickly
                     * than a small device, such as when the large device uses a grid layout of items.
                     */
                    /**
                     * A good page size is a value that fills at least a few screens worth of content on a
                     * large device so the User is unlikely to see a null item.
                     * You can play with this constant to observe the paging behavior.
                     *
                     * It's possible to vary this with list device size, but often unnecessary, unless a
                     * user scrolling on a large device is expected to scroll through items more quickly
                     * than a small device, such as when the large device uses a grid layout of items.
                     */
                    /**
                     * A good page size is a value that fills at least a few screens worth of content on a
                     * large device so the User is unlikely to see a null item.
                     * You can play with this constant to observe the paging behavior.
                     *
                     * It's possible to vary this with list device size, but often unnecessary, unless a
                     * user scrolling on a large device is expected to scroll through items more quickly
                     * than a small device, such as when the large device uses a grid layout of items.
                     */
                    pageSize = 60,

                    /**
                     * If placeholders are enabled, PagedList will report the full size but some items might
                     * be null in onBind method (PagedListAdapter triggers a rebind when data is loaded).
                     *
                     * If placeholders are disabled, onBind will never receive null but as more pages are
                     * loaded, the scrollbars will jitter as new pages are loaded. You should probably
                     * disable scrollbars if you disable placeholders.
                     */

                    /**
                     * If placeholders are enabled, PagedList will report the full size but some items might
                     * be null in onBind method (PagedListAdapter triggers a rebind when data is loaded).
                     *
                     * If placeholders are disabled, onBind will never receive null but as more pages are
                     * loaded, the scrollbars will jitter as new pages are loaded. You should probably
                     * disable scrollbars if you disable placeholders.
                     */

                    /**
                     * If placeholders are enabled, PagedList will report the full size but some items might
                     * be null in onBind method (PagedListAdapter triggers a rebind when data is loaded).
                     *
                     * If placeholders are disabled, onBind will never receive null but as more pages are
                     * loaded, the scrollbars will jitter as new pages are loaded. You should probably
                     * disable scrollbars if you disable placeholders.
                     */

                    /**
                     * If placeholders are enabled, PagedList will report the full size but some items might
                     * be null in onBind method (PagedListAdapter triggers a rebind when data is loaded).
                     *
                     * If placeholders are disabled, onBind will never receive null but as more pages are
                     * loaded, the scrollbars will jitter as new pages are loaded. You should probably
                     * disable scrollbars if you disable placeholders.
                     */
                    enablePlaceholders = true,

                    /**
                     * Maximum number of items a PagedList should hold in memory at once.
                     *
                     * This number triggers the PagedList to start dropping distant pages as more are loaded.
                     */

                    /**
                     * Maximum number of items a PagedList should hold in memory at once.
                     *
                     * This number triggers the PagedList to start dropping distant pages as more are loaded.
                     */

                    /**
                     * Maximum number of items a PagedList should hold in memory at once.
                     *
                     * This number triggers the PagedList to start dropping distant pages as more are loaded.
                     */

                    /**
                     * Maximum number of items a PagedList should hold in memory at once.
                     *
                     * This number triggers the PagedList to start dropping distant pages as more are loaded.
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