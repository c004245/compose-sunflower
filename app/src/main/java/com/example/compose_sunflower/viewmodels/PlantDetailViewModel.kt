package com.example.compose_sunflower.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.compose_sunflower.data.GardenPlantingRepository
import com.example.compose_sunflower.data.PlantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlantDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    plantRepository: PlantRepository,
    private val gardenPlantingRepository: GardenPlantingRepository
): ViewModel() {

    val plantId: String = savedStateHandle.get<String>(PLANT_ID_SAVED_STATE_KEY)!!

    val isPlanted = gardenPlantingRepository.isPlanted(plantId = plantId).asLiveData()
    val plant = plantRepository.getPlant(plantId = plantId).asLiveData()

    private val _showSnackbar = MutableLiveData(false)
    val showSnackbar: LiveData<Boolean>
        get() = _showSnackbar

    fun dismissSnackbar() {
        _showSnackbar.value = false
    }

    companion object {
        private const val PLANT_ID_SAVED_STATE_KEY = "plantId"
    }
}