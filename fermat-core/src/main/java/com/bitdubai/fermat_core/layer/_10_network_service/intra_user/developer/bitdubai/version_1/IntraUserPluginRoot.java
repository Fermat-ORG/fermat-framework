package com.bitdubai.fermat_core.layer._10_network_service.intra_user.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer._10_network_service.intra_user.IntraUserManager;
import com.bitdubai.fermat_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer._2_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer._2_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer._2_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_api.layer._2_platform_service.event_manager.EventHandler;
import com.bitdubai.fermat_api.layer._2_platform_service.event_manager.EventListener;
import com.bitdubai.fermat_api.layer._2_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_api.layer._2_platform_service.event_manager.EventType;
import com.bitdubai.fermat_api.layer._3_os.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer._3_os.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer._4_user.DeviceUser;
import com.bitdubai.fermat_api.layer._10_network_service.NetworkService;
import com.bitdubai.fermat_api.layer._10_network_service.intra_user.IntraUser;
import com.bitdubai.fermat_api.layer._10_network_service.intra_user.IntraUserNetworkService;
import com.bitdubai.fermat_core.layer._10_network_service.intra_user.developer.bitdubai.version_1.EventHandlers.UserLoggedInEventHandler;
import com.bitdubai.fermat_core.layer._10_network_service.intra_user.developer.bitdubai.version_1.EventHandlers.UserLoggedOutEventHandler;

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
 * All other plugins providing services on top of a communication involving intra users are requested to use connections
 * provided by this plugin, as a way to ensure this plug in can monitor the activity going on and use the statistics to 
 * maintain a good quality cache. 
 *
 * * * * * * * * * * * * * * * * * *
 */

public class IntraUserPluginRoot implements Service, NetworkService, IntraUserNetworkService, IntraUserManager, DealsWithEvents, DealsWithErrors, DealsWithPluginFileSystem,Plugin {

    
   // Loui TODO: Escuchar el evento Intra User Logged In, y  -- cuando ocurra ejecutar el metodo logIn (UUID userId)

   // Loui TODO: Escuchar el evento Intra User Logged Out, y -- cuando ocurra ejecutar el metodo logOut (UUID userId)

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
     * IntraUserPluginRoot Interface implementation.
     */
    public void logIn (UUID userId) {
        
        // LUIS TODO: Aca debe registrar este usuario con el Communication Layer, de manera que pueda ser encontrado remotamente por otros usuarios.
        
        // LOUI TODO: Esta clase debe implementar la interface DealsWithCommunicationLayerManager y recibir desde la plataforma la referencia al communicationLayer a traves de la interfase CommunicationLayerManger.
        
    }


    public void logOut (UUID userId) {


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

        eventListener = eventManager.getNewListener(EventType.USER_LOGGED_IN);
        eventHandler = new UserLoggedInEventHandler();
        ((UserLoggedInEventHandler) eventHandler).setIntraUserManager(this);
        eventListener.setEventHandler(eventHandler);
        eventManager.addListener(eventListener);
        listenersAdded.add(eventListener);

        eventListener = eventManager.getNewListener(EventType.USER_LOGGED_OUT);
        eventHandler = new UserLoggedOutEventHandler();
        ((UserLoggedOutEventHandler) eventHandler).setIntraUserManager(this);
        eventListener.setEventHandler(eventHandler);
        eventManager.addListener(eventListener);
        listenersAdded.add(eventListener);
        
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
     * SystemUserPluginRoot Interface implementation.
     */
   
    public void userLoggedIn (DeviceUser deviceUser){

    }

    public void userLoggedOut (DeviceUser deviceUser){


    }

    /**
     * NetworkService Interface implementation.
     */
    
    @Override
    public UUID getId() {
        return null; // LUIS: TODO: Devolver el ID del IntraUser Logeado.
    }
    
    /**
     * UserNetworkService Interface implementation.
     */

    @Override
    public IntraUser getSystemUser(UUID userId) {
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
