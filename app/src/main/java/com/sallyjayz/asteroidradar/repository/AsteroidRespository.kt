package com.sallyjayz.asteroidradar.repository

import android.util.Log
import com.sallyjayz.asteroidradar.Constants
import com.sallyjayz.asteroidradar.network.*
import com.sallyjayz.asteroidradar.database.AsteroidDatabase
import com.sallyjayz.asteroidradar.model.Asteroid
import com.sallyjayz.asteroidradar.model.PictureOfDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class AsteroidRespository(private val asteroidDatabase: AsteroidDatabase) {

    fun insertAsteroids(){
        AsteroidApi.retrofitService.getNearEarthObjects(getTodaysDate(), getSeventhDayDate(), Constants.API_KEY).enqueue(object:
            Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                for (neoAsteroid in parseAsteroidsJsonResult(JSONObject(response.body()))) {
                    asteroidDatabase.asteroidDao.insert(neoAsteroid)
                    Log.d("ResponseRepository"," ${parseAsteroidsJsonResult(JSONObject(response.body()))}")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Timber.d("Failure: ${t.message}")
            }

        })
    }

    fun getSavedAsteroids(): List<Asteroid> {
        return asteroidDatabase.asteroidDao.getSavedAsteroid()
    }

    fun getWeekAsteroid(): List<Asteroid> {
        return asteroidDatabase.asteroidDao.getWeekAsteroids(getTodaysDate(), getSeventhDayDate())
    }

    fun getTodayOnwardAsteroid(): List<Asteroid> {
        return asteroidDatabase.asteroidDao.getTodayOnwardAsteroid(getTodaysDate())
    }

    fun getTodayAsteroid(): List<Asteroid> {
        return asteroidDatabase.asteroidDao.getTodayAsteroids(getTodaysDate())
    }

    fun insertPictureOfDay() {
        AsteroidApi.retrofitService.getPictureOfDay(Constants.API_KEY).enqueue(object:
            Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {

                val pictureOfDay = parsePodJsonResult(JSONObject(response.body())).mediaType

                if (pictureOfDay == "image") {
                    asteroidDatabase.pictureOfDay.insert(parsePodJsonResult(JSONObject(response.body())))
                    Log.d("Response body2","${parsePodJsonResult(JSONObject(response.body()))}")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Timber.d("Failure: ${t.message}")
            }

        })
    }

    fun getPictureOfDay(): PictureOfDay {
        return asteroidDatabase.pictureOfDay.getPictureOfDay(getTodaysDate())
    }

    fun deletePreviousPicture() {
        asteroidDatabase.pictureOfDay.deletePreviousPictureOfDay(getTodaysDate())
    }

}