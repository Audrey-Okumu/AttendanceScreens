package com.example.attendancescreens.data

import kotlinx.coroutines.delay

class AttendanceRepository(
    private val dao: AttendanceDao
) {

    fun getAllAttendance() =
        dao.getAllAttendance()

    suspend fun checkIn(attendanceEntity: AttendanceEntity) {
        dao.insertAttendance(attendanceEntity)
    }

    suspend fun checkOut(attendanceEntity: AttendanceEntity) {
        dao.updateAttendance(attendanceEntity)
        delay(2500)
        dao.removeAllActives()
    }

    fun getActiveAttendanceFlow() =
        dao.getActiveAttendanceFlow()

    suspend fun getActiveAttendance() =
        dao.getActiveAttendance()
}