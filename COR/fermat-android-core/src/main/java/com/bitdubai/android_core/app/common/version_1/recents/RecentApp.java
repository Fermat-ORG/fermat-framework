package com.bitdubai.android_core.app.common.version_1.recents;

import com.bitdubai.fermat_android_api.engine.FermatRecentApp;
import com.bitdubai.fermat_api.layer.all_definition.runtime.FermatApp;

import java.io.Serializable;

/**
 * Created by mati on 2016.03.03..
 */
public class RecentApp implements FermatRecentApp, Serializable {

    private String publicKey;
    private FermatApp fermatApp;

    private int taskStackPosition;

    public RecentApp(String publicKey, FermatApp fermatApp, int taskStackPosition) {
        this.publicKey = publicKey;
        this.fermatApp = fermatApp;
        this.taskStackPosition = taskStackPosition;
    }

    public RecentApp(String appPublickKey) {
        this.publicKey = appPublickKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public FermatApp getFermatApp() {
        return fermatApp;
    }

    public int getTaskStackPosition() {
        return taskStackPosition;
    }

    public void setTaskStackPosition(int taskStackPosition) {
        this.taskStackPosition = taskStackPosition;
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
