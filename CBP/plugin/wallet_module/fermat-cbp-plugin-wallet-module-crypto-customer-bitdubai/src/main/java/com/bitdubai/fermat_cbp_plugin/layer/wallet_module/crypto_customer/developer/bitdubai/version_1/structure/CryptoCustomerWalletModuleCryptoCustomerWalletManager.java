package com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.modules.interfaces.FermatSettings;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationType;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationBankAccount;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationLocations;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantGetListCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchase;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchaseManager;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.exceptions.CantGetCryptoCustomerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.interfaces.CryptoCustomerIdentity;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.interfaces.CryptoCustomerIdentityManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantCreateBankAccountPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantCreateLocationPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantDeleteBankAccountPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantGetListLocationsPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantGetListPurchaseNegotiationsException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantUpdateBankAccountPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.user_level_business_transaction.customer_broker_purchase.interfaces.CustomerBrokerPurchaseManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CantGetContractHistoryException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CantGetContractsWaitingForBrokerException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CantGetContractsWaitingForCustomerException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CantGetNegotiationInformationException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CantGetNegotiationsWaitingForBrokerException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CantGetNegotiationsWaitingForCustomerException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CouldNotCancelNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CouldNotConfirmNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ContractBasicInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.IndexInfoSummary;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.MerchandiseExchangeRate;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantGetCryptoBrokerListException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantGetAssociatedCryptoCustomerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantGetCryptoCustomerIdentityListException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantGetCurrentIndexSummaryForCurrenciesOfInterestException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantNewEmptyCryptoCustomerWalletAssociatedSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantNewEmptyCryptoCustomerWalletProviderSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantSaveCryptoCustomerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CouldNotStartNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.BrokerIdentityBusinessInfo;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.CryptoCustomerWalletManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.settings.CryptoCustomerWalletAssociatedSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.settings.CryptoCustomerWalletProviderSetting;
import com.bitdubai.fermat_cer_api.layer.provider.interfaces.CurrencyExchangeRateProviderManager;
import com.bitdubai.fermat_cer_api.layer.search.exceptions.CantGetProviderException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantListWalletsException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletManagerManager;

