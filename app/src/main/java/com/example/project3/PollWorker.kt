package com.example.project3

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.project3.api.AstroRepository
import com.example.project3.api.GridItem
import android.app.PendingIntent
import androidx.core.content.ContextCompat.getSystemService
import com.example.project3.api.PhotoOfTheDay
import java.util.Calendar

private const val TAG = "PollWorker"


class PollWorker(
    private val context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        Log.i(TAG, "Work request triggered")

        val astroRepository = AstroRepository(context)

        try {
            val newPhoto = astroRepository.fetchPhotoOfTheDay()
            if (isNewPhoto(newPhoto)) {
                notifyUser()
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Failed to fetch photo of the day", ex)
            return Result.failure()
        }
        return Result.success()
    }

    private fun isNewPhoto(newPhoto: PhotoOfTheDay): Boolean {
        return true
    }

    private fun notifyUser() {
        val intent = MainActivity.newIntent(context)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val currentDate = getCurrentDate()

        val notification = NotificationCompat
            .Builder(context, NOTIFICATION_CHANNEL_ID)
            .setTicker("ticker")
            .setSmallIcon(android.R.drawable.ic_menu_report_image)
            .setContentTitle("New Astronomy Photo Of The Day")
            .setContentText("Photo For $currentDate")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) { return }
        NotificationManagerCompat.from(context).notify(0, notification)
    }
    private fun getCurrentDate(): String {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = (calendar.get(Calendar.MONTH) + 1).toString().padStart(2, '0')
        val day = calendar.get(Calendar.DAY_OF_MONTH).toString().padStart(2, '0')
        return "$year-$month-$day"
    }
}
