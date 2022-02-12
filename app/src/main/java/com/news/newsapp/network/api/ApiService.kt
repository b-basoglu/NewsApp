package com.news.newsapp.network.api

import com.news.newsapp.network.response.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query(value = "country") country: String?,
        @Query(value = "apiKey") apiKey: String?,
    ): Response<NewsResponse>
}