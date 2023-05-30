package com.example.compose_sunflower.data

import android.content.Context
import androidx.databinding.adapters.Converters
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.compose_sunflower.utils.DATABASE_NAME
import com.example.compose_sunflower.utils.PLANT_DATA_FILENAME
import com.example.compose_sunflower.workers.SeedDatabaseWorker
import com.example.compose_sunflower.workers.SeedDatabaseWorker.Companion.KEY_FILENAME

/**
 * The Room Database for this app
 */

@Database(entities = [GardenPlanting::class, Plant::class], version = 1, exportSchema = false)
@TypeConverters(com.example.compose_sunflower.data.Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun plantDao(): PlantDao

    abstract fun gardenPlantingDao(): GardenPlantingDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(
                    object: Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>()
                                .setInputData(workDataOf(KEY_FILENAME to PLANT_DATA_FILENAME))
                                .build()
                            WorkManager.getInstance(context).enqueue(request)
                        }
                    }
                )
                .build()
        }
    }
}