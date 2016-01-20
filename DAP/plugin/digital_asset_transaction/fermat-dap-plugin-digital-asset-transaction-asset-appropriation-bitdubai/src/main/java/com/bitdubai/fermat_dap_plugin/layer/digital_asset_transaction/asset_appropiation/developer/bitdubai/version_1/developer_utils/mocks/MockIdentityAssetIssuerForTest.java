package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_appropiation.developer.bitdubai.version_1.developer_utils.mocks;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuer;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 25/09/15.
 */
public class MockIdentityAssetIssuerForTest implements IdentityAssetIssuer {
    @Override
    public String getAlias() {
        return "Franklin Marcano";
    }

    @Override
    public byte[] getImage() {
        return new byte[0];
    }

    @Override
    public String getPublicKey() {
        return "ASDS-10087982";
    }

    @Override
    public Actors getActorType() {
        return Actors.DAP_ASSET_ISSUER;
    }


    @Override
    public void setNewProfileImage(byte[] newProfileImage) {

    }

    @Override
    public String createMessageSignature(String message) {
        return "signature";
    }
}
