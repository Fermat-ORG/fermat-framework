package com.bitdubai.fermat_pip_plugin.layer.sub_app_module.notification.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_pip_api.layer.module.notification.interfaces.NotificationEvent;

/**
 * Created by Matias Furszyfer on 2015.08.18..
 */
public class Notification implements NotificationEvent {


    /**
     * Message
     */
    private String alertTitle;
    private String textTitle;
    private String textBody;

    /**
     * Image
     */
    private byte[] image;

    /**
     * Screen/activity to show
     */
    private Activities screen;

    /**
     * Wallet to show
     */
    private String walletPublicKey;

    /**
     * Notification type
     */
    private String notificationType;

    // ver si pongo una imagen o cargo la imagen de fermat
    //private byte[] image;


    public Notification() {
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

    @Override
    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public void setWalletPublicKey(String walletPublicKey) {
        this.walletPublicKey = walletPublicKey;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
