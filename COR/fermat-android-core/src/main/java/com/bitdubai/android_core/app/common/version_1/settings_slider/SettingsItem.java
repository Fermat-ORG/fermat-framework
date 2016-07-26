package com.bitdubai.android_core.app.common.version_1.settings_slider;

/**
 * Created by Matias Furszyfer on 2016.03.25..
 */
public class SettingsItem {

    private SettingsType settingsType;
    private int imgRes;
    private String text;
    private String subText;
    private boolean isBlock;

    public SettingsItem(SettingsType settingsType, int imgRes, String text, String subText) {
        this.imgRes = imgRes;
        this.settingsType = settingsType;
        this.text = text;
        this.subText = subText;
    }

    public int getImgRes() {
        return imgRes;
    }

    public String getText() {
        return text;
    }

    public String getSubText() {
        return subText;
    }

    public SettingsType getSettingsType() {
        return settingsType;
    }

    public void setImageRes(int imageRes) {
        this.imgRes = imageRes;
    }

    public boolean isBlock() {
        return isBlock;
    }

    public void setIsBlock(boolean isBlock) {
        this.isBlock = isBlock;
    }
}
