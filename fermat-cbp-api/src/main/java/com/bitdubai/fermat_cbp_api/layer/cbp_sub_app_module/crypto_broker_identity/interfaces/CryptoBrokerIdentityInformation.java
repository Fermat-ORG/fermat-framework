package com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_identity.interfaces;

/**
 * Created by natalia on 16/09/15.
 */
/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityInformation</code>
 * provides the method to extract information about an crypto broker identity.
 */
public interface CryptoBrokerIdentityInformation {
    /**
     * The method <code>getPublicKey</code> returns the public key of the represented crypto broker
     * @return the public key of the crypto broker
     */
    public String getPublicKey();

    /**
     * The method <code>getName</code> returns the name of the represented crypto broker
     *
     * @return the name of the crypto broker
     */
    public String getName();

    /**
     * The method <code>getProfileImage</code> returns the profile image of the represented crypto broker
     *
     * @return the profile image
     */
    public byte[] getProfileImage();


}

