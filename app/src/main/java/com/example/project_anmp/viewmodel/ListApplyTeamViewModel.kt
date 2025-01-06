package com.example.project_anmp.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.project_anmp.model.AppDatabase.Companion.buildDatabase
import com.example.project_anmp.model.Proposal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ListApplyTeamViewModel(application: Application)
    : AndroidViewModel(application), CoroutineScope {

    val proposalLD = MutableLiveData<List<Proposal>>()
    val proposalLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()
    private var job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun refresh() {
        loadingLD.value = true
        proposalLoadErrorLD.value = false
        launch {
            val db = buildDatabase(getApplication())
            try {
                val shared:SharedPreferences = getApplication<Application>().getSharedPreferences("com.example.project_anmp",
                    Context.MODE_PRIVATE)
                val username = shared.getString("username", null)
                if(username == "admin"){
                    val proposals = db.proposalDao().getAllProposalsAdmin()
                    proposalLD.postValue(proposals)
                }
                else{
                    val proposals = username?.let { db.proposalDao().getListProposal(it) }
                    proposalLD.postValue(proposals!!)
                }
                loadingLD.postValue(false)
            } catch (e: Exception) {
                // Handle the error and set the loadError LiveData
                proposalLoadErrorLD.postValue(true)
                loadingLD.postValue(false)
            }
        }
    }
}