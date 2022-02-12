package com.news.newsapp.base

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDirections

abstract class BaseActivity : AppCompatActivity() {

    abstract fun getNavController() : NavController

    interface ActivityListener{
        fun onNavigate(navDirections: NavDirections)
        fun updateToolbarTitle(title: String)
        fun hideBackButton(hideBackButton: Boolean)
    }

    fun navigateTo(navDirections: NavDirections){
        navDirections.let {
            getNavController().navigate(it)
        }
    }
}