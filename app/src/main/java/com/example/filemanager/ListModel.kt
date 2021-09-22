package com.example.filemanager

import android.os.Environment
import java.io.File

data class ListModel(val file: File) {
    fun isDirectory(): Boolean {
        return file.isDirectory
    }

    fun isRootDirectory(): Boolean {
        return file == Environment.getExternalStorageDirectory()
    }

    fun parentDirectory(): ListModel {
        return ListModel(file.parentFile)
    }

    fun listFiles(): List<ListModel> {
        return file.listFiles().map {
            ListModel(it)
        }
    }
}
