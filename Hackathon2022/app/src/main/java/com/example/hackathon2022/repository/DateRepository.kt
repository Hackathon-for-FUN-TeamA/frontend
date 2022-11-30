package com.example.hackathon2022.repository

import androidx.annotation.WorkerThread
import com.example.hackathon2022.model.Date
import com.example.hackathon2022.model.DateDao
import kotlinx.coroutines.flow.Flow

class DateRepository(private val dateDao: DateDao) {

    val allDates: Flow<List<Date>> = dateDao.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(date: Date) {
        dateDao.insert(date)
    }
}