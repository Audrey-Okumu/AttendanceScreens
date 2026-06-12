package com.example.attendancescreens.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AttendanceEntity::class], version = 1, exportSchema = false)
abstract class AttendanceDatabase : RoomDatabase(){
    abstract fun AttendanceDao(): AttendanceDao
    companion object {
        @Volatile
        private var Instance: AttendanceDatabase? = null
        fun getDatabase(context: Context): AttendanceDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AttendanceDatabase::class.java, "attendance_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}