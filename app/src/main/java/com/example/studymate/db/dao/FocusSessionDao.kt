package com.example.studymate.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.studymate.db.entity.FocusSessionEntity

@Dao
interface FocusSessionDao {

    @Insert
    suspend fun insert(session: FocusSessionEntity)

    @Query("SELECT SUM(durationSeconds) FROM focus_sessions")
    suspend fun getTotalFocusTime(): Long?

    @Query("SELECT COUNT(*) FROM focus_sessions")
    suspend fun getSessionCount(): Int
}
