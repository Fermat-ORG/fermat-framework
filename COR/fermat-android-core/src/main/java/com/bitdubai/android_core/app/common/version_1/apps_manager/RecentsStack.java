package com.bitdubai.android_core.app.common.version_1.apps_manager;

import com.bitdubai.android_core.app.common.version_1.recents.RecentApp;

import java.util.Stack;

/**
 * Created by Matias Furszyfer on 2016.05.15..
 */
public class RecentsStack extends Stack<RecentApp> {


    public RecentApp getApp(String publickKey) {
        int pos = 0;
        RecentApp recentApp = null;
        while (true) {
            recentApp = elementAt(pos);
            if (recentApp.getPublicKey().equals(publickKey)) {
                break;
            }
            pos++;
        }
        return recentApp;
    }

    /**
     * re order the stack
     *
     * @param appPublicKey
     */

    public void reOrder(String appPublicKey) {
        if (containsKey(appPublicKey)) {
            if (!peek().getPublicKey().equals(appPublicKey)) {
                int appPos = searchApp(appPublicKey);
                RecentApp recentApp = remove(appPos);
                push(recentApp);
            }
        }
    }

    /**
     * If the element is found return the position of the element if is not return -1
     *
     * @param appPublicKey
     * @return
     */

    public int searchApp(String appPublicKey) {
        for (int i = 0; i < elementCount; i++) {
            if (elementAt(i).getPublicKey().equals(appPublicKey)) {
                return i;
            }
        }
        return -1;
    }

    public boolean containsKey(String appPublicKey) {
        return (searchApp(appPublicKey) != -1);
    }
}
