package com.news.newsapp.main

import androidx.lifecycle.viewModelScope
import com.news.newsapp.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainActivityViewModel: BaseViewModel() {
    private val mIsToolbarVisible: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isToolbarVisible: StateFlow<Boolean> = mIsToolbarVisible

    fun setToolbarVisible(isVisible : Boolean) {
        viewModelScope.launch {
            mIsToolbarVisible.value = isVisible
        }
    }

}