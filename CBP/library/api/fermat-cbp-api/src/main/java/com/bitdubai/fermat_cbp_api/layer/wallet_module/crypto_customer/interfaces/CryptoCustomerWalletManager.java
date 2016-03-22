package com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces;

import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantListActorConnectionsException;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantGetSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.SettingsNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
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
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantGetListPurchaseNegotiationsException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantUpdateBankAccountPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.exceptions.CantCreateCustomerBrokerNewPurchaseNegotiationTransactionException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CantGetAssociatedIdentityException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.IndexInfoSummary;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.WalletManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantClearCryptoCustomerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantGetAssociatedCryptoCustomerIdentityException;
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
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.settings.CryptoCustomerWalletProviderSetting;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantGetExchangeRateException;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantGetProviderInfoException;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.UnsupportedCurrencyPairException;
import com.bitdubai.fermat_cer_api.layer.provider.interfaces.CurrencyExchangeRateProviderManager;
import com.bitdubai.fermat_cer_api.layer.search.exceptions.CantGetProviderException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantListWalletsException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by nelson on 22/09/15.
 * Updated by Manuel Perez on 24/01/2016
 */
public interface CryptoCustomerWalletManager extends WalletManager {

    /**
     * associate an Identity to this wallet
     *
     * @param customer        the Crypto Customer ID who is going to be associated with this wallet
     * @param walletPublicKey the public key of the wallet to associate
     * @return true if the association was successful false otherwise
     */
    boolean associateIdentity(ActorIdentity customer, String walletPublicKey) throws CantCreateNewCustomerIdentityWalletRelationshipException;

    /**
     * Clear all identities from this wallet
     *
     * @param walletPublicKey the public key of the wallet to clear
     * @return true if the association was successful false otherwise
     */
    void clearAssociatedIdentities(String walletPublicKey) throws CantClearAssociatedCustomerIdentityWalletRelationshipException;


    boolean haveAssociatedIdentity(String walletPublicKey) throws CantListCryptoCustomerIdentityException, CantGetCustomerIdentityWalletRelationshipException;

    /**
     * Through this method you can get the customer identity associated to any crypto customer wallet.
     *
     * @param walletPublicKey the public key of the crypto customer wallet that we're trying to get the associated identity.
     *
     * @return an instance of CryptoCustomerIdentity associated to the wallet.
     *
     * @throws CantGetAssociatedIdentityException            if something goes wrong.
     * @throws IdentityAssociatedNotFoundException  if can't find the identity associated with the wallet.
     */
    CryptoCustomerIdentity getAssociatedIdentity(

            String walletPublicKey

    ) throws CantGetAssociatedIdentityException,
            IdentityAssociatedNotFoundException;

    /**
     * @return list of identities associated with this wallet
     */
    List<CryptoCustomerIdentity> getListOfIdentities() throws CantGetCryptoCustomerIdentityListException, CantListCryptoCustomerIdentityException;

    /**
     * @return a summary of the current market rate for the different currencies the customer is interested
     * @param walletPublicKey
     */
    Collection<IndexInfoSummary> getProvidersCurrentExchangeRates(String walletPublicKey) throws CantGetProvidersCurrentExchangeRatesException, CantGetProviderException, UnsupportedCurrencyPairException, CantGetExchangeRateException, CantGetSettingsException, SettingsNotFoundException;

    /**
     * return the list of brokers connected with the crypto customer and the merchandises that they sell
     *
     * @return the list of crypto brokers
     */
    Collection<BrokerIdentityBusinessInfo> getListOfConnectedBrokersAndTheirMerchandises() throws CantGetCryptoBrokerListException, CantGetListActorExtraDataException;

    /**
     * @return list of identities associated with this wallet
     */
    CryptoCustomerIdentity getAssociatedIdentity() throws CantGetAssociatedCryptoCustomerIdentityException;


    /**
     * Verify if thew wallet is configured or not
     *
     * @param customerWalletPublicKey the wallet public key
     * @return true if configure, false otherwise
     */
    boolean isWalletConfigured(String customerWalletPublicKey) throws CantGetSettingsException, SettingsNotFoundException;

