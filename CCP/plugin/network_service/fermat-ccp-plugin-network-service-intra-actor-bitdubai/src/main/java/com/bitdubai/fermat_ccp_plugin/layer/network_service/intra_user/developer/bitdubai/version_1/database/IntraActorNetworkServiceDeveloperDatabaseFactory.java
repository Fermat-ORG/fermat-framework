package com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.data_base.CommunicationNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantInitializeTemplateNetworkServiceDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by natalia on 18/12/15.
 */
public class IntraActorNetworkServiceDeveloperDatabaseFactory {//implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

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
    public IntraActorNetworkServiceDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeTemplateNetworkServiceDatabaseException
     */
    public void initializeDatabase() throws CantInitializeTemplateNetworkServiceDatabaseException {
        try {

             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId,IntraActorNetworkServiceDataBaseConstants.DATA_BASE_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeTemplateNetworkServiceDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            IntraActorNetworkServiceDatabaseFactory intraActorNetworkServiceDatabaseFactory = new IntraActorNetworkServiceDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = intraActorNetworkServiceDatabaseFactory.createDatabase(pluginId, IntraActorNetworkServiceDataBaseConstants.DATA_BASE_NAME);
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeTemplateNetworkServiceDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }


    public void initializeDatabaseCommunication() throws CantInitializeTemplateNetworkServiceDatabaseException {

        try {

            database = this.pluginDatabaseSystem.openDatabase(pluginId, CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

            throw new CantInitializeTemplateNetworkServiceDatabaseException(cantOpenDatabaseException);

        } catch (DatabaseNotFoundException e) {

            IntraActorNetworkServiceDatabaseFactory cryptoPaymentRequestNetworkServiceDatabaseFactory = new IntraActorNetworkServiceDatabaseFactory(pluginDatabaseSystem);

            try {

                database = cryptoPaymentRequestNetworkServiceDatabaseFactory.createDatabase(
                        pluginId,
                        CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME
                );

            } catch (CantCreateDatabaseException cantCreateDatabaseException) {

                throw new CantInitializeTemplateNetworkServiceDatabaseException(cantCreateDatabaseException);

            }
        }
    }


    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase(IntraActorNetworkServiceDataBaseConstants.DATA_BASE_NAME, this.pluginId.toString()));
        databases.add(developerObjectFactory.getNewDeveloperDatabase(CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME, this.pluginId.toString()));

        return databases;
    }


    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();


        /**
         * Table incoming_notification columns.
         */
        List<String> intraUsersIncomingColumns = new ArrayList<String>();

        intraUsersIncomingColumns.add(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_ID_COLUMN_NAME);
        intraUsersIncomingColumns.add(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_SENDER_PUBLIC_KEY_COLUMN_NAME);
        intraUsersIncomingColumns.add(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_RECEIVER_PUBLIC_KEY_COLUMN_NAME);
        intraUsersIncomingColumns.add(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_SENDER_TYPE_COLUMN_NAME);
        intraUsersIncomingColumns.add(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_RECEIVER_TYPE_COLUMN_NAME);
        intraUsersIncomingColumns.add(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_TIMESTAMP_COLUMN_NAME);
        intraUsersIncomingColumns.add(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_SENDER_ALIAS_COLUMN_NAME);
        intraUsersIncomingColumns.add(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_READ_MARK_COLUMN_NAME);
        intraUsersIncomingColumns.add(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_SENDER_PHRASE_COLUMN_NAME);
        intraUsersIncomingColumns.add(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_RESPONSE_TO_NOTIFICATION_ID_COLUMN_NAME);


        /**
         * Table incoming messages addition.
         */
        DeveloperDatabaseTable intraUsersIncomingTable = developerObjectFactory.getNewDeveloperDatabaseTable(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_TABLE_NAME, intraUsersIncomingColumns);
        tables.add(intraUsersIncomingTable);


        /**
         * Table outgoing_notification columns.
         */
        List<String> intraUsersOutgoingColumns = new ArrayList<String>();

        intraUsersOutgoingColumns.add(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_ID_COLUMN_NAME);
        intraUsersOutgoingColumns.add(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_SENDER_PUBLIC_KEY_COLUMN_NAME);
        intraUsersOutgoingColumns.add(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_RECEIVER_PUBLIC_KEY_COLUMN_NAME);
        intraUsersOutgoingColumns.add(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_SENDER_TYPE_COLUMN_NAME);
        intraUsersOutgoingColumns.add(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_RECEIVER_TYPE_COLUMN_NAME);
        intraUsersOutgoingColumns.add(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_TIMESTAMP_COLUMN_NAME);
        intraUsersOutgoingColumns.add(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_SENDER_ALIAS_COLUMN_NAME);
        intraUsersOutgoingColumns.add(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_DESCRIPTOR_COLUMN_NAME);
        intraUsersOutgoingColumns.add(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME);
        intraUsersOutgoingColumns.add(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_READ_MARK_COLUMN_NAME);
        intraUsersOutgoingColumns.add(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_SENT_COUNT_COLUMN_NAME);
        intraUsersOutgoingColumns.add(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_SENDER_PHRASE_COLUMN_NAME);
        intraUsersOutgoingColumns.add(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_RESPONSE_TO_NOTIFICATION_ID_COLUMN_NAME);

