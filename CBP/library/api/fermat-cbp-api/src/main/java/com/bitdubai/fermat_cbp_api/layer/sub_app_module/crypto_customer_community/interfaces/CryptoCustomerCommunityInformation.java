package com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces;

/**
 * Created by natalia on 16/09/15.
 */

import java.util.List;

/**
 * The interface <code>CryptoCustomerCommunityInformation</code>
 * provides the method to extract information about a crypto customer.
 */
public interface CryptoCustomerCommunityInformation {
    /**
     * The method <code>getPublicKey</code> returns the public key of the represented crypto broker
     * @return the public key of the crypto broker
     */
    String getPublicKey();

    /**
     * The method <code>getAlias</code> returns the name of the represented crypto broker
     *
     * @return the name of the crypto broker
     */
    String getAlias();

    /**
     * The method <code>getProfileImage</code> returns the profile image of the represented crypto broker
     *
     * @return the profile image
     */
    byte[] getImage();

    /**
     * The method <code>listCryptoBrokerWallets</code> returns the list of the public crypto customer wallets
     * @return
     */
    List listCryptoCustomerWallets();

}

