package com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_broker.developer.bitdubai.version_1;

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
import com.bitdubai.fermat_api.layer.pip_Identity.developer.exceptions.CantCreateNewDeveloperException;
import com.bitdubai.fermat_cbp_api.layer.cbp_identity.crypto_broker.exceptions.CantCreateCryptoBrokerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.cbp_identity.crypto_broker.exceptions.CantGetCryptoBrokerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.cbp_identity.crypto_broker.interfaces.CryptoBrokerIdentity;
import com.bitdubai.fermat_cbp_api.layer.cbp_identity.crypto_broker.interfaces.CryptoBrokerIdentityManager;
import com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_broker.developer.bitdubai.version_1.database.CryptoBrokerIdentityDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_broker.developer.bitdubai.version_1.database.CryptoBrokerIdentityDeveloperDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_broker.developer.bitdubai.version_1.exceptions.CantInitializeCryptoBrokerIdentityDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_broker.developer.bitdubai.version_1.exceptions.CantListCryptoBrokerIdentitiesException;
import com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_broker.developer.bitdubai.version_1.structure.CryptoBrokerIdentityImpl;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.exceptions.CantGetLoggedInDeviceUserException;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DealsWithDeviceUser;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DeviceUser;
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
 * Created by jorge on 28-09-2015.
 * Modified by Yordin Alayn 10.09.15
 */
public class CryptoBrokerIdentityPluginRoot implements  CryptoBrokerIdentityManager,
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
    private CryptoBrokerIdentityDatabaseDao cryptoBrokerIdentityDatabaseDao;

    private ErrorManager errorManager;

    private LogManager logManager;

    private DeviceUserManager deviceUserManager;

    private PluginFileSystem pluginFileSystem;

    private static Map<String, LogLevel> newLoggingLevel = new HashMap<>();

    private UUID pluginId;

    private ServiceStatus serviceStatus = ServiceStatus.CREATED;

    private PluginDatabaseSystem pluginDatabaseSystem;

    public static final String CRYPTO_BROKER_IDENTITY_PROFILE_IMAGE_FILE_NAME = "cryptoBrokerIdentityProfileImage";

    public static final String CRYPTO_BROKER_IDENTITY_PRIVATE_KEYS_FILE_NAME = "cryptoBrokerIdentityPrivateKey";

    /*CryptoBrokerIdentityManager Interface implementation.*/
    public List<CryptoBrokerIdentity> getAllCryptoBrokersFromCurrentDeviceUser() throws CantGetCryptoBrokerIdentityException {
        try {
            List<CryptoBrokerIdentity> cryptoBrokerIdentityList1 = new ArrayList<CryptoBrokerIdentity>();
            DeviceUser loggedUser = deviceUserManager.getLoggedInDeviceUser();
            cryptoBrokerIdentityList1 = cryptoBrokerIdentityDatabaseDao.getAllCryptoBrokersIdentitiesFromCurrentDeviceUser(loggedUser);
            return cryptoBrokerIdentityList1;
        } catch (CantGetLoggedInDeviceUserException e) {
            throw new CantGetCryptoBrokerIdentityException("CAN'T GET CRYPTO BROKER IDENTITIES", e, "Error get logged user device", "");
        } catch (CantListCryptoBrokerIdentitiesException e) {
            throw new CantGetCryptoBrokerIdentityException("CAN'T GET CRYPTO BROKER IDENTITIES", e, "", "");
        } catch (Exception e) {
            throw new CantGetCryptoBrokerIdentityException("CAN'T GET CRYPTO BROKER IDENTITIES", FermatException.wrapException(e), "", "");
        }
    }

    public CryptoBrokerIdentity createCryptoBrokerIdentity(String alias, byte[] profileImage) throws CantCreateCryptoBrokerIdentityException {
        try {
            DeviceUser loggedUser = deviceUserManager.getLoggedInDeviceUser();
            KeyPair keyPair = AsymmetricCryptography.generateECCKeyPair();
            CryptoBrokerIdentity cryptoBroker = new CryptoBrokerIdentityImpl(alias, keyPair, profileImage, false);
            cryptoBrokerIdentityDatabaseDao.createNewCryptoBrokerIdentity(cryptoBroker, keyPair.getPrivateKey(), loggedUser);
            return cryptoBroker;
        } catch (CantGetLoggedInDeviceUserException e) {
            throw new CantCreateCryptoBrokerIdentityException("CAN'T CREATE NEW CRYPTO BROKER IDENTITY", e, "Error getting current logged in device user", "");
        } catch (CantCreateNewDeveloperException e) {
            throw new CantCreateCryptoBrokerIdentityException("CAN'T CREATE NEW CRYPTO BROKER IDENTITY", e, "Error save user on database", "");
        } catch (Exception e) {
            throw new CantCreateCryptoBrokerIdentityException("CAN'T CREATE NEW CRYPTO BROKER IDENTITY", FermatException.wrapException(e), "", "");
        }

    }

    /*DatabaseManagerForDevelopers Interface implementation.*/
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        CryptoBrokerIdentityDeveloperDatabaseFactory dbFactory = new CryptoBrokerIdentityDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return dbFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        CryptoBrokerIdentityDeveloperDatabaseFactory dbFactory = new CryptoBrokerIdentityDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return dbFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        try {
            CryptoBrokerIdentityDeveloperDatabaseFactory dbFactory = new CryptoBrokerIdentityDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
            dbFactory.initializeDatabase();
            return dbFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (CantInitializeCryptoBrokerIdentityDatabaseException e) {
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_INTRA_WALLET_USER_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
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
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_broker.developer.bitdubai.version_1.CryptoBrokerIdentityPluginRoot");
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_broker.developer.bitdubai.version_1.structure.CryptoBrokerIdentityImpl");
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_broker.developer.bitdubai.version_1.database.CryptoBrokerIdentityDatabaseDao");
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_broker.developer.bitdubai.version_1.database.CryptoBrokerIdentityDatabaseFactory");
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_broker.developer.bitdubai.version_1.database.CryptoBrokerIdentityDatabaseConstants");
        return returnedClasses;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {
        for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {
            if (CryptoBrokerIdentityPluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                CryptoBrokerIdentityPluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                CryptoBrokerIdentityPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                CryptoBrokerIdentityPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
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

    /*PlugIn Interface implementation.*/
    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    /*Service Interface implementation.*/
    @Override
    public void start() throws CantStartPluginException {
        this.serviceStatus = ServiceStatus.STARTED;
        try {
            this.cryptoBrokerIdentityDatabaseDao = new CryptoBrokerIdentityDatabaseDao(pluginDatabaseSystem, this.pluginFileSystem, this.pluginId);
            this.cryptoBrokerIdentityDatabaseDao.initialize();
        } catch (CantInitializeCryptoBrokerIdentityDatabaseException cantInitializeExtraUserRegistryException) {
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
