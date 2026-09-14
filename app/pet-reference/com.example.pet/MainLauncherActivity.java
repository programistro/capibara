package com.example.pet;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MainLauncherActivity.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0012\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\tH\u0014R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lcom/example/pet/MainLauncherActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "<init>", "()V", "prefs", "Lcom/example/pet/PreferenceHelper;", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "app_debug"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class MainLauncherActivity extends AppCompatActivity {
    private PreferenceHelper prefs;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.prefs = new PreferenceHelper(this);
        MoodManager moodManager = new MoodManager(this);
        moodManager.checkAndUpdateMood();
        PreferenceHelper preferenceHelper = this.prefs;
        if (preferenceHelper == null) {
            Intrinsics.throwUninitializedPropertyAccessException("prefs");
            preferenceHelper = null;
        }
        if (!PreferenceHelper.getBoolean$default(preferenceHelper, "isRegistered", false, 2, null)) {
            startActivity(new Intent(this, (Class<?>) RegistrationActivity.class));
        } else {
            PreferenceHelper preferenceHelper2 = this.prefs;
            if (preferenceHelper2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("prefs");
                preferenceHelper2 = null;
            }
            if (!PreferenceHelper.getBoolean$default(preferenceHelper2, "isPetNamed", false, 2, null)) {
                startActivity(new Intent(this, (Class<?>) PetNameActivity.class));
            } else {
                startActivity(new Intent(this, (Class<?>) AuthorisationActivity.class));
            }
        }
        finish();
    }
}
