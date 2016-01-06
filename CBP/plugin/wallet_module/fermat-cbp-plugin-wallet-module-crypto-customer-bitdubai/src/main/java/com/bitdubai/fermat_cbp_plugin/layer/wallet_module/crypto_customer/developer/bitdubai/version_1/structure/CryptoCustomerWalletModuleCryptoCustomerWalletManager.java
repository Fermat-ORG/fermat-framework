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
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationType;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationBankAccount;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationLocations;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.exceptions.CantGetCryptoCustomerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.interfaces.CryptoCustomerIdentity;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.interfaces.CryptoCustomerIdentityManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantCreateBankAccountPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantCreateLocationPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantDeleteBankAccountPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantGetListLocationsPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantUpdateBankAccountPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiationManager;
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

    /*
    *Constructor with Parameters
    */
    public CryptoCustomerWalletModuleCryptoCustomerWalletManager(WalletManagerManager walletManagerManager,
                                                                 CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager,
                                                                 UUID pluginId,
                                                                 PluginFileSystem pluginFileSystem,
                                                                 CryptoCustomerIdentityManager cryptoCustomerIdentityManager)
    {
        this.walletManagerManager                     = walletManagerManager;
        this.customerBrokerPurchaseNegotiationManager = customerBrokerPurchaseNegotiationManager;
        this.pluginId                                 = pluginId;
        this.pluginFileSystem                         = pluginFileSystem;
        this.cryptoCustomerIdentityManager            = cryptoCustomerIdentityManager;
    }

    private List<ContractBasicInformation> contractsHistory;
    private List<ContractBasicInformation> openContracts;
    private List<CustomerBrokerNegotiationInformation> openNegotiations;
    private List<BrokerIdentityBusinessInfo> connectedBrokers;

    @Override
    public CustomerBrokerNegotiationInformation addClause(CustomerBrokerNegotiationInformation negotiation, ClauseInformation clause) {
        return null;
    }

    @Override
    public CustomerBrokerNegotiationInformation changeClause(CustomerBrokerNegotiationInformation negotiation, ClauseInformation clause) {
        return null;
    }

    @Override
    public Collection<ContractBasicInformation> getContractsHistory(ContractStatus status, int max, int offset) throws CantGetContractHistoryException {
        try {
            List<ContractBasicInformation> contractsHistory = getContractHistoryTestData();

            if (status != null) {
                List<ContractBasicInformation> filteredList = new ArrayList<>();
                for (ContractBasicInformation item : contractsHistory) {
                    if (item.getStatus().equals(status))
                        filteredList.add(item);
                }
                contractsHistory = filteredList;
            }

            return contractsHistory;

        } catch (Exception ex) {
            throw new CantGetContractHistoryException(ex);
        }
    }

    @Override
    public Collection<ContractBasicInformation> getContractsWaitingForBroker(int max, int offset) throws CantGetContractsWaitingForBrokerException {
        try {
            Collection<ContractBasicInformation> openContracts = getOpenContractsTestData();

            List<ContractBasicInformation> waitingForBroker = new ArrayList<>();
            for (ContractBasicInformation item : openContracts) {
                //TODO: Changed for Manuel Perez on 30/11/2015 for compiling.
                if (item.getStatus().equals(ContractTransactionStatus.PENDING_CONFIRMATION))
                    waitingForBroker.add(item);
            }

            return waitingForBroker;

        } catch (Exception ex) {
            throw new CantGetContractsWaitingForBrokerException("Cant get contracts waiting for the broker", ex);
        }
    }

    @Override
    public Collection<ContractBasicInformation> getContractsWaitingForCustomer(int max, int offset) throws CantGetContractsWaitingForCustomerException {
        try {
            Collection<ContractBasicInformation> openContracts = getOpenContractsTestData();

            List<ContractBasicInformation> waitingForCustomer = new ArrayList<>();
            for (ContractBasicInformation item : openContracts) {
                if (item.getStatus().equals(ContractStatus.PENDING_PAYMENT))
                    waitingForCustomer.add(item);
            }

            return waitingForCustomer;

        } catch (Exception ex) {
            throw new CantGetContractsWaitingForCustomerException("Cant get contracts waiting for the customers", ex);
        }
    }

    @Override
    public Collection<CustomerBrokerNegotiationInformation> getNegotiationsWaitingForBroker(int max, int offset) throws CantGetNegotiationsWaitingForBrokerException {
        try {
            Collection<CustomerBrokerNegotiationInformation> openNegotiations = getOpenNegotiationsTestData();

            List<CustomerBrokerNegotiationInformation> waitingForBroker = new ArrayList<>();
            for (CustomerBrokerNegotiationInformation item : openNegotiations) {
                if (item.getStatus().equals(NegotiationStatus.WAITING_FOR_BROKER))
                    waitingForBroker.add(item);
            }

            return waitingForBroker;

        } catch (Exception ex) {
            throw new CantGetNegotiationsWaitingForBrokerException(ex);
        }
    }

    @Override
    public Collection<CustomerBrokerNegotiationInformation> getNegotiationsWaitingForCustomer(int max, int offset) throws CantGetNegotiationsWaitingForCustomerException {
        try {
            Collection<CustomerBrokerNegotiationInformation> openNegotiations = getOpenNegotiationsTestData();

            List<CustomerBrokerNegotiationInformation> waitingForCustomer = new ArrayList<>();
            for (CustomerBrokerNegotiationInformation item : openNegotiations) {
                if (item.getStatus().equals(NegotiationStatus.WAITING_FOR_CUSTOMER))
                    waitingForCustomer.add(item);
            }

            return waitingForCustomer;

        } catch (Exception ex) {
            throw new CantGetNegotiationsWaitingForCustomerException(ex);
        }
    }

    @Override
    public CustomerBrokerNegotiationInformation getNegotiationInformation(UUID negotiationID) throws CantGetNegotiationInformationException {
        // TODO
        return null;
    }

    /**
     * @param negotiationType
     * @return Collection<NegotiationLocations>
     */
    @Override
    public Collection<NegotiationLocations> getAllLocations(NegotiationType negotiationType) throws CantGetListLocationsPurchaseException {
        //TODO: Implementar
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
    public CustomerBrokerNegotiationInformation confirmNegotiation(CustomerBrokerNegotiationInformation negotiation) throws CouldNotConfirmNegotiationException {
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
        return getBrokerListTestData();
    }

    @Override
    public Collection<MerchandiseExchangeRate> getListOfBrokerMerchandisesExchangeRate(String brokerPublicKey, FermatEnum target) {
        List<MerchandiseExchangeRate> list = new ArrayList<>();

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
        return false;
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

            contract = new CryptoBrokerWalletModuleContractBasicInformation("adrianasupernova", "USD", "Crypto Transfer", "BTC", ContractStatus.PAUSED);
            openContracts.add(contract);
            contract = new CryptoBrokerWalletModuleContractBasicInformation("adrianasupernova", "USD", "Crypto Transfer", "BTC", ContractStatus.PAUSED);
            openContracts.add(contract);
            contract = new CryptoBrokerWalletModuleContractBasicInformation("adrianasupernova", "USD", "Crypto Transfer", "BTC", ContractStatus.PAUSED);
            openContracts.add(contract);
            contract = new CryptoBrokerWalletModuleContractBasicInformation("adrianasupernova", "USD", "Crypto Transfer", "BTC", ContractStatus.PAUSED);
            openContracts.add(contract);
            contract = new CryptoBrokerWalletModuleContractBasicInformation("yalayn", "BTC", "Bank Transfer", "USD", ContractStatus.PENDING_PAYMENT);
            openContracts.add(contract);
            contract = new CryptoBrokerWalletModuleContractBasicInformation("vzlangel", "BsF", "Cash Delivery", "BsF", ContractStatus.PENDING_PAYMENT);
            openContracts.add(contract);
            contract = new CryptoBrokerWalletModuleContractBasicInformation("vzlangel", "BsF", "Cash Delivery", "BsF", ContractStatus.PENDING_PAYMENT);
            openContracts.add(contract);
            contract = new CryptoBrokerWalletModuleContractBasicInformation("vzlangel", "BsF", "Cash Delivery", "BsF", ContractStatus.PENDING_PAYMENT);
            openContracts.add(contract);
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
            ContractBasicInformation contract;
            contractsHistory = new ArrayList<>();

            contract = new CryptoBrokerWalletModuleContractBasicInformation("adrianasupernova", "USD", "Crypto Transfer", "BTC", ContractStatus.COMPLETED);
            contractsHistory.add(contract);
            contract = new CryptoBrokerWalletModuleContractBasicInformation("nelsoanlfo", "BTC", "Bank Transfer", "Arg $", ContractStatus.COMPLETED);
            contractsHistory.add(contract);
            contract = new CryptoBrokerWalletModuleContractBasicInformation("neoperol", "USD", "Cash in Hand", "BsF", ContractStatus.COMPLETED);
            contractsHistory.add(contract);
            contract = new CryptoBrokerWalletModuleContractBasicInformation("nairovene", "USD", "Cash Delivery", "BsF", ContractStatus.CANCELLED);
            contractsHistory.add(contract);
            contract = new CryptoBrokerWalletModuleContractBasicInformation("Luis Pineda", "USD", "Crypto Transfer", "BTC", ContractStatus.PENDING_PAYMENT);
            contractsHistory.add(contract);
            contract = new CryptoBrokerWalletModuleContractBasicInformation("Carlos Ruiz", "USD", "Bank Transfer", "Col $", ContractStatus.CANCELLED);
            contractsHistory.add(contract);
            contract = new CryptoBrokerWalletModuleContractBasicInformation("josePres", "USD", "Crypto Transfer", "BTC", ContractStatus.COMPLETED);
            contractsHistory.add(contract);
            contract = new CryptoBrokerWalletModuleContractBasicInformation("nairo300", "USD", "Crypto Transfer", "BTC", ContractStatus.COMPLETED);
            contractsHistory.add(contract);
            contract = new CryptoBrokerWalletModuleContractBasicInformation("dbz_brokers", "USD", "Crypto Transfer", "BTC", ContractStatus.CANCELLED);
            contractsHistory.add(contract);
            contract = new CryptoBrokerWalletModuleContractBasicInformation("Mirian Margarita Noguera", "USD", "Crypto Transfer", "BTC", ContractStatus.CANCELLED);
            contractsHistory.add(contract);
            contract = new CryptoBrokerWalletModuleContractBasicInformation("adrianasupernova", "USD", "Crypto Transfer", "BTC", ContractStatus.PENDING_PAYMENT);
            contractsHistory.add(contract);
            contract = new CryptoBrokerWalletModuleContractBasicInformation("nelsoanlfo", "BTC", "Bank Transfer", "Arg $", ContractStatus.CANCELLED);
            contractsHistory.add(contract);
            contract = new CryptoBrokerWalletModuleContractBasicInformation("neoperol", "USD", "Cash in Hand", "BsF", ContractStatus.COMPLETED);
            contractsHistory.add(contract);
            contract = new CryptoBrokerWalletModuleContractBasicInformation("nairovene", "USD", "Cash Delivery", "BsF", ContractStatus.COMPLETED);
            contractsHistory.add(contract);
            contract = new CryptoBrokerWalletModuleContractBasicInformation("Luis Pineda", "USD", "Crypto Transfer", "BTC", ContractStatus.PENDING_PAYMENT);
            contractsHistory.add(contract);
            contract = new CryptoBrokerWalletModuleContractBasicInformation("Carlos Ruiz", "USD", "Crypto Transfer", "BTC", ContractStatus.CANCELLED);
            contractsHistory.add(contract);
            contract = new CryptoBrokerWalletModuleContractBasicInformation("josePres", "USD", "Crypto Transfer", "BTC", ContractStatus.COMPLETED);
            contractsHistory.add(contract);
            contract = new CryptoBrokerWalletModuleContractBasicInformation("nairo300", "USD", "Crypto Transfer", "BTC", ContractStatus.COMPLETED);
            contractsHistory.add(contract);
            contract = new CryptoBrokerWalletModuleContractBasicInformation("dbz_brokers", "USD", "Crypto Transfer", "BTC", ContractStatus.PENDING_PAYMENT);
            contractsHistory.add(contract);
            contract = new CryptoBrokerWalletModuleContractBasicInformation("Mirian Margarita Noguera", "USD", "Crypto Transfer", "BTC", ContractStatus.COMPLETED);
            contractsHistory.add(contract);
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
