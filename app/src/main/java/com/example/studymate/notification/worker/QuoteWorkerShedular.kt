package com.example.studymate.notification.worker

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

fun scheduleTestQuoteWorker(context: Context) {

    val workRequest =
        OneTimeWorkRequestBuilder<QuoteWorker>()
            .setInitialDelay(1, TimeUnit.MINUTES)
            .build()

    WorkManager.getInstance(context).enqueue(workRequest)
}
