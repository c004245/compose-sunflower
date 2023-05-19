package com.example.compose_sunflower.viewmodels

import com.example.compose_sunflower.data.PlantAndGardenPlantings
import java.text.SimpleDateFormat
import java.util.*

class PlantAndGardenPlantingsViewModel(plantings: PlantAndGardenPlantings) {

    private val plant = checkNotNull(plantings.plant)
    private val gardenPlanting = plantings.gardenPlantings[0]

    val imageUrl
        get() = plant.imageUrl

    val plantName
        get() = plant.name

    val plantId
        get() = plant.plantId

    val waterDateString: String = dateFormat.format(gardenPlanting.lastWateringDate.time)
    val wateringInterval
        get() = plant.wateringInterval
    val plantDateString: String = dateFormat.format(gardenPlanting.plantDate.time)


    companion object {
        private val dateFormat = SimpleDateFormat("MMM d, yyyy", Locale.US)
    }

}