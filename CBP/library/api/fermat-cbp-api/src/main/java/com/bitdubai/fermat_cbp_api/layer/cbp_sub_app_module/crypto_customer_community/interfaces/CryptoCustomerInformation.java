package com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_customer_community.interfaces;

/**
 * Created by natalia on 16/09/15.
 */

import java.util.List;

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_customer_community.interfaces.CryptoCustomerInformation</code>
 * provides the method to extract information about a crypto customer.
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

