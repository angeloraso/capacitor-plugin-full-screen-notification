package com.angeloraso.plugins.fullscreennotification;

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
    public static Boolean thereIsANotification = false;

    @PluginMethod
    public void show(PluginCall call) {
        FullScreenNotificationPlugin.app = getActivity();
        FullScreenNotificationPlugin.call = call;
        FullScreenNotificationPlugin.context = getContext();

        Intent intent = new Intent("android.intent.action.NOTIFICATION_SERVICE");
        intent.setPackage(FullScreenNotificationPlugin.app.getPackageName());
        intent.putExtra("icon", FullScreenNotificationPlugin.app.getResources().getIdentifier("icon", "drawable", FullScreenNotificationPlugin.app.getPackageName()));

        intent.putExtra("callerNameId",  FullScreenNotificationPlugin.app.getResources().getIdentifier("callerNameId", "id", FullScreenNotificationPlugin.app.getPackageName()));
        String callerName = call.hasOption("callerName") ? call.getString("callerName") : "Unknow";
        intent.putExtra("callerName", callerName);

        intent.putExtra("callerNumberId", FullScreenNotificationPlugin.app.getResources().getIdentifier("callerNumberId", "id", FullScreenNotificationPlugin.app.getPackageName()));
        String callerNumber = call.hasOption("callerNumber") ? call.getString("callerNumber") : "Unknow";
        intent.putExtra("callerNumber", callerNumber);

        final Boolean thereIsACallInProgress = call.getBoolean("thereIsACallInProgress");
        if (!thereIsACallInProgress) {
            intent.putExtra("activity_notification", FullScreenNotificationPlugin.app.getResources().getIdentifier("first_incoming_call", "layout", FullScreenNotificationPlugin.app.getPackageName()));
            intent.putExtra("declineButton", FullScreenNotificationPlugin.app.getResources().getIdentifier("declineButton", "id", FullScreenNotificationPlugin.app.getPackageName()));
            intent.putExtra("answerButton", FullScreenNotificationPlugin.app.getResources().getIdentifier("answerButton", "id", FullScreenNotificationPlugin.app.getPackageName()));

            intent.putExtra("declineButtonTextId", FullScreenNotificationPlugin.app.getResources().getIdentifier("declineButtonTextId", "id", FullScreenNotificationPlugin.app.getPackageName()));
            String declineButtonText = call.hasOption("declineButtonText") ? call.getString("declineButtonText") : "Decline";
            intent.putExtra("declineButtonText", declineButtonText);

            intent.putExtra("answerButtonTextId", FullScreenNotificationPlugin.app.getResources().getIdentifier("answerButtonTextId", "id", FullScreenNotificationPlugin.app.getPackageName()));
            String answerButtonText = call.hasOption("answerButtonText") ? call.getString("answerButtonText") : "Answer";
            intent.putExtra("answerButtonText", answerButtonText);
        } else {
            intent.putExtra("activity_notification", FullScreenNotificationPlugin.app.getResources().getIdentifier("second_incoming_call", "layout", FullScreenNotificationPlugin.app.getPackageName()));
            intent.putExtra("terminateButton", FullScreenNotificationPlugin.app.getResources().getIdentifier("terminateButton", "id", FullScreenNotificationPlugin.app.getPackageName()));
            intent.putExtra("declineButton", FullScreenNotificationPlugin.app.getResources().getIdentifier("declineButton", "id", FullScreenNotificationPlugin.app.getPackageName()));
            intent.putExtra("answerButton", FullScreenNotificationPlugin.app.getResources().getIdentifier("answerButton", "id", FullScreenNotificationPlugin.app.getPackageName()));

            intent.putExtra("finishAndAnswerButtonTextId", FullScreenNotificationPlugin.app.getResources().getIdentifier("finishAndAnswerButtonTextId", "id", FullScreenNotificationPlugin.app.getPackageName()));
            String finishAndAnswerButtonText = call.hasOption("finishAndAnswerButtonText") ? call.getString("finishAndAnswerButtonText") : "Finish and answer";
            intent.putExtra("finishAndAnswerButtonText", finishAndAnswerButtonText);

            intent.putExtra("declineSecondCallButtonTextId", FullScreenNotificationPlugin.app.getResources().getIdentifier("declineSecondCallButtonTextId", "id", FullScreenNotificationPlugin.app.getPackageName()));
            String declineSecondCallButtonText = call.hasOption("declineSecondCallButtonText") ? call.getString("declineSecondCallButtonText") : "Decline";
            intent.putExtra("declineSecondCallButtonText", declineSecondCallButtonText);

            intent.putExtra("holdAndAnswerButtonTextId", FullScreenNotificationPlugin.app.getResources().getIdentifier("holdAndAnswerButtonTextId", "id", FullScreenNotificationPlugin.app.getPackageName()));
            String holdAndAnswerButtonText = call.hasOption("holdAndAnswerButtonText") ? call.getString("holdAndAnswerButtonText") : "Hold and answer";
            intent.putExtra("holdAndAnswerButtonText", holdAndAnswerButtonText);
        }

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

    public static void notificationAction(String action) {
        boolean validAction = true;
        JSObject output = new JSObject();

        switch (action) {
            case "answer":
                output.put("data", "answer");
                FullScreenNotificationPlugin.call.resolve(output);
                openApp();
                break;
            case "decline":
                output.put("data", "decline");
                FullScreenNotificationPlugin.call.resolve(output);
                break;
            case "terminate":
                output.put("data", "terminate");
                FullScreenNotificationPlugin.call.resolve(output);
                break;
            case "tap":
                openApp();
                break;
            default:
                validAction = false;
        }

        if (!validAction) {
            FullScreenNotificationPlugin.call.errorCallback("Invalid action: " + action);
        }
    }

    public static void openApp() {
        Intent intent = FullScreenNotificationPlugin.context.getPackageManager().getLaunchIntentForPackage(FullScreenNotificationPlugin.context.getPackageName());
        FullScreenNotificationPlugin.app.startActivity(intent);
    }

}
