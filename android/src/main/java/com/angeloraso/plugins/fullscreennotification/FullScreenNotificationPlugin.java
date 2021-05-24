package com.angeloraso.plugins.fullscreennotification;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "FullScreenNotification")
public class FullScreenNotificationPlugin extends Plugin {

    private static PluginCall call;
    private static Context context;
    private static AppCompatActivity app;

    private FullScreenNotification implementation = new FullScreenNotification();

    @PluginMethod
    public void show(PluginCall call) {
        FullScreenNotificationPlugin.app = getActivity();
        FullScreenNotificationPlugin.call = call;
        FullScreenNotificationPlugin.context = getContext();

        Intent intent = new Intent("android.intent.action.NOTIFICATION_SERVICE");
        intent.setPackage(FullScreenNotificationPlugin.app.getPackageName());
        intent.putExtra("activity_notification", FullScreenNotificationPlugin.app.getResources().getIdentifier("activity_notification", "layout", FullScreenNotificationPlugin.app.getPackageName()));
        intent.putExtra("caller_text", "Caller Test");
        intent.putExtra("callType", FullScreenNotificationPlugin.app.getResources().getIdentifier("callType", "id", FullScreenNotificationPlugin.app.getPackageName()));
        intent.putExtra("btnAnswer", FullScreenNotificationPlugin.app.getResources().getIdentifier("btnAnswer", "id", FullScreenNotificationPlugin.app.getPackageName()));
        intent.putExtra("btnDecline", FullScreenNotificationPlugin.app.getResources().getIdentifier("btnDecline", "id", FullScreenNotificationPlugin.app.getPackageName()));
        intent.putExtra("icon", FullScreenNotificationPlugin.app.getResources().getIdentifier("icon", "drawable", FullScreenNotificationPlugin.app.getPackageName()));
        if (Build.VERSION.SDK_INT >= 26) {
            FullScreenNotificationPlugin.app.startForegroundService(intent);
        }
    }

    @PluginMethod
    public void hide() {
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationService.that.stopForeground(Service.STOP_FOREGROUND_REMOVE);
        }
    }

    public static void answer() {
        JSObject output = new JSObject();
        output.put("data", "answer");
        FullScreenNotificationPlugin.call.resolve(output);
        Intent intent = FullScreenNotificationPlugin.context.getPackageManager().getLaunchIntentForPackage(FullScreenNotificationPlugin.context.getPackageName());
        FullScreenNotificationPlugin.app.startActivity(intent);
    }


    public static void discard() {
        JSObject output = new JSObject();
        output.put("data", "discard");
        FullScreenNotificationPlugin.call.resolve(output);
    }

}
