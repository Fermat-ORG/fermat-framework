package com.bitdubai.fermat_ccp_plugin.layer.identity.intra_wallet_user.developer.bitdubai.version_1;


import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_ccp_api.layer.actor.Actor;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantListPendingCryptoAddressRequestsException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.CryptoAddressRequest;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.interfaces.DealsWithIntraUsersNetworkService;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.interfaces.IntraUserManager;

import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.DealsWithWalletManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletManagerManager;
import com.bitdubai.fermat_ccp_api.all_definition.enums.EventType;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_wallet_user.exceptions.CantCreateNewIntraWalletUserException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_wallet_user.exceptions.CantListIntraWalletUsersException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_wallet_user.interfaces.IntraWalletUser;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_wallet_user.interfaces.IntraWalletUserManager;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;


import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;

import com.bitdubai.fermat_ccp_plugin.layer.identity.intra_wallet_user.developer.bitdubai.version_1.database.IntraWalletUserIdentityDao;
import com.bitdubai.fermat_ccp_plugin.layer.identity.intra_wallet_user.developer.bitdubai.version_1.database.IntraWalletUserIdentityDeveloperDatabaseFactory;
import com.bitdubai.fermat_ccp_plugin.layer.identity.intra_wallet_user.developer.bitdubai.version_1.exceptions.CantHandleCryptoAddressRequestEventException;

import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.CryptoAddressesManager;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.DealsWithCryptoAddressesNetworkService;
import com.bitdubai.fermat_ccp_plugin.layer.identity.intra_wallet_user.developer.bitdubai.version_1.event_handlers.CryptoAddressRequestedEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.identity.intra_wallet_user.developer.bitdubai.version_1.exceptions.CantInitializeIntraWalletUserIdentityDatabaseException;
import com.bitdubai.fermat_ccp_plugin.layer.identity.intra_wallet_user.developer.bitdubai.version_1.exceptions.CantListIntraWalletUserIdentitiesException;
import com.bitdubai.fermat_ccp_plugin.layer.identity.intra_wallet_user.developer.bitdubai.version_1.structure.IntraWalletUserIdentity;
import com.bitdubai.fermat_api.layer.pip_Identity.developer.exceptions.CantCreateNewDeveloperException;
import com.bitdubai.fermat_ccp_plugin.layer.identity.intra_wallet_user.developer.bitdubai.version_1.structure.IntraWalletUserIdentityCryptoAddressGenerationService;
import com.bitdubai.fermat_ccp_plugin.layer.identity.intra_wallet_user.developer.bitdubai.version_1.structure.IntraWalletUserIdentityVaultAdministrator;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.DealsWithCryptoAddressBook;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.DealsWithCryptoVault;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
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
 * An Intra-User identity is used to "authenticate" in a wallet or some sub-apps.
 * The management is done through this plugin.
 * The User can create, list or set a new profile picture for an identity.
 * <p/>
 * The "authentication" is managed in each wallet or sub-app in which is used.
 * <p/>
 * Created by loui on 22/02/15.
 * Modified by Leon Acosta - (laion.cj91@gmail.com) on 07/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */


