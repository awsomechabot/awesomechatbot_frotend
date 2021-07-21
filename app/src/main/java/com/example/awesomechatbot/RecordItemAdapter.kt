package com.example.awesomechatbot

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_showrecord.view.*

class RecordItemAdapter (var items:ArrayList<RecordItem>) : RecyclerView.Adapter<RecordItemAdapter.ViewHolder>() { // 대형 쇼핑몰 정보 리사이클러뷰로 보여주기 위함
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordItemAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_showrecord,parent,false)
        return ViewHolder(itemView)
    }

    override fun getItemCount()= items.count()

    override fun onBindViewHolder(holder: RecordItemAdapter.ViewHolder, position: Int) {
        val item = items[position]

        holder.setItem(item)
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun setItem(item:RecordItem) {
            itemView.recycler_hospitalname.text = item.hname
            itemView.recycler_showrecord_date.text = item.redate
            itemView.setOnClickListener { // 상세정보 보여주기
                val mActivity = itemView.context as MainActivity
                mActivity.setDataAtFragmentHome(InfoFragment(), item)
            }
        }

    }
}