package com.example.attendancescreens.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.attendancescreens.screens.CalendarScreen
import com.example.attendancescreens.screens.DashboardScreen
import com.example.attendancescreens.screens.LaunchScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = LaunchRoute,
        modifier = modifier
    ) {
        composable<LaunchRoute>{
            LaunchScreen(onClick = {})
        }
        composable<HomeRoute> {
            DashboardScreen(onClick = { })
        }
        composable<CalendarRoute> {
            CalendarScreen()
        }
    }
}
