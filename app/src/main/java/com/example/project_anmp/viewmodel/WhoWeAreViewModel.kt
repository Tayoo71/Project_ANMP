package com.example.project_anmp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.project_anmp.model.AppDatabase.Companion.buildDatabase
import com.example.project_anmp.model.LikeData
import com.example.project_anmp.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class WhoWeAreViewModel (application: Application): AndroidViewModel(application),
    CoroutineScope {

    val likeCountLD = MutableLiveData<LikeData>()
    val likeLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()
    val likeStatusLD = MutableLiveData<Boolean>()
    private var job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun refresh(){
        likeLoadErrorLD.value = false
        loadingLD.value = true

        launch {
            val db = buildDatabase(getApplication())
            try {
                val likes = db.likeDao().getLike()
                likeCountLD.postValue(likes)
                loadingLD.postValue(false)
            } catch (e: Exception) {
                // Handle the error and set the loadError LiveData
                likeLoadErrorLD.postValue(true)
                loadingLD.postValue(false)
            }
        }
    }

    fun increaseLike(username: String){
        likeLoadErrorLD.value = false
        loadingLD.value = true

        launch {
            val db = buildDatabase(getApplication())
            try {
                val likeData = db.likeDao().getLike()
                val newLikeCount = likeData.like + 1

                db.userDao().updateUserLikeStatus(username, true)
                db.likeDao().updateLike(likeData.id, newLikeCount)

                likeCountLD.postValue(likeData.copy(like = newLikeCount))

                loadingLD.postValue(false)
            } catch (e: Exception) {
                likeLoadErrorLD.postValue(true)
                loadingLD.postValue(false)
            }
        }
    }

    fun decreaseLike(username: String, ) {
        likeLoadErrorLD.value = false
        loadingLD.value = true

        launch {
            val db = buildDatabase(getApplication())
            try {
                val likeData = db.likeDao().getLike()
                val newLikeCount = if (likeData.like > 0) likeData.like - 1 else 0

                db.userDao().updateUserLikeStatus(username, false)
                db.likeDao().updateLike(likeData.id, newLikeCount)

                likeCountLD.postValue(likeData.copy(like = newLikeCount))

                loadingLD.postValue(false)
            } catch (e: Exception) {
                likeLoadErrorLD.postValue(true)
                loadingLD.postValue(false)
            }
        }
    }

    fun getLikeStatus(username: String){
        launch {
            val db = buildDatabase(getApplication())

            val likeStatus = db.userDao().getUserLike(username)

            likeStatusLD.postValue(likeStatus)
        }
    }
}