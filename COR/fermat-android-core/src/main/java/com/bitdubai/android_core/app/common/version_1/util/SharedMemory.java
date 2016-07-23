package com.bitdubai.android_core.app.common.version_1.util;

import com.bitdubai.fermat_api.AppsStatus;

/**
 * Created by mati on 2016.04.20..
 */
public class SharedMemory {

    public static SharedMemory instance;

    public static SharedMemory getInstance() {
        if (instance == null) {
            instance = new SharedMemory();
        }
        return instance;
    }


    /**
     * App Filter Status
     */
    private AppsStatus appsStatus = AppsStatus.ALPHA;

    public void changeAppStatus(AppsStatus appsStatus) {
        this.appsStatus = appsStatus;
    }

    public AppsStatus getAppStatus() {
        return appsStatus;
    }
}
