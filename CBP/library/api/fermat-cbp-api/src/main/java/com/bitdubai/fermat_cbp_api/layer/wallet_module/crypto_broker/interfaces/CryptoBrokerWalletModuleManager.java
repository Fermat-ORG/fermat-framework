package com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantListActorConnectionsException;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.CantLoadWalletsException;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankAccountType;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantAddNewAccountException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantLoadBankMoneyWalletException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankAccountNumber;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractDetailType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.OriginTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationBankAccount;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationLocations;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantClearBrokerIdentityWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantCreateNewBrokerIdentityWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantGetRelationBetweenBrokerIdentityAndBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantAckPaymentException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantSubmitMerchandiseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantGetListCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSale;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.CantListCryptoBrokerIdentitiesException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentity;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.enums.EarningTransactionState;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantAssociatePairException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantDisassociatePairException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantExtractEarningsException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantListEarningTransactionsException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantListEarningsPairsException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantLoadEarningSettingsException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.EarningsSettingsNotRegisteredException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.PairAlreadyAssociatedException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningTransaction;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsPair;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantCreateBankAccountSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantCreateLocationSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantDeleteBankAccountSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantDeleteLocationSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantGetListSaleNegotiationsException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantUpdateLocationSaleException;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.exceptions.CantSendNegotiationToCryptoCustomerException;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.bank_money_destock.exceptions.CantCreateBankMoneyDestockException;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.bank_money_restock.exceptions.CantCreateBankMoneyRestockException;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.cash_money_destock.exceptions.CantCreateCashMoneyDestockException;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.crypto_money_destock.exceptions.CantCreateCryptoMoneyDestockException;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.crypto_money_restock.exceptions.CantCreateCryptoMoneyRestockException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantClearCryptoBrokerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetAvailableBalanceCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetCryptoBrokerMarketRateException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetCryptoBrokerQuoteException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetCryptoBrokerStockTransactionException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetCryptoBrokerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetStockCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantSaveCryptoBrokerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CryptoBrokerWalletNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerStockTransaction;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.FiatIndex;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.Quote;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletAssociatedSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletProviderSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletSettingSpread;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CantGetAssociatedIdentityException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CantNewEmptyBankAccountException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CantNewEmptyCryptoBrokerWalletAssociatedSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CantNewEmptyCryptoBrokerWalletProviderSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CantNewEmptyCryptoBrokerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CBPWalletsModuleManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.IndexInfoSummary;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.CryptoBrokerWalletPreferenceSettings;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.CurrencyPairAndProvider;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.exceptions.CantGetCryptoBrokerIdentityListException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.exceptions.CantGetProvidersCurrentExchangeRatesException;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.CurrencyPair;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.ExchangeRate;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantGetExchangeRateException;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantGetProviderInfoException;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.UnsupportedCurrencyPairException;
import com.bitdubai.fermat_cer_api.layer.search.exceptions.CantGetProviderException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantCreateCashMoneyWalletException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantGetCashMoneyWalletBalanceException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantGetCashMoneyWalletCurrencyException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantLoadCashMoneyWalletException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantListWalletsException;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * Created by Nelson Ramirez
 * on 22/09/15.
 */
