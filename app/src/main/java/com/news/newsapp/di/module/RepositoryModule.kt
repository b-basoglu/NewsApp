package com.news.newsapp.di.module

import com.news.newsapp.db.NewsDatabase
import com.news.newsapp.di.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(
        database: NewsDatabase
    ): MainRepository = MainRepository(database)
}