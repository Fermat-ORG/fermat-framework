package com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_1.test;

import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_issuer.exceptions.CantSingMessageException;
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
    public String getPublicKey() {
        return "ASDS-10087982";
    }

    @Override
    public byte[] getProfileImage() {
        return new byte[0];
    }

    @Override
    public void setNewProfileImage(byte[] newProfileImage) {

    }

    @Override
    public String createMessageSignature(String mensage) {
        return "signature";
    }
}
