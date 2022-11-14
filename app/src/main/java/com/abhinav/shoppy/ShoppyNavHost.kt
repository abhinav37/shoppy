package com.abhinav.shoppy

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.abhinav.shoppy.common.navigation.Details
import com.abhinav.shoppy.common.navigation.Details.ARG_PRODUCT_ID
import com.abhinav.shoppy.common.navigation.Home
import com.abhinav.shoppy.features.details.DetailsScreen
import com.abhinav.shoppy.features.details.DetailsViewModel
import com.abhinav.shoppy.features.details.DetailsViewModelImpl
import com.abhinav.shoppy.features.home.HomeScreen
import com.abhinav.shoppy.features.home.HomeViewModel
import com.abhinav.shoppy.features.home.HomeViewModelImpl

/*
* Navigation Host for both the screens.
* If in future we need to scale with more screens we should split this into each
* screens Navigation class
*/

@Composable
fun ShoppyNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Home.route,
        modifier = modifier
    ) {
        composable(route = Home.route) { backStackEntry ->
            val homeViewModel: HomeViewModel = hiltViewModel<HomeViewModelImpl>(backStackEntry)
            HomeScreen(
                onItemClick = { id ->
                    navController.navigateSingleTopTo("${Details.route}/$id")
                },
                viewModel = homeViewModel
            )
        }
        composable(
            route = Details.route + "/{" + ARG_PRODUCT_ID + "}",
            arguments = listOf(
                navArgument(ARG_PRODUCT_ID) {
                    type = NavType.IntType
                    nullable = false
                }
            )
        ) { backStackEntry ->
            val detailsViewModel: DetailsViewModel =
                hiltViewModel<DetailsViewModelImpl>(backStackEntry)
            DetailsScreen(
                onBackClick = navController::navigateUp,
                viewModel = detailsViewModel
            )
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
