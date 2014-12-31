package com.bitdubai.smartwallet.core.platform.layer.middleware.engine.device.version_1;

import com.bitdubai.smartwallet.core.platform.layer.lowlevel.service.shop.version_1.Shop;
import com.bitdubai.smartwallet.core.platform.layer.bottom.subsystem.os.android.version_1.sql_storage.Database;
import com.bitdubai.smartwallet.core.platform.layer.lowlevel.service.user.version_1.LocalPersonalUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ciencias on 22.12.14.
 */
public class LocalDevice implements Device {
    private List<LocalPersonalUser> mLocalPersonalUsers = new ArrayList<LocalPersonalUser>();
    private Shop[] mShops;

    public LocalDevice () {

        Database database = new Database();

        List<String> LocalPersonalUsersIds = new ArrayList<String>();
        LocalPersonalUsersIds = database.getLocalPersonalUsersIds();

        for (String UserId : LocalPersonalUsersIds ) {
            
            LocalPersonalUser user = new LocalPersonalUser(UserId);
            mLocalPersonalUsers.add(user);
        }
    }

    public List<LocalPersonalUser> getLocalPersonalUsers() {
        return mLocalPersonalUsers;
    }
}
