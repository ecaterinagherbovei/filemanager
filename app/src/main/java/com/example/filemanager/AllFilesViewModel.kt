package com.example.filemanager

import android.os.Environment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AllFilesViewModel() : ViewModel() {

    val files = MutableLiveData<List<ListModel>>()
    private val rootDirectory: ListModel = ListModel(Environment.getExternalStorageDirectory())
    var currentDirectory: ListModel = rootDirectory

    fun listRootDirectoryFiles() {
        files.postValue(listFiles(rootDirectory))
    }

    fun listFiles(currentItem: ListModel): List<ListModel> {
        return currentItem.file.listFiles()?.map {
            ListModel(it)
        } ?: emptyList()
    }

    fun listParentDirectory(): List<ListModel> {
        val parentDirectory = currentDirectory.parentDirectory() ?: return emptyList()
        currentDirectory = ListModel(parentDirectory)
        return listFiles(currentDirectory)
    }

    fun hasReachedRootFolder(): Boolean {
        return currentDirectory.isRootDirectory()
    }
}
