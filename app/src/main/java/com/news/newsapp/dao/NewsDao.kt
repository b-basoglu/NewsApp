package com.news.newsapp.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.news.newsapp.entities.News
import com.news.newsapp.utils.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Query("SELECT * FROM "+ Constants.TABLE_NEWS +" ORDER BY id ASC")
    fun getNews() : PagingSource<Int, News>

    @Query("SELECT * FROM " + Constants.TABLE_NEWS + " WHERE id =:id")
    suspend fun getNewsWithId(id:Int) : News

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(news:News)

    @Delete
    suspend fun deleteNews(news:News)

    @Update
    suspend fun updateNews(news:News)

    @Query("SELECT COUNT(*) FROM "+ Constants.TABLE_NEWS)
    fun getRowCount(): Flow<Int>

    //just for test
    @Query("SELECT * FROM "+ Constants.TABLE_NEWS +" ORDER BY id DESC")
    fun getNewsForTest(): Flow<List<News>>
}