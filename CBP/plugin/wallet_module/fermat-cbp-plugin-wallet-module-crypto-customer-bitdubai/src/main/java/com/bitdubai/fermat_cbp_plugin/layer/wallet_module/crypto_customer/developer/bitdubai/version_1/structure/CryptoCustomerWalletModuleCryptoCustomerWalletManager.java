package com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.modules.interfaces.FermatSettings;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.contract.ContractClause;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractDetailType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationType;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationBankAccount;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationLocations;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantGetListActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.ActorExtraData;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.ActorExtraDataManager;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.QuotesExtraData;
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
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantGetListCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchase;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchaseManager;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.exceptions.CantListCryptoCustomerIdentityException;
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
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.exceptions.CantCreateCustomerBrokerNewPurchaseNegotiationTransactionException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.interfaces.CustomerBrokerNewManager;
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
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantGetCurrentIndexSummaryForCurrenciesOfInterestException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantNewEmptyCryptoCustomerWalletAssociatedSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantNewEmptyCryptoCustomerWalletProviderSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantNewEmptyCustomerBrokerNegotiationInformationException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantSaveCryptoCustomerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CouldNotStartNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.BrokerIdentityBusinessInfo;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.CryptoCustomerWalletManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.settings.CryptoCustomerWalletAssociatedSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.settings.CryptoCustomerWalletProviderSetting;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.CurrencyPair;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantGetProviderInfoException;
import com.bitdubai.fermat_cer_api.layer.provider.interfaces.CurrencyExchangeRateProviderManager;
import com.bitdubai.fermat_cer_api.layer.search.exceptions.CantGetProviderException;
import com.bitdubai.fermat_cer_api.layer.search.interfaces.CurrencyExchangeProviderFilterManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantListWalletsException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletManagerManager;

