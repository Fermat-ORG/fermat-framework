package com.bitdubai.fermat.dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version1.structure.mocks;

import com.bitdubai.fermat_api.layer.all_definition.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Genders;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;

/**
 * Created by lcampo on 07/11/15.
 */
public class MockActorAssetIssuer implements ActorAssetIssuer {


    @Override
    public String getPublicKey() {
        return "getPublicKey";
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
    public ConnectionState getConnectionState() {
        return ConnectionState.CONNECTED;
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
