/*
 * Date: 15-1-8
 * Project: IconButton
 */
package com.phillipcalvin.iconbutton;

/**
 * Author: msdx (645079761@qq.com)
 * Time: 15-1-8 上午10:16
 */
public class IconConfig {
    public int center;
    public int textWidth;
    public int iconPadding;
    public int drawableWidth;
    public DrawablePositions drawablePosition;

    public IconConfig(int center, int textWidth, int iconPadding, int drawableWidth, DrawablePositions drawablePosition) {
        this.center = center;
        this.textWidth = textWidth;
        this.iconPadding = iconPadding;
        this.drawableWidth = drawableWidth;
        this.drawablePosition = drawablePosition;
    }
}
