package com.sallyjayz.asteroidradar.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sallyjayz.asteroidradar.model.Asteroid

@Dao
interface AsteroidDao {

    @Query("SELECT * FROM asteroid_table WHERE close_approach_date = :today")
    fun getTodayAsteroids(today: String): List<Asteroid>

    @Query("SELECT * FROM asteroid_table WHERE close_approach_date >=:today ORDER BY close_approach_date ASC")
    fun getTodayOnwardAsteroid(today: String): List<Asteroid>

    /*@Query("SELECT * FROM asteroid_table WHERE close_approach_date >= :startDate AND close_approach_date <= :endDate ORDER BY close_approach_date ASC")
    fun getWeekAsteroids(startDate: String, endDate:String): List<Asteroid>*/

    @Query("SELECT * FROM asteroid_table WHERE close_approach_date BETWEEN :startDate AND :endDate ORDER BY close_approach_date ASC")
    fun getWeekAsteroids(startDate: String, endDate:String): List<Asteroid>


    @Query("SELECT * FROM asteroid_table ORDER BY close_approach_date ASC")
    fun getSavedAsteroid(): List<Asteroid>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(asteroids: Asteroid)
}