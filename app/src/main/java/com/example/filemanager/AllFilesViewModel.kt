package com.example.filemanager

import android.os.Build
import android.os.Environment
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.File

class AllFilesViewModel() : ViewModel() {

    val files = MutableLiveData<List<ListModel>>()

    @RequiresApi(Build.VERSION_CODES.R)
    fun fetchFiles() {
        val path = Environment.getExternalStorageDirectory().absolutePath ?: return
        Log.d("PATH", path)
        val files2 = File(path).listFiles()?.map {
            ListModel(it)
        } ?: emptyList()
        files.postValue(files2)
    }
}
