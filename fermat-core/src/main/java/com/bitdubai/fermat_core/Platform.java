package com.bitdubai.fermat_core;


import com.bitdubai.fermat_api.*;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;


import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DealWithDatabaseManagers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DealsWithLogManagers;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.PlatformComponents;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.event.DealWithEventMonitor;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.DealsWithBitcoinWallet;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.DealsWithWalletContacts;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactsManager;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.DealsWithWalletFactory;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryManager;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.interfaces.DealsWithWalletManager;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.interfaces.WalletManagerManager;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.interfaces.DealsWithWalletPublisher;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.interfaces.WalletPublisherManager;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.interfaces.DealsWithWalletStore;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.interfaces.WalletStoreManager;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_resources.DealsWithWalletResources;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_resources.WalletResourcesManager;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_statistics.interfaces.DealsWithWalletStatisticsNetworkService;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_statistics.interfaces.WalletStatisticsManager;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.DealsWithWalletStoreNetworkService;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.DealsWithNicheWalletTypeCryptoWallet;
import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_extrauser.DealsWithOutgoingExtraUser;
import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_extrauser.OutgoingExtraUserManager;
import com.bitdubai.fermat_api.layer.osa_android.DataBaseSystemOs;
import com.bitdubai.fermat_api.layer.osa_android.FileSystemOs;
import com.bitdubai.fermat_api.layer.osa_android.LocationSystemOs;
import com.bitdubai.fermat_api.layer.osa_android.LoggerSystemOs;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPlatformFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPlatformDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationLayerManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.DealsWithCommunicationLayerManager;
import com.bitdubai.fermat_pip_api.layer.pip_actor.developer.DealsWithToolManager;
import com.bitdubai.fermat_pip_api.layer.pip_actor.developer.ToolManager;
import com.bitdubai.fermat_pip_api.layer.pip_identity.developer.interfaces.DealsWithDeveloperIdentity;
import com.bitdubai.fermat_pip_api.layer.pip_identity.developer.interfaces.DeveloperIdentityManager;
import com.bitdubai.fermat_pip_api.layer.pip_module.developer.interfaces.DealsWithDeveloperModule;
import com.bitdubai.fermat_pip_api.layer.pip_module.developer.interfaces.DeveloperModuleManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPlatformExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.EventManager;

import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.pip_user.device_user.DealsWithDeviceUsers;
import com.bitdubai.fermat_api.layer.pip_user.device_user.DeviceUserManager;
import com.bitdubai.fermat_api.layer.dmp_actor.extra_user.DealsWithExtraUsers;
import com.bitdubai.fermat_api.layer.dmp_actor.extra_user.ExtraUserManager;
import com.bitdubai.fermat_core.layer.cry_crypto_router.CryptoRouterLayer;
import com.bitdubai.fermat_core.layer.dmp_basic_wallet.BasicWalletLayer;
import com.bitdubai.fermat_core.layer.dmp_transaction.TransactionLayer;
import com.bitdubai.fermat_core.layer.dmp_niche_wallet_type.NicheWalletTypeLayer;
import com.bitdubai.fermat_core.layer.pip_actor.ActorLayer;
import com.bitdubai.fermat_core.layer.pip_identity.IdentityLayer;
import com.bitdubai.fermat_core.layer.pip_platform_service.PlatformServiceLayer;

import com.bitdubai.fermat_core.layer.all_definition.DefinitionLayer;
import com.bitdubai.fermat_core.layer.osa_android.OsLayer;
import com.bitdubai.fermat_core.layer.pip_hardware.HardwareLayer;
import com.bitdubai.fermat_core.layer.pip_user.UserLayer;
import com.bitdubai.fermat_core.layer.pip_license.LicenseLayer;
import com.bitdubai.fermat_core.layer.dmp_world.WorldLayer;
import com.bitdubai.fermat_core.layer.cry_crypto_network.CryptoNetworkLayer;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.ActorAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.DealsWithActorAddressBook;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.interfaces.DealsWithWalletAddressBook;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.interfaces.WalletAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_network.CryptoNetworks;
import com.bitdubai.fermat_core.layer.cry_cypto_vault.CryptoVaultLayer;
import com.bitdubai.fermat_core.layer.cry_crypto_module.CryptoLayer;
import com.bitdubai.fermat_core.layer.p2p_communication.CommunicationLayer;
import com.bitdubai.fermat_core.layer.dmp_network_service.NetworkServiceLayer;
import com.bitdubai.fermat_core.layer.dmp_middleware.MiddlewareLayer;
import com.bitdubai.fermat_core.layer.dmp_module.ModuleLayer;
import com.bitdubai.fermat_core.layer.dmp_agent.AgentLayer;
import com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.BitcoinCryptoNetworkManager;
import com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.DealsWithBitcoinCryptoNetwork;
import com.bitdubai.fermat_cry_api.layer.crypto_router.incoming_crypto.DealsWithIncomingCrypto;
import com.bitdubai.fermat_cry_api.layer.crypto_router.incoming_crypto.IncomingCryptoManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.DealsWithCryptoVault;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ciencias on 20.01.15.
 */
public class Platform  {

