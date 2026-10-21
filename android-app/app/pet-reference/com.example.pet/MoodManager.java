package com.example.pet;

import com.example.capibara.R;

import android.content.Context;
import android.graphics.Color;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: MoodManager.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\b\b\u0018\u0000 52\u00020\u0001:\u00015B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0006\u0010\n\u001a\u00020\u000bJ\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000bH\u0002J\u0006\u0010\u000f\u001a\u00020\u0010J\u0006\u0010\u0011\u001a\u00020\rJ\u0010\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u000e\u0010\u0015\u001a\u00020\r2\u0006\u0010\u0016\u001a\u00020\u000bJ\u000e\u0010\u0017\u001a\u00020\r2\u0006\u0010\u0016\u001a\u00020\u000bJ\u000e\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bJ\u0006\u0010\u001c\u001a\u00020\u000bJ\u0006\u0010\u001d\u001a\u00020\u001bJ\u0006\u0010\u001e\u001a\u00020\u001bJ\u0006\u0010\u001f\u001a\u00020\u000bJ\u0006\u0010 \u001a\u00020\u000bJ\u0006\u0010!\u001a\u00020\u001bJ\u0006\u0010\"\u001a\u00020\rJ\u000e\u0010#\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bJ \u0010$\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010%\u001a\u00020\u000b2\u0006\u0010&\u001a\u00020\u000bH\u0002J\u0016\u0010'\u001a\b\u0012\u0004\u0012\u00020)0(2\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u0010\u0010*\u001a\u00020\u000b2\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\b\u0010+\u001a\u00020\u0014H\u0002J\u0010\u0010,\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u000e\u0010-\u001a\b\u0012\u0004\u0012\u00020\u001b0.H\u0002J\u0016\u0010/\u001a\u00020\r2\f\u00100\u001a\b\u0012\u0004\u0012\u00020\u001b0(H\u0002J\b\u00101\u001a\u00020\rH\u0002J\u0018\u00102\u001a\u00020\u00192\u0006\u00103\u001a\u00020\u00142\u0006\u00104\u001a\u00020\u0014H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u00066"}, d2 = {"Lcom/example/pet/MoodManager;", "", "context", "Landroid/content/Context;", "<init>", "(Landroid/content/Context;)V", "prefs", "Lcom/example/pet/PreferenceHelper;", "dbHelper", "Lcom/example/pet/DbHelper;", "getCurrentScore", "", "setScore", "", "score", "getCurrentMood", "Lcom/example/pet/MoodManager$Companion$Mood;", "checkAndUpdateMood", "processYesterdayPerformance", "date", "Ljava/util/Date;", "addScore", "points", "subtractScore", "markPillAsTaken", "", "pillName", "", "getTodayCompletionPercent", "getTodayProgressText", "getMoodDescription", "getMoodColor", "getPetImageResId", "getMoodStats", "resetMood", "isPillMarkedToday", "saveDayStats", "marked", "total", "getPillsForDate", "", "Lcom/example/pet/Pill;", "getMarkedCountForDate", "getLastCheckDate", "saveLastCheckDate", "getTodayMarkedPills", "", "saveTodayMarkedPills", "pills", "resetDailyMarks", "isSameDay", "date1", "date2", "Companion", "app_debug"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class MoodManager {
    private static final int BONUS_ALL_COMPLETED = 20;
    private static final int DAILY_DECAY = 3;
    private static final int DEFAULT_SCORE = 50;
    private static final int HAPPY_THRESHOLD = 70;
    private static final int MAX_SCORE = 100;
    private static final int MIN_SCORE = 0;
    private static final int PENALTY_PER_MISSED = 12;
    private static final String PREF_LAST_CHECK_DATE = "last_mood_check_date";
    private static final String PREF_MOOD_SCORE = "mood_score";
    private static final String PREF_TODAY_MARKED = "today_pills_marked";
    private static final int SAD_THRESHOLD = 30;
    private static final int SCORE_PER_PILL = 8;
    private final Context context;
    private final DbHelper dbHelper;
    private final PreferenceHelper prefs;

    /* JADX INFO: compiled from: MoodManager.kt */
    @Metadata(k = 3, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Companion.Mood.values().length];
            try {
                iArr[Companion.Mood.HAPPY.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[Companion.Mood.NEUTRAL.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[Companion.Mood.SAD.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public MoodManager(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.prefs = new PreferenceHelper(this.context);
        this.dbHelper = new DbHelper(this.context, null);
    }

    public final int getCurrentScore() {
        return RangesKt.coerceIn(this.prefs.getInt(PREF_MOOD_SCORE, 50), 0, 100);
    }

    private final void setScore(int score) {
        int clampedScore = RangesKt.coerceIn(score, 0, 100);
        this.prefs.saveInt(PREF_MOOD_SCORE, clampedScore);
    }

    public final Companion.Mood getCurrentMood() {
        int score = getCurrentScore();
        return score >= HAPPY_THRESHOLD ? Companion.Mood.HAPPY : score <= 30 ? Companion.Mood.SAD : Companion.Mood.NEUTRAL;
    }

    public final void checkAndUpdateMood() {
        Date today = new Date();
        Date lastCheckDate = getLastCheckDate();
        if (!isSameDay(today, lastCheckDate)) {
            processYesterdayPerformance(lastCheckDate);
            saveLastCheckDate(today);
        }
    }

    private final void processYesterdayPerformance(Date date) {
        int totalCount = getPillsForDate(date).size();
        if (totalCount > 0) {
            int markedCount = getMarkedCountForDate(date);
            int missedCount = totalCount - markedCount;
            int scoreChange = (0 + (markedCount * 8)) - (missedCount * 12);
            if (markedCount == totalCount && totalCount > 0) {
                scoreChange += 20;
            }
            if (scoreChange > 0) {
                addScore(scoreChange);
            } else if (scoreChange < 0) {
                subtractScore(-scoreChange);
            }
            saveDayStats(date, markedCount, totalCount);
            return;
        }
        subtractScore(3);
    }

    public final void addScore(int points) {
        setScore(getCurrentScore() + points);
    }

    public final void subtractScore(int points) {
        setScore(getCurrentScore() - points);
    }

    public final boolean markPillAsTaken(String pillName) {
        Intrinsics.checkNotNullParameter(pillName, "pillName");
        List<String> todayMarkedPills = getTodayMarkedPills();
        if (todayMarkedPills.contains(pillName)) {
            return false;
        }
        todayMarkedPills.add(pillName);
        saveTodayMarkedPills(todayMarkedPills);
        addScore(2);
        return true;
    }

    public final int getTodayCompletionPercent() {
        List<Pill> pillsForDate = getPillsForDate(new Date());
        if (pillsForDate.isEmpty()) {
            return 0;
        }
        int markedCount = getTodayMarkedPills().size();
        return (markedCount * 100) / pillsForDate.size();
    }

    public final String getTodayProgressText() {
        List<Pill> pillsForDate = getPillsForDate(new Date());
        int markedCount = getTodayMarkedPills().size();
        return markedCount + "/" + pillsForDate.size();
    }

    public final String getMoodDescription() {
        switch (WhenMappings.$EnumSwitchMapping$0[getCurrentMood().ordinal()]) {
            case 1:
                return "Капибара счастлива 😄";
            case 2:
                return "Капибара в норме 😐";
            case 3:
                return "Капибара грустит 😔";
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    public final int getMoodColor() {
        switch (WhenMappings.$EnumSwitchMapping$0[getCurrentMood().ordinal()]) {
            case 1:
                return Color.parseColor("#4CAF50");
            case 2:
                return Color.parseColor("#FFC107");
            case 3:
                return Color.parseColor("#F44336");
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    public final int getPetImageResId() {
        switch (WhenMappings.$EnumSwitchMapping$0[getCurrentMood().ordinal()]) {
            case 1:
                return R.drawable.pet_happy;
            case 2:
                return R.drawable.pet_normal;
            case 3:
                return R.drawable.pet_crying;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    public final String getMoodStats() {
        return "Настроение: " + getCurrentScore() + "/100\nПрогресс сегодня: " + getTodayProgressText();
    }

    public final void resetMood() {
        setScore(50);
    }

    public final boolean isPillMarkedToday(String pillName) {
        Intrinsics.checkNotNullParameter(pillName, "pillName");
        return getTodayMarkedPills().contains(pillName);
    }

    private final void saveDayStats(Date date, int marked, int total) {
        String dateKey = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(date);
        this.prefs.saveString("stats_" + dateKey, marked + "/" + total);
    }

    private final List<Pill> getPillsForDate(Date date) {
        return this.dbHelper.getPillsForDate(date);
    }

    private final int getMarkedCountForDate(Date date) {
        Integer intOrNull;
        String dateKey = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(date);
        String stats = this.prefs.getString("stats_" + dateKey, "0/0");
        List parts = StringsKt.split$default((CharSequence) stats, new String[]{"/"}, false, 0, 6, (Object) null);
        String str = (String) CollectionsKt.getOrNull(parts, 0);
        if (str == null || (intOrNull = StringsKt.toIntOrNull(str)) == null) {
            return 0;
        }
        return intOrNull.intValue();
    }

    private final Date getLastCheckDate() {
        String dateStr = this.prefs.getString(PREF_LAST_CHECK_DATE, "");
        if (dateStr.length() > 0) {
            Date date = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).parse(dateStr);
            return date == null ? new Date() : date;
        }
        return new Date();
    }

    private final void saveLastCheckDate(Date date) {
        String dateStr = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(date);
        PreferenceHelper preferenceHelper = this.prefs;
        Intrinsics.checkNotNull(dateStr);
        preferenceHelper.saveString(PREF_LAST_CHECK_DATE, dateStr);
    }

    private final List<String> getTodayMarkedPills() {
        String markedString = this.prefs.getString(PREF_TODAY_MARKED, "");
        if (markedString.length() > 0) {
            return CollectionsKt.toMutableList((Collection) StringsKt.split$default((CharSequence) markedString, new String[]{","}, false, 0, 6, (Object) null));
        }
        return new ArrayList();
    }

    private final void saveTodayMarkedPills(List<String> pills) {
        String pillsString = CollectionsKt.joinToString$default(pills, ",", null, null, 0, null, null, 62, null);
        this.prefs.saveString(PREF_TODAY_MARKED, pillsString);
    }

    private final void resetDailyMarks() {
        this.prefs.saveString(PREF_TODAY_MARKED, "");
    }

    private final boolean isSameDay(Date date1, Date date2) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        return Intrinsics.areEqual(fmt.format(date1), fmt.format(date2));
    }
}
