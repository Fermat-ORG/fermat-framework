package com.bitdubai.wallet_platform_core.layer._4_user.device_user.developer.bitdubai.version_1;

import com.bitdubai.wallet_platform_api.Addon;
import com.bitdubai.wallet_platform_api.Service;
import com.bitdubai.wallet_platform_api.layer._13_module.Modules;
import com.bitdubai.wallet_platform_api.layer._1_definition.enums.DeviceDirectory;
import com.bitdubai.wallet_platform_api.layer._1_definition.enums.PlatformFileName;
import com.bitdubai.wallet_platform_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.error_manager.ErrorManager;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.EventManager;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.EventHandler;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.EventListener;
import com.bitdubai.wallet_platform_api.layer._3_os.file_system.*;
import com.bitdubai.wallet_platform_api.layer._4_user.device_user.exceptions.CantCreateDeviceUserException;
import com.bitdubai.wallet_platform_api.layer._4_user.device_user.exceptions.CantLoadDeviceUserException;
import com.bitdubai.wallet_platform_api.layer._4_user.DeviceUser;
import com.bitdubai.wallet_platform_api.layer._4_user.device_user.DeviceUserManager;
import com.bitdubai.wallet_platform_api.layer._4_user.device_user.exceptions.LoginFailedException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by ciencias on 22.01.15.
 */

/**
 * The User Manager knows the users managed by the current device.
 * 
 * It is responsible for login in users to the current device.
 */

public class DeviceUserAddonRoot implements Service, DeviceUserManager, DealsWithPlatformFileSystem, DealsWithEvents, DealsWithErrors, Addon {

    /**
     * PlatformService Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;
    List<EventListener> listenersAdded = new ArrayList<>();
        
    /**
     * UserManager Interface member variables.
     */
    DeviceUser mLoggedInDeviceUser;

    /**
     * DealsWithPlatformFileSystem Interface member variables.
     */
    PlatformFileSystem platformFileSystem;

    /**
     * DealWithEvents Interface member variables.
     */
    EventManager eventManager;

    /**
     * Plugin Interface member variables.
     */
    UUID pluginId;


    /**
     * PlatformService Interface implementation.
     */

