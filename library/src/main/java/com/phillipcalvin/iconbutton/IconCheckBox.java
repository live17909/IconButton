/*
 * Date: 15-1-8
 * Project: IconButton
 */
package com.phillipcalvin.iconbutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.CheckBox;

/**
 * Author: msdx (645079761@qq.com)
 * Time: 15-1-8 上午9:45
 */
public class IconCheckBox extends CheckBox implements CenterInterface {
    protected int drawableWidth;
    protected DrawablePositions drawablePosition;
    protected int iconPadding;
    protected int center;

    // Cached to prevent allocation during onLayout
    Rect bounds;

    public IconCheckBox(Context context) {
        super(context);
        bounds = new Rect();
    }

    public IconCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        bounds = new Rect();
        applyAttributes(attrs);
    }

    public IconCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        bounds = new Rect();
        applyAttributes(attrs);
    }


    protected void applyAttributes(AttributeSet attrs) {
        // Slight contortion to prevent allocating in onLayout
        if (null == bounds) {
            bounds = new Rect();
        }

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.IconButton);
        int padding = typedArray.getDimensionPixelSize(R.styleable.IconButton_iconPadding, 0);
        int center = typedArray.getInt(R.styleable.IconButton_center, CENTER_ALL);
        iconPadding = padding;
        this.center = center;
        typedArray.recycle();
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        requestLayout();
    }

    @Override
    public void append(CharSequence text, int start, int end) {
        super.append(text, start, end);
        requestLayout();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        Paint textPaint = getPaint();
        String text = getText().toString();
        textPaint.getTextBounds(text, 0, text.length(), bounds);

        int textWidth = bounds.width();

        IconConfig config = new IconConfig(center, textWidth, iconPadding, drawableWidth, drawablePosition);
        IconUtil.setCenter(this, config);
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
