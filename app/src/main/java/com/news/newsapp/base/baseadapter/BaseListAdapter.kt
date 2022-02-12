package com.news.newsapp.base.baseadapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BasePagedListAdapter(
    vararg types: Row<RecyclerItem>,
    private val listenerBase: BaseAdapterListener? = null
) : PagingDataAdapter<RecyclerItem, RecyclerView.ViewHolder>(object :
    DiffUtil.ItemCallback<RecyclerItem>() {

    override fun areItemsTheSame(oldItem: RecyclerItem, newItem: RecyclerItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RecyclerItem, newItem: RecyclerItem): Boolean {
        return oldItem == newItem
    }
}) {

    private val rowTypes: RowTypes<RecyclerItem> = RowTypes(*types)

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return rowTypes.of(item).type()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return rowTypes.of(viewType).holder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let {
            rowTypes.of(it).bind(holder, it, holder.layoutPosition, listenerBase)
        }
    }
}
