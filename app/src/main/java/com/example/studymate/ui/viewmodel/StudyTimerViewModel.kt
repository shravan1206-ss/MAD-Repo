package com.example.studymate.ui.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studymate.db.dao.TimerHistoryDao
import com.example.studymate.db.entity.TimerHistoryEntity
import com.example.studymate.notification.NotificationHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StudyTimerViewModel(
    private val dao: TimerHistoryDao,
    private val appContext: Context
) : ViewModel() {

    private val totalTime = 25 * 60 * 1000L

    var timeLeft by mutableStateOf(totalTime)
        private set

    var isRunning by mutableStateOf(false)
        private set

    init {
        startTimer()
    }

    private fun startTimer() {
        viewModelScope.launch {
            while (true) {
                delay(1000)
                if (isRunning && timeLeft > 0) {
                    timeLeft -= 1000

                    if (timeLeft <= 0) {
                        isRunning = false
                        timeLeft = 0
                        saveHistory(completed = true)
                        showTimeUpNotification()
                    }
                }
            }
        }
    }

    fun toggle() {
        isRunning = !isRunning
    }

    fun reset() {
        saveHistory(completed = false)
        isRunning = false
        timeLeft = totalTime
    }

    private fun saveHistory(completed: Boolean) {
        viewModelScope.launch {
            dao.insert(
                TimerHistoryEntity(
                    durationMillis = totalTime,
                    completed = completed
                )
            )
        }
    }

    private fun showTimeUpNotification() {
        NotificationHelper.showNotification(
            appContext,
            "⏰ Time Up!",
            "Your study session is complete"
        )
    }
}
