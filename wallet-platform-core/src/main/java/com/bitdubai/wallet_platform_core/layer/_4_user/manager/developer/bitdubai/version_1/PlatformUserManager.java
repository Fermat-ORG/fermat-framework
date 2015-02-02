package com.bitdubai.wallet_platform_core.layer._4_user.manager.developer.bitdubai.version_1;

import com.bitdubai.wallet_platform_api.Plugin;
import com.bitdubai.wallet_platform_api.layer._2_event.manager.DealsWithEvents;
import com.bitdubai.wallet_platform_api.layer._2_event.EventManager;
import com.bitdubai.wallet_platform_api.layer._3_os.PluginFileSystem;
import com.bitdubai.wallet_platform_api.layer._3_os.DealsWithFileSystem;
import com.bitdubai.wallet_platform_api.layer._4_user.manager.CantCreateUserException;
import com.bitdubai.wallet_platform_api.layer._4_user.manager.CantLoadUserException;
import com.bitdubai.wallet_platform_api.layer._4_user.User;
import com.bitdubai.wallet_platform_api.layer._4_user.UserManager;

import java.util.UUID;

/**
 * Created by ciencias on 22.01.15.
 */
public class PlatformUserManager implements UserManager,DealsWithFileSystem, DealsWithEvents, Plugin {

    // todo:implement a service interface, geeralizar la interface actual
    /**
     * UserManager Interface member variables.
     */
    User mLoggedInUser;

    /**
     * UsesFileSystem Interface member variables.
     */
    PluginFileSystem pluginFileSystem;

    /**
     * DealWithEvents Interface member variables.
     */
    EventManager eventManager;

    /**
     * Plugin Interface member variables.
     */
    UUID pluginId;
    
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
            ((DealsWithFileSystem) user).setPluginFileSystem(this.pluginFileSystem);
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
            ((DealsWithFileSystem) user).setPluginFileSystem(this.pluginFileSystem);
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
     * Plugin Interface implementation.
     */
    
    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }
}
