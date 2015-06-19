package com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.PlatformWalletType;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantGetTransactionsException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.BitcoinTransaction;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.*;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactRecord;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions.CantGetBalanceException;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions.CantRequestCryptoAddressException;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions.CantSendCryptoException;

import java.util.List;
import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_dmp_plugin.layer.niche_wallet_type.crypto_wallet.NicheWalletTypeCryptoWalletManager</code>
 * haves all consumable methods from the plugin
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 10/06/15.
 * @version 1.0
 */
public interface NicheWalletTypeCryptoWalletManager {

    /**
     * Contacts Fragment methods...
     */

    List<WalletContactRecord> listWalletContacts(UUID walletId) throws CantGetAllWalletContactsException;

    List<WalletContactRecord> listWalletContactsScrolling(UUID walletId, Integer max, Integer offset) throws CantGetAllWalletContactsException;

    WalletContactRecord createWalletContact(CryptoAddress receivedCryptoAddress, String actorName, Actors actorType, PlatformWalletType platformWalletType, UUID walletId) throws CantCreateWalletContactException;

    void updateWalletContact(UUID contactId, CryptoAddress receivedCryptoAddress, String actorName) throws CantUpdateWalletContactException;

    void deleteWalletContact(UUID contactId) throws CantDeleteWalletContactException;

    WalletContactRecord getWalletContactByContainsLikeAndWalletId(String actorName, UUID walletId) throws CantGetWalletContactException;

    /**
     * Balance Fragment methods
     */
    long getBalance(UUID walletId) throws CantGetBalanceException;

    /**
     * Transactions Fragment methods
     */
    List<BitcoinTransaction> getTransactions(int max, int offset, UUID walletId) throws CantGetTransactionsException;

    /**
     * Receive methods
     */
    CryptoAddress requestAddress(String actorName, Actors actorType, PlatformWalletType platformWalletType, UUID walletId) throws CantRequestCryptoAddressException;

    /**
     * Send money methods
     */
    void send(UUID walletID, CryptoAddress destinationAddress, long cryptoAmount) throws CantSendCryptoException;
}
