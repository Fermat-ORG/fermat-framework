package com.bitdubai.wallet_platform_core.layer._4_user.manager.developer.bitdubai.version_1;

import com.bitdubai.wallet_platform_api.DealsWithPluginIdentity;
import com.bitdubai.wallet_platform_api.layer._1_definition.enums.DeviceDirectory;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.error_manager.ErrorManager;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.*;
import com.bitdubai.wallet_platform_api.layer._1_definition.event.PlatformEvent;
import com.bitdubai.wallet_platform_api.layer._3_os.File_System.*;
import com.bitdubai.wallet_platform_api.layer._4_user.*;
import com.bitdubai.wallet_platform_api.layer._4_user.manager.*;

import java.util.UUID;

/**
 * Created by ciencias on 22.01.15.
 */
public class PlatformUser implements User,DealsWithFileSystem, DealsWithEvents, DealsWithErrors, DealsWithPluginIdentity {

    /**
     * User Interface member variables.
     */
    UUID userId;
    String userName = "";
    String password = "";
    UserStatus status;

    /**
     * UsesFileSystem Interface member variables.
     */
    PluginFileSystem pluginFileSystem;

    /**
     * DealWithEvents Interface member variables.
     */
    EventManager eventManager;

    /**
     * DealsWithPluginIdentity Interface member variables.
     */
    UUID pluginId;

    /**
     * User Interface implementation.
     */


    /**
     * This method is to be used for creating a new user.
     */

    public void createUser () throws CantCreateUserException {

        this.userId = UUID.randomUUID();
        this.status = UserStatus.LOGGED_OUT;

        try {
            persist();
        }
        catch (CantPersistUserException cantPersistUserException)
        {
            /**
             * This is bad, but lets handle it...
             */
            System.err.println("CantPersistUserException: " + cantPersistUserException.getMessage());
            cantPersistUserException.printStackTrace();
            throw new CantCreateUserException();
        }

        /**
         * Now I fire the User Created event.
         */

        PlatformEvent platformEvent = eventManager.getNewEvent(EventType.USER_CREATED);
        ((UserCreatedEvent) platformEvent).setUserId(this.userId );
        eventManager.raiseEvent(platformEvent);

    }

    /**
     * This method is to be used to regenerate a user that was already logged in while the last session was
     * destroyed.
     */

    public void loadUser (UUID id) throws CantLoadUserException {
        this.userId = id;

        try {
            load();
            this.changeToLoggedInStatus();
        }
        catch (CantLoadUserException cantLoadUserException)
        {
            /**
             * This is bad, but lets handle it...
             */
            System.err.println("CantLoadUserException: " + cantLoadUserException.getMessage());
            cantLoadUserException.printStackTrace();
            throw new CantLoadUserException();
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
    public UserStatus getStatus() {
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
     * UsesFileSystem Interface implementation.
     */

    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
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

        this.status = UserStatus.LOGGED_IN;

        /**
         * Now I fire the Logged In event.
         */

        PlatformEvent platformEvent = eventManager.getNewEvent(EventType.USER_LOGGED_IN);
        ((UserLoggedInEvent) platformEvent).setUserId(this.userId );
        eventManager.raiseEvent(platformEvent);

    }


    private void persist() throws CantPersistUserException{

        PluginDataFile file = this.pluginFileSystem.createDataFile(
                pluginId,
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
            throw new CantPersistUserException();
        }
    }


    private void load() throws CantLoadUserException {

        try {
            
            PluginDataFile file = this.pluginFileSystem.getDataFile(
                    pluginId,
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
        catch (FileNotFoundException |CantLoadFileException ex)
        {
            /**
             * This is bad, but lets handle it...
             */
            System.err.println("FileNotFoundException or CantLoadFileException: " + ex.getMessage());
            ex.printStackTrace();
            throw new CantLoadUserException();
        }
    }


    /**
     * DealsWithPluginIdentity methods implementation.
     */

    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }
}
