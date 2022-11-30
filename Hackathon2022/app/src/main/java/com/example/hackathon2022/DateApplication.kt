package com.example.hackathon2022

import android.app.Application
import com.example.hackathon2022.model.DateRoomDatabase
import com.example.hackathon2022.repository.DateRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class DateApplication: Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())

    private val database by lazy { DateRoomDatabase.getInstance(this, applicationScope) }
    val repository by lazy { DateRepository(database.dateDao()) }
}