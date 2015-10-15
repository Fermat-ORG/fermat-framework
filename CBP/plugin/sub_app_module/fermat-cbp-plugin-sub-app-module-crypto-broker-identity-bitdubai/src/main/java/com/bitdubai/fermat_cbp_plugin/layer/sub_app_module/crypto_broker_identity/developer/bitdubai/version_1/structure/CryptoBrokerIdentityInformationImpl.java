package com.bitdubai.fermat_cbp_plugin.layer.sub_app_module.crypto_broker_identity.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityInformation;

/**
 * Created by jorge on 14-10-2015.
 */
public class CryptoBrokerIdentityInformationImpl implements CryptoBrokerIdentityInformation {

    private final String publicKey;
    private final String name;
    private final byte[] profileImage;

    public CryptoBrokerIdentityInformationImpl(final String publicKey, final String name,  final byte[] profileImage){
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