        /**
         * Table incoming messages addition.
         */
        DeveloperDatabaseTable intraUsersOutgoingTable = developerObjectFactory.getNewDeveloperDatabaseTable(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_TABLE_NAME, intraUsersOutgoingColumns);
        tables.add(intraUsersOutgoingTable);
        /**
         * Table intra users online cache columns.
         */
        List<String> intraUsersOnlineColumns = new ArrayList<String>();

        intraUsersOnlineColumns.add(IntraActorNetworkServiceDataBaseConstants.INTRA_ACTOR_ONLINE_CACHE_ID_COLUMN_NAME);
        intraUsersOnlineColumns.add(IntraActorNetworkServiceDataBaseConstants.INTRA_ACTOR_ONLINE_CACHE_PUBLIC_KEY_COLUMN_NAME);
        intraUsersOnlineColumns.add(IntraActorNetworkServiceDataBaseConstants.INTRA_ACTOR_ONLINE_CACHE_ALIAS_COLUMN_NAME);
        intraUsersOnlineColumns.add(IntraActorNetworkServiceDataBaseConstants.INTRA_ACTOR_ONLINE_CACHE_CITY_COLUMN_NAME);
        intraUsersOnlineColumns.add(IntraActorNetworkServiceDataBaseConstants.INTRA_ACTOR_ONLINE_CACHE_COUNTRY_COLUMN_NAME);
        intraUsersOnlineColumns.add(IntraActorNetworkServiceDataBaseConstants.INTRA_ACTOR_ONLINE_CACHE_PHRASE_COLUMN_NAME);
        intraUsersOnlineColumns.add(IntraActorNetworkServiceDataBaseConstants.INTRA_ACTOR_ONLINE_CACHE_TIMESTAMP_COLUMN_NAME);
        /**
         * Table outgoing messages addition.
         */
        DeveloperDatabaseTable intraUsersOnlineTable = developerObjectFactory.getNewDeveloperDatabaseTable(IntraActorNetworkServiceDataBaseConstants.INTRA_ACTOR_ONLINE_CACHE_TABLE_NAME, intraUsersOnlineColumns);
        tables.add(intraUsersOnlineTable);


        return tables;
    }


    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase,DeveloperDatabaseTable developerDatabaseTable) {

        /**
         * Will get the records for the given table
         */
        List<DeveloperDatabaseTableRecord> returnedRecords = new ArrayList<DeveloperDatabaseTableRecord>();
        try {
            if(!developerDatabase.getName().equals(IntraActorNetworkServiceDataBaseConstants.DATA_BASE_NAME))
                initializeDatabaseCommunication();
            else
                initializeDatabase();


        /**
         * I load the passed table name from the SQLite database.
         */
        DatabaseTable selectedTable = database.getTable(developerDatabaseTable.getName());

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

    public List<DeveloperDatabaseTable> getDatabaseTableListCommunication(final DeveloperObjectFactory developerObjectFactory) {


        List<DeveloperDatabaseTable> tables = new ArrayList<>();

        /**
         * Table Crypto Address Request columns.
         */
        List<String> cryptoIncomingColumns = new ArrayList<>();

        cryptoIncomingColumns.add(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_ID_COLUMN_NAME         );
        cryptoIncomingColumns.add(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_SENDER_ID_COLUMN_NAME);
        cryptoIncomingColumns.add(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_RECEIVER_ID_COLUMN_NAME      );
        cryptoIncomingColumns.add(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TEXT_CONTENT_COLUMN_NAME   );
        cryptoIncomingColumns.add(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TYPE_COLUMN_NAME         );
        cryptoIncomingColumns.add(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_SHIPPING_TIMESTAMP_COLUMN_NAME        );
        cryptoIncomingColumns.add(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_DELIVERY_TIMESTAMP_COLUMN_NAME     );
        cryptoIncomingColumns.add(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_STATUS_COLUMN_NAME    );
        /**
         * Table Crypto Address Request addition.
         */
        DeveloperDatabaseTable cryptoIncomingTable = developerObjectFactory.getNewDeveloperDatabaseTable(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_NAME, cryptoIncomingColumns);
        tables.add(cryptoIncomingTable);

        List<String> cryptoOutgoingColumns = new ArrayList<>();
        cryptoOutgoingColumns.add(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_ID_COLUMN_NAME         );
        cryptoOutgoingColumns.add(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_SENDER_ID_COLUMN_NAME);
        cryptoOutgoingColumns.add(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_RECEIVER_ID_COLUMN_NAME      );
        cryptoOutgoingColumns.add(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_TEXT_CONTENT_COLUMN_NAME   );
        cryptoOutgoingColumns.add(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_TYPE_COLUMN_NAME         );
        cryptoOutgoingColumns.add(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_SHIPPING_TIMESTAMP_COLUMN_NAME        );
        cryptoOutgoingColumns.add(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_DELIVERY_TIMESTAMP_COLUMN_NAME     );
        cryptoOutgoingColumns.add(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_STATUS_COLUMN_NAME    );
        /**
         * Table Crypto Address Request addition.
         */
        DeveloperDatabaseTable cryptoOutgoingTable = developerObjectFactory.getNewDeveloperDatabaseTable(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_TABLE_NAME, cryptoOutgoingColumns);
        tables.add(cryptoOutgoingTable);


        return tables;
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