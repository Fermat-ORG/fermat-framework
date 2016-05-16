package com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantListActorConnectionsException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.CantLoadWalletsException;
import com.bitdubai.fermat_api.layer.modules.ModuleManagerImpl;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankAccountType;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantAddNewAccountException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantLoadBankMoneyWalletException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankAccountNumber;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyWalletBalance;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyWalletManager;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ActorType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractDetailType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.OriginTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantGetCompletionDateException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationBankAccount;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationLocations;
import com.bitdubai.fermat_cbp_api.all_definition.util.NegotiationClauseHelper;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantClearBrokerIdentityWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantCreateNewBrokerIdentityWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantGetRelationBetweenBrokerIdentityAndBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.interfaces.BrokerIdentityWalletRelationship;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.interfaces.CryptoBrokerActorManager;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_customer.interfaces.CryptoCustomerActorConnectionManager;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_customer.interfaces.CryptoCustomerActorConnectionSearch;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_customer.utils.CryptoCustomerActorConnection;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_customer.utils.CryptoCustomerLinkedActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.broker_ack_offline_payment.interfaces.BrokerAckOfflinePaymentManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.broker_ack_online_payment.interfaces.BrokerAckOnlinePaymentManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.broker_submit_offline_merchandise.interfaces.BrokerSubmitOfflineMerchandiseManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.broker_submit_online_merchandise.interfaces.BrokerSubmitOnlineMerchandiseManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantAckPaymentException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantSubmitMerchandiseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.ObjectChecker;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.mocks.CustomerBrokerContractSaleMock;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_ack_offline_merchandise.interfaces.CustomerAckOfflineMerchandiseManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_ack_online_merchandise.interfaces.CustomerAckOnlineMerchandiseManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_offline_payment.interfaces.CustomerOfflinePaymentManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_online_payment.interfaces.CustomerOnlinePaymentManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantGetListCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSale;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSaleManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.ListsForStatusSale;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.CantGetCryptoBrokerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.CantListCryptoBrokerIdentitiesException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.IdentityNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentity;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentityManager;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.enums.EarningTransactionState;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantAssociatePairException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantDisassociatePairException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantExtractEarningsException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantListEarningTransactionsException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantListEarningsPairsException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantLoadEarningSettingsException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantRegisterEarningsSettingsException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantUpdatePairException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.EarningsSettingsNotRegisteredException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.PairAlreadyAssociatedException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.PairNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningExtractorManager;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningTransaction;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsPair;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsSearch;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsSettings;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.MatchingEngineManager;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.utils.WalletReference;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantCreateBankAccountSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantCreateCustomerBrokerSaleNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantCreateLocationSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantDeleteBankAccountSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantDeleteLocationSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantGetListLocationsSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantGetListSaleNegotiationsException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantUpdateLocationSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetListClauseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.interfaces.CustomerBrokerCloseManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_update.exceptions.CantCancelNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_update.exceptions.CantCreateCustomerBrokerUpdateSaleNegotiationTransactionException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_update.interfaces.CustomerBrokerUpdateManager;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.exceptions.CantSendNegotiationToCryptoCustomerException;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.bank_money_destock.exceptions.CantCreateBankMoneyDestockException;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.bank_money_destock.interfaces.BankMoneyDestockManager;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.bank_money_restock.exceptions.CantCreateBankMoneyRestockException;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.bank_money_restock.interfaces.BankMoneyRestockManager;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.cash_money_destock.exceptions.CantCreateCashMoneyDestockException;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.cash_money_destock.interfaces.CashMoneyDestockManager;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.cash_money_restock.exceptions.CantCreateCashMoneyRestockException;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.cash_money_restock.interfaces.CashMoneyRestockManager;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.crypto_money_destock.exceptions.CantCreateCryptoMoneyDestockException;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.crypto_money_destock.interfaces.CryptoMoneyDestockManager;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.crypto_money_restock.exceptions.CantCreateCryptoMoneyRestockException;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.crypto_money_restock.interfaces.CryptoMoneyRestockManager;
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
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWallet;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.FiatIndex;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.Quote;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletAssociatedSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletProviderSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletSettingSpread;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CantGetAssociatedIdentityException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CantGetContractHistoryException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CantGetContractsWaitingForBrokerException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CantGetContractsWaitingForCustomerException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CantGetNegotiationInformationException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CantGetNegotiationsWaitingForBrokerException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CantGetNegotiationsWaitingForCustomerException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CantNewEmptyBankAccountException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CantNewEmptyCryptoBrokerWalletAssociatedSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CantNewEmptyCryptoBrokerWalletProviderSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CantNewEmptyCryptoBrokerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CouldNotCancelNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ContractBasicInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.IndexInfoSummary;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.CurrencyPairAndProvider;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.exceptions.CantGetCryptoBrokerIdentityListException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.exceptions.CantGetProvidersCurrentExchangeRatesException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.CryptoBrokerWalletPreferenceSettings;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.CurrencyPair;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.ExchangeRate;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantGetExchangeRateException;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantGetProviderInfoException;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.UnsupportedCurrencyPairException;
import com.bitdubai.fermat_cer_api.layer.provider.interfaces.CurrencyExchangeRateProviderManager;
import com.bitdubai.fermat_cer_api.layer.search.exceptions.CantGetProviderException;
import com.bitdubai.fermat_cer_api.layer.search.interfaces.CurrencyExchangeProviderFilterManager;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantCreateCashMoneyWalletException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantGetCashMoneyWalletBalanceException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantGetCashMoneyWalletCurrencyException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantLoadCashMoneyWalletException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWalletManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantListWalletsException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletManagerManager;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * Module Manager of Crypto Broker Module Plugin
 *
 * @author Nelson Ramirez
 * @version 1.0
 * @since 05/11/15
 * Modified by Franklin Marcano 23/12/15
 */
