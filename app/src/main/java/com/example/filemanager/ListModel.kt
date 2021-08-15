package com.example.filemanager

class ListModel(fileName: String?) {
    var setGetName: String? = fileName
        get() = field.toString()
        set(value) {
            field = value.toString()
        }
}
