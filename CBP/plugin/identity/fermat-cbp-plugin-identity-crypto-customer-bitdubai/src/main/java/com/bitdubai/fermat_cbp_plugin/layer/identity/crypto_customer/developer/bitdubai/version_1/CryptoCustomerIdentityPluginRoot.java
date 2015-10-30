package com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_customer.developer.bitdubai.version_1;

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
import com.bitdubai.fermat_cbp_api.layer.cbp_identity.crypto_customer.exceptions.CantCreateCryptoCustomerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.cbp_identity.crypto_customer.exceptions.CantGetCryptoCustomerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.cbp_identity.crypto_customer.interfaces.CryptoCustomerIdentity;
import com.bitdubai.fermat_cbp_api.layer.cbp_identity.crypto_customer.interfaces.CryptoCustomerIdentityManager;
import com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_customer.developer.bitdubai.version_1.database.CryptoCustomerIdentityDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_customer.developer.bitdubai.version_1.database.CryptoCustomerIdentityDeveloperDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_customer.developer.bitdubai.version_1.exceptions.CantInitializeCryptoCustomerIdentityDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_customer.developer.bitdubai.version_1.exceptions.CantListCryptoCustomerIdentitiesException;
import com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerIdentityImpl;
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
public class CryptoCustomerIdentityPluginRoot implements    CryptoCustomerIdentityManager,
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
    private CryptoCustomerIdentityDatabaseDao cryptoCustomerIdentityDatabaseDao;

    private PluginDatabaseSystem pluginDatabaseSystem;

    private static Map<String, LogLevel> newLoggingLevel = new HashMap<>();

    private ErrorManager errorManager;

    private LogManager logManager;

    private DeviceUserManager deviceUserManager;

    private PluginFileSystem pluginFileSystem;

    private UUID pluginId;

    private ServiceStatus serviceStatus = ServiceStatus.CREATED;

    public static final String CRYPTO_CUSTOMER_IDENTITY_PROFILE_IMAGE_FILE_NAME = "cryptoCustomerIdentityProfileImage";

    public static final String CRYPTO_CUSTOMER_IDENTITY_PRIVATE_KEYS_FILE_NAME = "cryptoCustomerIdentityPrivateKey";

     /*CryptoCustomerIdentityManager Interface implementation.*/
     public List<CryptoCustomerIdentity> getAllCryptoCustomerFromCurrentDeviceUser() throws CantGetCryptoCustomerIdentityException {
        try {
            List<CryptoCustomerIdentity> cryptoCustomerIdentityList1 = new ArrayList<CryptoCustomerIdentity>();
            DeviceUser loggedUser = deviceUserManager.getLoggedInDeviceUser();
            cryptoCustomerIdentityList1 = cryptoCustomerIdentityDatabaseDao.getAllCryptoCustomerIdentitiesFromCurrentDeviceUser(loggedUser);
            return cryptoCustomerIdentityList1;
        } catch (CantGetLoggedInDeviceUserException e) {
            throw new CantGetCryptoCustomerIdentityException("CAN'T GET CRYPTO CUSTOMER IDENTITIES", e, "Error get logged user device", "");
        } catch (CantListCryptoCustomerIdentitiesException e) {
            throw new CantGetCryptoCustomerIdentityException("CAN'T GET CRYPTO CUSTOMER IDENTITIES", e, "", "");
        } catch (Exception e) {
            throw new CantGetCryptoCustomerIdentityException("CAN'T GET CRYPTO CUSTOMER IDENTITIES", FermatException.wrapException(e), "", "");
        }
    }

    public CryptoCustomerIdentity createCryptoCustomerIdentity(String alias, byte[] profileImage) throws CantCreateCryptoCustomerIdentityException {
        try {
            DeviceUser loggedUser = deviceUserManager.getLoggedInDeviceUser();
            KeyPair keyPair = AsymmetricCryptography.generateECCKeyPair();
            CryptoCustomerIdentity cryptoCustomer = new CryptoCustomerIdentityImpl(alias, keyPair, profileImage, false);
            cryptoCustomerIdentityDatabaseDao.createNewCryptoCustomerIdentity(cryptoCustomer, keyPair.getPrivateKey(), loggedUser);
            return cryptoCustomer;
        } catch (CantGetLoggedInDeviceUserException e) {
            throw new CantCreateCryptoCustomerIdentityException("CAN'T CREATE NEW CRYPTO CUSTOMER IDENTITY", e, "Error getting current logged in device user", "");
        } catch (CantCreateNewDeveloperException e) {
            throw new CantCreateCryptoCustomerIdentityException("CAN'T CREATE NEW CRYPTO CUSTOMER IDENTITY", e, "Error save user on database", "");
        } catch (Exception e) {
            throw new CantCreateCryptoCustomerIdentityException("CAN'T CREATE NEW CRYPTO CUSTOMER IDENTITY", FermatException.wrapException(e), "", "");
        }

    }

    /*DatabaseManagerForDevelopers Interface implementation.*/
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        CryptoCustomerIdentityDeveloperDatabaseFactory dbFactory = new CryptoCustomerIdentityDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return dbFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        CryptoCustomerIdentityDeveloperDatabaseFactory dbFactory = new CryptoCustomerIdentityDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return dbFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        try {
            CryptoCustomerIdentityDeveloperDatabaseFactory dbFactory = new CryptoCustomerIdentityDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
            dbFactory.initializeDatabase();
            return dbFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (CantInitializeCryptoCustomerIdentityDatabaseException e) {
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CBP_CRYPTO_CUSTOMER_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
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
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_customer.developer.bitdubai.version_1.CryptoCustomerIdentityPluginRoot");
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerIdentityImpl");
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_customer.developer.bitdubai.version_1.database.CryptoCustomerIdentityDatabaseDao");
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_customer.developer.bitdubai.version_1.database.CryptoCustomerIdentityDatabaseFactory");
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_customer.developer.bitdubai.version_1.database.CryptoCustomerIdentityDatabaseConstants");
        return returnedClasses;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {
        for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {
            if (CryptoCustomerIdentityPluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                CryptoCustomerIdentityPluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                CryptoCustomerIdentityPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                CryptoCustomerIdentityPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
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
            this.cryptoCustomerIdentityDatabaseDao = new CryptoCustomerIdentityDatabaseDao(pluginDatabaseSystem, this.pluginFileSystem, this.pluginId);
            this.cryptoCustomerIdentityDatabaseDao.initialize();
        } catch (CantInitializeCryptoCustomerIdentityDatabaseException cantInitializeExtraUserRegistryException) {
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
