package com.example.attendancescreens.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.getSelectedDate
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.attendancescreens.components.Calendar
import com.example.attendancescreens.components.HistoryItem
import com.example.attendancescreens.model.AttendanceHistoryData
import java.text.SimpleDateFormat
import java.time.LocalDate


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    modifier: Modifier = Modifier
) {
    val historyItems: List<AttendanceHistoryData> = listOf(
        AttendanceHistoryData("26", "Thu", "09:00 am", "--:--", "--:--", true),
        AttendanceHistoryData("25", "Wed", "09:00 am", "18:00", "08:00", false),
        AttendanceHistoryData("24", "Tue", "09:00 am", "18:00", "08:00", false)
    )
    val locale = LocalConfiguration.current.locales[0]

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Text(
                    text = "Attendance History",
                    fontWeight = Bold,
                    fontSize = 24.sp,
                )
            }

            val datePickerState = rememberDatePickerState(
                initialSelectedDateMillis = LocalDate.now().atStartOfDay().toInstant(java.time.ZoneOffset.UTC).toEpochMilli()
            )

            val currentDate = datePickerState.getSelectedDate()

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                item {
                    Calendar(datePickerState = datePickerState)
                    Spacer(modifier = Modifier.height(16.dp))
                }

                val date: String = currentDate?.dayOfMonth.toString()
                val day : String = SimpleDateFormat("EEE",locale ).format(datePickerState.selectedDateMillis)

                items(historyItems) { item: AttendanceHistoryData ->
                    HistoryItem(
                        date = date,
                        day = day,
                        checkIn = item.checkIn,
                        checkOut = item.checkOut,
                        totalHrs = item.totalHrs,
                        location = "NLS Tech Solutions Limited, Saachi Plaza",
                        isMainColor = item.isMainColor
                    )
                }
            }
        }
    }
}