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

    Surface(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 40.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(horizontal = 10.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
            ) {
                Text(text = "Attendance History", fontWeight = Bold, fontSize = 24.sp)
            }

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
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                item {
                    Calendar(datePickerState = datePickerState)
                    Spacer(modifier = Modifier.height(16.dp))
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
                        location = "NLS Tech Solutions Limited, Saachi Plaza",
                        isMainColor = entity.checkOutTime == null
                    )
                }
            }
        }
    }
}
