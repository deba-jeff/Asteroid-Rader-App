package ukponahiunsijeffery.example.asteroidraderapp.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ukponahiunsijeffery.example.asteroidraderapp.domain.AsteroidDomain
import java.lang.IllegalArgumentException


class DetailViewModelFactory(private val asteroidDomain: AsteroidDomain,
                             private val application: Application): ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(DetailViewModel::class.java)){
            return DetailViewModel(asteroidDomain, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}
