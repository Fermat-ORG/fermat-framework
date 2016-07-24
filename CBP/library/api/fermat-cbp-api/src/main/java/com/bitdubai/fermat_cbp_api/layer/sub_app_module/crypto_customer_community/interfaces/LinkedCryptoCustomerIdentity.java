package com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces;


/**
 * Created by Alejandro Bicelis on 2/2/2016.
 */

import java.io.Serializable;
import java.util.List;
import java.util.UUID;


public interface LinkedCryptoCustomerIdentity extends Serializable {

    /**
     * The method <code>getPublicKey</code> returns the UUID of the connected actor to this identity
     */
    UUID getConnectionId();

    /**
     * The method <code>getPublicKey</code> returns the public key of the represented crypto broker
     *
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
     *
     * @return
     */
    List listCryptoCustomerWallets();

}

