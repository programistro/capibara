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

/* JADX INFO: compiled from: AuthorisationActivity.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0012\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\tH\u0014R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lcom/example/pet/AuthorisationActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "<init>", "()V", "prefs", "Lcom/example/pet/PreferenceHelper;", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "app_debug"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class AuthorisationActivity extends AppCompatActivity {
    private PreferenceHelper prefs;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorisation);
        this.prefs = new PreferenceHelper(this);
        View viewFindViewById = findViewById(R.id.user_password);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
        final EditText userPassword = (EditText) viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.button_auth);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(...)");
        Button buttonAuth = (Button) viewFindViewById2;
        buttonAuth.setOnClickListener(new View.OnClickListener() { // from class: com.example.pet.AuthorisationActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AuthorisationActivity.onCreate$lambda$0(userPassword, this, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(EditText $userPassword, AuthorisationActivity this$0, View it) {
        String password = StringsKt.trim((CharSequence) $userPassword.getText().toString()).toString();
        PreferenceHelper preferenceHelper = this$0.prefs;
        if (preferenceHelper == null) {
            Intrinsics.throwUninitializedPropertyAccessException("prefs");
            preferenceHelper = null;
        }
        if (!Intrinsics.areEqual(password, PreferenceHelper.getString$default(preferenceHelper, "password", null, 2, null))) {
            Toast.makeText(this$0, "Пароли не совпадают!", 1).show();
            return;
        }
        if (password.length() == 0) {
            Toast.makeText(this$0, "Не все поля заполнены", 1).show();
        } else {
            this$0.startActivity(new Intent(this$0, (Class<?>) MainActivity.class));
            this$0.finish();
        }
    }
}
