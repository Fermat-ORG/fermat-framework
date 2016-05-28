package com.bitdubai.fermat.dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version1.structure.mocks;

import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;

import org.fermat.fermat_dap_api.layer.all_definition.enums.DAPConnectionState;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;

/**
 * Created by lcampo on 07/11/15.
 */
public class MockActorAssetIssuer implements ActorAssetIssuer {


    @Override
    public String getActorPublicKey() {
        return "getActorPublicKey";
    }

    @Override
    public String getName() {
        return "Luis Campo";
    }

    @Override
    public long getRegistrationDate() {
        return 0;
    }

    @Override
    public long getLastConnectionDate() {
        return 0;
    }

    @Override
    public DAPConnectionState getDapConnectionState() {
        return DAPConnectionState.CONNECTED_ONLINE;
    }

    @Override
    public String getDescription() {
        return "getDescription";
    }

    @Override
    public Location getLocation() {
        return null;
    }

    @Override
    public Double getLocationLatitude() {
        return 10.5;
    }

    @Override
    public Double getLocationLongitude() {
        return 10.6;
    }

    @Override
    public byte[] getProfileImage() {
        return new byte[0];
    }

}
