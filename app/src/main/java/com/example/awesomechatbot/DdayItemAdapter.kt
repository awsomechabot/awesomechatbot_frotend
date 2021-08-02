package com.example.awesomechatbot

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.dday_item.view.*

class DdayItemAdapter (var items:ArrayList<DdayItem>) : RecyclerView.Adapter<DdayItemAdapter.ViewHolder>() { // 대형 쇼핑몰 정보 리사이클러뷰로 보여주기 위함
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DdayItemAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.dday_item, parent,false)
        return ViewHolder(itemView)
    }

    override fun getItemCount()= items.count()

    override fun onBindViewHolder(holder: DdayItemAdapter.ViewHolder, position: Int) {
        val item = items[position]

        holder.setItem(item)
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun setItem(item:DdayItem) {
            var imgsrc = 0
            when(item.part) {
                "머리" -> imgsrc = R.drawable.m_head
                "배" -> imgsrc = R.drawable.m_stomach
                "발톱/손톱" -> imgsrc = R.drawable.nail
                "치아" -> imgsrc = R.drawable.m_tooth
            }
            itemView.item_partimg.setImageResource(imgsrc)
            itemView.item_hd.text = item.hospital_name + "  -  " + item.disease_name
            itemView.item_redate.text = item.redate
            itemView.item_dday.text = "D-"+item.dday.toString()
        }
    }
}