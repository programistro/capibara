package com.example.pet;

import com.example.capibara.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CustomizationAdapter.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0002\u0019\u001aB\u001d\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0004\b\b\u0010\tJ\u0018\u0010\n\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u000eH\u0016J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0005H\u0002J\b\u0010\u0016\u001a\u00020\u000eH\u0016J\u0014\u0010\u0017\u001a\u00020\u00102\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lcom/example/pet/CustomizationAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/example/pet/CustomizationAdapter$CustomizationViewHolder;", "items", "", "Lcom/example/pet/CustomizationItem;", "listener", "Lcom/example/pet/CustomizationAdapter$OnCustomizationActionListener;", "<init>", "(Ljava/util/List;Lcom/example/pet/CustomizationAdapter$OnCustomizationActionListener;)V", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "", "onBindViewHolder", "", "holder", "position", "getCategoryText", "", "item", "getItemCount", "updateItems", "newItems", "OnCustomizationActionListener", "CustomizationViewHolder", "app_debug"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class CustomizationAdapter extends RecyclerView.Adapter<CustomizationViewHolder> {
    private List<CustomizationItem> items;
    private final OnCustomizationActionListener listener;

    /* JADX INFO: compiled from: CustomizationAdapter.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0007\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\b"}, d2 = {"Lcom/example/pet/CustomizationAdapter$OnCustomizationActionListener;", "", "onBuyItem", "", "item", "Lcom/example/pet/CustomizationItem;", "onEquipItem", "onUnequipItem", "app_debug"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public interface OnCustomizationActionListener {
        void onBuyItem(CustomizationItem item);

        void onEquipItem(CustomizationItem item);

        void onUnequipItem(CustomizationItem item);
    }

    public CustomizationAdapter(List<CustomizationItem> items, OnCustomizationActionListener listener) {
        Intrinsics.checkNotNullParameter(items, "items");
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.items = items;
        this.listener = listener;
    }

    /* JADX INFO: compiled from: CustomizationAdapter.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u000e\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\rR\u0011\u0010\u0010\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\rR\u0011\u0010\u0012\u001a\u00020\u0013¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0016\u001a\u00020\u0013¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0015R\u0011\u0010\u0018\u001a\u00020\u0013¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0015R\u0011\u0010\u001a\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001c¨\u0006\u001d"}, d2 = {"Lcom/example/pet/CustomizationAdapter$CustomizationViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "<init>", "(Landroid/view/View;)V", "itemImage", "Landroid/widget/ImageView;", "getItemImage", "()Landroid/widget/ImageView;", "itemName", "Landroid/widget/TextView;", "getItemName", "()Landroid/widget/TextView;", "itemPrice", "getItemPrice", "itemCategory", "getItemCategory", "btnBuy", "Landroid/widget/Button;", "getBtnBuy", "()Landroid/widget/Button;", "btnEquip", "getBtnEquip", "btnUnequip", "getBtnUnequip", "statusIndicator", "getStatusIndicator", "()Landroid/view/View;", "app_debug"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static final class CustomizationViewHolder extends RecyclerView.ViewHolder {
        private final Button btnBuy;
        private final Button btnEquip;
        private final Button btnUnequip;
        private final TextView itemCategory;
        private final ImageView itemImage;
        private final TextView itemName;
        private final TextView itemPrice;
        private final View statusIndicator;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public CustomizationViewHolder(View itemView) {
            super(itemView);
            Intrinsics.checkNotNullParameter(itemView, "itemView");
            View viewFindViewById = itemView.findViewById(R.id.ivItemImage);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
            this.itemImage = (ImageView) viewFindViewById;
            View viewFindViewById2 = itemView.findViewById(R.id.tvItemName);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(...)");
            this.itemName = (TextView) viewFindViewById2;
            View viewFindViewById3 = itemView.findViewById(R.id.tvItemPrice);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(...)");
            this.itemPrice = (TextView) viewFindViewById3;
            View viewFindViewById4 = itemView.findViewById(R.id.tvItemCategory);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(...)");
            this.itemCategory = (TextView) viewFindViewById4;
            View viewFindViewById5 = itemView.findViewById(R.id.btnBuy);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "findViewById(...)");
            this.btnBuy = (Button) viewFindViewById5;
            View viewFindViewById6 = itemView.findViewById(R.id.btnEquip);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById6, "findViewById(...)");
            this.btnEquip = (Button) viewFindViewById6;
            View viewFindViewById7 = itemView.findViewById(R.id.btnUnequip);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById7, "findViewById(...)");
            this.btnUnequip = (Button) viewFindViewById7;
            View viewFindViewById8 = itemView.findViewById(R.id.viewStatusIndicator);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById8, "findViewById(...)");
            this.statusIndicator = viewFindViewById8;
        }

        public final ImageView getItemImage() {
            return this.itemImage;
        }

        public final TextView getItemName() {
            return this.itemName;
        }

        public final TextView getItemPrice() {
            return this.itemPrice;
        }

        public final TextView getItemCategory() {
            return this.itemCategory;
        }

        public final Button getBtnBuy() {
            return this.btnBuy;
        }

        public final Button getBtnEquip() {
            return this.btnEquip;
        }

        public final Button getBtnUnequip() {
            return this.btnUnequip;
        }

        public final View getStatusIndicator() {
            return this.statusIndicator;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public CustomizationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customization, parent, false);
        Intrinsics.checkNotNull(view);
        return new CustomizationViewHolder(view);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00ac  */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onBindViewHolder(com.example.pet.CustomizationAdapter.CustomizationViewHolder r10, int r11) {
        /*
            Method dump skipped, instruction units count: 586
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.example.pet.CustomizationAdapter.onBindViewHolder(com.example.pet.CustomizationAdapter$CustomizationViewHolder, int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onBindViewHolder$lambda$0(CustomizationItem $item, CustomizationAdapter this$0, View it) {
        System.out.println((Object) ("DEBUG: Buy button clicked for item " + $item.getId()));
        this$0.listener.onBuyItem($item);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onBindViewHolder$lambda$1(CustomizationItem $item, CustomizationAdapter this$0, View it) {
        System.out.println((Object) ("DEBUG: Equip button clicked for item " + $item.getId()));
        this$0.listener.onEquipItem($item);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onBindViewHolder$lambda$2(CustomizationItem $item, CustomizationAdapter this$0, View it) {
        System.out.println((Object) ("DEBUG: Unequip button clicked for item " + $item.getId()));
        this$0.listener.onUnequipItem($item);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private final String getCategoryText(CustomizationItem item) {
        if (Intrinsics.areEqual(item.getItemType(), "background")) {
            return "🏞️ Фон";
        }
        String category = item.getCategory();
        switch (category.hashCode()) {
            case -2115023086:
                if (category.equals("accessory")) {
                    return "💎 Аксессуар";
                }
                break;
            case 3029410:
                if (category.equals("body")) {
                    return "👕 Тело";
                }
                break;
            case 3135069:
                if (category.equals("face")) {
                    return "👓 Лицо";
                }
                break;
            case 3198432:
                if (category.equals("head")) {
                    return "👒 Голова";
                }
                break;
        }
        return item.getCategory();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.items.size();
    }

    public final void updateItems(List<CustomizationItem> newItems) {
        Intrinsics.checkNotNullParameter(newItems, "newItems");
        this.items = newItems;
        notifyDataSetChanged();
    }
}
