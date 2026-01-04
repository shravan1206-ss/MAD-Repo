package com.example.studymate.db.modules

import android.content.Context
import androidx.room.Room
import com.example.studymate.db.AppDatabase

object DatabaseModule {

    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDb(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "studymate_db"
            ).build().also { INSTANCE = it }
        }
    }
}
