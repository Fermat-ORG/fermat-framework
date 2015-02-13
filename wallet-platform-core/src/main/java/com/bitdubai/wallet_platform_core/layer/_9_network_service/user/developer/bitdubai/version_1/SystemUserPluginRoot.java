package com.bitdubai.wallet_platform_core.layer._9_network_service.user.developer.bitdubai.version_1;

import com.bitdubai.wallet_platform_api.Plugin;
import com.bitdubai.wallet_platform_api.Service;
import com.bitdubai.wallet_platform_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.error_manager.ErrorManager;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.EventHandler;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.EventListener;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.EventManager;
import com.bitdubai.wallet_platform_api.layer._3_os.File_System.DealsWithFileSystem;
import com.bitdubai.wallet_platform_api.layer._3_os.File_System.PluginFileSystem;
import com.bitdubai.wallet_platform_api.layer._4_user.User;
import com.bitdubai.wallet_platform_api.layer._9_network_service.NetworkService;
import com.bitdubai.wallet_platform_api.layer._9_network_service.user.SystemUser;
import com.bitdubai.wallet_platform_api.layer._9_network_service.user.UserNetworkService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by loui on 12/02/15.
 */

/**
 * The System User plugin knows about other users of the system as a whole. It manages a cache with the basic information of 
 * system users, like their name, picture and last location. It maintains that cache taking into account the level of 
 * interest on each individual. 
 * 
 * With some users, it maintains an online connection open. For that it takes into account the current logged in user
 * and the level of activities this user have with others.
 * 
 * For another level of closeness it maintains the cached information updated. 
 *
 * For a third group it will test from time to time if it is worth having them on the cache at all.
 * 
 * All other plugins providing services on top of a communication involving system users are requested to use connections
 * provided by this plugin, as a way to ensure this plug in can monitor the activity going on and use the statistics to 
 * maintain a good quality cache. 
 *
 * * * * * * * * * * * * * * * * * *
 */

public class SystemUserPluginRoot implements Service, NetworkService, UserNetworkService, DealsWithEvents, DealsWithErrors, DealsWithFileSystem,Plugin {

    
    // Loui TODO: Escuchar el evento User Logged In, y cuando ocurra ejecutar el metodo correspondiente 

    // Loui TODO: Escuchar el evento User Logged Out, y cuando ocurra ejecutar el metodo correspondiente 

    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;
    List<EventListener> listenersAdded = new ArrayList<>();
    
    /**
     * DealWithEvents Interface member variables.
     */
    EventManager eventManager;

    /**
     * UsesFileSystem Interface member variables.
     */
    PluginFileSystem pluginFileSystem;

    /**
     * DealsWithPluginIdentity Interface member variables.
     */
    UUID pluginId;


    /**
     * SystemUserPluginRoot Interface implementation.
     */  
    
    public void userLoggedIn (User user){

    }

    public void userLoggedOut (User user){


    }

    /**
     * Service Interface implementation.
     */
    
    @Override
    public void start() {
        /**
         * I will initialize the handling of com.bitdubai.platform events.
         */

        EventListener eventListener;
        EventHandler eventHandler;

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
     * UserNetworkService Interface implementation.
     */

    @Override
    public SystemUser getSystemUser(UUID userId) {
        return null;
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

    }


    /**
     *DealWithErrors Interface implementation.
     */

    @Override
    public void setErrorManager(ErrorManager errorManager) {

    }


    /**
     * DealsWithPluginIdentity methods implementation.
     */

    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }



}
