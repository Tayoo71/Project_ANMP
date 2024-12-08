package com.example.project_anmp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.project_anmp.model.ScheduleData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class ScheduleDetailViewModel (application: Application): AndroidViewModel(application),
    CoroutineScope {
    val scheduleDetailLD = MutableLiveData<ScheduleData>()
    val notifyButtonLD = MutableLiveData<String>("Notify Me")
    private var job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun refresh(schedule: ScheduleData) {
        scheduleDetailLD.value = schedule
    }

    fun toggleNotification() {
        notifyButtonLD.postValue(
            if (notifyButtonLD.value == "Notify Me") {
                "Disable Notification"
            } else {
                "Notify Me"
            }
        )
    }
}