package com.example.pet;

import com.example.capibara.R;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AlarmReceiver.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J(\u0010\n\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J \u0010\u0011\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u0010\u0010\u0012\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0010\u0010\u0013\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\u0015H\u0002¨\u0006\u0017"}, d2 = {"Lcom/example/pet/AlarmReceiver;", "Landroid/content/BroadcastReceiver;", "<init>", "()V", "onReceive", "", "context", "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "showReminderNotification", "pillName", "", "reminderTime", "", "isHourly", "", "showIntakeNotification", "formatTimeText", "createNotificationChannel", "notificationManager", "Landroid/app/NotificationManager;", "Companion", "app_debug"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class AlarmReceiver extends BroadcastReceiver {
    public static final String CHANNEL_ID = "pill_reminders";
    public static final String CHANNEL_NAME = "Напоминания о лекарствах";

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(intent, "intent");
        String pillName = intent.getStringExtra("pill_name");
        if (pillName == null) {
            pillName = "Лекарство";
        }
        String notificationType = intent.getStringExtra("notification_type");
        if (notificationType == null) {
            notificationType = NotificationCompat.CATEGORY_REMINDER;
        }
        int reminderTime = intent.getIntExtra("reminder_time", 180);
        boolean isHourly = intent.getBooleanExtra("hourly", false);
        System.out.println((Object) ("DEBUG: AlarmReceiver triggered - " + notificationType + " for " + pillName + " (hourly: " + isHourly + ")"));
        if (!Intrinsics.areEqual(notificationType, NotificationCompat.CATEGORY_REMINDER)) {
            if (Intrinsics.areEqual(notificationType, "intake")) {
                showIntakeNotification(context, pillName, isHourly);
                return;
            }
            return;
        }
        showReminderNotification(context, pillName, reminderTime, isHourly);
    }

    private final void showReminderNotification(Context context, String pillName, int reminderTime, boolean isHourly) {
        Object systemService = context.getSystemService("notification");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.NotificationManager");
        NotificationManager notificationManager = (NotificationManager) systemService;
        createNotificationChannel(notificationManager);
        int notificationId = (int) (System.currentTimeMillis() & 268435455);
        Intent intent = new Intent(context, (Class<?>) MainActivity.class);
        intent.setFlags(268468224);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, notificationId, intent, 201326592);
        String timeText = formatTimeText(reminderTime);
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID).setSmallIcon(android.R.drawable.ic_dialog_info).setContentTitle("Скоро нужно будет накормить капибару!").setContentText("Прием " + timeText).setPriority(1).setContentIntent(pendingIntent).setAutoCancel(true).setDefaults(-1).build();
        Intrinsics.checkNotNullExpressionValue(notification, "build(...)");
        notificationManager.notify(notificationId, notification);
        System.out.println((Object) ("DEBUG: Reminder notification shown for " + pillName));
    }

    private final void showIntakeNotification(Context context, String pillName, boolean isHourly) {
        Object systemService = context.getSystemService("notification");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.NotificationManager");
        NotificationManager notificationManager = (NotificationManager) systemService;
        createNotificationChannel(notificationManager);
        int notificationId = ((int) (System.currentTimeMillis() & 268435455)) + 1;
        Intent intent = new Intent(context, (Class<?>) MainActivity.class);
        intent.setFlags(268468224);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, notificationId, intent, 201326592);
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID).setSmallIcon(android.R.drawable.ic_dialog_info).setContentTitle("Время кормить капибару!").setContentText("Капибара уже голодна!").setPriority(1).setContentIntent(pendingIntent).setAutoCancel(true).setDefaults(-1).build();
        Intrinsics.checkNotNullExpressionValue(notification, "build(...)");
        notificationManager.notify(notificationId, notification);
        System.out.println((Object) ("DEBUG: Intake notification shown for " + pillName));
    }

    private final String formatTimeText(int reminderTime) {
        int hours = reminderTime / 60;
        int minutes = reminderTime % 60;
        if (hours > 0) {
            return (minutes > 0 ? new StringBuilder().append("через ").append(hours).append(" ч. ").append(minutes).append(" мин.") : new StringBuilder().append("через ").append(hours).append(" ч.")).toString();
        }
        return "через " + minutes + " мин.";
    }

    private final void createNotificationChannel(NotificationManager notificationManager) {
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel existingChannel = notificationManager.getNotificationChannel(CHANNEL_ID);
            if (existingChannel == null) {
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, 4);
                channel.setDescription("Канал для напоминаний о приеме лекарств");
                channel.enableLights(true);
                channel.setLightColor(-16776961);
                channel.enableVibration(true);
                channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000});
                notificationManager.createNotificationChannel(channel);
                System.out.println((Object) "DEBUG: Notification channel created");
            }
        }
    }
}
