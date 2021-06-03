package com.angeloraso.plugins.fullscreennotification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AnswerReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        FullScreenNotificationPlugin.notificationAction("answer");
    }
}