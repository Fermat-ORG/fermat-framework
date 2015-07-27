package com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.FermatException;

import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;

import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.dmp_actor.Actor;
import com.bitdubai.fermat_pip_api.layer.pip_actor.exception.CantGetDataBaseTool;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;

import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedAddonsExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import com.bitdubai.fermat_api.layer.dmp_actor.extra_user.UserRegistry;
import com.bitdubai.fermat_api.layer.dmp_actor.extra_user.exceptions.CantCreateExtraUserRegistry;
import com.bitdubai.fermat_api.layer.dmp_actor.extra_user.exceptions.CantGetExtraUserRegistry;
import com.bitdubai.fermat_api.layer.dmp_actor.extra_user.exceptions.CantInitializeExtraUserRegistryException;

import java.util.UUID;

/**
 * Created by ciencias on 3/18/15.
 * Modified by natalia
 */
public class ExtraUserRegistry implements DealsWithErrors, DealsWithPluginDatabaseSystem, DealsWithPluginIdentity, UserRegistry {

    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;

    /**
     * DealsWithPlatformDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    UUID pluginId;


    /**
     * UserRegistry Interface member variables.
     */


    private Database database;


    /**
     * DealsWithErrors Interface implementation.
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

    public void initialize() throws CantInitializeExtraUserRegistryException {
        //TODO Manuel este metodo tiene que gestionar excepciones genericas
        /**
         * I will try to open the users' database..
         */
        try {

            this.database = this.pluginDatabaseSystem.openDatabase(pluginId, "ExtraUser");
        } catch (DatabaseNotFoundException databaseNotFoundException) {

            ExtraUserDatabaseFactory databaseFactory = new ExtraUserDatabaseFactory();
            databaseFactory.setPluginDatabaseSystem(this.pluginDatabaseSystem);
            databaseFactory.setErrorManager(this.errorManager);

            try {

                this.database = databaseFactory.createDatabase(pluginId);

            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                String message = CantInitializeExtraUserRegistryException.DEFAULT_MESSAGE;
                FermatException cause = cantCreateDatabaseException.getCause();
                String context = "DataBase Factory: " + cantCreateDatabaseException.getContext();
                String possibleReason = "The exception occurred when calling  'databaseFactory.createDatabase()': " + cantCreateDatabaseException.getPossibleReason();

                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_EXTRA_USER, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                throw new CantInitializeExtraUserRegistryException(message, cause, context, possibleReason);
            }
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            String message = CantInitializeExtraUserRegistryException.DEFAULT_MESSAGE;
            FermatException cause = cantOpenDatabaseException.getCause();
            String context = "Create Database:" + cantOpenDatabaseException.getContext();
            String possibleReason = "The exception occurred while trying to open the database of users 'this.database = this.platformDatabaseSystem.openDatabase (\"ExtraUser\")': " + cantOpenDatabaseException.getPossibleReason();

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_EXTRA_USER, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            /*
            Modified by Francisco Arce
            */
            throw new CantInitializeExtraUserRegistryException(message, cause, context, possibleReason);
        }

    }

    /**
     * UserRegistry interface implementation.
     */


    /**
     * <p>Create a new Extra User, insert new table record.
     *
     * @param userName
     * @return Object user
     * @throws CantCreateExtraUserRegistry
     */
    @Override
    public Actor createUser(String userName) throws CantCreateExtraUserRegistry {

        /**
         *  I create new ExtraUser instance
         */

        Actor actor = new ExtraUser();
        //Modified by Manuel Pérez on 26/07/2015
        DatabaseTable extrauserTable=null;
        DatabaseTableRecord extrauserRecord=null;
        try{

            UUID userId = UUID.randomUUID();

            actor.setId(userId);
            actor.setName(userName);
            /**
             * Here I create a new Extra User record .
             */
            long unixTime = System.currentTimeMillis() / 1000L;

            extrauserTable = database.getTable(ExtraUserDatabaseConstants.EXTRA_USER_TABLE_NAME);
            extrauserRecord = extrauserTable.getEmptyRecord();

            extrauserRecord.setUUIDValue(ExtraUserDatabaseConstants.EXTRA_USER_TABLE_ID_COLUMN_NAME, userId);
            extrauserRecord.setStringValue(ExtraUserDatabaseConstants.EXTRA_USER_TABLE_NAME_COLUMN_NAME, userName);
            extrauserRecord.setLongValue(ExtraUserDatabaseConstants.EXTRA_USER_TABLE_TIME_STAMP_COLUMN_NAME, unixTime);
            extrauserTable.insertRecord(extrauserRecord);

        }catch (CantInsertRecordException cantInsertRecord) {
            /**
             * I can not solve this situation.
             */
        /*Francisco Arce
        Exception in the context Fermat Context
        *
        * */
            String message = CantCreateExtraUserRegistry.DEFAULT_MESSAGE;
            FermatException cause = cantInsertRecord.getCause();
            String context = "Extra User Record: " + cantInsertRecord.getContext();
            String possibleReason = "The exception occurred when recording the Extra User extrauserTable.insertRecord(extrauserRecord): " + cantInsertRecord.getPossibleReason();
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_EXTRA_USER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantInsertRecord);
            /*
          modified by Francisco Arce
            */
            throw new CantCreateExtraUserRegistry(message, cause, context, possibleReason);
        }catch(Exception exception){

            FermatException e = new CantGetDataBaseTool(CantGetDataBaseTool.DEFAULT_MESSAGE, FermatException.wrapException(exception), "PluginID: "+pluginId ,"Check the cause");
            this.errorManager.reportUnexpectedAddonsException(Addons.EXTRA_USER, UnexpectedAddonsExceptionSeverity.DISABLES_THIS_ADDONS, e);


        }

        return actor;
    }

    /**
     * <p>Return a specific user, looking for registered user id.
     *
     * @param userId
     * @return Object user
     * @throws CantGetExtraUserRegistry
     */
    @Override
    public Actor getUser(UUID userId) throws CantGetExtraUserRegistry {
         /**
         * Reads the user data table , in this case only the name , creates an instance and returns
         */
        DatabaseTable table;
        //Modified by Manuel Pérez on 26/07/2015
        Actor actor = new ExtraUser();
        try {
        /**
         *  I will load the information of table into a memory structure, filter by user id .
         */
            table = this.database.getTable(ExtraUserDatabaseConstants.EXTRA_USER_TABLE_NAME);
            table.setUUIDFilter(ExtraUserDatabaseConstants.EXTRA_USER_TABLE_ID_COLUMN_NAME, userId, DatabaseFilterType.EQUAL);

            table.loadToMemory();

            /**
             * Will go through the records getting each extra user.
             */

            actor.setId(userId);
            for (DatabaseTableRecord record : table.getRecords()) {
                actor.setName(record.getStringValue(ExtraUserDatabaseConstants.EXTRA_USER_TABLE_NAME_COLUMN_NAME));

            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            /**
             * I can not solve this situation.
             */
            /*Francisco Arce
            Exception in the context Fermat Context
            *
            * */
            String message = CantGetExtraUserRegistry.DEFAULT_MESSAGE;
            FermatException cause = cantLoadTableToMemory.getCause();
            String context = "table Memory: " + cantLoadTableToMemory.getContext();
            String possibleReason = "The exception occurred when calling table.loadToMemory (): " + cantLoadTableToMemory.getPossibleReason();

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_EXTRA_USER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantLoadTableToMemory);
            throw new CantGetExtraUserRegistry(message, cause, context, possibleReason);
        }catch(Exception exception){

            throw new CantGetExtraUserRegistry(CantGetExtraUserRegistry.DEFAULT_MESSAGE,FermatException.wrapException(exception), null, null);

        }




        return actor;
    }

    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }
}
