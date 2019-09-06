package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    private Button start_stop;
    private Button reset;
    private Chronometer time;

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

    public void setListeners(){
        start_stop.setOnClickListener(new View.OnClickListener() {
            boolean running = false;
            long old_base;
            long new_base;
            @Override
            public void onClick(View view) {
                if(!running) {
                    new_base = time.getBase();
                    running = true;
                    time.start();
                    start_stop.setText("Stop");
                }
                else {
                    old_base = time.getBase();
                    time.stop();
                    start_stop.setText("Start");
                    running = false;
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: ");
        wireWidgets();
        setListeners();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }
    //activity is now running

    //another activity is covering this one
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    //activity is completely covered
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }


    //activity is finished
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    private void wireWidgets() {
        start_stop = findViewById(R.id.button_main_start_stop);
        reset = findViewById(R.id.button_main_reset);
        time = findViewById(R.id.chronometer_main_time);
    }
}