import java.text.DecimalFormat;
import java.text.NumberFormat;
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
    private final CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager;
    private final UUID pluginId;
    private final PluginFileSystem pluginFileSystem;
    private final CryptoCustomerIdentityManager cryptoCustomerIdentityManager;
    private final CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager;
    private final CustomerBrokerNewManager customerBrokerNewManager;
    private final CurrencyExchangeProviderFilterManager currencyExchangeProviderFilterManager;
    private final ActorExtraDataManager actorExtraDataManager;
    private String merchandise = null, typeOfPayment = null, paymentCurrency = null;
    private final CustomerOnlinePaymentManager customerOnlinePaymentManager;
    private final CustomerOfflinePaymentManager customerOfflinePaymentManager;
    private final CustomerAckOnlineMerchandiseManager customerAckOnlineMerchandiseManager;
    private final CustomerAckOfflineMerchandiseManager customerAckOfflineMerchandiseManager;

    /*
    *Constructor with Parameters
    */
    public CryptoCustomerWalletModuleCryptoCustomerWalletManager(WalletManagerManager walletManagerManager,
                                                                 CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager,
                                                                 UUID pluginId,
                                                                 PluginFileSystem pluginFileSystem,
                                                                 CryptoCustomerIdentityManager cryptoCustomerIdentityManager,
                                                                 CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager,
                                                                 CustomerBrokerNewManager customerBrokerNewManage,
                                                                 CurrencyExchangeProviderFilterManager currencyExchangeProviderFilterManager,
                                                                 ActorExtraDataManager actorExtraDataManager,
                                                                 CustomerOnlinePaymentManager customerOnlinePaymentManager,
                                                                 CustomerOfflinePaymentManager customerOfflinePaymentManager,
                                                                 CustomerAckOnlineMerchandiseManager customerAckOnlineMerchandiseManager,
                                                                 CustomerAckOfflineMerchandiseManager customerAckOfflineMerchandiseManager) {
        this.walletManagerManager = walletManagerManager;
        this.customerBrokerPurchaseNegotiationManager = customerBrokerPurchaseNegotiationManager;
        this.pluginId = pluginId;
        this.pluginFileSystem = pluginFileSystem;
        this.cryptoCustomerIdentityManager = cryptoCustomerIdentityManager;
        this.customerBrokerContractPurchaseManager = customerBrokerContractPurchaseManager;
        this.customerBrokerNewManager = customerBrokerNewManage;
        this.currencyExchangeProviderFilterManager = currencyExchangeProviderFilterManager;
        this.actorExtraDataManager = actorExtraDataManager;
        this.customerOnlinePaymentManager = customerOnlinePaymentManager;
        this.customerOfflinePaymentManager = customerOfflinePaymentManager;
        this.customerAckOnlineMerchandiseManager = customerAckOnlineMerchandiseManager;
        this.customerAckOfflineMerchandiseManager = customerAckOfflineMerchandiseManager;
    }

    private List<ContractBasicInformation> contractsHistory;
    private List<ContractBasicInformation> openContracts;
    private List<CustomerBrokerNegotiationInformation> openNegotiations;
    private List<BrokerIdentityBusinessInfo> connectedBrokers;

    @Override
    public Collection<ContractBasicInformation> getContractsHistory(ContractStatus status, int max, int offset) throws CantGetContractHistoryException, CantGetListCustomerBrokerContractPurchaseException {
        List<ContractBasicInformation> filteredList = new ArrayList<>();
        try {

            List<ContractBasicInformation> contractsHistory = new ArrayList<>();

            CryptoBrokerWalletModuleContractBasicInformation contract = null;

            if (status != null) {

                //TODO: Preguntar de donde saco la demas informacion del contract "merchandise Moneda que recibe como mercancia el customer", "typeOfPayment Forma en la que paga el customer", "paymentCurrency Moneda que recibe como pago el broker"
                for (CustomerBrokerContractPurchase customerBrokerContractPurchase : customerBrokerContractPurchaseManager.getCustomerBrokerContractPurchaseForStatus(status)) {
                    CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation = customerBrokerPurchaseNegotiationManager.getNegotiationsByNegotiationId(UUID.fromString(customerBrokerContractPurchase.getNegotiatiotId()));
                    if (customerBrokerContractPurchase.getStatus().equals(status))
                        for (Clause clause : customerBrokerPurchaseNegotiation.getClauses()) {
                            if (clause.getType().getCode() == ClauseType.CUSTOMER_CURRENCY.getCode()) {
                                merchandise = clause.getValue();
                            }
                            if (clause.getType().getCode() == ClauseType.CUSTOMER_PAYMENT_METHOD.getCode()) {
                                typeOfPayment = clause.getValue();
                            }
                            if (clause.getType().getCode() == ClauseType.BROKER_CURRENCY.getCode()) {
                                paymentCurrency = clause.getValue();
                            }
                        }
                    contract = new CryptoBrokerWalletModuleContractBasicInformation(customerBrokerContractPurchase.getPublicKeyCustomer(), merchandise, typeOfPayment, paymentCurrency, status, customerBrokerPurchaseNegotiation);
                    filteredList.add(contract);
                }
                contractsHistory = filteredList;
            } else {
                for (CustomerBrokerContractPurchase customerBrokerContractPurchase : customerBrokerContractPurchaseManager.getCustomerBrokerContractPurchaseForStatus(ContractStatus.CANCELLED)) {
                    CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation = customerBrokerPurchaseNegotiationManager.getNegotiationsByNegotiationId(UUID.fromString(customerBrokerContractPurchase.getNegotiatiotId()));
                    if (customerBrokerContractPurchase.getStatus().equals(status))
                        for (Clause clause : customerBrokerPurchaseNegotiation.getClauses()) {
                            if (clause.getType().getCode() == ClauseType.CUSTOMER_CURRENCY.getCode()) {
                                merchandise = clause.getValue();
                            }
                            if (clause.getType().getCode() == ClauseType.CUSTOMER_PAYMENT_METHOD.getCode()) {
                                typeOfPayment = clause.getValue();
                            }
                            if (clause.getType().getCode() == ClauseType.BROKER_CURRENCY.getCode()) {
                                paymentCurrency = clause.getValue();
                            }
                        }
                    contract = new CryptoBrokerWalletModuleContractBasicInformation(customerBrokerContractPurchase.getPublicKeyCustomer(), merchandise, typeOfPayment, paymentCurrency, ContractStatus.CANCELLED, customerBrokerPurchaseNegotiation);
                    filteredList.add(contract);
                }
                for (CustomerBrokerContractPurchase customerBrokerContractPurchase : customerBrokerContractPurchaseManager.getCustomerBrokerContractPurchaseForStatus(ContractStatus.COMPLETED)) {
                    CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation = customerBrokerPurchaseNegotiationManager.getNegotiationsByNegotiationId(UUID.fromString(customerBrokerContractPurchase.getNegotiatiotId()));
                    if (customerBrokerContractPurchase.getStatus().equals(status))
                        for (Clause clause : customerBrokerPurchaseNegotiation.getClauses()) {
                            if (clause.getType().getCode() == ClauseType.CUSTOMER_CURRENCY.getCode()) {
                                merchandise = clause.getValue();
                            }
                            if (clause.getType().getCode() == ClauseType.CUSTOMER_PAYMENT_METHOD.getCode()) {
                                typeOfPayment = clause.getValue();
                            }
                            if (clause.getType().getCode() == ClauseType.BROKER_CURRENCY.getCode()) {
                                paymentCurrency = clause.getValue();
                            }
                        }
                    contract = new CryptoBrokerWalletModuleContractBasicInformation(customerBrokerContractPurchase.getPublicKeyCustomer(), merchandise, typeOfPayment, paymentCurrency, ContractStatus.COMPLETED, customerBrokerPurchaseNegotiation);
                    filteredList.add(contract);
                }
            }

            //TODO:Eliminar solo para que se terminen las pantallas
            contract = new CryptoBrokerWalletModuleContractBasicInformation("publicKeyCustomer", "merchandise", "typeOfPayment", "paymentCurrency", ContractStatus.COMPLETED, null);
            contract = new CryptoBrokerWalletModuleContractBasicInformation("publicKeyCustomer", "merchandise", "typeOfPayment", "paymentCurrency", ContractStatus.COMPLETED, null);
            contract = new CryptoBrokerWalletModuleContractBasicInformation("publicKeyCustomer", "merchandise", "typeOfPayment", "paymentCurrency", ContractStatus.CANCELLED, null);
            filteredList.add(contract);
            return contractsHistory;

        } catch (Exception ex) {
            throw new CantGetContractHistoryException(ex);
        }

    }

    @Override
    public Collection<ContractBasicInformation> getContractsWaitingForBroker(int max, int offset) throws CantGetContractsWaitingForBrokerException {
        try {
            ContractBasicInformation contract;
            Collection<ContractBasicInformation> waitingForBroker = new ArrayList<>();

            for (CustomerBrokerContractPurchase customerBrokerContractPurchase : customerBrokerContractPurchaseManager.getCustomerBrokerContractPurchaseForStatus(ContractStatus.PENDING_MERCHANDISE)) {
                CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation = customerBrokerPurchaseNegotiationManager.getNegotiationsByNegotiationId(UUID.fromString(customerBrokerContractPurchase.getNegotiatiotId()));
                for (Clause clause : customerBrokerPurchaseNegotiation.getClauses()) {
                    if (clause.getType().getCode() == ClauseType.CUSTOMER_CURRENCY.getCode()) {
                        merchandise = clause.getValue();
                    }
                    if (clause.getType().getCode() == ClauseType.CUSTOMER_PAYMENT_METHOD.getCode()) {
                        typeOfPayment = clause.getValue();
                    }
                    if (clause.getType().getCode() == ClauseType.BROKER_CURRENCY.getCode()) {
                        paymentCurrency = clause.getValue();
                    }
                }
                contract = new CryptoBrokerWalletModuleContractBasicInformation(customerBrokerContractPurchase.getPublicKeyCustomer(), merchandise, typeOfPayment, paymentCurrency, ContractStatus.PENDING_MERCHANDISE, customerBrokerPurchaseNegotiation);
                waitingForBroker.add(contract);
            }
            for (CustomerBrokerContractPurchase customerBrokerContractPurchase : customerBrokerContractPurchaseManager.getCustomerBrokerContractPurchaseForStatus(ContractStatus.MERCHANDISE_SUBMIT)) {
                CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation = customerBrokerPurchaseNegotiationManager.getNegotiationsByNegotiationId(UUID.fromString(customerBrokerContractPurchase.getNegotiatiotId()));
                for (Clause clause : customerBrokerPurchaseNegotiation.getClauses()) {
                    if (clause.getType().getCode() == ClauseType.CUSTOMER_CURRENCY.getCode()) {
                        merchandise = clause.getValue();
                    }
                    if (clause.getType().getCode() == ClauseType.CUSTOMER_PAYMENT_METHOD.getCode()) {
                        typeOfPayment = clause.getValue();
                    }
                    if (clause.getType().getCode() == ClauseType.BROKER_CURRENCY.getCode()) {
                        paymentCurrency = clause.getValue();
                    }
                }
                contract = new CryptoBrokerWalletModuleContractBasicInformation(customerBrokerContractPurchase.getPublicKeyCustomer(), merchandise, typeOfPayment, paymentCurrency, ContractStatus.PAYMENT_SUBMIT, customerBrokerPurchaseNegotiation);
                waitingForBroker.add(contract);
            }
            //TODO:Eliminar solo para que se terminen las pantallas
            contract = new CryptoBrokerWalletModuleContractBasicInformation("publicKeyCustomer", "merchandise", "typeOfPayment", "paymentCurrency", ContractStatus.PAYMENT_SUBMIT, null);
            contract = new CryptoBrokerWalletModuleContractBasicInformation("publicKeyCustomer", "merchandise", "typeOfPayment", "paymentCurrency", ContractStatus.PAYMENT_SUBMIT, null);
            contract = new CryptoBrokerWalletModuleContractBasicInformation("publicKeyCustomer", "merchandise", "typeOfPayment", "paymentCurrency", ContractStatus.PAYMENT_SUBMIT, null);
            waitingForBroker.add(contract);
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

            for (CustomerBrokerContractPurchase customerBrokerContractPurchase : customerBrokerContractPurchaseManager.getCustomerBrokerContractPurchaseForStatus(ContractStatus.MERCHANDISE_SUBMIT)) {
                CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation = customerBrokerPurchaseNegotiationManager.getNegotiationsByNegotiationId(UUID.fromString(customerBrokerContractPurchase.getNegotiatiotId()));
                for (Clause clause : customerBrokerPurchaseNegotiation.getClauses()) {
                    if (clause.getType().getCode() == ClauseType.CUSTOMER_CURRENCY.getCode()) {
                        merchandise = clause.getValue();
                    }
                    if (clause.getType().getCode() == ClauseType.CUSTOMER_PAYMENT_METHOD.getCode()) {
                        typeOfPayment = clause.getValue();
                    }
                    if (clause.getType().getCode() == ClauseType.BROKER_CURRENCY.getCode()) {
                        paymentCurrency = clause.getValue();
                    }
                }
                contract = new CryptoBrokerWalletModuleContractBasicInformation(customerBrokerContractPurchase.getPublicKeyCustomer(), merchandise, typeOfPayment, paymentCurrency, ContractStatus.MERCHANDISE_SUBMIT, customerBrokerPurchaseNegotiation);
                waitingForBroker.add(contract);
            }
            for (CustomerBrokerContractPurchase customerBrokerContractPurchase : customerBrokerContractPurchaseManager.getCustomerBrokerContractPurchaseForStatus(ContractStatus.PENDING_PAYMENT)) {
                CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation = customerBrokerPurchaseNegotiationManager.getNegotiationsByNegotiationId(UUID.fromString(customerBrokerContractPurchase.getNegotiatiotId()));
                for (Clause clause : customerBrokerPurchaseNegotiation.getClauses()) {
                    if (clause.getType().getCode() == ClauseType.CUSTOMER_CURRENCY.getCode()) {
                        merchandise = clause.getValue();
                    }
                    if (clause.getType().getCode() == ClauseType.CUSTOMER_PAYMENT_METHOD.getCode()) {
                        typeOfPayment = clause.getValue();
                    }
                    if (clause.getType().getCode() == ClauseType.BROKER_CURRENCY.getCode()) {
                        paymentCurrency = clause.getValue();
                    }
                }
                contract = new CryptoBrokerWalletModuleContractBasicInformation(customerBrokerContractPurchase.getPublicKeyCustomer(), merchandise, typeOfPayment, paymentCurrency, ContractStatus.PENDING_PAYMENT, customerBrokerPurchaseNegotiation);
                waitingForBroker.add(contract);
            }
            //TODO:Eliminar solo para que se terminen las pantallas
            contract = new CryptoBrokerWalletModuleContractBasicInformation("publicKeyCustomer", "merchandise", "typeOfPayment", "paymentCurrency", ContractStatus.PENDING_PAYMENT, null);
            contract = new CryptoBrokerWalletModuleContractBasicInformation("publicKeyCustomer", "merchandise", "typeOfPayment", "paymentCurrency", ContractStatus.PENDING_PAYMENT, null);
            contract = new CryptoBrokerWalletModuleContractBasicInformation("publicKeyCustomer", "merchandise", "typeOfPayment", "paymentCurrency", ContractStatus.PENDING_PAYMENT, null);
            waitingForBroker.add(contract);
            return waitingForBroker;

        } catch (Exception ex) {
            throw new CantGetContractsWaitingForCustomerException("Cant get contracts waiting for the broker", ex);
        }
    }

    /*@Override
    public Collection<CustomerBrokerNegotiationInformation> getNegotiationsWaitingForBroker(int max, int offset) throws CantGetNegotiationsWaitingForBrokerException {
        try {
            CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation = null;

            Collection<CustomerBrokerNegotiationInformation> waitingForBroker = new ArrayList<>();
<<<<<<< HEAD
            for (CustomerBrokerPurchaseNegotiation customerBrokerSaleNegotiation : customerBrokerPurchaseNegotiationManager.getNegotiationsByStatus(NegotiationStatus.WAITING_FOR_BROKER))
            {
//                cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation = new CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation(customerBrokerSaleNegotiation.getCustomerPublicKey(), "merchandise", "paymentMethod", "paymentCurrency", NegotiationStatus.WAITING_FOR_BROKER);
                cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation = new CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation(customerBrokerSaleNegotiation);
=======
            for (CustomerBrokerPurchaseNegotiation customerBrokerSaleNegotiation : customerBrokerPurchaseNegotiationManager.getNegotiationsByStatus(NegotiationStatus.WAITING_FOR_BROKER)) {
                cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation = new CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation(customerBrokerSaleNegotiation.getCustomerPublicKey(), "merchandise", "paymentMethod", "paymentCurrency", NegotiationStatus.WAITING_FOR_BROKER);
>>>>>>> 7d921e1863e084e40627b781b9d06b5fe94d9d1d
                waitingForBroker.add(cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation);
            }
            //TODO:Eliminar solo para que se terminen las pantallas
//            cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation = new CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation("publicKeyCustomer", "merchandise", "paymentMethod", "paymentCurrency", NegotiationStatus.WAITING_FOR_BROKER);
//            cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation = new CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation("publicKeyCustomer", "merchandise", "paymentMethod", "paymentCurrency", NegotiationStatus.WAITING_FOR_BROKER);
//            cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation = new CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation("publicKeyCustomer", "merchandise", "paymentMethod", "paymentCurrency", NegotiationStatus.WAITING_FOR_BROKER);
//            waitingForBroker.add(cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation);
            return waitingForBroker;

        } catch (Exception ex) {
            throw new CantGetNegotiationsWaitingForBrokerException("Cant get negotiations waiting for the broker", ex, "", "");
        }
    }*/

    @Override
    //Modified by Yordin Alayn 03.02.16
    public Collection<CustomerBrokerNegotiationInformation> getNegotiationsWaitingForBroker(int max, int offset) throws CantGetNegotiationsWaitingForBrokerException {
        try {

            CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation = null;

            Collection<CustomerBrokerNegotiationInformation> waitingForBroker = new ArrayList<>();
            for (CustomerBrokerPurchaseNegotiation negotiation : customerBrokerPurchaseNegotiationManager.getNegotiationsByStatus(NegotiationStatus.SENT_TO_BROKER))
                waitingForBroker.add(getItemNegotiationInformation(negotiation, NegotiationStatus.WAITING_FOR_BROKER));

            for (CustomerBrokerPurchaseNegotiation negotiation : customerBrokerPurchaseNegotiationManager.getNegotiationsByStatus(NegotiationStatus.WAITING_FOR_BROKER))
                waitingForBroker.add(getItemNegotiationInformation(negotiation, NegotiationStatus.WAITING_FOR_BROKER));

            return waitingForBroker;

        } catch (Exception ex) {
            throw new CantGetNegotiationsWaitingForBrokerException("Cant get negotiations waiting for the broker", ex, "", "");
        }
    }

    @Override
    //Modified by Yordin Alayn 03.02.16
    public Collection<CustomerBrokerNegotiationInformation> getNegotiationsWaitingForCustomer(int max, int offset) throws CantGetNegotiationsWaitingForCustomerException {
        try {
            CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation = null;

            Collection<CustomerBrokerNegotiationInformation> waitingForBroker = new ArrayList<>();

            for (CustomerBrokerPurchaseNegotiation negotiation : customerBrokerPurchaseNegotiationManager.getNegotiationsByStatus(NegotiationStatus.WAITING_FOR_CUSTOMER))
                waitingForBroker.add(getItemNegotiationInformation(negotiation, NegotiationStatus.WAITING_FOR_CUSTOMER));

            //TODO:Eliminar solo para que se terminen las pantallas
//            cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation = new CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation("publicKeyCustomer", "merchandise", "paymentMethod", "paymentCurrency", NegotiationStatus.WAITING_FOR_BROKER);
//            cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation = new CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation("publicKeyCustomer", "merchandise", "paymentMethod", "paymentCurrency", NegotiationStatus.WAITING_FOR_BROKER);
//            cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation = new CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation("publicKeyCustomer", "merchandise", "paymentMethod", "paymentCurrency", NegotiationStatus.WAITING_FOR_BROKER);
//            waitingForBroker.add(cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation);
            return waitingForBroker;

        } catch (Exception ex) {
            throw new CantGetNegotiationsWaitingForCustomerException("Cant get negotiations waiting for the broker", ex, "", "");
        }
    }

    @Override
    public CustomerBrokerNegotiationInformation getNegotiationInformation(UUID negotiationID) throws CantGetNegotiationInformationException, CantGetListPurchaseNegotiationsException {
        CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation = null;
//        cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation = new CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation(customerBrokerPurchaseNegotiationManager.getNegotiationsByNegotiationId(negotiationID).getCustomerPublicKey(), "merchandise", "paymentMethod", "paymentCurrency", customerBrokerPurchaseNegotiationManager.getNegotiationsByNegotiationId(negotiationID).getStatus());
        cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation = new CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation(customerBrokerPurchaseNegotiationManager.getNegotiationsByNegotiationId(negotiationID));
        return cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation;
    }

    /**
     * @param negotiationType
     * @return Collection<NegotiationLocations>
     */
    @Override
    public Collection<NegotiationLocations> getAllLocations(NegotiationType negotiationType) throws CantGetListLocationsPurchaseException {
        Collection<NegotiationLocations> negotiationLocations = null;
        if (negotiationType.getCode() == NegotiationType.PURCHASE.getCode()) {
            negotiationLocations = customerBrokerPurchaseNegotiationManager.getAllLocations();
        }
        return negotiationLocations;
    }

    @Override
    public boolean associateIdentity(String customerId) {
        return false;
    }

    /**
     * @return list of identities associated with this wallet
     */
    @Override
    public List<CryptoCustomerIdentity> getListOfIdentities() throws CantGetCryptoCustomerIdentityListException, CantListCryptoCustomerIdentityException {
        List<CryptoCustomerIdentity> cryptoCustomerIdentities = cryptoCustomerIdentityManager.listAllCryptoCustomerFromCurrentDeviceUser();
        return cryptoCustomerIdentities;
    }

    @Override
    public CustomerBrokerNegotiationInformation cancelNegotiation(CustomerBrokerNegotiationInformation negotiation, String reason) throws CouldNotCancelNegotiationException {
        return null;
    }

    @Override
    public Collection<IndexInfoSummary> getCurrentIndexSummaryForCurrenciesOfInterest() throws CantGetCurrentIndexSummaryForCurrenciesOfInterestException {
        try {
            IndexInfoSummary indexInfoSummary;
            Collection<IndexInfoSummary> summaryList = new ArrayList<>();

            indexInfoSummary = new CryptoCustomerWalletModuleIndexInfoSummary(CryptoCurrency.BITCOIN, FiatCurrency.US_DOLLAR, 240.62, 235.87);
            summaryList.add(indexInfoSummary);
            indexInfoSummary = new CryptoCustomerWalletModuleIndexInfoSummary(FiatCurrency.VENEZUELAN_BOLIVAR, CryptoCurrency.BITCOIN, 245000, 240000);
            summaryList.add(indexInfoSummary);
            indexInfoSummary = new CryptoCustomerWalletModuleIndexInfoSummary(FiatCurrency.VENEZUELAN_BOLIVAR, FiatCurrency.US_DOLLAR, 840, 800);
            summaryList.add(indexInfoSummary);
            indexInfoSummary = new CryptoCustomerWalletModuleIndexInfoSummary(FiatCurrency.US_DOLLAR, FiatCurrency.EURO, 1.2, 1.1);
            summaryList.add(indexInfoSummary);

            return summaryList;

        } catch (Exception ex) {
            throw new CantGetCurrentIndexSummaryForCurrenciesOfInterestException(ex);
        }
    }

    @Override
    public Collection<BrokerIdentityBusinessInfo> getListOfConnectedBrokersAndTheirMerchandises() throws CantGetCryptoBrokerListException, CantGetListActorExtraDataException {

        Collection<ActorExtraData> actorExtraDataList = actorExtraDataManager.getAllActorExtraDataConnected();
        Collection<BrokerIdentityBusinessInfo> brokersBusinessInfo = new ArrayList<>();


        for (ActorExtraData actorExtraData : actorExtraDataList) {
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

        return brokersBusinessInfo;
    }

    @Override
    public CryptoCustomerIdentity getAssociatedIdentity() throws CantGetAssociatedCryptoCustomerIdentityException {
        return null;
    }

    @Override
    public boolean isWalletConfigured(String customerWalletPublicKey) {
        return false;
    }

    @Override
    public boolean startNegotiation(String customerPublicKey, String brokerPublicKey, Collection<ClauseInformation> clauses) throws CouldNotStartNegotiationException, CantCreateCustomerBrokerNewPurchaseNegotiationTransactionException {
        try {
            /*
            CustomerBrokerPurchaseNegotiationImpl customerBrokerPurchaseNegotiation = new CustomerBrokerPurchaseNegotiationImpl();
            customerBrokerPurchaseNegotiation.setClauses(getClause(clauses));
            customerBrokerPurchaseNegotiation.setBrokerPublicKey(brokerPublicKey);
            customerBrokerPurchaseNegotiation.setCustomerPublicKey(customerPublicKey);
            customerBrokerNewManager.createCustomerBrokerNewPurchaseNegotiationTranasction(customerBrokerPurchaseNegotiation);
            return true; //CustomerBrokerNewManager con la data minima
            */
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

            customerBrokerNewManager.createCustomerBrokerNewPurchaseNegotiationTranasction(customerBrokerPurchaseNegotiation);

            return true;
        } catch (Exception exception) {
            return false;
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
        CustomerBrokerNegotiationInformation customerBrokerNegotiationInformation = data;
        customerBrokerNegotiationInformation.setMemo(memo);
        return customerBrokerNegotiationInformation;
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
    public void saveWalletSettingAssociated(CryptoCustomerWalletAssociatedSetting setting, String customerWalletpublicKey) throws CantSaveCryptoCustomerWalletSettingException, CantCreateFileException, CantPersistFileException {
        String settingFilename = customerWalletpublicKey;
        PluginTextFile pluginTextFile = null;//pluginFileSystem.createTextFile(pluginId, PATH_DIRECTORY, customerWalletpublicKey, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
        String toXml = XMLParser.parseObject(setting);
        pluginTextFile = pluginFileSystem.createTextFile(pluginId, PATH_DIRECTORY, settingFilename, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
        pluginTextFile.setContent(toXml);
        pluginTextFile.persistToMedia();
    }

    /**
     * Returns a list of provider references which can obtain the ExchangeRate of the given CurrencyPair
     *
     * @param currencyFrom
     * @param currencyTo
     * @return a Map of name/provider reference pairs
     */
    @Override
    public Map<String, CurrencyExchangeRateProviderManager> getProviderReferencesFromCurrencyPair(final Currency currencyFrom, final Currency currencyTo) throws CantGetProviderException, CantGetProviderInfoException {
        Map<String, CurrencyExchangeRateProviderManager> managerMap = new HashMap<String, CurrencyExchangeRateProviderManager>();
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
    public void saveCryptoCustomerWalletProviderSetting(CryptoCustomerWalletProviderSetting setting, String customerWalletpublicKey) throws CantSaveCryptoCustomerWalletSettingException, CantPersistFileException, CantCreateFileException {
        String settingFilename = customerWalletpublicKey;
        PluginTextFile pluginTextFile;//pluginFileSystem.createTextFile(pluginId, PATH_DIRECTORY, customerWalletpublicKey, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
        String toXml = XMLParser.parseObject(setting);
        pluginTextFile = pluginFileSystem.createTextFile(pluginId, PATH_DIRECTORY, settingFilename, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
        pluginTextFile.setContent(toXml);
        pluginTextFile.persistToMedia();
    }

    @Override
    public List<CryptoCustomerWalletProviderSetting> getAssociatedProviders(String walletPublicKey) {
        return null;
    }

    /**
     * This method returns the CustomerBrokerContractPurchase associated to a negotiationId
     *
     * @param negotiationId
     * @return
     * @throws CantGetListCustomerBrokerContractPurchaseException
     */
    @Override
    public CustomerBrokerContractPurchase getCustomerBrokerContractPurchaseByNegotiationId(
            String negotiationId) throws CantGetListCustomerBrokerContractPurchaseException {
        Collection<CustomerBrokerContractPurchase> customerBrokerContractPurchases =
                customerBrokerContractPurchaseManager.getAllCustomerBrokerContractPurchase();
        String negotiationIdFromCollection;
        //This line is only for testing
        return new CustomerBrokerContractPurchaseMock();
        /*for(CustomerBrokerContractPurchase customerBrokerContractPurchase : customerBrokerContractPurchases){
            negotiationIdFromCollection=customerBrokerContractPurchase.getNegotiatiotId();
            if(negotiationIdFromCollection.equals(negotiationId)){
                return customerBrokerContractPurchase;
            }
        }
        throw new CantGetListCustomerBrokerContractPurchaseException(
                "Cannot find the contract associated to negotiation "+negotiationId);*/
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
    public CurrencyType getCurrencyTypeFromContract(
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
                            return CurrencyType.getByCode(clause.getValue());
                        }
                    case CUSTOMER_DETAIL:
                        if (clauseType.equals(ClauseType.CUSTOMER_PAYMENT_METHOD)) {
                            return CurrencyType.getByCode(clause.getValue());
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
                    "Cannot send the payment",
                    "Cannot get the contract");
        } catch (CantGetListPurchaseNegotiationsException e) {
            throw new CantSendPaymentException(
                    e,
                    "Cannot send the payment",
                    "Cannot get the negotiation list");
        } catch (CantGetListClauseException e) {
            throw new CantSendPaymentException(
                    e,
                    "Cannot send the payment",
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
    private List<ContractBasicInformation> getOpenContractsTestData() {
        if (openContracts == null) {
            ContractBasicInformation contract;
            openContracts = new ArrayList<>();
//
//            contract = new CryptoBrokerWalletModuleContractBasicInformation("adrianasupernova", "USD", "Crypto Transfer", "BTC", ContractStatus.PAUSED);
//            openContracts.add(contract);
//            contract = new CryptoBrokerWalletModuleContractBasicInformation("adrianasupernova", "USD", "Crypto Transfer", "BTC", ContractStatus.PAUSED);
//            openContracts.add(contract);
//            contract = new CryptoBrokerWalletModuleContractBasicInformation("adrianasupernova", "USD", "Crypto Transfer", "BTC", ContractStatus.PAUSED);
//            openContracts.add(contract);
//            contract = new CryptoBrokerWalletModuleContractBasicInformation("adrianasupernova", "USD", "Crypto Transfer", "BTC", ContractStatus.PAUSED);
//            openContracts.add(contract);
//            contract = new CryptoBrokerWalletModuleContractBasicInformation("yalayn", "BTC", "Bank Transfer", "USD", ContractStatus.PENDING_PAYMENT);
//            openContracts.add(contract);
//            contract = new CryptoBrokerWalletModuleContractBasicInformation("vzlangel", "BsF", "Cash Delivery", "BsF", ContractStatus.PENDING_PAYMENT);
//            openContracts.add(contract);
//            contract = new CryptoBrokerWalletModuleContractBasicInformation("vzlangel", "BsF", "Cash Delivery", "BsF", ContractStatus.PENDING_PAYMENT);
//            openContracts.add(contract);
//            contract = new CryptoBrokerWalletModuleContractBasicInformation("vzlangel", "BsF", "Cash Delivery", "BsF", ContractStatus.PENDING_PAYMENT);
//            openContracts.add(contract);
        }

        return openContracts;
    }

    private List<CustomerBrokerNegotiationInformation> getOpenNegotiationsTestData() {
        if (openNegotiations == null) {
            CustomerBrokerNegotiationInformation negotiation;
            openNegotiations = new ArrayList<>();

//            negotiation = new CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation("nelsonalfo", "USD", "Crypto Transfer", "BTC", NegotiationStatus.WAITING_FOR_BROKER);
//            openNegotiations.add(negotiation);
//            negotiation = new CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation("jorgeegonzalez", "BTC", "Cash in Hand", "USD", NegotiationStatus.WAITING_FOR_BROKER);
//            openNegotiations.add(negotiation);
//            negotiation = new CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation("neoperol", "USD", "Cash in Hand", "BsF", NegotiationStatus.WAITING_FOR_BROKER);
//            openNegotiations.add(negotiation);
//            negotiation = new CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation("lnacosta", "BTC", "Crypto Transfer", "LiteCoin", NegotiationStatus.WAITING_FOR_BROKER);
//            openNegotiations.add(negotiation);
//            negotiation = new CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation("andreaCoronado1", "USD", "Bank Transfer", "BsF", NegotiationStatus.WAITING_FOR_CUSTOMER);
//            openNegotiations.add(negotiation);
//            negotiation = new CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation("Customer 5", "$ Arg", "Crypto Transfer", "BTC", NegotiationStatus.WAITING_FOR_CUSTOMER);
//            openNegotiations.add(negotiation);
//            negotiation = new CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation("CustomerXX", "BTC", "Cash Delivery", "USD", NegotiationStatus.WAITING_FOR_CUSTOMER);
//            openNegotiations.add(negotiation);
        }

        return openNegotiations;
    }

    private List<ContractBasicInformation> getContractHistoryTestData() {
        if (contractsHistory == null) {
//            ContractBasicInformation contract;
//            contractsHistory = new ArrayList<>();
//
//            contract = new CryptoBrokerWalletModuleContractBasicInformation("adrianasupernova", "USD", "Crypto Transfer", "BTC", ContractStatus.COMPLETED);
//            contractsHistory.add(contract);
//            contract = new CryptoBrokerWalletModuleContractBasicInformation("nelsoanlfo", "BTC", "Bank Transfer", "Arg $", ContractStatus.COMPLETED);
//            contractsHistory.add(contract);
//            contract = new CryptoBrokerWalletModuleContractBasicInformation("neoperol", "USD", "Cash in Hand", "BsF", ContractStatus.COMPLETED);
//            contractsHistory.add(contract);
//            contract = new CryptoBrokerWalletModuleContractBasicInformation("nairovene", "USD", "Cash Delivery", "BsF", ContractStatus.CANCELLED);
//            contractsHistory.add(contract);
//            contract = new CryptoBrokerWalletModuleContractBasicInformation("Luis Pineda", "USD", "Crypto Transfer", "BTC", ContractStatus.PENDING_PAYMENT);
//            contractsHistory.add(contract);
//            contract = new CryptoBrokerWalletModuleContractBasicInformation("Carlos Ruiz", "USD", "Bank Transfer", "Col $", ContractStatus.CANCELLED);
//            contractsHistory.add(contract);
//            contract = new CryptoBrokerWalletModuleContractBasicInformation("josePres", "USD", "Crypto Transfer", "BTC", ContractStatus.COMPLETED);
//            contractsHistory.add(contract);
//            contract = new CryptoBrokerWalletModuleContractBasicInformation("nairo300", "USD", "Crypto Transfer", "BTC", ContractStatus.COMPLETED);
//            contractsHistory.add(contract);
//            contract = new CryptoBrokerWalletModuleContractBasicInformation("dbz_brokers", "USD", "Crypto Transfer", "BTC", ContractStatus.CANCELLED);
//            contractsHistory.add(contract);
//            contract = new CryptoBrokerWalletModuleContractBasicInformation("Mirian Margarita Noguera", "USD", "Crypto Transfer", "BTC", ContractStatus.CANCELLED);
//            contractsHistory.add(contract);
//            contract = new CryptoBrokerWalletModuleContractBasicInformation("adrianasupernova", "USD", "Crypto Transfer", "BTC", ContractStatus.PENDING_PAYMENT);
//            contractsHistory.add(contract);
//            contract = new CryptoBrokerWalletModuleContractBasicInformation("nelsoanlfo", "BTC", "Bank Transfer", "Arg $", ContractStatus.CANCELLED);
//            contractsHistory.add(contract);
//            contract = new CryptoBrokerWalletModuleContractBasicInformation("neoperol", "USD", "Cash in Hand", "BsF", ContractStatus.COMPLETED);
//            contractsHistory.add(contract);
//            contract = new CryptoBrokerWalletModuleContractBasicInformation("nairovene", "USD", "Cash Delivery", "BsF", ContractStatus.COMPLETED);
//            contractsHistory.add(contract);
//            contract = new CryptoBrokerWalletModuleContractBasicInformation("Luis Pineda", "USD", "Crypto Transfer", "BTC", ContractStatus.PENDING_PAYMENT);
//            contractsHistory.add(contract);
//            contract = new CryptoBrokerWalletModuleContractBasicInformation("Carlos Ruiz", "USD", "Crypto Transfer", "BTC", ContractStatus.CANCELLED);
//            contractsHistory.add(contract);
//            contract = new CryptoBrokerWalletModuleContractBasicInformation("josePres", "USD", "Crypto Transfer", "BTC", ContractStatus.COMPLETED);
//            contractsHistory.add(contract);
//            contract = new CryptoBrokerWalletModuleContractBasicInformation("nairo300", "USD", "Crypto Transfer", "BTC", ContractStatus.COMPLETED);
//            contractsHistory.add(contract);
//            contract = new CryptoBrokerWalletModuleContractBasicInformation("dbz_brokers", "USD", "Crypto Transfer", "BTC", ContractStatus.PENDING_PAYMENT);
//            contractsHistory.add(contract);
//            contract = new CryptoBrokerWalletModuleContractBasicInformation("Mirian Margarita Noguera", "USD", "Crypto Transfer", "BTC", ContractStatus.COMPLETED);
//            contractsHistory.add(contract);
        }

        return contractsHistory;
    }

    private List<BrokerIdentityBusinessInfo> getBrokerListTestData() {
        if (connectedBrokers == null) {
            connectedBrokers = new ArrayList<>();

            BrokerIdentityBusinessInfo broker;
            broker = new CryptoCustomerWalletModuleBrokerIdentityBusinessInfo("elBrokerVerdugo", new byte[0], "elBrokerVerdugo", CryptoCurrency.BITCOIN);
            connectedBrokers.add(broker);
            broker = new CryptoCustomerWalletModuleBrokerIdentityBusinessInfo("elBrokerVerdugo", new byte[0], "elBrokerVerdugo", FiatCurrency.US_DOLLAR);
            connectedBrokers.add(broker);
            broker = new CryptoCustomerWalletModuleBrokerIdentityBusinessInfo("Brokers de Argentina", new byte[0], "BrokersDeArgentina", FiatCurrency.US_DOLLAR);
            connectedBrokers.add(broker);
            broker = new CryptoCustomerWalletModuleBrokerIdentityBusinessInfo("Brokers de Argentina", new byte[0], "BrokersDeArgentina", FiatCurrency.AUSTRALIAN_DOLLAR);
            connectedBrokers.add(broker);
            broker = new CryptoCustomerWalletModuleBrokerIdentityBusinessInfo("Brokers de Argentina", new byte[0], "BrokersDeArgentina", CryptoCurrency.CHAVEZCOIN);
            connectedBrokers.add(broker);
            broker = new CryptoCustomerWalletModuleBrokerIdentityBusinessInfo("leonAcostaBroker", new byte[0], "leonAcostaBroker", CryptoCurrency.CHAVEZCOIN);
            connectedBrokers.add(broker);
            broker = new CryptoCustomerWalletModuleBrokerIdentityBusinessInfo("leonAcostaBroker", new byte[0], "leonAcostaBroker", CryptoCurrency.BITCOIN);
            connectedBrokers.add(broker);
            broker = new CryptoCustomerWalletModuleBrokerIdentityBusinessInfo("leonAcostaBroker", new byte[0], "leonAcostaBroker", CryptoCurrency.LITECOIN);
            connectedBrokers.add(broker);
            broker = new CryptoCustomerWalletModuleBrokerIdentityBusinessInfo("leonAcostaBroker", new byte[0], "leonAcostaBroker", FiatCurrency.EURO);
            connectedBrokers.add(broker);
            broker = new CryptoCustomerWalletModuleBrokerIdentityBusinessInfo("Nelson Ramirez", new byte[0], "NelsonRamirez", FiatCurrency.US_DOLLAR);
            connectedBrokers.add(broker);
            broker = new CryptoCustomerWalletModuleBrokerIdentityBusinessInfo("Luis Molina", new byte[0], "LuisMolina", FiatCurrency.CHINESE_YUAN);
            connectedBrokers.add(broker);
            broker = new CryptoCustomerWalletModuleBrokerIdentityBusinessInfo("Luis Molina", new byte[0], "LuisMolina", FiatCurrency.ARGENTINE_PESO);
            connectedBrokers.add(broker);
            broker = new CryptoCustomerWalletModuleBrokerIdentityBusinessInfo("Mayorista La Asuncion", new byte[0], "MayoristaLaAsuncion", FiatCurrency.BRITISH_POUND);
            connectedBrokers.add(broker);
            broker = new CryptoCustomerWalletModuleBrokerIdentityBusinessInfo("brokers_mcbo", new byte[0], "brokers_mcbo", FiatCurrency.US_DOLLAR);
            connectedBrokers.add(broker);
        }

        return connectedBrokers;
    }

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

    //Add by Yordin Alayn 03.02.16
    private CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation getItemNegotiationInformation(CustomerBrokerPurchaseNegotiation customerBrokerSaleNegotiation,NegotiationStatus status)
            throws CantGetNegotiationsWaitingForBrokerException{

        try{

            CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation = null;

            Collection<Clause> negotiationClause = customerBrokerSaleNegotiation.getClauses();
            Map<ClauseType, ClauseInformation> clauses = getNegotiationClause(negotiationClause);
            String customerPublickey= customerBrokerSaleNegotiation.getCustomerPublicKey();
            String customerAlias    = customerBrokerSaleNegotiation.getCustomerPublicKey();
            String brokerPublickey  = customerBrokerSaleNegotiation.getBrokerPublicKey();
            String brokerAlias      = customerBrokerSaleNegotiation.getBrokerPublicKey();
            long lastUpdateDate     = customerBrokerSaleNegotiation.getLastNegotiationUpdateDate();
            String note             = "";
            if(customerBrokerSaleNegotiation.getMemo() != null)
                note = customerBrokerSaleNegotiation.getMemo();


            cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation = new CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation(
                    customerAlias,
                    brokerAlias,
                    status,
                    clauses,
                    note,
                    lastUpdateDate
            );

            return cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation;

        } catch (Exception ex) {
            throw new CantGetNegotiationsWaitingForBrokerException("Cant get negotiations waiting for the broker", ex, "", "");
        }
    }

    //Add by Yordin Alayn 03.02.16
    private Map<ClauseType, ClauseInformation> getNegotiationClause(Collection<Clause> negotiationClause){

        Map<ClauseType, ClauseInformation> clauses = new HashMap<>();
        for (Clause item : negotiationClause) {
            clauses.put(
                    item.getType(),
                    putClause(item.getType(),item.getValue())
            );
        }

        return clauses;
    }

    //Add by Yordin Alayn 03.02.16
    private ClauseInformation putClause(final ClauseType clauseType, final String value) {

        ClauseInformation clauseInformation = new ClauseInformation() {
            @Override
            public UUID getClauseID() { return UUID.randomUUID(); }

            @Override
            public ClauseType getType() { return clauseType; }

            @Override
            public String getValue() { return (value != null) ? value : ""; }

            @Override
            public ClauseStatus getStatus() { return ClauseStatus.DRAFT; }
        };

        return clauseInformation;
    }
}
