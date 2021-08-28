package com.example.filemanager

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class ListAdapter(
    private val context: Context,
    private val itemClickListener: ItemClickListener
) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    private val list = mutableListOf<ListModel>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val fileName = view.findViewById<TextView>(R.id.fileName)
        val icon = view.findViewById<ImageView>(R.id.icon)
        val additionalInfo = view.findViewById<TextView>(R.id.additionalInfo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_of_files, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        if (item.file.isDirectory) {
            holder.icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.folder_icon))
        } else if (item.file.isFile) {
            holder.icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.file_icon))
        }
        holder.fileName.text = item.file.name
        holder.additionalInfo.text = item.file.totalSpace.toString()

        holder.itemView.setOnClickListener {
            itemClickListener.onItemClickListener()
        }
    }

    override fun getItemCount() = list.size

    fun setFiles(files: List<ListModel>) {
        list.clear()
        list.addAll(files)
        notifyDataSetChanged()
    }
}
