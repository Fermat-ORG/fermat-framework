package com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantLoadBankMoneyWalletException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankAccountNumber;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStepStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.OriginTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationBankAccount;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationLocations;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantGetListCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSale;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.CantListCryptoBrokerIdentitiesException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentity;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantCreateBankAccountSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantCreateLocationSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantDeleteBankAccountSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantDeleteLocationSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantUpdateLocationSaleException;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.bank_money_destock.exceptions.CantCreateBankMoneyDestockException;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.bank_money_restock.exceptions.CantCreateBankMoneyRestockException;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.cash_money_destock.exceptions.CantCreateCashMoneyDestockException;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.cash_money_restock.exceptions.CantCreateCashMoneyRestockException;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.crypto_money_destock.exceptions.CantCreateCryptoMoneyDestockException;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.crypto_money_restock.exceptions.CantCreateCryptoMoneyRestockException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetAvailableBalanceCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetCryptoBrokerMarketRateException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetCryptoBrokerStockTransactionException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetCryptoBrokerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetStockCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantSaveCryptoBrokerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CryptoBrokerWalletNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerStockTransaction;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.FiatIndex;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletAssociatedSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletProviderSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletSettingSpread;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CantNewEmptyCryptoBrokerWalletAssociatedSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CantNewEmptyCryptoBrokerWalletProviderSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CantNewEmptyCryptoBrokerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CantNewEmptyNegotiationBankAccountException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.IndexInfoSummary;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.NegotiationStep;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.WalletManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.exceptions.CantGetCryptoBrokerIdentityListException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.exceptions.CantGetCurrentIndexSummaryForStockCurrenciesException;

import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_cer_api.all_definition.enums.TimeUnit;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.CurrencyPair;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.ExchangeRate;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantGetExchangeRateException;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.UnsupportedCurrencyPairException;
import com.bitdubai.fermat_cer_api.layer.provider.interfaces.CurrencyExchangeRateProviderManager;
import com.bitdubai.fermat_cer_api.layer.search.exceptions.CantGetProviderException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantGetCashMoneyWalletBalanceException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantGetCashMoneyWalletCurrencyException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantLoadCashMoneyWalletException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantListWalletsException;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Created by nelson on 22/09/15.
 */
public interface CryptoBrokerWalletManager extends WalletManager {
    //TODO: Documentar
    /**
     * associate an Identity to this wallet
     *
     * @param brokerPublicKey the Public Key of the Crypto Broker who is going to be associated with this wallet
     */
    boolean associateIdentity(String brokerPublicKey);

    /**
     * @return a summary of the current market rate for the different currencies the broker have as stock
     */
    Collection<IndexInfoSummary> getCurrentIndexSummaryForStockCurrencies() throws CantGetCurrentIndexSummaryForStockCurrenciesException;

    /**
     * @return list of identities associated with this wallet
     */
    List<CryptoBrokerIdentity> getListOfIdentities() throws CantGetCryptoBrokerIdentityListException, CantListCryptoBrokerIdentitiesException;

    List<String> getPaymentMethods(String currencyToSell);

    List<NegotiationStep> getSteps(CustomerBrokerNegotiationInformation negotiationInfo);

    void modifyNegotiationStepValues(NegotiationStep step, NegotiationStepStatus status, String... newValues);

    boolean isNothingLeftToConfirm(List<NegotiationStep> dataSet);

    CustomerBrokerNegotiationInformation sendNegotiationSteps(CustomerBrokerNegotiationInformation data, List<NegotiationStep> dataSet);

     /**
     * This method list all wallet installed in device, start the transaction
     */
    List<com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet> getInstallWallets()  throws CantListWalletsException;

    CryptoBrokerWalletSettingSpread newEmptyCryptoBrokerWalletSetting() throws CantNewEmptyCryptoBrokerWalletSettingException;

    CryptoBrokerWalletAssociatedSetting newEmptyCryptoBrokerWalletAssociatedSetting() throws CantNewEmptyCryptoBrokerWalletAssociatedSettingException;

    CryptoBrokerWalletProviderSetting newEmptyCryptoBrokerWalletProviderSetting() throws CantNewEmptyCryptoBrokerWalletProviderSettingException;

    NegotiationBankAccount newEmptyNegotiationBankAccount(UUID bankAccountId, String bankAccount, FiatCurrency fiatCurrency) throws CantNewEmptyNegotiationBankAccountException;

    void saveWalletSetting(CryptoBrokerWalletSettingSpread cryptoBrokerWalletSettingSpread, String publicKeyWalletCryptoBrokerInstall) throws CantSaveCryptoBrokerWalletSettingException, CryptoBrokerWalletNotFoundException, CantGetCryptoBrokerWalletSettingException;

    void saveWalletSettingAssociated(CryptoBrokerWalletAssociatedSetting cryptoBrokerWalletAssociatedSetting, String publicKeyWalletCryptoBrokerInstall) throws CantGetCryptoBrokerWalletSettingException, CryptoBrokerWalletNotFoundException, CantSaveCryptoBrokerWalletSettingException;

