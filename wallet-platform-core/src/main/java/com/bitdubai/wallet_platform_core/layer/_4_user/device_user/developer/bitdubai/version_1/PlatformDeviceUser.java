package com.bitdubai.wallet_platform_core.layer._4_user.device_user.developer.bitdubai.version_1;

import com.bitdubai.wallet_platform_api.layer._1_definition.enums.DeviceDirectory;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.error_manager.ErrorManager;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.*;
import com.bitdubai.wallet_platform_api.layer._1_definition.event.PlatformEvent;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.events.UserCreatedEvent;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.events.UserLoggedInEvent;
import com.bitdubai.wallet_platform_api.layer._3_os.file_system.*;
import com.bitdubai.wallet_platform_api.layer._4_user.*;
import com.bitdubai.wallet_platform_api.layer._4_user.device_user.*;
import com.bitdubai.wallet_platform_api.layer._4_user.device_user.exceptions.CantCreateDeviceUserException;
import com.bitdubai.wallet_platform_api.layer._4_user.device_user.exceptions.CantLoadDeviceUserException;
import com.bitdubai.wallet_platform_api.layer._4_user.device_user.exceptions.CantPersistDeviceUserException;
import com.bitdubai.wallet_platform_api.layer._4_user.device_user.exceptions.LoginFailedException;

import java.util.UUID;

/**
 * Created by ciencias on 22.01.15.
 */
public class PlatformDeviceUser implements DeviceUser,DealsWithPlatformFileSystem, DealsWithEvents, DealsWithErrors {

    /**
     * User Interface member variables.
     */
    UUID userId;
    String userName = "";
    String password = "";
    DeviceUserStatus status;

    /**
     * DealsWithPlatformFileSystem Interface member variables.
     */
    PlatformFileSystem platformFileSystem;

    /**
     * DealWithEvents Interface member variables.
     */
    EventManager eventManager;

    /**
     * DealsWithPluginIdentity Interface member variables.
     */

    /**
     * User Interface implementation.
     */


    /**
     * This method is to be used for creating a new user.
     */

    public void createUser () throws CantCreateDeviceUserException {

        this.userId = UUID.randomUUID();
        this.status = DeviceUserStatus.LOGGED_OUT;

        try {
            persist();
        }
        catch (CantPersistDeviceUserException cantPersistDeviceUserException)
        {
            /**
             * This is bad, but lets handle it...
             */
            System.err.println("CantPersistUserException: " + cantPersistDeviceUserException.getMessage());
            cantPersistDeviceUserException.printStackTrace();
            throw new CantCreateDeviceUserException();
        }

        /**
         * Now I fire the User Created event.
         */

        PlatformEvent platformEvent = eventManager.getNewEvent(EventType.USER_CREATED);
        ((UserCreatedEvent) platformEvent).setUserId(this.userId );
        platformEvent.setSource(EventSource.user_device_user_plugin);
        eventManager.raiseEvent(platformEvent);

    }

    /**
     * This method is to be used to regenerate a user that was already logged in while the last session was
     * destroyed.
     */

    public void loadUser (UUID id) throws CantLoadDeviceUserException {
        this.userId = id;

        try {
            load();
            this.changeToLoggedInStatus();
        }
        catch (CantLoadDeviceUserException cantLoadDeviceUserException)
        {
            /**
             * This is bad, but lets handle it...
             */
            System.err.println("CantLoadUserException: " + cantLoadDeviceUserException.getMessage());
            cantLoadDeviceUserException.printStackTrace();
            throw new CantLoadDeviceUserException();
        }

    }


    @Override
    public UUID getId() {
        return this.userId ;
    }

    @Override
    public String getUserName() {
        return this.userName;
    }

    @Override
    public DeviceUserStatus getStatus() {
        return this.status;
    }

    @Override
    public void login(String password) throws LoginFailedException {
        if (this.password != password) {
            throw new LoginFailedException();
        }
        else
        {
            this.changeToLoggedInStatus();
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

    /**
     * Private methods implementation.
     */

    private void changeToLoggedInStatus(){

        this.status = DeviceUserStatus.LOGGED_IN;

        /**
         * Now I fire the Logged In event.
         */

        PlatformEvent platformEvent = eventManager.getNewEvent(EventType.USER_LOGGED_IN);
        ((UserLoggedInEvent) platformEvent).setUserId(this.userId );
        platformEvent.setSource(EventSource.user_device_user_plugin);
        eventManager.raiseEvent(platformEvent);

    }


    private void persist() throws CantPersistDeviceUserException {

        PlatformDataFile file = this.platformFileSystem.createFile(
                DeviceDirectory.LOCAL_USERS.getName(),
                this.userId.toString(),
                FilePrivacy.PRIVATE,
                FileLifeSpan.PERMANENT
        );

        file.setContent(this.userName + ";" + this.password);

        try {
            file.persistToMedia();
        }
        catch (CantPersistFileException cantPersistFileException) {
            /**
             * This is bad, but lets handle it...
             */
            System.err.println("CantPersistFileException: " + cantPersistFileException.getMessage());
            cantPersistFileException.printStackTrace();
            throw new CantPersistDeviceUserException();
        }
    }


    private void load() throws CantLoadDeviceUserException {

        try {
            
            PlatformDataFile file = this.platformFileSystem.getFile(
                    DeviceDirectory.LOCAL_USERS.getName(),
                    this.userId.toString(),
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );

            file.loadToMemory();

            String[] values = file.getContent().split(";", -1);

           this.userName = values[0];
            this.password = values[1];

        }
        catch (FileNotFoundException |CantLoadFileException  ex)
        {
            /**
             * This is bad, but lets handle it...
             */
            System.err.println("FileNotFoundException or CantLoadFileException: " + ex.getMessage());
            ex.printStackTrace();
            throw new CantLoadDeviceUserException();
        }
    }


    /**
     * DealsWithPluginIdentity methods implementation.
     */

}
