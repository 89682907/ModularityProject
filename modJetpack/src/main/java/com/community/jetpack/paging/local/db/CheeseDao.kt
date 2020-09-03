package com.community.jetpack.paging.local.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

/**
 * Cheese数据库的数据库访问对象。
 */
@Dao
interface CheeseDao {
    /**
     * 通过Room返回一个LivePagedListProvider，从中我们可以获得一个LiveData并通过ViewModel将其返回给UI。
     */
    @Query("SELECT * FROM Cheese ORDER BY name COLLATE NOCASE ASC")
    fun allCheesesByName(): PagingSource<Int, Cheese>

    @Insert
    fun insert(cheeses: List<Cheese>)

    @Insert
    fun insert(cheese: Cheese)

    @Delete
    fun delete(cheese: Cheese)
}