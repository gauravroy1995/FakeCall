package com.example.fakecall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class RingScreen extends AppCompatActivity {
    TextView MarqueeText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring_screen);
        getSupportActionBar().hide();
        ringTheAlarm();

        MarqueeText = (TextView) this.findViewById(R.id.maruqeeText);
        MarqueeText.setSelected(true);
    }




    void ringTheAlarm(){
//        WorkManager mWorkManager = WorkManager.getInstance(getApplicationContext());
        //This is the subclass of our WorkRequest
//        final OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(MyWorker.class).setInitialDelay(200, TimeUnit.MILLISECONDS).build();
//        WorkManager.getInstance(getApplicationContext()).enqueue(workRequest);

        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play();
    }
}