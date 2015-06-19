package com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.interfaces;

import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.exceptions.CantGetWalletAddressBookRegistryException;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.crypto_module.wallet_address_book.interfaces.WalletAddressBookManager</code>
 * indicates the functionality of a WalletAddressBookManager.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/06/15.
 * @version 1.0
 */
public interface WalletAddressBookManager {

    WalletAddressBookRegistry getWalletAddressBookRegistry() throws CantGetWalletAddressBookRegistryException;

}
