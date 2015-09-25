package com.bitdubai.fermat_api.layer.ccp_network_service.crypto_addressees.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.ccp_network_service.crypto_addressees.interfaces.RequestHandlerWallet</code>
 * list the information of a wallet that can handle the request
 */
public interface RequestHandlerWallet {

    // TODO THIS INFORMATION MUST BE PROVIDED BY THE WALLET MANAGER TO THE WALLET WHEN INTERFACE WHEN IS NEEDED
    /**
     * The method <code>getPublicKey</code> gives us the public key of the represented wallet
     *
     * @return the public key
     */
    String getPublicKey();

    /**
     * The method <code>getWalletName</code> gives us the name of the represented wallet
     *
     * @return the wallet name
     */
    String getWalletName();

    /**
     *
     * @return the category of the wallet
     */
    WalletCategory getWalletCategory();

    /**
     *
     * @return the platform identifier of the wallet
     */
    String getPlatformIdentifier();
}
