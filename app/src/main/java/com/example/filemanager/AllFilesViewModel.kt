package com.example.filemanager

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Environment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.File

class AllFilesViewModel(
    @SuppressLint("StaticFieldLeak")
    private val context: Context
) : ViewModel() {

    val files = MutableLiveData<List<ListModel>>()

    fun fetchFiles() {
        val path = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            System.getenv("EXTERNAL_STORAGE")
        } else {
            Environment.getExternalStorageDirectory().absolutePath
        }
        //Log.d("ENV_VARS", System.getenv().toString())

        if (path == null) return
        //Log.d("PATH", path)
        val files2 = File(path).listFiles()?.map {
            ListModel(it)
        } ?: emptyList()
        files.postValue(files2)
        //Log.d("FILE", files2.toString())
    }
}
