package com.example.hackathon2022.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DateDao {
    @Insert
    fun insert(vararg date: Date)

    @Update
    fun update(date: Date)

    @Delete
    fun delete(date: Date)

    @Query("SELECT * FROM date")
    fun getAll(): List<Date>

}