package com.example.fakecall;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class RingScreen extends AppCompatActivity {
    TextView MarqueeText;

    ConstraintLayout PhoneLayout;

    ConstraintLayout BottomConstraint;
    Ringtone ring;
    ConstraintLayout AnsweredScreen;

    ImageView endButton;

    Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring_screen);
        getSupportActionBar().hide();
        ringTheAlarm();

        MarqueeText = (TextView) this.findViewById(R.id.maruqeeText);
        MarqueeText.setSelected(true);

        Chronometer simpleChronometer = (Chronometer) findViewById(R.id.simpleChronometer); // initiate a chronometer

        endButton = findViewById(R.id.endCallButton);
        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                System.exit(0);
            }
        });

        BottomConstraint = findViewById(R.id.bottomConstraint);

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
            int initialCoord = 80;
            int finalCoord = 80;
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
                        Log.d("yes", "Action is DragEvent.EXITED"+Integer.toString(y_cord));
                        break;

                    case DragEvent.ACTION_DRAG_LOCATION  :
                        Log.d("yes", "Action is DragEvent.ACTION_DRAG_LOCATION");
                        x_cord = (int) event.getX();
                        y_cord = (int) event.getY();
                        // swipe up
                        if(y_cord < 10){
                            BottomConstraint.setVisibility(View.GONE);
                            AnsweredScreen.setVisibility(View.VISIBLE);
                            simpleChronometer.setVisibility(View.VISIBLE);
                            simpleChronometer.start();
                            vibrator.cancel();
                            ring.stop();
                        }
                        // swipe down
                        if(y_cord > 153){
                            finishAffinity();
                            System.exit(0);
                        }
                        Log.d("yes", "Action is DragEvent.ACTION_DRAG_LOCATION"+Integer.toString(y_cord));
                        Integer.toHexString(y_cord);
                        finalCoord = Math.abs(initialCoord- y_cord);

                        float finalF = (float)(finalCoord/80);


                        v.setAlpha(finalF);

//                        v.setBackgroundColor( getResources().getColor(R.color.design_default_color_background));
                        break;

                    case DragEvent.ACTION_DRAG_ENDED   :
                        Log.d("yes", "Action is DragEvent.ACTION_DRAG_ENDED");

                        v.setAlpha(1);

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

        startVibration();

        AnsweredScreen = findViewById(R.id.answerScreen);
    }


    void startVibration(){
        // Get instance of Vibrator from current Context
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

// Start without a delay
// Vibrate for 100 milliseconds
// Sleep for 1000 milliseconds
        long[] pattern = {0, 300, 2000};

// The '0' here means to repeat indefinitely
// '0' is actually the index at which the pattern keeps repeating from (the start)
// To repeat the pattern from any other point, you could increase the index, e.g. '1'
        vibrator.vibrate(pattern, 0);
    }

    @Override
    public void onBackPressed() {
        exitTheApp();
        super.onBackPressed();
    }

    void exitTheApp(){
        finishAffinity();
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
        ring = RingtoneManager.getRingtone(getApplicationContext(), notification);
        ring.play();
    }

    @Override
    protected void onStop() {
        vibrator.cancel();
        ring.stop();
        super.onStop();
    }
}