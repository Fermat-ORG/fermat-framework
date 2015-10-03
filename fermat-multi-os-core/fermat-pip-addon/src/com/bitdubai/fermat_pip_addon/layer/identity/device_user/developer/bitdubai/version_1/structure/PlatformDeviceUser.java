package com.bitdubai.fermat_pip_addon.layer.identity.device_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer._1_definition.enums.Addons;
import com.bitdubai.fermat_api.layer._1_definition.enums.DeviceDirectory;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.UnexpectedAddonsExceptionSeverity;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.*;
import com.bitdubai.fermat_api.layer._1_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.events.DeviceUserCreatedEvent;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.events.DeviceUserLoggedInEvent;
import com.bitdubai.fermat_api.layer._2_os.file_system.*;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_api.layer._5_user.device_user.DeviceUser;
import com.bitdubai.fermat_api.layer._5_user.device_user.*;
import com.bitdubai.fermat_api.layer._5_user.device_user.exceptions.CantCreateDeviceUserException;
import com.bitdubai.fermat_api.layer._5_user.device_user.exceptions.CantLoadDeviceUserException;
import com.bitdubai.fermat_api.layer._5_user.device_user.exceptions.CantPersistDeviceUserException;
import com.bitdubai.fermat_api.layer._5_user.device_user.exceptions.LoginFailedException;

import java.util.UUID;

/**
 * Created by ciencias on 22.01.15.
 */
public class PlatformDeviceUser implements  DealsWithEvents, DealsWithErrors,DealsWithPlatformFileSystem,DeviceUser {

    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;

    /**
     * DealWithEvents Interface member variables.
     */
    EventManager eventManager;

    /**
     * DealsWithPlatformFileSystem Interface member variables.
     */
    PlatformFileSystem platformFileSystem;


    /**
     * DeviceUser Interface member variables.
     */
    String userName = "";
    String password = "";
    DeviceUserStatus status;
    UUID userId;

    /**
     *DealWithErrors Interface implementation.
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
     * DealsWithPlatformFileSystem Interface implementation.
     */

    @Override
    public void setPlatformFileSystem(PlatformFileSystem platformFileSystem) {
        this.platformFileSystem = platformFileSystem;
    }

    /**
     * DeviceUser Interface implementation.
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
            errorManager.reportUnexpectedAddonsException(Addons.DEVICE_USER, UnexpectedAddonsExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_ADDONS, cantPersistDeviceUserException);
            throw new CantCreateDeviceUserException();
        }

        /**
         * Now I fire the User Created event.
         */

        PlatformEvent platformEvent = eventManager.getNewEvent(EventType.DEVICE_USER_CREATED);
        ((DeviceUserCreatedEvent) platformEvent).setUserId(this.userId );
        platformEvent.setSource(EventSource.USER_DEVICE_USER_PLUGIN);
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
            errorManager.reportUnexpectedAddonsException(Addons.DEVICE_USER, UnexpectedAddonsExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_ADDONS, cantLoadDeviceUserException);
            throw new CantLoadDeviceUserException();
        }

    }

    @Override
    public UUID getId(){
        return this.userId;
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
     * Private methods implementation.
     */

    private void changeToLoggedInStatus(){

        this.status = DeviceUserStatus.LOGGED_IN;

        /**
         * Now I fire the Logged In event.
         */

        PlatformEvent platformEvent = eventManager.getNewEvent(EventType.DEVICE_USER_LOGGED_IN);
        ((DeviceUserLoggedInEvent) platformEvent).setUserId(this.userId );
        platformEvent.setSource(EventSource.USER_DEVICE_USER_PLUGIN);
        eventManager.raiseEvent(platformEvent);

    }


    private void persist() throws CantPersistDeviceUserException {

        PlatformTextFile file = null;
        try{
            file = this.platformFileSystem.createFile(
                    DeviceDirectory.LOCAL_USERS.getName(),
                    this.userId.toString(),
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );
        }
        catch(CantCreateFileException cantCreateFileException)
        {
            /**
             * This is bad, but lets handle it...
             */

            errorManager.reportUnexpectedAddonsException(Addons.DEVICE_USER, UnexpectedAddonsExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_ADDONS, cantCreateFileException);
            throw new CantPersistDeviceUserException();
        }


        file.setContent(this.userName + ";" + this.password);

        try {
            file.persistToMedia();
        }
        catch (CantPersistFileException cantPersistFileException) {
            /**
             * This is bad, but lets handle it...
             */
            errorManager.reportUnexpectedAddonsException(Addons.DEVICE_USER, UnexpectedAddonsExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_ADDONS, cantPersistFileException);
            throw new CantPersistDeviceUserException();
        }
    }


    private void load() throws CantLoadDeviceUserException {

        try {

            PlatformTextFile file = null;
            try{

                file = this.platformFileSystem.getFile(
                        DeviceDirectory.LOCAL_USERS.getName(),
                        this.userId.toString(),
                        FilePrivacy.PRIVATE,
                        FileLifeSpan.PERMANENT
                );
            }
            catch(CantCreateFileException cantCreateFileException)
            {
                /**
                 * This is bad, but lets handle it...
                 */
                errorManager.reportUnexpectedAddonsException(Addons.DEVICE_USER, UnexpectedAddonsExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_ADDONS, cantCreateFileException);
                throw new CantLoadDeviceUserException();
            }
            file.persistToMedia();

            String[] values = file.getContent().split(";", -1);

            this.userName = values[0];
            this.password = values[1];

        }
        catch (FileNotFoundException |CantPersistFileException cantPersistFileException)
        {
            /**
             * This is bad, but lets handle it...
             */
            errorManager.reportUnexpectedAddonsException(Addons.DEVICE_USER, UnexpectedAddonsExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_ADDONS, cantPersistFileException);
            throw new CantLoadDeviceUserException();
        }
    }


}