    PlatformLayer mDefinitionLayer = new DefinitionLayer();
    PlatformLayer mPlatformServiceLayer = new PlatformServiceLayer();
    PlatformLayer mOsLayer = new OsLayer();
    PlatformLayer mHardwareLayer = new HardwareLayer();
    PlatformLayer mUserLayer = new UserLayer();
    PlatformLayer mLicenseLayer = new LicenseLayer();
    PlatformLayer mWorldLayer = new WorldLayer();
    PlatformLayer mCryptoNetworkLayer = new CryptoNetworkLayer();
    PlatformLayer mCryptoVaultLayer = new CryptoVaultLayer();
    PlatformLayer mCryptoLayer = new CryptoLayer();
    PlatformLayer mCryptoRouterLayer = new CryptoRouterLayer();
    PlatformLayer mCommunicationLayer = new CommunicationLayer();
    PlatformLayer mNetworkServiceLayer = new NetworkServiceLayer();
    PlatformLayer mTransactionLayer = new TransactionLayer();
    PlatformLayer mMiddlewareLayer = new MiddlewareLayer();
    PlatformLayer mModuleLayer = new ModuleLayer();
    PlatformLayer mAgentLayer = new AgentLayer();
    PlatformLayer mBasicWalletLayer = new BasicWalletLayer();
    PlatformLayer mNicheWalletTypeLayer = new NicheWalletTypeLayer();
    PlatformLayer mActorLayer = new ActorLayer();
    PlatformLayer mIdentityLayer = new IdentityLayer();
    PlatformLayer mModuleLayerPip = new com.bitdubai.fermat_core.layer.pip_module.ModuleLayer();



    private Map<Plugins, Plugin> dealsWithDatabaseManagersPlugins = new ConcurrentHashMap<Plugins, Plugin>();
    private Map<Plugins, Plugin> dealsWithLogManagersPlugins = new ConcurrentHashMap<Plugins, Plugin>();

    private Map<Addons, Addon> dealsWithDatabaseManagersAddons = new ConcurrentHashMap<Addons, Addon>();
    private Map<Addons, Addon> dealsWithLogManagersAddons = new ConcurrentHashMap<Addons, Addon>();


    public PlatformLayer getDefinitionLayer() {
        return mDefinitionLayer;
    }

    public PlatformLayer getEventLayer() {
        return mPlatformServiceLayer;
    }

    public PlatformLayer getOsLayer() {
        return mOsLayer;
    }

    public PlatformLayer getHardwareLayer(){
        return mHardwareLayer;
    }

    public PlatformLayer getUserLayer() {
        return mUserLayer;
    }

    public PlatformLayer getLicesnseLayer() {
        return mLicenseLayer;
    }

    public PlatformLayer getWorldLayer() {
        return mWorldLayer;
    }

    public PlatformLayer getCryptoNetworkLayer() {
        return mCryptoNetworkLayer;
    }

    public PlatformLayer getCryptoVault() {
        return mCryptoVaultLayer;
    }

    public PlatformLayer getCrypto() {
        return mCryptoLayer;
    }

    public PlatformLayer getCryptoRouter() { return mCryptoRouterLayer; }

    public PlatformLayer getCommunicationLayer() {
        return mCommunicationLayer;
    }

    public PlatformLayer getNetworkServiceLayer() {
        return mNetworkServiceLayer;
    }

    public PlatformLayer getMiddlewareayer() {
        return mMiddlewareLayer;
    }

    public PlatformLayer getModuleLayer() {
        return mModuleLayer;
    }

    public PlatformLayer getAgentLayer() {
        return mAgentLayer;
    }

    public PlatformLayer getBasicWalletLayer(){
        return mBasicWalletLayer;
    }

    public PlatformLayer getmNicheWalletTypeLayer() {
        return mNicheWalletTypeLayer;
    }

    public PlatformLayer getActorLayer() {
        return mActorLayer;
    }

    public PlatformLayer getIdentityLayer() {
        return mIdentityLayer;
    }

    public PlatformLayer getmModuleLayerPip() {
        return mModuleLayerPip;
    }


    public Map<Plugins, Plugin> getDealsWithDatabaseManagersPlugins() {
        return dealsWithDatabaseManagersPlugins;
    }

    public Map<Plugins, Plugin> getDealsWithLogManagersPlugins() {
        return dealsWithLogManagersPlugins;
    }

    public Map<Addons, Addon> getDealsWithDatabaseManagersAddons() {
        return dealsWithDatabaseManagersAddons;
    }

    public Map<Addons, Addon> getDealsWithLogManagersAddons() {
        return dealsWithLogManagersAddons;
    }

    PlatformEventMonitor eventMonitor;

    PluginsIdentityManager pluginsIdentityManager;


    CorePlatformContext corePlatformContext;

    Object osContext;

    FileSystemOs fileSystemOs;
    DataBaseSystemOs databaseSystemOs;
    LocationSystemOs locationSystemOs;
    LoggerSystemOs loggerSystemOs;


    public CorePlatformContext getCorePlatformContext() {
        return corePlatformContext;

        // Luis: TODO: Este metodo debe ser removido y lo que se debe devolver es un context con referencias a los plugins que la interfaz grafica puede acceder, no a todos los que existen como esta ahora mismo.
    }


    public Platform () {

        /**
         * The event monitor is intended to handle exceptions on listeners, in order to take appropiate action.
         */

        eventMonitor = new PlatformEventMonitor();

        corePlatformContext = new CorePlatformContext();
    }



    /**
     * Somebody is starting the com.bitdubai.platform. The com.bitdubai.platform is portable. That somebody is OS dependent and has access to
     * the OS. I have to transport a reference to that somebody to the OS subsystem in other to allow it to access
     * the OS through this reference.
     */
    public void setOsContext(Object osContext) {
        this.osContext = osContext;
    }

    /**
     * An unresolved bug in either Android or Gradle does not allow us to create the os object on a library outside the
     * main module. While this situation persists, we will create it inside the wallet package and receive it throw this
     * method.
     */


    public void setFileSystemOs (FileSystemOs fileSystemOs) {
        this.fileSystemOs = fileSystemOs;
    }

    public void setDataBaseSystemOs (DataBaseSystemOs databaseSystemOs) {
        this.databaseSystemOs = databaseSystemOs;
    }

