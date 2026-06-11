package com.example.attendancescreens.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "attendance_table")
data class AttendanceEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val checkInTime : String,
    val checkOutTime : String? = null,
    val checkInMillis : Long,
    val checkOutMillis : Long? = null,
    val totalHours : String? = null
)