    boolean isWalletConfigured(String publicKeyWalletCryptoBrokerInstall) throws CryptoBrokerWalletNotFoundException, CantGetCryptoBrokerWalletSettingException;

    /**
     *
     * @param location
     * @param uri
     * @throws CantCreateLocationSaleException
     */
    void createNewLocation(String location, String uri) throws CantCreateLocationSaleException;

    /**
     *
     * @param location
     * @throws CantUpdateLocationSaleException
     */
    void updateLocation(NegotiationLocations location) throws CantUpdateLocationSaleException;

    /**
     *
     * @param location
     * @throws CantDeleteLocationSaleException
     */
    void deleteLocation(NegotiationLocations location) throws CantDeleteLocationSaleException;

    List<BankAccountNumber> getAccounts(String walletPublicKey) throws CantLoadBankMoneyWalletException;

    /**
     * Returns an object which allows getting and modifying (credit/debit) the Book Balance
     *
     * @return A FiatCurrency object
     */
    FiatCurrency getCashCurrency(String walletPublicKey) throws CantGetCashMoneyWalletCurrencyException, CantLoadCashMoneyWalletException;

    /**
     * Method that create the transaction Restock
     *
     * @param publicKeyActor
     * @param fiatCurrency
     * @param cbpWalletPublicKey
     * @param cbpWalletPublicKey
     * @param bankWalletPublicKey
     * @param bankAccount
     * @param amount
     * @param memo
     * @param priceReference
     * @param originTransaction
     *
     * @throws CantCreateBankMoneyRestockException
     */

    void createTransactionRestockBank(
            String       publicKeyActor,
            FiatCurrency fiatCurrency,
            String       cbpWalletPublicKey,
            String       bankWalletPublicKey,
            String       bankAccount,
            BigDecimal amount,
            String       memo,
            BigDecimal   priceReference,
            OriginTransaction originTransaction
    ) throws com.bitdubai.fermat_cbp_api.layer.stock_transactions.bank_money_restock.exceptions.CantCreateBankMoneyRestockException;

    /**
     * Method that create the transaction Destock
     *
     * @param publicKeyActor
     * @param fiatCurrency
     * @param cbpWalletPublicKey
     * @param cbpWalletPublicKey
     * @param bankWalletPublicKey
     * @param bankAccount
     * @param amount
     * @param memo
     * @param priceReference
     * @param originTransaction
     *
     * @throws CantCreateBankMoneyDestockException
     */
    void createTransactionDestockBank(
            String publicKeyActor,
            FiatCurrency fiatCurrency,
            String cbpWalletPublicKey,
            String bankWalletPublicKey,
            String bankAccount,
            BigDecimal amount,
            String memo,
            BigDecimal priceReference,
            OriginTransaction originTransaction
    ) throws CantCreateBankMoneyDestockException;

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
     * @throws CantCreateCashMoneyRestockException
     */
    void createTransactionRestockCash(
            String publicKeyActor,
            FiatCurrency fiatCurrency,
            String cbpWalletPublicKey,
            String cshWalletPublicKey,
            String cashReference,
            BigDecimal amount,
            String memo,
            BigDecimal priceReference,
            OriginTransaction originTransaction
    ) throws com.bitdubai.fermat_cbp_api.layer.stock_transactions.cash_money_restock.exceptions.CantCreateCashMoneyRestockException;

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
    void createTransactionDestockCash(
            String publicKeyActor,
            FiatCurrency fiatCurrency,
            String cbpWalletPublicKey,
            String cshWalletPublicKey,
            String cashReference,
            BigDecimal amount,
            String memo,
            BigDecimal priceReference,
            OriginTransaction originTransaction
    ) throws CantCreateCashMoneyDestockException;

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
    void createTransactionRestockCrypto(
            String publicKeyActor,
            CryptoCurrency cryptoCurrency,
            String cbpWalletPublicKey,
            String cryWalletPublicKey,
            BigDecimal amount,
            String memo,
            BigDecimal priceReference,
            OriginTransaction originTransaction
    ) throws CantCreateCryptoMoneyRestockException;


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
    void createTransactionDestockCrypto(
            String publicKeyActor,
            CryptoCurrency cryptoCurrency,
            String cbpWalletPublicKey,
            String cryWalletPublicKey,
            BigDecimal amount,
            String memo,
            BigDecimal priceReference,
            OriginTransaction originTransaction
    ) throws CantCreateCryptoMoneyDestockException;

    /**
     * This method load the list CryptoBrokerStockTransaction
     * @param merchandise
     * @param fiatCurrency
     * @param currencyType
     * @return FiatIndex
     * @exception CantGetCryptoBrokerMarketRateException
     */
    FiatIndex getMarketRate(FermatEnum merchandise, FiatCurrency fiatCurrency, CurrencyType currencyType, String walletPublicKey) throws CantGetCryptoBrokerMarketRateException, CryptoBrokerWalletNotFoundException;

   CustomerBrokerNegotiationInformation setMemo(String memo, CustomerBrokerNegotiationInformation data);

