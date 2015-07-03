package com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Addon;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPlatformDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PlatformDatabaseSystem;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.UnexpectedAddonsExceptionSeverity;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPlatformFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PlatformFileSystem;
import com.bitdubai.fermat_api.layer.pip_user.extra_user.ExtraUserManager;
import com.bitdubai.fermat_api.layer.pip_user.User;
import com.bitdubai.fermat_api.layer.pip_user.extra_user.exceptions.CantCreateExtraUserRegistry;
import com.bitdubai.fermat_api.layer.pip_user.extra_user.exceptions.CantGetExtraUserRegistry;
import com.bitdubai.fermat_api.layer.pip_user.extra_user.exceptions.CantInitializeExtraUserRegistryException;
import com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.ExtraUserDeveloperDatabaseFactory;
import com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.ExtraUserRegistry;

import java.util.List;
import java.util.UUID;

/**
 * Created by loui on 22/02/15.
 */

/**
 * This plug-in manages a registry of known extra users..
 */

public class ExtraUserUserAddonRoot implements Addon, DatabaseManagerForDevelopers, DealsWithErrors, DealsWithEvents, DealsWithPlatformDatabaseSystem, DealsWithPlatformFileSystem, ExtraUserManager, Service  {

    /**
     * DatabaseManagerForDevelopers interface implementation
     * Returns the list of databases implemented on this plug in.
     */
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        ExtraUserDeveloperDatabaseFactory dbFactory = new ExtraUserDeveloperDatabaseFactory(errorManager, platformDatabaseSystem);
        List<DeveloperDatabase> developerDatabaseList = null;
        try {
            dbFactory.initializeDatabase();
            developerDatabaseList = dbFactory.getDatabaseList(developerObjectFactory);
        } catch (Exception e) {
            System.out.println("******* Error trying to get database list for plugin Wallet Contacts");
        }
        return developerDatabaseList;
    }

    /**
     * returns the list of tables for the given database
     *
     * @param developerObjectFactory
     * @param developerDatabase
     * @return
     */
    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        ExtraUserDeveloperDatabaseFactory dbFactory = new ExtraUserDeveloperDatabaseFactory(errorManager, platformDatabaseSystem);
        List<DeveloperDatabaseTable> developerDatabaseTableList = null;
        try {
            dbFactory.initializeDatabase();
            developerDatabaseTableList = dbFactory.getDatabaseTableList(developerObjectFactory);
        } catch (Exception e) {
            System.out.println("******* Error trying to get database table list for plugin Wallet Contacts");
        }
        return developerDatabaseTableList;
    }

    /**
     * returns the list of records for the passed table
     *
     * @param developerObjectFactory
     * @param developerDatabase
     * @param developerDatabaseTable
     * @return
     */
    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        ExtraUserDeveloperDatabaseFactory dbFactory = new ExtraUserDeveloperDatabaseFactory(errorManager, platformDatabaseSystem);
        List<DeveloperDatabaseTableRecord> developerDatabaseTableRecordList = null;
        try {
            dbFactory.initializeDatabase();
            developerDatabaseTableRecordList = dbFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (Exception e) {
            System.out.println("******* Error trying to get database table list for plugin Wallet Contacts");
        }
        return developerDatabaseTableRecordList;
    }

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
            throw new CantStartPluginException(cantInitializeExtraUserRegistryException, Plugins.BITDUBAI_USER_EXTRA_USER);
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
