package com.example.pet;

import com.example.capibara.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: WalletView.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B'\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0004\b\b\u0010\tJ\b\u0010\u000f\u001a\u00020\u0010H\u0002J\u000e\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u0007J\u0006\u0010\u0013\u001a\u00020\u0007R\u000e\u0010\n\u001a\u00020\u000bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lcom/example/pet/WalletView;", "Landroid/widget/FrameLayout;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "defStyleAttr", "", "<init>", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "walletImage", "Landroid/widget/ImageView;", "coinText", "Landroid/widget/TextView;", "coinCount", "setupView", "", "setCoinCount", "count", "getCoinCount", "app_debug"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class WalletView extends FrameLayout {
    private int coinCount;
    private TextView coinText;
    private ImageView walletImage;

    public /* synthetic */ WalletView(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public WalletView(Context context) {
        this(context, null, 0, 6, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public WalletView(Context context, AttributeSet attrs) {
        this(context, attrs, 0, 4, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WalletView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Intrinsics.checkNotNullParameter(context, "context");
        setupView();
    }

    private final void setupView() {
        ImageView imageView = new ImageView(getContext());
        imageView.setLayoutParams(new FrameLayout.LayoutParams(-2, -2, 17));
        imageView.setImageResource(R.drawable.wallet);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setAdjustViewBounds(true);
        this.walletImage = imageView;
        TextView textView = new TextView(getContext());
        textView.setLayoutParams(new FrameLayout.LayoutParams(-2, -2, 17));
        textView.setText("0");
        textView.setTextSize(14.0f);
        textView.setTextColor(-1);
        textView.setTypeface(textView.getTypeface(), 1);
        textView.setShadowLayer(2.0f, 1.0f, 1.0f, ViewCompat.MEASURED_STATE_MASK);
        this.coinText = textView;
        ImageView imageView2 = this.walletImage;
        TextView textView2 = null;
        if (imageView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("walletImage");
            imageView2 = null;
        }
        addView(imageView2);
        TextView textView3 = this.coinText;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("coinText");
        } else {
            textView2 = textView3;
        }
        addView(textView2);
    }

    public final void setCoinCount(int count) {
        this.coinCount = count;
        TextView textView = this.coinText;
        TextView textView2 = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("coinText");
            textView = null;
        }
        textView.setText(String.valueOf(count));
        TextView textView3 = this.coinText;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("coinText");
        } else {
            textView2 = textView3;
        }
        textView2.animate().scaleX(1.3f).scaleY(1.3f).setDuration(150L).withEndAction(new Runnable() { // from class: com.example.pet.WalletView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                WalletView.setCoinCount$lambda$2(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setCoinCount$lambda$2(WalletView this$0) {
        TextView textView = this$0.coinText;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("coinText");
            textView = null;
        }
        textView.animate().scaleX(1.0f).scaleY(1.0f).setDuration(150L);
    }

    public final int getCoinCount() {
        return this.coinCount;
    }
}
