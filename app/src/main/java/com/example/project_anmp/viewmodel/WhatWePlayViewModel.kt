package com.example.project_anmp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.project_anmp.model.Game
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken

class WhatWePlayViewModel(application: Application) : AndroidViewModel(application) {
    val whatWePlayLD = MutableLiveData<ArrayList<Game>>()
    val playLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()

    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    // Function to refresh and load data from the server
    fun refresh() {
        playLoadErrorLD.value = false
        loadingLD.value = true

        val url = "https://raw.githubusercontent.com/Tayoo71/Project_ANMP/refs/heads/master/API_JSON/Games.json"

        try {
            queue = Volley.newRequestQueue(getApplication())
            val stringRequest = StringRequest(
                Request.Method.GET, url, { response ->
                    // Parse JSON response into a list of Game objects
                    val gType = object : TypeToken<ArrayList<Game>>() {}.type
                    val result = Gson().fromJson<ArrayList<Game>>(response, gType)
                    whatWePlayLD.value = result
                    loadingLD.value = false
                    Log.d("showVolley", result.toString())
                }, { error ->
                    // Handle error response
                    Log.d("showVolley", error.toString())
                    playLoadErrorLD.value = true
                    loadingLD.value = false
                }
            )

            stringRequest.tag = TAG
            queue?.add(stringRequest)
        } catch (e: JsonSyntaxException) {
            // Handle JSON parsing errors
            Log.e("GsonError", "Error parsing JSON", e)
        }
    }

    // Function to cancel requests when ViewModel is cleared
    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }
}
