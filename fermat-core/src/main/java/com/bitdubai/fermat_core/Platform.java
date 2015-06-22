package com.bitdubai.fermat_core;


import com.bitdubai.fermat_api.*;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;


import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.PlatformComponents;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.event.DealWithEventMonitor;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.DealsWithBitcoinWallet;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.DealsWithWalletContacts;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactsManager;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.DealsWithNicheWalletTypeCryptoWallet;
import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_extrauser.DealsWithOutgoingExtraUser;
import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_extrauser.OutgoingExtraUserManager;
import com.bitdubai.fermat_api.layer.osa_android.DataBaseSystemOs;
import com.bitdubai.fermat_api.layer.osa_android.FileSystemOs;
import com.bitdubai.fermat_api.layer.osa_android.LocationSystemOs;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPlatformFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPlatformDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.UnexpectedPlatformExceptionSeverity;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventManager;

import com.bitdubai.fermat_api.layer.pip_user.device_user.DealsWithDeviceUsers;
import com.bitdubai.fermat_api.layer.pip_user.device_user.DeviceUserManager;
import com.bitdubai.fermat_api.layer.pip_user.extra_user.DealsWithExtraUsers;
import com.bitdubai.fermat_api.layer.pip_user.extra_user.ExtraUserManager;
import com.bitdubai.fermat_core.layer.cry_crypto_router.CryptoRouterLayer;
import com.bitdubai.fermat_core.layer.dmp_basic_wallet.BasicWalletLayer;
import com.bitdubai.fermat_core.layer.dmp_transaction.TransactionLayer;
import com.bitdubai.fermat_core.layer.dmp_niche_wallet_type.NicheWalletTypeLayer;
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
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.DealsWithCryptoVault;

