package com.bitdubai.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;

import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.enums.ContactState;
import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.exceptions.CantAcceptIntraUserException;
import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.exceptions.CantCancelIntraUserException;
import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.exceptions.CantCreateIntraUserException;
import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.exceptions.CantDecideAcceptanceLaterException;
import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.exceptions.CantDenyConnectionException;
import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.exceptions.CantDisconnectIntraUserException;
import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.exceptions.CantGetIntraUSersException;
import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.interfaces.ActorIntraUser;
import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.interfaces.ActorIntraUserManager;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;

import com.bitdubai.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.database.IntraUserActorDao;
import com.bitdubai.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.database.IntraUserActorDatabaseConstants;
import com.bitdubai.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.database.IntraUserActorDeveloperDatabaseFactory;
import com.bitdubai.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.exceptions.CantAddPendingIntraUserException;
import com.bitdubai.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.exceptions.CantDeliverDatabaseException;
import com.bitdubai.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.exceptions.CantGetIntraUsersListException;
import com.bitdubai.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.exceptions.CantInitializeIntraUserActorDatabaseException;
import com.bitdubai.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.exceptions.CantUpdateIntraUserConnectionException;
import com.bitdubai.fermat_pip_api.layer.pip_actor.exception.CantGetLogTool;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedAddonsExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;


