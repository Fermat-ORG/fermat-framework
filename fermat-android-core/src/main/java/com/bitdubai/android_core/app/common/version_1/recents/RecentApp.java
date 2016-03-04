package com.bitdubai.android_core.app.common.version_1.recents;

import com.bitdubai.fermat_api.AppsStatus;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.FermatAppType;

/**
 * Created by mati on 2016.03.03..
 */
public class RecentApp {

    private String publicKey;
    private String name;
    private AppsStatus appsStatus;
    private FermatAppType fermatAppType;
    private int backgroundColor;

    public RecentApp(String publicKey, int color) {
        this.publicKey = publicKey;
        this.backgroundColor = color;
    }

    public RecentApp(String publicKey, String name, AppsStatus appsStatus, FermatAppType fermatAppType) {
        this.publicKey = publicKey;
        this.name = name;
        this.appsStatus = appsStatus;
        this.fermatAppType = fermatAppType;
    }

    public RecentApp(String appPublickKey) {
        this.publicKey = appPublickKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RecentApp recentApp = (RecentApp) o;

        return publicKey.equals(recentApp.publicKey);

    }

    @Override
    public int hashCode() {
        return publicKey.hashCode();
    }
}
