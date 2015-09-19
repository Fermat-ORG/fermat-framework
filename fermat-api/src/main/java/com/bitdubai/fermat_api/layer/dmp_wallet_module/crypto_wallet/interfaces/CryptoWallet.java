package com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.VaultType;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.BalanceType;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exceptions.*;

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
public interface CryptoWallet extends Serializable {

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
    List<CryptoWalletWalletContact> listWalletContacts(String walletPublicKey) throws CantGetAllWalletContactsException;

    /**
     * List all wallet contact related to an specific wallet.
     *
     * @param walletPublicKey public key of the wallet in which we are working.
     * @param max quantity of instance you want to return
     * @param offset the point of start in the list you're trying to bring.
     * @return a list of instances of wallet contact records
     * @throws CantGetAllWalletContactsException if something goes wrong
     */
    List<CryptoWalletWalletContact> listWalletContactsScrolling(String walletPublicKey, Integer max, Integer offset) throws CantGetAllWalletContactsException;

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
    CryptoWalletWalletContact createWalletContact(CryptoAddress receivedCryptoAddress,
                                                  String actorName,
                                                  Actors actorType,
                                                  ReferenceWallet referenceWallet,
                                                  String walletPublicKey) throws CantCreateWalletContactException, ContactNameAlreadyExistsException;

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
     * @throws ContactNameAlreadyExistsException if the name of the contact already exists
     */
    CryptoWalletWalletContact createWalletContactWithPhoto(CryptoAddress receivedCryptoAddress,
                                                           String actorName, Actors actorType,
                                                           ReferenceWallet referenceWallet,
                                                           String walletPublicKey,
                                                           byte[] photo) throws CantCreateWalletContactException, ContactNameAlreadyExistsException;

    /**
     * updates the photo of an actor
     *
     * @param actorPublicKey actor's public key
     * @param actor type
     * @param photo byte array with photo information
     * @throws CantUpdateWalletContactException
     */
    void updateContactPhoto(String actorPublicKey,
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
     * @return instance of a crypto wallet contact
     * @throws CantFindWalletContactException
     * @throws WalletContactNotFoundException
     */
    CryptoWalletWalletContact findWalletContactById(UUID contactId) throws CantFindWalletContactException, WalletContactNotFoundException;

    boolean isValidAddress(CryptoAddress cryptoAddress);

    /**
     * Receive methods
     */
    CryptoAddress requestAddressToKnownUser(String deliveredByActorPublicKey,
                                            Actors deliveredByActorType,
                                            String deliveredToActorPublicKey,
                                            Actors deliveredToActorType,
                                            Platforms platform,
                                            VaultType vaultType,
                                            String vaultIdentifier,
                                            String walletPublicKey,
                                            ReferenceWallet walletType) throws CantRequestCryptoAddressException;

    CryptoAddress requestAddressToNewExtraUser(String deliveredByActorPublicKey,
                                               Actors deliveredByActorType,
                                               String deliveredToActorName,
                                               Platforms platform,
                                               VaultType vaultType,
                                               String vaultIdentifier,
                                               String walletPublicKey,
                                               ReferenceWallet walletType) throws CantRequestCryptoAddressException;

    /**
     * Send money methods
     */
    void send(long cryptoAmount,
              CryptoAddress destinationAddress,
              String notes, String walletPublicKey,
              String deliveredByActorPublicKey,
              Actors deliveredByActorType,
              String deliveredToActorPublicKey,
              Actors deliveredToActorType) throws CantSendCryptoException, InsufficientFundsException;


    //TODO:  LEON ponele lo que necesites de arriba, me creo los metodos y los hardcodeo
    //TODO:  MALO TEAM FOREVER
    //TODO:  VAMOS RACING CARAJO.


    /**
     * Throw the method <code>getBalance</code> you can get the balance of the wallet, having i count the type of balance that you need.
     *
     * @param balanceType type of balance that you need
     * @param walletPublicKey public key of the wallet which you're working with.
     * @return the balance of the wallet in long format.
     * @throws CantGetBalanceException if something goes wrong
     */
    long getBalance(BalanceType balanceType,
                    String walletPublicKey) throws CantGetBalanceException;

    /**
     * Throw the method <code>getTransactions</code> you cant get all the transactions for an specific balance type.
     *
     * @param balanceType type of balance that you need
     * @param walletPublicKey of the wallet which you're working with.
     * @param max quantity of instance you want to return
     * @param offset the point of start in the list you're trying to bring.
     * @return a list of crypto wallet transactions (enriched crypto transactions).
     * @throws CantListTransactionsException if something goes wrong.
     */
    List<CryptoWalletTransaction> getTransactions(BalanceType balanceType,
                                                  String walletPublicKey,
                                                  int max,
                                                  int offset) throws CantListTransactionsException;

    /**
     * Throw the method <code>listTransactionByActor</code> you cant get all the transactions related with an specific actor.
     *
     * @param balanceType type of balance that you need
     * @param walletPublicKey of the wallet which you're working with.
     * @param actorPublicKey of the actor from which you need the transactions.
     * @param max quantity of instance you want to return
     * @param offset the point of start in the list you're trying to bring.
     * @return a list of crypto wallet transactions (enriched crypto transactions).
     * @throws CantListTransactionsException if something goes wrong.
     */
    List<CryptoWalletTransaction> listTransactionsByActor(BalanceType balanceType,
                                                          String walletPublicKey,
                                                          String actorPublicKey,
                                                          int max,
                                                          int offset) throws CantListTransactionsException;
    /**
     * Throw the method <code>getActorTransactionHistory</code> you can get the transaction history of an specific actor.
     *
     * @param balanceType type of balance that you need
     * @param walletPublicKey public key of the wallet in where you're working.
     * @param actorPublicKey public key of the actor that you're trying to find.
     * @return an instance of ActorTransactionSummary
     * @throws CantGetActorTransactionHistoryException if something goes wrong.
     */
    ActorTransactionSummary getActorTransactionHistory(BalanceType balanceType,
                                                       String walletPublicKey,
                                                       String actorPublicKey) throws CantGetActorTransactionHistoryException;


    // aca solo devolveme la ultima transaccion que igualmente viene con el actor pegado
    List<CryptoWalletTransaction>  listContactOrdererByLastSendTransaction();

    // aca solo devolveme la ultima transaccion que igualmente viene con el actor pegado
    List<CryptoWalletTransaction>  listContactOrdererByLastReceiveTransaction();

    List<PaymentRequest> listSentPaymentRequest();

    List<PaymentRequest> listReceivedPaymentRequest();

}
