package com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantListActorConnectionsException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantGetSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.SettingsNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.CantLoadWalletsException;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankAccountType;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankAccountNumber;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ActorType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractDetailType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationType;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantGetCompletionDateException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationBankAccount;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationLocations;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantClearAssociatedCustomerIdentityWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantCreateNewCustomerIdentityWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantGetCustomerIdentityWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantGetListActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.RelationshipNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.ActorExtraData;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.ActorExtraDataManager;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.CustomerIdentityWalletRelationship;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.QuotesExtraData;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.interfaces.CryptoBrokerActorConnectionManager;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.interfaces.CryptoBrokerActorConnectionSearch;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.utils.CryptoBrokerActorConnection;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.utils.CryptoBrokerLinkedActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.broker_ack_offline_payment.interfaces.BrokerAckOfflinePaymentManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.broker_ack_online_payment.interfaces.BrokerAckOnlinePaymentManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.broker_submit_offline_merchandise.interfaces.BrokerSubmitOfflineMerchandiseManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.broker_submit_online_merchandise.interfaces.BrokerSubmitOnlineMerchandiseManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantAckMerchandiseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantSendPaymentException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.ObjectChecker;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.mocks.CustomerBrokerContractPurchaseMock;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_ack_offline_merchandise.interfaces.CustomerAckOfflineMerchandiseManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_ack_online_merchandise.interfaces.CustomerAckOnlineMerchandiseManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_offline_payment.interfaces.CustomerOfflinePaymentManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_online_payment.interfaces.CustomerOnlinePaymentManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantGetListCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchase;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchaseManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.ListsForStatusPurchase;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.exceptions.CantGetCryptoCustomerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.exceptions.CantListCryptoCustomerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.exceptions.IdentityNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.interfaces.CryptoCustomerIdentity;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.interfaces.CryptoCustomerIdentityManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantCreateBankAccountPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantCreateCustomerBrokerPurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantCreateLocationPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantDeleteBankAccountPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantDeleteLocationPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantGetListBankAccountsPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantGetListLocationsPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantGetListPurchaseNegotiationsException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantUpdateBankAccountPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetListClauseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.interfaces.CustomerBrokerCloseManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.exceptions.CantCreateCustomerBrokerNewPurchaseNegotiationTransactionException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.interfaces.CustomerBrokerNewManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_update.exceptions.CantCancelNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_update.exceptions.CantCreateCustomerBrokerUpdatePurchaseNegotiationTransactionException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_update.interfaces.CustomerBrokerUpdateManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CantGetAssociatedIdentityException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CantGetContractHistoryException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CantGetContractsWaitingForBrokerException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CantGetContractsWaitingForCustomerException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CantGetNegotiationInformationException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CantGetNegotiationsWaitingForBrokerException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CantGetNegotiationsWaitingForCustomerException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CouldNotCancelNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ContractBasicInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.IndexInfoSummary;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.MerchandiseExchangeRate;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantClearCryptoCustomerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantGetCryptoBrokerListException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantGetCryptoCustomerIdentityListException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantGetProvidersCurrentExchangeRatesException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantNewEmptyCryptoCustomerWalletAssociatedSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantNewEmptyCryptoCustomerWalletProviderSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantNewEmptyCustomerBrokerNegotiationInformationException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CouldNotStartNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CouldNotUpdateNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.IdentityAssociatedNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.BrokerIdentityBusinessInfo;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.CryptoCustomerWalletModuleManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.settings.CryptoCustomerWalletAssociatedSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.settings.CryptoCustomerWalletPreferenceSettings;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.settings.CryptoCustomerWalletProviderSetting;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.CurrencyPair;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.ExchangeRate;
import com.bitdubai.fermat_cer_api.all_definition.utils.CurrencyPairImpl;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantGetExchangeRateException;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantGetProviderInfoException;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.UnsupportedCurrencyPairException;
import com.bitdubai.fermat_cer_api.layer.provider.interfaces.CurrencyExchangeRateProviderManager;
import com.bitdubai.fermat_cer_api.layer.search.exceptions.CantGetProviderException;
import com.bitdubai.fermat_cer_api.layer.search.interfaces.CurrencyExchangeProviderFilterManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantListWalletsException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletManagerManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * Created by nelson on 11/11/15.
 * Modified by Franklin Marcano 30/12/15
 */
public class CryptoCustomerWalletModuleCryptoCustomerWalletManager implements CryptoCustomerWalletModuleManager {

    private final WalletManagerManager walletManagerManager;
    private final CryptoBrokerActorConnectionManager cryptoBrokerActorConnectionManager;
    private final CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager;
    private final CryptoCustomerIdentityManager cryptoCustomerIdentityManager;
    private final CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager;
    private final CustomerBrokerNewManager customerBrokerNewManager;
    private final CustomerBrokerUpdateManager customerBrokerUpdateManager;
    private final CustomerBrokerCloseManager customerBrokerCloseManager;
    private final CurrencyExchangeProviderFilterManager currencyExchangeProviderFilterManager;
    private final ActorExtraDataManager actorExtraDataManager;
    private final CustomerOnlinePaymentManager customerOnlinePaymentManager;
    private final CustomerOfflinePaymentManager customerOfflinePaymentManager;
    private final CustomerAckOnlineMerchandiseManager customerAckOnlineMerchandiseManager;
    private final CustomerAckOfflineMerchandiseManager customerAckOfflineMerchandiseManager;
    private final BrokerAckOnlinePaymentManager brokerAckOnlinePaymentManager;
    private final BrokerAckOfflinePaymentManager brokerAckOfflinePaymentManager;
    private final BrokerSubmitOnlineMerchandiseManager brokerSubmitOnlineMerchandiseManager;
    private final BrokerSubmitOfflineMerchandiseManager brokerSubmitOfflineMerchandiseManager;
    private final BitcoinWalletManager bitcoinWalletManager;
    private final PluginVersionReference pluginVersionReference;
    private final ErrorManager errorManager;
    private final PluginFileSystem pluginFileSystem;
    private final UUID pluginId;

    private  SettingsManager<CryptoCustomerWalletPreferenceSettings> settingsManager;
    private String merchandise = null;
    private String typeOfPayment = null;
    private String paymentCurrency = null;


    /*
    *Constructor with Parameters
    */
    public CryptoCustomerWalletModuleCryptoCustomerWalletManager(WalletManagerManager walletManagerManager,
                                                                 CryptoBrokerActorConnectionManager cryptoBrokerActorConnectionManager,
                                                                 CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager,
                                                                 CryptoCustomerIdentityManager cryptoCustomerIdentityManager,
                                                                 CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager,
                                                                 CustomerBrokerNewManager customerBrokerNewManage,
                                                                 CustomerBrokerUpdateManager customerBrokerUpdateManager,
                                                                 CustomerBrokerCloseManager customerBrokerCloseManager,
                                                                 CurrencyExchangeProviderFilterManager currencyExchangeProviderFilterManager,
                                                                 ActorExtraDataManager actorExtraDataManager,
                                                                 CustomerOnlinePaymentManager customerOnlinePaymentManager,
                                                                 CustomerOfflinePaymentManager customerOfflinePaymentManager,
                                                                 CustomerAckOnlineMerchandiseManager customerAckOnlineMerchandiseManager,
                                                                 CustomerAckOfflineMerchandiseManager customerAckOfflineMerchandiseManager,
                                                                 BrokerAckOnlinePaymentManager brokerAckOnlinePaymentManager,
                                                                 BrokerAckOfflinePaymentManager brokerAckOfflinePaymentManager,
                                                                 BrokerSubmitOnlineMerchandiseManager brokerSubmitOnlineMerchandiseManager,
                                                                 BrokerSubmitOfflineMerchandiseManager brokerSubmitOfflineMerchandiseManager,
                                                                 BitcoinWalletManager bitcoinWalletManager,
                                                                 final ErrorManager errorManager,
                                                                 final PluginVersionReference pluginVersionReference,
                                                                 PluginFileSystem pluginFileSystem,
                                                                 UUID pluginId) {
        this.walletManagerManager = walletManagerManager;
        this.cryptoBrokerActorConnectionManager = cryptoBrokerActorConnectionManager;
        this.customerBrokerPurchaseNegotiationManager = customerBrokerPurchaseNegotiationManager;
        this.cryptoCustomerIdentityManager = cryptoCustomerIdentityManager;
        this.customerBrokerContractPurchaseManager = customerBrokerContractPurchaseManager;
        this.customerBrokerNewManager = customerBrokerNewManage;
        this.customerBrokerUpdateManager = customerBrokerUpdateManager;
        this.customerBrokerCloseManager = customerBrokerCloseManager;
        this.currencyExchangeProviderFilterManager = currencyExchangeProviderFilterManager;
        this.actorExtraDataManager = actorExtraDataManager;
        this.customerOnlinePaymentManager = customerOnlinePaymentManager;
        this.customerOfflinePaymentManager = customerOfflinePaymentManager;
        this.customerAckOnlineMerchandiseManager = customerAckOnlineMerchandiseManager;
        this.customerAckOfflineMerchandiseManager = customerAckOfflineMerchandiseManager;
        this.brokerAckOnlinePaymentManager = brokerAckOnlinePaymentManager;
        this.brokerAckOfflinePaymentManager = brokerAckOfflinePaymentManager;
        this.brokerSubmitOnlineMerchandiseManager = brokerSubmitOnlineMerchandiseManager;
        this.brokerSubmitOfflineMerchandiseManager = brokerSubmitOfflineMerchandiseManager;
        this.bitcoinWalletManager = bitcoinWalletManager;
        this.errorManager = errorManager;
        this.pluginVersionReference = pluginVersionReference;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
    }

