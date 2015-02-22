package com.bitdubai.fermat_core.layer._10_network_service.intra_user.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.layer._10_network_service.intra_user.IntraUser;
import com.bitdubai.fermat_api.layer._10_network_service.intra_user.IntraUserStatus;

import java.util.UUID;

/**
 * Created by ciencias on 2/13/15.
 */
public class NetworkIntraUser implements IntraUser {
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
    public IntraUserStatus getStatus() {
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
