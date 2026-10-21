package com.example.pet;

import com.example.capibara.R;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.Regex;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: AddPillFragment.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u000e\u0018\u0000 -2\u00020\u0001:\u0001-B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J&\u0010\u0017\u001a\u0004\u0018\u00010\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0016J\u001a\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u00182\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0016J\b\u0010\"\u001a\u00020 H\u0002J\b\u0010#\u001a\u00020 H\u0002J\b\u0010$\u001a\u00020 H\u0002J\b\u0010%\u001a\u00020 H\u0002J\b\u0010&\u001a\u00020 H\u0002J\b\u0010'\u001a\u00020 H\u0002J\b\u0010(\u001a\u00020 H\u0002J\u001a\u0010)\u001a\u00020 2\b\u0010*\u001a\u0004\u0018\u00010\u00162\u0006\u0010+\u001a\u00020\u0016H\u0002J\b\u0010,\u001a\u00020 H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0007X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0007X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082.¢\u0006\u0002\n\u0000R\u0018\u0010\u0011\u001a\n \u0013*\u0004\u0018\u00010\u00120\u0012X\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u0014R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006."}, d2 = {"Lcom/example/pet/AddPillFragment;", "Landroidx/fragment/app/Fragment;", "<init>", "()V", "dbHelper", "Lcom/example/pet/DbHelper;", "etPillName", "Landroid/widget/EditText;", "etStartDate", "etIntakeTime", "spinnerPeriodicity", "Landroid/widget/Spinner;", "etReminderTime", "switchReminder", "Landroid/widget/Switch;", "btnSave", "Landroid/widget/Button;", "selectedCalendar", "Ljava/util/Calendar;", "kotlin.jvm.PlatformType", "Ljava/util/Calendar;", "editingPill", "Lcom/example/pet/Pill;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onViewCreated", "", "view", "setupCreateMode", "setupEditMode", "setCurrentDateTime", "setupPeriodicitySpinner", "showDatePicker", "showTimePicker", "savePill", "setupNotifications", "oldPill", "newPill", "onDestroy", "Companion", "app_debug"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class AddPillFragment extends Fragment {
    private static final String ARG_PILL = "pill";

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private Button btnSave;
    private DbHelper dbHelper;
    private Pill editingPill;
    private EditText etIntakeTime;
    private EditText etPillName;
    private EditText etReminderTime;
    private EditText etStartDate;
    private Calendar selectedCalendar = Calendar.getInstance();
    private Spinner spinnerPeriodicity;
    private Switch switchReminder;

    /* JADX INFO: compiled from: AddPillFragment.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\tR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lcom/example/pet/AddPillFragment$Companion;", "", "<init>", "()V", "ARG_PILL", "", "newInstance", "Lcom/example/pet/AddPillFragment;", AddPillFragment.ARG_PILL, "Lcom/example/pet/Pill;", "app_debug"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final AddPillFragment newInstance(Pill pill) {
            AddPillFragment fragment = new AddPillFragment();
            if (pill != null) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(AddPillFragment.ARG_PILL, pill);
                fragment.setArguments(bundle);
            }
            return fragment;
        }
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        return inflater.inflate(R.layout.fragment_add_pill, container, false);
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, savedInstanceState);
        Context contextRequireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
        Button button = null;
        this.dbHelper = new DbHelper(contextRequireContext, null);
        Bundle arguments = getArguments();
        this.editingPill = arguments != null ? (Pill) arguments.getParcelable(ARG_PILL) : null;
        this.etPillName = (EditText) view.findViewById(R.id.etPillName);
        this.etStartDate = (EditText) view.findViewById(R.id.etStartDate);
        this.etIntakeTime = (EditText) view.findViewById(R.id.etIntakeTime);
        this.spinnerPeriodicity = (Spinner) view.findViewById(R.id.spinnerPeriodicity);
        this.etReminderTime = (EditText) view.findViewById(R.id.etReminderTime);
        this.switchReminder = (Switch) view.findViewById(R.id.switchReminder);
        this.btnSave = (Button) view.findViewById(R.id.btnSave);
        if (this.editingPill != null) {
            setupEditMode();
        } else {
            setupCreateMode();
        }
        setupPeriodicitySpinner();
        setCurrentDateTime();
        EditText editText = this.etStartDate;
        if (editText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("etStartDate");
            editText = null;
        }
        editText.setOnClickListener(new View.OnClickListener() { // from class: com.example.pet.AddPillFragment$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.showDatePicker();
            }
        });
        EditText editText2 = this.etIntakeTime;
        if (editText2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("etIntakeTime");
            editText2 = null;
        }
        editText2.setOnClickListener(new View.OnClickListener() { // from class: com.example.pet.AddPillFragment$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.showTimePicker();
            }
        });
        Switch r0 = this.switchReminder;
        if (r0 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("switchReminder");
            r0 = null;
        }
        r0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.example.pet.AddPillFragment$$ExternalSyntheticLambda4
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                AddPillFragment.onViewCreated$lambda$2(this.f$0, compoundButton, z);
            }
        });
        Button button2 = this.btnSave;
        if (button2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnSave");
        } else {
            button = button2;
        }
        button.setOnClickListener(new View.OnClickListener() { // from class: com.example.pet.AddPillFragment$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.savePill();
            }
        });
        ((Button) view.findViewById(R.id.btnCancel)).setOnClickListener(new View.OnClickListener() { // from class: com.example.pet.AddPillFragment$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                AddPillFragment.onViewCreated$lambda$4(this.f$0, view2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onViewCreated$lambda$2(AddPillFragment this$0, CompoundButton compoundButton, boolean isChecked) {
        Intrinsics.checkNotNullParameter(compoundButton, "<unused var>");
        EditText editText = this$0.etReminderTime;
        if (editText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("etReminderTime");
            editText = null;
        }
        editText.setVisibility(isChecked ? 0 : 8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onViewCreated$lambda$4(AddPillFragment this$0, View it) {
        TabletFragment tabletFragment = new TabletFragment();
        this$0.getParentFragmentManager().beginTransaction().replace(R.id.fragment_frame, tabletFragment).commit();
    }

    private final void setupCreateMode() {
        Button button = this.btnSave;
        if (button == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnSave");
            button = null;
        }
        button.setText("Сохранить");
    }

    private final void setupEditMode() {
        Button button = this.btnSave;
        Switch r1 = null;
        if (button == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnSave");
            button = null;
        }
        button.setText("Обновить");
        Pill pill = this.editingPill;
        if (pill != null) {
            EditText editText = this.etPillName;
            if (editText == null) {
                Intrinsics.throwUninitializedPropertyAccessException("etPillName");
                editText = null;
            }
            editText.setText(pill.getName());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(pill.getStartDate());
            this.selectedCalendar = calendar;
            int i = 5;
            String str = calendar.get(5) + "." + (calendar.get(2) + 1) + "." + calendar.get(1);
            EditText editText2 = this.etStartDate;
            if (editText2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("etStartDate");
                editText2 = null;
            }
            editText2.setText(str);
            EditText editText3 = this.etIntakeTime;
            if (editText3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("etIntakeTime");
                editText3 = null;
            }
            editText3.setText(pill.getIntakeTime());
            switch (pill.getPeriodicity()) {
                case -12:
                    i = 0;
                    break;
                case 1:
                    i = 1;
                    break;
                case 2:
                    i = 2;
                    break;
                case 3:
                    i = 3;
                    break;
                case 7:
                    i = 4;
                    break;
                case 14:
                    break;
                default:
                    i = 0;
                    break;
            }
            Spinner spinner = this.spinnerPeriodicity;
            if (spinner == null) {
                Intrinsics.throwUninitializedPropertyAccessException("spinnerPeriodicity");
                spinner = null;
            }
            spinner.setSelection(i);
            EditText editText4 = this.etReminderTime;
            if (editText4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("etReminderTime");
                editText4 = null;
            }
            editText4.setText(String.valueOf(pill.getReminderTime()));
            Switch r6 = this.switchReminder;
            if (r6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("switchReminder");
            } else {
                r1 = r6;
            }
            r1.setChecked(true);
        }
    }

    private final void setCurrentDateTime() {
        if (this.editingPill == null) {
            Calendar currentCalendar = Calendar.getInstance();
            this.selectedCalendar = currentCalendar;
            String dateStr = currentCalendar.get(5) + "." + (currentCalendar.get(2) + 1) + "." + currentCalendar.get(1);
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            String timeStr = String.format("%02d:%02d", Arrays.copyOf(new Object[]{Integer.valueOf(currentCalendar.get(11)), Integer.valueOf(currentCalendar.get(12))}, 2));
            Intrinsics.checkNotNullExpressionValue(timeStr, "format(...)");
            EditText editText = this.etStartDate;
            EditText editText2 = null;
            if (editText == null) {
                Intrinsics.throwUninitializedPropertyAccessException("etStartDate");
                editText = null;
            }
            editText.setText(dateStr);
            EditText editText3 = this.etIntakeTime;
            if (editText3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("etIntakeTime");
            } else {
                editText2 = editText3;
            }
            editText2.setText(timeStr);
        }
    }

    private final void setupPeriodicitySpinner() {
        String[] periodicityOptions = {"Каждые 12 часов", "Каждый день", "Через день", "Каждые 3 дня", "Раз в неделю", "Каждые 2 недели"};
        ArrayAdapter adapter = new ArrayAdapter(requireContext(), R.layout.custom_spinner_item, periodicityOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = this.spinnerPeriodicity;
        if (spinner == null) {
            Intrinsics.throwUninitializedPropertyAccessException("spinnerPeriodicity");
            spinner = null;
        }
        spinner.setAdapter((SpinnerAdapter) adapter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showDatePicker() {
        int year = this.selectedCalendar.get(1);
        int month = this.selectedCalendar.get(2);
        int day = this.selectedCalendar.get(5);
        DatePickerDialog datePicker = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() { // from class: com.example.pet.AddPillFragment$$ExternalSyntheticLambda1
            @Override // android.app.DatePickerDialog.OnDateSetListener
            public final void onDateSet(DatePicker datePicker2, int i, int i2, int i3) {
                AddPillFragment.showDatePicker$lambda$7(this.f$0, datePicker2, i, i2, i3);
            }
        }, year, month, day);
        datePicker.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showDatePicker$lambda$7(AddPillFragment this$0, DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
        this$0.selectedCalendar.set(selectedYear, selectedMonth, selectedDay);
        String dateStr = selectedDay + "." + (selectedMonth + 1) + "." + selectedYear;
        EditText editText = this$0.etStartDate;
        if (editText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("etStartDate");
            editText = null;
        }
        editText.setText(dateStr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showTimePicker() {
        int hour = this.selectedCalendar.get(11);
        int minute = this.selectedCalendar.get(12);
        TimePickerDialog timePicker = new TimePickerDialog(requireContext(), new TimePickerDialog.OnTimeSetListener() { // from class: com.example.pet.AddPillFragment$$ExternalSyntheticLambda0
            @Override // android.app.TimePickerDialog.OnTimeSetListener
            public final void onTimeSet(TimePicker timePicker2, int i, int i2) {
                AddPillFragment.showTimePicker$lambda$8(this.f$0, timePicker2, i, i2);
            }
        }, hour, minute, true);
        timePicker.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showTimePicker$lambda$8(AddPillFragment this$0, TimePicker timePicker, int selectedHour, int selectedMinute) {
        this$0.selectedCalendar.set(11, selectedHour);
        this$0.selectedCalendar.set(12, selectedMinute);
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String timeStr = String.format("%02d:%02d", Arrays.copyOf(new Object[]{Integer.valueOf(selectedHour), Integer.valueOf(selectedMinute)}, 2));
        Intrinsics.checkNotNullExpressionValue(timeStr, "format(...)");
        EditText editText = this$0.etIntakeTime;
        if (editText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("etIntakeTime");
            editText = null;
        }
        editText.setText(timeStr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void savePill() {
        String errorMessage;
        int periodicity;
        int reminderTime;
        Integer intOrNull;
        EditText editText = this.etPillName;
        if (editText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("etPillName");
            editText = null;
        }
        String name = StringsKt.trim((CharSequence) editText.getText().toString()).toString();
        EditText editText2 = this.etStartDate;
        if (editText2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("etStartDate");
            editText2 = null;
        }
        String startDateStr = StringsKt.trim((CharSequence) editText2.getText().toString()).toString();
        EditText editText3 = this.etIntakeTime;
        if (editText3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("etIntakeTime");
            editText3 = null;
        }
        String intakeTimeStr = StringsKt.trim((CharSequence) editText3.getText().toString()).toString();
        if (name.length() == 0) {
            Toast.makeText(requireContext(), "Введите название лекарства", 0).show();
            return;
        }
        if (startDateStr.length() == 0) {
            Toast.makeText(requireContext(), "Выберите дату начала приема", 0).show();
            return;
        }
        if (intakeTimeStr.length() == 0) {
            Toast.makeText(requireContext(), "Выберите время приема", 0).show();
            return;
        }
        Regex timeRegex = new Regex("^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$");
        if (!timeRegex.matches(intakeTimeStr)) {
            Toast.makeText(requireContext(), "Время должно быть в формате ЧЧ:MM (например, 14:30)", 0).show();
            return;
        }
        try {
            List dateParts = StringsKt.split$default((CharSequence) startDateStr, new String[]{"."}, false, 0, 6, (Object) null);
            if (dateParts.size() != 3) {
                Toast.makeText(requireContext(), "Неверный формат даты", 0).show();
                return;
            }
            int day = Integer.parseInt((String) dateParts.get(0));
            int month = Integer.parseInt((String) dateParts.get(1)) - 1;
            int year = Integer.parseInt((String) dateParts.get(2));
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day);
            List listSplit$default = StringsKt.split$default((CharSequence) intakeTimeStr, new String[]{":"}, false, 0, 6, (Object) null);
            if (listSplit$default.size() == 2) {
                Integer intOrNull2 = StringsKt.toIntOrNull((String) listSplit$default.get(0));
                int iIntValue = intOrNull2 != null ? intOrNull2.intValue() : 12;
                Integer intOrNull3 = StringsKt.toIntOrNull((String) listSplit$default.get(1));
                int iIntValue2 = intOrNull3 != null ? intOrNull3.intValue() : 0;
                calendar.set(11, iIntValue);
                calendar.set(12, iIntValue2);
            }
            calendar.set(13, 0);
            calendar.set(14, 0);
            Date startDate = new Date(calendar.getTimeInMillis());
            Spinner spinner = this.spinnerPeriodicity;
            if (spinner == null) {
                Intrinsics.throwUninitializedPropertyAccessException("spinnerPeriodicity");
                spinner = null;
            }
            switch (spinner.getSelectedItemPosition()) {
                case 0:
                    periodicity = 1;
                    break;
                case 1:
                    periodicity = 2;
                    break;
                case 2:
                    periodicity = 3;
                    break;
                case 3:
                    periodicity = 7;
                    break;
                case 4:
                    periodicity = 14;
                    break;
                default:
                    periodicity = 1;
                    break;
            }
            Switch r2 = this.switchReminder;
            if (r2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("switchReminder");
                r2 = null;
            }
            if (r2.isChecked()) {
                EditText editText4 = this.etReminderTime;
                if (editText4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("etReminderTime");
                    editText4 = null;
                }
                String reminderText = StringsKt.trim((CharSequence) editText4.getText().toString()).toString();
                reminderTime = ((reminderText.length() == 0) || (intOrNull = StringsKt.toIntOrNull(reminderText)) == null) ? 180 : intOrNull.intValue();
            } else {
                reminderTime = 180;
            }
            if (this.editingPill != null) {
                Pill updatedPill = new Pill(name, startDate, periodicity, reminderTime, intakeTimeStr);
                Context contextRequireContext = requireContext();
                Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
                NotificationHelper notificationHelper = new NotificationHelper(contextRequireContext);
                Pill pill = this.editingPill;
                Intrinsics.checkNotNull(pill);
                notificationHelper.cancelPillNotifications(pill);
                DbHelper dbHelper = this.dbHelper;
                if (dbHelper == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("dbHelper");
                    dbHelper = null;
                }
                Pill pill2 = this.editingPill;
                Intrinsics.checkNotNull(pill2);
                dbHelper.updatePill(pill2, updatedPill);
                Pill pill3 = this.editingPill;
                Intrinsics.checkNotNull(pill3);
                setupNotifications(pill3, updatedPill);
                Toast.makeText(requireContext(), "Лекарство обновлено", 0).show();
            } else {
                Pill pill4 = new Pill(name, startDate, periodicity, reminderTime, intakeTimeStr);
                DbHelper dbHelper2 = this.dbHelper;
                if (dbHelper2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("dbHelper");
                    dbHelper2 = null;
                }
                dbHelper2.addPill(pill4);
                setupNotifications(null, pill4);
                Toast.makeText(requireContext(), "Лекарство добавлено", 0).show();
            }
            TabletFragment tabletFragment = new TabletFragment();
            getParentFragmentManager().beginTransaction().replace(R.id.fragment_frame, tabletFragment).commit();
        } catch (Exception e) {
            if (e instanceof NumberFormatException) {
                errorMessage = "Ошибка в формате числа";
            } else {
                errorMessage = e instanceof IndexOutOfBoundsException ? "Ошибка в формате даты или времени" : "Ошибка при сохранении: " + e.getMessage();
            }
            Toast.makeText(requireContext(), errorMessage, 1).show();
            e.printStackTrace();
        }
    }

    private final void setupNotifications(Pill oldPill, Pill newPill) {
        try {
            System.out.println((Object) ("DEBUG: Setting up notifications for " + newPill.getName()));
            System.out.println((Object) ("DEBUG: Start date: " + newPill.getStartDate() + ", Time: " + newPill.getIntakeTime() + ", Reminder: " + newPill.getReminderTime() + "min"));
            Context contextRequireContext = requireContext();
            Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
            NotificationHelper notificationHelper = new NotificationHelper(contextRequireContext);
            FragmentActivity activity = getActivity();
            MainActivity activity2 = activity instanceof MainActivity ? (MainActivity) activity : null;
            if (activity2 != null && activity2.hasNotificationPermission()) {
                System.out.println((Object) "DEBUG: Notification permission granted");
                if (!activity2.hasExactAlarmPermission()) {
                    System.out.println((Object) "DEBUG: No exact alarm permission - using less precise alarms");
                    Toast.makeText(requireContext(), "Уведомления будут менее точными. Для точных напоминаний предоставьте разрешение в настройках", 1).show();
                }
                if (oldPill != null) {
                    System.out.println((Object) "DEBUG: Canceling old notifications");
                    notificationHelper.cancelPillNotifications(oldPill);
                }
                System.out.println((Object) "DEBUG: Scheduling new notifications");
                notificationHelper.schedulePillNotifications(newPill);
                System.out.println((Object) "DEBUG: Notifications scheduled successfully");
                return;
            }
            System.out.println((Object) "DEBUG: No notification permission");
            Toast.makeText(requireContext(), "Разрешите уведомления в настройках приложения для напоминаний", 1).show();
        } catch (Exception e) {
            System.out.println((Object) ("DEBUG: Notification setup error: " + e.getMessage()));
            Toast.makeText(requireContext(), "Уведомления настроены с ограничениями", 0).show();
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
