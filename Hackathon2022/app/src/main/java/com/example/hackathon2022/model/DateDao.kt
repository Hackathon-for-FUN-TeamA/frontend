package com.example.hackathon2022.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DateDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(date: Date)

    @Update
    fun update(date: Date)

    @Delete
    fun delete(date: Date)

    @Query("SELECT * FROM date_database")
    fun getAll(): Flow<List<Date>>

    @Query("DELETE FROM date_database")
    suspend fun deleteALL()
}