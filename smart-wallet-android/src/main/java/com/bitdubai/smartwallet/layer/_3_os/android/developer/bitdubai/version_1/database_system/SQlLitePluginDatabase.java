package com.bitdubai.smartwallet.layer._3_os.android.developer.bitdubai.version_1.database_system;

import com.bitdubai.wallet_platform_api.layer._3_os.PluginDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ciencias on 23.12.14.
 */
public class SQlLitePluginDatabase implements PluginDatabase {

    public  List<String> getLocalPersonalUsersIds() {

        List<String> LocalPersonalUsersIds = new ArrayList<String>();
        LocalPersonalUsersIds.add ("1");

        return LocalPersonalUsersIds;
    }

    @Override
    public void createTable(String tableName) {

    }
}
