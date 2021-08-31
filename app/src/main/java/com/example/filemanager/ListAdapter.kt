package com.example.filemanager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class ListAdapter(
    private val itemClickListener: ItemClickListener
) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    private val files = mutableListOf<ListModel>()

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
        val item = files[position]
        val fileIconRes = if (item.file.isDirectory) R.drawable.folder_icon else R.drawable.file_icon

        holder.icon.setImageDrawable(ContextCompat.getDrawable(holder.itemView.context, fileIconRes))

        holder.fileName.text = item.file.name
        holder.additionalInfo.text = item.file.totalSpace.toString()

        holder.itemView.setOnClickListener {
            itemClickListener.onItemClickListener()
        }
    }

    override fun getItemCount() = files.size

    fun setFiles(files: List<ListModel>) {
        this.files.clear()
        this.files.addAll(files)
        notifyDataSetChanged()
    }
}
