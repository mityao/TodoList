package com.example.android.todolist;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.android.todolist.models.Todo;

/**
 * Created by mitya on 12/17/2016.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //Toast.makeText(context, "alarm!", Toast.LENGTH_LONG).show();

        final int notificationId = 100; // this will be used to cancel the notification
        Todo todo = intent.getParcelableExtra(TodoEditActivity.KEY_TODO);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_notifications_white_24dp)
                .setContentTitle(todo.text)
                .setContentText(todo.text);
        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(context, TodoEditActivity.class);
        resultIntent.putExtra(TodoEditActivity.KEY_TODO, todo);
        resultIntent.putExtra(TodoEditActivity.KEY_NOTIFICATION_ID, notificationId);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context,
                                                                      0,
                                                                      resultIntent,
                                                                      PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        NotificationManager nm = (NotificationManager) context.getSystemService(
                context.NOTIFICATION_SERVICE);
        // notificationId allows you to update the notification later on, like canceling it
        nm.notify(notificationId, builder.build());
    }
}
