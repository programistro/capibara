package com.example.pet;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.Calendar;
import java.util.Date;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Pill.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u0000 \u001f2\u00020\u0001:\u0001\u001fB/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\u0003¢\u0006\u0004\b\n\u0010\u000bB\u0011\b\u0016\u0012\u0006\u0010\f\u001a\u00020\r¢\u0006\u0004\b\n\u0010\u000eJ\u0018\u0010\u0017\u001a\u00020\u00182\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0019\u001a\u00020\u0007H\u0016J\b\u0010\u001a\u001a\u00020\u0007H\u0016J\u000e\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u0005J\b\u0010\u001e\u001a\u00020\u0003H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\b\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0014R\u0011\u0010\t\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0010¨\u0006 "}, d2 = {"Lcom/example/pet/Pill;", "Landroid/os/Parcelable;", "name", "", "startDate", "Ljava/util/Date;", "periodicity", "", "reminderTime", "intakeTime", "<init>", "(Ljava/lang/String;Ljava/util/Date;IILjava/lang/String;)V", "parcel", "Landroid/os/Parcel;", "(Landroid/os/Parcel;)V", "getName", "()Ljava/lang/String;", "getStartDate", "()Ljava/util/Date;", "getPeriodicity", "()I", "getReminderTime", "getIntakeTime", "writeToParcel", "", "flags", "describeContents", "shouldTakeOnDate", "", "date", "toString", "CREATOR", "app_debug"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class Pill implements Parcelable {

    /* JADX INFO: renamed from: CREATOR, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final String intakeTime;
    private final String name;
    private final int periodicity;
    private final int reminderTime;
    private final Date startDate;

    public Pill(String name, Date startDate, int periodicity, int reminderTime, String intakeTime) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(startDate, "startDate");
        Intrinsics.checkNotNullParameter(intakeTime, "intakeTime");
        this.name = name;
        this.startDate = startDate;
        this.periodicity = periodicity;
        this.reminderTime = reminderTime;
        this.intakeTime = intakeTime;
    }

    public final String getName() {
        return this.name;
    }

    public final Date getStartDate() {
        return this.startDate;
    }

    public final int getPeriodicity() {
        return this.periodicity;
    }

    public final int getReminderTime() {
        return this.reminderTime;
    }

    public final String getIntakeTime() {
        return this.intakeTime;
    }

    public Pill(Parcel parcel) {
        Intrinsics.checkNotNullParameter(parcel, "parcel");
        String string = parcel.readString();
        String str = string == null ? "" : string;
        Date date = new Date(parcel.readLong());
        int i = parcel.readInt();
        int i2 = parcel.readInt();
        String string2 = parcel.readString();
        this(str, date, i, i2, string2 == null ? "" : string2);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        Intrinsics.checkNotNullParameter(parcel, "parcel");
        parcel.writeString(this.name);
        parcel.writeLong(this.startDate.getTime());
        parcel.writeInt(this.periodicity);
        parcel.writeInt(this.reminderTime);
        parcel.writeString(this.intakeTime);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* JADX INFO: renamed from: com.example.pet.Pill$CREATOR, reason: from kotlin metadata */
    /* JADX INFO: compiled from: Pill.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u001d\u0010\b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¢\u0006\u0002\u0010\f¨\u0006\r"}, d2 = {"Lcom/example/pet/Pill$CREATOR;", "Landroid/os/Parcelable$Creator;", "Lcom/example/pet/Pill;", "<init>", "()V", "createFromParcel", "parcel", "Landroid/os/Parcel;", "newArray", "", "size", "", "(I)[Lcom/example/pet/Pill;", "app_debug"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static final class Companion implements Parcelable.Creator<Pill> {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Pill createFromParcel(Parcel parcel) {
            Intrinsics.checkNotNullParameter(parcel, "parcel");
            return new Pill(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Pill[] newArray(int size) {
            return new Pill[size];
        }
    }

    public final boolean shouldTakeOnDate(Date date) {
        Intrinsics.checkNotNullParameter(date, "date");
        if (this.periodicity < 0) {
            int i = -this.periodicity;
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime(this.startDate);
            startCalendar.set(11, 0);
            startCalendar.set(12, 0);
            startCalendar.set(13, 0);
            startCalendar.set(14, 0);
            Calendar targetCalendar = Calendar.getInstance();
            targetCalendar.setTime(date);
            targetCalendar.set(11, 0);
            targetCalendar.set(12, 0);
            targetCalendar.set(13, 0);
            targetCalendar.set(14, 0);
            if (targetCalendar.getTimeInMillis() < startCalendar.getTimeInMillis()) {
                return false;
            }
            return targetCalendar.getTimeInMillis() == startCalendar.getTimeInMillis() ? true : true;
        }
        Calendar startCalendar2 = Calendar.getInstance();
        startCalendar2.setTime(this.startDate);
        startCalendar2.set(11, 0);
        startCalendar2.set(12, 0);
        startCalendar2.set(13, 0);
        startCalendar2.set(14, 0);
        Calendar targetCalendar2 = Calendar.getInstance();
        targetCalendar2.setTime(date);
        targetCalendar2.set(11, 0);
        targetCalendar2.set(12, 0);
        targetCalendar2.set(13, 0);
        targetCalendar2.set(14, 0);
        long daysDiff = (targetCalendar2.getTimeInMillis() - startCalendar2.getTimeInMillis()) / ((long) 86400000);
        return daysDiff >= 0 && daysDiff % ((long) this.periodicity) == 0;
    }

    public String toString() {
        String periodicityText;
        if (this.periodicity < 0) {
            periodicityText = "every " + (-this.periodicity) + " hours";
        } else {
            periodicityText = "every " + this.periodicity + " days";
        }
        return "Pill(name='" + this.name + "', startDate=" + this.startDate + ", periodicity=" + periodicityText + ", reminderTime=" + this.reminderTime + ", intakeTime='" + this.intakeTime + "')";
    }
}
