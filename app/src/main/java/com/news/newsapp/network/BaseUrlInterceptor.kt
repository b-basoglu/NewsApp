package com.news.newsapp.network

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class BaseUrlInterceptor (var urlConfigHolder: BaseUrlConfigHolder) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val builder = request.newBuilder()
        urlConfigHolder.baseUrl?.let {
            adjustBaseUrl(request.url)?.let {
                request = builder.url(it).build()
            }
        }
        return chain.proceed(request)
    }

    private fun adjustBaseUrl(url: HttpUrl): HttpUrl? {
        return urlConfigHolder.baseUrl?.let {
            url.newBuilder().host(it.toHttpUrl().host).build()
        }
    }
}