package com.example.project_anmp.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface UserDao {
    // Insert user(s) into the database,
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertUser(vararg user: User)

    // Query to check if a username already exists
    @Query("SELECT COUNT(*) FROM User WHERE username = :username")
    fun isUsernameTaken(username: String): Int

    // Query to get user data for sign-in validation
    @Query("SELECT * FROM User WHERE username = :username AND password = :password")
    fun getUserForSignIn(username: String, password: String): User?
}