    @Override
    public void start() {


        /**
         * Now I will recover the last state, If there was a user logged in before closing the APP the last time, I will
         * re-loggin it,  
         */


        /**
         * If there is no last state file, I assume this is the first time the platform is running on this device.
         * Under this situation I will do the following;
         *
         * 1) Create a new User with no password.
         * 2) Auto login that user.
         * 3) Save the last state for future use.
         */
        
        
        
        /**
         * I will initialize the handling of platform events.
         */

        EventListener eventListener;
        EventHandler eventHandler;

       // eventListener = eventManager.getNewListener(EventType.USER_CREATED);
       // eventHandler = new UserCreatedEventHandler();
       // ((UserCreatedEventHandler) eventHandler).setWalletManager(this);
       // eventListener.setEventHandler(eventHandler);
       // eventManager.addListener(eventListener);
       // listenersAdded.add(eventListener);

        this.serviceStatus = ServiceStatus.STARTED;
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

        /**
         * I will remove all the event listeners registered with the event manager.
         */

        for (EventListener eventListener : listenersAdded) {
            eventManager.removeListener(eventListener);
        }

        listenersAdded.clear();

        this.serviceStatus = ServiceStatus.STOPPED;

    }

    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }

    
    /**
     * UserManager Interface implementation.
     */

    @Override
    public DeviceUser getLoggedInUser() {
        return mLoggedInDeviceUser;
    }

    @Override
    public DeviceUser createUser() throws CantCreateDeviceUserException {

        try
        {
            DeviceUser deviceUser = new PlatformDeviceUser();
            ((DealsWithPlatformFileSystem) deviceUser).setPlatformFileSystem(this.platformFileSystem);
            deviceUser.createUser();

            return deviceUser;
        }
        catch (CantCreateDeviceUserException cantCreateDeviceUserException)
        {
            /**
             * This is bad, the only thing I can do is to throw the exception again.
             */
            System.err.println("CantPersistUserException: " + cantCreateDeviceUserException.getMessage());
            cantCreateDeviceUserException.printStackTrace();
            throw cantCreateDeviceUserException;
        }

    }

    @Override
    public void loadUser(UUID id) throws CantLoadDeviceUserException {

        try
        {
            DeviceUser deviceUser = new PlatformDeviceUser();
            ((DealsWithPlatformFileSystem) deviceUser).setPlatformFileSystem(this.platformFileSystem);
            deviceUser.loadUser(id);

            mLoggedInDeviceUser = deviceUser;
        }
        catch (CantLoadDeviceUserException cantLoadDeviceUserException)
        {
            /**
             * This is bad, the only thing I can do is to throw the exception again.
             */
            System.err.println("CantLoadUserException: " + cantLoadDeviceUserException.getMessage());
            cantLoadDeviceUserException.printStackTrace();
            throw cantLoadDeviceUserException;
        }

    }









    /**
     * DealsWithPlatformFileSystem Interface implementation.
     */

    @Override
    public void setPlatformFileSystem(PlatformFileSystem platformFileSystem) {
        this.platformFileSystem = platformFileSystem;
    }

    /**
     * DealWithEvents Interface implementation.
     */

    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    /**
     *DealWithErrors Interface implementation.
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {

    }

    
    
    
    private void recoverLastState () throws CantCreateDeviceUserException {

        try {

            PlatformDataFile platformStateFile =  this.platformFileSystem.getFile(
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
               // throw new CantStartPlatformException();  TODO: Luis checkear esto
            }

            UUID userId =  UUID.fromString(platformStateFile.getContent());

            // Luis TODO: de aca tiene que sacar no solo el usuario sino tambien el modulo donde estuvo por ultima vez

            try
            {
                ((DeviceUserManager) this).loadUser(userId);
            }
            catch (CantLoadDeviceUserException cantLoadDeviceUserException)
            {
                /**
                 * This really should never happen. But if it does...
                 */
                System.err.println("CantLoadUserException: " + cantLoadDeviceUserException.getMessage());
                cantLoadDeviceUserException.printStackTrace();
                // throw new CantStartPlatformException();  TODO: Luis checkear esto
            }

        }
        catch (FileNotFoundException fileNotFoundException)
        {
            /**
             * If there is no last state file, I assume this is the first time the platform is running on this device.
             * Under this situation I will do the following;
             *
             * 1) Create a new User with no password.
             * 2) Auto login that user.
             * 3) Save the last state of the platform.
             */

            DeviceUser newDeviceUser = this.createUser();


            try {

                newDeviceUser.login("");

                // Luis TODO; como se conecta esto con el communication layer que usa el usuario logeado del Platform Context?

            } catch (LoginFailedException exception) {
                /**
                 * This really should never happen. But if it does...
                 */
                System.err.println("LoginFailedException or CantCreateUserException: " + exception.getMessage());
                exception.printStackTrace();
                // throw new CantStartPlatformException();  TODO: Luis checkear esto
            }

            PlatformDataFile platformStateFile =  this.platformFileSystem.createFile(
                    DeviceDirectory.PLATFORM.getName(),
                    PlatformFileName.LAST_STATE.getFileName(),
                    FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT
            );

            String content = newDeviceUser.getId().toString() + ";" + Modules.WALLET_RUNTIME.getModuleName();

            platformStateFile.setContent(content);

            try {
                platformStateFile.persistToMedia();
            } catch (CantPersistFileException cantPersistFileException) {
                /**
                 * This really should never happen. But if it does...
                 */
                System.err.println("Cant persist com.bitdubai.platform state to media: " + cantPersistFileException.getMessage());
                cantPersistFileException.printStackTrace();
                // throw new CantStartPlatformException();  TODO: Luis checkear esto
            }
        }


    }
    
}
