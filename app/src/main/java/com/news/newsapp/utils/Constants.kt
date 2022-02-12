package com.news.newsapp.utils

object Constants {
    const val TABLE_NEWS = "News"
    const val NEWS_DB_NAME = "News.db"

    object NetworkConstants {
        //Http response time limit TimeoutSecForRequest
        const val TimeoutSecForRequest = 10L

        const val BASE_URL = "https://newsapi.org/"
        const val DEFAULT_COUNTRY_US = "us"
    }
}