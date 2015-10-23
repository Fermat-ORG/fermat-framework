package com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1;

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
import com.bitdubai.fermat_cbp_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_cbp_api.layer.cbp_identity.crypto_broker.interfaces.CryptoBrokerIdentity;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.exceptions.CantTransactionCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.interfaces.CryptoBroker;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.interfaces.CryptoBrokerTransaction;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.interfaces.CryptoBrokerTransactionRecord;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.interfaces.CryptoBrokerTransactionSummary;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.database.CryptoBrokerWalletDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.database.CryptoBrokerWalletDeveloperDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.exceptions.CantAddCreditException;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.exceptions.CantAddDebitException;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.exceptions.CantInitializeCryptoBrokerWalletDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.exceptions.CantListCryptoBrokerWalletTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.CryptoBrokerWalletImpl;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.exceptions.CantGetLoggedInDeviceUserException;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DealsWithDeviceUser;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DeviceUser;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DeviceUserManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 19.10.15.
 */
public class CryptoBrokerWalletPluginRoot implements    CryptoBroker,
                                                        CryptoBrokerTransaction,
                                                        DatabaseManagerForDevelopers,
                                                        DealsWithPluginDatabaseSystem,
                                                        LogManagerForDevelopers,
                                                        DealsWithErrors,
                                                        DealsWithLogger,
                                                        DealsWithDeviceUser,
                                                        DealsWithPluginFileSystem,
                                                        Plugin,
                                                        Service{

    /*Variables.*/
    private CryptoBrokerWalletDatabaseDao cryptoBrokerWalletDatabaseDao;

    private ErrorManager errorManager;

    private LogManager logManager;

    private DeviceUserManager deviceUserManager;

    private PluginFileSystem pluginFileSystem;

    private static Map<String, LogLevel> newLoggingLevel = new HashMap<>();

    private UUID pluginId;

    private ServiceStatus serviceStatus = ServiceStatus.CREATED;

    private PluginDatabaseSystem pluginDatabaseSystem;

    public static final String CRYPTO_BROKER_PRIVATE_KEYS_WALLET_FILE_NAME = "cryptoBrokerWalletPrivateKeyWallet";

    public static final String CRYPTO_BROKER_PRIVATE_KEYS_BROKER_FILE_NAME = "cryptoBrokerWalletPrivateKeyBroker";

    public static final String CRYPTO_BROKER_PRIVATE_KEYS_CUSTOMER_FILE_NAME = "cryptoBrokerWalletPrivateKeyCustomer";

    /*CryptoBroker Interface Implementation*/
    public double getBookBalance() throws CantTransactionCryptoBrokerException{
        try{
            return cryptoBrokerWalletDatabaseDao.getCalculateBookBalance();
        } catch (CantCalculateBalanceException e) {
            throw new CantTransactionCryptoBrokerException("CAN'T GET CRYPTO BROKER WALLET BOOKED BALANCE", e, "", "");
        } catch (Exception e) {
            throw new CantTransactionCryptoBrokerException("CAN'T GET CRYPTO BROKER WALLET BOOKED BALANCE", FermatException.wrapException(e), "", "");
        }
    }

    public double getAvailableBalance() throws CantTransactionCryptoBrokerException{
        try{
            return cryptoBrokerWalletDatabaseDao.getCalculateAvailableBalance();
        } catch (CantCalculateBalanceException e) {
            throw new CantTransactionCryptoBrokerException("CAN'T GET CRYPTO BROKER WALLET AVAILABLE BALANCE", e, "", "");
        } catch (Exception e) {
            throw new CantTransactionCryptoBrokerException("CAN'T GET CRYPTO BROKER WALLET AVAILABLE BALANCE", FermatException.wrapException(e), "", "");
        }
    }

    public List<CryptoBrokerTransactionRecord> getTransactions(BalanceType balanceType, int max, int offset)throws CantTransactionCryptoBrokerException{
        try {
            List<CryptoBrokerTransactionRecord> cryptoBrokerTransactionList = new ArrayList<CryptoBrokerTransactionRecord>();
            DeviceUser loggedUser = deviceUserManager.getLoggedInDeviceUser();
            cryptoBrokerTransactionList = cryptoBrokerWalletDatabaseDao.getTransactionsList(loggedUser);
            return cryptoBrokerTransactionList;
        } catch (CantGetLoggedInDeviceUserException e) {
            throw new CantTransactionCryptoBrokerException("CAN'T GET CRYPTO BROKER WALLET TRANSACTION", e, "Error get logged user device", "");
        } catch (CantListCryptoBrokerWalletTransactionException e) {
            throw new CantTransactionCryptoBrokerException("CAN'T GET CRYPTO BROKER WALLET TRANSACTION", e, "", "");
        } catch (Exception e) {
            throw new CantTransactionCryptoBrokerException("CAN'T GET CRYPTO BROKER WALLET TRANSACTION", FermatException.wrapException(e), "", "");
        }
    }

    public CryptoBrokerTransactionSummary getBrokerTransactionSummary(BalanceType balanceType) throws CantTransactionCryptoBrokerException{
        return null;
    }

    /*CryptoBrokerTransaction Interface Implementation*/
    public CryptoBrokerTransactionRecord debit(String publickeyWalle, String publickeyBroker, String publicKeyCustomer, BalanceType balanceType, CurrencyType currencyType, float amount, String memo) throws CantRegisterDebitException{
        try {
            UUID transactionId              = UUID.randomUUID();
            KeyPair keyPairWallet           = AsymmetricCryptography.createKeyPair(publickeyWalle);
            KeyPair keyPairBroker           = AsymmetricCryptography.createKeyPair(publickeyBroker);
            KeyPair keyPairCustomer         = AsymmetricCryptography.createKeyPair(publicKeyCustomer);
            TransactionType transactionType = TransactionType.DEBIT;
            float availableAmount           = balanceType.equals(BalanceType.AVAILABLE) ? amount : 0L;
            float bookAmount                = balanceType.equals(BalanceType.BOOK) ? amount : 0L;
            float runningBookBalance        = cryptoBrokerWalletDatabaseDao.calculateBookRunningBalanceByAsset(-bookAmount, keyPairWallet.getPrivateKey());
            float runningAvailableBalance   = cryptoBrokerWalletDatabaseDao.calculateAvailableRunningBalanceByAsset(-availableAmount, keyPairWallet.getPrivateKey());
//            long timeStamp = Timestamp(long time);
            long timeStamp = 0;
            CryptoBrokerTransactionRecord cryptoBrokerTransaction = new CryptoBrokerWalletImpl(
                    transactionId,
                    keyPairWallet,
                    keyPairBroker,
                    keyPairCustomer,
                    balanceType,
                    transactionType,
                    currencyType,
                    amount,
                    runningBookBalance,
                    runningAvailableBalance,
                    timeStamp,
                    memo
            );
            cryptoBrokerWalletDatabaseDao.addDebit(cryptoBrokerTransaction, balanceType, keyPairWallet.getPrivateKey());
            return cryptoBrokerTransaction;
        } catch (CantAddDebitException e) {
            throw new CantRegisterDebitException("CAN'T ADD CRYPTO BROKER WALLET TRANSACTION DEBIT", e, "", "");
        } catch (Exception e) {
            throw new CantRegisterDebitException("CAN'T ADD CRYPTO BROKER WALLET TRANSACTION DEBIT", FermatException.wrapException(e), "", "");
        }
    }

    public CryptoBrokerTransactionRecord credit(String publickeyWalle, String publickeyBroker, String publicKeyCustomer, BalanceType balanceType, CurrencyType currencyType, float amount, String memo) throws CantRegisterCreditException{
        try {
            UUID transactionId              = UUID.randomUUID();
            KeyPair keyPairWallet           = AsymmetricCryptography.createKeyPair(publickeyWalle);
            KeyPair keyPairBroker           = AsymmetricCryptography.createKeyPair(publickeyBroker);
            KeyPair keyPairCustomer         = AsymmetricCryptography.createKeyPair(publicKeyCustomer);
            TransactionType transactionType = TransactionType.DEBIT;
            float availableAmount           = balanceType.equals(BalanceType.AVAILABLE) ? amount : 0L;
            float bookAmount                = balanceType.equals(BalanceType.BOOK) ? amount : 0L;
            float runningBookBalance        = cryptoBrokerWalletDatabaseDao.calculateBookRunningBalanceByAsset(-bookAmount, keyPairWallet.getPrivateKey());
            float runningAvailableBalance   = cryptoBrokerWalletDatabaseDao.calculateAvailableRunningBalanceByAsset(-availableAmount, keyPairWallet.getPrivateKey());
//            long timeStamp = Timestamp(long time);
            long timeStamp = 0;
            CryptoBrokerTransactionRecord cryptoBrokerTransaction = new CryptoBrokerWalletImpl(
                    transactionId,
                    keyPairWallet,
                    keyPairBroker,
                    keyPairCustomer,
                    balanceType,
                    transactionType,
                    currencyType,
                    amount,
                    runningBookBalance,
                    runningAvailableBalance,
                    timeStamp,
                    memo
            );
            cryptoBrokerWalletDatabaseDao.addCredit(cryptoBrokerTransaction, balanceType, keyPairWallet.getPrivateKey());
            return cryptoBrokerTransaction;
        } catch (CantAddCreditException e) {
            throw new CantRegisterCreditException("CAN'T ADD CRYPTO BROKER WALLET TRANSACTION DEBIT", e, "", "");
        } catch (Exception e) {
            throw new CantRegisterCreditException("CAN'T ADD CRYPTO BROKER WALLET TRANSACTION DEBIT", FermatException.wrapException(e), "", "");
        }
    }

    /*DatabaseManagerForDevelopers Interface Implementation.*/
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        CryptoBrokerWalletDeveloperDatabaseFactory dbFactory = new CryptoBrokerWalletDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return dbFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        CryptoBrokerWalletDeveloperDatabaseFactory dbFactory = new CryptoBrokerWalletDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return dbFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        try {
            CryptoBrokerWalletDeveloperDatabaseFactory dbFactory = new CryptoBrokerWalletDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
            dbFactory.initializeDatabase();
            return dbFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (CantInitializeCryptoBrokerWalletDatabaseException e) {
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_INTRA_WALLET_USER_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        return new ArrayList<>();
    }

    /*DealsWithPluginDatabaseSystem interface Implementation.*/
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;

    }

    /*LogManagerForDevelopers Interface Implementation.*/
    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.CryptoBrokerWalletPluginRoot");
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.CryptoBrokerWalletImpl");
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.database.CryptoBrokerWalletDatabaseDao");
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.database.CryptoBrokerWalletDatabaseFactory");
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.database.CryptoBrokerWalletDatabaseConstants");
        return returnedClasses;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {
        for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {
            if (CryptoBrokerWalletPluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                CryptoBrokerWalletPluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                CryptoBrokerWalletPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                CryptoBrokerWalletPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }
    }

    /*DealWithErrors Interface Implementation.*/
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /*DealsWithLogger Interface Implementation.*/
    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    /*DealsWithDeviceUser Interface Implementation.*/
    @Override
    public void setDeviceUserManager(DeviceUserManager deviceUserManager) {
        this.deviceUserManager = deviceUserManager;
    }

    /*DealWithPluginFileSystem Interface Implementation.*/
    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

    /*PlugIn Interface Implementation.*/
    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    /*Service Interface Implementation.*/
    @Override
    public void start() throws CantStartPluginException {
        this.serviceStatus = ServiceStatus.STARTED;
        try {
            this.cryptoBrokerWalletDatabaseDao = new CryptoBrokerWalletDatabaseDao(pluginDatabaseSystem, this.pluginFileSystem, this.pluginId);
            this.cryptoBrokerWalletDatabaseDao.initialize();
        } catch (CantInitializeCryptoBrokerWalletDatabaseException cantInitializeExtraUserRegistryException) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DESIGNER_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantInitializeExtraUserRegistryException);
            throw new CantStartPluginException(cantInitializeExtraUserRegistryException, Plugins.BITDUBAI_DESIGNER_IDENTITY);
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

    @Override
    public ServiceStatus getStatus() {
        return serviceStatus;
    }
}