import java.util.UUID;

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


    PlatformEventMonitor eventMonitor;

    PluginsIdentityManager pluginsIdentityManager;


    CorePlatformContext corePlatformContext;

    Object osContext;

    FileSystemOs fileSystemOs;
    DataBaseSystemOs databaseSystemOs;
    LocationSystemOs locationSystemOs;


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

    public void start() throws CantStartPlatformException, CantReportCriticalStartingProblem {

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
            throw new CantReportCriticalStartingProblem();
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
         *---------------------------------
         * Addon Extra User
         * -------------------------------
         */
        Service extraUser = (Service) ((UserLayer) mUserLayer).getExtraUser();

        ((DealsWithPlatformFileSystem) extraUser).setPlatformFileSystem(fileSystemOs.getPlatformFileSystem());
        ((DealsWithPlatformDatabaseSystem) extraUser).setPlatformDatabaseSystem(databaseSystemOs.getPlatformDatabaseSystem());
        ((DealsWithEvents) extraUser).setEventManager((EventManager) eventManager);

        corePlatformContext.addAddon((Addon) extraUser, Addons.EXTRA_USER);

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
        Plugin blockchainInfoWorld = ((WorldLayer)  mWorldLayer).getBlockchainInfo();
        setPluginReferencesAndStart(blockchainInfoWorld, Plugins.BITDUBAI_BLOCKCHAIN_INFO_WORLD);

        /**
         * -----------------------------
         * Plugin Shape Shift World
         * -----------------------------
         */
        Plugin shapeShiftWorld = ((WorldLayer)  mWorldLayer).getShapeShift();
        setPluginReferencesAndStart(shapeShiftWorld, Plugins.BITDUBAI_SHAPE_SHIFT_WORLD);

        /**
         * -----------------------------
         * Plugin Coinapult World
         * -----------------------------
         */
        Plugin coinapultWorld = ((WorldLayer)  mWorldLayer).getCoinapult();
        setPluginReferencesAndStart(coinapultWorld, Plugins.BITDUBAI_COINAPULT_WORLD);

        /**
         * -----------------------------
         * Plugin Coinbase World
         * -----------------------------
         */
        Plugin coinbaseWorld = ((WorldLayer)  mWorldLayer).getCoinbase();
        setPluginReferencesAndStart(coinbaseWorld, Plugins.BITDUBAI_COINBASE_WORLD);

        /**
         * -----------------------------
         * Plugin Location World
         * -----------------------------
         */
        Plugin locationWorld = ((WorldLayer)  mWorldLayer).getLocation();
        setPluginReferencesAndStart(locationWorld, Plugins.BITDUBAI_LOCATION_WORLD);

        /**
         * -----------------------------
         * Plugin Crypto Index World
         * -----------------------------
         */
        Plugin cryptoIndexWorld = ((WorldLayer)  mWorldLayer).getCryptoIndex();
        setPluginReferencesAndStart(cryptoIndexWorld, Plugins.BITDUBAI_CRYPTO_INDEX);

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
<<<<<<< HEAD
        Plugin bankNotesMiddleware = ((MiddlewareLayer) mMiddlewareLayer).getBankNotesPlugin();
        setPluginReferencesAndStart(bankNotesMiddleware, Plugins.BITDUBAI_BANK_NOTES_MIDDLEWARE);
=======

        Plugin coinbaseWorld = ((WorldLayer)  mWorldLayer).getCoinbase();

        ((DealsWithPluginFileSystem) coinbaseWorld).setPluginFileSystem(fileSystemOs.getPlugInFileSystem());
        ((DealsWithEvents) coinbaseWorld).setEventManager((EventManager) eventManager);

        corePlatformContext.addPlugin(coinbaseWorld, Plugins.BITDUBAI_CRYPTO_INDEX);

        try
        {
            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */

            UUID pluginID = pluginsIdentityManager.getPluginId(coinbaseWorld);
            (coinbaseWorld).setId(pluginID);

            try {
                ((Service) coinbaseWorld).start();
            }
            catch (CantStartPluginException cantStartPluginException) {

                ((ErrorManager) errorManager).reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, cantStartPluginException);

                /**
                 * This plugin wont disable the whole platform, so I will allow the Platform to start even if this one
                 * will be disabled. In the future, I will re-try the start of plugins that are not starting at once.
                 * * *
                 */
            }
            catch (Exception e){

                ((ErrorManager) errorManager).reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, e);

                /**
                 * This is worse than the previous catch since the plugin didn't even throw an expected exception.
                 * * *
                 */
                throw new CantStartPlatformException();
            }

        }
        catch (PluginNotRecognizedException pluginNotRecognizedException)
        {

            System.err.println("PluginNotRecognizedException: " + pluginNotRecognizedException.getMessage());
            pluginNotRecognizedException.printStackTrace();

        }


        /**
         * -----------------------------
         * Plugin Location World
         * -----------------------------
         * * * *
         */



        /**
         * I will give the Crypto Index plugin access to the File System so it can load and save information from persistent
         * media.
         */

        Plugin locationWorld = ((WorldLayer)  mWorldLayer).getLocation();

        ((DealsWithPluginFileSystem) locationWorld).setPluginFileSystem(fileSystemOs.getPlugInFileSystem());
        ((DealsWithEvents) locationWorld).setEventManager((EventManager) eventManager);

        corePlatformContext.addPlugin(locationWorld, Plugins.BITDUBAI_CRYPTO_INDEX);

        try
        {
            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */

            UUID pluginID = pluginsIdentityManager.getPluginId(locationWorld);
            (locationWorld).setId(pluginID);

            try {
                ((Service) locationWorld).start();
            }
            catch (CantStartPluginException cantStartPluginException) {

                ((ErrorManager) errorManager).reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, cantStartPluginException);

                /**
                 * This plugin wont disable the whole platform, so I will allow the Platform to start even if this one
                 * will be disabled. In the future, I will re-try the start of plugins that are not starting at once.
                 * * *
                 */
            }
            catch (Exception e){

                ((ErrorManager) errorManager).reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, e);

                /**
                 * This is worse than the previous catch since the plugin didn't even throw an expected exception.
                 * * *
                 */
                throw new CantStartPlatformException();
            }

        }
        catch (PluginNotRecognizedException pluginNotRecognizedException)
        {

            System.err.println("PluginNotRecognizedException: " + pluginNotRecognizedException.getMessage());
            pluginNotRecognizedException.printStackTrace();

        }




        /**
         * -----------------------------
         * Plugin Crypto Index World
         * -----------------------------
         * * * * 
         */



        /**
         * I will give the Crypto Index plugin access to the File System so it can load and save information from persistent
         * media.
         */

        Plugin cryptoIndexWorld = ((WorldLayer)  mWorldLayer).getCryptoIndex();

        ((DealsWithPluginFileSystem) cryptoIndexWorld).setPluginFileSystem(fileSystemOs.getPlugInFileSystem());
        ((DealsWithEvents) cryptoIndexWorld).setEventManager((EventManager) eventManager);

        corePlatformContext.addPlugin(cryptoIndexWorld, Plugins.BITDUBAI_CRYPTO_INDEX);

        try
        {
            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */

            UUID pluginID = pluginsIdentityManager.getPluginId(cryptoIndexWorld);
            (cryptoIndexWorld).setId(pluginID);

            try {
                ((Service) cryptoIndexWorld).start();
            }
            catch (CantStartPluginException cantStartPluginException) {

                ((ErrorManager) errorManager).reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, cantStartPluginException);

                /**
                 * This plugin wont disable the whole platform, so I will allow the Platform to start even if this one
                 * will be disabled. In the future, I will re-try the start of plugins that are not starting at once.
                 * * *
                 */
            }
            catch (Exception e){

                ((ErrorManager) errorManager).reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, e);

                /**
                 * This is worse than the previous catch since the plugin didn't even throw an expected exception.
                 * * *
                 */
                throw new CantStartPlatformException();
            }

        }
        catch (PluginNotRecognizedException pluginNotRecognizedException)
        {

            System.err.println("PluginNotRecognizedException: " + pluginNotRecognizedException.getMessage());
            pluginNotRecognizedException.printStackTrace();

        }



        /**
         * -----------------------------
         * Plugin Bitcoin Crypto Network
         * -----------------------------
         * * * * 
         */



        /**
         * I will give the plugin access to the File System so it can load and save and load information from persistent 
         * media.
         */
        Plugin cryptoNetwork = ((CryptoNetworkLayer) mCryptoNetworkLayer).getCryptoNetwork(CryptoNetworks.BITCOIN);

        ((DealsWithErrors) cryptoNetwork).setErrorManager((ErrorManager) errorManager);
        ((DealsWithPluginFileSystem) cryptoNetwork).setPluginFileSystem(fileSystemOs.getPlugInFileSystem());


        corePlatformContext.addPlugin(cryptoNetwork, Plugins.BITDUBAI_BITCOIN_CRYPTO_NETWORK);

        try
        {
            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */

            UUID pluginID = pluginsIdentityManager.getPluginId(cryptoNetwork);
            (cryptoNetwork).setId(pluginID);

            try {
                ((Service)cryptoNetwork).start();
            }
            catch (CantStartPluginException cantStartPluginException) {

                ((ErrorManager) errorManager).reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, cantStartPluginException);

                /**
                 * This plugin wont disable the whole platform, so I will allow the Platform to start even if this one
                 * will be disabled. In the future, I will re-try the start of plugins that are not starting at once.
                 * * *
                 */
            }
            catch (Exception e){

                ((ErrorManager) errorManager).reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, e);

                /**
                 * This is worse than the previous catch since the plugin didn't even throw an expected exception.
                 * * *
                 */

            }
        }
        catch (PluginNotRecognizedException pluginNotRecognizedException)
        {
            /**
             * Even if it is not desirable, the platform can still start without one crypto network. I will simply ask
             * this module to stop running. 
             */
            ((ErrorManager) errorManager).reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, pluginNotRecognizedException);

            ((Service)cryptoNetwork).stop();
        }

        /**
         * ------------------------------------*
         *  Plugin Wallet Address Book Crypto  *
         * ------------------------------------*
         *
         * I'm giving the Wallet Address book crypto plugin access to the Database System and to Error Manager
         */

        Plugin walletAddressBookCrypto = ((CryptoLayer) mCryptoLayer).getmWalletAddressBook();

        ((DealsWithErrors) walletAddressBookCrypto).setErrorManager((ErrorManager) errorManager);
        ((DealsWithPluginDatabaseSystem) walletAddressBookCrypto).setPluginDatabaseSystem(databaseSystemOs.getPluginDatabaseSystem());

        corePlatformContext.addPlugin(walletAddressBookCrypto, Plugins.BITDUBAI_WALLET_ADDRESS_BOOK_CRYPTO);

        try {
            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */
            UUID pluginID = pluginsIdentityManager.getPluginId(walletAddressBookCrypto);
            (walletAddressBookCrypto).setId(pluginID);
            try {
                ((Service) walletAddressBookCrypto).start();
            } catch (Exception e){
                System.err.println(e.getMessage());

            }
        } catch (PluginNotRecognizedException pluginNotRecognizedException) {
            System.err.println("PluginNotRecognizedException: " + pluginNotRecognizedException.getMessage());
            pluginNotRecognizedException.printStackTrace();
            throw new CantStartPlatformException();
        }


        /**
         * ------------------------------
         *  Plugin Actor Address Book Crypto
         * ------------------------------
         *
         * I'm giving to the Actor Address book crypto plugin access to the Database System and to the Error Manager
         */

        Plugin actorAddressBookCrypto = ((CryptoLayer) mCryptoLayer).getmUserAddressBook();

        ((DealsWithPluginDatabaseSystem) actorAddressBookCrypto).setPluginDatabaseSystem(databaseSystemOs.getPluginDatabaseSystem());
        ((DealsWithErrors) actorAddressBookCrypto).setErrorManager((ErrorManager) errorManager);

        corePlatformContext.addPlugin(actorAddressBookCrypto, Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO);

        try {
            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */
            UUID pluginID = pluginsIdentityManager.getPluginId(actorAddressBookCrypto);
            (actorAddressBookCrypto).setId(pluginID);
            try {
                ((Service) actorAddressBookCrypto).start();
            } catch (Exception e){
                System.err.println(e.getMessage());
            }
        } catch (PluginNotRecognizedException pluginNotRecognizedException) {
            System.err.println("PluginNotRecognizedException: " + pluginNotRecognizedException.getMessage());
            pluginNotRecognizedException.printStackTrace();
            throw new CantStartPlatformException();
        }


        /**
         * -----------------------------
         * Plugin Cloud Server Communication
         * -----------------------------
         * * * *
         */



        /**
         * I will give the Cloud Server Communication access to the File System and to the Event Manager
         */

        Plugin cloudServerCommunication = ((CommunicationLayer) mCommunicationLayer).getCloudServerPlugin();

        ((DealsWithPluginFileSystem) cloudServerCommunication).setPluginFileSystem(fileSystemOs.getPlugInFileSystem());
        ((DealsWithEvents) cloudServerCommunication).setEventManager((EventManager) eventManager);

        corePlatformContext.addPlugin(cloudServerCommunication, Plugins.BITDUBAI_CLOUD_SERVER_COMMUNICATION);

        try
        {

            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */

            UUID pluginID = pluginsIdentityManager.getPluginId(cloudServerCommunication);
            (cloudServerCommunication).setId(pluginID);

            try {
                ((Service) cloudServerCommunication).start();
            }
            catch (CantStartPluginException cantStartPluginException) {

                System.err.println("CantStartPluginException: " + cantStartPluginException.getMessage() + cantStartPluginException.getPlugin().getKey());
                cantStartPluginException.printStackTrace();

                /**
                 * For now, we will take this plugin as a essential for the platform itself to be running so if it can not
                 * start, then the platform wont start either. In the future we will review this policy.
                 * * *
                 */

                throw new CantStartPlatformException();
            }
            catch (Exception e){

                ((ErrorManager) errorManager).reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, e);

                /**
                 * This is worse than the previous catch since the plugin didn't even throw an expected exception.
                 * * *
                 */

            }

        }
        catch (PluginNotRecognizedException pluginNotRecognizedException)
        {

            System.err.println("PluginNotRecognizedException: " + pluginNotRecognizedException.getMessage());
            pluginNotRecognizedException.printStackTrace();

            throw new CantStartPlatformException();
        }


        /**
         * -----------------------------
         * Plugin Cloud Communication
         * -----------------------------
         * * * * 
         */



        /**
         * I will give the Cloud Communication access to the File System and to the Event Manager
         */

        Plugin cloudCommunication = ((CommunicationLayer) mCommunicationLayer).getCloudPlugin();

        ((DealsWithPluginFileSystem) cloudCommunication).setPluginFileSystem(fileSystemOs.getPlugInFileSystem());
        ((DealsWithEvents) cloudCommunication).setEventManager((EventManager) eventManager);

        corePlatformContext.addPlugin(cloudCommunication, Plugins.BITDUBAI_CLOUD_CHANNEL);

        try
        {

            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */

            UUID pluginID = pluginsIdentityManager.getPluginId(cloudCommunication);
            (cloudCommunication).setId(pluginID);

            try {
                ((Service) cloudCommunication).start();
            }
            catch (CantStartPluginException cantStartPluginException) {

                System.err.println("CantStartPluginException: " + cantStartPluginException.getMessage() + cantStartPluginException.getPlugin().getKey());
                cantStartPluginException.printStackTrace();

                /**
                 * For now, we will take this plugin as a essential for the platform itself to be running so if it can not
                 * start, then the platform wont start either. In the future we will review this policy.
                 * * * 
                 */

                throw new CantStartPlatformException();
            }
            catch (Exception e){

                ((ErrorManager) errorManager).reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, e);

                /**
                 * This is worse than the previous catch since the plugin didn't even throw an expected exception.
                 * * *
                 */

            }

        }
        catch (PluginNotRecognizedException pluginNotRecognizedException)
        {

            System.err.println("PluginNotRecognizedException: " + pluginNotRecognizedException.getMessage());
            pluginNotRecognizedException.printStackTrace();

            throw new CantStartPlatformException();
        }


        /**
         * -----------------------------
         * Plugin Bank Notes Network Service
         * -----------------------------
         * * * *
         */



        /**
         * I will give the Bank Notes Network Service access to the File System and to the Event Manager
         */

        Plugin bankNotesNetworkService = ((NetworkServiceLayer) mNetworkServiceLayer).getBankNotesPlugin();

        ((DealsWithPluginFileSystem) bankNotesNetworkService).setPluginFileSystem(fileSystemOs.getPlugInFileSystem());
        ((DealsWithEvents) bankNotesNetworkService).setEventManager((EventManager) eventManager);

        corePlatformContext.addPlugin(bankNotesNetworkService, Plugins.BITDUBAI_BANK_NOTES_NETWORK_SERVICE);

        try
        {

            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */

            UUID pluginID = pluginsIdentityManager.getPluginId(bankNotesNetworkService);
            (bankNotesNetworkService).setId(pluginID);

            try {
                ((Service) bankNotesNetworkService).start();
            }
            catch (CantStartPluginException cantStartPluginException) {

                System.err.println("CantStartPluginException: " + cantStartPluginException.getMessage() + cantStartPluginException.getPlugin().getKey());
                cantStartPluginException.printStackTrace();

                /**
                 * For now, we will take this plugin as a essential for the platform itself to be running so if it can not
                 * start, then the platform wont start either. In the future we will review this policy.
                 * * * 
                 */

                throw new CantStartPlatformException();
            }
            catch (Exception e){

                ((ErrorManager) errorManager).reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, e);

                /**
                 * This is worse than the previous catch since the plugin didn't even throw an expected exception.
                 * * *
                 */

            }
        }
        catch (PluginNotRecognizedException pluginNotRecognizedException)
        {


            System.err.println("PluginNotRecognizedException: " + pluginNotRecognizedException.getMessage());
            pluginNotRecognizedException.printStackTrace();

            throw new CantStartPlatformException();
        }


        /**
         * -----------------------------
         * Plugin Wallet Resources Network Service
         * -----------------------------
         * * * *
         */



        /**
         * I will give the Wallet Resources Network Service access to the File System and to the Event Manager
         */

        Plugin walletResourcesNetworkService = ((NetworkServiceLayer) mNetworkServiceLayer).getWalletResources();

        ((DealsWithPluginFileSystem) walletResourcesNetworkService).setPluginFileSystem(fileSystemOs.getPlugInFileSystem());
        ((DealsWithEvents) walletResourcesNetworkService).setEventManager((EventManager) eventManager);
        ((DealsWithErrors) walletResourcesNetworkService).setErrorManager((ErrorManager) errorManager);

        corePlatformContext.addPlugin(walletResourcesNetworkService, Plugins.BITDUBAI_WALLET_RESOURCES_NETWORK_SERVICE);

        try
        {

            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */

            UUID pluginID = pluginsIdentityManager.getPluginId(walletResourcesNetworkService);
            (walletResourcesNetworkService).setId(pluginID);

            try {
                ((Service) walletResourcesNetworkService).start();
            }
            catch (CantStartPluginException cantStartPluginException) {

                System.err.println("CantStartPluginException: " + cantStartPluginException.getMessage() + cantStartPluginException.getPlugin().getKey());
                cantStartPluginException.printStackTrace();

                /**
                 * For now, we will take this plugin as a essential for the platform itself to be running so if it can not
                 * start, then the platform wont start either. In the future we will review this policy.
                 * * * 
                 */

                throw new CantStartPlatformException();
            }
            catch (Exception e){

                ((ErrorManager) errorManager).reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, e);

                /**
                 * This is worse than the previous catch since the plugin didn't even throw an expected exception.
                 * * *
                 */

            }

        }
        catch (PluginNotRecognizedException pluginNotRecognizedException)
        {


            System.err.println("PluginNotRecognizedException: " + pluginNotRecognizedException.getMessage());
            pluginNotRecognizedException.printStackTrace();

            throw new CantStartPlatformException();
        }

        /**
         * -----------------------------
         * Plugin Wallet Community Network Service
         * -----------------------------
         * * * *
         */



        /**
         * I will give the Wallet Community Network Service access to the File System and to the Event Manager
         */

        Plugin walletCommunityNetworkService = ((NetworkServiceLayer) mNetworkServiceLayer).getWalletCommunity();

        ((DealsWithPluginFileSystem) walletCommunityNetworkService).setPluginFileSystem(fileSystemOs.getPlugInFileSystem());
        ((DealsWithEvents) walletCommunityNetworkService).setEventManager((EventManager) eventManager);

        corePlatformContext.addPlugin(walletCommunityNetworkService, Plugins.BITDUBAI_WALLET_STORE_NETWORK_SERVICE);

        try
        {

            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */

            UUID pluginID = pluginsIdentityManager.getPluginId(walletCommunityNetworkService);
            (walletCommunityNetworkService).setId(pluginID);

            try {
                ((Service) walletCommunityNetworkService).start();
            }
            catch (CantStartPluginException cantStartPluginException) {

                System.err.println("CantStartPluginException: " + cantStartPluginException.getMessage() + cantStartPluginException.getPlugin().getKey());
                cantStartPluginException.printStackTrace();

                /**
                 * For now, we will take this plugin as a essential for the platform itself to be running so if it can not
                 * start, then the platform wont start either. In the future we will review this policy.
                 * * * 
                 */

                throw new CantStartPlatformException();
            }
            catch (Exception e){

                ((ErrorManager) errorManager).reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, e);

                /**
                 * This is worse than the previous catch since the plugin didn't even throw an expected exception.
                 * * *
                 */

            }

        }
        catch (PluginNotRecognizedException pluginNotRecognizedException)
        {


            System.err.println("PluginNotRecognizedException: " + pluginNotRecognizedException.getMessage());
            pluginNotRecognizedException.printStackTrace();

            throw new CantStartPlatformException();
        }



        /**
         * -----------------------------
         * Plugin Wallet Store Network Service
         * -----------------------------
         * * * *
         */



        /**
         * I will give the Wallet Store Network Service access to the File System and to the Event Manager
         */

        Plugin walletStoreNetworkService = ((NetworkServiceLayer) mNetworkServiceLayer).getWalletStore();

        ((DealsWithPluginFileSystem) walletStoreNetworkService).setPluginFileSystem(fileSystemOs.getPlugInFileSystem());
        ((DealsWithEvents) walletStoreNetworkService).setEventManager((EventManager) eventManager);

        corePlatformContext.addPlugin(walletStoreNetworkService, Plugins.BITDUBAI_WALLET_STORE_NETWORK_SERVICE);

        try
        {

            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */

            UUID pluginID = pluginsIdentityManager.getPluginId(walletStoreNetworkService);
            (walletStoreNetworkService).setId(pluginID);

            try {
                ((Service) walletStoreNetworkService).start();
            }
            catch (CantStartPluginException cantStartPluginException) {

                System.err.println("CantStartPluginException: " + cantStartPluginException.getMessage() + cantStartPluginException.getPlugin().getKey());
                cantStartPluginException.printStackTrace();

                /**
                 * For now, we will take this plugin as a essential for the platform itself to be running so if it can not
                 * start, then the platform wont start either. In the future we will review this policy.
                 * * * 
                 */

                throw new CantStartPlatformException();
            }
            catch (Exception e){

                ((ErrorManager) errorManager).reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, e);

                /**
                 * This is worse than the previous catch since the plugin didn't even throw an expected exception.
                 * * *
                 */

            }

        }
        catch (PluginNotRecognizedException pluginNotRecognizedException)
        {


            System.err.println("PluginNotRecognizedException: " + pluginNotRecognizedException.getMessage());
            pluginNotRecognizedException.printStackTrace();

            throw new CantStartPlatformException();
        }



        /**
         * -------------------------------
         * Plugin App Runtime Middleware 
         * -------------------------------
         * * * * 
         */



        Plugin appRuntimeMiddleware = ((MiddlewareLayer) mMiddlewareLayer).getAppRuntimePlugin();

        ((DealsWithPluginFileSystem) appRuntimeMiddleware).setPluginFileSystem(fileSystemOs.getPlugInFileSystem());
        ((DealsWithEvents) appRuntimeMiddleware).setEventManager((EventManager) eventManager);

        corePlatformContext.addPlugin(appRuntimeMiddleware, Plugins.BITDUBAI_APP_RUNTIME_MIDDLEWARE);

        try
        {

            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */

            UUID pluginID = pluginsIdentityManager.getPluginId(appRuntimeMiddleware);
            (appRuntimeMiddleware).setId(pluginID);

            try {
                ((Service) appRuntimeMiddleware).start();
            }
            catch (CantStartPluginException cantStartPluginException) {

                System.err.println("CantStartPluginException: " + cantStartPluginException.getMessage() + cantStartPluginException.getPlugin().getKey());
                cantStartPluginException.printStackTrace();

                /**
                 * For now, we will take this plugin as a essential for the platform itself to be running so if it can not
                 * start, then the platform wont start either. In the future we will review this policy.
                 * * * 
                 */

                throw new CantStartPlatformException();
            }
            catch (Exception e){

                ((ErrorManager) errorManager).reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, e);

                /**
                 * This is worse than the previous catch since the plugin didn't even throw an expected exception.
                 * * *
                 */

            }

        }
        catch (PluginNotRecognizedException pluginNotRecognizedException)
        {


            System.err.println("PluginNotRecognizedException: " + pluginNotRecognizedException.getMessage());
            pluginNotRecognizedException.printStackTrace();

            throw new CantStartPlatformException();
        }



        /**
         * -----------------------------
         * Plugin Bank Notes Middleware
         * -----------------------------
         * * * *
         */



        /**
         * I will give the Bank Notes Middleware access to the File System and to the Event Manager
         */

        Plugin bankNotesMiddleware = ((MiddlewareLayer) mMiddlewareLayer).getBankNotesPlugin();

        ((DealsWithPluginFileSystem) bankNotesMiddleware).setPluginFileSystem(fileSystemOs.getPlugInFileSystem());
        ((DealsWithEvents) bankNotesMiddleware).setEventManager((EventManager) eventManager);

        corePlatformContext.addPlugin(bankNotesMiddleware, Plugins.BITDUBAI_BANK_NOTES_MIDDLEWARE);

        try
        {

            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */

            UUID pluginID = pluginsIdentityManager.getPluginId(bankNotesMiddleware);
            (bankNotesMiddleware).setId(pluginID);

            try {
                ((Service) bankNotesMiddleware).start();
            }
            catch (CantStartPluginException cantStartPluginException) {

                System.err.println("CantStartPluginException: " + cantStartPluginException.getMessage() + cantStartPluginException.getPlugin().getKey());
                cantStartPluginException.printStackTrace();

                /**
                 * For now, we will take this plugin as a essential for the platform itself to be running so if it can not
                 * start, then the platform wont start either. In the future we will review this policy.
                 * * * 
                 */

                throw new CantStartPlatformException();
            }
            catch (Exception e){

                ((ErrorManager) errorManager).reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, e);

                /**
                 * This is worse than the previous catch since the plugin didn't even throw an expected exception.
                 * * *
                 */

            }


        }
        catch (PluginNotRecognizedException pluginNotRecognizedException)
        {


            System.err.println("PluginNotRecognizedException: " + pluginNotRecognizedException.getMessage());
            pluginNotRecognizedException.printStackTrace();

            throw new CantStartPlatformException();
        }






        /**
         * -----------------------------
         * Plugin Bitcoin Wallet Basic Wallet
         * -----------------------------
         * * * *
         */



        /**
         * I will give the Wallet Middleware access to the File System and to the Event Manager
         */

        // Plugin bitcoinWalletBasicWallet = ((BasicWalletLayer) mBasicWalletLayer).getBitcoinWallet();

        //  ((DealsWithPluginFileSystem) bitcoinWalletBasicWallet).setPluginFileSystem(fileSystemOs.getPlugInFileSystem());
        // ((DealsWithEvents) bitcoinWalletBasicWallet).setEventManager((EventManager) eventManager);

        // corePlatformContext.addPlugin(bitcoinWalletBasicWallet, Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET);

        //  try
        // {

        /**
         * As any other plugin, this one will need its identity in order to access the data it persisted before.
         */

        //  UUID pluginID = pluginsIdentityManager.getPluginId(bitcoinWalletBasicWallet);
        //  (bitcoinWalletBasicWallet).setId(pluginID);

        // try {
        //       ((Service) bitcoinWalletBasicWallet).start();
        //    }
        //   catch (CantStartPluginException cantStartPluginException) {

        //     System.err.println("CantStartPluginException: " + cantStartPluginException.getMessage() + cantStartPluginException.getPlugin().getKey());
        //    cantStartPluginException.printStackTrace();

        /**
         * For now, we will take this plugin as a essential for the platform itself to be running so if it can not
         * start, then the platform wont start either. In the future we will review this policy.
         * * *
         */

        //  throw new CantStartPlatformException();
        //}
        //  }
        // catch (PluginNotRecognizedException pluginNotRecognizedException)
        // {


        //  System.err.println("PluginNotRecognizedException: " + pluginNotRecognizedException.getMessage());
        //  pluginNotRecognizedException.printStackTrace();

        //throw new CantStartPlatformException();
        // }



        /**
         * -----------------------------
         * Plugin Discount Wallet Basic Wallet
         * -----------------------------
         * * * * 
         */



        /**
         * I will give the Discount Wallet access to the File System and to the Event Manager
         */

        //    Plugin discountWalletBasicWallet = ((BasicWalletLayer) mBasicWalletLayer).getDiscountWallet();

        //  ((DealsWithPluginFileSystem) discountWalletBasicWallet).setPluginFileSystem(fileSystemOs.getPlugInFileSystem());
        //  ((DealsWithEvents) discountWalletBasicWallet).setEventManager((EventManager) eventManager);

        //  corePlatformContext.addPlugin(discountWalletBasicWallet, Plugins.BITDUBAI_DISCOUNT_WALLET_BASIC_WALLET);

        //   try
        //  {

        /**
         * As any other plugin, this one will need its identity in order to access the data it persisted before.
         */

        //     UUID pluginID = pluginsIdentityManager.getPluginId(discountWalletBasicWallet);
        //    (discountWalletBasicWallet).setId(pluginID);

        // try {
        //      ((Service) discountWalletBasicWallet).start();
        //  }
        //  catch (CantStartPluginException cantStartPluginException) {

        //  System.err.println("CantStartPluginException: " + cantStartPluginException.getMessage() + cantStartPluginException.getPlugin().getKey());
        //  cantStartPluginException.printStackTrace();

        /**
         * For now, we will take this plugin as a essential for the platform itself to be running so if it can not
         * start, then the platform wont start either. In the future we will review this policy.
         * * *
         */

        //  throw new CantStartPlatformException();
        //  }
        // }
        //  catch (PluginNotRecognizedException pluginNotRecognizedException)
        //  {


        //  System.err.println("PluginNotRecognizedException: " + pluginNotRecognizedException.getMessage());
        //  pluginNotRecognizedException.printStackTrace();

        // throw new CantStartPlatformException();
        // }

        /**
         * ----------------------------------
         * Plugin Wallet Contacts Middleware
         * ----------------------------------
         *
         * I will give the Wallet Contacts Middleware access to the Database System and to the Error Manager
         */

        Plugin walletContactsMiddleware = ((MiddlewareLayer) mMiddlewareLayer).getWalletContactsPlugin();

        ((DealsWithErrors) walletContactsMiddleware).setErrorManager((ErrorManager) errorManager);
        ((DealsWithPluginDatabaseSystem) walletContactsMiddleware).setPluginDatabaseSystem(databaseSystemOs.getPluginDatabaseSystem());
        corePlatformContext.addPlugin(walletContactsMiddleware, Plugins.BITDUBAI_WALLET_CONTACTS_MIDDLEWARE);

        try {
            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */
            UUID pluginID = pluginsIdentityManager.getPluginId(walletContactsMiddleware);
            (walletContactsMiddleware).setId(pluginID);
            try {
                ((Service) walletContactsMiddleware).start();
            } catch (CantStartPluginException cantStartPluginException) {
                System.err.println("CantStartPluginException: " + cantStartPluginException.getMessage() + cantStartPluginException.getPlugin().getKey());
                cantStartPluginException.printStackTrace();
                /**
                 * For now, we will take this plugin as a essential for the platform itself to be running so if it can not
                 * start, then the platform wont start either. In the future we will review this policy.
                 * * * 
                 */
                throw new CantStartPlatformException();
            } catch (Exception e){
                ((ErrorManager) errorManager).reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, e);
                /**
                 * This is worse than the previous catch since the plugin didn't even throw an expected exception.
                 * * *
                 */
            }
        } catch (PluginNotRecognizedException pluginNotRecognizedException) {
            System.err.println("PluginNotRecognizedException: " + pluginNotRecognizedException.getMessage());
            pluginNotRecognizedException.printStackTrace();
            throw new CantStartPlatformException();
        }


        /**
         * ----------------------------------
         * Plugin Bitcoin Crypto Vault
         * ----------------------------------
         * * * *
         */



        /**
         * I will give the Bitcoin Crypto Vault access to the File System and to the Event Manager
         */

        Plugin bitcoinCryptoVault = ((CryptoVaultLayer) mCryptoVaultLayer).getmBitcoin();


        ((DealsWithDeviceUsers) bitcoinCryptoVault).setDeviceUserManager((DeviceUserManager) deviceUser);
        ((DealsWithErrors) bitcoinCryptoVault).setErrorManager((ErrorManager) errorManager);
        ((DealsWithPluginFileSystem) bitcoinCryptoVault).setPluginFileSystem(fileSystemOs.getPlugInFileSystem());
        ((DealsWithEvents) bitcoinCryptoVault).setEventManager((EventManager) eventManager);
        ((DealsWithPluginDatabaseSystem) bitcoinCryptoVault).setPluginDatabaseSystem(databaseSystemOs.getPluginDatabaseSystem());

        corePlatformContext.addPlugin(bitcoinCryptoVault, Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT);

        try
        {

            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */

            UUID pluginID = pluginsIdentityManager.getPluginId(bitcoinCryptoVault);
            (bitcoinCryptoVault).setId(pluginID);

            try {
                ((Service) bitcoinCryptoVault).start();
            }
            catch (CantStartPluginException cantStartPluginException) {

                System.err.println("CantStartPluginException: " + cantStartPluginException.getMessage() + cantStartPluginException.getPlugin().getKey());
                cantStartPluginException.printStackTrace();

                /**
                 * For now, we will take this plugin as a essential for the platform itself to be running so if it can not
                 * start, then the platform wont start either. In the future we will review this policy.
                 * * *
                 */

                throw new CantStartPlatformException();
            }
            catch (Exception e){

                ((ErrorManager) errorManager).reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, e);

                /**
                 * This is worse than the previous catch since the plugin didn't even throw an expected exception.
                 * * *
                 */

            }

        }
        catch (PluginNotRecognizedException pluginNotRecognizedException)
        {
            System.err.println("PluginNotRecognizedException: " + pluginNotRecognizedException.getMessage());
            pluginNotRecognizedException.printStackTrace();

            throw new CantStartPlatformException();
        }





        /**
         * ----------------------------------
         * Plugin Incoming Crypto Transaction
         * ----------------------------------
         * * * *
         */



        /**
         * I will give the Incoming Crypto Transaction access to the File System and to the Event Manager
         */

        Plugin incomingCryptoTransaction = ((CryptoRouterLayer) mCryptoRouterLayer).getIncomingCrypto();

        ((DealsWithActorAddressBook) incomingCryptoTransaction).setActorAddressBookManager((ActorAddressBookManager) actorAddressBookCrypto);
        ((DealsWithCryptoVault) incomingCryptoTransaction).setCryptoVaultManager((CryptoVaultManager) bitcoinCryptoVault);
        ((DealsWithErrors) incomingCryptoTransaction).setErrorManager((ErrorManager) errorManager);
        ((DealsWithEvents) incomingCryptoTransaction).setEventManager((EventManager) eventManager);
        ((DealsWithPluginDatabaseSystem) incomingCryptoTransaction).setPluginDatabaseSystem(databaseSystemOs.getPluginDatabaseSystem());

        corePlatformContext.addPlugin(incomingCryptoTransaction, Plugins.BITDUBAI_INCOMING_EXTRA_USER_TRANSACTION);

        try {
            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */

            UUID pluginID = pluginsIdentityManager.getPluginId(incomingCryptoTransaction);
            (incomingCryptoTransaction).setId(pluginID);

            try {
                ((Service) incomingCryptoTransaction).start();
            } catch (CantStartPluginException cantStartPluginException) {

                System.err.println("CantStartPluginException: " + cantStartPluginException.getMessage() + cantStartPluginException.getPlugin().getKey());
                cantStartPluginException.printStackTrace();

                /**
                 * For now, we will take this plugin as a essential for the platform itself to be running so if it can not
                 * start, then the platform wont start either. In the future we will review this policy.
                 * * * 
                 */

                throw new CantStartPlatformException();
            } catch (Exception e) {

                ((ErrorManager) errorManager).reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, e);

                /**
                 * This is worse than the previous catch since the plugin didn't even throw an expected exception.
                 * * *
                 */

            }
        }
        catch (PluginNotRecognizedException pluginNotRecognizedException)
        {
            System.err.println("PluginNotRecognizedException: " + pluginNotRecognizedException.getMessage());
            pluginNotRecognizedException.printStackTrace();

            throw new CantStartPlatformException();
        }






        /**
         * ----------------------------------
         * Plugin Incoming Extra User Transaction
         * ----------------------------------
         * * * * 
         */



        /**
         * I will give the From Extra User Transaction access to the File System and to the Event Manager
         */

        Plugin incomingExtraUserTransaction = ((TransactionLayer) mTransactionLayer).getIncomingExtraUserPlugin();

        ((DealsWithPluginFileSystem) incomingExtraUserTransaction).setPluginFileSystem(fileSystemOs.getPlugInFileSystem());
        ((DealsWithEvents) incomingExtraUserTransaction).setEventManager((EventManager) eventManager);
        corePlatformContext.addPlugin(incomingExtraUserTransaction, Plugins.BITDUBAI_INCOMING_EXTRA_USER_TRANSACTION);

        try
        {

            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */

            UUID pluginID = pluginsIdentityManager.getPluginId(incomingExtraUserTransaction);
            (incomingExtraUserTransaction).setId(pluginID);

            try {
                ((Service) incomingExtraUserTransaction).start();
            }
            catch (CantStartPluginException cantStartPluginException) {

                System.err.println("CantStartPluginException: " + cantStartPluginException.getMessage() + cantStartPluginException.getPlugin().getKey());
                cantStartPluginException.printStackTrace();

                /**
                 * For now, we will take this plugin as a essential for the platform itself to be running so if it can not
                 * start, then the platform wont start either. In the future we will review this policy.
                 * * * 
                 */

                throw new CantStartPlatformException();
            }
            catch (Exception e){

                ((ErrorManager) errorManager).reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, e);

                /**
                 * This is worse than the previous catch since the plugin didn't even throw an expected exception.
                 * * *
                 */

            }
        }
        catch (PluginNotRecognizedException pluginNotRecognizedException)
        {


            System.err.println("PluginNotRecognizedException: " + pluginNotRecognizedException.getMessage());
            pluginNotRecognizedException.printStackTrace();

            throw new CantStartPlatformException();
        }



        /**
         * ----------------------------------
         * Plugin Incoming Intra User Transaction
         * ----------------------------------
         * * * *
         */



        /**
         * I will give the Inter User Transaction access to the File System and to the Event Manager
         */

        Plugin incomingIntraUserTransaction = ((TransactionLayer) mTransactionLayer).getIncomingIntraUserPlugin();

        ((DealsWithPluginFileSystem) incomingIntraUserTransaction).setPluginFileSystem(fileSystemOs.getPlugInFileSystem());
        ((DealsWithEvents) incomingIntraUserTransaction).setEventManager((EventManager) eventManager);

        corePlatformContext.addPlugin(incomingIntraUserTransaction, Plugins.BITDUBAI_INCOMING_INTRA_USER_TRANSACTION);

        try
        {

            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */

            UUID pluginID = pluginsIdentityManager.getPluginId(incomingIntraUserTransaction);
            (incomingIntraUserTransaction).setId(pluginID);

            try {
                ((Service) incomingIntraUserTransaction).start();
            }
            catch (CantStartPluginException cantStartPluginException) {

                System.err.println("CantStartPluginException: " + cantStartPluginException.getMessage() + cantStartPluginException.getPlugin().getKey());
                cantStartPluginException.printStackTrace();

                /**
                 * For now, we will take this plugin as a essential for the platform itself to be running so if it can not
                 * start, then the platform wont start either. In the future we will review this policy.
                 * * * 
                 */

                throw new CantStartPlatformException();
            }
            catch (Exception e){

                ((ErrorManager) errorManager).reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, e);

                /**
                 * This is worse than the previous catch since the plugin didn't even throw an expected exception.
                 * * *
                 */

            }

        }
        catch (PluginNotRecognizedException pluginNotRecognizedException)
        {


            System.err.println("PluginNotRecognizedException: " + pluginNotRecognizedException.getMessage());
            pluginNotRecognizedException.printStackTrace();

            throw new CantStartPlatformException();
        }



        /**
         * ----------------------------------
         * Plugin Inter Wallet Transaction
         * ----------------------------------
         * * * *
         */



        /**
         * I will give the Inter Wallet Transaction access to the File System and to the Event Manager
         */

        Plugin interWalletTransaction = ((TransactionLayer) mTransactionLayer).getInterWalletPlugin();

        ((DealsWithPluginFileSystem) interWalletTransaction).setPluginFileSystem(fileSystemOs.getPlugInFileSystem());
        ((DealsWithEvents) interWalletTransaction).setEventManager((EventManager) eventManager);

        corePlatformContext.addPlugin(interWalletTransaction, Plugins.BITDUBAI_INTER_WALLET_TRANSACTION);

        try
        {

            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */

            UUID pluginID = pluginsIdentityManager.getPluginId(interWalletTransaction);
            (interWalletTransaction).setId(pluginID);

            try {
                ((Service) interWalletTransaction).start();
            }
            catch (CantStartPluginException cantStartPluginException) {

                System.err.println("CantStartPluginException: " + cantStartPluginException.getMessage() + cantStartPluginException.getPlugin().getKey());
                cantStartPluginException.printStackTrace();

                /**
                 * For now, we will take this plugin as a essential for the platform itself to be running so if it can not
                 * start, then the platform wont start either. In the future we will review this policy.
                 * * * 
                 */

                throw new CantStartPlatformException();
            }
            catch (Exception e){

                ((ErrorManager) errorManager).reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, e);

                /**
                 * This is worse than the previous catch since the plugin didn't even throw an expected exception.
                 * * *
                 */

            }

        }
        catch (PluginNotRecognizedException pluginNotRecognizedException)
        {


            System.err.println("PluginNotRecognizedException: " + pluginNotRecognizedException.getMessage());
            pluginNotRecognizedException.printStackTrace();

            throw new CantStartPlatformException();
        }

        /**
         * ----------------------------------
         * Plugin Outgoing Extra User Transaction
         * ----------------------------------
         * * * *
         */



        /**
         * I will give the Outgoing Extra User Transaction access to the File System and to the Event Manager
         */

        Plugin outgoingExtraUserTransaction = ((TransactionLayer) mTransactionLayer).getOutgoingExtraUserPlugin();

        ((DealsWithPluginFileSystem) outgoingExtraUserTransaction).setPluginFileSystem(fileSystemOs.getPlugInFileSystem());
        ((DealsWithEvents) outgoingExtraUserTransaction).setEventManager((EventManager) eventManager);

        corePlatformContext.addPlugin(outgoingExtraUserTransaction, Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION);

        try
        {

            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */

            UUID pluginID = pluginsIdentityManager.getPluginId(outgoingExtraUserTransaction);
            (outgoingExtraUserTransaction).setId(pluginID);

            try {
                ((Service) outgoingExtraUserTransaction).start();
            }
            catch (CantStartPluginException cantStartPluginException) {

                System.err.println("CantStartPluginException: " + cantStartPluginException.getMessage() + cantStartPluginException.getPlugin().getKey());
                cantStartPluginException.printStackTrace();

                /**
                 * For now, we will take this plugin as a essential for the platform itself to be running so if it can not
                 * start, then the platform wont start either. In the future we will review this policy.
                 * * * 
                 */

                throw new CantStartPlatformException();
            }
            catch (Exception e){

                ((ErrorManager) errorManager).reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, e);

                /**
                 * This is worse than the previous catch since the plugin didn't even throw an expected exception.
                 * * *
                 */

            }

        }
        catch (PluginNotRecognizedException pluginNotRecognizedException)
        {


            System.err.println("PluginNotRecognizedException: " + pluginNotRecognizedException.getMessage());
            pluginNotRecognizedException.printStackTrace();

            throw new CantStartPlatformException();
        }



        /**
         * ----------------------------------
         * Plugin Outgoing Device user Transaction
         * ----------------------------------
         * * * *
         */



        /**
         * I will give the Outgoing Device User Transaction access to the File System and to the Event Manager
         */

        Plugin outgoingDeviceUserTransaction = ((TransactionLayer) mTransactionLayer).getOutgoingDeviceUserPlugin();

        ((DealsWithPluginFileSystem) outgoingDeviceUserTransaction).setPluginFileSystem(fileSystemOs.getPlugInFileSystem());
        ((DealsWithEvents) outgoingDeviceUserTransaction).setEventManager((EventManager) eventManager);

        corePlatformContext.addPlugin(outgoingDeviceUserTransaction, Plugins.BITDUBAI_OUTGOING_DEVICE_USER_TRANSACTION);

        try
        {

            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */

            UUID pluginID = pluginsIdentityManager.getPluginId(outgoingDeviceUserTransaction);
            (outgoingDeviceUserTransaction).setId(pluginID);

            try {
                ((Service) outgoingDeviceUserTransaction).start();
            }
            catch (CantStartPluginException cantStartPluginException) {

                System.err.println("CantStartPluginException: " + cantStartPluginException.getMessage() + cantStartPluginException.getPlugin().getKey());
                cantStartPluginException.printStackTrace();

                /**
                 * For now, we will take this plugin as a essential for the platform itself to be running so if it can not
                 * start, then the platform wont start either. In the future we will review this policy.
                 * * * 
                 */

                throw new CantStartPlatformException();
            }
            catch (Exception e){

                ((ErrorManager) errorManager).reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, e);

                /**
                 * This is worse than the previous catch since the plugin didn't even throw an expected exception.
                 * * *
                 */

            }

        }
        catch (PluginNotRecognizedException pluginNotRecognizedException)
        {


            System.err.println("PluginNotRecognizedException: " + pluginNotRecognizedException.getMessage());
            pluginNotRecognizedException.printStackTrace();

            throw new CantStartPlatformException();
        }


