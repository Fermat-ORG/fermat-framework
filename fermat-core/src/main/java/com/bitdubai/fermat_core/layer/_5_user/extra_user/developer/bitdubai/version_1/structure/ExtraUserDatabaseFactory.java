package com.bitdubai.fermat_core.layer._5_user.extra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer._2_os.database_system.Database;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer._2_os.database_system.DealsWithPlatformDatabaseSystem;
import com.bitdubai.fermat_api.layer._2_os.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer._2_os.database_system.PlatformDatabaseSystem;
import com.bitdubai.fermat_api.layer._2_os.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.InvalidOwnerId;


/**
 * Created by Natalia on 31/03/2015.
 */
 class ExtraUserDatabaseFactory implements DealsWithPlatformDatabaseSystem {

    /**
     * DealsWithPlatformDatabaseSystem Interface member variables.
     */
    private PlatformDatabaseSystem platformDatabaseSystem;

    /**
     * DealsWithPluginDatabaseSystem Interface implementation.
     */
    @Override
    public void setPlatformDatabaseSystem(PlatformDatabaseSystem platformDatabaseSystem) {
        this.platformDatabaseSystem = platformDatabaseSystem;
    }

    Database createDatabase() throws CantCreateDatabaseException {

        Database database;

        /**
         * I will create the database where I am going to store the information of this wallet.
         */
        try {

            database = this.platformDatabaseSystem.createDatabase("ExtraUser");

        }
        catch (CantCreateDatabaseException cantCreateDatabaseException){

            /**
             * I can not handle this situation.
             */
            System.err.println("CantCreateDatabaseException: " + cantCreateDatabaseException.getMessage());
            cantCreateDatabaseException.printStackTrace();
            throw new CantCreateDatabaseException();
        }


        /**
         * Next, I will add the needed tables.
         */
        try {

            DatabaseTableFactory table;

            /**
             * First the Address book table.
             */
            table = ((DatabaseFactory) database).newTableFactory( ExtraUserDatabaseConstants.EXTRA_USER_TABLE_NAME);
            table.addColumn(ExtraUserDatabaseConstants.EXTRA_USER_TABLE_ID_COLUMN_NAME, DatabaseDataType.STRING, 36);
            table.addColumn(ExtraUserDatabaseConstants.EXTRA_USER_TABLE_NAME_COLUMN_NAME, DatabaseDataType.STRING, 100);
            table.addColumn(ExtraUserDatabaseConstants.EXTRA_USER_TABLE_TIME_STAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0);
            table.addIndex(ExtraUserDatabaseConstants.EXTRA_USER_FIRST_KEY_COLUM);

            try {
                ((DatabaseFactory) database).createTable(table);
            }
            catch (CantCreateTableException cantCreateTableException) {
                System.err.println("CantCreateTableException: " + cantCreateTableException.getMessage());
                cantCreateTableException.printStackTrace();
                throw new CantCreateDatabaseException();
            }


        }
        catch (InvalidOwnerId invalidOwnerId) {
            /**
             * This shouldn't happen here because I was the one who gave the owner id to the database file system,
             * but anyway, if this happens, I can not continue.
             * * *
             */
            System.err.println("InvalidOwnerId: " + invalidOwnerId.getMessage());
            invalidOwnerId.printStackTrace();
            throw new CantCreateDatabaseException();
        }

        return database;
    }
}
