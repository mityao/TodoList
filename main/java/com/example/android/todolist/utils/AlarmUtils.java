package com.example.android.todolist.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.android.todolist.AlarmReceiver;
import com.example.android.todolist.TodoEditActivity;
import com.example.android.todolist.models.Todo;

import java.util.Calendar;

/**
 * Created by mitya on 12/17/2016.
 */

public class AlarmUtils {

    public static void setAlarm(Context context, Todo todo) {
        Calendar c = Calendar.getInstance();// c will contain the current time
        if (todo.remindDate.compareTo(c.getTime()) < 0) {// this statement checks if date is smaller than current time
            // we only fire alarm when date is in the future
            return;
        }

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(TodoEditActivity.KEY_TODO, todo);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context,
                                                               0,
                                                               intent,
                                                               PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP,
                         todo.remindDate.getTime(),
                         alarmIntent);
    }
}
