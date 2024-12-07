package com.example.project_anmp.model

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [User::class, Proposal::class, GameData::class, Team::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun proposalDao(): ProposalDao
    abstract fun gameDao(): GameDao
    abstract fun teamDao(): TeamDao

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
                        username = "admin",
                        status = "WAITING"
                    ),
                    Proposal(
                        game = "Mobile Legends",
                        team = "Team B",
                        reason = "Experienced in jungle role.",
                        username = "guest",
                        status = "GRANTED"
                    )
                )
                db.gameDao().insertGame(
                    GameData(
                        name = "Valorant",
                        description = "Valorant is a tactical first-person shooter game developed by Riot Games. Players take on the roles of agents, each with unique abilities, in a 5v5 competitive format where strategic gameplay is key.",
                        urlLink = "https://staticg.sportskeeda.com/editor/2022/08/8fd46-16594963257131-1920.jpg"
                    ),
                    GameData(
                        name = "League of Legends",
                        description = "League of Legends (LoL) is a multiplayer online battle arena game where teams of champions compete to destroy the opposing team's Nexus. Players select from a roster of characters with different roles, abilities, and strategies.",
                        urlLink = "https://static.invenglobal.com/upload/image/2019/09/17/i1568742297124374.jpeg"
                    ),
                    GameData(
                        name = "Call of Duty",
                        description = "Call of Duty (CoD) is a popular first-person shooter franchise known for its intense combat scenarios, story-driven campaigns, and competitive multiplayer modes.",
                        urlLink = "https://assets-prd.ignimgs.com/2022/05/24/call-of-duty-modern-warfare-2-button-02-1653417394041.jpg"
                    ),
                    GameData(
                        name = "Dota 2",
                        description = "Dota 2 is a multiplayer online battle arena game where two teams of five players compete to destroy each other's Ancient, a large structure within their base. It features a diverse pool of heroes with unique abilities.",
                        urlLink = "https://pixelz.cc/wp-content/uploads/2018/10/dota-2-heroes-uhd-4k-wallpaper.jpg"
                    ),
                    GameData(
                        name = "Fortnite",
                        description = "Fortnite is a battle royale game where players compete to be the last person or team standing on a constantly shrinking map. It includes building mechanics, where players can construct structures to gain advantages during gameplay.",
                        urlLink = "https://cdn2.unrealengine.com/15br-bplaunch-egs-s3-2560x1440-2560x1440-687570387.jpg"
                    )
                )
                db.teamDao().insertTeam(
                    // Valorant Teams
                    Team(
                        game = "Valorant",
                        name = "Shadow Strike",
                        user = "AgentPhoenix"
                    ),
                    Team(
                        game = "Valorant",
                        name = "Shadow Strike",
                        user = "AgentViper"
                    ),
                    Team(
                        game = "Valorant",
                        name = "Vanguard",
                        user = "SniperX"
                    ),
                    Team(
                        game = "Valorant",
                        name = "Vanguard",
                        user = "BladeRunner"
                    ),

                    // League of Legends Teams
                    Team(
                        game = "League of Legends",
                        name = "Team A",
                        user = "ADCMaster"
                    ),
                    Team(
                        game = "League of Legends",
                        name = "Team A",
                        user = "MidLaneMage"
                    ),
                    Team(
                        game = "League of Legends",
                        name = "Team B",
                        user = "TankTop"
                    ),
                    Team(
                        game = "League of Legends",
                        name = "Team B",
                        user = "SupportWizard"
                    ),

                    // Call of Duty Teams
                    Team(
                        game = "Call of Duty",
                        name = "Team C",
                        user = "SniperElite"
                    ),
                    Team(
                        game = "Call of Duty",
                        name = "Team C",
                        user = "RifleCommander"
                    ),

                    // Dota 2 Teams
                    Team(
                        game = "Dota 2",
                        name = "Radiant Warriors",
                        user = "InvokerGod"
                    ),
                    Team(
                        game = "Dota 2",
                        name = "Radiant Warriors",
                        user = "CarryPlayer"
                    ),
                    Team(
                        game = "Dota 2",
                        name = "Dire Demons",
                        user = "SupportDemon"
                    ),
                    Team(
                        game = "Dota 2",
                        name = "Dire Demons",
                        user = "OfflaneKing"
                    ),

                    // Fortnite Teams
                    Team(
                        game = "Fortnite",
                        name = "Sky Builders",
                        user = "BuildMaster"
                    ),
                    Team(
                        game = "Fortnite",
                        name = "Sky Builders",
                        user = "FortKnight"
                    ),
                    Team(
                        game = "Fortnite",
                        name = "Battle Royals",
                        user = "SoloKing"
                    ),
                    Team(
                        game = "Fortnite",
                        name = "Battle Royals",
                        user = "SquadLeader"
                    )
                )
            }.start()
            Log.d("Database", "Default data inserted")
        }
    }
}
