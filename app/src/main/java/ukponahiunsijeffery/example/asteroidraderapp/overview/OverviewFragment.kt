package ukponahiunsijeffery.example.asteroidraderapp.overview

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ukponahiunsijeffery.example.asteroidraderapp.R
import ukponahiunsijeffery.example.asteroidraderapp.databinding.FragmentOverviewBinding


class OverviewFragment : Fragment() {

    private val viewModel: OverviewViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(this, OverviewViewModelFactory(activity.application))
            .get(OverviewViewModel::class.java)
    }

    private lateinit var binding: FragmentOverviewBinding
    private lateinit var adapter: AsteroidAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_overview, container, false)

        // Enable fragment_overview.xml direct access to variables/Objects in the OverviewViewModel
        binding.overviewViewModel = viewModel

        // Enable LiveData Observation
        binding.lifecycleOwner = viewLifecycleOwner

        // Set up Adapter and ClickListener
        adapter = AsteroidAdapter(AsteroidAdapter.OnClickListener {
            viewModel.displayPropertyDetails(it)
        })

        // Enable RecylclerView Adapter to use the created Adapter
        binding.nasaRecyclerviewId.adapter = adapter


        /**
         *  Observe for changes on the asteroids to update the RecyclerView with the latest data
         */
        viewModel.asteroids.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        /**
         *  Observe for changes on the navigateToSelectedProperty to trigger navigation to
         *  the DetailFragment passing in the clicked AsteroidDomain Object
         */
        viewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                this.findNavController().navigate(
                    OverviewFragmentDirections.actionOverviewFragmentToDetailFragment(it)
                )

                // Clears the LiveData to avoid triggering navigation after a configuration change
                viewModel.displayPropertyDetailsComplete()
            }
        })

        setHasOptionsMenu(true)
        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.today_asteroids -> viewModel.updateFilter(AsteroidFilter.SHOW_TODAY_ASTEROIDS)
            R.id.this_week_asteroids -> viewModel.updateFilter(AsteroidFilter.SHOW_THIS_WEEK_ASTEROIDS)
            R.id.saved_asteroids -> viewModel.updateFilter(AsteroidFilter.SHOW_SAVED_ASTEROIDS)
        }
        return true
    }

}