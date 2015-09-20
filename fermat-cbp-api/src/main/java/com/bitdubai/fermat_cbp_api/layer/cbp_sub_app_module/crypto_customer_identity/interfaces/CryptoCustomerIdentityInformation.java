package com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_customer_identity.interfaces;

/**
 * Created by natalia on 16/09/15.
 */
/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_customer_identity.interfaces.CryptoCustomerIdentityInformation</code>
 * provides the method to extract information about an crypto Customer identity.
 */
public interface CryptoCustomerIdentityInformation {
    /**
     * The method <code>getPublicKey</code> returns the public key of the represented crypto Customer
     * @return the public key of the crypto Customer
     */
    public String getPublicKey();

    /**
     * The method <code>getName</code> returns the name of the represented crypto Customer
     *
     * @return the name of the crypto Customer
     */
    public String getName();

    /**
     * The method <code>getProfileImage</code> returns the profile image of the represented crypto Customer
     *
     * @return the profile image
     */
    public byte[] getProfileImage();


}

