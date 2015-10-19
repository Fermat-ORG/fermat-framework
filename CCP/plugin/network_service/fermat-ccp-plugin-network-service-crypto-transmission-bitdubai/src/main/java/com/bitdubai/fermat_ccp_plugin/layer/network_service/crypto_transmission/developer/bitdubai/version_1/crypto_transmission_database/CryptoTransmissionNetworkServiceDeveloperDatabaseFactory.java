package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.crypto_transmission_database;

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
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.crypto_transmission_database.exceptions.CantInitializeCryptoTransmissionNetworkServiceDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.cryptotransmission.developer.bitdubai.version_1.database.CryptoTransmissionNetworkServiceDeveloperDatabaseFactory</code> have
 * contains the methods that the Developer Database Tools uses to show the information.
 * <p/>
 *
 * Created by Matias Furszyfer - (matiasfurszyfer@gmail.com) on 05/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class CryptoTransmissionNetworkServiceDeveloperDatabaseFactory implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

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
     * @param pluginDatabaseSystem
     * @param pluginId
     */
    public CryptoTransmissionNetworkServiceDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeCryptoTransmissionNetworkServiceDatabaseException
     */
    public void initializeDatabase() throws CantInitializeCryptoTransmissionNetworkServiceDatabaseException {
        try {

             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeCryptoTransmissionNetworkServiceDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            CryptoTransmissionNetworkServiceDatabaseFactory cryptoTransmissionNetworkServiceDatabaseFactory = new CryptoTransmissionNetworkServiceDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = cryptoTransmissionNetworkServiceDatabaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeCryptoTransmissionNetworkServiceDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }


    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase("CryptoTransmission", this.pluginId.toString()));
        return databases;
    }


    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

           /**
            * Table CRYPTO TRANSMISSION METADATA columns.
            */
           List<String> cRYPTOTRANSMISSIONMETADATAColumns = new ArrayList<String>();

              cRYPTOTRANSMISSIONMETADATAColumns.add(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_TRANSMISSION_ID_COLUMN_NAME);
              cRYPTOTRANSMISSIONMETADATAColumns.add(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_REQUEST_ID_COLUMN_NAME);
              cRYPTOTRANSMISSIONMETADATAColumns.add(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_CRYPTO_CURRENCY_COLUMN_NAME);
              cRYPTOTRANSMISSIONMETADATAColumns.add(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_CRYPTO_AMOUNT_COLUMN_NAME);
              cRYPTOTRANSMISSIONMETADATAColumns.add(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_SENDER_PUBLICK_KEY_COLUMN_NAME);
              cRYPTOTRANSMISSIONMETADATAColumns.add(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_DESTINATION_PUBLIC_KEY_COLUMN_NAME);
              cRYPTOTRANSMISSIONMETADATAColumns.add(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_ASSOCIATED_CRYPTO_TRANSACTION_HASH_COLUMN_NAME);
              cRYPTOTRANSMISSIONMETADATAColumns.add(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_PAYMENT_DESCRIPTION_COLUMN_NAME);
              cRYPTOTRANSMISSIONMETADATAColumns.add(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_STATUS_COLUMN_NAME);
           /**
            * Table CRYPTO TRANSMISSION METADATA addition.
            */
                   DeveloperDatabaseTable cRYPTOTRANSMISSIONMETADATATable = developerObjectFactory.getNewDeveloperDatabaseTable(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_TABLE_NAME, cRYPTOTRANSMISSIONMETADATAColumns);
                   tables.add(cRYPTOTRANSMISSIONMETADATATable);

           /**
            * Table COMPONENT VERSIONS DETAILS columns.
            */
           List<String> cOMPONENTVERSIONSDETAILSColumns = new ArrayList<String>();

              cOMPONENTVERSIONSDETAILSColumns.add(CryptoTransmissionNetworkServiceDatabaseConstants.COMPONENT_VERSIONS_DETAILS_ID_COLUMN_NAME);
              cOMPONENTVERSIONSDETAILSColumns.add(CryptoTransmissionNetworkServiceDatabaseConstants.COMPONENT_VERSIONS_DETAILS_ACTOR_PUBLIC_KEY_COLUMN_NAME);
              cOMPONENTVERSIONSDETAILSColumns.add(CryptoTransmissionNetworkServiceDatabaseConstants.COMPONENT_VERSIONS_DETAILS_IPK_COLUMN_NAME);
              cOMPONENTVERSIONSDETAILSColumns.add(CryptoTransmissionNetworkServiceDatabaseConstants.COMPONENT_VERSIONS_DETAILS_LAST_CONNECTION_COLUMN_NAME);
           /**
            * Table COMPONENT VERSIONS DETAILS addition.
            */
                   DeveloperDatabaseTable cOMPONENTVERSIONSDETAILSTable = developerObjectFactory.getNewDeveloperDatabaseTable(CryptoTransmissionNetworkServiceDatabaseConstants.COMPONENT_VERSIONS_DETAILS_TABLE_NAME, cOMPONENTVERSIONSDETAILSColumns);
                   tables.add(cOMPONENTVERSIONSDETAILSTable);



        return tables;
    }


    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabaseTable developerDatabaseTable) {
        /**
         * Will get the records for the given table
         */
        List<DeveloperDatabaseTableRecord> returnedRecords = new ArrayList<DeveloperDatabaseTableRecord>();
        /**
         * I load the passed table name from the SQLite database.
         */
        DatabaseTable selectedTable = database.getTable(developerDatabaseTable.getName());
        try {
            selectedTable.loadToMemory();
            List<DatabaseTableRecord> records = selectedTable.getRecords();
            for (DatabaseTableRecord row: records){
                List<String> developerRow = new ArrayList<String>();
                /**
                 * for each row in the table list
                 */
                for (DatabaseRecord field : row.getValues()){
                    /**
                     * I get each row and save them into a List<String>
                     */
                    developerRow.add(field.getValue());
                }
                /**
                 * I create the Developer Database record
                 */
                returnedRecords.add(developerObjectFactory.getNewDeveloperDatabaseTableRecord(developerRow));
            }
            /**
             * return the list of DeveloperRecords for the passed table.
             */
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            /**
             * if there was an error, I will returned an empty list.
             */
            database.closeDatabase();
            return returnedRecords;
        } catch (Exception e){
            database.closeDatabase();
            return returnedRecords;
        }
        database.closeDatabase();
        return returnedRecords;
    }

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }
}