    public void setLocationSystemOs(LocationSystemOs locationSystemOs) {
        this.locationSystemOs  = locationSystemOs;
    }

    public void setLoggerSystemOs(LoggerSystemOs loggerSystemOs) {
        this.loggerSystemOs  = loggerSystemOs;
    }



    public void start() throws CantStartPlatformException, CantReportCriticalStartingProblemException {

        /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
         * ------------------------------------------------------------------------------------------------------------*
         * Layers initialization                                                                                       *
         * ------------------------------------------------------------------------------------------------------------*
         * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

        /**
         * Here I will be starting all the platforms layers. It is required that none of them fails. That does not mean
         * that a layer will have at least one service to offer. It depends on each layer. If one believes its lack of
         * services prevent the whole platform to start, then it will throw an exception that will effectively prevent 
         * the platform to start.
         */

        try {

            mDefinitionLayer.start();
            mPlatformServiceLayer.start();
        } catch (CantStartLayerException cantStartLayerException) {
            /**
             * At this point the platform not only can not be started but also the problem can not be reported. That is 
             * the reason why I am going to throw a special exception in order to alert the situation to whoever is running
             * the GUI. In this way it can alert the end user of what is going on and provide them with some information.
             * * * *
             */
            throw new CantReportCriticalStartingProblemException();
        }


        /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
         * ------------------------------------------------------------------------------------------------------------*
         * Critical Addons initialization                                                                              *
         * ------------------------------------------------------------------------------------------------------------*
         * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

        /**
         * -----------------------------
         * Addon Error Manager
         * -----------------------------
         */
        Service errorManager = (Service) ((PlatformServiceLayer) mPlatformServiceLayer).getErrorManager();
        corePlatformContext.addAddon((Addon) errorManager, Addons.ERROR_MANAGER);
        ((DealsWithPlatformDatabaseSystem) errorManager).setPlatformDatabaseSystem(databaseSystemOs.getPlatformDatabaseSystem());

        try {
            errorManager.start();
        } catch (CantStartPluginException cantStartPluginException) {
            System.err.println("CantStartPluginException: " + cantStartPluginException.getMessage());
            cantStartPluginException.printStackTrace();
            throw new CantStartPlatformException();
        }

        /**
         * -----------------------------
         * Addon Event Manager
         * -----------------------------
         */
        Service eventManager = (Service) ((PlatformServiceLayer) mPlatformServiceLayer).getEventManager();
        corePlatformContext.addAddon((Addon) eventManager, Addons.EVENT_MANAGER);

        /**
         * I will give the Event Monitor to the Event Manager, in order to allow it to monitor listeners exceptions.
         */

        ((DealWithEventMonitor) eventManager).setEventMonitor(eventMonitor);


        try {
            // mOsLayer.start(); // Due to an Android bug is not possible to handle this here.
            mHardwareLayer.start();
            mUserLayer.start();
            mLicenseLayer.start();
            mWorldLayer.start();
            mCryptoNetworkLayer.start();
            mCryptoVaultLayer.start();
            mCryptoLayer.start();
            mCryptoRouterLayer.start();
            mCommunicationLayer.start();
            mNetworkServiceLayer.start();
            mMiddlewareLayer.start();
            mModuleLayer.start();
            mAgentLayer.start();
            mTransactionLayer.start();
            mBasicWalletLayer.start();
            mNicheWalletTypeLayer.start();
            mActorLayer.start();
            mIdentityLayer.start();
            mModuleLayerPip.start();
        } catch (CantStartLayerException cantStartLayerException) {
            ((ErrorManager) errorManager).reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ALL_THE_PLATFORM, cantStartLayerException);
            throw new CantStartPlatformException();
        }

        /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
         * ------------------------------------------------------------------------------------------------------------*
         * Addons initialization                                                                                       *
         * ------------------------------------------------------------------------------------------------------------*
         * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

        /**
         * -----------------------------
         * Addon Remote Device Manager
         * -----------------------------
         *
         * I will give the Remote Device Manager access to the File System so it can load and save user information from
         * persistent media.
         */
        Service remoteDevice = (Service) ((HardwareLayer) mHardwareLayer).getRemoteDeviceManager();

        ((DealsWithPlatformFileSystem) remoteDevice).setPlatformFileSystem(fileSystemOs.getPlatformFileSystem());
        ((DealsWithEvents) remoteDevice).setEventManager((EventManager) eventManager);

        corePlatformContext.addAddon((Addon) remoteDevice, Addons.REMOTE_DEVICE);

        /**
         * -----------------------------
         * Addon Local Device Manager
         * -----------------------------
         *
         * I will give the Local Device Manager access to the File System so it can load and save user information from
         * persistent media.
         */

        Service localDevice = (Service) ((HardwareLayer) mHardwareLayer).getLocalDeviceManager();

        ((DealsWithPlatformFileSystem) localDevice).setPlatformFileSystem(fileSystemOs.getPlatformFileSystem());
        ((DealsWithEvents) localDevice).setEventManager((EventManager) eventManager);

        corePlatformContext.addAddon((Addon) localDevice, Addons.LOCAL_DEVICE);

        /**
         * -----------------------------
         * Addon User Manager
         * -----------------------------
         *
         * I will give the User Manager access to the File System so it can load and save user information from
         * persistent media.
         */
        Service deviceUser = (Service) ((UserLayer) mUserLayer).getDeviceUser();

        ((DealsWithPlatformFileSystem) deviceUser).setPlatformFileSystem(fileSystemOs.getPlatformFileSystem());
        ((DealsWithEvents) deviceUser).setEventManager((EventManager) eventManager);

        corePlatformContext.addAddon((Addon) deviceUser, Addons.DEVICE_USER);

