package com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.GeoFrequency;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantCreateMessageSignatureException;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.ExposureLevel;

import java.io.Serializable;


/**
 * Created by nelson on 24/11/15.
 */
public class CryptoCustomerWalletModuleActorIdentityImpl implements ActorIdentity, Serializable {

    private String publicKey;
    private String alias;
    private byte[] img;

    public CryptoCustomerWalletModuleActorIdentityImpl(String alias, byte[] img) {
        this.publicKey = "54as65d4a8sd4ds8fv2vr3as2df6a85";
        this.alias = alias;
        this.img = img;
    }

    public CryptoCustomerWalletModuleActorIdentityImpl(String publicKey, String alias, byte[] img) {
        this.publicKey = publicKey;
        this.alias = alias;
        this.img = img;
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

    @Override
    public long getAccuracy() {
        return 0;
    }

    @Override
    public GeoFrequency getFrequency() {
        return null;
    }
}
