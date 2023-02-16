package com.example.recycler_view_practice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val newlist:ArrayList<list>):
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    private  lateinit var mListener : onItemClickListener

    interface onItemClickListener
    {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener)
    {
        mListener=listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)

        return MyViewHolder(itemView,mListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val curr=newlist[position]
        holder.image.setImageResource(curr.titleImage)
        holder.titlename.text=curr.name
    }

    override fun getItemCount(): Int {
        return newlist.size
    }
    class MyViewHolder(itemView: View,listener: onItemClickListener):RecyclerView.ViewHolder(itemView)
    {
        val image:ImageView=itemView.findViewById(R.id.img)
        val titlename:TextView=itemView.findViewById(R.id.name)


        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
}

