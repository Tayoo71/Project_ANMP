package com.example.project_anmp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Schedule(
    val id: Int,
    val event: String,
    val game: String,
    val team: String,
    val datetime: String,
    val location: String,
    val venue_photo: String,
    val description: String
) : Parcelable