    @Override
    public Collection<ContractBasicInformation> getContractsHistory(ContractStatus status, int max, int offset) throws CantGetContractHistoryException, CantGetListCustomerBrokerContractPurchaseException {


        try {
            List<ContractBasicInformation> filteredList = new ArrayList<>();
            CryptoBrokerWalletModuleContractBasicInformation contract = null;
            NegotiationStatus statusNegotiationCancelled = NegotiationStatus.CANCELLED;
            ContractStatus statusContractCancelled = ContractStatus.CANCELLED;

            if (status != null) {

                //CONTRACT
                for (CustomerBrokerContractPurchase customerBrokerContractPurchase : customerBrokerContractPurchaseManager.getCustomerBrokerContractPurchaseForStatus(status)) {

                    CustomerBrokerPurchaseNegotiation PurchaseNegotiation = customerBrokerPurchaseNegotiationManager.getNegotiationsByNegotiationId(UUID.fromString(customerBrokerContractPurchase.getNegotiatiotId()));
                    if (customerBrokerContractPurchase.getStatus().equals(status)) {
                        merchandise = getClauseType(PurchaseNegotiation, ClauseType.CUSTOMER_CURRENCY);
                        typeOfPayment = getClauseType(PurchaseNegotiation, ClauseType.CUSTOMER_PAYMENT_METHOD);
                        paymentCurrency = getClauseType(PurchaseNegotiation, ClauseType.BROKER_CURRENCY);
                    }
                    ActorIdentity brokerIdentity = getBrokerInfoByPublicKey(customerBrokerContractPurchase.getPublicKeyCustomer(), customerBrokerContractPurchase.getPublicKeyBroker());

                    final CryptoCustomerIdentity cryptoCustomerIdentity = cryptoCustomerIdentityManager.getCryptoCustomerIdentity(customerBrokerContractPurchase.getPublicKeyCustomer());

                    contract = new CryptoBrokerWalletModuleContractBasicInformation(
                            cryptoCustomerIdentity.getAlias(),
                            cryptoCustomerIdentity.getProfileImage(),
                            brokerIdentity.getAlias(),
                            brokerIdentity.getProfileImage(),
                            merchandise,
                            typeOfPayment,
                            paymentCurrency,
                            status,
                            customerBrokerContractPurchase.getNearExpirationDatetime(),
                            PurchaseNegotiation,
                            customerBrokerContractPurchase.getContractId(),
                            customerBrokerContractPurchase.getCancelReason());

                    filteredList.add(contract);
                }

                //NEGOTIATION
                if (status.equals(statusContractCancelled)) {
                    for (CustomerBrokerPurchaseNegotiation PurchaseNegotiation : customerBrokerPurchaseNegotiationManager.getNegotiationsByStatus(statusNegotiationCancelled)) {

                        merchandise = getClauseType(PurchaseNegotiation, ClauseType.CUSTOMER_CURRENCY);
                        typeOfPayment = getClauseType(PurchaseNegotiation, ClauseType.CUSTOMER_PAYMENT_METHOD);
                        paymentCurrency = getClauseType(PurchaseNegotiation, ClauseType.BROKER_CURRENCY);

                        ActorIdentity cryptoCustomerIdentity = cryptoCustomerIdentityManager.getCryptoCustomerIdentity(PurchaseNegotiation.getCustomerPublicKey());

                        contract = new CryptoBrokerWalletModuleContractBasicInformation(
                                cryptoCustomerIdentity.getAlias(),
                                cryptoCustomerIdentity.getProfileImage(),
                                null,
                                null,
                                merchandise,
                                typeOfPayment,
                                paymentCurrency,
                                statusContractCancelled,
                                false,
                                PurchaseNegotiation,
                                "",
                                "");

                        filteredList.add(contract);
                    }
                }

            } else {
                ListsForStatusPurchase history = customerBrokerContractPurchaseManager.getCustomerBrokerContractHistory();
                final Collection<CustomerBrokerContractPurchase> historyContracts = history.getHistoryContracts();

                //CONTRACT
                for (CustomerBrokerContractPurchase customerBrokerContractPurchase : historyContracts) {

                    CustomerBrokerPurchaseNegotiation PurchaseNegotiation = customerBrokerPurchaseNegotiationManager.getNegotiationsByNegotiationId(UUID.fromString(customerBrokerContractPurchase.getNegotiatiotId()));
                    merchandise = getClauseType(PurchaseNegotiation, ClauseType.CUSTOMER_CURRENCY);
                    typeOfPayment = getClauseType(PurchaseNegotiation, ClauseType.CUSTOMER_PAYMENT_METHOD);
                    paymentCurrency = getClauseType(PurchaseNegotiation, ClauseType.BROKER_CURRENCY);
                    ActorIdentity brokerIdentity = getBrokerInfoByPublicKey(customerBrokerContractPurchase.getPublicKeyCustomer(), customerBrokerContractPurchase.getPublicKeyBroker());

                    ActorIdentity cryptoCustomerIdentity = cryptoCustomerIdentityManager.getCryptoCustomerIdentity(customerBrokerContractPurchase.getPublicKeyCustomer());

                    contract = new CryptoBrokerWalletModuleContractBasicInformation(
                            cryptoCustomerIdentity.getAlias(),
                            cryptoCustomerIdentity.getProfileImage(),
                            brokerIdentity.getAlias(),
                            brokerIdentity.getProfileImage(),
                            merchandise,
                            typeOfPayment,
                            paymentCurrency,
                            customerBrokerContractPurchase.getStatus(),
                            customerBrokerContractPurchase.getNearExpirationDatetime(),
                            PurchaseNegotiation,
                            customerBrokerContractPurchase.getContractId(),
                            customerBrokerContractPurchase.getCancelReason());

                    filteredList.add(contract);
                }

                //NEGOTIATION
                for (CustomerBrokerPurchaseNegotiation PurchaseNegotiation : customerBrokerPurchaseNegotiationManager.getNegotiationsByStatus(statusNegotiationCancelled)) {

                    merchandise = getClauseType(PurchaseNegotiation, ClauseType.CUSTOMER_CURRENCY);
                    typeOfPayment = getClauseType(PurchaseNegotiation, ClauseType.CUSTOMER_PAYMENT_METHOD);
                    paymentCurrency = getClauseType(PurchaseNegotiation, ClauseType.BROKER_CURRENCY);

                    ActorIdentity cryptoCustomerIdentity = cryptoCustomerIdentityManager.getCryptoCustomerIdentity(PurchaseNegotiation.getCustomerPublicKey());
                    contract = new CryptoBrokerWalletModuleContractBasicInformation(
                            cryptoCustomerIdentity.getAlias(),
                            cryptoCustomerIdentity.getProfileImage(),
                            null,
                            null,
                            merchandise,
                            typeOfPayment,
                            paymentCurrency,
                            statusContractCancelled,
                            false,
                            PurchaseNegotiation,
                            "",
                            "");

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
            ContractBasicInformation contract;
            Collection<ContractBasicInformation> waitingForBroker = new ArrayList<>();

            ListsForStatusPurchase history = customerBrokerContractPurchaseManager.getCustomerBrokerContractHistory();
            for (CustomerBrokerContractPurchase customerBrokerContract : history.getContractsWaitingForBroker()) {
                CustomerBrokerPurchaseNegotiation negotiation = customerBrokerPurchaseNegotiationManager.getNegotiationsByNegotiationId(UUID.fromString(customerBrokerContract.getNegotiatiotId()));

                merchandise = getClauseType(negotiation, ClauseType.CUSTOMER_CURRENCY);
                typeOfPayment = getClauseType(negotiation, ClauseType.CUSTOMER_PAYMENT_METHOD);
                paymentCurrency = getClauseType(negotiation, ClauseType.BROKER_CURRENCY);

                ActorIdentity brokerIdentity = getBrokerInfoByPublicKey(customerBrokerContract.getPublicKeyCustomer(), customerBrokerContract.getPublicKeyBroker());
                ActorIdentity customerIdentity = cryptoCustomerIdentityManager.getCryptoCustomerIdentity(customerBrokerContract.getPublicKeyCustomer());

                contract = new CryptoBrokerWalletModuleContractBasicInformation(
                        customerIdentity.getAlias(),
                        customerIdentity.getProfileImage(),
                        brokerIdentity.getAlias(),
                        brokerIdentity.getProfileImage(),
                        merchandise,
                        typeOfPayment,
                        paymentCurrency,
                        customerBrokerContract.getStatus(),
                        customerBrokerContract.getNearExpirationDatetime(),
                        negotiation,
                        customerBrokerContract.getContractId(),
                        customerBrokerContract.getCancelReason());

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
            ContractBasicInformation contract;
            Collection<ContractBasicInformation> waitingForBroker = new ArrayList<>();

            ListsForStatusPurchase history = customerBrokerContractPurchaseManager.getCustomerBrokerContractHistory();

            for (CustomerBrokerContractPurchase customerBrokerContract : history.getContractsWaitingForCustomer()) {
                CustomerBrokerPurchaseNegotiation negotiation = customerBrokerPurchaseNegotiationManager.getNegotiationsByNegotiationId(UUID.fromString(customerBrokerContract.getNegotiatiotId()));

                merchandise = getClauseType(negotiation, ClauseType.CUSTOMER_CURRENCY);
                typeOfPayment = getClauseType(negotiation, ClauseType.CUSTOMER_PAYMENT_METHOD);
                paymentCurrency = getClauseType(negotiation, ClauseType.BROKER_CURRENCY);

                ActorIdentity brokerIdentity = getBrokerInfoByPublicKey(customerBrokerContract.getPublicKeyCustomer(), customerBrokerContract.getPublicKeyBroker());
                ActorIdentity customerIdentity = cryptoCustomerIdentityManager.getCryptoCustomerIdentity(customerBrokerContract.getPublicKeyCustomer());

                contract = new CryptoBrokerWalletModuleContractBasicInformation(
                        customerIdentity.getAlias(),
                        customerIdentity.getProfileImage(),
                        brokerIdentity.getAlias(),
                        brokerIdentity.getProfileImage(),
                        merchandise,
                        typeOfPayment,
                        paymentCurrency,
                        customerBrokerContract.getStatus(),
                        customerBrokerContract.getNearExpirationDatetime(),
                        negotiation,
                        customerBrokerContract.getContractId(),
                        customerBrokerContract.getCancelReason());

                waitingForBroker.add(contract);
            }

            return waitingForBroker;

        } catch (CantGetListPurchaseNegotiationsException e) {
            throw new CantGetContractsWaitingForCustomerException("Cant get contracts waiting for the broker", e);
        } catch (CantGetListCustomerBrokerContractPurchaseException e) {
            throw new CantGetContractsWaitingForCustomerException("Cant get contracts waiting for the broker", e);
        } catch (Exception ex) {
            throw new CantGetContractsWaitingForCustomerException("Cant get contracts waiting for the broker", ex);
        }
    }

    @Override
    public Collection<Clause> getNegotiationClausesFromNegotiationId(UUID negotiationId) throws CantGetListClauseException {
        try {
            CustomerBrokerPurchaseNegotiation negotiation = customerBrokerPurchaseNegotiationManager.getNegotiationsByNegotiationId(negotiationId);
            Collection<Clause> clauses = negotiation.getClauses();
            return clauses;
        } catch (CantGetListPurchaseNegotiationsException | CantGetListClauseException ex) {
            throw new CantGetListClauseException("Cant get the negotiation clauses for the given negotiationId " + negotiationId.toString(), ex);
        }
    }

    private String getClauseType(CustomerBrokerPurchaseNegotiation purchaseNegotiation, ClauseType clauseType) throws CantGetListClauseException {
        String value = null;
        try {
            if (purchaseNegotiation != null && clauseType != null) {
                for (Clause clause : purchaseNegotiation.getClauses()) {
                    if (clause.getType() == clauseType) {
                        value = clause.getValue();
                    }
                }
            }
        } catch (CantGetListClauseException e) {
            throw new CantGetListClauseException("Cant get the clauses", e);
        }
        return value;
    }

    @Override
    public Collection<CustomerBrokerNegotiationInformation> getNegotiationsWaitingForBroker(int max, int offset) throws CantGetNegotiationsWaitingForBrokerException {
        try {

            Collection<CustomerBrokerNegotiationInformation> waitingForBroker = new ArrayList<>();

            for (CustomerBrokerPurchaseNegotiation negotiation : customerBrokerPurchaseNegotiationManager.getNegotiationsBySendAndWaiting(ActorType.BROKER))
                waitingForBroker.add(getItemNegotiationInformation(negotiation, NegotiationStatus.WAITING_FOR_BROKER));

            return waitingForBroker;

        } catch (Exception ex) {
            throw new CantGetNegotiationsWaitingForBrokerException("Cant get negotiations waiting for the broker", ex, "", "");
        }
    }

    @Override
    public Collection<CustomerBrokerNegotiationInformation> getNegotiationsWaitingForCustomer(int max, int offset) throws CantGetNegotiationsWaitingForCustomerException {
        try {

            Collection<CustomerBrokerNegotiationInformation> waitingForCustomer = new ArrayList<>();

            for (CustomerBrokerPurchaseNegotiation negotiation : customerBrokerPurchaseNegotiationManager.getNegotiationsBySendAndWaiting(ActorType.CUSTOMER))
                waitingForCustomer.add(getItemNegotiationInformation(negotiation, NegotiationStatus.WAITING_FOR_CUSTOMER));

            return waitingForCustomer;

        } catch (Exception ex) {
            throw new CantGetNegotiationsWaitingForCustomerException("Cant get negotiations waiting for the broker", ex, "", "");
        }
    }

    @Override
    public CustomerBrokerNegotiationInformation getNegotiationInformation(UUID negotiationID) throws CantGetNegotiationInformationException {
        final CustomerBrokerPurchaseNegotiation purchaseNegotiation;
        try {
            purchaseNegotiation = customerBrokerPurchaseNegotiationManager.getNegotiationsByNegotiationId(negotiationID);
        } catch (CantGetListPurchaseNegotiationsException e) {
            throw new CantGetNegotiationInformationException(CantGetNegotiationInformationException.DEFAULT_MESSAGE, e,
                    "negotiationId = " + negotiationID, "Cant get the Purchase Negotiation Information");
        } catch (Exception e) {
            throw new CantGetNegotiationInformationException(e, "Unexpected Exception: " + e.getMessage(), "N/A");
        }

        final String brokerPublicKey = purchaseNegotiation.getBrokerPublicKey();
        final String customerPublicKey = purchaseNegotiation.getCustomerPublicKey();
        try {
            ActorIdentity customerIdentity = cryptoCustomerIdentityManager.getCryptoCustomerIdentity(customerPublicKey);
            ActorIdentity brokerIdentity = getBrokerInfoByPublicKey(customerPublicKey, brokerPublicKey);
            return new CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation(purchaseNegotiation, customerIdentity, brokerIdentity);

        } catch (CantListActorConnectionsException e) {
            throw new CantGetNegotiationInformationException(e, "negotiationId = " + negotiationID + "\nbrokerPublicKey = " + brokerPublicKey +
                    "\ncustomerPublicKey = " + customerPublicKey, "Cant get the Broker Information");

        } catch (IdentityNotFoundException | CantGetCryptoCustomerIdentityException e) {
            throw new CantGetNegotiationInformationException(e, "negotiationId = " + negotiationID + "\ncustomerPublicKey = " + customerPublicKey,
                    "Cant get the Customer Information");

        } catch (Exception e) {
            throw new CantGetNegotiationInformationException(e, "Unexpected Exception: " + e.getMessage(), "N/A");
        }
    }

    @Override
    public Collection<NegotiationLocations> getAllLocations(NegotiationType negotiationType) throws CantGetListLocationsPurchaseException {
        Collection<NegotiationLocations> negotiationLocations = null;
        if (negotiationType.getCode().equals(NegotiationType.PURCHASE.getCode())) {
            negotiationLocations = customerBrokerPurchaseNegotiationManager.getAllLocations();
        }
        return negotiationLocations;
    }

    @Override
    public long getBalanceBitcoinWallet(String walletPublicKey) {
        try {
            return bitcoinWalletManager.loadWallet(walletPublicKey).getBalance(BalanceType.AVAILABLE).getBalance();
        } catch (CantCalculateBalanceException e) {

        } catch (CantLoadWalletsException e) {

        }
        return 0;
    }

    @Override
    public boolean associateIdentity(ActorIdentity customer, String walletPublicKey) throws CantCreateNewCustomerIdentityWalletRelationshipException {
        CustomerIdentityWalletRelationship relationship = actorExtraDataManager.createNewCustomerIdentityWalletRelationship(customer, walletPublicKey);
        return relationship != null;
//        return true;
    }

    @Override
    public void clearAssociatedIdentities(String walletPublicKey) throws CantClearAssociatedCustomerIdentityWalletRelationshipException {
        actorExtraDataManager.clearAssociatedCustomerIdentityWalletRelationship(walletPublicKey);
    }

    @Override
    public boolean haveAssociatedIdentity(final String walletPublicKey) throws CantListCryptoCustomerIdentityException, CantGetCustomerIdentityWalletRelationshipException {

        CustomerIdentityWalletRelationship relationship;

        try {


            relationship = actorExtraDataManager.getCustomerIdentityWalletRelationshipByWallet(walletPublicKey);

            if( relationship == null ){

                return false;
            }

        } catch (RelationshipNotFoundException relationshipNotFoundException) {

            return false;
        }

        try {

            return cryptoCustomerIdentityManager.getCryptoCustomerIdentity(relationship.getCryptoCustomer()) != null;

        } catch (CantGetCryptoCustomerIdentityException | IdentityNotFoundException e) {

            throw new CantGetCustomerIdentityWalletRelationshipException(e, "", "The identity has not been found there's a serious error here.");
        }

    }

    @Override
    public CryptoCustomerIdentity getAssociatedIdentity(final String walletPublicKey) throws IdentityAssociatedNotFoundException,
            CantGetAssociatedIdentityException {

        try {

            CustomerIdentityWalletRelationship relationship = actorExtraDataManager.getCustomerIdentityWalletRelationshipByWallet(walletPublicKey);

            if (relationship != null) {

                return cryptoCustomerIdentityManager.getCryptoCustomerIdentity(
                        relationship.getCryptoCustomer()
                );

            } else {
                throw new IdentityAssociatedNotFoundException("walletPublicKey: " + walletPublicKey, "We cannot find a customer identity associated to the referenced wallet.");
            }

        } catch (IdentityAssociatedNotFoundException identityAssociatedNotFoundException) {

            throw identityAssociatedNotFoundException;
        } catch (RelationshipNotFoundException relationshipNotFoundException) {

            errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, relationshipNotFoundException);
            throw new IdentityAssociatedNotFoundException(relationshipNotFoundException, "walletPublicKey: " + walletPublicKey, "There's no relationship for this wallet.");
        } catch (CantGetCryptoCustomerIdentityException cantGetCryptoCustomerIdentityException) {

            errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantGetCryptoCustomerIdentityException);
            throw new CantGetAssociatedIdentityException(cantGetCryptoCustomerIdentityException, "walletPublicKey: " + walletPublicKey, "There was a problem trying to get a crypto customer identity.");
        } catch (CantGetCustomerIdentityWalletRelationshipException cantGetCustomerIdentityWalletRelationshipException) {

            errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantGetCustomerIdentityWalletRelationshipException);
            throw new CantGetAssociatedIdentityException(cantGetCustomerIdentityWalletRelationshipException, "walletPublicKey: " + walletPublicKey, "There was a problem listing the relationship between the wallet and the crypto customers.");
        } catch (Exception exception) {

            errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new CantGetAssociatedIdentityException(exception, "walletPublicKey: " + walletPublicKey, "Unhandled Error.");
        }
    }

