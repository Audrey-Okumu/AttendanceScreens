package com.example.attendancescreens.data

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [AttendanceEntity::class], version = 1, exportSchema = true)
abstract class AttendanceDatabase : RoomDatabase(){
    abstract fun AttendanceDao(): AttendanceDao
    companion object {
        @Volatile
        private var Instance: AttendanceDatabase? = null
        fun getDatabase(context: Context): AttendanceDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AttendanceDatabase::class.java, "attendance_database")
                    .fallbackToDestructiveMigration(true)
                    .build()
                    .also { Instance = it }
            }
        }
    }
}