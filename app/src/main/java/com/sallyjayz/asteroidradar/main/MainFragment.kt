package com.sallyjayz.asteroidradar.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sallyjayz.asteroidradar.R
import com.sallyjayz.asteroidradar.adapter.AsteroidAdapter
import com.sallyjayz.asteroidradar.adapter.AsteroidListener
import com.sallyjayz.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var adapter: AsteroidAdapter
    private lateinit var viewModel: MainViewModel

    /*private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }*/

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentMainBinding.inflate(inflater)


        val application = requireNotNull(this.activity).application

        val viewModelFactory = MainViewModelFactory(application)

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        adapter = AsteroidAdapter(AsteroidListener { asteroid ->
            viewModel.onAsteroidClicked(asteroid)
            Log.d("MainFragment", "onCreateView: $asteroid")

        })
        binding.asteroidRecycler.adapter = adapter

        viewModel.todayOnwardAsteroid.observe(viewLifecycleOwner, Observer { asteroid ->
            if (asteroid != null) {
                adapter.submitList(asteroid)
            }
         })

        viewModel.navigateToDetailFragment.observe(viewLifecycleOwner, { asteroid ->
            if (asteroid != null) {
                findNavController().navigate(MainFragmentDirections.actionShowDetail(asteroid))
            }
        })



        setHasOptionsMenu(true)

        return binding.root
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.next_week_asteroid_menu -> viewModel.weekAsteroid.observe(viewLifecycleOwner, Observer { asteroid ->
                if (asteroid != null) {
                    adapter.submitList(asteroid)
                }
            })
            R.id.today_asteroid_menu -> viewModel.todayAsteroid.observe(viewLifecycleOwner, Observer { asteroid ->
                if (asteroid != null) {
                    adapter.submitList(asteroid)
                }
            })
            R.id.saved_asteroid_menu -> viewModel.savedAsteroid.observe(viewLifecycleOwner, Observer { asteroid ->
                if (asteroid != null) {
                    adapter.submitList(asteroid)
                }
            })
            else -> viewModel.todayOnwardAsteroid.observe(viewLifecycleOwner, Observer { asteroid ->
                if (asteroid != null) {
                    adapter.submitList(asteroid)
                }
            })
        }
        return true
    }
}