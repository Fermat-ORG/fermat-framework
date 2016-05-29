package com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.enums.DeviceDirectory;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOperator;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilterGroup;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.enums.ConnectionRequestAction;
import com.bitdubai.fermat_art_api.layer.actor_network_service.enums.ProtocolState;
import com.bitdubai.fermat_art_api.layer.actor_network_service.enums.RequestType;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantAcceptConnectionRequestException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantCancelConnectionRequestException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantDenyConnectionRequestException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantDisconnectException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantListPendingConnectionRequestsException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantRequestConnectionException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.ConnectionRequestNotFoundException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistConnectionRequest;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.util.FanConnectionInformation;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.util.FanConnectionRequest;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.exceptions.CantChangeProtocolStateException;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.exceptions.CantConfirmConnectionRequestException;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.exceptions.CantFindRequestException;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.exceptions.CantGetProfileImageException;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.exceptions.CantInitializeDatabaseException;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.exceptions.CantPersistProfileImageException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * The class <code>com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.database.CryptoBrokerActorNetworkServiceDao</code>
 * contains all the methods that interact with the database.
 * Manages the actor connection requests by storing them on a Database Table.
 * <p/>
 * Created by Gabriel Araujo. (31/03/2016)
 *
 * @version 1.0
 */
public final class FanActorNetworkServiceDao {

    private static final String PROFILE_IMAGE_DIRECTORY_NAME   = DeviceDirectory.LOCAL_USERS.getName() + "/ART/artistActorNS";
    private static final String PROFILE_IMAGE_FILE_NAME_PREFIX = "profileImage";

    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final PluginFileSystem     pluginFileSystem    ;
    private final UUID                 pluginId            ;

    private Database database;

