package com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure;

import android.util.Log;

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
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.modules.interfaces.FermatSettings;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ActorType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractDetailType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationType;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationBankAccount;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationLocations;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantCreateNewCustomerIdentityWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantGetListActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantGetCustomerIdentityWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.RelationshipNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.ActorExtraData;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.ActorExtraDataManager;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.CustomerIdentityWalletRelationship;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.QuotesExtraData;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.interfaces.CryptoBrokerActorConnectionManager;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.interfaces.CryptoBrokerActorConnectionSearch;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.utils.CryptoBrokerActorConnection;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.utils.CryptoBrokerLinkedActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_customer.interfaces.CryptoCustomerActorConnectionSearch;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_customer.utils.CryptoCustomerActorConnection;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_customer.utils.CryptoCustomerLinkedActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantAckMerchandiseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantSendPaymentException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.ObjectChecker;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.mocks.CustomerBrokerContractPurchaseManagerMock;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.mocks.CustomerBrokerContractPurchaseMock;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.mocks.PurchaseNegotiationOfflineMock;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.mocks.PurchaseNegotiationOnlineMock;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_ack_offline_merchandise.interfaces.CustomerAckOfflineMerchandiseManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_ack_online_merchandise.interfaces.CustomerAckOnlineMerchandiseManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_offline_payment.interfaces.CustomerOfflinePaymentManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_online_payment.interfaces.CustomerOnlinePaymentManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.interfaces.ContractSaleRecord;
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
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantGetAssociatedCryptoCustomerIdentityException;
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
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.CryptoCustomerWalletManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.settings.CryptoCustomerWalletAssociatedSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.settings.CryptoCustomerWalletPreferenceSettings;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.settings.CryptoCustomerWalletProviderSetting;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.CurrencyPair;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.ExchangeRate;
import com.bitdubai.fermat_cer_api.all_definition.utils.CurrencyPairImpl;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantGetExchangeRateException;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantGetProviderInfoException;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.UnsupportedCurrencyPairException;
import com.bitdubai.fermat_cer_api.layer.provider.interfaces.CurrencyExchangeRateProviderManager;
import com.bitdubai.fermat_cer_api.layer.search.exceptions.CantGetProviderException;
import com.bitdubai.fermat_cer_api.layer.search.interfaces.CurrencyExchangeProviderFilterManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
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
public class CryptoCustomerWalletModuleCryptoCustomerWalletManager implements CryptoCustomerWalletManager {
    public static final String PATH_DIRECTORY = "cbpwallet/setting";

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
    private String merchandise = null, typeOfPayment = null, paymentCurrency = null;
    private float amount = 0, exchangeRateAmount = 0;
    private final CustomerOnlinePaymentManager customerOnlinePaymentManager;
    private final CustomerOfflinePaymentManager customerOfflinePaymentManager;
    private final CustomerAckOnlineMerchandiseManager customerAckOnlineMerchandiseManager;
    private final CustomerAckOfflineMerchandiseManager customerAckOfflineMerchandiseManager;
    private final SettingsManager<CryptoCustomerWalletPreferenceSettings> settingsManager;

    private final ErrorManager           errorManager          ;
    private final PluginVersionReference pluginVersionReference;

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
                                                                 SettingsManager<CryptoCustomerWalletPreferenceSettings> settingsManager,

