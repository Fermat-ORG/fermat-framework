package com.bitdubai.smartwallet.platform.layer._4_user.manager.developer.bitdubai.version_1;

import com.bitdubai.smartwallet.platform.layer._2_event.*;
import com.bitdubai.smartwallet.platform.layer._2_event.manager.DealWithEvents;
import com.bitdubai.smartwallet.platform.layer._2_event.manager.EventType;
import com.bitdubai.smartwallet.platform.layer._2_event.manager.PlatformEvent;
import com.bitdubai.smartwallet.platform.layer._2_event.manager.UserLoggedInEvent;
import com.bitdubai.smartwallet.platform.layer._3_os.*;
import com.bitdubai.smartwallet.platform.layer._4_user.*;
import com.bitdubai.smartwallet.platform.layer._4_user.manager.*;

import java.util.UUID;

/**
 * Created by ciencias on 22.01.15.
 */
public class PlatformUser implements User,DealWithFileSystem, DealWithEvents {

    /**
     * User Interface member variables.
     */
    UUID mId;
    String mUserName = "";
    String mPassword = "";
    User_Status mStatus;

    /**
     * UsesFileSystem Interface member variables.
     */
    FileSystem mFileSystem;

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

        mId = UUID.randomUUID();
        mStatus = User_Status.LOGGED_OUT;

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

    }

    /**
     * This constructor is to be used to regenerate a user that was already logged in while the last session was
     * destroyed.
     */
    public void loadUser (UUID id) throws CantLoadUserException {
        mId = id;
        this.changeToLoggedInStatus();

        try {
            load();
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
        return mId;
    }

    @Override
    public String getUserName() {
        return mUserName;
    }

    @Override
    public User_Status getStatus() {
        return mStatus;
    }

    @Override
    public void login(String password) throws LoginFailedException {
        if (mPassword != password) {
            throw new LoginFailedException();
        }
        else
        {
            mStatus = User_Status.LOGGED_IN;
        }
    }



    /**
     * UsesFileSystem Interface implementation.
     */

    @Override
    public void setFileSystem(FileSystem fileSystem) {
        mFileSystem = fileSystem;
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
        mStatus = User_Status.LOGGED_IN;

        /**
         * Now I fire the Logged In event.
         */

        PlatformEvent platformEvent = eventManager.getNewEvent(EventType.USER_LOGGED_IN);
        ((UserLoggedInEvent) platformEvent).setUserId(mId);
        eventManager.raiseEvent(platformEvent);

    }


    private void persist() throws CantPersistUserException{

        PlatformFile file = mFileSystem.createFile(mId.toString(), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);

        file.setContent(mUserName + ";" + mPassword);

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
            PlatformFile file = mFileSystem.getFile(mId.toString(), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            file.loadToMemory();
            String[] values = file.getContent().split(";", -1);

            mUserName = values[0];
            mPassword = values[1];

        }
        catch (FileNotFoundException|CantLoadFileException ex)
        {
            /**
             * This is bad, but lets handle it...
             */
            System.err.println("CantPersistFileException: " + ex.getMessage());
            ex.printStackTrace();
            throw new CantLoadUserException();
        }

    }


}
