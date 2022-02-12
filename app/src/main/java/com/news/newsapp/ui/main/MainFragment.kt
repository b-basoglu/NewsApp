package com.news.newsapp.ui.main

import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.news.newsapp.R
import com.news.newsapp.base.BaseFragment
import com.news.newsapp.base.baseadapter.BaseAdapterClick
import com.news.newsapp.base.baseadapter.BaseAdapterListener
import com.news.newsapp.customui.ErrorRefreshView
import com.news.newsapp.databinding.MainFragmentBinding
import com.news.newsapp.entities.News
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import androidx.recyclerview.widget.GridLayoutManager




@AndroidEntryPoint
class MainFragment : BaseFragment(), BaseAdapterListener {

    companion object {
        private const val REFRESH_TIME = 500L
    }

    private lateinit var binding : MainFragmentBinding

    private val viewModel by viewModels<MainViewModel>()

    private val listAdapter by lazy { NewsListRecyclerviewAdapter(this) }

    private val handler: Handler = Handler()

    private var gridLayoutManager: GridLayoutManager ?=null

    private val progressFinishListener = object :
        ErrorRefreshView.ProgressListener{
        override fun progressFinished() {
            viewModel.getNewsList()
            binding.errorView.cancelCountDownTimer()
            binding.errorView.visibility = View.INVISIBLE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = MainFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        gridLayoutManager?.let {
            it.spanCount = resources.getInteger(R.integer.grid_column_count)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gridLayoutManager = GridLayoutManager(requireContext(), resources.getInteger(R.integer.grid_column_count))
        this.binding.rvNews.run {
            adapter = listAdapter
            layoutManager = gridLayoutManager
        }
        viewModel.run {
            adapter = listAdapter
            getNewsList()
            fetchAll()
        }
        viewModel.rowCountLD.observe(viewLifecycleOwner,{
            Log.d("adsa",it.toString())
            binding.tvNoData.visibility = if (it<=0){
                errorViewProcess(true)
                View.VISIBLE
            }else {
                errorViewProcess(false)
                View.INVISIBLE
            }
        })
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.errorViewVisibility.collect { value ->
                    value?.let {
                        errorViewProcess(it)
                    }
                }
            }
        }
        binding.srlHolder.setOnRefreshListener {
            refreshLayout()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.refreshing.collect { value ->
                    if (value){
                        errorViewProcess(false)
                    }
                }
            }
        }
    }
    //Refresh recyclerview. viewModel.refreshing keep state of refreshing to block user from retrying aggressively
    private fun refreshLayout(){
        if (!viewModel.refreshing.value){
            viewModel.setRefreshingValue(true)
            postDelayedRefresh()
        }
    }
    //Handler used for post delay to block user from retrying aggressively
    private fun postDelayedRefresh(){
        handler.postDelayed({
            viewModel.getNewsList()
            viewModel.setRefreshingValue(false)
            if (binding.srlHolder.isRefreshing){
                binding.srlHolder.isRefreshing = false
            }
        }, REFRESH_TIME )
    }

    private fun errorViewProcess(isVisible: Boolean){
        binding.run {
            if (isVisible){
                errorView.startCountDownTimer(progressFinishListener)
                errorView.visibility = View.VISIBLE
            }else if (!isVisible){
                errorView.cancelCountDownTimer()
                errorView.visibility = View.INVISIBLE
            }
        }
    }

    override fun getTitle(): String {
        return getString(R.string.news_list)
    }

    override fun hideBackButton(): Boolean {
        return true
    }

    override fun onClick(clickBase: BaseAdapterClick?) {
        when (clickBase) {
            is News -> {
                clickBase.let{  news ->
                    val source : String = (news.source?.name)?:""
                    news.url?.let {
                        navigateTo(MainFragmentDirections.actionMainFragmentToWebViewFragment(
                            it,source
                        ))
                    }
                }
            }
        }
    }
}