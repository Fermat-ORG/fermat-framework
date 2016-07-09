package com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces;

import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantListActorConnectionsException;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantGetSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.SettingsNotFoundException;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankAccountNumber;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractDetailType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationBankAccount;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantClearAssociatedCustomerIdentityWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantCreateNewCustomerIdentityWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantGetCustomerIdentityWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantGetListActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantAckMerchandiseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantSendPaymentException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantGetListCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchase;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.exceptions.CantListCryptoCustomerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.interfaces.CryptoCustomerIdentity;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantCreateBankAccountPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantCreateCustomerBrokerPurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantCreateLocationPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantDeleteBankAccountPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantDeleteLocationPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantGetListBankAccountsPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantGetListPurchaseNegotiationsException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantUpdateBankAccountPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.exceptions.CantCreateCustomerBrokerNewPurchaseNegotiationTransactionException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CantGetAssociatedIdentityException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CBPWalletsModuleManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.IndexInfoSummary;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantClearCryptoCustomerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantGetCryptoBrokerListException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantGetCryptoCustomerIdentityListException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantGetProvidersCurrentExchangeRatesException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantNewEmptyCryptoCustomerWalletAssociatedSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantNewEmptyCryptoCustomerWalletProviderSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantNewEmptyCustomerBrokerNegotiationInformationException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantSaveCryptoCustomerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CouldNotStartNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CouldNotUpdateNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.IdentityAssociatedNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.settings.CryptoCustomerWalletAssociatedSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.settings.CryptoCustomerWalletPreferenceSettings;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.settings.CryptoCustomerWalletProviderSetting;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantGetExchangeRateException;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantGetProviderInfoException;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.UnsupportedCurrencyPairException;
import com.bitdubai.fermat_cer_api.layer.search.exceptions.CantGetProviderException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantListWalletsException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * Created by nelson on 22/09/15.
 * Updated by Manuel Perez on 24/01/2016
 */
