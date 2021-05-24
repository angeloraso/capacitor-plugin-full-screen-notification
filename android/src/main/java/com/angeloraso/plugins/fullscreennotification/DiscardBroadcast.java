package com.angeloraso.plugins.fullscreennotification;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class DiscardBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        FullScreenNotificationPlugin.discard();
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationService.that.stopForeground(Service.STOP_FOREGROUND_REMOVE);
        }
    }
}
