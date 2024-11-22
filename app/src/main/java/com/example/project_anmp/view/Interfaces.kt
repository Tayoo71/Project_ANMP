package com.example.project_anmp.view

import android.view.View
import com.example.project_anmp.model.User

interface SignUpActionsHandler {
    fun onBackClicked(v: View)
    fun onSubmitClicked(obj:User, repeatPassword:String)
}

interface SignInActionsHandler{
    fun onSignUpClicked(v: View)
    fun onSubmitClicked(username: String, password: String)
}