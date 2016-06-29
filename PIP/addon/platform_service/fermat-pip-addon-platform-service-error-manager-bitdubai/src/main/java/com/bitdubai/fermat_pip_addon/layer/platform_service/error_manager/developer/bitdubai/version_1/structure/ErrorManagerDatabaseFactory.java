package com.bitdubai.fermat_pip_addon.layer.platform_service.error_manager.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPlatformDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PlatformDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;

/**
 * Created by Federico Rodriguez on 26/04/15.
 * Modified by Federico Rodriguez on 01.05.15
 */
public class ErrorManagerDatabaseFactory implements DealsWithPlatformDatabaseSystem {

    /**
     * DealsWithPlatformDatabaseSystem Interface member variables.
     */
    private PlatformDatabaseSystem platformDatabaseSystem;

    @Override
    public void setPlatformDatabaseSystem(PlatformDatabaseSystem platformDatabaseSystem) {
        this.platformDatabaseSystem = platformDatabaseSystem;
    }

    public PlatformDatabaseSystem getPlatformDatabaseSystem() {
        return this.platformDatabaseSystem;
    }

    public Database createErrorManagerDatabase() throws CantCreateDatabaseException {

        /*
        *  The ErrorManagerRegistry related database
        */
        Database database = this.platformDatabaseSystem.createDatabase(ErrorManagerDatabaseConstants.EXCEPTION_DATABASE_NAME);
        /*
        *  The ErrorManagerRegistry related table
        */
        //DatabaseTableFactory table = null;
        DatabaseTableFactory table;
        DatabaseFactory databaseFactory = database.getDatabaseFactory();

        table = databaseFactory.newTableFactory(ErrorManagerDatabaseConstants.EXCEPTION_TABLE_NAME);
//        table = ((DatabaseFactory) database).newTableFactory(ErrorManagerDatabaseConstants.EXCEPTION_TABLE_NAME);
        /*
        * Registry fields
        */
        table.addColumn(ErrorManagerDatabaseConstants.EXCEPTION_TABLE_ID_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, true);
        table.addColumn(ErrorManagerDatabaseConstants.EXCEPTION_TABLE_COMPONENT_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 35, false);
        table.addColumn(ErrorManagerDatabaseConstants.EXCEPTION_TABLE_COMPONENT_NAME_COLUMN_NAME, DatabaseDataType.STRING, 35, false);
        table.addColumn(ErrorManagerDatabaseConstants.EXCEPTION_TABLE_SEVERITY_COLUMN_NAME, DatabaseDataType.STRING, 35, false);
        table.addColumn(ErrorManagerDatabaseConstants.EXCEPTION_TABLE_MESSAGE_COLUMN_NAME, DatabaseDataType.STRING, 100, false);
        table.addColumn(ErrorManagerDatabaseConstants.EXCEPTION_TABLE_SENT_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, false);
        table.addColumn(ErrorManagerDatabaseConstants.EXCEPTION_TABLE_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, false);

        try {
            databaseFactory.createTable(table);
//            ((DatabaseFactory) database).createTable(table);
        } catch (CantCreateTableException cantCreateTableException) {
            System.err.println("CantCreateTableException: " + cantCreateTableException.getMessage());
            cantCreateTableException.printStackTrace();
        }

        return database;
    }


}
