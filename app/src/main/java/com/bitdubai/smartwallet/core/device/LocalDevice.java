package com.bitdubai.smartwallet.core.device;

import com.bitdubai.smartwallet.core.commerce.shop.Shop;
import com.bitdubai.smartwallet.core.device.storage.Database;
import com.bitdubai.smartwallet.core.system.user.LocalPersonalUser;

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
