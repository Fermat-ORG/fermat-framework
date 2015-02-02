package com.bitdubai.wallet_platform_core;

import com.bitdubai.wallet_platform_api.CantInitializePluginsManagerException;
import com.bitdubai.wallet_platform_api.Plugin;
import com.bitdubai.wallet_platform_api.PluginNotRecognizedException;
import com.bitdubai.wallet_platform_api.layer.CantStartLayerException;
import com.bitdubai.wallet_platform_api.layer.PlatformLayer;

import com.bitdubai.wallet_platform_api.PlatformService;
import com.bitdubai.wallet_platform_api.layer._1_definition.enums.DeviceDirectory;
import com.bitdubai.wallet_platform_api.layer._1_definition.enums.PlatformFileName;
import com.bitdubai.wallet_platform_api.layer._1_definition.event.DealWithEventMonitor;
import com.bitdubai.wallet_platform_api.layer._2_event.manager.DealsWithEvents;
import com.bitdubai.wallet_platform_api.layer._3_os.*;
import com.bitdubai.wallet_platform_api.layer._4_user.User;
import com.bitdubai.wallet_platform_api.layer._4_user.UserManager;
import com.bitdubai.wallet_platform_core.layer._2_event.EventLayer;
import com.bitdubai.wallet_platform_api.layer._2_event.EventManager;

import com.bitdubai.wallet_platform_core.layer._1_definition.DefinitionLayer;
import com.bitdubai.wallet_platform_core.layer._3_os.OsLayer;
import com.bitdubai.wallet_platform_core.layer._4_user.*;
import com.bitdubai.wallet_platform_api.layer._4_user.manager.CantCreateUserException;
import com.bitdubai.wallet_platform_api.layer._4_user.manager.CantLoadUserException;
import com.bitdubai.wallet_platform_api.layer._4_user.manager.LoginFailedException;
import com.bitdubai.wallet_platform_core.layer._5_license.LicenseLayer;
import com.bitdubai.wallet_platform_core.layer._6_world.WorldLayer;
import com.bitdubai.wallet_platform_core.layer._7_crypto_network.CryptoNetworkLayer;
import com.bitdubai.wallet_platform_api.layer._7_crypto_network.CryptoNetworks;
import com.bitdubai.wallet_platform_core.layer._8_communication.CommunicationLayer;
import com.bitdubai.wallet_platform_core.layer._9_network_service.NetworkServiceLayer;
import com.bitdubai.wallet_platform_core.layer._10_middleware.MiddlewareLayer;
import com.bitdubai.wallet_platform_core.layer._11_module.ModuleLayer;
import com.bitdubai.wallet_platform_core.layer._12_agent.AgentLayer;
import com.bitdubai.wallet_platform_api.CantStartPlatformException;

import java.util.UUID;

/**
 * Created by ciencias on 20.01.15.
 */
public class Platform  {



    PlatformLayer mDefinitionLayer = new DefinitionLayer();
    PlatformLayer mEventLayer = new EventLayer();
    PlatformLayer mOsLayer = new OsLayer();
    PlatformLayer mUserLayer = new UserLayer();
    PlatformLayer mLicesnseLayer = new LicenseLayer();
    PlatformLayer mWorldLayer = new WorldLayer();
    PlatformLayer mCryptoNetworkLayer = new CryptoNetworkLayer();
    PlatformLayer mCommunicationLayer = new CommunicationLayer();
    PlatformLayer mNetworkServiceLayer = new NetworkServiceLayer();
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


    User mLoggedInUser;
    PlatformEventMonitor eventMonitor;
    
    PluginsManager pluginsManager;

    public User getLoggedInUser() {
        return mLoggedInUser;
    }

    Object context;
    Os os;


    public void Platform () {

         /**
         * The event monitor is intended to handle exceptions on listeners, in order to take appropiate action.
         */

        eventMonitor = new PlatformEventMonitor();
    }

    /**
     * Somebody is starting the com.bitdubai.platform. The com.bitdubai.platform is portable. That somebody is OS dependent and has access to
     * the OS. I have to transport a reference to that somebody to the OS subsystem in other to allow it to access
     * the OS through this reference.
     */

