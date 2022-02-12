package com.news.newsapp.utils

import android.content.res.Resources
import android.util.DisplayMetrics
import kotlin.math.roundToInt

private val displayMetrics: DisplayMetrics by lazy { Resources.getSystem().displayMetrics }

val Number.dp2px: Int
    get() = (this.toFloat() * displayMetrics.density).roundToInt()
