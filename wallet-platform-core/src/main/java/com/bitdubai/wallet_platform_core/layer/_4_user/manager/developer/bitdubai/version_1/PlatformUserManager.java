package com.bitdubai.wallet_platform_core.layer._4_user.manager.developer.bitdubai.version_1;

import com.bitdubai.wallet_platform_api.PlatformService;
import com.bitdubai.wallet_platform_api.Plugin;
import com.bitdubai.wallet_platform_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.wallet_platform_api.layer._2_event.manager.DealsWithEvents;
import com.bitdubai.wallet_platform_api.layer._2_event.EventManager;
import com.bitdubai.wallet_platform_api.layer._2_event.manager.EventHandler;
import com.bitdubai.wallet_platform_api.layer._2_event.manager.EventListener;
import com.bitdubai.wallet_platform_api.layer._2_event.manager.EventType;
import com.bitdubai.wallet_platform_api.layer._3_os.PluginFileSystem;
import com.bitdubai.wallet_platform_api.layer._3_os.DealsWithFileSystem;
import com.bitdubai.wallet_platform_api.layer._4_user.manager.CantCreateUserException;
import com.bitdubai.wallet_platform_api.layer._4_user.manager.CantLoadUserException;
import com.bitdubai.wallet_platform_api.layer._4_user.User;
import com.bitdubai.wallet_platform_api.layer._4_user.UserManager;
import com.bitdubai.wallet_platform_core.layer._11_module.wallet_manager.developer.bitdubai.version_1.UserCreatedEventHandler;
import com.bitdubai.wallet_platform_core.layer._11_module.wallet_manager.developer.bitdubai.version_1.UserLoggedInEventHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by ciencias on 22.01.15.
 */
public class PlatformUserManager implements PlatformService,UserManager,DealsWithFileSystem, DealsWithEvents, Plugin {

    /**
     * PlatformService Interface member variables.
     */
    ServiceStatus serviceStatus;
    List<EventListener> listenersAdded = new ArrayList<>();
        
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
     * PlatformService Interface implementation.
     */

    @Override
    public void start() {

        /**
         * I will initialize the handling of com.bitdubai.platform events.
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
    }

    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }
    
    
    
    
    
    
    
    
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
