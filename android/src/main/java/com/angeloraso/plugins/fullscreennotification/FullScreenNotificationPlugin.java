package com.angeloraso.plugins.fullscreennotification;

import android.app.NotificationManager;
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
    private static String _package;
    public static Boolean thereIsANotification = false;

    @PluginMethod
    public void show(PluginCall call) {
        FullScreenNotificationPlugin.app = getActivity();
        FullScreenNotificationPlugin.call = call;
        FullScreenNotificationPlugin.context = getContext();
        FullScreenNotificationPlugin._package = FullScreenNotificationPlugin.app.getPackageName();


        Intent intent = new Intent("android.intent.action.NOTIFICATION_SERVICE");
        intent.setPackage(FullScreenNotificationPlugin._package);
        intent.putExtra("icon", FullScreenNotificationPlugin.app.getResources().getIdentifier("icon", "drawable", FullScreenNotificationPlugin._package));

        String channelName = call.hasOption("channelName") ? call.getString("channelName") : "Incoming call";
        intent.putExtra("channelName", channelName);
        String channelDescription = call.hasOption("channelDescription") ? call.getString("channelDescription") : "Incoming call notification";
        intent.putExtra("channelDescription", channelDescription);

        intent.putExtra("callerNameId",  FullScreenNotificationPlugin.app.getResources().getIdentifier("callerNameId", "id", FullScreenNotificationPlugin._package));
        String callerName = call.hasOption("callerName") ? call.getString("callerName") : "Unknown";
        intent.putExtra("callerName", callerName);

        intent.putExtra("callerNumberId", FullScreenNotificationPlugin.app.getResources().getIdentifier("callerNumberId", "id", FullScreenNotificationPlugin._package));
        String callerNumber = call.hasOption("callerNumber") ? call.getString("callerNumber") : "Unknown";
        intent.putExtra("callerNumber", callerNumber);

        final Boolean thereIsACallInProgress = call.getBoolean("thereIsACallInProgress");
        if (!thereIsACallInProgress) {
            intent.putExtra("firstIncomingCall", FullScreenNotificationPlugin.app.getResources().getIdentifier("first_incoming_call", "layout", FullScreenNotificationPlugin._package));
            intent.putExtra("declineButtonId", FullScreenNotificationPlugin.app.getResources().getIdentifier("declineButtonId", "id", FullScreenNotificationPlugin._package));
            intent.putExtra("answerButtonId", FullScreenNotificationPlugin.app.getResources().getIdentifier("answerButtonId", "id", FullScreenNotificationPlugin._package));

            intent.putExtra("declineButtonTextId", FullScreenNotificationPlugin.app.getResources().getIdentifier("declineButtonTextId", "id", FullScreenNotificationPlugin._package));
            String declineButtonText = call.hasOption("declineButtonText") ? call.getString("declineButtonText") : "Decline";
            intent.putExtra("declineButtonText", declineButtonText);

            intent.putExtra("answerButtonTextId", FullScreenNotificationPlugin.app.getResources().getIdentifier("answerButtonTextId", "id", FullScreenNotificationPlugin._package));
            String answerButtonText = call.hasOption("answerButtonText") ? call.getString("answerButtonText") : "Answer";
            intent.putExtra("answerButtonText", answerButtonText);
        } else {
            intent.putExtra("secondIncomingCall", FullScreenNotificationPlugin.app.getResources().getIdentifier("second_incoming_call", "layout", FullScreenNotificationPlugin._package));
            intent.putExtra("finishAndAnswerBackButtonId", FullScreenNotificationPlugin.app.getResources().getIdentifier("finishAndAnswerBackButtonId", "id", FullScreenNotificationPlugin._package));
            intent.putExtra("finishAndAnswerFrontButtonId", FullScreenNotificationPlugin.app.getResources().getIdentifier("finishAndAnswerFrontButtonId", "id", FullScreenNotificationPlugin._package));
            intent.putExtra("declineSecondCallButtonId", FullScreenNotificationPlugin.app.getResources().getIdentifier("declineSecondCallButtonId", "id", FullScreenNotificationPlugin._package));
            intent.putExtra("holdAndAnswerBackButtonId", FullScreenNotificationPlugin.app.getResources().getIdentifier("holdAndAnswerBackButtonId", "id", FullScreenNotificationPlugin._package));
            intent.putExtra("holdAndAnswerFrontButtonId", FullScreenNotificationPlugin.app.getResources().getIdentifier("holdAndAnswerFrontButtonId", "id", FullScreenNotificationPlugin._package));

            intent.putExtra("finishAndAnswerButtonTextId", FullScreenNotificationPlugin.app.getResources().getIdentifier("finishAndAnswerButtonTextId", "id", FullScreenNotificationPlugin._package));
            String finishAndAnswerButtonText = call.hasOption("finishAndAnswerButtonText") ? call.getString("finishAndAnswerButtonText") : "Finish and answer";
            intent.putExtra("finishAndAnswerButtonText", finishAndAnswerButtonText);

            intent.putExtra("declineSecondCallButtonTextId", FullScreenNotificationPlugin.app.getResources().getIdentifier("declineSecondCallButtonTextId", "id", FullScreenNotificationPlugin._package));
            String declineSecondCallButtonText = call.hasOption("declineSecondCallButtonText") ? call.getString("declineSecondCallButtonText") : "Decline";
            intent.putExtra("declineSecondCallButtonText", declineSecondCallButtonText);

            intent.putExtra("holdAndAnswerButtonTextId", FullScreenNotificationPlugin.app.getResources().getIdentifier("holdAndAnswerButtonTextId", "id", FullScreenNotificationPlugin._package));
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
      FullScreenNotificationPlugin.dismissNotification();
      JSObject output = new JSObject();
      output.put("data", "success");
      call.resolve(output);
    }

    public static void notificationAction(String action) {
      FullScreenNotificationPlugin.dismissNotification();
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
                openApp();
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

    private static void dismissNotification() {
      if (Build.VERSION.SDK_INT >= 26 && NotificationService.that != null) {
        NotificationManager notifManager = (NotificationManager) FullScreenNotificationPlugin.context.getSystemService(Context.NOTIFICATION_SERVICE);
        notifManager.cancelAll();
        NotificationService.that.stopForeground(FullScreenNotificationPlugin.thereIsANotification);
        FullScreenNotificationPlugin.thereIsANotification = false;
      }
    }

}
