package com.example.compose_sunflower.viewmodels

import androidx.lifecycle.ViewModel
import com.example.compose_sunflower.data.GardenPlantingRepository
import com.example.compose_sunflower.data.PlantAndGardenPlantings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class GardenPlantingListViewModel @Inject internal constructor(
    gardenPlantingRepository: GardenPlantingRepository
): ViewModel() {
    val plantAndGardenPlantings: Flow<List<PlantAndGardenPlantings>> =
        gardenPlantingRepository.getPlantedGardens()

}