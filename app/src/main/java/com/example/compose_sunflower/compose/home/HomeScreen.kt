package com.example.compose_sunflower.compose.home

import android.widget.Toolbar
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.example.compose_sunflower.R
import com.example.compose_sunflower.compose.garden.GardenScreen
import com.example.compose_sunflower.data.Plant
import com.example.compose_sunflower.databinding.HomeScreenBinding
import kotlinx.coroutines.launch

enum class SunflowerPage(
    @StringRes val titleResId: Int,
    @DrawableRes val drawableResId: Int
) {
    MY_GARDEN(R.string.my_garden_title, R.drawable.ic_my_garden_active),
    PLANT_LIST(R.string.plant_list_title, R.drawable.ic_plant_list_active)
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onPlantClick: (Plant) -> Unit = {},
    onPageChange: (SunflowerPage) -> Unit = {},
    onAttached: (androidx.appcompat.widget.Toolbar) -> Unit = {},
) {
    val activity = (LocalContext.current as AppCompatActivity)

    AndroidViewBinding(factory = HomeScreenBinding::inflate, modifier = modifier) {
        onAttached(toolbar)
        activity.setSupportActionBar(toolbar)
        composeView.setContent {
            HomePagerScreen(onPlantClick = onPlantClick, onPageChange = onPageChange)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomePagerScreen(
    onPlantClick: (Plant) -> Unit,
    onPageChange: (SunflowerPage) -> Unit,
    modifier: Modifier = Modifier,
    pages: Array<SunflowerPage> = SunflowerPage.values()
) {
    val pagerState = rememberPagerState()

    LaunchedEffect(pagerState.currentPage) {
        onPageChange(pages[pagerState.currentPage])
    }

    Column(modifier.nestedScroll(rememberNestedScrollInteropConnection())) {
        val coroutineScope = rememberCoroutineScope()

        TabRow(selectedTabIndex = pagerState.currentPage) {
            pages.forEachIndexed { index, page -> 
                val title = stringResource(id = page.titleResId)
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                    text = { Text(text = title) },
                    icon = {
                        Icon(
                            painter = painterResource(id = page.drawableResId),
                            contentDescription = title
                        )
                    },
                    unselectedContentColor = androidx.compose.material.MaterialTheme.colors.primaryVariant,
                    selectedContentColor = androidx.compose.material.MaterialTheme.colors.secondary
                )
            }
        }

        HorizontalPager(pageCount = pages.size,
            state = pagerState,
            verticalAlignment = Alignment.Top
        ) { index ->
            when (pages[index]) {
                SunflowerPage.MY_GARDEN -> {
                    GardenScreen(gardenPlants = g)

                }
                SunflowerPage.PLANT_LIST -> {

                }
            }
        }
    }


}
