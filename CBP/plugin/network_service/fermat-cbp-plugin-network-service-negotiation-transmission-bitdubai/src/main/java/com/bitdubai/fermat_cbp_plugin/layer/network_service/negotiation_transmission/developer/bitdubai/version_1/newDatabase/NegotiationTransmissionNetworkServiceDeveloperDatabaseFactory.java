package com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.exceptions.CantInitializeNetworkServiceDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.database.NegotiationTransmissionNetworkServiceDeveloperDatabaseFactory</code> have
 * contains the methods that the Developer Database Tools uses to show the information.
 * <p/>
 * <p/>
 * Created by Yordin Alayn - (y.alayn@gmail.com) on 27/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public final class NegotiationTransmissionNetworkServiceDeveloperDatabaseFactory {

    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;

    private Database database;

    public NegotiationTransmissionNetworkServiceDeveloperDatabaseFactory(final PluginDatabaseSystem pluginDatabaseSystem,
                                                                         final UUID pluginId) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    public final void initializeDatabase() throws CantInitializeNetworkServiceDatabaseException {

        try {

            database = this.pluginDatabaseSystem.openDatabase(pluginId, NegotiationTransmissionNetworkServiceDatabaseConstants.DATA_BASE_NAME);

        } catch (final CantOpenDatabaseException cantOpenDatabaseException) {

            throw new CantInitializeNetworkServiceDatabaseException(cantOpenDatabaseException);
        } catch (final DatabaseNotFoundException e) {

            final com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseFactory negotiationTransmissionNetworkServiceDatabaseFactory = new NegotiationTransmissionNetworkServiceDatabaseFactory(pluginDatabaseSystem);

            try {

                database = negotiationTransmissionNetworkServiceDatabaseFactory.createDatabase(pluginId, NegotiationTransmissionNetworkServiceDatabaseConstants.DATA_BASE_NAME);
            } catch (final CantCreateDatabaseException cantCreateDatabaseException) {

                throw new CantInitializeNetworkServiceDatabaseException(cantCreateDatabaseException);
            }
        }
    }


    public final List<DeveloperDatabase> getDatabaseList(final DeveloperObjectFactory developerObjectFactory) {

        List<DeveloperDatabase> databases = new ArrayList<>();

        databases.add(developerObjectFactory.getNewDeveloperDatabase(NegotiationTransmissionNetworkServiceDatabaseConstants.DATA_BASE_NAME, this.pluginId.toString()));

        return databases;
    }


    public final List<DeveloperDatabaseTable> getDatabaseTableList(final DeveloperObjectFactory developerObjectFactory) {

        List<DeveloperDatabaseTable> tables = new ArrayList<>();

        /**
         * Table incoming notifications columns.
         */
        List<String> outgoingNotificationsColumns = new ArrayList<>();

        outgoingNotificationsColumns.add(com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_TRANSMISSION_ID_COLUMN_NAME);
        outgoingNotificationsColumns.add(com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_TRANSACTION_ID_COLUMN_NAME);
        outgoingNotificationsColumns.add(com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_NEGOTIATION_ID_COLUMN_NAME);
        outgoingNotificationsColumns.add(com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_NEGOTIATION_STATUS_COLUMN_NAME);
        outgoingNotificationsColumns.add(com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_NEGOTIATION_TRANSACTION_TYPE_COLUMN_NAME);
        outgoingNotificationsColumns.add(com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_PUBLIC_KEY_ACTOR_SEND_COLUMN_NAME);
        outgoingNotificationsColumns.add(com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_ACTOR_SEND_TYPE_COLUMN_NAME);
        outgoingNotificationsColumns.add(com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_PUBLIC_KEY_ACTOR_RECEIVE_COLUMN_NAME);
        outgoingNotificationsColumns.add(com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_ACTOR_RECEIVE_TYPE_COLUMN_NAME);
        outgoingNotificationsColumns.add(com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_TRANSMISSION_TYPE_COLUMN_NAME);
        outgoingNotificationsColumns.add(com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_TRANSMISSION_STATE_COLUMN_NAME);
        outgoingNotificationsColumns.add(com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_NEGOTIATION_TYPE_COLUMN_NAME);
        outgoingNotificationsColumns.add(com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_NEGOTIATION_XML_COLUMN_NAME);
        outgoingNotificationsColumns.add(com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_NETWORK_SERVICE_PENDING_FLAG_COLUMN_NAME);
        outgoingNotificationsColumns.add(com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_TIMESTAMP_COLUMN_NAME);
        outgoingNotificationsColumns.add(com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME);
        outgoingNotificationsColumns.add(com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_READ_MARK_COLUMN_NAME);
        outgoingNotificationsColumns.add(com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_RESPONSE_TO_NOTIFICATION_ID_COLUMN_NAME);
        outgoingNotificationsColumns.add(com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_SENT_COUNT_COLUMN_NAME);
        /**
         * Table incoming notifications addition.
         */

        DeveloperDatabaseTable incomingMessagesTable = developerObjectFactory.getNewDeveloperDatabaseTable(com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_TABLE_NAME, outgoingNotificationsColumns);
        tables.add(incomingMessagesTable);

        /**
         * Table outgoing notifications columns.
         */
        List<String> incomingNotificationsColumns = new ArrayList<>();

        incomingNotificationsColumns.add(com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_TRANSMISSION_ID_COLUMN_NAME);
        incomingNotificationsColumns.add(com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_TRANSACTION_ID_COLUMN_NAME);
        incomingNotificationsColumns.add(com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_NEGOTIATION_ID_COLUMN_NAME);
        incomingNotificationsColumns.add(com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_NEGOTIATION_STATUS_COLUMN_NAME);
        incomingNotificationsColumns.add(com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_NEGOTIATION_TRANSACTION_TYPE_COLUMN_NAME);
        incomingNotificationsColumns.add(com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_PUBLIC_KEY_ACTOR_SEND_COLUMN_NAME);
        incomingNotificationsColumns.add(com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_ACTOR_SEND_TYPE_COLUMN_NAME);
        incomingNotificationsColumns.add(com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_PUBLIC_KEY_ACTOR_RECEIVE_COLUMN_NAME);
        incomingNotificationsColumns.add(com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_ACTOR_RECEIVE_TYPE_COLUMN_NAME);
        incomingNotificationsColumns.add(com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_TRANSMISSION_TYPE_COLUMN_NAME);
        incomingNotificationsColumns.add(com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_TRANSMISSION_STATE_COLUMN_NAME);
        incomingNotificationsColumns.add(com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_NEGOTIATION_TYPE_COLUMN_NAME);
        incomingNotificationsColumns.add(com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_NEGOTIATION_XML_COLUMN_NAME);
        incomingNotificationsColumns.add(com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_NETWORK_SERVICE_PENDING_FLAG_COLUMN_NAME);
        incomingNotificationsColumns.add(com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_TIMESTAMP_COLUMN_NAME);
        incomingNotificationsColumns.add(com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME);
        incomingNotificationsColumns.add(com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_READ_MARK_COLUMN_NAME);
        incomingNotificationsColumns.add(com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_RESPONSE_TO_NOTIFICATION_ID_COLUMN_NAME);
        /**
         * Table outgoing notifications addition.
         */
        DeveloperDatabaseTable outgoingMessagesTable = developerObjectFactory.getNewDeveloperDatabaseTable(com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_TABLE_NAME, incomingNotificationsColumns);
        tables.add(outgoingMessagesTable);

        return tables;
    }


    public final List<DeveloperDatabaseTableRecord> getDatabaseTableContent(final DeveloperObjectFactory developerObjectFactory,
                                                                            final DeveloperDatabaseTable developerDatabaseTable) {


        final List<DeveloperDatabaseTableRecord> returnedRecords = new ArrayList<>();

        final DatabaseTable selectedTable = database.getTable(developerDatabaseTable.getName());

        try {

            selectedTable.loadToMemory();
            final List<DatabaseTableRecord> records = selectedTable.getRecords();

            for (final DatabaseTableRecord row : records) {

                final List<String> developerRow = new ArrayList<>();

                for (DatabaseRecord field : row.getValues())
                    developerRow.add(field.getValue());


                returnedRecords.add(developerObjectFactory.getNewDeveloperDatabaseTableRecord(developerRow));
            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {

            return returnedRecords;
        } catch (Exception e) {
            return returnedRecords;
        }
        return returnedRecords;
    }
}