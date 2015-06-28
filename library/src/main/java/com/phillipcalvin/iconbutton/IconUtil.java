/*
 * Date: 15-1-8
 * Project: IconButton
 */
package com.phillipcalvin.iconbutton;

import android.widget.Button;

/**
 * @author Huang Haohang (msdx.android@qq.com)
 * Time: 15-1-8 上午9:30
 */
public class IconUtil implements CenterInterface{

    public static void setCenter(Button iconButton, IconConfig config) {
        if (config.center == CENTER_WITHOUT_DRAWABLE) {
            IconUtil.centerWithoutDrawable(iconButton, config);
        } else if (config.center == CENTER_TEXT_ONLY) {
            IconUtil.centerOnlyText(iconButton, config);
        } else {
            IconUtil.allCenter(iconButton, config);
        }
    }

    public static void centerWithoutDrawable(Button iconButton, IconConfig config) {
        int contentWidth = config.iconPadding + config.textWidth;
        int contentLeft = (iconButton.getWidth() - contentWidth) / 2;
        iconButton.setCompoundDrawablePadding(-contentLeft + config.iconPadding);
        switch (config.drawablePosition) {
            case LEFT:
                iconButton.setPadding(contentLeft - config.drawableWidth, 0, 0, 0);
                break;
            case RIGHT:
                iconButton.setPadding(0, 0, contentLeft - config.drawableWidth, 0);
                break;
            default:
                iconButton.setPadding(0, 0, 0, 0);
        }
    }

    public static void centerOnlyText(Button iconButton, IconConfig config) {
        int contentLeft = (iconButton.getWidth() - config.textWidth) / 2;
        iconButton.setCompoundDrawablePadding(-contentLeft + config.iconPadding);
        switch (config.drawablePosition) {
            case LEFT:
                iconButton.setPadding(contentLeft - config.drawableWidth - config.iconPadding, 0, 0, 0);
                break;
            case RIGHT:
                iconButton.setPadding(0, 0, contentLeft - config.drawableWidth - config.iconPadding, 0);
                break;
            default:
                iconButton.setPadding(0, 0, 0, 0);
        }
    }

    public static void allCenter(Button iconButton, IconConfig config) {
        int contentWidth = config.drawableWidth + config.iconPadding + config.textWidth;
        int contentLeft = (iconButton.getWidth() - contentWidth) / 2;
        iconButton.setCompoundDrawablePadding(-contentLeft + config.iconPadding);
        switch (config.drawablePosition) {
            case LEFT:
                iconButton.setPadding(contentLeft, 0, 0, 0);
                break;
            case RIGHT:
                iconButton.setPadding(0, 0, contentLeft, 0);
                break;
            default:
                iconButton.setPadding(0, 0, 0, 0);
        }
    }

}
