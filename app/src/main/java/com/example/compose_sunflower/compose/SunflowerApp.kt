package com.example.compose_sunflower.compose

import android.app.Activity
import android.content.Intent
import android.widget.Toolbar
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ShareCompat
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.compose_sunflower.R
import com.example.compose_sunflower.compose.home.HomeScreen
import com.example.compose_sunflower.compose.home.SunflowerPage
import com.example.compose_sunflower.compose.plantdetail.PlantDetailsScreen


@Composable
fun SunflowerApp(
    onPageChange: (SunflowerPage) -> Unit = {},
    onAttached: (androidx.appcompat.widget.Toolbar) -> Unit = {},
) {
    val navController = rememberNavController()
    SunFlowerNavHost(
        navController = navController,
        onPageChange = onPageChange,
        onAttached = onAttached
    )
}

@Composable
fun SunFlowerNavHost(
    navController: NavHostController,
    onPageChange: (SunflowerPage) -> Unit = {},
    onAttached: (androidx.appcompat.widget.Toolbar) -> Unit = {},
) {
    val activity = (LocalContext.current as Activity)
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onPlantClick = {
                    navController.navigate("plantDetail/${it.plantId}")
                },
                onPageChange = onPageChange,
                onAttached = onAttached
            )
        }
        composable(
            "plantDetail/{plantId}",
            arguments = listOf(
                navArgument("plantId") {
                    type = NavType.StringType
                })
        ) {
            PlantDetailsScreen(
                onBackClick = { navController.navigateUp() },
                onShareClick = {
                    createShareIntent(activity, it)
                },
                onGalleryClick = {
                    navController.navigate("gallery/${it.name}")
                }
            )
        }
        composable(
            "gallery/{plantName}",
            arguments = listOf(navArgument("plantName") {
                type = NavType.StringType
            })
        ) {
            GalleryScreen(

            )
        }

    }

}

// Helper function for calling a share functionality.
// Should be used when user presses a share button/menu item.
private fun createShareIntent(activity: Activity, plantName: String) {
    val shareText = activity.getString(R.string.share_text_plant, plantName)
    val shareIntent = ShareCompat.IntentBuilder(activity)
        .setText(shareText)
        .setType("text/plain")
        .createChooserIntent()
        .addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
    activity.startActivity(shareIntent)
}