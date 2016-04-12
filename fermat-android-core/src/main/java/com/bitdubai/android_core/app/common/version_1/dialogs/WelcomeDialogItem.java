package com.bitdubai.android_core.app.common.version_1.dialogs;

/**
 * Created by mati on 2016.04.06..
 */
public class WelcomeDialogItem {

    private String title;
    private String firstParagraphs;
    private String secondParagraphs;
    private String thirdParagraphs;
    private int img;

    public WelcomeDialogItem(String title, String firstParagraphs, String secondParagraphs, String thirdParagraphs,int imgRes) {
        this.title = title;
        this.firstParagraphs = firstParagraphs;
        this.secondParagraphs = secondParagraphs;
        this.thirdParagraphs = thirdParagraphs;
        this.img = imgRes;
    }

    public String getTitle() {
        return title;
    }

    public String getFirstParagraphs() {
        return firstParagraphs;
    }

    public String getSecondParagraphs() {
        return secondParagraphs;
    }

    public String getThirdParagraphs() {
        return thirdParagraphs;
    }

    public int getImg() {
        return img;
    }
}
