package com.bitdubai.fermat_pip_api.layer.module.android_core.interfaces;

import com.bitdubai.fermat_api.AppsStatus;
import com.bitdubai.fermat_api.layer.modules.interfaces.FermatSettings;

import java.io.Serializable;

/**
 * Created by mati on 2016.02.15..
 */
public class AndroidCoreSettings implements FermatSettings,Serializable {

    AppsStatus appsStatus;
    boolean isHelpEnabled;
    boolean isErrorReportEnabled;

    public AndroidCoreSettings(AppsStatus appsStatus) {
        this.appsStatus = appsStatus;
    }


    public AppsStatus getAppsStatus() {
        return appsStatus;
    }

    public void setAppsStatus(AppsStatus appsStatus) {
        this.appsStatus = appsStatus;
    }

    @Override
    public void setIsPresentationHelpEnabled(boolean b) {
        isHelpEnabled = b;
    }

    public boolean isHelpEnabled() {
        return isHelpEnabled;
    }

    public boolean isErrorReportEnabled() {
        return isErrorReportEnabled;
    }

    public void setIsErrorReportEnabled(boolean isErrorReportEnabled) {
        this.isErrorReportEnabled = isErrorReportEnabled;
    }

    public void setIsHelpEnabled(boolean isHelpEnabled) {
        this.isHelpEnabled = isHelpEnabled;
    }
}
