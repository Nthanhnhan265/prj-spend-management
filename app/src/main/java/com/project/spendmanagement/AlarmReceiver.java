package com.project.spendmanagement;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        // Lấy thông tin từ Intent
        String title = intent.getStringExtra("title");
        String desc = intent.getStringExtra("desc");
        long reminderId = intent.getLongExtra("id", -1);

        // Hiển thị thông báo
        showNotification(context, title,desc);
    }

    public void showNotification(Context context,String title,String desc) {
        String id="CHANEL_ID";
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context,id) ;
        builder.setSmallIcon(R.drawable.ic_alarm)
                .setContentTitle(title)
                .setContentText(desc)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        Intent intent=new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,0,
                intent, PendingIntent.FLAG_MUTABLE);
        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager =
                (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        if(android.os.Build.VERSION.SDK_INT>= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel=notificationManager.getNotificationChannel(id);
            if(notificationChannel==null) {
                int importance =NotificationManager.IMPORTANCE_HIGH;
                notificationChannel=new NotificationChannel(id,"desc",importance);
                notificationChannel.setLightColor(Color.GREEN);
                notificationChannel.enableVibration(true);
                notificationManager.createNotificationChannel(notificationChannel);

            }
        }
        notificationManager.notify(0,builder.build());


    }



}
