package com.example.stopwatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String KEY_CHRONOMETER_BASE = "chronometer_base";
    public static final String KEY_CHRONOMETER_OLD_TIME = "chronometer_old_time";
    public static final String KEY_CHRONOMETER_RUNNING = "chronometer_running";
    public static final String KEY_BUTTON_TEXT = "button_start_stop";
    public static final String KEY_CHRONOMETER_TEXT = "chronometer_text";
    private Button start_stop;
    private Button reset;
    private Chronometer time;
    private long old_base = 0;
    boolean running = false;

    //Log Priority (lowest to highest)
    //Verbose, Debug, Info, Warning, Error, Assert
    //Log.v    Log.d  Log.i Log.w    Log.e
    //App launched = onCreate, onStart, onResume
    //Phone rotated = onPause, onStop, onDestroy, onCreate, onStart, onResume
    //Square button = onPause, onStop
    //Back button (after square) = onStart, onResume
    //Circle Button = onPause, onStop
    //Relaunch app = onStart, onResume
    //Hit back = onPause, onStop, onDestroy

    public void setListeners() {

        start_stop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (old_base == 0) {
                    old_base = SystemClock.elapsedRealtime();
                    time.setBase(SystemClock.elapsedRealtime());
                }

                if (!running) {
                    time.setBase(SystemClock.elapsedRealtime() - old_base + time.getBase());
                    time.start();
                    start_stop.setText(getString(R.string.main_stop));
                    running = true;
                } else {
                    old_base = SystemClock.elapsedRealtime();
                    time.stop();
                    start_stop.setText(getString(R.string.main_start));
                    running = false;
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time.stop();
                time.setBase(SystemClock.elapsedRealtime());
                old_base = SystemClock.elapsedRealtime();
                start_stop.setText(getString(R.string.main_start));
                running = false;
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Log.d(TAG, "onCreate: ");
        wireWidgets();
        setListeners();
        if(savedInstanceState != null) {
            if(time.getBase() != 0) {
                time.setBase(savedInstanceState.getLong(KEY_CHRONOMETER_BASE));
                old_base = savedInstanceState.getLong(KEY_CHRONOMETER_OLD_TIME);
                time.setText(savedInstanceState.getString(KEY_CHRONOMETER_TEXT));
            }
            running = savedInstanceState.getBoolean(KEY_CHRONOMETER_RUNNING);
            start_stop.setText(savedInstanceState.getString(KEY_BUTTON_TEXT));
            if(running) {
                time.start();
            }
        }

        //if saved saved instance state is null
        //pull out value saved from bundle
        //set chronometer's base to that value
        //start chronometer(if running)
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Log.d(TAG, "onResume: ");
    }
    //activity is now running

    //another activity is covering this one
    @Override
    protected void onPause() {
        super.onPause();
        //Log.d(TAG, "onPause: ");
    }

    //activity is completely covered
    @Override
    protected void onStop() {
        super.onStop();
        //Log.d(TAG, "onStop: ");
    }


    //activity is finished
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Log.d(TAG, "onDestroy: ");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_CHRONOMETER_BASE, time.getBase());
        outState.putLong(KEY_CHRONOMETER_OLD_TIME, old_base);
        outState.putBoolean(KEY_CHRONOMETER_RUNNING, running);
        outState.putString(KEY_BUTTON_TEXT, String.valueOf(start_stop.getText()));
        outState.putString(KEY_CHRONOMETER_TEXT, String.valueOf(time.getText()));
    }

    private void wireWidgets() {
        start_stop = findViewById(R.id.button_main_start_stop);
        reset = findViewById(R.id.button_main_reset);
        time = findViewById(R.id.chronometer_main_time);
    }
}
