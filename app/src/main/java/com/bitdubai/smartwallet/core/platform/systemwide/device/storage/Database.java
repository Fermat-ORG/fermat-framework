package com.bitdubai.smartwallet.core.platform.systemwide.device.storage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ciencias on 23.12.14.
 */
public class Database {

    public  List<String> getLocalPersonalUsersIds() {

        List<String> LocalPersonalUsersIds = new ArrayList<String>();
        LocalPersonalUsersIds.add ("1");

        return LocalPersonalUsersIds;
    }
}
