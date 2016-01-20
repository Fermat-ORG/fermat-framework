package com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantCreateMessageSignatureException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.ExposureLevel;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentity;

/**
 * Created by nelson on 22/12/15.
 */
public class CryptoBrokerWalletModuleCryptoBrokerIdentity implements CryptoBrokerIdentity {
    String alias;
    byte[] actorImg;
    String publicKey;

    public CryptoBrokerWalletModuleCryptoBrokerIdentity(String alias) {
        this.alias = alias;
        actorImg = new byte[0];
        this.publicKey = "crypto_broker_public_key";
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public String getPublicKey() {
        return publicKey;
    }

    @Override
    public byte[] getProfileImage() {
        return actorImg;
    }

    @Override
    public void setNewProfileImage(byte[] imageBytes) {

    }

    @Override
    public boolean isPublished() {
        return false;
    }

    @Override
    public ExposureLevel getExposureLevel() {
        return null;
    }

    @Override
    public String createMessageSignature(String message) throws CantCreateMessageSignatureException {
        return null;
    }
}
