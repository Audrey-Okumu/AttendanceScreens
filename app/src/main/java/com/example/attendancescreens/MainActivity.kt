package com.example.attendancescreens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.attendancescreens.components.AttendanceBottomNav
import com.example.attendancescreens.components.AttendanceHeader
import com.example.attendancescreens.model.AttendanceNavigationItem
import com.example.attendancescreens.navigation.CalendarRoute
import com.example.attendancescreens.navigation.HomeRoute
import com.example.attendancescreens.navigation.LaunchRoute
import com.example.attendancescreens.navigation.LoginRoute
import com.example.attendancescreens.navigation.NavGraph
import com.example.attendancescreens.navigation.ProfileRoute
import com.example.attendancescreens.navigation.SignupRoute
import com.example.attendancescreens.ui.theme.AttendanceScreensTheme
import com.example.attendancescreens.ui.viewmodel.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AttendanceScreensTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    profileViewModel: ProfileViewModel = viewModel()
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val photoUrl by profileViewModel.photoUrl.collectAsState()
    val userName by profileViewModel.userName.collectAsState()

    val hideBarsRoutes = listOf(
        LaunchRoute::class,
        SignupRoute::class,
        LoginRoute::class
    )

    val showTopBar = hideBarsRoutes.none { currentDestination?.hasRoute(it) == true } &&
            currentDestination?.hasRoute(ProfileRoute::class) != true

    val showBottomBar = hideBarsRoutes.none { currentDestination?.hasRoute(it) == true }

    val items: List<AttendanceNavigationItem> = listOf(
        AttendanceNavigationItem(id = 1, name = "Home", icon = Icons.Default.Home),
        AttendanceNavigationItem(id = 2, name = "Calendar", icon = Icons.Filled.CalendarMonth),
        AttendanceNavigationItem(id = 3, name = "Profile", icon = Icons.Filled.AccountCircle)
    )

    val selectedState = remember { mutableStateOf(items[0]) }
    var selected by selectedState
        Scaffold(
            modifier = modifier
                .fillMaxSize(),
            topBar = {
                if (showTopBar) {
                    AttendanceHeader(
                        userName = userName,
                        photoUrl = photoUrl,
                        onIconClick = {
                            if (currentDestination?.hasRoute(ProfileRoute::class) != true) {
                                navController.navigate(ProfileRoute)
                            }
                        }
                    )
                }
            },
            bottomBar = {
                if (showBottomBar) {
                    AttendanceBottomNav(
                        navItems = items,
                        selected = selected,
                        onItemSelected = { navItem ->
                            selected = navItem
                            when (navItem.id) {
                                1 -> navController.navigate(HomeRoute)
                                2 -> navController.navigate(CalendarRoute)
                                3 -> navController.navigate(ProfileRoute)
                            }
                        }
                    )
                }
            }
        ) { innerPadding ->
            NavGraph(
                navController = navController,
                modifier = Modifier.padding(innerPadding )
            )
        }
    }


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    AttendanceScreensTheme {
        MainScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreviewDark() {
    AttendanceScreensTheme(darkTheme = true) {
        MainScreen()
    }
}
