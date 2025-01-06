package com.example.project_anmp.model

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [User::class, Proposal::class, GameData::class, Team::class, ScheduleData::class, LikeData::class, AchievementData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun proposalDao(): ProposalDao
    abstract fun gameDao(): GameDao
    abstract fun teamDao(): TeamDao
    abstract fun scheduleDao(): ScheduleDao
    abstract fun likeDao(): LikeDao
    abstract fun achievementDao(): achievementDao

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
                    User(username = "admin", password = "admin", firstName = "Admin", lastName = "User", like = false),
                    User(username = "guest", password = "guest", firstName = "Guest", lastName = "User", like = false)
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
                db.scheduleDao().insertSchedule(
                    ScheduleData(
                        event = "Regional Qualifier - Valorant",
                        game = "Valorant",
                        team = "Team A",
                        datetime = "2023-09-05T10:00:00",
                        location = "Los Angeles, CA",
                        venue_photo = "https://live.staticflickr.com/65535/52699161503_fe86be5a4d_h.jpg",
                        description = "This high-stakes event will bring together top teams from across the region, all competing for a chance to advance to the national finals. Expect intense gameplay, strategic plays, and thrilling moments as teams battle it out in one of the most popular first-person shooters."
                    ),
                    ScheduleData(
                        event = "League of Legends Workshop",
                        game = "LOL",
                        team = "Team C",
                        datetime = "2023-09-10T11:00:00",
                        location = "San Francisco, CA",
                        venue_photo = "https://steamuserimages-a.akamaihd.net/ugc/941709610193363954/52EC15F168C8605FF7C0CC8F1C77793F9DF63B6C/?imw=5000&imh=5000&ima=fit&impolicy=Letterbox&imcolor=%23000000&letterbox=false",
                        description = "Join this comprehensive workshop where professionals share strategies and tips to dominate the Summoner's Rift in League of Legends. Perfect for players looking to improve their skills and teamwork."
                    ),
                    ScheduleData(
                        event = "Call of Duty Championship",
                        game = "COD",
                        team = "Team A",
                        datetime = "2023-10-07T12:00:00",
                        location = "New York, NY",
                        venue_photo = "https://ik.imagekit.io/0eqydxstn/Call_of_Duty-_Competitive_History.jpeg",
                        description = "Top teams from across the globe will face off in the Call of Duty Championship. Watch the intense competition as teams fight for glory in this fast-paced and tactical shooter game."
                    ),
                    ScheduleData(
                        event = "Dota 2 Livestream",
                        game = "Dota 2",
                        team = "Team B",
                        datetime = "2023-11-11T14:00:00",
                        location = "Online",
                        venue_photo = "https://asset-2.tstatic.net/banjarmasin/foto/bank/images/live-streaming-dota-2-kkg-vs-ugm-liga-game-indonesia-bersama-coki-muslim.jpg",
                        description = "Witness Team B as they showcase their mastery of Dota 2 in an exclusive online livestream event, complete with expert commentary and live audience interactions."
                    ),
                    ScheduleData(
                        event = "Fortnite Invitational",
                        game = "Fortnite",
                        team = "Team A",
                        datetime = "2023-12-04T15:00:00",
                        location = "Las Vegas, NV",
                        venue_photo = "https://cdn-0001.qstv.on.epicgames.com/wnXXbcizlQjNMUrMAJ/image/landscape_comp.jpeg",
                        description = "Watch the best Fortnite players compete in this invitational event featuring high-level gameplay, crazy builds, and intense battles to be the last one standing."
                    )
                )
                db.likeDao().insertLike(
                    LikeData(
                        like = 0
                    )
                )
                db.achievementDao().insertAchievement(
                    AchievementData(
                        gameName = "Valorant",
                        year = "2022",
                        achievementDescription = "Spring Valorant Cup (2022) - Team A: Flawless Victory"
                    ),
                    AchievementData(
                        gameName = "Valorant",
                        year = "2023",
                        achievementDescription = "Winter Valorant Clash (2023) - Team B: Dominant Performance"
                    ),
                    AchievementData(
                        gameName = "Valorant",
                        year = "2023",
                        achievementDescription = "Valorant Elite Series (2023) - Team C: MVP Award"
                    ),
                    AchievementData(
                        gameName = "Valorant",
                        year = "2024",
                        achievementDescription = "International Valorant Masters (2024) - Team A: Runner-Up"
                    ),
                    AchievementData(
                        gameName = "Valorant",
                        year = "2024",
                        achievementDescription = "Valorant Pro Defense Award (2024) - Team B: Best Defensive Play"
                    ),
                    AchievementData(
                        gameName = "League of Legends",
                        year = "2022",
                        achievementDescription = "Summer LoL Invitational (2022) - Team A: Flawless Run"
                    ),
                    AchievementData(
                        gameName = "League of Legends",
                        year = "2023",
                        achievementDescription = "LoL Global Championship (2023) - Team B: Grand Winner"
                    ),
                    AchievementData(
                        gameName = "League of Legends",
                        year = "2023",
                        achievementDescription = "International LoL Showdown (2023) - Team C: Top 8 Finish"
                    ),
                    AchievementData(
                        gameName = "League of Legends",
                        year = "2024",
                        achievementDescription = "LoL Star MVP (2024) - Team A: Outstanding Player"
                    ),
                    AchievementData(
                        gameName = "League of Legends",
                        year = "2024",
                        achievementDescription = "LoL Best Defensive Team Award (2024) - Team B"
                    ),
                    AchievementData(
                        gameName = "Call of Duty",
                        year = "2022",
                        achievementDescription = "Call of Duty Summer Season (2022) - Team C: Unbeaten"
                    ),
                    AchievementData(
                        gameName = "Call of Duty",
                        year = "2023",
                        achievementDescription = "Regional COD Masters (2023) - Team A: First Place"
                    ),
                    AchievementData(
                        gameName = "Call of Duty",
                        year = "2023",
                        achievementDescription = "COD World Series (2023) - Team B: Top 6 Performance"
                    ),
                    AchievementData(
                        gameName = "Call of Duty",
                        year = "2024",
                        achievementDescription = "COD MVP Award (2024) - Team C: Star Player"
                    ),
                    AchievementData(
                        gameName = "Call of Duty",
                        year = "2024",
                        achievementDescription = "COD Defense Champions (2024) - Team A"
                    ),
                    AchievementData(
                        gameName = "Dota 2",
                        year = "2022",
                        achievementDescription = "Summer Dota Open (2022) - Team B: Unstoppable"
                    ),
                    AchievementData(
                        gameName = "Dota 2",
                        year = "2022",
                        achievementDescription = "The International 11 (2022) - Team C: Top 5 Finish"
                    ),
                    AchievementData(
                        gameName = "Dota 2",
                        year = "2023",
                        achievementDescription = "Regional Dota 2 League (2023) - Team A: Champions"
                    ),
                    AchievementData(
                        gameName = "Dota 2",
                        year = "2024",
                        achievementDescription = "Dota 2 Most Valuable Player (2024) - Team B"
                    ),
                    AchievementData(
                        gameName = "Dota 2",
                        year = "2024",
                        achievementDescription = "Dota 2 Defense Masters (2024) - Team C"
                    ),
                    AchievementData(
                        gameName = "Fortnite",
                        year = "2022",
                        achievementDescription = "Fortnite Winter Royale (2022) - Team A: Flawless Win"
                    ),
                    AchievementData(
                        gameName = "Fortnite",
                        year = "2022",
                        achievementDescription = "Fortnite Regional Clash (2022) - Team B: Top 10 Finish"
                    ),
                    AchievementData(
                        gameName = "Fortnite",
                        year = "2023",
                        achievementDescription = "Fortnite Championship Series (2023) - Team C: Victors"
                    ),
                    AchievementData(
                        gameName = "Fortnite",
                        year = "2024",
                        achievementDescription = "Fortnite MVP Award (2024) - Team A"
                    ),
                    AchievementData(
                        gameName = "Fortnite",
                        year = "2024",
                        achievementDescription = "Fortnite Defense Excellence Award (2024) - Team B"
                    )
                )
            }.start()
            Log.d("Database", "Default data inserted")
        }
    }
}