    /**
     * @return list of identities associated with this wallet
     */
    @Override
    public List<CryptoCustomerIdentity> getListOfIdentities() throws CantGetCryptoCustomerIdentityListException, CantListCryptoCustomerIdentityException {
        return cryptoCustomerIdentityManager.listAllCryptoCustomerFromCurrentDeviceUser();
    }

    /**
     * Add by Yordin Alayn 16.02.16
     * This method cancel negotiation for the customer. Indicated reason for cancel
     *
     * @param negotiation negotiation information
     * @param reason      reason of cancellation
     *
     * @return CustomerBrokerNegotiationInformation
     *
     * @throws CouldNotCancelNegotiationException exception in Wallet Module Crypto Customer
     */
    @Override
    public CustomerBrokerNegotiationInformation cancelNegotiation(CustomerBrokerNegotiationInformation negotiation, String reason) throws CouldNotCancelNegotiationException {


        try {

            CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation negotiationInformation = new CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation(
                    negotiation, reason
            );

            Date time = new Date();

            Map<ClauseType, ClauseInformation> mapClauses = negotiation.getClauses();
            Collection<Clause> clauseNegotiation = getClause(getClauseInformation(mapClauses));
            CustomerBrokerPurchaseNegotiationImpl customerBrokerPurchaseNegotiation = new CustomerBrokerPurchaseNegotiationImpl();
            customerBrokerPurchaseNegotiation.setBrokerPublicKey(negotiation.getBroker().getPublicKey());
            customerBrokerPurchaseNegotiation.setCustomerPublicKey(negotiation.getCustomer().getPublicKey());
            customerBrokerPurchaseNegotiation.setNegotiationId(negotiation.getNegotiationId());
            customerBrokerPurchaseNegotiation.setStartDate(time.getTime());
            customerBrokerPurchaseNegotiation.setStatus(negotiation.getStatus());
            customerBrokerPurchaseNegotiation.setClauses(clauseNegotiation);
            customerBrokerPurchaseNegotiation.setNearExpirationDatetime(false);
            customerBrokerPurchaseNegotiation.setNegotiationExpirationDate(time.getTime());
            customerBrokerPurchaseNegotiation.setLastNegotiationUpdateDate(negotiation.getNegotiationExpirationDate());
            customerBrokerPurchaseNegotiation.setMemo(negotiation.getMemo());
            customerBrokerPurchaseNegotiation.setCancelReason(reason);

            customerBrokerUpdateManager.cancelNegotiation(customerBrokerPurchaseNegotiation);

            System.out.print("\nREFERENCE WALLET - CRYPTO CUSTOMER: CUSTOMER BROKER CANCEL " +
                            "\n - NEGOTIATION ID " + negotiation.getNegotiationId() + " | " + negotiationInformation.getNegotiationId() +
                            "\n - STATUS " + negotiation.getStatus() + " | " + negotiationInformation.getStatus() +
                            "\n - REASON: " + reason + " | " + negotiationInformation.getCancelReason()
            );
            return negotiationInformation;
//        } catch (CantCancelNegotiationException e) {
//            throw new CouldNotCancelNegotiationException(e.getMessage(), FermatException.wrapException(e), CantCancelNegotiationException.DEFAULT_MESSAGE, "ERROR CREATE CUSTOMER BROKER UPDATE NEGOTIATION TRANSACTION OF CANCELLATION, UNKNOWN FAILURE.");
        } catch (Exception e) {
            throw new CouldNotCancelNegotiationException(e.getMessage(), FermatException.wrapException(e), CantCancelNegotiationException.DEFAULT_MESSAGE, "ERROR CREATE CUSTOMER BROKER UPDATE NEGOTIATION TRANSACTION OF CANCELLATION, UNKNOWN FAILURE.");
        }
    }

