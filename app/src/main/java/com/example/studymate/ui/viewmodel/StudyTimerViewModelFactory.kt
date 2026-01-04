package com.example.studymate.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.studymate.db.dao.TimerHistoryDao

class StudyTimerViewModelFactory(
    private val dao: TimerHistoryDao,
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StudyTimerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StudyTimerViewModel(dao, context.applicationContext) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}
