package com.news.newsapp.entities

import androidx.room.*
import com.news.newsapp.base.baseadapter.BaseAdapterClick
import com.news.newsapp.base.baseadapter.RecyclerItem
import com.news.newsapp.utils.Constants

@Entity(tableName = Constants.TABLE_NEWS,
    indices=[Index(value= arrayOf("url"), unique = true)])
data class News(
    @Embedded(prefix = "source_") var source: Source?,
    @ColumnInfo(name = "author") var author: String?,
    @ColumnInfo(name = "title") var title: String?,
    @ColumnInfo(name = "description") var description: String?,
    @ColumnInfo(name = "url") var url: String?,
    @ColumnInfo(name = "urlToImage") var urlToImage: String?,
    @ColumnInfo(name = "publishedAt") var publishedAt: String?,
    @ColumnInfo(name = "content") var content: String?
) : RecyclerItem, BaseAdapterClick {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    override var id:Int? = null
    override fun toString(): String {
        return "News(source=$source, author=$author, title=$title, description=$description, url=$url, urlToImage=$urlToImage, publishedAt=$publishedAt, content=$content)"
    }
}