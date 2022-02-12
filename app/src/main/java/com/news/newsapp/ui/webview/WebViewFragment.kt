package com.news.newsapp.ui.webview

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.news.newsapp.R
import com.news.newsapp.base.BaseFragment
import com.news.newsapp.databinding.WebviewFragmentBinding
import android.webkit.WebChromeClient
import android.webkit.WebChromeClient.CustomViewCallback
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.news.newsapp.main.MainActivityViewModel


class WebViewFragment : BaseFragment(){
    private var webViewUrl : String = ""
    private var source : String = ""
    private lateinit var binding : WebviewFragmentBinding

    private var mCustomView: View? = null
    private var mWebChromeClient: MyWebChromeClient? = null
    private var mWebViewClient: WebViewClient? = null
    private var customViewCallback: CustomViewCallback? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = WebviewFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBundleArguments()
        initView()
        initOnBackPressDispatcher()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    private fun initBundleArguments(){
        val args = requireArguments()
        webViewUrl = args.getString(WEB_VIEW_URL).toString()
        source = args.getString(WEB_VIEW_SOURCE).toString()
    }

    private fun initView() {
        initWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView(){
        binding.webView.run {
            mWebViewClient = WebViewClient()
            webViewClient = mWebViewClient!!
            mWebChromeClient = MyWebChromeClient()
            this.webChromeClient = mWebChromeClient
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.loadsImagesAutomatically = true
            settings.setSupportZoom(true)
            this.settings.builtInZoomControls = true
            loadUrl(webViewUrl)
        }
    }

    companion object {
        const val WEB_VIEW_URL = "WEB_VIEW_URL"
        const val WEB_VIEW_SOURCE= "WEB_VIEW_SOURCE"
    }

    override fun getTitle(): String {
        return if (TextUtils.isEmpty(source)){
            getString(R.string.news_source)
        }else{
            source
        }
    }

    override fun hideBackButton(): Boolean {
        return false
    }

    inner class MyWebChromeClient : WebChromeClient() {
        private var mVideoProgressView: View? = null
        override fun onShowCustomView(
            view: View,
            requestedOrientation: Int,
            callback: CustomViewCallback
        ) {
            onShowCustomView(
                view,
                callback
            )
        }

        override fun onShowCustomView(view: View, callback: CustomViewCallback) {

            viewModel.setToolbarVisible(false)
            if (mCustomView != null) {
                callback.onCustomViewHidden()
                return
            }
            this@WebViewFragment.mCustomView = view
            binding.run {
                webView.visibility = View.GONE
                container.visibility = View.VISIBLE
                container.addView(view)
            }
            customViewCallback = callback
        }

        override fun getVideoLoadingProgressView(): View? {
            if (mVideoProgressView == null) {
                val inflater = LayoutInflater.from(this@WebViewFragment.context)
                mVideoProgressView = inflater.inflate(R.layout.video_progress, null)
            }
            return mVideoProgressView
        }

        override fun onHideCustomView() {
            super.onHideCustomView()
            viewModel.setToolbarVisible(true)

            if (mCustomView == null){
                return
            }
            binding.run {
                webView.visibility = View.VISIBLE
                container.visibility = View.GONE

                // Hide the custom view.
                mCustomView?.visibility = View.GONE

                // Remove the custom view from its container.
                container.removeView(mCustomView)
                customViewCallback?.onCustomViewHidden()
                mCustomView = null
            }
        }
    }

    private fun inCustomView(): Boolean {
        return mCustomView != null
    }

    private fun hideCustomView() {
        mWebChromeClient?.onHideCustomView()
    }

    override fun onPause() {
        super.onPause()
        binding.webView.onPause()
    }

    override fun onResume() {
        super.onResume()
        binding.webView.onResume()
    }

    override fun onStop() {
        super.onStop()
        if (inCustomView()) {
            hideCustomView()
        }
    }

    private fun initOnBackPressDispatcher() {
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(
                viewLifecycleOwner, object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        binding.run {
                            when {
                                webView.canGoBack() -> {
                                    webView.goBack()
                                }
                                else -> {
                                    findNavController().navigateUp()
                                }
                            }
                        }
                    }
                })
    }

    private val viewModel by activityViewModels<MainActivityViewModel>()

}