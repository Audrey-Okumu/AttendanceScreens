package com.example.attendancescreens.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp), 
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = SimpleDateFormat("HH:mm", locale).format(Date()), 
                style = MaterialTheme.typography.displayLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            )
            
            Text(
                text = SimpleDateFormat("MMMM dd, yyyy • EEEE", locale).format(Date()), 
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
            
            Spacer(modifier = Modifier.weight(1f))
            
            Box(contentAlignment = Alignment.Center) {
                // Outer glow effect
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                    modifier = Modifier.size(220.dp)
                        .clip(CircleShape)
                ) {}
                
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.surface,
                    shadowElevation = 8.dp,
                    modifier = Modifier
                        .size(180.dp)
                        .clip(CircleShape)
                        .clickable {
                            viewModel.toggleAttendance()
                            onClick()
                        },
                    border = BorderStroke(8.dp, MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            imageVector = Icons.Default.TouchApp, 
                            contentDescription = null, 
                            tint = MaterialTheme.colorScheme.primary, 
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = if (active == null) "CHECK IN" else "CHECK OUT",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp, horizontal = 12.dp), 
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    StatusItem(Icons.AutoMirrored.Filled.Login, active?.checkInTime ?: "--:--", "Check In")
                    VerticalDivider(modifier = Modifier.height(40.dp).width(1.dp), color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f))
                    StatusItem(Icons.AutoMirrored.Filled.Logout, active?.checkOutTime ?: "--:--", "Check Out")
                    VerticalDivider(modifier = Modifier.height(40.dp).width(1.dp), color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f))
                    StatusItem(Icons.Default.History, active?.totalHours ?: "--:--", "Total Hrs")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DashboardScreenPrev(){
    DashboardScreen(onClick = {})
}
