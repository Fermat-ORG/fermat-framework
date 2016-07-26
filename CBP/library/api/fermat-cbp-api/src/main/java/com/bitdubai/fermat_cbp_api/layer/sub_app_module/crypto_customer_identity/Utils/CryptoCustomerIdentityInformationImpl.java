package com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.Utils;

import com.bitdubai.fermat_api.layer.all_definition.enums.GeoFrequency;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.ExposureLevel;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.interfaces.CryptoCustomerIdentityInformation;


/**
 * Created by jorge on 14-10-2015.
 * Updated by lnacosta (laion.cj91@gmail.com) on 25/11/2015.
 */
public class CryptoCustomerIdentityInformationImpl implements CryptoCustomerIdentityInformation {

    private static final int HASH_PRIME_NUMBER_PRODUCT = 3307;
    private static final int HASH_PRIME_NUMBER_ADD = 4153;

    private final String alias;
    private final String publicKey;
    private final byte[] profileImage;
    private final ExposureLevel exposureLevel;
    private long accuracy;
    private GeoFrequency frequency;


    public CryptoCustomerIdentityInformationImpl(final String alias,
                                                 final String publicKey,
                                                 final byte[] profileImage,
                                                 final ExposureLevel exposureLevel,
                                                 final long accuracy,
                                                 final GeoFrequency frequency) {
        this.alias = alias;
        this.publicKey = publicKey;
        this.profileImage = profileImage;
        this.exposureLevel = exposureLevel;
        this.accuracy = accuracy;
        this.frequency = frequency;
    }

    public CryptoCustomerIdentityInformationImpl(CryptoCustomerIdentityInformation identityInfo, int accuracy, GeoFrequency frequency) {
        this.alias = identityInfo.getAlias();
        this.publicKey = identityInfo.getPublicKey();
        this.profileImage = identityInfo.getProfileImage();
        this.exposureLevel = identityInfo.isPublished() ? ExposureLevel.PUBLISH : ExposureLevel.HIDE;
        this.accuracy = accuracy;
        this.frequency = frequency;
    }

    @Override
    public String getAlias() {
        return this.alias;
    }

    @Override
    public String getPublicKey() {
        return this.publicKey;
    }

    @Override
    public byte[] getProfileImage() {
        return this.profileImage;
    }

    @Override
    public boolean isPublished() {
        return exposureLevel.equals(ExposureLevel.PUBLISH);
    }

    @Override
    public long getAccuracy() {
        return accuracy;
    }

    @Override
    public GeoFrequency getFrequency() {
        return frequency;
    }

    public boolean equals(Object o) {
        if (!(o instanceof CryptoBrokerIdentityInformation))
            return false;
        CryptoBrokerIdentityInformation compare = (CryptoBrokerIdentityInformation) o;
        return alias.equals(compare.getAlias()) && this.publicKey.equals(compare.getPublicKey());
    }

    @Override
    public int hashCode() {
        int c = 0;
        c += alias.hashCode();
        c += publicKey.hashCode();
        return HASH_PRIME_NUMBER_PRODUCT * HASH_PRIME_NUMBER_ADD + c;
    }
}
