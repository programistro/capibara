package com.example.pet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: DbHelper.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0012\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0016J\"\u0010\u0010\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0012H\u0016J\u000e\u0010\u0014\u001a\u00020\r2\u0006\u0010\u0015\u001a\u00020\u0016J\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00160\u0018J\u0014\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00160\u00182\u0006\u0010\u001a\u001a\u00020\u001bJ\u0006\u0010\u001c\u001a\u00020\u0012J\u0016\u0010\u001d\u001a\u00020\r2\u0006\u0010\u001e\u001a\u00020\u00162\u0006\u0010\u001f\u001a\u00020\u0016J\u000e\u0010 \u001a\u00020\r2\u0006\u0010\u0015\u001a\u00020\u0016J\u000e\u0010!\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u0003R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\""}, d2 = {"Lcom/example/pet/DbHelper;", "Landroid/database/sqlite/SQLiteOpenHelper;", "context", "Landroid/content/Context;", "factory", "Landroid/database/sqlite/SQLiteDatabase$CursorFactory;", "<init>", "(Landroid/content/Context;Landroid/database/sqlite/SQLiteDatabase$CursorFactory;)V", "getContext", "()Landroid/content/Context;", "getFactory", "()Landroid/database/sqlite/SQLiteDatabase$CursorFactory;", "onCreate", "", "db", "Landroid/database/sqlite/SQLiteDatabase;", "onUpgrade", "oldVersion", "", "newVersion", "addPill", "pill", "Lcom/example/pet/Pill;", "getAllPills", "", "getPillsForDate", "date", "Ljava/util/Date;", "clearAllPills", "updatePill", "oldPill", "newPill", "deletePill", "clearAllPillsWithNotifications", "app_debug"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class DbHelper extends SQLiteOpenHelper {
    private final Context context;
    private final SQLiteDatabase.CursorFactory factory;

    public final Context getContext() {
        return this.context;
    }

    public final SQLiteDatabase.CursorFactory getFactory() {
        return this.factory;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DbHelper(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, "app", factory, 3);
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.factory = factory;
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase db) {
        Intrinsics.checkNotNull(db);
        db.execSQL("CREATE TABLE pills (\n    id INTEGER PRIMARY KEY AUTOINCREMENT, \n    name TEXT, \n    startDate INTEGER, \n    periodicity INT, \n    reminderTime INT,\n    intakeTime TEXT\n)");
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Intrinsics.checkNotNull(db);
        db.execSQL("DROP TABLE IF EXISTS pills");
        onCreate(db);
    }

    public final void addPill(Pill pill) {
        Intrinsics.checkNotNullParameter(pill, "pill");
        ContentValues values = new ContentValues();
        values.put("name", pill.getName());
        values.put("startDate", Long.valueOf(pill.getStartDate().getTime()));
        values.put("periodicity", Integer.valueOf(pill.getPeriodicity()));
        values.put("reminderTime", Integer.valueOf(pill.getReminderTime()));
        values.put("intakeTime", pill.getIntakeTime());
        SQLiteDatabase db = getWritableDatabase();
        db.insert("pills", null, values);
        db.close();
    }

    public final List<Pill> getAllPills() throws IOException {
        List pills = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM pills", null);
        Intrinsics.checkNotNullExpressionValue(cursor, "rawQuery(...)");
        Cursor cursor2 = cursor;
        try {
            Cursor cursor3 = cursor2;
            while (cursor3.moveToNext()) {
                String string = cursor3.getString(cursor3.getColumnIndexOrThrow("name"));
                long j = cursor3.getLong(cursor3.getColumnIndexOrThrow("startDate"));
                int i = cursor3.getInt(cursor3.getColumnIndexOrThrow("periodicity"));
                int i2 = cursor3.getInt(cursor3.getColumnIndexOrThrow("reminderTime"));
                String string2 = cursor3.getString(cursor3.getColumnIndexOrThrow("intakeTime"));
                Intrinsics.checkNotNull(string);
                Date date = new Date(j);
                Intrinsics.checkNotNull(string2);
                pills.add(new Pill(string, date, i, i2, string2));
            }
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(cursor2, null);
            db.close();
            return pills;
        } finally {
        }
    }

    public final List<Pill> getPillsForDate(Date date) throws IOException {
        Intrinsics.checkNotNullParameter(date, "date");
        Iterable allPills = getAllPills();
        Collection arrayList = new ArrayList();
        for (Object obj : allPills) {
            if (((Pill) obj).shouldTakeOnDate(date)) {
                arrayList.add(obj);
            }
        }
        return (List) arrayList;
    }

    public final int clearAllPills() {
        SQLiteDatabase db = getWritableDatabase();
        int deletedRows = db.delete("pills", null, null);
        db.close();
        return deletedRows;
    }

    public final void updatePill(Pill oldPill, Pill newPill) {
        Intrinsics.checkNotNullParameter(oldPill, "oldPill");
        Intrinsics.checkNotNullParameter(newPill, "newPill");
        SQLiteDatabase db = getWritableDatabase();
        try {
            try {
                Context context = this.context;
                if (context != null) {
                    NotificationHelper notificationHelper = new NotificationHelper(context);
                    notificationHelper.cancelPillNotifications(oldPill);
                }
                int deleted = db.delete("pills", "name = ? AND startDate = ? AND intakeTime = ?", new String[]{oldPill.getName(), String.valueOf(oldPill.getStartDate().getTime()), oldPill.getIntakeTime()});
                System.out.println((Object) ("DEBUG: При обновлении удалено старых записей: " + deleted));
                ContentValues values = new ContentValues();
                values.put("name", newPill.getName());
                values.put("startDate", Long.valueOf(newPill.getStartDate().getTime()));
                values.put("periodicity", Integer.valueOf(newPill.getPeriodicity()));
                values.put("reminderTime", Integer.valueOf(newPill.getReminderTime()));
                values.put("intakeTime", newPill.getIntakeTime());
                long newId = db.insert("pills", null, values);
                System.out.println((Object) ("DEBUG: Добавлена новая запись с ID: " + newId));
                if (context != null) {
                    NotificationHelper notificationHelper2 = new NotificationHelper(context);
                    notificationHelper2.schedulePillNotifications(newPill);
                }
            } catch (Exception e) {
                System.out.println((Object) ("DEBUG: Ошибка при обновлении лекарства: " + e.getMessage()));
                e.printStackTrace();
            }
        } finally {
            db.close();
        }
    }

    public final void deletePill(Pill pill) {
        Intrinsics.checkNotNullParameter(pill, "pill");
        SQLiteDatabase db = getWritableDatabase();
        try {
            try {
                int deletedRows = db.delete("pills", "name = ? AND startDate = ? AND intakeTime = ?", new String[]{pill.getName(), String.valueOf(pill.getStartDate().getTime()), pill.getIntakeTime()});
                System.out.println((Object) ("DEBUG: Удалено строк из БД: " + deletedRows));
                if (deletedRows == 0) {
                    db.delete("pills", "name = ?", new String[]{pill.getName()});
                    System.out.println((Object) "DEBUG: Использован альтернативный способ удаления");
                }
            } catch (Exception e) {
                System.out.println((Object) ("DEBUG: Ошибка при удалении: " + e.getMessage()));
            }
        } finally {
            db.close();
        }
    }

    public final int clearAllPillsWithNotifications(Context context) throws IOException {
        Intrinsics.checkNotNullParameter(context, "context");
        List<Pill> allPills = getAllPills();
        NotificationHelper notificationHelper = new NotificationHelper(context);
        for (Pill pill : allPills) {
            notificationHelper.cancelPillNotifications(pill);
        }
        int deletedRows = clearAllPills();
        System.out.println((Object) ("DEBUG: Очищено всех лекарств: " + deletedRows));
        return deletedRows;
    }
}
