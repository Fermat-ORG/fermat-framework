package com.bitdubai.fermat_ccp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_ccp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.exceptions.CantInitializeExtraUserActorDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>ExtraUserActorDeveloperDatabaseFactory</code> have
 * contains the methods that the Developer Database Tools uses to show the information.
 * <p/>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 03/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class ExtraUserActorDeveloperDatabaseFactory {//implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPluginIdentity Interface member variables.
     */
    UUID pluginId;


    Database database;

    /**
     * Constructor
     *
     * @param pluginDatabaseSystem reference passed by plugin root
     * @param pluginId reference passed by plugin root
     */
    public ExtraUserActorDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeExtraUserActorDatabaseException
     */
    public void initializeDatabase() throws CantInitializeExtraUserActorDatabaseException {
        try {
            database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());
        } catch (CantOpenDatabaseException e) {
            throw new CantInitializeExtraUserActorDatabaseException(CantInitializeExtraUserActorDatabaseException.DEFAULT_MESSAGE, e, "Error opening database.", null);
        } catch (DatabaseNotFoundException e) {
            ExtraUserActorDatabaseFactory extraUserActorDatabaseFactory = new ExtraUserActorDatabaseFactory(pluginDatabaseSystem);
            try {
                database = extraUserActorDatabaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException z) {
                throw new CantInitializeExtraUserActorDatabaseException(CantInitializeExtraUserActorDatabaseException.DEFAULT_MESSAGE, z, "Error creating database.", null);
            } catch (Exception z) {
                throw new CantInitializeExtraUserActorDatabaseException(CantInitializeExtraUserActorDatabaseException.DEFAULT_MESSAGE, z, "Error not handled.", null);
            }
        } catch (Exception e) {
            throw new CantInitializeExtraUserActorDatabaseException(CantInitializeExtraUserActorDatabaseException.DEFAULT_MESSAGE, e, "Error not handled.", null);
        }
    }

    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabase> databases = new ArrayList<>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase("Extra User", this.pluginId.toString()));
        return databases;
    }


    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<>();

        List<String> extraUserColumns = new ArrayList<>();

        extraUserColumns.add(ExtraUserActorDatabaseConstants.EXTRA_USER_EXTRA_USER_PUBLIC_KEY_COLUMN_NAME);
        extraUserColumns.add(ExtraUserActorDatabaseConstants.EXTRA_USER_NAME_COLUMN_NAME);
        extraUserColumns.add(ExtraUserActorDatabaseConstants.EXTRA_USER_TIME_STAMP_COLUMN_NAME);

        DeveloperDatabaseTable extraUserTable = developerObjectFactory.getNewDeveloperDatabaseTable(ExtraUserActorDatabaseConstants.EXTRA_USER_TABLE_NAME, extraUserColumns);
        tables.add(extraUserTable);

        return tables;
    }


    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabaseTable developerDatabaseTable) {

        List<DeveloperDatabaseTableRecord> returnedRecords = new ArrayList<>();

        DatabaseTable selectedTable = database.getTable(developerDatabaseTable.getName());
        try {
            selectedTable.loadToMemory();
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            return returnedRecords;
        }

        List<DatabaseTableRecord> records = selectedTable.getRecords();
        List<String> developerRow = new ArrayList<>();
        for (DatabaseTableRecord row : records) {
             for (DatabaseRecord field : row.getValues()) {
                  developerRow.add(field.getValue());
            }
            returnedRecords.add(developerObjectFactory.getNewDeveloperDatabaseTableRecord(developerRow));
        }
        return returnedRecords;
    }

//    @Override
//    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
//        this.pluginDatabaseSystem = pluginDatabaseSystem;
//    }
//
//    @Override
//    public void setPluginId(UUID pluginId) {
//        this.pluginId = pluginId;
//    }
}