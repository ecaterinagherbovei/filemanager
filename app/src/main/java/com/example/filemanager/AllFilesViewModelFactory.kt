package com.example.filemanager

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AllFilesViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AllFilesViewModel::class.java)) {
            return AllFilesViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
