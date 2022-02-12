package com.news.newsapp.ui.main.viewholders

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.news.newsapp.R
import com.news.newsapp.base.baseadapter.BaseAdapterListener
import com.news.newsapp.base.baseadapter.RecyclerItem
import com.news.newsapp.base.baseadapter.Row
import com.news.newsapp.entities.News

object NewsRow : Row<RecyclerItem>() {

    override fun belongsTo(item: RecyclerItem?): Boolean {
        return item is News
    }

    override fun type(): Int {
        return R.layout.item_news_list
    }

    override fun holder(parent: ViewGroup): RecyclerView.ViewHolder {
        return NewsViewHolder(parent.viewOf(type()))
    }

    override fun bind(
        holder: RecyclerView.ViewHolder,
        item: RecyclerItem?,
        position: Int,
        listenerBase: BaseAdapterListener?
    ) {
        if (holder is NewsViewHolder && item is News) {
            holder.bind(item,listenerBase)
        }
    }
}