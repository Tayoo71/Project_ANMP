package com.example.project_anmp.model

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [User::class, Proposal::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun proposalDao(): ProposalDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(LOCK) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }

        fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "app_database"
            )
                .fallbackToDestructiveMigration()
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        insertDefaultData(context)
                    }
                })
                .build()

        private fun insertDefaultData(context: Context) {
            // Populate the database with default data in a background thread
            Thread {
                val db = getInstance(context)
                db.userDao().insertUser(
                    User(username = "admin", password = "admin", firstName = "Admin", lastName = "User"),
                    User(username = "guest", password = "guest", firstName = "Guest", lastName = "User")
                )
                db.proposalDao().insertProposal(
                    Proposal(
                        game = "League of Legends",
                        team = "Team A",
                        reason = "I am skilled in ADC role.",
                        status = "WAITING"
                    ),
                    Proposal(
                        game = "Mobile Legends",
                        team = "Team B",
                        reason = "Experienced in jungle role.",
                        status = "GRANTED"
                    )
                )
            }.start()
            Log.d("Database", "Default data inserted")
        }
    }
}
