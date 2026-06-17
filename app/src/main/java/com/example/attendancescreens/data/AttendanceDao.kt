package com.example.attendancescreens.data

import android.util.Log
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow

const val TAG ="ATTENDANCE DAO"

@Dao
interface AttendanceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttendance(attendanceEntity: AttendanceEntity)

    @Update
    suspend fun updateAttendance(attendanceEntity: AttendanceEntity)

    @Query("SELECT * FROM attendance_table WHERE id = :id")
    fun getAttendanceById(id: Int): Flow<AttendanceEntity?>

    @Query(""" SELECT * FROM attendance_table ORDER BY checkInMillis DESC """)
    fun getAllAttendance(): Flow<List<AttendanceEntity>>

    @Query(" SELECT * FROM attendance_table WHERE isActive IS 1 LIMIT 1 ")
    fun getActiveAttendanceFlow(): Flow<AttendanceEntity?>

    @Query(" SELECT * FROM attendance_table WHERE isActive IS 1 LIMIT 1 ")
    suspend fun getActiveAttendance(): AttendanceEntity?

    @Query("SELECT * FROM attendance_table ORDER BY checkInMillis DESC LIMIT 1")
    fun getLatestAttendanceFlow(): Flow<AttendanceEntity?>

    @Query("update attendance_table set isActive = 0")
    suspend fun removeAllActives()

}