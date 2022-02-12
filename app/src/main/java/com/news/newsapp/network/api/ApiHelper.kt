package com.news.newsapp.network.api

import com.news.newsapp.network.NetworkResponse
import com.news.newsapp.network.response.NewsResponse
import retrofit2.Response

interface ApiHelper {
    suspend fun getTopHeadlines(country:String?,apiKey:String?): NetworkResponse<NewsResponse>
}