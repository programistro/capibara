package com.example.pet;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.savedstate.serialization.ClassDiscriminatorModeKt;
import java.util.Calendar;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: NotificationHelper.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bJ\u0018\u0010\f\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0018\u0010\u000f\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0010\u0010\u0010\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J \u0010\u0011\u001a\u00020\u00122\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\u0010\u0010\u0017\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u0010\u0010\u0018\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u0018\u0010\u0019\u001a\u00020\t2\u0006\u0010\u001a\u001a\u00020\u00162\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\u0010\u0010\u001d\u001a\u00020\u000e2\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u000e\u0010\u001e\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bJ\u0018\u0010\u001f\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u0018\u0010 \u001a\u00020\u00122\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0013\u001a\u00020\u0014H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006!"}, d2 = {"Lcom/example/pet/NotificationHelper;", "", "context", "Landroid/content/Context;", "<init>", "(Landroid/content/Context;)V", "alarmManager", "Landroid/app/AlarmManager;", "schedulePillNotifications", "", "pill", "Lcom/example/pet/Pill;", "scheduleSingleReminder", "intakeCalendar", "Ljava/util/Calendar;", "scheduleSingleIntake", "scheduleHourlyPillNotifications", "generateHourlyRequestCode", "", ClassDiscriminatorModeKt.CLASS_DISCRIMINATOR_KEY, "", "time", "", "scheduleReminderNotification", "scheduleIntakeNotification", "setReliableAlarm", "triggerTime", "pendingIntent", "Landroid/app/PendingIntent;", "getIntakeCalendar", "cancelPillNotifications", "cancelNotification", "generateRequestCode", "app_debug"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class NotificationHelper {
    private final AlarmManager alarmManager;
    private final Context context;

    public NotificationHelper(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        Object systemService = this.context.getSystemService(NotificationCompat.CATEGORY_ALARM);
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.AlarmManager");
        this.alarmManager = (AlarmManager) systemService;
    }

    public final void schedulePillNotifications(Pill pill) {
        Intrinsics.checkNotNullParameter(pill, "pill");
        System.out.println((Object) ("DEBUG: Scheduling notifications for " + pill.getName()));
        if (pill.getPeriodicity() < 0) {
            scheduleHourlyPillNotifications(pill);
        } else {
            scheduleReminderNotification(pill);
            scheduleIntakeNotification(pill);
        }
    }

    private final void scheduleSingleReminder(Pill pill, Calendar intakeCalendar) {
        Object objClone = intakeCalendar.clone();
        Intrinsics.checkNotNull(objClone, "null cannot be cast to non-null type java.util.Calendar");
        Calendar reminderCalendar = (Calendar) objClone;
        reminderCalendar.add(12, -pill.getReminderTime());
        System.out.println((Object) ("DEBUG: Hourly reminder time: " + reminderCalendar.getTime() + " for " + pill.getName()));
        Intent intent = new Intent(this.context, (Class<?>) AlarmReceiver.class);
        intent.putExtra("pill_name", pill.getName());
        intent.putExtra("notification_type", NotificationCompat.CATEGORY_REMINDER);
        intent.putExtra("reminder_time", pill.getReminderTime());
        intent.putExtra("hourly", true);
        int requestCode = generateHourlyRequestCode(pill, NotificationCompat.CATEGORY_REMINDER, intakeCalendar.getTimeInMillis());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.context, requestCode, intent, 201326592);
        long timeInMillis = reminderCalendar.getTimeInMillis();
        Intrinsics.checkNotNull(pendingIntent);
        setReliableAlarm(timeInMillis, pendingIntent);
        System.out.println((Object) ("DEBUG: Hourly reminder scheduled for " + reminderCalendar.getTime()));
    }

    private final void scheduleSingleIntake(Pill pill, Calendar intakeCalendar) {
        System.out.println((Object) ("DEBUG: Hourly intake time: " + intakeCalendar.getTime() + " for " + pill.getName()));
        Intent intent = new Intent(this.context, (Class<?>) AlarmReceiver.class);
        intent.putExtra("pill_name", pill.getName());
        intent.putExtra("notification_type", "intake");
        intent.putExtra("hourly", true);
        int requestCode = generateHourlyRequestCode(pill, "intake", intakeCalendar.getTimeInMillis());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.context, requestCode, intent, 201326592);
        long timeInMillis = intakeCalendar.getTimeInMillis();
        Intrinsics.checkNotNull(pendingIntent);
        setReliableAlarm(timeInMillis, pendingIntent);
        System.out.println((Object) ("DEBUG: Hourly intake scheduled for " + intakeCalendar.getTime()));
    }

    private final void scheduleHourlyPillNotifications(Pill pill) {
        int intervalHours = -pill.getPeriodicity();
        int i = 72 / intervalHours;
        for (int i2 = 0; i2 < i; i2++) {
            Calendar notificationCalendar = getIntakeCalendar(pill);
            notificationCalendar.add(11, i2 * intervalHours);
            if (notificationCalendar.getTimeInMillis() >= System.currentTimeMillis() - ((long) 3600000)) {
                scheduleSingleReminder(pill, notificationCalendar);
                scheduleSingleIntake(pill, notificationCalendar);
            }
        }
    }

    private final int generateHourlyRequestCode(Pill pill, String type, long time) {
        return (int) (((long) pill.getName().hashCode()) + time + ((long) type.hashCode()));
    }

    private final void scheduleReminderNotification(Pill pill) {
        Calendar reminderCalendar = getIntakeCalendar(pill);
        reminderCalendar.add(12, -pill.getReminderTime());
        System.out.println((Object) ("DEBUG: Reminder time: " + reminderCalendar.getTime() + " for " + pill.getName()));
        if (reminderCalendar.getTimeInMillis() <= System.currentTimeMillis()) {
            reminderCalendar.add(6, 1);
            System.out.println((Object) ("DEBUG: Reminder time passed, scheduling for tomorrow: " + reminderCalendar.getTime()));
        }
        Intent intent = new Intent(this.context, (Class<?>) AlarmReceiver.class);
        intent.putExtra("pill_name", pill.getName());
        intent.putExtra("notification_type", NotificationCompat.CATEGORY_REMINDER);
        intent.putExtra("reminder_time", pill.getReminderTime());
        int requestCode = generateRequestCode(pill, NotificationCompat.CATEGORY_REMINDER);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.context, requestCode, intent, 201326592);
        long timeInMillis = reminderCalendar.getTimeInMillis();
        Intrinsics.checkNotNull(pendingIntent);
        setReliableAlarm(timeInMillis, pendingIntent);
        System.out.println((Object) ("DEBUG: Reminder scheduled for " + reminderCalendar.getTime()));
    }

    private final void scheduleIntakeNotification(Pill pill) {
        Calendar intakeCalendar = getIntakeCalendar(pill);
        System.out.println((Object) ("DEBUG: Intake time: " + intakeCalendar.getTime() + " for " + pill.getName()));
        if (intakeCalendar.getTimeInMillis() <= System.currentTimeMillis()) {
            intakeCalendar.add(6, 1);
            System.out.println((Object) ("DEBUG: Intake time passed, scheduling for tomorrow: " + intakeCalendar.getTime()));
        }
        Intent intent = new Intent(this.context, (Class<?>) AlarmReceiver.class);
        intent.putExtra("pill_name", pill.getName());
        intent.putExtra("notification_type", "intake");
        int requestCode = generateRequestCode(pill, "intake");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.context, requestCode, intent, 201326592);
        long timeInMillis = intakeCalendar.getTimeInMillis();
        Intrinsics.checkNotNull(pendingIntent);
        setReliableAlarm(timeInMillis, pendingIntent);
        System.out.println((Object) ("DEBUG: Intake scheduled for " + intakeCalendar.getTime()));
    }

    private final void setReliableAlarm(long triggerTime, PendingIntent pendingIntent) {
        try {
            this.alarmManager.setExactAndAllowWhileIdle(0, triggerTime, pendingIntent);
            System.out.println((Object) "DEBUG: Used setExactAndAllowWhileIdle");
        } catch (SecurityException e) {
            System.out.println((Object) ("DEBUG: Security exception, using basic set: " + e.getMessage()));
            try {
                this.alarmManager.set(0, triggerTime, pendingIntent);
            } catch (Exception e2) {
                System.out.println((Object) ("DEBUG: All alarm methods failed: " + e2.getMessage()));
            }
        } catch (Exception e3) {
            System.out.println((Object) ("DEBUG: Alarm setting failed: " + e3.getMessage()));
        }
    }

    private final Calendar getIntakeCalendar(Pill pill) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(pill.getStartDate());
        List timeParts = StringsKt.split$default((CharSequence) pill.getIntakeTime(), new String[]{":"}, false, 0, 6, (Object) null);
        if (timeParts.size() == 2) {
            Integer intOrNull = StringsKt.toIntOrNull((String) timeParts.get(0));
            int hour = intOrNull != null ? intOrNull.intValue() : 12;
            Integer intOrNull2 = StringsKt.toIntOrNull((String) timeParts.get(1));
            int minute = intOrNull2 != null ? intOrNull2.intValue() : 0;
            calendar.set(11, hour);
            calendar.set(12, minute);
            calendar.set(13, 0);
            calendar.set(14, 0);
        }
        Intrinsics.checkNotNull(calendar);
        return calendar;
    }

    public final void cancelPillNotifications(Pill pill) {
        Intrinsics.checkNotNullParameter(pill, "pill");
        try {
            cancelNotification(pill, NotificationCompat.CATEGORY_REMINDER);
            cancelNotification(pill, "intake");
            System.out.println((Object) ("DEBUG: Отменены уведомления для: " + pill.getName()));
            System.out.println((Object) ("DEBUG: Время приема: " + pill.getIntakeTime() + ", Дата начала: " + pill.getStartDate()));
        } catch (Exception e) {
            System.out.println((Object) ("DEBUG: Ошибка при отмене уведомлений: " + e.getMessage()));
        }
    }

    private final void cancelNotification(Pill pill, String type) {
        try {
            Intent intent = new Intent(this.context, (Class<?>) AlarmReceiver.class);
            int requestCode = generateRequestCode(pill, type);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this.context, requestCode, intent, 201326592);
            this.alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
            System.out.println((Object) ("DEBUG: Отменено " + type + " уведомление для " + pill.getName()));
        } catch (Exception e) {
            System.out.println((Object) ("DEBUG: Ошибка при отмене " + type + " уведомления: " + e.getMessage()));
        }
    }

    private final int generateRequestCode(Pill pill, String type) {
        return (int) (((long) pill.getName().hashCode()) + pill.getStartDate().getTime() + ((long) type.hashCode()));
    }
}
