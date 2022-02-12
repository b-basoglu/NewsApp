package com.news.newsapp.base.baseadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

interface RecyclerItem {
    val id: Int?
    override fun equals(other: Any?): Boolean
}

abstract class Row<T> {

    abstract fun belongsTo(item: T?): Boolean
    abstract fun type(): Int
    abstract fun holder(parent: ViewGroup): RecyclerView.ViewHolder
    abstract fun bind(
        holder: RecyclerView.ViewHolder,
        item: T?,
        position: Int,
        listenerBase: BaseAdapterListener?
    )


    protected fun ViewGroup.viewOf(@LayoutRes resource: Int): View {
        return LayoutInflater
            .from(context)
            .inflate(resource, this, false)
    }
}