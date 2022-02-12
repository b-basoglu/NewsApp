package com.news.newsapp.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.news.newsapp.R
import com.news.newsapp.base.BaseActivity
import com.news.newsapp.databinding.MainActivityBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : BaseActivity(), BaseActivity.ActivityListener {

    private lateinit var navController: NavController

    private lateinit var binding: MainActivityBinding

    private val viewModel by viewModels<MainActivityViewModel>()

    override fun getNavController(): NavController = navController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHost = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        navController = navHost.navController
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController)
        observeViewModel()
    }

    private fun observeViewModel(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isToolbarVisible.collect { value ->
                    if (value){
                        binding.toolbar.visibility = View.VISIBLE
                    }else{
                        binding.toolbar.visibility = View.GONE
                    }

                }
            }
        }
    }

    override fun onNavigate(navDirections: NavDirections) {
        navigateTo(navDirections)
    }

    override fun updateToolbarTitle(title: String) {
        binding.toolbar.title = title
    }

    override fun hideBackButton(hideBackButton: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(!hideBackButton)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}