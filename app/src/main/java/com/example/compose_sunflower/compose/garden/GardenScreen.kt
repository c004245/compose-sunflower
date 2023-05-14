package com.example.compose_sunflower.compose.garden

import androidx.activity.compose.ReportDrawn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.compose_sunflower.R
import com.example.compose_sunflower.data.PlantAndGardenPlantings
import com.example.compose_sunflower.viewmodels.GardenPlantingListViewModel

@Composable
fun GardenScreen(
    modifier: Modifier = Modifier,
    viewModel: GardenPlantingListViewModel = hiltViewModel(),
    onAddPlantClick: () -> Unit,
    onPlantClick: (PlantAndGardenPlantings) -> Unit
) {
    val gardenPlants by viewModel.plantAndGardenPlantings.collectAsState(initial = emptyList())
    GardenScreen(gardenPlants = gardenPlants,
        modonAddPlantClick = { /*TODO*/ }, onPlantClick = )

}

@Composable
fun GardenScreen(
    gardenPlants: List<PlantAndGardenPlantings>,
    modifier: Modifier = Modifier,
    onAddPlantClick: () -> Unit = {},
    onPlantClick: (PlantAndGardenPlantings) -> Unit = {}
) {
    if (gardenPlants.isEmpty()) {
        EmptyGarden(onAddPlantClick, modifier)
    } else {
        GardenList
    }
}

@Composable
private fun EmptyGarden(onAddPlantClick: () -> Unit, modifier: Modifier = Modifier) {
    ReportDrawn()

    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.garden_empty),
            style = androidx.compose.material.MaterialTheme.typography.h5
        )
        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = androidx.compose.material.MaterialTheme.colors.onPrimary),
            shape = RoundedCornerShape(
                topStart = 0.dp,
                topEnd = dimensionResource(id = R.dimen.button_corner_radius),
                bottomStart = dimensionResource(id = R.dimen.button_corner_radius),
                bottomEnd = 0.dp
            ),
            onClick = onAddPlantClick
        ) {
            Text(
                color = androidx.compose.material.MaterialTheme.colors.primary,
                text = stringResource(id = R.string.add_plant)
            )
        }
    }
}