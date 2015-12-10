package com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces;

/**
 * Created by natalia on 16/09/15.
 */

import java.util.List;

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_community.interfaces.CryptoCustomerIdentityInformation</code>
 * provides the method to extract information about an crypto broker.
 */
public interface CryptoBrokerInformation {
    /**
     * The method <code>getPublicKet</code> returns the public key of the represented crypto broker
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

    /**
     * The method <code>getCryptoBrokerWallets</code> returns the list of the public crypto broker wallets
     * @return
     */
    public List getCryptoBrokerWallets();
}

