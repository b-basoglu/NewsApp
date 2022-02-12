package com.news.newsapp.base

import androidx.lifecycle.ViewModel
import com.news.newsapp.network.NetworkResponse
import com.news.newsapp.network.Status
import kotlinx.coroutines.flow.MutableStateFlow

abstract class BaseViewModel : ViewModel() {

    val statusStateFlow = MutableStateFlow<Status>(Status.LOADING)

    fun setStatus(response: NetworkResponse<*>) {
        when(response) {
            is NetworkResponse.Success -> {
                if (statusStateFlow.value!= Status.SUCCESS){
                    statusStateFlow.value = Status.SUCCESS
                }
            }
            is NetworkResponse.Loading -> {
                if (statusStateFlow.value!= Status.LOADING){
                    statusStateFlow.value = Status.LOADING
                }
            }
            is NetworkResponse.Error -> {
                if (statusStateFlow.value!= Status.ERROR){
                    statusStateFlow.value = Status.ERROR
                }
            }
        }
    }
}