public class IntraWalletUserIdentityPluginRoot extends AbstractPlugin
        implements DatabaseManagerForDevelopers,
                   DealsWithCryptoAddressesNetworkService,//
                   DealsWithCryptoVault,
                   DealsWithCryptoAddressBook,
                   DealsWithDeviceUser,
                   DealsWithErrors,
                   DealsWithEvents,
                   DealsWithPluginDatabaseSystem,
                   DealsWithPluginFileSystem,
                   DealsWithWalletManager,
                   DealsWithIntraUsersNetworkService,//
                   IntraWalletUserManager,
                   LogManagerForDevelopers {


    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER         )
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER         )
    private EventManager eventManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.ANDROID         , addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.ANDROID         , addon = Addons.PLUGIN_FILE_SYSTEM    )
    private PluginFileSystem pluginFileSystem;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.NETWORK_SERVICE, plugin = Plugins.CRYPTO_ADDRESSES   )
    private CryptoAddressesManager cryptoAddressesManager;

    @NeededPluginReference(platform = Platforms.BLOCKCHAINS             , layer = Layers.CRYPTO_MODULE  , plugin = Plugins.CRYPTO_ADDRESS_BOOK)
    private CryptoAddressBookManager cryptoAddressBookManager;

    @NeededPluginReference(platform = Platforms.BLOCKCHAINS             , layer = Layers.CRYPTO_VAULT   , plugin = Plugins.BITCOIN_VAULT      )
    private CryptoVaultManager cryptoVaultManager;

    @NeededPluginReference(platform = Platforms.PLUG_INS_PLATFORM       , layer = Layers.USER           , plugin = Plugins.DEVICE_USER        )
    private DeviceUserManager deviceUserManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.NETWORK_SERVICE, plugin = Plugins.INTRA_WALLET_USER  )
    private IntraUserManager intraActorManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.MIDDLEWARE     , plugin = Plugins.WALLET_MANAGER     )
    private WalletManagerManager walletManagerManager;


    private IntraWalletUserIdentityDao intraWalletUserIdentityDao;

    private List<FermatEventListener> listenersAdded = new ArrayList<>();

    private static Map<String, LogLevel> newLoggingLevel = new HashMap<>();

    public static final String INTRA_WALLET_USERS_PROFILE_IMAGE_FILE_NAME = "intraWalletUserIdentityProfileImage";
    public static final String INTRA_WALLET_USERS_PRIVATE_KEYS_FILE_NAME = "intraWalletUserIdentityPrivateKey";

    public IntraWalletUserIdentityPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }




    /**
     * List Intra Users linked to current Device User
     * <p/>
     * The Intra-Users are linked to the device with the publicKey (it can be acquired by deviceUserManager.getLoggedInDeviceUser()
     * <p/>
     * IntraWalletUserIdentityDao is used to database access (select).
     *
     * @return a list of instances of the class IntraWalletUserIdentity found in structure package of the plugin
     * @throws CantListIntraWalletUsersException
     */
    @Override
    public List<IntraWalletUser> getAllIntraWalletUsersFromCurrentDeviceUser() throws CantListIntraWalletUsersException {

        try {

            List<IntraWalletUser> intraWalletUserList1 = new ArrayList<IntraWalletUser>();


            DeviceUser loggedUser = deviceUserManager.getLoggedInDeviceUser();
            intraWalletUserList1 = intraWalletUserIdentityDao.getAllIntraUserFromCurrentDeviceUser(loggedUser);


            return intraWalletUserList1;

        } catch (CantGetLoggedInDeviceUserException e) {
          throw new CantListIntraWalletUsersException("CAN'T GET INTRA WALLET USER IDENTITIES", e, "Error get logged user device", "");
        } catch (CantListIntraWalletUserIdentitiesException e) {
            throw new CantListIntraWalletUsersException("CAN'T GET INTRA WALLET USER IDENTITIES", e, "", "");
        } catch (Exception e) {
            throw new CantListIntraWalletUsersException("CAN'T GET INTRA WALLET USER IDENTITIES", FermatException.wrapException(e), "", "");
        }
    }

    /**
     * Create an Identity Intra-User
     * <p/>
     * When an User decides to create a new identity, that is achieved throw this method.
     * The new Intra-User is linked to the current device user (it can be acquired by deviceUserManager.getLoggedInDeviceUser()
     * The key-pair is created with a new ECCKeyPair()
     * <p/>
     * The user must have an alias and a profileImage for the identification, if not, give an Exception.
     * <p/>
     * The privateKey must be saved in a file with "$publicKey" like name.
     * The profileImage must be saved in a file with "$publicKey_profileImage" like name.
     * <p/>
     * IntraWalletUserIdentityDao is used to database access (insert).
     *
     * @param alias        the alias that the user choose as intra user identity
     * @param profileImage the profile image to identify this identity
     * @return an instance of the class IntraWalletUserIdentity found in structure package of the plugin
     * @throws CantCreateNewIntraWalletUserException
     */
    @Override
    public IntraWalletUser createNewIntraWalletUser(String alias, byte[] profileImage) throws CantCreateNewIntraWalletUserException {
        try {
            DeviceUser loggedUser = deviceUserManager.getLoggedInDeviceUser();

            ECCKeyPair keyPair = new ECCKeyPair();
           String publicKey = keyPair.getPublicKey();
            String privateKey = keyPair.getPrivateKey();

            intraWalletUserIdentityDao.createNewUser(alias, publicKey, privateKey, loggedUser, profileImage);

            IntraWalletUserIdentity intraWalletUserIdentity = new IntraWalletUserIdentity(alias, publicKey, privateKey, profileImage, pluginFileSystem, pluginId);

            registerIdentities();

            return intraWalletUserIdentity;
        } catch (CantGetLoggedInDeviceUserException e) {
            throw new CantCreateNewIntraWalletUserException("CAN'T CREATE NEW INTRA WALLET USER IDENTITY", e, "Error getting current logged in device user", "");
        } catch (CantCreateNewDeveloperException e) {
            throw new CantCreateNewIntraWalletUserException("CAN'T CREATE NEW INTRA WALLET USER IDENTITY", e, "Error save user on database", "");
        } catch (Exception e) {
            throw new CantCreateNewIntraWalletUserException("CAN'T CREATE NEW INTRA WALLET USER IDENTITY", FermatException.wrapException(e), "", "");
        }

    }

    @Override
   public boolean  hasIntraUserIdentity() throws CantListIntraWalletUsersException{
        try {

            DeviceUser loggedUser = deviceUserManager.getLoggedInDeviceUser();
            if(intraWalletUserIdentityDao.getAllIntraUserFromCurrentDeviceUser(loggedUser).size() > 0)
                return true;
            else
                return false;
        } catch (CantGetLoggedInDeviceUserException e) {
            throw new CantListIntraWalletUsersException("CAN'T GET IF INTRA WALLET USER IDENTITIES  EXISTS", e, "Error get logged user device", "");
        } catch (CantListIntraWalletUserIdentitiesException e) {
            throw new CantListIntraWalletUsersException("CAN'T GET IF WALLET USER IDENTITIES EXISTS", e, "", "");

        } catch (Exception e) {
            throw new CantListIntraWalletUsersException("CAN'T GET IF INTRA WALLET USER IDENTITY EXISTS", FermatException.wrapException(e), "", "");
        }
    }
    /**
     * Service Interface implementation.
     */
    @Override
    public void start() throws CantStartPluginException {

        this.serviceStatus = ServiceStatus.STARTED;
        try {

            this.intraWalletUserIdentityDao = new IntraWalletUserIdentityDao(pluginDatabaseSystem, this.pluginFileSystem, this.pluginId);
            this.intraWalletUserIdentityDao.initializeDatabase();

        } catch (CantInitializeIntraWalletUserIdentityDatabaseException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_INTRA_WALLET_USER_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(e, Plugins.BITDUBAI_CCP_INTRA_WALLET_USER_IDENTITY);
        }

        IntraWalletUserIdentityVaultAdministrator intraWalletUserIdentityVaultAdministrator = new IntraWalletUserIdentityVaultAdministrator(
                cryptoVaultManager
        );

        IntraWalletUserIdentityCryptoAddressGenerationService cryptoAddressGenerationService = new IntraWalletUserIdentityCryptoAddressGenerationService(
                cryptoAddressesManager,
                cryptoAddressBookManager,
                intraWalletUserIdentityVaultAdministrator,
                walletManagerManager
        );

        try {
            registerIdentities();
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_INTRA_WALLET_USER_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
        }

        executePendingAddressExchangeRequests(cryptoAddressGenerationService);

        FermatEventListener cryptoAddressReceivedEventListener = eventManager.getNewListener(EventType.CRYPTO_ADDRESS_REQUESTED);
        cryptoAddressReceivedEventListener.setEventHandler(new CryptoAddressRequestedEventHandler(this, cryptoAddressGenerationService));
        eventManager.addListener(cryptoAddressReceivedEventListener);
        listenersAdded.add(cryptoAddressReceivedEventListener);

    }

    private void executePendingAddressExchangeRequests(IntraWalletUserIdentityCryptoAddressGenerationService cryptoAddressGenerationService) {
        try {
            List<CryptoAddressRequest> cryptoAddressRequestList = cryptoAddressesManager.listPendingCryptoAddressRequests(
                    IntraWalletUserIdentityCryptoAddressGenerationService.actorType
            );

            for (CryptoAddressRequest request : cryptoAddressRequestList) {
                cryptoAddressGenerationService.handleCryptoAddressRequestedEvent(request);
            }

        } catch (CantListPendingCryptoAddressRequestsException | CantHandleCryptoAddressRequestEventException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_INTRA_WALLET_USER_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
    }


    @Override
    public void stop() {

        for (FermatEventListener fermatEventListener : listenersAdded) {
            eventManager.removeListener(fermatEventListener);
        }
        listenersAdded.clear();

        this.serviceStatus = ServiceStatus.STOPPED;
    }

    /**
     * DatabaseManagerForDevelopers Interface implementation.
     */

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        IntraWalletUserIdentityDeveloperDatabaseFactory dbFactory = new IntraWalletUserIdentityDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return dbFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        IntraWalletUserIdentityDeveloperDatabaseFactory dbFactory = new IntraWalletUserIdentityDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return dbFactory.getDatabaseTableList(developerObjectFactory);
    }


    public void registerIdentities(){
        try {
            List<IntraWalletUser> lstIntraWalletUSer = intraWalletUserIdentityDao.getAllIntraUserFromCurrentDeviceUser(deviceUserManager.getLoggedInDeviceUser());
            List<Actor> lstActors = new ArrayList<Actor>();
            for(IntraWalletUser user : lstIntraWalletUSer){
                lstActors.add(intraActorManager.contructIdentity(user.getPublicKey(), user.getAlias(), Actors.INTRA_USER,user.getProfileImage()));
            }
            intraActorManager.registrateActors(lstActors);
        } catch (CantListIntraWalletUserIdentitiesException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_INTRA_WALLET_USER_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (CantGetLoggedInDeviceUserException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_INTRA_WALLET_USER_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {

        try {
            IntraWalletUserIdentityDeveloperDatabaseFactory dbFactory = new IntraWalletUserIdentityDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
            dbFactory.initializeDatabase();
            return dbFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (CantInitializeIntraWalletUserIdentityDatabaseException e) {
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_INTRA_WALLET_USER_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        // If we are here the database could not be opened, so we return an empry list
        return new ArrayList<>();
    }

    /**
     * LogManagerForDevelopers Interface implementation.
     */
    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<>();

        returnedClasses.add("com.bitdubai.fermat_ccp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.IntraWalletUserIdentityPluginRoot");
        returnedClasses.add("com.bitdubai.fermat_ccp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.database.IntraWalletUserIdentityDao");
        returnedClasses.add("com.bitdubai.fermat_ccp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.database.IntraWalletUserIdentityDatabaseConstants");
        returnedClasses.add("com.bitdubai.fermat_ccp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.database.IntraWalletUserIdentityDatabaseFactory");
        returnedClasses.add("com.bitdubai.fermat_ccp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.structure.IntraWalletUserIdentity");

        return returnedClasses;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {
        /**
         * I will check the current values and update the LogLevel in those which is different
         */
        for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {
            /**
             * if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
             */
            if (IntraWalletUserIdentityPluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                IntraWalletUserIdentityPluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                IntraWalletUserIdentityPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                IntraWalletUserIdentityPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }
    }

    @Override
    public void setCryptoAddressesManager(CryptoAddressesManager cryptoAddressesManager) {
        this.cryptoAddressesManager = cryptoAddressesManager;
    }

    @Override
    public void setCryptoAddressBookManager(CryptoAddressBookManager cryptoAddressBookManager) {
        this.cryptoAddressBookManager = cryptoAddressBookManager;
    }

    @Override
    public void setCryptoVaultManager(CryptoVaultManager cryptoVaultManager) {
        this.cryptoVaultManager = cryptoVaultManager;
    }

    /**
     * DealWithDeviceUser Interface implementation.
     */
    @Override
    public void setDeviceUserManager(DeviceUserManager deviceUserManager) {
        this.deviceUserManager = deviceUserManager;
    }

    /**
     * DealWithErrors Interface implementation.
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * DealWithEvents Interface implementation.
     */
    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    /**
     * DealsWithPluginDatabaseSystem interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;

    }

    /**
     * DealWithPluginFileSystem Interface implementation.
     */
    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

    @Override
    public void setWalletManagerManager(WalletManagerManager walletManagerManager) {
        this.walletManagerManager = walletManagerManager;
    }

    @Override
    public void setIntraUserNetworkServiceManager(IntraUserManager intraUserManager) {
        this.intraActorManager = intraUserManager;
    }

}
