package com.bitdubai.fermat_core;


import com.bitdubai.fermat_api.*;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;


import com.bitdubai.fermat_api.layer._1_definition.enums.Addons;
import com.bitdubai.fermat_api.layer._1_definition.enums.PlatformComponents;
import com.bitdubai.fermat_api.layer._1_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer._1_definition.event.DealWithEventMonitor;
import com.bitdubai.fermat_api.layer.osa_android_xxx.database_system.DealsWithPlatformDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android_xxx.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.UnexpectedPlatformExceptionSeverity;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_api.layer._2_os.*;
import com.bitdubai.fermat_api.layer._2_os.file_system.*;

import com.bitdubai.fermat_api.layer._5_user.device_user.DealsWithDeviceUsers;
import com.bitdubai.fermat_api.layer._5_user.device_user.DeviceUserManager;
import com.bitdubai.fermat_api.layer._5_user.extra_user.DealsWithExtraUsers;
import com.bitdubai.fermat_api.layer._5_user.extra_user.ExtraUserManager;
import com.bitdubai.fermat_api.layer._5_user.intra_user.DealsWithIntraUsers;
import com.bitdubai.fermat_api.layer._5_user.intra_user.IntraUserManager;
import com.bitdubai.fermat_core.layer._13_basic_wallet.BasicWalletLayer;
import com.bitdubai.fermat_core.layer._16_transaction.TransactionLayer;
import com.bitdubai.fermat_core.layer._19_niche_wallet_type.NicheWalletTypeLayer;
import com.bitdubai.fermat_core.layer._3_platform_service.PlatformServiceLayer;