        /**
         *-------------------------------
         * Addon Intra User
         * -----------------------------
         */
        Service intraUser = (Service) ((UserLayer) mUserLayer).getIntraUser();

        ((DealsWithPlatformFileSystem) intraUser).setPlatformFileSystem(fileSystemOs.getPlatformFileSystem());
        ((DealsWithEvents) intraUser).setEventManager((EventManager) eventManager);

        corePlatformContext.addAddon((Addon) intraUser, Addons.INTRA_USER);

        /**
         * -----------------------------------------------------------------------------------------------------------
         * Plugins initialization
         * -----------------------------------------------------------------------------------------------------------
         *
         * I will initialize the Plugin Identity Manager, the one who assigns identities to each plug in.
         */

        try {
            pluginsIdentityManager = new PluginsIdentityManager(fileSystemOs.getPlatformFileSystem());
        } catch (CantInitializePluginsManagerException cantInitializePluginsManagerException) {
            System.err.println("CantInitializePluginsManager: " + cantInitializePluginsManagerException.getMessage());
            cantInitializePluginsManagerException.printStackTrace();
            throw new CantStartPlatformException();
        }

        /**
         * -----------------------------
         * Plugin Blockchain Info World
         * -----------------------------
         */
       // Plugin blockchainInfoWorld = ((WorldLayer)  mWorldLayer).getBlockchainInfo();
       // setPluginReferencesAndStart(blockchainInfoWorld, Plugins.BITDUBAI_BLOCKCHAIN_INFO_WORLD);

        /**
         * -----------------------------
         * Plugin Shape Shift World
         * -----------------------------
         */
       // Plugin shapeShiftWorld = ((WorldLayer)  mWorldLayer).getShapeShift();
       // setPluginReferencesAndStart(shapeShiftWorld, Plugins.BITDUBAI_SHAPE_SHIFT_WORLD);

        /**
         * -----------------------------
         * Plugin Coinapult World
         * -----------------------------
         */
       // Plugin coinapultWorld = ((WorldLayer)  mWorldLayer).getCoinapult();
       // setPluginReferencesAndStart(coinapultWorld, Plugins.BITDUBAI_COINAPULT_WORLD);

        /**
         * -----------------------------
         * Plugin Coinbase World
         * -----------------------------
         */
       // Plugin coinbaseWorld = ((WorldLayer)  mWorldLayer).getCoinbase();
       // setPluginReferencesAndStart(coinbaseWorld, Plugins.BITDUBAI_COINBASE_WORLD);

        /**
         * -----------------------------
         * Plugin Location World
         * -----------------------------
         */
       // Plugin locationWorld = ((WorldLayer)  mWorldLayer).getLocation();
       // setPluginReferencesAndStart(locationWorld, Plugins.BITDUBAI_LOCATION_WORLD);

        /**
         * -----------------------------
         * Plugin Crypto Index World
         * -----------------------------
         */
       // Plugin cryptoIndexWorld = ((WorldLayer)  mWorldLayer).getCryptoIndex();
       // setPluginReferencesAndStart(cryptoIndexWorld, Plugins.BITDUBAI_CRYPTO_INDEX);

        /**
         * -----------------------------
         * Plugin Actor Developer
         * -----------------------------
         */
        Plugin actorDeveloper = ((ActorLayer) mActorLayer).getmActorDeveloper();
        setPluginReferencesAndStart(actorDeveloper, Plugins.BITDUBAI_ACTOR_DEVELOPER);

        /**
         * -----------------------------
         * Plugin Developer Identity
         * -----------------------------
         */
      //  Plugin developerIdentity = ((IdentityLayer) mIdentityLayer).getMdeveloperIdentity();
     //   setPluginReferencesAndStart(developerIdentity, Plugins.BITDUBAI_DEVELOPER_IDENTITY);

        /**
         * -----------------------------
         * Plugin Developer Module
         * -----------------------------
         */
        Plugin developerModule = ((com.bitdubai.fermat_core.layer.pip_module.ModuleLayer) mModuleLayerPip).getmDeveloperModule();
        setPluginReferencesAndStart(developerModule, Plugins.BITDUBAI_DEVELOPER_MODULE);

        /**
         *---------------------------------
         * Plugin Extra User
         * -------------------------------
         */
        Plugin extraUser = ((ActorLayer) mActorLayer).getmActorExtraUser();
        setPluginReferencesAndStart(extraUser, Plugins.BITDUBAI_USER_EXTRA_USER);

        /**
         * -----------------------------
         * Plugin Bitcoin Crypto Network
         * -----------------------------
         */
        Plugin cryptoNetwork = ((CryptoNetworkLayer) mCryptoNetworkLayer).getCryptoNetwork(CryptoNetworks.BITCOIN);
        setPluginReferencesAndStart(cryptoNetwork, Plugins.BITDUBAI_BITCOIN_CRYPTO_NETWORK);

        /**
         * ------------------------------------*
         *  Plugin Wallet Address Book Crypto  *
         * ------------------------------------*
         */
        Plugin walletAddressBookCrypto = ((CryptoLayer) mCryptoLayer).getmWalletAddressBook();
        setPluginReferencesAndStart(walletAddressBookCrypto, Plugins.BITDUBAI_WALLET_ADDRESS_BOOK_CRYPTO);

        /**
         * ------------------------------
         *  Plugin Actor Address Book Crypto
         * ------------------------------
         */
        Plugin actorAddressBookCrypto = ((CryptoLayer) mCryptoLayer).getmUserAddressBook();
        setPluginReferencesAndStart(actorAddressBookCrypto, Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO);

        /**
         * -----------------------------
         * Plugin Cloud Server Communication
         * -----------------------------
         */
        Plugin cloudServerCommunication = ((CommunicationLayer) mCommunicationLayer).getCloudServerPlugin();
        setPluginReferencesAndStart(cloudServerCommunication, Plugins.BITDUBAI_CLOUD_SERVER_COMMUNICATION);

