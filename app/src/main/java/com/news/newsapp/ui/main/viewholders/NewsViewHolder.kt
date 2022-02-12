package com.news.newsapp.ui.main.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.news.newsapp.base.baseadapter.BaseAdapterListener
import com.news.newsapp.databinding.ItemNewsListBinding
import com.news.newsapp.entities.News

class NewsViewHolder (view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemNewsListBinding.bind(view)

    fun bind(news: News?, listenerBase: BaseAdapterListener?) {
        binding.root.setOnClickListener { listenerBase?.onClick(news) }
        news?.let {
            binding.tvTitle.text = it.title
            binding.tvDesc.text = it.description
            it.source?.name?.let { sourceName ->
                binding.tvSrc.text = sourceName
            }
            binding.newsImage.setImage(it.urlToImage)
        }
    }
}