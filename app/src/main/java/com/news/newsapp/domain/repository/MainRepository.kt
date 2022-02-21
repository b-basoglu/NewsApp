package com.news.newsapp.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.news.newsapp.db.NewsDatabase
import com.news.newsapp.entities.News
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainRepository @Inject constructor(private val database: NewsDatabase) {
    @ExperimentalCoroutinesApi
    fun getNews(): Flow<PagingData<News>> {
        val pagingSourceFactory = { database.newsDao().getNews() }
        return Pager(
            config = PagingConfig(NETWORK_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    suspend fun getNewsWithId(id: Int): News {
        return database.newsDao().getNewsWithId(id)
    }

    fun getRowCount() = database.newsDao().getRowCount()

    suspend fun deleteNews(news: News) {
        database.newsDao().deleteNews(news)
    }

    suspend fun insertNews(news: News) {
        database.newsDao().insertNews(news)
    }

    suspend fun updateNews(news: News) {
        database.newsDao().updateNews(news)
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 15
    }
}