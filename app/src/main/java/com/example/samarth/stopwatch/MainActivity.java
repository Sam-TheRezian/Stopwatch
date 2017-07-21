package com.example.samarth.stopwatch;

import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView displayText;
    Button start, pause, reset, lap;
    ListView myList;
    Handler handler;
    String[] listElements = new String[]{};
    List<String> elementsArrayList;
    ArrayAdapter<String> myAdapter;
    int mins, secs, millisecs;
    long millisecTime, startTime, timeBuff, updateTime = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayText = (TextView)findViewById(R.id.displayText);
        start = (Button)findViewById(R.id.start);
        pause = (Button)findViewById(R.id.pause);
        reset = (Button)findViewById(R.id.reset);
        lap = (Button)findViewById(R.id.lap);
        myList = (ListView)findViewById(R.id.myList);

        handler = new Handler();

        elementsArrayList = new ArrayList<String>(Arrays.asList(listElements));
        myAdapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,elementsArrayList);
        myList.setAdapter(myAdapter);
    }

    public void onStart(View view){
        startTime = SystemClock.uptimeMillis();
        handler.postDelayed(runnable,0);
        reset.setEnabled(false);
    }

    public void onPause(View view){
        timeBuff += millisecTime;
        handler.removeCallbacks(runnable);
        reset.setEnabled(true);
    }

    public void onReset(View view){
        millisecTime = 0L;
        startTime = 0L;
        timeBuff = 0L;
        updateTime = 0L;
        mins = 0;
        secs = 0;
        millisecs = 0;

        displayText.setText("00:00:00");
        elementsArrayList.clear();
        myAdapter.notifyDataSetChanged();
    }

    public void onLap(View view){
        elementsArrayList.add(displayText.getText().toString());
        myAdapter.notifyDataSetChanged();
    }

    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            millisecTime = SystemClock.uptimeMillis() - startTime;
            updateTime = timeBuff + millisecTime;
            secs = (int)updateTime/1000;
            mins = secs/60;
            secs = secs%60;
            millisecs = (int)updateTime%1000;
            displayText.setText("" + mins + ":" + String.format("%2d",secs) + ":" + String.format("%3d",millisecs));
            handler.postDelayed(this,0);
        }
    };
}
