        /**
         * -----------------------------
         * Plugin Cloud Communication
         * -----------------------------
         */
        Plugin cloudCommunication = ((CommunicationLayer) mCommunicationLayer).getCloudPlugin();
        setPluginReferencesAndStart(cloudCommunication, Plugins.BITDUBAI_CLOUD_CHANNEL);

        /**
         * -----------------------------
         * Plugin Bank Notes Network Service
         * -----------------------------
         */
        Plugin bankNotesNetworkService = ((NetworkServiceLayer) mNetworkServiceLayer).getBankNotesPlugin();
        setPluginReferencesAndStart(bankNotesNetworkService, Plugins.BITDUBAI_BANK_NOTES_NETWORK_SERVICE);

        /**
         * -----------------------------
         * Plugin Wallet Resources Network Service
         * -----------------------------
         */
        Plugin walletResourcesNetworkService = ((NetworkServiceLayer) mNetworkServiceLayer).getWalletResources();
        setPluginReferencesAndStart(walletResourcesNetworkService, Plugins.BITDUBAI_WALLET_RESOURCES_NETWORK_SERVICE);

        /**
         * -----------------------------
         * Plugin Wallet Community Network Service
         * -----------------------------
         */
        Plugin walletCommunityNetworkService = ((NetworkServiceLayer) mNetworkServiceLayer).getWalletCommunity();
        setPluginReferencesAndStart(walletCommunityNetworkService, Plugins.BITDUBAI_WALLET_COMMUNITY_NETWORK_SERVICE);

        /**
         * -----------------------------
         * Plugin Wallet Store Network Service
         * -----------------------------
         */
        Plugin walletStoreNetworkService = ((NetworkServiceLayer) mNetworkServiceLayer).getWalletStore();
        setPluginReferencesAndStart(walletStoreNetworkService, Plugins.BITDUBAI_WALLET_STORE_NETWORK_SERVICE);

        /**
         * -----------------------------
         * Plugin Wallet Statistics Network Service
         * -----------------------------
         */
        Plugin walletStatisticsNetworkService = ((NetworkServiceLayer) mNetworkServiceLayer).getWalletStatistics();
        setPluginReferencesAndStart(walletStatisticsNetworkService, Plugins.BITDUBAI_WALLET_STATISTICS_NETWORK_SERVICE);

        /**
         * -------------------------------
         * Plugin App Runtime Middleware 
         * -------------------------------
         */
        Plugin appRuntimeMiddleware = ((MiddlewareLayer) mMiddlewareLayer).getAppRuntimePlugin();
        setPluginReferencesAndStart(appRuntimeMiddleware, Plugins.BITDUBAI_APP_RUNTIME_MIDDLEWARE);

        /**
         * -----------------------------
         * Plugin Bank Notes Middleware
         * -----------------------------
         */
        Plugin bankNotesMiddleware = ((MiddlewareLayer) mMiddlewareLayer).getBankNotesPlugin();
        setPluginReferencesAndStart(bankNotesMiddleware, Plugins.BITDUBAI_BANK_NOTES_MIDDLEWARE);

        /**
         * -----------------------------
         * Plugin Bitcoin Wallet Basic Wallet
         * -----------------------------
         */
         Plugin bitcoinWalletBasicWallet = ((BasicWalletLayer) mBasicWalletLayer).getBitcoinWallet();
         setPluginReferencesAndStart(bitcoinWalletBasicWallet, Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET);

        /**
         * -----------------------------
         * Plugin Discount Wallet Basic Wallet
         * -----------------------------
         */
        //Plugin discountWalletBasicWallet = ((BasicWalletLayer) mBasicWalletLayer).getDiscountWallet();
        //setPluginReferencesAndStart(discountWalletBasicWallet, Plugins.BITDUBAI_DISCOUNT_WALLET_BASIC_WALLET);

        /**
         * ----------------------------------
         * Plugin Wallet Contacts Middleware
         * ----------------------------------
         */
        Plugin walletContactsMiddleware = ((MiddlewareLayer) mMiddlewareLayer).getWalletContactsPlugin();
        setPluginReferencesAndStart(walletContactsMiddleware, Plugins.BITDUBAI_WALLET_CONTACTS_MIDDLEWARE);

        /**
         * ----------------------------------
         * Plugin Wallet Contacts Middleware
         * ----------------------------------
         */
        Plugin walletFactoryMiddleware = ((MiddlewareLayer) mMiddlewareLayer).getmWalletFactoryPlugin();
        setPluginReferencesAndStart(walletFactoryMiddleware, Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE);

        /**
         * ----------------------------------
         * Plugin Wallet Contacts Middleware
         * ----------------------------------
         */
        Plugin walletManagerMiddleware = ((MiddlewareLayer) mMiddlewareLayer).getmWalletManagerPlugin();
        setPluginReferencesAndStart(walletManagerMiddleware, Plugins.BITDUBAI_WALLET_MANAGER_MIDDLEWARE);

        /**
         * ----------------------------------
         * Plugin Wallet Contacts Middleware
         * ----------------------------------
         */
        Plugin walletPublisherMiddleware = ((MiddlewareLayer) mMiddlewareLayer).getmWalletPublisherPlugin();
        setPluginReferencesAndStart(walletPublisherMiddleware, Plugins.BITDUBAI_WALLET_PUBLISHER_MIDDLEWARE);

        /**
         * ----------------------------------
         * Plugin Wallet Contacts Middleware
         * ----------------------------------
         */
        Plugin walletStoreMiddleware = ((MiddlewareLayer) mMiddlewareLayer).getmWalletStorePlugin();
        setPluginReferencesAndStart(walletStoreMiddleware, Plugins.BITDUBAI_WALLET_STORE_MIDDLEWARE);

