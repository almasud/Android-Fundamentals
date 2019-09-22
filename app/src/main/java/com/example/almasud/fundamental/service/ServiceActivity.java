package com.example.almasud.fundamental.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.almasud.fundamental.R;
import com.example.almasud.fundamental.service.bound_service.MyBoundService;
import com.example.almasud.fundamental.service.intent_service.MyIntentService;
import com.example.almasud.fundamental.service.started_service.BackgroundAudioService;
import com.example.almasud.fundamental.service.started_service.MyStartedService;

public class ServiceActivity extends AppCompatActivity implements View.OnClickListener {

    Button startPlyBtn, stopPlayBtn;
    Intent playServiceIntent;
    private MyBoundService boundService;
    private Boolean isBound;

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyBoundService.MyLocalBinder localBinder
                    = (MyBoundService.MyLocalBinder) service;
            boundService = localBinder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
            boundService = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        // Play and stop a music using started service.
        startPlyBtn = findViewById(R.id.start_play_btn);
        stopPlayBtn = findViewById(R.id.stop_play_btn);
        startPlyBtn.setOnClickListener(this);
        stopPlayBtn.setOnClickListener(this);
        playServiceIntent = new Intent(ServiceActivity.this,
                BackgroundAudioService.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Bind service start with activity.
        Intent intent = new Intent(ServiceActivity.this, MyBoundService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(connection);
    }

    @Override
    public void onClick(View v) {
        if (v == startPlyBtn) {
            startService(playServiceIntent);
            finish(); // Exit from current activity
        } else if (v == stopPlayBtn) {
            stopService(playServiceIntent);
            finish(); // Exit from current activity
        }
    }

    // Start started service
    public void startStartedService(View view) {
        startService(new Intent(ServiceActivity.this, MyStartedService.class)
                .putExtra("msg", "Message from service activity"));
    }

    // Stop started service
    public void stopStartedService(View view) {
        stopService(new Intent(ServiceActivity.this, MyStartedService.class));
    }

    // Start intent service
    public void startIntentService(View view) {
        startService(new Intent(ServiceActivity.this, MyIntentService.class).putExtra("msg", "Message from intent service"));
    }

    // Start bound service
    public void getRandomNumber(View view) {
        if (isBound) {
            int random = boundService.generateRandomNumber();
            Toast.makeText(this, "Random: " + random, Toast.LENGTH_SHORT).show();
        }
    }

}