public interface CryptoCustomerWalletModuleManager
        extends CBPWalletsModuleManager<CryptoCustomerWalletPreferenceSettings, ActiveActorIdentityInformation>, Serializable {

    /**
     * Returns the Balance this BitcoinWalletBalance belongs to. (Can be available or book)
     *
     * @return A BigDecimal, containing the balance.
     */
    long getBalanceBitcoinWallet(String walletPublicKey);

    /**
     * associate an Identity to this wallet
     *
     * @param customer        the Crypto Customer ID who is going to be associated with this wallet
     * @param walletPublicKey the public key of the wallet to associate
     *
     * @return true if the association was successful false otherwise
     */
    boolean associateIdentity(ActorIdentity customer, String walletPublicKey) throws CantCreateNewCustomerIdentityWalletRelationshipException;

    /**
     * Clear all identities from this wallet
     *
     * @param walletPublicKey the public key of the wallet to clear
     */
    void clearAssociatedIdentities(String walletPublicKey) throws CantClearAssociatedCustomerIdentityWalletRelationshipException;


    boolean haveAssociatedIdentity(String walletPublicKey)
            throws CantListCryptoCustomerIdentityException, CantGetCustomerIdentityWalletRelationshipException;

    /**
     * Through this method you can get the customer identity associated to any crypto customer wallet.
     *
     * @param walletPublicKey the public key of the crypto customer wallet that we're trying to get the associated identity.
     *
     * @return an instance of CryptoCustomerIdentity associated to the wallet.
     *
     * @throws CantGetAssociatedIdentityException  if something goes wrong.
     * @throws IdentityAssociatedNotFoundException if can't find the identity associated with the wallet.
     */
    CryptoCustomerIdentity getAssociatedIdentity(String walletPublicKey)
            throws CantGetAssociatedIdentityException, IdentityAssociatedNotFoundException;

    /**
     * @return list of identities associated with this wallet
     */
    List<CryptoCustomerIdentity> getListOfIdentities()
            throws CantGetCryptoCustomerIdentityListException, CantListCryptoCustomerIdentityException;

    /**
     * @param walletPublicKey the customer wallet public key
     *
     * @return a summary of the current market rate for the different currencies the customer is interested
     */
    Collection<IndexInfoSummary> getProvidersCurrentExchangeRates(String walletPublicKey)
            throws CantGetProvidersCurrentExchangeRatesException, CantGetProviderException, UnsupportedCurrencyPairException,
            CantGetExchangeRateException, CantGetSettingsException, SettingsNotFoundException;

    /**
     * return the list of brokers connected with the crypto customer and the merchandises that they sell
     *
     * @return the list of crypto brokers
     */
    Collection<BrokerIdentityBusinessInfo> getListOfConnectedBrokersAndTheirMerchandises()
            throws CantGetCryptoBrokerListException, CantGetListActorExtraDataException;

    /**
     *
     * @param paymentCurrency
     * @return list of platforms supporteds
     */
    Collection<Platforms> getPlatformsSupported(String customerPublicKey, String brokerPublicKey, String paymentCurrency) throws CantGetListActorExtraDataException;

    /**
     * Verify if thew wallet is configured or not
     *
     * @param customerWalletPublicKey the wallet public key
     *
     * @return true if configure, false otherwise
     */
    boolean isWalletConfigured(String customerWalletPublicKey) throws CantGetSettingsException, SettingsNotFoundException;

    /**
     * Start a new negotiation with a crypto broker
     *
     * @param customerPublicKey the crypto customer publicKey
     * @param brokerPublicKey   the crypto broker publicKey
     * @param clauses           the initial and mandatory clauses to start a negotiation
     *
     * @return true if the association was successful false otherwise
     */
    boolean startNegotiation(String customerPublicKey, String brokerPublicKey, Collection<ClauseInformation> clauses)
            throws CouldNotStartNegotiationException, CantCreateCustomerBrokerNewPurchaseNegotiationTransactionException;

    /**
     * Start a new negotiation with a crypto broker
     *
     * @param negotiation the negotiation
     */
    void updateNegotiation(CustomerBrokerNegotiationInformation negotiation) throws CouldNotUpdateNegotiationException;

    /**
     * This method list all wallet installed in device, start the transaction
     */
    List<InstalledWallet> getInstallWallets() throws CantListWalletsException;

    /**
     * Set the memo of the negotiation
     *
     * @param memo the memo
     * @param data the negotiation information
     *
     * @return the updated negotiation information
     */
    CustomerBrokerNegotiationInformation setMemo(String memo, CustomerBrokerNegotiationInformation data);

    /**
     * create and add a new location
     *
     * @param location the location
     * @param uri      the URI for this location, can be <code>null</code>
     *
     * @throws CantCreateLocationPurchaseException
     */
    void createNewLocation(String location, String uri) throws CantCreateLocationPurchaseException;

    /**
     * Clear all saved locations from wallet
     *
     * @throws CantDeleteLocationPurchaseException
     */
    void clearLocations() throws CantDeleteLocationPurchaseException;

    /**
     * @return a empty {@link CustomerBrokerNegotiationInformation} to fill
     *
     * @throws CantNewEmptyCustomerBrokerNegotiationInformationException
     */
    CustomerBrokerNegotiationInformation newEmptyCustomerBrokerNegotiationInformation()
            throws CantNewEmptyCustomerBrokerNegotiationInformationException;

    /**
     * Create and add a new bank account in the database
     *
     * @param bankAccount the bank account
     *
     * @throws CantCreateBankAccountPurchaseException
     */
    void createNewBankAccount(String bankAccount, FiatCurrency currency) throws CantCreateBankAccountPurchaseException;

    /**
     * Update a bank account in the database
     *
     * @param bankAccount the bank account with the updated data
     *
     * @throws CantUpdateBankAccountPurchaseException
     */
    void updateBankAccount(NegotiationBankAccount bankAccount) throws CantUpdateBankAccountPurchaseException;

    /**
     * delete a bank account in the database
     *
     * @param bankAccount the bank account to delete
     *
     * @throws CantDeleteBankAccountPurchaseException
     */
    void deleteBankAccount(NegotiationBankAccount bankAccount) throws CantDeleteBankAccountPurchaseException;

    /**
     * Returns a list of all associated bank accounts
     *
     * @throws CantGetListBankAccountsPurchaseException
     */
    List<BankAccountNumber> getListOfBankAccounts() throws CantGetListBankAccountsPurchaseException;

    /**
     * Delete all bank accounts associated with the crypto customer wallet
     *
     * @throws CantDeleteBankAccountPurchaseException
     */
    void clearAllBankAccounts() throws CantDeleteBankAccountPurchaseException;

    /**
     * Create a {@link CustomerBrokerNegotiationInformation} in the database
     *
     * @param negotiation the negotiation to add in the database
     *
     * @throws CantCreateCustomerBrokerPurchaseNegotiationException
     */
    void createCustomerBrokerPurchaseNegotiation(CustomerBrokerNegotiationInformation negotiation)
            throws CantCreateCustomerBrokerPurchaseNegotiationException;


    /**
     * @return a empty {@link CryptoCustomerWalletAssociatedSetting} to fill and add to the database
     *
     * @throws CantNewEmptyCryptoCustomerWalletAssociatedSettingException
     */
    CryptoCustomerWalletAssociatedSetting newEmptyCryptoBrokerWalletAssociatedSetting()
            throws CantNewEmptyCryptoCustomerWalletAssociatedSettingException;

    /**
     * save in the database the association between a crypto wallet with the customer wallet
     *
     * @param setting                 the object with the data of the wallets to associate
     * @param customerWalletPublicKey the customer wallet public key
     *
     * @throws CantSaveCryptoCustomerWalletSettingException
     * @throws CantCreateFileException
     * @throws CantPersistFileException
     * @throws CantGetSettingsException
     * @throws CantPersistSettingsException
     */
    void saveWalletSettingAssociated(CryptoCustomerWalletAssociatedSetting setting, String customerWalletPublicKey)
            throws CantSaveCryptoCustomerWalletSettingException, CantCreateFileException, CantPersistFileException,
            CantGetSettingsException, CantPersistSettingsException;

    /**
     * Returns a list of provider references which can obtain the ExchangeRate of the given CurrencyPair
     *
     * @param currencyFrom currency from
     * @param currencyTo   currency to
     *
     * @return a Map of name/provider reference pairs
     */
    Map<String, UUID> getProviderReferencesFromCurrencyPair(Currency currencyFrom, Currency currencyTo)
            throws CantGetProviderException, CantGetProviderInfoException;

    /**

    /**
     * Create a empty {@link CryptoCustomerWalletProviderSetting} object to fill and can be used
     * to associate a exchange rate provider with a customer wallet
     *
     * @return a empty {@link CryptoCustomerWalletProviderSetting} object
     *
     * @throws CantNewEmptyCryptoCustomerWalletProviderSettingException
     */
    CryptoCustomerWalletProviderSetting newEmptyCryptoCustomerWalletProviderSetting()
            throws CantNewEmptyCryptoCustomerWalletProviderSettingException;

    /**
     * Associate a exchange rate provider with the customer wallet
     *
     * @param setting                 the object with the data of the provider to associate
     * @param customerWalletPublicKey the customer wallet public key
     *
     * @throws CantSaveCryptoCustomerWalletSettingException
     * @throws CantPersistFileException
     * @throws CantCreateFileException
     * @throws CantPersistSettingsException
     * @throws CantGetSettingsException
     */
    void saveCryptoCustomerWalletProviderSetting(CryptoCustomerWalletProviderSetting setting, String customerWalletPublicKey)
            throws CantSaveCryptoCustomerWalletSettingException, CantPersistFileException, CantCreateFileException,
            CantPersistSettingsException, CantGetSettingsException;

    /**
     * clear the exchange rate providers associated with the customer wallet
     *
     * @param customerWalletPublicKey the customer wallet public key
     *
     * @throws CantClearCryptoCustomerWalletSettingException
     * @throws CantPersistFileException
     * @throws CantCreateFileException
     * @throws CantPersistSettingsException
     * @throws CantGetSettingsException
     */
    void clearCryptoCustomerWalletProviderSetting(String customerWalletPublicKey)
            throws CantClearCryptoCustomerWalletSettingException, CantPersistFileException, CantCreateFileException,
            CantPersistSettingsException, CantGetSettingsException;

    /**
     * Return the list of associated exchange rate providers
     *
     * @param walletPublicKey the customer wallet public key
     *
     * @return the list of associated exchange rate providers
     *
     * @throws FileNotFoundException
     * @throws CantCreateFileException
     * @throws CantGetSettingsException
     * @throws SettingsNotFoundException
     */
    List<CryptoCustomerWalletProviderSetting> getAssociatedProviders(String walletPublicKey)
            throws FileNotFoundException, CantCreateFileException, CantGetSettingsException, SettingsNotFoundException;

    /**
     * This method returns the Contract associated to a negotiation ID
     *
     * @param negotiationId the negotiation ID
     *
     * @return the associated Contract
     *
     * @throws CantGetListCustomerBrokerContractPurchaseException
     */
    CustomerBrokerContractPurchase getCustomerBrokerContractPurchaseByNegotiationId(String negotiationId)
            throws CantGetListCustomerBrokerContractPurchaseException;

    /**
     * This method returns the money type from a contract
     *
     * @param contractPurchase   the contract object
     * @param contractDetailType the detail type
     *
     * @return the money type
     *
     * @throws CantGetListPurchaseNegotiationsException
     */
    MoneyType getCurrencyTypeFromContract(CustomerBrokerContractPurchase contractPurchase, ContractDetailType contractDetailType)
            throws CantGetListPurchaseNegotiationsException;

    /**
     * This method send a payment according the contract elements.
     *
     * @param contractHash the contract Hash/ID
     */
    void sendPayment(String contractHash) throws CantSendPaymentException;

    /**
     * This method execute a Customer Ack Merchandise Business Transaction
     *
     * @param contractHash the contract Hash/ID
     *
     * @throws CantAckMerchandiseException
     */
    ContractStatus ackMerchandise(String contractHash) throws CantAckMerchandiseException;

    /**
     * This method returns the Contract Status by contractHash/Id
     *
     * @param contractHash the contract Hash/ID
     *
     * @return the Contract Status
     */
    ContractStatus getContractStatus(String contractHash) throws CantGetListCustomerBrokerContractPurchaseException;

    /**
     * @param brokerPublicKey   the broker wallet public key
     * @param customerPublicKey the customer wallet public key
     *
     * @return The basic information of the broker whose publickey equals the parameter passed as publickey.
     *
     * @throws CantListActorConnectionsException
     */
    ActorIdentity getBrokerInfoByPublicKey(String customerPublicKey, String brokerPublicKey) throws CantListActorConnectionsException;

    /**
     * Returns a Completion Date in which a specific status was achieved for a specific contract
     *
     * @param contractHash   the contract Hash/ID
     * @param contractStatus the Contract Status
     * @param paymentMethod  the Payment Method
     *
     * @return a Completion Date in millis
     */
    long getCompletionDateForContractStatus(String contractHash, ContractStatus contractStatus, String paymentMethod);

}
