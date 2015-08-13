package com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.fiat_over_crypto_wallet.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactRecord;

import java.util.List;
import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.fiat_over_crypto_wallet.interfaces.FiatOverCryptoWalletModule</code>
 * provides the methods to the user interface needed to interact with the fiat over crypto wallet.
 */
public interface FiatOverCryptoWalletModule {

    /**
     * Contacts administration related methods
     */

    List<WalletContactRecord> listWalletContacts(UUID walletId);// throws CantGetAllWalletContactsException;

    List<WalletContactRecord> listWalletContactsScrolling(UUID walletId, Integer max, Integer offset);// throws CantGetAllWalletContactsException;

    WalletContactRecord createWalletContact(CryptoAddress receivedCryptoAddress, String actorName, Actors actorType, ReferenceWallet referenceWallet, UUID walletId);// throws CantCreateWalletContactException;

    void updateWalletContact(UUID contactId, CryptoAddress receivedCryptoAddress, String actorName);// throws CantUpdateWalletContactException;

    void deleteWalletContact(UUID contactId);// throws CantDeleteWalletContactException;

    List<WalletContactRecord> getWalletContactByNameContainsAndWalletId(String actorName, UUID walletId);// throws CantGetWalletContactException;

    boolean isValidAddress(CryptoAddress cryptoAddress);

    /**
     * Balance Fragment methods
     */
    long getAvailableBalance(UUID walletId);// throws CantGetBalanceException;

    long getBookBalance(UUID walletId);//throws CantGetBalanceException;

    /**
     * Transactions Fragment methods
     */
    List<FiatOverCryptoWalletModuleTransaction> getTransactions(int max, int offset, UUID walletId);// throws CantGetTransactionsException;

    /**
     * Receive methods
     */
    CryptoAddress requestAddress(UUID deliveredByActorId, Actors deliveredByActorType, String deliveredToActorName, Actors deliveredToActorType, ReferenceWallet referenceWallet, UUID walletId);// throws CantRequestCryptoAddressException;

    CryptoAddress requestAddress(UUID deliveredByActorId, Actors deliveredByActorType, UUID deliveredToActorId, Actors deliveredToActorType, ReferenceWallet referenceWallet, UUID walletId);// throws CantRequestCryptoAddressException;

    /**
     * Send money methods
     */
    void send(long cryptoAmount, CryptoAddress destinationAddress, String notes, UUID walletID, UUID deliveredByActorId, Actors deliveredByActorType, UUID deliveredToActorId, Actors deliveredToActorType);// throws CantSendCryptoException, InsufficientFundsException;

}
