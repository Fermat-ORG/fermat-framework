package com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.VaultType;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces.BitcoinLossProtectedWalletSpend;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.exceptions.CantCreateNewIntraWalletUserException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentity;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.WalletContactNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.*;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantListReceivePaymentRequestException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.ContactNameAlreadyExistsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantApproveLossProtectedRequestPaymentException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantCreateLossProtectedWalletContactException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantDeleteLossProtectedWalletContactException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantFindLossProtectedWalletContactException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantGetActorLossProtectedTransactionHistoryException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantGetAllIntraUserLossProtectedConnectionsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantGetAllLossProtectedWalletContactsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantGetBasicWalletExchangeProviderException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantGetCurrencyExchangeException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantGetCurrencyExchangeProviderException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantGetLossProtectedBalanceException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantListLossProtectedPaymentRequestDateOrderException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantListLossProtectedReceivePaymentRequestException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantListLossProtectedSentPaymentRequestException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantListLossProtectedSpendingException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantListLossProtectedTransactionsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantListLossProtectedWalletIntraUserIdentityException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantRefuseLossProtectedRequestPaymentException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantRequestLossProtectedAddressException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantSaveLossProtectedTransactionDescriptionException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantSendLossProtectedCryptoException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantSendLossProtectedPaymentRequestException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantSetBasicWalletExchangeProviderException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantUpdateLossProtectedWalletContactException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.LossProtectedInsufficientFundsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.LossProtectedPaymentRequestNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.LossProtectedRequestPaymentInsufficientFundsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.LossProtectedTransactionNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantGetMnemonicTextException;

import com.bitdubai.fermat_cer_api.all_definition.interfaces.ExchangeRate;
import com.bitdubai.fermat_cer_api.layer.provider.interfaces.CurrencyExchangeRateProviderManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantListWalletsException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.BlockingDeque;

/**
 * The interface <code>com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.CryptoWallet</code>
 * haves all consumable methods from the plugin
 *
 * Created by Natalia Cortez 03/14/2016
 * @version 1.0
 */
public interface LossProtectedWallet extends Serializable {

    /**
     * List all wallet contact related to an specific wallet.
     *
     * @param walletPublicKey public key of the wallet in which we are working.
     * @param intraUserLoggedInPublicKey public key of the intra user identity to use wallet.
     *
     * @return a list of instances of wallet contact records
     *
     * @throws CantGetAllLossProtectedWalletContactsException if something goes wrong
     */
    List<LossProtectedWalletContact> listWalletContacts(String walletPublicKey, String intraUserLoggedInPublicKey) throws CantGetAllLossProtectedWalletContactsException;


    /**
     * List  wallet contact and intra user connections related to an specific wallet.
     *
     * @param walletPublicKey
     * @param intraUserLoggedInPublicKey
     * @return
     * @throws CantGetAllLossProtectedWalletContactsException
     */
    List<LossProtectedWalletContact> listAllActorContactsAndConnections(String walletPublicKey,String intraUserLoggedInPublicKey) throws CantGetAllLossProtectedWalletContactsException;

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
     * @throws CantGetAllLossProtectedWalletContactsException if something goes wrong
     */
    List<LossProtectedWalletContact> listWalletContactsScrolling(String  walletPublicKey,
                                                                String intraUserLoggedInPublicKey,
                                                                Integer max,
                                                                Integer offset) throws CantGetAllLossProtectedWalletContactsException;

