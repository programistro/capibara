package com.example.pet;

import com.example.capibara.R;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PillAdapter.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0002\u001a\u001bB%\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0004\b\n\u0010\u000bJ\u0018\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00022\u0006\u0010\u0014\u001a\u00020\u0010H\u0016J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\b\u0010\u0019\u001a\u00020\u0010H\u0016R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Lcom/example/pet/PillAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/example/pet/PillAdapter$PillViewHolder;", "pills", "", "Lcom/example/pet/Pill;", "moodManager", "Lcom/example/pet/MoodManager;", "listener", "Lcom/example/pet/PillAdapter$OnPillActionListener;", "<init>", "(Ljava/util/List;Lcom/example/pet/MoodManager;Lcom/example/pet/PillAdapter$OnPillActionListener;)V", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "", "onBindViewHolder", "", "holder", "position", "isDateToday", "", "date", "Ljava/util/Date;", "getItemCount", "OnPillActionListener", "PillViewHolder", "app_debug"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class PillAdapter extends RecyclerView.Adapter<PillViewHolder> {
    private final OnPillActionListener listener;
    private final MoodManager moodManager;
    private final List<Pill> pills;

    /* JADX INFO: compiled from: PillAdapter.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0007\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\b"}, d2 = {"Lcom/example/pet/PillAdapter$OnPillActionListener;", "", "onEditPill", "", "pill", "Lcom/example/pet/Pill;", "onDeletePill", "onMarkPill", "app_debug"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public interface OnPillActionListener {
        void onDeletePill(Pill pill);

        void onEditPill(Pill pill);

        void onMarkPill(Pill pill);
    }

    public PillAdapter(List<Pill> pills, MoodManager moodManager, OnPillActionListener listener) {
        Intrinsics.checkNotNullParameter(pills, "pills");
        Intrinsics.checkNotNullParameter(moodManager, "moodManager");
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.pills = pills;
        this.moodManager = moodManager;
        this.listener = listener;
    }

    /* JADX INFO: compiled from: PillAdapter.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u0011\u0010\f\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\tR\u0011\u0010\u000e\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0012\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0011R\u0011\u0010\u0014\u001a\u00020\u0015¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0018\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001a¨\u0006\u001b"}, d2 = {"Lcom/example/pet/PillAdapter$PillViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "<init>", "(Landroid/view/View;)V", "nameTextView", "Landroid/widget/TextView;", "getNameTextView", "()Landroid/widget/TextView;", "periodicityTextView", "getPeriodicityTextView", "timeTextView", "getTimeTextView", "btnEdit", "Landroid/widget/ImageButton;", "getBtnEdit", "()Landroid/widget/ImageButton;", "btnDelete", "getBtnDelete", "btnMark", "Landroid/widget/Button;", "getBtnMark", "()Landroid/widget/Button;", "markStatus", "getMarkStatus", "()Landroid/view/View;", "app_debug"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static final class PillViewHolder extends RecyclerView.ViewHolder {
        private final ImageButton btnDelete;
        private final ImageButton btnEdit;
        private final Button btnMark;
        private final View markStatus;
        private final TextView nameTextView;
        private final TextView periodicityTextView;
        private final TextView timeTextView;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public PillViewHolder(View itemView) {
            super(itemView);
            Intrinsics.checkNotNullParameter(itemView, "itemView");
            View viewFindViewById = itemView.findViewById(R.id.tvPillName);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
            this.nameTextView = (TextView) viewFindViewById;
            View viewFindViewById2 = itemView.findViewById(R.id.tvPillDate);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(...)");
            this.periodicityTextView = (TextView) viewFindViewById2;
            View viewFindViewById3 = itemView.findViewById(R.id.tvReminderTime);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(...)");
            this.timeTextView = (TextView) viewFindViewById3;
            View viewFindViewById4 = itemView.findViewById(R.id.btnEdit);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(...)");
            this.btnEdit = (ImageButton) viewFindViewById4;
            View viewFindViewById5 = itemView.findViewById(R.id.btnDelete);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "findViewById(...)");
            this.btnDelete = (ImageButton) viewFindViewById5;
            View viewFindViewById6 = itemView.findViewById(R.id.btnMark);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById6, "findViewById(...)");
            this.btnMark = (Button) viewFindViewById6;
            View viewFindViewById7 = itemView.findViewById(R.id.viewMarkStatus);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById7, "findViewById(...)");
            this.markStatus = viewFindViewById7;
        }

        public final TextView getNameTextView() {
            return this.nameTextView;
        }

        public final TextView getPeriodicityTextView() {
            return this.periodicityTextView;
        }

        public final TextView getTimeTextView() {
            return this.timeTextView;
        }

        public final ImageButton getBtnEdit() {
            return this.btnEdit;
        }

        public final ImageButton getBtnDelete() {
            return this.btnDelete;
        }

        public final Button getBtnMark() {
            return this.btnMark;
        }

        public final View getMarkStatus() {
            return this.markStatus;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public PillViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pill_with_mark, parent, false);
        Intrinsics.checkNotNull(view);
        return new PillViewHolder(view);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(PillViewHolder holder, int position) {
        String periodicityText;
        String reminderText;
        Intrinsics.checkNotNullParameter(holder, "holder");
        final Pill pill = this.pills.get(position);
        holder.getNameTextView().setText(pill.getName());
        switch (pill.getPeriodicity()) {
            case 1:
                periodicityText = "Каждый день";
                break;
            case 2:
                periodicityText = "Через день";
                break;
            case 3:
                periodicityText = "Каждые 3 дня";
                break;
            case 7:
                periodicityText = "Раз в неделю";
                break;
            case 14:
                periodicityText = "Каждые 2 недели";
                break;
            default:
                periodicityText = "Каждые " + pill.getPeriodicity() + " дней";
                break;
        }
        holder.getPeriodicityTextView().setText(periodicityText);
        int hours = pill.getReminderTime() / 60;
        int minutes = pill.getReminderTime() % 60;
        if (hours <= 0) {
            reminderText = "Напоминание за " + minutes + " мин.";
        } else {
            reminderText = minutes > 0 ? "Напоминание за " + hours + " ч. " + minutes + " мин." : "Напоминание за " + hours + " ч.";
        }
        holder.getTimeTextView().setText("Время: " + pill.getIntakeTime() + " | " + reminderText);
        boolean isToday = isDateToday(pill.getStartDate());
        if (isToday) {
            holder.getBtnMark().setVisibility(0);
            boolean isMarked = this.moodManager.isPillMarkedToday(pill.getName());
            if (isMarked) {
                holder.getBtnMark().setText("✓ Выполнено");
                holder.getBtnMark().setBackgroundColor(Color.parseColor("#4CAF50"));
                holder.getBtnMark().setEnabled(false);
                holder.getBtnMark().setAlpha(0.7f);
                holder.getMarkStatus().setBackgroundColor(Color.parseColor("#4CAF50"));
                holder.itemView.setAlpha(0.8f);
            } else {
                holder.getBtnMark().setText("Отметить");
                holder.getBtnMark().setBackgroundColor(Color.parseColor("#2196F3"));
                holder.getBtnMark().setEnabled(true);
                holder.getBtnMark().setAlpha(1.0f);
                holder.getMarkStatus().setBackgroundColor(0);
                holder.itemView.setAlpha(1.0f);
                holder.getBtnMark().setOnClickListener(new View.OnClickListener() { // from class: com.example.pet.PillAdapter$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        PillAdapter.onBindViewHolder$lambda$0(this.f$0, pill, view);
                    }
                });
            }
        } else {
            holder.getBtnMark().setVisibility(8);
            holder.getMarkStatus().setBackgroundColor(0);
            holder.itemView.setAlpha(0.6f);
        }
        holder.getBtnEdit().setOnClickListener(new View.OnClickListener() { // from class: com.example.pet.PillAdapter$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PillAdapter.onBindViewHolder$lambda$1(this.f$0, pill, view);
            }
        });
        holder.getBtnDelete().setOnClickListener(new View.OnClickListener() { // from class: com.example.pet.PillAdapter$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PillAdapter.onBindViewHolder$lambda$2(this.f$0, pill, view);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.example.pet.PillAdapter$$ExternalSyntheticLambda3
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                return PillAdapter.onBindViewHolder$lambda$3(this.f$0, pill, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onBindViewHolder$lambda$0(PillAdapter this$0, Pill $pill, View it) {
        this$0.listener.onMarkPill($pill);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onBindViewHolder$lambda$1(PillAdapter this$0, Pill $pill, View it) {
        this$0.listener.onEditPill($pill);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onBindViewHolder$lambda$2(PillAdapter this$0, Pill $pill, View it) {
        this$0.listener.onDeletePill($pill);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean onBindViewHolder$lambda$3(PillAdapter this$0, Pill $pill, View it) {
        this$0.listener.onEditPill($pill);
        return true;
    }

    private final boolean isDateToday(Date date) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        return Intrinsics.areEqual(fmt.format(date), fmt.format(new Date()));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.pills.size();
    }
}
