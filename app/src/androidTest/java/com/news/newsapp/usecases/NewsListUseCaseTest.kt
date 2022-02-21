package com.news.newsapp.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import com.news.newsapp.BuildConfig
import com.news.newsapp.domain.repository.MainRepository
import com.news.newsapp.domain.usecase.NewsListUseCase
import com.news.newsapp.network.NetworkResponse
import com.news.newsapp.network.api.ApiHelper
import com.news.newsapp.utils.Constants
import com.news.newsapp.utils.MainCoroutineRule
import com.news.newsapp.utils.getValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import javax.inject.Inject
import kotlin.jvm.Throws

@HiltAndroidTest
class NewsListUseCaseTest {
    private val hiltRule = HiltAndroidRule(this)
    private val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val coroutineRule = MainCoroutineRule()
    private lateinit var useCase: NewsListUseCase

    @get:Rule
    val rule = RuleChain
        .outerRule(hiltRule)
        .around(coroutineRule)
        .around(instantTaskExecutorRule)

    @Inject
    lateinit var apiHelper: ApiHelper

    @Inject
    lateinit var mainRepository: MainRepository


    @Before
    fun setUp() {
        hiltRule.inject()
        useCase = NewsListUseCase(mainRepository,apiHelper)
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    @Test
    @Throws(InterruptedException::class)
    fun isValidParam() = run {
        val param1 = NewsListUseCase.Parameter("tr","1231dasd")
        val param2 = NewsListUseCase.Parameter(null,"1231dasd")
        val param3 = NewsListUseCase.Parameter("tr","")
        Assert.assertTrue(useCase.isValidParam(param1))
        Assert.assertFalse(useCase.isValidParam(param2))
        Assert.assertFalse(useCase.isValidParam(param3))
    }
    @Suppress("BlockingMethodInNonBlockingContext")
    @Test
    @Throws(InterruptedException::class)
    fun execute() = runBlocking {
        val defVal = getValue(mainRepository.getRowCount().asLiveData())

        val newsListResult = useCase.execute(
            NewsListUseCase.Parameter(
                Constants.NetworkConstants.DEFAULT_COUNTRY_US,
                BuildConfig.NEWS_API_KEY)
        )
        when(newsListResult) {
            is NetworkResponse.Success -> {
                Assert.assertTrue(getValue(mainRepository.getRowCount().asLiveData()) > 0)
            }
            is NetworkResponse.Loading -> {
                Assert.assertTrue(getValue(mainRepository.getRowCount().asLiveData()) == defVal)
            }
            is NetworkResponse.Error -> {
                Assert.assertTrue(getValue(mainRepository.getRowCount().asLiveData()) == defVal)
            }
        }
    }
}