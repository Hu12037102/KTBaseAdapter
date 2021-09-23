package com.huxiaobai.baseadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.huxiaobai.adapter.BaseRecyclerAdapter

/**
 * 作者: 胡庆岭
 * 创建时间: 2021/9/22 17:53
 * 更新时间: 2021/9/22 17:53
 * 描述:
 */
open class TextAdapter(mContext: Context, mData: List<String>) :
    BaseRecyclerAdapter<String>(mContext, mData) {
    override fun onCreateChildViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(mContext).inflate(R.layout.item_text_view, parent, false)
        )
    }

    override fun onBindChildViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val text: String = mData[position]
        if (holder is ViewHolder) {
            holder.mAtvContent.text = text
        }
    }

    private class ViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mAtvContent: AppCompatTextView = itemView.findViewById(R.id.atv_content)
    }
}