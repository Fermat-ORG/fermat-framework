package com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer.interfaces;

/**
 * Created by natalia on 16/09/15.
 */

import java.io.Serializable;

/**
 * The interface <code>CryptoCustomerCommunityInformation</code>
 * provides the method to extract information about an crypto customer.
 */
public interface CryptoCustomerInformation extends Serializable {
    /**
     * The method <code>getPublicKet</code> returns the public key of the represented crypto customer
     *
     * @return the public key of the crypto customer
     */
    String getPublicKey();

    /**
     * The method <code>getName</code> returns the name of the represented crypto customer
     *
     * @return the name of the crypto customer
     */
    String getName();

    /**
     * The method <code>getProfileImage</code> returns the profile image of the represented crypto customer
     *
     * @return the profile image
     */
    byte[] getProfileImage();


}

