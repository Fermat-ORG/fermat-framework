package com.bitdubai.android_core.app.common.version_1.provisory;

import com.bitdubai.fermat_api.AppsStatus;

/**
 * Created by mati on 2016.02.22..
 */
public class FermatDesktopManager implements DesktopManager{


    @Override
    public String getAppName() {
        return "desktop";
    }

    @Override
    public String getAppPublicKey() {
        return "main_desktop";
    }

    @Override
    public AppsStatus getAppStatus() {
        return AppsStatus.RELEASE;
    }
}
