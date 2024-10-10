package com.example.project_anmp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.project_anmp.model.Schedule
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken

class OurScheduleViewModel(application: Application): AndroidViewModel(application)  {
    val ourSchedulesLD = MutableLiveData<ArrayList<Schedule>>()
    val scheduleLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()

    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    // Fungsi untuk memuat data dari server (atau sumber data lainnya)
    fun refresh(){
        scheduleLoadErrorLD.value = false
        loadingLD.value = true

        val url = "https://raw.githubusercontent.com/Tayoo71/Project_ANMP/refs/heads/master/API_JSON/Schedules.json"

        try{
            queue = Volley.newRequestQueue(getApplication())
            val stringRequest = StringRequest(
                Request.Method.GET, url, { response ->
                    // Jika berhasil mengambil data
                    val sType = object : TypeToken<ArrayList<Schedule>>() {}.type
                    val result = Gson().fromJson<ArrayList<Schedule>>(response, sType)
                    ourSchedulesLD.value = result
                    loadingLD.value = false
                    Log.d("showVolley", result.toString())
                }, { error ->
                    // Jika gagal mengambil data
                    Log.d("showVolley", error.toString())
                    scheduleLoadErrorLD.value = true
                    loadingLD.value = false
                }
            )

            stringRequest.tag = TAG
            queue?.add(stringRequest)
        } catch (e: JsonSyntaxException) {
            // Handle single object or other response types
            Log.e("GsonError", "Error parsing JSON", e)
        }
    }

    // Fungsi ini akan dipanggil ketika ViewModel dihapus dari memori
    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }
}