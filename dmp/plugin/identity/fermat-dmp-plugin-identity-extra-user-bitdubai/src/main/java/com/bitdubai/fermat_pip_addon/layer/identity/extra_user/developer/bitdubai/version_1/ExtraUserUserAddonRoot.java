package com.bitdubai.fermat_pip_addon.layer.user.extra_user.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Addon;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer._1_definition.enums.Addons;
import com.bitdubai.fermat_api.layer._1_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPlatformDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PlatformDatabaseSystem;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.UnexpectedAddonsExceptionSeverity;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_api.layer._2_os.file_system.DealsWithPlatformFileSystem;
import com.bitdubai.fermat_api.layer._2_os.file_system.PlatformFileSystem;
import com.bitdubai.fermat_api.layer._5_user.extra_user.ExtraUserManager;
import com.bitdubai.fermat_api.layer._5_user.User;
import com.bitdubai.fermat_api.layer._5_user.extra_user.exceptions.CantCreateExtraUserRegistry;
import com.bitdubai.fermat_api.layer._5_user.extra_user.exceptions.CantGetExtraUserRegistry;
import com.bitdubai.fermat_api.layer._5_user.extra_user.exceptions.CantInitializeExtraUserRegistryException;
import com.bitdubai.fermat_pip_addon.layer.user.extra_user.developer.bitdubai.version_1.structure.ExtraUserRegistry;

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



    /**
     *DealsWithErrors Interface implementation.
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
     * DealsWithPlatformFileSystem implementation.
     */

    @Override
    public void setPlatformFileSystem(PlatformFileSystem platformFileSystem) {
        this.platformFileSystem  = platformFileSystem;
    }

    /**
     * DeviceUserManager Interface implementation.
     */

    /*
     *   <p>Return a specific user, looking for registered user id.
     *
     *   @return Object user.
     *   @param UUID user id.
     * */
    @Override
    public User getUser(UUID id){
        User user = null;
        try
        {
            user = this.extraUserRegistry.getUser(id);
        }
        catch (CantGetExtraUserRegistry cantGetExtraUserRegistry){
            errorManager.reportUnexpectedAddonsException(Addons.EXTRA_USER, UnexpectedAddonsExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_ADDONS, cantGetExtraUserRegistry);

        }

        return user;

    }

    /**
     * <p>Create a new Extra User, insert new table record.
     *
     * @param userName
     * @return Object user
     */

    @Override
    public User createUser(String userName){
        User user = null;
        try
        {
            user = this.extraUserRegistry.createUser(userName);
        }
        catch (CantCreateExtraUserRegistry cantCreateExtraUserRegistry){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_EXTRA_USER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantCreateExtraUserRegistry);

        }

        return user;

    }

    /**
     * Service Interface implementation.
     */
    @Override
    public void start() throws CantStartPluginException {

        this.serviceStatus = ServiceStatus.STARTED;

        /**
         * I created instance of ExtraUserRegistry
         */
        this.extraUserRegistry = new ExtraUserRegistry();

        this.extraUserRegistry.setPlatformDatabaseSystem(this.platformDatabaseSystem);

        try {
            this.extraUserRegistry.initialize();

        } catch (CantInitializeExtraUserRegistryException cantInitializeExtraUserRegistryException) {
            errorManager.reportUnexpectedAddonsException(Addons.EXTRA_USER, UnexpectedAddonsExceptionSeverity.DISABLES_THIS_ADDONS, cantInitializeExtraUserRegistryException);
            throw new CantStartPluginException(Plugins.BITDUBAI_USER_EXTRA_USER);
        }

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
