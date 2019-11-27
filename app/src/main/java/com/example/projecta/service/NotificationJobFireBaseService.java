package com.example.projecta.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.NonNull;

import com.firebase.jobdispatcher.JobService;

public class NotificationJobFireBaseService extends JobService {

    @Override
    public boolean onStartJob(@NonNull com.firebase.jobdispatcher.JobParameters job) {
        AlarmManager manager=(AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent= new Intent(this, AlarmBroadcastReceiver.class);
        PendingIntent pendingIntent= PendingIntent.getBroadcast(this,0,intent,0);
        SharedPreferences sf=getSharedPreferences("file",MODE_PRIVATE);
        long defValue= -1234;
        long text=sf.getLong("long", defValue);

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,text,pendingIntent);
        }else if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            manager.setExact(AlarmManager.RTC_WAKEUP,text,pendingIntent);
        }else{
            manager.set(AlarmManager.RTC_WAKEUP,text,pendingIntent);
        }
        return false;
    }

    @Override
    public boolean onStopJob(@NonNull com.firebase.jobdispatcher.JobParameters job) {
        return false;
    }
}
