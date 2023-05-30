package com.example.compose_sunflower.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.compose_sunflower.data.AppDatabase
import com.example.compose_sunflower.data.Plant
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class SeedDatabaseWorker(
    context: Context,
    workerParameters: WorkerParameters
): CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val filename = inputData.getString(KEY_FILENAME)
            if (filename != null) {
                applicationContext.assets.open(filename).use { inputStream ->
                    com.google.gson.stream.JsonReader(inputStream.reader()).use { jsonReader ->
                        val plantType = object: TypeToken<List<Plant>>() {}.type
                        val plantList: List<Plant> = Gson().fromJson(jsonReader, plantType)

                        val database = AppDatabase.getInstance(applicationContext)
                        database.plantDao().insertAll(plantList)

                        Result.success()
                    }
                }
            } else {
                Result.failure()
            }
        } catch (ex: Exception) {
            Result.failure()
        }
    }

    companion object {
        const val KEY_FILENAME = "PLANT_DATA_FILENAME"
    }
}