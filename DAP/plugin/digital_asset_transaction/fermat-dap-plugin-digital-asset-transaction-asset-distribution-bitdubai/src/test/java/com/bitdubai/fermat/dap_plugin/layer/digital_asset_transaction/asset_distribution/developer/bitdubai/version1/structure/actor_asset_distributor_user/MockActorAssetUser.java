package com.bitdubai.fermat.dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version1.structure.actor_asset_distributor_user;

import com.bitdubai.fermat_api.layer.all_definition.enums.ConnectionState;//quitar
//import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.enums.ContactState;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 05/10/15.
 */
public class MockActorAssetUser implements ActorAssetUser {
    @Override
    public String getPublicKey() {
        return "publicKey";
    }

    @Override
    public String getName() {
        return "testName";
    }

    @Override
    public long getContactRegistrationDate() {
        return 0;
    }

    @Override
    public byte[] getProfileImage() {
        return new byte[0];
    }

    @Override
    public ConnectionState getConnectionState() {
        return null;
    }


}
