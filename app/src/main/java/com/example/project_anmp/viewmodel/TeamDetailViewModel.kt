package com.example.project_anmp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.project_anmp.model.AppDatabase.Companion.buildDatabase
import com.example.project_anmp.model.Team
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class TeamDetailViewModel (application: Application) : AndroidViewModel(application),
    CoroutineScope {

    // LiveData to hold the list of teams
    val teamsLD = MutableLiveData<List<Team>>()
    val teamsLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()

    // Job for coroutine management
    private var job = Job()

    override val coroutineContext: CoroutineContext
    get() = job + Dispatchers.IO

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    // Function to load teams for a specific game
    fun refresh(gameName: String, teamName: String) {
        teamsLoadErrorLD.value = false
        loadingLD.value = true

        launch {
            val db = buildDatabase(getApplication())
            try {
                val allTeams = db.teamDao().getTeamsByGameName(gameName, teamName) // Fetch all teams
                teamsLD.postValue(allTeams) // Update LiveData with the fetched teams
                loadingLD.postValue(false)
            } catch (e: Exception) {
                // Handle errors and update LiveData
                teamsLoadErrorLD.postValue(true)
                loadingLD.postValue(false)
            }
        }
    }
}
