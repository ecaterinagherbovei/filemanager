package com.example.filemanager

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filemanager.databinding.FragmentAllFilesBinding


class AllFilesFragment : Fragment(), ItemClickListener {
    private lateinit var adapter: ListAdapter
    private lateinit var binding: FragmentAllFilesBinding
    private lateinit var viewModel: AllFilesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, AllFilesViewModelFactory(applicationContext()))
            .get(AllFilesViewModel::class.java)
        adapter = ListAdapter(this)

        requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                handleBackPress()
            }
        })
    }

    private fun handleBackPress() {
        if (!viewModel.hasReachedRootFolder()) {
            adapter.setFiles(viewModel.listParentDirectory())
        } else {
            hideToolbarNavigation()
            Toast.makeText(context, "Root folder", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllFilesBinding.inflate(inflater, container, false)
        val layoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                RecyclerView.VERTICAL
            )
        )
        return binding.root
    }

    private fun applicationContext() = requireContext().applicationContext

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.files.observe(viewLifecycleOwner, { files ->
            adapter.setFiles(files)
        })
    }

    override fun onStart() {
        super.onStart()
        fetchFiles()
    }

    private fun getMainActivity(): MainActivity = requireActivity() as MainActivity

    private fun setToolbarNavigation() {
        val toolbar = activity?.findViewById<Toolbar>(R.id.toolbar)
        getMainActivity().setSupportActionBar(toolbar)
        getMainActivity().supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar?.setNavigationOnClickListener {
            handleBackPress()
        }
    }

    private fun hideToolbarNavigation() {
        getMainActivity().supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun onFileClick(file: ListModel) {
        setToolbarNavigation()
        if (file.isDirectory()) {
            viewModel.currentDirectory = file
            adapter.setFiles(viewModel.listFiles(file))
        } else {
            Toast.makeText(context, "FILE", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchFiles() {
        if (!checkPermission()) {
            showPermissionDialog()
        } else {
            viewModel.listRootDirectoryFiles()
        }
    }

    private fun showPermissionDialog() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val uri = Uri.parse("package:${BuildConfig.APPLICATION_ID}")
            startActivity(
                Intent(
                    Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, uri
                )
            )
        } else {
            requestPermissions(
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE
            )
        }
    }

    private fun checkPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Environment.isExternalStorageManager()
        } else {
            val writePermission = ContextCompat.checkSelfPermission(
                applicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            writePermission == PackageManager.PERMISSION_GRANTED
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        if (requestCode == PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.isNotEmpty()) {
                val writePermission = grantResults[0] == PackageManager.PERMISSION_GRANTED
                if (writePermission) {
                    fetchFiles()
                } else {
                    Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object {
        const val PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE = 333
    }
}
