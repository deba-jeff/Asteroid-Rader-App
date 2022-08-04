package ukponahiunsijeffery.example.asteroidraderapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AsteroidDatabaseDao {

    /**
     *  Selects and returns all the List of Asteroid in the Database
     */
    @Query("SELECT * FROM asteroid_table ORDER BY closeApproachDate ASC")
    fun getAsteroids(): LiveData<List<AsteroidDatabaseEntity>>


    /**
     *  Selects and returns the List of Asteroid for today
     */
    @Query("SELECT * FROM asteroid_table WHERE closeApproachDate = :todayDate")
    fun getTodayAsteroids(todayDate: String): LiveData<List<AsteroidDatabaseEntity>>


    /**
     *  Selects and returns the List of Asteroid for the week
     */
    @Query("SELECT * FROM asteroid_table WHERE closeApproachDate BETWEEN :todayDate AND :seventhDayDate ORDER BY closeApproachDate ASC ")
    fun getThisWeekAsteroid(todayDate: String, seventhDayDate: String): LiveData<List<AsteroidDatabaseEntity>>


    /**
     *  Inserts Asteroids into the database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroidEntities: AsteroidDatabaseEntity)


    /**
     *  Deletes the previous day Asteroid
     */
    @Query("DELETE FROM asteroid_table WHERE closeApproachDate = :previousDay")
    fun deletePreviousDayAsteroid(previousDay: String)

}