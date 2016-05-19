package com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.VaultType;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.core.MethodDetail;
import com.bitdubai.fermat_api.layer.modules.ModuleSettingsImpl;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.BlockchainDownloadProgress;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetBlockchainDownloadProgress;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.vault_seed.exceptions.CantLoadExistingVaultSeed;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantGetMnemonicTextException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.exceptions.CantCreateNewIntraWalletUserException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentity;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.CantApproveCryptoPaymentRequestException;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.CantRejectCryptoPaymentRequestException;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.CryptoPaymentRequestNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.BitcoinWalletSettings;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantApproveRequestPaymentException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantCreateWalletContactException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantDeleteWalletContactException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantFindWalletContactException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantGetActorTransactionHistoryException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantGetAllIntraUserConnectionsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantGetAllWalletContactsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantGetBalanceException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantListCryptoWalletIntraUserIdentityException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantListPaymentRequestDateOrderException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantListReceivePaymentRequestException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantListSentPaymentRequestException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantListTransactionsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantRefuseRequestPaymentException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantRequestCryptoAddressException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantSaveTransactionDescriptionException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantSendCryptoException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantSendCryptoPaymentRequestException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantUpdateWalletContactException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.ContactNameAlreadyExistsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.InsufficientFundsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.PaymentRequestNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.RequestPaymentInsufficientFundsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.TransactionNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.WalletContactNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantSendLossProtectedCryptoException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.LossProtectedInsufficientFundsException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantListWalletsException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.CryptoWallet</code>
 * haves all consumable methods from the plugin
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 10/06/15.
 * @version 1.0
 */
