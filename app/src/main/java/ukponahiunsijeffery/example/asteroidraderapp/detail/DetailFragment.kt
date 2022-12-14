package ukponahiunsijeffery.example.asteroidraderapp.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import ukponahiunsijeffery.example.asteroidraderapp.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val application = requireNotNull(activity).application
        val binding = FragmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        // Gets the AsteroidDomain Object passed from the OverviewFragment
        val asteroidDomain = DetailFragmentArgs.fromBundle(requireArguments()).selectedProperty

        val viewModelFactory = DetailViewModelFactory(asteroidDomain, application)
        binding.viewModel = ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)
        return binding.root
    }



}