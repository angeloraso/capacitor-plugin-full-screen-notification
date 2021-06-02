package com.angeloraso.plugins.fullscreennotification;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class TerminateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        FullScreenNotificationPlugin.notificationAction("terminate");
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationService.that.stopForeground(Service.STOP_FOREGROUND_REMOVE);
            FullScreenNotificationPlugin.thereIsANotification = false;
        }
    }
}