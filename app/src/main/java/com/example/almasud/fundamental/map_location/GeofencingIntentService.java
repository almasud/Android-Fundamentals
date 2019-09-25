package com.example.almasud.fundamental.map_location;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
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

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class GeofencingIntentService extends IntentService {

    public GeofencingIntentService() {
        super("GeofencingIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            Log.d("GeofencingService", "GeofencingEvent error " + geofencingEvent.getErrorCode());
        } else {
            int transitionType = geofencingEvent.getGeofenceTransition();
            String transitionTypeString = "";
            switch (transitionType) {
                case Geofence.GEOFENCE_TRANSITION_ENTER:
                    transitionTypeString = "Entered";
                    break;
                case Geofence.GEOFENCE_TRANSITION_EXIT:
                    transitionTypeString = "Exited";
                    break;
            }

            List<String> triggeredGeofencesIds = new ArrayList<>();
            for (Geofence geofence: geofencingEvent.getTriggeringGeofences()) {
                triggeredGeofencesIds.add(geofence.getRequestId());
            }

            String notificationString = transitionTypeString + ": " + TextUtils
                    .join(", ", triggeredGeofencesIds);
            sendNotification(notificationString);
        }
    }

    private void sendNotification(String notificationString) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "com.example.almasud.fundamental.geofence";
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

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
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
