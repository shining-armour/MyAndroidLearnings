package com.example.broadcast_receiver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    // Custom Broadcast Receiver parameters
    private static final String ACTION_CUSTOM_BROADCAST = BuildConfig.APPLICATION_ID + ".ACTION_CUSTOM_BROADCAST";
    private IntentFilter filter2 = new IntentFilter(ACTION_CUSTOM_BROADCAST);
    Button sendLocalBroadcast;

    // System Broadcast Receiver parameters
    private MyReceiver myReceiver = new MyReceiver();
    // Intent filters specify the types of intents a component can receive.
    private IntentFilter filter1 = new IntentFilter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendLocalBroadcast = findViewById(R.id.sendBroadcast);
        sendLocalBroadcast.setOnClickListener(this);

        // When the system receives an Intent as a broadcast, it searches the broadcast receivers based on the action value specified in the IntentFilter object.
        filter1.addAction(Intent.ACTION_POWER_DISCONNECTED);
        filter1.addAction(Intent.ACTION_POWER_CONNECTED);
        filter1.addAction(Intent.ACTION_HEADSET_PLUG);
        filter1.addAction(Intent.ACTION_BATTERY_LOW);
        filter1.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);

        // Registering receiver to receive System Broadcasts
        this.registerReceiver(myReceiver, filter1);

        // Registering receiver to receive Custom Broadcasts
        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver, filter2);
    }

    @Override
    protected void onDestroy() {
        // Unregistering from System Broadcast
        this.unregisterReceiver(myReceiver);
        // Unregistering from Custom Broadcast
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myReceiver);

        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        // Make a custom intent object with String action as app's package name followed by custom action name
        Intent customBroadcastIntent = new Intent(ACTION_CUSTOM_BROADCAST);
        // Send the broadcast locally within the app
        LocalBroadcastManager.getInstance(this).sendBroadcast(customBroadcastIntent);
    }
}