import java.util.ArrayList;
import java.util.Collection;
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
    private String merchandise = null, typeOfPayment = null, paymentCurrency = null;

    /*
    *Constructor with Parameters
    */
    public CryptoCustomerWalletModuleCryptoCustomerWalletManager(WalletManagerManager walletManagerManager,
                                                                 CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager,
                                                                 UUID pluginId,
                                                                 PluginFileSystem pluginFileSystem,
                                                                 CryptoCustomerIdentityManager cryptoCustomerIdentityManager,
                                                                 CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager)
    {
        this.walletManagerManager                     = walletManagerManager;
        this.customerBrokerPurchaseNegotiationManager = customerBrokerPurchaseNegotiationManager;
        this.pluginId                                 = pluginId;
        this.pluginFileSystem                         = pluginFileSystem;
        this.cryptoCustomerIdentityManager            = cryptoCustomerIdentityManager;
        this.customerBrokerContractPurchaseManager    = customerBrokerContractPurchaseManager;
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
                for (CustomerBrokerContractPurchase customerBrokerContractPurchase : customerBrokerContractPurchaseManager.getCustomerBrokerContractPurchaseForStatus(status))
                {
                    CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation = customerBrokerPurchaseNegotiationManager.getNegotiationsByNegotiationId(UUID.fromString(customerBrokerContractPurchase.getNegotiatiotId()));
                    if (customerBrokerContractPurchase.getStatus().equals(status))
                        for(Clause clause : customerBrokerPurchaseNegotiation.getClauses())
                        {
                            if (clause.getType().getCode() == ClauseType.CUSTOMER_CURRENCY.getCode())
                            {
                                merchandise = clause.getValue();
                            }
                            if (clause.getType().getCode() == ClauseType.CUSTOMER_PAYMENT_METHOD.getCode())
                            {
                                typeOfPayment = clause.getValue();
                            }
                            if (clause.getType().getCode() == ClauseType.BROKER_CURRENCY.getCode())
                            {
                                paymentCurrency = clause.getValue();
                            }
                        }
                        contract = new CryptoBrokerWalletModuleContractBasicInformation(customerBrokerContractPurchase.getPublicKeyCustomer(), merchandise, typeOfPayment, paymentCurrency, status, customerBrokerPurchaseNegotiation);
                    filteredList.add(contract);
                }
                contractsHistory = filteredList;
            }else
            {
                for (CustomerBrokerContractPurchase customerBrokerContractPurchase : customerBrokerContractPurchaseManager.getCustomerBrokerContractPurchaseForStatus(ContractStatus.CANCELLED))
                {
                    CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation = customerBrokerPurchaseNegotiationManager.getNegotiationsByNegotiationId(UUID.fromString(customerBrokerContractPurchase.getNegotiatiotId()));
                    if (customerBrokerContractPurchase.getStatus().equals(status))
                        for(Clause clause : customerBrokerPurchaseNegotiation.getClauses())
                        {
                            if (clause.getType().getCode() == ClauseType.CUSTOMER_CURRENCY.getCode())
                            {
                                merchandise = clause.getValue();
                            }
                            if (clause.getType().getCode() == ClauseType.CUSTOMER_PAYMENT_METHOD.getCode())
                            {
                                typeOfPayment = clause.getValue();
                            }
                            if (clause.getType().getCode() == ClauseType.BROKER_CURRENCY.getCode())
                            {
                                paymentCurrency = clause.getValue();
                            }
                        }
                        contract = new CryptoBrokerWalletModuleContractBasicInformation(customerBrokerContractPurchase.getPublicKeyCustomer(), merchandise, typeOfPayment, paymentCurrency, ContractStatus.CANCELLED, customerBrokerPurchaseNegotiation);
                    filteredList.add(contract);
                }
                for (CustomerBrokerContractPurchase customerBrokerContractPurchase : customerBrokerContractPurchaseManager.getCustomerBrokerContractPurchaseForStatus(ContractStatus.COMPLETED))
                {
                    CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation = customerBrokerPurchaseNegotiationManager.getNegotiationsByNegotiationId(UUID.fromString(customerBrokerContractPurchase.getNegotiatiotId()));
                    if (customerBrokerContractPurchase.getStatus().equals(status))
                        for(Clause clause : customerBrokerPurchaseNegotiation.getClauses())
                        {
                            if (clause.getType().getCode() == ClauseType.CUSTOMER_CURRENCY.getCode())
                            {
                                merchandise = clause.getValue();
                            }
                            if (clause.getType().getCode() == ClauseType.CUSTOMER_PAYMENT_METHOD.getCode())
                            {
                                typeOfPayment = clause.getValue();
                            }
                            if (clause.getType().getCode() == ClauseType.BROKER_CURRENCY.getCode())
                            {
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

            for (CustomerBrokerContractPurchase customerBrokerContractPurchase : customerBrokerContractPurchaseManager.getCustomerBrokerContractPurchaseForStatus(ContractStatus.PENDING_MERCHANDISE))
            {
                CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation = customerBrokerPurchaseNegotiationManager.getNegotiationsByNegotiationId(UUID.fromString(customerBrokerContractPurchase.getNegotiatiotId()));
                for(Clause clause : customerBrokerPurchaseNegotiation.getClauses())
                {
                    if (clause.getType().getCode() == ClauseType.CUSTOMER_CURRENCY.getCode())
                    {
                        merchandise = clause.getValue();
                    }
                    if (clause.getType().getCode() == ClauseType.CUSTOMER_PAYMENT_METHOD.getCode())
                    {
                        typeOfPayment = clause.getValue();
                    }
                    if (clause.getType().getCode() == ClauseType.BROKER_CURRENCY.getCode())
                    {
                        paymentCurrency = clause.getValue();
                    }
                }
                contract = new CryptoBrokerWalletModuleContractBasicInformation(customerBrokerContractPurchase.getPublicKeyCustomer(), merchandise, typeOfPayment, paymentCurrency, ContractStatus.PENDING_MERCHANDISE, customerBrokerPurchaseNegotiation);
                waitingForBroker.add(contract);
            }
            for (CustomerBrokerContractPurchase customerBrokerContractPurchase : customerBrokerContractPurchaseManager.getCustomerBrokerContractPurchaseForStatus(ContractStatus.MERCHANDISE_SUBMIT))
            {
                CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation = customerBrokerPurchaseNegotiationManager.getNegotiationsByNegotiationId(UUID.fromString(customerBrokerContractPurchase.getNegotiatiotId()));
                for(Clause clause : customerBrokerPurchaseNegotiation.getClauses())
                {
                    if (clause.getType().getCode() == ClauseType.CUSTOMER_CURRENCY.getCode())
                    {
                        merchandise = clause.getValue();
                    }
                    if (clause.getType().getCode() == ClauseType.CUSTOMER_PAYMENT_METHOD.getCode())
                    {
                        typeOfPayment = clause.getValue();
                    }
                    if (clause.getType().getCode() == ClauseType.BROKER_CURRENCY.getCode())
                    {
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

            for (CustomerBrokerContractPurchase customerBrokerContractPurchase : customerBrokerContractPurchaseManager.getCustomerBrokerContractPurchaseForStatus(ContractStatus.MERCHANDISE_SUBMIT))
            {
                CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation = customerBrokerPurchaseNegotiationManager.getNegotiationsByNegotiationId(UUID.fromString(customerBrokerContractPurchase.getNegotiatiotId()));
                for(Clause clause : customerBrokerPurchaseNegotiation.getClauses())
                {
                    if (clause.getType().getCode() == ClauseType.CUSTOMER_CURRENCY.getCode())
                    {
                        merchandise = clause.getValue();
                    }
                    if (clause.getType().getCode() == ClauseType.CUSTOMER_PAYMENT_METHOD.getCode())
                    {
                        typeOfPayment = clause.getValue();
                    }
                    if (clause.getType().getCode() == ClauseType.BROKER_CURRENCY.getCode())
                    {
                        paymentCurrency = clause.getValue();
                    }
                }
                contract = new CryptoBrokerWalletModuleContractBasicInformation(customerBrokerContractPurchase.getPublicKeyCustomer(), merchandise, typeOfPayment, paymentCurrency, ContractStatus.MERCHANDISE_SUBMIT, customerBrokerPurchaseNegotiation);
                waitingForBroker.add(contract);
            }
            for (CustomerBrokerContractPurchase customerBrokerContractPurchase : customerBrokerContractPurchaseManager.getCustomerBrokerContractPurchaseForStatus(ContractStatus.PENDING_PAYMENT))
            {
                CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation = customerBrokerPurchaseNegotiationManager.getNegotiationsByNegotiationId(UUID.fromString(customerBrokerContractPurchase.getNegotiatiotId()));
                for(Clause clause : customerBrokerPurchaseNegotiation.getClauses())
                {
                    if (clause.getType().getCode() == ClauseType.CUSTOMER_CURRENCY.getCode())
                    {
                        merchandise = clause.getValue();
                    }
                    if (clause.getType().getCode() == ClauseType.CUSTOMER_PAYMENT_METHOD.getCode())
                    {
                        typeOfPayment = clause.getValue();
                    }
                    if (clause.getType().getCode() == ClauseType.BROKER_CURRENCY.getCode())
                    {
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

    @Override
    public Collection<CustomerBrokerNegotiationInformation> getNegotiationsWaitingForBroker(int max, int offset) throws CantGetNegotiationsWaitingForBrokerException {
        try {
            CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation = null;

            Collection<CustomerBrokerNegotiationInformation> waitingForBroker = new ArrayList<>();
            for (CustomerBrokerPurchaseNegotiation customerBrokerSaleNegotiation : customerBrokerPurchaseNegotiationManager.getNegotiationsByStatus(NegotiationStatus.WAITING_FOR_BROKER))
            {
                cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation = new CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation(customerBrokerSaleNegotiation.getCustomerPublicKey(), "merchandise", "paymentMethod", "paymentCurrency", NegotiationStatus.WAITING_FOR_BROKER);
                waitingForBroker.add(cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation);
            }
            //TODO:Eliminar solo para que se terminen las pantallas
            cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation = new CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation("publicKeyCustomer", "merchandise", "paymentMethod", "paymentCurrency", NegotiationStatus.WAITING_FOR_BROKER);
            cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation = new CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation("publicKeyCustomer", "merchandise", "paymentMethod", "paymentCurrency", NegotiationStatus.WAITING_FOR_BROKER);
            cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation = new CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation("publicKeyCustomer", "merchandise", "paymentMethod", "paymentCurrency", NegotiationStatus.WAITING_FOR_BROKER);
            waitingForBroker.add(cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation);
            return waitingForBroker;

        } catch (Exception ex) {
            throw new CantGetNegotiationsWaitingForBrokerException("Cant get negotiations waiting for the broker", ex, "", "");
        }
    }

    @Override
    public Collection<CustomerBrokerNegotiationInformation> getNegotiationsWaitingForCustomer(int max, int offset) throws CantGetNegotiationsWaitingForCustomerException {
        try {
            CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation = null;

            Collection<CustomerBrokerNegotiationInformation> waitingForBroker = new ArrayList<>();
            for (CustomerBrokerPurchaseNegotiation customerBrokerSaleNegotiation : customerBrokerPurchaseNegotiationManager.getNegotiationsByStatus(NegotiationStatus.WAITING_FOR_CUSTOMER))
            {
                cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation = new CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation(customerBrokerSaleNegotiation.getCustomerPublicKey(), "merchandise", "paymentMethod", "paymentCurrency", NegotiationStatus.WAITING_FOR_BROKER);
                waitingForBroker.add(cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation);
            }
            //TODO:Eliminar solo para que se terminen las pantallas
            cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation = new CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation("publicKeyCustomer", "merchandise", "paymentMethod", "paymentCurrency", NegotiationStatus.WAITING_FOR_BROKER);
            cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation = new CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation("publicKeyCustomer", "merchandise", "paymentMethod", "paymentCurrency", NegotiationStatus.WAITING_FOR_BROKER);
            cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation = new CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation("publicKeyCustomer", "merchandise", "paymentMethod", "paymentCurrency", NegotiationStatus.WAITING_FOR_BROKER);
            waitingForBroker.add(cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation);
            return waitingForBroker;

        } catch (Exception ex) {
            throw new CantGetNegotiationsWaitingForCustomerException("Cant get negotiations waiting for the broker", ex, "", "");
        }
    }

    @Override
    public CustomerBrokerNegotiationInformation getNegotiationInformation(UUID negotiationID) throws CantGetNegotiationInformationException, CantGetListPurchaseNegotiationsException {
        CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation = null;
        cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation = new CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation(customerBrokerPurchaseNegotiationManager.getNegotiationsByNegotiationId(negotiationID).getCustomerPublicKey(), "merchandise", "paymentMethod", "paymentCurrency", customerBrokerPurchaseNegotiationManager.getNegotiationsByNegotiationId(negotiationID).getStatus());
        return cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation;
    }

    /**
     * @param negotiationType
     * @return Collection<NegotiationLocations>
     */
    @Override
    public Collection<NegotiationLocations> getAllLocations(NegotiationType negotiationType) throws CantGetListLocationsPurchaseException {
        Collection<NegotiationLocations> negotiationLocations = null;
        if (negotiationType.getCode() == NegotiationType.PURCHASE.getCode())
        {
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
    public List<CryptoCustomerIdentity> getListOfIdentities() throws CantGetCryptoCustomerIdentityListException, CantGetCryptoCustomerIdentityException {
        List<CryptoCustomerIdentity> cryptoCustomerIdentities = cryptoCustomerIdentityManager.getAllCryptoCustomerFromCurrentDeviceUser();
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
    public Collection<BrokerIdentityBusinessInfo> getListOfConnectedBrokersAndTheirMerchandises(String customerPublicKey) throws CantGetCryptoBrokerListException {
        return getBrokerListTestData(); //recorrer actor NS y buscar dicho actor en el plugin Actor y devolver la mercancia del actor
    }

    @Override
    public Collection<MerchandiseExchangeRate> getListOfBrokerMerchandisesExchangeRate(String brokerPublicKey, FermatEnum target) {
        List<MerchandiseExchangeRate> list = new ArrayList<>(); //PublicKeyWallet y los providers asociados a la wallet y luego buscarlo en el CER

        list.add(new CryptoCustomerWalletModuleMerchandiseExchangeRate(target, CryptoCurrency.LITECOIN, 250));
        list.add(new CryptoCustomerWalletModuleMerchandiseExchangeRate(target, FiatCurrency.COLOMBIAN_PESO, 240000.23));
        list.add(new CryptoCustomerWalletModuleMerchandiseExchangeRate(target, FiatCurrency.BRAZILIAN_REAL, 1.2));
        list.add(new CryptoCustomerWalletModuleMerchandiseExchangeRate(target, CryptoCurrency.CHAVEZCOIN, 366985));

        return list;
    }

    @Override
    public CryptoCustomerIdentity getAssociatedIdentity() throws CantGetAssociatedCryptoCustomerIdentityException {
        return null;
    }

    @Override
    public boolean startNegotiation(UUID customerId, UUID brokerId, Collection<ClauseInformation> clauses) throws CouldNotStartNegotiationException {
        return false; //CustomerBrokerNewManager con la data minima
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
    public Map<String, CurrencyExchangeRateProviderManager> getProviderReferencesFromCurrencyPair(Currency currencyFrom, Currency currencyTo) throws CantGetProviderException {
        return null;
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

            negotiation = new CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation("nelsonalfo", "USD", "Crypto Transfer", "BTC", NegotiationStatus.WAITING_FOR_BROKER);
            openNegotiations.add(negotiation);
            negotiation = new CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation("jorgeegonzalez", "BTC", "Cash in Hand", "USD", NegotiationStatus.WAITING_FOR_BROKER);
            openNegotiations.add(negotiation);
            negotiation = new CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation("neoperol", "USD", "Cash in Hand", "BsF", NegotiationStatus.WAITING_FOR_BROKER);
            openNegotiations.add(negotiation);
            negotiation = new CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation("lnacosta", "BTC", "Crypto Transfer", "LiteCoin", NegotiationStatus.WAITING_FOR_BROKER);
            openNegotiations.add(negotiation);
            negotiation = new CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation("andreaCoronado1", "USD", "Bank Transfer", "BsF", NegotiationStatus.WAITING_FOR_CUSTOMER);
            openNegotiations.add(negotiation);
            negotiation = new CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation("Customer 5", "$ Arg", "Crypto Transfer", "BTC", NegotiationStatus.WAITING_FOR_CUSTOMER);
            openNegotiations.add(negotiation);
            negotiation = new CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation("CustomerXX", "BTC", "Cash Delivery", "USD", NegotiationStatus.WAITING_FOR_CUSTOMER);
            openNegotiations.add(negotiation);
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
    public void setAppPublicKey(String publicKey) {

    }

    @Override
    public int[] getMenuNotifications() {
        return new int[0];
    }
}
