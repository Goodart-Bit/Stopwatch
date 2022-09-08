package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    //Is stopwatch running?
    private boolean running;
    //Stopwatch counter
    private int seconds=0;
    private int remaining_laps=0;
    private ArrayList<String> lap_times;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        runTimer();
    }

    public void onClickStart(View view) {
        running=true;
    }

    public void onClickStop(View view) { running=false; }

    public void onClickReset(View view) {
        running=false;
        seconds=0;
    }

    public void onClickTime(View view) {
        Button five_laps = (Button) findViewById(R.id.time_button);
        TextView laps = (TextView) findViewById(R.id.laps);
        if(five_laps.getText().equals(getString(R.string.lap)) && remaining_laps > 0){
            int hours=seconds/3600;
            int minutes=(seconds%3600)/60;
            String time=String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, seconds);
            lap_times.add(time);
            laps.setText(laps.getText() + "\n" + lap_times.get(lap_times.size() - 1));
            seconds = 0;
            if(--remaining_laps == 0){
                five_laps.setText(R.string.time_five_laps);
                running=false;
            }
            return;
        }
        seconds=0;
        lap_times = new ArrayList<String>();
        laps.setText(getString(R.string.laps_time));
        five_laps.setText(getString(R.string.lap));
        remaining_laps = 5;
        running=true;
    }

    private void runTimer(){
        //get TextView from Layout
        TextView timeView=(TextView) findViewById(R.id.time_view);
        //Handler for time management execution thread
        Handler handler=new Handler();
        handler.post(new Runnable() {
            public int hours;
            public int minutes;
            @Override
            public void run() {
                hours=seconds/3600;
                minutes=(seconds%3600)/60;
                String time=String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, seconds);
                timeView.setText(time);
                if(running) {
                    seconds++;
                }
                handler.postDelayed(this,1000);
            }
        });
    }
}