    /**
     * Start a new negotiation with a crypto broker
     *
     * @param customerPublicKey the crypto customer publicKey
     * @param brokerPublicKey   the crypto broker publicKey
     * @param clauses           the initial and mandatory clauses to start a negotiation
     * @return true if the association was successful false otherwise
     */
    boolean startNegotiation(String customerPublicKey, String brokerPublicKey, Collection<ClauseInformation> clauses) throws CouldNotStartNegotiationException, CantCreateCustomerBrokerNewPurchaseNegotiationTransactionException;

    /**
     * Start a new negotiation with a crypto broker
     *
     * @param negotiation the negotiation
     * @return true if the association was successful false otherwise
     */
    void updateNegotiation(CustomerBrokerNegotiationInformation negotiation) throws CouldNotUpdateNegotiationException;

    /**
     * This method list all wallet installed in device, start the transaction
     */
    List<InstalledWallet> getInstallWallets() throws CantListWalletsException;

    CustomerBrokerNegotiationInformation setMemo(String memo, CustomerBrokerNegotiationInformation data);

    /**
     * @param location
     * @param uri
     * @throws CantCreateLocationPurchaseException
     */
    void createNewLocation(String location, String uri) throws CantCreateLocationPurchaseException;

    /**
     * Clear all saved locations from wallet
     * @throws CantDeleteLocationPurchaseException
     */
    void clearLocations() throws CantDeleteLocationPurchaseException;

    NegotiationBankAccount newEmptyNegotiationBankAccount(final String bankAccount, final FiatCurrency currencyType) throws CantCreateBankAccountPurchaseException;

    CustomerBrokerNegotiationInformation newEmptyCustomerBrokerNegotiationInformation() throws CantNewEmptyCustomerBrokerNegotiationInformationException;

    /**
     * @param bankAccount
     * @throws CantCreateBankAccountPurchaseException
     */
    void createNewBankAccount(NegotiationBankAccount bankAccount) throws CantCreateBankAccountPurchaseException;

    /**
     * @param bankAccount
     * @throws CantUpdateBankAccountPurchaseException
     */
    void updateBankAccount(NegotiationBankAccount bankAccount) throws CantUpdateBankAccountPurchaseException;

    /**
     * @param bankAccount
     * @throws CantDeleteBankAccountPurchaseException
     */
    void deleteBankAccount(NegotiationBankAccount bankAccount) throws CantDeleteBankAccountPurchaseException;

    /**
     * Delete all bank accounts associated with the crypto customer wallet
     * @throws CantDeleteBankAccountPurchaseException
     */
    void clearAllBankAccounts() throws CantDeleteBankAccountPurchaseException;

    /**
     * @param negotiation
     * @throws CantCreateCustomerBrokerPurchaseNegotiationException
     */
    void createCustomerBrokerPurchaseNegotiation(CustomerBrokerNegotiationInformation negotiation) throws CantCreateCustomerBrokerPurchaseNegotiationException;


    CryptoCustomerWalletAssociatedSetting newEmptyCryptoBrokerWalletAssociatedSetting() throws CantNewEmptyCryptoCustomerWalletAssociatedSettingException;

    void saveWalletSettingAssociated(CryptoCustomerWalletAssociatedSetting setting, String customerWalletpublicKey) throws CantSaveCryptoCustomerWalletSettingException, CantCreateFileException, CantPersistFileException, CantGetSettingsException, CantPersistSettingsException;

    /**
     * Returns a list of provider references which can obtain the ExchangeRate of the given CurrencyPair
     *
     * @param currencyFrom currency from
     * @param currencyTo currency to
     * @return a Map of name/provider reference pairs
     */
    Map<String, CurrencyExchangeRateProviderManager> getProviderReferencesFromCurrencyPair(Currency currencyFrom, Currency currencyTo) throws CantGetProviderException, CantGetProviderInfoException;

