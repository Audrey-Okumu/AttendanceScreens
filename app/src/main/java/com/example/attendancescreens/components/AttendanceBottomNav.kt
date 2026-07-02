package com.example.attendancescreens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.attendancescreens.model.AttendanceNavigationItem
import com.example.attendancescreens.ui.theme.AppDarkGreen
import com.example.attendancescreens.ui.theme.AppGoldAccent
import com.example.attendancescreens.ui.theme.AttendanceScreensTheme

import androidx.compose.material3.MaterialTheme

@Composable
fun AttendanceBottomNav(
    modifier: Modifier = Modifier,
    navItems: List<AttendanceNavigationItem>,
    selected: AttendanceNavigationItem,
    onItemSelected: (AttendanceNavigationItem) -> Unit = {}
) {

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(32.dp)),
        color = MaterialTheme.colorScheme.primary,
       shadowElevation = 8.dp
    ) {

        NavigationBar(
            modifier = Modifier.fillMaxWidth(),
            containerColor = Color.Transparent,
        ) {
            for (navItem in navItems) {
                val isSelected = navItem == selected

                NavigationBarItem(
                    selected = isSelected,
                    onClick = { onItemSelected(navItem) },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = MaterialTheme.colorScheme.secondary,
                        unselectedIconColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),
                        selectedIconColor = MaterialTheme.colorScheme.onSecondary,
                        selectedTextColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    icon = {
                        Row(
                          //  modifier = Modifier.padding(4.dp),
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
                                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = Bold),
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                        }
                    },
                    alwaysShowLabel = false
                )
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
