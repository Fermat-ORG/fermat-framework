package com.bitdubai.wallet_platform_core;

import com.bitdubai.wallet_platform_api.*;
import com.bitdubai.wallet_platform_api.layer.CantStartLayerException;
import com.bitdubai.wallet_platform_api.layer.PlatformLayer;

import com.bitdubai.wallet_platform_api.layer._1_definition.enums.Addons;
import com.bitdubai.wallet_platform_api.layer._1_definition.enums.Plugins;
import com.bitdubai.wallet_platform_api.layer._1_definition.event.DealWithEventMonitor;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.EventManager;
import com.bitdubai.wallet_platform_api.layer._3_os.*;
import com.bitdubai.wallet_platform_api.layer._3_os.file_system.*;
import com.bitdubai.wallet_platform_api.layer._9_network_service.NetworkService;
import com.bitdubai.wallet_platform_core.layer._11_transaction.TransactionLayer;
import com.bitdubai.wallet_platform_core.layer._2_platform_service.PlatformServiceLayer;

import com.bitdubai.wallet_platform_core.layer._1_definition.DefinitionLayer;
import com.bitdubai.wallet_platform_core.layer._3_os.OsLayer;
import com.bitdubai.wallet_platform_core.layer._4_user.UserLayer;
import com.bitdubai.wallet_platform_core.layer._5_license.LicenseLayer;
import com.bitdubai.wallet_platform_core.layer._6_world.WorldLayer;
import com.bitdubai.wallet_platform_core.layer._7_crypto_network.CryptoNetworkLayer;
import com.bitdubai.wallet_platform_api.layer._7_crypto_network.CryptoNetworks;
import com.bitdubai.wallet_platform_core.layer._8_communication.CommunicationLayer;
import com.bitdubai.wallet_platform_core.layer._9_network_service.NetworkServiceLayer;
import com.bitdubai.wallet_platform_core.layer._10_middleware.MiddlewareLayer;
import com.bitdubai.wallet_platform_core.layer._12_module.ModuleLayer;
import com.bitdubai.wallet_platform_core.layer._13_agent.AgentLayer;

import java.util.UUID;

/**
 * Created by ciencias on 20.01.15.
 */
public class Platform  {



    PlatformLayer mDefinitionLayer = new DefinitionLayer();
    PlatformLayer mEventLayer = new PlatformServiceLayer();
    PlatformLayer mOsLayer = new OsLayer();
    PlatformLayer mUserLayer = new UserLayer();
    PlatformLayer mLicesnseLayer = new LicenseLayer();
    PlatformLayer mWorldLayer = new WorldLayer();
    PlatformLayer mCryptoNetworkLayer = new CryptoNetworkLayer();
    PlatformLayer mCommunicationLayer = new CommunicationLayer();
    PlatformLayer mNetworkServiceLayer = new NetworkServiceLayer();
    PlatformLayer mTransactionLayer = new TransactionLayer();
    PlatformLayer mMiddlewareayer = new MiddlewareLayer();
    PlatformLayer mModuleLayer = new ModuleLayer();
    PlatformLayer mAgentLayer = new AgentLayer();



    public PlatformLayer getDefinitionLayer() {
        return mDefinitionLayer;
    }

    public PlatformLayer getEventLayer() {
        return mEventLayer;
    }

    public PlatformLayer getOsLayer() {
        return mOsLayer;
    }

    public PlatformLayer getUserLayer() {
        return mUserLayer;
    }

    public PlatformLayer getLicesnseLayer() {
        return mLicesnseLayer;
    }

    public PlatformLayer getWorldLayer() {
        return mWorldLayer;
    }

    public PlatformLayer getCryptoNetworkLayer() {
        return mCryptoNetworkLayer;
    }

    public PlatformLayer getCommunicationLayer() {
        return mCommunicationLayer;
    }

    public PlatformLayer getNetworkServiceLayer() {
        return mNetworkServiceLayer;
    }

    public PlatformLayer getMiddlewareayer() {
        return mMiddlewareayer;
    }

