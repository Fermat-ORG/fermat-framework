package com.bitdubai.fermat.dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version1.structure.actor_asset_distribution_user;

import com.bitdubai.fermat_api.layer.all_definition.enums.Genders;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;

import org.fermat.fermat_dap_api.layer.all_definition.enums.DAPConnectionState;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;

//import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.enums.ContactState;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 05/10/15.
 */
public class MockActorAssetUser implements ActorAssetUser {
    @Override
    public String getActorPublicKey() {
        return "publicKey";
    }

    @Override
    public String getPublicLinkedIdentity() {
        return "publicLinkedIdentity";
    }

    @Override
    public String getName() {
        return "testName";
    }

    @Override
    public long getRegistrationDate() {
        return 0;
    }

    @Override
    public byte[] getProfileImage() {
        return new byte[0];
    }

    @Override
    public long getLastConnectionDate() {
        return 1000;
    }

    @Override
    public DAPConnectionState getDapConnectionState() {
        return DAPConnectionState.CONNECTED_ONLINE;
    }

    //Fix for compilation
    @Override
    public Double getLocationLatitude() {
        return null;
    }

    @Override
    public Double getLocationLongitude() {
        return null;
    }

    @Override
    public Location getLocation() {
        return null;
    }

    @Override
    public Genders getGenders() {
        return null;
    }

    @Override
    public String getAge() {
        return null;
    }

    @Override
    public CryptoAddress getCryptoAddress() {
        return null;
    }


}
