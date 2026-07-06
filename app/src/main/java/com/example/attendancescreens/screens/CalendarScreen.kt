package com.example.attendancescreens.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.attendancescreens.components.Calendar
import com.example.attendancescreens.components.HistoryItem
import com.example.attendancescreens.data.AttendanceDatabase
import com.example.attendancescreens.data.AttendanceRepository
import com.example.attendancescreens.ui.viewmodel.AttendanceViewModel
import com.example.attendancescreens.ui.viewmodel.AttendanceViewModelFactory
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val repository = remember { AttendanceRepository(AttendanceDatabase.getDatabase(context).AttendanceDao()) }
    val viewModel: AttendanceViewModel = viewModel(factory = AttendanceViewModelFactory(repository))
    
    val history by viewModel.history.collectAsState()
    val locale = LocalConfiguration.current.locales[0]

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            val datePickerState = rememberDatePickerState(
                initialSelectedDateMillis = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
            )

            val selectedDateMillis = datePickerState.selectedDateMillis ?: System.currentTimeMillis()
            val filteredHistory = remember(history, selectedDateMillis) {
                val selectedDate = Instant.ofEpochMilli(selectedDateMillis)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
                
                history.filter {
                    Instant.ofEpochMilli(it.checkInMillis)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate() == selectedDate
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                item {
                    Card(
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Calendar(datePickerState = datePickerState)
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    Text(
                        text = "Logs",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = Bold),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                if (filteredHistory.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth().padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "No logs for this date",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                            )
                        }
                    }
                }

                items(filteredHistory) { entity ->
                    val dateObj = Date(entity.checkInMillis)
                    val dateStr = SimpleDateFormat("dd", locale).format(dateObj)
                    val dayStr = SimpleDateFormat("EEE", locale).format(dateObj)

                    HistoryItem(
                        date = dateStr,
                        day = dayStr,
                        checkIn = entity.checkInTime,
                        checkOut = entity.checkOutTime ?: "--:--",
                        totalHrs = entity.totalHours ?: "--:--",
                        location = entity.location ?: "Location not found",
                        isMainColor = entity.checkOutTime == null
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CalendarScreenPrev() {
    CalendarScreen()
}