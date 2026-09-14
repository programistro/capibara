package com.example.pet;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CustomizationItem.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u0002\n\u0002\b\u000e\n\u0002\u0010\u0000\n\u0002\b\u0004\b\u0086\b\u0018\u0000 22\u00020\u0001:\u00012B_\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\t\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0005\u0012\b\b\u0002\u0010\f\u001a\u00020\u0005\u0012\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00030\u000e¢\u0006\u0004\b\u000f\u0010\u0010B\u0011\b\u0016\u0012\u0006\u0010\u0011\u001a\u00020\u0012¢\u0006\u0004\b\u000f\u0010\u0013J\u0018\u0010\u001f\u001a\u00020 2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010!\u001a\u00020\u0003H\u0016J\b\u0010\"\u001a\u00020\u0003H\u0016J\t\u0010#\u001a\u00020\u0003HÆ\u0003J\t\u0010$\u001a\u00020\u0005HÆ\u0003J\t\u0010%\u001a\u00020\u0003HÆ\u0003J\t\u0010&\u001a\u00020\u0003HÆ\u0003J\t\u0010'\u001a\u00020\tHÆ\u0003J\t\u0010(\u001a\u00020\tHÆ\u0003J\t\u0010)\u001a\u00020\u0005HÆ\u0003J\t\u0010*\u001a\u00020\u0005HÆ\u0003J\u000f\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00030\u000eHÆ\u0003Ji\u0010,\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\t2\b\b\u0002\u0010\u000b\u001a\u00020\u00052\b\b\u0002\u0010\f\u001a\u00020\u00052\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00030\u000eHÆ\u0001J\u0013\u0010-\u001a\u00020\t2\b\u0010.\u001a\u0004\u0018\u00010/HÖ\u0003J\t\u00100\u001a\u00020\u0003HÖ\u0001J\t\u00101\u001a\u00020\u0005HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0015R\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0015R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u001aR\u0011\u0010\n\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u001aR\u0011\u0010\u000b\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0017R\u0011\u0010\f\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0017R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00030\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001e¨\u00063"}, d2 = {"Lcom/example/pet/CustomizationItem;", "Landroid/os/Parcelable;", "id", "", "name", "", "drawableId", "price", "isPurchased", "", "isEquipped", "category", "itemType", "incompatibleWith", "", "<init>", "(ILjava/lang/String;IIZZLjava/lang/String;Ljava/lang/String;Ljava/util/List;)V", "parcel", "Landroid/os/Parcel;", "(Landroid/os/Parcel;)V", "getId", "()I", "getName", "()Ljava/lang/String;", "getDrawableId", "getPrice", "()Z", "getCategory", "getItemType", "getIncompatibleWith", "()Ljava/util/List;", "writeToParcel", "", "flags", "describeContents", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "", "hashCode", "toString", "CREATOR", "app_debug"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final /* data */ class CustomizationItem implements Parcelable {

    /* JADX INFO: renamed from: CREATOR, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final String category;
    private final int drawableId;
    private final int id;
    private final List<Integer> incompatibleWith;
    private final boolean isEquipped;
    private final boolean isPurchased;
    private final String itemType;
    private final String name;
    private final int price;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ CustomizationItem copy$default(CustomizationItem customizationItem, int i, String str, int i2, int i3, boolean z, boolean z2, String str2, String str3, List list, int i4, Object obj) {
        if ((i4 & 1) != 0) {
            i = customizationItem.id;
        }
        if ((i4 & 2) != 0) {
            str = customizationItem.name;
        }
        if ((i4 & 4) != 0) {
            i2 = customizationItem.drawableId;
        }
        if ((i4 & 8) != 0) {
            i3 = customizationItem.price;
        }
        if ((i4 & 16) != 0) {
            z = customizationItem.isPurchased;
        }
        if ((i4 & 32) != 0) {
            z2 = customizationItem.isEquipped;
        }
        if ((i4 & 64) != 0) {
            str2 = customizationItem.category;
        }
        if ((i4 & 128) != 0) {
            str3 = customizationItem.itemType;
        }
        if ((i4 & 256) != 0) {
            list = customizationItem.incompatibleWith;
        }
        String str4 = str3;
        List list2 = list;
        boolean z3 = z2;
        String str5 = str2;
        boolean z4 = z;
        int i5 = i2;
        return customizationItem.copy(i, str, i5, i3, z4, z3, str5, str4, list2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final int getId() {
        return this.id;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getName() {
        return this.name;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final int getDrawableId() {
        return this.drawableId;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final int getPrice() {
        return this.price;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final boolean getIsPurchased() {
        return this.isPurchased;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final boolean getIsEquipped() {
        return this.isEquipped;
    }

    /* JADX INFO: renamed from: component7, reason: from getter */
    public final String getCategory() {
        return this.category;
    }

    /* JADX INFO: renamed from: component8, reason: from getter */
    public final String getItemType() {
        return this.itemType;
    }

    public final List<Integer> component9() {
        return this.incompatibleWith;
    }

    public final CustomizationItem copy(int id, String name, int drawableId, int price, boolean isPurchased, boolean isEquipped, String category, String itemType, List<Integer> incompatibleWith) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(category, "category");
        Intrinsics.checkNotNullParameter(itemType, "itemType");
        Intrinsics.checkNotNullParameter(incompatibleWith, "incompatibleWith");
        return new CustomizationItem(id, name, drawableId, price, isPurchased, isEquipped, category, itemType, incompatibleWith);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CustomizationItem)) {
            return false;
        }
        CustomizationItem customizationItem = (CustomizationItem) other;
        return this.id == customizationItem.id && Intrinsics.areEqual(this.name, customizationItem.name) && this.drawableId == customizationItem.drawableId && this.price == customizationItem.price && this.isPurchased == customizationItem.isPurchased && this.isEquipped == customizationItem.isEquipped && Intrinsics.areEqual(this.category, customizationItem.category) && Intrinsics.areEqual(this.itemType, customizationItem.itemType) && Intrinsics.areEqual(this.incompatibleWith, customizationItem.incompatibleWith);
    }

    public int hashCode() {
        return (((((((((((((((Integer.hashCode(this.id) * 31) + this.name.hashCode()) * 31) + Integer.hashCode(this.drawableId)) * 31) + Integer.hashCode(this.price)) * 31) + Boolean.hashCode(this.isPurchased)) * 31) + Boolean.hashCode(this.isEquipped)) * 31) + this.category.hashCode()) * 31) + this.itemType.hashCode()) * 31) + this.incompatibleWith.hashCode();
    }

    public String toString() {
        return "CustomizationItem(id=" + this.id + ", name=" + this.name + ", drawableId=" + this.drawableId + ", price=" + this.price + ", isPurchased=" + this.isPurchased + ", isEquipped=" + this.isEquipped + ", category=" + this.category + ", itemType=" + this.itemType + ", incompatibleWith=" + this.incompatibleWith + ")";
    }

    public CustomizationItem(int id, String name, int drawableId, int price, boolean isPurchased, boolean isEquipped, String category, String itemType, List<Integer> incompatibleWith) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(category, "category");
        Intrinsics.checkNotNullParameter(itemType, "itemType");
        Intrinsics.checkNotNullParameter(incompatibleWith, "incompatibleWith");
        this.id = id;
        this.name = name;
        this.drawableId = drawableId;
        this.price = price;
        this.isPurchased = isPurchased;
        this.isEquipped = isEquipped;
        this.category = category;
        this.itemType = itemType;
        this.incompatibleWith = incompatibleWith;
    }

    public /* synthetic */ CustomizationItem(int i, String str, int i2, int i3, boolean z, boolean z2, String str2, String str3, List list, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, str, i2, i3, (i4 & 16) != 0 ? false : z, (i4 & 32) != 0 ? false : z2, (i4 & 64) != 0 ? "clothing" : str2, (i4 & 128) != 0 ? "clothing" : str3, (i4 & 256) != 0 ? CollectionsKt.emptyList() : list);
    }

    public final int getId() {
        return this.id;
    }

    public final String getName() {
        return this.name;
    }

    public final int getDrawableId() {
        return this.drawableId;
    }

    public final int getPrice() {
        return this.price;
    }

    public final boolean isPurchased() {
        return this.isPurchased;
    }

    public final boolean isEquipped() {
        return this.isEquipped;
    }

    public final String getCategory() {
        return this.category;
    }

    public final String getItemType() {
        return this.itemType;
    }

    public final List<Integer> getIncompatibleWith() {
        return this.incompatibleWith;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public CustomizationItem(Parcel parcel) {
        boolean z;
        List<Integer> list;
        Intrinsics.checkNotNullParameter(parcel, "parcel");
        int i = parcel.readInt();
        String string = parcel.readString();
        String str = string == null ? "" : string;
        int i2 = parcel.readInt();
        int i3 = parcel.readInt();
        boolean z2 = false;
        if (parcel.readByte() != 0) {
            z = false;
            z2 = true;
        } else {
            z = false;
        }
        boolean z3 = parcel.readByte() != 0 ? true : z;
        String string2 = parcel.readString();
        String str2 = string2 == null ? "clothing" : string2;
        String string3 = parcel.readString();
        String str3 = string3 == null ? "clothing" : string3;
        int[] iArrCreateIntArray = parcel.createIntArray();
        this(i, str, i2, i3, z2, z3, str2, str3, (iArrCreateIntArray == null || (list = ArraysKt.toList(iArrCreateIntArray)) == null) ? CollectionsKt.emptyList() : list);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        Intrinsics.checkNotNullParameter(parcel, "parcel");
        parcel.writeInt(this.id);
        parcel.writeString(this.name);
        parcel.writeInt(this.drawableId);
        parcel.writeInt(this.price);
        parcel.writeByte(this.isPurchased ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isEquipped ? (byte) 1 : (byte) 0);
        parcel.writeString(this.category);
        parcel.writeString(this.itemType);
        parcel.writeIntArray(CollectionsKt.toIntArray(this.incompatibleWith));
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* JADX INFO: renamed from: com.example.pet.CustomizationItem$CREATOR, reason: from kotlin metadata */
    /* JADX INFO: compiled from: CustomizationItem.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u001d\u0010\b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¢\u0006\u0002\u0010\f¨\u0006\r"}, d2 = {"Lcom/example/pet/CustomizationItem$CREATOR;", "Landroid/os/Parcelable$Creator;", "Lcom/example/pet/CustomizationItem;", "<init>", "()V", "createFromParcel", "parcel", "Landroid/os/Parcel;", "newArray", "", "size", "", "(I)[Lcom/example/pet/CustomizationItem;", "app_debug"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static final class Companion implements Parcelable.Creator<CustomizationItem> {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CustomizationItem createFromParcel(Parcel parcel) {
            Intrinsics.checkNotNullParameter(parcel, "parcel");
            return new CustomizationItem(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CustomizationItem[] newArray(int size) {
            return new CustomizationItem[size];
        }
    }
}
