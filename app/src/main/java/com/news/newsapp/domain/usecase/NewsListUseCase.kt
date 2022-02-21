package com.news.newsapp.domain.usecase

import android.text.TextUtils
import com.news.newsapp.domain.repository.MainRepository
import com.news.newsapp.network.NetworkResponse
import com.news.newsapp.network.api.ApiHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsListUseCase @Inject constructor(
    private val mainRepository: MainRepository,
    private val apiHelper: ApiHelper
){
    suspend fun execute(parameter: Parameter): NetworkResponse<Unit> {
        return try {
            withContext(Dispatchers.IO) {
                if (isValidParam(parameter)){
                    when (val response = apiHelper.getTopHeadlines(parameter.country,parameter.apiKey)) {
                        is NetworkResponse.Success -> {
                            response.data?.articles?.forEach {
                                mainRepository.insertNews(it)
                            }
                            NetworkResponse.Success(Unit)
                        }
                        else -> NetworkResponse.Error("Request fail")
                    }
                } else{
                    NetworkResponse.Error("Parameter Error")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            NetworkResponse.Error("Exception caught")
        }
    }

    fun isValidParam(parameter: Parameter): Boolean {
        return !TextUtils.isEmpty(parameter.apiKey) && !TextUtils.isEmpty(parameter.country)
    }

    data class Parameter(val country: String?,val apiKey: String?)
}