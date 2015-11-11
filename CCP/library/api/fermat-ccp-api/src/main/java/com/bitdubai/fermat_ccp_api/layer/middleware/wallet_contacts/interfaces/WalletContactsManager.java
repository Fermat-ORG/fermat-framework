package com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.interfaces;

import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantGetWalletContactRegistryException;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.middleware.wallet_contacts.interfaces.WalletContactsManager</code>
 * indicates the functionality of a WalletContactsManager
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 19/06/15.
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface WalletContactsManager {

    /**
     * Through the method <code>getWalletContactsRegistry</code> you can get an instance of Wallet Contacts Registry.
     *
     * @return an instance of WalletContactsRegistry.
     *
     * @throws CantGetWalletContactRegistryException is thrown if something goes wrong.
     */
    WalletContactsRegistry getWalletContactsRegistry() throws CantGetWalletContactRegistryException;

}
