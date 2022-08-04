package ukponahiunsijeffery.example.asteroidraderapp

import android.app.Application
import android.os.Build
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import ukponahiunsijeffery.example.asteroidraderapp.work.RefreshAsteroidDataWork
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


class AsteroidApplication: Application() {

    // Creates the scope using the Default Dispatcher
    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        launchWork()
    }


    private fun launchWork(){
        applicationScope.launch {
            setUpRecurringWork()
        }
    }

    /**
     *  Sets up the Work
     */
    private fun setUpRecurringWork(){

        // Constraints for the Work to run with
        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(true)
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    setRequiresDeviceIdle(true)
                }
            }
            .build()

        // WorkRequest which defines the conditions of the work
        val repeatingRequest = PeriodicWorkRequestBuilder<RefreshAsteroidDataWork>(
            1,
            TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

        // Creates an instance of WorkManager and schedule the work request
        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            RefreshAsteroidDataWork.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest)
    }


}