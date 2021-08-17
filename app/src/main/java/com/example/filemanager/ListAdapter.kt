package com.example.filemanager

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

internal class ListAdapter(
    private val context: Context,
    private val list: ArrayList<ListModel>,
    private val itemClickListener: ItemClickListener
) :
    RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    internal inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var fileName: TextView = view.findViewById(R.id.fileName)
        var icon: ImageView = view.findViewById(R.id.icon)
        var additionalInfo: TextView = view.findViewById(R.id.additionalInfo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_of_files, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.icon.setImageDrawable(item.icon?.let { ContextCompat.getDrawable(context, it) })
        holder.fileName.text = item.fileName
        holder.additionalInfo.text = item.additionalInfo
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClickListener()
        }
    }

    override fun getItemCount(): Int {
        return list.count()
    }
}
