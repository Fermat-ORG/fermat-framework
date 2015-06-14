package com.bitdubai.fermat_pip_addon.layer._5_user.extra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer._1_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPlatformDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PlatformDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.UnexpectedPluginExceptionSeverity;


/**
 * Created by Natalia on 31/03/2015.
 */
class ExtraUserDatabaseFactory implements DealsWithErrors,DealsWithPlatformDatabaseSystem {

    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;

    /**
     * DealsWithPlatformDatabaseSystem Interface member variables.
     */
    private PlatformDatabaseSystem platformDatabaseSystem;

    /**
     *DealsWithErrors Interface implementation.
     */

    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }


    /**
     * DealsWithPluginDatabaseSystem Interface implementation.
     */
    @Override
    public void setPlatformDatabaseSystem(PlatformDatabaseSystem platformDatabaseSystem) {
        this.platformDatabaseSystem = platformDatabaseSystem;
    }

    /**
     * <p>Method that create a new database and her tables.
     *
     * @return Object database.
     * @throws CantCreateDatabaseException
     */
    public Database createDatabase() throws CantCreateDatabaseException {

        Database database;

        /**
         * I will create the database where I am going to store the information of this User.
         */
        try {

            database = this.platformDatabaseSystem.createDatabase("ExtraUser");

        }
        catch (CantCreateDatabaseException cantCreateDatabaseException){

            /**
             * I can not handle this situation.
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
            throw new CantCreateDatabaseException();
        }


        /**
         * Next, I will add the needed tables.
         */


            DatabaseTableFactory table;

            /**
             * First the Extra User table.
             */
            table = ((DatabaseFactory) database).newTableFactory( ExtraUserDatabaseConstants.EXTRA_USER_TABLE_NAME);
            table.addColumn(ExtraUserDatabaseConstants.EXTRA_USER_TABLE_ID_COLUMN_NAME, DatabaseDataType.STRING, 36,true);
            table.addColumn(ExtraUserDatabaseConstants.EXTRA_USER_TABLE_NAME_COLUMN_NAME, DatabaseDataType.STRING, 100,false);
            table.addColumn(ExtraUserDatabaseConstants.EXTRA_USER_TABLE_TIME_STAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0,false);
            table.addIndex(ExtraUserDatabaseConstants.EXTRA_USER_FIRST_KEY_COLUMN);

            try {
                ((DatabaseFactory) database).createTable(table);
            }
            catch (CantCreateTableException cantCreateTableException) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateTableException);
                throw new CantCreateDatabaseException();
            }





        return database;
    }
}
