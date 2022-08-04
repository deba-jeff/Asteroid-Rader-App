package ukponahiunsijeffery.example.asteroidraderapp.overview

import android.app.Application
import androidx.lifecycle.*
import ukponahiunsijeffery.example.asteroidraderapp.database.AsteroidDatabase
import ukponahiunsijeffery.example.asteroidraderapp.domain.AsteroidDomain
import ukponahiunsijeffery.example.asteroidraderapp.repository.AsteroidRepository
import kotlinx.coroutines.*
import ukponahiunsijeffery.example.asteroidraderapp.network.PictureOfTheDay


enum class AsteroidFilter {
    SHOW_TODAY_ASTEROIDS,
    SHOW_THIS_WEEK_ASTEROIDS,
    SHOW_SAVED_ASTEROIDS
}

enum class PictureOfTheDayStatus{
    LOADING,
    ERROR,
    DONE
}


class OverviewViewModel(application: Application): AndroidViewModel(application) {

    // LiveData to hold the result of the getPictureProperties query
    private val _pictureOfTheDay = MutableLiveData<PictureOfTheDay>()
    val pictureOfTheDay: LiveData<PictureOfTheDay>
        get() {
            return _pictureOfTheDay
        }


    // LiveData to hold the status of the result of the getPictureProperties query
    private val _pictureDataResultStatus = MutableLiveData<PictureOfTheDayStatus>()
    val pictureDataResultStatus: LiveData<PictureOfTheDayStatus>
        get() {
            return _pictureDataResultStatus
        }


    // LiveData to hold the clicked Asteroid
    private val _navigateToSelectedProperty = MutableLiveData<AsteroidDomain?>()
    val navigateToSelectedProperty: MutableLiveData<AsteroidDomain?>
        get() {
            return _navigateToSelectedProperty
        }


    /**
     * Updates the _navigateToSelectedProperty LiveData
     */
    fun displayPropertyDetails(asteroidDomain: AsteroidDomain){
        _navigateToSelectedProperty.value = asteroidDomain
    }


    /**
     *  Clears the _navigateToSelectedProperty LiveData to avoid triggering navigation after a
     *  configuration change
     */
    fun displayPropertyDetailsComplete(){
        _navigateToSelectedProperty.value = null
    }


    // Creates the Job
    private var viewModelJob = Job()

    // Creates the scope using the Main Dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    // Database instance
    private val database = AsteroidDatabase.getInstance(application)

    // Repository instance
    private val asteroidRepository = AsteroidRepository(database)


    /**
     *  LiveData to hold the AsteroidFilter
     *  Initialized as SHOW_SAVED_ASTEROIDS
     */
    private val _asteroidFilter = MutableLiveData<AsteroidFilter>(AsteroidFilter.SHOW_SAVED_ASTEROIDS)



    /**
     *  SwitchMap to transform the data from AsteroidFilter to a List of AsteroidDomain
     */
    val asteroids = Transformations.switchMap(_asteroidFilter) {asteroidFilterInput: AsteroidFilter ->
        when (asteroidFilterInput) {
            AsteroidFilter.SHOW_TODAY_ASTEROIDS -> asteroidRepository.getTodayAsteroidDomain()
            AsteroidFilter.SHOW_THIS_WEEK_ASTEROIDS -> asteroidRepository.getThisWeekAsteroidDomain()
            AsteroidFilter.SHOW_SAVED_ASTEROIDS -> asteroidRepository.getAllAsteroidDomain()
        }
    }


    /**
     * Updates the _asteroidFilter LiveData
     */
    fun updateFilter(asteroidFilter: AsteroidFilter) {
        _asteroidFilter.value = asteroidFilter
    }


    init {
        initializeNasaResultString()
    }

    /**
     *  Gets the next seven days Asteroid from the network, saves to the database and
     *  gets the picture of the day from the network
     */
    private fun initializeNasaResultString() {
        coroutineScope.launch {
            asteroidRepository.refreshAsteroid()
            getPictureOfTheDay()
        }
    }


    /**
     *  Gets the picture of the day from the network
     */
    private fun getPictureOfTheDay(){
        viewModelScope.launch {
            try {

                // Saves the status as LOADING
                _pictureDataResultStatus.value = PictureOfTheDayStatus.LOADING
                var getPictureObject = asteroidRepository.refreshPictureOfTheDay()

                // Saves the status as DONE
                _pictureDataResultStatus.value = PictureOfTheDayStatus.DONE
                _pictureOfTheDay.value = getPictureObject
            }
            catch (t: Throwable){

                // Resets _pictureOfTheDay LiveData to an empty Object
                _pictureOfTheDay.value = PictureOfTheDay("", "", "", "")

                // Saves the status as ERROR
                _pictureDataResultStatus.value = PictureOfTheDayStatus.ERROR
            }
        }
    }


    /**
     *  Cancels Job when ViewModel is destroyed
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}
