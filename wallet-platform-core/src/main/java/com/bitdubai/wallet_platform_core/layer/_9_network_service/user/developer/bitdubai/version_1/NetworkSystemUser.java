package com.bitdubai.wallet_platform_core.layer._9_network_service.user.developer.bitdubai.version_1;

import com.bitdubai.wallet_platform_api.layer._9_network_service.user.SystemUser;
import com.bitdubai.wallet_platform_api.layer._9_network_service.user.SystemUserStatus;

import java.util.UUID;

/**
 * Created by ciencias on 2/13/15.
 */
public class NetworkSystemUser implements SystemUser {
    @Override
    public UUID getUserId() {
        return null;
    }

    @Override
    public String getUserName() {
        return null;
    }

    @Override
    public int getLastLocation() {
        return 0;
    }

    @Override
    public SystemUserStatus getStatus() {
        return null;
    }

    @Override
    public int getSmallProfilePicture() {
        return 0;
    }

    @Override
    public int getMediumProfilePicture() {
        return 0;
    }

    @Override
    public int getBigProfilePicture() {
        return 0;
    }
}
