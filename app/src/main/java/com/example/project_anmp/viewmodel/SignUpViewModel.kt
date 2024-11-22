package com.example.project_anmp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.project_anmp.model.AppDatabase.Companion.buildDatabase
import com.example.project_anmp.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.math.sign

class SignUpViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {
    private val job = Job()
    val signUpMessage = MutableLiveData<String>()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun signUp(user: User, repeatPassword: String) {
        if(user.password != repeatPassword){
            signUpMessage.postValue("Password and Repeat Password do not match!")
        }
        else {
            launch {
                val db = buildDatabase(getApplication())

                val isUsernameTaken = db.userDao().isUsernameTaken(user.username) > 0
                if (isUsernameTaken) {
                    signUpMessage.postValue("Username already exists!")
                } else {
                    db.userDao().insertUser(user)
                    signUpMessage.postValue("Sign-up successful!")
                }
            }
        }
    }
}