                                                                 final ErrorManager           errorManager          ,
                                                                 final PluginVersionReference pluginVersionReference) {
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

        this.settingsManager = settingsManager;

        this.errorManager           = errorManager          ;
        this.pluginVersionReference = pluginVersionReference;
    }

    @Override
    public Collection<ContractBasicInformation> getContractsHistory(ContractStatus status, int max, int offset) throws CantGetContractHistoryException, CantGetListCustomerBrokerContractPurchaseException {


        try {
            List<ContractBasicInformation> filteredList = new ArrayList<>();
//            List<ContractBasicInformation> contractsHistory = new ArrayList<>();
            CryptoBrokerWalletModuleContractBasicInformation contract = null;
            NegotiationStatus statusNegotiationCancelled = NegotiationStatus.CANCELLED;
            ContractStatus statusContractCancelled = ContractStatus.CANCELLED;
            String aliasCustomer = "Customer";

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

                    aliasCustomer = "Customer";
                    if (cryptoCustomerIdentityManager.getCryptoCustomerIdentity(customerBrokerContractPurchase.getPublicKeyCustomer()) != null)
                        aliasCustomer = cryptoCustomerIdentityManager.getCryptoCustomerIdentity(customerBrokerContractPurchase.getPublicKeyCustomer()).getAlias();

                    contract = new CryptoBrokerWalletModuleContractBasicInformation(aliasCustomer, new byte[0], brokerIdentity.getAlias(), brokerIdentity.getProfileImage(), merchandise, typeOfPayment, paymentCurrency, status, PurchaseNegotiation);
                    //contract = new CryptoBrokerWalletModuleContractBasicInformation(aliasCustomer, merchandise, typeOfPayment, paymentCurrency, status, PurchaseNegotiation);
                    filteredList.add(contract);
                }
//                contractsHistory = filteredList;

                //NEGOTIATION
                if (status.equals(statusContractCancelled)) {
                    for (CustomerBrokerPurchaseNegotiation PurchaseNegotiation : customerBrokerPurchaseNegotiationManager.getNegotiationsByStatus(statusNegotiationCancelled)) {

                        merchandise = getClauseType(PurchaseNegotiation, ClauseType.CUSTOMER_CURRENCY);
                        typeOfPayment = getClauseType(PurchaseNegotiation, ClauseType.CUSTOMER_PAYMENT_METHOD);
                        paymentCurrency = getClauseType(PurchaseNegotiation, ClauseType.BROKER_CURRENCY);

                        aliasCustomer = "Customer";
                        if (cryptoCustomerIdentityManager.getCryptoCustomerIdentity(PurchaseNegotiation.getCustomerPublicKey()) != null)
                            aliasCustomer = cryptoCustomerIdentityManager.getCryptoCustomerIdentity(PurchaseNegotiation.getCustomerPublicKey()).getAlias();

                        contract = new CryptoBrokerWalletModuleContractBasicInformation(aliasCustomer, new byte[0], null, null, merchandise, typeOfPayment, paymentCurrency, statusContractCancelled, PurchaseNegotiation);
                        filteredList.add(contract);
                    }
                }

            } else {

                ListsForStatusPurchase history = customerBrokerContractPurchaseManager.getCustomerBrokerContractHistory();
                final Collection<CustomerBrokerContractPurchase> historyContracts = history.getHistoryContracts();

                //CONTRACT
                for (CustomerBrokerContractPurchase customerBrokerContractPurchase : historyContracts) {

                    CustomerBrokerPurchaseNegotiation PurchaseNegotiation = customerBrokerPurchaseNegotiationManager.getNegotiationsByNegotiationId(UUID.fromString(customerBrokerContractPurchase.getNegotiatiotId()));
                    if (customerBrokerContractPurchase.getStatus().equals(status)) {
                        merchandise = getClauseType(PurchaseNegotiation, ClauseType.CUSTOMER_CURRENCY);
                        typeOfPayment = getClauseType(PurchaseNegotiation, ClauseType.CUSTOMER_PAYMENT_METHOD);
                        paymentCurrency = getClauseType(PurchaseNegotiation, ClauseType.BROKER_CURRENCY);
                    }
                    ActorIdentity brokerIdentity = getBrokerInfoByPublicKey(customerBrokerContractPurchase.getPublicKeyCustomer(), customerBrokerContractPurchase.getPublicKeyBroker());

                    aliasCustomer = "Customer";
                    if (cryptoCustomerIdentityManager.getCryptoCustomerIdentity(customerBrokerContractPurchase.getPublicKeyCustomer()) != null)
                        aliasCustomer = cryptoCustomerIdentityManager.getCryptoCustomerIdentity(customerBrokerContractPurchase.getPublicKeyCustomer()).getAlias();

                    contract = new CryptoBrokerWalletModuleContractBasicInformation(aliasCustomer, new byte[0], brokerIdentity.getAlias(), brokerIdentity.getProfileImage(), merchandise, typeOfPayment, paymentCurrency, customerBrokerContractPurchase.getStatus(), PurchaseNegotiation);
                    //contract = new CryptoBrokerWalletModuleContractBasicInformation(aliasCustomer, merchandise, typeOfPayment, paymentCurrency, customerBrokerContractPurchase.getStatus(), PurchaseNegotiation);
                    filteredList.add(contract);
                }

                //NEGOTIATION
                for (CustomerBrokerPurchaseNegotiation PurchaseNegotiation : customerBrokerPurchaseNegotiationManager.getNegotiationsByStatus(statusNegotiationCancelled)) {

                    merchandise = getClauseType(PurchaseNegotiation, ClauseType.CUSTOMER_CURRENCY);
                    typeOfPayment = getClauseType(PurchaseNegotiation, ClauseType.CUSTOMER_PAYMENT_METHOD);
                    paymentCurrency = getClauseType(PurchaseNegotiation, ClauseType.BROKER_CURRENCY);

                    aliasCustomer = "Customer";
                    if (cryptoCustomerIdentityManager.getCryptoCustomerIdentity(PurchaseNegotiation.getCustomerPublicKey()) != null)
                        aliasCustomer = cryptoCustomerIdentityManager.getCryptoCustomerIdentity(PurchaseNegotiation.getCustomerPublicKey()).getAlias();
                    contract = new CryptoBrokerWalletModuleContractBasicInformation(aliasCustomer, new byte[0], null, null, merchandise, typeOfPayment, paymentCurrency, statusContractCancelled, PurchaseNegotiation);
                    //contract = new CryptoBrokerWalletModuleContractBasicInformation(aliasCustomer, merchandise, typeOfPayment, paymentCurrency, statusContractCancelled, PurchaseNegotiation);
                    filteredList.add(contract);
                }
            }


            //TODO:Eliminar solo para que se terminen las pantallas
            contract = new CryptoBrokerWalletModuleContractBasicInformation("publicKeyCustomer", new byte[0], "publicKeyBroker", new byte[0], "merchandise", "typeOfPayment", "paymentCurrency", ContractStatus.CANCELLED, null);
            filteredList.add(contract);
            contract = new CryptoBrokerWalletModuleContractBasicInformation("publicKeyCustomer", new byte[0], "publicKeyBroker", new byte[0], "merchandise", "typeOfPayment", "paymentCurrency", ContractStatus.CANCELLED, null);
            filteredList.add(contract);
            contract = new CryptoBrokerWalletModuleContractBasicInformation("publicKeyCustomer", new byte[0], "publicKeyBroker", new byte[0], "merchandise", "typeOfPayment", "paymentCurrency", ContractStatus.CANCELLED, null);
            filteredList.add(contract);

            return filteredList;

        } catch (CantGetListCustomerBrokerContractPurchaseException ex) {
            throw new CantGetContractHistoryException(ex);
        } catch (CantGetListPurchaseNegotiationsException ex){
            throw new CantGetContractHistoryException(ex);
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

                contract = new CryptoBrokerWalletModuleContractBasicInformation(customerIdentity.getAlias(), customerIdentity.getProfileImage(), brokerIdentity.getAlias(), brokerIdentity.getProfileImage(),
                                                                                merchandise, typeOfPayment, paymentCurrency, customerBrokerContract.getStatus(), negotiation);
                waitingForBroker.add(contract);
            }

            //TODO:Eliminar. Solo para que se terminen las pantallas
            contract = new CryptoBrokerWalletModuleContractBasicInformation("TESTMOCK", new byte[0], "TESTMOCK", new byte[0],
                                                                            "TESTMOCK", "TESTMOCK", "TESTMOCK", ContractStatus.PAYMENT_SUBMIT, null);
            waitingForBroker.add(contract);

            return waitingForBroker;

        }catch(CantGetListPurchaseNegotiationsException e){
            throw new CantGetContractsWaitingForBrokerException("Cant get contracts waiting for the broker", e);
        }catch(CantGetListCustomerBrokerContractPurchaseException e){
            throw new CantGetContractsWaitingForBrokerException("Cant get contracts waiting for the broker", e);
        }catch(Exception ex){
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

                contract = new CryptoBrokerWalletModuleContractBasicInformation(customerIdentity.getAlias(), customerIdentity.getProfileImage(), brokerIdentity.getAlias(), brokerIdentity.getProfileImage(),
                                                                                merchandise, typeOfPayment, paymentCurrency, customerBrokerContract.getStatus(), negotiation);
                waitingForBroker.add(contract);
            }

            //TODO: Eliminar. Solo para que se terminen las pantallas
            contract = new CryptoBrokerWalletModuleContractBasicInformation("TESTMOCK", new byte[0], "TESTMOCK", new byte[0],
                                                                            "TESTMOCK", "TESTMOCK", "TESTMOCK", ContractStatus.PENDING_MERCHANDISE, null);
            waitingForBroker.add(contract);

            return waitingForBroker;

        }catch(CantGetListPurchaseNegotiationsException e){
            throw new CantGetContractsWaitingForCustomerException("Cant get contracts waiting for the broker", e);
        }catch(CantGetListCustomerBrokerContractPurchaseException e){
            throw new CantGetContractsWaitingForCustomerException("Cant get contracts waiting for the broker", e);
        }catch(Exception ex){
            throw new CantGetContractsWaitingForCustomerException("Cant get contracts waiting for the broker", ex);
        }
    }

    @Override
    public Collection<Clause> getNegotiationClausesFromNegotiationId(UUID negotiationId) throws CantGetListClauseException
    {
        try{
            CustomerBrokerPurchaseNegotiation negotiation = customerBrokerPurchaseNegotiationManager.getNegotiationsByNegotiationId(negotiationId);
            Collection<Clause> clauses = negotiation.getClauses();
            return clauses;
        }catch(CantGetListPurchaseNegotiationsException | CantGetListClauseException ex) {
            throw new CantGetListClauseException("Cant get the negotiation clauses for the given negotiationId " + negotiationId.toString(), ex);
        }
    }

    private String getClauseType(CustomerBrokerPurchaseNegotiation purchaseNegotiation, ClauseType clauseType) throws CantGetListClauseException{
        String value = null;
        try {
            if(purchaseNegotiation != null && clauseType != null) {
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
    public CustomerBrokerNegotiationInformation getNegotiationInformation(UUID negotiationID) throws CantGetNegotiationInformationException, CantGetListPurchaseNegotiationsException {
        CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation;
        cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation = new CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation(customerBrokerPurchaseNegotiationManager.getNegotiationsByNegotiationId(negotiationID));
        return cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation;
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
    public boolean associateIdentity(ActorIdentity customer, String walletPublicKey) throws CantCreateNewCustomerIdentityWalletRelationshipException {
        CustomerIdentityWalletRelationship relationship = actorExtraDataManager.createNewCustomerIdentityWalletRelationship(customer, walletPublicKey);
        return relationship != null;
//        return true;
    }

    @Override
    public boolean haveAssociatedIdentity(final String walletPublicKey) throws CantListCryptoCustomerIdentityException, CantGetCustomerIdentityWalletRelationshipException {

        CustomerIdentityWalletRelationship relationship;

        try {

            relationship = actorExtraDataManager.getCustomerIdentityWalletRelationshipByWallet(walletPublicKey);

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
            throw new IdentityAssociatedNotFoundException(relationshipNotFoundException, "walletPublicKey: "+walletPublicKey, "There's no relationship for this wallet.");
        } catch (CantGetCryptoCustomerIdentityException cantGetCryptoCustomerIdentityException) {

            errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantGetCryptoCustomerIdentityException);
            throw new CantGetAssociatedIdentityException(cantGetCryptoCustomerIdentityException, "walletPublicKey: "+walletPublicKey, "There was a problem trying to get a crypto customer identity.");
        } catch (CantGetCustomerIdentityWalletRelationshipException cantGetCustomerIdentityWalletRelationshipException) {

            errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantGetCustomerIdentityWalletRelationshipException);
            throw new CantGetAssociatedIdentityException(cantGetCustomerIdentityWalletRelationshipException, "walletPublicKey: "+walletPublicKey, "There was a problem listing the relationship between the wallet and the crypto customers.");
        } catch (Exception exception) {

            errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new CantGetAssociatedIdentityException(exception, "walletPublicKey: "+walletPublicKey, "Unhandled Error.");
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
     * @param negotiation negotiation information
     * @param reason reason of cancellation
     * @throws CouldNotCancelNegotiationException exception in Wallet Module Crypto Customer
     * @return CustomerBrokerNegotiationInformation
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
                            "\n - NEGOTIATION ID "+ negotiation.getNegotiationId() + " | " + negotiationInformation.getNegotiationId() +
                            "\n - STATUS "+ negotiation.getStatus() +" | "+ negotiationInformation.getStatus() +
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
    public CryptoCustomerIdentity getAssociatedIdentity() throws CantGetAssociatedCryptoCustomerIdentityException {
        return null;
    }

    @Override
    public boolean isWalletConfigured(String customerWalletPublicKey) throws CantGetSettingsException, SettingsNotFoundException {
        CryptoCustomerWalletPreferenceSettings walletSettings = settingsManager.loadAndGetSettings(customerWalletPublicKey);
        return walletSettings.getSelectedBitcoinWallet() != null && !walletSettings.getSelectedProviders().isEmpty();
    }

    /**
     * Add by Yordin Alayn 22.01.16
     * This method start negotiation
     * @param customerPublicKey public key of customer
     * @param brokerPublicKey public key od broker
     * @param clauses Clauses of negotiation
     * @throws CouldNotStartNegotiationException, exception in wallet module Crypto Customer
     * @throws CantCreateCustomerBrokerNewPurchaseNegotiationTransactionException exception in CustomerBrokerNew Negotiation Transaction
     * @return boolean
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
//            for (ClauseInformation information: clauses){
//                System.out.print("\n**** 1.1.1) - CLAUSES: ****\n" +
//                        "\n- "+information.getType().getCode()+": "+information.getValue()+" (STATUS: "+information.getStatus()+")");
//            }

            System.out.print("\n**** 1.2) MOCK MODULE CRYPTO CUSTOMER - PURCHASE NEGOTIATION - CLAUSES NEGOTIATION****\n");
//            for (Clause information: clauseNegotiation){
//                System.out.print("\n**** 1.2.1) - CLAUSES: ****\n" +
//                        "\n- "+information.getType().getCode()+": "+information.getValue()+" (STATUS: "+information.getStatus()+")");
//            }

            customerBrokerNewManager.createCustomerBrokerNewPurchaseNegotiationTransaction(customerBrokerPurchaseNegotiation);

            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * Add by Yordin Alayn 22.01.16
     * This method Update or Close Negotiation.
     * @param negotiation negotiation information
     * @throws CouldNotUpdateNegotiationException, exception in wallet module Crypto Customer
     * @throws CantCreateCustomerBrokerUpdatePurchaseNegotiationTransactionException exception in CustomerBrokerUpdate Negotiation Transaction
     * @return boolean
     */
    @Override
    public boolean updateNegotiation(CustomerBrokerNegotiationInformation negotiation) throws CouldNotUpdateNegotiationException {
        try {

            System.out.print("\n**** 1) MOCK MODULE CRYPTO CUSTOMER - UPDATE NEGOTIATION****\n" +
                    "\n-CUSTOMER: " + negotiation.getCustomer().getPublicKey() +
                    "\n-BROKER: " + negotiation.getBroker().getPublicKey() + "" +
                    "\n-Memo: " + negotiation.getMemo());

            //VALIDATE STATUS CLAUSE
            ClauseStatus validateStatusClause = validateStatusClause(negotiation.getClauses());

            if ((validateStatusClause != ClauseStatus.CONFIRM)) {

                System.out.print("\n**** 1.1) MOCK MODULE CRYPTO CUSTOMER - UPDATE NEGOTIATION - CLAUSES INFORMATION****\n");

                if (validateStatusClause.equals(ClauseStatus.CHANGED) || !negotiation.getMemo().isEmpty()) {
                    System.out.print("\n**** 1.2) MOCK MODULE CRYPTO CUSTOMER - UPDATE NEGOTIATION - CLAUSES INFORMATION****\n");
                    CustomerBrokerPurchaseNegotiationImpl customerBrokerPurchaseNegotiation = purchaseNegotiation(negotiation,NegotiationStatus.SENT_TO_BROKER);
                    customerBrokerUpdateManager.createCustomerBrokerUpdatePurchaseNegotiationTranasction(customerBrokerPurchaseNegotiation);
                } else if (validateStatusClause.equals(ClauseStatus.ACCEPTED)) {
                    System.out.print("\n**** 1.2) MOCK MODULE CRYPTO CUSTOMER - CLOSE NEGOTIATION - CLAUSES INFORMATION****\n");
                    CustomerBrokerPurchaseNegotiationImpl customerBrokerPurchaseNegotiation = purchaseNegotiation(negotiation,NegotiationStatus.SENT_TO_BROKER);
                    customerBrokerCloseManager.createCustomerBrokerClosePurchaseNegotiationTranasction(customerBrokerPurchaseNegotiation);
                }
            }

            return true;
        } catch (CantCreateCustomerBrokerUpdatePurchaseNegotiationTransactionException e) {
            throw new CouldNotUpdateNegotiationException(e.getMessage(),e, CouldNotUpdateNegotiationException.DEFAULT_MESSAGE, "ERROR REGISTER CUSTOMER BROKER PURCHASE NEGOTIATION TR*ANSACTION, IN CUSTOMER BROKER PURCHASE NEGOTIATION, UPDATE.");
//            return false;
        } catch (CantCreateCustomerBrokerPurchaseNegotiationException e){
            throw new CouldNotUpdateNegotiationException(e.getMessage(),e, CouldNotUpdateNegotiationException.DEFAULT_MESSAGE, "ERROR REGISTER CUSTOMER BROKER PURCHASE NEGOTIATION TRANSACTION, IN CUSTOMER BROKER PURCHASE NEGOTIATION, CLOSE.");
//            return false;
        } catch (Exception e) {
            throw new CouldNotUpdateNegotiationException(e.getMessage(), FermatException.wrapException(e), CouldNotUpdateNegotiationException.DEFAULT_MESSAGE, "ERROR PROCESS CUSTOMER BROKER PURCHASE NEGOTIATION, UNKNOWN FAILURE.");
//            return false;
        }
    }

    private CustomerBrokerPurchaseNegotiationImpl purchaseNegotiation(CustomerBrokerNegotiationInformation negotiation, NegotiationStatus status){

        Date time = new Date();
        Map<ClauseType, ClauseInformation> mapClauses = negotiation.getClauses();

        Collection<Clause> clauseNegotiation = getClause(getClauseInformation(mapClauses));
        CustomerBrokerPurchaseNegotiationImpl customerBrokerPurchaseNegotiation = new CustomerBrokerPurchaseNegotiationImpl();
        customerBrokerPurchaseNegotiation.setBrokerPublicKey(negotiation.getBroker().getPublicKey());
        customerBrokerPurchaseNegotiation.setCustomerPublicKey(negotiation.getCustomer().getPublicKey());
        customerBrokerPurchaseNegotiation.setNegotiationId(negotiation.getNegotiationId());
        customerBrokerPurchaseNegotiation.setStartDate(time.getTime());
        customerBrokerPurchaseNegotiation.setStatus(status);
        customerBrokerPurchaseNegotiation.setClauses(clauseNegotiation);
        customerBrokerPurchaseNegotiation.setNearExpirationDatetime(false);
        customerBrokerPurchaseNegotiation.setNegotiationExpirationDate(time.getTime());
        customerBrokerPurchaseNegotiation.setLastNegotiationUpdateDate(negotiation.getNegotiationExpirationDate());
        customerBrokerPurchaseNegotiation.setMemo(negotiation.getMemo());

        return customerBrokerPurchaseNegotiation;
    }

    private ClauseStatus validateStatusClause(Map<ClauseType, ClauseInformation> clause){

        boolean isChange = false;

        String customerPaymentMethod = clause.get(ClauseType.CUSTOMER_PAYMENT_METHOD).getValue();
        String brokerPaymentMethod = clause.get(ClauseType.BROKER_PAYMENT_METHOD).getValue();

        for (ClauseInformation item : clause.values()) {
            if (validateClauseUsed(item,customerPaymentMethod, brokerPaymentMethod)) {
                if ((item.getStatus() != ClauseStatus.CHANGED) && (item.getStatus() != ClauseStatus.ACCEPTED))
                    return ClauseStatus.CONFIRM;

                if (item.getStatus().equals(ClauseStatus.CHANGED))
                    isChange = true;
            }
        }

        if (isChange)
            return ClauseStatus.CHANGED;
        else
            return ClauseStatus.ACCEPTED;
    }

    private boolean validateClauseUsed(ClauseInformation item, String customerPaymentMethod, String brokerPaymentMethod){

        if( item.getType().equals(ClauseType.BROKER_BANK_ACCOUNT)       ||
                item.getType().equals(ClauseType.BROKER_PAYMENT_METHOD)     ||
                item.getType().equals(ClauseType.BROKER_CRYPTO_ADDRESS)     ||
                item.getType().equals(ClauseType.CUSTOMER_BANK_ACCOUNT)     ||
                item.getType().equals(ClauseType.CUSTOMER_PAYMENT_METHOD)   ||
                item.getType().equals(ClauseType.CUSTOMER_CRYPTO_ADDRESS)
                ) {

            if (item.getType().equals(ClauseType.BROKER_BANK_ACCOUNT) && (brokerPaymentMethod.equals(MoneyType.BANK.getCode())))
                return true;
            else if (item.getType().equals(ClauseType.BROKER_PLACE_TO_DELIVER) && ((brokerPaymentMethod.equals(MoneyType.CASH_DELIVERY.getCode())) || (brokerPaymentMethod.equals(MoneyType.CASH_ON_HAND.getCode()))))
                return true;
            else if (item.getType().equals(ClauseType.BROKER_CRYPTO_ADDRESS) && (brokerPaymentMethod.equals(MoneyType.CRYPTO.getCode())))
                return true;
            else if (item.getType().equals(ClauseType.CUSTOMER_BANK_ACCOUNT) && (customerPaymentMethod.equals(MoneyType.BANK.getCode())))
                return true;
            else if (item.getType().equals(ClauseType.CUSTOMER_PAYMENT_METHOD) && ((customerPaymentMethod.equals(MoneyType.CASH_DELIVERY.getCode())) || (customerPaymentMethod.equals(MoneyType.CASH_ON_HAND.getCode()))))
                return true;
            else if (item.getType().equals(ClauseType.CUSTOMER_CRYPTO_ADDRESS) && (customerPaymentMethod.equals(MoneyType.CRYPTO.getCode())))
                return true;
            else
                return false;

        } else {
            return true;
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
     * @throws CantCreateLocationPurchaseException
     */
    @Override
    public void createNewLocation(String location, String uri) throws CantCreateLocationPurchaseException {
        customerBrokerPurchaseNegotiationManager.createNewLocation(location, uri);
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
     * @throws CantCreateBankAccountPurchaseException
     */
    @Override
    public void createNewBankAccount(NegotiationBankAccount bankAccount) throws CantCreateBankAccountPurchaseException {
        customerBrokerPurchaseNegotiationManager.createNewBankAccount(bankAccount);
    }

    /**
     * @param bankAccount
     * @throws CantUpdateBankAccountPurchaseException
     */
    @Override
    public void updateBankAccount(NegotiationBankAccount bankAccount) throws CantUpdateBankAccountPurchaseException {
        customerBrokerPurchaseNegotiationManager.updateBankAccount(bankAccount);
    }

    /**
     * @param bankAccount
     * @throws CantDeleteBankAccountPurchaseException
     */
    @Override
    public void deleteBankAccount(NegotiationBankAccount bankAccount) throws CantDeleteBankAccountPurchaseException {
        customerBrokerPurchaseNegotiationManager.deleteBankAccount(bankAccount);
    }

    /**
     * @param negotiation
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

    @Override
    public List<CryptoCustomerWalletProviderSetting> getAssociatedProviders(String walletPublicKey) throws CantGetSettingsException, SettingsNotFoundException {
        CryptoCustomerWalletPreferenceSettings settings = settingsManager.loadAndGetSettings(walletPublicKey);
        return settings.getSelectedProviders();
    }

    /**
     * This method returns the CustomerBrokerContractPurchase associated to a negotiationId
     *
     * @param negotiationId
     * @return
     * @throws CantGetListCustomerBrokerContractPurchaseException
     */
    @Override
    public CustomerBrokerContractPurchase getCustomerBrokerContractPurchaseByNegotiationId(String negotiationId) throws CantGetListCustomerBrokerContractPurchaseException {
        Collection<CustomerBrokerContractPurchase> customerBrokerContractPurchases = customerBrokerContractPurchaseManager.getAllCustomerBrokerContractPurchase();
        String negotiationIdFromCollection;

        for(CustomerBrokerContractPurchase customerBrokerContractPurchase : customerBrokerContractPurchases){
            negotiationIdFromCollection = customerBrokerContractPurchase.getNegotiatiotId();
            if(negotiationIdFromCollection.equals(negotiationId)){
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
     * @param customerBrokerContractPurchase
     * @param contractDetailType
     * @return
     * @throws CantGetListPurchaseNegotiationsException
     */
    @Override
    public MoneyType getCurrencyTypeFromContract(
            CustomerBrokerContractPurchase customerBrokerContractPurchase,
            ContractDetailType contractDetailType) throws
            CantGetListPurchaseNegotiationsException {
        try {
            String negotiationId = customerBrokerContractPurchase.getNegotiatiotId();
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
     * @throws CantSendPaymentException
     */
    @Override
    public void sendPayment(String contractHash) throws CantSendPaymentException {
        try {
            CustomerBrokerContractPurchase customerBrokerContractPurchase;
            //TODO: This is the real implementation
            /*customerBrokerContractPurchase =
                    this.customerBrokerContractPurchaseManager.
                            getCustomerBrokerContractPurchaseForContractId(contractHash);*/
            //TODO: for testing
            CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManagerMock =
                    new CustomerBrokerContractPurchaseManagerMock();
            customerBrokerContractPurchase =
                    customerBrokerContractPurchaseManagerMock.
                            getCustomerBrokerContractPurchaseForContractId(contractHash);
            //End of Mock testing
            //I need to discover the payment type (online or offline)
            String negotiationId = customerBrokerContractPurchase.getNegotiatiotId();
            CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation =
                    this.customerBrokerPurchaseNegotiationManager.getNegotiationsByNegotiationId(
                            UUID.fromString(negotiationId));
            //TODO: remove this mock
            customerBrokerPurchaseNegotiation = new PurchaseNegotiationOnlineMock();
            ContractClauseType contractClauseType = getContractClauseType(
                    customerBrokerPurchaseNegotiation);
            /**
             * Case: sending crypto payment.
             */
            if (contractClauseType.getCode() == ContractClauseType.CRYPTO_TRANSFER.getCode()) {

                /**
                 * TODO: here we need to get the CCP Wallet public key to send BTC to customer,
                 * when the settings is finished, please, implement how to get the CCP Wallet public
                 * key here. Thanks.
                 */
                //TODO: this is a hardcoded public key
                String cryptoBrokerPublicKey = "walletPublicKeyTest";
                this.customerOnlinePaymentManager.sendPayment(
                        cryptoBrokerPublicKey,
                        contractHash);
            }
            /**
             * Case: sending offline payment.
             */
            if (contractClauseType.getCode() == ContractClauseType.BANK_TRANSFER.getCode() ||
                    contractClauseType.getCode() == ContractClauseType.CASH_DELIVERY.getCode() ||
                    contractClauseType.getCode() == ContractClauseType.CASH_DELIVERY.getCode()) {
                this.customerOfflinePaymentManager.sendPayment(contractHash);
            }
        } catch (CantGetListCustomerBrokerContractPurchaseException e) {
            throw new CantSendPaymentException(
                    e,
                    "Sending the payment",
                    "Cannot get the contract");
        } catch (CantGetListPurchaseNegotiationsException e) {
            throw new CantSendPaymentException(
                    e,
                    "Sending the payment",
                    "Cannot get the negotiation list");
        } catch (CantGetListClauseException e) {
            throw new CantSendPaymentException(
                    e,
                    "Sending the payment",
                    "Cannot get the clauses list");
        }

    }

    /**
     * This method return the ContractClauseType included in a CustomerBrokerPurchaseNegotiation clauses
     *
     * @param customerBrokerPurchaseNegotiation
     * @return
     * @throws CantGetListClauseException
     */
    private ContractClauseType getContractClauseType(
            CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation) throws
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
                if (clauseType.equals(ClauseType.CUSTOMER_PAYMENT_METHOD)) {
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
     * @throws CantAckMerchandiseException
     */
    public ContractStatus ackMerchandise(String contractHash) throws CantAckMerchandiseException {
        try {
            CustomerBrokerContractPurchase customerBrokerContractPurchase;
            //TODO: This is the real implementation
            /*customerBrokerContractPurchase =
                    this.customerBrokerContractPurchaseManager.
                            getCustomerBrokerContractPurchaseForContractId(contractHash);*/
            //TODO: for testing
            CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManagerMock =
                    new CustomerBrokerContractPurchaseManagerMock();
            customerBrokerContractPurchase =
                    customerBrokerContractPurchaseManagerMock.
                            getCustomerBrokerContractPurchaseForContractId(contractHash);
            //End of Mock testing
            //System.out.println("From module:"+customerBrokerContractPurchase);
            String negotiationId = customerBrokerContractPurchase.getNegotiatiotId();
            CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation =
                    this.customerBrokerPurchaseNegotiationManager.getNegotiationsByNegotiationId(
                            UUID.fromString(negotiationId));
            //TODO: remove this mock
            customerBrokerPurchaseNegotiation = new PurchaseNegotiationOfflineMock();
            ContractClauseType contractClauseType = getContractClauseType(
                    customerBrokerPurchaseNegotiation);
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
     * @return
     */
    @Override
    public ContractStatus getContractStatus(String contractHash) throws
            CantGetListCustomerBrokerContractPurchaseException {

        CustomerBrokerContractPurchase customerBrokerContractPurchase;
        //TODO: This is the real implementation
        /*customerBrokerContractPurchase =
                this.customerBrokerContractPurchaseManager.getCustomerBrokerContractPurchaseForContractId(contractHash);*/
        //TODO: for testing
        CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManagerMock =
                new CustomerBrokerContractPurchaseManagerMock();
        customerBrokerContractPurchase =
                customerBrokerContractPurchaseManagerMock.
                        getCustomerBrokerContractPurchaseForContractId(contractHash);
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
        }    }

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
    public SettingsManager<FermatSettings> getSettingsManager() {
        return null;
    }

    @Override
    public ActiveActorIdentityInformation getSelectedActorIdentity() throws CantGetSelectedActorIdentityException {
        return null;
    }

    @Override
    public void createIdentity(String name, String phrase, byte[] profile_img) throws Exception {

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
            UUID negotiationId  = customerBrokerSaleNegotiation.getNegotiationId();
            long lastUpdateDate = customerBrokerSaleNegotiation.getLastNegotiationUpdateDate();
            String customerPublickey = customerBrokerSaleNegotiation.getCustomerPublicKey();
            ActorIdentity customerIdentity = new CryptoCustomerWalletModuleActorIdentityImpl(customerPublickey, "Not Alias", new byte[0]);
            String brokerPublickey = customerBrokerSaleNegotiation.getBrokerPublicKey();
            ActorIdentity brokerIdentity = new CryptoCustomerWalletModuleActorIdentityImpl(brokerPublickey, "Not Alias", new byte[0]);
            long expirationDate = customerBrokerSaleNegotiation.getNegotiationExpirationDate();
//            long expirationDate = new Date().getTime();
            String note = "";

            if (cryptoCustomerIdentityManager.getCryptoCustomerIdentity(customerPublickey) != null)
                customerIdentity = cryptoCustomerIdentityManager.getCryptoCustomerIdentity(customerPublickey);

            if (this.actorExtraDataManager.getActorInformationByPublicKey(brokerPublickey) != null)
                brokerIdentity = this.actorExtraDataManager.getActorInformationByPublicKey(brokerPublickey);

            if (customerBrokerSaleNegotiation.getMemo() != null)
                note = customerBrokerSaleNegotiation.getMemo();

            cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation = new CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation(
                    customerIdentity,
                    brokerIdentity,
                    negotiationId,
                    status,
                    clauses,
                    note,
                    lastUpdateDate,
                    expirationDate
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
            public UUID getClauseID() { return UUID.randomUUID(); }

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
            public ClauseStatus getStatus() { return ClauseStatus.DRAFT; }
        };
    }
}
