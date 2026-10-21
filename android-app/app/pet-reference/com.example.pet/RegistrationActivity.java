package com.example.pet;

import com.example.capibara.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: RegistrationActivity.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0012\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\tH\u0014R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lcom/example/pet/RegistrationActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "<init>", "()V", "prefs", "Lcom/example/pet/PreferenceHelper;", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "app_debug"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class RegistrationActivity extends AppCompatActivity {
    private PreferenceHelper prefs;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);
        this.prefs = new PreferenceHelper(this);
        View viewFindViewById = findViewById(R.id.user_password);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
        final EditText userPassword = (EditText) viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.user_repeat_password);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(...)");
        final EditText userPasswordRepeat = (EditText) viewFindViewById2;
        View viewFindViewById3 = findViewById(R.id.button_registration);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(...)");
        Button buttonRegistration = (Button) viewFindViewById3;
        buttonRegistration.setOnClickListener(new View.OnClickListener() { // from class: com.example.pet.RegistrationActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                RegistrationActivity.onCreate$lambda$0(userPassword, userPasswordRepeat, this, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(EditText $userPassword, EditText $userPasswordRepeat, RegistrationActivity this$0, View it) {
        String password = StringsKt.trim((CharSequence) $userPassword.getText().toString()).toString();
        String passwordRepeat = StringsKt.trim((CharSequence) $userPasswordRepeat.getText().toString()).toString();
        if (!Intrinsics.areEqual(passwordRepeat, password)) {
            Toast.makeText(this$0, "Пароли не совпадают!", 1).show();
            return;
        }
        if (!(password.length() == 0)) {
            if (!(passwordRepeat.length() == 0)) {
                PreferenceHelper preferenceHelper = this$0.prefs;
                PreferenceHelper preferenceHelper2 = null;
                if (preferenceHelper == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("prefs");
                    preferenceHelper = null;
                }
                preferenceHelper.saveString("password", password);
                PreferenceHelper preferenceHelper3 = this$0.prefs;
                if (preferenceHelper3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("prefs");
                } else {
                    preferenceHelper2 = preferenceHelper3;
                }
                preferenceHelper2.saveBoolean("isRegistered", true);
                this$0.startActivity(new Intent(this$0, (Class<?>) PetNameActivity.class));
                this$0.finish();
                return;
            }
        }
        Toast.makeText(this$0, "Не все поля заполнены", 1).show();
    }
}
