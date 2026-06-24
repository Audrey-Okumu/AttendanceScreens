package com.example.attendancescreens.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.attendancescreens.screens.CalendarScreen
import com.example.attendancescreens.screens.DashboardScreen
import com.example.attendancescreens.screens.LaunchScreen
import com.example.attendancescreens.screens.LoginScreen
import com.example.attendancescreens.screens.ProfileScreen
import com.example.attendancescreens.screens.SignupScreen

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
            LaunchScreen(
                onSignupClick = {navController.navigate(SignupRoute)},
                onLoginClick = {navController.navigate(LoginRoute)}
            )
        }
        composable<HomeRoute> {
            DashboardScreen(onClick = { })
        }
        composable<CalendarRoute> {
            CalendarScreen()
        }
        composable<SignupRoute>{
            SignupScreen(
                onBackClick = { navController.navigate(LaunchRoute)},
                onLoginClick = {navController.navigate(LoginRoute)},
                onSignupSuccess = {
                    navController.navigate(HomeRoute) {
                        popUpTo(LaunchRoute) { inclusive = true }
                    }
                }
            )
        }
        composable<LoginRoute>{
            LoginScreen(
                onSignupClick = { navController.navigate(SignupRoute) },
                onLoginSuccess = {
                    navController.navigate(HomeRoute) {
                        popUpTo(LaunchRoute) { inclusive = true }
                    }
                }
            )
        }
        composable < ProfileRoute>{
            ProfileScreen(onClick = { })
        }
    }
}
