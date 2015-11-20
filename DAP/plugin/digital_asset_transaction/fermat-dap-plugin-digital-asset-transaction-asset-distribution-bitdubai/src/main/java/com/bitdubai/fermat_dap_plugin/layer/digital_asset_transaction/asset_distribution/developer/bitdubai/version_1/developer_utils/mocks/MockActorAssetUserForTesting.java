package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.developer_utils.mocks;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Genders;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DAPConnectionState;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 24/10/15.
 */
public class MockActorAssetUserForTesting implements ActorAssetUser {

    @Override
    public String getPublicLinkedIdentity() {
        return new ECCKeyPair().getPublicKey();
    }

    @Override
    public String getPublicKey() {
        return new ECCKeyPair().getPublicKey();
    }

    @Override
    public String getName() {
        return "Actor Asset User Patriotic Name";
    }

    @Override
    public String getAge() {
        return "90";
    }

    @Override
    public Genders getGenders() {
        return Genders.MALE;
    }

    @Override
    public DAPConnectionState getDapConnectionState() {
        return DAPConnectionState.CONNECTED_ONLINE;
    }

    /**
     * The method <code>getLocation</code> gives us the Location of the represented Asset user
     *
     * @return the Location of the Asset user
     */
    @Override
    public Location getLocation() {
        return null;
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
    public Double getLocationLatitude() {
        return 24.846565;
    }

    @Override
    public Double getLocationLongitude() {
        return 1.054688;
    }

    @Override
    public byte[] getProfileImage() {
        return new byte[0];
    }

    @Override
    public CryptoAddress getCryptoAddress() {
        CryptoAddress actorUserCryptoAddress=new CryptoAddress("mqBuPbxaxKzni6uTQCyVxK2FRLbcmaDrsV", CryptoCurrency.BITCOIN);
        return actorUserCryptoAddress;
    }
}
