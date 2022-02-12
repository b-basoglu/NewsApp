package com.news.newsapp.network.api

import com.news.newsapp.network.NetworkResponse
import com.news.newsapp.network.response.NewsResponse
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) :
    ApiHelper {
    override suspend fun getTopHeadlines(country:String?,apiKey:String?): NetworkResponse<NewsResponse> {
        val response = apiService.getTopHeadlines(country,apiKey)
        return if ( response.isSuccessful)  {
            NetworkResponse.Success(response.body())
        }else{
            NetworkResponse.Error(response.message())
        }
    }
}