package com.example.filemanager

import android.os.Environment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AllFilesViewModel() : ViewModel() {

    val files = MutableLiveData<List<ListModel>>()
    private val listRootDirectoryFiles: ListModel = ListModel(Environment.getExternalStorageDirectory())
    var currentFile: ListModel? = listRootDirectoryFiles

    fun fetchFiles() {
        files.postValue(listFiles(listRootDirectoryFiles))
    }

    fun listFiles(currentItem: ListModel): List<ListModel> {
        return currentItem.file.listFiles()?.map {
            ListModel(it)
        } ?: emptyList()
    }

    fun listParentDirectory(): List<ListModel> {
        val parentFile = currentFile?.file?.parentFile ?: return emptyList()
        currentFile = ListModel(parentFile)
        return listFiles(currentFile!!)
    }

    fun hasReachedRootFolder(): Boolean {
        return currentFile!!.isRootDirectory()
    }
}
