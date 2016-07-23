package com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.interfaces.KeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.GeoFrequency;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantCreateMessageSignatureException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.ExposureLevel;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentity;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentityExtraData;

import java.io.Serializable;


/**
 * Created by jorge on 28-09-2015.
 * Modified by Yordin Alayn 10.09.15
 * Updated by lnacosta (laion.cj91@gmail.com) on 25/11/2015.
 */
public class CryptoBrokerIdentityImpl implements CryptoBrokerIdentity, Serializable {

    private static final int HASH_PRIME_NUMBER_PRODUCT = 7681;
    private static final int HASH_PRIME_NUMBER_ADD = 3581;

    private final String alias;
    private final KeyPair keyPair;
    private byte[] profileImage;
    private ExposureLevel exposureLevel;
    private long accuracy;
    private GeoFrequency frequency;
    private CryptoBrokerIdentityExtraData cryptoBrokerIdentityExtraData;

    public CryptoBrokerIdentityImpl(final String alias,
                                    final KeyPair keyPair,
                                    final byte[] profileImage,
                                    final ExposureLevel exposureLevel,
                                    final long accuracy,
                                    final GeoFrequency frequency) {

        this.alias = alias;
        this.keyPair = keyPair;
        this.profileImage = profileImage;
        this.exposureLevel = exposureLevel;
        this.accuracy = accuracy;
        this.frequency = frequency;
        //Default CryptoBrokerIdentityExtraData
        this.cryptoBrokerIdentityExtraData = new CryptoBrokerIdentityExtraData(
                CryptoCurrency.BITCOIN,
                FiatCurrency.US_DOLLAR,
                "Selling Bitcoin");
    }

    public CryptoBrokerIdentityImpl(
            final String alias,
            final KeyPair keyPair,
            final byte[] profileImage,
            final ExposureLevel exposureLevel,
            final long accuracy,
            final GeoFrequency frequency,
            final CryptoBrokerIdentityExtraData cryptoBrokerIdentityExtraData) {
        this.alias = alias;
        this.keyPair = keyPair;
        this.profileImage = profileImage;
        this.exposureLevel = exposureLevel;
        this.accuracy = accuracy;
        this.frequency = frequency;
        this.cryptoBrokerIdentityExtraData = cryptoBrokerIdentityExtraData;
    }

    @Override
    public String getAlias() {
        return this.alias;
    }

    @Override
    public String getPublicKey() {
        return this.keyPair.getPublicKey();
    }

    @Override
    public byte[] getProfileImage() {
        return this.profileImage;
    }

    @Override
    public void setNewProfileImage(byte[] imageBytes) {
        this.profileImage = imageBytes;
    }

    @Override
    public boolean isPublished() {
        return this.exposureLevel.equals(ExposureLevel.PUBLISH);
    }

    @Override
    public ExposureLevel getExposureLevel() {
        return exposureLevel;
    }

    @Override
    public String createMessageSignature(String message) throws CantCreateMessageSignatureException {
        try {
            return AsymmetricCryptography.createMessageSignature(message, this.keyPair.getPrivateKey());
        } catch (Exception ex) {
            throw new CantCreateMessageSignatureException(CantCreateMessageSignatureException.DEFAULT_MESSAGE, ex, new StringBuilder().append("Message: ").append(message).toString(), "The message could be invalid");
        }
    }

    @Override
    public long getAccuracy() {
        return this.accuracy;
    }

    @Override
    public GeoFrequency getFrequency() {
        return this.frequency;
    }

    public boolean equals(Object o) {
        if (!(o instanceof CryptoBrokerIdentity))
            return false;
        CryptoBrokerIdentity compare = (CryptoBrokerIdentity) o;
        return alias.equals(compare.getAlias()) && keyPair.getPublicKey().equals(compare.getPublicKey());
    }

    @Override
    public int hashCode() {
        int c = 0;
        c += alias.hashCode();
        c += keyPair.hashCode();
        return HASH_PRIME_NUMBER_PRODUCT * HASH_PRIME_NUMBER_ADD + c;
    }

    public CryptoBrokerIdentityExtraData getCryptoBrokerIdentityExtraData() {
        return cryptoBrokerIdentityExtraData;
    }

}
