package com.example.project_anmp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.project_anmp.model.AppDatabase.Companion.buildDatabase
import com.example.project_anmp.model.Game
import com.example.project_anmp.model.GameData
import com.example.project_anmp.model.ScheduleData
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class WhatWePlayViewModel(application: Application) : AndroidViewModel(application),
    CoroutineScope {
    val whatWePlayLD = MutableLiveData<List<GameData>>()
    val playLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()
    private var job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    // Function to refresh and load data from the server
    fun refresh() {
        playLoadErrorLD.value = false
        loadingLD.value = true

        launch {
            val db = buildDatabase(getApplication())
            try {
                val games = db.gameDao().getAllGames()
                whatWePlayLD.postValue(games)
                loadingLD.postValue(false)
            } catch (e: Exception) {
                // Handle the error and set the loadError LiveData
                playLoadErrorLD.postValue(true)
                loadingLD.postValue(false)
            }
        }
    }
}
