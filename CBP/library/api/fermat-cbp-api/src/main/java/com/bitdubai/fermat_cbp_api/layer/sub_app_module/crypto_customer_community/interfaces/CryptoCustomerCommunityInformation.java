package com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces;

/**
 * Created by natalia on 16/09/15.
 */

import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * The interface <code>CryptoCustomerCommunityInformation</code>
 * provides the method to extract information about a crypto customer.
 */
public interface CryptoCustomerCommunityInformation extends Serializable{
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

    /**
     * The method <code>getConnectionState</code> returns the Connection State Status
     * @return ConnectionState object
     */
    ConnectionState getConnectionState();

    /**
     * The method <code>getConnectionId</code> returns the Connection UUID this actor has with the selected actor
     * @return UUID object
     */
    UUID getConnectionId();
}

