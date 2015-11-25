package com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantCreateMessageSignatureException;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.ExposureLevel;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentity;

/**
 * Created by nelson on 24/11/15.
 */
public class CryptoCustomerWalletModuleCryptoBrokerIdentity implements CryptoBrokerIdentity {

    private String publicKey;
    private String alias;
    private byte[] img;


    public CryptoCustomerWalletModuleCryptoBrokerIdentity(String alias, byte[] img, String publicKey) {
        this.alias = alias;
        this.img = img;
        this.publicKey = publicKey;
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
        return img;
    }

    @Override
    public void setNewProfileImage(byte[] imageBytes) {

    }

    @Override
    public ExposureLevel getExposureLevel() {
        return ExposureLevel.PUBLISH;
    }

    @Override
    public boolean isPublished() {
        return true;
    }

    @Override
    public String createMessageSignature(String message) throws CantCreateMessageSignatureException {
        return null;
    }
}
