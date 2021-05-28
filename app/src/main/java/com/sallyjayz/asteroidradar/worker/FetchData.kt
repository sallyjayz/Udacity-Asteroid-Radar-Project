package com.sallyjayz.asteroidradar.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.sallyjayz.asteroidradar.database.AsteroidDatabase
import com.sallyjayz.asteroidradar.repository.AsteroidRespository
import retrofit2.HttpException

class FetchData(context: Context, worker: WorkerParameters):
    CoroutineWorker(context, worker) {

    override suspend fun doWork(): Result {
        val database = AsteroidDatabase.getInstance(applicationContext)
        val repository = AsteroidRespository(database)

        return try {
            repository.insertAsteroids()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}