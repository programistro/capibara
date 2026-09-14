package com.example.pet;

import com.example.capibara.R;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.location.LocationRequestCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pet.CustomizationAdapter;
import com.google.android.material.card.MaterialCardViewHelper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CustomizationFragment.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J&\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0016J\u001a\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u000e2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0016J\u000e\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u0002J\u0010\u0010\u0019\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\fH\u0002J\b\u0010\u001b\u001a\u00020\u0016H\u0016J\u0010\u0010\u001c\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\fH\u0002J\u0016\u0010\u001d\u001a\u00020\u00162\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00160\u001fH\u0002J\b\u0010 \u001a\u00020\u0016H\u0002J\u0010\u0010!\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\fH\u0002J\u0010\u0010\"\u001a\u00020#2\u0006\u0010\u001a\u001a\u00020\fH\u0002J\u0010\u0010$\u001a\u00020#2\u0006\u0010\u001a\u001a\u00020\fH\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082.¢\u0006\u0002\n\u0000¨\u0006%"}, d2 = {"Lcom/example/pet/CustomizationFragment;", "Landroidx/fragment/app/Fragment;", "<init>", "()V", "equipmentManager", "Lcom/example/pet/EquipmentManager;", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "customizationAdapter", "Lcom/example/pet/CustomizationAdapter;", "allItems", "", "Lcom/example/pet/CustomizationItem;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onViewCreated", "", "view", "createAllItems", "buyItem", "item", "onResume", "equipItem", "runOnUiThreadIfAvailable", "action", "Lkotlin/Function0;", "refreshAllItems", "unequipItem", "getItemTypeText", "", "getEquipText", "app_debug"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class CustomizationFragment extends Fragment {
    private List<CustomizationItem> allItems;
    private CustomizationAdapter customizationAdapter;
    private EquipmentManager equipmentManager;
    private RecyclerView recyclerView;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        return inflater.inflate(R.layout.fragment_customization_unified, container, false);
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, savedInstanceState);
        Context contextRequireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
        this.equipmentManager = new EquipmentManager(contextRequireContext);
        this.recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewCustomization);
        RecyclerView recyclerView = this.recyclerView;
        CustomizationAdapter customizationAdapter = null;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recyclerView");
            recyclerView = null;
        }
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        this.allItems = createAllItems();
        List<CustomizationItem> list = this.allItems;
        if (list == null) {
            Intrinsics.throwUninitializedPropertyAccessException("allItems");
            list = null;
        }
        this.customizationAdapter = new CustomizationAdapter(list, new CustomizationAdapter.OnCustomizationActionListener() { // from class: com.example.pet.CustomizationFragment.onViewCreated.1
            @Override // com.example.pet.CustomizationAdapter.OnCustomizationActionListener
            public void onBuyItem(CustomizationItem item) {
                Intrinsics.checkNotNullParameter(item, "item");
                CustomizationFragment.this.buyItem(item);
            }

            @Override // com.example.pet.CustomizationAdapter.OnCustomizationActionListener
            public void onEquipItem(CustomizationItem item) {
                Intrinsics.checkNotNullParameter(item, "item");
                CustomizationFragment.this.equipItem(item);
            }

            @Override // com.example.pet.CustomizationAdapter.OnCustomizationActionListener
            public void onUnequipItem(CustomizationItem item) {
                Intrinsics.checkNotNullParameter(item, "item");
                CustomizationFragment.this.unequipItem(item);
            }
        });
        RecyclerView recyclerView2 = this.recyclerView;
        if (recyclerView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recyclerView");
            recyclerView2 = null;
        }
        CustomizationAdapter customizationAdapter2 = this.customizationAdapter;
        if (customizationAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("customizationAdapter");
        } else {
            customizationAdapter = customizationAdapter2;
        }
        recyclerView2.setAdapter(customizationAdapter);
        ((Button) view.findViewById(R.id.btnBack)).setOnClickListener(new View.OnClickListener() { // from class: com.example.pet.CustomizationFragment$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                CustomizationFragment.onViewCreated$lambda$0(this.f$0, view2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onViewCreated$lambda$0(CustomizationFragment this$0, View it) {
        this$0.getParentFragmentManager().beginTransaction().replace(R.id.fragment_frame, new TabletFragment()).commit();
    }

    private final List<CustomizationItem> createAllItems() {
        System.out.println((Object) "DEBUG: createAllItems called");
        List items = new ArrayList();
        EquipmentManager equipmentManager = this.equipmentManager;
        EquipmentManager equipmentManager2 = null;
        if (equipmentManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("equipmentManager");
            equipmentManager = null;
        }
        List<Integer> purchasedItems = equipmentManager.getPurchasedItems();
        EquipmentManager equipmentManager3 = this.equipmentManager;
        if (equipmentManager3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("equipmentManager");
            equipmentManager3 = null;
        }
        List<Integer> equippedItems = equipmentManager3.getEquippedItems();
        EquipmentManager equipmentManager4 = this.equipmentManager;
        if (equipmentManager4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("equipmentManager");
        } else {
            equipmentManager2 = equipmentManager4;
        }
        int equippedBackgroundId = equipmentManager2.getEquippedBackgroundId();
        System.out.println((Object) ("DEBUG: purchasedItems: " + purchasedItems));
        System.out.println((Object) ("DEBUG: equippedItems: " + equippedItems));
        System.out.println((Object) ("DEBUG: equippedBackgroundId: " + equippedBackgroundId));
        Iterable<CustomizationItem> clothingItems = CollectionsKt.listOf((Object[]) new CustomizationItem[]{new CustomizationItem(1, "Шляпа", R.drawable.hat_1, 100, false, false, "head", "clothing", CollectionsKt.listOf((Object[]) new Integer[]{5, 6, 8, 10}), 48, null), new CustomizationItem(2, "Очки", R.drawable.glasses_1, 150, false, false, "face", "clothing", CollectionsKt.listOf(7), 48, null), new CustomizationItem(3, "Пиджак", R.drawable.jacket, 80, false, false, "body", "clothing", CollectionsKt.listOf(4), 48, null), new CustomizationItem(4, "Пальто", R.drawable.coat, 120, false, false, "body", "clothing", CollectionsKt.listOf(3), 48, null), new CustomizationItem(5, "Бант", R.drawable.bow, MaterialCardViewHelper.DEFAULT_FADE_ANIM_DURATION, false, false, "head", "clothing", CollectionsKt.listOf((Object[]) new Integer[]{1, 6, 8, 10}), 48, null), new CustomizationItem(6, "Кепка", R.drawable.cap, 90, false, false, "head", "clothing", CollectionsKt.listOf((Object[]) new Integer[]{1, 5, 8, 10}), 48, null), new CustomizationItem(7, "Деловые очки", R.drawable.glasses_2, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, false, false, "face", "clothing", CollectionsKt.listOf(2), 48, null), new CustomizationItem(8, "Ободок", R.drawable.headband, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, false, false, "head", "clothing", CollectionsKt.listOf((Object[]) new Integer[]{1, 5, 6, 10}), 48, null), new CustomizationItem(9, "Шарф", R.drawable.scarf, 150, false, false, "accessory", "clothing", null, 304, null), new CustomizationItem(10, "Ковбойская шляпа", R.drawable.hat_2, 180, false, false, "head", "clothing", CollectionsKt.listOf((Object[]) new Integer[]{1, 5, 6, 8}), 48, null)});
        for (CustomizationItem customizationItem : clothingItems) {
            items.add(CustomizationItem.copy$default(customizationItem, 0, null, 0, 0, purchasedItems.contains(Integer.valueOf(customizationItem.getId())), equippedItems.contains(Integer.valueOf(customizationItem.getId())), null, null, null, 463, null));
        }
        Iterable<CustomizationItem> backgroundItems = CollectionsKt.listOf((Object[]) new CustomizationItem[]{new CustomizationItem(TypedValues.TYPE_TARGET, "Лес", R.drawable.background_forest, 0, false, false, "background", "background", null, 304, null), new CustomizationItem(LocationRequestCompat.QUALITY_BALANCED_POWER_ACCURACY, "Пляж", R.drawable.background_beach, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, false, false, "background", "background", null, 304, null), new CustomizationItem(103, "Горы", R.drawable.background_sea, MaterialCardViewHelper.DEFAULT_FADE_ANIM_DURATION, false, false, "background", "background", null, 304, null), new CustomizationItem(LocationRequestCompat.QUALITY_LOW_POWER, "Город", R.drawable.background_swamp, 400, false, false, "background", "background", null, 304, null)});
        for (CustomizationItem customizationItem2 : backgroundItems) {
            boolean zContains = customizationItem2.getId() == 101 ? true : purchasedItems.contains(Integer.valueOf(customizationItem2.getId()));
            boolean z = equippedBackgroundId == customizationItem2.getId();
            System.out.println((Object) ("DEBUG: Background " + customizationItem2.getId() + " - purchased: " + zContains + ", equipped: " + z));
            items.add(CustomizationItem.copy$default(customizationItem2, 0, null, 0, 0, zContains, z, null, null, null, 463, null));
        }
        System.out.println((Object) ("DEBUG: Total items created: " + items.size()));
        return items;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void buyItem(CustomizationItem item) {
        if (item.isPurchased()) {
            Toast.makeText(requireContext(), "Этот предмет уже куплен!", 0).show();
            return;
        }
        Context contextRequireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
        CurrencyManager currencyManager = new CurrencyManager(contextRequireContext);
        if (!currencyManager.hasEnoughCoins(item.getPrice())) {
            int needed = item.getPrice() - currencyManager.getCurrentCoins();
            Toast.makeText(requireContext(), "Недостаточно монет! Нужно ещё " + needed + " монет", 1).show();
            return;
        }
        boolean purchaseSuccess = currencyManager.subtractCoins(item.getPrice());
        if (!purchaseSuccess) {
            Toast.makeText(requireContext(), "Ошибка при покупке!", 0).show();
            return;
        }
        EquipmentManager equipmentManager = this.equipmentManager;
        List<CustomizationItem> list = null;
        if (equipmentManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("equipmentManager");
            equipmentManager = null;
        }
        equipmentManager.purchaseItem(item.getId());
        FragmentActivity activity = getActivity();
        MainActivity mainActivity = activity instanceof MainActivity ? (MainActivity) activity : null;
        if (mainActivity != null) {
            mainActivity.updateCoinDisplay();
        }
        this.allItems = createAllItems();
        CustomizationAdapter customizationAdapter = this.customizationAdapter;
        if (customizationAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("customizationAdapter");
            customizationAdapter = null;
        }
        List<CustomizationItem> list2 = this.allItems;
        if (list2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("allItems");
        } else {
            list = list2;
        }
        customizationAdapter.updateItems(list);
        Toast.makeText(requireContext(), getItemTypeText(item) + " \"" + item.getName() + "\" куплен за " + item.getPrice() + " монет!", 0).show();
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        System.out.println((Object) "DEBUG: CustomizationFragment onResume");
        refreshAllItems();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void equipItem(final CustomizationItem item) {
        System.out.println((Object) "=== DEBUG: equipItem called ===");
        System.out.println((Object) ("Item: " + item.getName() + ", ID: " + item.getId() + ", Type: " + item.getItemType() + ", Purchased: " + item.isPurchased() + ", Equipped: " + item.isEquipped()));
        if (!item.isPurchased()) {
            Toast.makeText(requireContext(), "Сначала нужно купить этот предмет!", 0).show();
            return;
        }
        List<CustomizationItem> list = null;
        EquipmentManager equipmentManager = null;
        if (Intrinsics.areEqual(item.getItemType(), "background")) {
            System.out.println((Object) "DEBUG: Processing as background");
            try {
                EquipmentManager equipmentManager2 = this.equipmentManager;
                if (equipmentManager2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("equipmentManager");
                    equipmentManager2 = null;
                }
                equipmentManager2.equipBackground(item.getId());
                System.out.println((Object) "DEBUG: Background saved to SharedPreferences");
                EquipmentManager equipmentManager3 = this.equipmentManager;
                if (equipmentManager3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("equipmentManager");
                } else {
                    equipmentManager = equipmentManager3;
                }
                System.out.println((Object) ("DEBUG: Read back from SharedPreferences: " + equipmentManager.getEquippedBackgroundId()));
                runOnUiThreadIfAvailable(new Function0() { // from class: com.example.pet.CustomizationFragment$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return CustomizationFragment.equipItem$lambda$3(this.f$0, item);
                    }
                });
                return;
            } catch (Exception e) {
                System.out.println((Object) ("ERROR in equipBackground: " + e.getMessage()));
                e.printStackTrace();
                return;
            }
        }
        System.out.println((Object) "DEBUG: Processing as clothing");
        try {
            EquipmentManager equipmentManager4 = this.equipmentManager;
            if (equipmentManager4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("equipmentManager");
                equipmentManager4 = null;
            }
            List<CustomizationItem> list2 = this.allItems;
            if (list2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("allItems");
            } else {
                list = list2;
            }
            boolean zEquipItem = equipmentManager4.equipItem(item, list);
            System.out.println((Object) ("DEBUG: EquipmentManager.equipItem returned: " + zEquipItem));
            if (zEquipItem) {
                runOnUiThreadIfAvailable(new Function0() { // from class: com.example.pet.CustomizationFragment$$ExternalSyntheticLambda2
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return CustomizationFragment.equipItem$lambda$4(this.f$0, item);
                    }
                });
            } else {
                Toast.makeText(requireContext(), "Этот предмет несовместим с уже надетыми!", 1).show();
            }
        } catch (Exception e2) {
            System.out.println((Object) ("ERROR in equipItem (clothing): " + e2.getMessage()));
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit equipItem$lambda$3(CustomizationFragment this$0, CustomizationItem $item) {
        FragmentActivity activity = this$0.getActivity();
        MainActivity mainActivity = activity instanceof MainActivity ? (MainActivity) activity : null;
        if (mainActivity != null) {
            mainActivity.updateBackground();
        }
        this$0.refreshAllItems();
        Toast.makeText(this$0.requireContext(), "Фон \"" + $item.getName() + "\" установлен!", 0).show();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit equipItem$lambda$4(CustomizationFragment this$0, CustomizationItem $item) {
        FragmentActivity activity = this$0.getActivity();
        MainActivity mainActivity = activity instanceof MainActivity ? (MainActivity) activity : null;
        if (mainActivity != null) {
            mainActivity.refreshPetCustomization();
        }
        this$0.refreshAllItems();
        Toast.makeText(this$0.requireContext(), "Предмет \"" + $item.getName() + "\" надет!", 0).show();
        return Unit.INSTANCE;
    }

    private final void runOnUiThreadIfAvailable(final Function0<Unit> action) {
        if (isAdded() && !isDetached()) {
            requireActivity().runOnUiThread(new Runnable() { // from class: com.example.pet.CustomizationFragment$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    action.invoke();
                }
            });
        }
    }

    private final void refreshAllItems() {
        System.out.println((Object) "DEBUG: refreshAllItems called");
        Context contextRequireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
        new EquipmentManager(contextRequireContext);
        Context contextRequireContext2 = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext2, "requireContext(...)");
        new CurrencyManager(contextRequireContext2);
        this.allItems = createAllItems();
        List<CustomizationItem> list = this.allItems;
        if (list == null) {
            Intrinsics.throwUninitializedPropertyAccessException("allItems");
            list = null;
        }
        System.out.println((Object) ("DEBUG: Items count: " + list.size()));
        Iterable iterable = this.allItems;
        if (iterable == null) {
            Intrinsics.throwUninitializedPropertyAccessException("allItems");
            iterable = null;
        }
        Collection arrayList = new ArrayList();
        for (Object obj : iterable) {
            if (Intrinsics.areEqual(((CustomizationItem) obj).getItemType(), "background")) {
                arrayList.add(obj);
            }
        }
        for (CustomizationItem customizationItem : (List) arrayList) {
            System.out.println((Object) ("DEBUG: Background " + customizationItem.getId() + " '" + customizationItem.getName() + "' - purchased: " + customizationItem.isPurchased() + ", equipped: " + customizationItem.isEquipped()));
        }
        CustomizationAdapter customizationAdapter = this.customizationAdapter;
        if (customizationAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("customizationAdapter");
            customizationAdapter = null;
        }
        List<CustomizationItem> list2 = this.allItems;
        if (list2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("allItems");
            list2 = null;
        }
        customizationAdapter.updateItems(list2);
        FragmentActivity activity = getActivity();
        MainActivity mainActivity = activity instanceof MainActivity ? (MainActivity) activity : null;
        if (mainActivity != null) {
            mainActivity.updateCoinDisplay();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void unequipItem(CustomizationItem item) {
        List<CustomizationItem> list = null;
        if (Intrinsics.areEqual(item.getItemType(), "background")) {
            EquipmentManager equipmentManager = this.equipmentManager;
            if (equipmentManager == null) {
                Intrinsics.throwUninitializedPropertyAccessException("equipmentManager");
                equipmentManager = null;
            }
            equipmentManager.equipBackground(TypedValues.TYPE_TARGET);
            FragmentActivity activity = getActivity();
            MainActivity mainActivity = activity instanceof MainActivity ? (MainActivity) activity : null;
            if (mainActivity != null) {
                mainActivity.updateBackground();
            }
            this.allItems = createAllItems();
            CustomizationAdapter customizationAdapter = this.customizationAdapter;
            if (customizationAdapter == null) {
                Intrinsics.throwUninitializedPropertyAccessException("customizationAdapter");
                customizationAdapter = null;
            }
            List<CustomizationItem> list2 = this.allItems;
            if (list2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("allItems");
            } else {
                list = list2;
            }
            customizationAdapter.updateItems(list);
            Toast.makeText(requireContext(), "Фон сброшен на стандартный", 0).show();
            return;
        }
        EquipmentManager equipmentManager2 = this.equipmentManager;
        if (equipmentManager2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("equipmentManager");
            equipmentManager2 = null;
        }
        equipmentManager2.unequipItem(item.getId());
        FragmentActivity activity2 = getActivity();
        MainActivity mainActivity2 = activity2 instanceof MainActivity ? (MainActivity) activity2 : null;
        if (mainActivity2 != null) {
            mainActivity2.refreshPetCustomization();
        }
        this.allItems = createAllItems();
        CustomizationAdapter customizationAdapter2 = this.customizationAdapter;
        if (customizationAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("customizationAdapter");
            customizationAdapter2 = null;
        }
        List<CustomizationItem> list3 = this.allItems;
        if (list3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("allItems");
        } else {
            list = list3;
        }
        customizationAdapter2.updateItems(list);
        Toast.makeText(requireContext(), "Предмет \"" + item.getName() + "\" снят!", 0).show();
    }

    private final String getItemTypeText(CustomizationItem item) {
        return Intrinsics.areEqual(item.getItemType(), "background") ? "Фон" : "Предмет";
    }

    private final String getEquipText(CustomizationItem item) {
        return Intrinsics.areEqual(item.getItemType(), "background") ? "установлен" : "надет";
    }
}
