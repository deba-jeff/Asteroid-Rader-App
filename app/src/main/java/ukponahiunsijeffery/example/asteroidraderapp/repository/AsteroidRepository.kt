package ukponahiunsijeffery.example.asteroidraderapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ukponahiunsijeffery.example.asteroidraderapp.database.AsteroidDatabase
import ukponahiunsijeffery.example.asteroidraderapp.database.asDomainModel
import ukponahiunsijeffery.example.asteroidraderapp.domain.AsteroidDomain
import ukponahiunsijeffery.example.asteroidraderapp.network.AsteroidApi
import ukponahiunsijeffery.example.asteroidraderapp.network.PictureApi
import ukponahiunsijeffery.example.asteroidraderapp.network.PictureOfTheDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ukponahiunsijeffery.example.asteroidraderapp.getNextSevenCalenderDates
import ukponahiunsijeffery.example.asteroidraderapp.network.asDatabaseModel
import ukponahiunsijeffery.example.asteroidraderapp.parseAsteroidJsonStringToAsteroidProperty


/**
 *  Repository Class
 */
class AsteroidRepository(private val database: AsteroidDatabase) {

    /**
     *  TODO: Replace DEMO_KEY with your api key
     */
    companion object{
        const val API_KEY = "DEMO_KEY"
    }


    /**
     *  Gets the entire List of Asteroids from the database and returns a List of
     *  AsteroidDomain using mapping
     */
    fun getAllAsteroidDomain(): LiveData<List<AsteroidDomain>> {
        return Transformations.map(database.asteroidDatabaseDao.getAsteroids()) {
            it.asDomainModel()
        }
    }


    /**
     *  Gets today List of Asteroids from the database and returns a List of
     *  AsteroidDomain using mapping
     */
    fun getTodayAsteroidDomain(): LiveData<List<AsteroidDomain>> {
        return Transformations.map(database.asteroidDatabaseDao.
        getTodayAsteroids(getNextSevenCalenderDates()[0])){
            it.asDomainModel()
        }
    }


    /**
     *  Gets this week List of Asteroids from the database and returns a List of
     *  AsteroidDomain using mapping
     */
    fun getThisWeekAsteroidDomain(): LiveData<List<AsteroidDomain>> {
        return Transformations.map(database.asteroidDatabaseDao.
        getThisWeekAsteroid(
            getNextSevenCalenderDates()[0],
            getNextSevenCalenderDates()[7])){
            it.asDomainModel()
        }
    }


    /**
     *  Gets the next seven days Asteroid from the network and saves to the database
     */
    suspend fun refreshAsteroid() {
        withContext(Dispatchers.IO) {
            try {

                // Creates and starts the network call on a background thread returning the Deferred
                var getPropertiesDeferred = AsteroidApi.retrofitService.getProperties(
                    getNextSevenCalenderDates()[0],
                    getNextSevenCalenderDates()[7],
                    API_KEY
                )

                // Returns the result from the network call when the value is ready
                var stringList = getPropertiesDeferred.await()

                // Returns an ArrayList of AsteroidProperty
                var asteroidList = parseAsteroidJsonStringToAsteroidProperty(stringList, 8)

                // Converts the ArrayList of AsteroidProperty to an ArrayList of AsteroidDatabase
                // and save to the database
                database.asteroidDatabaseDao.insertAll(*asteroidList.asDatabaseModel())
            }
            catch (e: Exception) {
                Log.i("Unable to Parse JSON : ", e.message!!)
            }
        }
    }


    /**
     *  Carries out the Network call and return PictureOfTheDay
     */
    suspend fun refreshPictureOfTheDay(): PictureOfTheDay {

        // Creates and starts the network call on a background thread returning the Deferred
        var getPictureDeferred = PictureApi.pictureRetrofitService.getPictureProperties(API_KEY)

        // Returns the result from the network call when the value is ready
        var pictureObject = getPictureDeferred.await()
        return pictureObject
    }



    /**
     *  Deletes the previous day Asteroid from the database
     */
    fun deletePreviousDayAsteroid(previousDayString: String){
        database.asteroidDatabaseDao.deletePreviousDayAsteroid(previousDayString)
    }


}

