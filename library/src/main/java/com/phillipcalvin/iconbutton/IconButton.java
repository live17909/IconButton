package com.phillipcalvin.iconbutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Button;

public class IconButton extends Button {
    private static final int CENTER_ALL = 0;
    private static final int CENTER_WITHOUT_DRAWABLE = 1;
    private static final int CENTER_TEXT_ONLY = 2;

    protected int drawableWidth;
    protected DrawablePositions drawablePosition;
    protected int iconPadding;
    protected int center;

    // Cached to prevent allocation during onLayout
    Rect bounds;

    private enum DrawablePositions {
        NONE,
        LEFT,
        RIGHT
    }

    public IconButton(Context context) {
        super(context);
        bounds = new Rect();
    }

    public IconButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        bounds = new Rect();
        applyAttributes(attrs);
    }

    public IconButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        bounds = new Rect();
        applyAttributes(attrs);
    }

    protected void applyAttributes(AttributeSet attrs) {
        // Slight contortion to prevent allocating in onLayout
        if (null == bounds) {
            bounds = new Rect();
        }

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.IconButton);
        int paddingId = typedArray.getDimensionPixelSize(R.styleable.IconButton_iconPadding, 0);
        int center = typedArray.getInt(R.styleable.IconButton_center, 0);
        setIconPadding(paddingId, center);
        typedArray.recycle();
    }

    public void setIconPadding(int padding, int center) {
        iconPadding = padding;
        this.center = center;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        Paint textPaint = getPaint();
        String text = getText().toString();
        textPaint.getTextBounds(text, 0, text.length(), bounds);

        int textWidth = bounds.width();

        if (center == CENTER_WITHOUT_DRAWABLE) {
            centerWithoutDrawable(textWidth);
        } else if (center == CENTER_TEXT_ONLY) {
            centerOnlyText(textWidth);
        } else {
            allCenter(textWidth);
        }
    }

    private void centerWithoutDrawable(int textWidth) {
        int contentWidth = iconPadding + textWidth;
        int contentLeft = (getWidth() - contentWidth) / 2;
        setCompoundDrawablePadding(-contentLeft + iconPadding);
        switch (drawablePosition) {
            case LEFT:
                setPadding(contentLeft - drawableWidth, 0, 0, 0);
                break;
            case RIGHT:
                setPadding(0, 0, contentLeft - drawableWidth, 0);
                break;
            default:
                setPadding(0, 0, 0, 0);
        }
    }

    private void centerOnlyText(int textWidth) {
        int contentLeft = (getWidth() - textWidth) / 2;
        setCompoundDrawablePadding(-contentLeft + iconPadding);
        switch (drawablePosition) {
            case LEFT:
                setPadding(contentLeft - drawableWidth - iconPadding, 0, 0, 0);
                break;
            case RIGHT:
                setPadding(0, 0, contentLeft - drawableWidth - iconPadding, 0);
                break;
            default:
                setPadding(0, 0, 0, 0);
        }
    }

    private void allCenter(int textWidth) {
        int contentWidth = drawableWidth + iconPadding + textWidth;
        int contentLeft = (getWidth() - contentWidth) / 2;
        setCompoundDrawablePadding(-contentLeft + iconPadding);
        switch (drawablePosition) {
            case LEFT:
                setPadding(contentLeft, 0, 0, 0);
                break;
            case RIGHT:
                setPadding(0, 0, contentLeft, 0);
                break;
            default:
                setPadding(0, 0, 0, 0);
        }
    }

    @Override
    public void setCompoundDrawablesWithIntrinsicBounds(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        if (null != left) {
            drawableWidth = left.getIntrinsicWidth();
            drawablePosition = DrawablePositions.LEFT;
        } else if (null != right) {
            drawableWidth = right.getIntrinsicWidth();
            drawablePosition = DrawablePositions.RIGHT;
        } else {
            drawablePosition = DrawablePositions.NONE;
        }
        requestLayout();
    }
}