>>>>>>> upstream/master

        /**
         * -----------------------------
         * Plugin Bitcoin Wallet Basic Wallet
         * -----------------------------
         */
        // Plugin bitcoinWalletBasicWallet = ((BasicWalletLayer) mBasicWalletLayer).getBitcoinWallet();
        // setPluginReferencesAndStart(bitcoinWalletBasicWallet, Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET);

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
         * Plugin Bitcoin Crypto Vault
         * ----------------------------------
         */
        Plugin bitcoinCryptoVault = ((CryptoVaultLayer) mCryptoVaultLayer).getmBitcoin();
        setPluginReferencesAndStart(bitcoinCryptoVault, Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT);

        /**
         * ----------------------------------
         * Plugin Incoming Crypto Transaction
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

    }

    private void setPluginReferencesAndStart(Plugin plugin, Plugins descriptor) {

        ErrorManager errorManager = (ErrorManager) corePlatformContext.getAddon(Addons.ERROR_MANAGER);

        try {
            if (plugin instanceof DealsWithActorAddressBook)
                ((DealsWithActorAddressBook) plugin).setActorAddressBookManager((ActorAddressBookManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO));

            //  if (plugin instanceof DealsWithBitcoinWallet)
            //      ((DealsWithBitcoinWallet) plugin).setBitcoinWalletManager((BitcoinWalletManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET));
            // TODO COMENTADO HASTA QUE EXISTA la referencia

            if (plugin instanceof DealsWithCryptoVault)
                ((DealsWithCryptoVault) plugin).setCryptoVaultManager((CryptoVaultManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT));

            if (plugin instanceof DealsWithErrors)
                ((DealsWithErrors) plugin).setErrorManager(errorManager);

            if (plugin instanceof DealsWithEvents)
                ((DealsWithEvents) plugin).setEventManager((EventManager) corePlatformContext.getAddon(Addons.EVENT_MANAGER));

            if (plugin instanceof DealsWithExtraUsers)
                ((DealsWithExtraUsers) plugin).setExtraUserManager((ExtraUserManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_USER_EXTRA_USER));

            if (plugin instanceof DealsWithNicheWalletTypeCryptoWallet)
                ((DealsWithNicheWalletTypeCryptoWallet) plugin).setNicheWalletTypeCryptoWalletManager((CryptoWalletManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_CRYPTO_WALLET_NICHE_WALLET_TYPE));

            if (plugin instanceof DealsWithOutgoingExtraUser)
                ((DealsWithOutgoingExtraUser) plugin).setOutgoingExtraUserManager((OutgoingExtraUserManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION));

            if (plugin instanceof DealsWithPluginFileSystem)
                ((DealsWithPluginFileSystem) plugin).setPluginFileSystem(fileSystemOs.getPlugInFileSystem());

            if (plugin instanceof DealsWithPluginDatabaseSystem)
                ((DealsWithPluginDatabaseSystem) plugin).setPluginDatabaseSystem(databaseSystemOs.getPluginDatabaseSystem());

            if (plugin instanceof DealsWithWalletAddressBook)
                ((DealsWithWalletAddressBook) plugin).setWalletAddressBookManager((WalletAddressBookManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_WALLET_ADDRESS_BOOK_CRYPTO));

            if (plugin instanceof DealsWithWalletContacts)
                ((DealsWithWalletContacts) plugin).setWalletContactsManager((WalletContactsManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_WALLET_CONTACTS_MIDDLEWARE));

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

        System.out.println("********************************* Pasa el plugin: " + descriptor.toString());
    }
}
