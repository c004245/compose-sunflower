package com.example.compose_sunflower.compose.plantdetail

import androidx.annotation.VisibleForTesting
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.compose_sunflower.R
import com.example.compose_sunflower.compose.Dimens
import com.example.compose_sunflower.compose.utils.TextSnackbarContainer
import com.example.compose_sunflower.data.Plant
import com.example.compose_sunflower.viewmodels.PlantDetailViewModel


data class PlantDetailsCallbacks(
    val onFabClick: () -> Unit,
    val onBackClick: () -> Unit,
    val onShareClick: (String) -> Unit,
    val onGalleryClick: (Plant) -> Unit
)
@Composable
fun PlantDetailsScreen(
    plantDetailsViewModel: PlantDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onShareClick: (String) -> Unit,
    onGalleryClick: (Plant) -> Unit,
) {

    val plant = plantDetailsViewModel.plant.observeAsState().value
    val isPlanted = plantDetailsViewModel.isPlanted.observeAsState().value
    val showSnackbar = plantDetailsViewModel.showSnackbar.observeAsState().value

    if (plant != null && isPlanted != null && showSnackbar != null) {
        Surface {
            TextSnackbarContainer(
                snackbarText = stringResource(R.string.added_plant_to_garden),
                showSnackbar = showSnackbar,
                onDismissSnackbar = { plantDetailsViewModel.dismissSnackbar()} )
            {
             PlantDetails(


             )
            }
        }
    }

}


@VisibleForTesting
@Composable
fun PlantDetails(
    plant: Plant,
    isPlanted: Boolean,
    hasValidUnsplashKey: Boolean,
    callbacks: PlantDetailsCallbacks,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    var plantScroller by remember {
        mutableStateOf(PlantDetailsScroller(scrollState, Float.MIN_VALUE))
    }

    val transitionState =
        remember(plantScroller) { plantScroller.toolbarTransitionState}

    val toolbarState = plantScroller.getToolbarState(LocalDensity.current)

    val transition = updateTransition(transitionState, label = "")
    val toolbarAlpha = transition.animateFloat(
        transitionSpec = { spring(stiffness = Spring.StiffnessLow) }, label = ""
    ) { toolbarTransitionState ->
        if (toolbarTransitionState == ToolbarState.HIDDEN) 0f else 1f
    }

    val contentAlpha = transition.animateFloat(
        transitionSpec = { spring(stiffness = Spring.StiffnessLow) }, label = ""
    ) {
        toolbarTransitionState ->
        if (toolbarTransitionState == ToolbarState.HIDDEN) 1f else 0f
    }

    val toolbarHeightPx = with(LocalDensity.current) {
        Dimens.PlantDetailAppBarHeight.roundToPx().toFloat()
    }

    val toolbarOffsetHeightPx = remember { mutableStateOf(0f) }
    val nestedScrollConnection = remember {
        object: NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = toolbarOffsetHeightPx.value + delta
                toolbarOffsetHeightPx.value =
                    newOffset.coerceIn(-toolbarHeightPx, 0f)
                return Offset.Zero

            }
        }
    }

    Box(modifier.fillMaxSize().systemBarsPadding().nestedScroll(nestedScrollConnection)) {
        PlantDetaisl
    }


}

@Composable
private fun PlantDetailsContent(
    

)