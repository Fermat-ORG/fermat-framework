package com.bitdubai.fermat_ccp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.dmp_actor.Actor;
import com.bitdubai.fermat_api.layer.dmp_actor.extra_user.exceptions.CantCreateExtraUserException;
import com.bitdubai.fermat_api.layer.dmp_actor.extra_user.exceptions.CantGetExtraUserException;
import com.bitdubai.fermat_api.layer.dmp_actor.extra_user.exceptions.ExtraUserNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_ccp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.exceptions.CantInitializeExtraUserActorDatabaseException;
import com.bitdubai.fermat_ccp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.ExtraUserActorRecord;

import java.util.List;
import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.database.ExtraUserActorDao</code>
 * haves all the methods that interact with the database.
 * Manages the extra user actors by storing them on a Database Table.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 03/09/2015.
 *
 * @version 1.0
 */
public class ExtraUserActorDao implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

    /**
     * DealsWithDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPluginIdentity Interface member variables.
     */
    private UUID pluginId;

    private Database database;

    /**
     * Constructor.
     */
    public ExtraUserActorDao(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginId = pluginId;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    public void initialize() throws CantInitializeExtraUserActorDatabaseException {
        try {
            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, this.pluginId.toString());
        } catch (DatabaseNotFoundException e) {
            try {
                ExtraUserActorDatabaseFactory databaseFactory = new ExtraUserActorDatabaseFactory(pluginDatabaseSystem);
                database = databaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException f) {
                throw new CantInitializeExtraUserActorDatabaseException(CantInitializeExtraUserActorDatabaseException.DEFAULT_MESSAGE, f, "", "There is a problem and i cannot create the database.");
            } catch (Exception z) {
                throw new CantInitializeExtraUserActorDatabaseException(CantInitializeExtraUserActorDatabaseException.DEFAULT_MESSAGE, z, "", "Generic Exception.");
            }
        } catch (CantOpenDatabaseException e) {
            throw new CantInitializeExtraUserActorDatabaseException(CantInitializeExtraUserActorDatabaseException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (Exception e) {
            throw new CantInitializeExtraUserActorDatabaseException(CantInitializeExtraUserActorDatabaseException.DEFAULT_MESSAGE, e, "", "Generic Exception.");
        }
    }

    public Actor getActorByPublicKey(String actorPublicKey) throws CantGetExtraUserException, ExtraUserNotFoundException {
        if (actorPublicKey == null) {
            throw new CantGetExtraUserException(CantGetExtraUserException.DEFAULT_MESSAGE, null, "", "actorPublicKey, can not be null");
        }

        try {
            DatabaseTable extraUserTable = database.getTable(ExtraUserActorDatabaseConstants.EXTRA_USER_TABLE_NAME);
            extraUserTable.setStringFilter(ExtraUserActorDatabaseConstants.EXTRA_USER_EXTRA_USER_PUBLIC_KEY_COLUMN_NAME, actorPublicKey, DatabaseFilterType.EQUAL);
            extraUserTable.loadToMemory();

            List<DatabaseTableRecord> records = extraUserTable.getRecords();

            if (!records.isEmpty()) {
                DatabaseTableRecord record = records.get(0);
                String name = record.getStringValue(ExtraUserActorDatabaseConstants.EXTRA_USER_NAME_COLUMN_NAME);
                return new ExtraUserActorRecord(actorPublicKey,"", name);
            } else
                throw  new ExtraUserNotFoundException(ExtraUserNotFoundException.DEFAULT_MESSAGE, null, "", "There's no record with that actorPublicKey.");

        }
        catch (CantLoadTableToMemoryException e) {
            throw new CantGetExtraUserException(CantGetExtraUserException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        }
        catch (Exception e) {
            throw new CantGetExtraUserException(CantGetExtraUserException.DEFAULT_MESSAGE, e, "", "General Exception");
        }
    }

    public void createActor(String actorName,
                            String actorPublicKey,
                            long timeStamp) throws CantCreateExtraUserException {

        if (actorName == null) {
            throw new CantCreateExtraUserException(CantCreateExtraUserException.DEFAULT_MESSAGE, null, "", "actorName, can not be null");
        }

        try {
            DatabaseTable extraUserTable = database.getTable(ExtraUserActorDatabaseConstants.EXTRA_USER_TABLE_NAME);

            DatabaseTableRecord entityRecord = extraUserTable.getEmptyRecord();

            entityRecord.setStringValue(ExtraUserActorDatabaseConstants.EXTRA_USER_NAME_COLUMN_NAME,                  actorName);
            entityRecord.setStringValue(ExtraUserActorDatabaseConstants.EXTRA_USER_EXTRA_USER_PUBLIC_KEY_COLUMN_NAME, actorPublicKey);
            entityRecord.setLongValue(  ExtraUserActorDatabaseConstants.EXTRA_USER_TIME_STAMP_COLUMN_NAME,            timeStamp);

            extraUserTable.insertRecord(entityRecord);

        } catch (CantInsertRecordException exception) {
            throw new CantCreateExtraUserException(CantCreateExtraUserException.DEFAULT_MESSAGE, exception, "", "There is a problem and i cannot insert the record in the table.");
        }
    }

    /**
     * DealsWithPluginDatabaseSystem interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * DealsWithPluginIdentity interface implementation.
     */
    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }
}
