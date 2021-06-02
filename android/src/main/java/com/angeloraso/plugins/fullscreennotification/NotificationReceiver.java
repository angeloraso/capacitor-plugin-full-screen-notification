package com.angeloraso.plugins.fullscreennotification;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getStringExtra("action");
        FullScreenNotificationPlugin.notificationAction(action);
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationService.that.stopForeground(Service.STOP_FOREGROUND_REMOVE);
            FullScreenNotificationPlugin.thereIsANotification = false;
        }
    }
}