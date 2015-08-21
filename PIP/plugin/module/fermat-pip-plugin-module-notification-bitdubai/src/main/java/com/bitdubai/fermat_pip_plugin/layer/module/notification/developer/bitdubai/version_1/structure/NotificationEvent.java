package com.bitdubai.fermat_pip_plugin.layer.module.notification.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;

/**
 * Created by Matias Furszyfer on 2015.08.18..
 */
public class NotificationEvent {


    /**
     * Message
     */
    private String alertTitle;
    private String textTitle;
    private String textBody;

    /**
     *  Screen/activity to show
     */
    private Activities screen;

    /**
     *  Wallet to show
     */
    private String walletPublicKey;

    // ver si pongo una imagen o cargo la imagen de fermat
    //private byte[] image;


    public NotificationEvent() {
    }

    public String getAlertTitle() {
        return alertTitle;
    }

    public void setAlertTitle(String alertTitle) {
        this.alertTitle = alertTitle;
    }

    public String getTextTitle() {
        return textTitle;
    }

    public void setTextTitle(String textTitle) {
        this.textTitle = textTitle;
    }

    public String getTextBody() {
        return textBody;
    }

    public void setTextBody(String textBody) {
        this.textBody = textBody;
    }

    public Activities getScreen() {
        return screen;
    }

    public void setScreen(Activities screen) {
        this.screen = screen;
    }

    public String getWalletPublicKey() {
        return walletPublicKey;
    }

    public void setWalletPublicKey(String walletPublicKey) {
        this.walletPublicKey = walletPublicKey;
    }
}
