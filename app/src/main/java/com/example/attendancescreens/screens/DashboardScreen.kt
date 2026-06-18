package com.example.attendancescreens.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.attendancescreens.components.StatusItem
import com.example.attendancescreens.data.AttendanceDatabase
import com.example.attendancescreens.data.AttendanceRepository
import com.example.attendancescreens.ui.theme.AppDarkGreen
import com.example.attendancescreens.ui.theme.AppLightGray
import com.example.attendancescreens.ui.theme.AppTextGray
import com.example.attendancescreens.ui.viewmodel.AttendanceViewModel
import com.example.attendancescreens.ui.viewmodel.AttendanceViewModelFactory
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun DashboardScreen(onClick: () -> Unit) {
    val context = LocalContext.current
    val repository = remember { AttendanceRepository(AttendanceDatabase.getDatabase(context).AttendanceDao()) }
    val viewModel: AttendanceViewModel = viewModel(factory = AttendanceViewModelFactory(repository))
    
    val active by viewModel.activeAttendance.collectAsState()
    val locale = LocalConfiguration.current.locales[0]

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(20.dp), 
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(SimpleDateFormat("HH:mm", locale).format(Date()), fontSize = 60.sp)
            Text(SimpleDateFormat("MMMM dd, yyyy - EEEE", locale).format(Date()), color = AppTextGray)
            
            Spacer(modifier = Modifier.height(60.dp))
            
            Surface(
                shape = CircleShape,
                color = White,
                shadowElevation = 10.dp,
                modifier = Modifier
                    .size(180.dp)
                    .clickable {
                        viewModel.toggleAttendance()
                        onClick()
                    },
                border = BorderStroke(20.dp, AppLightGray)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(Icons.Default.TouchApp, null, tint = AppDarkGreen, modifier = Modifier.size(48.dp))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if (active == null) "Check in" else "Check out",
                        color = AppDarkGreen,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(60.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                StatusItem(Icons.AutoMirrored.Filled.Login, active?.checkInTime ?: "--:--", "Check In")
                StatusItem(Icons.AutoMirrored.Filled.Logout, active?.checkOutTime ?: "--:--", "Check Out")
                StatusItem(Icons.Default.History, active?.totalHours ?: "--:--", "Total Hrs")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DashboardScreenPrev(){
    DashboardScreen(onClick = {})
}
