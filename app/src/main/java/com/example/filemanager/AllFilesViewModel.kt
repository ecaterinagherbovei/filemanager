package com.example.filemanager

import android.os.Environment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.File

class AllFilesViewModel() : ViewModel() {

    val files = MutableLiveData<List<ListModel>>()
    private val rootDirectory: File = Environment.getExternalStorageDirectory()
    var currentFile: ListModel? = ListModel(rootDirectory)

    fun fetchFiles() {
        files.postValue(listFiles(ListModel(rootDirectory)))
    }

    fun listFiles(currentItem: ListModel): List<ListModel> {
        return currentItem.file.listFiles()?.map {
            ListModel(it)
        } ?: emptyList()
    }

    fun listParentDirectory(): List<ListModel> {
        val parentFile = currentFile?.file?.parentFile ?: return emptyList()
        currentFile = ListModel(parentFile)
        return listFiles(ListModel(parentFile))
    }

    fun compareDirectories(): Boolean {
        return currentFile?.file != rootDirectory
    }
}