    @Override
    public Collection<IndexInfoSummary> getProvidersCurrentExchangeRates(String walletPublicKey) throws CantGetProvidersCurrentExchangeRatesException, CantGetProviderException, UnsupportedCurrencyPairException, CantGetExchangeRateException, CantGetSettingsException, SettingsNotFoundException {

        final Collection<IndexInfoSummary> summaryList = new ArrayList<>();

        CryptoCustomerWalletPreferenceSettings settings = settingsManager.loadAndGetSettings(walletPublicKey);
        final List<CryptoCustomerWalletProviderSetting> providerSettings = settings.getSelectedProviders();

        for (CryptoCustomerWalletProviderSetting providerSetting : providerSettings) {
            UUID providerId = providerSetting.getPlugin();

            CurrencyExchangeRateProviderManager providerReference = currencyExchangeProviderFilterManager.getProviderReference(providerId);
            Currency from = providerSetting.getCurrencyFrom();
            Currency to = providerSetting.getCurrencyTo();

            ExchangeRate currentExchangeRate = providerReference.getCurrentExchangeRate(new CurrencyPairImpl(from, to));

            summaryList.add(new CryptoCustomerWalletModuleIndexInfoSummary(currentExchangeRate, providerReference));
        }

        return summaryList;
    }

    @Override
    public Collection<BrokerIdentityBusinessInfo> getListOfConnectedBrokersAndTheirMerchandises() throws CantGetCryptoBrokerListException, CantGetListActorExtraDataException {

        Collection<ActorExtraData> actorExtraDataList = actorExtraDataManager.getAllActorExtraDataConnected();
        Collection<BrokerIdentityBusinessInfo> brokersBusinessInfo = new ArrayList<>();

        if (actorExtraDataList != null) {
            for (ActorExtraData actorExtraData : actorExtraDataList) {

                if (actorExtraData.getQuotes() != null) {
                    Map<Currency, BrokerIdentityBusinessInfo> map = new HashMap<>();

                    for (QuotesExtraData quotesExtraData : actorExtraData.getQuotes()) {
                        final ActorIdentity brokerIdentity = actorExtraData.getBrokerIdentity();
                        final Currency merchandise = quotesExtraData.getMerchandise();

                        BrokerIdentityBusinessInfo businessInfo = map.get(merchandise);
                        if (businessInfo == null) {
                            businessInfo = new CryptoCustomerWalletModuleBrokerIdentityBusinessInfo(brokerIdentity, merchandise);
                            brokersBusinessInfo.add(businessInfo);

                            map.put(merchandise, businessInfo);
                        }

                        List<MerchandiseExchangeRate> quotes = businessInfo.getQuotes();
                        quotes.add(new CryptoCustomerWalletModuleMerchandiseExchangeRate(quotesExtraData));
                    }
                }

            }
        }

        return brokersBusinessInfo;
    }

