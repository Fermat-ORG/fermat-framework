package com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.dmp_identity.designer.interfaces.DesignerIdentity;
import com.bitdubai.fermat_api.layer.dmp_identity.translator.interfaces.TranslatorIdentity;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.pip_Identity.developer.interfaces.DeveloperIdentity;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantInitializeNetworkIntraUserDataBaseException;

import java.util.UUID;

/**
 * Created by natalia on 02/09/15.
 */
public class IntraUserNetworkServiceDao {

    UUID databaseOwnerId;
    String databaseName;


    /**
     * Represent the Plugin Database.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    private UUID pluginId;

    /**
     * Constructor with parameters
     *
     * @param pluginDatabaseSystem DealsWithPluginDatabaseSystem
     */

    public IntraUserNetworkServiceDao(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /**
     * Represent de Database where i will be working with
     */
    Database database;


    public void saveAskIntraUserForAcceptanceRequest(UUID id, String intraUserLoggedInPublicKey, String intraUserLoggedInName, String intraUserToAddPublicKey, byte[] myProfileImage) throws CantExecuteDatabaseOperationException {
        try
        {
            long millis = new java.util.Date().getTime();

            database = openDatabase();
            DatabaseTable table = this.database.getTable(IntraUserNetworkServiceDatabaseConstants.INTRA_USER_NETWORK_SERVICE_CACHE_TABLE_NAME);
            DatabaseTableRecord record = table.getEmptyRecord();

            record.setUUIDValue(IntraUserNetworkServiceDatabaseConstants.INTRA_USER_NETWORK_SERVICE_CACHE_TABLE_ID_COLUMN_NAME, id);
            record.setStringValue(IntraUserNetworkServiceDatabaseConstants.INTRA_USER_NETWORK_SERVICE_CACHE_TABLE_INTRA_USER_PUBLIC_KEY_COLUMN_NAME, intraUserToAddPublicKey);
            record.setStringValue(IntraUserNetworkServiceDatabaseConstants.INTRA_USER_NETWORK_SERVICE_CACHE_TABLE_USER_NAME_COLUMN_NAME, intraUserLoggedInName);
            record.setStringValue(IntraUserNetworkServiceDatabaseConstants.INTRA_USER_NETWORK_SERVICE_CACHE_TABLE_INTRA_USER_LOGGED_IN_PUBLIC_KEY_COLUMN_NAME,intraUserLoggedInPublicKey);
            record.setLongValue(IntraUserNetworkServiceDatabaseConstants.INTRA_USER_NETWORK_SERVICE_CACHE_TABLE_CREATED_TIME_COLUMN_NAME,millis);

            table.insertRecord(record);
            closeDatabase();
        }  catch (CantInitializeNetworkIntraUserDataBaseException e){
            closeDatabase();
            throw new CantExecuteDatabaseOperationException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE,e,"", "Error save record to ask intra user acceptance");

        }  catch (Exception exception){
            closeDatabase();
            throw new CantExecuteDatabaseOperationException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE,exception, "", "Error save record to ask intra user acceptance");
        }
    }

    private Database openDatabase() throws CantInitializeNetworkIntraUserDataBaseException {
        try {
            if(database == null)
                database = pluginDatabaseSystem.openDatabase(this.databaseOwnerId, IntraUserNetworkServiceDatabaseConstants.DATA_BASE_NAME);
            else
                database.openDatabase();
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            throw new CantInitializeNetworkIntraUserDataBaseException(CantInitializeNetworkIntraUserDataBaseException.DEFAULT_MESSAGE,cantOpenDatabaseException, "Trying to open database " + databaseName, "Error in Database plugin");
        } catch (DatabaseNotFoundException databaseNotFoundException) {
            throw new CantInitializeNetworkIntraUserDataBaseException(CantInitializeNetworkIntraUserDataBaseException.DEFAULT_MESSAGE,databaseNotFoundException, "Trying to open database " + databaseName, "Error in Database plugin. Database should already exists.");
        }
        return database;
    }

    private void closeDatabase(){
        if(database != null)
            database.closeDatabase();
    }
}
