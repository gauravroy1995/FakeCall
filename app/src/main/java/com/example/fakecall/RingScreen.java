package com.example.fakecall;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import android.content.ClipData;
import android.content.ClipDescription;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class RingScreen extends AppCompatActivity {
    TextView MarqueeText;

    ConstraintLayout PhoneLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring_screen);
        getSupportActionBar().hide();
        ringTheAlarm();

        MarqueeText = (TextView) this.findViewById(R.id.maruqeeText);
        MarqueeText.setSelected(true);

        PhoneLayout = this.findViewById(R.id.callPhoneLayout);
        final Animation animShake = AnimationUtils.loadAnimation(this, R.anim.rotate);
        PhoneLayout.startAnimation(animShake);
        
        PhoneLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String text = "yes";
                ClipData.Item item = new ClipData.Item(text);
                String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};

                ClipData dragData = new ClipData(text,mimeTypes, item);
                View.DragShadowBuilder myShadow = new View.DragShadowBuilder(PhoneLayout);

                v.startDrag(dragData,myShadow,null,0);

                return true;
            }
        });

        PhoneLayout.setOnDragListener(new  View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch(event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        Log.d("yes", "Action is DragEvent.ACTION_DRAG_STARTED");


                        // Do nothing
                        break;

                    case DragEvent.ACTION_DRAG_ENTERED:
                        Log.d("yes", "Action is DragEvent.ACTION_DRAG_ENTERED");
                        int x_cord = (int) event.getX();
                        int y_cord = (int) event.getY();
                        break;

                    case DragEvent.ACTION_DRAG_EXITED :
                        Log.d("yes", "Action is DragEvent.ACTION_DRAG_EXITED");
                        x_cord = (int) event.getX();
                        y_cord = (int) event.getY();
                        break;

                    case DragEvent.ACTION_DRAG_LOCATION  :
                        Log.d("yes", "Action is DragEvent.ACTION_DRAG_LOCATION");
                        x_cord = (int) event.getX();
                        y_cord = (int) event.getY();

                        v.setBackgroundColor( getResources().getColor(R.color.design_default_color_background));
                        break;

                    case DragEvent.ACTION_DRAG_ENDED   :
                        Log.d("yes", "Action is DragEvent.ACTION_DRAG_ENDED");

                        // Do nothing
                        break;

                    case DragEvent.ACTION_DROP:
                        Log.d("yes", "ACTION_DROP event");

                        // Do nothing
                        break;
                    default: break;
                }
                return true;
            }
        });

        // change the status
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            changeStatusBarColor();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    void changeStatusBarColor(){
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.red_call));
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