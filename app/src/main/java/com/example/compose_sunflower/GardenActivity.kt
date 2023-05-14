package com.example.compose_sunflower

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.MenuProvider
import androidx.core.view.WindowCompat
import com.example.compose_sunflower.compose.SunflowerApp
import com.example.compose_sunflower.compose.home.SunflowerPage
import com.example.compose_sunflower.ui.theme.ComposesunflowerTheme
import com.google.accompanist.themeadapter.material.MdcTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GardenActivity : AppCompatActivity() {

    private val menuProvider = object: MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(R.menu.menu_plant_list, menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            return false
        }


    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            MdcTheme {
                SunflowerApp(
                    onAttached = { toolbar ->
                        setSupportActionBar(toolbar)
                    },
                    onPageChange = { page ->
                        when (page) {
                            SunflowerPage.MY_GARDEN -> removeMenuProvider(menuProvider)
                            SunflowerPage.PLANT_LIST -> addMenuProvider(
                                menuProvider, this
                            )
                        }
                    }
                )

            }
        }
    }
}