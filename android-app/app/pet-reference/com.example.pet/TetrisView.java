package com.example.pet;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.InputDeviceCompat;
import com.google.android.material.card.MaterialCardViewHelper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;

/* JADX INFO: compiled from: TetrisView.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0010\u0015\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0005\u0018\u0000 =2\u00020\u0001:\u0001=B'\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0004\b\b\u0010\tJ\u0006\u0010%\u001a\u00020\u001aJ\b\u0010&\u001a\u00020\u001aH\u0002J\u0006\u0010'\u001a\u00020\u001aJ\u0006\u0010(\u001a\u00020\u001aJ\u0006\u0010)\u001a\u00020\u001aJ\u0006\u0010*\u001a\u00020+J/\u0010,\u001a\u00020+2\u0006\u0010-\u001a\u00020\u00072\u0006\u0010.\u001a\u00020\u00072\u0010\b\u0002\u0010/\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000bH\u0002¢\u0006\u0002\u00100J\b\u00101\u001a\u00020\u001aH\u0002J\b\u00102\u001a\u00020\u001aH\u0002J\u0010\u00103\u001a\u00020\u001a2\u0006\u00104\u001a\u000205H\u0014J8\u00106\u001a\u00020\u001a2\u0006\u00104\u001a\u0002052\u0006\u0010-\u001a\u00020\u00072\u0006\u0010.\u001a\u00020\u00072\u0006\u00107\u001a\u00020\u00072\u0006\u00108\u001a\u0002092\u0006\u0010:\u001a\u000209H\u0002J\u001d\u0010;\u001a\b\u0012\u0004\u0012\u00020\f0\u000b*\b\u0012\u0004\u0012\u00020\f0\u000bH\u0002¢\u0006\u0002\u0010<R\u0016\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\rR\u0018\u0010\u000e\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0004\n\u0002\u0010\rR\u000e\u0010\u000f\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R4\u0010\u0018\u001a\u001c\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u001a\u0018\u00010\u0019X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\"\u0010\u001f\u001a\n\u0012\u0004\u0012\u00020\u001a\u0018\u00010 X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$¨\u0006>"}, d2 = {"Lcom/example/pet/TetrisView;", "Landroid/view/View;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "defStyleAttr", "", "<init>", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "board", "", "", "[[I", "currentShape", "currentColor", "currentX", "currentY", "score", "lines", "level", "paint", "Landroid/graphics/Paint;", "gridPaint", "onGameUpdate", "Lkotlin/Function3;", "", "getOnGameUpdate", "()Lkotlin/jvm/functions/Function3;", "setOnGameUpdate", "(Lkotlin/jvm/functions/Function3;)V", "onGameOver", "Lkotlin/Function0;", "getOnGameOver", "()Lkotlin/jvm/functions/Function0;", "setOnGameOver", "(Lkotlin/jvm/functions/Function0;)V", "resetGame", "newPiece", "moveLeft", "moveRight", "rotate", "moveDown", "", "checkCollision", "x", "y", "shape", "(II[[I)Z", "placePiece", "clearLines", "onDraw", "canvas", "Landroid/graphics/Canvas;", "drawCell", TypedValues.Custom.S_COLOR, "cellSize", "", "offsetY", "rotateMatrix", "([[I)[[I", "Companion", "app_debug"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class TetrisView extends View {
    private static final int BOARD_HEIGHT = 20;
    private static final int BOARD_WIDTH = 10;
    private static final int EMPTY = 0;
    private final int[][] board;
    private int currentColor;
    private int[][] currentShape;
    private int currentX;
    private int currentY;
    private final Paint gridPaint;
    private int level;
    private int lines;
    private Function0<Unit> onGameOver;
    private Function3<? super Integer, ? super Integer, ? super Integer, Unit> onGameUpdate;
    private final Paint paint;
    private int score;
    private static final int[][][] SHAPES = {new int[][]{new int[]{1, 1, 1, 1}}, new int[][]{new int[]{1, 1}, new int[]{1, 1}}, new int[][]{new int[]{0, 1, 0}, new int[]{1, 1, 1}}, new int[][]{new int[]{1, 1, 0}, new int[]{0, 1, 1}}, new int[][]{new int[]{0, 1, 1}, new int[]{1, 1, 0}}, new int[][]{new int[]{1, 0, 0}, new int[]{1, 1, 1}}, new int[][]{new int[]{0, 0, 1}, new int[]{1, 1, 1}}};
    private static final int[] COLORS = {-16711681, InputDeviceCompat.SOURCE_ANY, -65281, SupportMenu.CATEGORY_MASK, -16711936, -16776961, Color.parseColor("#FFA500")};

    public /* synthetic */ TetrisView(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public TetrisView(Context context) {
        this(context, null, 0, 6, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public TetrisView(Context context, AttributeSet attrs) {
        this(context, attrs, 0, 4, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TetrisView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Intrinsics.checkNotNullParameter(context, "context");
        int[][] iArr = new int[20][];
        for (int i = 0; i < 20; i++) {
            int[] iArr2 = new int[10];
            for (int i2 = 0; i2 < 10; i2++) {
                iArr2[i2] = 0;
            }
            iArr[i] = iArr2;
        }
        this.board = iArr;
        this.currentColor = -16711681;
        this.currentX = 4;
        this.level = 1;
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(2.0f);
        this.paint = paint;
        Paint paint2 = new Paint();
        paint2.setColor(-7829368);
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setStrokeWidth(1.0f);
        this.gridPaint = paint2;
        newPiece();
    }

    public final Function3<Integer, Integer, Integer, Unit> getOnGameUpdate() {
        return this.onGameUpdate;
    }

    public final void setOnGameUpdate(Function3<? super Integer, ? super Integer, ? super Integer, Unit> function3) {
        this.onGameUpdate = function3;
    }

    public final Function0<Unit> getOnGameOver() {
        return this.onGameOver;
    }

    public final void setOnGameOver(Function0<Unit> function0) {
        this.onGameOver = function0;
    }

    public final void resetGame() {
        for (int y = 0; y < 20; y++) {
            for (int x = 0; x < 10; x++) {
                this.board[y][x] = 0;
            }
        }
        this.score = 0;
        this.lines = 0;
        this.level = 1;
        newPiece();
        Function3<? super Integer, ? super Integer, ? super Integer, Unit> function3 = this.onGameUpdate;
        if (function3 != null) {
            function3.invoke(Integer.valueOf(this.score), Integer.valueOf(this.lines), Integer.valueOf(this.level));
        }
        invalidate();
    }

    private final void newPiece() {
        int shapeIndex = Random.INSTANCE.nextInt(SHAPES.length);
        Object[] objArr = SHAPES[shapeIndex];
        Collection arrayList = new ArrayList(objArr.length);
        for (Object obj : objArr) {
            arrayList.add((int[]) ((int[]) obj).clone());
        }
        this.currentShape = (int[][]) ((List) arrayList).toArray(new int[0][]);
        this.currentColor = COLORS[shapeIndex];
        int[][] iArr = this.currentShape;
        Intrinsics.checkNotNull(iArr);
        this.currentX = 5 - (iArr[0].length / 2);
        this.currentY = 0;
        if (checkCollision$default(this, this.currentX, this.currentY, null, 4, null)) {
            Function0<Unit> function0 = this.onGameOver;
            if (function0 != null) {
                function0.invoke();
            }
            resetGame();
        }
    }

    public final void moveLeft() {
        if (!checkCollision$default(this, this.currentX - 1, this.currentY, null, 4, null)) {
            this.currentX--;
            invalidate();
        }
    }

    public final void moveRight() {
        if (!checkCollision$default(this, this.currentX + 1, this.currentY, null, 4, null)) {
            this.currentX++;
            invalidate();
        }
    }

    public final void rotate() {
        int[][] iArr = this.currentShape;
        Intrinsics.checkNotNull(iArr);
        int[][] rotated = rotateMatrix(iArr);
        if (!checkCollision(this.currentX, this.currentY, rotated)) {
            this.currentShape = rotated;
            invalidate();
        }
    }

    public final boolean moveDown() {
        if (!checkCollision$default(this, this.currentX, this.currentY + 1, null, 4, null)) {
            this.currentY++;
            invalidate();
            return true;
        }
        placePiece();
        clearLines();
        newPiece();
        invalidate();
        return false;
    }

    static /* synthetic */ boolean checkCollision$default(TetrisView tetrisView, int i, int i2, int[][] iArr, int i3, Object obj) {
        if ((i3 & 4) != 0) {
            iArr = tetrisView.currentShape;
        }
        return tetrisView.checkCollision(i, i2, iArr);
    }

    private final boolean checkCollision(int x, int y, int[][] shape) {
        Object[] objArr;
        int i;
        if (shape == null) {
            return false;
        }
        int[][] iArr = shape;
        int i2 = 0;
        int i3 = 0;
        int length = iArr.length;
        int i4 = 0;
        while (i4 < length) {
            int i5 = i3 + 1;
            int[] iArr2 = (int[]) iArr[i4];
            int i6 = 0;
            int length2 = iArr2.length;
            int i7 = 0;
            while (i7 < length2) {
                int i8 = i6 + 1;
                if (iArr2[i7] == 0) {
                    objArr = iArr;
                    i = i2;
                } else {
                    int i9 = x + i6;
                    objArr = iArr;
                    int i10 = y + i3;
                    if (i9 >= 0) {
                        i = i2;
                        if (i9 < 10 && i10 < 20) {
                            if (i10 >= 0 && this.board[i10][i9] != 0) {
                                return true;
                            }
                        }
                    }
                    return true;
                }
                i7++;
                i6 = i8;
                iArr = objArr;
                i2 = i;
            }
            i4++;
            i3 = i5;
            i2 = i2;
        }
        return false;
    }

    private final void placePiece() {
        Object[] objArr;
        Object[] objArr2 = this.currentShape;
        if (objArr2 == null) {
            return;
        }
        Object[] objArr3 = objArr2;
        int i = 0;
        int length = objArr3.length;
        int i2 = 0;
        while (i2 < length) {
            int i3 = i + 1;
            int[] iArr = (int[]) objArr3[i2];
            int i4 = 0;
            int length2 = iArr.length;
            int i5 = 0;
            while (i5 < length2) {
                int i6 = i4 + 1;
                if (iArr[i5] == 0) {
                    objArr = objArr3;
                } else {
                    int i7 = this.currentY + i;
                    objArr = objArr3;
                    int i8 = this.currentX + i4;
                    if (i7 >= 0) {
                        this.board[i7][i8] = this.currentColor;
                    }
                }
                i5++;
                i4 = i6;
                objArr3 = objArr;
            }
            i2++;
            i = i3;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void clearLines() {
        int i;
        int linesCleared = 0;
        int y = 19;
        while (true) {
            boolean z = true;
            i = 0;
            if (y < 0) {
                break;
            }
            int[] iArr = this.board[y];
            int length = iArr.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    break;
                }
                if ((iArr[i2] != 0 ? 1 : 0) == 0) {
                    z = false;
                    break;
                }
                i2++;
            }
            if (z) {
                for (int ny = y; ny > 0; ny--) {
                    this.board[ny] = this.board[ny - 1].clone();
                }
                int[][] iArr2 = this.board;
                int[] iArr3 = new int[10];
                for (int i3 = 0; i3 < 10; i3++) {
                    iArr3[i3] = 0;
                }
                iArr2[0] = iArr3;
                linesCleared++;
            } else {
                y--;
            }
        }
        if (linesCleared > 0) {
            this.lines += linesCleared;
            int i4 = this.score;
            switch (linesCleared) {
                case 1:
                    i = this.level * 100;
                    break;
                case 2:
                    i = this.level * MaterialCardViewHelper.DEFAULT_FADE_ANIM_DURATION;
                    break;
                case 3:
                    i = this.level * 500;
                    break;
                case 4:
                    i = this.level * 800;
                    break;
            }
            this.score = i4 + i;
            this.level = (this.lines / 10) + 1;
            Function3<? super Integer, ? super Integer, ? super Integer, Unit> function3 = this.onGameUpdate;
            if (function3 != null) {
                function3.invoke(Integer.valueOf(this.score), Integer.valueOf(this.lines), Integer.valueOf(this.level));
            }
        }
    }

    /* JADX WARN: Incorrect condition in loop: B:39:0x0134 */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void onDraw(android.graphics.Canvas r29) {
        /*
            Method dump skipped, instruction units count: 454
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.example.pet.TetrisView.onDraw(android.graphics.Canvas):void");
    }

    private final void drawCell(Canvas canvas, int x, int y, int color, float cellSize, float offsetY) {
        float left = x * cellSize;
        float top = offsetY + (y * cellSize);
        float right = left + cellSize;
        float bottom = top + cellSize;
        this.paint.setColor(color);
        canvas.drawRect(left, top, right, bottom, this.paint);
        this.paint.setColor(Color.argb(100, 255, 255, 255));
        float f = 2;
        canvas.drawRect(left, top, right - f, top + f, this.paint);
        canvas.drawRect(left, top, left + f, bottom - f, this.paint);
        this.paint.setColor(Color.argb(100, 0, 0, 0));
        canvas.drawRect(left + f, bottom - f, right, bottom, this.paint);
        canvas.drawRect(right - f, top + f, right, bottom, this.paint);
    }

    private final int[][] rotateMatrix(int[][] $this$rotateMatrix) {
        int rows = $this$rotateMatrix.length;
        int cols = $this$rotateMatrix[0].length;
        int[][] rotated = new int[cols][];
        for (int i = 0; i < cols; i++) {
            rotated[i] = new int[rows];
        }
        for (int i2 = 0; i2 < rows; i2++) {
            for (int j = 0; j < cols; j++) {
                rotated[j][(rows - 1) - i2] = $this$rotateMatrix[i2][j];
            }
        }
        return rotated;
    }
}
