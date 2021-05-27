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
import android.os.PowerManager;
import android.provider.Settings;
import android.view.WindowManager;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class NotificationService extends Service {

  public static Service that = null;

  @Override
  public int onStartCommand (Intent intent, int flags, int startId) {
    NotificationService.that = this;
    final RemoteViews customView = new RemoteViews(intent.getPackage(), intent.getIntExtra("activity_notification", 0));
    final Intent notificationIntent = new Intent("android.intent.action.NOTIFICATION_ACTIVITY");
    final Intent discardIntent = new Intent(this, DiscardBroadcast.class);
    final Intent answerIntent = new Intent(this, AnswerBroadcast.class);

    final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    final PendingIntent discardPendingIntent = PendingIntent.getBroadcast(this, 0, discardIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    final PendingIntent answerPendingIntent = PendingIntent.getBroadcast(this, 0, answerIntent, PendingIntent.FLAG_UPDATE_CURRENT);

    customView.setTextViewText(intent.getIntExtra("nameText", 0), intent.getStringExtra("name"));
    customView.setTextViewText(intent.getIntExtra("numberText", 0), intent.getStringExtra("number"));
    customView.setOnClickPendingIntent(intent.getIntExtra("btnAnswer", 0), answerPendingIntent);
    customView.setOnClickPendingIntent(intent.getIntExtra("btnDiscard", 0), discardPendingIntent);

    PowerManager powerManager = (PowerManager) this.getSystemService(POWER_SERVICE);

    final String WAKE_LOCK_TAG = "fullscreennotification::MH24_SCREENLOCK";

    // If screen is not already on, turn it on (get wake_lock for 10 seconds)
    if (!powerManager.isInteractive()){
      PowerManager.WakeLock wl = powerManager.newWakeLock(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |PowerManager.ACQUIRE_CAUSES_WAKEUP |PowerManager.ON_AFTER_RELEASE,WAKE_LOCK_TAG);
      wl.acquire(10000);
      PowerManager.WakeLock wl_cpu = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,WAKE_LOCK_TAG);
      wl_cpu.acquire(10000);
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      // Creando canal para la notificacion (necesario para api 26 y posteriores)
      String CHANNEL_ID = "full-screen-notification";
      CharSequence CHANNEL_NAME = "Llamada entrante";
      String CHANNEL_DESCRIPTION = intent.getStringExtra("name") + " - " + intent.getStringExtra("number");
      // Se le asigna IMPORTANCE_HIGH al canal de notificación para que se muestre como emergente para api 26 en adelante
      int CHANNEL_IMPORTANCE = NotificationManager.IMPORTANCE_HIGH;
      final NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, CHANNEL_IMPORTANCE);
      notificationChannel.setDescription(CHANNEL_DESCRIPTION);
      notificationChannel.setSound(Settings.System.DEFAULT_NOTIFICATION_URI, null);
      // Register the channel with the system; you can't change the importance
      // or other notification behaviors after this
      final NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
      notificationManager.createNotificationChannel(notificationChannel);

      final NotificationCompat.Builder notification = new NotificationCompat.Builder(this, CHANNEL_ID);
      notification.setContentTitle("Llamada entrante");
      notification.setContentText(intent.getStringExtra("name") + " - " + intent.getStringExtra("number"));
      notification.setTicker("Call_STATUS");
      // Para no mostrar el horario de notificación. No aporta información útil al usuario
      notification.setShowWhen(false);
      notification.setSmallIcon(intent.getIntExtra("icon", 0));
      notification.setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND);
      // Para saber si es necesario molestar al usuario con una notificación a pesar de tener activado el modo "No interrumpir"
      notification.setCategory(NotificationCompat.CATEGORY_CALL);
      final long[] DEFAULT_VIBRATE_PATTERN = {0, 250, 250, 250};
      notification.setVibrate(DEFAULT_VIBRATE_PATTERN);
      notification.setLights(Color.WHITE, 2000, 3000);
      notification.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
      // VISIBILITY_PUBLIC muestra el contenido completo de la notificación
      notification.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
      notification.setOngoing(true);
      notification.setAutoCancel(true);
      notification.setFullScreenIntent(pendingIntent, true);
      // Se le asigna PRIORITY_HIGH a la notificación para que se muestre como emergente para api 25 y anteriores
      notification.setPriority(NotificationCompat.PRIORITY_HIGH);
      notification.setCustomContentView(customView);
      notification.setCustomBigContentView(customView);

      startForeground(1234, notification.build());
    }

    return flags;
  }

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }
}
