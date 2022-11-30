package com.example.hackathon2022.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Date::class], version = 1, exportSchema = false)
abstract class DateRoomDatabase: RoomDatabase() {
    abstract fun dateDao(): DateDao

    companion object {
        private var INSTANCE: DateRoomDatabase? = null

        private val lock = Any()

        fun getInstance(context: Context): DateRoomDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        DateRoomDatabase::class.java, "Drive.db"
                    )
                        .allowMainThreadQueries()
                        .build()
                }
                return INSTANCE!!
            }
        }

    }
}