package com.news.newsapp.base

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import java.lang.ClassCastException

abstract class BaseFragment : Fragment() {

    var activityListener : BaseActivity.ActivityListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            activityListener = context as BaseActivity.ActivityListener
        }catch (e : ClassCastException){
            e.printStackTrace()
        }
    }

    override fun onResume() {
        super.onResume()
        activityListener?.let {
            it.updateToolbarTitle(getTitle())
            it.hideBackButton(hideBackButton())
        }
    }

    abstract fun getTitle(): String
    abstract fun hideBackButton(): Boolean

    override fun onDetach() {
        activityListener = null
        super.onDetach()
    }

    fun navigateTo(navDirections: NavDirections? = null){
        navDirections?.let {
            activityListener?.onNavigate(navDirections)
        }
    }
}