    public FanActorNetworkServiceDao(final PluginDatabaseSystem pluginDatabaseSystem,
                                     final PluginFileSystem pluginFileSystem,
                                     final UUID pluginId) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginFileSystem     = pluginFileSystem    ;
        this.pluginId             = pluginId            ;
    }

    public void initialize() throws CantInitializeDatabaseException {

        try {

            database = this.pluginDatabaseSystem.openDatabase(
                    this.pluginId,
                    FanActorNetworkServiceDatabaseConstants.FAN_ACTOR_NETWORK_SERVICE_DATABASE_NAME
            );

        } catch (final DatabaseNotFoundException e) {

            try {

                FanActorNetworkServiceDatabaseFactory FanActorNetworkServiceDatabaseFactory = new FanActorNetworkServiceDatabaseFactory(pluginDatabaseSystem);
                database = FanActorNetworkServiceDatabaseFactory.createDatabase(
                        pluginId,
                        FanActorNetworkServiceDatabaseConstants.FAN_ACTOR_NETWORK_SERVICE_DATABASE_NAME
                );

            } catch (final CantCreateDatabaseException f) {

                throw new CantInitializeDatabaseException(f, "", "There was a problem and we cannot create the database.");
            } catch (final Exception z) {

                throw new CantInitializeDatabaseException(z, "", "Unhandled Exception.");
            }

        } catch (final CantOpenDatabaseException e) {

            throw new CantInitializeDatabaseException(e, "", "Exception not handled by the plugin, there was a problem and we cannot open the database.");
        } catch (final Exception e) {

            throw new CantInitializeDatabaseException(e, "", "Unhandled Exception.");
        }
    }

    /**
     * Return all the pending requests depending on the action informed through parameters.
     **
     * @return a list of FanConnectionRequest instances.
     *
     * @throws CantListPendingConnectionRequestsException  if something goes wrong.
     */
    public final List<FanConnectionRequest> listPendingConnectionUpdates() throws CantListPendingConnectionRequestsException {

        try {

            final ProtocolState protocolState = ProtocolState.PENDING_LOCAL_ACTION;

            final DatabaseTable connectionNewsTable = database.getTable(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_TABLE_NAME);

            connectionNewsTable.addFermatEnumFilter(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_STATE_COLUMN_NAME, protocolState, DatabaseFilterType.EQUAL);

            List<ConnectionRequestAction> actions = new ArrayList<>();

            actions.add(ConnectionRequestAction.ACCEPT);
            actions.add(ConnectionRequestAction.DENY);

            final List<DatabaseTableFilter> tableFilters = new ArrayList<>();

            for(final ConnectionRequestAction action : actions)
                tableFilters.add(connectionNewsTable.getNewFilter(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ACTION_COLUMN_NAME, DatabaseFilterType.EQUAL, action.getCode()));

            final DatabaseTableFilterGroup filterGroup = connectionNewsTable.getNewFilterGroup(tableFilters, null, DatabaseFilterOperator.OR);

            connectionNewsTable.setFilterGroup(filterGroup);

            connectionNewsTable.loadToMemory();

            final List<DatabaseTableRecord> records = connectionNewsTable.getRecords();

            final List<FanConnectionRequest> artistConnectionRequests = new ArrayList<>();

            for (final DatabaseTableRecord record : records)
                artistConnectionRequests.add(buildConnectionNewRecord(record));

            return artistConnectionRequests;

        } catch (final CantLoadTableToMemoryException e) {

            throw new CantListPendingConnectionRequestsException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (final InvalidParameterException e) {

            throw new CantListPendingConnectionRequestsException(e, "", "There is a problem with some enum code."                                                                                );
        }
    }

    /**
     * Return all the pending requests depending on the action informed through parameters.
     *
     * @param actions  the list of actions that we need to bring.
     *
     * @return a list of FanConnectionRequest instances.
     *
     * @throws CantListPendingConnectionRequestsException  if something goes wrong.
     */
    public final List<FanConnectionRequest> listAllPendingRequestsByActorType(final PlatformComponentType actorType, final List<ConnectionRequestAction> actions) throws CantListPendingConnectionRequestsException {

        try {

            final ProtocolState protocolState = ProtocolState.PENDING_LOCAL_ACTION;

            final DatabaseTable connectionNewsTable = database.getTable(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_TABLE_NAME);

            connectionNewsTable.addFermatEnumFilter(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_STATE_COLUMN_NAME, protocolState, DatabaseFilterType.EQUAL);
            connectionNewsTable.addFermatEnumFilter(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_SENDER_ACTOR_TYPE_COLUMN_NAME, actorType, DatabaseFilterType.EQUAL);

            final List<DatabaseTableFilter> tableFilters = new ArrayList<>();

            for(final ConnectionRequestAction action : actions)
                tableFilters.add(connectionNewsTable.getNewFilter(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ACTION_COLUMN_NAME, DatabaseFilterType.EQUAL, action.getCode()));

            final DatabaseTableFilterGroup filterGroup = connectionNewsTable.getNewFilterGroup(tableFilters, null, DatabaseFilterOperator.OR);

            connectionNewsTable.setFilterGroup(filterGroup);

            connectionNewsTable.loadToMemory();

            final List<DatabaseTableRecord> records = connectionNewsTable.getRecords();

            final List<FanConnectionRequest> artistConnectionRequests = new ArrayList<>();

            for (final DatabaseTableRecord record : records)
                artistConnectionRequests.add(buildConnectionNewRecord(record));

            return artistConnectionRequests;

        } catch (final CantLoadTableToMemoryException e) {

            throw new CantListPendingConnectionRequestsException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (final InvalidParameterException e) {

            throw new CantListPendingConnectionRequestsException(e, "", "There is a problem with some enum code."                                                                                );
        }
    }

    public final List<FanConnectionRequest> listPendingConnectionNews(final PlatformComponentType actorType) throws CantListPendingConnectionRequestsException {

        try {

            final ProtocolState protocolState = ProtocolState.PENDING_LOCAL_ACTION;

            final ConnectionRequestAction action = ConnectionRequestAction.REQUEST;

            final DatabaseTable connectionNewsTable = database.getTable(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_TABLE_NAME);

            connectionNewsTable.addFermatEnumFilter(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_STATE_COLUMN_NAME, protocolState, DatabaseFilterType.EQUAL);

            if (actorType != null)
                connectionNewsTable.addFermatEnumFilter(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_SENDER_ACTOR_TYPE_COLUMN_NAME, actorType, DatabaseFilterType.EQUAL);

            connectionNewsTable.addFermatEnumFilter(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ACTION_COLUMN_NAME, action, DatabaseFilterType.EQUAL);

            connectionNewsTable.loadToMemory();

            final List<DatabaseTableRecord> records = connectionNewsTable.getRecords();

            final List<FanConnectionRequest> artistConnectionRequests = new ArrayList<>();

            for (final DatabaseTableRecord record : records)
                artistConnectionRequests.add(buildConnectionNewRecord(record));

            return artistConnectionRequests;

        } catch (final CantLoadTableToMemoryException e) {

            throw new CantListPendingConnectionRequestsException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (final InvalidParameterException e) {

            throw new CantListPendingConnectionRequestsException(e, "", "There is a problem with some enum code."                                                                                );
        }
    }


    /**
     * Return all the pending requests depending on the protocol state informed through parameters.
     *
     * @param protocolState  the protocol state that we need to bring.
     *
     * @return a list of FanConnectionRequest instances.
     *
     * @throws CantListPendingConnectionRequestsException  if something goes wrong.
     */
    public final List<FanConnectionRequest> listAllRequestByProtocolState(final ProtocolState protocolState) throws CantListPendingConnectionRequestsException {

        try {

            final DatabaseTable connectionNewsTable = database.getTable(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_TABLE_NAME);

            connectionNewsTable.addFermatEnumFilter(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_STATE_COLUMN_NAME, protocolState, DatabaseFilterType.EQUAL);

            connectionNewsTable.loadToMemory();

            final List<DatabaseTableRecord> records = connectionNewsTable.getRecords();

            final List<FanConnectionRequest> cryptoAddressRequests = new ArrayList<>();

            for (final DatabaseTableRecord record : records)
                cryptoAddressRequests.add(buildConnectionNewRecord(record));

            return cryptoAddressRequests;

        } catch (final CantLoadTableToMemoryException e) {

            throw new CantListPendingConnectionRequestsException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (final InvalidParameterException e) {

            throw new CantListPendingConnectionRequestsException(e, "", "There is a problem with some enum code."                                                                                );
        }
    }

    /**
     * This method returns all the request persisted in database
     * @return
     * @throws CantListPendingConnectionRequestsException
     */
    public final List<FanConnectionRequest> listAllRequest() throws CantListPendingConnectionRequestsException {

        try {

            final DatabaseTable connectionNewsTable = database.getTable(
                    FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_TABLE_NAME);

            connectionNewsTable.loadToMemory();

            final List<DatabaseTableRecord> records = connectionNewsTable.getRecords();

            final List<FanConnectionRequest> cryptoAddressRequests = new ArrayList<>();

            for (final DatabaseTableRecord record : records)
                cryptoAddressRequests.add(buildConnectionNewRecord(record));

            return cryptoAddressRequests;

        } catch (final CantLoadTableToMemoryException e) {

            throw new CantListPendingConnectionRequestsException(
                    e,
                    "",
                    "Exception not handled by the plugin, there is a problem in database and I cannot load the table.");
        } catch (final InvalidParameterException e) {
            throw new CantListPendingConnectionRequestsException(
                    e,
                    "",
                    "There is a problem with some enum code."                                                                                );
        }
    }

    /**
     * Return all the pending requests depending on the protocol state informed through parameters.
     *
     * @param protocolStates  list of the protocol states that we need to bring.
     *
     * @return a list of FanConnectionRequest instances.
     *
     * @throws CantListPendingConnectionRequestsException  if something goes wrong.
     */
    public final List<FanConnectionRequest> listAllRequestByProtocolStates(final List<ProtocolState> protocolStates) throws CantListPendingConnectionRequestsException {

        try {

            final DatabaseTable connectionNewsTable = database.getTable(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_TABLE_NAME);

            final List<DatabaseTableFilter> tableFilters = new ArrayList<>();

            for(final ProtocolState protocolState : protocolStates)
                tableFilters.add(connectionNewsTable.getNewFilter(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_STATE_COLUMN_NAME, DatabaseFilterType.EQUAL, protocolState.getCode()));

            final DatabaseTableFilterGroup filterGroup = connectionNewsTable.getNewFilterGroup(tableFilters, null, DatabaseFilterOperator.OR);

            connectionNewsTable.setFilterGroup(filterGroup);

            connectionNewsTable.loadToMemory();

            final List<DatabaseTableRecord> records = connectionNewsTable.getRecords();

            final List<FanConnectionRequest> artistConnectionRequests = new ArrayList<>();

            for (final DatabaseTableRecord record : records)
                artistConnectionRequests.add(buildConnectionNewRecord(record));

            return artistConnectionRequests;

        } catch (final CantLoadTableToMemoryException e) {

            throw new CantListPendingConnectionRequestsException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (final InvalidParameterException e) {

            throw new CantListPendingConnectionRequestsException(e, "", "There is a problem with some enum code."                                                                                );
        }
    }

    public final void createConnectionRequest(final FanConnectionInformation fanConnectionInformation,
                                              final ProtocolState state,
                                              final RequestType type,
                                              final ConnectionRequestAction action, int sentCount) throws CantRequestConnectionException {

        try {

            final FanConnectionRequest connectionNew = new FanConnectionRequest(
                    fanConnectionInformation.getConnectionId()        ,
                    fanConnectionInformation.getSenderPublicKey()     ,
                    fanConnectionInformation.getSenderActorType()     ,
                    fanConnectionInformation.getSenderAlias()         ,
                    fanConnectionInformation.getSenderImage()         ,
                    fanConnectionInformation.getDestinationPublicKey(),
                    fanConnectionInformation.getDestinationActorType(),
                    type                                       ,
                    state                                      ,
                    action                                     ,
                    sentCount,
                    fanConnectionInformation.getSendingTime()
            );

            final DatabaseTable addressExchangeRequestTable = database.getTable(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_TABLE_NAME);

            DatabaseTableRecord entityRecord = addressExchangeRequestTable.getEmptyRecord();

            entityRecord = buildConnectionNewDatabaseRecord(entityRecord, connectionNew);

            addressExchangeRequestTable.insertRecord(entityRecord);

        } catch (final CantInsertRecordException e) {

            throw new CantRequestConnectionException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.");
        }
    }

    /**
     * Through this method you can save a denial for a connection request.
     * It can be LOCAL or REMOTE.
     * Possible states: PROCESSING_SEND, PROCESSING_RECEIVE.
     *
     * @param requestId id of the connection request.
     * @param state     PROCESSING_SEND, PROCESSING_RECEIVE
     *
     * @throws CantDenyConnectionRequestException    if something goes wrong.
     * @throws ConnectionRequestNotFoundException    if we cannot find the request.
     */
    public void denyConnection(final UUID          requestId,
                               final ProtocolState state    ) throws CantDenyConnectionRequestException,
            ConnectionRequestNotFoundException {

        if (requestId == null)
            throw new CantDenyConnectionRequestException(null, "", "The requestId is required, can not be null");

        if (state == null)
            throw new CantDenyConnectionRequestException(null, "", "The state is required, can not be null");

        try {

            final ConnectionRequestAction action = ConnectionRequestAction.DENY;

            final DatabaseTable connectionNewsTable = database.getTable(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_TABLE_NAME);

            connectionNewsTable.addUUIDFilter(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ID_COLUMN_NAME, requestId, DatabaseFilterType.EQUAL);

            connectionNewsTable.loadToMemory();

            final List<DatabaseTableRecord> records = connectionNewsTable.getRecords();

            if (!records.isEmpty()) {

                final DatabaseTableRecord record = records.get(0);

                record.setFermatEnum(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_STATE_COLUMN_NAME , state );
                record.setFermatEnum(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ACTION_COLUMN_NAME, action);

                connectionNewsTable.updateRecord(record);

            } else
                throw new ConnectionRequestNotFoundException(null, "requestId: "+requestId, "Cannot find an actor connection request with that requestId.");

        } catch (final CantUpdateRecordException e) {

            throw new CantDenyConnectionRequestException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot update the record.");
        } catch (final CantLoadTableToMemoryException e) {

            throw new CantDenyConnectionRequestException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        }
    }

    /**
     * Through this method you can save a denial for a connection request.
     * It can be LOCAL or REMOTE.
     * Possible states: PROCESSING_SEND, PROCESSING_RECEIVE.
     *
     * @param requestId id of the connection request.
     * @param state     PROCESSING_SEND, PROCESSING_RECEIVE
     *
     * @throws CantDisconnectException    if something goes wrong.
     * @throws ConnectionRequestNotFoundException    if we cannot find the request.
     */
    public void disconnectConnection(final UUID          requestId,
                                     final ProtocolState state    ) throws CantDisconnectException,
            ConnectionRequestNotFoundException {

        if (requestId == null)
            throw new CantDisconnectException(null, "", "The requestId is required, can not be null");

        if (state == null)
            throw new CantDisconnectException(null, "", "The state is required, can not be null");

        try {

            final ConnectionRequestAction action = ConnectionRequestAction.DISCONNECT;

            final DatabaseTable connectionNewsTable = database.getTable(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_TABLE_NAME);

            connectionNewsTable.addUUIDFilter(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ID_COLUMN_NAME, requestId, DatabaseFilterType.EQUAL);

            connectionNewsTable.loadToMemory();

            final List<DatabaseTableRecord> records = connectionNewsTable.getRecords();

            if (!records.isEmpty()) {

                final DatabaseTableRecord record = records.get(0);

                record.setFermatEnum(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_STATE_COLUMN_NAME , state );
                record.setFermatEnum(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ACTION_COLUMN_NAME, action);

                connectionNewsTable.updateRecord(record);

            } else
                throw new ConnectionRequestNotFoundException(null, "requestId: "+requestId, "Cannot find an actor connection request with that requestId.");

        } catch (final CantUpdateRecordException e) {

            throw new CantDisconnectException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot update the record.");
        } catch (final CantLoadTableToMemoryException e) {

            throw new CantDisconnectException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        }
    }


    public FanConnectionRequest getConnectionRequest(final UUID requestId) throws CantFindRequestException, ConnectionRequestNotFoundException {

        if (requestId == null)
            throw new CantFindRequestException(null, "", "The requestId is required, can not be null");

        try {

            final DatabaseTable connectionRequestTable = database.getTable(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_TABLE_NAME);

            connectionRequestTable.addUUIDFilter(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ID_COLUMN_NAME, requestId, DatabaseFilterType.EQUAL);

            connectionRequestTable.loadToMemory();

            final List<DatabaseTableRecord> records = connectionRequestTable.getRecords();

            if (!records.isEmpty())
                return buildConnectionNewRecord(records.get(0));
            else
                throw new ConnectionRequestNotFoundException(null, "requestId: "+requestId, "Cannot find an actor Connection request with that requestId.");

        } catch (final CantLoadTableToMemoryException e) {

            throw new CantFindRequestException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (final InvalidParameterException e) {

            throw new CantFindRequestException(e, "", "Exception reading records of the table Cannot recognize the codes of the currencies.");
        }
    }
    public void cancelConnection(final UUID          requestId,
                                 final ProtocolState state    ) throws CantCancelConnectionRequestException,
            ConnectionRequestNotFoundException {

        if (requestId == null)
            throw new CantCancelConnectionRequestException(null, "", "The requestId is required, can not be null");

        if (state == null)
            throw new CantCancelConnectionRequestException(null, "", "The state is required, can not be null");

        try {

            final ConnectionRequestAction action = ConnectionRequestAction.CANCEL;

            final DatabaseTable connectionNewsTable = database.getTable(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_TABLE_NAME);

            connectionNewsTable.addUUIDFilter(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ID_COLUMN_NAME, requestId, DatabaseFilterType.EQUAL);

            connectionNewsTable.loadToMemory();

            final List<DatabaseTableRecord> records = connectionNewsTable.getRecords();

            if (!records.isEmpty()) {

                final DatabaseTableRecord record = records.get(0);

                record.setFermatEnum(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_STATE_COLUMN_NAME , state );
                record.setFermatEnum(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ACTION_COLUMN_NAME, action);

                connectionNewsTable.updateRecord(record);

            } else
                throw new ConnectionRequestNotFoundException(null, "requestId: "+requestId, "Cannot find an actor connection request with that requestId.");

        } catch (final CantUpdateRecordException e) {

            throw new CantCancelConnectionRequestException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot update the record.");
        } catch (final CantLoadTableToMemoryException e) {

            throw new CantCancelConnectionRequestException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        }
    }
    /**
     * change the protocol state
     *
     * @param requestId id of the address exchange request we want to confirm.
     * @param state     protocol state to change
     *
     * @throws CantChangeProtocolStateException      if something goes wrong.
     * @throws ConnectionRequestNotFoundException    if i can't find the record.
     */
    public void changeProtocolState(final UUID          requestId,
                                    final ProtocolState state    ) throws CantChangeProtocolStateException,
            ConnectionRequestNotFoundException  {

        if (requestId == null)
            throw new CantChangeProtocolStateException(null, "", "The requestId is required, can not be null");

        if (state == null)
            throw new CantChangeProtocolStateException(null, "", "The state is required, can not be null");

        try {

            DatabaseTable actorConnectionRequestTable = database.getTable(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_TABLE_NAME);

            actorConnectionRequestTable.addUUIDFilter(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ID_COLUMN_NAME, requestId, DatabaseFilterType.EQUAL);

            actorConnectionRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = actorConnectionRequestTable.getRecords();

            if (!records.isEmpty()) {
                DatabaseTableRecord record = records.get(0);

                record.setStringValue(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_STATE_COLUMN_NAME, state.getCode());

                actorConnectionRequestTable.updateRecord(record);

            } else
                throw new ConnectionRequestNotFoundException(null, "requestId: "+requestId, "Cannot find an actor Connection request with that requestId.");

        } catch (CantUpdateRecordException e) {

            throw new CantChangeProtocolStateException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot update the record.");
        } catch (CantLoadTableToMemoryException e) {

            throw new CantChangeProtocolStateException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");

        }
    }

    /**
     * when i confirm a request i put it in the final state, indicating:
     * State : DONE.
     * Action: NONE.
     *
     * @param requestId id of the address exchange request we want to confirm.
     *
     * @throws CantConfirmConnectionRequestException   if something goes wrong.
     * @throws ConnectionRequestNotFoundException      if i can't find the record.
     */
    public void confirmActorConnectionRequest(final UUID requestId) throws CantConfirmConnectionRequestException,
            ConnectionRequestNotFoundException   {

        if (requestId == null) {
            throw new CantConfirmConnectionRequestException(null, "", "The requestId is required, can not be null");
        }

        try {

            ProtocolState           state  = ProtocolState          .DONE;
            ConnectionRequestAction action = ConnectionRequestAction.NONE;

            DatabaseTable actorConnectionRequestTable = database.getTable(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_TABLE_NAME);

            actorConnectionRequestTable.addUUIDFilter(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ID_COLUMN_NAME, requestId, DatabaseFilterType.EQUAL);

            actorConnectionRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = actorConnectionRequestTable.getRecords();

            if (!records.isEmpty()) {
                DatabaseTableRecord record = records.get(0);

                record.setStringValue(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_STATE_COLUMN_NAME, state.getCode());
                record.setStringValue(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ACTION_COLUMN_NAME, action.getCode());

                actorConnectionRequestTable.updateRecord(record);

            } else
                throw new ConnectionRequestNotFoundException(null, "requestId: "+requestId, "Cannot find an address exchange request with that requestId.");

        } catch (CantUpdateRecordException e) {

            throw new CantConfirmConnectionRequestException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot update the record.");
        } catch (CantLoadTableToMemoryException e) {

            throw new CantConfirmConnectionRequestException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");

        }
    }

    public boolean isPendingConnectionUpdates() throws CantListPendingConnectionRequestsException {

        return this.listPendingConnectionUpdates() != null && !(this.listPendingConnectionUpdates().isEmpty());
    }

    public boolean isPendingConnectionRequests() throws CantListPendingConnectionRequestsException {

        return this.listPendingConnectionNews(null) != null && !(this.listPendingConnectionNews(null).isEmpty());
    }

    /**
     * Through this method you can save an acceptation for a connection request.
     * It can be LOCAL or REMOTE.
     * Possible states: PROCESSING_SEND, PROCESSING_RECEIVE.
     *
     * @param requestId id of the connection request.
     * @param state     PROCESSING_SEND, PROCESSING_RECEIVE
     *
     * @throws CantAcceptConnectionRequestException  if something goes wrong.
     * @throws ConnectionRequestNotFoundException    if we cannot find the request.
     */
    public void acceptConnection(final UUID          requestId,
                                 final ProtocolState state    ) throws CantAcceptConnectionRequestException,
            ConnectionRequestNotFoundException  {

        if (requestId == null)
            throw new CantAcceptConnectionRequestException(null, "", "The requestId is required, can not be null");

        if (state == null)
            throw new CantAcceptConnectionRequestException(null, "", "The state is required, can not be null");

        try {

            final ConnectionRequestAction action = ConnectionRequestAction.ACCEPT;

            final DatabaseTable connectionNewsTable = database.getTable(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_TABLE_NAME);

            connectionNewsTable.addUUIDFilter(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ID_COLUMN_NAME, requestId, DatabaseFilterType.EQUAL);

            connectionNewsTable.loadToMemory();

            final List<DatabaseTableRecord> records = connectionNewsTable.getRecords();

            if (!records.isEmpty()) {

                final DatabaseTableRecord record = records.get(0);

                record.setFermatEnum(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_STATE_COLUMN_NAME, state);
                record.setFermatEnum(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ACTION_COLUMN_NAME, action);

                connectionNewsTable.updateRecord(record);

            } else
                throw new ConnectionRequestNotFoundException(null, "requestId: "+requestId, "Cannot find an actor connection request with that requestId.");

        } catch (final CantUpdateRecordException e) {

            throw new CantAcceptConnectionRequestException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot update the record.");
        } catch (final CantLoadTableToMemoryException e) {

            throw new CantAcceptConnectionRequestException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        }
    }

    public String getDestinationPublicKey(final UUID connectionId) throws CantListPendingConnectionRequestsException  ,
            ConnectionRequestNotFoundException {

        if (connectionId == null)
            throw new CantListPendingConnectionRequestsException("", "The connectionId is required, can not be null");

        try {
            final DatabaseTable actorConnectionsTable = database.getTable(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_TABLE_NAME);

            actorConnectionsTable.addUUIDFilter(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ID_COLUMN_NAME, connectionId, DatabaseFilterType.EQUAL);

            actorConnectionsTable.loadToMemory();

            final List<DatabaseTableRecord> records = actorConnectionsTable.getRecords();

            if (!records.isEmpty()) {

                final DatabaseTableRecord record = records.get(0);

                return record.getStringValue(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_DESTINATION_PUBLIC_KEY_COLUMN_NAME);

            } else
                throw new ConnectionRequestNotFoundException(
                        "connectionId: "+connectionId,
                        "Cannot find an actor connection request with that requestId."
                );

        } catch (final CantLoadTableToMemoryException cantLoadTableToMemoryException) {

            throw new CantListPendingConnectionRequestsException(
                    cantLoadTableToMemoryException,
                    "connectionId: "+connectionId,
                    "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        }
    }


    public boolean existsConnectionRequest(final UUID requestId) throws CantFindRequestException {

        if (requestId == null)
            throw new CantFindRequestException(null, "", "The requestId is required, can not be null");

        try {

            final DatabaseTable connectionNewsTable = database.getTable(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_TABLE_NAME);

            connectionNewsTable.addUUIDFilter(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ID_COLUMN_NAME, requestId, DatabaseFilterType.EQUAL);

            connectionNewsTable.loadToMemory();

            final List<DatabaseTableRecord> records = connectionNewsTable.getRecords();

            return !records.isEmpty();

        } catch (final CantLoadTableToMemoryException e) {

            throw new CantFindRequestException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        }
    }

    public List<FanConnectionRequest> getConnectionRequestByDestinationPublicKey(final String publickey) throws CantFindRequestException, ConnectionRequestNotFoundException {

        if (publickey == null)
            throw new CantFindRequestException(null, "", "The requestId is required, can not be null");

        try {

            final DatabaseTable connectionRequestTable = database.getTable(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_TABLE_NAME);

            connectionRequestTable.addStringFilter(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_DESTINATION_PUBLIC_KEY_COLUMN_NAME, publickey, DatabaseFilterType.EQUAL);

            connectionRequestTable.loadToMemory();

            final List<DatabaseTableRecord> records = connectionRequestTable.getRecords();

            if (!records.isEmpty())
                return Collections.singletonList(buildConnectionNewRecord(records.get(0)));
            else
                throw new ConnectionRequestNotFoundException(null, "publickey: "+publickey, "Cannot find an actor Connection request with that requestId.");

        } catch (final CantLoadTableToMemoryException e) {

            throw new CantFindRequestException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (final InvalidParameterException e) {

            throw new CantFindRequestException(e, "", "Exception reading records of the table Cannot recognize the codes of the currencies.");
        }
    }
    private DatabaseTableRecord buildConnectionNewDatabaseRecord(final DatabaseTableRecord           record       ,
                                                                 final FanConnectionRequest connectionNew) {

        try {

            record.setUUIDValue  (FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ID_COLUMN_NAME            , connectionNew.getRequestId())           ;
            record.setStringValue(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_SENDER_PUBLIC_KEY_COLUMN_NAME, connectionNew.getSenderPublicKey())     ;
            record.setFermatEnum(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_SENDER_ACTOR_TYPE_COLUMN_NAME, connectionNew.getSenderActorType())     ;
            record.setStringValue(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_SENDER_ALIAS_COLUMN_NAME, connectionNew.getSenderAlias())         ;
            record.setStringValue(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_DESTINATION_PUBLIC_KEY_COLUMN_NAME, connectionNew.getDestinationPublicKey());
            record.setFermatEnum(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_DESTINATION_ACTOR_TYPE_COLUMN_NAME, connectionNew.getDestinationActorType())     ;
            record.setFermatEnum (FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_TYPE_COLUMN_NAME          , connectionNew.getRequestType())         ;
            record.setFermatEnum (FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_STATE_COLUMN_NAME         , connectionNew.getProtocolState())       ;
            record.setFermatEnum (FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ACTION_COLUMN_NAME        , connectionNew.getRequestAction())       ;
            record.setIntegerValue(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_SENT_COUNT_COLUMN_NAME, connectionNew.getSentCount());
            record.setLongValue(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_SENT_TIME_COLUMN_NAME, connectionNew.getSentTime())            ;

            if (connectionNew.getSenderImage() != null && connectionNew.getSenderImage().length > 0)
                persistNewUserProfileImage(connectionNew.getSenderPublicKey(), connectionNew.getSenderImage());

            return record;
        } catch (final Exception e) {

            // TODO add better error management, "throws CantBuildDatabaseRecordException".

            System.err.println("error trying to persist image:"+e.getMessage());
            return record;
        }
    }

    private FanConnectionRequest buildConnectionNewRecord(final DatabaseTableRecord record) throws InvalidParameterException {

        try {
            UUID   requestId             = record.getUUIDValue  (FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ID_COLUMN_NAME            );
            String senderPublicKey       = record.getStringValue(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_SENDER_PUBLIC_KEY_COLUMN_NAME);
            String senderActorTypeString = record.getStringValue(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_SENDER_ACTOR_TYPE_COLUMN_NAME     );
            String senderAlias           = record.getStringValue(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_SENDER_ALIAS_COLUMN_NAME          );
            String destinationPublicKey  = record.getStringValue(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_DESTINATION_PUBLIC_KEY_COLUMN_NAME);
            String destinationActorTypeString = record.getStringValue(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_DESTINATION_ACTOR_TYPE_COLUMN_NAME);
            String requestTypeString     = record.getStringValue(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_TYPE_COLUMN_NAME          );
            String protocolStateString   = record.getStringValue(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_STATE_COLUMN_NAME         );
            String requestActionString   = record.getStringValue(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ACTION_COLUMN_NAME        );
            int sentCount                = record.getIntegerValue(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_SENT_COUNT_COLUMN_NAME   );
            Long   sentTime              = record.getLongValue(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_SENT_TIME_COLUMN_NAME);

            PlatformComponentType senderActorType = PlatformComponentType.getByCode(senderActorTypeString);
            PlatformComponentType destinationActorType = PlatformComponentType.getByCode(destinationActorTypeString);
            RequestType requestType = RequestType.getByCode(requestTypeString);
            ProtocolState state = ProtocolState.getByCode(protocolStateString);
            ConnectionRequestAction action = ConnectionRequestAction.getByCode(requestActionString);

            byte[] profileImage;

            try {
                profileImage = getProfileImage(senderPublicKey);
            } catch (FileNotFoundException e) {
                profileImage = new byte[0];
            }

            return new FanConnectionRequest(
                    requestId,
                    senderPublicKey,
                    senderActorType,
                    senderAlias,
                    profileImage,
                    destinationPublicKey,
                    destinationActorType,
                    requestType,
                    state,
                    action,
                    sentCount,
                    sentTime
            );

        } catch (final CantGetProfileImageException e) {

            throw new InvalidParameterException(
                    e,
                    "",
                    "Problem trying to get the profile image."
            );
        }
    }

    private void persistNewUserProfileImage(final String publicKey   ,
                                            final byte[] profileImage) throws CantPersistProfileImageException {

        try {

            PluginBinaryFile file = this.pluginFileSystem.createBinaryFile(pluginId,
                    PROFILE_IMAGE_DIRECTORY_NAME,
                    buildProfileImageFileName(publicKey),
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );

            file.setContent(profileImage);
            file.persistToMedia();

        } catch (final CantPersistFileException e) {

            throw new CantPersistProfileImageException(
                    e,
                    "Error persist file.",
                    null
            );

        } catch (final CantCreateFileException e) {

            throw new CantPersistProfileImageException(
                    e,
                    "Error creating file.",
                    null
            );
        } catch (final Exception e) {

            throw new CantPersistProfileImageException(
                    e,
                    "",
                    "Unhandled Exception."
            );
        }
    }


    private byte[] getProfileImage(final String publicKey) throws CantGetProfileImageException,
            FileNotFoundException       {

        try {

            PluginBinaryFile file = this.pluginFileSystem.getBinaryFile(pluginId,
                    PROFILE_IMAGE_DIRECTORY_NAME,
                    buildProfileImageFileName(publicKey),
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );

            file.loadFromMedia();

            return file.getContent();

        } catch (final CantLoadFileException e) {

            throw new CantGetProfileImageException(
                    e,
                    "Error loaded file.",
                    null
            );

        } catch (final FileNotFoundException | CantCreateFileException e) {

            throw new FileNotFoundException(e, "", null);
        } catch (Exception e) {

            throw new CantGetProfileImageException(
                    e,
                    "",
                    "Unhandled Exception"
            );
        }
    }

    public final void updateConnectionRequest(FanConnectionRequest fanConnectionRequest){
        if (fanConnectionRequest == null) {
            throw new IllegalArgumentException("The entity is required, can not be null");
        }

        try {


            DatabaseTable databaseTable = database.getTable(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_TABLE_NAME);
            DatabaseTableRecord emptyRecord =  database.getTable(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_TABLE_NAME).getEmptyRecord();
            /*
             * 1- Create the record to the entity
             */
            DatabaseTableRecord entityRecord = buildConnectionNewDatabaseRecord(emptyRecord, fanConnectionRequest);

            /**
             * 2.- Create a new transaction and execute
             */
            DatabaseTransaction transaction = database.newTransaction();

            //set filter

            databaseTable.addUUIDFilter(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ID_COLUMN_NAME, fanConnectionRequest.getRequestId(), DatabaseFilterType.EQUAL);

            transaction.addRecordToUpdate(databaseTable, entityRecord);
            database.executeTransaction(transaction);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void delete(UUID notificationId) throws CantDeleteRecordException {

        try {

            DatabaseTable table = database.getTable(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_TABLE_NAME);
            table.addUUIDFilter(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ID_COLUMN_NAME, notificationId, DatabaseFilterType.EQUAL);

            table.loadToMemory();

            List<DatabaseTableRecord> records = table.getRecords();


            for (DatabaseTableRecord record : records) {
                table.deleteRecord(record);
            }


        } catch (CantDeleteRecordException e) {

            throw new CantDeleteRecordException(CantDeleteRecordException.DEFAULT_MESSAGE,e, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.","");
        } catch(CantLoadTableToMemoryException exception){

            throw new CantDeleteRecordException(CantDeleteRecordException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "Exception invalidParameterException.","");
        }

    }

    private String buildProfileImageFileName(final String publicKey) {
        return PROFILE_IMAGE_FILE_NAME_PREFIX + "_" + publicKey;
    }

}
