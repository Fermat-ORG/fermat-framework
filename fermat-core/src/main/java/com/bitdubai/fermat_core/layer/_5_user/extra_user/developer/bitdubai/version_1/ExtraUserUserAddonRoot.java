package com.bitdubai.fermat_core.layer._5_user.extra_user.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer._2_os.database_system.DealsWithPlatformDatabaseSystem;
import com.bitdubai.fermat_api.layer._2_os.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer._2_os.database_system.PlatformDatabaseSystem;
import com.bitdubai.fermat_api.layer._2_os.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer._2_os.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_api.layer._2_os.file_system.DealsWithPlatformFileSystem;
import com.bitdubai.fermat_api.layer._2_os.file_system.PlatformFileSystem;
import com.bitdubai.fermat_api.layer._5_user.extra_user.ExtraUserManager;
import com.bitdubai.fermat_api.layer._5_user.User;
import com.bitdubai.fermat_core.layer._5_user.extra_user.developer.bitdubai.version_1.structure.ExtraUser;
import com.bitdubai.fermat_core.layer._5_user.extra_user.developer.bitdubai.version_1.structure.ExtraUserRegistry;

import java.util.UUID;

/**
 * Created by loui on 22/02/15.
 */

/**
 * This plug-in manages a registry of known extra users..
 */

public class ExtraUserUserAddonRoot implements Addon, DealsWithErrors, DealsWithEvents, DealsWithPlatformDatabaseSystem, DealsWithPlatformFileSystem, ExtraUserManager, Service  {

    /**
     * Addon Interface member variables.
     */
     private ExtraUserRegistry extraUserRegistry;

    /**
     * DealWithEvents Interface member variables.
     */
    EventManager eventManager;


    /**
    * DealsWithPlatformDatabaseSystem Interface member variables.
    */
    /**
     * DealsWithPlatformFileSystem Interface member variables.
     */
    PlatformFileSystem platformFileSystem;

    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;





    /**
     *DealsWithErrors Interface implementation.
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
    PlatformDatabaseSystem platformDatabaseSystem;

    /**
     * DealsWithPluginDatabaseSystem interface implementation.
     */
    @Override
    public void setPlatformDatabaseSystem(PlatformDatabaseSystem platformDatabaseSystem) {
        this.platformDatabaseSystem = platformDatabaseSystem;
    }

    /**
     * DealsWithPlatformFileSystem implementation.
     */

    @Override
    public void setPlatformFileSystem(PlatformFileSystem platformFileSystem) {
        this.platformFileSystem  = platformFileSystem;
    }

    /**
     * DeviceUserManager Interface implementation.
     */

    @Override
    public User getUser(UUID id){
        return null;
    }

    /**
     * Service Interface implementation.
     */
    @Override
    public void start() {

        this.serviceStatus = ServiceStatus.STARTED;

        /**
         * I created instance of ExtraUserRegistry
         */
        this.extraUserRegistry = new ExtraUserRegistry();

        this.extraUserRegistry.setPlatformDatabaseSystem(this.platformDatabaseSystem);

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

    @Override
    public ServiceStatus getStatus() {
        return serviceStatus;
    }



}
