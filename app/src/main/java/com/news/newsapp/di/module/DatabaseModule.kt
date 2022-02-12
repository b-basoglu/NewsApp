package com.news.newsapp.di.module

import android.content.Context
import androidx.room.Room
import com.news.newsapp.dao.NewsDao
import com.news.newsapp.db.NewsDatabase
import com.news.newsapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context): NewsDatabase {
        return Room.databaseBuilder(
            appContext
            , NewsDatabase::class.java
            , Constants.NEWS_DB_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun providePeopleDao(database: NewsDatabase): NewsDao {
        return database.newsDao()
    }
}
