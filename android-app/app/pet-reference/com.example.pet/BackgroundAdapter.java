package com.example.pet;

import com.example.capibara.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: BackgroundAdapter.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\b\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0002\u0016\u0017B\u001d\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0004\b\b\u0010\tJ\u0018\u0010\n\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u000eH\u0016J\b\u0010\u0013\u001a\u00020\u000eH\u0016J\u0014\u0010\u0014\u001a\u00020\u00102\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lcom/example/pet/BackgroundAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/example/pet/BackgroundAdapter$BackgroundViewHolder;", "items", "", "Lcom/example/pet/BackgroundItem;", "listener", "Lcom/example/pet/BackgroundAdapter$OnBackgroundActionListener;", "<init>", "(Ljava/util/List;Lcom/example/pet/BackgroundAdapter$OnBackgroundActionListener;)V", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "", "onBindViewHolder", "", "holder", "position", "getItemCount", "updateItems", "newItems", "OnBackgroundActionListener", "BackgroundViewHolder", "app_debug"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class BackgroundAdapter extends RecyclerView.Adapter<BackgroundViewHolder> {
    private List<BackgroundItem> items;
    private final OnBackgroundActionListener listener;

    /* JADX INFO: compiled from: BackgroundAdapter.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0007"}, d2 = {"Lcom/example/pet/BackgroundAdapter$OnBackgroundActionListener;", "", "onBuyBackground", "", "item", "Lcom/example/pet/BackgroundItem;", "onEquipBackground", "app_debug"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public interface OnBackgroundActionListener {
        void onBuyBackground(BackgroundItem item);

        void onEquipBackground(BackgroundItem item);
    }

    public BackgroundAdapter(List<BackgroundItem> items, OnBackgroundActionListener listener) {
        Intrinsics.checkNotNullParameter(items, "items");
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.items = items;
        this.listener = listener;
    }

    /* JADX INFO: compiled from: BackgroundAdapter.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u000e\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\rR\u0011\u0010\u0010\u001a\u00020\u0011¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0014\u001a\u00020\u0011¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0013R\u0011\u0010\u0016\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018¨\u0006\u0019"}, d2 = {"Lcom/example/pet/BackgroundAdapter$BackgroundViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "<init>", "(Landroid/view/View;)V", "backgroundImage", "Landroid/widget/ImageView;", "getBackgroundImage", "()Landroid/widget/ImageView;", "backgroundName", "Landroid/widget/TextView;", "getBackgroundName", "()Landroid/widget/TextView;", "backgroundPrice", "getBackgroundPrice", "btnBuy", "Landroid/widget/Button;", "getBtnBuy", "()Landroid/widget/Button;", "btnEquip", "getBtnEquip", "statusIndicator", "getStatusIndicator", "()Landroid/view/View;", "app_debug"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static final class BackgroundViewHolder extends RecyclerView.ViewHolder {
        private final ImageView backgroundImage;
        private final TextView backgroundName;
        private final TextView backgroundPrice;
        private final Button btnBuy;
        private final Button btnEquip;
        private final View statusIndicator;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public BackgroundViewHolder(View itemView) {
            super(itemView);
            Intrinsics.checkNotNullParameter(itemView, "itemView");
            View viewFindViewById = itemView.findViewById(R.id.ivBackgroundImage);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
            this.backgroundImage = (ImageView) viewFindViewById;
            View viewFindViewById2 = itemView.findViewById(R.id.tvBackgroundName);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(...)");
            this.backgroundName = (TextView) viewFindViewById2;
            View viewFindViewById3 = itemView.findViewById(R.id.tvBackgroundPrice);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(...)");
            this.backgroundPrice = (TextView) viewFindViewById3;
            View viewFindViewById4 = itemView.findViewById(R.id.btnBuyBackground);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(...)");
            this.btnBuy = (Button) viewFindViewById4;
            View viewFindViewById5 = itemView.findViewById(R.id.btnEquipBackground);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "findViewById(...)");
            this.btnEquip = (Button) viewFindViewById5;
            View viewFindViewById6 = itemView.findViewById(R.id.viewStatusIndicator);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById6, "findViewById(...)");
            this.statusIndicator = viewFindViewById6;
        }

        public final ImageView getBackgroundImage() {
            return this.backgroundImage;
        }

        public final TextView getBackgroundName() {
            return this.backgroundName;
        }

        public final TextView getBackgroundPrice() {
            return this.backgroundPrice;
        }

        public final Button getBtnBuy() {
            return this.btnBuy;
        }

        public final Button getBtnEquip() {
            return this.btnEquip;
        }

        public final View getStatusIndicator() {
            return this.statusIndicator;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public BackgroundViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_background, parent, false);
        Intrinsics.checkNotNull(view);
        return new BackgroundViewHolder(view);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(BackgroundViewHolder holder, int position) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        final BackgroundItem item = this.items.get(position);
        holder.getBackgroundImage().setImageResource(item.getDrawableId());
        holder.getBackgroundName().setText(item.getName());
        if (item.isPurchased()) {
            holder.getBackgroundPrice().setText("Куплено");
            holder.getBackgroundPrice().setTextColor(ContextCompat.getColor(holder.itemView.getContext(), android.R.color.holo_green_dark));
            holder.getBtnBuy().setVisibility(8);
            if (item.isEquipped()) {
                holder.getBtnEquip().setText("✓ Установлен");
                holder.getBtnEquip().setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), android.R.color.holo_green_dark));
                holder.getBtnEquip().setEnabled(false);
                holder.getStatusIndicator().setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), android.R.color.holo_green_light));
                return;
            }
            holder.getBtnEquip().setText("Установить");
            holder.getBtnEquip().setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), android.R.color.holo_blue_dark));
            holder.getBtnEquip().setEnabled(true);
            holder.getStatusIndicator().setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), android.R.color.transparent));
            holder.getBtnEquip().setOnClickListener(new View.OnClickListener() { // from class: com.example.pet.BackgroundAdapter$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    BackgroundAdapter.onBindViewHolder$lambda$0(this.f$0, item, view);
                }
            });
            return;
        }
        holder.getBackgroundPrice().setText(item.getPrice() + " монет");
        holder.getBackgroundPrice().setTextColor(ContextCompat.getColor(holder.itemView.getContext(), android.R.color.black));
        holder.getBtnBuy().setVisibility(0);
        holder.getBtnEquip().setVisibility(8);
        holder.getStatusIndicator().setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), android.R.color.transparent));
        holder.getBtnBuy().setOnClickListener(new View.OnClickListener() { // from class: com.example.pet.BackgroundAdapter$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                BackgroundAdapter.onBindViewHolder$lambda$1(this.f$0, item, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onBindViewHolder$lambda$0(BackgroundAdapter this$0, BackgroundItem $item, View it) {
        this$0.listener.onEquipBackground($item);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onBindViewHolder$lambda$1(BackgroundAdapter this$0, BackgroundItem $item, View it) {
        this$0.listener.onBuyBackground($item);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.items.size();
    }

    public final void updateItems(List<BackgroundItem> newItems) {
        Intrinsics.checkNotNullParameter(newItems, "newItems");
        this.items = newItems;
        notifyDataSetChanged();
    }
}