    /**
     * Returns a CER provider given its providerId
     *
     * @param providerId the provider's ID
     * @return a CurrencyExchangeRateProviderManager reference
     */
    CurrencyExchangeRateProviderManager getProviderReferenceFromId(UUID providerId) throws CantGetProviderException;

    CryptoCustomerWalletProviderSetting newEmptyCryptoCustomerWalletProviderSetting() throws CantNewEmptyCryptoCustomerWalletProviderSettingException;

    void saveCryptoCustomerWalletProviderSetting(CryptoCustomerWalletProviderSetting setting, String customerWalletpublicKey) throws CantSaveCryptoCustomerWalletSettingException, CantPersistFileException, CantCreateFileException, CantPersistSettingsException, CantGetSettingsException;

    void clearCryptoCustomerWalletProviderSetting(String customerWalletpublicKey) throws CantClearCryptoCustomerWalletSettingException, CantPersistFileException, CantCreateFileException, CantPersistSettingsException, CantGetSettingsException;

    List<CryptoCustomerWalletProviderSetting> getAssociatedProviders(String walletPublicKey) throws FileNotFoundException, CantCreateFileException, CantGetSettingsException, SettingsNotFoundException;

    /**
     * This method returns the CustomerBrokerContractPurchase associated to a negotiationId
     *
     * @param negotiationId
     * @return
     * @throws CantGetListCustomerBrokerContractPurchaseException
     */
    CustomerBrokerContractPurchase getCustomerBrokerContractPurchaseByNegotiationId(
            String negotiationId
    ) throws CantGetListCustomerBrokerContractPurchaseException;

    /**
     * This method returns the currency type from a contract
     *
     * @param customerBrokerContractPurchase
     * @param contractDetailType
     * @return
     * @throws CantGetListPurchaseNegotiationsException
     */
    MoneyType getCurrencyTypeFromContract(
            CustomerBrokerContractPurchase customerBrokerContractPurchase,
            ContractDetailType contractDetailType) throws
            CantGetListPurchaseNegotiationsException;

    /**
     * This method send a payment according the contract elements.
     *
     * @param contractHash
     */
    void sendPayment(String contractHash) throws CantSendPaymentException;

    /**
     * This method execute a Customer Ack Merchandise Business Transaction
     *
     * @param contractHash
     * @throws CantAckMerchandiseException
     */
    ContractStatus ackMerchandise(String contractHash) throws CantAckMerchandiseException;

    /**
     * This method returns the ContractStatus by contractHash/Id
     *
     * @param contractHash
     * @return
     */
    ContractStatus getContractStatus(String contractHash) throws CantGetListCustomerBrokerContractPurchaseException;

    /**
     * This method returns a string with the currency code.
     * @param customerBrokerContractPurchase
     * @param contractDetailType
     * @return
     * @throws CantGetListPurchaseNegotiationsException
     */
    /*String getCurrencyCodeFromContract(
            CustomerBrokerContractPurchase customerBrokerContractPurchase,
            ContractDetailType contractDetailType) throws
            CantGetListPurchaseNegotiationsException;*/

    /**
     * This method returns the currency amount
     * @param customerBrokerContractPurchase
     * @param contractDetailType
     * @return
     * @throws CantGetListPurchaseNegotiationsException
     */
    /*float getCurrencyAmountFromContract(
            CustomerBrokerContractPurchase customerBrokerContractPurchase,
            ContractDetailType contractDetailType) throws
            CantGetListPurchaseNegotiationsException;*/


    /**
     *
     * @param brokerPublicKey
     * @param customerPublicKey
     * @return The basic information of the broker whose publickey equals the parameter passed as publickey.
     * @throws CantListActorConnectionsException
     */
    ActorIdentity getBrokerInfoByPublicKey(String customerPublicKey, String brokerPublicKey) throws CantListActorConnectionsException;


    /**
     * Returns a completionDate in which a specific status was achieved for a specific contract
     *
     * @param contractHash
     * @param contractStatus
     * @param paymentMethod
     * @return a completionDate in which a specific status was achieved for a specific contract
     */
    long getCompletionDateForContractStatus(String contractHash, ContractStatus contractStatus, String paymentMethod);

}
