package com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.exceptions.CantInitializeNetworkServiceDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.database.CommunicationNetworkServiceDeveloperDatabaseFactory</code> have
 * contains the methods that the Developer Database Tools uses to show the information.
 * <p/>
 *
 * Created by Yordin Alayn - (y.alayn@gmail.com) on 27/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public final class CommunicationNetworkServiceDeveloperDatabaseFactory {

    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID                 pluginId            ;

    private       Database             database            ;

    public CommunicationNetworkServiceDeveloperDatabaseFactory(final PluginDatabaseSystem pluginDatabaseSystem,
                                                               final UUID                 pluginId            ) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId             = pluginId            ;
    }

    public final void initializeDatabase() throws CantInitializeNetworkServiceDatabaseException {

        try {

            database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());

        } catch (final CantOpenDatabaseException cantOpenDatabaseException) {

            throw new CantInitializeNetworkServiceDatabaseException(cantOpenDatabaseException);
        } catch (final DatabaseNotFoundException e) {

            final CommunicationNetworkServiceDatabaseFactory communicationNetworkServiceDatabaseFactory = new CommunicationNetworkServiceDatabaseFactory(pluginDatabaseSystem);

            try {

                database = communicationNetworkServiceDatabaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (final CantCreateDatabaseException cantCreateDatabaseException) {

                throw new CantInitializeNetworkServiceDatabaseException(cantCreateDatabaseException);
            }
        }
    }


    public final List<DeveloperDatabase> getDatabaseList(final DeveloperObjectFactory developerObjectFactory) {

        List<DeveloperDatabase> databases = new ArrayList<>();

        databases.add(developerObjectFactory.getNewDeveloperDatabase(CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME, this.pluginId.toString()));

        return databases;
    }


    public final List<DeveloperDatabaseTable> getDatabaseTableList(final DeveloperObjectFactory developerObjectFactory) {

        List<DeveloperDatabaseTable> tables = new ArrayList<>();

        /**
         * Table incoming messages columns.
         */
        List<String> incomingMessagesColumns = new ArrayList<>();

        incomingMessagesColumns.add(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_ID_COLUMN_NAME);
        incomingMessagesColumns.add(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_SENDER_ID_COLUMN_NAME);
        incomingMessagesColumns.add(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_RECEIVER_ID_COLUMN_NAME);
        incomingMessagesColumns.add(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TEXT_CONTENT_COLUMN_NAME);
        incomingMessagesColumns.add(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TYPE_COLUMN_NAME);
        incomingMessagesColumns.add(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_SHIPPING_TIMESTAMP_COLUMN_NAME);
        incomingMessagesColumns.add(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_DELIVERY_TIMESTAMP_COLUMN_NAME);
        incomingMessagesColumns.add(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_STATUS_COLUMN_NAME);
        /**
         * Table incoming messages addition.
         */
        DeveloperDatabaseTable incomingMessagesTable = developerObjectFactory.getNewDeveloperDatabaseTable(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_NAME, incomingMessagesColumns);
        tables.add(incomingMessagesTable);

        /**
         * Table outgoing messages columns.
         */
        List<String> outgoingMessagesColumns = new ArrayList<>();

        outgoingMessagesColumns.add(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_ID_COLUMN_NAME);
        outgoingMessagesColumns.add(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_SENDER_ID_COLUMN_NAME);
        outgoingMessagesColumns.add(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_RECEIVER_ID_COLUMN_NAME);
        outgoingMessagesColumns.add(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_TEXT_CONTENT_COLUMN_NAME);
        outgoingMessagesColumns.add(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_TYPE_COLUMN_NAME);
        outgoingMessagesColumns.add(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_SHIPPING_TIMESTAMP_COLUMN_NAME);
        outgoingMessagesColumns.add(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_DELIVERY_TIMESTAMP_COLUMN_NAME);
        outgoingMessagesColumns.add(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_STATUS_COLUMN_NAME);
        /**
         * Table outgoing messages addition.
         */
        DeveloperDatabaseTable outgoingMessagesTable = developerObjectFactory.getNewDeveloperDatabaseTable(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_TABLE_NAME, outgoingMessagesColumns);
        tables.add(outgoingMessagesTable);

        /**
         * Table Negotiation Transmission Network Service columns.
         */
        List<String> negotiationTransmissionNetworkServiceColumns = new ArrayList<String>();

        negotiationTransmissionNetworkServiceColumns.add(CommunicationNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_TRANSMISSION_ID_COLUMN_NAME);
        negotiationTransmissionNetworkServiceColumns.add(CommunicationNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_TRANSACTION_ID_COLUMN_NAME);
        negotiationTransmissionNetworkServiceColumns.add(CommunicationNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_NEGOTIATION_ID_COLUMN_NAME);
        negotiationTransmissionNetworkServiceColumns.add(CommunicationNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_NEGOTIATION_STATUS_COLUMN_NAME);
        negotiationTransmissionNetworkServiceColumns.add(CommunicationNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_NEGOTIATION_TRANSACTION_TYPE_COLUMN_NAME);
        negotiationTransmissionNetworkServiceColumns.add(CommunicationNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_PUBLIC_KEY_ACTOR_SEND_COLUMN_NAME);
        negotiationTransmissionNetworkServiceColumns.add(CommunicationNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_ACTOR_SEND_TYPE_COLUMN_NAME);
        negotiationTransmissionNetworkServiceColumns.add(CommunicationNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_PUBLIC_KEY_ACTOR_RECEIVE_COLUMN_NAME);
        negotiationTransmissionNetworkServiceColumns.add(CommunicationNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_ACTOR_RECEIVE_TYPE_COLUMN_NAME);
        negotiationTransmissionNetworkServiceColumns.add(CommunicationNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_STATE_COLUMN_NAME);
        negotiationTransmissionNetworkServiceColumns.add(CommunicationNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_TIMESTAMP_COLUMN_NAME);
        /**
         * Table Negotiation Transmission Network Service addition.
         */
        DeveloperDatabaseTable negotiationTransmissionNetworkServiceTable = developerObjectFactory.getNewDeveloperDatabaseTable(CommunicationNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_TABLE_NAME, negotiationTransmissionNetworkServiceColumns);
        tables.add(negotiationTransmissionNetworkServiceTable);

        return tables;
    }


    public final List<DeveloperDatabaseTableRecord> getDatabaseTableContent(final DeveloperObjectFactory developerObjectFactory,
                                                                            final DeveloperDatabaseTable developerDatabaseTable) {


        final List<DeveloperDatabaseTableRecord> returnedRecords = new ArrayList<>();

        final DatabaseTable selectedTable = database.getTable(developerDatabaseTable.getName());

        try {

            selectedTable.loadToMemory();
            final List<DatabaseTableRecord> records = selectedTable.getRecords();

            for (final DatabaseTableRecord row: records){

                final List<String> developerRow = new ArrayList<>();

                for (DatabaseRecord field : row.getValues())
                    developerRow.add(field.getValue());


                returnedRecords.add(developerObjectFactory.getNewDeveloperDatabaseTableRecord(developerRow));
            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {

            return returnedRecords;
        } catch (Exception e){
            return returnedRecords;
        }
        return returnedRecords;
    }
}