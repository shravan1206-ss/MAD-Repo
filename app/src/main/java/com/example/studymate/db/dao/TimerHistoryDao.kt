package com.example.studymate.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.studymate.db.entity.TimerHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TimerHistoryDao {

    @Insert
    suspend fun insert(history: TimerHistoryEntity)

    @Query("SELECT * FROM timer_history ORDER BY timestamp DESC")
    fun getAll(): Flow<List<TimerHistoryEntity>>
}
