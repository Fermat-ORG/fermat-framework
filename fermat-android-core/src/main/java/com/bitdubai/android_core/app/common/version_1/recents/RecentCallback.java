package com.bitdubai.android_core.app.common.version_1.recents;

/**
 * Created by mati on 2016.03.04..
 */
public interface RecentCallback<M> {

    void onItemClick(M model);

    void onFirstElementAdded();

}
