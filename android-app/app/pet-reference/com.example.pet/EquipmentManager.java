package com.example.pet;

import android.content.Context;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: EquipmentManager.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\r\u0018\u0000 \u001d2\u00020\u0001:\u0001\u001dB\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tJ\u001c\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000e0\tJ\u000e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\nJ\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\n0\tJ\u000e\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\nJ\u0006\u0010\u0015\u001a\u00020\nJ\u000e\u0010\u0016\u001a\u00020\u00112\u0006\u0010\u0017\u001a\u00020\nJ\u000e\u0010\u0018\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\nJ\u0016\u0010\u0019\u001a\u00020\u00112\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u0002J,\u0010\u001b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\n0\t2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000e0\tH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lcom/example/pet/EquipmentManager;", "", "context", "Landroid/content/Context;", "<init>", "(Landroid/content/Context;)V", "prefs", "Lcom/example/pet/PreferenceHelper;", "getEquippedItems", "", "", "equipItem", "", "item", "Lcom/example/pet/CustomizationItem;", "allItems", "unequipItem", "", "itemId", "getPurchasedItems", "purchaseItem", "getEquippedBackgroundId", "equipBackground", "backgroundId", "isBackgroundItem", "saveEquippedItems", "items", "isItemCompatible", "equippedItems", "Companion", "app_debug"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class EquipmentManager {
    private static final String PREF_EQUIPPED_BACKGROUND = "equipped_background";
    private static final String PREF_EQUIPPED_ITEMS = "equipped_items";
    private static final String PREF_PURCHASED_ITEMS = "purchased_items";
    private final Context context;
    private final PreferenceHelper prefs;

    public EquipmentManager(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.prefs = new PreferenceHelper(this.context);
    }

    public final List<Integer> getEquippedItems() {
        String itemsString = this.prefs.getString(PREF_EQUIPPED_ITEMS, "");
        if (!(itemsString.length() > 0)) {
            return CollectionsKt.emptyList();
        }
        Iterable iterableSplit$default = StringsKt.split$default((CharSequence) itemsString, new String[]{","}, false, 0, 6, (Object) null);
        Collection arrayList = new ArrayList();
        Iterator it = iterableSplit$default.iterator();
        while (it.hasNext()) {
            Integer intOrNull = StringsKt.toIntOrNull((String) it.next());
            if (intOrNull != null) {
                arrayList.add(intOrNull);
            }
        }
        return (List) arrayList;
    }

    public final boolean equipItem(CustomizationItem item, List<CustomizationItem> allItems) {
        Intrinsics.checkNotNullParameter(item, "item");
        Intrinsics.checkNotNullParameter(allItems, "allItems");
        if (Intrinsics.areEqual(item.getItemType(), "background")) {
            equipBackground(item.getId());
            return true;
        }
        List<Integer> mutableList = CollectionsKt.toMutableList((Collection) getEquippedItems());
        if (!isItemCompatible(item, mutableList, allItems)) {
            return false;
        }
        if (!mutableList.contains(Integer.valueOf(item.getId()))) {
            mutableList.add(Integer.valueOf(item.getId()));
            saveEquippedItems(mutableList);
        }
        return true;
    }

    public final void unequipItem(int itemId) {
        List<Integer> mutableList = CollectionsKt.toMutableList((Collection) getEquippedItems());
        mutableList.remove(Integer.valueOf(itemId));
        saveEquippedItems(mutableList);
    }

    public final List<Integer> getPurchasedItems() {
        String itemsString = this.prefs.getString(PREF_PURCHASED_ITEMS, "");
        if (!(itemsString.length() > 0)) {
            return CollectionsKt.emptyList();
        }
        Iterable iterableSplit$default = StringsKt.split$default((CharSequence) itemsString, new String[]{","}, false, 0, 6, (Object) null);
        Collection arrayList = new ArrayList();
        Iterator it = iterableSplit$default.iterator();
        while (it.hasNext()) {
            Integer intOrNull = StringsKt.toIntOrNull((String) it.next());
            if (intOrNull != null) {
                arrayList.add(intOrNull);
            }
        }
        return (List) arrayList;
    }

    public final void purchaseItem(int itemId) {
        List purchasedItems = CollectionsKt.toMutableList((Collection) getPurchasedItems());
        if (!purchasedItems.contains(Integer.valueOf(itemId))) {
            purchasedItems.add(Integer.valueOf(itemId));
            String itemsString = CollectionsKt.joinToString$default(purchasedItems, ",", null, null, 0, null, null, 62, null);
            this.prefs.saveString(PREF_PURCHASED_ITEMS, itemsString);
        }
    }

    public final int getEquippedBackgroundId() {
        return this.prefs.getInt(PREF_EQUIPPED_BACKGROUND, 1);
    }

    public final void equipBackground(int backgroundId) {
        System.out.println((Object) ("DEBUG: EquipmentManager.equipBackground(" + backgroundId + ")"));
        this.prefs.saveInt(PREF_EQUIPPED_BACKGROUND, backgroundId);
        int saved = this.prefs.getInt(PREF_EQUIPPED_BACKGROUND, 1);
        System.out.println((Object) ("DEBUG: Actually saved: " + saved));
    }

    public final boolean isBackgroundItem(int itemId) {
        return 101 <= itemId && itemId < 106;
    }

    private final void saveEquippedItems(List<Integer> items) {
        String itemsString = CollectionsKt.joinToString$default(items, ",", null, null, 0, null, null, 62, null);
        this.prefs.saveString(PREF_EQUIPPED_ITEMS, itemsString);
    }

    private final boolean isItemCompatible(CustomizationItem item, List<Integer> equippedItems, List<CustomizationItem> allItems) {
        Object next;
        List<Integer> incompatibleWith = item.getIncompatibleWith();
        Iterator<Integer> it = equippedItems.iterator();
        while (it.hasNext()) {
            int equippedId = it.next().intValue();
            if (incompatibleWith.contains(Integer.valueOf(equippedId))) {
                return false;
            }
            Iterator<T> it2 = allItems.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    next = null;
                    break;
                }
                next = it2.next();
                if (((CustomizationItem) next).getId() == equippedId) {
                    break;
                }
            }
            CustomizationItem equippedItem = (CustomizationItem) next;
            if (equippedItem != null && ((Intrinsics.areEqual(equippedItem.getCategory(), item.getCategory()) && !Intrinsics.areEqual(equippedItem.getCategory(), "accessory")) || equippedItem.getIncompatibleWith().contains(Integer.valueOf(item.getId())))) {
                return false;
            }
        }
        return true;
    }
}
