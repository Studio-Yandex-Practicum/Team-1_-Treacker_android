package com.example.tracker.settings.common

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.core.net.toUri
import com.example.tracker.settings.domain.api.SettingsInteractor
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.Calendar

/**
 * Планировщик для ежедневных точных уведомлений в заданное пользователем время,
 * с поддержкой запроса на точные будильники (exact alarms) на Android 12+
 * и восстановлением после перезагрузки.
 */
object NotificationScheduler : KoinComponent {
    private const val REQUEST_CODE = 1001
    private val settingsInteractor: SettingsInteractor by inject()

    private lateinit var alarmManager: AlarmManager

    /**
     * Читает текущие настройки и запускает или отменяет ежедневное напоминание.
     */
    fun scheduleDailyReminder(context: Context) {
        val settings = settingsInteractor.getNotificationSettings()
        if (!settings.active) {
            cancelDailyReminder(context)
            return
        }
        val (hour, minute) = settings.time.split(":").map { it.toInt() }
        scheduleExactAlarm(context, hour, minute)
    }

    /**
     * Запрашивает разрешение на точные будильники (exact alarm) на Android 12+, если необходимо,
     * затем планирует следующий будильник.
     */
    private fun scheduleExactAlarm(context: Context, hour: Int, minute: Int) {
        alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S &&
            !alarmManager.canScheduleExactAlarms()) {
            val intent = Intent(
                Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM,
                "package:${context.packageName}".toUri()
            ).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            return
        }

        val alarmIntent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = setupPendingIntent(context, alarmIntent)
        val calendar = setupCalendar(hour, minute)

        if (calendar.timeInMillis <= System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        checkVersionAndSetupAlarm(calendar, pendingIntent)
    }

    private fun setupCalendar(hour: Int, minute: Int): Calendar {
        return Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
        }
    }
    private fun setupPendingIntent(context: Context, alarmIntent: Intent): PendingIntent {
        return PendingIntent.getBroadcast(
            context,
            REQUEST_CODE,
            alarmIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }
    private fun checkVersionAndSetupAlarm(calendar: Calendar, pendingIntent: PendingIntent) {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ->
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT ->
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )
            else ->
                alarmManager.set(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )
        }
    }

    /**
     * Отменяет напоминание, если оно уже существует.
     */
    fun cancelDailyReminder(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            REQUEST_CODE,
            alarmIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }
}