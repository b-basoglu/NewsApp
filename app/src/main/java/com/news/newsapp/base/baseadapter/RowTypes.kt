package com.news.newsapp.base.baseadapter

class RowTypes<T>(vararg types: Row<T>) {

    private val rowTypes: ArrayList<Row<T>> = ArrayList()

    init {
        types.forEach { addType(it) }
    }

    fun addType(type: Row<T>) {
        rowTypes.add(type)
    }

    fun of(item: T?): Row<T> {
        for (cellType in rowTypes) {
            if (cellType.belongsTo(item)) return cellType
        }
        throw RecyclerItemMissMatchException()
    }

    fun of(viewType: Int): Row<T> {
        for (cellType in rowTypes) {
            if (cellType.type() == viewType) return cellType
        }
        throw RecyclerViewMissMatchException()
    }

}

class RecyclerItemMissMatchException : RuntimeException()
class RecyclerViewMissMatchException : RuntimeException()