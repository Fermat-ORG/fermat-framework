package com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantLoadBankMoneyWalletException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankAccountNumber;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStepStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.OriginTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationLocations;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentity;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantCreateLocationSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantDeleteLocationSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantUpdateLocationSaleException;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.bank_money_restock.exceptions.CantCreateBankMoneyRestockException;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.cash_money_restock.exceptions.CantCreateCashMoneyRestockException;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.crypto_money_restock.exceptions.CantCreateCryptoMoneyRestockException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetCryptoBrokerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantSaveCryptoBrokerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CryptoBrokerWalletNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletAssociatedSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletSettingSpread;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CantNewEmptyCryptoBrokerWalletAssociatedSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CantNewEmptyCryptoBrokerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.IndexInfoSummary;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.NegotiationStep;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.WalletManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.exceptions.CantGetCryptoBrokerIdentityListException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.exceptions.CantGetCurrentIndexSummaryForStockCurrenciesException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantListWalletsException;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

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
     * Get information about the current stock
     *
     * @param stockCurrency the stock currency
     * @return information about the current stock
     */
    StockInformation getCurrentStock(String stockCurrency);

    /**
     * @return list of identities associated with this wallet
     */
    List<CryptoBrokerIdentity> getListOfIdentities() throws CantGetCryptoBrokerIdentityListException;

    /**
     * Get stock staticstics data about the given stock currency
     *
     * @param stockCurrency the stock currency
     * @return stock statistics data
     */
    StockStatistics getStockStatistics(String stockCurrency);

    List<String> getBrokerLocations();

    List<String> getBrokerBankAccounts();

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
     * @param cryptoCurrency
     * @param cbpWalletPublicKey
     * @param cryWalletPublicKey
     * @param amount
     * @param memo
     * @param priceReference
     * @param originTransaction
     *
     * @throws CantCreateCashMoneyRestockException
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
}
