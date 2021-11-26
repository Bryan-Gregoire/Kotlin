package g53735.mobg5.myapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Login::class], version = 1, exportSchema = false)
abstract class LoginDatabase : RoomDatabase(), LoginDatabaseDao {

    abstract val loginDatabaseDao: LoginDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: LoginDatabase? = null

        fun getInstance(context: Context): LoginDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        LoginDatabase::class.java,
                        "login_history_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}