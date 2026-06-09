package com.example.attendancescreens.model

data class AttendanceHistoryData(
    val date: String,
    val day: String,
    val checkIn: String,
    val checkOut: String,
    val totalHrs: String,
    val isMainColor: Boolean
)