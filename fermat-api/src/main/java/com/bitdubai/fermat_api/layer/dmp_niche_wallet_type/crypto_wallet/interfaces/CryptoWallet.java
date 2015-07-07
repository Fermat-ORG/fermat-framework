package com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.PlatformWalletType;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletTransactionRecord;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactRecord;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions.*;

import java.util.List;
import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_dmp_plugin.layer.niche_wallet_type.crypto_wallet.CryptoWallet</code>
 * haves all consumable methods from the plugin
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 10/06/15.
 * @version 1.0
 */
public interface CryptoWallet {

    /**
     * Contacts Fragment methods...
     */

    List<WalletContactRecord> listWalletContacts(UUID walletId) throws CantGetAllWalletContactsException;

    List<WalletContactRecord> listWalletContactsScrolling(UUID walletId, Integer max, Integer offset) throws CantGetAllWalletContactsException;

    WalletContactRecord createWalletContact(CryptoAddress receivedCryptoAddress, String actorName, Actors actorType, PlatformWalletType platformWalletType, UUID walletId) throws CantCreateWalletContactException;

    void updateWalletContact(UUID contactId, CryptoAddress receivedCryptoAddress, String actorName) throws CantUpdateWalletContactException;

    void deleteWalletContact(UUID contactId) throws CantDeleteWalletContactException;

    List<WalletContactRecord> getWalletContactByNameContainsAndWalletId(String actorName, UUID walletId) throws CantGetWalletContactException;

    boolean isValidAddress(CryptoAddress cryptoAddress);

    /**
     * Balance Fragment methods
     */
    long getBalance(UUID walletId) throws CantGetBalanceException;

    /**
     * Transactions Fragment methods
     */
    List<BitcoinWalletTransactionRecord> getTransactions(int max, int offset, UUID walletId) throws CantGetTransactionsException;

    /**
     * Receive methods
     */
    CryptoAddress requestAddress(String actorName, Actors actorType, PlatformWalletType platformWalletType, UUID walletId) throws CantRequestCryptoAddressException;

    /**
     * Send money methods
     */
    void send(long cryptoAmount, CryptoAddress destinationAddress, UUID walletID) throws CantSendCryptoException, InsufficientFundsException;
}
