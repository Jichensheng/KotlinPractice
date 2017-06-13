package com.heshun.firstkotlin

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * author：Jics
 * 2017/6/13 14:13
 */
class RvListAdapter(val context: Context, val list: List<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is MyViewHolder) {
            holder.tv_item.text = "hello"+list[position] + position
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.rv_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var tv_item: TextView

        init {
            tv_item = itemView?.findViewById(R.id.tv_item) as TextView
        }
    }
}