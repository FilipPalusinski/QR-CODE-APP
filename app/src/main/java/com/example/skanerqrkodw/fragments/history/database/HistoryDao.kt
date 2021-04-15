package com.example.skanerqrkodw.fragments.history.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HistoryDao{



    @Query("SELECT * FROM history ORDER BY id DESC")
    fun getAll(): LiveData<List<History>>

    @Insert
    suspend fun insert(history: History)

    @Query("DELETE FROM history WHERE id = :itemId")
    suspend fun deleteById(itemId: Int)

    @Delete
    suspend fun delete(history: History)



}