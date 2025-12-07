package com.example.studymate.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.studymate.db.dao.UserDAO
import com.example.studymate.db.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDAO
}
