package com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.VaultType;
import com.bitdubai.fermat_api.layer.all_definition.identities.ActiveIdentity;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_wallet_user.interfaces.IntraWalletUser;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.*;

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
     * List all wallet contact related to an specific wallet.
     *
     * @param walletPublicKey public key of the wallet in which we are working.
     * @param intraUserLoggedInPublicKey public key of the intra user identity to use wallet.
     *
     * @return a list of instances of wallet contact records
     *
     * @throws CantGetAllWalletContactsException if something goes wrong
     */
    List<CryptoWalletWalletContact> listWalletContacts(String walletPublicKey, String intraUserLoggedInPublicKey) throws CantGetAllWalletContactsException;


    /**
     * List  wallet contact and intra user connections related to an specific wallet.
     *
     * @param walletPublicKey
     * @param intraUserLoggedInPublicKey
     * @return
     * @throws CantGetAllWalletContactsException
     */
    List<CryptoWalletWalletContact> listAllActorContactsAndConnections(String walletPublicKey,String intraUserLoggedInPublicKey) throws CantGetAllWalletContactsException;

    /**
     * List all wallet contact related to an specific wallet.
     *
     * @param walletPublicKey public key of the wallet in which we are working.
    * @param intraUserLoggedInPublicKey public key of the wallet intra user identity.
     * @param max             quantity of instance you want to return
     * @param offset          the point of start in the list you're trying to bring.
     *
     * @return a list of instances of wallet contact records
     *
     * @throws CantGetAllWalletContactsException if something goes wrong
     */
    List<CryptoWalletWalletContact> listWalletContactsScrolling(String  walletPublicKey,
                                                                String intraUserLoggedInPublicKey,
                                                                Integer max,
                                                                Integer offset) throws CantGetAllWalletContactsException;

    /**
     * Throw the method <code>listAllIntraUserConnections</code> you can get all the connections of the intra user selected.
     *
     * @param intraUserSelectedPublicKey the public key of the intra user that the user select.
     * @param walletPublicKey            public key of the wallet in which we are working.
     * @param max                        quantity of instance you want to return
     * @param offset                     the point of start in the list you're trying to bring.
     *
     * @return a list of crypto wallet intra user actors
     *
     * @throws CantGetAllIntraUserConnectionsException if something goes wrong.
     */
    List<CryptoWalletIntraUserActor> listAllIntraUserConnections(String  intraUserSelectedPublicKey,
                                                                 String  walletPublicKey,
                                                                 Integer max,
                                                                 Integer offset) throws CantGetAllIntraUserConnectionsException;

    /**
     * Create a new contact for an specific wallet
     *
     * @param receivedCryptoAddress the crypto address of the contact
     * @param actorAlias            the actor alias for the person we're adding like contact
     * @param actorFirstName        string containing actor first name
     * @param actorLastName         string containing actor last name
     * @param actorType             type of actor that we're adding
     * @param walletPublicKey       public key of the wallet in which we are working
     *
     * @return an instance of the created public key
     *
     * @throws CantCreateWalletContactException if something goes wrong
     */
    CryptoWalletWalletContact createWalletContact(CryptoAddress receivedCryptoAddress,
                                                  String        actorAlias,
                                                  String        actorFirstName,
                                                  String        actorLastName,
                                                  Actors        actorType,
                                                  String        walletPublicKey) throws CantCreateWalletContactException, ContactNameAlreadyExistsException;

    /**
     * Convert a intra user connection to a new wallet contact
     *
     * @param actorAlias
     * @param actorConnectedType
     * @param actorConnectedPublicKey
     * @param actorPhoto
     * @param actorWalletType
     * @param identityWalletPublicKey
     * @param walletPublicKey
     * @param walletCryptoCurrency
     * @param blockchainNetworkType
     * @return
     * @throws CantCreateWalletContactException
     * @throws ContactNameAlreadyExistsException
     */
    CryptoWalletWalletContact convertConnectionToContact( String        actorAlias,
                                                          Actors        actorConnectedType,
                                                          String        actorConnectedPublicKey,
                                                          byte[]        actorPhoto,
                                                          Actors        actorWalletType ,
                                                          String        identityWalletPublicKey,
                                                          String        walletPublicKey,
                                                          CryptoCurrency walletCryptoCurrency,
                                                          BlockchainNetworkType blockchainNetworkType) throws CantCreateWalletContactException, ContactNameAlreadyExistsException;
        ;


    /**
     * Create a new contact with a photo for an specific wallet
     *
     * @param receivedCryptoAddress the crypto address of the contact
     * @param actorAlias            the actor alias for the person we're adding like contact
     * @param actorFirstName        string containing actor first name
     * @param actorLastName         string containing actor last name
     * @param actorType             type of actor that we're adding
     * @param walletPublicKey       public key of the wallet in which we are working
     * @param photo                 bite array with photo information
     *
     * @return an instance of the created wallet contact
     *
     * @throws CantCreateWalletContactException if something goes wrong
     * @throws ContactNameAlreadyExistsException if the name of the contact already exists
     */
    CryptoWalletWalletContact createWalletContactWithPhoto(CryptoAddress receivedCryptoAddress,
                                                           String        actorAlias,
                                                           String        actorFirstName,
                                                           String        actorLastName,
                                                           Actors        actorType,
                                                           String        walletPublicKey,
                                                           byte[]        photo) throws CantCreateWalletContactException, ContactNameAlreadyExistsException;

    /**
     * Throw the method <code>addIntraUserActorLikeContact</code> you can add an intra user connection like contact
     *
     * @param intraUserPublicKey the public key of the actor that you want to add.
     * @param alias              the actor name or alias for the person we're adding like contact
     * @param actorFirstName     string containing actor first name
     * @param actorLastName      string containing actor last name
     * @param walletPublicKey    public key of the wallet in which we are working
     *
     * @return an instance of the created wallet contact
     *
     * @throws CantCreateWalletContactException if something goes wrong.
     */
    CryptoWalletWalletContact addIntraUserActorLikeContact(String intraUserPublicKey,
                                                           String alias,
                                                           String actorFirstName,
                                                           String actorLastName,
                                                           String walletPublicKey) throws CantCreateWalletContactException;

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
     * @param intraUserLoggedInPublicKey public key of the wallet intra user identity.
     * @return instance of a crypto wallet contact
     * @throws CantFindWalletContactException
     * @throws WalletContactNotFoundException
     */
    CryptoWalletWalletContact findWalletContactById(UUID contactId, String intraUserLoggedInPublicKey) throws CantFindWalletContactException, WalletContactNotFoundException;

    /**
     * Throw the method <code>isValidAddress</code> you can validate in the specific vault if a specific crypto address is valid.
     *
     * @param cryptoAddress to validate
     * @return boolean value, true if positive, false if negative.
     */
    boolean isValidAddress(CryptoAddress cryptoAddress);
    // TODO ADD BLOCKCHAIN CRYPTO NETWORK ENUM (TO VALIDATE WITH THE SPECIFIC NETWORK).


    CryptoAddress requestAddressToKnownUser(String deliveredByActorPublicKey,
                                            Actors deliveredByActorType,
                                            String deliveredToActorPublicKey,
                                            Actors deliveredToActorType,
                                            Platforms platform,
                                            VaultType vaultType,
                                            String vaultIdentifier,
                                            String walletPublicKey,
                                            ReferenceWallet walletType) throws CantRequestCryptoAddressException;
    // TODO ADD BLOCKCHAIN CRYPTO NETWORK ENUM (TO VALIDATE WITH THE SPECIFIC NETWORK).

    CryptoAddress requestAddressToNewExtraUser(String deliveredByActorPublicKey,
                                               Actors deliveredByActorType,
                                               String deliveredToActorName,
                                               Platforms platform,
                                               VaultType vaultType,
                                               String vaultIdentifier,
                                               String walletPublicKey,
                                               ReferenceWallet walletType) throws CantRequestCryptoAddressException;
    // TODO ADD BLOCKCHAIN CRYPTO NETWORK ENUM (TO VALIDATE WITH THE SPECIFIC NETWORK).

    void send(long cryptoAmount,
              CryptoAddress destinationAddress,
              String notes, String walletPublicKey,
              String deliveredByActorPublicKey,
              Actors deliveredByActorType,
              String deliveredToActorPublicKey,
              Actors deliveredToActorType,
              ReferenceWallet referenceWallet) throws CantSendCryptoException, InsufficientFundsException;


    /**
     * Throw the method <code>getBalance</code> you can get the balance of the wallet, having i count the type of balance that you need.
     *
     * @param balanceType     type of balance that you need
     * @param walletPublicKey public key of the wallet which you're working with.
     *
     * @return the balance of the wallet in long format.
     *
     * @throws CantGetBalanceException if something goes wrong
     */
    long getBalance(BalanceType balanceType,
                    String      walletPublicKey) throws CantGetBalanceException;

    /**
     * Throw the method <code>getTransactions</code> you cant get all the transactions for an specific balance type.
     *
     * @param balanceType     type of balance that you need
     * @param walletPublicKey of the wallet which you're working with.
     * @param max             quantity of instance you want to return
     * @param offset          the point of start in the list you're trying to bring.
     *
     * @return a list of crypto wallet transactions (enriched crypto transactions).
     *
     * @throws CantListTransactionsException if something goes wrong.
     */
    List<CryptoWalletTransaction> getTransactions(String intraUserLoggedInPublicKey,BalanceType balanceType,
                                                                                                             TransactionType transactionType,
                                                                                                             String walletPublicKey,
                                                                                                             int max,
                                                                                                             int offset) throws CantListTransactionsException;

    /**
     * Throw the method <code>listTransactionsByActor</code> you cant get all the transactions related with an specific actor.
     *
     * @param balanceType     type of balance that you need
     * @param walletPublicKey of the wallet which you're working with.
     * @param actorPublicKey  of the actor from which you need the transactions.
     * @param max             quantity of instance you want to return
     * @param offset          the point of start in the list you're trying to bring.
     *
     * @return a list of crypto wallet transactions (enriched crypto transactions).
     *
     * @throws CantListTransactionsException if something goes wrong.
     */
    List<CryptoWalletTransaction> listTransactionsByActor(BalanceType balanceType,
                                                          String walletPublicKey,
                                                          String actorPublicKey,
                                                          String intraUserLoggedInPublicKey,
                                                          int max,
                                                          int offset) throws CantListTransactionsException;

    /**
     * Throw the method <code>getActorTransactionHistory</code> you can get the transaction history of an specific actor.
     *
     * @param balanceType     type of balance that you need
     * @param walletPublicKey public key of the wallet in where you're working.
     * @param actorPublicKey  public key of the actor that you're trying to find.
     *
     * @return an instance of ActorTransactionSummary
     *
     * @throws CantGetActorTransactionHistoryException if something goes wrong.
     */
    ActorTransactionSummary getActorTransactionHistory(BalanceType balanceType,
                                                       String      walletPublicKey,
                                                       String      actorPublicKey) throws CantGetActorTransactionHistoryException;

    /**
     * Throw the method <code>listLastActorTransactionsByTransactionType</code> you can get the last transaction for each actor
     * who have made transactions with the specified wallet.
     *
     * @param balanceType     type of balance that you need
     * @param transactionType type of transaction you want to bring, credits or debits or both.
     * @param walletPublicKey public key of the wallet in where you're working.
     * @param max             quantity of instance you want to return
     * @param offset          the point of start in the list you're trying to bring.
     *
     * @return a list of crypto wallet transactions (enriched crypto transactions).
     *
     * @throws CantListTransactionsException if something goes wrong.
     */
    List<CryptoWalletTransaction> listLastActorTransactionsByTransactionType(BalanceType balanceType,
                                                                                                                                        TransactionType transactionType,
                                                                                                                                        String walletPublicKey,
                                                                             String actorPublicKey,
                                                                                                                                        int max,
                                                                                                                                        int offset) throws CantListTransactionsException;

    /**
     * Throw the method <code>setTransactionDescription</code> you can add or change a description for an existent transaction.
     *
     * @param walletPublicKey public key of the wallet in where you're working.
     * @param transactionID   to identify the transaction.
     * @param description     string describing the transaction
     *
     * @throws CantSaveTransactionDescriptionException if something goes wrong.
     * @throws TransactionNotFoundException if we cant find the transaction.
     */
    void setTransactionDescription(String walletPublicKey,
                                   UUID   transactionID,
                                   String description) throws CantSaveTransactionDescriptionException, TransactionNotFoundException;

    /**
     *The method <code>listSentPaymentRequest</code> list the wallet send payments request.
     *
     * @param walletPublicKey
     * @return List of PaymentRequest object
     */
    List<PaymentRequest> listSentPaymentRequest(String  walletPublicKey,int max,int offset) throws CantListSentPaymentRequestException;

    /**
     *The method <code>listReceivedPaymentRequest</code> list the wallet receive payments request.
     *
     * @param walletPublicKey
     * @return List of PaymentRequest object
     */
    List<PaymentRequest> listReceivedPaymentRequest(String  walletPublicKey,int max,int offset)throws CantListReceivePaymentRequestException;

    /**
     * The method <code>listPaymentRequestDateOrder</code> list the wallet payments requests order by date.
     *
     * @param walletPublicKey
     * @return List of PaymentRequest object
     */
    List<PaymentRequest> listPaymentRequestDateOrder(String  walletPublicKey,int max,int offset) throws CantListPaymentRequestDateOrderException;

    /**
     *
     * @return
     * @throws CantListCryptoWalletIntraUserIdentityException
     */

    List<com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletIntraUserIdentity> getAllIntraWalletUsersFromCurrentDeviceUser() throws CantListCryptoWalletIntraUserIdentityException;

    /**
     *
     * @param cryptoAmount
     * @param destinationAddress
     * @param notes
     * @param walletPublicKey
     * @param deliveredByActorPublicKey
     * @param deliveredByActorType
     * @param deliveredToActorPublicKey
     * @param deliveredToActorType
     */
    void sendMetadataLikeChampion(long cryptoAmount,
                                  CryptoAddress destinationAddress,
                                  String notes, String walletPublicKey,
                                  String deliveredByActorPublicKey,
                                  Actors deliveredByActorType,
                                  String deliveredToActorPublicKey,
                                  Actors deliveredToActorType);

    List<IntraWalletUser> getActiveIdentities();

}
