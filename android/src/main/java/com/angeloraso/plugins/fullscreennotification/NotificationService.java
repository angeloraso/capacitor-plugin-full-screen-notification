package com.angeloraso.plugins.fullscreennotification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class NotificationService extends Service {

  public static Service that = null;
  public static int notificationId = 0;

  @Override
  public int onStartCommand (Intent intent, int flags, int startId) {
    NotificationService.notificationId++;
    NotificationService.that = this;
    RemoteViews customView;
    final Intent notificationIntent = new Intent("android.intent.action.NOTIFICATION_ACTIVITY");
    final Intent declineIntent = new Intent(this, DeclineReceiver.class);
    final Intent answerIntent = new Intent(this, AnswerReceiver.class);
    final Intent terminateIntent = new Intent(this, TerminateReceiver.class);
    final Intent declineSecondCallIntent = new Intent(this, DeclineReceiver.class);
    final Intent holdAndAnswerIntent = new Intent(this, AnswerReceiver.class);
    final Intent tapIntent = new Intent(this, TapReceiver.class);

    final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    final PendingIntent declinePendingIntent = PendingIntent.getBroadcast(this, 0, declineIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    final PendingIntent answerPendingIntent = PendingIntent.getBroadcast(this, 0, answerIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    final PendingIntent terminatePendingIntent = PendingIntent.getBroadcast(this, 0, terminateIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    final PendingIntent declineSecondCallPendingIntent = PendingIntent.getBroadcast(this, 0, declineSecondCallIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    final PendingIntent holdAndAnswerPendingIntent = PendingIntent.getBroadcast(this, 0, holdAndAnswerIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    final PendingIntent tapPendingIntent = PendingIntent.getBroadcast(this, 0, tapIntent, PendingIntent.FLAG_UPDATE_CURRENT);

    if (intent.hasExtra("firstIncomingCall")) {
      customView = new RemoteViews(intent.getPackage(), intent.getIntExtra("firstIncomingCall", 0));
      customView.setImageViewResource(intent.getIntExtra("firstCallLogoId", 0), intent.getIntExtra("firstCallLogo", 0));
      customView.setTextViewText(intent.getIntExtra("callerNameId", 0), intent.getStringExtra("callerName"));
      customView.setTextViewText(intent.getIntExtra("callerNumberId", 0), intent.getStringExtra("callerNumber"));
      customView.setOnClickPendingIntent(intent.getIntExtra("declineButtonId", 0), declinePendingIntent);
      customView.setOnClickPendingIntent(intent.getIntExtra("answerButtonId", 0), answerPendingIntent);
      customView.setTextViewText(intent.getIntExtra("declineButtonTextId", 0), intent.getStringExtra("declineButtonText"));
      customView.setTextViewText(intent.getIntExtra("answerButtonTextId", 0), intent.getStringExtra("answerButtonText"));
    } else {
      customView = new RemoteViews(intent.getPackage(), intent.getIntExtra("secondIncomingCall", 0));
      customView.setImageViewResource(intent.getIntExtra("secondCallLogoId", 0), intent.getIntExtra("secondCallLogo", 0));
      customView.setOnClickPendingIntent(intent.getIntExtra("finishAndAnswerFrontButtonId", 0), terminatePendingIntent);
      customView.setOnClickPendingIntent(intent.getIntExtra("declineSecondCallButtonId", 0), declineSecondCallPendingIntent);
      customView.setOnClickPendingIntent(intent.getIntExtra("holdAndAnswerFrontButtonId", 0), holdAndAnswerPendingIntent);
      customView.setTextViewText(intent.getIntExtra("finishAndAnswerButtonTextId", 0), intent.getStringExtra("finishAndAnswerButtonText"));
      customView.setTextViewText(intent.getIntExtra("declineSecondCallButtonTextId", 0), intent.getStringExtra("declineSecondCallButtonText"));
      customView.setTextViewText(intent.getIntExtra("holdAndAnswerButtonTextId", 0), intent.getStringExtra("holdAndAnswerButtonText"));
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      // Creating channel for notification (necessary for api 26 and later)
      String CHANNEL_ID = "full-screen-notification";
      CharSequence CHANNEL_NAME = intent.getStringExtra("channelName");
      String CHANNEL_DESCRIPTION = intent.getStringExtra("channelDescription");
      // Notification channel is assigned IMPORTANCE_HIGH to show as popup for api 26 onwards
      int CHANNEL_IMPORTANCE = NotificationManager.IMPORTANCE_HIGH;
      final long[] DEFAULT_VIBRATE_PATTERN = {0, 250, 250, 250};
      final NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, CHANNEL_IMPORTANCE);
      notificationChannel.setDescription(CHANNEL_DESCRIPTION);
      notificationChannel.enableVibration(true);
      notificationChannel.setVibrationPattern(DEFAULT_VIBRATE_PATTERN);
      notificationChannel.setSound(Settings.System.DEFAULT_NOTIFICATION_URI, null);
      // Register the channel with the system; you can't change the importance or other notification behaviors after this
      final NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
      notificationManager.createNotificationChannel(notificationChannel);

      final NotificationCompat.Builder notification = new NotificationCompat.Builder(this, CHANNEL_ID);
      notification.setContentTitle(intent.getStringExtra("channelName"));
      notification.setContentText(intent.getStringExtra("name") + " - " + intent.getStringExtra("number"));
      notification.setTicker("Call_STATUS");
      // To not show the notification schedule. Does not provide useful information to the user
      notification.setShowWhen(false);
      notification.setSmallIcon(R.drawable.answer_24);
      notification.setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND);
      // To know if it is necessary to disturb the user with a notification despite having activated the "Do not interrupt" mode
      notification.setCategory(NotificationCompat.CATEGORY_CALL);
      notification.setDefaults(Notification.DEFAULT_ALL);
      // VISIBILITY_PUBLIC displays the full content of the notification
      notification.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
      notification.setOngoing(true);
      // Supply a PendingIntent to be sent when the notification is clicked.
      notification.setContentIntent(tapPendingIntent);
      notification.setFullScreenIntent(pendingIntent, true);
      // The notification is assigned PRIORITY_MAX to show as a popup for api 25 and earlier
      notification.setPriority(NotificationCompat.PRIORITY_MAX);
      notification.setCustomContentView(customView);
      notification.setCustomBigContentView(customView);

      startForeground(NotificationService.notificationId, notification.build());
    }

    return flags;
  }

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }
}
