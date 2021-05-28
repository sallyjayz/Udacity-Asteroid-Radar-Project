package com.sallyjayz.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.sallyjayz.asteroidradar.database.AsteroidDatabase
import com.sallyjayz.asteroidradar.model.Asteroid
import com.sallyjayz.asteroidradar.model.PictureOfDay
import com.sallyjayz.asteroidradar.repository.AsteroidRespository
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AsteroidDatabase.getInstance(application)
    private val asteroidRespository = AsteroidRespository(database)

    private var _todayOnwardAsteroid = MutableLiveData<List<Asteroid>>()
    val todayOnwardAsteroid : LiveData<List<Asteroid>>
        get() = _todayOnwardAsteroid

    private var _todayAsteroid = MutableLiveData<List<Asteroid>>()
    val todayAsteroid : LiveData<List<Asteroid>>
        get() = _todayAsteroid

    private var _weekAsteroid = MutableLiveData<List<Asteroid>>()
    val weekAsteroid : LiveData<List<Asteroid>>
        get() = _weekAsteroid

    private var _savedAsteroid = MutableLiveData<List<Asteroid>>()
    val savedAsteroid : LiveData<List<Asteroid>>
    get() = _savedAsteroid

    private var _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay:LiveData<PictureOfDay>
    get() = _pictureOfDay

    private val _navigateToDetailFragment = MutableLiveData<Asteroid>()
    val navigateToDetailFragment: LiveData<Asteroid>
        get() = _navigateToDetailFragment

    init {
        insertAsteroid()
        getTodayOnwardAsteroid()
        getPictureOfToday()
        getWeekAsteroid()
        getSavedAsteroid()
        getTodayAsteroid()
    }

    private fun insertAsteroid() {
        viewModelScope.launch {
            asteroidRespository.insertAsteroids()
            asteroidRespository.insertPictureOfDay()
        }
    }

    private fun getTodayOnwardAsteroid() {
        _todayOnwardAsteroid.value = asteroidRespository.getTodayOnwardAsteroid()
    }

    private fun getPictureOfToday() {
        _pictureOfDay.value = asteroidRespository.getPictureOfDay()
    }

    private fun getTodayAsteroid() {
        _todayAsteroid.value = asteroidRespository.getTodayAsteroid()
    }

    private fun getWeekAsteroid() {
        _weekAsteroid.value = asteroidRespository.getWeekAsteroid()
    }

    private fun getSavedAsteroid() {
        _savedAsteroid.value = asteroidRespository.getSavedAsteroids()
    }

    fun onAsteroidClicked(asteroid: Asteroid) {
        _navigateToDetailFragment.value = asteroid
    }

}
