package com.huxiaobai.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.huxiaobai.imp.OnRecyclerViewItemClickListener

/**
 * 作者: 胡庆岭
 * 创建时间: 2021/9/22 10:13
 * 更新时间: 2021/9/22 10:13
 * 描述: BaseAdapter
 */
abstract class BaseRecyclerAdapter<T>(
    protected val mContext: Context,
    protected val mData: List<T>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    protected val mHeadParentGroup: ConstraintLayout = ConstraintLayout(mContext)
    protected val mFootParentGroup: ConstraintLayout = ConstraintLayout(mContext)
    private var mOnRecyclerViewItemClickListener: OnRecyclerViewItemClickListener? = null
    private var isFirstShowEmptyView = false
    var mHasHeadView = false
    var mHasFootView = false

    companion object {
       const val TYPE_HEAD_VIEW = 100
        const val TYPE_FOOT_VIEW = 101
        const val TYPE_EMPTY_VIEW = 102
    }

    init {
        mHeadParentGroup.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        mFootParentGroup.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    fun setOnRecyclerViewItemClickListener(onRecyclerViewItemClickListener: OnRecyclerViewItemClickListener) {
        this.mOnRecyclerViewItemClickListener = onRecyclerViewItemClickListener
    }

    fun addHeadView(headView: View) {
        this.mHasHeadView = true
        mHeadParentGroup.addView(headView)
    }

    fun removeHeadView() {
        mHeadParentGroup.removeAllViews()
        this.mHasHeadView = false
    }

    fun addFootView(footView: View) {
        this.mHasFootView = true
        mFootParentGroup.addView(footView)

    }

    fun removeFootView() {
        mFootParentGroup.removeAllViews()
        this.mHasFootView = false
    }

    override fun getItemCount(): Int {
        var count = 0
        val listSize = BaseCompat.listSize(mData)
        count += if (listSize == 0) {
            1
        } else {
            listSize
        }
        count += if (mHasHeadView) {
            1
        } else {
            0
        }
        count += if (mHasFootView) {
            1
        } else {
            0
        }
        Log.w("BaseRecyclerAdapter--", "getItemCount:$count")
        return count
    }

    override fun getItemViewType(position: Int): Int {
        val type = when {
            isHeadViewPosition(position) -> {
                TYPE_HEAD_VIEW
            }
            isFootViewPosition(position) -> {
                TYPE_FOOT_VIEW
            }
            BaseCompat.listSize(mData) == 0 -> {
                TYPE_EMPTY_VIEW
            }
            else -> super.getItemViewType(position)
        }
        Log.w("BaseRecyclerAdapter--", "getItemViewType:$type")
        return type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder = when (viewType) {
            TYPE_HEAD_VIEW ->
                BaseViewHolder(mHeadParentGroup)
            TYPE_FOOT_VIEW -> BaseViewHolder(mFootParentGroup)
            TYPE_EMPTY_VIEW -> EmptyViewHolder(
                LayoutInflater.from(mContext).inflate(R.layout.item_empty_view, parent, false)
            )
            else -> onCreateChildViewHolder(parent, viewType)
        }
        Log.w("BaseRecyclerAdapter--", "onCreateViewHolder:$viewHolder")

        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemType = getItemViewType(position)
        if (itemType == 0) {
            var index = position
            if (mHasHeadView) {
                index -= 1
            }
            onBindChildViewHolder(holder, index)
            initHolderEvent(holder, index)
        }else if (holder is EmptyViewHolder){
            if (!isFirstShowEmptyView){
                holder.itemView.isVisible = false
                isFirstShowEmptyView = true
            }else{
                holder.itemView.isVisible = true
            }
        }
        Log.w("BaseRecyclerAdapter--", "onBindViewHolder->type:$itemType--position:$position")
    }

    private fun initHolderEvent(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            mOnRecyclerViewItemClickListener?.clickItem(it, position)
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val layoutManger = recyclerView.layoutManager
        if (layoutManger is GridLayoutManager) {
            layoutManger.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when {
                        isSingSpanCount(position) -> layoutManger.spanCount
                        else
                        -> 1
                    }
                }
            }
        }
        Log.w("BaseRecyclerAdapter--", "onAttachedToRecyclerView:${recyclerView.layoutManager}")
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        val layoutParams: ViewGroup.LayoutParams = holder.itemView.layoutParams
        if (layoutParams is StaggeredGridLayoutManager) {
            val position = holder.adapterPosition
            layoutParams.spanCount = if (isSingSpanCount(position)) 1 else layoutParams.spanCount
            holder.itemView.layoutParams = layoutParams
        }
        Log.w("BaseRecyclerAdapter--", "onViewAttachedToWindow->position:${holder.adapterPosition}")
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        Log.w(
            "BaseRecyclerAdapter--",
            "onViewDetachedFromWindow->position:${holder.adapterPosition}"
        )
    }

    private fun isHeadViewPosition(position: Int): Boolean = position == 0 && mHasHeadView
    private fun isFootViewPosition(position: Int): Boolean =
        position == itemCount - 1 && mHasFootView

    private fun isSingSpanCount(position: Int): Boolean =
        isHeadViewPosition(position) || isFootViewPosition(position)
                || BaseCompat.isEmptyList(mData)

    abstract fun onCreateChildViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
    abstract fun onBindChildViewHolder(holder: RecyclerView.ViewHolder, position: Int)


}