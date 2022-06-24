package com.huxiaobai.baseadapter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.huxiaobai.adapter.BaseCompat

class MainActivity : AppCompatActivity() {
    private lateinit var mBtnAdd: Button
    private lateinit var mRvContent: RecyclerView
    private val mData = arrayListOf<String>()
    private lateinit var mAdapter: TextAdapter
    private lateinit var mBtnRemove: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initData()
        initEvent()
    }

    private fun initView() {
        mBtnAdd = findViewById(R.id.btn_add)
        mRvContent = findViewById(R.id.rv_content)
        mBtnRemove = findViewById(R.id.btn_remove)
        // mRvContent.layoutManager = LinearLayoutManager(this)
        mRvContent.layoutManager =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        mRvContent.itemAnimator = DefaultItemAnimator()
    }

    private fun initData() {


        mAdapter = TextAdapter(this, mData)
        mAdapter.setShowEmptyView(true)
        mAdapter.setEmptyData("sb", 0)
        mRvContent.adapter = mAdapter
        //  mAdapter.addHeadView(createHeadView())
        //  mAdapter.addFootView(createFootView())
        mRvContent.postDelayed(Runnable {
            /*    for (i in 0 until 20) {
                    mData.add("胡歌:$i")


                }*/
            mAdapter.notifyDataSetChanged()
            //   mAdapter.notifyDataSetChanged()
        }, 2000)
    }

    private fun createHeadView(): View {
        val headView = layoutInflater.inflate(R.layout.item_text_view, mRvContent, false)
        val textContent = headView.findViewById<TextView>(R.id.atv_content)
        textContent.text = "我是HeadView"
        return headView
    }

    private fun createFootView(): View {
        val footView = layoutInflater.inflate(R.layout.item_text_view, mRvContent, false)
        val textContent = footView.findViewById<TextView>(R.id.atv_content)
        textContent.text = "我是FootView"
        return footView
    }

    private fun initEvent() {
        mBtnAdd.setOnClickListener {
            mData.add("胡歌:${BaseCompat.listSize(mData)}")
            //  mAdapter.notifyItemInserted(mData.size - 1)
            mAdapter.notifyDataSetChanged()
        }
        mAdapter.notifyDataSetChanged()
        mBtnRemove.setOnClickListener {
            if (!BaseCompat.isEmptyList(mData)) {
                mData.removeAt(0)
                mAdapter.notifyDataSetChanged()
            }
        }
    }
}