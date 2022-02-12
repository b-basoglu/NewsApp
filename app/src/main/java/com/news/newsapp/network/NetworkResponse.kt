package com.news.newsapp.network

sealed class NetworkResponse<T> {
    data class Success<T>(val data: T?) : NetworkResponse<T>()
    data class Error<T>(val message: String?) : NetworkResponse<T>()
    class Loading<T> : NetworkResponse<T>()
}