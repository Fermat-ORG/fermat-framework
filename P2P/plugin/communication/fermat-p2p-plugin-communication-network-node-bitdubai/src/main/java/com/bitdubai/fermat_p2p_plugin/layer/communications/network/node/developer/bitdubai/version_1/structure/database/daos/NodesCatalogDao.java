package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.daos;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOrder;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.NodesCatalog;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.RecordNotFoundException;

import java.util.ArrayList;
import java.util.List;

import static com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CATALOG_DEFAULT_PORT_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CATALOG_IDENTITY_PUBLIC_KEY_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CATALOG_IP_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CATALOG_LAST_CONNECTION_TIMESTAMP_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CATALOG_LAST_LATITUDE_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CATALOG_LAST_LONGITUDE_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CATALOG_LATE_NOTIFICATION_COUNTER_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CATALOG_NAME_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CATALOG_OFFLINE_COUNTER_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CATALOG_REGISTERED_TIMESTAMP_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CATALOG_TABLE_NAME;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.daos.NodesCatalogDao</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 23/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class NodesCatalogDao  extends AbstractBaseDao<NodesCatalog> {

    /**
     * Constructor with parameter
     *
     * @param dataBase
     */
    public NodesCatalogDao(Database dataBase) {
        super(dataBase, NODES_CATALOG_TABLE_NAME, NODES_CATALOG_IDENTITY_PUBLIC_KEY_COLUMN_NAME );
    }

    /**
     * Method that update a nodes catalog record in the database increasing its offline counter.
     *
     * @param publicKey       of the node profile to update.
     * @param offlineCounter  to set.
     *
     * @throws CantUpdateRecordDataBaseException  if something goes wrong.
     * @throws RecordNotFoundException            if we can't find the record in db.
     */
    public final void setOfflineCounter(final String  publicKey     ,
                                        final Integer offlineCounter) throws CantUpdateRecordDataBaseException, RecordNotFoundException, InvalidParameterException {

        if (publicKey == null)
            throw new IllegalArgumentException("The publicKey is required, can not be null.");

        if (offlineCounter == null)
            throw new IllegalArgumentException("The offlineCounter is required, can not be null.");

        try {

            final DatabaseTable table = this.getDatabaseTable();
            table.addStringFilter(this.getIdTableName(), publicKey, DatabaseFilterType.EQUAL);
            table.loadToMemory();

            final List<DatabaseTableRecord> records = table.getRecords();

            if (!records.isEmpty()) {
                DatabaseTableRecord record = records.get(0);
                record.setIntegerValue(NODES_CATALOG_OFFLINE_COUNTER_COLUMN_NAME, offlineCounter);
                table.updateRecord(record);
            } else
                throw new RecordNotFoundException("publicKey: " + publicKey, "Cannot find an node catalog with this public key.");

        } catch (final CantUpdateRecordException e) {

            throw new CantUpdateRecordDataBaseException(e, "Table Name: " + this.getTableName(), "The record do not exist");
        } catch (final CantLoadTableToMemoryException e) {

            throw new CantUpdateRecordDataBaseException(e, "Table Name: " + this.getTableName(), "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        }
    }

    /**
     * Method that list the all entities on the data base. The valid value of
     * the key are the att of the <code>DatabaseConstants</code>
     *
     * @param max     number of records to bring
     * @param offset  pointer to start bringing records.
     * @param point   coordinates
     *
     * @return All entities filtering by the parameters specified.
     *
     * @throws CantReadRecordDataBaseException if something goes wrong.
     *
     */
    public final List<NodesCatalog> findAllNearestTo(final Integer  max   ,
                                                     final Integer  offset,
                                                     final Location point ) throws CantReadRecordDataBaseException {

        try {

            final DatabaseTable table = getDatabaseTable();

            table.setFilterTop(max.toString());
            table.setFilterOffSet(offset.toString());

            table.addNearbyLocationOrder(
                    NODES_CATALOG_LAST_LATITUDE_COLUMN_NAME,
                    NODES_CATALOG_LAST_LONGITUDE_COLUMN_NAME,
                    point,
                    DatabaseFilterOrder.ASCENDING,
                    "NOT_NECESSARY"
            );

            table.loadToMemory();

            final List<DatabaseTableRecord> records = table.getRecords();

            final List<NodesCatalog> list = new ArrayList<>();

            // Convert into entity objects and add to the list.
            for (DatabaseTableRecord record : records)
                list.add(getEntityFromDatabaseTableRecord(record));

            return list;

        } catch (final CantLoadTableToMemoryException e) {

            throw new CantReadRecordDataBaseException(e, "Table Name: " + super.getTableName(), "The data no exist");
        } catch (final InvalidParameterException e) {

            throw new CantReadRecordDataBaseException(e, "Table Name: " + super.getTableName(), "Invalid parameter found, maybe the enum is wrong.");
        }
    }

    /**
     * (non-javadoc)
     * @see AbstractBaseDao#getEntityFromDatabaseTableRecord(DatabaseTableRecord)
     */
    @Override
    protected NodesCatalog getEntityFromDatabaseTableRecord(DatabaseTableRecord record) throws InvalidParameterException {

        NodesCatalog entity = new NodesCatalog();

        entity.setLastConnectionTimestamp(getTimestampFromLongValue(record.getLongValue(NODES_CATALOG_LAST_CONNECTION_TIMESTAMP_COLUMN_NAME)));
        entity.setDefaultPort(record.getIntegerValue(NODES_CATALOG_DEFAULT_PORT_COLUMN_NAME));
        entity.setIdentityPublicKey(record.getStringValue(NODES_CATALOG_IDENTITY_PUBLIC_KEY_COLUMN_NAME));
        entity.setIp(record.getStringValue(NODES_CATALOG_IP_COLUMN_NAME));
        entity.setName(record.getStringValue(NODES_CATALOG_NAME_COLUMN_NAME));
        entity.setLastLocation(record.getDoubleValue(NODES_CATALOG_LAST_LATITUDE_COLUMN_NAME), record.getDoubleValue(NODES_CATALOG_LAST_LONGITUDE_COLUMN_NAME));
        entity.setRegisteredTimestamp(getTimestampFromLongValue(record.getLongValue(NODES_CATALOG_REGISTERED_TIMESTAMP_COLUMN_NAME)));
        entity.setLateNotificationsCounter(record.getIntegerValue(NODES_CATALOG_LATE_NOTIFICATION_COUNTER_COLUMN_NAME));
        entity.setOfflineCounter(record.getIntegerValue(NODES_CATALOG_OFFLINE_COUNTER_COLUMN_NAME));
        
        return entity;
    }

    /**
     * (non-javadoc)
     * @see AbstractBaseDao#getDatabaseTableRecordFromEntity
     */
    @Override
    protected DatabaseTableRecord getDatabaseTableRecordFromEntity(NodesCatalog entity) {

        DatabaseTableRecord databaseTableRecord = getDatabaseTable().getEmptyRecord();

        databaseTableRecord.setLongValue(NODES_CATALOG_LAST_CONNECTION_TIMESTAMP_COLUMN_NAME, getLongValueFromTimestamp(entity.getLastConnectionTimestamp()));
        databaseTableRecord.setIntegerValue(NODES_CATALOG_DEFAULT_PORT_COLUMN_NAME, entity.getDefaultPort());
        databaseTableRecord.setStringValue(NODES_CATALOG_IDENTITY_PUBLIC_KEY_COLUMN_NAME, entity.getIdentityPublicKey());
        databaseTableRecord.setStringValue(NODES_CATALOG_IP_COLUMN_NAME, entity.getIp());
        databaseTableRecord.setStringValue(NODES_CATALOG_NAME_COLUMN_NAME, entity.getName());
        databaseTableRecord.setDoubleValue(NODES_CATALOG_LAST_LATITUDE_COLUMN_NAME, entity.getLastLocation().getLatitude());
        databaseTableRecord.setDoubleValue(NODES_CATALOG_LAST_LONGITUDE_COLUMN_NAME, entity.getLastLocation().getLongitude());
        databaseTableRecord.setLongValue(NODES_CATALOG_REGISTERED_TIMESTAMP_COLUMN_NAME, getLongValueFromTimestamp(entity.getRegisteredTimestamp()));
        databaseTableRecord.setIntegerValue(NODES_CATALOG_LATE_NOTIFICATION_COUNTER_COLUMN_NAME, entity.getLateNotificationsCounter());
        databaseTableRecord.setIntegerValue(NODES_CATALOG_OFFLINE_COUNTER_COLUMN_NAME, entity.getOfflineCounter());

        return databaseTableRecord;
    }

    /**
     * Method that list ten nodes with less late_notification_counter and offline_counter
     *
     * @return All entities.
     *
     * @throws CantReadRecordDataBaseException if something goes wrong.
     */
    public final List<NodesCatalog> getNodeCatalogueListToShare(String identityPublicKey) throws CantReadRecordDataBaseException {

        try {

            // load the data base to memory
            DatabaseTable table = getDatabaseTable();

            table.addFilterOrder(NODES_CATALOG_LATE_NOTIFICATION_COUNTER_COLUMN_NAME, DatabaseFilterOrder.ASCENDING);
            table.addFilterOrder(NODES_CATALOG_OFFLINE_COUNTER_COLUMN_NAME          , DatabaseFilterOrder.ASCENDING);

            table.addStringFilter(NODES_CATALOG_IDENTITY_PUBLIC_KEY_COLUMN_NAME, identityPublicKey, DatabaseFilterType.NOT_EQUALS);
            table.setFilterTop("10");
            table.loadToMemory();

            final List<DatabaseTableRecord> records = table.getRecords();

            final List<NodesCatalog> list = new ArrayList<>();

            // Convert into entity objects and add to the list.
            for (DatabaseTableRecord record : records)
                list.add(getEntityFromDatabaseTableRecord(record));

            return list;

        } catch (final CantLoadTableToMemoryException e) {

            throw new CantReadRecordDataBaseException(e, "Table Name: " + getTableName(), "The data no exist");
        } catch (final InvalidParameterException e) {

            throw new CantReadRecordDataBaseException(e, "Table Name: " + getTableName(), "Invalid parameter found, maybe the enum is wrong.");
        }
    }

}
