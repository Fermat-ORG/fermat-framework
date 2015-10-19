package com.bitdubai.fermat_cbp_plugin.layer.sub_app_module.crypto_customer_identity.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_customer_identity.interfaces.CryptoCustomerIdentityInformation;

/**
 * Created by angel on 15/10/15.
 */
public class CryptoCustomerIdentityInformationImpl implements CryptoCustomerIdentityInformation {

    private final String publicKey;
    private final String name;
    private final byte[] profileImage;

    public CryptoCustomerIdentityInformationImpl(final String publicKey, final String name,  final byte[] profileImage){
        this.publicKey = publicKey;
        this.name = name;
        this.profileImage = profileImage;
    }

    @Override
    public String getPublicKey() {
        return publicKey;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public byte[] getProfileImage() {
        return profileImage;
    }
}