        /**
         * ----------------------------------
         * Plugin Bitcoin Crypto Vault
         * ----------------------------------
         */
        Plugin bitcoinCryptoVault = ((CryptoVaultLayer) mCryptoVaultLayer).getmBitcoin();
        setPluginReferencesAndStart(bitcoinCryptoVault, Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT);

        /**
         * ----------------------------------
         * Plugin Incoming Crypto Crypto Router
         * ----------------------------------
         */
        Plugin incomingCryptoTransaction = ((CryptoRouterLayer) mCryptoRouterLayer).getIncomingCrypto();
        setPluginReferencesAndStart(incomingCryptoTransaction, Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION);

        /**
         * ----------------------------------
         * Plugin Incoming Extra User Transaction
         * ----------------------------------
         */
        Plugin incomingExtraUserTransaction = ((TransactionLayer) mTransactionLayer).getIncomingExtraUserPlugin();
        setPluginReferencesAndStart(incomingExtraUserTransaction, Plugins.BITDUBAI_INCOMING_EXTRA_USER_TRANSACTION);
        //System.out.println("EXTRA USER START SUCCESS");


        /**
         * ----------------------------------
         * Plugin Incoming Intra User Transaction
         * ----------------------------------
         */
        Plugin incomingIntraUserTransaction = ((TransactionLayer) mTransactionLayer).getIncomingIntraUserPlugin();
        setPluginReferencesAndStart(incomingIntraUserTransaction, Plugins.BITDUBAI_INCOMING_INTRA_USER_TRANSACTION);

        /**
         * ----------------------------------
         * Plugin Inter Wallet Transaction
         * ----------------------------------
         */
        Plugin interWalletTransaction = ((TransactionLayer) mTransactionLayer).getInterWalletPlugin();
        setPluginReferencesAndStart(interWalletTransaction, Plugins.BITDUBAI_INTER_WALLET_TRANSACTION);

        /**
         * ----------------------------------
         * Plugin Outgoing Extra User Transaction
         * ----------------------------------
         */
        Plugin outgoingExtraUserTransaction = ((TransactionLayer) mTransactionLayer).getOutgoingExtraUserPlugin();
        setPluginReferencesAndStart(outgoingExtraUserTransaction, Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION);

        /**
         * ----------------------------------
         * Plugin Outgoing Device user Transaction
         * ----------------------------------
         */
        Plugin outgoingDeviceUserTransaction = ((TransactionLayer) mTransactionLayer).getOutgoingDeviceUserPlugin();
        setPluginReferencesAndStart(outgoingDeviceUserTransaction, Plugins.BITDUBAI_OUTGOING_DEVICE_USER_TRANSACTION);

        /**
         * ----------------------------------
         * Plugin Incoming Device user Transaction
         * ----------------------------------
         */
        Plugin outgoingIntraUserTransaction = ((TransactionLayer) mTransactionLayer).getOutgoingIntraUserPlugin();
        setPluginReferencesAndStart(outgoingIntraUserTransaction, Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION);

        /**
         * ----------------------------------
         * Plugin Incoming Device user Transaction
         * ----------------------------------
         */
        Plugin incomingDeviceUserTransaction = ((TransactionLayer) mTransactionLayer).getIncomingDeviceUserPlugin();
        setPluginReferencesAndStart(incomingDeviceUserTransaction, Plugins.BITDUBAI_INCOMING_DEVICE_USER_TRANSACTION);

        /**
         * ----------------------------------
         * Plugin Crypto Loss Protected Wallet Niche Type Wallet
         * ----------------------------------
         */
        //TODO lo comente porque la variable cryptoLossProtectedWalletNicheWalletType es null y da error al inicializar la APP (Natalia)
        //Plugin cryptoLossProtectedWalletNicheWalletType = ((NicheWalletTypeLayer) mNicheWalletTypeLayer).getmCryptoLossProtectedWallet();
        //setPluginReferencesAndStart(cryptoLossProtectedWalletNicheWalletType, Plugins.BITDUBAI_CRYPTO_LOSS_PROTECTED_WALLET_NICHE_WALLET_TYPE);

        /**
         * ----------------------------------
         * Plugin crypto Wallet Niche Type Wallet
         * ----------------------------------
         */
        Plugin cryptoWalletNicheWalletType = ((NicheWalletTypeLayer) mNicheWalletTypeLayer).getmCryptoWallet();
        setPluginReferencesAndStart(cryptoWalletNicheWalletType, Plugins.BITDUBAI_CRYPTO_WALLET_NICHE_WALLET_TYPE);

        /**
         * ----------------------------------
         * Plugin Discount Wallet Niche Type Wallet
         * ----------------------------------
         */
        Plugin discountWalletNicheWalletType = ((NicheWalletTypeLayer) mNicheWalletTypeLayer).getmDiscountWallet();
        setPluginReferencesAndStart(discountWalletNicheWalletType, Plugins.BITDUBAI_DISCOUNT_WALLET_NICHE_WALLET_TYPE);

        /**
         * ----------------------------------
         * Plugin Fiat Over Crypto Loss Protected Wallet Wallet Niche Type Wallet
         * ----------------------------------
         */
        //TODO lo comente porque la variable fiatOverCryptoLossProtectedWalletNicheWalletType es null  y da error al levantar la APP (Natalia)
        //Plugin fiatOverCryptoLossProtectedWalletNicheWalletType = ((NicheWalletTypeLayer) mNicheWalletTypeLayer).getmFiatOverCryptoLossProtectedWallet();
        //setPluginReferencesAndStart(fiatOverCryptoLossProtectedWalletNicheWalletType, Plugins.BITDUBAI_FIAT_OVER_CRYPTO_LOSS_PROTECTED_WALLET_NICHE_WALLET_TYPE);


