package com.news.newsapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.news.newsapp.dao.NewsDao
import com.news.newsapp.entities.News

@Database(entities = [News::class], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao
}