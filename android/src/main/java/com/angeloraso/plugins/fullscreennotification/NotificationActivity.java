package com.angeloraso.plugins.fullscreennotification;

import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_incoming_call);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED| WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        getWindow().setStatusBarColor(Color.parseColor("#20111111"));
        getWindow().setNavigationBarColor(Color.parseColor("#20111111"));
    }
}
