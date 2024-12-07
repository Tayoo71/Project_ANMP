package com.example.project_anmp.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.project_anmp.model.AppDatabase.Companion.buildDatabase
import com.example.project_anmp.model.Proposal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class NewApplyTeamViewModel(application: Application)
    : AndroidViewModel(application), CoroutineScope {
    private var job = Job()
    val statusMessage = MutableLiveData<String>()
    val gamesDataLD = MutableLiveData<List<String>>()
    val teamsDataLD = MutableLiveData<List<String>>()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun applyTeam(game: String, teamName: String, description: String){
        if (game.isBlank() || teamName.isBlank() || description.isBlank() || game == "Choose Game" || teamName == "Choose Team"){
            statusMessage.postValue("Please Fill All Data Required!")
        }
        else{
            launch {
                val db = buildDatabase(getApplication())
                var sharedFile = "com.example.project_anmp"
                var shared: SharedPreferences = getApplication<Application>().getSharedPreferences(sharedFile,
                    Context.MODE_PRIVATE)
                val username = shared.getString("username", "")
                db.proposalDao().insertProposal(Proposal(game, teamName, description,
                    username.toString(), "WAITING"))
                statusMessage.postValue("Proposal Apply Team Successfully Added!")
            }
        }
    }

    fun fetchGames(){
        launch {
            try {
                val db = buildDatabase(getApplication())
                gamesDataLD.postValue(db.gameDao().getAllGamesNames())
            } catch (e: Exception) {
                statusMessage.postValue("Error while Fetch Games Data")
            }
        }
    }

    fun fetchTeamsForGame(game: String) {
        launch {
            try {
                val db = buildDatabase(getApplication())
                teamsDataLD.postValue(db.teamDao().getTeamsByGame(game))
            } catch (e: Exception) {
                statusMessage.postValue("Error while Fetch Teams Data")
            }
        }
    }
}