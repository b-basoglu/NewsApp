package com.news.newsapp.network.response

import com.news.newsapp.entities.News

data class NewsResponse (
    val status: String? = null,
    val articles: ArrayList<News>? = null
)