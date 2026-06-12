package com.example.attendancescreens.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AttendanceDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAttendance(attendanceEntity: AttendanceEntity)

    @Update
    suspend fun updateAttendance(attendanceEntity: AttendanceEntity)

    @Query("SELECT * FROM attendance_table WHERE id = :id")
    fun getAttendanceById(id: Int): Flow<AttendanceEntity?>

    @Query(""" SELECT * FROM attendance_table ORDER BY checkInMillis DESC """)
    fun getAllAttendance(): Flow<List<AttendanceEntity>>

    @Query(" SELECT * FROM attendance_table WHERE checkOutMillis IS NULL LIMIT 1 ")
    fun getActiveAttendanceFlow(): Flow<AttendanceEntity?>

    @Query(" SELECT * FROM attendance_table WHERE checkOutMillis IS NULL LIMIT 1 ")
    suspend fun getActiveAttendance(): AttendanceEntity?

}