package com.sallyjayz.asteroidradar

import android.app.Application
import android.os.Build
import androidx.work.*
import com.sallyjayz.asteroidradar.worker.DeletePreviousData
import com.sallyjayz.asteroidradar.worker.FetchData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class AsteroidApplication: Application() {

    private val scopeApplication = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        scopeApplication.launch {
            val constraints = Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.UNMETERED)
                    .setRequiresCharging(true)
                    .setRequiresBatteryNotLow(true).apply {
                       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                           setRequiresDeviceIdle(true)
                       }
                    }.build()

            val fetchRequest = PeriodicWorkRequestBuilder<FetchData>(
                        1, TimeUnit.DAYS
                ).setConstraints(constraints).build()

                WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
                        "Refresh", ExistingPeriodicWorkPolicy.KEEP, fetchRequest
                )

            val deletePreviousRequest = PeriodicWorkRequestBuilder<DeletePreviousData>(
                1, TimeUnit.DAYS
            ).setConstraints(constraints).build()

            WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
                "Refresh", ExistingPeriodicWorkPolicy.KEEP, deletePreviousRequest
            )

        }
    }

}
