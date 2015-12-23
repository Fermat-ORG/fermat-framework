package com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces;

import java.util.List;

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_community.interfaces.CryptoCustomerIdentityInformation</code>
 * provides the method to extract information about an crypto broker.
 *
 * Created by natalia on 16/09/15.
 */

public interface CryptoBrokerInformation {

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
     * The method <code>listCryptoBrokerWallets</code> returns the list of the public crypto broker wallets
     * @return
     */
    List listCryptoBrokerWallets();

}

