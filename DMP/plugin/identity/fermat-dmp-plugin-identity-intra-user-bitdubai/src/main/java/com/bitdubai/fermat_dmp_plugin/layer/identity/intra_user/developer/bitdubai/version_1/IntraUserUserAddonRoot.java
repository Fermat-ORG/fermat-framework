package com.bitdubai.fermat_dmp_plugin.layer.identity.intra_user.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPlatformDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PlatformDatabaseSystem;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventHandler;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventListener;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventSource;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventType;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPlatformFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PlatformFileSystem;
import com.bitdubai.fermat_api.layer.pip_user.device_user.DealsWithDeviceUsers;
import com.bitdubai.fermat_api.layer.pip_user.device_user.DeviceUserManager;
import com.bitdubai.fermat_api.layer.pip_user.User;
import com.bitdubai.fermat_api.layer.pip_user.intra_user.IntraUserManager;
import com.bitdubai.fermat_api.layer.pip_user.intra_user.exceptions.CantCreateIntraUserException;
import com.bitdubai.fermat_dmp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.event_handlers.UserCratedEventHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by loui on 22/02/15.
 */


public class IntraUserUserAddonRoot implements Addon, DealsWithDeviceUsers, DealsWithEvents, DealsWithErrors, DealsWithPlatformDatabaseSystem,DealsWithPlatformFileSystem, IntraUserManager, Service  {


    /**
     * DealsWithDeviceUsers Interface member variables.
     */

    DeviceUserManager deviceUserManager;

    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;

    /**
     * DealWithEvents Interface member variables.
     */
    EventManager eventManager;

    /**
     * DealsWithPlatformDatabaseSystem Interface member variables.
     */
    PlatformDatabaseSystem platformDatabaseSystem;

    /**
     * DealsWithPlatformFileSystem Interface member variables.
     */
    PlatformFileSystem platformFileSystem;

    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;
    List<EventListener> listenersAdded = new ArrayList<>();


    /**
     * DealWithDeviceUser Interface implementation.
     */

    @Override
    public void setDeviceUserManager(DeviceUserManager deviceUserManager) {
        this.deviceUserManager = deviceUserManager;
    }



    /**
     * DealWithErrors Interface implementation.
     */

    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * DealWithEvents Interface implementation.
     */

    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    /**
     * DealsWithPluginDatabaseSystem interface implementation.
     */
    @Override
    public void setPlatformDatabaseSystem(PlatformDatabaseSystem platformDatabaseSystem) {
        this.platformDatabaseSystem = platformDatabaseSystem;
    }
   
    /**
     * DealWithPlatformFileSystem Interface implementation.
     */
    @Override
    public void setPlatformFileSystem(PlatformFileSystem platformFileSystem) {
            this.platformFileSystem = platformFileSystem;
    }


    /**
     * IntraUserManager Interface implementation.
     */

    @Override
    public void crateUser(UUID userId) throws CantCreateIntraUserException {

    }

    @Override
    public User getUser(UUID id){
        return null;
    }


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




}
