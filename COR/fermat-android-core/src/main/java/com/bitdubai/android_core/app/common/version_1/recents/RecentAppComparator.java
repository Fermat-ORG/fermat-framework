package com.bitdubai.android_core.app.common.version_1.recents;

import java.util.Comparator;

/**
 * Created by mati on 2016.03.04..
 */
public class RecentAppComparator implements Comparator<RecentApp>{

    @Override
    public int compare(RecentApp lhs, RecentApp rhs) {
        return (lhs.getTaskStackPosition()<rhs.getTaskStackPosition()) ? -1:1;
    }
}
