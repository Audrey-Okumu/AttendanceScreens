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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.attendancescreens.components.AttendanceBottomNav
import com.example.attendancescreens.components.AttendanceHeader
import com.example.attendancescreens.components.CalendarRoute
import com.example.attendancescreens.components.HomeRoute
import com.example.attendancescreens.model.AttendanceNavigationItem
import com.example.attendancescreens.screens.CalendarScreen
import com.example.attendancescreens.screens.DashboardScreen
import com.example.attendancescreens.ui.theme.AttendanceScreensTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
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
fun MainScreen(modifier: Modifier = Modifier){
    val navController = rememberNavController()

    val items: List<AttendanceNavigationItem> = listOf(
        AttendanceNavigationItem(id = 1, name = "Home", icon = Icons.Default.Home),
        AttendanceNavigationItem(id = 2, name = "Calendar", icon = Icons.Filled.CalendarMonth),
        AttendanceNavigationItem(id = 3, name = "Profile", icon = Icons.Filled.AccountCircle)
    )

    val selectedState = remember { mutableStateOf(items[0]) }
    var selected by selectedState
    Scaffold(
        modifier= modifier.fillMaxSize(),
        topBar = {
            AttendanceHeader()
        },
        bottomBar = {
            AttendanceBottomNav(
                navItems = items,
                selected = selected,
                onItemSelected = { navItem ->
                    selected = navItem
                    when (navItem.id) {
                        1 -> navController.navigate(HomeRoute)
                        2 -> navController.navigate(CalendarRoute)
                    }
                }
            )
        }
        ){
        innerPadding ->
        NavHost(
            navController = navController,
            startDestination = HomeRoute,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<HomeRoute> {
                DashboardScreen(onClick = { })
            }
            composable<CalendarRoute> {
                CalendarScreen(

                )
            }
        }
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
