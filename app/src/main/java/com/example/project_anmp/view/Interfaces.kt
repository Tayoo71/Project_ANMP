package com.example.project_anmp.view

import android.view.View
import android.widget.Button
import android.widget.Spinner
import com.example.project_anmp.model.GameData
import com.example.project_anmp.model.ScheduleData
import com.example.project_anmp.model.User

interface SignUpActionsHandler {
    fun onBackClicked(v: View)
    fun onSubmitClicked(obj:User, repeatPassword:String)
}

interface SignInActionsHandler{
    fun onSignUpClicked(v: View)
    fun onSubmitClicked(username: String, password: String)
}

interface ApplyTeamActionsHandler{
    fun onFabAddClicked(v: View)
    fun onRefresh()
}

interface NewApplyTeamActionsHandler{
    fun onSubmitClicked(game: Spinner, team: Spinner, description: String)
}

interface OurScheduleListActionsHandler {
    fun onViewClicked(v: View, schedule: ScheduleData)
}

interface ScheduleDetailActionsHandler{
    fun buttonNotifyClicked(v: View)
}

interface WhatWePlayListActionHandler {
    fun onButtonAchievementClicked(v: View, game: GameData)
    fun onButtonTeamClicked(v: View, game: GameData)
}