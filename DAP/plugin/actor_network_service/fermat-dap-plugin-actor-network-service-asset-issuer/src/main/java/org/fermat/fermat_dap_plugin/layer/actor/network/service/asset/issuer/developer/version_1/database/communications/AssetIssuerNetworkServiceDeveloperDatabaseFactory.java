package org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.version_1.database.communications;

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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 17/10/15.
 */
public class AssetIssuerNetworkServiceDeveloperDatabaseFactory implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

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
    public AssetIssuerNetworkServiceDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.version_1.exceptions.CantInitializeTemplateNetworkServiceDatabaseException
     */
    public void initializeDatabase() throws org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.version_1.exceptions.CantInitializeTemplateNetworkServiceDatabaseException {
        try {

             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.version_1.database.communications.AssetIssuerNetworkServiceDatabaseConstants.DATA_BASE_NAME);
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.version_1.exceptions.CantInitializeTemplateNetworkServiceDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            AssetIssuerNetworkServiceDatabaseFactory assetIssuerNetworkServiceDatabaseFactory = new AssetIssuerNetworkServiceDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = assetIssuerNetworkServiceDatabaseFactory.createDatabase(pluginId, org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.version_1.database.communications.AssetIssuerNetworkServiceDatabaseConstants.DATA_BASE_NAME);
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.version_1.exceptions.CantInitializeTemplateNetworkServiceDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }


    public void initializeDatabaseCommunication() throws org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.version_1.exceptions.CantInitializeTemplateNetworkServiceDatabaseException {

        try {

            database = this.pluginDatabaseSystem.openDatabase(pluginId, CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

            throw new org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.version_1.exceptions.CantInitializeTemplateNetworkServiceDatabaseException(cantOpenDatabaseException);

        } catch (DatabaseNotFoundException e) {

            AssetIssuerNetworkServiceDatabaseFactory assetIssuerNetworkServiceDatabaseFactory = new AssetIssuerNetworkServiceDatabaseFactory(pluginDatabaseSystem);

            try {

                database = assetIssuerNetworkServiceDatabaseFactory.createDatabase(
                        pluginId,
                        CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME
                );

            } catch (CantCreateDatabaseException cantCreateDatabaseException) {

                throw new org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.version_1.exceptions.CantInitializeTemplateNetworkServiceDatabaseException(cantCreateDatabaseException);

            }
        }
    }

    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase(org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.version_1.database.communications.AssetIssuerNetworkServiceDatabaseConstants.DATA_BASE_NAME, this.pluginId.toString()));
        databases.add(developerObjectFactory.getNewDeveloperDatabase(CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME, this.pluginId.toString()));
        return databases;
    }


    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

        /**
         * Table outgoing messages columns.
         */
        List<String> outgoingNotificationsColumns = new ArrayList<String>();

        outgoingNotificationsColumns.add(org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.version_1.database.communications.AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_ID_COLUMN_NAME);
        outgoingNotificationsColumns.add(org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.version_1.database.communications.AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_SENDER_PUBLIC_KEY_COLUMN_NAME);
        outgoingNotificationsColumns.add(org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.version_1.database.communications.AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_RECEIVER_PUBLIC_KEY_COLUMN_NAME);
        outgoingNotificationsColumns.add(org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.version_1.database.communications.AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_SENDER_TYPE_COLUMN_NAME);
        outgoingNotificationsColumns.add(org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.version_1.database.communications.AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_RECEIVER_TYPE_COLUMN_NAME);
        outgoingNotificationsColumns.add(org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.version_1.database.communications.AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_TIMESTAMP_COLUMN_NAME);
        outgoingNotificationsColumns.add(org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.version_1.database.communications.AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_SENDER_ALIAS_COLUMN_NAME);
        outgoingNotificationsColumns.add(org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.version_1.database.communications.AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_DESCRIPTOR_COLUMN_NAME);
        outgoingNotificationsColumns.add(org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.version_1.database.communications.AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME);
        outgoingNotificationsColumns.add(org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.version_1.database.communications.AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_READ_MARK_COLUMN_NAME);
        outgoingNotificationsColumns.add(org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.version_1.database.communications.AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_SENT_COUNT_COLUMN_NAME);
        outgoingNotificationsColumns.add(org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.version_1.database.communications.AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_BLOCKCHAIN_NETWORK_TYPE_COLUMN_NAME);
        outgoingNotificationsColumns.add(org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.version_1.database.communications.AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_RESPONSE_TO_NOTIFICATION_ID_COLUMN_NAME);
        outgoingNotificationsColumns.add(org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.version_1.database.communications.AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_MESSAGE);

        /**
         * Table incoming messages addition.
         */
        DeveloperDatabaseTable outgoingNotificationsTable = developerObjectFactory.getNewDeveloperDatabaseTable(org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.version_1.database.communications.AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_TABLE_NAME, outgoingNotificationsColumns);
        tables.add(outgoingNotificationsTable);

        /**
         * Table incoming messages columns.
         */
        List<String> incomingNotificationsColumns = new ArrayList<String>();

        incomingNotificationsColumns.add(org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.version_1.database.communications.AssetIssuerNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_ID_COLUMN_NAME);
        incomingNotificationsColumns.add(org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.version_1.database.communications.AssetIssuerNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_SENDER_PUBLIC_KEY_COLUMN_NAME);
        incomingNotificationsColumns.add(org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.version_1.database.communications.AssetIssuerNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_RECEIVER_PUBLIC_KEY_COLUMN_NAME);
        incomingNotificationsColumns.add(org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.version_1.database.communications.AssetIssuerNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_SENDER_TYPE_COLUMN_NAME);
        incomingNotificationsColumns.add(org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.version_1.database.communications.AssetIssuerNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_RECEIVER_TYPE_COLUMN_NAME);
        incomingNotificationsColumns.add(org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.version_1.database.communications.AssetIssuerNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_TIMESTAMP_COLUMN_NAME);
        incomingNotificationsColumns.add(org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.version_1.database.communications.AssetIssuerNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_SENDER_ALIAS_COLUMN_NAME);
        incomingNotificationsColumns.add(org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.version_1.database.communications.AssetIssuerNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_DESCRIPTOR_COLUMN_NAME);
        incomingNotificationsColumns.add(org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.version_1.database.communications.AssetIssuerNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME);
        incomingNotificationsColumns.add(org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.version_1.database.communications.AssetIssuerNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_READ_MARK_COLUMN_NAME);
        incomingNotificationsColumns.add(org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.version_1.database.communications.AssetIssuerNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_BLOCKCHAIN_NETWORK_TYPE_COLUMN_NAME);
        incomingNotificationsColumns.add(org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.version_1.database.communications.AssetIssuerNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_RESPONSE_TO_NOTIFICATION_ID_COLUMN_NAME);
        incomingNotificationsColumns.add(org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.version_1.database.communications.AssetIssuerNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_MESSAGE);

        /**
         * Table outgoing messages addition.
         */
        DeveloperDatabaseTable incomingNotificationsTable = developerObjectFactory.getNewDeveloperDatabaseTable(org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.version_1.database.communications.AssetIssuerNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_TABLE_NAME, incomingNotificationsColumns);
        tables.add(incomingNotificationsTable);

        return tables;
    }

    public List<DeveloperDatabaseTable> getDatabaseTableListCommunication(final DeveloperObjectFactory developerObjectFactory) {


        List<DeveloperDatabaseTable> tables = new ArrayList<>();

        /**
         * Table incoming messages columns.
         */
        List<String> incomingMessagesColumns = new ArrayList<String>();

        incomingMessagesColumns.add(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_ID_COLUMN_NAME);
        incomingMessagesColumns.add(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_SENDER_ID_COLUMN_NAME);
        incomingMessagesColumns.add(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_RECEIVER_ID_COLUMN_NAME);

        incomingMessagesColumns.add(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TYPE_COLUMN_NAME);
        incomingMessagesColumns.add(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_SHIPPING_TIMESTAMP_COLUMN_NAME);
        incomingMessagesColumns.add(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_DELIVERY_TIMESTAMP_COLUMN_NAME);
        incomingMessagesColumns.add(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_STATUS_COLUMN_NAME);
        incomingMessagesColumns.add(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TEXT_CONTENT_COLUMN_NAME);
        /**
         * Table incoming messages addition.
         */
        DeveloperDatabaseTable incomingMessagesTable = developerObjectFactory.getNewDeveloperDatabaseTable(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_NAME, incomingMessagesColumns);
        tables.add(incomingMessagesTable);

        /**
         * Table outgoing messages columns.
         */
        List<String> outgoingMessagesColumns = new ArrayList<String>();

        outgoingMessagesColumns.add(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_ID_COLUMN_NAME);
        outgoingMessagesColumns.add(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_SENDER_ID_COLUMN_NAME);
        outgoingMessagesColumns.add(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_SENDER_TYPE_COLUMN_NAME);
        outgoingMessagesColumns.add(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_SENDER_NS_TYPE_COLUMN_NAME);
        outgoingMessagesColumns.add(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_RECEIVER_ID_COLUMN_NAME);
        outgoingMessagesColumns.add(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_RECEIVER_TYPE_COLUMN_NAME);
        outgoingMessagesColumns.add(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_RECEIVER_NS_TYPE_COLUMN_NAME);
        outgoingMessagesColumns.add(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_TYPE_COLUMN_NAME);
        outgoingMessagesColumns.add(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_SHIPPING_TIMESTAMP_COLUMN_NAME);
        outgoingMessagesColumns.add(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_DELIVERY_TIMESTAMP_COLUMN_NAME);
        outgoingMessagesColumns.add(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_FAIL_COUNT_COLUMN_NAME);
        outgoingMessagesColumns.add(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_STATUS_COLUMN_NAME);
        outgoingMessagesColumns.add(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_TEXT_CONTENT_COLUMN_NAME);
        /**
         * Table outgoing messages addition.
         */
        DeveloperDatabaseTable outgoingMessagesTable = developerObjectFactory.getNewDeveloperDatabaseTable(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_TABLE_NAME, outgoingMessagesColumns);
        tables.add(outgoingMessagesTable);

        return tables;
    }

    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        /**
         * Will get the records for the given table
         */
        List<DeveloperDatabaseTableRecord> returnedRecords = new ArrayList<DeveloperDatabaseTableRecord>();
        try {
            if (!developerDatabase.getName().equals(org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.version_1.database.communications.AssetIssuerNetworkServiceDatabaseConstants.DATA_BASE_NAME))
                initializeDatabaseCommunication();
            else
                initializeDatabase();

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

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }
}
