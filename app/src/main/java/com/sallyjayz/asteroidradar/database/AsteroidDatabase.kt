package com.sallyjayz.asteroidradar.database

import android.content.Context
import androidx.room.*
import com.sallyjayz.asteroidradar.Constants
import com.sallyjayz.asteroidradar.model.Asteroid
import com.sallyjayz.asteroidradar.model.PictureOfDay


@Database(entities = [Asteroid::class, PictureOfDay::class], version = 1, exportSchema = false)
abstract class AsteroidDatabase: RoomDatabase() {

    abstract val asteroidDao: AsteroidDao

    abstract val pictureOfDay: PictureOfDayDao

    companion object {

        @Volatile
        private var INSTANCE: AsteroidDatabase? = null

        fun getInstance(context: Context): AsteroidDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AsteroidDatabase::class.java,
                        Constants.DATABASE_NAME
                    ).fallbackToDestructiveMigration().allowMainThreadQueries()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}