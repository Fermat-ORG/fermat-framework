package com.bitdubai.smartwallet.platform.layer._4_user.manager.developer.bitdubai.version_1;

import com.bitdubai.smartwallet.platform.layer._3_os.FileSystem;
import com.bitdubai.smartwallet.platform.layer._3_os.UsesFileSystem;
import com.bitdubai.smartwallet.platform.layer._4_user.CantCreateUserException;
import com.bitdubai.smartwallet.platform.layer._4_user.CantLoadUserException;
import com.bitdubai.smartwallet.platform.layer._4_user.User;
import com.bitdubai.smartwallet.platform.layer._4_user.UserManager;

import java.util.UUID;

/**
 * Created by ciencias on 22.01.15.
 */
public class LocalUserManager implements UserManager,UsesFileSystem {

    /**
     * UserManager Interface member variables.
     */
    User mLoggedInUser;

    /**
     * UsesFileSystem Interface member variables.
     */
    FileSystem mFileSystem;


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
            ((UsesFileSystem) user).setFileSystem(mFileSystem);
            user.createUser();

            return user;
        }
        catch (CantCreateUserException cantCreateUserException)
        {
            /**
             * This is bad, the only thing I can do is to through the exception again.
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
            ((UsesFileSystem) user).setFileSystem(mFileSystem);
            user.loadUser(id);

            mLoggedInUser = user;
        }
        catch (CantLoadUserException cantLoadUserException)
        {
            /**
             * This is bad, the only thing I can do is to through the exception again.
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
        mFileSystem = fileSystem;
    }


}
