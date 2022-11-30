package com.example.hackathon2022.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Date::class], version = 1, exportSchema = false)
abstract class DateRoomDatabase: RoomDatabase() {

    abstract fun dateDao(): DateDao

    companion object {
        @Volatile
        private var INSTANCE: DateRoomDatabase? = null

        fun getInstance(
            context: Context,
            scope: CoroutineScope
        ): DateRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DateRoomDatabase::class.java,
                    "date_database"
                )
                    .fallbackToDestructiveMigration()
//                    .addCallback(DateDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }


//        private class DateDatabaseCallback(
//            private val scope: CoroutineScope
//        ) : RoomDatabase.Callback() {
//            override fun onCreate(db: SupportSQLiteDatabase) {
//                super.onCreate(db)
//
//                INSTANCE?.let { database ->
//                    scope.launch(Dispatchers.IO) {
//                        populateDatabase(database.dateDao())
//                    }
//                }
//            }
//        }

//        suspend fun populateDatabase(dateDao: DateDao) {
//            dateDao.getAll()
//        }
    }
}