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
    private static Boolean thereIsANotification = false;

    private FullScreenNotification implementation = new FullScreenNotification();

    @PluginMethod
    public void show(PluginCall call) {
        FullScreenNotificationPlugin.app = getActivity();
        FullScreenNotificationPlugin.call = call;
        FullScreenNotificationPlugin.context = getContext();

        Intent intent = new Intent("android.intent.action.NOTIFICATION_SERVICE");
        intent.setPackage(FullScreenNotificationPlugin.app.getPackageName());
        intent.putExtra("activity_notification", FullScreenNotificationPlugin.app.getResources().getIdentifier("activity_notification", "layout", FullScreenNotificationPlugin.app.getPackageName()));
        intent.putExtra("name", call.getString("name"));
        intent.putExtra("number", call.getString("number"));
        intent.putExtra("nameText", FullScreenNotificationPlugin.app.getResources().getIdentifier("nameText", "id", FullScreenNotificationPlugin.app.getPackageName()));
        intent.putExtra("numberText", FullScreenNotificationPlugin.app.getResources().getIdentifier("numberText", "id", FullScreenNotificationPlugin.app.getPackageName()));
        intent.putExtra("btnAnswer", FullScreenNotificationPlugin.app.getResources().getIdentifier("btnAnswer", "id", FullScreenNotificationPlugin.app.getPackageName()));
        intent.putExtra("btnDiscard", FullScreenNotificationPlugin.app.getResources().getIdentifier("btnDiscard", "id", FullScreenNotificationPlugin.app.getPackageName()));
        intent.putExtra("icon", FullScreenNotificationPlugin.app.getResources().getIdentifier("icon", "drawable", FullScreenNotificationPlugin.app.getPackageName()));
        if (Build.VERSION.SDK_INT >= 26) {
            FullScreenNotificationPlugin.app.startForegroundService(intent);
            FullScreenNotificationPlugin.thereIsANotification = true;
        }
    }

    @PluginMethod
    public void hide(PluginCall call) {
        if (Build.VERSION.SDK_INT >= 26 && NotificationService.that != null) {
            NotificationService.that.stopForeground(FullScreenNotificationPlugin.thereIsANotification);
            FullScreenNotificationPlugin.thereIsANotification = false;
        }
        JSObject output = new JSObject();
        output.put("data", "success");
        call.resolve(output);
    }

    public static void answer() {
        FullScreenNotificationPlugin.thereIsANotification = false;
        JSObject output = new JSObject();
        output.put("data", "answer");
        FullScreenNotificationPlugin.call.resolve(output);
        FullScreenNotificationPlugin.openApp();
    }


    public static void discard() {
        FullScreenNotificationPlugin.thereIsANotification = false;
        JSObject output = new JSObject();
        output.put("data", "discard");
        FullScreenNotificationPlugin.call.resolve(output);
    }

    public static void openApp() {
        Intent intent = FullScreenNotificationPlugin.context.getPackageManager().getLaunchIntentForPackage(FullScreenNotificationPlugin.context.getPackageName());
        FullScreenNotificationPlugin.app.startActivity(intent);
    }

}
