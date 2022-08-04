package ukponahiunsijeffery.example.asteroidraderapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AsteroidDatabaseEntity::class], version = 1, exportSchema = false)
abstract class AsteroidDatabase: RoomDatabase() {

    abstract val asteroidDatabaseDao: AsteroidDatabaseDao

    companion object{

        @Volatile
        private var INSTANCE: AsteroidDatabase? = null


        /**
         *  Return an instance of the AsteroidDatabase
         */
        fun getInstance(context: Context): AsteroidDatabase {

            synchronized(AsteroidDatabase::class.java){
                var instance = INSTANCE
                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AsteroidDatabase::class.java,

                        "asteroid_history_database"
                    )

                        // Allows Room to destructively recreate database tables if Migrations that would
                        // migrate old database schemas to the latest schema version are not found.
                        // This wipes and rebuild the database instead of migrating
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE =  instance
                }
                return instance
            }
        }
    }



}