/**
 * This plugin manages connections between users of the platform..
 * Provides contact information of Intra User
 *
 * Created by loui on 22/02/15.
 * modified by Natalia on 11/08/2015
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class IntraUserActorPluginRoot implements ActorIntraUserManager,DatabaseManagerForDevelopers,DealsWithErrors, DealsWithLogger, LogManagerForDevelopers, DealsWithPluginDatabaseSystem, DealsWithPluginFileSystem, Plugin, Service  {

    private IntraUserActorDao intraUserActorDao;

    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;

    /**
     * DealsWithLogger interface member variable
     */
    LogManager logManager;
    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();


    /**
     * DealsWithPlatformDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;


    /**
     * FileSystem Interface member variables.
     */
    PluginFileSystem pluginFileSystem;


    /**
     * Plugin Interface member variables.
     */
    UUID pluginId;

    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;


    /**
     * ActorIntraUserManager interface implementation.
     */


    /**
     * That method registers a new intra user in the list
     * managed by this plugin with ContactState PENDING_HIS_ACCEPTANCE until the other intra user
     * accepts the connection request sent also by this method.
     *
     * @param intraUserLoggedInPublicKey The public key of the intra user sending the connection request.
     * @param intraUserToAddName         The name of the intra user to add
     * @param intraUserToAddPublicKey    The public key of the intra user to add
     * @param profileImage               The profile image that the intra user has
     * @throws CantCreateIntraUserException
     */

    @Override
    public void askIntraUserForAcceptance(String intraUserLoggedInPublicKey, String intraUserToAddName, String intraUserToAddPublicKey, byte[] profileImage) throws CantCreateIntraUserException {
        try
        {
            this.intraUserActorDao.createNewIntraUser(intraUserLoggedInPublicKey, intraUserToAddName, intraUserToAddPublicKey,profileImage);
        }
        catch(CantAddPendingIntraUserException e)
        {
            throw new CantCreateIntraUserException("CAN'T ADD NEW INTRA USER CONNECTION",e,"","");
        }
        catch(Exception e)
        {
            throw new CantCreateIntraUserException("CAN'T ADD NEW INTRA USER CONNECTION",FermatException.wrapException(e),"","");
        }

    }


    /**
     * That method takes the information of a connection request, accepts
     * the request and adds the intra user to the list managed by this plugin with ContactState CONTACT.
     *
     * @param intraUserLoggedInPublicKey The public key of the intra user sending the connection request.
     * @param intraUserToAddPublicKey    The public key of the intra user to add
     * @throws CantAcceptIntraUserException
     */

    @Override
    public void acceptIntraUser(String intraUserLoggedInPublicKey, String intraUserToAddPublicKey) throws CantAcceptIntraUserException {
        try
        {
            this.intraUserActorDao.updateIntraUserConnectionState(intraUserLoggedInPublicKey, intraUserToAddPublicKey, ContactState.CONNECTED);
        }
        catch(CantUpdateIntraUserConnectionException e)
        {
            throw new CantAcceptIntraUserException("CAN'T ACCEPT INTRA USER CONNECTION",e,"","");
        }
        catch(Exception e)
        {
            throw new CantAcceptIntraUserException("CAN'T ACCEPT INTRA USER CONNECTION",FermatException.wrapException(e),"","");
        }
    }



    /**
     * That method rejects a connection request from another intra user
     *
     * @param intraUserLoggedInPublicKey The public key of the intra user identity that is the receptor of the request
     * @param intraUserToRejectPublicKey The public key of the intra user that sent the request
     * @throws CantDenyConnectionException
     */
    @Override
    public void denyConnection(String intraUserLoggedInPublicKey, String intraUserToRejectPublicKey) throws CantDenyConnectionException {

        try
        {
            this.intraUserActorDao.updateIntraUserConnectionState(intraUserLoggedInPublicKey, intraUserToRejectPublicKey, ContactState.LOCALLY_DENIED);
        }
        catch(CantUpdateIntraUserConnectionException e)
        {
            throw new CantDenyConnectionException("CAN'T DENY INTRA USER CONNECTION",e,"","");
        }
        catch(Exception e)
        {
            throw new CantDenyConnectionException("CAN'T DENY INTRA USER CONNECTION",FermatException.wrapException(e),"","");
        }
    }

    /**
     * That method disconnect an intra user from the connections registry
     *
     * @param intraUserLoggedInPublicKey The public key of the intra user identity that is the receptor of the request
     * @param intraUserToDisconnectPublicKey The public key of the intra user to disconnect as connection
     * @throws CantDisconnectIntraUserException
     */
    @Override
    public void disconnectIntraUser(String intraUserLoggedInPublicKey, String intraUserToDisconnectPublicKey) throws CantDisconnectIntraUserException {
        try
        {
            this.intraUserActorDao.updateIntraUserConnectionState(intraUserLoggedInPublicKey, intraUserToDisconnectPublicKey, ContactState.LOCALLY_DISCONNECTED);
        }
        catch(CantUpdateIntraUserConnectionException e)
        {
            throw new CantDisconnectIntraUserException("CAN'T CANCEL INTRA USER CONNECTION",e,"","");
        }
        catch(Exception e)
        {
            throw new CantDisconnectIntraUserException("CAN'T CANCEL INTRA USER CONNECTION",FermatException.wrapException(e),"","");
        }
    }


    /**
     * That method cancels an intra user from the connections registry
     *
     * @param intraUserLoggedInPublicKey The public key of the intra user identity that is the receptor of the request
     * @param intraUserToCancelPublicKey The public key of the intra user to cancel as connection
     * @throws CantCancelIntraUserException
     */
    @Override
    public void cancelIntraUser(String intraUserLoggedInPublicKey, String intraUserToCancelPublicKey) throws CantCancelIntraUserException {
        try
        {
            this.intraUserActorDao.updateIntraUserConnectionState(intraUserLoggedInPublicKey, intraUserToCancelPublicKey, ContactState.CANCELLED);
        }
        catch(CantUpdateIntraUserConnectionException e)
        {
            throw new CantCancelIntraUserException("CAN'T CANCEL INTRA USER CONNECTION",e,"","");
        }
        catch(Exception e)
        {
            throw new CantCancelIntraUserException("CAN'T CANCEL INTRA USER CONNECTION",FermatException.wrapException(e),"","");
        }
    }

    /**
     * That method get the list of all intra users that are connections of the logged in one.
     *
     * @param intraUserLoggedInPublicKey the public key of the intra user logged in
     * @return the list of intra users the logged in intra user has as connections.
     * @throws CantGetIntraUSersException
     */
    @Override
    public List<ActorIntraUser> getAllIntraUsers(String intraUserLoggedInPublicKey) throws CantGetIntraUSersException {
        try
        {
            return this.intraUserActorDao.getAllIntraUsers(intraUserLoggedInPublicKey);
        }
        catch(CantGetIntraUsersListException e)
        {
            throw new CantGetIntraUSersException("CAN'T LIST INTRA USER CONNECTIONS",e,"","");
        }
        catch(Exception e)
        {
            throw new CantGetIntraUSersException("CAN'T LIST INTRA USER CONNECTIONS",FermatException.wrapException(e),"","");
        }
    }


    /**
     * That method get the list of all intra users
     * that sent a connection request and are waiting for the acceptance of the logged in one.
     *
     * @param intraUserLoggedInPublicKey the public key of the intra user logged in
     * @return the list of intra users the logged in intra user has as connections.
     * @throws CantGetIntraUSersException
     */

    @Override
    public List<ActorIntraUser> getWaitingYourAcceptanceIntraUsers(String intraUserLoggedInPublicKey) throws CantGetIntraUSersException {
        try
        {
            return this.intraUserActorDao.getIntraUsers(intraUserLoggedInPublicKey, ContactState.PENDING_HIS_ACCEPTANCE);
        }
        catch(CantGetIntraUsersListException e)
        {
            throw new CantGetIntraUSersException("CAN'T LIST INTRA USER ACCEPTED CONNECTIONS",e,"","");
        }
        catch(Exception e)
        {
            throw new CantGetIntraUSersException("CAN'T LIST INTRA USER ACCEPTED CONNECTIONS",FermatException.wrapException(e),"","");
        }
    }


    /**
     * That method get  the list of all intra users
     * that the logged in one has sent connections request to and have not been answered yet..
     *
     * @param intraUserLoggedInPublicKey the public key of the intra user logged in
     * @return the list of intra users the logged in intra user has as connections.
     * @throws CantGetIntraUSersException
     */

    @Override
    public List<ActorIntraUser> getWaitingTheirAcceptanceIntraUsers(String intraUserLoggedInPublicKey) throws CantGetIntraUSersException {
        try
        {
            return this.intraUserActorDao.getIntraUsers(intraUserLoggedInPublicKey, ContactState.PENDING_HIS_ACCEPTANCE);
        }
        catch(CantGetIntraUsersListException e)
        {
            throw new CantGetIntraUSersException("CAN'T LIST INTRA USER PENDING_HIS_ACCEPTANCE CONNECTIONS",e,"","");
        }
        catch(Exception e)
        {
            throw new CantGetIntraUSersException("CAN'T LIST INTRA USER PENDING_HIS_ACCEPTANCE CONNECTIONS",FermatException.wrapException(e),"","");
        }
    }


    /**
     *DealsWithErrors Interface implementation.
     */

    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * DealsWithPluginDatabaseSystem interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;

    }

    /**
     * DealWithPluginFileSystem Interface implementation.
     */
    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem  = pluginFileSystem;

    }

    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.IntraUserActorPluginRoot");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.structure.ActorIntraUser");

        /**
         * I return the values.
         */
        return returnedClasses;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {
        /**
         * Modify by Manuel on 25/07/2015
         * I will wrap all this method within a try, I need to catch any generic java Exception
         */
        try{

            /**
             * I will check the current values and update the LogLevel in those which is different
             */
            for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {
                /**
                 * if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
                 */
                if (IntraUserActorPluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                    IntraUserActorPluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                    IntraUserActorPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                } else {
                    IntraUserActorPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                }
            }

        }catch (Exception exception){
            FermatException e = new CantGetLogTool(CantGetLogTool.DEFAULT_MESSAGE, FermatException.wrapException(exception), "setLoggingLevelPerClass: "+ IntraUserActorPluginRoot.newLoggingLevel ,"Check the cause");
            this.errorManager.reportUnexpectedAddonsException(Addons.EXTRA_USER, UnexpectedAddonsExceptionSeverity.DISABLES_THIS_ADDONS, e);
        }

    }

    /**
     * Static method to get the logging level from any class under root.
     * @param className
     * @return
     */
    public static LogLevel getLogLevelByClass(String className){
        try{
            /**
             * sometimes the classname may be passed dinamically with an $moretext
             * I need to ignore whats after this.
             */
            String[] correctedClass = className.split((Pattern.quote("$")));
            return IntraUserActorPluginRoot.newLoggingLevel.get(correctedClass[0]);

        } catch (Exception exception){
            /**
             * If I couldn't get the correct loggin level, then I will set it to minimal.
             */
            return DEFAULT_LOG_LEVEL;
        }
    }



    /**
     * Service Interface implementation.
     */
    @Override
    public void start() throws CantStartPluginException {
        try {
            /**
             * I created instance of IntraUserActorDao
             */
            this.intraUserActorDao = new IntraUserActorDao(pluginDatabaseSystem,this.pluginFileSystem, this.pluginId);

            this.intraUserActorDao.initializeDatabase();

        } catch (CantInitializeIntraUserActorDatabaseException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INTRA_USER_ACTOR, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(e, Plugins.BITDUBAI_INTRA_USER_ACTOR);
        }

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

    @Override
    public ServiceStatus getStatus() {
        return serviceStatus;
    }

    /**
     * PlugIn Interface implementation.
     */
    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }



    /**
     * DatabaseManagerForDevelopers Interface implementation.
     */
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        IntraUserActorDeveloperDatabaseFactory dbFactory = new IntraUserActorDeveloperDatabaseFactory(this.pluginDatabaseSystem,this.pluginId);
        return dbFactory.getDatabaseList(developerObjectFactory);


    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        IntraUserActorDeveloperDatabaseFactory dbFactory = new IntraUserActorDeveloperDatabaseFactory(this.pluginDatabaseSystem,this.pluginId);
        return dbFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        Database database;
        try {
            IntraUserActorDeveloperDatabaseFactory dbFactory = new IntraUserActorDeveloperDatabaseFactory(this.pluginDatabaseSystem,this.pluginId);

            database = this.pluginDatabaseSystem.openDatabase(pluginId, IntraUserActorDatabaseConstants.INTRA_USER_DATABASE_NAME);
            return dbFactory.getDatabaseTableContent(developerObjectFactory,  developerDatabaseTable);
        }catch (CantOpenDatabaseException cantOpenDatabaseException){
            /**
             * The database exists but cannot be open. I can not handle this situation.
             */
            FermatException e = new CantDeliverDatabaseException("I can't open database",cantOpenDatabaseException,"WalletId: " + developerDatabase.getName(),"");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INTRA_USER_ACTOR, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
        }
        catch (DatabaseNotFoundException databaseNotFoundException) {
            FermatException e = new CantDeliverDatabaseException("Database does not exists",databaseNotFoundException,"WalletId: " + developerDatabase.getName(),"");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INTRA_USER_ACTOR,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
        }
        // If we are here the database could not be opened, so we return an empry list
        return new ArrayList<>();
    }
}
