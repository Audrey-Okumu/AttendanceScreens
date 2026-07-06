package com.example.attendancescreens.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.attendancescreens.data.AttendanceEntity
import com.example.attendancescreens.data.AttendanceRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AttendanceViewModel(private val repository: AttendanceRepository) : ViewModel() {

    val activeAttendance = repository.getActiveAttendanceFlow()
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    val history = repository.getAllAttendance()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun toggleAttendance(location: String? = null) = viewModelScope.launch {
        val active = repository.getActiveAttendance()
        val now = System.currentTimeMillis()
        val time = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(now))

        if (active == null) {
            repository.checkIn(AttendanceEntity(checkInTime = time, checkInMillis = now, location = location))
        } else {
            val diff = now - active.checkInMillis
            val duration = "${diff / 3600000}h ${(diff / 60000) % 60}m"
            repository.checkOut(active.copy(checkOutTime = time, checkOutMillis = now, totalHours = duration))
        }
    }
}
