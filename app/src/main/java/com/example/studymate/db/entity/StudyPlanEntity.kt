package com.example.studymate.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "study_plan")
data class StudyPlanEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val subject: String,
    val dateMillis: Long,
    val timeMillis: Long,
    val createdAt: Long = System.currentTimeMillis()
)
