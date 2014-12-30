package com.bitdubai.smartwallet.core.platform.systemwide.device;

import com.bitdubai.smartwallet.core.platform.innersystem.layer.lowlevel.service.shop.version_1.Shop;
import com.bitdubai.smartwallet.core.platform.systemwide.device.storage.Database;
import com.bitdubai.smartwallet.core.platform.innersystem.layer.lowlevel.service.user.version_1.LocalPersonalUser;

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
