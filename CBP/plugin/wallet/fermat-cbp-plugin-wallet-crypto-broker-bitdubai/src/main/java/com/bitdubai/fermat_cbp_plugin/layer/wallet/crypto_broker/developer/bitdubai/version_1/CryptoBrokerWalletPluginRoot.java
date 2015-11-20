package com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
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
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantCreateCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CryptoBrokerWalletNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWallet;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.database.CryptoBrokerWalletDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.database.CryptoBrokerWalletDeveloperDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.exceptions.CantGetCryptoBrokerWalletImplException;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.exceptions.CantInitializeCryptoBrokerWalletDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.CryptoBrokerWalletImpl;
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

/**
 * Created by Yordin Alayn on 19.10.15.
 */
public class CryptoBrokerWalletPluginRoot implements CryptoBrokerWalletManager,
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

    public static final String CRYPTO_BROKER_WALLET_PRIVATE_KEYS_FILE_NAME = "cryptoBrokerWalletPrivateKeyWallet";

    /*CryptoBrokerMAnager Interface Implementation */
    @Override
    public CryptoBrokerWallet createNewCryptoBrokerWallet(String cryptoBroker) throws CantCreateCryptoBrokerWalletException {
        try {
            CryptoBrokerWalletImpl CryptoBrokerWallet = new CryptoBrokerWalletImpl(AsymmetricCryptography.generateECCKeyPair(), cryptoBroker, cryptoBrokerWalletDatabaseDao);
            CryptoBrokerWallet.createWallet();
            return CryptoBrokerWallet;
        } catch (Exception e) {
            throw new CantCreateCryptoBrokerWalletException("CRYPTO BROKER WALLET", e, "CAN'T CREATE NEW CRYPTO BROKER WALLET", "");
        }
    }

    @Override
    public CryptoBrokerWallet getCryptoBrokerWallet(String cryptoBroker) throws CryptoBrokerWalletNotFoundException {
        try {
            CryptoBrokerWalletImpl CryptoBrokerWallet = new CryptoBrokerWalletImpl(AsymmetricCryptography.generateECCKeyPair(), cryptoBroker, cryptoBrokerWalletDatabaseDao);
            CryptoBrokerWallet.getWallet();
            return CryptoBrokerWallet;
        } catch (CantGetCryptoBrokerWalletImplException e) {
            throw new CryptoBrokerWalletNotFoundException("CAN'T GET CRYPTO BROKER IDENTITY", e, "Error getting Crypto Broker Wallet", "");
        } catch (Exception e) {
            throw new CryptoBrokerWalletNotFoundException("CAN'T CREATE NEW CRYPTO BROKER IDENTITY", e, "Error getting current logged in device user", "");
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
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CBP_CRYPTO_BROKER_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
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
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.CryptoBrokerStockTransactionRecordImpl");
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