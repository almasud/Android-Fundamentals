package com.example.almasud.fundamental.map_location;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.almasud.fundamental.R;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.ArrayList;
import java.util.List;

public class GeofenceBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            Log.d("GeofenceBReceiver", "GeofencingEvent error " + geofencingEvent.getErrorCode());
            return;
        }

        // Get the transition type.
        int geofenceTransition = geofencingEvent.getGeofenceTransition();

        // Test that the reported transition was of interest.
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ||
                geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {

            // Get the geofences that were triggered. A single event can trigger
            // multiple geofences.
            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();

            // Get the transition details as a String.
            List<String> triggeredGeofencesIds = new ArrayList<>();
            for (Geofence geofence: triggeringGeofences) {
                triggeredGeofencesIds.add(geofence.getRequestId());
            }

            String transitionTypeString = "";
            switch (geofenceTransition) {
                case Geofence.GEOFENCE_TRANSITION_ENTER:
                    transitionTypeString = "Entered";
                    break;
                case Geofence.GEOFENCE_TRANSITION_EXIT:
                    transitionTypeString = "Exited";
                    break;
            }

            String notificationString = transitionTypeString + ": " + TextUtils
                    .join(", ", triggeredGeofencesIds);
            sendNotification(context, notificationString);
        } else {
            // Log the error.
            Log.e("GeofenceBReceiver", "Invalid Transition Type.");
        }
    }

    private void sendNotification(Context context, String notificationString) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "com.example.almasud.fundamental.map_location.geofence";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    NOTIFICATION_CHANNEL_ID, "GeoFence", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("GeoFence Channel");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.setVibrationPattern(new long[] {0, 1000, 500, 1000});
            notificationChannel.enableLights(true);

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID);
        builder.setAutoCancel(true);
        /* Adaptive icons are a new thing introduced in Android Oreo. They can change
           the icon shape, based on the device’s own preferences. To create an adaptive
           effect, we can’t rely on just resources in mipmaps anymore. The adaptive
           icons live in mipmap-anydpi-v26 folder, and are defined in XML. On devices
           running SDK 25 and lower the icons will just be taken from the corresponding
           mipmap-xdpi folder. To solving the problem the easiest thing to do would be
           to target SDK 25 or avoid using Adaptive Icons for now, until a fix is
           issued by Google.
         */
        builder.setSmallIcon(R.drawable.ic_notification);
        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        builder.setContentTitle("Geofencing Notification");
        builder.setContentText(notificationString);

        notificationManager.notify(123, builder.build());
    }
}
