package com.example.notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    // Notification Channel = Notification Category displayed in Android app's Settings.
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";

    // Notification Id needed when we need to update or cancel the notification
    private static final int NOTIFICATION_ID = 0;

    // Notification Manager to deliver notifications to the user
    private NotificationManager mNotifyManager;

    private Button notify_button;
    private Button button_cancel;
    private Button button_update;

    // Broadcast Receiver Intent String
    private static final String ACTION_UPDATE_NOTIFICATION = BuildConfig.APPLICATION_ID + ".ACTION_UPDATE_NOTIFICATION";
    private final NotificationReceiver mReceiver = new NotificationReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notify_button = findViewById(R.id.notify);
        button_update = findViewById(R.id.update);
        button_cancel = findViewById(R.id.cancel);

        createNotificationChannel();

        notify_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNotification();
            }
        });

        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateNotification();
            }
        });

        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelNotification();
            }
        });

        setNotificationButtonState(true, false, false);

        registerReceiver(mReceiver,new IntentFilter(ACTION_UPDATE_NOTIFICATION));
    }

    private void sendNotification(){
        // For updating the notification from notification drawer
        Intent intentAction = new Intent(ACTION_UPDATE_NOTIFICATION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,NOTIFICATION_ID, intentAction, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
        // Add a pending intent of "Updating from notification bar" whenever a notification is built
        notifyBuilder.addAction(R.drawable.ic_update,"Update Notification", pendingIntent);
        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());

        setNotificationButtonState(false, true, true);
    }

    private void createNotificationChannel(){
        mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // If the API level >= Android 8 (API level 26) then only create a notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel nc = new NotificationChannel(PRIMARY_CHANNEL_ID, "My_Notification_Channel", NotificationManager.IMPORTANCE_HIGH);
            // configure notification channel as required
            nc.enableLights(true);
            nc.enableVibration(true);
            nc.setLightColor(Color.RED);
            nc.setDescription("Description for My_Notification_Channel");
            mNotifyManager.createNotificationChannel(nc);
        }
    }

    private NotificationCompat.Builder getNotificationBuilder(){
        // To launch the MainActivity from notification on tap
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingNotificationIntent = PendingIntent.getActivity(this, NOTIFICATION_ID,notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Creates the actual notification
        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                // launch pending intent when user taps on the notification
                .setContentIntent(pendingNotificationIntent)
                // dismiss the notification on tap
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                // Set the sound, vibration, and LED-color pattern (if any) for your notification to the default values.
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setContentTitle("You've been notified!")
                .setContentText("This is your notification text.")
                .setSmallIcon(R.drawable.ic_android);

        return notifyBuilder;
    }

    protected void updateNotification() {
        System.out.println("Entered Update Notification");
        // convert drawable image to bitmap
        Bitmap bitmapImage = BitmapFactory.decodeResource(getResources(), R.drawable.update_notification_img);
        // We need to build notification again for update
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
        notifyBuilder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmapImage).setBigContentTitle("Notification update with Big Picture Style"));
        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());
        setNotificationButtonState(false, false, true);
    }

    private void cancelNotification() {
        mNotifyManager.cancel(NOTIFICATION_ID);
        setNotificationButtonState(true, false, false);
    }

    private void setNotificationButtonState(boolean isNotifyEnabled, boolean isUpdateEnabled, boolean isCancelEnabled){
        notify_button.setEnabled(isNotifyEnabled);
        button_update.setEnabled(isUpdateEnabled);
        button_cancel.setEnabled(isCancelEnabled);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    public class NotificationReceiver extends BroadcastReceiver {

        public NotificationReceiver() {
        }

        /**
         * Receives the incoming broadcasts and responds accordingly.
         *
         * @param context Context of the app when the broadcast is received.
         * @param intent The broadcast intent containing the action.
         */
        @Override
        public void onReceive(Context context, Intent intent) {
            // Update the notification.
            updateNotification();
        }
    }
}