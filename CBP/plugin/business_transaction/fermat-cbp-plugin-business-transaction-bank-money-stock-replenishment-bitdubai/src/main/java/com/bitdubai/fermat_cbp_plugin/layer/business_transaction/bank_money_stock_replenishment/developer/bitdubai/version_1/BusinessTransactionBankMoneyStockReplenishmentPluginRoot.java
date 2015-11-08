package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.bank_money_stock_replenishment.developer.bitdubai.version_1;


import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.interfaces.KeyPair;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BankCurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BankOperationType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BusinessTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.bank_money_stock_replenishment.exceptions.CantCreateBankMoneyStockReplenishmentException;
import com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.bank_money_stock_replenishment.exceptions.CantGetBankMoneyStockReplenishmentException;
import com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.bank_money_stock_replenishment.exceptions.CantUpdateStatusBankMoneyStockReplenishmentException;
import com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.bank_money_stock_replenishment.interfaces.BankMoneyStockReplenishment;
import com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.bank_money_stock_replenishment.interfaces.BankMoneyStockReplenishmentManager;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.bank_money_stock_replenishment.developer.bitdubai.version_1.database.BankMoneyStockReplenishmentBusinessTransactionDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.bank_money_stock_replenishment.developer.bitdubai.version_1.database.BankMoneyStockReplenishmentBusinessTransactionDeveloperDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.bank_money_stock_replenishment.developer.bitdubai.version_1.exceptions.CantInitializeBankMoneyStockReplenishmentBusinessTransactionDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.bank_money_stock_replenishment.developer.bitdubai.version_1.exceptions.CantInsertRecordBankMoneyStockReplenishmentBusinessTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.bank_money_stock_replenishment.developer.bitdubai.version_1.exceptions.CantListBankMoneyStockReplenishmentBusinessTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.bank_money_stock_replenishment.developer.bitdubai.version_1.exceptions.CantUpdateStatusBankMoneyStockReplenishmentBusinessTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.bank_money_stock_replenishment.developer.bitdubai.version_1.structure.BankMoneyStockReplenishmentBusinessTransactionImpl;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DealsWithDeviceUser;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DeviceUserManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Created by Yordin Alayn on 16.09.15.
 */

public class BusinessTransactionBankMoneyStockReplenishmentPluginRoot implements
        BankMoneyStockReplenishmentManager,
        DatabaseManagerForDevelopers,
        DealsWithPluginDatabaseSystem,
        LogManagerForDevelopers,
        DealsWithErrors,
        DealsWithLogger,
        DealsWithDeviceUser,
        DealsWithPluginFileSystem,
        Plugin,
        Service {

    private BankMoneyStockReplenishmentBusinessTransactionDatabaseDao bankMoneyStockReplenishmentBusinessTransactionDatabaseDao;

    private ErrorManager errorManager;

    private LogManager logManager;

    private DeviceUserManager deviceUserManager;

    private PluginFileSystem pluginFileSystem;

    private static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    private UUID pluginId;

    private ServiceStatus serviceStatus = ServiceStatus.CREATED;

    private PluginDatabaseSystem pluginDatabaseSystem;

    /*BankMoneyStockReplenishmentManager Interface Implementation*/
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

    /*DatabaseManagerForDevelopers Interface implementation.*/
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

    /*DealsWithPluginDatabaseSystem interface implementation.*/
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;

    }

    /*LogManagerForDevelopers Interface implementation.*/
    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.business_transaction.bank_money_stock_replenishment.developer.bitdubai.version_1.BusinessTransactionBankMoneyStockReplenishmentPluginRoot");
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
                if (BusinessTransactionBankMoneyStockReplenishmentPluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                    BusinessTransactionBankMoneyStockReplenishmentPluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                    BusinessTransactionBankMoneyStockReplenishmentPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                } else {
                    BusinessTransactionBankMoneyStockReplenishmentPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                }
            }
        } catch (Exception exception) {
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CBP_CRYPTO_BROKER_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
        }
    }

    public static LogLevel getLogLevelByClass(String className) {
        try {
            String[] correctedClass = className.split((Pattern.quote("$")));
            return BusinessTransactionBankMoneyStockReplenishmentPluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e) {
            System.err.println("CantGetLogLevelByClass: " + e.getMessage());
            return DEFAULT_LOG_LEVEL;
        }
    }

    /*DealWithErrors Interface implementation.*/
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /*DealsWithLogger Interface implementation.*/
    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    /*DealsWithDeviceUser Interface implementation.*/
    @Override
    public void setDeviceUserManager(DeviceUserManager deviceUserManager) {
        this.deviceUserManager = deviceUserManager;
    }

    /*DealWithPluginFileSystem Interface implementation.*/
    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

    @Override
    public void setId(UUID uuid) {
        this.pluginId = uuid;
    }

    /*Service Interface implementation.*/
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

    /*PlugIn Interface implementation.*/
    @Override
    public ServiceStatus getStatus() {
        return serviceStatus;
    }
}