        /**
         * ----------------------------------
         * Plugin Fiat Over Crypto Wallet Niche Type Wallet
         * ----------------------------------
         */
        Plugin fiatOverCryptoWalletNicheWalletType = ((NicheWalletTypeLayer) mNicheWalletTypeLayer).getmFiatOverCryptoWallet();
        setPluginReferencesAndStart(fiatOverCryptoWalletNicheWalletType, Plugins.BITDUBAI_FIAT_OVER_CRYPTO_WALLET_NICHE_WALLET_TYPE);

        /**
         * ----------------------------------
         * Plugin Multi account Wallet Niche Type Wallet
         * ----------------------------------
         */
        Plugin multiAccountWalletNicheWalletType = ((NicheWalletTypeLayer) mNicheWalletTypeLayer).getmMultiAccountWallet();
        setPluginReferencesAndStart(multiAccountWalletNicheWalletType, Plugins.BITDUBAI_MULTI_ACCOUNT_WALLET_NICHE_WALLET_TYPE);

        /**
         * ----------------------------------
         * Plugin Bank Notes Wallet Niche Type Wallet
         * ----------------------------------
         */
        Plugin bankNotesWalletNicheWalletType = ((NicheWalletTypeLayer) mNicheWalletTypeLayer).getmBankNotesWallet();
        setPluginReferencesAndStart(bankNotesWalletNicheWalletType, Plugins.BITDUBAI_BANK_NOTES_WALLET_NICHE_WALLET_TYPE);

        /**
         * -----------------------------
         * Plugin Wallet factory
         * -----------------------------
         */
        //  Plugin walletFactoryModule =  ((ModuleLayer) mModuleLayer).getWalletFactory();
        //  setPluginReferencesAndStart(walletFactoryModule, Plugins.BITDUBAI_WALLET_FACTORY_MODULE);

        /**
         * -----------------------------
         * Plugin Wallet Manager
         * -----------------------------
         */
        Plugin walletManager =  ((ModuleLayer) mModuleLayer).getWalletManager();
        setPluginReferencesAndStart(walletManager, Plugins.BITDUBAI_WALLET_MANAGER_MODULE);

        /**
         * -----------------------------
         * Plugin Wallet Runtime
         * -----------------------------
         */
        Plugin walletRuntime =  ((ModuleLayer) mModuleLayer).getWalletRuntime();
        setPluginReferencesAndStart(walletRuntime, Plugins.BITDUBAI_WALLET_RUNTIME_MODULE);


        /**
         * -----------------------------
         * Plugin Template Network Service
         * -----------------------------
         */
        Plugin templateNetworkService = ((NetworkServiceLayer) mNetworkServiceLayer).getTemplate();
        setPluginReferencesAndStart(templateNetworkService, Plugins.BITDUBAI_TEMPLATE_NETWORK_SERVICE);




        for(Addons registeredDescriptor : corePlatformContext.getRegisteredAddonsDescriptors())
            checkAddonForDeveloperInterfaces(registeredDescriptor);
        for(Plugins registeredDescriptor : corePlatformContext.getRegisteredPluginsDescriptors())
            checkPluginForDeveloperInterfaces(registeredDescriptor);

