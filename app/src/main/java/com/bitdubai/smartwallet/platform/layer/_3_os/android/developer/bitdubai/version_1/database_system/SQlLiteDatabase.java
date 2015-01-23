package com.bitdubai.smartwallet.platform.layer._3_os.android.developer.bitdubai.version_1.database_system;

import com.bitdubai.smartwallet.platform.layer._3_os.Database;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ciencias on 23.12.14.
 */
public class SQlLiteDatabase implements Database {

    public  List<String> getLocalPersonalUsersIds() {

        List<String> LocalPersonalUsersIds = new ArrayList<String>();
        LocalPersonalUsersIds.add ("1");

        return LocalPersonalUsersIds;
    }
}
