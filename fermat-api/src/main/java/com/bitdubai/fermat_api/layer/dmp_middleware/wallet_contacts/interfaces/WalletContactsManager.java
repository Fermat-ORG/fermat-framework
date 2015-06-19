package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.*;

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

    WalletContactsRegistry getWalletContactsRegistry() throws CantGetWalletContactRegistryException;

}
