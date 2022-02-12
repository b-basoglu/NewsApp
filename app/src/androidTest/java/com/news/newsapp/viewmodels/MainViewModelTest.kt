package com.news.newsapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.news.newsapp.base.baseadapter.BaseAdapterClick
import com.news.newsapp.base.baseadapter.BaseAdapterListener
import com.news.newsapp.di.repository.MainRepository
import com.news.newsapp.di.usecase.NewsListUseCase
import com.news.newsapp.ui.main.MainViewModel
import com.news.newsapp.ui.main.NewsListRecyclerviewAdapter
import com.news.newsapp.utils.MainCoroutineRule
import com.news.newsapp.utils.getValue
import com.news.newsapp.utils.runBlockingTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.*
import org.junit.rules.RuleChain
import javax.inject.Inject
import kotlin.jvm.Throws

@HiltAndroidTest
class MainViewModelTest : BaseAdapterListener {

    private lateinit var viewModel: MainViewModel
    private val hiltRule = HiltAndroidRule(this)
    private val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val coroutineRule = MainCoroutineRule()

    @get:Rule
    val rule = RuleChain
        .outerRule(hiltRule)
        .around(instantTaskExecutorRule)
        .around(coroutineRule)

    @Inject
    lateinit var newsListUseCase: NewsListUseCase

    @Inject
    lateinit var mainRepository: MainRepository

    private val listAdapter by lazy { NewsListRecyclerviewAdapter(this) }

    @Before
    fun setUp() {
        hiltRule.inject()
        viewModel = MainViewModel(mainRepository,newsListUseCase)
        viewModel.adapter = listAdapter
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    @Test
    @Throws(InterruptedException::class)
    fun testGetAdapter() = run {
        Assert.assertNotNull(viewModel.adapter)
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    @Test
    @Throws(InterruptedException::class)
    fun getNewsList() = coroutineRule.runBlockingTest {
        viewModel.getNewsList()
        Assert.assertTrue(getValue(viewModel.rowCountLD)>0)
    }

    override fun onClick(clickBase: BaseAdapterClick?) {
        TODO("Not yet implemented")
    }
}