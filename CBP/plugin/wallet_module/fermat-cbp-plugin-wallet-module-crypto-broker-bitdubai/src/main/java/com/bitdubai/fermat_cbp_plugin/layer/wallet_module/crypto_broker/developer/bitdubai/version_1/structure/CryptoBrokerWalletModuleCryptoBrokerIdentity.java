package com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.GeoFrequency;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantCreateMessageSignatureException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.ExposureLevel;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentity;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentityExtraData;


/**
 * Created by nelsonalfo on 13/07/16.
 */
public class CryptoBrokerWalletModuleCryptoBrokerIdentity implements CryptoBrokerIdentity {

    private CryptoBrokerIdentityExtraData extraData;
    private String alias;
    private String publicKey;
    private byte[] profileImage;
    private ExposureLevel exposureLevel;
    private long accuracy;
    private GeoFrequency frequency;

    public CryptoBrokerWalletModuleCryptoBrokerIdentity(CryptoBrokerIdentity associatedIdentity) {
        extraData = associatedIdentity.getCryptoBrokerIdentityExtraData();
        alias = associatedIdentity.getAlias();
        publicKey = associatedIdentity.getPublicKey();
        profileImage = associatedIdentity.getProfileImage();
        exposureLevel = associatedIdentity.getExposureLevel();
        accuracy = associatedIdentity.getAccuracy();
        frequency = associatedIdentity.getFrequency();
    }

    public CryptoBrokerWalletModuleCryptoBrokerIdentity(String publicKey,
                                                        String alias,
                                                        byte[] profileImage,
                                                        ExposureLevel exposureLevel,
                                                        GeoFrequency frequency,
                                                        long accuracy,
                                                        Currency merchandise,
                                                        Currency paymentCurrency,
                                                        String extraText) {
        this.accuracy = accuracy;
        this.alias = alias;
        this.exposureLevel = exposureLevel;
        this.extraData = new CryptoBrokerIdentityExtraData(merchandise, paymentCurrency, extraText);
        this.frequency = frequency;
        this.profileImage = profileImage;
        this.publicKey = publicKey;
    }

    public CryptoBrokerWalletModuleCryptoBrokerIdentity(String publicKey,
                                                        String alias,
                                                        byte[] profileImage,
                                                        ExposureLevel exposureLevel,
                                                        GeoFrequency frequency,
                                                        long accuracy) {
        this.accuracy = accuracy;
        this.alias = alias;
        this.exposureLevel = exposureLevel;
        this.extraData = null;
        this.frequency = frequency;
        this.profileImage = profileImage;
        this.publicKey = publicKey;
    }

    public void setExtraData(Currency merchandise, Currency paymentCurrency, String extraText) {
        this.extraData = new CryptoBrokerIdentityExtraData(merchandise, paymentCurrency, extraText);
    }

    @Override
    public CryptoBrokerIdentityExtraData getCryptoBrokerIdentityExtraData() {
        return extraData;
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
        return profileImage;
    }

    @Override
    public void setNewProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }

    @Override
    public boolean isPublished() {
        return false;
    }

    @Override
    public ExposureLevel getExposureLevel() {
        return exposureLevel;
    }

    @Override
    public String createMessageSignature(String message) throws CantCreateMessageSignatureException {
        return null;
    }

    @Override
    public long getAccuracy() {
        return accuracy;
    }

    @Override
    public GeoFrequency getFrequency() {
        return frequency;
    }
}
