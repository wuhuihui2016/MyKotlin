package com.whh.mykotlin.mvvm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.whh.mykotlin.R

class StudentAdapter : RecyclerView.Adapter<StudentAdapter.MyViewHolder>() {

    var studentDatas: List<Student> = ArrayList()

    // 内部类
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvID: TextView = itemView.findViewById(R.id.tv_id)
        var tvName: TextView = itemView.findViewById(R.id.tv_name)
        var tvAge: TextView = itemView.findViewById(R.id.tv_age)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val itemView: View = layoutInflater.inflate(R.layout.item_collect_list, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val student = studentDatas[position]
        holder.tvID.text = "${position + 1}"
        holder.tvName.text = student.name
        holder.tvAge.text = "${student.age}"
        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context, student.name, Toast.LENGTH_LONG).show();
        }
    }

    override fun getItemCount(): Int = studentDatas.size
}