package com.example.studymate.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.studymate.db.dao.FocusSessionDao
import com.example.studymate.db.dao.StudyPlanDao
import com.example.studymate.db.dao.TimerHistoryDao
import com.example.studymate.db.dao.UserDAO
import com.example.studymate.db.entity.FocusSessionEntity
import com.example.studymate.db.entity.StudyPlanEntity
import com.example.studymate.db.entity.TimerHistoryEntity
import com.example.studymate.db.entity.UserEntity

@Database(
    entities = [
        UserEntity::class,
        StudyPlanEntity::class,
        FocusSessionEntity::class,
        TimerHistoryEntity::class
    ],
    version = 4
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDAO
    abstract fun studyPlanDao(): StudyPlanDao
    abstract  fun focusSessionDao(): FocusSessionDao
    abstract  fun timerHistoryDao(): TimerHistoryDao
}
