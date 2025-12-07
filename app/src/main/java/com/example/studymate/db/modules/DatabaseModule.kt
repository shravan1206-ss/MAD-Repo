package com.example.studymate.db.modules

import android.content.Context
import androidx.room.Room
import com.example.studymate.db.AppDatabase

object DatabaseModule {
    private var INSTANCE: AppDatabase? = null

    fun getDb(context: Context): AppDatabase {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "studymate_db"
            ).build()
        }
        return INSTANCE!!
    }
}
