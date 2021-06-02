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

        intent.putExtra("callerNameId",  this.getLayoutElementId("callerNameId"));
        String callerName = call.hasOption("callerName") ? call.getString("callerName") : "Unknow";
        intent.putExtra("callerName", callerName);

        intent.putExtra("callerNumberId", this.getLayoutElementId("callerNumberId"));
        String callerNumber = call.hasOption("callerNumber") ? call.getString("callerNumber") : "Unknow";
        intent.putExtra("callerNumber", callerNumber);

        final Boolean thereIsACallInProgress = call.getBoolean("thereIsACallInProgress");
        if (!thereIsACallInProgress) {
            intent.putExtra("activity_notification", FullScreenNotificationPlugin.app.getResources().getIdentifier("first_incoming_call", "layout", FullScreenNotificationPlugin.app.getPackageName()));
            intent.putExtra("declineButton", this.getLayoutElementId("declineButton"));
            intent.putExtra("answerButton", this.getLayoutElementId("answerButton"));

            intent.putExtra("declineButtonTextId", this.getLayoutElementId("declineButtonText"));
            String declineButtonText = call.hasOption("declineButtonText") ? call.getString("declineButtonText") : "Decline";
            intent.putExtra("declineButtonText", declineButtonText);

            intent.putExtra("answerButtonTextId", this.getLayoutElementId("answerButtonText"));
            String answerButtonText = call.hasOption("answerButtonText") ? call.getString("answerButtonText") : "Answer";
            intent.putExtra("answerButtonText", answerButtonText);
        } else {
            intent.putExtra("activity_notification", FullScreenNotificationPlugin.app.getResources().getIdentifier("second_incoming_call", "layout", FullScreenNotificationPlugin.app.getPackageName()));
            intent.putExtra("terminateButton", this.getLayoutElementId("finishAndAnswerButton"));
            intent.putExtra("declineButton", this.getLayoutElementId("declineSecondCallButton"));
            intent.putExtra("answerButton", this.getLayoutElementId("holdAndAnswerButton"));

            intent.putExtra("finishAndAnswerButtonTextId", this.getLayoutElementId("declineButtonText"));
            String finishAndAnswerButtonText = call.hasOption("finishAndAnswerButtonText") ? call.getString("finishAndAnswerButtonText") : "Finish and answer";
            intent.putExtra("finishAndAnswerButtonText", finishAndAnswerButtonText);

            intent.putExtra("declineSecondCallButtonTextId", this.getLayoutElementId("declineButtonText"));
            String declineSecondCallButtonText = call.hasOption("declineSecondCallButtonText") ? call.getString("declineSecondCallButtonText") : "Decline";
            intent.putExtra("declineSecondCallButtonText", declineSecondCallButtonText);

            intent.putExtra("holdAndAnswerButtonTextId", this.getLayoutElementId("declineButtonText"));
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

    private int getLayoutElementId(String name) {
        return FullScreenNotificationPlugin.app.getResources().getIdentifier(name, "id", FullScreenNotificationPlugin.app.getPackageName());
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
            case "click":
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
