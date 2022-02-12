package com.news.newsapp.ui.main

import com.news.newsapp.base.baseadapter.BaseAdapterListener
import com.news.newsapp.base.baseadapter.BasePagedListAdapter
import com.news.newsapp.ui.main.viewholders.NewsRow


class NewsListRecyclerviewAdapter(listenerBase: BaseAdapterListener) : BasePagedListAdapter(
    NewsRow,
    listenerBase = listenerBase
)