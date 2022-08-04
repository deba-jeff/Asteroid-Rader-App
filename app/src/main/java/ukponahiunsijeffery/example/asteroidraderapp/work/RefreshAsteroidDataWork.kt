package ukponahiunsijeffery.example.asteroidraderapp.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import ukponahiunsijeffery.example.asteroidraderapp.database.AsteroidDatabase
import ukponahiunsijeffery.example.asteroidraderapp.repository.AsteroidRepository
import retrofit2.HttpException
import java.text.SimpleDateFormat
import java.util.*

class RefreshAsteroidDataWork(appContext: Context, params: WorkerParameters): CoroutineWorker(appContext, params) {

    // Name to uniquely identify our Work
    companion object{
        const val WORK_NAME = "RefreshAsteroidDataWork"
    }


    /**
     *  Background work to be carried out
     */
    override suspend fun doWork(): Result {

        // Database instance
        val database = AsteroidDatabase.getInstance(applicationContext)

        // Repository instance
        val asteroidRepository = AsteroidRepository(database)

        return try {

            // Gets the next seven days Asteroid from the network and saves to the database
            asteroidRepository.refreshAsteroid()

            // Deletes the previous day Asteroid from the database
            asteroidRepository.deletePreviousDayAsteroid(getPreviousDay())

            // Returns success for a successful work
            Result.success()
        }
        catch (exception: HttpException){

            // Retry work in case of failure
            Result.retry()
        }
    }


    /**
     *  Returns the previous day Calender date
     */
    private fun getPreviousDay(): String{

        // Gets a calendar using the current time in the default time zone with the default FORMAT locale.
        val calendar = Calendar.getInstance()

        // Subtracts 1 day from the current time of the calendar
        // Returns the previous day Calender date
        calendar.add(Calendar.DAY_OF_MONTH, -1)

        // Returns a Date Object representing the Calendar's time value
        val previousDayTime = calendar.time

        // Constructs a date format
        val dateFormat = SimpleDateFormat("YYYY-MM-dd", Locale.getDefault())

        // Formats the Date Object into a date String
        val previousDayString = dateFormat.format(previousDayTime)

        return previousDayString
    }


}