    @Override
    public boolean isWalletConfigured(String customerWalletPublicKey) throws CantGetSettingsException, SettingsNotFoundException {
        CryptoCustomerWalletPreferenceSettings walletSettings = settingsManager.loadAndGetSettings(customerWalletPublicKey);
        return walletSettings.isWalletConfigured();
    }

    /**
     * Add by Yordin Alayn 22.01.16
     * This method start negotiation
     *
     * @param customerPublicKey public key of customer
     * @param brokerPublicKey   public key od broker
     * @param clauses           Clauses of negotiation
     *
     * @return boolean
     *
     * @throws CouldNotStartNegotiationException,                                 exception in wallet module Crypto Customer
     * @throws CantCreateCustomerBrokerNewPurchaseNegotiationTransactionException exception in CustomerBrokerNew Negotiation Transaction
     */
    @Override
    public boolean startNegotiation(String customerPublicKey, String brokerPublicKey, Collection<ClauseInformation> clauses) throws CouldNotStartNegotiationException, CantCreateCustomerBrokerNewPurchaseNegotiationTransactionException {
        try {

            System.out.print("\n**** 1) MOCK MODULE CRYPTO CUSTOMER - PURCHASE NEGOTIATION****\n" +
                    "\n-CUSTOMER: " + customerPublicKey +
                    "\n-BROKER: " + brokerPublicKey);

            Date time = new Date();

            Collection<Clause> clauseNegotiation = getClause(clauses);
            CustomerBrokerPurchaseNegotiationImpl customerBrokerPurchaseNegotiation = new CustomerBrokerPurchaseNegotiationImpl();
            customerBrokerPurchaseNegotiation.setBrokerPublicKey(brokerPublicKey);
            customerBrokerPurchaseNegotiation.setCustomerPublicKey(customerPublicKey);
            customerBrokerPurchaseNegotiation.setNegotiationId(UUID.randomUUID());
            customerBrokerPurchaseNegotiation.setStartDate(time.getTime());
            customerBrokerPurchaseNegotiation.setStatus(NegotiationStatus.SENT_TO_BROKER);
            customerBrokerPurchaseNegotiation.setClauses(clauseNegotiation);
            customerBrokerPurchaseNegotiation.setNearExpirationDatetime(Boolean.FALSE);
            customerBrokerPurchaseNegotiation.setNegotiationExpirationDate((long) 0);
            customerBrokerPurchaseNegotiation.setLastNegotiationUpdateDate(time.getTime());
            customerBrokerPurchaseNegotiation.setMemo("");

            System.out.print("\n**** 1.1) MOCK MODULE CRYPTO CUSTOMER - PURCHASE NEGOTIATION - CLAUSES INFORMATION****\n");

            customerBrokerNewManager.createCustomerBrokerNewPurchaseNegotiationTransaction(customerBrokerPurchaseNegotiation);

            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * This method Update or Close Negotiation.
     *
     * @param negotiation negotiation information
     *
     * @return boolean
     *
     * @throws CouldNotUpdateNegotiationException exception in wallet module Crypto Customer
     */
    @Override
    public void updateNegotiation(CustomerBrokerNegotiationInformation negotiation) throws CouldNotUpdateNegotiationException {
        CustomerBrokerPurchaseNegotiationImpl purchaseNegotiationImpl = null;

        try {

            //VALIDATE STATUS CLAUSE
            CustomerBrokerPurchaseNegotiation purchaseNegotiation = customerBrokerPurchaseNegotiationManager.getNegotiationsByNegotiationId(negotiation.getNegotiationId());
            purchaseNegotiationImpl = new CustomerBrokerPurchaseNegotiationImpl(purchaseNegotiation);
            purchaseNegotiationImpl.changeInfo(negotiation);

            if (purchaseNegotiationImpl.dataHasChanged()) {
                customerBrokerUpdateManager.createCustomerBrokerUpdatePurchaseNegotiationTranasction(purchaseNegotiationImpl);
            } else {
                customerBrokerCloseManager.createCustomerBrokerClosePurchaseNegotiationTranasction(purchaseNegotiationImpl);
            }

        } catch (CantGetListPurchaseNegotiationsException cause) {
            throw new CouldNotUpdateNegotiationException("Cant get the Purchase Negotiation from the Data Base",
                    cause, "negotiationInfo.getNegotiationId(): " + negotiation.getNegotiationId(),
                    "There is no Record of the Sale Negotiation in the Data Base");

        } catch (CantCreateCustomerBrokerUpdatePurchaseNegotiationTransactionException cause) {
            throw new CouldNotUpdateNegotiationException("Cant send the UPDATED Purchase Negotiation Transaction to the customer",
                    cause, "saleNegotiationImpl: " + purchaseNegotiationImpl, "N/A");

        } catch (CantCreateCustomerBrokerPurchaseNegotiationException cause) {
            throw new CouldNotUpdateNegotiationException("Cant send the CLOSED Purchase Negotiation Transaction to the customer",
                    cause, "saleNegotiationImpl: " + purchaseNegotiationImpl, "N/A");

        } catch (Exception cause) {
            throw new CouldNotUpdateNegotiationException(cause.getMessage(), cause, "Unknown Exception", "N/A");
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
    public CustomerBrokerNegotiationInformation setMemo(String memo, CustomerBrokerNegotiationInformation data) {
        data.setMemo(memo);
        return data;
    }

    /**
     * @param location
     * @param uri
     *
     * @throws CantCreateLocationPurchaseException
     */
    @Override
    public void createNewLocation(String location, String uri) throws CantCreateLocationPurchaseException {
        customerBrokerPurchaseNegotiationManager.createNewLocation(location, uri);
    }

    public void clearLocations() throws CantDeleteLocationPurchaseException {
        try {
            for (NegotiationLocations nl : customerBrokerPurchaseNegotiationManager.getAllLocations())
                customerBrokerPurchaseNegotiationManager.deleteLocation(nl);
        } catch (CantGetListLocationsPurchaseException e) {
            throw new CantDeleteLocationPurchaseException(CantDeleteLocationPurchaseException.DEFAULT_MESSAGE, e);
        }
    }

    @Override
    public NegotiationBankAccount newEmptyNegotiationBankAccount(final String bankAccount, final FiatCurrency currencyType) throws CantCreateBankAccountPurchaseException {
        return new NegotiationBankAccount() {
            @Override
            public UUID getBankAccountId() {
                return UUID.randomUUID();
            }

            @Override
            public String getBankAccount() {
                return bankAccount;
            }

            @Override
            public FiatCurrency getCurrencyType() {
                return currencyType;
            }
        };
    }

    @Override
    public CustomerBrokerNegotiationInformation newEmptyCustomerBrokerNegotiationInformation() throws CantNewEmptyCustomerBrokerNegotiationInformationException {
        return new EmptyCustomerBrokerNegotiationInformationImpl();
    }

    /**
     * @param bankAccount
     *
     * @throws CantCreateBankAccountPurchaseException
     */
    @Override
    public void createNewBankAccount(NegotiationBankAccount bankAccount) throws CantCreateBankAccountPurchaseException {
        customerBrokerPurchaseNegotiationManager.createNewBankAccount(bankAccount);
    }

    /**
     * @param bankAccount
     *
     * @throws CantUpdateBankAccountPurchaseException
     */
    @Override
    public void updateBankAccount(NegotiationBankAccount bankAccount) throws CantUpdateBankAccountPurchaseException {
        customerBrokerPurchaseNegotiationManager.updateBankAccount(bankAccount);
    }

    /**
     * @param bankAccount
     *
     * @throws CantDeleteBankAccountPurchaseException
     */
    @Override
    public void deleteBankAccount(NegotiationBankAccount bankAccount) throws CantDeleteBankAccountPurchaseException {
        customerBrokerPurchaseNegotiationManager.deleteBankAccount(bankAccount);
    }

    /**
     * Returns a list of all associated bank accounts
     *
     * @throws CantGetListBankAccountsPurchaseException
     */
    @Override
    public List<BankAccountNumber> getListOfBankAccounts() throws CantGetListBankAccountsPurchaseException {
        List<BankAccountNumber> bankAccounts = new ArrayList<>();

        for (NegotiationBankAccount ba : customerBrokerPurchaseNegotiationManager.getAllBankAccount()) {

            String bankAccountInfo = ba.getBankAccount();
            String bank, accountNumber;
            BankAccountType bankAccountType = null;

            bank = bankAccountInfo.substring(bankAccountInfo.indexOf("Bank: ")+6, bankAccountInfo.indexOf("\n"));
            bankAccountInfo = bankAccountInfo.substring(bankAccountInfo.indexOf("\n")+1);

            try{
                String accountType = bankAccountInfo.substring(bankAccountInfo.indexOf("Account Type: ")+14, bankAccountInfo.indexOf("\n"));
                bankAccountType = BankAccountType.getByCode(accountType);
            }catch (FermatException e){ }
            bankAccountInfo = bankAccountInfo.substring(bankAccountInfo.indexOf("\n")+1);

            accountNumber = bankAccountInfo.substring(bankAccountInfo.indexOf("Number: ")+8);

            BankAccountData ban = new BankAccountData(ba.getCurrencyType(), bankAccountType, bank, accountNumber, "");

            bankAccounts.add(ban);

        }
        return bankAccounts;
    }


    /**
     * Delete all bank accounts associated with the crypto customer wallet
     *
     * @throws CantDeleteBankAccountPurchaseException
     */
    @Override
    public void clearAllBankAccounts() throws CantDeleteBankAccountPurchaseException {
        try {
            for (NegotiationBankAccount ba : customerBrokerPurchaseNegotiationManager.getAllBankAccount()) {
                customerBrokerPurchaseNegotiationManager.deleteBankAccount(ba);
            }
        } catch (CantGetListBankAccountsPurchaseException e) {
            throw new CantDeleteBankAccountPurchaseException(CantGetListBankAccountsPurchaseException.DEFAULT_MESSAGE, e);
        }

    }

    /**
     * @param negotiation
     *
     * @throws CantCreateCustomerBrokerPurchaseNegotiationException
     */
    @Override
    public void createCustomerBrokerPurchaseNegotiation(CustomerBrokerNegotiationInformation negotiation) throws CantCreateCustomerBrokerPurchaseNegotiationException {
        CustomerBrokerPurchaseNegotiationImpl customerBrokerPurchaseNegotiation = new CustomerBrokerPurchaseNegotiationImpl();
        customerBrokerPurchaseNegotiation.setNegotiationId(negotiation.getNegotiationId());
        customerBrokerPurchaseNegotiation.setCustomerPublicKey(negotiation.getCustomer().getPublicKey());
        customerBrokerPurchaseNegotiation.setBrokerPublicKey(negotiation.getBroker().getPublicKey());
        customerBrokerPurchaseNegotiation.setStartDate(null);
        customerBrokerPurchaseNegotiation.setNegotiationExpirationDate(negotiation.getNegotiationExpirationDate());
        customerBrokerPurchaseNegotiation.setStatus(negotiation.getStatus());
        customerBrokerPurchaseNegotiation.setNearExpirationDatetime(false);
        //customerBrokerPurchaseNegotiation.setClauses(negotiation.getClauses());
        customerBrokerPurchaseNegotiation.setClauses(null);
        customerBrokerPurchaseNegotiation.setCancelReason(negotiation.getCancelReason());
        customerBrokerPurchaseNegotiation.setMemo(negotiation.getMemo());
        customerBrokerPurchaseNegotiation.setLastNegotiationUpdateDate(negotiation.getLastNegotiationUpdateDate());
        customerBrokerPurchaseNegotiationManager.createCustomerBrokerPurchaseNegotiation(customerBrokerPurchaseNegotiation);
    }

    @Override
    public CryptoCustomerWalletAssociatedSetting newEmptyCryptoBrokerWalletAssociatedSetting() throws CantNewEmptyCryptoCustomerWalletAssociatedSettingException {
        return new CryptoCustomerWalletAssociatedSettingImpl();
    }

    @Override
    public void saveWalletSettingAssociated(CryptoCustomerWalletAssociatedSetting setting, String customerWalletPublicKey) throws CantGetSettingsException, CantPersistSettingsException {

        CryptoCustomerWalletPreferenceSettings cryptoCustomerWalletSettings;
        try {
            cryptoCustomerWalletSettings = settingsManager.loadAndGetSettings(customerWalletPublicKey);

        } catch (SettingsNotFoundException e) {
            cryptoCustomerWalletSettings = new CryptoCustomerWalletPreferenceSettings();
        }

        cryptoCustomerWalletSettings.setSelectedBitcoinWallet(setting);

        settingsManager.persistSettings(customerWalletPublicKey, cryptoCustomerWalletSettings);
    }

    @Override
    public Map<String, CurrencyExchangeRateProviderManager> getProviderReferencesFromCurrencyPair(final Currency currencyFrom, final Currency currencyTo) throws CantGetProviderException, CantGetProviderInfoException {
        Map<String, CurrencyExchangeRateProviderManager> managerMap = new HashMap<>();
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
        for (CurrencyExchangeRateProviderManager currencyExchangeRateProviderManager : currencyExchangeProviderFilterManager.getProviderReferencesFromCurrencyPair(currencyPair)) {
            managerMap.put(currencyExchangeRateProviderManager.getProviderName(), currencyExchangeRateProviderManager);
        }

        return managerMap;
    }

    @Override
    public CurrencyExchangeRateProviderManager getProviderReferenceFromId(UUID providerId) throws CantGetProviderException {
        return currencyExchangeProviderFilterManager.getProviderReference(providerId);
    }


    @Override
    public CryptoCustomerWalletProviderSetting newEmptyCryptoCustomerWalletProviderSetting() throws CantNewEmptyCryptoCustomerWalletProviderSettingException {
        return new CryptoCustomerWalletProviderSettingImpl();
    }

    @Override
    public void saveCryptoCustomerWalletProviderSetting(CryptoCustomerWalletProviderSetting setting, String customerWalletPublicKey) throws CantGetSettingsException, CantPersistSettingsException {

        CryptoCustomerWalletPreferenceSettings cryptoCustomerWalletSettings;

        try {
            cryptoCustomerWalletSettings = settingsManager.loadAndGetSettings(customerWalletPublicKey);

        } catch (SettingsNotFoundException e) {
            cryptoCustomerWalletSettings = new CryptoCustomerWalletPreferenceSettings();
        }

        Collection<CryptoCustomerWalletProviderSetting> selectedProviders = cryptoCustomerWalletSettings.getSelectedProviders();
        selectedProviders.add(setting);

        settingsManager.persistSettings(customerWalletPublicKey, cryptoCustomerWalletSettings);
    }

    public void clearCryptoCustomerWalletProviderSetting(String customerWalletPublicKey) throws CantClearCryptoCustomerWalletSettingException, CantPersistFileException, CantCreateFileException, CantPersistSettingsException, CantGetSettingsException {

        CryptoCustomerWalletPreferenceSettings cryptoCustomerWalletSettings;

        try {
            cryptoCustomerWalletSettings = settingsManager.loadAndGetSettings(customerWalletPublicKey);

        } catch (SettingsNotFoundException e) {
            cryptoCustomerWalletSettings = new CryptoCustomerWalletPreferenceSettings();
        }

        Collection<CryptoCustomerWalletProviderSetting> selectedProviders = cryptoCustomerWalletSettings.getSelectedProviders();
        selectedProviders.clear();

        settingsManager.persistSettings(customerWalletPublicKey, cryptoCustomerWalletSettings);
    }

    @Override
    public List<CryptoCustomerWalletProviderSetting> getAssociatedProviders(String walletPublicKey) throws CantGetSettingsException, SettingsNotFoundException {
        CryptoCustomerWalletPreferenceSettings settings = settingsManager.loadAndGetSettings(walletPublicKey);
        return settings.getSelectedProviders();
    }

    /**
     * This method returns the CustomerBrokerContractPurchase associated to a negotiationId
     *
     * @param negotiationId
     *
     * @return
     *
     * @throws CantGetListCustomerBrokerContractPurchaseException
     */
    @Override
    public CustomerBrokerContractPurchase getCustomerBrokerContractPurchaseByNegotiationId(String negotiationId) throws CantGetListCustomerBrokerContractPurchaseException {
        Collection<CustomerBrokerContractPurchase> customerBrokerContractPurchases = customerBrokerContractPurchaseManager.getAllCustomerBrokerContractPurchase();
        String negotiationIdFromCollection;

        for (CustomerBrokerContractPurchase customerBrokerContractPurchase : customerBrokerContractPurchases) {
            negotiationIdFromCollection = customerBrokerContractPurchase.getNegotiatiotId();
            if (negotiationIdFromCollection.equals(negotiationId)) {
                return customerBrokerContractPurchase;
            }
        }

        //TODO: This line is only for testing, kill this and uncomment exception below when done
        return new CustomerBrokerContractPurchaseMock();

        //throw new CantGetListCustomerBrokerContractPurchaseException("Cannot find the contract associated to negotiation "+negotiationId);
    }

    /**
     * This method returns the currency type from a contract
     *
     * @param contractPurchase
     * @param contractDetailType
     *
     * @return
     *
     * @throws CantGetListPurchaseNegotiationsException
     */
    @Override
    public MoneyType getCurrencyTypeFromContract(
            CustomerBrokerContractPurchase contractPurchase,
            ContractDetailType contractDetailType) throws
            CantGetListPurchaseNegotiationsException {
        try {
            String negotiationId = contractPurchase.getNegotiatiotId();
            CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation =
                    customerBrokerPurchaseNegotiationManager.getNegotiationsByNegotiationId(
                            UUID.fromString(negotiationId));
            Collection<Clause> clauses = customerBrokerPurchaseNegotiation.getClauses();
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
            throw new CantGetListPurchaseNegotiationsException("Cannot find the proper clause");
        } catch (InvalidParameterException e) {
            throw new CantGetListPurchaseNegotiationsException("Cannot get the negotiation list", e);
        } catch (CantGetListClauseException e) {
            throw new CantGetListPurchaseNegotiationsException("Cannot find clauses list");
        }

    }

    /**
     * This method send a payment according the contract elements.
     *
     * @param contractHash
     *
     * @throws CantSendPaymentException
     */
    @Override
    public void sendPayment(String contractHash) throws CantSendPaymentException {
        try {
            CustomerBrokerContractPurchase customerBrokerContractPurchase;
            //TODO: This is the real implementation
            customerBrokerContractPurchase = customerBrokerContractPurchaseManager.getCustomerBrokerContractPurchaseForContractId(contractHash);

            //I need to discover the payment type (online or offline)
            String negotiationId = customerBrokerContractPurchase.getNegotiatiotId();
            CustomerBrokerPurchaseNegotiation negotiation = customerBrokerPurchaseNegotiationManager.getNegotiationsByNegotiationId(UUID.fromString(negotiationId));
            ContractClauseType contractClauseType = getContractClauseType(negotiation,ClauseType.CUSTOMER_PAYMENT_METHOD);

            if (contractClauseType.equals(ContractClauseType.CRYPTO_TRANSFER)) { //Case: sending online payment
                //TODO: here we need to get the CCP Wallet public key to send BTC to the customer, when the settings are finished, please, implement how to get the CCP Wallet public key here. Thanks.
                String cryptoBrokerPublicKey = "reference_wallet"; //TODO: this is a hardcoded public key
                customerOnlinePaymentManager.sendPayment(cryptoBrokerPublicKey, contractHash);

            } else {  // Case: sending offline payment.
                customerOfflinePaymentManager.sendPayment(contractHash);
            }

        } catch (CantGetListCustomerBrokerContractPurchaseException e) {
            throw new CantSendPaymentException(e, "Sending the payment", "Cannot get the contract");
        } catch (CantGetListPurchaseNegotiationsException e) {
            throw new CantSendPaymentException(e, "Sending the payment", "Cannot get the negotiation list");
        } catch (CantGetListClauseException e) {
            throw new CantSendPaymentException(e, "Sending the payment", "Cannot get the clauses list");
        }

    }

    /**
     * This method return the ContractClauseType included in a CustomerBrokerPurchaseNegotiation clauses
     *
     * @param customerBrokerPurchaseNegotiation
     *
     * @return
     *
     * @throws CantGetListClauseException
     */
    private ContractClauseType getContractClauseType(
            CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation,ClauseType paramClauseType ) throws
            CantGetListClauseException {
        try {
            //I will check if customerBrokerPurchaseNegotiation is null
            ObjectChecker.checkArgument(
                    customerBrokerPurchaseNegotiation,
                    "The customerBrokerPurchaseNegotiation is null");
            Collection<Clause> clauses = customerBrokerPurchaseNegotiation.getClauses();
            ClauseType clauseType;
            for (Clause clause : clauses) {
                clauseType = clause.getType();
                if (clauseType.equals(paramClauseType)) {
                    return ContractClauseType.getByCode(clause.getValue());
                }
            }
            throw new CantGetListClauseException("Cannot find the proper clause");
        } catch (InvalidParameterException e) {
            throw new CantGetListClauseException(
                    "An invalid parameter is found in ContractClauseType enum");
        } catch (ObjectNotSetException e) {
            throw new CantGetListClauseException(
                    "The CustomerBrokerPurchaseNegotiation is null");
        }

    }

    /**
     * This method execute a Customer Ack Merchandise Business Transaction
     *
     * @param contractHash
     *
     * @throws CantAckMerchandiseException
     */
    public ContractStatus ackMerchandise(String contractHash) throws CantAckMerchandiseException {
        try {
            CustomerBrokerContractPurchase customerBrokerContractPurchase;
            //TODO: This is the real implementation
            customerBrokerContractPurchase =
                    this.customerBrokerContractPurchaseManager.
                            getCustomerBrokerContractPurchaseForContractId(contractHash);
            /*//TODO: for testing
            CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManagerMock =
                    new CustomerBrokerContractPurchaseManagerMock();
            customerBrokerContractPurchase =
                    customerBrokerContractPurchaseManagerMock.
                            getCustomerBrokerContractPurchaseForContractId(contractHash);*/
            //End of Mock testing
            //System.out.println("From module:"+customerBrokerContractPurchase);
            String negotiationId = customerBrokerContractPurchase.getNegotiatiotId();
            CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation =
                    this.customerBrokerPurchaseNegotiationManager.getNegotiationsByNegotiationId(
                            UUID.fromString(negotiationId));
            /*//TODO: remove this mock
            customerBrokerPurchaseNegotiation = new PurchaseNegotiationOfflineMock();*/
            ContractClauseType contractClauseType = getContractClauseType(
                    customerBrokerPurchaseNegotiation,ClauseType.BROKER_PAYMENT_METHOD);
            /**
             * Case: ack crypto merchandise.
             */
            if (contractClauseType.getCode() == ContractClauseType.CRYPTO_TRANSFER.getCode()) {
                return customerBrokerContractPurchase.getStatus();
            }
            /**
             * Case: ack offline merchandise.
             */
            if (contractClauseType.getCode() == ContractClauseType.BANK_TRANSFER.getCode() ||
                    contractClauseType.getCode() == ContractClauseType.CASH_DELIVERY.getCode() ||
                    contractClauseType.getCode() == ContractClauseType.CASH_ON_HAND.getCode()) {
                this.customerAckOfflineMerchandiseManager.ackMerchandise(contractHash);
                return customerBrokerContractPurchase.getStatus();
            }

            throw new CantAckMerchandiseException("Cannot find the contract clause");

        } catch (CantGetListCustomerBrokerContractPurchaseException e) {
            throw new CantAckMerchandiseException(
                    e,
                    "Cannot ack the merchandise",
                    "Cannot get the contract");
        } catch (CantGetListClauseException e) {
            throw new CantAckMerchandiseException(
                    e,
                    "Cannot ack the merchandise",
                    "Cannot get the clauses list");
        } catch (CantGetListPurchaseNegotiationsException e) {
            throw new CantAckMerchandiseException(
                    e,
                    "Cannot ack the merchandise",
                    "Cannot get the negotiation list");
        }
    }

    /**
     * This method returns the ContractStatus by contractHash/Id
     *
     * @param contractHash
     *
     * @return
     */
    @Override
    public ContractStatus getContractStatus(String contractHash) throws
            CantGetListCustomerBrokerContractPurchaseException {

        CustomerBrokerContractPurchase customerBrokerContractPurchase;
        //TODO: This is the real implementation
        customerBrokerContractPurchase =
                this.customerBrokerContractPurchaseManager.getCustomerBrokerContractPurchaseForContractId(contractHash);
        /*//TODO: for testing
        CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManagerMock =
                new CustomerBrokerContractPurchaseManagerMock();
        customerBrokerContractPurchase =
                customerBrokerContractPurchaseManagerMock.
                        getCustomerBrokerContractPurchaseForContractId(contractHash);*/
        //End of testing
        return customerBrokerContractPurchase.getStatus();

    }

    @Override
    public ActorIdentity getBrokerInfoByPublicKey(String customerPublicKey, String brokerPublicKey) throws CantListActorConnectionsException {
        try {

            CryptoBrokerLinkedActorIdentity linkedActorIdentity = new CryptoBrokerLinkedActorIdentity(
                    customerPublicKey,
                    Actors.CBP_CRYPTO_CUSTOMER
            );

            final CryptoBrokerActorConnectionSearch search = cryptoBrokerActorConnectionManager.getSearch(linkedActorIdentity);

            search.addConnectionState(ConnectionState.CONNECTED);

            List<CryptoBrokerActorConnection> brokers = search.getResult();

            for (CryptoBrokerActorConnection broker : brokers) {
                if (broker.getPublicKey().equalsIgnoreCase(brokerPublicKey)) {
                    return new CryptoCustomerWalletActorIdentity(broker.getPublicKey(), broker.getAlias(), broker.getImage());
                }
            }

            return null;

        } catch (CantListActorConnectionsException e) {

            throw new CantListActorConnectionsException(e, "", "Error trying to list the broker connections of the customer.");
        }
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

    /**
     * This method returns a string with the currency code.
     * @param customerBrokerContractPurchase
     * @param contractDetailType
     * @return
     * @throws CantGetListPurchaseNegotiationsException
     */
    /*public String getCurrencyCodeFromContract(
            CustomerBrokerContractPurchase customerBrokerContractPurchase,
            ContractDetailType contractDetailType) throws
            CantGetListPurchaseNegotiationsException{
        try {
            String negotiationId=customerBrokerContractPurchase.getNegotiatiotId();
            CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation=
                    customerBrokerPurchaseNegotiationManager.getNegotiationsByNegotiationId(
                            UUID.fromString(negotiationId));
            Collection<Clause> clauses = customerBrokerPurchaseNegotiation.getClauses();
            ClauseType clauseType;
            String currencyCode;
            for(Clause clause : clauses){
                clauseType=clause.getType();

                switch (contractDetailType){
                    case BROKER_DETAIL:
                        if(clauseType.equals(ClauseType.BROKER_CURRENCY)){
                            currencyCode=clause.getValue();
                            return FiatCurrency.getByCode(currencyCode).getFriendlyName();
                        }
                    case CUSTOMER_DETAIL:
                        if(clauseType.equals(ClauseType.CUSTOMER_CURRENCY)){
                            currencyCode=clause.getValue();
                            return FiatCurrency.getByCode(currencyCode).getFriendlyName();
                        }
                }
            }
            throw new CantGetListPurchaseNegotiationsException("Cannot find the proper clause");
        } catch (CantGetListClauseException e) {
            throw new CantGetListPurchaseNegotiationsException("Cannot find clauses list");
        } catch (InvalidParameterException e) {
            throw new CantGetListPurchaseNegotiationsException("Cannot get the negotiation list",e );
        }

    }*/

    /**
     * This method returns the currency amount
     *
     * @return
     *
     * @throws CantGetListPurchaseNegotiationsException
     */
    /*public float getCurrencyAmountFromContract(
            CustomerBrokerContractPurchase customerBrokerContractPurchase,
            ContractDetailType contractDetailType) throws
            CantGetListPurchaseNegotiationsException{
        try {
            String negotiationId=customerBrokerContractPurchase.getNegotiatiotId();
            CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation=
                    customerBrokerPurchaseNegotiationManager.getNegotiationsByNegotiationId(
                            UUID.fromString(negotiationId));
            Collection<Clause> clauses = customerBrokerPurchaseNegotiation.getClauses();
            ClauseType clauseType;
            String currencyAmount;
            for(Clause clause : clauses){
                clauseType=clause.getType();

                switch (contractDetailType){
                    case BROKER_DETAIL:
                        if(clauseType.equals(ClauseType.BROKER_CURRENCY_QUANTITY)){
                            currencyAmount=clause.getValue();
                            return Float.valueOf(currencyAmount);
                        }
                    case CUSTOMER_DETAIL:
                        if(clauseType.equals(ClauseType.CUSTOMER_CURRENCY_QUANTITY)){
                            currencyAmount=clause.getValue();
                            return Float.valueOf(currencyAmount);
                        }
                }
            }
            throw new CantGetListPurchaseNegotiationsException("Cannot find the proper clause");
        } catch (CantGetListClauseException e) {
            throw new CantGetListPurchaseNegotiationsException("Cannot find clauses list");
        }

    }*/
    @Override
    public SettingsManager<CryptoCustomerWalletPreferenceSettings> getSettingsManager() {
        if (this.settingsManager != null)
            return this.settingsManager;

        this.settingsManager = new SettingsManager<>(
                pluginFileSystem,
                pluginId
        );

        return this.settingsManager;
    }

    @Override
    public ActiveActorIdentityInformation getSelectedActorIdentity() throws CantGetSelectedActorIdentityException {
        return null;
    }

    @Override
    public void createIdentity(String name, String phrase, byte[] profile_img) throws Exception {
        cryptoCustomerIdentityManager.createCryptoCustomerIdentity(name, profile_img);
    }

    @Override
    public void setAppPublicKey(String publicKey) {

    }

    @Override
    public int[] getMenuNotifications() {
        return new int[0];
    }

    private Collection<Clause> getClause(Collection<ClauseInformation> clauseInformation) {

        Collection<Clause> collectionClause = new ArrayList<>();
        Clause clause;

        for (ClauseInformation information : clauseInformation) {
            if (information != null) {

                clause = new CryptoCustomerWalletModuleClausesImpl(
                        information.getClauseID(),
                        information.getType(),
                        information.getValue(),
                        information.getStatus(),
                        "",
                        (short) 1
                );

                collectionClause.add(clause);
            }
        }

        return collectionClause;
    }

    //Add by Yordin Alayn 18.02.16
    private Collection<ClauseInformation> getClauseInformation(Map<ClauseType, ClauseInformation> mapClauses) {

        Collection<ClauseInformation> clauses = new ArrayList<>();

        if (mapClauses != null)
            for (Map.Entry<ClauseType, ClauseInformation> clauseInformation : mapClauses.entrySet())
                clauses.add(clauseInformation.getValue());

        return clauses;
    }

    //Add by Yordin Alayn 03.02.16
    private CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation getItemNegotiationInformation(CustomerBrokerPurchaseNegotiation customerBrokerSaleNegotiation, NegotiationStatus status)
            throws CantGetNegotiationsWaitingForBrokerException {

        try {

            CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation = null;

            Collection<Clause> negotiationClause = customerBrokerSaleNegotiation.getClauses();
            Map<ClauseType, ClauseInformation> clauses = getNegotiationClause(negotiationClause);
            UUID negotiationId = customerBrokerSaleNegotiation.getNegotiationId();
            long lastUpdateDate = customerBrokerSaleNegotiation.getLastNegotiationUpdateDate();
            String customerPublickey = customerBrokerSaleNegotiation.getCustomerPublicKey();
            ActorIdentity customerIdentity = new CryptoCustomerWalletModuleActorIdentityImpl(customerPublickey, "Not Alias", new byte[0]);
            String brokerPublickey = customerBrokerSaleNegotiation.getBrokerPublicKey();
            ActorIdentity brokerIdentity = new CryptoCustomerWalletModuleActorIdentityImpl(brokerPublickey, "Not Alias", new byte[0]);
            long expirationDate = customerBrokerSaleNegotiation.getNegotiationExpirationDate();
//            long expirationDate = new Date().getTime();
            String note = "";
            String cancelReason = "";

            if (cryptoCustomerIdentityManager.getCryptoCustomerIdentity(customerPublickey) != null)
                customerIdentity = cryptoCustomerIdentityManager.getCryptoCustomerIdentity(customerPublickey);

            if (this.actorExtraDataManager.getActorInformationByPublicKey(brokerPublickey) != null)
                brokerIdentity = this.actorExtraDataManager.getActorInformationByPublicKey(brokerPublickey);

            if (customerBrokerSaleNegotiation.getMemo() != null)
                note = customerBrokerSaleNegotiation.getMemo();

            if (customerBrokerSaleNegotiation.getCancelReason() != null)
                cancelReason = customerBrokerSaleNegotiation.getCancelReason();

            cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation = new CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation(
                    customerIdentity,
                    brokerIdentity,
                    negotiationId,
                    status,
                    clauses,
                    note,
                    lastUpdateDate,
                    expirationDate,
                    cancelReason
            );

            return cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation;

        } catch (CantGetListActorExtraDataException ex) {
            throw new CantGetNegotiationsWaitingForBrokerException(CantGetListActorExtraDataException.DEFAULT_MESSAGE, ex, "Not Get actorExtraData, Identity", "");
        } catch (Exception ex) {
            throw new CantGetNegotiationsWaitingForBrokerException("Cant get negotiations waiting for the broker", ex, "", "");
        }
    }

    //Add by Yordin Alayn 03.02.16
    private Map<ClauseType, ClauseInformation> getNegotiationClause(Collection<Clause> negotiationClause) {

        Map<ClauseType, ClauseInformation> clauses = new HashMap<>();
        for (Clause item : negotiationClause) {
            clauses.put(
                    item.getType(),
                    putClause(item.getType(), item.getValue(), item.getStatus())
            );
        }

        return clauses;
    }

    //Add by Yordin Alayn 03.02.16
    private ClauseInformation putClause(final ClauseType clauseType, final String value, final ClauseStatus status) {

        return new ClauseInformation() {
            @Override
            public UUID getClauseID() {
                return UUID.randomUUID();
            }

            @Override
            public ClauseType getType() {
                return clauseType;
            }

            @Override
            public String getValue() {
                return (value != null) ? value : "";
            }

            @Override
//            public ClauseStatus getStatus() { return (status != null) ? status : ClauseStatus.DRAFT; }
            public ClauseStatus getStatus() {
                return ClauseStatus.DRAFT;
            }
        };
    }
}
