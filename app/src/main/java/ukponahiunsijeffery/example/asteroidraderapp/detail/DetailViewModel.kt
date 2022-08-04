package ukponahiunsijeffery.example.asteroidraderapp.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ukponahiunsijeffery.example.asteroidraderapp.domain.AsteroidDomain

class DetailViewModel(asteroidDomain: AsteroidDomain, app: Application) : AndroidViewModel(app) {

    private val _selectedProperty = MutableLiveData<AsteroidDomain>()
    val selectedProperty: LiveData<AsteroidDomain>
        get() {
            return _selectedProperty
        }

    init {

        // Initialize LiveData with the AsteroidDomain passed from the DetailViewModel
        _selectedProperty.value = asteroidDomain
    }

}