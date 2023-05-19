package com.example.compose_sunflower.compose.garden

import androidx.activity.compose.ReportDrawn
import androidx.activity.compose.ReportDrawnWhen
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme

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
import com.example.compose_sunflower.compose.card
import com.example.compose_sunflower.data.PlantAndGardenPlantings
import com.example.compose_sunflower.viewmodels.GardenPlantingListViewModel
import com.example.compose_sunflower.viewmodels.PlantAndGardenPlantingsViewModel


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
        GardenList()
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

@Composable
private fun GardenList(
    gardenPlants: List<PlantAndGardenPlantings>,
    onPlantClick: (PlantAndGardenPlantings) -> Unit,
    modifier: Modifier = Modifier
) {
    val gridState = rememberLazyGridState()
    ReportDrawnWhen { gridState.layoutInfo.totalItemsCount > 0 }
    LazyVerticalGrid(columns = GridCells.Fixed(2),
        modifier,
        state = gridState,
        contentPadding = PaddingValues(
            horizontal = dimensionResource(id = R.dimen.card_side_margin),
            vertical = dimensionResource(id = R.dimen.margin_normal)
        )
    ) {
        items(
            items = gardenPlants,
            key = { it.plant.plantId }
        ) {
            GardenListI
        }
    }
}

@Composable
private fun GardenListItem(
    plant: PlantAndGardenPlantings,
    onPlantClick: (PlantAndGardenPlantings) -> Unit
) {
    val vm = PlantAndGardenPlantingsViewModel(plant)

    val cardSideMargin = dimensionResource(id = R.dimen.card_side_margin)
    val marginNormal = dimensionResource(id = R.dimen.margin_normal)

    Card(
        onClick = { onPlantClick(plant) },
        modifier = Modifier.padding(
            start = cardSideMargin,
            end = cardSideMargin,
            bottom = dimensionResource(id = R.dimen.card_bottom_margin)
        ),
        elevation = dimensionResource(id = R.dimen.card_elevation),
        shape = MaterialTheme.shapes.card,
    ) {
        Column(Modifier.fillMaxWidth()) {
            SunflowerImage
        }
    }
        }
    )
}