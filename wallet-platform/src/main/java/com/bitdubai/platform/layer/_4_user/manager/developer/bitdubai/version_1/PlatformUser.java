package com.bitdubai.platform.layer._4_user.manager.developer.bitdubai.version_1;

import com.bitdubai.platform.layer._1_definition.enums.DeviceDirectory;
import com.bitdubai.platform.layer._2_event.*;
import com.bitdubai.platform.layer._2_event.manager.DealsWithEvents;
import com.bitdubai.platform.layer._2_event.manager.EventType;
import com.bitdubai.platform.layer._1_definition.event.PlatformEvent;
import com.bitdubai.platform.layer._2_event.manager.UserLoggedInEvent;
import com.bitdubai.platform.layer._2_event.manager.developer.UserCreatedEvent;
import com.bitdubai.platform.layer._3_os.*;
import com.bitdubai.platform.layer._4_user.*;
import com.bitdubai.platform.layer._4_user.manager.*;

import java.util.UUID;

/**
 * Created by ciencias on 22.01.15.
 */
public class PlatformUser implements User,DealsWithFileSystem, DealsWithEvents {

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
    FileSystem fileSystem;

    /**
     * DealWithEvents Interface member variables.
     */
    EventManager eventManager;


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
    public void setFileSystem(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }


    /**
     * DealWithEvents Interface implementation.
     */

    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
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

        PlatformFile file = this.fileSystem.createFile(
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
            PlatformFile file = this.fileSystem.getFile(
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
        catch (FileNotFoundException|CantLoadFileException ex)
        {
            /**
             * This is bad, but lets handle it...
             */
            System.err.println("FileNotFoundException or CantLoadFileException: " + ex.getMessage());
            ex.printStackTrace();
            throw new CantLoadUserException();
        }
    }


}
