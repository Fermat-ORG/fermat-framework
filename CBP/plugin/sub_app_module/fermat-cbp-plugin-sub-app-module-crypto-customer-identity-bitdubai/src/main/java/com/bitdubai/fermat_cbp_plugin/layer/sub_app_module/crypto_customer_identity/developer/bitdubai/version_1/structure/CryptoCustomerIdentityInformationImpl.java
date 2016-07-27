package com.bitdubai.fermat_cbp_plugin.layer.sub_app_module.crypto_customer_identity.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.GeoFrequency;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.interfaces.CryptoCustomerIdentityInformation;

/**
 * Created by angel on 15/10/15.
 */
public class CryptoCustomerIdentityInformationImpl implements CryptoCustomerIdentityInformation {

    private static final int HASH_PRIME_NUMBER_PRODUCT = 7219;
    private static final int HASH_PRIME_NUMBER_ADD = 953;

    private final String alias;
    private final String publicKey;
    private final byte[] profileImage;
    private final boolean published;
    private long accuracy;
    private GeoFrequency frequency;

    public CryptoCustomerIdentityInformationImpl(final String alias, final String publicKey, final byte[] profileImage, final boolean published,
                                                 final long accuracy,
                                                 final GeoFrequency frequency) {
        this.alias = alias;
        this.publicKey = publicKey;
        this.profileImage = profileImage;
        this.published = published;
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
    public boolean isPublished() {
        return published;
    }

    @Override
    public long getAccuracy() {
        return accuracy;
    }

    @Override
    public GeoFrequency getFrequency() {
        return frequency;
    }

    @Override
    public byte[] getProfileImage() {
        return this.profileImage;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CryptoCustomerIdentityInformation))
            return false;
        CryptoCustomerIdentityInformation compare = (CryptoCustomerIdentityInformation) o;
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
