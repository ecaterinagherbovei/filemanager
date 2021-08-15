package com.example.filemanager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

internal class ListAdapter : RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    private var items = listOf<ListModel>()

    internal inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.fileName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_of_files, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.name.text = item.setGetName
    }

    fun replaceItems(items: List<ListModel>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size
}
