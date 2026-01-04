package com.example.studymate.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "timer_history")
data class TimerHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val durationMillis: Long,
    val completed: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)
