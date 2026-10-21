package com.example.pet;

import com.example.capibara.R;

import android.app.AlarmManager;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.location.LocationRequestCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import com.google.android.material.card.MaterialCardViewHelper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MainActivity.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u008a\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u0000 E2\u00020\u0001:\u0001EB\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0012\u0010%\u001a\u00020&2\b\u0010'\u001a\u0004\u0018\u00010(H\u0014J\u0006\u0010)\u001a\u00020&J\u0006\u0010*\u001a\u00020&J\u0006\u0010+\u001a\u00020&J\u000e\u0010,\u001a\u00020&2\u0006\u0010-\u001a\u00020.J\b\u0010/\u001a\u00020&H\u0002J\u0006\u00100\u001a\u00020&J\b\u00101\u001a\u00020&H\u0002J\b\u00102\u001a\u00020&H\u0002J\u0014\u00103\u001a\u00020&2\f\u00104\u001a\b\u0012\u0004\u0012\u00020\u001f05J(\u00106\u001a\u00020&2\u0006\u00107\u001a\u00020\u00052\u0006\u00108\u001a\u0002092\u0006\u0010:\u001a\u00020;2\u0006\u0010<\u001a\u00020;H\u0002J\u000e\u0010=\u001a\b\u0012\u0004\u0012\u00020905H\u0002J\u0006\u0010>\u001a\u00020&J\b\u0010?\u001a\u00020&H\u0002J\b\u0010@\u001a\u00020&H\u0002J\u0006\u0010A\u001a\u00020BJ\u0006\u0010C\u001a\u00020BJ\b\u0010D\u001a\u00020&H\u0014R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0015X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0013X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0015X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0015X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n\u0000R\u001a\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020 0\u001eX\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010!\u001a\u0010\u0012\f\u0012\n $*\u0004\u0018\u00010#0#0\"X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006F"}, d2 = {"Lcom/example/pet/MainActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "<init>", "()V", "backgroundImageView", "Landroid/widget/ImageView;", "prefs", "Lcom/example/pet/PreferenceHelper;", "equipmentManager", "Lcom/example/pet/EquipmentManager;", "petImageView", "hatImageView", "glassesImageView", "bodyImageView", "accessory1ImageView", "accessory2ImageView", "moodManager", "Lcom/example/pet/MoodManager;", "moodProgressBar", "Landroid/widget/ProgressBar;", "moodTextView", "Landroid/widget/TextView;", "moodScoreTextView", "todayProgressBar", "todayProgressText", "currencyManager", "Lcom/example/pet/CurrencyManager;", "tvCoinCount", "walletImage", "accessoryPositions", "", "", "Lcom/example/pet/AccessoryPosition;", "requestPermissionLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "", "kotlin.jvm.PlatformType", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "updateBackground", "updateCoinsFromFragment", "updateCoinDisplay", "markPillAndReward", "pill", "Lcom/example/pet/Pill;", "checkCurrentNotifications", "updateMoodDisplay", "setupNavigation", "loadEquippedItems", "updatePetCustomization", "equippedItemIds", "", "applyAccessory", "imageView", "item", "Lcom/example/pet/CustomizationItem;", "petWidth", "", "petHeight", "getCustomizationItems", "refreshPetCustomization", "checkNotificationPermission", "requestNotificationPermission", "hasNotificationPermission", "", "hasExactAlarmPermission", "onResume", "Companion", "app_debug"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class MainActivity extends AppCompatActivity {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static MainActivity instance;
    private ImageView accessory1ImageView;
    private ImageView accessory2ImageView;
    private final Map<Integer, AccessoryPosition> accessoryPositions = MapsKt.mapOf(TuplesKt.to(1, new AccessoryPosition(0.4f, 0.2f, 0.02f, -0.35f)), TuplesKt.to(5, new AccessoryPosition(0.4f, 0.15f, 0.02f, -0.3f)), TuplesKt.to(6, new AccessoryPosition(0.4f, 0.35f, 0.03f, -0.32f)), TuplesKt.to(8, new AccessoryPosition(0.35f, 0.22f, 0.02f, -0.3f)), TuplesKt.to(10, new AccessoryPosition(0.65f, 0.4f, 0.04f, -0.3f)), TuplesKt.to(2, new AccessoryPosition(0.35f, 0.1f, 0.03f, -0.15f)), TuplesKt.to(7, new AccessoryPosition(0.35f, 0.15f, 0.03f, -0.15f)), TuplesKt.to(3, new AccessoryPosition(0.85f, 0.55f, 0.01f, 0.3f)), TuplesKt.to(4, new AccessoryPosition(0.75f, 0.65f, 0.0f, 0.3f)), TuplesKt.to(9, new AccessoryPosition(0.9f, 0.5f, 0.0f, 0.25f)));
    private ImageView backgroundImageView;
    private ImageView bodyImageView;
    private CurrencyManager currencyManager;
    private EquipmentManager equipmentManager;
    private ImageView glassesImageView;
    private ImageView hatImageView;
    private MoodManager moodManager;
    private ProgressBar moodProgressBar;
    private TextView moodScoreTextView;
    private TextView moodTextView;
    private ImageView petImageView;
    private PreferenceHelper prefs;
    private final ActivityResultLauncher<String> requestPermissionLauncher;
    private ProgressBar todayProgressBar;
    private TextView todayProgressText;
    private TextView tvCoinCount;
    private ImageView walletImage;

    public MainActivity() {
        ActivityResultLauncher<String> activityResultLauncherRegisterForActivityResult = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback() { // from class: com.example.pet.MainActivity$$ExternalSyntheticLambda1
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                MainActivity.requestPermissionLauncher$lambda$0(this.f$0, (Boolean) obj);
            }
        });
        Intrinsics.checkNotNullExpressionValue(activityResultLauncherRegisterForActivityResult, "registerForActivityResult(...)");
        this.requestPermissionLauncher = activityResultLauncherRegisterForActivityResult;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void requestPermissionLauncher$lambda$0(MainActivity this$0, Boolean isGranted) {
        if (isGranted.booleanValue()) {
            Toast.makeText(this$0, "Разрешение на уведомления получено", 0).show();
        } else {
            Toast.makeText(this$0, "Уведомления отключены", 1).show();
        }
    }

    /* JADX INFO: compiled from: MainActivity.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n\u0000¨\u0006\u0007"}, d2 = {"Lcom/example/pet/MainActivity$Companion;", "", "<init>", "()V", "instance", "Lcom/example/pet/MainActivity;", "getInstance", "app_debug"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final MainActivity getInstance() {
            MainActivity mainActivity = MainActivity.instance;
            if (mainActivity != null) {
                return mainActivity;
            }
            Intrinsics.throwUninitializedPropertyAccessException("instance");
            return null;
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;
        this.prefs = new PreferenceHelper(this);
        this.moodManager = new MoodManager(this);
        this.equipmentManager = new EquipmentManager(this);
        this.currencyManager = new CurrencyManager(this);
        this.petImageView = (ImageView) findViewById(R.id.pet_crying);
        this.hatImageView = (ImageView) findViewById(R.id.hat_layer);
        this.glassesImageView = (ImageView) findViewById(R.id.glasses_layer);
        this.bodyImageView = (ImageView) findViewById(R.id.body_layer);
        this.accessory1ImageView = (ImageView) findViewById(R.id.accessory1_layer);
        this.accessory2ImageView = (ImageView) findViewById(R.id.accessory2_layer);
        this.moodProgressBar = (ProgressBar) findViewById(R.id.moodProgressBar);
        this.moodTextView = (TextView) findViewById(R.id.moodTextView);
        this.moodScoreTextView = (TextView) findViewById(R.id.moodScoreTextView);
        this.todayProgressBar = (ProgressBar) findViewById(R.id.todayProgressBar);
        this.todayProgressText = (TextView) findViewById(R.id.todayProgressText);
        this.walletImage = (ImageView) findViewById(R.id.walletImage);
        this.tvCoinCount = (TextView) findViewById(R.id.tvCoinCount);
        this.backgroundImageView = (ImageView) findViewById(R.id.background_forest);
        updateBackground();
        checkNotificationPermission();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame, new TabletFragment()).commit();
        MoodManager moodManager = this.moodManager;
        if (moodManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("moodManager");
            moodManager = null;
        }
        moodManager.checkAndUpdateMood();
        updateMoodDisplay();
        setupNavigation();
        loadEquippedItems();
        updateCoinDisplay();
    }

    public final void updateBackground() {
        EquipmentManager equipmentManager = new EquipmentManager(this);
        int backgroundId = equipmentManager.getEquippedBackgroundId();
        ImageView backgroundImageView = (ImageView) findViewById(R.id.background_forest);
        System.out.println((Object) ("DEBUG: Changing background to ID: " + backgroundId));
        switch (backgroundId) {
            case TypedValues.TYPE_TARGET /* 101 */:
                backgroundImageView.setImageResource(R.drawable.background_forest);
                break;
            case LocationRequestCompat.QUALITY_BALANCED_POWER_ACCURACY /* 102 */:
                backgroundImageView.setImageResource(R.drawable.background_beach);
                break;
            case 103:
                backgroundImageView.setImageResource(R.drawable.background_sea);
                break;
            case LocationRequestCompat.QUALITY_LOW_POWER /* 104 */:
                backgroundImageView.setImageResource(R.drawable.background_swamp);
                break;
            default:
                backgroundImageView.setImageResource(R.drawable.background_forest);
                break;
        }
    }

    public final void updateCoinsFromFragment() {
        runOnUiThread(new Runnable() { // from class: com.example.pet.MainActivity$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.updateCoinDisplay();
            }
        });
    }

    public final void updateCoinDisplay() {
        CurrencyManager currencyManager = this.currencyManager;
        ImageView imageView = null;
        if (currencyManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("currencyManager");
            currencyManager = null;
        }
        int coins = currencyManager.getCurrentCoins();
        TextView textView = this.tvCoinCount;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvCoinCount");
            textView = null;
        }
        textView.setText(String.valueOf(coins));
        if (coins > 0) {
            TextView textView2 = this.tvCoinCount;
            if (textView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvCoinCount");
                textView2 = null;
            }
            textView2.animate().scaleX(1.3f).scaleY(1.3f).setDuration(150L).withEndAction(new Runnable() { // from class: com.example.pet.MainActivity$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    MainActivity.updateCoinDisplay$lambda$2(this.f$0);
                }
            });
            ImageView imageView2 = this.walletImage;
            if (imageView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("walletImage");
            } else {
                imageView = imageView2;
            }
            imageView.animate().rotationBy(360.0f).setDuration(300L).start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void updateCoinDisplay$lambda$2(MainActivity this$0) {
        TextView textView = this$0.tvCoinCount;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvCoinCount");
            textView = null;
        }
        textView.animate().scaleX(1.0f).scaleY(1.0f).setDuration(150L);
    }

    public final void markPillAndReward(Pill pill) {
        Intrinsics.checkNotNullParameter(pill, "pill");
        MoodManager moodManager = this.moodManager;
        CurrencyManager currencyManager = null;
        if (moodManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("moodManager");
            moodManager = null;
        }
        boolean success = moodManager.markPillAsTaken(pill.getName());
        if (success) {
            CurrencyManager currencyManager2 = this.currencyManager;
            if (currencyManager2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("currencyManager");
            } else {
                currencyManager = currencyManager2;
            }
            currencyManager.rewardForMarkedPill(pill.getName());
            updateCoinDisplay();
            Toast.makeText(this, "✓ " + pill.getName() + " выполнено! +5 монет", 0).show();
            updateMoodDisplay();
            return;
        }
        Toast.makeText(this, "⚠️ " + pill.getName() + " уже отмечено!", 0).show();
    }

    private final void checkCurrentNotifications() throws IOException {
        DbHelper dbHelper = new DbHelper(this, null);
        List<Pill> pillsForDate = dbHelper.getPillsForDate(new Date());
        System.out.println((Object) "=== ДЕБАГ ИНФОРМАЦИЯ ===");
        System.out.println((Object) ("Лекарств в БД на сегодня: " + pillsForDate.size()));
        int i = 0;
        for (Object obj : pillsForDate) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            Pill pill = (Pill) obj;
            System.out.println((Object) (i + ". " + pill.getName() + " - " + pill.getIntakeTime()));
            i = i2;
        }
        MoodManager moodManager = new MoodManager(this);
        System.out.println((Object) ("Баллы настроения: " + moodManager.getCurrentScore()));
        System.out.println((Object) ("Отмечено сегодня: " + moodManager.getTodayProgressText()));
        Toast.makeText(this, "Проверка: " + pillsForDate.size() + " напоминаний на сегодня", 1).show();
        dbHelper.close();
    }

    public final void updateMoodDisplay() {
        MoodManager moodManager = this.moodManager;
        MoodManager moodManager2 = null;
        if (moodManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("moodManager");
            moodManager = null;
        }
        int currentScore = moodManager.getCurrentScore();
        MoodManager moodManager3 = this.moodManager;
        if (moodManager3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("moodManager");
            moodManager3 = null;
        }
        int todayPercent = moodManager3.getTodayCompletionPercent();
        ProgressBar progressBar = this.moodProgressBar;
        if (progressBar == null) {
            Intrinsics.throwUninitializedPropertyAccessException("moodProgressBar");
            progressBar = null;
        }
        progressBar.setProgress(currentScore);
        ProgressBar progressBar2 = this.moodProgressBar;
        if (progressBar2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("moodProgressBar");
            progressBar2 = null;
        }
        MoodManager moodManager4 = this.moodManager;
        if (moodManager4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("moodManager");
            moodManager4 = null;
        }
        progressBar2.setProgressTintList(ColorStateList.valueOf(moodManager4.getMoodColor()));
        TextView textView = this.moodTextView;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("moodTextView");
            textView = null;
        }
        MoodManager moodManager5 = this.moodManager;
        if (moodManager5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("moodManager");
            moodManager5 = null;
        }
        textView.setText(moodManager5.getMoodDescription());
        TextView textView2 = this.moodTextView;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("moodTextView");
            textView2 = null;
        }
        MoodManager moodManager6 = this.moodManager;
        if (moodManager6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("moodManager");
            moodManager6 = null;
        }
        textView2.setTextColor(moodManager6.getMoodColor());
        TextView textView3 = this.moodScoreTextView;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("moodScoreTextView");
            textView3 = null;
        }
        textView3.setText(currentScore + "/100");
        ProgressBar progressBar3 = this.todayProgressBar;
        if (progressBar3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("todayProgressBar");
            progressBar3 = null;
        }
        progressBar3.setProgress(todayPercent);
        TextView textView4 = this.todayProgressText;
        if (textView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("todayProgressText");
            textView4 = null;
        }
        MoodManager moodManager7 = this.moodManager;
        if (moodManager7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("moodManager");
            moodManager7 = null;
        }
        textView4.setText("Сегодня: " + moodManager7.getTodayProgressText());
        ImageView imageView = this.petImageView;
        if (imageView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("petImageView");
            imageView = null;
        }
        MoodManager moodManager8 = this.moodManager;
        if (moodManager8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("moodManager");
        } else {
            moodManager2 = moodManager8;
        }
        imageView.setImageResource(moodManager2.getPetImageResId());
    }

    private final void setupNavigation() {
        ImageButton button1 = (ImageButton) findViewById(R.id.button);
        ImageButton button2 = (ImageButton) findViewById(R.id.button2);
        ImageButton button3 = (ImageButton) findViewById(R.id.button3);
        ImageButton button4 = (ImageButton) findViewById(R.id.button4);
        button1.setOnClickListener(new View.OnClickListener() { // from class: com.example.pet.MainActivity$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.setupNavigation$lambda$4(this.f$0, view);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() { // from class: com.example.pet.MainActivity$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.setupNavigation$lambda$5(this.f$0, view);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() { // from class: com.example.pet.MainActivity$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.setupNavigation$lambda$6(this.f$0, view);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() { // from class: com.example.pet.MainActivity$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.setupNavigation$lambda$7(this.f$0, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupNavigation$lambda$4(MainActivity this$0, View it) {
        GamesListFragment gamesListFragment = new GamesListFragment();
        this$0.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame, gamesListFragment).addToBackStack(null).commit();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupNavigation$lambda$5(MainActivity this$0, View it) {
        CustomizationFragment customizationFragment = new CustomizationFragment();
        this$0.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame, customizationFragment).addToBackStack(null).commit();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupNavigation$lambda$6(MainActivity this$0, View it) {
        AddPillFragment addPillFragment = new AddPillFragment();
        this$0.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame, addPillFragment).addToBackStack(null).commit();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupNavigation$lambda$7(MainActivity this$0, View it) {
        TabletFragment tabletFragment = new TabletFragment();
        this$0.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame, tabletFragment).commit();
    }

    private final void loadEquippedItems() {
        EquipmentManager equipmentManager = this.equipmentManager;
        if (equipmentManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("equipmentManager");
            equipmentManager = null;
        }
        updatePetCustomization(equipmentManager.getEquippedItems());
    }

    public final void updatePetCustomization(final List<Integer> equippedItemIds) {
        ImageView imageView;
        String str;
        List equippedItems;
        Object next;
        CustomizationItem headItem;
        Object next2;
        CustomizationItem faceItem;
        Object next3;
        ImageView imageView2;
        Object next4;
        Intrinsics.checkNotNullParameter(equippedItemIds, "equippedItemIds");
        ImageView imageView3 = this.hatImageView;
        String str2 = "hatImageView";
        if (imageView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("hatImageView");
            imageView3 = null;
        }
        imageView3.setVisibility(8);
        ImageView imageView4 = this.glassesImageView;
        if (imageView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("glassesImageView");
            imageView4 = null;
        }
        imageView4.setVisibility(8);
        ImageView imageView5 = this.bodyImageView;
        if (imageView5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bodyImageView");
            imageView5 = null;
        }
        imageView5.setVisibility(8);
        ImageView imageView6 = this.accessory1ImageView;
        if (imageView6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("accessory1ImageView");
            imageView6 = null;
        }
        imageView6.setVisibility(8);
        ImageView imageView7 = this.accessory2ImageView;
        if (imageView7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("accessory2ImageView");
            imageView7 = null;
        }
        imageView7.setVisibility(8);
        ImageView imageView8 = this.petImageView;
        if (imageView8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("petImageView");
            imageView8 = null;
        }
        float petWidth = imageView8.getWidth();
        ImageView imageView9 = this.petImageView;
        if (imageView9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("petImageView");
            imageView9 = null;
        }
        float petHeight = imageView9.getHeight();
        if (!(petWidth == 0.0f)) {
            if (!(petHeight == 0.0f)) {
                List<CustomizationItem> customizationItems = getCustomizationItems();
                Collection arrayList = new ArrayList();
                Iterator it = equippedItemIds.iterator();
                while (it.hasNext()) {
                    int iIntValue = ((Number) it.next()).intValue();
                    Iterator<T> it2 = customizationItems.iterator();
                    while (true) {
                        if (it2.hasNext()) {
                            next4 = it2.next();
                            if (((CustomizationItem) next4).getId() == iIntValue) {
                                break;
                            }
                        } else {
                            next4 = null;
                            break;
                        }
                    }
                    CustomizationItem customizationItem = (CustomizationItem) next4;
                    if (customizationItem != null) {
                        arrayList.add(customizationItem);
                    }
                }
                List equippedItems2 = (List) arrayList;
                Iterator it3 = equippedItems2.iterator();
                while (true) {
                    if (!it3.hasNext()) {
                        str = str2;
                        equippedItems = equippedItems2;
                        next = null;
                        break;
                    } else {
                        next = it3.next();
                        str = str2;
                        equippedItems = equippedItems2;
                        if (Intrinsics.areEqual(((CustomizationItem) next).getCategory(), "head")) {
                            break;
                        }
                        str2 = str;
                        equippedItems2 = equippedItems;
                    }
                }
                CustomizationItem headItem2 = (CustomizationItem) next;
                List list = equippedItems;
                Iterator it4 = list.iterator();
                while (true) {
                    if (!it4.hasNext()) {
                        headItem = headItem2;
                        next2 = null;
                        break;
                    }
                    next2 = it4.next();
                    headItem = headItem2;
                    Iterable iterable = list;
                    if (Intrinsics.areEqual(((CustomizationItem) next2).getCategory(), "face")) {
                        break;
                    }
                    headItem2 = headItem;
                    list = iterable;
                }
                CustomizationItem faceItem2 = (CustomizationItem) next2;
                List list2 = equippedItems;
                Iterator it5 = list2.iterator();
                while (true) {
                    if (!it5.hasNext()) {
                        faceItem = faceItem2;
                        next3 = null;
                        break;
                    }
                    next3 = it5.next();
                    faceItem = faceItem2;
                    Iterable iterable2 = list2;
                    if (Intrinsics.areEqual(((CustomizationItem) next3).getCategory(), "body")) {
                        break;
                    }
                    faceItem2 = faceItem;
                    list2 = iterable2;
                }
                CustomizationItem bodyItem = (CustomizationItem) next3;
                List list3 = equippedItems;
                Collection arrayList2 = new ArrayList();
                for (Object obj : list3) {
                    CustomizationItem bodyItem2 = bodyItem;
                    Iterable iterable3 = list3;
                    List<CustomizationItem> list4 = customizationItems;
                    if (Intrinsics.areEqual(((CustomizationItem) obj).getCategory(), "accessory")) {
                        arrayList2.add(obj);
                    }
                    bodyItem = bodyItem2;
                    list3 = iterable3;
                    customizationItems = list4;
                }
                CustomizationItem bodyItem3 = bodyItem;
                List accessoryItems = (List) arrayList2;
                if (headItem != null) {
                    CustomizationItem customizationItem2 = headItem;
                    ImageView imageView10 = this.hatImageView;
                    if (imageView10 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException(str);
                        imageView10 = null;
                    }
                    applyAccessory(imageView10, customizationItem2, petWidth, petHeight);
                }
                if (faceItem != null) {
                    CustomizationItem customizationItem3 = faceItem;
                    ImageView imageView11 = this.glassesImageView;
                    if (imageView11 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("glassesImageView");
                        imageView11 = null;
                    }
                    applyAccessory(imageView11, customizationItem3, petWidth, petHeight);
                }
                if (bodyItem3 != null) {
                    ImageView imageView12 = this.bodyImageView;
                    if (imageView12 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("bodyImageView");
                        imageView12 = null;
                    }
                    applyAccessory(imageView12, bodyItem3, petWidth, petHeight);
                }
                int i = 0;
                for (Object obj2 : CollectionsKt.take(accessoryItems, 2)) {
                    int i2 = i + 1;
                    if (i < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    CustomizationItem customizationItem4 = (CustomizationItem) obj2;
                    if (i == 0) {
                        imageView2 = this.accessory1ImageView;
                        if (imageView2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("accessory1ImageView");
                            imageView2 = null;
                        }
                    } else {
                        imageView2 = this.accessory2ImageView;
                        if (imageView2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("accessory2ImageView");
                            imageView2 = null;
                        }
                    }
                    applyAccessory(imageView2, customizationItem4, petWidth, petHeight);
                    i = i2;
                }
                return;
            }
        }
        ImageView imageView13 = this.petImageView;
        if (imageView13 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("petImageView");
            imageView = null;
        } else {
            imageView = imageView13;
        }
        imageView.post(new Runnable() { // from class: com.example.pet.MainActivity$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.updatePetCustomization(equippedItemIds);
            }
        });
    }

    private final void applyAccessory(ImageView imageView, CustomizationItem item, float petWidth, float petHeight) {
        AccessoryPosition position = this.accessoryPositions.get(Integer.valueOf(item.getId()));
        imageView.setImageResource(item.getDrawableId());
        imageView.setVisibility(0);
        if (position != null) {
            int widthPx = (int) (position.getWidthPercent() * petWidth);
            int heightPx = (int) (position.getHeightPercent() * petHeight);
            float offsetXPx = position.getOffsetXPercent() * petWidth;
            float offsetYPx = position.getOffsetYPercent() * petHeight;
            ViewGroup.LayoutParams params = imageView.getLayoutParams();
            params.width = widthPx;
            params.height = heightPx;
            imageView.setLayoutParams(params);
            imageView.setTranslationX(offsetXPx);
            imageView.setTranslationY(offsetYPx);
        }
        imageView.requestLayout();
    }

    private final List<CustomizationItem> getCustomizationItems() {
        return CollectionsKt.listOf((Object[]) new CustomizationItem[]{new CustomizationItem(1, "Шляпа", R.drawable.hat_1, 100, false, false, "head", null, CollectionsKt.listOf((Object[]) new Integer[]{5, 6, 8, 10}), 176, null), new CustomizationItem(2, "Очки", R.drawable.glasses_1, 150, false, false, "face", null, CollectionsKt.listOf(7), 176, null), new CustomizationItem(3, "Пиджак", R.drawable.jacket, 80, false, false, "body", null, CollectionsKt.listOf(4), 176, null), new CustomizationItem(4, "Пальто", R.drawable.coat, 120, false, false, "body", null, CollectionsKt.listOf(3), 176, null), new CustomizationItem(5, "Бант", R.drawable.bow, MaterialCardViewHelper.DEFAULT_FADE_ANIM_DURATION, false, false, "head", null, CollectionsKt.listOf((Object[]) new Integer[]{1, 6, 8, 10}), 176, null), new CustomizationItem(6, "Кепка", R.drawable.cap, 90, false, false, "head", null, CollectionsKt.listOf((Object[]) new Integer[]{1, 5, 8, 10}), 176, null), new CustomizationItem(7, "Деловые очки", R.drawable.glasses_2, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, false, false, "face", null, CollectionsKt.listOf(2), 176, null), new CustomizationItem(8, "Ободок", R.drawable.headband, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, false, false, "head", null, CollectionsKt.listOf((Object[]) new Integer[]{1, 5, 6, 10}), 176, null), new CustomizationItem(9, "Шарф", R.drawable.scarf, 150, false, false, "accessory", null, null, 432, null), new CustomizationItem(10, "Ковбойская шляпа", R.drawable.hat_2, 180, false, false, "head", null, CollectionsKt.listOf((Object[]) new Integer[]{1, 5, 6, 8}), 176, null)});
    }

    public final void refreshPetCustomization() {
        EquipmentManager equipmentManager = this.equipmentManager;
        if (equipmentManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("equipmentManager");
            equipmentManager = null;
        }
        updatePetCustomization(equipmentManager.getEquippedItems());
    }

    private final void checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= 33) {
            if (ContextCompat.checkSelfPermission(this, "android.permission.POST_NOTIFICATIONS") == 0) {
                System.out.println((Object) "DEBUG: Notification permission granted");
                return;
            } else if (shouldShowRequestPermissionRationale("android.permission.POST_NOTIFICATIONS")) {
                Toast.makeText(this, "Для уведомлений о приеме лекарств нужно разрешение", 1).show();
                requestNotificationPermission();
                return;
            } else {
                requestNotificationPermission();
                return;
            }
        }
        System.out.println((Object) "DEBUG: Android version < 13, no permission needed");
    }

    private final void requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= 33) {
            this.requestPermissionLauncher.launch("android.permission.POST_NOTIFICATIONS");
        }
    }

    public final boolean hasNotificationPermission() {
        return Build.VERSION.SDK_INT < 33 || ContextCompat.checkSelfPermission(this, "android.permission.POST_NOTIFICATIONS") == 0;
    }

    public final boolean hasExactAlarmPermission() {
        if (Build.VERSION.SDK_INT >= 31) {
            Object systemService = getSystemService(NotificationCompat.CATEGORY_ALARM);
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.AlarmManager");
            AlarmManager alarmManager = (AlarmManager) systemService;
            return alarmManager.canScheduleExactAlarms();
        }
        return true;
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        updateCoinDisplay();
    }
}
