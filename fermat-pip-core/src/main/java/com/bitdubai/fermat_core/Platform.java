package com.bitdubai.fermat_core;

import com.bitdubai.fermat_api.*;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;

import com.bitdubai.fermat_api.layer._1_definition.enums.Addons;
import com.bitdubai.fermat_api.layer._1_definition.enums.PlatformComponents;
import com.bitdubai.fermat_api.layer._1_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer._1_definition.event.DealWithEventMonitor;
import com.bitdubai.fermat_api.layer._2_os.database_system.DealsWithPlatformDatabaseSystem;
import com.bitdubai.fermat_api.layer._2_os.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.UnexpectedPlatformExceptionSeverity;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_api.layer._2_os.*;
import com.bitdubai.fermat_api.layer._2_os.file_system.*;
import com.bitdubai.fermat_core.layer._12_basic_wallet.BasicWalletLayer;
import com.bitdubai.fermat_core.layer._15_transaction.TransactionLayer;
import com.bitdubai.fermat_core.layer._3_platform_service.PlatformServiceLayer;

import com.bitdubai.fermat_core.layer._1_definition.DefinitionLayer;
import com.bitdubai.fermat_core.layer._2_os.OsLayer;
import com.bitdubai.fermat_core.layer._4_hardware.HardwareLayer;
import com.bitdubai.fermat_core.layer._5_user.UserLayer;
import com.bitdubai.fermat_core.layer._6_license.LicenseLayer;
import com.bitdubai.fermat_core.layer._11_world.WorldLayer;
import com.bitdubai.fermat_core.layer._7_crypto_network.CryptoNetworkLayer;
import com.bitdubai.fermat_api.layer._7_crypto_network.CryptoNetworks;
import com.bitdubai.fermat_core.layer._8_crypto.CryptoLayer;
import com.bitdubai.fermat_core.layer._9_communication.CommunicationLayer;
import com.bitdubai.fermat_core.layer._10_network_service.NetworkServiceLayer;
import com.bitdubai.fermat_core.layer._14_middleware.MiddlewareLayer;
import com.bitdubai.fermat_core.layer._16_module.ModuleLayer;
import com.bitdubai.fermat_core.layer._17_agent.AgentLayer;

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
    PlatformLayer mCryptoLayer = new CryptoLayer();
    PlatformLayer mCommunicationLayer = new CommunicationLayer();
    PlatformLayer mNetworkServiceLayer = new NetworkServiceLayer();
    PlatformLayer mTransactionLayer = new TransactionLayer();
    PlatformLayer mMiddlewareLayer = new MiddlewareLayer();
    PlatformLayer mModuleLayer = new ModuleLayer();
    PlatformLayer mAgentLayer = new AgentLayer();
    PlatformLayer mBasicWalletLayer = new BasicWalletLayer();



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

    public PlatformLayer getCrypto() {
        return mCryptoLayer;
    }

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


    PlatformEventMonitor eventMonitor;
    
    PluginsIdentityManager pluginsIdentityManager;


    CorePlatformContext corePlatformContext;
    
    Object osContext;
    Os os;

    
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



    public void start() throws CantStartPlatformException, CantReportCriticalStartingProblem {

        

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
            mPlatformServiceLayer.start();
        }
        catch (CantStartLayerException cantStartLayerException) {
            /**
             * At this point the platform not only can not be started but also the problem can not be reported. That is 
             * the reason why I am going to throw a special exception in order to alert the situation to whoever is running
             * the GUI. In this way it can alert the end user of what is going on and provide them with some information.
             * * * *
             */
            throw new CantReportCriticalStartingProblem();
        }

        /**
         * -----------------------------------------------------------------------------------------------------------
         * Critical Addons initialization
         * -----------------------------------------------------------------------------------------------------------
         * * * * 
         */
        

        /**
         * -----------------------------
         * Addon Error Manager
         * -----------------------------
         * * * * 
         */

        Service errorManager = (Service) ((PlatformServiceLayer) mPlatformServiceLayer).getErrorManager();
        corePlatformContext.addAddon((Addon) errorManager, Addons.ERROR_MANAGER);



        /**
         * -----------------------------
         * Addon Event Manager
         * -----------------------------
         * * * *
         */



        Service eventManager = (Service) ((PlatformServiceLayer) mPlatformServiceLayer).getEventManager();
        corePlatformContext.addAddon((Addon) eventManager, Addons.EVENT_MANAGER);

        /**
         * I will give the Event Monitor to the Event Manager, in order to allow it to monitor listeners exceptions.
         */

        ((DealWithEventMonitor) eventManager).setEventMonitor(eventMonitor);
        

        try {
            
            //mOsLayer.start(); // Due to an Android bug is not possible to handle this here.
            mHardwareLayer.start();
            mUserLayer.start();
            mLicenseLayer.start();
            mWorldLayer.start();
            mCryptoNetworkLayer.start();
            mCryptoLayer.start();
            mCommunicationLayer.start();
            mNetworkServiceLayer.start();
            mMiddlewareLayer.start();
            mModuleLayer.start();
            mAgentLayer.start();
            mTransactionLayer.start();
            mBasicWalletLayer.start();
        }
        catch (CantStartLayerException cantStartLayerException) {
            ((ErrorManager) errorManager).reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ALL_THE_PLATFORM, cantStartLayerException); 
            throw new CantStartPlatformException();
        }



        /**
         * -----------------------------------------------------------------------------------------------------------
         * Addons initialization
         * -----------------------------------------------------------------------------------------------------------
         * * * * 
         */

        

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
         * Addon Remote Device Manager
         * -----------------------------
         * * * *
         */



        /**
         * I will give the Remote Device Manager access to the File System so it can load and save user information from
         * persistent media.
         */

        Service remoteDevice = (Service) ((HardwareLayer) mHardwareLayer).getRemoteDeviceManager();

        ((DealsWithPlatformFileSystem) remoteDevice).setPlatformFileSystem(os.getPlatformFileSystem());
        ((DealsWithEvents) remoteDevice).setEventManager((EventManager) eventManager);

        corePlatformContext.addAddon((Addon) remoteDevice, Addons.REMOTE_DEVICE);

        
        
        /**
         * -----------------------------
         * Addon Local Device Manager
         * -----------------------------
         * * * * 
         */



        /**
         * I will give the Local Device Manager access to the File System so it can load and save user information from
         * persistent media.
         */

        Service localDevice = (Service) ((HardwareLayer) mHardwareLayer).getLocalDeviceManager();

        ((DealsWithPlatformFileSystem) localDevice).setPlatformFileSystem(os.getPlatformFileSystem());
        ((DealsWithEvents) localDevice).setEventManager((EventManager) eventManager);

        corePlatformContext.addAddon((Addon) localDevice, Addons.LOCAL_DEVICE);




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

        Service deviceUser = (Service) ((UserLayer) mUserLayer).getDeviceUser();

        ((DealsWithPlatformFileSystem) deviceUser).setPlatformFileSystem(os.getPlatformFileSystem());
        ((DealsWithEvents) deviceUser).setEventManager((EventManager) eventManager);

        corePlatformContext.addAddon((Addon) deviceUser, Addons.DEVICE_USER);

        /**
         *---------------------------------
         * Addon Extra User
         * -------------------------------
         * * * *  
         */
        
        Service extraUser = (Service) ((UserLayer) mUserLayer).getExtraUser();

        ((DealsWithPlatformFileSystem) extraUser).setPlatformFileSystem(os.getPlatformFileSystem());
        ((DealsWithPlatformDatabaseSystem) extraUser).setPlatformDatabaseSystem(os.getPlatfotmDatabaseSystem());
        ((DealsWithEvents) extraUser).setEventManager((EventManager) eventManager);

        corePlatformContext.addAddon((Addon) extraUser, Addons.EXTRA_USER);



        /**
         *-------------------------------
         * Addon Intra User
         * -----------------------------
         * * * *  
         */
        
        
        Service intraUser = (Service) ((UserLayer) mUserLayer).getIntraUser();

        ((DealsWithPlatformFileSystem) intraUser).setPlatformFileSystem(os.getPlatformFileSystem());

        ((DealsWithEvents) intraUser).setEventManager((EventManager) eventManager);
        
        corePlatformContext.addAddon((Addon) intraUser, Addons.INTRA_USER);


        
        
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
         * Plugin Blockchain Info World
         * -----------------------------
         * * * *
         */



        /**
         * I will give the Blockchain Info plugin access to the File System so it can load and save information from persistent
         * media.
         */

        Plugin blockchainInfoWorld = ((WorldLayer)  mWorldLayer).getBlockchainInfo();

        ((DealsWithPluginFileSystem) blockchainInfoWorld).setPluginFileSystem(os.getPlugInFileSystem());
        ((DealsWithPluginDatabaseSystem) blockchainInfoWorld).setPluginDatabaseSystem(os.getPluginDatabaseSystem());
        ((DealsWithEvents) blockchainInfoWorld).setEventManager((EventManager) eventManager);
        ((DealsWithErrors) blockchainInfoWorld).setErrorManager((ErrorManager) errorManager);

        corePlatformContext.addPlugin(blockchainInfoWorld, Plugins.BITDUBAI_BLOCKCHAIN_INFO_WORLD);

        try
        {
            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */

            UUID pluginID = pluginsIdentityManager.getPluginId(blockchainInfoWorld);
            (blockchainInfoWorld).setId(pluginID);

            try {
                ((Service) blockchainInfoWorld).start();
            }
            catch (CantStartPluginException cantStartPluginException) {

                ((ErrorManager) errorManager).reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, cantStartPluginException);
                
                /**
                 * This plugin wont disable the whole platform, so I will allow the Platform to start even if this one
                 * will be disabled. In the future, I will re-try the start of plugins that are not starting at once. 
                 * * * 
                 */
            
            }
            
        }
        catch (PluginNotRecognizedException pluginNotRecognizedException)
        {

            ((ErrorManager) errorManager).reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, pluginNotRecognizedException);

            /**
             * This exception definitely shouldn't happen in production, but if it does we should know about it.
             * * *
             */

        }

        /**
         * -----------------------------
         * Plugin Shape Shift World
         * -----------------------------
         * * * *
         */



        /**
         * I will give the Shape Shift World plugin access to the File System so it can load and save information from persistent
         * media.
         */

        Plugin ShapeShiftWorld = ((WorldLayer)  mWorldLayer).getShapeShift();

        ((DealsWithPluginFileSystem) ShapeShiftWorld).setPluginFileSystem(os.getPlugInFileSystem());
        ((DealsWithEvents) ShapeShiftWorld).setEventManager((EventManager) eventManager);

        corePlatformContext.addPlugin(ShapeShiftWorld, Plugins.BITDUBAI_SHAPE_SHIFT_WORLD);

        try
        {
            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */

            UUID pluginID = pluginsIdentityManager.getPluginId(ShapeShiftWorld);
            (ShapeShiftWorld).setId(pluginID);

            try {
                ((Service) ShapeShiftWorld).start();
            }
            catch (CantStartPluginException cantStartPluginException) {

                ((ErrorManager) errorManager).reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, cantStartPluginException);

                /**
                 * This plugin wont disable the whole platform, so I will allow the Platform to start even if this one
                 * will be disabled. In the future, I will re-try the start of plugins that are not starting at once. 
                 * * * 
                 */
            }
        }
        catch (PluginNotRecognizedException pluginNotRecognizedException)
        {

            System.err.println("PluginNotRecognizedException: " + pluginNotRecognizedException.getMessage());
            pluginNotRecognizedException.printStackTrace();

        }

        /**
         * -----------------------------
         * Plugin Coinapult World
         * -----------------------------
         * * * *
         */



        /**
         * I will give the Coinapult plugin access to the File System so it can load and save information from persistent
         * media.
         */

        Plugin CoinapultWorld = ((WorldLayer)  mWorldLayer).getCoinapult();

        ((DealsWithPluginFileSystem) CoinapultWorld).setPluginFileSystem(os.getPlugInFileSystem());
        ((DealsWithEvents) CoinapultWorld).setEventManager((EventManager) eventManager);

        corePlatformContext.addPlugin(CoinapultWorld, Plugins.BITDUBAI_COINAPULT_WORLD);

        try
        {
            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */

            UUID pluginID = pluginsIdentityManager.getPluginId(CoinapultWorld);
            (CoinapultWorld).setId(pluginID);

            try {
                ((Service) CoinapultWorld).start();
            }
            catch (CantStartPluginException cantStartPluginException) {

                ((ErrorManager) errorManager).reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, cantStartPluginException);

                /**
                 * This plugin wont disable the whole platform, so I will allow the Platform to start even if this one
                 * will be disabled. In the future, I will re-try the start of plugins that are not starting at once.
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
         * Plugin Coinbase World
         * -----------------------------
         * * * *
         */



        /**
         * I will give the Coinbase plugin access to the File System so it can load and save information from persistent
         * media.
         */

        Plugin coinbaseWorld = ((WorldLayer)  mWorldLayer).getCoinbase();

        ((DealsWithPluginFileSystem) coinbaseWorld).setPluginFileSystem(os.getPlugInFileSystem());
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

        ((DealsWithPluginFileSystem) cryptoIndexWorld).setPluginFileSystem(os.getPlugInFileSystem());
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

        ((DealsWithPluginFileSystem) cryptoNetwork).setPluginFileSystem(os.getPlugInFileSystem());
        ((DealsWithEvents) cryptoNetwork).setEventManager((EventManager) eventManager);

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
         * ------------------------------
         *  Plugin Address Book Crypto
         * ------------------------------
         * * * * 
         */
        /**
         * I will give the Address book crypto access to the File System and to the Event Manager
         */

        Plugin addressBookCrypto = ((CryptoLayer) mCryptoLayer).getmAddressBook();

        ((DealsWithPluginFileSystem) addressBookCrypto).setPluginFileSystem(os.getPlugInFileSystem());
        ((DealsWithEvents) addressBookCrypto).setEventManager((EventManager) eventManager);

        corePlatformContext.addPlugin(addressBookCrypto, Plugins.BITDUBAI_ADDRESS_BOOK_CRYPTO);

        try
        {

            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */

            UUID pluginID = pluginsIdentityManager.getPluginId(addressBookCrypto);
            (addressBookCrypto).setId(pluginID);
            
            try {
                ((Service) addressBookCrypto).start();
            }
            catch (Exception e){
                System.err.println(e.getMessage());
                
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
         * Plugin Cloud Server Communication
         * -----------------------------
         * * * *
         */



        /**
         * I will give the Cloud Server Communication access to the File System and to the Event Manager
         */

        Plugin cloudServerCommunication = ((CommunicationLayer) mCommunicationLayer).getCloudServerPlugin();

        ((DealsWithPluginFileSystem) cloudServerCommunication).setPluginFileSystem(os.getPlugInFileSystem());
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
        
        ((DealsWithPluginFileSystem) cloudCommunication).setPluginFileSystem(os.getPlugInFileSystem());
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

        ((DealsWithPluginFileSystem) walletCommunityNetworkService).setPluginFileSystem(os.getPlugInFileSystem());
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

        ((DealsWithPluginFileSystem) appRuntimeMiddleware).setPluginFileSystem(os.getPlugInFileSystem());
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

        ((DealsWithPluginFileSystem) bankNotesMiddleware).setPluginFileSystem(os.getPlugInFileSystem());
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

      //  ((DealsWithPluginFileSystem) bitcoinWalletBasicWallet).setPluginFileSystem(os.getPlugInFileSystem());
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

      //  ((DealsWithPluginFileSystem) discountWalletBasicWallet).setPluginFileSystem(os.getPlugInFileSystem());
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
         * * * *
         */



        /**
         * I will give the Wallet Contacts Middleware access to the File System and to the Event Manager
         */

        Plugin walletContactsMiddleware = ((MiddlewareLayer) mMiddlewareLayer).getWalletContactsPlugin();

        ((DealsWithPluginFileSystem) walletContactsMiddleware).setPluginFileSystem(os.getPlugInFileSystem());
        ((DealsWithEvents) walletContactsMiddleware).setEventManager((EventManager) eventManager);
        corePlatformContext.addPlugin(walletContactsMiddleware, Plugins.BITDUBAI_WALLET_CONTACTS_MIDDLEWARE);

        try
        {

            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */

            UUID pluginID = pluginsIdentityManager.getPluginId(walletContactsMiddleware);
            (walletContactsMiddleware).setId(pluginID);

            try {
                ((Service) walletContactsMiddleware).start();
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

        Plugin incomingCryptoTransaction = ((TransactionLayer) mTransactionLayer).getIncomingCrypto();

        ((DealsWithPluginFileSystem) incomingCryptoTransaction).setPluginFileSystem(os.getPlugInFileSystem());
        ((DealsWithEvents) incomingCryptoTransaction).setEventManager((EventManager) eventManager);
        corePlatformContext.addPlugin(incomingCryptoTransaction, Plugins.BITDUBAI_INCOMING_EXTRA_USER_TRANSACTION);

        try
        {

            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */

            UUID pluginID = pluginsIdentityManager.getPluginId(incomingCryptoTransaction);
            (incomingCryptoTransaction).setId(pluginID);
            
            try {
                ((Service) incomingCryptoTransaction).start();
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

        ((DealsWithPluginFileSystem) incomingExtraUserTransaction).setPluginFileSystem(os.getPlugInFileSystem());
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

        ((DealsWithPluginFileSystem) incomingIntraUserTransaction).setPluginFileSystem(os.getPlugInFileSystem());
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

        ((DealsWithPluginFileSystem) outgoingExtraUserTransaction).setPluginFileSystem(os.getPlugInFileSystem());
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

        ((DealsWithPluginFileSystem) outgoingDeviceUserTransaction).setPluginFileSystem(os.getPlugInFileSystem());
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
        }
        catch (PluginNotRecognizedException pluginNotRecognizedException)
        {


            System.err.println("PluginNotRecognizedException: " + pluginNotRecognizedException.getMessage());
            pluginNotRecognizedException.printStackTrace();

            throw new CantStartPlatformException();
        }



        /**
         * ----------------------------------
         * Plugin Incoming Device user Transaction
         * ----------------------------------
         * * * *
         */



        /**
         * I will give the Incoming Device User Transaction access to the File System and to the Event Manager
         */

        Plugin outgoingIntraUserTransaction = ((TransactionLayer) mTransactionLayer).getOutgoingIntraUserPlugin();

        ((DealsWithPluginFileSystem) outgoingIntraUserTransaction).setPluginFileSystem(os.getPlugInFileSystem());
        ((DealsWithEvents) outgoingIntraUserTransaction).setEventManager((EventManager) eventManager);

        corePlatformContext.addPlugin(outgoingIntraUserTransaction, Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION);

        try
        {

            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */

            UUID pluginID = pluginsIdentityManager.getPluginId(outgoingIntraUserTransaction);
            (outgoingIntraUserTransaction).setId(pluginID);
            
            try {
                ((Service) outgoingIntraUserTransaction).start();
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
        }
        catch (PluginNotRecognizedException pluginNotRecognizedException)
        {


            System.err.println("PluginNotRecognizedException: " + pluginNotRecognizedException.getMessage());
            pluginNotRecognizedException.printStackTrace();

            throw new CantStartPlatformException();
        }






        /**
         * ----------------------------------
         * Plugin Incoming Device user Transaction
         * ----------------------------------
         * * * *
         */



        /**
         * I will give the Incoming Device User Transaction access to the File System and to the Event Manager
         */

        Plugin incomingDeviceUserTransaction = ((TransactionLayer) mTransactionLayer).getIncomingDeviceUserPlugin();

        ((DealsWithPluginFileSystem) incomingDeviceUserTransaction).setPluginFileSystem(os.getPlugInFileSystem());
        ((DealsWithEvents) incomingDeviceUserTransaction).setEventManager((EventManager) eventManager);

        corePlatformContext.addPlugin(incomingDeviceUserTransaction, Plugins.BITDUBAI_INCOMING_DEVICE_USER_TRANSACTION);

        try
        {

            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */

            UUID pluginID = pluginsIdentityManager.getPluginId(incomingDeviceUserTransaction);
            (incomingDeviceUserTransaction).setId(pluginID);

            try {
                ((Service) incomingDeviceUserTransaction).start();
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
        }
        catch (PluginNotRecognizedException pluginNotRecognizedException)
        {


            System.err.println("PluginNotRecognizedException: " + pluginNotRecognizedException.getMessage());
            pluginNotRecognizedException.printStackTrace();

            throw new CantStartPlatformException();
        }



        /**
         * -----------------------------
         * Plugin Wallet factory
         * -----------------------------
         * * * *
         */



        /**
         * I will give the Wallet Manager access to the File System and to the Event Manager
         */

      /*  Plugin walletFactoryModule =  ((ModuleLayer) mModuleLayer).getWalletFactory();

        ((DealsWithPluginFileSystem) walletFactoryModule).setPluginFileSystem(os.getPlugInFileSystem());
        ((DealsWithEvents) walletFactoryModule).setEventManager((EventManager) eventManager);

        corePlatformContext.addPlugin(walletFactoryModule, Plugins.BITDUBAI_WALLET_FACTORY_MODULE);

        try
        {

            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */

        /*    UUID pluginID = pluginsIdentityManager.getPluginId(walletFactoryModule);
            (walletFactoryModule).setId(pluginID);

            try {
                ((Service) walletFactoryModule).start();
            }
            catch (CantStartPluginException cantStartPluginException) {

                System.err.println("CantStartPluginException: " + cantStartPluginException.getMessage() + cantStartPluginException.getPlugin().getKey());
                cantStartPluginException.printStackTrace();

                /**
                 * For now, we will take this plugin as a essential for the platform itself to be running so if it can not
                 * start, then the platform wont start either. In the future we will review this policy.
                 * * *
                 */

        /*        throw new CantStartPlatformException();
            }
        }
        catch (PluginNotRecognizedException pluginNotRecognizedException)
        {

            /**
             * The wallet manager is a critical component for the platform to run. Whiteout it there is no platform.
             */

         /*   System.err.println("PluginNotRecognizedException: " + pluginNotRecognizedException.getMessage());
            pluginNotRecognizedException.printStackTrace();

            throw new CantStartPlatformException();
        }*/




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

        corePlatformContext.addPlugin(walletManager, Plugins.BITDUBAI_WALLET_MANAGER_MODULE);
        
        try
        {

            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */

            UUID pluginID = pluginsIdentityManager.getPluginId(walletManager);
            (walletManager).setId(pluginID);
            
            try {
                ((Service) walletManager).start();
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

        corePlatformContext.addPlugin(walletRuntime, Plugins.BITDUBAI_WALLET_RUNTIME_MODULE);

        try
        {

            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */

            UUID pluginID = pluginsIdentityManager.getPluginId(walletRuntime);
            (walletRuntime).setId(pluginID);

            try {
                ((Service) walletRuntime).start();
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
        }
        catch (PluginNotRecognizedException pluginNotRecognizedException)
        {

            System.err.println("PluginNotRecognizedException: " + pluginNotRecognizedException.getMessage());
            pluginNotRecognizedException.printStackTrace();

            throw new CantStartPlatformException();
        }






    }
}