import com.bitdubai.fermat_core.layer._1_definition.DefinitionLayer;
import com.bitdubai.fermat_core.layer._2_os.OsLayer;
import com.bitdubai.fermat_core.layer._4_hardware.HardwareLayer;
import com.bitdubai.fermat_core.layer._5_user.UserLayer;
import com.bitdubai.fermat_core.layer._6_license.LicenseLayer;
import com.bitdubai.fermat_core.layer._12_world.WorldLayer;
import com.bitdubai.fermat_core.layer.cry_1_crypto_network.CryptoNetworkLayer;
import com.bitdubai.fermat_api.layer.cry_1_crypto_network.CryptoNetworks;
import com.bitdubai.fermat_core.layer.cry_2_cypto_vault.CryptoVaultLayer;
import com.bitdubai.fermat_core.layer.cry_3_crypto_module.CryptoLayer;
import com.bitdubai.fermat_core.layer._10_communication.CommunicationLayer;
import com.bitdubai.fermat_core.layer._11_network_service.NetworkServiceLayer;
import com.bitdubai.fermat_core.layer._15_middleware.MiddlewareLayer;
import com.bitdubai.fermat_core.layer._18_module.ModuleLayer;
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
    PlatformLayer mCryptoVaultLayer = new CryptoVaultLayer();
    PlatformLayer mCryptoLayer = new CryptoLayer();
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

    public void setOsContext(Object osContext){
        this.osContext = osContext;
    }

    /**
     * An unresolved bug in either Android or Gradle does not allow us to create the os object on a library outside the
     * main module. While this situation persists, we will create it inside the wallet package and receive it throw this
     * method.
     */



    public void setFileSystemOs (FileSystemOs fileSystemOs){
        this.fileSystemOs = fileSystemOs;
    }


    public void setDataBaseSystemOs (DataBaseSystemOs databaseSystemOs){
        this.databaseSystemOs = databaseSystemOs;
    }


    public void setLocationSystemOs(LocationSystemOs locationSystemOs)
    {
        this.locationSystemOs  = locationSystemOs;
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
        ((DealsWithPlatformDatabaseSystem) errorManager).setPlatformDatabaseSystem(databaseSystemOs.getPlatformDatabaseSystem());

        try {
            errorManager.start();

        }
        catch (CantStartPluginException cantStartPluginException) {
            System.err.println("CantStartPluginException: " + cantStartPluginException.getMessage());
            cantStartPluginException.printStackTrace();
            throw new CantStartPlatformException();
        }




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

            // mOsLayer.start(); // Due to an Android bug is not possible to handle this here.
            mHardwareLayer.start();
            mUserLayer.start();
            mLicenseLayer.start();
            mWorldLayer.start();
            mCryptoNetworkLayer.start();
            mCryptoVaultLayer.start();
            mCryptoLayer.start();
            mCommunicationLayer.start();
            mNetworkServiceLayer.start();
            mMiddlewareLayer.start();
            mModuleLayer.start();
            mAgentLayer.start();
            mTransactionLayer.start();
            mBasicWalletLayer.start();
            mNicheWalletTypeLayer.start();
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
         * Addon Remote Device Manager
         * -----------------------------
         * * * *
         */


        /**
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
         * * * * 
         */



        /**
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
         * * * * 
         */



        /**
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
         * * * *  
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
         * * * *  
         */


        Service intraUser = (Service) ((UserLayer) mUserLayer).getIntraUser();

        ((DealsWithPlatformFileSystem) intraUser).setPlatformFileSystem(fileSystemOs.getPlatformFileSystem());

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
            pluginsIdentityManager = new PluginsIdentityManager(fileSystemOs.getPlatformFileSystem());
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

        ((DealsWithPluginFileSystem) blockchainInfoWorld).setPluginFileSystem(fileSystemOs.getPlugInFileSystem());
        ((DealsWithPluginDatabaseSystem) blockchainInfoWorld).setPluginDatabaseSystem(databaseSystemOs.getPluginDatabaseSystem());
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

        ((DealsWithPluginFileSystem) ShapeShiftWorld).setPluginFileSystem(fileSystemOs.getPlugInFileSystem());
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

        Plugin coinapultWorld = ((WorldLayer)  mWorldLayer).getCoinapult();

        ((DealsWithPluginFileSystem) coinapultWorld).setPluginFileSystem(fileSystemOs.getPlugInFileSystem());
        ((DealsWithEvents) coinapultWorld).setEventManager((EventManager) eventManager);

        corePlatformContext.addPlugin(coinapultWorld, Plugins.BITDUBAI_COINAPULT_WORLD);

        try
        {
            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */

            UUID pluginID = pluginsIdentityManager.getPluginId(coinapultWorld);
            (coinapultWorld).setId(pluginID);

            try {
                ((Service) coinapultWorld).start();
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
         * Plugin Coinbase World
         * -----------------------------
         * * * *
         */



        /**
         * I will give the Coinbase plugin access to the File System so it can load and save information from persistent
         * media.
         */

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

       // ((DealsWithPluginFileSystem) cryptoNetwork).setPluginFileSystem(fileSystemOs.getPlugInFileSystem());
        //((DealsWithEvents) cryptoNetwork).setEventManager((EventManager) eventManager);

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
         * ------------------------------
         *  Plugin Wallet Address Book Crypto
         * ------------------------------
         * * * * 
         */
        /**
         * I will give the Wallet Address book crypto access to the File System and to the Event Manager
         */

        Plugin walletAddressBookCrypto = ((CryptoLayer) mCryptoLayer).getmWalletAddressBook();

        ((DealsWithPluginFileSystem) walletAddressBookCrypto).setPluginFileSystem(fileSystemOs.getPlugInFileSystem());
        ((DealsWithEvents) walletAddressBookCrypto).setEventManager((EventManager) eventManager);

        corePlatformContext.addPlugin(walletAddressBookCrypto, Plugins.BITDUBAI_WALLET_ADDRESS_BOOK_CRYPTO);

        try
        {

            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */

            UUID pluginID = pluginsIdentityManager.getPluginId(walletAddressBookCrypto);
            (walletAddressBookCrypto).setId(pluginID);

            try {
                ((Service) walletAddressBookCrypto).start();
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
         * ------------------------------
         *  Plugin User Address Book Crypto
         * ------------------------------
         * * * * 
         */
        /**
         * I will give the User Address book crypto access to the File System and to the Event Manager
         */

        Plugin userAddressBookCrypto = ((CryptoLayer) mCryptoLayer).getmUserAddressBook();

        ((DealsWithPluginFileSystem) userAddressBookCrypto).setPluginFileSystem(fileSystemOs.getPlugInFileSystem());
        ((DealsWithEvents) userAddressBookCrypto).setEventManager((EventManager) eventManager);
        ((DealsWithExtraUsers) userAddressBookCrypto).setExtraUserManager((ExtraUserManager) extraUser);
        ((DealsWithDeviceUsers) userAddressBookCrypto).setDeviceUserManager((DeviceUserManager) deviceUser);
        ((DealsWithIntraUsers) userAddressBookCrypto).setIntraUserManager((IntraUserManager) intraUser);

        corePlatformContext.addPlugin(userAddressBookCrypto, Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO);

        try
        {

            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */

            UUID pluginID = pluginsIdentityManager.getPluginId(userAddressBookCrypto);
            (userAddressBookCrypto).setId(pluginID);

            try {
                ((Service) userAddressBookCrypto).start();
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
         * ----------------------------------
         * Plugin Crypto Loss Protected Wallet Niche Type Wallet
         * ----------------------------------
         * * * *
         */



        /**
         * I will give the crypto loss protected wallet access to the File System and to the Event Manager
         */

        //TODO lo comente porque la variable cryptoLossProtectedWalletNicheWalletType es null y da error al inicializar la APP (Natalia)
       // Plugin cryptoLossProtectedWalletNicheWalletType = ((NicheWalletTypeLayer) mNicheWalletTypeLayer).getmCryptoLossProtectedWallet();

       // ((DealsWithPluginFileSystem) cryptoLossProtectedWalletNicheWalletType).setPluginFileSystem(fileSystemOs.getPlugInFileSystem());
       // ((DealsWithEvents) cryptoLossProtectedWalletNicheWalletType).setEventManager((EventManager) eventManager);
       // corePlatformContext.addPlugin(cryptoLossProtectedWalletNicheWalletType, Plugins.BITDUBAI_CRYPTO_LOSS_PROTECTED_WALLET_NICHE_WALLET_TYPE);

      //  try
      //  {

            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */

         //   UUID pluginID = pluginsIdentityManager.getPluginId(cryptoLossProtectedWalletNicheWalletType);
         //   (cryptoLossProtectedWalletNicheWalletType).setId(pluginID);

          //  try {
           //     ((Service) cryptoLossProtectedWalletNicheWalletType).start();
          //  }
           // catch (CantStartPluginException cantStartPluginException) {

             //   System.err.println("CantStartPluginException: " + cantStartPluginException.getMessage() + cantStartPluginException.getPlugin().getKey());
            //    cantStartPluginException.printStackTrace();

                /**
                 * For now, we will take this plugin as a essential for the platform itself to be running so if it can not
                 * start, then the platform wont start either. In the future we will review this policy.
                 * * *
                 */

               // throw new CantStartPlatformException();
          //  }
          //  catch (Exception e){

           //     ((ErrorManager) errorManager).reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, e);

                /**
                 * This is worse than the previous catch since the plugin didn't even throw an expected exception.
                 * * *
                 */

            //}

       // }
        //catch (PluginNotRecognizedException pluginNotRecognizedException)
        //{


            //System.err.println("PluginNotRecognizedException: " + pluginNotRecognizedException.getMessage());
           // pluginNotRecognizedException.printStackTrace();

           // throw new CantStartPlatformException();
       // }

        /**
         * ----------------------------------
         * Plugin crypto Wallet Niche Type Wallet
         * ----------------------------------
         * * * *
         */



        /**
         * I will give the crypto wallet access to the File System and to the Event Manager
         */

        Plugin cryptoWalletNicheWalletType = ((NicheWalletTypeLayer) mNicheWalletTypeLayer).getmCryptoWallet();

        ((DealsWithPluginFileSystem) cryptoWalletNicheWalletType).setPluginFileSystem(fileSystemOs.getPlugInFileSystem());
        ((DealsWithEvents) cryptoWalletNicheWalletType).setEventManager((EventManager) eventManager);
        corePlatformContext.addPlugin(cryptoWalletNicheWalletType, Plugins.BITDUBAI_CRYPTO_WALLET_NICHE_WALLET_TYPE);

        try
        {

            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */

            UUID pluginID = pluginsIdentityManager.getPluginId(cryptoWalletNicheWalletType);
            (cryptoWalletNicheWalletType).setId(pluginID);

            try {
                ((Service) cryptoWalletNicheWalletType).start();
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
         * Plugin Discount Wallet Niche Type Wallet
         * ----------------------------------
         * * * *
         */



        /**
         * I will give the discount wallet access to the File System and to the Event Manager
         */

        Plugin discountWalletNicheWalletType = ((NicheWalletTypeLayer) mNicheWalletTypeLayer).getmDiscountWallet();

        ((DealsWithPluginFileSystem) discountWalletNicheWalletType).setPluginFileSystem(fileSystemOs.getPlugInFileSystem());
        ((DealsWithEvents) discountWalletNicheWalletType).setEventManager((EventManager) eventManager);
        corePlatformContext.addPlugin(discountWalletNicheWalletType, Plugins.BITDUBAI_DISCOUNT_WALLET_NICHE_WALLET_TYPE);

        try
        {

            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */

            UUID pluginID = pluginsIdentityManager.getPluginId(discountWalletNicheWalletType);
            (discountWalletNicheWalletType).setId(pluginID);

            try {
                ((Service) discountWalletNicheWalletType).start();
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
         * Plugin Fiat Over Crypto Loss Protected Wallet Wallet Niche Type Wallet
         * ----------------------------------
         * * * *
         */



        /**
         * I will give the Fiat over crypto loss protected wallet access to the File System and to the Event Manager
         */
        //TODO lo comente porque la variable fiatOverCryptoLossProtectedWalletNicheWalletType es null  y da error al levantar la APP (Natalia)
      //  Plugin fiatOverCryptoLossProtectedWalletNicheWalletType = ((NicheWalletTypeLayer) mNicheWalletTypeLayer).getmFiatOverCryptoLossProtectedWallet();

       // ((DealsWithPluginFileSystem) fiatOverCryptoLossProtectedWalletNicheWalletType).setPluginFileSystem(fileSystemOs.getPlugInFileSystem());
       // ((DealsWithEvents) fiatOverCryptoLossProtectedWalletNicheWalletType).setEventManager((EventManager) eventManager);
       // corePlatformContext.addPlugin(fiatOverCryptoLossProtectedWalletNicheWalletType, Plugins.BITDUBAI_FIAT_OVER_CRYPTO_LOSS_PROTECTED_WALLET_NICHE_WALLET_TYPE);

       // try
       // {

            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */

          //  UUID pluginID = pluginsIdentityManager.getPluginId(fiatOverCryptoLossProtectedWalletNicheWalletType);
          //  (fiatOverCryptoLossProtectedWalletNicheWalletType).setId(pluginID);

          //  try {
          //      ((Service) fiatOverCryptoLossProtectedWalletNicheWalletType).start();
          //  }
          //  catch (CantStartPluginException cantStartPluginException) {

               // System.err.println("CantStartPluginException: " + cantStartPluginException.getMessage() + cantStartPluginException.getPlugin().getKey());
                //cantStartPluginException.printStackTrace();

                /**
                 * For now, we will take this plugin as a essential for the platform itself to be running so if it can not
                 * start, then the platform wont start either. In the future we will review this policy.
                 * * *
                 */

               // throw new CantStartPlatformException();
           // }
           // catch (Exception e){

             //   ((ErrorManager) errorManager).reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, e);

                /**
                 * This is worse than the previous catch since the plugin didn't even throw an expected exception.
                 * * *
                 */

            //}

       // }
       // catch (PluginNotRecognizedException pluginNotRecognizedException)
       // {


          //  System.err.println("PluginNotRecognizedException: " + pluginNotRecognizedException.getMessage());
          //  pluginNotRecognizedException.printStackTrace();

           // throw new CantStartPlatformException();
        //}


        /**
         * ----------------------------------
         * Plugin Fiat Over Crypto Wallet Niche Type Wallet
         * ----------------------------------
         * * * *
         */



        /**
         * I will give the fiat over crypto wallet access to the File System and to the Event Manager
         */

        Plugin fiatOverCryptoWalletNicheWalletType = ((NicheWalletTypeLayer) mNicheWalletTypeLayer).getmFiatOverCryptoWallet();

        ((DealsWithPluginFileSystem) fiatOverCryptoWalletNicheWalletType).setPluginFileSystem(fileSystemOs.getPlugInFileSystem());
        ((DealsWithEvents) fiatOverCryptoWalletNicheWalletType).setEventManager((EventManager) eventManager);
        corePlatformContext.addPlugin(fiatOverCryptoWalletNicheWalletType, Plugins.BITDUBAI_FIAT_OVER_CRYPTO_WALLET_NICHE_WALLET_TYPE);

        try
        {

            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */

            UUID pluginID = pluginsIdentityManager.getPluginId(fiatOverCryptoWalletNicheWalletType);
            (fiatOverCryptoWalletNicheWalletType).setId(pluginID);

            try {
                ((Service) fiatOverCryptoWalletNicheWalletType).start();
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
         * Plugin Multi account Wallet Niche Type Wallet
         * ----------------------------------
         * * * *
         */



        /**
         * I will give the multi account wallet access to the File System and to the Event Manager
         */

        Plugin multiAccountWalletNicheWalletType = ((NicheWalletTypeLayer) mNicheWalletTypeLayer).getmMultiAccountWallet();

        ((DealsWithPluginFileSystem) multiAccountWalletNicheWalletType).setPluginFileSystem(fileSystemOs.getPlugInFileSystem());
        ((DealsWithEvents) multiAccountWalletNicheWalletType).setEventManager((EventManager) eventManager);
        corePlatformContext.addPlugin(multiAccountWalletNicheWalletType, Plugins.BITDUBAI_MULTI_ACCOUNT_WALLET_NICHE_WALLET_TYPE);

        try
        {

            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */

            UUID pluginID = pluginsIdentityManager.getPluginId(multiAccountWalletNicheWalletType);
            (multiAccountWalletNicheWalletType).setId(pluginID);

            try {
                ((Service) multiAccountWalletNicheWalletType).start();
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
         * Plugin Bank Notes Wallet Niche Type Wallet
         * ----------------------------------
         * * * *
         */



        /**
         * I will give the bank notes wallet access to the File System and to the Event Manager
         */

        Plugin bankNotesWalletNicheWalletType = ((NicheWalletTypeLayer) mNicheWalletTypeLayer).getmBankNotesWallet();

        ((DealsWithPluginFileSystem) bankNotesWalletNicheWalletType).setPluginFileSystem(fileSystemOs.getPlugInFileSystem());
        ((DealsWithEvents) bankNotesWalletNicheWalletType).setEventManager((EventManager) eventManager);
        corePlatformContext.addPlugin(bankNotesWalletNicheWalletType, Plugins.BITDUBAI_BANK_NOTES_WALLET_NICHE_WALLET_TYPE);

        try
        {

            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */

            UUID pluginID = pluginsIdentityManager.getPluginId(bankNotesWalletNicheWalletType);
            (bankNotesWalletNicheWalletType).setId(pluginID);

            try {
                ((Service) bankNotesWalletNicheWalletType).start();
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
         * * * *
         */



        /**
         * I will give the Wallet Contacts Middleware access to the File System and to the Event Manager
         */

        Plugin walletContactsMiddleware = ((MiddlewareLayer) mMiddlewareLayer).getWalletContactsPlugin();

        ((DealsWithPluginFileSystem) walletContactsMiddleware).setPluginFileSystem(fileSystemOs.getPlugInFileSystem());
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
         * Plugin Bitcoin Crypto Vault
         * ----------------------------------
         * * * *
         */



        /**
         * I will give the Bitcoin Crypto Vault access to the File System and to the Event Manager
         */

        Plugin bitcoinCryptoVault = ((CryptoVaultLayer) mCryptoVaultLayer).getmBitcoin();

        ((DealsWithPluginFileSystem) bitcoinCryptoVault).setPluginFileSystem(fileSystemOs.getPlugInFileSystem());
        ((DealsWithEvents) bitcoinCryptoVault).setEventManager((EventManager) eventManager);

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

        Plugin incomingCryptoTransaction = ((TransactionLayer) mTransactionLayer).getIncomingCrypto();

       // ((DealsWithPluginFileSystem) incomingCryptoTransaction).setPluginFileSystem(fileSystemOs.getPlugInFileSystem());
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
                //TODO: ver que da error null point
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

        ((DealsWithPluginFileSystem) outgoingIntraUserTransaction).setPluginFileSystem(fileSystemOs.getPlugInFileSystem());
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
         * Plugin Incoming Device user Transaction
         * ----------------------------------
         * * * *
         */



        /**
         * I will give the Incoming Device User Transaction access to the File System and to the Event Manager
         */

        Plugin incomingDeviceUserTransaction = ((TransactionLayer) mTransactionLayer).getIncomingDeviceUserPlugin();

        ((DealsWithPluginFileSystem) incomingDeviceUserTransaction).setPluginFileSystem(fileSystemOs.getPlugInFileSystem());
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
         * Plugin Wallet factory
         * -----------------------------
         * * * *
         */



        /**
         * I will give the Wallet Manager access to the File System and to the Event Manager
         */

      /*  Plugin walletFactoryModule =  ((ModuleLayer) mModuleLayer).getWalletFactory();

        ((DealsWithPluginFileSystem) walletFactoryModule).setPluginFileSystem(fileSystemOs.getPlugInFileSystem());
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

        ((DealsWithPluginFileSystem) walletManager).setPluginFileSystem(fileSystemOs.getPlugInFileSystem());
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

        ((DealsWithPluginFileSystem) walletRuntime).setPluginFileSystem(fileSystemOs.getPlugInFileSystem());
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






    }
}
