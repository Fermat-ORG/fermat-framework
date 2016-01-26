package com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractDetailType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationBankAccount;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantGetListActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantGetListCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchase;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.exceptions.CantGetCryptoCustomerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.interfaces.CryptoCustomerIdentity;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantCreateBankAccountPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantCreateCustomerBrokerPurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantCreateLocationPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantDeleteBankAccountPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantGetListPurchaseNegotiationsException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantUpdateBankAccountPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.exceptions.CantCreateCustomerBrokerNewPurchaseNegotiationTransactionException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.IndexInfoSummary;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.MerchandiseExchangeRate;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.WalletManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantGetCryptoBrokerListException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantGetAssociatedCryptoCustomerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantGetCryptoCustomerIdentityListException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantGetCurrentIndexSummaryForCurrenciesOfInterestException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantNewEmptyCryptoCustomerWalletAssociatedSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantNewEmptyCryptoCustomerWalletProviderSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantNewEmptyCustomerBrokerNegotiationInformationException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantSaveCryptoCustomerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CouldNotStartNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.settings.CryptoCustomerWalletAssociatedSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.settings.CryptoCustomerWalletProviderSetting;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantGetProviderInfoException;
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
     * @param customerId the Crypto Customer ID who is going to be associated with this wallet
     * @return true if the association was successful false otherwise
     */
    boolean associateIdentity(String customerId);

    /**
     * @return list of identities associated with this wallet
     */
    List<CryptoCustomerIdentity> getListOfIdentities() throws CantGetCryptoCustomerIdentityListException, CantGetCryptoCustomerIdentityException;

    /**
     * @return a summary of the current market rate for the different currencies the customer is interested
     */
    Collection<IndexInfoSummary> getCurrentIndexSummaryForCurrenciesOfInterest() throws CantGetCurrentIndexSummaryForCurrenciesOfInterestException;

    /**
     * return the list of brokers connected with the crypto customer and the merchandises that they sell
     *
     * @param customerPublicKey the crypto customer public key
     * @return the list of crypto brokers
     */
    Collection<BrokerIdentityBusinessInfo> getListOfConnectedBrokersAndTheirMerchandises(String customerPublicKey) throws CantGetCryptoBrokerListException, CantGetListActorExtraDataException;

    /**
     * return a list of exchange rate info for each merchandise the broker accept as payment
     *
     * @param brokerPublicKey the broker public key
     * @param target          the currency against the exchange rate is going to be calculated
     * @return list of exchange rate info
     */
    Collection<MerchandiseExchangeRate> getListOfBrokerMerchandisesExchangeRate(String brokerPublicKey, FermatEnum target);

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
    boolean isWalletConfigured(String customerWalletPublicKey);

    /**
     * Start a new negotiation with a crypto broker
     *
     * @param customerPublicKey the crypto customer publicKey
     * @param brokerPublicKey   the crypto broker publicKey
     * @param clauses    the initial and mandatory clauses to start a negotiation
     * @return true if the association was successful false otherwise
     */
    boolean startNegotiation(String customerPublicKey, String brokerPublicKey, Collection<ClauseInformation> clauses) throws CouldNotStartNegotiationException, CantCreateCustomerBrokerNewPurchaseNegotiationTransactionException;

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
     *
     * @param negotiation
     * @throws CantCreateCustomerBrokerPurchaseNegotiationException
     */
    void createCustomerBrokerPurchaseNegotiation(CustomerBrokerNegotiationInformation negotiation) throws CantCreateCustomerBrokerPurchaseNegotiationException;



    CryptoCustomerWalletAssociatedSetting newEmptyCryptoBrokerWalletAssociatedSetting() throws CantNewEmptyCryptoCustomerWalletAssociatedSettingException;

    void saveWalletSettingAssociated(CryptoCustomerWalletAssociatedSetting setting, String customerWalletpublicKey) throws CantSaveCryptoCustomerWalletSettingException, CantCreateFileException, CantPersistFileException;

    /**
     * Returns a list of provider references which can obtain the ExchangeRate of the given CurrencyPair
     *
     * @return a Map of name/provider reference pairs
     */
    Map<String, CurrencyExchangeRateProviderManager> getProviderReferencesFromCurrencyPair(Currency currencyFrom, Currency currencyTo) throws CantGetProviderException, CantGetProviderInfoException;

    CryptoCustomerWalletProviderSetting newEmptyCryptoCustomerWalletProviderSetting() throws CantNewEmptyCryptoCustomerWalletProviderSettingException;

    void saveCryptoCustomerWalletProviderSetting(CryptoCustomerWalletProviderSetting setting, String customerWalletpublicKey) throws CantSaveCryptoCustomerWalletSettingException, CantPersistFileException, CantCreateFileException;

    List<CryptoCustomerWalletProviderSetting> getAssociatedProviders(String walletPublicKey);

    /**
     * This method returns the CustomerBrokerContractPurchase associated to a negotiationId
     * @param negotiationId
     * @return
     * @throws CantGetListCustomerBrokerContractPurchaseException
     */
    CustomerBrokerContractPurchase getCustomerBrokerContractPurchaseByNegotiationId(
            String negotiationId
    ) throws CantGetListCustomerBrokerContractPurchaseException;

    /**
     * This method returns the currency type from a contract
     * @param customerBrokerContractPurchase
     * @param contractDetailType
     * @return
     * @throws CantGetListPurchaseNegotiationsException
     */
    CurrencyType getCurrencyTypeFromContract(
            CustomerBrokerContractPurchase customerBrokerContractPurchase,
            ContractDetailType contractDetailType) throws
            CantGetListPurchaseNegotiationsException;

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
}
