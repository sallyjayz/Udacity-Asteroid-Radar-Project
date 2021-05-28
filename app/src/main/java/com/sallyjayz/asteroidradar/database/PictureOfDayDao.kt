package com.sallyjayz.asteroidradar.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sallyjayz.asteroidradar.model.PictureOfDay

@Dao
interface PictureOfDayDao {

    @Query("SELECT * FROM picture_table WHERE date = :today")
    fun getPictureOfDay(today: String): PictureOfDay

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(picture: PictureOfDay)

    @Query("DELETE FROM picture_table WHERE date < :today")
    fun deletePreviousPictureOfDay(today: String): Int
}