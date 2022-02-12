package com.news.newsapp.entities

data class Source (
    val id: String ?= null,
    val name: String ?= null,
) {
    override fun toString(): String {
        return "Source(id=$id, name=$name)"
    }
}