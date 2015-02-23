package com.bitdubai.fermat_core.layer._4_user.intra_user.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer._1_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer._2_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer._2_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer._2_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_api.layer._2_platform_service.event_manager.EventHandler;
import com.bitdubai.fermat_api.layer._2_platform_service.event_manager.EventListener;
import com.bitdubai.fermat_api.layer._2_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_api.layer._2_platform_service.event_manager.EventSource;
import com.bitdubai.fermat_api.layer._2_platform_service.event_manager.EventType;
import com.bitdubai.fermat_api.layer._3_os.file_system.DealsWithPlatformFileSystem;
import com.bitdubai.fermat_api.layer._3_os.file_system.PlatformFileSystem;
import com.bitdubai.fermat_api.layer._4_user.User;
import com.bitdubai.fermat_api.layer._4_user.device_user.DealsWithDeviceUsers;
import com.bitdubai.fermat_api.layer._4_user.device_user.DeviceUserManager;
import com.bitdubai.fermat_api.layer._4_user.intra_user.IntraUserManager;
import com.bitdubai.fermat_api.layer._4_user.intra_user.exceptions.CantCreateIntraUserException;
import com.bitdubai.fermat_core.layer._4_user.intra_user.developer.bitdubai.version_1.event_handlers.UserCratedEventHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by loui on 22/02/15.
 */

/**
 * This addon handles the relationship between one device user and several intra users. That means tha a device user
 * could have many online identities detached from its device identity. In our first implementation it will only have
 * one, but it is expected in the future to enable that functionality.
 * 
 * * * * *
 * * * * * *
 */

public class IntraUserUserAddonRoot implements Service, User, IntraUserManager, DealsWithEvents, DealsWithPlatformFileSystem, DealsWithErrors, DealsWithDeviceUsers,  Addon {

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
     * Service Interface implementation.
     */
    @Override
    public void start() {

        EventListener eventListener;
        EventHandler eventHandler;

        eventListener = eventManager.getNewListener(EventType.DEVICE_USER_CREATED);
        eventHandler = new UserCratedEventHandler();
        ((UserCratedEventHandler) eventHandler).setIntraUserManager(this);
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

        this.serviceStatus = ServiceStatus.STOPPED;

    }

    private void intraUserLoggedIn(){
        
        PlatformEvent platformEvent = eventManager.getNewEvent(EventType.INTRA_USER_LOGGED_IN);
        platformEvent.setSource(EventSource.USER_INTRA_USER_PLUGIN);
        eventManager.raiseEvent(platformEvent);


    }
    
    @Override
    public ServiceStatus getStatus() {
        return serviceStatus;
    }

    /**
     * DealWithErrors Interface implementation.
     */

    @Override
    public void setErrorManager(ErrorManager errorManager) {

    }

    /**
     * DealWithEvents Interface implementation.
     */

    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }
   
   
    /**
     * DealWithPlatformFileSystem Interface implementation.
     */
    @Override
    public void setPlatformFileSystem(PlatformFileSystem platformFileSystem) {

    }
        
    
    /**
     * DealWithDeviceUser Interface implementation.
     */   
    @Override
    public void crateUser(UUID userId) throws CantCreateIntraUserException {
        
    }
    
    @Override
    public void setDeviceUserManager(DeviceUserManager deviceUserManager) {
        
    }


}
