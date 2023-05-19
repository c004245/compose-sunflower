package com.example.compose_sunflower.viewmodels

import com.example.compose_sunflower.data.PlantAndGardenPlantings

class PlantAndGardenPlantingsViewModel(plantings: PlantAndGardenPlantings) {

    private val plant = checkNotNull(plantings.plant)
    private val gardenPlanting = plantings.gardenPlantings[0]



}