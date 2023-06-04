package com.example.compose_sunflower.data

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GardenPlantingRepository @Inject constructor(
    private val gardenPlantingDao: GardenPlantingDao
): ViewModel() {

    fun getPlantedGardens() = gardenPlantingDao.getPlantedGardens()


    fun isPlanted(plantId: String) =
        gardenPlantingDao.isPlanted(plantId = plantId)
}