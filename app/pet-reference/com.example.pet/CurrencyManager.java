package com.example.pet;

import android.content.Context;
import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CurrencyManager.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0006\u0010\b\u001a\u00020\tJ\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\tJ\u000e\u0010\r\u001a\u00020\u000e2\u0006\u0010\f\u001a\u00020\tJ\u000e\u0010\u000f\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u0011J\u000e\u0010\u0012\u001a\u00020\u000e2\u0006\u0010\f\u001a\u00020\tJ\u0006\u0010\u0013\u001a\u00020\u000bJ\b\u0010\u0014\u001a\u00020\u000bH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lcom/example/pet/CurrencyManager;", "", "context", "Landroid/content/Context;", "<init>", "(Landroid/content/Context;)V", "prefs", "Lcom/example/pet/PreferenceHelper;", "getCurrentCoins", "", "addCoins", "", "amount", "subtractCoins", "", "rewardForMarkedPill", "pillName", "", "hasEnoughCoins", "resetCoins", "notifyMainActivity", "Companion", "app_debug"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class CurrencyManager {
    private static final int COINS_PER_MARKED_PILL = 5;
    private static final int DEFAULT_COINS = 500;
    private static final String PREF_COINS = "player_coins";
    private final Context context;
    private final PreferenceHelper prefs;

    public CurrencyManager(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.prefs = new PreferenceHelper(this.context);
    }

    public final int getCurrentCoins() {
        return this.prefs.getInt(PREF_COINS, DEFAULT_COINS);
    }

    public final void addCoins(int amount) {
        int currentCoins = getCurrentCoins();
        this.prefs.saveInt(PREF_COINS, currentCoins + amount);
        notifyMainActivity();
    }

    public final boolean subtractCoins(int amount) {
        int currentCoins = getCurrentCoins();
        if (currentCoins >= amount) {
            this.prefs.saveInt(PREF_COINS, currentCoins - amount);
            notifyMainActivity();
            return true;
        }
        return false;
    }

    public final void rewardForMarkedPill(String pillName) {
        Intrinsics.checkNotNullParameter(pillName, "pillName");
        addCoins(5);
    }

    public final boolean hasEnoughCoins(int amount) {
        return getCurrentCoins() >= amount;
    }

    public final void resetCoins() {
        this.prefs.saveInt(PREF_COINS, DEFAULT_COINS);
        notifyMainActivity();
    }

    private final void notifyMainActivity() {
        try {
            MainActivity mainActivity = MainActivity.INSTANCE.getInstance();
            if (mainActivity != null) {
                mainActivity.updateCoinsFromFragment();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
