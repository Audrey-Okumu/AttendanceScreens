package com.example.attendancescreens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.attendancescreens.model.*
import com.example.attendancescreens.ui.theme.AttendanceScreensTheme
import com.example.attendancescreens.ui.theme.AppDarkGreen
import com.example.attendancescreens.ui.theme.AppGoldAccent
import kotlinx.serialization.Serializable

@Serializable object HomeRoute
@Serializable object CalendarRoute

@Composable
fun AttendanceBottomNav(
    modifier: Modifier = Modifier,
    navItems: List<AttendanceNavigationItem>,
    selected: AttendanceNavigationItem,
    onItemSelected: (AttendanceNavigationItem) -> Unit = {}
) {

    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.background,
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(40.dp)),
            color = AppDarkGreen,
            contentColor = Color.White
        ) {

            NavigationBar(
                modifier = Modifier
                    .fillMaxWidth(),
                containerColor = Color.Transparent,
                contentColor = White,
                windowInsets = WindowInsets(10.dp, 4.dp, 10.dp, 0.dp)
            ) {
                for (navItem in navItems) {

                    val isSelected = navItem == selected

                    NavigationBarItem(
                        onClick = {
                            onItemSelected(navItem)
                        }, label = { },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = AppGoldAccent,
                            unselectedIconColor = Color.White
                        ),
                        icon = {
                            Row(
                                modifier = Modifier.padding(4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = navItem.icon,
                                    contentDescription = navItem.name
                                )

                                if (isSelected) {
                                    Text(
                                        text = navItem.name,
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        },
                        selected = isSelected,
                        alwaysShowLabel = false
                    )
                }

            }
        }
    }

}


@Preview
@Composable
private fun AttendanceBottomNavPrev() {
    AttendanceScreensTheme {
        val items = listOf(
            AttendanceNavigationItem(id = 1, name = "Home", icon = Icons.Default.Home),
            AttendanceNavigationItem(id = 2, name = "Calendar", icon = Icons.Filled.CalendarMonth),
            AttendanceNavigationItem(id = 3, name = "Profile", icon = Icons.Filled.AccountCircle)
        )

        var selected by remember {
            mutableStateOf(items[0])
        }


        AttendanceBottomNav(
            navItems = items,
            selected = selected,
            onItemSelected = {
                selected = it
            }
        )
    }
}
