package com.bitdubai.fermat_api.layer.dmp_module;

public class DesktopManagerSettings implements AppManagerSettings {

    private boolean isHelpEnabled;
    private boolean notificationEnabled;



    public boolean getNotificationEnabled() {
        return this.notificationEnabled;
    }

    public void setNotificationEnabled(boolean notificationEnabled) {
        this.notificationEnabled = notificationEnabled;
    }

    @Override
    public void setIsPresentationHelpEnabled(boolean b) {
        isHelpEnabled = b;
    }

    public boolean isHelpEnabled() {
        return isHelpEnabled;
    }
}