public interface CryptoBrokerWalletModuleManager
        extends CBPWalletsModuleManager<CryptoBrokerWalletPreferenceSettings, ActiveActorIdentityInformation>,
        Serializable {

    /**
     * associate an Identity to this wallet
     *
     * @param brokerPublicKey the Public Key of the Crypto Broker who is going to be associated with this wallet
     */
    boolean associateIdentity(ActorIdentity brokerPublicKey, String brokerWalletPublicKey) throws CantCreateNewBrokerIdentityWalletRelationshipException;

    /**
     * Clear any associated identities to this wallet
     *
     * @param brokerWalletPublicKey the Public Key of the wallet to be cleared of its identity
     */
    void clearAssociatedIdentities(String brokerWalletPublicKey) throws CantClearBrokerIdentityWalletRelationshipException;

    /**
     * @param merchandise
     * @param currencyPayment
     * @param brokerWalletPublicKey
     *
     * @return
     *
     * @throws CantGetCryptoBrokerQuoteException
     */
    Quote getQuote(Currency merchandise, Currency currencyPayment, String brokerWalletPublicKey) throws CantGetCryptoBrokerQuoteException;

    /**
     * Return a list of exchange rates for the desired provider and currency pair, from the current date to the desired number of days back
     *
     * @param indexInfo    object with the necessary info: the currency pair, the provider ID
     * @param numberOfDays the number of days the list will cover
     *
     * @return The list of Exchange Rates
     *
     * @throws CantGetProviderException         Cant get the provider from the CER platform
     * @throws UnsupportedCurrencyPairException The Currency pair fot the selected provider is not supported
     * @throws CantGetExchangeRateException     Cant get current the exchange rate for the currency pair in the provider
     */
    Collection<ExchangeRate> getDailyExchangeRatesFromCurrentDate(IndexInfoSummary indexInfo, int numberOfDays) throws CantGetProviderException, UnsupportedCurrencyPairException, CantGetExchangeRateException;

    /**
     * @param brokerWalletPublicKey the wallet public key
     *
     * @return A summary of the current market rate for the different selected providers
     *
     * @throws CantGetProvidersCurrentExchangeRatesException Cant get current Index Summary for the selected providers
     * @throws CryptoBrokerWalletNotFoundException           Cant find the installed wallet data
     * @throws CantGetCryptoBrokerWalletSettingException     Cant find the settings for the wallet with the public key
     * @throws CantGetProviderException                      Cant get the provider from the CER platform
     * @throws UnsupportedCurrencyPairException              The Currency pair fot the selected provider is not supported
     * @throws CantGetExchangeRateException                  Cant get current the exchange rate for the currency pair in the provider
     * @throws InvalidParameterException                     Invalid parameters
     */
    Collection<IndexInfoSummary> getProvidersCurrentExchangeRates(String brokerWalletPublicKey) throws CantGetProvidersCurrentExchangeRatesException, CryptoBrokerWalletNotFoundException, CantGetCryptoBrokerWalletSettingException, CantGetProviderException, UnsupportedCurrencyPairException, CantGetExchangeRateException, InvalidParameterException;

    /**
     * @param walletPublicKey
     *
     * @return
     *
     * @throws CantListCryptoBrokerIdentitiesException
     * @throws CantGetRelationBetweenBrokerIdentityAndBrokerWalletException
     */
    boolean haveAssociatedIdentity(String walletPublicKey) throws CantListCryptoBrokerIdentitiesException, CantGetRelationBetweenBrokerIdentityAndBrokerWalletException;

    /**
     * @param walletPublicKey
     *
     * @return
     *
     * @throws CantListCryptoBrokerIdentitiesException
     * @throws CantGetRelationBetweenBrokerIdentityAndBrokerWalletException
     * @throws CantGetAssociatedIdentityException
     */
    CryptoBrokerIdentity getAssociatedIdentity(String walletPublicKey) throws CantListCryptoBrokerIdentitiesException, CantGetRelationBetweenBrokerIdentityAndBrokerWalletException, CantGetAssociatedIdentityException;

    /**
     * @return list of identities associated with this wallet
     */
    List<CryptoBrokerIdentity> getListOfIdentities() throws CantGetCryptoBrokerIdentityListException, CantListCryptoBrokerIdentitiesException;

    /**
     * @param currencyCode
     * @param brokerWalletPublicKey
     *
     * @return
     *
     * @throws CryptoBrokerWalletNotFoundException
     * @throws CantGetCryptoBrokerWalletSettingException
     */
    List<MoneyType> getPaymentMethods(String currencyCode, String brokerWalletPublicKey) throws CryptoBrokerWalletNotFoundException, CantGetCryptoBrokerWalletSettingException;

    /**
     * @param currencyCode
     * @param brokerWalletPublicKey
     *
     * @return
     *
     * @throws CryptoBrokerWalletNotFoundException
     * @throws CantGetCryptoBrokerWalletSettingException
     * @throws CantLoadBankMoneyWalletException
     */
    List<String> getAccounts(String currencyCode, String brokerWalletPublicKey) throws CryptoBrokerWalletNotFoundException, CantGetCryptoBrokerWalletSettingException, CantLoadBankMoneyWalletException;

    /**
     * @param negotiationInfo
     *
     * @throws CantSendNegotiationToCryptoCustomerException
     */
    void sendNegotiation(CustomerBrokerNegotiationInformation negotiationInfo) throws CantSendNegotiationToCryptoCustomerException;

    /**
     * @param brokerPublicKey
     * @param customerPublicKey
     *
     * @return The basic information of the customer whose publickey equals the parameter passed as publickey.
     *
     * @throws CantListActorConnectionsException
     */
    ActorIdentity getCustomerInfoByPublicKey(String brokerPublicKey, String customerPublicKey) throws CantListActorConnectionsException;

    /**
     * This method list all wallet installed in device, start the transaction
     */
    List<com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet> getInstallWallets()
            throws CantListWalletsException;

    /**
     * @return
     *
     * @throws CantNewEmptyCryptoBrokerWalletSettingException
     */
    CryptoBrokerWalletSettingSpread newEmptyCryptoBrokerWalletSetting()
            throws CantNewEmptyCryptoBrokerWalletSettingException;

    /**
     * @return
     *
     * @throws CantNewEmptyCryptoBrokerWalletAssociatedSettingException
     */
    CryptoBrokerWalletAssociatedSetting newEmptyCryptoBrokerWalletAssociatedSetting()
            throws CantNewEmptyCryptoBrokerWalletAssociatedSettingException;

    /**
     * @return
     *
     * @throws CantNewEmptyCryptoBrokerWalletProviderSettingException
     */
    CryptoBrokerWalletProviderSetting newEmptyCryptoBrokerWalletProviderSetting()
            throws CantNewEmptyCryptoBrokerWalletProviderSettingException;

    /**
     * @param bankName
     * @param bankAccountType
     * @param alias
     * @param account
     * @param currencyType
     *
     * @return
     *
     * @throws CantNewEmptyBankAccountException
     */
    BankAccountNumber newEmptyBankAccountNumber(String bankName, BankAccountType bankAccountType, String alias, String account, FiatCurrency currencyType)
            throws CantNewEmptyBankAccountException;

    /**
     * @param bankAccountNumber
     * @param walletPublicKey
     *
     * @throws CantAddNewAccountException
     * @throws CantLoadBankMoneyWalletException
     */
    void addNewAccount(BankAccountNumber bankAccountNumber, String walletPublicKey)
            throws CantAddNewAccountException, CantLoadBankMoneyWalletException;

    /**
     * Create a Cash wallet
     *
     * @param walletPublicKey the cash wallet public key
     * @param fiatCurrency    the currency this wallet is going to manage
     *
     * @throws CantCreateCashMoneyWalletException
     */
    void createCashMoneyWallet(String walletPublicKey, FiatCurrency fiatCurrency)
            throws CantCreateCashMoneyWalletException;

    /**
     * @param cryptoBrokerWalletSettingSpread
     * @param publicKeyWalletCryptoBrokerInstall
     *
     * @throws CantSaveCryptoBrokerWalletSettingException
     * @throws CryptoBrokerWalletNotFoundException
     * @throws CantGetCryptoBrokerWalletSettingException
     */
    void saveWalletSetting(CryptoBrokerWalletSettingSpread cryptoBrokerWalletSettingSpread, String publicKeyWalletCryptoBrokerInstall)
            throws CantSaveCryptoBrokerWalletSettingException, CryptoBrokerWalletNotFoundException, CantGetCryptoBrokerWalletSettingException;

    /**
     * @param publicKeyWalletCryptoBrokerInstall
     *
     * @throws CantClearCryptoBrokerWalletSettingException
     * @throws CryptoBrokerWalletNotFoundException
     * @throws CantGetCryptoBrokerWalletSettingException
     */
    void clearWalletSetting(String publicKeyWalletCryptoBrokerInstall)
            throws CantClearCryptoBrokerWalletSettingException, CryptoBrokerWalletNotFoundException, CantGetCryptoBrokerWalletSettingException;

    /**
     * @param cryptoBrokerWalletAssociatedSetting
     * @param publicKeyWalletCryptoBrokerInstall
     *
     * @throws CantGetCryptoBrokerWalletSettingException
     * @throws CryptoBrokerWalletNotFoundException
     * @throws CantSaveCryptoBrokerWalletSettingException
     */
    void saveWalletSettingAssociated(CryptoBrokerWalletAssociatedSetting cryptoBrokerWalletAssociatedSetting, String publicKeyWalletCryptoBrokerInstall)
            throws CantGetCryptoBrokerWalletSettingException, CryptoBrokerWalletNotFoundException, CantSaveCryptoBrokerWalletSettingException;

    /**
     * @param publicKeyWalletCryptoBrokerInstall
     *
     * @throws CryptoBrokerWalletNotFoundException
     * @throws CantGetCryptoBrokerWalletSettingException
     * @throws CantClearCryptoBrokerWalletSettingException
     */
    void clearAssociatedWalletSettings(String publicKeyWalletCryptoBrokerInstall, Platforms platform)
            throws CryptoBrokerWalletNotFoundException, CantGetCryptoBrokerWalletSettingException, CantClearCryptoBrokerWalletSettingException;

    /**
     * @param publicKeyWalletCryptoBrokerInstall
     *
     * @return
     *
     * @throws CryptoBrokerWalletNotFoundException
     * @throws CantGetCryptoBrokerWalletSettingException
     */
    boolean isWalletConfigured(String publicKeyWalletCryptoBrokerInstall)
            throws CryptoBrokerWalletNotFoundException, CantGetCryptoBrokerWalletSettingException, EarningsSettingsNotRegisteredException,
            CantListEarningsPairsException, CantLoadEarningSettingsException;

    /**
     * @param location
     * @param uri
     *
     * @throws CantCreateLocationSaleException
     */
    void createNewLocation(String location, String uri)
            throws CantCreateLocationSaleException;

    /**
     * @param location
     *
     * @throws CantUpdateLocationSaleException
     */
    void updateLocation(NegotiationLocations location)
            throws CantUpdateLocationSaleException;

    /**
     * @param location
     *
     * @throws CantDeleteLocationSaleException
     */
    void deleteLocation(NegotiationLocations location)
            throws CantDeleteLocationSaleException;

    /**
     * @param walletPublicKey
     *
     * @return
     *
     * @throws CantLoadBankMoneyWalletException
     */
    List<BankAccountNumber> getAccounts(String walletPublicKey)
            throws CantLoadBankMoneyWalletException;

    /**
     * Returns an object which allows getting and modifying (credit/debit) the Book Balance
     *
     * @return A FiatCurrency object
     */
    FiatCurrency getCashCurrency(String walletPublicKey) throws CantGetCashMoneyWalletCurrencyException, CantLoadCashMoneyWalletException;

    /**
     * Method that create a Restock Transaction in a bank wallet
     *
     * @param publicKeyActor      the actor (customer/broker) public key
     * @param fiatCurrency        the currency of the wallet
     * @param cbpWalletPublicKey  the broker or customer wallet public key
     * @param bankWalletPublicKey the bank wallet public key
     * @param bankAccount         the bank account where is going to be made the restock
     * @param amount              the amount to restock
     * @param memo                a memo for this transaction
     * @param priceReference      reference price
     * @param originTransaction   the transaction origin
     * @param originTransactionId the transaction ID
     *
     * @throws CantCreateBankMoneyRestockException
     */
    void createTransactionRestockBank(String publicKeyActor, FiatCurrency fiatCurrency, String cbpWalletPublicKey, String bankWalletPublicKey,
                                      String bankAccount, BigDecimal amount, String memo, BigDecimal priceReference, OriginTransaction originTransaction,
                                      String originTransactionId)
            throws CantCreateBankMoneyRestockException;

    /**
     * @param publicKeyActor
     * @param fiatCurrency
     * @param cbpWalletPublicKey
     * @param bankWalletPublicKey
     * @param bankAccount
     * @param amount
     * @param memo
     * @param priceReference
     * @param originTransaction
     * @param originTransactionId
     *
     * @throws CantCreateBankMoneyDestockException
     */
    void createTransactionDestockBank(String publicKeyActor, FiatCurrency fiatCurrency, String cbpWalletPublicKey, String bankWalletPublicKey,
                                      String bankAccount, BigDecimal amount, String memo, BigDecimal priceReference,
                                      OriginTransaction originTransaction, String originTransactionId)
            throws CantCreateBankMoneyDestockException;

    /**
     * @param publicKeyActor
     * @param fiatCurrency
     * @param cbpWalletPublicKey
     * @param cshWalletPublicKey
     * @param cashReference
     * @param amount
     * @param memo
     * @param priceReference
     * @param originTransaction
     * @param originTransactionId
     *
     * @throws com.bitdubai.fermat_cbp_api.layer.stock_transactions.cash_money_restock.exceptions.CantCreateCashMoneyRestockException
     */
    void createTransactionRestockCash(String publicKeyActor, FiatCurrency fiatCurrency, String cbpWalletPublicKey, String cshWalletPublicKey,
                                      String cashReference, BigDecimal amount, String memo, BigDecimal priceReference,
                                      OriginTransaction originTransaction, String originTransactionId)
            throws com.bitdubai.fermat_cbp_api.layer.stock_transactions.cash_money_restock.exceptions.CantCreateCashMoneyRestockException;

    /**
     * Method that create the transaction Destock
     *
     * @param publicKeyActor
     * @param fiatCurrency
     * @param cbpWalletPublicKey
     * @param cshWalletPublicKey
     * @param cashReference
     * @param amount
     * @param memo
     * @param priceReference
     * @param originTransaction
     *
     * @throws CantCreateCashMoneyDestockException
     */
    void createTransactionDestockCash(String publicKeyActor, FiatCurrency fiatCurrency, String cbpWalletPublicKey, String cshWalletPublicKey,
                                      String cashReference, BigDecimal amount, String memo, BigDecimal priceReference,
                                      OriginTransaction originTransaction, String originTransactionId)
            throws CantCreateCashMoneyDestockException;

    /**
     * Method that create the transaction Destock
     *
     * @param publicKeyActor
     * @param cryptoCurrency
     * @param cbpWalletPublicKey
     * @param cryWalletPublicKey
     * @param amount
     * @param memo
     * @param priceReference
     * @param originTransaction
     *
     * @throws CantCreateCryptoMoneyRestockException
     */
    void createTransactionRestockCrypto(String publicKeyActor, CryptoCurrency cryptoCurrency, String cbpWalletPublicKey, String cryWalletPublicKey,
                                        BigDecimal amount, String memo, BigDecimal priceReference, OriginTransaction originTransaction,
                                        String originTransactionId, BlockchainNetworkType blockchainNetworkType)
            throws CantCreateCryptoMoneyRestockException;


    /**
     * Method that create the transaction Destock
     *
     * @param publicKeyActor
     * @param cryptoCurrency
     * @param cbpWalletPublicKey
     * @param cryWalletPublicKey
     * @param amount
     * @param memo
     * @param priceReference
     * @param originTransaction
     *
     * @throws CantCreateCryptoMoneyDestockException
     */
    void createTransactionDestockCrypto(String publicKeyActor, CryptoCurrency cryptoCurrency, String cbpWalletPublicKey, String cryWalletPublicKey,
                                        BigDecimal amount, String memo, BigDecimal priceReference, OriginTransaction originTransaction,
                                        String originTransactionId, BlockchainNetworkType blockchainNetworkType)
            throws CantCreateCryptoMoneyDestockException;

    /**
     * This method load the list CryptoBrokerStockTransaction
     *
     * @param merchandise
     * @param fiatCurrency
     * @param moneyType
     *
     * @return FiatIndex
     *
     * @throws CantGetCryptoBrokerMarketRateException
     */
    FiatIndex getMarketRate(Currency merchandise, FiatCurrency fiatCurrency, MoneyType moneyType, String walletPublicKey)
            throws CantGetCryptoBrokerMarketRateException, CryptoBrokerWalletNotFoundException;

    /**
     * @param ContractId the contract ID
     *
     * @return a {@link CustomerBrokerContractSale} with the contract information of the giver ID
     *
     * @throws CantGetListCustomerBrokerContractSaleException cant get the list of contracts from the plugin
     */
    CustomerBrokerContractSale getCustomerBrokerContractSaleForContractId(final String ContractId)
            throws CantGetListCustomerBrokerContractSaleException;

    /**
     * Return a list of provider related settings associated with the wallet of the given public key
     *
     * @param walletPublicKey the broker wallet public key
     *
     * @return The list Provider related settings associated with this wallet
     *
     * @throws CantGetCryptoBrokerWalletSettingException Cant get the seetings from the broker wallet plugin
     * @throws CryptoBrokerWalletNotFoundException       the wallet associated with the passed public key os not found
     */
    List<CryptoBrokerWalletProviderSetting> getCryptoBrokerWalletProviderSettings(String walletPublicKey)
            throws CantGetCryptoBrokerWalletSettingException, CryptoBrokerWalletNotFoundException;

    /**
     * Returns a list of Providers able to obtain the  CurrencyPair for providers wallet associated
     *
     * @return a map containing both the ProviderID and the CurrencyPair for providers wallet associated
     */
    Map<String, CurrencyPair> getWalletProviderAssociatedCurrencyPairs(CurrencyPair currencyPair, String walletPublicKey)
            throws CryptoBrokerWalletNotFoundException, CantGetCryptoBrokerWalletSettingException;

    /**
     * This method load the list CryptoBrokerWalletProviderSetting
     *
     * @param walletPublicKey the broker wallet public key
     *
     * @return The list of settings associated with this wallet
     *
     * @throws CantGetCryptoBrokerWalletSettingException Cant get the seetings from the broker wallet plugin
     * @throws CryptoBrokerWalletNotFoundException       the wallet associated with the passed public key os not found
     */
    List<CryptoBrokerWalletAssociatedSetting> getCryptoBrokerWalletAssociatedSettings(String walletPublicKey)
            throws CantGetCryptoBrokerWalletSettingException, CryptoBrokerWalletNotFoundException;

    /**
     * This method load the instance saveCryptoBrokerWalletSpreadSetting
     *
     * @param walletPublicKey the broker wallet public key
     *
     * @return the object with the spread and other wallet settings
     *
     * @throws CantGetCryptoBrokerWalletSettingException
     * @throws CryptoBrokerWalletNotFoundException
     */
    CryptoBrokerWalletSettingSpread getCryptoBrokerWalletSpreadSetting(String walletPublicKey)
            throws CantGetCryptoBrokerWalletSettingException, CryptoBrokerWalletNotFoundException;

    /**
     * Returns an exchange rate of a given date, for a specific currencyPair
     *
     * @return an exchangeRate object
     */
    ExchangeRate getExchangeRateFromDate(Currency currencyFrom, Currency currencyTo, Calendar calendar, UUID providerId)
            throws UnsupportedCurrencyPairException, CantGetExchangeRateException, CantGetProviderException;

    /**
     * Given a start and end timestamp and a currencyPair, returns a list of DAILY ExchangeRates
     *
     * @return a list of exchangeRate objects
     */
    Collection<ExchangeRate> getDailyExchangeRatesForPeriod(Currency currencyFrom, Currency currencyTo, Calendar startCalendar, Calendar endCalendar,
                                                            UUID providerId)
            throws UnsupportedCurrencyPairException, CantGetExchangeRateException, CantGetProviderException;

    /**
     * This method load the list CryptoBrokerStockTransaction
     *
     * @param merchandise
     * @param moneyType
     * @param offset
     * @param timeStamp
     *
     * @return List<CryptoBrokerStockTransaction>
     *
     * @throws CantGetCryptoBrokerStockTransactionException
     */
    List<CryptoBrokerStockTransaction> getStockHistory(Currency merchandise, MoneyType moneyType, int offset, long timeStamp, String walletPublicKey)
            throws CantGetCryptoBrokerStockTransactionException;

    /**
     * @param merchandise
     * @param walletPublicKey
     *
     * @return
     *
     * @throws CantGetAvailableBalanceCryptoBrokerWalletException
     * @throws CryptoBrokerWalletNotFoundException
     * @throws CantGetStockCryptoBrokerWalletException
     * @throws CantStartPluginException
     */
    float getAvailableBalance(Currency merchandise, String walletPublicKey)
            throws CantGetAvailableBalanceCryptoBrokerWalletException, CryptoBrokerWalletNotFoundException,
            CantGetStockCryptoBrokerWalletException, CantStartPluginException;

    /**
     * Returns a list of provider references which can obtain the ExchangeRate of the given CurrencyPair
     *
     * @param currencyFrom
     * @param currencyTo
     *
     * @return a Collection of provider reference pairs
     */
    Collection<CurrencyPairAndProvider> getProviderReferencesFromCurrencyPair(Currency currencyFrom, Currency currencyTo)
            throws CantGetProviderException, CantGetProviderInfoException;

    /**
     * This method save the instance CryptoBrokerWalletProviderSetting
     *
     * @param cryptoBrokerWalletProviderSetting
     *
     * @return
     *
     * @throws CantSaveCryptoBrokerWalletSettingException
     */
    void saveCryptoBrokerWalletProviderSetting(CryptoBrokerWalletProviderSetting cryptoBrokerWalletProviderSetting, String walletPublicKey)
            throws CantSaveCryptoBrokerWalletSettingException, CryptoBrokerWalletNotFoundException, CantGetCryptoBrokerWalletSettingException;

    /**
     * This method clears the instance CryptoBrokerWalletProviderSetting
     *
     * @return
     *
     * @throws CantClearCryptoBrokerWalletSettingException
     */
    void clearCryptoBrokerWalletProviderSetting(String walletPublicKey)
            throws CantClearCryptoBrokerWalletSettingException, CryptoBrokerWalletNotFoundException, CantGetCryptoBrokerWalletSettingException;

    /**
     * @param bankAccount
     *
     * @throws CantCreateBankAccountSaleException
     */
    void createNewBankAccount(NegotiationBankAccount bankAccount)
            throws CantCreateBankAccountSaleException;

    /**
     * @param bankAccount
     *
     * @throws CantDeleteBankAccountSaleException
     */
    void deleteBankAccount(NegotiationBankAccount bankAccount)
            throws CantDeleteBankAccountSaleException;

    /**
     * Returns the Balance this BankMoneyWalletBalance belongs to. (Can be available or book)
     *
     * @return A double, containing the balance.
     */
    double getBalanceBankWallet(String walletPublicKey, String accountNumber)
            throws CantCalculateBalanceException, CantLoadBankMoneyWalletException;

    /**
     * Returns the Balance this CashMoneyWalletBalance belongs to. (Can be available or book)
     *
     * @return A BigDecimal, containing the balance.
     */
    BigDecimal getBalanceCashWallet(String walletPublicKey)
            throws CantGetCashMoneyWalletBalanceException, CantLoadCashMoneyWalletException;

    /**
     * Checks if wallet exists in wallet database
     */
    boolean cashMoneyWalletExists(String walletPublicKey);

    /**
     * Returns the Balance this BitcoinWalletBalance belongs to. (Can be available or book)
     *
     * @return A BigDecimal, containing the balance.
     */
    long getBalanceBitcoinWallet(String walletPublicKey)
            throws com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantCalculateBalanceException, CantLoadWalletsException;

    /**
     * This method returns the CustomerBrokerContractSale associated to a negotiationId
     *
     * @param negotiationId
     *
     * @return
     *
     * @throws CantGetListCustomerBrokerContractSaleException
     */
    CustomerBrokerContractSale getCustomerBrokerContractSaleByNegotiationId(String negotiationId)
            throws CantGetListCustomerBrokerContractSaleException;

    /**
     * This method returns the currency type from a contract
     *
     * @param customerBrokerContractSale
     * @param contractDetailType
     *
     * @return
     *
     * @throws CantGetListSaleNegotiationsException
     */
    MoneyType getMoneyTypeFromContract(CustomerBrokerContractSale customerBrokerContractSale, ContractDetailType contractDetailType)
            throws CantGetListSaleNegotiationsException;

    /**
     * This method returns the ContractStatus by contractHash/Id
     *
     * @param contractHash
     *
     * @return
     *
     * @throws CantGetListCustomerBrokerContractSaleException
     */
    ContractStatus getContractStatus(String contractHash)
            throws CantGetListCustomerBrokerContractSaleException;

    /**
     * This method send a merchandise according the contract elements.
     *
     * @param contractHash
     */
    void submitMerchandise(String contractHash)
            throws CantSubmitMerchandiseException;

    /**
     * This method send a merchandise according the contract elements.
     *
     * @param contractHash
     */
    boolean stockInTheWallet(String contractHash)
            throws CantSubmitMerchandiseException;
    /**
     * This method execute a Broker Ack payment Business Transaction
     *
     * @param contractHash
     *
     * @throws CantAckPaymentException
     */
    ContractStatus ackPayment(String contractHash)
            throws CantAckPaymentException;

    /**
     * Through the method <code>associatePair</code> you can associate an earnings pair to the wallet in which we're working.
     * Automatically will be generated an UUID to the earnings pair, with it, we can identify the same.
     *
     * @param earningCurrency        the currency which we decided to extract the earnings.
     * @param linkedCurrency         the currency that we're linking to the previous selected currency to conform the pair.
     * @param earningWalletPublicKey the wallet's public key that we're associating and where we will deposit the earnings.
     * @param brokerWalletPublicKey  the broker wallet's public key who we're associating this earning setting
     *
     * @return an instance of the well formed associated EarningPair-
     *
     * @throws CantAssociatePairException       if something goes wrong.
     * @throws PairAlreadyAssociatedException   if the pair is already associated.
     * @throws CantLoadEarningSettingsException if something goes wrong trying to get the earning settings.
     */
    EarningsPair addEarningsPairToEarningSettings(Currency earningCurrency, Currency linkedCurrency, String earningWalletPublicKey,
                                                  String brokerWalletPublicKey)
            throws CantLoadEarningSettingsException, CantAssociatePairException, PairAlreadyAssociatedException;

    /**
     * The method <code>clearEarningPairsFromEarningSettings</code> clears (disassotiates) all earning pairs.
     *
     * @param brokerWalletPublicKey the broker wallet's public key
     *
     * @throws CantDisassociatePairException    if something goes wrong.
     * @throws CantLoadEarningSettingsException if something goes wrong trying to get the earning settings.
     */
    void clearEarningPairsFromEarningSettings(String brokerWalletPublicKey)
            throws CantLoadEarningSettingsException, CantDisassociatePairException;

    /**
     * Return the Earning Pairs associated with the given broker wallet public key
     *
     * @param brokerWalletPublicKey the  broker wallet public key
     *
     * @return the list of earning pair or a empty list if nothing was found
     *
     * @throws CantLoadEarningSettingsException
     * @throws EarningsSettingsNotRegisteredException
     * @throws CantListEarningsPairsException
     */
    List<EarningsPair> getEarningsPairs(String brokerWalletPublicKey)
            throws CantLoadEarningSettingsException, EarningsSettingsNotRegisteredException, CantListEarningsPairsException;

    /**
     * Extract the earnings from the broker wallet and send the merchandise to the wallet contained en the {@link EarningsPair}
     *
     * @param earningsPair        the earnings pair whit the info about which wallet and currencies is going to extract the merchandises
     * @param earningTransactions the list of earning transactions which contain the amount of merchandise to extract
     *
     * @throws CantExtractEarningsException
     */
    boolean extractEarnings(EarningsPair earningsPair, List<EarningTransaction> earningTransactions) throws CantExtractEarningsException;

    List<EarningTransaction> searchEarnings(EarningsPair earningsPair, EarningTransactionState state) throws CantListEarningTransactionsException;

    List<EarningTransaction> searchEarnings(EarningsPair earningsPair) throws CantListEarningTransactionsException;
}
