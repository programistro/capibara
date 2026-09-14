package com.example.pet;

import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;

/* JADX INFO: compiled from: AccessoryPosition.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003¢\u0006\u0004\b\u0007\u0010\bJ\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J1\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\n¨\u0006\u001a"}, d2 = {"Lcom/example/pet/AccessoryPosition;", "", "widthPercent", "", "heightPercent", "offsetXPercent", "offsetYPercent", "<init>", "(FFFF)V", "getWidthPercent", "()F", "getHeightPercent", "getOffsetXPercent", "getOffsetYPercent", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "", "toString", "", "app_debug"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final /* data */ class AccessoryPosition {
    private final float heightPercent;
    private final float offsetXPercent;
    private final float offsetYPercent;
    private final float widthPercent;

    public static /* synthetic */ AccessoryPosition copy$default(AccessoryPosition accessoryPosition, float f, float f2, float f3, float f4, int i, Object obj) {
        if ((i & 1) != 0) {
            f = accessoryPosition.widthPercent;
        }
        if ((i & 2) != 0) {
            f2 = accessoryPosition.heightPercent;
        }
        if ((i & 4) != 0) {
            f3 = accessoryPosition.offsetXPercent;
        }
        if ((i & 8) != 0) {
            f4 = accessoryPosition.offsetYPercent;
        }
        return accessoryPosition.copy(f, f2, f3, f4);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final float getWidthPercent() {
        return this.widthPercent;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final float getHeightPercent() {
        return this.heightPercent;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final float getOffsetXPercent() {
        return this.offsetXPercent;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final float getOffsetYPercent() {
        return this.offsetYPercent;
    }

    public final AccessoryPosition copy(float widthPercent, float heightPercent, float offsetXPercent, float offsetYPercent) {
        return new AccessoryPosition(widthPercent, heightPercent, offsetXPercent, offsetYPercent);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AccessoryPosition)) {
            return false;
        }
        AccessoryPosition accessoryPosition = (AccessoryPosition) other;
        return Float.compare(this.widthPercent, accessoryPosition.widthPercent) == 0 && Float.compare(this.heightPercent, accessoryPosition.heightPercent) == 0 && Float.compare(this.offsetXPercent, accessoryPosition.offsetXPercent) == 0 && Float.compare(this.offsetYPercent, accessoryPosition.offsetYPercent) == 0;
    }

    public int hashCode() {
        return (((((Float.hashCode(this.widthPercent) * 31) + Float.hashCode(this.heightPercent)) * 31) + Float.hashCode(this.offsetXPercent)) * 31) + Float.hashCode(this.offsetYPercent);
    }

    public String toString() {
        return "AccessoryPosition(widthPercent=" + this.widthPercent + ", heightPercent=" + this.heightPercent + ", offsetXPercent=" + this.offsetXPercent + ", offsetYPercent=" + this.offsetYPercent + ")";
    }

    public AccessoryPosition(float widthPercent, float heightPercent, float offsetXPercent, float offsetYPercent) {
        this.widthPercent = widthPercent;
        this.heightPercent = heightPercent;
        this.offsetXPercent = offsetXPercent;
        this.offsetYPercent = offsetYPercent;
    }

    public final float getWidthPercent() {
        return this.widthPercent;
    }

    public final float getHeightPercent() {
        return this.heightPercent;
    }

    public final float getOffsetXPercent() {
        return this.offsetXPercent;
    }

    public final float getOffsetYPercent() {
        return this.offsetYPercent;
    }
}
