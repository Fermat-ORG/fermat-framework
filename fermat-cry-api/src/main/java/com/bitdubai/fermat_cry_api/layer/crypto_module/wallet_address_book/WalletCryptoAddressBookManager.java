package com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book;

import com.bitdubai.fermat_api.layer.all_definition.enums.PlatformWalletType;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.exceptions.CantGetWalletCryptoAddressBookException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.exceptions.CantRegisterWalletCryptoAddressBookException;

import java.util.List;
import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.crypto_module.wallet_address_book.WalletCryptoAddressBookManager</code>
 * indicates the functionality of a WalletCryptoAddressBookManager.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/06/15.
 * @version 1.0
 */
public interface WalletCryptoAddressBookManager {

    WalletCryptoAddressBook getWalletCryptoAddressBookByCryptoAddress(CryptoAddress cryptoAddress) throws CantGetWalletCryptoAddressBookException;

    List<WalletCryptoAddressBook> getAllWalletCryptoAddressBookByWalletId(UUID walletId) throws CantGetWalletCryptoAddressBookException;

    void registerWalletCryptoAddressBook(CryptoAddress cryptoAddress, PlatformWalletType walletType, UUID walletId) throws CantRegisterWalletCryptoAddressBookException;

}