    /**
     * Throw the method <code>listAllIntraUserConnections</code> you can get all the connections of the intra user selected.
     *
     * @param intraUserIdentityPublicKey the public key of the intra user that the user select.
     * @param walletPublicKey            public key of the wallet in which we are working.
     * @param max                        quantity of instance you want to return
     * @param offset                     the point of start in the list you're trying to bring.
     *
     * @return a list of crypto wallet intra user actors
     *
     * @throws CantGetAllIntraUserLossProtectedConnectionsException if something goes wrong.
     */
    List<LossProtectedWalletIntraUserActor> listAllIntraUserConnections(String  intraUserIdentityPublicKey,
                                                                 String  walletPublicKey,
                                                                 Integer max,
                                                                 Integer offset) throws CantGetAllIntraUserLossProtectedConnectionsException;

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
     * @throws com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantCreateLossProtectedWalletContactException if something goes wrong
     */
    LossProtectedWalletContact createWalletContact(CryptoAddress receivedCryptoAddress,
                                                  String        actorAlias,
                                                  String        actorFirstName,
                                                  String        actorLastName,
                                                  Actors        actorType,
                                                  String        walletPublicKey,
                                                  BlockchainNetworkType blockchainNetworkType) throws CantCreateLossProtectedWalletContactException, ContactNameAlreadyExistsException;

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
     * @throws CantCreateLossProtectedWalletContactException
     * @throws ContactNameAlreadyExistsException
     */
    LossProtectedWalletContact convertConnectionToContact( String        actorAlias,
                                                          Actors        actorConnectedType,
                                                          String        actorConnectedPublicKey,
                                                          byte[]        actorPhoto,
                                                          Actors        actorWalletType ,
                                                          String        identityWalletPublicKey,
                                                          String        walletPublicKey,
                                                          CryptoCurrency walletCryptoCurrency,
                                                          BlockchainNetworkType blockchainNetworkType) throws CantCreateLossProtectedWalletContactException, ContactNameAlreadyExistsException;

    /**
     *
     * Send an address request
     * //TODO: ver si hay que actualizar solo el estado a pre_processing en el address y no pedir otra nueva.
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
     */

    void sendAddressExchangeRequest(String actorAlias,
                                    Actors actorConnectedType,
                                    String actorConnectedPublicKey,
                                    byte[] actorPhoto,
                                    Actors actorWalletType,
                                    String identityWalletPublicKey,
                                    String walletPublicKey,
                                    CryptoCurrency walletCryptoCurrency,
                                    BlockchainNetworkType blockchainNetworkType);

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
     * @throws CantCreateLossProtectedWalletContactException if something goes wrong
     * @throws ContactNameAlreadyExistsException if the name of the contact already exists
     */
    LossProtectedWalletContact createWalletContactWithPhoto(CryptoAddress receivedCryptoAddress,
                                                           String        actorAlias,
                                                           String        actorFirstName,
                                                           String        actorLastName,
                                                           Actors        actorType,
                                                           String        walletPublicKey,
                                                           byte[]        photo,
                                                           BlockchainNetworkType blockchainNetworkType) throws CantCreateLossProtectedWalletContactException, ContactNameAlreadyExistsException;

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
     * @throws CantCreateLossProtectedWalletContactException if something goes wrong.
     */
    LossProtectedWalletContact addIntraUserActorLikeContact(String intraUserPublicKey,
                                                           String alias,
                                                           String actorFirstName,
                                                           String actorLastName,
                                                           String walletPublicKey) throws CantCreateLossProtectedWalletContactException;

    /**
     * updates the photo of an actor
     *
     * @param actorPublicKey actor's public key
     * @param actor type
     * @param photo byte array with photo information
     * @throws CantUpdateLossProtectedWalletContactException
     */
    void updateContactPhoto(String actorPublicKey,
                            Actors actor,
                            byte[] photo) throws CantUpdateLossProtectedWalletContactException;

    void updateWalletContact(UUID contactId,
                             CryptoAddress receivedCryptoAddress,
                             String actorName,
                             BlockchainNetworkType blockchainNetworkType) throws CantUpdateLossProtectedWalletContactException;



    /**
     * deletes a contact having in count the contact id
     *
     * @param contactId specific id of the contact that you're trying to delete
     * @throws CantDeleteLossProtectedWalletContactException
     */
    void deleteWalletContact(UUID contactId) throws CantDeleteLossProtectedWalletContactException;

