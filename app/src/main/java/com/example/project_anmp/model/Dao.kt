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
    @Query("SELECT COUNT(*) FROM users WHERE username = :username")
    fun isUsernameTaken(username: String): Int

    // Query to get user data for sign-in validation
    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    fun getUserForSignIn(username: String, password: String): User?
}

@Dao
interface ProposalDao {
    // Insert a new proposal
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertProposal(vararg proposal: Proposal)

    // Get all proposals
    @Query("SELECT * FROM proposals ORDER BY uuid DESC")
    fun getAllProposals(): List<Proposal>
}