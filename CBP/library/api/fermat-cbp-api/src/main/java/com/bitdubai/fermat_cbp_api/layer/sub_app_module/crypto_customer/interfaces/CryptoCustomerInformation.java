package com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer.interfaces;

/**
 * Created by natalia on 16/09/15.
 */

/**
 * The interface <code>CryptoCustomerInformation</code>
 * provides the method to extract information about an crypto customer.
 */
public interface CryptoCustomerInformation {
    /**
     * The method <code>getPublicKet</code> returns the public key of the represented crypto customer
     * @return the public key of the crypto customer
     */
    public String getPublicKey();

    /**
     * The method <code>getName</code> returns the name of the represented crypto customer
     *
     * @return the name of the crypto customer
     */
    public String getName();

    /**
     * The method <code>getProfileImage</code> returns the profile image of the represented crypto customer
     *
     * @return the profile image
     */
    public byte[] getProfileImage();


}

