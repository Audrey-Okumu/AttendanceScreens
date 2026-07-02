package com.example.attendancescreens.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.attendancescreens.AuthManager
import com.example.attendancescreens.screens.CalendarScreen
import com.example.attendancescreens.screens.DashboardScreen
import com.example.attendancescreens.screens.LaunchScreen
import com.example.attendancescreens.screens.LoginScreen
import com.example.attendancescreens.screens.ProfileScreen
import com.example.attendancescreens.screens.SignupScreen
import kotlinx.coroutines.launch

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = LaunchRoute,
        modifier = modifier.fillMaxSize()
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
            val scope = rememberCoroutineScope()
            val context = LocalContext.current
            val authManager = remember { AuthManager(context) }
            ProfileScreen(onLogout = {
                scope.launch {
                    authManager.signOut()
                    navController.navigate(LaunchRoute) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            })
        }
    }
}
