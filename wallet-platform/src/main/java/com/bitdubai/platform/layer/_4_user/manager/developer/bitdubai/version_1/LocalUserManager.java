package com.bitdubai.platform.layer._4_user.manager.developer.bitdubai.version_1;

import com.bitdubai.platform.layer._2_event.manager.DealsWithEvents;
import com.bitdubai.platform.layer._2_event.EventManager;
import com.bitdubai.platform.layer._3_os.FileSystem;
import com.bitdubai.platform.layer._3_os.DealsWithFileSystem;
import com.bitdubai.platform.layer._4_user.manager.CantCreateUserException;
import com.bitdubai.platform.layer._4_user.manager.CantLoadUserException;
import com.bitdubai.platform.layer._4_user.User;
import com.bitdubai.platform.layer._4_user.UserManager;

import java.util.UUID;

/**
 * Created by ciencias on 22.01.15.
 */
public class LocalUserManager implements UserManager,DealsWithFileSystem, DealsWithEvents {

    /**
     * UserManager Interface member variables.
     */
    User mLoggedInUser;

    /**
     * UsesFileSystem Interface member variables.
     */
    FileSystem fileSystem;

    /**
     * DealWithEvents Interface member variables.
     */
    EventManager eventManager;

    /**
     * UserManager Interface implementation.
     */

    @Override
    public User getLoggedInUser() {
        return mLoggedInUser;
    }

    @Override
    public User createUser() throws CantCreateUserException {

        try
        {
            User user = new PlatformUser();
            ((DealsWithFileSystem) user).setFileSystem(this.fileSystem);
            user.createUser();

            return user;
        }
        catch (CantCreateUserException cantCreateUserException)
        {
            /**
             * This is bad, the only thing I can do is to throw the exception again.
             */
            System.err.println("CantPersistUserException: " + cantCreateUserException.getMessage());
            cantCreateUserException.printStackTrace();
            throw cantCreateUserException;
        }

    }

    @Override
    public void loadUser(UUID id) throws CantLoadUserException  {

        try
        {
            User user = new PlatformUser();
            ((DealsWithFileSystem) user).setFileSystem(this.fileSystem);
            user.loadUser(id);

            mLoggedInUser = user;
        }
        catch (CantLoadUserException cantLoadUserException)
        {
            /**
             * This is bad, the only thing I can do is to throw the exception again.
             */
            System.err.println("CantLoadUserException: " + cantLoadUserException.getMessage());
            cantLoadUserException.printStackTrace();
            throw cantLoadUserException;
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

}
