package com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_network_service.intra_user.enums.IntraUserStatus;
import com.bitdubai.fermat_api.layer.dmp_network_service.intra_user.interfaces.IntraUser;

/**
 * Created by natalia on 03/09/15.
 */
public class IntraUserNetworkService  implements IntraUser {

    @Override
    public String getAddress() {
        return null;
    }

    @Override
    public String getPublicKey() {
        return null;
    }

    @Override
    public byte[] getProfileImage() {
        return new byte[0];
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getLastLocation() {
        return null;
    }

    @Override
    public IntraUserStatus getStatus() {
        return null;
    }

    @Override
    public String getSmallProfilePicture() {
        return null;
    }

    @Override
    public String getMediumProfilePicture() {
        return null;
    }

    @Override
    public String getBigProfilePicture() {
        return null;
    }

    @Override
    public Long getCreatedTime() {
        return null;
    }

    @Override
    public Long getUpdateTime() {
        return null;
    }
}
