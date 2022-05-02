package com.example.bowling;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;

import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationHandler {
    private static final String CHANNEL_ID = "bowling_notification_channel";
    private final int NOTIFICATION_ID =0;

    private NotificationManager mManager;
    private Context mContext;

    public NotificationHandler(Context context) {
        this.mContext = context;
        this.mManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        createChannel();
    }

    private void createChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            return;

        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Bowling Notification", NotificationManager.IMPORTANCE_DEFAULT);
        channel.enableVibration(true);
        channel.setDescription("dfdfdfd");
        this.mManager.createNotificationChannel(channel);
    }

    public void send(String message){


        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, CHANNEL_ID)
                .setContentTitle("Bowling").setContentText(message)
                .setSmallIcon(R.drawable.ic_happy);

        this.mManager.notify(NOTIFICATION_ID, builder.build() );
    }
}
