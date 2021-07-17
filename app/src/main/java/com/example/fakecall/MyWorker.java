package com.example.fakecall;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import javax.xml.transform.Result;

public class MyWorker extends Worker {
    Context mcontext;
    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.mcontext = context;
    }

    /*
     * This method is responsible for doing the work
     * so whatever work that is needed to be performed
     * we will put it here
     *
     * For example, here I am calling the method displayNotification()
     * It will display a notification
     * So that we will understand the work is executed
     * */

    @NonNull
    @Override
    public Result doWork() {
//        displayNotification("My Worker", "Hey I finished my work");
        Log.d("Work Manager","work");
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play();

        Intent intent = new Intent(mcontext, MainActivity.class);
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        mcontext.startActivity(intent);
//        new Handler(Looper.getMainLooper()).post(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(mcontext, MainActivity.class);
//                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
//                mcontext.startActivity(intent);
//            }
//        });
        return Result.success();
    }


}