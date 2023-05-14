package com.example.compose_sunflower.compose

import android.app.Activity
import android.widget.Toolbar
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose_sunflower.compose.home.HomeScreen
import com.example.compose_sunflower.compose.home.SunflowerPage


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

    }

}