package com.angeloraso.plugins.fullscreennotification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class TapBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
      FullScreenNotificationPlugin.openApp();
    }
}
