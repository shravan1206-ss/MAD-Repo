package com.example.studymate.notification.worker

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.example.studymate.MainActivity
import com.example.studymate.network.RetrofitInstance
import com.example.studymate.notification.NotificationHelper
import java.util.concurrent.TimeUnit

class QuoteWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {

            val response = RetrofitInstance.api.getQuotes(
                page = (1..10).random(),
                limit = 1
            )
            val quote = response.quotes.firstOrNull()
            Log.d("QuoteWorker", "Quote: ${quote?.quote}")

            val intent = Intent(applicationContext, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                putExtra("open_screen", "motivation") // Identifier for Motivation screen
            }
            val pendingIntent = PendingIntent.getActivity(
                applicationContext,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            NotificationHelper.showNotification(
                applicationContext,
                "💡 Motivation",
                quote?.quote ?: "Stay motivated!",
            )

            val nextWork =
                OneTimeWorkRequestBuilder<QuoteWorker>()
                    .setInitialDelay(1, TimeUnit.MINUTES)
                    .build()
            WorkManager
                .getInstance(applicationContext)
                .enqueue(nextWork)

            Result.success()

        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
    }
}