    public void setContext (Object context){
        this.context = context;
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
         * Here I will be starting all the platforms layers. It is required that none of them fails. That does not mean
         * that a layer will have at least one service to offer. It depends on each layer. If one believes its lack of
         * services prevent the whole com.bitdubai.platform to start, then it will throw an exception that will effectively prevent the
         * com.bitdubai.platform to start.
         */

        try {

            mDefinitionLayer.start();
            mEventLayer.start();
            mOsLayer.start();
            mUserLayer.start();
            mLicesnseLayer.start();
            mWorldLayer.start();
            mCryptoNetworkLayer.start();
            mCommunicationLayer.start();
            mNetworkServiceLayer.start();
            mMiddlewareayer.start();
            mModuleLayer.start();
            mAgentLayer.start();
        }
        catch (CantStartLayerException cantStartLayerException) {
            System.err.println("CantStartLayerException: " + cantStartLayerException.getMessage());
            cantStartLayerException.printStackTrace();
            throw new CantStartPlatformException();
        }



        /**
         * The OS and Event Manager will need to be handled to several other objects. I will have them handly.
         */

        if (os == null) {
            /**
             * In Android, an unresolved bug doesn't allow us to create the Os object where it should be created and we
             * receive it from the wallet module. If we did not receive it we follow the standard procedure.
             */

            this.os  = ((OsLayer) mOsLayer).getOs();
        }

        EventManager eventManager = ((EventLayer) mEventLayer).getEventManager();

        /**
         * I will give the Event Monitor to the Event Manager, in order to allow it to monitor listeners exceptions..
         */

        ((DealWithEventMonitor) eventManager).setEventMonitor(eventMonitor);

        /**
         * I will set the context to the Os in order to enable access to the underlying Os objects.
         */

        os.setContext(this.context);

        /**
         * I will initialize the Plugin Manager
         */

        try
        {
            pluginsManager = new PluginsManager(os.getPlatformFileSystem());
        }
        catch (CantInitializePluginsManagerException cantInitializePluginsManagerException) {
            System.err.println("CantInitializePluginsManager: " + cantInitializePluginsManagerException.getMessage());
            cantInitializePluginsManagerException.printStackTrace();
            throw new CantStartPlatformException();
        }

        /**
         * I will give the User Manager access to the File System so it can load and save user information from
         * persistent media.
         */

        UserManager userManager =  ((UserLayer) mUserLayer).getUserManager();

        ((DealsWithFileSystem) userManager).setPluginFileSystem(os.getPlugInFileSystem());
        ((DealsWithEvents) userManager).setEventManager(eventManager);
        
        try
        {
            /**
             * As any other plugin, this one will need its identity in order to access the data he persisted before.
             */
        
            UUID pluginID = pluginsManager.getPluginId((Plugin) userManager);
            ((Plugin) userManager).setId(pluginID);
        }
        catch (PluginNotRecognizedException pluginNotRecognizedException)
        {
            /**
             * In this case this exception means the platform cannot start, as it cannot work without and active user
             * Manager.  
             */
            System.err.println("PluginNotRecognizedException: " + pluginNotRecognizedException.getMessage());
            pluginNotRecognizedException.printStackTrace();
            throw new CantStartPlatformException();
        }
        


        /**
         * I will give the Crypto Wallet Manager of each crypto network access to the File System and Event Manager so
         * it can load and save and load information from persistent media and also raise events.
         */

        PlatformService cryptoNetworkService =  ((CryptoNetworkLayer) mCryptoNetworkLayer).getCryptoNetwork(CryptoNetworks.BITCOIN);

        ((DealsWithFileSystem) cryptoNetworkService).setPluginFileSystem(os.getPlugInFileSystem());
        ((DealsWithEvents) cryptoNetworkService).setEventManager(eventManager);

        try
        {
            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */

            UUID pluginID = pluginsManager.getPluginId((Plugin) cryptoNetworkService);
            ((Plugin) cryptoNetworkService).setId(pluginID);

            cryptoNetworkService.start();
        }
        catch (PluginNotRecognizedException pluginNotRecognizedException)
        {
            /**
             * Even if it is not desirable, the platform can still start without one crypto network. I will simply ask
             * this module to stop running. 
             */
            System.err.println("PluginNotRecognizedException: " + pluginNotRecognizedException.getMessage());
            pluginNotRecognizedException.printStackTrace();
            
            cryptoNetworkService.stop();
        }
        
        
        
        /**
         * I will give the Wallet Manager access to the File System and to the Event Manager
         */

        PlatformService walletManager =  ((ModuleLayer) mModuleLayer).getWalletManager();

        ((DealsWithFileSystem) walletManager).setPluginFileSystem(os.getPlugInFileSystem());
        ((DealsWithEvents) walletManager).setEventManager(eventManager);


        try
        {
            /**
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */

            UUID pluginID = pluginsManager.getPluginId((Plugin) walletManager);
            ((Plugin) walletManager).setId(pluginID);

            walletManager.start();
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
        
        
        
        
        
        
        
        
        
        
        
        
        walletManager.start();

        
        
        
        
        
        
        
        
        
        
        
        
        /**
         * Now I will recover the last state, in order to allow the end user to continue where he was.The first thing
         * to do is to get the file where the last state was saved.
         *
         * It is important to note that the recover of the last state comes after all the initialization process is done,
         * because if not, events raised during this recovery could not be handled by the corresponding listeners.
         */

        try {

            PlatformFile platformStateFile =  os.getPlatformFileSystem().getFile(
                    DeviceDirectory.PLATFORM.getName(),
                    PlatformFileName.LAST_STATE.getFileName(),
                    FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT
            );

            try {
                platformStateFile.loadToMemory();
            }
            catch (CantLoadFileException cantLoadFileException) {
                /**
                 * This really should never happen. But if it does...
                 */
                System.err.println("CantLoadFileException: " + cantLoadFileException.getMessage());
                cantLoadFileException.printStackTrace();
                throw new CantStartPlatformException();
            }

            UUID userId =  UUID.fromString(platformStateFile.getContent());

            try
            {
                ((UserLayer) mUserLayer).getUserManager().loadUser(userId);
            }
            catch (CantLoadUserException cantLoadUserException)
            {
                /**
                 * This really should never happen. But if it does...
                 */
                System.err.println("CantLoadUserException: " + cantLoadUserException.getMessage());
                cantLoadUserException.printStackTrace();
                throw new CantStartPlatformException();
            }

        }
        catch (FileNotFoundException fileNotFoundException)
        {
            /**
             * If there is no last state file, I assume this is the first time the com.bitdubai.platform is running on this device.
             * Under this situation I will do the following;
             *
             * 1) Create a new User with no password.
             * 2) Auto login that user.
             * 3) Save the last state of the com.bitdubai.platform.
             */

            User newUser;

            try {

                newUser = ((UserLayer) mUserLayer).getUserManager().createUser();
                newUser.login("");

            } catch (CantCreateUserException | LoginFailedException exception) {
                /**
                 * This really should never happen. But if it does...
                 */
                System.err.println("LoginFailedException or CantCreateUserException: " + exception.getMessage());
                exception.printStackTrace();
                throw new CantStartPlatformException();
            }

             PlatformFile platformStateFile =  os.getPlatformFileSystem().createFile(
                    DeviceDirectory.PLATFORM.getName(),
                    PlatformFileName.LAST_STATE.getFileName(),
                    FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT
            );

            platformStateFile.setContent(newUser.getId().toString());

            try {
                platformStateFile.persistToMedia();
            } catch (CantPersistFileException cantPersistFileException) {
                /**
                 * This really should never happen. But if it does...
                 */
                System.err.println("Cant persist com.bitdubai.platform state to media: " + cantPersistFileException.getMessage());
                cantPersistFileException.printStackTrace();
                throw new CantStartPlatformException();
            }
        }

    }
}