    public PlatformLayer getModuleLayer() {
        return mModuleLayer;
    }

    public PlatformLayer getAgentLayer() {
        return mAgentLayer;
    }



    PlatformEventMonitor eventMonitor;
    
    PluginsIdentityManager pluginsIdentityManager;


    CorePlatformContext corePlatformContext;
    
    Object osContext;
    Os os;

    
    public CorePlatformContext getCorePlatformContext() {
        return corePlatformContext;
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

    public void setOsContext(Object osContext){
        this.osContext = osContext;
    }

    /**
     * An unresolved bug in either Android or Gradle does not allow us to create the os object on a library outside the
     * main module. While this situation persists, we will create it inside the wallet package and receive it throw this
     * method.
     */

    public void setOs (Os os){
        this.os = os;
    }


    
    
    
    
    
    
    
    
    
    
    
    
    
    public void start() throws CantStartPlatformException {

        

        /**
         * -----------------------------------------------------------------------------------------------------------
         * Layers initialization
         * -----------------------------------------------------------------------------------------------------------
         * * * * 
         */
        
        /**
         * Here I will be starting all the platforms layers. It is required that none of them fails. That does not mean
         * that a layer will have at least one service to offer. It depends on each layer. If one believes its lack of
         * services prevent the whole platform to start, then it will throw an exception that will effectively prevent 
         * the platform to start.
         */

        try {

            mDefinitionLayer.start();
            mEventLayer.start();
            //mOsLayer.start(); // Due to an Android bug is not possible to handle this here.
            mUserLayer.start();
            mLicesnseLayer.start();
            mWorldLayer.start();
            mCryptoNetworkLayer.start();
            mCommunicationLayer.start();
            mNetworkServiceLayer.start();
            mMiddlewareayer.start();
            mModuleLayer.start();
            mAgentLayer.start();
            mTransactionLayer.start();
        }
        catch (CantStartLayerException cantStartLayerException) {
            System.err.println("CantStartLayerException: " + cantStartLayerException.getMessage());
            cantStartLayerException.printStackTrace();
            throw new CantStartPlatformException();
        }


        /**
         * Some Layers need the Platform context.
         */
        
        ((DealsWithPlatformContext) mCommunicationLayer).setPlatformContext(corePlatformContext);

        
        
        
        
        
        
        
        
        
        
        
        

        /**
         * -----------------------------------------------------------------------------------------------------------
         * Addons initialization
         * -----------------------------------------------------------------------------------------------------------
         * * * * 
         */





        /**
         * -----------------------------
         * Addon Error Manager
         * -----------------------------
         * * * * 
         */

        Service errorManager = (Service) ((PlatformServiceLayer) mEventLayer).getErrorManager();
        corePlatformContext.addAddon((Addon) errorManager, Addons.ERROR_MANAGER);


        
        /**
         * -----------------------------
         * Addon Event Manager
         * -----------------------------
         * * * *
         */



        Service eventManager = (Service) ((PlatformServiceLayer) mEventLayer).getEventManager();
        corePlatformContext.addAddon((Addon) eventManager, Addons.EVENT_MANAGER);

        /**
         * I will give the Event Monitor to the Event Manager, in order to allow it to monitor listeners exceptions.
         */

        ((DealWithEventMonitor) eventManager).setEventMonitor(eventMonitor);



        /**
         * -----------------------------
         * Addon Os
         * -----------------------------
         * * * * 
         */

        
        
        /**
         * The OS and Event Manager will need to be handled to several other objects.
         */

        if (os == null) {
            /**
             * In Android, an unresolved bug doesn't allow us to create the Os object where it should be created and we
             * receive it from the wallet module. If we did not receive it we follow the standard procedure.
             */

            this.os  = ((OsLayer) mOsLayer).getOs();

            /**
             * I will set the osContext to the Os in order to enable access to the underlying Os objects.
             */

            os.setContext(this.osContext);
        }


        
        /**
         * -----------------------------
         * Addon User Manager
         * -----------------------------
         * * * * 
         */ 


        
        /**
         * I will give the User Manager access to the File System so it can load and save user information from
         * persistent media.
         */

        Service userManager = (Service) ((UserLayer) mUserLayer).getUserManager();

        ((DealsWithPlatformFileSystem) userManager).setPlatformFileSystem(os.getPlatformFileSystem());
        ((DealsWithEvents) userManager).setEventManager((EventManager) eventManager);

        corePlatformContext.addAddon((Addon) userManager, Addons.USER_MANAGER);
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        /**
         * -----------------------------------------------------------------------------------------------------------
         * Plugins initialization
         * -----------------------------------------------------------------------------------------------------------
         * * * * 
         */

        /**
         * I will initialize the Plugin Manager, the one who assigns identities to each plug in.
         */

        try
        {
            pluginsIdentityManager = new PluginsIdentityManager(os.getPlatformFileSystem());
        }
        catch (CantInitializePluginsManagerException cantInitializePluginsManagerException) {
            System.err.println("CantInitializePluginsManager: " + cantInitializePluginsManagerException.getMessage());
            cantInitializePluginsManagerException.printStackTrace();
            throw new CantStartPlatformException();
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

        Plugin WorldService = ((WorldLayer)  mWorldLayer).getmCryptoIndex();

        ((DealsWithPluginFileSystem) WorldService).setPluginFileSystem(os.getPlugInFileSystem());
        ((DealsWithEvents) WorldService).setEventManager((EventManager) eventManager);

        corePlatformContext.addPlugin(WorldService, Plugins.CRYPTO_INDEX);
        
        try
        {
            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */
        
            UUID pluginID = pluginsIdentityManager.getPluginId(WorldService);
            (WorldService).setId(pluginID);

            ((Service) WorldService).start();
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
        Plugin cryptoNetworkService = ((CryptoNetworkLayer) mCryptoNetworkLayer).getCryptoNetwork(CryptoNetworks.BITCOIN);

        ((DealsWithPluginFileSystem) cryptoNetworkService).setPluginFileSystem(os.getPlugInFileSystem());
        ((DealsWithEvents) cryptoNetworkService).setEventManager((EventManager) eventManager);

        corePlatformContext.addPlugin(cryptoNetworkService, Plugins.BITCOIN_CRYPTO_NETWORK);

        try
        {
            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */

            UUID pluginID = pluginsIdentityManager.getPluginId(cryptoNetworkService);
            (cryptoNetworkService).setId(pluginID);

            ((Service)cryptoNetworkService).start();
        }
        catch (PluginNotRecognizedException pluginNotRecognizedException)
        {
            /**
             * Even if it is not desirable, the platform can still start without one crypto network. I will simply ask
             * this module to stop running. 
             */
            System.err.println("PluginNotRecognizedException: " + pluginNotRecognizedException.getMessage());
            pluginNotRecognizedException.printStackTrace();

            ((Service)cryptoNetworkService).stop();
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
        
        ((DealsWithPluginFileSystem) cloudCommunication).setPluginFileSystem(os.getPlugInFileSystem());
        ((DealsWithEvents) cloudCommunication).setEventManager((EventManager) eventManager);
        
        corePlatformContext.addPlugin(cloudCommunication, Plugins.CLOUD_CHANNEL);
        
        try
        {

            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */

            UUID pluginID = pluginsIdentityManager.getPluginId(cloudCommunication);
            (cloudCommunication).setId(pluginID);

            ((Service) cloudCommunication).start();
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

        ((DealsWithPluginFileSystem) bankNotesNetworkService).setPluginFileSystem(os.getPlugInFileSystem());
        ((DealsWithEvents) bankNotesNetworkService).setEventManager((EventManager) eventManager);

        corePlatformContext.addPlugin(bankNotesNetworkService, Plugins.BANK_NOTES_NETWORK_SERVICE);

        try
        {

            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */

            UUID pluginID = pluginsIdentityManager.getPluginId(bankNotesNetworkService);
            (bankNotesNetworkService).setId(pluginID);

            ((Service) bankNotesNetworkService).start();
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

        ((DealsWithPluginFileSystem) walletResourcesNetworkService).setPluginFileSystem(os.getPlugInFileSystem());
        ((DealsWithEvents) walletResourcesNetworkService).setEventManager((EventManager) eventManager);

        corePlatformContext.addPlugin(walletResourcesNetworkService, Plugins.BANK_NOTES_NETWORK_SERVICE);

        try
        {

            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */

            UUID pluginID = pluginsIdentityManager.getPluginId(walletResourcesNetworkService);
            (walletResourcesNetworkService).setId(pluginID);

            ((Service) walletResourcesNetworkService).start();
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

        ((DealsWithPluginFileSystem) walletStoreNetworkService).setPluginFileSystem(os.getPlugInFileSystem());
        ((DealsWithEvents) walletStoreNetworkService).setEventManager((EventManager) eventManager);

        corePlatformContext.addPlugin(walletStoreNetworkService, Plugins.WALLET_STORE_NETWORK_SERVICE);

        try
        {

            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */

            UUID pluginID = pluginsIdentityManager.getPluginId(walletStoreNetworkService);
            (walletStoreNetworkService).setId(pluginID);

            ((Service) walletStoreNetworkService).start();
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

        
        
        Plugin appRuntimeMiddleware = ((MiddlewareLayer) mMiddlewareayer).getAppRuntimePlugin();

        ((DealsWithPluginFileSystem) appRuntimeMiddleware).setPluginFileSystem(os.getPlugInFileSystem());
        ((DealsWithEvents) appRuntimeMiddleware).setEventManager((EventManager) eventManager);

        corePlatformContext.addPlugin(appRuntimeMiddleware, Plugins.APP_RUNTIME_MIDDLEWARE);

        try
        {

            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */

            UUID pluginID = pluginsIdentityManager.getPluginId(appRuntimeMiddleware);
            (appRuntimeMiddleware).setId(pluginID);

            ((Service) appRuntimeMiddleware).start();
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

        Plugin bankNotesMiddleware = ((MiddlewareLayer) mMiddlewareayer).getBankNotesPlugin();

        ((DealsWithPluginFileSystem) bankNotesMiddleware).setPluginFileSystem(os.getPlugInFileSystem());
        ((DealsWithEvents) bankNotesMiddleware).setEventManager((EventManager) eventManager);

        corePlatformContext.addPlugin(bankNotesMiddleware, Plugins.BANK_NOTES_MIDDLEWARE);

        try
        {

            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */

            UUID pluginID = pluginsIdentityManager.getPluginId(bankNotesMiddleware);
            (bankNotesMiddleware).setId(pluginID);

            ((Service) bankNotesMiddleware).start();
        }
        catch (PluginNotRecognizedException pluginNotRecognizedException)
        {


            System.err.println("PluginNotRecognizedException: " + pluginNotRecognizedException.getMessage());
            pluginNotRecognizedException.printStackTrace();

            throw new CantStartPlatformException();
        }




        /**
         * -----------------------------
         * Plugin Wallet Middleware
         * -----------------------------
         * * * * 
         */



        /**
         * I will give the Wallet Middleware access to the File System and to the Event Manager
         */

        Plugin walletMiddleware = ((MiddlewareLayer) mMiddlewareayer).getWalletPlugin();

        ((DealsWithPluginFileSystem) walletMiddleware).setPluginFileSystem(os.getPlugInFileSystem());
        ((DealsWithEvents) walletMiddleware).setEventManager((EventManager) eventManager);

        corePlatformContext.addPlugin(walletMiddleware, Plugins.WALLET_MIDDLEWARE);

        try
        {

            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */

            UUID pluginID = pluginsIdentityManager.getPluginId(walletMiddleware);
            (walletMiddleware).setId(pluginID);

            ((Service) walletMiddleware).start();
        }
        catch (PluginNotRecognizedException pluginNotRecognizedException)
        {


            System.err.println("PluginNotRecognizedException: " + pluginNotRecognizedException.getMessage());
            pluginNotRecognizedException.printStackTrace();

            throw new CantStartPlatformException();
        }

        /**
         * ----------------------------------
         * Plugin Wallet Contacts Middleware
         * ----------------------------------
         * * * *
         */



        /**
         * I will give the Wallet Contacts Middleware access to the File System and to the Event Manager
         */

        Plugin walletContactsMiddleware = ((MiddlewareLayer) mMiddlewareayer).getWalletContactsPlugin();

        ((DealsWithPluginFileSystem) walletContactsMiddleware).setPluginFileSystem(os.getPlugInFileSystem());
        ((DealsWithEvents) walletContactsMiddleware).setEventManager((EventManager) eventManager);
        corePlatformContext.addPlugin(walletContactsMiddleware, Plugins.WALLET_CONTACTS_MIDDLEWARE);

        try
        {

            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */

            UUID pluginID = pluginsIdentityManager.getPluginId(walletContactsMiddleware);
            (walletContactsMiddleware).setId(pluginID);

            ((Service) walletContactsMiddleware).start();
        }
        catch (PluginNotRecognizedException pluginNotRecognizedException)
        {


            System.err.println("PluginNotRecognizedException: " + pluginNotRecognizedException.getMessage());
            pluginNotRecognizedException.printStackTrace();

            throw new CantStartPlatformException();
        }


        /**
         * ----------------------------------
         * Plugin From Extra User Transaction
         * ----------------------------------
         * * * * 
         */

        
        
        /**
         * I will give the From Extra User Transaction access to the File System and to the Event Manager
         */

        Plugin fromExtraUserTransaction = ((TransactionLayer) mTransactionLayer).getFromExtraUserPlugin();

        ((DealsWithPluginFileSystem) fromExtraUserTransaction).setPluginFileSystem(os.getPlugInFileSystem());
        ((DealsWithEvents) fromExtraUserTransaction).setEventManager((EventManager) eventManager);
        corePlatformContext.addPlugin(fromExtraUserTransaction, Plugins.FROM_EXTRA_USER_TRANSACTION);

        try
        {

            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */

            UUID pluginID = pluginsIdentityManager.getPluginId(fromExtraUserTransaction);
            (fromExtraUserTransaction).setId(pluginID);

            ((Service) fromExtraUserTransaction).start();
        }
        catch (PluginNotRecognizedException pluginNotRecognizedException)
        {


            System.err.println("PluginNotRecognizedException: " + pluginNotRecognizedException.getMessage());
            pluginNotRecognizedException.printStackTrace();

            throw new CantStartPlatformException();
        }

        
        
        /**
         * ----------------------------------
         * Plugin Inter User Transaction
         * ----------------------------------
         * * * *
         */

        
        
        /**
         * I will give the Inter User Transaction access to the File System and to the Event Manager
         */

        Plugin interUserTransaction = ((TransactionLayer) mTransactionLayer).getInterUserPlugin();

        ((DealsWithPluginFileSystem) interUserTransaction).setPluginFileSystem(os.getPlugInFileSystem());
        ((DealsWithEvents) interUserTransaction).setEventManager((EventManager) eventManager);

        corePlatformContext.addPlugin(interUserTransaction, Plugins.INTER_USER_TRANSACTION);

        try
        {

            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */

            UUID pluginID = pluginsIdentityManager.getPluginId(interUserTransaction);
            (interUserTransaction).setId(pluginID);

            ((Service) interUserTransaction).start();
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

        ((DealsWithPluginFileSystem) interWalletTransaction).setPluginFileSystem(os.getPlugInFileSystem());
        ((DealsWithEvents) interWalletTransaction).setEventManager((EventManager) eventManager);

        corePlatformContext.addPlugin(interWalletTransaction, Plugins.INTER_WALLET_TRANSACTION);

        try
        {

            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */

            UUID pluginID = pluginsIdentityManager.getPluginId(interWalletTransaction);
            (interWalletTransaction).setId(pluginID);

            ((Service) interWalletTransaction).start();
        }
        catch (PluginNotRecognizedException pluginNotRecognizedException)
        {


            System.err.println("PluginNotRecognizedException: " + pluginNotRecognizedException.getMessage());
            pluginNotRecognizedException.printStackTrace();

            throw new CantStartPlatformException();
        }

        /**
         * ----------------------------------
         * Plugin To Extra User Transaction
         * ----------------------------------
         * * * *
         */



        /**
         * I will give the To Extra User Transaction access to the File System and to the Event Manager
         */

        Plugin toExtraUserTransaction = ((TransactionLayer) mTransactionLayer).getToExtraUserPlugin();

        ((DealsWithPluginFileSystem) toExtraUserTransaction).setPluginFileSystem(os.getPlugInFileSystem());
        ((DealsWithEvents) toExtraUserTransaction).setEventManager((EventManager) eventManager);

        corePlatformContext.addPlugin(toExtraUserTransaction, Plugins.TO_EXTRA_USER_TRANSACTION);

        try
        {

            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */

            UUID pluginID = pluginsIdentityManager.getPluginId(interWalletTransaction);
            (interWalletTransaction).setId(pluginID);

            ((Service) toExtraUserTransaction).start();
        }
        catch (PluginNotRecognizedException pluginNotRecognizedException)
        {


            System.err.println("PluginNotRecognizedException: " + pluginNotRecognizedException.getMessage());
            pluginNotRecognizedException.printStackTrace();

            throw new CantStartPlatformException();
        }



        /**
         * -----------------------------
         * Plugin Wallet Manager
         * -----------------------------
         * * * * 
         */
        
        
        
        /**
         * I will give the Wallet Manager access to the File System and to the Event Manager
         */

        Plugin walletManager =  ((ModuleLayer) mModuleLayer).getWalletManager();

        ((DealsWithPluginFileSystem) walletManager).setPluginFileSystem(os.getPlugInFileSystem());
        ((DealsWithEvents) walletManager).setEventManager((EventManager) eventManager);

        corePlatformContext.addPlugin(walletManager, Plugins.WALLET_MANAGER_MODULE);
        
        try
        {

            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */

            UUID pluginID = pluginsIdentityManager.getPluginId(walletManager);
            (walletManager).setId(pluginID);

            ((Service) walletManager).start();
        }
        catch (PluginNotRecognizedException pluginNotRecognizedException)
        {
          
            /**
             * The wallet manager is a critical component for the platform to run. Whiteout it there is no platform.
             */
    
            System.err.println("PluginNotRecognizedException: " + pluginNotRecognizedException.getMessage());
            pluginNotRecognizedException.printStackTrace();

            throw new CantStartPlatformException();
        }



        /**
         * -----------------------------
         * Plugin Wallet Runtime
         * -----------------------------
         * * * * 
         */



        /**
         * I will give the Wallet Runtime access to the File System and to the Event Manager
         */

        Plugin walletRuntime =  ((ModuleLayer) mModuleLayer).getWalletRuntime();

        ((DealsWithPluginFileSystem) walletRuntime).setPluginFileSystem(os.getPlugInFileSystem());
        ((DealsWithEvents) walletRuntime).setEventManager((EventManager) eventManager);

        corePlatformContext.addPlugin(walletRuntime, Plugins.WALLET_RUNTIME_MODULE);

        try
        {

            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */

            UUID pluginID = pluginsIdentityManager.getPluginId(walletRuntime);
            (walletRuntime).setId(pluginID);

            ((Service) walletRuntime).start();
        }
        catch (PluginNotRecognizedException pluginNotRecognizedException)
        {

            System.err.println("PluginNotRecognizedException: " + pluginNotRecognizedException.getMessage());
            pluginNotRecognizedException.printStackTrace();

            throw new CantStartPlatformException();
        }






    }
}

