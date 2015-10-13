package com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.layer.all_definition.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.all_definition.enums.Genders;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;

import java.util.Date;

//import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.enums.ContactState;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 05/10/15.
 */
public class MockActorAssetUser implements ActorAssetUser {

    private String publickeys ="publickey";


    public MockActorAssetUser(){
        super();
        Date today = new Date();
        this.publickeys=this.publickeys+today.getTime();

    }


    @Override
    public String getPublicKey() {
        return this.publickeys;
    }

    @Override
    public String getName() {
        return "testName"+this.publickeys;
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
        return ConnectionState.PENDING_REMOTELY_ACCEPTANCE;
    }

    @Override
    public Location getLocation() {
        return null;
    }

    @Override
    public Genders getGender() {
        return null;
    }

    @Override
    public String getAge() {
        return null;
    }

    @Override
    public String getCryptoAddress() {
        return null;
    }


}
