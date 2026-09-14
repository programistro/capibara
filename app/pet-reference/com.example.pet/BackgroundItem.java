package com.example.pet;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: BackgroundItem.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u000b\n\u0002\u0010\u0000\n\u0002\b\u0004\b\u0086\b\u0018\u0000 '2\u00020\u0001:\u0001'B;\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\t¢\u0006\u0004\b\u000b\u0010\fB\u0011\b\u0016\u0012\u0006\u0010\r\u001a\u00020\u000e¢\u0006\u0004\b\u000b\u0010\u000fJ\u0018\u0010\u0017\u001a\u00020\u00182\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0019\u001a\u00020\u0003H\u0016J\b\u0010\u001a\u001a\u00020\u0003H\u0016J\t\u0010\u001b\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001c\u001a\u00020\u0005HÆ\u0003J\t\u0010\u001d\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001f\u001a\u00020\tHÆ\u0003J\t\u0010 \u001a\u00020\tHÆ\u0003JE\u0010!\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\tHÆ\u0001J\u0013\u0010\"\u001a\u00020\t2\b\u0010#\u001a\u0004\u0018\u00010$HÖ\u0003J\t\u0010%\u001a\u00020\u0003HÖ\u0001J\t\u0010&\u001a\u00020\u0005HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0011R\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0011R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0016R\u0011\u0010\n\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0016¨\u0006("}, d2 = {"Lcom/example/pet/BackgroundItem;", "Landroid/os/Parcelable;", "id", "", "name", "", "drawableId", "price", "isPurchased", "", "isEquipped", "<init>", "(ILjava/lang/String;IIZZ)V", "parcel", "Landroid/os/Parcel;", "(Landroid/os/Parcel;)V", "getId", "()I", "getName", "()Ljava/lang/String;", "getDrawableId", "getPrice", "()Z", "writeToParcel", "", "flags", "describeContents", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "other", "", "hashCode", "toString", "CREATOR", "app_debug"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final /* data */ class BackgroundItem implements Parcelable {

    /* JADX INFO: renamed from: CREATOR, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final int drawableId;
    private final int id;
    private final boolean isEquipped;
    private final boolean isPurchased;
    private final String name;
    private final int price;

    public static /* synthetic */ BackgroundItem copy$default(BackgroundItem backgroundItem, int i, String str, int i2, int i3, boolean z, boolean z2, int i4, Object obj) {
        if ((i4 & 1) != 0) {
            i = backgroundItem.id;
        }
        if ((i4 & 2) != 0) {
            str = backgroundItem.name;
        }
        if ((i4 & 4) != 0) {
            i2 = backgroundItem.drawableId;
        }
        if ((i4 & 8) != 0) {
            i3 = backgroundItem.price;
        }
        if ((i4 & 16) != 0) {
            z = backgroundItem.isPurchased;
        }
        if ((i4 & 32) != 0) {
            z2 = backgroundItem.isEquipped;
        }
        boolean z3 = z;
        boolean z4 = z2;
        return backgroundItem.copy(i, str, i2, i3, z3, z4);
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

    public final BackgroundItem copy(int id, String name, int drawableId, int price, boolean isPurchased, boolean isEquipped) {
        Intrinsics.checkNotNullParameter(name, "name");
        return new BackgroundItem(id, name, drawableId, price, isPurchased, isEquipped);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BackgroundItem)) {
            return false;
        }
        BackgroundItem backgroundItem = (BackgroundItem) other;
        return this.id == backgroundItem.id && Intrinsics.areEqual(this.name, backgroundItem.name) && this.drawableId == backgroundItem.drawableId && this.price == backgroundItem.price && this.isPurchased == backgroundItem.isPurchased && this.isEquipped == backgroundItem.isEquipped;
    }

    public int hashCode() {
        return (((((((((Integer.hashCode(this.id) * 31) + this.name.hashCode()) * 31) + Integer.hashCode(this.drawableId)) * 31) + Integer.hashCode(this.price)) * 31) + Boolean.hashCode(this.isPurchased)) * 31) + Boolean.hashCode(this.isEquipped);
    }

    public String toString() {
        return "BackgroundItem(id=" + this.id + ", name=" + this.name + ", drawableId=" + this.drawableId + ", price=" + this.price + ", isPurchased=" + this.isPurchased + ", isEquipped=" + this.isEquipped + ")";
    }

    public BackgroundItem(int id, String name, int drawableId, int price, boolean isPurchased, boolean isEquipped) {
        Intrinsics.checkNotNullParameter(name, "name");
        this.id = id;
        this.name = name;
        this.drawableId = drawableId;
        this.price = price;
        this.isPurchased = isPurchased;
        this.isEquipped = isEquipped;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ BackgroundItem(int i, String str, int i2, int i3, boolean z, boolean z2, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        boolean z3;
        z = (i4 & 16) != 0 ? false : z;
        if ((i4 & 32) == 0) {
            z3 = z2;
        } else {
            z3 = false;
        }
        this(i, str, i2, i3, z, z3);
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

    /* JADX WARN: Illegal instructions before constructor call */
    public BackgroundItem(Parcel parcel) {
        boolean z;
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
        this(i, str, i2, i3, z2, parcel.readByte() != 0 ? true : z);
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
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* JADX INFO: renamed from: com.example.pet.BackgroundItem$CREATOR, reason: from kotlin metadata */
    /* JADX INFO: compiled from: BackgroundItem.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u001d\u0010\b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¢\u0006\u0002\u0010\f¨\u0006\r"}, d2 = {"Lcom/example/pet/BackgroundItem$CREATOR;", "Landroid/os/Parcelable$Creator;", "Lcom/example/pet/BackgroundItem;", "<init>", "()V", "createFromParcel", "parcel", "Landroid/os/Parcel;", "newArray", "", "size", "", "(I)[Lcom/example/pet/BackgroundItem;", "app_debug"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static final class Companion implements Parcelable.Creator<BackgroundItem> {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BackgroundItem createFromParcel(Parcel parcel) {
            Intrinsics.checkNotNullParameter(parcel, "parcel");
            return new BackgroundItem(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BackgroundItem[] newArray(int size) {
            return new BackgroundItem[size];
        }
    }
}