public interface CryptoWallet  extends Serializable,ModuleManager<BitcoinWalletSettings,ActiveActorIdentityInformation>,ModuleSettingsImpl<BitcoinWalletSettings> {

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
     * @param intraUserIdentityPublicKey the public key of the intra user that the user select.
     * @param walletPublicKey            public key of the wallet in which we are working.
     * @param max                        quantity of instance you want to return
     * @param offset                     the point of start in the list you're trying to bring.
     *
     * @return a list of crypto wallet intra user actors
     *
     * @throws CantGetAllIntraUserConnectionsException if something goes wrong.
     */
    List<CryptoWalletIntraUserActor> listAllIntraUserConnections(String  intraUserIdentityPublicKey,
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
                                                  String        walletPublicKey,
                                                  BlockchainNetworkType blockchainNetworkType) throws CantCreateWalletContactException, ContactNameAlreadyExistsException;

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
     * @throws CantCreateWalletContactException if something goes wrong
     * @throws ContactNameAlreadyExistsException if the name of the contact already exists
     */
    CryptoWalletWalletContact createWalletContactWithPhoto(CryptoAddress receivedCryptoAddress,
                                                           String        actorAlias,
                                                           String        actorFirstName,
                                                           String        actorLastName,
                                                           Actors        actorType,
                                                           String        walletPublicKey,
                                                           byte[]        photo,
                                                           BlockchainNetworkType blockchainNetworkType) throws CantCreateWalletContactException, ContactNameAlreadyExistsException;

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
                             String actorName,
                             BlockchainNetworkType blockchainNetworkType) throws CantUpdateWalletContactException;



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
                                            ReferenceWallet walletType,
                                            BlockchainNetworkType blockchainNetworkType) throws CantRequestCryptoAddressException;

    CryptoAddress requestAddressToNewExtraUser(String deliveredByActorPublicKey,
                                               Actors deliveredByActorType,
                                               String deliveredToActorName,
                                               Platforms platform,
                                               VaultType vaultType,
                                               String vaultIdentifier,
                                               String walletPublicKey,
                                               ReferenceWallet walletType) throws CantRequestCryptoAddressException;


    void send(long cryptoAmount,
              CryptoAddress destinationAddress,
              String notes, String walletPublicKey,
              String deliveredByActorPublicKey,
              Actors deliveredByActorType,
              String deliveredToActorPublicKey,
              Actors deliveredToActorType,
              ReferenceWallet referenceWallet,
              BlockchainNetworkType blockchainNetworkType) throws CantSendCryptoException, InsufficientFundsException;


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
                    String      walletPublicKey,
                    BlockchainNetworkType blockchainNetworkType) throws CantGetBalanceException;

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
    @MethodDetail(looType = MethodDetail.LoopType.BACKGROUND)
    List<CryptoWalletTransaction> listLastActorTransactionsByTransactionType(BalanceType balanceType,
                                                                             TransactionType transactionType,
                                                                             String walletPublicKey,
                                                                             String actorPublicKey,
                                                                             BlockchainNetworkType blockchainNetworkType,
                                                                             int max,
                                                                             int offset) throws CantListTransactionsException;

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
     * @throws CantListTransactionsException
     */
    List<CryptoWalletTransaction> listTransactionsByActorAndType(BalanceType balanceType,
                                                                 TransactionType transactionType,
                                                                 String walletPublicKey,
                                                                 String actorPublicKey,
                                                                 String intraUserLoggedInPublicKey,
                                                                 BlockchainNetworkType blockchainNetworkType,
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
    List<PaymentRequest> listSentPaymentRequest(String  walletPublicKey,BlockchainNetworkType blockchainNetworkType,int max,int offset) throws CantListSentPaymentRequestException;


    /**
     * The method <code>listPaymentRequestDateOrder</code> list the wallet payments requests order by date.
     *
     * @param walletPublicKey
     * @return List of PaymentRequest object
     */
    List<PaymentRequest> listPaymentRequestDateOrder(String  walletPublicKey,int max,int offset) throws CantListPaymentRequestDateOrderException;


    /**
     * Throw the method <code>refuseRequest</code> you can refuse a request.
     *
     * @param requestId
     * @throws CantRejectCryptoPaymentRequestException
     * @throws CryptoPaymentRequestNotFoundException
     */
    void refuseRequest(UUID requestId) throws CantRefuseRequestPaymentException,PaymentRequestNotFoundException;


    /**
     * Throw the method <code>approveRequest</code> you can approve a request and send the specified crypto.
     * @param requestId
     * @param intraUserLoggedInPublicKey
     * @throws CantApproveCryptoPaymentRequestException
     * @throws CryptoPaymentRequestNotFoundException
     * @throws com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.InsufficientFundsException
     */

    void approveRequest(UUID requestId,String intraUserLoggedInPublicKey) throws CantApproveRequestPaymentException,PaymentRequestNotFoundException,RequestPaymentInsufficientFundsException;

    /**
     *
     * @return
     * @throws CantListCryptoWalletIntraUserIdentityException
     */

    ArrayList<CryptoWalletIntraUserIdentity> getAllIntraWalletUsersFromCurrentDeviceUser() throws CantListCryptoWalletIntraUserIdentityException;

    ArrayList<IntraWalletUserIdentity> getActiveIdentities();

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
     * @throws CantSendCryptoPaymentRequestException  if something goes wrong.
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
                                   final ReferenceWallet       referenceWallet) throws CantSendCryptoPaymentRequestException;

    void createIntraUser(String name, String phrase, byte[] image) throws CantCreateNewIntraWalletUserException;

     void registerIdentities();

     CryptoWalletWalletContact findWalletContactByName(String alias,String walletPublicKey,String intraUserLoggedInPublicKey) throws CantFindWalletContactException, WalletContactNotFoundException;

    /**
     *
     * @param transactionId
     * @return
     * @throws CantListTransactionsException
     */
    CryptoWalletTransaction getTransaction(UUID transactionId,String walletPublicKey,String intraUserLoggedInPublicKey) throws CantListTransactionsException;


    /**
     *
     * @param requestId
     * @return
     * @throws CantListReceivePaymentRequestException
     */
    PaymentRequest getPaymentRequest(UUID requestId) throws CantListReceivePaymentRequestException;

    /**
     * Through the method <code>getMnemonicText</code> you can get the Mnemonic Text from Vault
     * @return List<String>
     * @throws CantGetMnemonicTextException
     */
    List<String> getMnemonicText() throws CantGetMnemonicTextException;

    /**
     * /**
     *The method <code>listReceivedPaymentRequest</code> list the wallet receive payments request.

     * @param walletPublicKey
     * @param blockchainNetworkType
     * @param max
     * @param offset
     * @return
     * @throws CantListReceivePaymentRequestException
     */
    List<PaymentRequest> listReceivedPaymentRequest(String walletPublicKey,BlockchainNetworkType blockchainNetworkType,int max,int offset) throws CantListReceivePaymentRequestException;

    /**
     * The method <code>getBlockchainDownloadProgress</code> get status of Blockchain Download Progress.
     * @param blockchainNetworkType
     * @return
     * @throws CantGetBlockchainDownloadProgress
     */

     BlockchainDownloadProgress getBlockchainDownloadProgress(BlockchainNetworkType blockchainNetworkType) throws CantGetBlockchainDownloadProgress;

    /**
     *The method <code>getInstalledWallets</code> return the list of installed wallets on platform.
     * @return
     * @throws CantListWalletsException
     */
    List<InstalledWallet> getInstalledWallets() throws CantListWalletsException;


    /**
     * The method <code>sendToWallet</code> send btc to loss protected wallet .
     * @param cryptoAmount
     * @param sendingWalletPublicKey
     * @param receivingWalletPublicKey
     * @param notes
     * @param deliveredToActorType
     * @param sendingWallet
     * @param receivingWallet
     * @param blockchainNetworkType
     * @throws CantSendLossProtectedCryptoException
     * @throws LossProtectedInsufficientFundsException
     */
    void sendToWallet(long cryptoAmount, String sendingWalletPublicKey,String receivingWalletPublicKey, String notes, Actors deliveredToActorType, ReferenceWallet sendingWallet, ReferenceWallet receivingWallet, BlockchainNetworkType blockchainNetworkType) throws CantSendLossProtectedCryptoException, LossProtectedInsufficientFundsException;

    void importMnemonicCode(List<String> mnemonicCode, long date, BlockchainNetworkType defaultBlockchainNetworkType) throws CantLoadExistingVaultSeed;
}
