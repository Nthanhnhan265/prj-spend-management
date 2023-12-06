package com.project.spendmanagement;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.Calendar;

public class ThongBaoNhacNho {
    private static final int ALARM_REQUEST_CODE_BASE = 1000;
    public static final String ACTION_REMINDER = "com.example.reminder.ACTION_REMINDER";

    public static void ThongBao(Context context, long timestamp, String title, long reminderId,NhacNho nhacNho) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.setAction(ACTION_REMINDER);
        intent.putExtra("title", title);
        intent.putExtra("desc", nhacNho.getNoiDung());
        // Thêm cờ FLAG_IMMUTABLE khi tạo PendingIntent
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                (int) (ALARM_REQUEST_CODE_BASE + reminderId),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        long alarmTimeAtUTC = System.currentTimeMillis()+ timestamp;
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, alarmTimeAtUTC, pendingIntent);
    }

    public static void HuyThongBao(Context context, long reminderId) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.setAction("com.example.reminder.ACTION_REMINDER");

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                (int) (ALARM_REQUEST_CODE_BASE + reminderId),
                intent,
                PendingIntent.FLAG_NO_CREATE|PendingIntent.FLAG_IMMUTABLE      );

        if (pendingIntent != null) {
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
        }
    }
}
