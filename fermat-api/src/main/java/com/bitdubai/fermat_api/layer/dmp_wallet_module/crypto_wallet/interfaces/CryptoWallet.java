package com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exceptions.*;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactRecord;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.CryptoWallet</code>
 * haves all consumable methods from the plugin
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 10/06/15.
 * @version 1.0
 */
public interface CryptoWallet extends Serializable{

    /**
     * Contacts Fragment methods...
     */

    /**
     * List all wallet contact related to an specific wallet.
     *
     * @param walletPublicKey publick key of the wallet in which we are working.
     * @return a list of instances of wallet contact records
     * @throws CantGetAllWalletContactsException if something goes wrong
     */
    List<WalletContactRecord> listWalletContacts(String walletPublicKey) throws CantGetAllWalletContactsException;

    /**
     * List all wallet contact related to an specific wallet.
     *
     * @param walletPublicKey public key of the wallet in which we are working.
     * @param max quantity of instance you want to return
     * @param offset the point of start in the list you're trying to bring.
     * @return a list of instances of wallet contact records
     * @throws CantGetAllWalletContactsException if something goes wrong
     */
    List<WalletContactRecord> listWalletContactsScrolling(String walletPublicKey, Integer max, Integer offset) throws CantGetAllWalletContactsException;

    /**
     * Create a new contact for an specific wallet
     *
     * @param receivedCryptoAddress the crypto address of the contact
     * @param actorName the actor name or alias for the person we're adding like contact
     * @param actorType type of actor that we're adding
     * @param referenceWallet type of reference wallet
     * @param walletPublicKey public key of the wallet in which we are working
     * @return an instance of the created publick key
     * @throws CantCreateWalletContactException if something goes wrong
     */
    WalletContactRecord createWalletContact(CryptoAddress receivedCryptoAddress,
                                            String actorName,
                                            Actors actorType,
                                            ReferenceWallet referenceWallet,
                                            String walletPublicKey) throws CantCreateWalletContactException;

    /**
     * Create a new contact with a photo for an specific wallet
     *
     * @param receivedCryptoAddress the crypto address of the contact
     * @param actorName the actor name or alias for the person we're adding like contact
     * @param actorType type of actor that we're adding
     * @param referenceWallet type of reference wallet
     * @param walletPublicKey public key of the wallet in which we are working
     * @param photo bite array with photo information
     * @return an instance of the created publick key
     * @throws CantCreateWalletContactException if something goes wrong
     */
    WalletContactRecord createWalletContact(CryptoAddress receivedCryptoAddress,
                                            String actorName, Actors actorType,
                                            ReferenceWallet referenceWallet,
                                            String walletPublicKey,
                                            byte[] photo) throws CantCreateWalletContactException;

    /**
     * updates the photo of an actor
     *
     * @param actorId actor's id
     * @param actor type
     * @param photo byte array with photo information
     * @throws CantUpdateWalletContactException
     */
    void updateContactPhoto(UUID actorId,
                            Actors actor,
                            byte[] photo) throws CantUpdateWalletContactException;

    void updateWalletContact(UUID contactId,
                             CryptoAddress receivedCryptoAddress,
                             String actorName) throws CantUpdateWalletContactException;

    /**
     * deletes a contact having in count the contact id
     *
     * @param contactId specific id of the contact that you're trying to delete
     * @throws CantDeleteWalletContactException
     */
    void deleteWalletContact(UUID contactId) throws CantDeleteWalletContactException;

    /**
     * find a wallet contact having in count its id
     *
     * @param contactId specific id of the contact that you're trying to find
     * @return
     * @throws CantFindWalletContactException
     * @throws WalletContactNotFoundException
     */
    WalletContactRecord findWalletContactById(UUID contactId) throws CantFindWalletContactException, WalletContactNotFoundException;

    List<WalletContactRecord> getWalletContactByNameContainsAndWalletPublicKey(String actorName,
                                                                               String walletPublicKey) throws CantGetWalletContactException;

    boolean isValidAddress(CryptoAddress cryptoAddress);

    /**
     * Balance Fragment methods
     */
    long getAvailableBalance(String walletPublicKey) throws CantGetBalanceException;

    long getBookBalance(String walletPublicKey)throws CantGetBalanceException; ;

    /**
     * Transactions Fragment methods
     */
    List<CryptoWalletTransaction> getTransactions(int max,
                                                  int offset,
                                                  String walletPublicKey) throws CantGetTransactionsException;

    /**
     * Receive methods
     */
    CryptoAddress requestAddress(UUID deliveredByActorId,
                                 Actors deliveredByActorType,
                                 String deliveredToActorName,
                                 Actors deliveredToActorType,
                                 ReferenceWallet referenceWallet,
                                 String walletPublicKey) throws CantRequestCryptoAddressException;

    CryptoAddress requestAddress(UUID deliveredByActorId,
                                 Actors deliveredByActorType,
                                 UUID deliveredToActorId,
                                 Actors deliveredToActorType,
                                 ReferenceWallet referenceWallet,
                                 String walletPublicKey) throws CantRequestCryptoAddressException;

    /**
     * Send money methods
     */
    void send(long cryptoAmount,
              CryptoAddress destinationAddress,
              String notes, String walletPublicKey,
              UUID deliveredByActorId,
              Actors deliveredByActorType,
              UUID deliveredToActorId,
              Actors deliveredToActorType) throws CantSendCryptoException, InsufficientFundsException;

}
