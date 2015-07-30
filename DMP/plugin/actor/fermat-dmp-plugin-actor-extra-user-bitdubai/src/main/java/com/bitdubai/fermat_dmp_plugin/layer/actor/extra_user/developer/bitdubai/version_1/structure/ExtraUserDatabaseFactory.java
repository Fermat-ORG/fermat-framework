package com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import java.util.UUID;


/**
 * Created by Natalia on 31/03/2015.
 */
public class ExtraUserDatabaseFactory implements DealsWithErrors, DealsWithPluginDatabaseSystem {

    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;

    /**
     * DealsWithPlatformDatabaseSystem Interface member variables.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithErrors Interface implementation.
     */

    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }


    /**
     * DealsWithPluginDatabaseSystem Interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * <p>Method that create a new database and her tables.
     *
     * @return Object database.
     * @throws CantCreateDatabaseException
     */
    public Database createDatabase(UUID pluginId) throws CantCreateDatabaseException {
        //TODO Manuel este meotod tambien tiene que gestionar las excepciones genericas
        Database database;

        /**
         * I will create the database where I am going to store the information of this User.
         */
        try {

            database = this.pluginDatabaseSystem.createDatabase(pluginId, "ExtraUser");
            /**
             * Modified by Manuel Perez on 27/07/2015
             * */
            /**
             * Next, I will add the needed tables.
             */

            DatabaseTableFactory table;

            /**
             * First the Extra User table.
             */
            table = ((DatabaseFactory) database).newTableFactory(ExtraUserDatabaseConstants.EXTRA_USER_TABLE_NAME);
            table.addColumn(ExtraUserDatabaseConstants.EXTRA_USER_TABLE_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, true);
            table.addColumn(ExtraUserDatabaseConstants.EXTRA_USER_TABLE_NAME_COLUMN_NAME, DatabaseDataType.STRING, 100, false);
            table.addColumn(ExtraUserDatabaseConstants.EXTRA_USER_TABLE_TIME_STAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, false);
            table.addIndex(ExtraUserDatabaseConstants.EXTRA_USER_FIRST_KEY_COLUMN);

            ((DatabaseFactory) database).createTable(table);

        } catch (CantCreateDatabaseException cantCreateDatabaseException) {

            /**
             * I can not handle this situation.
             *s
             */
        /*Francisco Arce
        Exception in the context Fermat Context
        *
        * */
            String message = CantCreateDatabaseException.DEFAULT_MESSAGE;
            FermatException cause = cantCreateDatabaseException.getCause();
            String context = "Extra User DataBase Factory: " + cantCreateDatabaseException.getContext();
            String possibleReason = "The exception is thrown the Create Database ExtraUser 'this.platformDatabaseSystem.createDatabase(\"ExtraUser\")'" + cantCreateDatabaseException.getPossibleReason();

          /*
            modified by Francisco Arce
            */
            throw new CantCreateDatabaseException(message, cause, context, possibleReason);
        } catch (CantCreateTableException cantCreateTableException) {
                 /*Francisco Arce
        Exception in the context Fermat Context
        *
        * */
            String message = CantCreateTableException.DEFAULT_MESSAGE;
            FermatException cause = cantCreateTableException.getCause();
            String context = "Create Table Extra User" + cantCreateTableException.getContext();
            String possibleReason = "The exception is generated when creating the table Extra User  ((DatabaseFactory) database).createTable(table) " + cantCreateTableException.getPossibleReason();
 /*
           modified by Francisco Arce
            */
            throw new CantCreateDatabaseException(message, cause, context, possibleReason);
        } catch(Exception exception){

            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);

        }

        return database;
    }
}
