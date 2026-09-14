package com.example.pet;

import com.example.capibara.R;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/* JADX INFO: compiled from: TetrisActivity.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000W\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006*\u0001\u0017\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0012\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0014J\b\u0010\u001d\u001a\u00020\u001aH\u0002J\b\u0010\u001e\u001a\u00020\u001aH\u0002J\b\u0010\u001f\u001a\u00020\u001aH\u0002J\u001a\u0010 \u001a\u00020\u00132\u0006\u0010!\u001a\u00020\"2\b\u0010#\u001a\u0004\u0018\u00010$H\u0016J\b\u0010%\u001a\u00020\u001aH\u0014J\b\u0010&\u001a\u00020\u001aH\u0014J\b\u0010'\u001a\u00020\u001aH\u0014J\u0010\u0010(\u001a\u00020\u001a2\u0006\u0010)\u001a\u00020\"H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0007X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u000bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0018¨\u0006*"}, d2 = {"Lcom/example/pet/TetrisActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "<init>", "()V", "tetrisView", "Lcom/example/pet/TetrisView;", "tvScore", "Landroid/widget/TextView;", "tvLevel", "tvLines", "btnPause", "Landroid/widget/Button;", "btnLeft", "btnRight", "btnRotate", "btnDown", "gameSpeed", "", "isPaused", "", "handler", "Landroid/os/Handler;", "gameLoop", "com/example/pet/TetrisActivity$gameLoop$1", "Lcom/example/pet/TetrisActivity$gameLoop$1;", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "initViews", "setupControls", "startGame", "onKeyDown", "keyCode", "", NotificationCompat.CATEGORY_EVENT, "Landroid/view/KeyEvent;", "onPause", "onResume", "onDestroy", "awardCoins", "score", "app_debug"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class TetrisActivity extends AppCompatActivity {
    private Button btnDown;
    private Button btnLeft;
    private Button btnPause;
    private Button btnRight;
    private Button btnRotate;
    private boolean isPaused;
    private TetrisView tetrisView;
    private TextView tvLevel;
    private TextView tvLines;
    private TextView tvScore;
    private long gameSpeed = 500;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final TetrisActivity$gameLoop$1 gameLoop = new Runnable() { // from class: com.example.pet.TetrisActivity$gameLoop$1
        @Override // java.lang.Runnable
        public void run() {
            if (!this.this$0.isPaused) {
                TetrisView tetrisView = this.this$0.tetrisView;
                if (tetrisView == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("tetrisView");
                    tetrisView = null;
                }
                tetrisView.moveDown();
                this.this$0.handler.postDelayed(this, this.this$0.gameSpeed);
            }
        }
    };

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tetris);
        initViews();
        setupControls();
        startGame();
    }

    private final void initViews() {
        this.tetrisView = (TetrisView) findViewById(R.id.tetrisView);
        this.tvScore = (TextView) findViewById(R.id.tvScore);
        this.tvLevel = (TextView) findViewById(R.id.tvLevel);
        this.tvLines = (TextView) findViewById(R.id.tvLines);
        this.btnPause = (Button) findViewById(R.id.btnPause);
        this.btnLeft = (Button) findViewById(R.id.btnLeft);
        this.btnRight = (Button) findViewById(R.id.btnRight);
        this.btnRotate = (Button) findViewById(R.id.btnRotate);
        this.btnDown = (Button) findViewById(R.id.btnDown);
        TetrisView tetrisView = this.tetrisView;
        if (tetrisView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tetrisView");
            tetrisView = null;
        }
        tetrisView.setOnGameUpdate(new Function3() { // from class: com.example.pet.TetrisActivity$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                return TetrisActivity.initViews$lambda$0(this.f$0, ((Integer) obj).intValue(), ((Integer) obj2).intValue(), ((Integer) obj3).intValue());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit initViews$lambda$0(TetrisActivity this$0, int score, int lines, int level) {
        TextView textView = this$0.tvScore;
        TextView textView2 = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvScore");
            textView = null;
        }
        textView.setText("Очки: " + score);
        TextView textView3 = this$0.tvLines;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvLines");
            textView3 = null;
        }
        textView3.setText("Линии: " + lines);
        TextView textView4 = this$0.tvLevel;
        if (textView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvLevel");
        } else {
            textView2 = textView4;
        }
        textView2.setText("Уровень: " + level);
        this$0.gameSpeed = RangesKt.coerceAtLeast(500 - ((long) (level * 40)), 100L);
        return Unit.INSTANCE;
    }

    private final void setupControls() {
        Button button = this.btnPause;
        Button button2 = null;
        if (button == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnPause");
            button = null;
        }
        button.setOnClickListener(new View.OnClickListener() { // from class: com.example.pet.TetrisActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TetrisActivity.setupControls$lambda$1(this.f$0, view);
            }
        });
        Button button3 = this.btnLeft;
        if (button3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnLeft");
            button3 = null;
        }
        button3.setOnClickListener(new View.OnClickListener() { // from class: com.example.pet.TetrisActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TetrisActivity.setupControls$lambda$2(this.f$0, view);
            }
        });
        Button button4 = this.btnRight;
        if (button4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnRight");
            button4 = null;
        }
        button4.setOnClickListener(new View.OnClickListener() { // from class: com.example.pet.TetrisActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TetrisActivity.setupControls$lambda$3(this.f$0, view);
            }
        });
        Button button5 = this.btnRotate;
        if (button5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnRotate");
            button5 = null;
        }
        button5.setOnClickListener(new View.OnClickListener() { // from class: com.example.pet.TetrisActivity$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TetrisActivity.setupControls$lambda$4(this.f$0, view);
            }
        });
        Button button6 = this.btnDown;
        if (button6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnDown");
        } else {
            button2 = button6;
        }
        button2.setOnClickListener(new View.OnClickListener() { // from class: com.example.pet.TetrisActivity$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TetrisActivity.setupControls$lambda$5(this.f$0, view);
            }
        });
        ((Button) findViewById(R.id.btnBack)).setOnClickListener(new View.OnClickListener() { // from class: com.example.pet.TetrisActivity$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.finish();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupControls$lambda$1(TetrisActivity this$0, View it) {
        this$0.isPaused = !this$0.isPaused;
        Button button = this$0.btnPause;
        if (button == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnPause");
            button = null;
        }
        button.setText(this$0.isPaused ? "Продолжить" : "Пауза");
        if (!this$0.isPaused) {
            this$0.handler.post(this$0.gameLoop);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupControls$lambda$2(TetrisActivity this$0, View it) {
        TetrisView tetrisView = this$0.tetrisView;
        if (tetrisView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tetrisView");
            tetrisView = null;
        }
        tetrisView.moveLeft();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupControls$lambda$3(TetrisActivity this$0, View it) {
        TetrisView tetrisView = this$0.tetrisView;
        if (tetrisView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tetrisView");
            tetrisView = null;
        }
        tetrisView.moveRight();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupControls$lambda$4(TetrisActivity this$0, View it) {
        TetrisView tetrisView = this$0.tetrisView;
        if (tetrisView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tetrisView");
            tetrisView = null;
        }
        tetrisView.rotate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupControls$lambda$5(TetrisActivity this$0, View it) {
        TetrisView tetrisView = this$0.tetrisView;
        if (tetrisView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tetrisView");
            tetrisView = null;
        }
        tetrisView.moveDown();
    }

    private final void startGame() {
        TetrisView tetrisView = this.tetrisView;
        Button button = null;
        if (tetrisView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tetrisView");
            tetrisView = null;
        }
        tetrisView.resetGame();
        this.isPaused = false;
        Button button2 = this.btnPause;
        if (button2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnPause");
        } else {
            button = button2;
        }
        button.setText("Пауза");
        this.handler.post(this.gameLoop);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        TetrisView tetrisView = null;
        switch (keyCode) {
            case 19:
                TetrisView tetrisView2 = this.tetrisView;
                if (tetrisView2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("tetrisView");
                } else {
                    tetrisView = tetrisView2;
                }
                tetrisView.rotate();
                return true;
            case 20:
                TetrisView tetrisView3 = this.tetrisView;
                if (tetrisView3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("tetrisView");
                } else {
                    tetrisView = tetrisView3;
                }
                tetrisView.moveDown();
                return true;
            case 21:
                TetrisView tetrisView4 = this.tetrisView;
                if (tetrisView4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("tetrisView");
                } else {
                    tetrisView = tetrisView4;
                }
                tetrisView.moveLeft();
                return true;
            case 22:
                TetrisView tetrisView5 = this.tetrisView;
                if (tetrisView5 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("tetrisView");
                } else {
                    tetrisView = tetrisView5;
                }
                tetrisView.moveRight();
                return true;
            default:
                return super.onKeyDown(keyCode, event);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
        this.isPaused = true;
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        if (!this.isPaused) {
            this.handler.post(this.gameLoop);
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        this.handler.removeCallbacks(this.gameLoop);
    }

    private final void awardCoins(int score) {
        int coinsEarned = score / 100;
        if (coinsEarned > 0) {
            CurrencyManager currencyManager = new CurrencyManager(this);
            currencyManager.addCoins(coinsEarned);
            Toast.makeText(this, "+" + coinsEarned + " монет за игру!", 1).show();
        }
    }
}