        ((DealWithDatabaseManagers) actorDeveloper).setDatabaseManagers(dealsWithDatabaseManagersPlugins, dealsWithDatabaseManagersAddons);
        ((DealsWithLogManagers) actorDeveloper).setLogManagers(dealsWithLogManagersPlugins, dealsWithLogManagersAddons);

    }

    private void setPluginReferencesAndStart(Plugin plugin, Plugins descriptor) {

        ErrorManager errorManager = (ErrorManager) corePlatformContext.getAddon(Addons.ERROR_MANAGER);

        try {
            if (plugin instanceof DealsWithActorAddressBook)
                ((DealsWithActorAddressBook) plugin).setActorAddressBookManager((ActorAddressBookManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO));

             if (plugin instanceof DealsWithBitcoinWallet)
                  ((DealsWithBitcoinWallet) plugin).setBitcoinWalletManager((BitcoinWalletManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET));

            if (plugin instanceof DealsWithBitcoinCryptoNetwork)
                ((DealsWithBitcoinCryptoNetwork) plugin).setBitcoinCryptoNetworkManager((BitcoinCryptoNetworkManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_BITCOIN_CRYPTO_NETWORK));

            if (plugin instanceof DealsWithCryptoVault)
                ((DealsWithCryptoVault) plugin).setCryptoVaultManager((CryptoVaultManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT));

            if (plugin instanceof DealsWithDeveloperIdentity)
                ((DealsWithDeveloperIdentity) plugin).setDeveloperIdentityManager((DeveloperIdentityManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_DEVELOPER_IDENTITY));

            if (plugin instanceof DealsWithDeveloperModule)
                ((DealsWithDeveloperModule) plugin).setDeveloperModuleManager((DeveloperModuleManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_DEVELOPER_MODULE));

            if (plugin instanceof DealsWithDeviceUsers)
                ((DealsWithDeviceUsers) plugin).setDeviceUserManager((DeviceUserManager) corePlatformContext.getAddon(Addons.DEVICE_USER));

            if (plugin instanceof DealsWithErrors)
                ((DealsWithErrors) plugin).setErrorManager(errorManager);

            if (plugin instanceof DealsWithEvents)
                ((DealsWithEvents) plugin).setEventManager((EventManager) corePlatformContext.getAddon(Addons.EVENT_MANAGER));

            if (plugin instanceof DealsWithExtraUsers)
                ((DealsWithExtraUsers) plugin).setExtraUserManager((ExtraUserManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_USER_EXTRA_USER));

            if (plugin instanceof DealsWithLogger)
                ((DealsWithLogger) plugin).setLogManager(loggerSystemOs.getLoggerManager());

            if (plugin instanceof DealsWithNicheWalletTypeCryptoWallet)
                ((DealsWithNicheWalletTypeCryptoWallet) plugin).setNicheWalletTypeCryptoWalletManager((CryptoWalletManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_CRYPTO_WALLET_NICHE_WALLET_TYPE));

            if (plugin instanceof DealsWithOutgoingExtraUser)
                ((DealsWithOutgoingExtraUser) plugin).setOutgoingExtraUserManager((OutgoingExtraUserManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION));

            if (plugin instanceof DealsWithPluginFileSystem)
                ((DealsWithPluginFileSystem) plugin).setPluginFileSystem(fileSystemOs.getPlugInFileSystem());

            if (plugin instanceof DealsWithPluginDatabaseSystem)
                ((DealsWithPluginDatabaseSystem) plugin).setPluginDatabaseSystem(databaseSystemOs.getPluginDatabaseSystem());

            if (plugin instanceof DealsWithToolManager)
                ((DealsWithToolManager) plugin).setToolManager((ToolManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_ACTOR_DEVELOPER));

            if (plugin instanceof DealsWithWalletAddressBook)
                ((DealsWithWalletAddressBook) plugin).setWalletAddressBookManager((WalletAddressBookManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_WALLET_ADDRESS_BOOK_CRYPTO));

            if (plugin instanceof DealsWithWalletContacts)
                ((DealsWithWalletContacts) plugin).setWalletContactsManager((WalletContactsManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_WALLET_CONTACTS_MIDDLEWARE));

            if (plugin instanceof DealsWithWalletFactory)
                ((DealsWithWalletFactory) plugin).setWalletFactoryManager((WalletFactoryManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE));

            if (plugin instanceof DealsWithWalletManager)
                ((DealsWithWalletManager) plugin).setWalletManagerManager((WalletManagerManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_WALLET_MANAGER_MIDDLEWARE));

            if (plugin instanceof DealsWithWalletPublisher)
                ((DealsWithWalletPublisher) plugin).setWalletPublisherManager((WalletPublisherManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_WALLET_PUBLISHER_MIDDLEWARE));

            if (plugin instanceof DealsWithWalletResources)
                ((DealsWithWalletResources) plugin).setWalletResourcesManager((WalletResourcesManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_WALLET_RESOURCES_NETWORK_SERVICE));

            if (plugin instanceof DealsWithWalletStatisticsNetworkService)
                ((DealsWithWalletStatisticsNetworkService) plugin).setWalletStatisticsManager((WalletStatisticsManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_WALLET_STATISTICS_NETWORK_SERVICE));

            if (plugin instanceof DealsWithWalletStore)
                ((DealsWithWalletStore) plugin).setWalletStoreManager((WalletStoreManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_WALLET_STORE_MIDDLEWARE));

            if (plugin instanceof DealsWithWalletStoreNetworkService)
                ((DealsWithWalletStoreNetworkService) plugin).setWalletStoreManager((com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.WalletStoreManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_WALLET_STORE_NETWORK_SERVICE));

            if (plugin instanceof DealsWithIncomingCrypto)
                ((DealsWithIncomingCrypto) plugin).setIncomingCryptoManager((IncomingCryptoManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION));

            if (plugin instanceof DealsWithCommunicationLayerManager)
                ((DealsWithCommunicationLayerManager) plugin).setCommunicationLayerManager((CommunicationLayerManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_CLOUD_CHANNEL));

            corePlatformContext.addPlugin(plugin, descriptor);
        } catch (Exception e){
            System.err.println("Exception: Problem with references.");
            e.printStackTrace();
        }

        try {
            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */
            UUID pluginID = pluginsIdentityManager.getPluginId(plugin);
            (plugin).setId(pluginID);
            try {
                ((Service) plugin).start();
            } catch (CantStartPluginException cantStartPluginException) {
                errorManager.reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, cantStartPluginException);
                /**
                 * This plugin wont disable the whole platform, so I will allow the Platform to start even if this one
                 * will be disabled. In the future, I will re-try the start of plugins that are not starting at once.
                 * * *
                 */
            } catch (Exception e){
                System.err.println(descriptor.getKey().toString()+" - PluginNotRecognizedException: " + e.getMessage());
                errorManager.reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, e);
                /**
                 * This is worse than the previous catch since the plugin didn't even throw an expected exception.
                 * * *
                 */
            }
        } catch (PluginNotRecognizedException pluginNotRecognizedException) {
            System.err.println("PluginNotRecognizedException: " + pluginNotRecognizedException.getMessage());
            pluginNotRecognizedException.printStackTrace();
        }
    }

    private void checkAddonForDeveloperInterfaces(final Addons descriptor){
        Addon addon = corePlatformContext.getAddon(descriptor);
        if(addon == null)
            return;
        if(addon instanceof DatabaseManagerForDevelopers)
            dealsWithDatabaseManagersAddons.put(descriptor, addon);
        if(addon instanceof LogManagerForDevelopers)
            dealsWithLogManagersAddons.put(descriptor, addon);
    }

    private void checkPluginForDeveloperInterfaces(final Plugins descriptor){
        Plugin plugin = corePlatformContext.getPlugin(descriptor);
        if(plugin == null)
            return;
        if(plugin instanceof DatabaseManagerForDevelopers)
            dealsWithDatabaseManagersPlugins.put(descriptor, plugin);
        if(plugin instanceof LogManagerForDevelopers)
            dealsWithLogManagersPlugins.put(descriptor, plugin);
    }

}
