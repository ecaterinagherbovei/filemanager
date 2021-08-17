package com.example.filemanager

import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filemanager.databinding.FragmentAllFilesBinding
import java.io.File

class AllFilesFragment : Fragment(), ItemClickListener {
    private lateinit var adapter: ListAdapter
    private var _binding: FragmentAllFilesBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            // columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAllFilesBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val items = mutableListOf<ListModel>()
        val path = Environment.getRootDirectory().absolutePath
        File(path).walk().forEach {
            items.add(ListModel(it.name))
        }

        adapter = ListAdapter(this)
        val layoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.itemAnimator = DefaultItemAnimator()
        adapter.replaceItems(items)
        binding.recyclerView.adapter = adapter
    }

    override fun onItemClickListener() {
        Toast.makeText(context,"Item clicked", Toast.LENGTH_SHORT).show()
    }
}