    /**
     * find a wallet contact having in count its id
     *
     * @param contactId specific id of the contact that you're trying to find
     * @param intraUserLoggedInPublicKey public key of the wallet intra user identity.
     * @return instance of a crypto wallet contact
     * @throws CantFindLossProtectedWalletContactException
     * @throws WalletContactNotFoundException
     */
    LossProtectedWalletContact findWalletContactById(UUID contactId, String intraUserLoggedInPublicKey) throws CantFindLossProtectedWalletContactException, WalletContactNotFoundException;

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
                                            ReferenceWallet walletType,
                                            BlockchainNetworkType blockchainNetworkType) throws CantRequestLossProtectedAddressException;

    CryptoAddress requestAddressToNewExtraUser(String deliveredByActorPublicKey,
                                               Actors deliveredByActorType,
                                               String deliveredToActorName,
                                               Platforms platform,
                                               VaultType vaultType,
                                               String vaultIdentifier,
                                               String walletPublicKey,
                                               ReferenceWallet walletType) throws CantRequestLossProtectedAddressException;


    void send(long cryptoAmount,
              CryptoAddress destinationAddress,
              String notes, String walletPublicKey,
              String deliveredByActorPublicKey,
              Actors deliveredByActorType,
              String deliveredToActorPublicKey,
              Actors deliveredToActorType,
              ReferenceWallet referenceWallet,
              BlockchainNetworkType blockchainNetworkType
              ) throws CantSendLossProtectedCryptoException, LossProtectedInsufficientFundsException;


    void sendToWallet (long cryptoAmount,
                       String sendWalletPublicKey,
                       String receivedWalletPublicKey,
                       String notes,
                       Actors deliveredToActorType,
                       ReferenceWallet sendingWallet,
                       ReferenceWallet receivingWallet,
                       BlockchainNetworkType blockchainNetworkType)throws CantSendLossProtectedCryptoException, LossProtectedInsufficientFundsException;


    /**
     * Throw the method <code>getBalance</code> you can get the real balance of the wallet, having i count the type of balance that you need.
     *
     * @param walletPublicKey public key of the wallet which you're working with.
     *
     * @return the balance of the wallet in long format.
     *
     * @throws CantGetLossProtectedBalanceException if something goes wrong
     */
    long getRealBalance(String      walletPublicKey,
                    BlockchainNetworkType blockchainNetworkType) throws CantGetLossProtectedBalanceException;

    /**
     * Throw the method <code>getBalance</code> you can get the book balance of the wallet, having i count the type of balance that you need.
     * @param walletPublicKey
     * @param blockchainNetworkType
     * @return
     * @throws CantGetLossProtectedBalanceException
     */

    long geBookBalance(String      walletPublicKey,
                        BlockchainNetworkType blockchainNetworkType) throws CantGetLossProtectedBalanceException;

    /**
     * Throw the method <code>getBalance</code> you can get the balance of the wallet, having i count the type of balance that you need and actual exange rate.
     * @param balanceType
     * @param walletPublicKey
     * @param blockchainNetworkType
     * @param exangeRate
     * @return
     * @throws CantGetLossProtectedBalanceException
     */
    long getBalance(BalanceType balanceType,
                    String walletPublicKey,
                    BlockchainNetworkType blockchainNetworkType,
                    String exangeRate) throws CantGetLossProtectedBalanceException;

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
     * @throws CantListLossProtectedTransactionsException if something goes wrong.
     */
    List<LossProtectedWalletTransaction> getTransactions(String intraUserLoggedInPublicKey,BalanceType balanceType,
                                                                                                             TransactionType transactionType,
                                                                                                             String walletPublicKey,
                                                                                                             int max,
                                                                                                             int offset) throws CantListLossProtectedTransactionsException;

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
     * @throws CantListLossProtectedTransactionsException if something goes wrong.
     */
    List<LossProtectedWalletTransaction> listTransactionsByActor(BalanceType balanceType,
                                                          String walletPublicKey,
                                                          String actorPublicKey,
                                                          String intraUserLoggedInPublicKey,
                                                          int max,
                                                          int offset) throws CantListLossProtectedTransactionsException;

    /**
     * Throw the method <code>getActorTransactionHistory</code> you can get the transaction history of an specific actor.
     *
     * @param balanceType     type of balance that you need
     * @param walletPublicKey public key of the wallet in where you're working.
     * @param actorPublicKey  public key of the actor that you're trying to find.
     *
     * @return an instance of ActorTransactionSummary
     *
     * @throws CantGetActorLossProtectedTransactionHistoryException if something goes wrong.
     */
    LossProtectedActorTransactionSummary getActorTransactionHistory(BalanceType balanceType,
                                                       String      walletPublicKey,
                                                       String      actorPublicKey) throws CantGetActorLossProtectedTransactionHistoryException;

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
     * @throws CantListLossProtectedTransactionsException if something goes wrong.
     */
    List<LossProtectedWalletTransaction> listLastActorTransactionsByTransactionType(BalanceType balanceType,
                                                                             TransactionType transactionType,
                                                                             String walletPublicKey,
                                                                             String actorPublicKey,
                                                                             BlockchainNetworkType blockchainNetworkType,
                                                                             int max,
                                                                             int offset) throws CantListLossProtectedTransactionsException;

    /**
     * Throw the method <code>listTransactionsByActorAndType</code> you can get the transactions for each actor
     * who have made transactions with the specified wallet.
     *
     * @param balanceType
     * @param transactionType
     * @param walletPublicKey
     * @param actorPublicKey
     * @param intraUserLoggedInPublicKey
     * @param max
     * @param offset
     * @return
     * @throws CantListLossProtectedTransactionsException
     */
    List<LossProtectedWalletTransaction> listTransactionsByActorAndType(BalanceType balanceType,
                                                                 TransactionType transactionType,
                                                                 String walletPublicKey,
                                                                 String actorPublicKey,
                                                                 String intraUserLoggedInPublicKey,
                                                                 BlockchainNetworkType blockchainNetworkType,
                                                                 int max,
                                                                 int offset) throws CantListLossProtectedTransactionsException;


    /**
     * Throw the method <code>listSpendingBlocksValue</code> you can list the btc spending, value blocks.
     * @param walletPublicKey
     * @param transactionId
     * @return
     * @throws CantListLossProtectedSpendingException
     */
    List<BitcoinLossProtectedWalletSpend> listSpendingBlocksValue(String walletPublicKey,UUID transactionId) throws CantListLossProtectedSpendingException, CantLoadWalletException;

    /**
     * Throw the method <code>listAllWalletSpendingValue</code> you can list all  btc spending value.
     * @param walletPublicKey
     * @return
     * @throws CantListLossProtectedSpendingException
     */
    List<BitcoinLossProtectedWalletSpend> listAllWalletSpendingValue(String walletPublicKey,BlockchainNetworkType blockchainNetworkType) throws CantListLossProtectedSpendingException, CantLoadWalletException;

    /**
     * Throw the method <code>setTransactionDescription</code> you can add or change a description for an existent transaction.
     *
     * @param walletPublicKey public key of the wallet in where you're working.
     * @param transactionID   to identify the transaction.
     * @param description     string describing the transaction
     *
     * @throws CantSaveLossProtectedTransactionDescriptionException if something goes wrong.
     * @throws LossProtectedTransactionNotFoundException if we cant find the transaction.
     */
    void setTransactionDescription(String walletPublicKey,
                                   UUID   transactionID,
                                   String description) throws CantSaveLossProtectedTransactionDescriptionException, LossProtectedTransactionNotFoundException;

    /**
     *The method <code>listSentPaymentRequest</code> list the wallet send payments request.
     *
     * @param walletPublicKey
     * @return List of PaymentRequest object
     */
    List<LossProtectedPaymentRequest> listSentPaymentRequest(String  walletPublicKey,BlockchainNetworkType blockchainNetworkType,int max,int offset) throws CantListLossProtectedSentPaymentRequestException;

    /**
     *The method <code>listReceivedPaymentRequest</code> list the wallet receive payments request.
     *
     * @param walletPublicKey
     * @return List of PaymentRequest object
     */
    List<LossProtectedPaymentRequest> listReceivedPaymentRequest(String  walletPublicKey,BlockchainNetworkType blockchainNetworkType,int max,int offset)throws CantListLossProtectedReceivePaymentRequestException;


    /**
     * Throw the method <code>refuseRequest</code> you can refuse a request.
     *
     * @param requestId
     * @throws CantRejectCryptoPaymentRequestException
     * @throws CryptoPaymentRequestNotFoundException
     */
    void refuseRequest(UUID requestId) throws CantRefuseLossProtectedRequestPaymentException,LossProtectedPaymentRequestNotFoundException;


    /**
     * Throw the method <code>approveRequest</code> you can approve a request and send the specified crypto.
     * @param requestId
     * @param intraUserLoggedInPublicKey
     * @throws CantApproveCryptoPaymentRequestException
     * @throws CryptoPaymentRequestNotFoundException
     * @throws com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.InsufficientFundsException
     */

    void approveRequest(UUID requestId,String intraUserLoggedInPublicKey) throws CantApproveLossProtectedRequestPaymentException,LossProtectedPaymentRequestNotFoundException,LossProtectedRequestPaymentInsufficientFundsException;

    /**
     *
     * @return
     * @throws CantListLossProtectedWalletIntraUserIdentityException
     */

    List<LossProtectedWalletIntraUserIdentity> getAllIntraWalletUsersFromCurrentDeviceUser() throws CantListLossProtectedWalletIntraUserIdentityException;

    List<IntraWalletUserIdentity> getActiveIdentities();

    /**
     * Through the method <code>sendCryptoPaymentRequest</code> you can generate and send a crypto payment request.
     *
     * @param walletPublicKey
     * @param identityPublicKey
     * @param identityType
     * @param actorPublicKey
     * @param actorType
     * @param cryptoAddress
     * @param description
     * @param amount
     * @param networkType
     *
     * @throws CantSendLossProtectedPaymentRequestException  if something goes wrong.
     */
     void sendCryptoPaymentRequest(final String                walletPublicKey  ,
                                   final String                identityPublicKey,
                                   final Actors                identityType     ,
                                   final String                actorPublicKey   ,
                                   final Actors                actorType        ,
                                   final CryptoAddress         cryptoAddress    ,
                                   final String                description      ,
                                   final long                  amount           ,
                                   final BlockchainNetworkType networkType      ,
                                   final ReferenceWallet       referenceWallet) throws CantSendLossProtectedPaymentRequestException;

    void createIntraUser(String name, String phrase, byte[] image) throws CantCreateNewIntraWalletUserException;

     void registerIdentities();

    LossProtectedWalletContact findWalletContactByName(String alias,String walletPublicKey,String intraUserLoggedInPublicKey) throws CantFindLossProtectedWalletContactException, WalletContactNotFoundException;

    /**
     *
     * @param transactionId
     * @return
     * @throws CantListLossProtectedTransactionsException
     */
    LossProtectedWalletTransaction getTransaction(UUID transactionId,String walletPublicKey,String intraUserLoggedInPublicKey) throws CantListLossProtectedTransactionsException;


    /**
     *
     * @param requestId
     * @return
     * @throws CantListReceivePaymentRequestException
     */
    LossProtectedPaymentRequest getPaymentRequest(UUID requestId) throws CantListReceivePaymentRequestException;

    long getActualExchangeRate() throws CantListReceivePaymentRequestException;


    /**
     *Through the method <code>getMnemonicText</code> you can get the wallet Mnemonic Text
     * @return
     * @throws CantGetMnemonicTextException
     */
    List<String> getMnemonicText() throws CantGetMnemonicTextException;


    /**
     * Through the method <code>getCurrencyExchange</code> you can get the actual currency exchange rate
     * @return
     * @throws CantGetMnemonicTextException
     */
    ExchangeRate getCurrencyExchange(UUID rateProviderManagerId) throws CantGetCurrencyExchangeException;

    /**
     * Through the method <code>getExchangeRateProviderManagers</code> you can get the list of exchange rate providers
     * @return
     * @throws CantGetCurrencyExchangeProviderException
     */
    Collection<CurrencyExchangeRateProviderManager> getExchangeRateProviderManagers() throws CantGetCurrencyExchangeProviderException;

    /**
     * Through the method <code>getInstalledWallets</code> you can get the list of wallets installed on platform
     * @return List of InstalledWallet
     * @throws CantListWalletsException
     */
    List<InstalledWallet> getInstalledWallets() throws CantListWalletsException;

    UUID getExchangeProvider() throws CantGetBasicWalletExchangeProviderException;

    void setExchangeProvider(UUID idProvider) throws CantSetBasicWalletExchangeProviderException;

    /**
     *
     * @param balanceType
     * @param transactionType
     * @param walletPublicKey
     * @param intraUserLoggedInPublicKey
     * @param blockchainNetworkType
     * @param max
     * @param offset
     * @return
     * @throws CantListLossProtectedTransactionsException
     */
    List<LossProtectedWalletTransaction> listAllActorTransactionsByTransactionType(BalanceType balanceType,
                                                                                   final TransactionType transactionType,
                                                                                   String walletPublicKey,
                                                                                   String intraUserLoggedInPublicKey,
                                                                                   BlockchainNetworkType blockchainNetworkType,
                                                                                   int max,
                                                                                   int offset) throws CantListLossProtectedTransactionsException;
}
