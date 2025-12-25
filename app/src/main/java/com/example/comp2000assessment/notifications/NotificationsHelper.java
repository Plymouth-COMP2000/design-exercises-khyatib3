package com.example.comp2000assessment.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.comp2000assessment.R;

public class NotificationsHelper {
    private static final String CHANNEL_ID = "restaurant_app_channel";
    private static final String CHANNEL_NAME = "Restaurant 2000 Updates";
    private static final String CHANNEL_DESC = "Notifications for changes made to bookings and menu";

    //method to create the notification channel needed
    private static void createNotificationChannel(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);
            channel.setDescription(CHANNEL_DESC);


            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    //method to show the notification (this is the one to be used in the other activities)
    public static void displayNotification(Context context, String notifTitle, String notifMessage){
        //first call the method to create notifcation channel
        createNotificationChannel(context);

        //then make the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setAutoCancel(true)
                .setContentTitle(notifTitle)
                .setContentText(notifMessage)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        //show the built notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        //checking permission isnt denied
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            //if denied, return to prevent app from crashing
            return;
        }

        //create the ids of notifications based on the time so they become a typical 'notification stack'
        int notificationId = (int) System.currentTimeMillis();
        notificationManager.notify(notificationId, builder.build());


    }

}
