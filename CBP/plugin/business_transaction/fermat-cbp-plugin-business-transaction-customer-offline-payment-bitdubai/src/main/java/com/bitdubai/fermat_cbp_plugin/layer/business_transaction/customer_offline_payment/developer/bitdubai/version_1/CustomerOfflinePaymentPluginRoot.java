package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1;


import com.bitdubai.fermat_api.Plugin;

import java.util.UUID;

/**
 * Created by Yordin Alayn on 16.09.15.
 */

public class CustomerOfflinePaymentPluginRoot implements
        /*BankMoneyStockReplenishmentManager,
        DatabaseManagerForDevelopers,
        DealsWithPluginDatabaseSystem,
        LogManagerForDevelopers,
        DealsWithErrors,
        DealsWithLogger,
        DealsWithDeviceUser,
        DealsWithPluginFileSystem,*/
        Plugin/*,
        Service*/ {
    @Override
    public void setId(UUID pluginId) {

    }/*

    private BankMoneyStockReplenishmentBusinessTransactionDatabaseDao bankMoneyStockReplenishmentBusinessTransactionDatabaseDao;

    private ErrorManager errorManager;

    private LogManager logManager;

    private DeviceUserManager deviceUserManager;

    private PluginFileSystem pluginFileSystem;

    private static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    private UUID pluginId;

    private ServiceStatus serviceStatus = ServiceStatus.CREATED;

    private PluginDatabaseSystem pluginDatabaseSystem;

    *//*BankMoneyStockReplenishmentManager Interface Implementation*//*
    public List<BankMoneyStockReplenishment> getAllBankMoneyStockReplenishmentFromCurrentDeviceUser() throws CantGetBankMoneyStockReplenishmentException {
        try {
            List<BankMoneyStockReplenishment> bankMoneyStockReplenishmentList = new ArrayList<BankMoneyStockReplenishment>();
            bankMoneyStockReplenishmentList = bankMoneyStockReplenishmentBusinessTransactionDatabaseDao.getAllBankMoneyStockReplenishmentListFromCurrentDeviceUser();
            return bankMoneyStockReplenishmentList;
        } catch (CantListBankMoneyStockReplenishmentBusinessTransactionException e) {
            throw new CantGetBankMoneyStockReplenishmentException("CAN'T GET BANK MONEY REPLENISHMENT BUSINESS TRANSACTION", e, "", "");
        } catch (Exception e) {
            throw new CantGetBankMoneyStockReplenishmentException("CAN'T GET BANK MONEY REPLENISHMENT BUSINESS TRANSACTION", FermatException.wrapException(e), "", "");
        }
    }

    public BankMoneyStockReplenishment createBankMoneyStockReplenishment(
             final String publicKeyBroker
            ,final CurrencyType merchandiseCurrency
            ,final float merchandiseAmount
            ,final UUID executionTransactionId
            ,final BankCurrencyType bankCurrencyType
            ,final BankOperationType bankOperationType
    ) throws CantCreateBankMoneyStockReplenishmentException {
        try {
            UUID transactionId = UUID.randomUUID();
            BusinessTransactionStatus transactionStatus = BusinessTransactionStatus.PENDING_PAYMENT;
            KeyPair keyPairBroker = AsymmetricCryptography.createKeyPair(publicKeyBroker);
            BankMoneyStockReplenishment bankMoneyStockReplenishment = new BankMoneyStockReplenishmentBusinessTransactionImpl(
                transactionId,
                keyPairBroker,
                merchandiseCurrency,
                merchandiseAmount,
                executionTransactionId,
                bankCurrencyType,
                bankOperationType,
                transactionStatus
            );
            bankMoneyStockReplenishmentBusinessTransactionDatabaseDao.createNewBankMoneyStockReplenishment(bankMoneyStockReplenishment);
            return bankMoneyStockReplenishment;
        } catch (CantInsertRecordBankMoneyStockReplenishmentBusinessTransactionException e) {
            throw new CantCreateBankMoneyStockReplenishmentException("CAN'T CREATE NEW BANK MONEY REPLENISHMENT BUSINESS TRANSACTION", e, "Error save user on database", "");
        } catch (Exception e) {
            throw new CantCreateBankMoneyStockReplenishmentException("CAN'T CREATE NEW BANK MONEY REPLENISHMENT BUSINESS TRANSACTION", FermatException.wrapException(e), "", "");
        }
    }

    public void updateStatusBankMoneyStockReplenishment(final UUID transactionId, final BusinessTransactionStatus transactionStatus) throws CantUpdateStatusBankMoneyStockReplenishmentException {
        try {
            bankMoneyStockReplenishmentBusinessTransactionDatabaseDao.updateStatusBankMoneyStockReplenishmentTransaction(transactionId, transactionStatus);
        } catch (CantUpdateStatusBankMoneyStockReplenishmentBusinessTransactionException e) {
            throw new CantUpdateStatusBankMoneyStockReplenishmentException("CAN'T UPDATE STATUS BANK MONEY REPLENISHMENT BUSINESS TRANSACTION", e, "Error save user on database", "");
        } catch (Exception e) {
            throw new CantUpdateStatusBankMoneyStockReplenishmentException("CAN'T UPDATE STATUS BANK MONEY REPLENISHMENT BUSINESS TRANSACTION", FermatException.wrapException(e), "", "");
        }
    }

    *//*DatabaseManagerForDevelopers Interface implementation.*//*
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        BankMoneyStockReplenishmentBusinessTransactionDeveloperDatabaseFactory dbFactory = new BankMoneyStockReplenishmentBusinessTransactionDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return dbFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        BankMoneyStockReplenishmentBusinessTransactionDeveloperDatabaseFactory dbFactory = new BankMoneyStockReplenishmentBusinessTransactionDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return dbFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        try {
            BankMoneyStockReplenishmentBusinessTransactionDeveloperDatabaseFactory dbFactory = new BankMoneyStockReplenishmentBusinessTransactionDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
            dbFactory.initializeDatabase();
            return dbFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (CantInitializeBankMoneyStockReplenishmentBusinessTransactionDatabaseException e) {
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_INTRA_USER_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        return new ArrayList<>();
    }

    *//*DealsWithPluginDatabaseSystem interface implementation.*//*
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;

    }

    *//*LogManagerForDevelopers Interface implementation.*//*
    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.business_transaction.bank_money_stock_replenishment.developer.bitdubai.version_1.CustomerOfflinePaymentPluginRoot");
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.business_transaction.bank_money_stock_replenishment.developer.bitdubai.version_1.structure.CryptoBrokerIdentityImpl");
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.business_transaction.bank_money_stock_replenishment.developer.bitdubai.version_1.database.BankMoneyStockReplenishmentBusinessTransactionDatabaseDao");
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.business_transaction.bank_money_stock_replenishment.developer.bitdubai.version_1.database.BankMoneyStockReplenishmentBusinessTransactionDatabaseFactory");
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.business_transaction.bank_money_stock_replenishment.developer.bitdubai.version_1.database.BankMoneyStockReplenishmentBusinessTransactionDatabaseConstants");
        return returnedClasses;
    }

    //BITDUBAI_CBP_CRYPTO_BROKER_IDENTITY TEMPORAL. AGREGAR BUSINESS TRANSACTION.
    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {
        try {
            for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {
                if (com.bitdubai.fermat_cbp_plugin.layer.business_transaction.bank_money_stock_replenishment.developer.bitdubai.version_1.CustomerOfflinePaymentPluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                    com.bitdubai.fermat_cbp_plugin.layer.business_transaction.bank_money_stock_replenishment.developer.bitdubai.version_1.CustomerOfflinePaymentPluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                    com.bitdubai.fermat_cbp_plugin.layer.business_transaction.bank_money_stock_replenishment.developer.bitdubai.version_1.CustomerOfflinePaymentPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                } else {
                    com.bitdubai.fermat_cbp_plugin.layer.business_transaction.bank_money_stock_replenishment.developer.bitdubai.version_1.CustomerOfflinePaymentPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                }
            }
        } catch (Exception exception) {
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CBP_CRYPTO_BROKER_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
        }
    }

    public static LogLevel getLogLevelByClass(String className) {
        try {
            String[] correctedClass = className.split((Pattern.quote("$")));
            return com.bitdubai.fermat_cbp_plugin.layer.business_transaction.bank_money_stock_replenishment.developer.bitdubai.version_1.CustomerOfflinePaymentPluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e) {
            System.err.println("CantGetLogLevelByClass: " + e.getMessage());
            return DEFAULT_LOG_LEVEL;
        }
    }

    *//*DealWithErrors Interface implementation.*//*
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    *//*DealsWithLogger Interface implementation.*//*
    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    *//*DealsWithDeviceUser Interface implementation.*//*
    @Override
    public void setDeviceUserManager(DeviceUserManager deviceUserManager) {
        this.deviceUserManager = deviceUserManager;
    }

    *//*DealWithPluginFileSystem Interface implementation.*//*
    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

    @Override
    public void setId(UUID uuid) {
        this.pluginId = uuid;
    }

    *//*Service Interface implementation.*//*
    @Override
    public void start() throws CantStartPluginException {
        try {
            this.serviceStatus = ServiceStatus.STARTED;
        } catch (Exception exception) {
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    @Override
    public void pause() {
        this.serviceStatus = ServiceStatus.PAUSED;
    }

    @Override
    public void resume() {
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    *//*PlugIn Interface implementation.*//*
    @Override
    public ServiceStatus getStatus() {
        return serviceStatus;
    }*/
}