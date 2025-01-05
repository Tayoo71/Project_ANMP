package com.example.project_anmp.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

// Web Service
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
@Parcelize
data class Game(
    val name: String,
    val description: String,
    val urlLink: String,
) : Parcelable
@Parcelize
data class Achievement(
    val gameName: String,
    val year: String,
    val achievementDescription: String,
) : Parcelable


// SQLite
@Entity(
    tableName = "users",
    indices = [Index(value = ["username"], unique = true)] // Enforce unique constraint on username
)
data class User(
    @ColumnInfo(name = "first_name")
    var firstName:String,
    @ColumnInfo(name = "last_name")
    var lastName:String,
    @ColumnInfo(name = "username")
    var username:String,
    @ColumnInfo(name = "password")
    var password:String,
    var like: Boolean = false
){
    @PrimaryKey(autoGenerate = true)
    var uuid:Int =0
}

@Entity(tableName = "proposals")
data class Proposal(
    val game: String,
    val team: String,
    val reason: String,
    val username: String,
    val status: String
){
    @PrimaryKey(autoGenerate = true)
    var uuid:Int =0
}

@Entity(tableName = "games")
data class GameData(
    val name: String,
    val description: String,
    val urlLink: String,
){
    @PrimaryKey(autoGenerate = true)
    var uuid:Int =0
}

@Entity(tableName = "teams")
data class Team(
    val game: String,
    val name: String,
    val user: String,
){
    @PrimaryKey(autoGenerate = true)
    var uuid:Int =0
}

@Entity(
    tableName = "schedules",
)
@Parcelize
data class ScheduleData(
    val event: String,
    val game: String,
    val team: String,
    val datetime: String,
    val location: String,
    val venue_photo: String,
    val description: String
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var uuid:Int =0
}

@Entity(
    tableName = "likes",
)
data class LikeData(
    val like : Int,
){
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
}