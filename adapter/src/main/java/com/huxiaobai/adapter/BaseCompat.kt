package com.huxiaobai.adapter

import android.widget.TextView

/**
 * 作者: 胡庆岭
 * 创建时间: 2021/9/22 10:23
 * 更新时间: 2021/9/22 10:23
 * 描述:
 */
object BaseCompat {
    fun <T> isEmptyList(list: List<T>?): Boolean = list == null || list.isEmpty()
    fun <T> listSize(list: List<T>?): Int = if (list == null || list.isEmpty()) 0 else list.size
}