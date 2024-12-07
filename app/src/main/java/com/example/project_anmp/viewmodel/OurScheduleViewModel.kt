package com.example.project_anmp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.project_anmp.model.AppDatabase.Companion.buildDatabase
import com.example.project_anmp.model.ScheduleData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class OurScheduleViewModel(application: Application): AndroidViewModel(application),
    CoroutineScope {
    val ourSchedulesLD = MutableLiveData<List<ScheduleData>>()
    val scheduleLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()
    private var job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    // Fungsi untuk memuat data dari server (atau sumber data lainnya)
    fun refresh(){
        scheduleLoadErrorLD.value = false
        loadingLD.value = true

        launch {
            val db = buildDatabase(getApplication())
            try {
                val schedules = db.scheduleDao().getAllSchedules()
                ourSchedulesLD.postValue(schedules)
                loadingLD.postValue(false)
            } catch (e: Exception) {
                // Handle the error and set the loadError LiveData
                scheduleLoadErrorLD.postValue(true)
                loadingLD.postValue(false)
            }
        }
    }
}