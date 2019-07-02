package com.example.almasud.fundamental.firebase_cloud_messaging;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.almasud.fundamental.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Random;

public class MessageService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getData().isEmpty())
            showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        else
            showNotification(remoteMessage.getData());
    }

    private void showNotification(Map<String, String> data) {

        String title = data.get("title");
        String body = data.get("body");

        NotificationManager notificationManager = (NotificationManager) getSystemService(
                Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "com.example.almasud.fundamental.test";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                    "Notification", NotificationManager.IMPORTANCE_DEFAULT);

            notificationChannel.setDescription("ARM Channel");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.setVibrationPattern(new long[] {0, 1000, 500, 1000});
            notificationChannel.enableLights(true);

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(
                this, NOTIFICATION_CHANNEL_ID);
        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setContentText(body)
                .setContentInfo("Info");

        if (notificationManager != null) {
            notificationManager.notify(new Random().nextInt(), notificationBuilder.build());
        }
    }

    private void showNotification(String title, String body) {

        NotificationManager notificationManager = (NotificationManager) getSystemService(
                Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "com.example.almasud.fundamental.test";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                    "Notification", NotificationManager.IMPORTANCE_DEFAULT);

            notificationChannel.setDescription("ARM Channel");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.setVibrationPattern(new long[] {0, 1000, 500, 1000});
            notificationChannel.enableLights(true);

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(
                this, NOTIFICATION_CHANNEL_ID);
        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setContentText(body)
                .setContentInfo("Info");

        if (notificationManager != null) {
            notificationManager.notify(new Random().nextInt(), notificationBuilder.build());
        }
    }

    // The onNewToken callback fires whenever a new token is generated.
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.d("TOKEN_FIREBASE", s);
    }
}
