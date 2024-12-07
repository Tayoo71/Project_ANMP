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

@Dao
interface GameDao {
    // Insert a new game
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertGame(vararg game: GameData)

    // Get all games data
    @Query("SELECT * FROM games ORDER BY name ASC")
    fun getAllGames(): List<GameData>

    // Get all games names
    @Query("SELECT name FROM games ORDER BY name ASC")
    fun getAllGamesNames(): List<String>
}

@Dao
interface TeamDao{
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertTeam(vararg team: Team)

    // (Prevent multiple data or teams returned using DISTINCT)
    @Query("SELECT DISTINCT name from teams where game = :game ORDER BY name ASC")
    fun getTeamsByGame(game: String): List<String>
}

@Dao
interface ScheduleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSchedule(vararg schedule: ScheduleData)

    @Query("SELECT * FROM schedules ORDER BY datetime DESC")
    fun getAllSchedules(): List<ScheduleData>
}