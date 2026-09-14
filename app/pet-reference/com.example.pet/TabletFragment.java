package com.example.pet;

import com.example.capibara.R;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pet.PillAdapter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: TabletFragment.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J&\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0016J\u001a\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00112\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0016J\b\u0010\u001b\u001a\u00020\u0019H\u0002J\b\u0010\u001c\u001a\u00020\u0019H\u0002J\b\u0010\u001d\u001a\u00020\u0019H\u0002J\u0018\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\u000fH\u0002J\u0010\u0010#\u001a\u00020\u00192\u0006\u0010 \u001a\u00020!H\u0002J\u0010\u0010$\u001a\u00020\u00192\u0006\u0010 \u001a\u00020!H\u0002J\b\u0010%\u001a\u00020\u0019H\u0016J\b\u0010&\u001a\u00020\u0019H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006'"}, d2 = {"Lcom/example/pet/TabletFragment;", "Landroidx/fragment/app/Fragment;", "<init>", "()V", "moodManager", "Lcom/example/pet/MoodManager;", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "pillAdapter", "Lcom/example/pet/PillAdapter;", "dbHelper", "Lcom/example/pet/DbHelper;", "tvSelectedDate", "Landroid/widget/TextView;", "selectedDate", "Ljava/util/Date;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onViewCreated", "", "view", "showDatePicker", "updateSelectedDateText", "loadPills", "isPillTakenOnDate", "", "pill", "Lcom/example/pet/Pill;", "date", "showDeleteConfirmationDialog", "deletePill", "onResume", "onDestroy", "app_debug"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class TabletFragment extends Fragment {
    private DbHelper dbHelper;
    private MoodManager moodManager;
    private PillAdapter pillAdapter;
    private RecyclerView recyclerView;
    private Date selectedDate = new Date();
    private TextView tvSelectedDate;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        return inflater.inflate(R.layout.fragment_tablet, container, false);
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) throws IOException {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, savedInstanceState);
        Context contextRequireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
        this.moodManager = new MoodManager(contextRequireContext);
        Context contextRequireContext2 = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext2, "requireContext(...)");
        TextView textView = null;
        this.dbHelper = new DbHelper(contextRequireContext2, null);
        this.recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewPills);
        this.tvSelectedDate = (TextView) view.findViewById(R.id.tvSelectedDate);
        RecyclerView recyclerView = this.recyclerView;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recyclerView");
            recyclerView = null;
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        updateSelectedDateText();
        TextView textView2 = this.tvSelectedDate;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvSelectedDate");
        } else {
            textView = textView2;
        }
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.example.pet.TabletFragment$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.showDatePicker();
            }
        });
        view.findViewById(R.id.btnToday).setOnClickListener(new View.OnClickListener() { // from class: com.example.pet.TabletFragment$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) throws IOException {
                TabletFragment.onViewCreated$lambda$1(this.f$0, view2);
            }
        });
        loadPills();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onViewCreated$lambda$1(TabletFragment this$0, View it) throws IOException {
        this$0.selectedDate = new Date();
        this$0.updateSelectedDateText();
        this$0.loadPills();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showDatePicker() {
        DatePickerFragment datePickerFragment = new DatePickerFragment(new Function1() { // from class: com.example.pet.TabletFragment$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return TabletFragment.showDatePicker$lambda$2(this.f$0, (Date) obj);
            }
        });
        datePickerFragment.show(getParentFragmentManager(), "datePicker");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit showDatePicker$lambda$2(TabletFragment this$0, Date date) throws IOException {
        Intrinsics.checkNotNullParameter(date, "date");
        this$0.selectedDate = date;
        this$0.updateSelectedDateText();
        this$0.loadPills();
        return Unit.INSTANCE;
    }

    private final void updateSelectedDateText() {
        String dateText;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(6, 1);
        Date tomorrow = calendar.getTime();
        calendar.setTime(today);
        calendar.add(6, -1);
        Date yesterday = calendar.getTime();
        if (this.selectedDate.getTime() == today.getTime()) {
            dateText = "Сегодня (" + dateFormat.format(this.selectedDate) + ")";
        } else if (this.selectedDate.getTime() == tomorrow.getTime()) {
            dateText = "Завтра (" + dateFormat.format(this.selectedDate) + ")";
        } else if (this.selectedDate.getTime() == yesterday.getTime()) {
            dateText = "Вчера (" + dateFormat.format(this.selectedDate) + ")";
        } else {
            dateText = dateFormat.format(this.selectedDate);
        }
        TextView textView = this.tvSelectedDate;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvSelectedDate");
            textView = null;
        }
        textView.setText(dateText);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void loadPills() throws IOException {
        DbHelper dbHelper = this.dbHelper;
        RecyclerView recyclerView = null;
        TextView textView = null;
        if (dbHelper == null) {
            Intrinsics.throwUninitializedPropertyAccessException("dbHelper");
            dbHelper = null;
        }
        List<Pill> pillsForDate = dbHelper.getPillsForDate(this.selectedDate);
        MoodManager moodManager = this.moodManager;
        if (moodManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("moodManager");
            moodManager = null;
        }
        this.pillAdapter = new PillAdapter(pillsForDate, moodManager, new PillAdapter.OnPillActionListener() { // from class: com.example.pet.TabletFragment.loadPills.1
            @Override // com.example.pet.PillAdapter.OnPillActionListener
            public void onEditPill(Pill pill) {
                Intrinsics.checkNotNullParameter(pill, "pill");
                AddPillFragment editPillFragment = AddPillFragment.INSTANCE.newInstance(pill);
                TabletFragment.this.getParentFragmentManager().beginTransaction().replace(R.id.fragment_frame, editPillFragment).addToBackStack(null).commit();
            }

            @Override // com.example.pet.PillAdapter.OnPillActionListener
            public void onDeletePill(Pill pill) {
                Intrinsics.checkNotNullParameter(pill, "pill");
                TabletFragment.this.showDeleteConfirmationDialog(pill);
            }

            @Override // com.example.pet.PillAdapter.OnPillActionListener
            public void onMarkPill(Pill pill) throws IOException {
                Intrinsics.checkNotNullParameter(pill, "pill");
                MoodManager moodManager2 = TabletFragment.this.moodManager;
                if (moodManager2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("moodManager");
                    moodManager2 = null;
                }
                boolean success = moodManager2.markPillAsTaken(pill.getName());
                if (success) {
                    Context contextRequireContext = TabletFragment.this.requireContext();
                    Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
                    CurrencyManager currencyManager = new CurrencyManager(contextRequireContext);
                    currencyManager.rewardForMarkedPill(pill.getName());
                    int newBalance = currencyManager.getCurrentCoins();
                    Toast.makeText(TabletFragment.this.requireContext(), "✓ " + pill.getName() + " выполнено! +5 монет\nБаланс: " + newBalance + " монет", 1).show();
                    FragmentActivity activity = TabletFragment.this.getActivity();
                    MainActivity mainActivity = activity instanceof MainActivity ? (MainActivity) activity : null;
                    if (mainActivity != null) {
                        mainActivity.updateMoodDisplay();
                        mainActivity.updateCoinDisplay();
                    }
                    TabletFragment.this.loadPills();
                    return;
                }
                Toast.makeText(TabletFragment.this.requireContext(), "⚠️ " + pill.getName() + " уже отмечено!", 0).show();
            }
        });
        RecyclerView recyclerView2 = this.recyclerView;
        if (recyclerView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recyclerView");
            recyclerView2 = null;
        }
        PillAdapter pillAdapter = this.pillAdapter;
        if (pillAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pillAdapter");
            pillAdapter = null;
        }
        recyclerView2.setAdapter(pillAdapter);
        View view = getView();
        TextView textView2 = view != null ? (TextView) view.findViewById(R.id.tvEmptyList) : null;
        if (pillsForDate.isEmpty()) {
            if (textView2 != null) {
                textView2.setVisibility(0);
            }
            RecyclerView recyclerView3 = this.recyclerView;
            if (recyclerView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("recyclerView");
                recyclerView3 = null;
            }
            recyclerView3.setVisibility(8);
            if (textView2 != null) {
                TextView textView3 = this.tvSelectedDate;
                if (textView3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("tvSelectedDate");
                } else {
                    textView = textView3;
                }
                textView2.setText("На " + ((Object) textView.getText()) + " лекарств нет");
                return;
            }
            return;
        }
        if (textView2 != null) {
            textView2.setVisibility(8);
        }
        RecyclerView recyclerView4 = this.recyclerView;
        if (recyclerView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recyclerView");
        } else {
            recyclerView = recyclerView4;
        }
        recyclerView.setVisibility(0);
    }

    private final boolean isPillTakenOnDate(Pill pill, Date date) {
        Calendar pillCalendar = Calendar.getInstance();
        pillCalendar.setTime(pill.getStartDate());
        Calendar checkCalendar = Calendar.getInstance();
        checkCalendar.setTime(date);
        if (pillCalendar.getTimeInMillis() > checkCalendar.getTimeInMillis() + ((long) 86400000)) {
            return false;
        }
        long hoursDiff = (checkCalendar.getTimeInMillis() - pillCalendar.getTimeInMillis()) / ((long) 3600000);
        return hoursDiff >= 0 && hoursDiff % ((long) (-pill.getPeriodicity())) == 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showDeleteConfirmationDialog(final Pill pill) {
        new AlertDialog.Builder(requireContext()).setTitle("Удаление лекарства").setMessage("Вы уверены, что хотите удалить \"" + pill.getName() + "\"?").setPositiveButton("Удалить", new DialogInterface.OnClickListener() { // from class: com.example.pet.TabletFragment$$ExternalSyntheticLambda3
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) throws IOException {
                this.f$0.deletePill(pill);
            }
        }).setNegativeButton("Отмена", (DialogInterface.OnClickListener) null).create().show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void deletePill(Pill pill) throws IOException {
        Context contextRequireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
        NotificationHelper notificationHelper = new NotificationHelper(contextRequireContext);
        notificationHelper.cancelPillNotifications(pill);
        DbHelper dbHelper = this.dbHelper;
        if (dbHelper == null) {
            Intrinsics.throwUninitializedPropertyAccessException("dbHelper");
            dbHelper = null;
        }
        dbHelper.deletePill(pill);
        loadPills();
        Toast.makeText(requireContext(), "Лекарство удалено", 0).show();
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        FragmentActivity activity = getActivity();
        MainActivity mainActivity = activity instanceof MainActivity ? (MainActivity) activity : null;
        if (mainActivity != null) {
            mainActivity.updateCoinDisplay();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        DbHelper dbHelper = this.dbHelper;
        if (dbHelper == null) {
            Intrinsics.throwUninitializedPropertyAccessException("dbHelper");
            dbHelper = null;
        }
        dbHelper.close();
    }
}
