package com.example.attendancescreens.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.attendancescreens.components.StatusItem
import com.example.attendancescreens.model.StatusData
import com.example.attendancescreens.ui.theme.AppDarkGreen
import com.example.attendancescreens.ui.theme.AppLightGray
import com.example.attendancescreens.ui.theme.AppTextGray
import java.text.SimpleDateFormat
import java.util.Date


@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    val locale = LocalConfiguration.current.locales[0]

    var isCheckedIn by remember { mutableStateOf(false) }
    var checkInTime by remember { mutableStateOf("--:--") }
    var checkOutTime by remember { mutableStateOf("--:--") }

    var checkInMillis by remember {mutableStateOf<Long?>(null)}
    var checkOutMillis by remember {mutableStateOf<Long?>(null)}

    val totalHours =
        if (checkInMillis != null && checkOutMillis != null) {

            val diffMillis = checkOutMillis!! - checkInMillis!!

            val hours = diffMillis / (1000 * 60 * 60)
            val minutes = (diffMillis / (1000 * 60)) % 60

            "${hours}h ${minutes}m"
        } else {
            "--:--"
        }
    val currentDate = SimpleDateFormat("MMMM dd, yyyy - EEEE", locale).format(Date())

    val statusItems = listOf(
        StatusData(
            icon = Icons.AutoMirrored.Filled.Login,
            time = checkInTime,
            label = "Check In"
        ),
        StatusData(
            icon = Icons.AutoMirrored.Filled.Logout,
            time = checkOutTime,
            label = "Check Out"
        ),
        StatusData(
            icon = Icons.Default.History,
            time = totalHours,
            label = "Total Hrs"
        )
    )

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = SimpleDateFormat("HH:mm",locale).format(Date()),
                    fontSize = 60.sp
                )
                Text(
                    text = currentDate,
                    color = AppTextGray
                )
                Spacer(modifier = Modifier.height(60.dp))
                Surface(
                    shape = CircleShape,
                    color = White,
                    shadowElevation = 10.dp,
                    modifier = Modifier
                        .size(180.dp)
                        .clickable {
                            val now = System.currentTimeMillis()
                            val currentTime = SimpleDateFormat("HH:mm",locale).format(Date(now))
                            if (!isCheckedIn){
                                checkInTime = currentTime
                                checkInMillis = now
                                isCheckedIn = true
                            } else {
                                checkOutTime = currentTime
                                checkOutMillis = now
                                isCheckedIn = false
                            }
                            onClick()
                        },
                    border = BorderStroke(20.dp, AppLightGray)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            imageVector = Icons.Default.TouchApp,
                            contentDescription = null,
                            tint = AppDarkGreen,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = if (isCheckedIn) "Check out" else "Check in",
                            color = AppDarkGreen,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                Spacer(modifier = Modifier.height(60.dp))
            }
            Spacer(modifier = Modifier.height(60.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                statusItems.forEach { item ->
                    StatusItem(
                        icon = item.icon,
                        time = item.time,
                        label = item.label
                    )
                }
            }
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Spacer(modifier = Modifier.height(24.dp))

            }
        }
    }
}