    /**
     *
     * @param ContractId
     * @return a CustomerBrokerContractSale with information of contract with ContractId
     * @throws CantGetListCustomerBrokerContractSaleException
     */
    CustomerBrokerContractSale getCustomerBrokerContractSaleForContractId(final String ContractId) throws CantGetListCustomerBrokerContractSaleException;

    /**
     * This method load the list CryptoBrokerWalletProviderSetting
     * @param
     * @return List<CryptoBrokerWalletProviderSetting>
     * @exception CantGetCryptoBrokerWalletSettingException
     */
    List<CryptoBrokerWalletProviderSetting> getCryptoBrokerWalletProviderSettings(String walletPublicKey) throws CantGetCryptoBrokerWalletSettingException, CryptoBrokerWalletNotFoundException;

    /**
     * This method load the list CryptoBrokerWalletProviderSetting
     * @param
     * @return List<CryptoBrokerWalletAssociatedSetting>
     * @exception CantGetCryptoBrokerWalletSettingException
     */
    List<CryptoBrokerWalletAssociatedSetting> getCryptoBrokerWalletAssociatedSettings(String walletPublicKey) throws CantGetCryptoBrokerWalletSettingException, CryptoBrokerWalletNotFoundException;

    /**
     * Returns an exchange rate of a given date, for a specific currencyPair
     *
     * @return an exchangeRate object
     */
    ExchangeRate getExchangeRateFromDate(Currency currencyFrom, Currency currencyTo, long timestamp, UUID providerId) throws UnsupportedCurrencyPairException, CantGetExchangeRateException, CantGetProviderException;

    /**
     * Given a start and end timestamp and a currencyPair, returns a list of DAILY ExchangeRates
     *
     * @return a list of exchangeRate objects
     */
    Collection<ExchangeRate> getDailyExchangeRatesForPeriod(Currency currencyFrom, Currency currencyTo, long startTimestamp, long endTimestamp, UUID providerId) throws UnsupportedCurrencyPairException, CantGetExchangeRateException, CantGetProviderException;

    /**
     * This method load the list CryptoBrokerStockTransaction
     * @param merchandise
     * @param currencyType
     * @param offset
     * @param timeStamp
     * @return List<CryptoBrokerStockTransaction>
     * @exception CantGetCryptoBrokerStockTransactionException
     */
    List<CryptoBrokerStockTransaction> getStockHistory(FermatEnum merchandise, CurrencyType currencyType, int offset, long timeStamp, String walletPublicKey) throws CantGetCryptoBrokerStockTransactionException;

    float getAvailableBalance(FermatEnum merchandise, String walletPublicKey) throws CantGetAvailableBalanceCryptoBrokerWalletException, CryptoBrokerWalletNotFoundException, CantGetStockCryptoBrokerWalletException;

    /**
     * Returns a list of provider references which can obtain the ExchangeRate of the given CurrencyPair
     * @param currencyFrom
     * @param currencyTo
     * @return a Collection of provider reference pairs
     * */
    Collection<CurrencyExchangeRateProviderManager> getProviderReferencesFromCurrencyPair(Currency currencyFrom, Currency currencyTo) throws CantGetProviderException;
    /**
     * This method save the instance CryptoBrokerWalletProviderSetting
     * @param cryptoBrokerWalletProviderSetting
     * @return
     * @exception CantSaveCryptoBrokerWalletSettingException
     */
    void saveCryptoBrokerWalletProviderSetting(CryptoBrokerWalletProviderSetting cryptoBrokerWalletProviderSetting, String walletPublicKey) throws CantSaveCryptoBrokerWalletSettingException, CryptoBrokerWalletNotFoundException, CantGetCryptoBrokerWalletSettingException;

    /**
     *
     * @param bankAccount
     * @throws CantCreateBankAccountSaleException
     */
    void createNewBankAccount(NegotiationBankAccount bankAccount) throws CantCreateBankAccountSaleException;

    /**
     *
     * @param bankAccount
     * @throws CantDeleteBankAccountSaleException
     */
    void deleteBankAccount(NegotiationBankAccount bankAccount) throws CantDeleteBankAccountSaleException;

    /**
     * Returns the Balance this BankMoneyWalletBalance belongs to. (Can be available or book)
     *
     * @return A double, containing the balance.
     */
    double getBalanceBankWallet(String walletPublicKey, String accountNumber) throws CantCalculateBalanceException, CantLoadBankMoneyWalletException;

    /**
     * Returns the Balance this CashMoneyWalletBalance belongs to. (Can be available or book)
     *
     * @return A BigDecimal, containing the balance.
     */
    BigDecimal getBalanceCashWallet(String walletPublicKey) throws CantGetCashMoneyWalletBalanceException, CantLoadCashMoneyWalletException;

    /**
     * Returns the Balance this BitcoinWalletBalance belongs to. (Can be available or book)
     *
     * @return A BigDecimal, containing the balance.
     */
    public long getBalanceBitcoinWallet(String walletPublicKey) throws com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantCalculateBalanceException, CantLoadWalletException;
}
