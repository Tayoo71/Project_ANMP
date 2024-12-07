package com.example.project_anmp.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.project_anmp.model.AppDatabase.Companion.buildDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class SignInViewModel (application: Application) : AndroidViewModel(application), CoroutineScope {
    private val job = Job()
    val signInMessage = MutableLiveData<String>()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    fun signIn(username: String, password: String) {
        launch {
            val db = buildDatabase(getApplication())
            val user = db.userDao().getUserForSignIn(username, password)
            if (user == null) {
                signInMessage.postValue("Invalid username or password!")
            } else {
                saveLoginData(user.uuid, username)
                signInMessage.postValue("Sign-In successful!")
            }
        }
    }

    private fun saveLoginData(uuid: Int, username: String) {
        var sharedFile = "com.example.project_anmp"
        var shared:SharedPreferences = getApplication<Application>().getSharedPreferences(sharedFile,
            Context.MODE_PRIVATE)
        var editor:SharedPreferences.Editor = shared.edit()
        editor.putInt("uuid", uuid)
        editor.putString("username", username)
        editor.putBoolean("isLoggedIn", true)
        editor.apply()
    }
}