package com.example.broadcast_receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    // Custom Broadcast Receiver
    private static final String ACTION_CUSTOM_BROADCAST = BuildConfig.APPLICATION_ID + ".ACTION_CUSTOM_BROADCAST";

    @Override
    public void onReceive(Context context, Intent intent) {
        String intentAction = intent.getAction();

        if(intentAction!=null){
            String toastMsg = "Unknown Intent Action";
            switch (intentAction){
                // System Broadcast Intent Actions
                case Intent.ACTION_POWER_CONNECTED:
                    toastMsg = "Power Connected";
                    break;
                case Intent.ACTION_POWER_DISCONNECTED:
                    toastMsg = "Power Disconnected";
                    break;
                case Intent.ACTION_HEADSET_PLUG:
                    int headsetState = intent.getIntExtra("state", -1);
                    switch (headsetState){
                        case 0:
                            toastMsg = "Headset Unplugged";
                            break;
                        case 1:
                            toastMsg = "Headset Plugged";
                            break;
                    }
                    break;
                case Intent.ACTION_AIRPLANE_MODE_CHANGED:
                    boolean airplaneState = intent.getBooleanExtra("state", true);
                    if(airplaneState){
                        toastMsg = "Airplane mode on";
                    } else {
                        toastMsg = "Airplane mode off";
                    }
                    break;
                // Custom Broadcast Intent Actions
                case ACTION_CUSTOM_BROADCAST:
                    toastMsg = "Custom Broadcast Received";
            }
            Toast.makeText(context, toastMsg, Toast.LENGTH_SHORT).show();
        }

    }
}