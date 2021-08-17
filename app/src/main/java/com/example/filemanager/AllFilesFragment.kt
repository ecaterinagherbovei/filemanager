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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.util.ArrayList
import com.example.filemanager.databinding.FragmentAllFilesBinding

class AllFilesFragment : Fragment(), ItemClickListener {
    private lateinit var adapter: ListAdapter
    private var _binding: FragmentAllFilesBinding ? = null
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

        val layoutManager = LinearLayoutManager(context)
        adapter = ListAdapter(requireContext(), fetchList(), this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                RecyclerView.VERTICAL
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun fetchList(): ArrayList<ListModel> {
        val list = arrayListOf<ListModel>()
        val path = Environment.getRootDirectory().absolutePath
        var icon : Int
        File(path).walk().forEach {
            if (it.isDirectory){
                icon = R.drawable.folder_icon
                list.add(ListModel(icon, it.name, it.usableSpace.toString()))
            }else if (it.isFile){
                icon = R.drawable.file_icon
                list.add(ListModel(icon, it.name, it.usableSpace.toString()))
            }
        }
        return list
    }

    override fun onItemClickListener() {
        Toast.makeText(context, "Item clicked", Toast.LENGTH_SHORT).show()
    }
}