public class CryptoBrokerWalletModuleCryptoBrokerWalletManager
        extends ModuleManagerImpl<CryptoBrokerWalletPreferenceSettings> implements CryptoBrokerWalletModuleManager, Serializable {

    private final WalletManagerManager walletManagerManager;
    private final com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWalletManager cryptoBrokerWalletManager;
    private final BankMoneyWalletManager bankMoneyWalletManager;
    private final CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager;
    private final BankMoneyRestockManager bankMoneyRestockManager;
    private final CashMoneyRestockManager cashMoneyRestockManager;
    private final CryptoMoneyRestockManager cryptoMoneyRestockManager;
    private final CashMoneyWalletManager cashMoneyWalletManager;
    private final BankMoneyDestockManager bankMoneyDestockManager;
    private final CashMoneyDestockManager cashMoneyDestockManager;
    private final CryptoMoneyDestockManager cryptoMoneyDestockManager;
    private final CustomerBrokerContractSaleManager customerBrokerContractSaleManager;
    private final CurrencyExchangeProviderFilterManager currencyExchangeProviderFilterManager;
    private final CryptoBrokerIdentityManager cryptoBrokerIdentityManager;
    private final CustomerBrokerUpdateManager customerBrokerUpdateManager;
    private final BitcoinWalletManager bitcoinWalletManager;
    private final CryptoBrokerActorManager cryptoBrokerActorManager;
    private final CustomerOnlinePaymentManager customerOnlinePaymentManager;
    private final CustomerOfflinePaymentManager customerOfflinePaymentManager;
    private final CustomerAckOnlineMerchandiseManager customerAckOnlineMerchandiseManager;
    private final CustomerAckOfflineMerchandiseManager customerAckOfflineMerchandiseManager;
    private final BrokerAckOfflinePaymentManager brokerAckOfflinePaymentManager;
    private final BrokerAckOnlinePaymentManager brokerAckOnlinePaymentManager;
    private final BrokerSubmitOfflineMerchandiseManager brokerSubmitOfflineMerchandiseManager;
    private final BrokerSubmitOnlineMerchandiseManager brokerSubmitOnlineMerchandiseManager;
    private final MatchingEngineManager matchingEngineManager;
    private final CustomerBrokerCloseManager customerBrokerCloseManager;
    private final CryptoCustomerActorConnectionManager cryptoCustomerActorConnectionManager;

    public CryptoBrokerWalletModuleCryptoBrokerWalletManager(WalletManagerManager walletManagerManager,
                                                             com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWalletManager cryptoBrokerWalletManager,
                                                             BankMoneyWalletManager bankMoneyWalletManagerManager,
                                                             CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager,
                                                             BankMoneyRestockManager bankMoneyRestockManager,
                                                             CashMoneyRestockManager cashMoneyRestockManager,
                                                             CryptoMoneyRestockManager cryptoMoneyRestockManager,
                                                             CashMoneyWalletManager cashMoneyWalletManager,
                                                             BankMoneyDestockManager bankMoneyDestockManager,
                                                             CashMoneyDestockManager cashMoneyDestockManager,
                                                             CryptoMoneyDestockManager cryptoMoneyDestockManager,
                                                             CustomerBrokerContractSaleManager customerBrokerContractSaleManager,
                                                             CurrencyExchangeProviderFilterManager currencyExchangeProviderFilterManager,
                                                             CryptoBrokerIdentityManager cryptoBrokerIdentityManager,
                                                             CustomerBrokerUpdateManager customerBrokerUpdateManager,
                                                             BitcoinWalletManager bitcoinWalletManager,
                                                             CryptoBrokerActorManager cryptoBrokerActorManager,
                                                             CustomerOnlinePaymentManager customerOnlinePaymentManager,
                                                             CustomerOfflinePaymentManager customerOfflinePaymentManager,
                                                             CustomerAckOnlineMerchandiseManager customerAckOnlineMerchandiseManager,
                                                             CustomerAckOfflineMerchandiseManager customerAckOfflineMerchandiseManager,
                                                             BrokerAckOfflinePaymentManager brokerAckOfflinePaymentManager,
                                                             BrokerAckOnlinePaymentManager brokerAckOnlinePaymentManager,
                                                             BrokerSubmitOfflineMerchandiseManager brokerSubmitOfflineMerchandiseManager,
                                                             BrokerSubmitOnlineMerchandiseManager brokerSubmitOnlineMerchandiseManager,
                                                             MatchingEngineManager matchingEngineManager,
                                                             CustomerBrokerCloseManager customerBrokerCloseManager,
                                                             CryptoCustomerActorConnectionManager cryptoCustomerActorConnectionManager,
                                                             PluginFileSystem pluginFileSystem, UUID pluginId) {
        super(pluginFileSystem, pluginId);

        this.walletManagerManager = walletManagerManager;
        this.cryptoBrokerWalletManager = cryptoBrokerWalletManager;
        this.bankMoneyWalletManager = bankMoneyWalletManagerManager;
        this.customerBrokerSaleNegotiationManager = customerBrokerSaleNegotiationManager;
        this.bankMoneyRestockManager = bankMoneyRestockManager;
        this.cashMoneyRestockManager = cashMoneyRestockManager;
        this.cryptoMoneyRestockManager = cryptoMoneyRestockManager;
        this.cashMoneyWalletManager = cashMoneyWalletManager;
        this.bankMoneyDestockManager = bankMoneyDestockManager;
        this.cashMoneyDestockManager = cashMoneyDestockManager;
        this.cryptoMoneyDestockManager = cryptoMoneyDestockManager;
        this.customerBrokerContractSaleManager = customerBrokerContractSaleManager;
        this.currencyExchangeProviderFilterManager = currencyExchangeProviderFilterManager;
        this.cryptoBrokerIdentityManager = cryptoBrokerIdentityManager;
        this.customerBrokerUpdateManager = customerBrokerUpdateManager;
        this.bitcoinWalletManager = bitcoinWalletManager;
        this.cryptoBrokerActorManager = cryptoBrokerActorManager;
        this.customerOnlinePaymentManager = customerOnlinePaymentManager;
        this.customerOfflinePaymentManager = customerOfflinePaymentManager;
        this.customerAckOnlineMerchandiseManager = customerAckOnlineMerchandiseManager;
        this.customerAckOfflineMerchandiseManager = customerAckOfflineMerchandiseManager;
        this.brokerAckOfflinePaymentManager = brokerAckOfflinePaymentManager;
        this.brokerAckOnlinePaymentManager = brokerAckOnlinePaymentManager;
        this.brokerSubmitOfflineMerchandiseManager = brokerSubmitOfflineMerchandiseManager;
        this.brokerSubmitOnlineMerchandiseManager = brokerSubmitOnlineMerchandiseManager;
        this.matchingEngineManager = matchingEngineManager;
        this.customerBrokerCloseManager = customerBrokerCloseManager;
        this.cryptoCustomerActorConnectionManager = cryptoCustomerActorConnectionManager;
    }

    @Override
    public Collection<ContractBasicInformation> getContractsHistory(ContractStatus status, int max, int offset) throws CantGetContractHistoryException {
        List<ContractBasicInformation> filteredList = new ArrayList<>();
        ActorIdentity customerIdentity;
        try {

            CryptoBrokerWalletModuleContractBasicInformation contract;

            if (status != null) {

                final Collection<CustomerBrokerContractSale> saleContracts = customerBrokerContractSaleManager.getCustomerBrokerContractSaleForStatus(status);
                for (CustomerBrokerContractSale saleContract : saleContracts) {
                    CustomerBrokerSaleNegotiation saleNegotiation = customerBrokerSaleNegotiationManager.getNegotiationsByNegotiationId(UUID.fromString(saleContract.getNegotiatiotId()));
                    customerIdentity = getCustomerInfoByPublicKey(saleContract.getPublicKeyBroker(), saleContract.getPublicKeyCustomer());
                    contract = new CryptoBrokerWalletModuleContractBasicInformation(customerIdentity, saleContract, saleNegotiation);
                    contract.setCancellationReason(saleContract.getCancelReason());
                    filteredList.add(contract);
                }

                if (status == ContractStatus.CANCELLED) {
                    final Collection<CustomerBrokerSaleNegotiation> canceledNegotiations = customerBrokerSaleNegotiationManager.getNegotiationsByStatus(NegotiationStatus.CANCELLED);
                    for (CustomerBrokerSaleNegotiation saleNegotiation : canceledNegotiations) {
                        customerIdentity = getCustomerInfoByPublicKey(saleNegotiation.getBrokerPublicKey(), saleNegotiation.getCustomerPublicKey());
                        contract = new CryptoBrokerWalletModuleContractBasicInformation(customerIdentity, saleNegotiation);
                        filteredList.add(contract);
                    }
                }

            } else {

                ListsForStatusSale history = customerBrokerContractSaleManager.getCustomerBrokerContractHistory();
                final Collection<CustomerBrokerContractSale> historyContracts = history.getHistoryContracts();
                for (CustomerBrokerContractSale customerBrokerContractSale : historyContracts) {
                    CustomerBrokerSaleNegotiation saleNegotiation = customerBrokerSaleNegotiationManager.getNegotiationsByNegotiationId(UUID.fromString(customerBrokerContractSale.getNegotiatiotId()));
                    customerIdentity = getCustomerInfoByPublicKey(customerBrokerContractSale.getPublicKeyBroker(), customerBrokerContractSale.getPublicKeyCustomer());
                    contract = new CryptoBrokerWalletModuleContractBasicInformation(customerIdentity, customerBrokerContractSale, saleNegotiation);
                    contract.setCancellationReason(customerBrokerContractSale.getCancelReason());
                    filteredList.add(contract);
                }

                final Collection<CustomerBrokerSaleNegotiation> canceledNegotiations = customerBrokerSaleNegotiationManager.getNegotiationsByStatus(NegotiationStatus.CANCELLED);
                for (CustomerBrokerSaleNegotiation saleNegotiation : canceledNegotiations) {
                    customerIdentity = getCustomerInfoByPublicKey(saleNegotiation.getBrokerPublicKey(), saleNegotiation.getCustomerPublicKey());
                    contract = new CryptoBrokerWalletModuleContractBasicInformation(customerIdentity, saleNegotiation);
                    filteredList.add(contract);
                }
            }

            return filteredList;

        } catch (Exception ex) {
            throw new CantGetContractHistoryException(ex);
        }
    }

    @Override
    public Collection<ContractBasicInformation> getContractsWaitingForBroker(int max, int offset) throws CantGetContractsWaitingForBrokerException {
        try {
            Collection<ContractBasicInformation> waitingForBroker = new ArrayList<>();

            ListsForStatusSale history = customerBrokerContractSaleManager.getCustomerBrokerContractHistory();
            final Collection<CustomerBrokerContractSale> contractsWaitingForBroker = history.getContractsWaitingForBroker();

            for (CustomerBrokerContractSale customerBrokerContractSale : contractsWaitingForBroker) {
                CustomerBrokerSaleNegotiation saleNegotiation = customerBrokerSaleNegotiationManager.getNegotiationsByNegotiationId(UUID.fromString(customerBrokerContractSale.getNegotiatiotId()));
                ActorIdentity customerIdentity = getCustomerInfoByPublicKey(customerBrokerContractSale.getPublicKeyBroker(), customerBrokerContractSale.getPublicKeyCustomer());
                ContractBasicInformation contract = new CryptoBrokerWalletModuleContractBasicInformation(customerIdentity, customerBrokerContractSale, saleNegotiation);
                waitingForBroker.add(contract);
            }

            return waitingForBroker;

        } catch (Exception ex) {
            throw new CantGetContractsWaitingForBrokerException("Cant get contracts waiting for the broker", ex);
        }
    }

    @Override
    public Collection<ContractBasicInformation> getContractsWaitingForCustomer(int max, int offset) throws CantGetContractsWaitingForCustomerException {
        try {
            Collection<ContractBasicInformation> waitingForCustomer = new ArrayList<>();

            ListsForStatusSale history = customerBrokerContractSaleManager.getCustomerBrokerContractHistory();
            final Collection<CustomerBrokerContractSale> contractsWaitingForCustomer = history.getContractsWaitingForCustomer();

            for (CustomerBrokerContractSale customerBrokerContractSale : contractsWaitingForCustomer) {
                CustomerBrokerSaleNegotiation saleNegotiation = customerBrokerSaleNegotiationManager.getNegotiationsByNegotiationId(UUID.fromString(customerBrokerContractSale.getNegotiatiotId()));
                ActorIdentity customerIdentity = getCustomerInfoByPublicKey(customerBrokerContractSale.getPublicKeyBroker(), customerBrokerContractSale.getPublicKeyCustomer());
                ContractBasicInformation contract = new CryptoBrokerWalletModuleContractBasicInformation(customerIdentity, customerBrokerContractSale, saleNegotiation);
                waitingForCustomer.add(contract);
            }

            return waitingForCustomer;

        } catch (Exception ex) {
            throw new CantGetContractsWaitingForCustomerException("Cant get contracts waiting for the broker", ex);
        }
    }

    @Override
    public Collection<Clause> getNegotiationClausesFromNegotiationId(UUID negotiationId) throws CantGetListClauseException {
        try {
            CustomerBrokerSaleNegotiation negotiation = customerBrokerSaleNegotiationManager.getNegotiationsByNegotiationId(negotiationId);
            return negotiation.getClauses();
        } catch (CantGetListSaleNegotiationsException | CantGetListClauseException ex) {
            throw new CantGetListClauseException("Cant get the negotiation clauses for the given negotiationId " + negotiationId.toString(), ex);
        }
    }

    @Override
    public Collection<CustomerBrokerNegotiationInformation> getNegotiationsWaitingForBroker(int max, int offset) throws CantGetNegotiationsWaitingForBrokerException {
        Collection<CustomerBrokerNegotiationInformation> waitingForBroker = new ArrayList<>();
        Collection<CustomerBrokerSaleNegotiation> saleNegotiations;

        try {
            saleNegotiations = customerBrokerSaleNegotiationManager.getNegotiationsBySendAndWaiting(ActorType.BROKER);
        } catch (CantGetListSaleNegotiationsException ex) {
            throw new CantGetNegotiationsWaitingForBrokerException("Cant get negotiations waiting for the broker: " + ex.getMessage(), ex, "N/A", "");
        }

        for (CustomerBrokerSaleNegotiation saleNegotiation : saleNegotiations) {
            ActorIdentity customerIdentity;
            try {
                customerIdentity = getCustomerInfoByPublicKey(saleNegotiation.getBrokerPublicKey(), saleNegotiation.getCustomerPublicKey());
                if (customerIdentity == null)
                    throw new CantGetNegotiationsWaitingForBrokerException("The Customer information is NULL");
            } catch (Exception e) {
                throw new CantGetNegotiationsWaitingForBrokerException("Cant get the Customer information: " + e.getMessage(), e, "N/A", "");
            }

            ActorIdentity brokerIdentity;
            try {
                brokerIdentity = cryptoBrokerIdentityManager.getCryptoBrokerIdentity(saleNegotiation.getBrokerPublicKey());
                if (brokerIdentity == null)
                    throw new CantGetNegotiationsWaitingForBrokerException("The Broker information is NULL");
            } catch (Exception e) {
                throw new CantGetNegotiationsWaitingForBrokerException("Cant get the Broker information: " + e.getMessage(), e, "N/A", "");
            }

            waitingForBroker.add(new CryptoBrokerWalletModuleCustomerBrokerNegotiationInformation(saleNegotiation, customerIdentity, brokerIdentity));
        }

        return waitingForBroker;

    }

    @Override
    public Collection<CustomerBrokerNegotiationInformation> getNegotiationsWaitingForCustomer(int max, int offset) throws CantGetNegotiationsWaitingForCustomerException {
        Collection<CustomerBrokerNegotiationInformation> waitingForCustomer = new ArrayList<>();
        Collection<CustomerBrokerSaleNegotiation> saleNegotiations;

        try {
            saleNegotiations = customerBrokerSaleNegotiationManager.getNegotiationsBySendAndWaiting(ActorType.CUSTOMER);
        } catch (CantGetListSaleNegotiationsException ex) {
            throw new CantGetNegotiationsWaitingForCustomerException("Cant get negotiations waiting for the Customer: " + ex.getMessage(), ex, "N/A", "");
        }

        for (CustomerBrokerSaleNegotiation saleNegotiation : saleNegotiations) {
            ActorIdentity customerIdentity;
            try {
                customerIdentity = getCustomerInfoByPublicKey(saleNegotiation.getBrokerPublicKey(), saleNegotiation.getCustomerPublicKey());
                if (customerIdentity == null)
                    throw new CantGetNegotiationsWaitingForCustomerException("The Customer information is NULL");
            } catch (Exception e) {
                throw new CantGetNegotiationsWaitingForCustomerException("Cant get the Customer information: " + e.getMessage(), e, "N/A", "");
            }

            ActorIdentity brokerIdentity;
            try {
                brokerIdentity = cryptoBrokerIdentityManager.getCryptoBrokerIdentity(saleNegotiation.getBrokerPublicKey());
                if (brokerIdentity == null)
                    throw new CantGetNegotiationsWaitingForCustomerException("The Broker information is NULL");
            } catch (Exception e) {
                throw new CantGetNegotiationsWaitingForCustomerException("Cant get the Broker information: " + e.getMessage(), e, "N/A", "");
            }

            waitingForCustomer.add(new CryptoBrokerWalletModuleCustomerBrokerNegotiationInformation(saleNegotiation, customerIdentity, brokerIdentity));
        }

        return waitingForCustomer;
    }

    @Override
    public CustomerBrokerNegotiationInformation getNegotiationInformation(UUID negotiationID) throws CantGetNegotiationInformationException {
        final CustomerBrokerSaleNegotiation saleNegotiation;
        try {
            saleNegotiation = customerBrokerSaleNegotiationManager.getNegotiationsByNegotiationId(negotiationID);
        } catch (CantGetListSaleNegotiationsException e) {
            throw new CantGetNegotiationInformationException(CantGetNegotiationInformationException.DEFAULT_MESSAGE, e,
                    "negotiationID = " + negotiationID, "Cant get the Sale Negotiation Information");
        } catch (Exception e) {
            throw new CantGetNegotiationInformationException(e);
        }

        try {
            ActorIdentity customerIdentity = getCustomerInfoByPublicKey(saleNegotiation.getBrokerPublicKey(), saleNegotiation.getCustomerPublicKey());
            ActorIdentity brokerIdentity = cryptoBrokerIdentityManager.getCryptoBrokerIdentity(saleNegotiation.getBrokerPublicKey());
            return new CryptoBrokerWalletModuleCustomerBrokerNegotiationInformation(saleNegotiation, customerIdentity, brokerIdentity);

        } catch (CantListActorConnectionsException e) {
            throw new CantGetNegotiationInformationException(CantGetNegotiationInformationException.DEFAULT_MESSAGE, e,
                    "saleNegotiation = " + saleNegotiation, "Cant get the Customer Information");
        } catch (IdentityNotFoundException | CantGetCryptoBrokerIdentityException e) {
            throw new CantGetNegotiationInformationException(CantGetNegotiationInformationException.DEFAULT_MESSAGE, e,
                    "saleNegotiation = " + saleNegotiation + " - customerIdentity = ", "Cant get the Broker Information");
        } catch (Exception e) {
            throw new CantGetNegotiationInformationException(e);
        }
    }

    @Override
    public Collection<NegotiationLocations> getAllLocations(NegotiationType negotiationType) throws CantGetListLocationsSaleException {
        Collection<NegotiationLocations> negotiationLocations = null;
        if (negotiationType.getCode().equalsIgnoreCase(NegotiationType.SALE.getCode())) {
            negotiationLocations = customerBrokerSaleNegotiationManager.getAllLocations();
        }
        return negotiationLocations;
    }

    @Override
    public long getCompletionDateForContractStatus(String contractHash, ContractStatus contractStatus, String paymentMethod) {
        try {
            switch (contractStatus) {
                case PAYMENT_SUBMIT:
                    if (paymentMethod.equals(MoneyType.CRYPTO.getFriendlyName()))
                        return customerOnlinePaymentManager.getCompletionDate(contractHash);
                    else
                        return customerOfflinePaymentManager.getCompletionDate(contractHash);
                case PENDING_MERCHANDISE:
                    if (paymentMethod.equals(MoneyType.CRYPTO.getFriendlyName()))
                        return brokerAckOnlinePaymentManager.getCompletionDate(contractHash);
                    else
                        return brokerAckOfflinePaymentManager.getCompletionDate(contractHash);
                case MERCHANDISE_SUBMIT:
                    if (paymentMethod.equals(MoneyType.CRYPTO.getFriendlyName()))
                        return brokerSubmitOnlineMerchandiseManager.getCompletionDate(contractHash);
                    else
                        return brokerSubmitOfflineMerchandiseManager.getCompletionDate(contractHash);
                case READY_TO_CLOSE:
                    if (paymentMethod.equals(MoneyType.CRYPTO.getFriendlyName()))
                        return customerAckOnlineMerchandiseManager.getCompletionDate(contractHash);
                    else
                        return customerAckOfflineMerchandiseManager.getCompletionDate(contractHash);
            }
        } catch (CantGetCompletionDateException e) {
        }
        return 0;
    }

    @Override
    public boolean associateIdentity(ActorIdentity brokerIdentity, String brokerWalletPublicKey) throws CantCreateNewBrokerIdentityWalletRelationshipException {
        return cryptoBrokerActorManager.createNewBrokerIdentityWalletRelationship(brokerIdentity, brokerWalletPublicKey) != null;
    }

    @Override
    public void clearAssociatedIdentities(String brokerWalletPublicKey) throws CantClearBrokerIdentityWalletRelationshipException {
        cryptoBrokerActorManager.clearBrokerIdentityWalletRelationship(brokerWalletPublicKey);
    }

    @Override
    public CustomerBrokerNegotiationInformation cancelNegotiation(CustomerBrokerNegotiationInformation negotiation, String reason) throws CouldNotCancelNegotiationException, CantCancelNegotiationException {
        CustomerBrokerSaleNegotiationImpl customerBrokerSaleNegotiation = new CustomerBrokerSaleNegotiationImpl(
                negotiation.getNegotiationId(),
                negotiation.getBroker().getPublicKey(),
                negotiation.getCustomer().getPublicKey()
        );
        customerBrokerSaleNegotiation.setCancelReason(reason);
        customerBrokerUpdateManager.cancelNegotiation(customerBrokerSaleNegotiation);

        return negotiation;
    }

    @Override
    public Quote getQuote(Currency merchandise, Currency currencyPayment, String brokerWalletPublicKey) throws CantGetCryptoBrokerQuoteException {
        try {
            CryptoBrokerWallet cryptoBrokerWallet = cryptoBrokerWalletManager.loadCryptoBrokerWallet(brokerWalletPublicKey);
            return cryptoBrokerWallet.getQuote(merchandise, 1f, currencyPayment);

        } catch (CryptoBrokerWalletNotFoundException e) {
            throw new CantGetCryptoBrokerQuoteException(e.getMessage(), e, "brokerWalletPublicKey: " + brokerWalletPublicKey, "N/A");
        }
    }

    @Override
    public Collection<ExchangeRate> getDailyExchangeRatesFromCurrentDate(IndexInfoSummary indexInfo, int numberOfDays) throws CantGetProviderException, UnsupportedCurrencyPairException, CantGetExchangeRateException {

        CurrencyExchangeRateProviderManager providerReference = currencyExchangeProviderFilterManager.getProviderReference(indexInfo.getProviderId());

        Calendar endCalendar = Calendar.getInstance();

        Calendar startCalendar = Calendar.getInstance();
        startCalendar.add(Calendar.DATE, -numberOfDays);


        ExchangeRate exchangeRateData = indexInfo.getExchangeRateData();
        CurrencyPairImpl currencyPair = new CurrencyPairImpl(exchangeRateData.getFromCurrency(), exchangeRateData.getToCurrency());

        return providerReference.getDailyExchangeRatesForPeriod(currencyPair, startCalendar, endCalendar);
    }

    @Override
    public Collection<IndexInfoSummary> getProvidersCurrentExchangeRates(String brokerWalletPublicKey) throws CantGetProvidersCurrentExchangeRatesException, CryptoBrokerWalletNotFoundException, CantGetCryptoBrokerWalletSettingException, CantGetProviderException, UnsupportedCurrencyPairException, CantGetExchangeRateException, InvalidParameterException {
        final String publicKeyWalletCryptoBrokerInstall = "walletPublicKeyTest"; //TODO: Quitar este hardcode luego que se implemente la instalacion de la wallet

        final Collection<IndexInfoSummary> summaryList = new ArrayList<>();

        final CryptoBrokerWallet cryptoBrokerWallet = cryptoBrokerWalletManager.loadCryptoBrokerWallet(publicKeyWalletCryptoBrokerInstall);
        final CryptoBrokerWalletSetting cryptoWalletSetting = cryptoBrokerWallet.getCryptoWalletSetting();
        final List<CryptoBrokerWalletProviderSetting> providerSettings = cryptoWalletSetting.getCryptoBrokerWalletProviderSettings();

        for (CryptoBrokerWalletProviderSetting providerSetting : providerSettings) {
            UUID providerId = providerSetting.getPlugin();

            CurrencyExchangeRateProviderManager providerReference = currencyExchangeProviderFilterManager.getProviderReference(providerId);
            Currency from = getCurrencyFromCode(providerSetting.getCurrencyFrom());
            Currency to = getCurrencyFromCode(providerSetting.getCurrencyTo());

            ExchangeRate currentExchangeRate = providerReference.getCurrentExchangeRate(new CurrencyPairImpl(from, to));

            summaryList.add(new CryptoBrokerWalletModuleIndexInfoSummary(currentExchangeRate, providerReference));
        }

        return summaryList;
    }

    @Override
    public boolean haveAssociatedIdentity(String walletPublicKey) throws CantListCryptoBrokerIdentitiesException, CantGetRelationBetweenBrokerIdentityAndBrokerWalletException {
        List<CryptoBrokerIdentity> cryptoBrokerIdentities = cryptoBrokerIdentityManager.listIdentitiesFromCurrentDeviceUser();
        BrokerIdentityWalletRelationship relationship = cryptoBrokerActorManager.getBrokerIdentityWalletRelationshipByWallet(walletPublicKey);

        if (relationship != null && cryptoBrokerIdentities != null) {
            for (CryptoBrokerIdentity identity : cryptoBrokerIdentities) {
                String identityPublicKey = identity.getPublicKey();
                String associatedCryptoBrokerPublicKey = relationship.getCryptoBroker();

                if (identityPublicKey.equals(associatedCryptoBrokerPublicKey))
                    return true;
            }
        }

        return false;
    }

    @Override
    public CryptoBrokerIdentity getAssociatedIdentity(String walletPublicKey) throws CantListCryptoBrokerIdentitiesException, CantGetRelationBetweenBrokerIdentityAndBrokerWalletException, CantGetAssociatedIdentityException {
        List<CryptoBrokerIdentity> cryptoBrokerIdentities = cryptoBrokerIdentityManager.listIdentitiesFromCurrentDeviceUser();
        BrokerIdentityWalletRelationship relationship = cryptoBrokerActorManager.getBrokerIdentityWalletRelationshipByWallet(walletPublicKey);

        if (relationship != null && cryptoBrokerIdentities != null) {
            for (CryptoBrokerIdentity identity : cryptoBrokerIdentities) {
                String identityPublicKey = identity.getPublicKey();
                String associatedCryptoBrokerPublicKey = relationship.getCryptoBroker();

                if (identityPublicKey.equals(associatedCryptoBrokerPublicKey))
                    return identity;
            }
        }

        throw new CantGetAssociatedIdentityException();
    }

    @Override
    public List<CryptoBrokerIdentity> getListOfIdentities() throws CantGetCryptoBrokerIdentityListException, CantListCryptoBrokerIdentitiesException {
        return cryptoBrokerIdentityManager.listIdentitiesFromCurrentDeviceUser();
    }

    @Override
    public List<MoneyType> getPaymentMethods(String currencyCode, String brokerWalletPublicKey) throws CryptoBrokerWalletNotFoundException, CantGetCryptoBrokerWalletSettingException {
        List<MoneyType> paymentMethod = new ArrayList<>();

        List<CryptoBrokerWalletAssociatedSetting> associatedWallets = getCryptoBrokerWalletAssociatedSettings(brokerWalletPublicKey);

        for (CryptoBrokerWalletAssociatedSetting associatedWallet : associatedWallets) {
            Currency merchandise = associatedWallet.getMerchandise();
            if (merchandise.getCode().equals(currencyCode)) {
                if (associatedWallet.getPlatform() == Platforms.CASH_PLATFORM) {
                    paymentMethod.add(MoneyType.CASH_ON_HAND);
                    paymentMethod.add(MoneyType.CASH_DELIVERY);
                } else
                    paymentMethod.add(associatedWallet.getMoneyType());
            }
        }

        return paymentMethod;
    }

    @Override
    public List<String> getAccounts(String currencyCode, String brokerWalletPublicKey) throws CryptoBrokerWalletNotFoundException, CantGetCryptoBrokerWalletSettingException, CantLoadBankMoneyWalletException {
        List<String> paymentMethod = new ArrayList<>();

        List<CryptoBrokerWalletAssociatedSetting> associatedWallets = getCryptoBrokerWalletAssociatedSettings(brokerWalletPublicKey);

        for (CryptoBrokerWalletAssociatedSetting associatedWallet : associatedWallets) {
            Currency merchandise = associatedWallet.getMerchandise();
            if (merchandise.getCode().equals(currencyCode) && associatedWallet.getPlatform() == Platforms.BANKING_PLATFORM) {
                List<BankAccountNumber> accounts = getAccounts(associatedWallet.getWalletPublicKey());
                for (BankAccountNumber accountNumber : accounts) {
                    if (associatedWallet.getBankAccount().equals(accountNumber.getAccount())) {
                        paymentMethod.add("Bank: " + accountNumber.getBankName() +
                                "\nAccount Number: " + accountNumber.getAccount() +
                                "\nAccount Type: " + accountNumber.getAccountType().getFriendlyName());
                    }
                }
            }

        }

        return paymentMethod;
    }

    @Override
    public void sendNegotiation(CustomerBrokerNegotiationInformation negotiationInfo) throws CantSendNegotiationToCryptoCustomerException {
        CustomerBrokerSaleNegotiationImpl saleNegotiationImpl = null;

        try {
            CustomerBrokerSaleNegotiation saleNegotiation = customerBrokerSaleNegotiationManager.getNegotiationsByNegotiationId(negotiationInfo.getNegotiationId());
            saleNegotiationImpl = new CustomerBrokerSaleNegotiationImpl(saleNegotiation);
            saleNegotiationImpl.changeInfo(negotiationInfo);

            if (saleNegotiationImpl.dataHasChanged())
                customerBrokerUpdateManager.createCustomerBrokerUpdateSaleNegotiationTranasction(saleNegotiationImpl);
            else
                customerBrokerCloseManager.createCustomerBrokerCloseSaleNegotiationTranasction(saleNegotiationImpl);

        } catch (CantGetListSaleNegotiationsException cause) {
            throw new CantSendNegotiationToCryptoCustomerException("Cant get the Sale Negotiation from the Data Base",
                    cause, "negotiationInfo.getNegotiationId(): " + negotiationInfo.getNegotiationId(),
                    "There is no Record of the Sale Negotiation in the Data Base");

        } catch (CantCreateCustomerBrokerUpdateSaleNegotiationTransactionException cause) {
            throw new CantSendNegotiationToCryptoCustomerException("Cant send the UPDATED saleNegotiation Transaction to the customer",
                    cause, "saleNegotiationImpl: " + saleNegotiationImpl, "N/A");

        } catch (CantCreateCustomerBrokerSaleNegotiationException cause) {
            throw new CantSendNegotiationToCryptoCustomerException("Cant send the CLOSED Sale Negotiation Transaction to the customer",
                    cause, "saleNegotiationImpl: " + saleNegotiationImpl, "N/A");

        } catch (Exception cause) {
            throw new CantSendNegotiationToCryptoCustomerException(cause.getMessage(), cause, "N/A", "N/A");
        }
    }

    @Override
    public ActorIdentity getCustomerInfoByPublicKey(String brokerPublicKey, String customerPublicKey) throws CantListActorConnectionsException {
        try {

            CryptoCustomerLinkedActorIdentity linkedActorIdentity = new CryptoCustomerLinkedActorIdentity(
                    brokerPublicKey,
                    Actors.CBP_CRYPTO_BROKER
            );

            final CryptoCustomerActorConnectionSearch search = cryptoCustomerActorConnectionManager.getSearch(linkedActorIdentity);

            search.addConnectionState(ConnectionState.CONNECTED);

            List<CryptoCustomerActorConnection> customers = search.getResult();

            for (CryptoCustomerActorConnection customer : customers) {
                if (customer.getPublicKey().equalsIgnoreCase(customerPublicKey)) {
                    return new CryptoBrokerWalletActorIdentity(customer.getPublicKey(), customer.getAlias(), customer.getImage());
                }
            }

            return null;

        } catch (CantListActorConnectionsException e) {

            throw new CantListActorConnectionsException(e, "", "Error trying to list the broker connections of the customer.");
        }

    }

    /**
     * This method list all wallet installed in device, start the transaction
     */
    @Override
    public List<InstalledWallet> getInstallWallets() throws CantListWalletsException {
        return walletManagerManager.getInstalledWallets();
    }

    @Override
    public CryptoBrokerWalletSettingSpread newEmptyCryptoBrokerWalletSetting() throws CantNewEmptyCryptoBrokerWalletSettingException {
        return new CryptoBrokerWalletSettingSpreadImpl();
    }

    @Override
    public CryptoBrokerWalletAssociatedSetting newEmptyCryptoBrokerWalletAssociatedSetting() throws CantNewEmptyCryptoBrokerWalletAssociatedSettingException {
        return new CryptoBrokerWalletAssociatedSettingImpl();
    }

    @Override
    public CryptoBrokerWalletProviderSetting newEmptyCryptoBrokerWalletProviderSetting() throws CantNewEmptyCryptoBrokerWalletProviderSettingException {
        return new CryptoBrokerWalletProviderSettingImpl();
    }

    @Override
    public BankAccountNumber newEmptyBankAccountNumber(String bankName, BankAccountType bankAccountType, String alias, String account, FiatCurrency currencyType) throws CantNewEmptyBankAccountException {
        return new BankAccountNumberImpl(bankName, bankAccountType, alias, account, currencyType);
    }

    @Override
    public void addNewAccount(BankAccountNumber bankAccountNumber, String walletPublicKey) throws CantAddNewAccountException, CantLoadBankMoneyWalletException {

        if (this.bankMoneyWalletManager.getBankName() == null)
            this.bankMoneyWalletManager.createBankName(bankAccountNumber.getBankName());
        bankMoneyWalletManager.addNewAccount(bankAccountNumber);
    }

    @Override
    public void createCashMoneyWallet(String walletPublicKey, FiatCurrency fiatCurrency) throws CantCreateCashMoneyWalletException {
        cashMoneyWalletManager.createCashMoneyWallet(walletPublicKey, fiatCurrency);
    }

    @Override
    public void saveWalletSetting(CryptoBrokerWalletSettingSpread cryptoBrokerWalletSettingSpread, String publicKeyWalletCryptoBrokerInstall) throws CantSaveCryptoBrokerWalletSettingException, CryptoBrokerWalletNotFoundException, CantGetCryptoBrokerWalletSettingException {
        //TODO: Quitar este hardcore luego que se implemente la instalacion de la wallet
        publicKeyWalletCryptoBrokerInstall = "walletPublicKeyTest";
        cryptoBrokerWalletManager.loadCryptoBrokerWallet(publicKeyWalletCryptoBrokerInstall).getCryptoWalletSetting().saveCryptoBrokerWalletSpreadSetting(cryptoBrokerWalletSettingSpread);
    }

    @Override
    public void clearWalletSetting(String publicKeyWalletCryptoBrokerInstall) throws CantClearCryptoBrokerWalletSettingException, CryptoBrokerWalletNotFoundException, CantGetCryptoBrokerWalletSettingException {
        //TODO: Quitar este hardcore luego que se implemente la instalacion de la wallet
        publicKeyWalletCryptoBrokerInstall = "walletPublicKeyTest";
        cryptoBrokerWalletManager.loadCryptoBrokerWallet(publicKeyWalletCryptoBrokerInstall).getCryptoWalletSetting().clearCryptoBrokerWalletSpreadSetting();
    }

    @Override
    public void saveWalletSettingAssociated(CryptoBrokerWalletAssociatedSetting cryptoBrokerWalletAssociatedSetting, String publicKeyWalletCryptoBrokerInstall) throws CantGetCryptoBrokerWalletSettingException, CryptoBrokerWalletNotFoundException, CantSaveCryptoBrokerWalletSettingException {
        //TODO: Quitar este hardcore luego que se implemente la instalacion de la wallet
        publicKeyWalletCryptoBrokerInstall = "walletPublicKeyTest";
        cryptoBrokerWalletManager.loadCryptoBrokerWallet(publicKeyWalletCryptoBrokerInstall).getCryptoWalletSetting().saveCryptoBrokerWalletAssociatedSetting(cryptoBrokerWalletAssociatedSetting);
    }

    @Override
    public void clearAssociatedWalletSettings(String publicKeyWalletCryptoBrokerInstall, Platforms platform) throws CryptoBrokerWalletNotFoundException, CantGetCryptoBrokerWalletSettingException, CantClearCryptoBrokerWalletSettingException {
        //TODO: Quitar este hardcode luego que se implemente la instalacion de la wallet
        publicKeyWalletCryptoBrokerInstall = "walletPublicKeyTest";
        cryptoBrokerWalletManager.loadCryptoBrokerWallet(publicKeyWalletCryptoBrokerInstall).getCryptoWalletSetting().clearCryptoBrokerWalletAssociatedSetting(platform);
    }

    @Override
    public boolean isWalletConfigured(String publicKeyWalletCryptoBrokerInstall) throws CryptoBrokerWalletNotFoundException, CantGetCryptoBrokerWalletSettingException, EarningsSettingsNotRegisteredException, CantListEarningsPairsException, CantLoadEarningSettingsException {

        CryptoBrokerWalletSettingSpread spread = getCryptoBrokerWalletSpreadSetting("walletPublicKeyTest"); //TODO: quitar este hardcodeo
        List<CryptoBrokerWalletAssociatedSetting> associatedWallets = getCryptoBrokerWalletAssociatedSettings("walletPublicKeyTest");//TODO: quitar este hardcodeo
        List<CryptoBrokerWalletProviderSetting> associatedProviders = getCryptoBrokerWalletProviderSettings("walletPublicKeyTest");//TODO: quitar este hardcodeo
        List<EarningsPair> earningsPairs;

        try {
            earningsPairs = getEarningsPairs(publicKeyWalletCryptoBrokerInstall);
        } catch (Exception e) {
            earningsPairs = new ArrayList<>();
        }

        return spread != null && !associatedWallets.isEmpty() && !associatedProviders.isEmpty() && !earningsPairs.isEmpty();
    }

    @Override
    public void createNewLocation(String location, String uri) throws CantCreateLocationSaleException {
        customerBrokerSaleNegotiationManager.createNewLocation(location, uri);
    }

    @Override
    public void updateLocation(NegotiationLocations location) throws CantUpdateLocationSaleException {
        customerBrokerSaleNegotiationManager.updateLocation(location);
    }

    @Override
    public void deleteLocation(NegotiationLocations location) throws CantDeleteLocationSaleException {
        customerBrokerSaleNegotiationManager.deleteLocation(location);
    }

    @Override
    public List<BankAccountNumber> getAccounts(String walletPublicKey) throws CantLoadBankMoneyWalletException {
        return bankMoneyWalletManager.getAccounts();
    }

    @Override
    public FiatCurrency getCashCurrency(String walletPublicKey) throws CantGetCashMoneyWalletCurrencyException, CantLoadCashMoneyWalletException {
        return cashMoneyWalletManager.loadCashMoneyWallet(walletPublicKey).getCurrency();
    }

    @Override
    public void createTransactionRestockBank(String publicKeyActor, FiatCurrency fiatCurrency, String cbpWalletPublicKey, String bankWalletPublicKey, String bankAccount, BigDecimal amount, String memo, BigDecimal priceReference, OriginTransaction originTransaction, String originTransactionId) throws CantCreateBankMoneyRestockException {
        bankMoneyRestockManager.createTransactionRestock(publicKeyActor, fiatCurrency, cbpWalletPublicKey, bankWalletPublicKey, bankAccount, amount, memo, priceReference, originTransaction, originTransactionId);
    }

    @Override
    public void createTransactionDestockBank(String publicKeyActor, FiatCurrency fiatCurrency, String cbpWalletPublicKey, String bankWalletPublicKey, String bankAccount, BigDecimal amount, String memo, BigDecimal priceReference, OriginTransaction originTransaction, String originTransactionId) throws CantCreateBankMoneyDestockException {
        bankMoneyDestockManager.createTransactionDestock(publicKeyActor, fiatCurrency, cbpWalletPublicKey, bankWalletPublicKey, bankAccount, amount, memo, priceReference, originTransaction, originTransactionId);
    }

    @Override
    public void createTransactionRestockCash(String publicKeyActor, FiatCurrency fiatCurrency, String cbpWalletPublicKey, String cshWalletPublicKey, String cashReference, BigDecimal amount, String memo, BigDecimal priceReference, OriginTransaction originTransaction, String originTransactionId) throws CantCreateCashMoneyRestockException {
        cashMoneyRestockManager.createTransactionRestock(publicKeyActor, fiatCurrency, cbpWalletPublicKey, cshWalletPublicKey, cashReference, amount, memo, priceReference, originTransaction, originTransactionId);
    }

    @Override
    public void createTransactionDestockCash(String publicKeyActor, FiatCurrency fiatCurrency, String cbpWalletPublicKey, String cshWalletPublicKey, String cashReference, BigDecimal amount, String memo, BigDecimal priceReference, OriginTransaction originTransaction, String originTransactionId) throws CantCreateCashMoneyDestockException {
        cashMoneyDestockManager.createTransactionDestock(publicKeyActor, fiatCurrency, cbpWalletPublicKey, cshWalletPublicKey, cashReference, amount, memo, priceReference, originTransaction, originTransactionId);
    }

    @Override
    public void createTransactionRestockCrypto(String publicKeyActor, CryptoCurrency cryptoCurrency, String cbpWalletPublicKey, String cryWalletPublicKey, BigDecimal amount, String memo, BigDecimal priceReference, OriginTransaction originTransaction, String originTransactionId, BlockchainNetworkType blockchainNetworkType) throws CantCreateCryptoMoneyRestockException {
        cryptoMoneyRestockManager.createTransactionRestock(publicKeyActor, cryptoCurrency, cbpWalletPublicKey, cryWalletPublicKey, amount, memo, priceReference, originTransaction, originTransactionId, blockchainNetworkType);
    }

    @Override
    public void createTransactionDestockCrypto(String publicKeyActor, CryptoCurrency cryptoCurrency, String cbpWalletPublicKey, String cryWalletPublicKey, BigDecimal amount, String memo, BigDecimal priceReference, OriginTransaction originTransaction, String originTransactionId, BlockchainNetworkType blockchainNetworkType) throws CantCreateCryptoMoneyDestockException {
        cryptoMoneyDestockManager.createTransactionDestock(publicKeyActor, cryptoCurrency, cbpWalletPublicKey, cryWalletPublicKey, amount, memo, priceReference, originTransaction, originTransactionId, blockchainNetworkType);
    }

    @Override
    public FiatIndex getMarketRate(Currency merchandise, FiatCurrency fiatCurrency, MoneyType moneyType, String walletPublicKey) throws CantGetCryptoBrokerMarketRateException, CryptoBrokerWalletNotFoundException {
        //TODO: Quitar este hardcore luego que se implemente la instalacion de la wallet
        walletPublicKey = "walletPublicKeyTest";
        return cryptoBrokerWalletManager.loadCryptoBrokerWallet(walletPublicKey).getMarketRate(merchandise, fiatCurrency, moneyType);
    }

    @Override
    public CustomerBrokerContractSale getCustomerBrokerContractSaleForContractId(String ContractId) throws CantGetListCustomerBrokerContractSaleException {
        return customerBrokerContractSaleManager.getCustomerBrokerContractSaleForContractId(ContractId);
    }

    @Override
    public List<CryptoBrokerWalletProviderSetting> getCryptoBrokerWalletProviderSettings(String walletPublicKey) throws CantGetCryptoBrokerWalletSettingException, CryptoBrokerWalletNotFoundException {
        //TODO: Quitar este hardcore luego que se implemente la instalacion de la wallet
        walletPublicKey = "walletPublicKeyTest";
        return cryptoBrokerWalletManager.loadCryptoBrokerWallet(walletPublicKey).getCryptoWalletSetting().getCryptoBrokerWalletProviderSettings();
    }

    @Override
    public Map<String, CurrencyPair> getWalletProviderAssociatedCurrencyPairs(CurrencyPair currencyPair, String walletPublicKey) throws CryptoBrokerWalletNotFoundException, CantGetCryptoBrokerWalletSettingException {
        //TODO: Quitar este hardcore luego que se implemente la instalacion de la wallet
        walletPublicKey = "walletPublicKeyTest";
        CurrencyPairImpl currencyPair1;
        CurrencyPairImpl currencyPair2;
        CurrencyPairImpl currencyPair3;
        CurrencyPairImpl currencyPair4;
        CurrencyPairImpl currencyPair5;
        CurrencyPairImpl currencyPair6;

        Map<String, CurrencyPair> map = new HashMap<>();

        final CryptoBrokerWallet cryptoBrokerWallet = cryptoBrokerWalletManager.loadCryptoBrokerWallet(walletPublicKey);
        final CryptoBrokerWalletSetting cryptoWalletSetting = cryptoBrokerWallet.getCryptoWalletSetting();
        final List<CryptoBrokerWalletAssociatedSetting> associatedWallets = cryptoWalletSetting.getCryptoBrokerWalletAssociatedSettings();

        if (!associatedWallets.isEmpty()) {
            if (associatedWallets.size() == 2) {
                currencyPair1 = new CurrencyPairImpl(
                        associatedWallets.get(0).getMerchandise(),
                        associatedWallets.get(1).getMerchandise());

                currencyPair2 = new CurrencyPairImpl(
                        associatedWallets.get(1).getMerchandise(),
                        associatedWallets.get(0).getMerchandise());

                map.put("par1", currencyPair1);
                map.put("par2", currencyPair2);
            }
            if (associatedWallets.size() == 3) {
                currencyPair1 = new CurrencyPairImpl(
                        associatedWallets.get(0).getMerchandise(),
                        associatedWallets.get(1).getMerchandise());

                currencyPair2 = new CurrencyPairImpl(
                        associatedWallets.get(0).getMerchandise(),
                        associatedWallets.get(2).getMerchandise());

                currencyPair3 = new CurrencyPairImpl(
                        associatedWallets.get(1).getMerchandise(),
                        associatedWallets.get(0).getMerchandise());

                currencyPair4 = new CurrencyPairImpl(
                        associatedWallets.get(1).getMerchandise(),
                        associatedWallets.get(2).getMerchandise());

                currencyPair5 = new CurrencyPairImpl(
                        associatedWallets.get(2).getMerchandise(),
                        associatedWallets.get(0).getMerchandise());

                currencyPair6 = new CurrencyPairImpl(
                        associatedWallets.get(2).getMerchandise(),
                        associatedWallets.get(1).getMerchandise());

                map.put("par1", currencyPair1);
                map.put("par2", currencyPair2);
                map.put("par3", currencyPair3);
                map.put("par4", currencyPair4);
                map.put("par5", currencyPair5);
                map.put("par6", currencyPair6);
            }
        }

        return map;
    }

    @Override
    public List<CryptoBrokerWalletAssociatedSetting> getCryptoBrokerWalletAssociatedSettings(String walletPublicKey) throws CantGetCryptoBrokerWalletSettingException, CryptoBrokerWalletNotFoundException {
        //TODO: Quitar este hardcore luego que se implemente la instalacion de la wallet
        walletPublicKey = "walletPublicKeyTest";
        return cryptoBrokerWalletManager.loadCryptoBrokerWallet(walletPublicKey).getCryptoWalletSetting().getCryptoBrokerWalletAssociatedSettings();
    }

    @Override
    public CryptoBrokerWalletSettingSpread getCryptoBrokerWalletSpreadSetting(String walletPublicKey) throws CantGetCryptoBrokerWalletSettingException, CryptoBrokerWalletNotFoundException {
        //TODO: Quitar este hardcore luego que se implemente la instalacion de la wallet
        walletPublicKey = "walletPublicKeyTest";
        return cryptoBrokerWalletManager.loadCryptoBrokerWallet(walletPublicKey).getCryptoWalletSetting().getCryptoBrokerWalletSpreadSetting();
    }

    @Override
    public ExchangeRate getExchangeRateFromDate(final Currency currencyFrom, final Currency currencyTo, Calendar calendar, UUID providerId) throws UnsupportedCurrencyPairException, CantGetExchangeRateException, CantGetProviderException {
        CurrencyPair currencyPair = new CurrencyPair() {
            @Override
            public Currency getFrom() {
                return currencyFrom;
            }

            @Override
            public Currency getTo() {
                return currencyTo;
            }
        };
        return currencyExchangeProviderFilterManager.getProviderReference(providerId).getExchangeRateFromDate(currencyPair, calendar);
    }

    @Override
    public Collection<ExchangeRate> getDailyExchangeRatesForPeriod(final Currency currencyFrom, final Currency currencyTo, Calendar startCalendar, Calendar endCalendar, UUID providerId) throws UnsupportedCurrencyPairException, CantGetExchangeRateException, CantGetProviderException {
        CurrencyPair currencyPair = new CurrencyPair() {
            @Override
            public Currency getFrom() {
                return currencyFrom;
            }

            @Override
            public Currency getTo() {
                return currencyTo;
            }
        };
        return currencyExchangeProviderFilterManager.getProviderReference(providerId).getDailyExchangeRatesForPeriod(currencyPair, startCalendar, endCalendar);
    }

    @Override
    public List<CryptoBrokerStockTransaction> getStockHistory(Currency merchandise, MoneyType moneyType, int offset, long timeStamp, String walletPublicKey) throws CantGetCryptoBrokerStockTransactionException {
        //TODO: Quitar este hardcore luego que se implemente la instalacion de la wallet
        walletPublicKey = "walletPublicKeyTest";
        try {
            return cryptoBrokerWalletManager.loadCryptoBrokerWallet(walletPublicKey).getStockHistory(merchandise, moneyType, offset, timeStamp);
        } catch (CryptoBrokerWalletNotFoundException e) {
            throw new CantGetCryptoBrokerStockTransactionException("Crypto Broker Wallet Not Found,", e, "", "");
        }
    }

    @Override
    public float getAvailableBalance(Currency merchandise, String walletPublicKey) throws CantGetAvailableBalanceCryptoBrokerWalletException, CryptoBrokerWalletNotFoundException, CantGetStockCryptoBrokerWalletException, CantStartPluginException {
        //TODO: Quitar este hardcore luego que se implemente la instalacion de la wallet
        walletPublicKey = "walletPublicKeyTest";
        return cryptoBrokerWalletManager.loadCryptoBrokerWallet(walletPublicKey).getStockBalance().getAvailableBalance(merchandise);
    }

    @Override
    public Collection<CurrencyPairAndProvider> getProviderReferencesFromCurrencyPair(final Currency currencyFrom, final Currency currencyTo) throws CantGetProviderException, CantGetProviderInfoException {
        CurrencyPair currencyPair = new CurrencyPair() {
            @Override
            public Currency getFrom() {
                return currencyFrom;
            }

            @Override
            public Currency getTo() {
                return currencyTo;
            }
        };
        final Collection<CurrencyExchangeRateProviderManager> providers = currencyExchangeProviderFilterManager.
                getProviderReferencesFromCurrencyPair(currencyPair);

        List<CurrencyPairAndProvider> providerList = new ArrayList<>();
        for (CurrencyExchangeRateProviderManager provider : providers) {
            providerList.add(new CurrencyPairAndProvider(currencyFrom, currencyTo, provider.getProviderId(), provider.getProviderName()));
        }

        return providerList;
    }

    @Override
    public void saveCryptoBrokerWalletProviderSetting(CryptoBrokerWalletProviderSetting cryptoBrokerWalletProviderSetting, String walletPublicKey) throws CantSaveCryptoBrokerWalletSettingException, CryptoBrokerWalletNotFoundException, CantGetCryptoBrokerWalletSettingException {
        //TODO: Quitar este hardcore luego que se implemente la instalacion de la wallet
        walletPublicKey = "walletPublicKeyTest";
        cryptoBrokerWalletManager.loadCryptoBrokerWallet(walletPublicKey).getCryptoWalletSetting().saveCryptoBrokerWalletProviderSetting(cryptoBrokerWalletProviderSetting);
    }

    @Override
    public void clearCryptoBrokerWalletProviderSetting(String walletPublicKey) throws CantClearCryptoBrokerWalletSettingException, CryptoBrokerWalletNotFoundException, CantGetCryptoBrokerWalletSettingException {
        //TODO: Quitar este hardcore luego que se implemente la instalacion de la wallet
        walletPublicKey = "walletPublicKeyTest";
        cryptoBrokerWalletManager.loadCryptoBrokerWallet(walletPublicKey).getCryptoWalletSetting().clearCryptoBrokerWalletProviderSetting();
    }

    @Override
    public void createNewBankAccount(NegotiationBankAccount bankAccount) throws CantCreateBankAccountSaleException {
        //customerBrokerSaleNegotiationManager.createNewBankAccount(bankAccount);
    }

    @Override
    public void deleteBankAccount(NegotiationBankAccount bankAccount) throws CantDeleteBankAccountSaleException {
        //customerBrokerSaleNegotiationManager.deleteBankAccount(bankAccount);
    }

    @Override //TODO BNK
    public double getBalanceBankWallet(String walletPublicKey, String accountNumber) throws CantCalculateBalanceException, CantLoadBankMoneyWalletException {
        final BankMoneyWalletBalance availableBalance = bankMoneyWalletManager.getAvailableBalance();
        return availableBalance.getBalance(accountNumber);
    }

    @Override
    public BigDecimal getBalanceCashWallet(String walletPublicKey) throws CantGetCashMoneyWalletBalanceException, CantLoadCashMoneyWalletException {
        return cashMoneyWalletManager.loadCashMoneyWallet(walletPublicKey).getAvailableBalance().getBalance();
    }

    @Override
    public boolean cashMoneyWalletExists(String walletPublicKey) {
        return cashMoneyWalletManager.cashMoneyWalletExists(walletPublicKey);
    }

    @Override//TODO CCP - CBP
    public long getBalanceBitcoinWallet(String walletPublicKey) throws com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantCalculateBalanceException, CantLoadWalletsException {
        try {
            return bitcoinWalletManager.loadWallet(walletPublicKey).getBalance(BalanceType.AVAILABLE).getBalance();
        } catch (com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantCalculateBalanceException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public ActiveActorIdentityInformation getSelectedActorIdentity() throws CantGetSelectedActorIdentityException {
        return null;
    }

    @Override
    public void createIdentity(String name, String phrase, byte[] profile_img) throws Exception {
        cryptoBrokerIdentityManager.createCryptoBrokerIdentity(name, profile_img);
    }

    @Override
    public void setAppPublicKey(String publicKey) {
        //DO NOTHING...
    }

    @Override
    public int[] getMenuNotifications() {
        return new int[0];
    }

    @Override
    public CustomerBrokerContractSale getCustomerBrokerContractSaleByNegotiationId(
            String negotiationId) throws CantGetListCustomerBrokerContractSaleException {
        //Collection<CustomerBrokerContractSale> customerBrokerContractSales = customerBrokerContractSaleManager.getAllCustomerBrokerContractSale();
        //String negotiationIdFromCollection;
        /*for(CustomerBrokerContractSale customerBrokerContractSale : customerBrokerContractSale){
            negotiationIdFromCollection=customerBrokerContractSale.getNegotiationId();
            if(negotiationIdFromCollection.equals(negotiationId)){
                return customerBrokerContractSale;
            }
        }
        throw new CantGetListCustomerBrokerContractSaleException(
                "Cannot find the contract associated to negotiation "+negotiationId);*/

        //TODO: This line is only for testing, remove this MOCK
        return new CustomerBrokerContractSaleMock();
    }

    @Override
    public MoneyType getMoneyTypeFromContract(CustomerBrokerContractSale customerBrokerContractSale, ContractDetailType contractDetailType)
            throws CantGetListSaleNegotiationsException {
        try {
            String negotiationId = customerBrokerContractSale.getNegotiatiotId();
            CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation = customerBrokerSaleNegotiationManager.getNegotiationsByNegotiationId(UUID.fromString(negotiationId));
            Collection<Clause> clauses = customerBrokerSaleNegotiation.getClauses();
            ClauseType clauseType;
            for (Clause clause : clauses) {
                clauseType = clause.getType();

                switch (contractDetailType) {
                    case BROKER_DETAIL:
                        if (clauseType.equals(ClauseType.BROKER_PAYMENT_METHOD)) {
                            return MoneyType.getByCode(clause.getValue());
                        }
                    case CUSTOMER_DETAIL:
                        if (clauseType.equals(ClauseType.CUSTOMER_PAYMENT_METHOD)) {
                            return MoneyType.getByCode(clause.getValue());
                        }
                }
            }
            throw new CantGetListSaleNegotiationsException("Cannot find the proper clause");
        } catch (InvalidParameterException e) {
            throw new CantGetListSaleNegotiationsException("Cannot get the negotiation list", e);
        } catch (CantGetListClauseException e) {
            throw new CantGetListSaleNegotiationsException("Cannot find clauses list");
        }

    }

    @Override
    public ContractStatus getContractStatus(String contractHash) throws CantGetListCustomerBrokerContractSaleException {

        CustomerBrokerContractSale customerBrokerContractSale;
        //TODO: This is the real implementation
        customerBrokerContractSale = this.customerBrokerContractSaleManager.getCustomerBrokerContractSaleForContractId(contractHash);
        /*//TODO: for testing
        CustomerBrokerContractSaleManager customerBrokerContractSaleManagerMock = new CustomerBrokerContractSaleManagerMock();
        customerBrokerContractSale = customerBrokerContractSaleManagerMock.getCustomerBrokerContractSaleForContractId(contractHash);*/
        //End of testing
        return customerBrokerContractSale.getStatus();

    }

    @Override
    public void submitMerchandise(String contractHash) throws CantSubmitMerchandiseException {
        try {
            CustomerBrokerContractSale customerBrokerContractSale;
            //TODO: This is the real implementation
            customerBrokerContractSale = this.customerBrokerContractSaleManager.getCustomerBrokerContractSaleForContractId(contractHash);

            //TODO: for testing
            /*CustomerBrokerContractSaleManager customerBrokerContractSaleManagerMock = new CustomerBrokerContractSaleManagerMock();
            customerBrokerContractSale = customerBrokerContractSaleManagerMock.getCustomerBrokerContractSaleForContractId(contractHash);*/
            //End of Mock testing
            //I need to discover the merchandise type (online or offline)
            String negotiationId = customerBrokerContractSale.getNegotiatiotId();
            CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation = this.customerBrokerSaleNegotiationManager.getNegotiationsByNegotiationId(UUID.fromString(negotiationId));
            /*//TODO: remove this mock
            customerBrokerSaleNegotiation = new SaleNegotiationOnlineMock();*/
            ContractClauseType contractClauseType = getContractClauseType(customerBrokerSaleNegotiation, ClauseType.BROKER_PAYMENT_METHOD);

            // Case: sending crypto merchandise.
            if (contractClauseType.getCode() == ContractClauseType.CRYPTO_TRANSFER.getCode()) {

                // TODO: here we need to get the CCP Wallet public key to send BTC to customer, when the settings is finished, please, implement how to get the CCP Wallet public  key here. Thanks.
                String cryptoBrokerPublicKey = "walletPublicKeyTest"; //TODO: this is a hardcoded public key
                BigDecimal referencePrice = BigDecimal.TEN; //TODO: this is a hardcoded reference price
                this.brokerSubmitOnlineMerchandiseManager.submitMerchandise(referencePrice, cryptoBrokerPublicKey, contractHash);
            }

            // Case: sending offline merchandise.
            if (contractClauseType == ContractClauseType.BANK_TRANSFER || contractClauseType == ContractClauseType.CASH_DELIVERY
                    || contractClauseType == ContractClauseType.CASH_ON_HAND) {
                String cryptoBrokerPublicKey = "walletPublicKeyTest"; //TODO: this is a hardcoded public key
                BigDecimal referencePrice = BigDecimal.TEN; //TODO: this is a hardcoded reference price
                this.brokerSubmitOfflineMerchandiseManager.submitMerchandise(referencePrice, cryptoBrokerPublicKey, contractHash);
            }
        } catch (CantGetListCustomerBrokerContractSaleException e) {
            throw new CantSubmitMerchandiseException(e, "Submitting the merchandise", "Cannot get the contract");
        } catch (CantGetListClauseException e) {
            throw new CantSubmitMerchandiseException(e, "Submitting the merchandise", "Cannot get the clauses list");
        } catch (CantGetListSaleNegotiationsException e) {
            throw new CantSubmitMerchandiseException(e, "Submitting the merchandise", "Cannot get the negotiation list");
        }
    }

    /**
     * This method validate if has enough stock for send a merchandise according the contract elements.
     *
     * @param contractHash
     */
    @Override
    public boolean stockInTheWallet(String contractHash) throws CantSubmitMerchandiseException {
        try {

            CryptoBrokerWalletAssociatedSetting walletAssociated;
            Platforms merchandiseWalletPlatform = null;
            double balance = 0;
            String cryptoBrokerPublicKey = "walletPublicKeyTest"; //TODO: this is a hardcoded public key
            Currency merchandiseCurrency = null;

            CustomerBrokerContractSale customerBrokerContractSale = this.customerBrokerContractSaleManager.getCustomerBrokerContractSaleForContractId(contractHash);
            String negotiationId = customerBrokerContractSale.getNegotiatiotId();
            CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation = this.customerBrokerSaleNegotiationManager.getNegotiationsByNegotiationId(UUID.fromString(negotiationId));

            final Collection<Clause> clauses = customerBrokerSaleNegotiation.getClauses();
            final String moneyTypeCode = NegotiationClauseHelper.getNegotiationClauseValue(clauses, ClauseType.BROKER_PAYMENT_METHOD);
            final MoneyType moneyType = MoneyType.getByCode(moneyTypeCode);
            final String merchandiseCurrencyCode = NegotiationClauseHelper.getNegotiationClauseValue(clauses, ClauseType.CUSTOMER_CURRENCY);
            final double amount = parseToDouble(NegotiationClauseHelper.getNegotiationClauseValue(clauses, ClauseType.CUSTOMER_CURRENCY_QUANTITY));

            switch (moneyType) {
                case CRYPTO:
                    //STOCK IN CCP
                    merchandiseWalletPlatform = Platforms.CRYPTO_CURRENCY_PLATFORM;
                    merchandiseCurrency = CryptoCurrency.getByCode(merchandiseCurrencyCode);
                    walletAssociated = getWalletAssociated(cryptoBrokerPublicKey, merchandiseWalletPlatform, merchandiseCurrency);
                    if (walletAssociated.getWalletPublicKey().isEmpty())
                        throw new CantSubmitMerchandiseException(null, "Submitting the merchandise, Validate Stock", "getPublicKeyWalletAssociated IS NULL");
                    balance = (double) bitcoinWalletManager.loadWallet(walletAssociated.getWalletPublicKey()).getBalance(BalanceType.AVAILABLE).getBalance();
                    break;
                case BANK:
                    //STOCK IN BNK
                    merchandiseWalletPlatform = Platforms.BANKING_PLATFORM;
                    merchandiseCurrency = FiatCurrency.getByCode(merchandiseCurrencyCode);
                    walletAssociated = getWalletAssociated(cryptoBrokerPublicKey, merchandiseWalletPlatform, merchandiseCurrency);

                    if (walletAssociated != null) {
                        if (walletAssociated.getWalletPublicKey().isEmpty())
                            throw new CantSubmitMerchandiseException(null, "Submitting the merchandise, Validate Stock", "getPublicKeyWalletAssociated IS NULL");

                        final BankMoneyWalletBalance availableBalance = bankMoneyWalletManager.getAvailableBalance();
                        balance = availableBalance.getBalance(walletAssociated.getBankAccount());
                    }

                    break;
                default:
                    //STOCK IN CSH
                    merchandiseWalletPlatform = Platforms.CASH_PLATFORM;
                    merchandiseCurrency = FiatCurrency.getByCode(merchandiseCurrencyCode);
                    walletAssociated = getWalletAssociated(cryptoBrokerPublicKey, merchandiseWalletPlatform, merchandiseCurrency);
                    if (walletAssociated.getWalletPublicKey().isEmpty())
                        throw new CantSubmitMerchandiseException(null, "Submitting the merchandise, Validate Stock", "getPublicKeyWalletAssociated IS NULL");
                    balance = cashMoneyWalletManager.loadCashMoneyWallet(walletAssociated.getWalletPublicKey()).getAvailableBalance().getBalance().doubleValue();
                    break;
            }

            if (balance >= amount) return Boolean.TRUE;
            return Boolean.FALSE;

        } catch (CantGetListCustomerBrokerContractSaleException e) {
            throw new CantSubmitMerchandiseException(e, "Submitting the merchandise, Validate Stock", "Cannot get the contract");
        } catch (CantGetListClauseException e) {
            throw new CantSubmitMerchandiseException(e, "Submitting the merchandise, Validate Stock", "Cannot get the clauses list");
        } catch (CantGetListSaleNegotiationsException e) {
            throw new CantSubmitMerchandiseException(e, "Submitting the merchandise, Validate Stock", "Cannot get the negotiation list");
        } catch (CantLoadWalletsException | com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantCalculateBalanceException e) {
            throw new CantSubmitMerchandiseException(e, "Submitting the merchandise, Validate Stock", "Cannot get Blance of the Crypto Currency Wallet");
        } catch (CantCalculateBalanceException e) {
            throw new CantSubmitMerchandiseException(e, "Submitting the merchandise, Validate Stock", "Cannot get Blance of the Bank Currency Wallet");
        } catch (CantLoadCashMoneyWalletException | CantGetCashMoneyWalletBalanceException e) {
            throw new CantSubmitMerchandiseException(e, "Submitting the merchandise, Validate Stock", "Cannot get Blance of the Cash Currency Wallet");
        } catch (InvalidParameterException e) {
            throw new CantSubmitMerchandiseException(e, "Submitting the merchandise, Validate Stock", "Invalidate Parameter Exception");
        }

    }

    @Override
    public ContractStatus ackPayment(String contractHash) throws CantAckPaymentException {
        try {
            CustomerBrokerContractSale customerBrokerContractSale;
            //TODO: This is the real implementation
            customerBrokerContractSale = this.customerBrokerContractSaleManager.getCustomerBrokerContractSaleForContractId(contractHash);
            //TODO: for testing
            /*CustomerBrokerContractSaleManager customerBrokerContractSaleManagerMock = new CustomerBrokerContractSaleManagerMock();
            customerBrokerContractSale = customerBrokerContractSaleManagerMock.getCustomerBrokerContractSaleForContractId(contractHash);*/
            //End of Mock testing
            //System.out.println("From module:"+customerBrokerContractPurchase);
            String negotiationId = customerBrokerContractSale.getNegotiatiotId();
            CustomerBrokerSaleNegotiation customerBrokerPurchaseNegotiation = this.customerBrokerSaleNegotiationManager.getNegotiationsByNegotiationId(UUID.fromString(negotiationId));
            /*//TODO: remove this mock
            customerBrokerPurchaseNegotiation = new SaleNegotiationOfflineMock();*/
            ContractClauseType contractClauseType = getContractClauseType(customerBrokerPurchaseNegotiation, ClauseType.CUSTOMER_PAYMENT_METHOD);

            // Case: ack crypto merchandise.
            if (contractClauseType == ContractClauseType.CRYPTO_TRANSFER) {
                return customerBrokerContractSale.getStatus();
            }

            // Case: ack offline merchandise.
            if (contractClauseType == ContractClauseType.BANK_TRANSFER
                    || contractClauseType == ContractClauseType.CASH_DELIVERY
                    || contractClauseType == ContractClauseType.CASH_ON_HAND) {

                String cryptoBrokerPublicKey = "walletPublicKeyTest"; //TODO: this is a hardcoded public key
                //Get the customer alias to show in contract execution
                ActorIdentity actorIdentity = getCustomerInfoByPublicKey(
                        customerBrokerContractSale.getPublicKeyBroker(),
                        customerBrokerContractSale.getPublicKeyCustomer()
                );
                String customerAlias;
                if (actorIdentity == null) {
                    customerAlias = "Unregistered customer";
                } else {
                    customerAlias = actorIdentity.getAlias();
                }
                this.brokerAckOfflinePaymentManager.ackPayment(cryptoBrokerPublicKey, contractHash, customerBrokerContractSale.getPublicKeyBroker(), customerAlias);
                return customerBrokerContractSale.getStatus();
            }

            throw new CantAckPaymentException("Cannot find the contract clause");

        } catch (CantGetListCustomerBrokerContractSaleException e) {
            throw new CantAckPaymentException(e, "Cannot ack the merchandise", "Cannot get the contract");
        } catch (CantGetListClauseException e) {
            throw new CantAckPaymentException(e, "Cannot ack the merchandise", "Cannot get the clauses list");
        } catch (CantGetListSaleNegotiationsException e) {
            throw new CantAckPaymentException(e, "Cannot ack the merchandise", "Cannot get the negotiation list");
        } catch (CantListActorConnectionsException e) {
            throw new CantAckPaymentException(e, "Cannot ack the merchandise", "Cannot get the customer alias");
        }
    }

    @Override
    public EarningsPair addEarningsPairToEarningSettings(Currency earningCurrency, Currency linkedCurrency, String earningWalletPublicKey,
                                                         String brokerWalletPublicKey) throws CantLoadEarningSettingsException, CantAssociatePairException, PairAlreadyAssociatedException {

        EarningsSettings earningsSettings;
        EarningsPair earningsPair = null;

        try {
            earningsSettings = matchingEngineManager.loadEarningsSettings(brokerWalletPublicKey);
        } catch (EarningsSettingsNotRegisteredException ex) {

            try {
                earningsSettings = matchingEngineManager.registerEarningsSettings(new WalletReference(brokerWalletPublicKey));
            } catch (CantRegisterEarningsSettingsException e) {
                throw new CantAssociatePairException(e, "", "");
            }
        }

        try {
            earningsPair = earningsSettings.registerPair(earningCurrency, linkedCurrency, new WalletReference(earningWalletPublicKey));
        } catch (CantAssociatePairException | PairAlreadyAssociatedException e) {

            try {
                for (EarningsPair ep : earningsSettings.listEarningPairs()) {
                    if (ep.getEarningCurrency() == earningCurrency && ep.getLinkedCurrency() == linkedCurrency)
                        earningsSettings.updateEarningsPair(ep.getId(), new WalletReference(brokerWalletPublicKey));
                }
            } catch (CantListEarningsPairsException | CantUpdatePairException | PairNotFoundException ex) {
                throw new CantAssociatePairException(ex, "Cant update earnings pair", "");
            }
        }

        return earningsPair;
    }

    @Override
    public void clearEarningPairsFromEarningSettings(String brokerWalletPublicKey) throws CantLoadEarningSettingsException, CantDisassociatePairException {
        EarningsSettings earningsSettings;

        try {
            earningsSettings = matchingEngineManager.loadEarningsSettings(brokerWalletPublicKey);
        } catch (EarningsSettingsNotRegisteredException earningsSettingsNotRegisteredException) {
            earningsSettings = null;
        }

        if (earningsSettings == null) {
            try {
                earningsSettings = matchingEngineManager.registerEarningsSettings(new WalletReference(brokerWalletPublicKey));
            } catch (CantRegisterEarningsSettingsException e) {
                throw new CantLoadEarningSettingsException(e, "", "");
            }
        }

        try {
            for (EarningsPair ep : earningsSettings.listEarningPairs()) {
                earningsSettings.disassociateEarningsPair(ep.getId());
            }
        } catch (Exception e) {
            throw new CantDisassociatePairException(e, "clearEarningPairsFromEarningSettings", "Cant disassociate earnings pair or pair not found");
        }
    }


    @Override
    public List<EarningsPair> getEarningsPairs(String brokerWalletPublicKey) throws CantLoadEarningSettingsException, EarningsSettingsNotRegisteredException, CantListEarningsPairsException {
        EarningsSettings earningsSettings = matchingEngineManager.loadEarningsSettings(brokerWalletPublicKey);
        return earningsSettings.listEarningPairs();
    }

    @Override
    public boolean extractEarnings(EarningsPair earningsPair, List<EarningTransaction> earningTransactions) throws CantExtractEarningsException {
        final EarningExtractorManager earningsExtractorManager = matchingEngineManager.getEarningsExtractorManager();
        return earningsExtractorManager.extractEarnings(earningsPair, earningTransactions);
    }

    @Override
    public List<EarningTransaction> searchEarnings(EarningsPair earningsPair, EarningTransactionState state) throws CantListEarningTransactionsException {
        final EarningsSearch earningsSearch = matchingEngineManager.getSearch(earningsPair);
        earningsSearch.setTransactionStateFilter(EarningTransactionState.CALCULATED);

        return earningsSearch.listResults();
    }

    @Override
    public List<EarningTransaction> searchEarnings(EarningsPair earningsPair) throws CantListEarningTransactionsException {
        final EarningsSearch earningsSearch = matchingEngineManager.getSearch(earningsPair);
        return earningsSearch.listResults();
    }

    private ContractClauseType getContractClauseType(CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation, ClauseType paramClauseType) throws CantGetListClauseException {
        try {
            //I will check if customerBrokerSaleNegotiation is null
            ObjectChecker.checkArgument(customerBrokerSaleNegotiation, "The customerBrokerSaleNegotiation is null");
            Collection<Clause> clauses = customerBrokerSaleNegotiation.getClauses();
            ClauseType clauseType;
            for (Clause clause : clauses) {
                clauseType = clause.getType();
                if (clauseType.getCode().equals(paramClauseType.getCode())) {
                    return ContractClauseType.getByCode(clause.getValue());
                }
            }
            throw new CantGetListClauseException("Cannot find the proper clause");
        } catch (InvalidParameterException e) {
            throw new CantGetListClauseException(
                    "An invalid parameter is found in ContractClauseType enum");
        } catch (ObjectNotSetException e) {
            throw new CantGetListClauseException(
                    "The CustomerBrokerSaleNegotiation is null");
        }

    }

    /**
     * This method get wallet associated a the param indicated.
     *
     * @param walletPublicKey
     * @param merchandiseWalletPlatform
     * @param merchandiseCurrency
     *
     * @return CryptoBrokerWalletAssociatedSetting associated
     */
    private CryptoBrokerWalletAssociatedSetting getWalletAssociated(String walletPublicKey, Platforms merchandiseWalletPlatform, Currency merchandiseCurrency) throws CantSubmitMerchandiseException {

        try {

            final CryptoBrokerWallet cryptoBrokerWallet = cryptoBrokerWalletManager.loadCryptoBrokerWallet(walletPublicKey);
            final CryptoBrokerWalletSetting cryptoBrokerWalletSetting = cryptoBrokerWallet.getCryptoWalletSetting();
            List<CryptoBrokerWalletAssociatedSetting> associatedWallets = cryptoBrokerWalletSetting.getCryptoBrokerWalletAssociatedSettings();

            if (!associatedWallets.isEmpty()) {

                for (CryptoBrokerWalletAssociatedSetting cryptoBrokerWalletAssociatedSetting : associatedWallets) {

                    Platforms associatedWalletPlatform = cryptoBrokerWalletAssociatedSetting.getPlatform();
                    Currency associatedWalletMerchandise = cryptoBrokerWalletAssociatedSetting.getMerchandise();

                    if (associatedWalletPlatform == merchandiseWalletPlatform && associatedWalletMerchandise == merchandiseCurrency)
                        return cryptoBrokerWalletAssociatedSetting;

                }

            }

        } catch (CryptoBrokerWalletNotFoundException e) {
            throw new CantSubmitMerchandiseException(e, "Submitting the merchandise, Validate Stock, Get Public Key Associated", "Cannot get Blance of the Cash Currency Wallet");
        } catch (CantGetCryptoBrokerWalletSettingException e) {
            throw new CantSubmitMerchandiseException(e, "Submitting the merchandise, Validate Stock, Get Public Key Associated", "Cannot get Blance of the Cash Currency Wallet");
        }

        return null;

    }

    private Currency getCurrencyFromCode(String currencyCode) throws InvalidParameterException {
        Currency currency = null;
        if (FiatCurrency.codeExists(currencyCode)) {
            currency = FiatCurrency.getByCode(currencyCode);
        } else if (CryptoCurrency.codeExists(currencyCode)) {
            currency = CryptoCurrency.getByCode(currencyCode);
        }

        return currency;
    }

    private double parseToDouble(String stringValue) throws InvalidParameterException {
        if (stringValue == null) {
            throw new InvalidParameterException("Cannot parse a null string value to long");
        } else {
            try {
                return NumberFormat.getInstance().parse(stringValue).doubleValue();
            } catch (Exception exception) {
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, FermatException.wrapException(exception),
                        "Parsing String object to long", "Cannot parse " + stringValue + " string value to long");
            }
        }
    }
}
