package com.news.newsapp.di.module

import android.app.Application
import android.content.Context
import com.news.newsapp.BuildConfig
import com.news.newsapp.network.BaseUrlConfigHolder
import com.news.newsapp.network.BaseUrlInterceptor
import com.news.newsapp.network.api.ApiHelper
import com.news.newsapp.network.api.ApiHelperImpl
import com.news.newsapp.network.api.ApiService
import com.news.newsapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule{

    @Provides
    @Singleton
    fun provideContext(app : Application) : Context {
        return app.applicationContext
    }

    @Provides
    @Singleton
    fun provideBaseUrlConfigHolder() : BaseUrlConfigHolder = BaseUrlConfigHolder()

    @Provides
    @Singleton
    fun provideOkHttpClient(baseUrlConfigHolder: BaseUrlConfigHolder) : OkHttpClient {
        val baseUrlInterceptor = BaseUrlInterceptor(baseUrlConfigHolder)
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            return OkHttpClient.Builder()
                .callTimeout(Constants.NetworkConstants.TimeoutSecForRequest, TimeUnit.SECONDS)
                .connectTimeout(Constants.NetworkConstants.TimeoutSecForRequest, TimeUnit.SECONDS)
                .readTimeout(Constants.NetworkConstants.TimeoutSecForRequest, TimeUnit.SECONDS)
                .writeTimeout(Constants.NetworkConstants.TimeoutSecForRequest, TimeUnit.SECONDS)
                .addInterceptor(baseUrlInterceptor)
                .addInterceptor(loggingInterceptor)
                .build()
        } else {
            return OkHttpClient
                .Builder()
                .callTimeout(Constants.NetworkConstants.TimeoutSecForRequest, TimeUnit.SECONDS)
                .connectTimeout(Constants.NetworkConstants.TimeoutSecForRequest, TimeUnit.SECONDS)
                .readTimeout(Constants.NetworkConstants.TimeoutSecForRequest, TimeUnit.SECONDS)
                .writeTimeout(Constants.NetworkConstants.TimeoutSecForRequest, TimeUnit.SECONDS)
                .addInterceptor(baseUrlInterceptor)
                .build()
        }
    }
    // Singleton Retrofit
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.NetworkConstants.BASE_URL)
            .client(okHttpClient)
            .build()

    //Provide ApiService for ApiHelperImpl
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    //Provide ApiHelper for Repository
    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper
}