package com.news.newsapp.ui.main

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.news.newsapp.BuildConfig
import com.news.newsapp.base.BaseViewModel
import com.news.newsapp.base.baseadapter.RecyclerItem
import com.news.newsapp.di.repository.MainRepository
import com.news.newsapp.di.usecase.NewsListUseCase
import com.news.newsapp.network.NetworkResponse
import com.news.newsapp.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val newsListUseCase: NewsListUseCase
): BaseViewModel(), LifecycleObserver{

    var adapter : NewsListRecyclerviewAdapter ?= null
    private val mErrorViewVisibility: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val errorViewVisibility: StateFlow<Boolean?> = mErrorViewVisibility

    private val mRefreshing: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val refreshing: StateFlow<Boolean> = mRefreshing

    val rowCountLD = mainRepository.getRowCount().asLiveData()
    fun fetchAll() {
        viewModelScope.launch {
            mainRepository.getNews().distinctUntilChanged().collectLatest {
                adapter?.submitData(it as PagingData<RecyclerItem>)
            }
        }
    }
    fun getNewsList() {
        viewModelScope.launch {
            val newsListResult = newsListUseCase.execute(
                NewsListUseCase.Parameter(
                    Constants.NetworkConstants.DEFAULT_COUNTRY_US,
                    BuildConfig.NEWS_API_KEY)
            )
            setStatus(newsListResult)
            when (newsListResult) {
                is NetworkResponse.Success -> {
                    mErrorViewVisibility.value = false
                }
                is NetworkResponse.Error -> {
                    mErrorViewVisibility.value = true
                }
                is NetworkResponse.Loading -> {
                }
            }
        }
    }

    fun setErrorVisible(isVisible : Boolean) {
        viewModelScope.launch {
            mErrorViewVisibility.value = isVisible
        }
    }
    fun setRefreshingValue(isRefreshing : Boolean) {
        viewModelScope.launch {
            mRefreshing.value = isRefreshing
        }
    }
}