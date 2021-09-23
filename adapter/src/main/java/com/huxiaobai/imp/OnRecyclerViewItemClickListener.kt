package com.huxiaobai.imp

import android.view.View
import org.jetbrains.annotations.NotNull

/**
 * 作者: 胡庆岭
 * 创建时间: 2021/9/22 17:59
 * 更新时间: 2021/9/22 17:59
 * 描述:
 */
interface OnRecyclerViewItemClickListener {
    fun clickItem(@NotNull view: View, position: Int)
    fun longClickItem(@NotNull view: View, position: Int)
    fun clickEmptyView(@NotNull view: View)

}