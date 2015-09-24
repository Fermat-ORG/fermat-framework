package com.bitdubai.fermat_api.layer.dmp_wallet_module.fiat_over_crypto_wallet.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactRecord;
import com.bitdubai.fermat_api.layer.dmp_request.money_request.interfaces.MoneyRequestInformation;

import java.util.List;
import java.util.UUID;

/**
 * The interface <code>FiatOverCryptoWalletModule</code>
 * provides the methods to the user interface needed to interact with the fiat over crypto wallet.
 */
public interface FiatOverCryptoWalletModule {

    /**
     * Contacts administration related methods
     */

    List<WalletContactRecord> listWalletContacts(String walletPublicKey);// throws CantGetAllWalletContactsException;

    List<WalletContactRecord> listWalletContactsScrolling(String walletPublicKey, Integer max, Integer offset);// throws CantGetAllWalletContactsException;

    WalletContactRecord createWalletContact(CryptoAddress receivedCryptoAddress, String actorName, Actors actorType, ReferenceWallet referenceWallet, UUID walletId);// throws CantCreateWalletContactException;

    void updateWalletContact(UUID contactId, CryptoAddress receivedCryptoAddress, String actorName);// throws CantUpdateWalletContactException;

    void deleteWalletContact(UUID contactId);// throws CantDeleteWalletContactException;

    List<WalletContactRecord> getWalletContactByNameContainsAndWalletId(String actorName, String walletPublicKey);// throws CantGetWalletContactException;

    /**
     * Balance Fragment methods
     */
    long getAvailableBalance(String walletPublicKey);// throws CantGetBalanceException;

    long getBookBalance(String walletPublicKey);//throws CantGetBalanceException;

    /**
     * Transactions related methods
     */
    List<FiatOverCryptoWalletModuleTransaction> getTransactions(int max, int offset, String walletPublicKey);// throws CantListTransactionsException;

    /**
     * Money Request information
     */

    /**
     *
     * @return
     */
    List<CryptoRequestToFiatOverCryptoWallet> getCryptoRequestReceived();
    List<MoneyRequestInformation> getMoneyRequestSent();
    List<MoneyRequestInformation> getMoneyRequestReceived();



    /**
     * Send money methods
     */

    /**
     * The method <code>send</code> initiates a fiat transaction
     *
     * @param fiatAmount                The amount of fiat to send
     * @param destinationAddress        The destination address to send the value
     * @param notes                     The description of the payment
     * @param walletPublicKey           The public key of the wallet sending the payment
     * @param deliveredByActorPublicKey The public key of the actor sending the payment
     * @param deliveredByActorType      The type of the actor sending the payment
     * @param deliveredToActorPublicKey The public key of the actor destination of the payment
     * @param deliveredToActorType      The type of the actor receptor of the payment
     */
    void send(long fiatAmount, CryptoAddress destinationAddress, String notes, String walletPublicKey, String deliveredByActorPublicKey, Actors deliveredByActorType, String deliveredToActorPublicKey, Actors deliveredToActorType);// throws CantSendCryptoException, InsufficientFundsException;

}
