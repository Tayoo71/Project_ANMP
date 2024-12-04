package com.example.project_anmp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class MainViewModel(application: Application)
    : AndroidViewModel(application), CoroutineScope {

    private var job = Job()
    var username = MutableLiveData<String>()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    fun setUsername(newUsername: String) {
        username.postValue(newUsername)
    }
}