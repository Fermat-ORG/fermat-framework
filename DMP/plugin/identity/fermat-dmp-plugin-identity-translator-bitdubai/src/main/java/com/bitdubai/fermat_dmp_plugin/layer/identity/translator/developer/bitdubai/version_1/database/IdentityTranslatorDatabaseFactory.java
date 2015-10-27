package com.bitdubai.fermat_dmp_plugin.layer.identity.translator.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;

import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_pip_plugin.layer.identity_translator.developer.bitdubai.version_1.database.IdentityTranslatorDatabaseFactory</code>
 * <p/>is responsible for creating the tables in the database where it is to keep the information.
 * <p/>
 * Created by Natalia on 31/07/2015
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class IdentityTranslatorDatabaseFactory implements DealsWithErrors, DealsWithPluginDatabaseSystem {

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

        Database database;

        /**
         * I will create the database where I am going to store the information of this User.
         */
        try {

            database = this.pluginDatabaseSystem.createDatabase(pluginId, IdentityTranslatorDatabaseConstants.TRANSLATOR_DB_NAME);

            /**
             * Next, I will add the needed tables.
             */

            DatabaseTableFactory table;

            /**
             * First the Extra User table.
             */
            //DatabaseTableFactory table;
            DatabaseFactory databaseFactory = database.getDatabaseFactory();
            table = databaseFactory.newTableFactory(IdentityTranslatorDatabaseConstants.TRANSLATOR_TABLE_NAME);
//            table = ((DatabaseFactory) database).newTableFactory(IdentityTranslatorDatabaseConstants.TRANSLATOR_TABLE_NAME);
            table.addColumn(IdentityTranslatorDatabaseConstants.TRANSLATOR_DEVELOPER_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 130, true);
            table.addColumn(IdentityTranslatorDatabaseConstants.TRANSLATOR_DEVELOPER_ALIAS_COLUMN_NAME, DatabaseDataType.STRING, 36, false);
            table.addColumn(IdentityTranslatorDatabaseConstants.TRANSLATOR_DEVICE_USER_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 130, false);
            table.addIndex(IdentityTranslatorDatabaseConstants.TRANSLATOR_FIRST_KEY_COLUMN);

            databaseFactory.createTable(table);
//            ((DatabaseFactory) database).createTable(table);

        } catch (CantCreateDatabaseException cantCreateDatabaseException) {

            /**
             * I can not handle this situation.
             *
             */

            String message = CantCreateDatabaseException.DEFAULT_MESSAGE;
            FermatException cause = cantCreateDatabaseException.getCause();
            String context = "Translator Identity DataBase Factory: " + cantCreateDatabaseException.getContext();
            String possibleReason = "The exception is thrown the Create Database ExtraUser 'this.platformDatabaseSystem.createDatabase(\"TranslatorIdentity\")'" + cantCreateDatabaseException.getPossibleReason();


            throw new CantCreateDatabaseException(message, cause, context, possibleReason);
        } catch (CantCreateTableException cantCreateTableException) {

            String message = CantCreateTableException.DEFAULT_MESSAGE;
            FermatException cause = cantCreateTableException.getCause();
            String context = "Create Table Translator Identity" + cantCreateTableException.getContext();
            String possibleReason = "The exception is generated when creating the table Translator Identity  ((DatabaseFactory) database).createTable(table) " + cantCreateTableException.getPossibleReason();

            throw new CantCreateDatabaseException(message, cause, context, possibleReason);
        } catch(Exception exception){

            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);

        }

        return database;
    }
}
