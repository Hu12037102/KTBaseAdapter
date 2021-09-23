package com.huxiaobai.adapter

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView

/**
 * 作者: 胡庆岭
 * 创建时间: 2021/9/22 16:07
 * 更新时间: 2021/9/22 16:07
 * 描述:
 */
class EmptyViewHolder(itemView: View) : BaseViewHolder(itemView) {
    private val mAivEmpty: AppCompatImageView = itemView.findViewById(R.id.aiv_empty)
    private val mAtvContent:AppCompatTextView= itemView.findViewById(R.id.atv_empty)

}