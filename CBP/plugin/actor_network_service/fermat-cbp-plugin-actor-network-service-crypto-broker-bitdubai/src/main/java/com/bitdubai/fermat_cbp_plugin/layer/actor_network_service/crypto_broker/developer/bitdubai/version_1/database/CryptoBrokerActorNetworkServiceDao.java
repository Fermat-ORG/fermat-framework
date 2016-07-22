package com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
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
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_api.layer.world.interfaces.CurrencyHelper;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.enums.ConnectionRequestAction;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.enums.ProtocolState;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.enums.RequestType;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantAcceptConnectionRequestException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantAnswerQuotesRequestException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantDenyConnectionRequestException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantDisconnectException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantListPendingConnectionRequestsException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantListPendingQuotesRequestsException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantRequestConnectionException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantRequestQuotesException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.ConnectionRequestNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.QuotesRequestNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces.CryptoBrokerExtraData;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.utils.CryptoBrokerConnectionInformation;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.utils.CryptoBrokerConnectionRequest;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.utils.CryptoBrokerQuote;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.exceptions.CantChangeProtocolStateException;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.exceptions.CantConfirmConnectionRequestException;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.exceptions.CantConfirmQuotesRequestException;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.exceptions.CantFindRequestException;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.exceptions.CantGetProfileImageException;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.exceptions.CantInitializeDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.exceptions.CantPersistProfileImageException;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.structure.CryptoBrokerActorNetworkServiceQuotesRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The class <code>com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.database.CryptoBrokerActorNetworkServiceDao</code>
 * contains all the methods that interact with the database.
 * Manages the actor connection requests by storing them on a Database Table.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/11/2015.
 *
 * @version 1.0
 */
public final class CryptoBrokerActorNetworkServiceDao {

    private static final String PROFILE_IMAGE_DIRECTORY_NAME = new StringBuilder().append(DeviceDirectory.LOCAL_USERS.getName()).append("/CBP/cryptoBrokerActorNS").toString();
    private static final String PROFILE_IMAGE_FILE_NAME_PREFIX = "profileImage";

    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final PluginFileSystem pluginFileSystem;
    private final UUID pluginId;

    private Database database;

    public CryptoBrokerActorNetworkServiceDao(final PluginDatabaseSystem pluginDatabaseSystem,
                                              final PluginFileSystem pluginFileSystem,
                                              final UUID pluginId) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
    }

    public void initialize() throws CantInitializeDatabaseException {

        try {

            database = this.pluginDatabaseSystem.openDatabase(
                    this.pluginId,
                    CryptoBrokerActorNetworkServiceDatabaseConstants.CRYPTO_BROKER_ACTOR_NETWORK_SERVICE_DATABASE_NAME
            );

        } catch (final DatabaseNotFoundException e) {

            try {

                CryptoBrokerActorNetworkServiceDatabaseFactory cryptoBrokerActorNetworkServiceDatabaseFactory = new CryptoBrokerActorNetworkServiceDatabaseFactory(pluginDatabaseSystem);
                database = cryptoBrokerActorNetworkServiceDatabaseFactory.createDatabase(
                        pluginId,
                        CryptoBrokerActorNetworkServiceDatabaseConstants.CRYPTO_BROKER_ACTOR_NETWORK_SERVICE_DATABASE_NAME
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
     * *
     *
     * @return a list of CryptoBrokerConnectionRequest instances.
     * @throws CantListPendingConnectionRequestsException if something goes wrong.
     */
    public final List<CryptoBrokerConnectionRequest> listPendingConnectionUpdates() throws CantListPendingConnectionRequestsException {

        try {

            final ProtocolState protocolState = ProtocolState.PENDING_LOCAL_ACTION;

            final DatabaseTable connectionNewsTable = database.getTable(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_TABLE_NAME);

            connectionNewsTable.addFermatEnumFilter(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_STATE_COLUMN_NAME, protocolState, DatabaseFilterType.EQUAL);

            List<ConnectionRequestAction> actions = new ArrayList<>();

            actions.add(ConnectionRequestAction.ACCEPT);
            actions.add(ConnectionRequestAction.DENY);

            final List<DatabaseTableFilter> tableFilters = new ArrayList<>();

            for (final ConnectionRequestAction action : actions)
                tableFilters.add(connectionNewsTable.getNewFilter(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ACTION_COLUMN_NAME, DatabaseFilterType.EQUAL, action.getCode()));

            final DatabaseTableFilterGroup filterGroup = connectionNewsTable.getNewFilterGroup(tableFilters, null, DatabaseFilterOperator.OR);

            connectionNewsTable.setFilterGroup(filterGroup);

            connectionNewsTable.loadToMemory();

            final List<DatabaseTableRecord> records = connectionNewsTable.getRecords();

            final List<CryptoBrokerConnectionRequest> cryptoAddressRequests = new ArrayList<>();

            for (final DatabaseTableRecord record : records)
                cryptoAddressRequests.add(buildConnectionNewRecord(record));

            return cryptoAddressRequests;

        } catch (final CantLoadTableToMemoryException e) {

            throw new CantListPendingConnectionRequestsException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (final InvalidParameterException e) {

            throw new CantListPendingConnectionRequestsException(e, "", "There is a problem with some enum code.");
        }
    }

    /**
     * Return all the pending requests depending on the action informed through parameters.
     *
     * @param actions the list of actions that we need to bring.
     * @return a list of CryptoBrokerConnectionRequest instances.
     * @throws CantListPendingConnectionRequestsException if something goes wrong.
     */
    public final List<CryptoBrokerConnectionRequest> listAllPendingRequestsByActorType(final Actors actorType, final List<ConnectionRequestAction> actions) throws CantListPendingConnectionRequestsException {

        try {

            final ProtocolState protocolState = ProtocolState.PENDING_LOCAL_ACTION;

            final DatabaseTable connectionNewsTable = database.getTable(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_TABLE_NAME);

            connectionNewsTable.addFermatEnumFilter(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_STATE_COLUMN_NAME, protocolState, DatabaseFilterType.EQUAL);
            connectionNewsTable.addFermatEnumFilter(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_SENDER_ACTOR_TYPE_COLUMN_NAME, actorType, DatabaseFilterType.EQUAL);

            final List<DatabaseTableFilter> tableFilters = new ArrayList<>();

            for (final ConnectionRequestAction action : actions)
                tableFilters.add(connectionNewsTable.getNewFilter(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ACTION_COLUMN_NAME, DatabaseFilterType.EQUAL, action.getCode()));

            final DatabaseTableFilterGroup filterGroup = connectionNewsTable.getNewFilterGroup(tableFilters, null, DatabaseFilterOperator.OR);

            connectionNewsTable.setFilterGroup(filterGroup);

            connectionNewsTable.loadToMemory();

            final List<DatabaseTableRecord> records = connectionNewsTable.getRecords();

            final List<CryptoBrokerConnectionRequest> cryptoAddressRequests = new ArrayList<>();

            for (final DatabaseTableRecord record : records)
                cryptoAddressRequests.add(buildConnectionNewRecord(record));

            return cryptoAddressRequests;

        } catch (final CantLoadTableToMemoryException e) {

            throw new CantListPendingConnectionRequestsException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (final InvalidParameterException e) {

            throw new CantListPendingConnectionRequestsException(e, "", "There is a problem with some enum code.");
        }
    }

    public final List<CryptoBrokerConnectionRequest> listPendingConnectionNews(final Actors actorType) throws CantListPendingConnectionRequestsException {

        try {

            final ProtocolState protocolState = ProtocolState.PENDING_LOCAL_ACTION;

            final ConnectionRequestAction action = ConnectionRequestAction.REQUEST;

            final DatabaseTable connectionNewsTable = database.getTable(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_TABLE_NAME);

            connectionNewsTable.addFermatEnumFilter(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_STATE_COLUMN_NAME, protocolState, DatabaseFilterType.EQUAL);

            if (actorType != null)
                connectionNewsTable.addFermatEnumFilter(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_SENDER_ACTOR_TYPE_COLUMN_NAME, actorType, DatabaseFilterType.EQUAL);

            connectionNewsTable.addFermatEnumFilter(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ACTION_COLUMN_NAME, action, DatabaseFilterType.EQUAL);

            connectionNewsTable.loadToMemory();

            final List<DatabaseTableRecord> records = connectionNewsTable.getRecords();

            final List<CryptoBrokerConnectionRequest> cryptoAddressRequests = new ArrayList<>();

            for (final DatabaseTableRecord record : records)
                cryptoAddressRequests.add(buildConnectionNewRecord(record));

            return cryptoAddressRequests;

        } catch (final CantLoadTableToMemoryException e) {

            throw new CantListPendingConnectionRequestsException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (final InvalidParameterException e) {

            throw new CantListPendingConnectionRequestsException(e, "", "There is a problem with some enum code.");
        }
    }


    /**
     * Return all the pending requests depending on the protocol state informed through parameters.
     *
     * @param protocolState the protocol state that we need to bring.
     * @return a list of CryptoBrokerConnectionRequest instances.
     * @throws CantListPendingConnectionRequestsException if something goes wrong.
     */
    public final List<CryptoBrokerConnectionRequest> listAllRequestByProtocolState(final ProtocolState protocolState) throws CantListPendingConnectionRequestsException {

        try {

            final DatabaseTable connectionNewsTable = database.getTable(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_TABLE_NAME);

            connectionNewsTable.addFermatEnumFilter(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_STATE_COLUMN_NAME, protocolState, DatabaseFilterType.EQUAL);

            connectionNewsTable.loadToMemory();

            final List<DatabaseTableRecord> records = connectionNewsTable.getRecords();

            final List<CryptoBrokerConnectionRequest> cryptoAddressRequests = new ArrayList<>();

            for (final DatabaseTableRecord record : records)
                cryptoAddressRequests.add(buildConnectionNewRecord(record));

            return cryptoAddressRequests;

        } catch (final CantLoadTableToMemoryException e) {

            throw new CantListPendingConnectionRequestsException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (final InvalidParameterException e) {

            throw new CantListPendingConnectionRequestsException(e, "", "There is a problem with some enum code.");
        }
    }

    /**
     * Return all the pending requests depending on the protocol state informed through parameters.
     *
     * @param protocolStates list of the protocol states that we need to bring.
     * @return a list of CryptoBrokerConnectionRequest instances.
     * @throws CantListPendingConnectionRequestsException if something goes wrong.
     */
    public final List<CryptoBrokerConnectionRequest> listAllRequestByProtocolStates(final List<ProtocolState> protocolStates) throws CantListPendingConnectionRequestsException {

        try {

            final DatabaseTable connectionNewsTable = database.getTable(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_TABLE_NAME);

            final List<DatabaseTableFilter> tableFilters = new ArrayList<>();

            for (final ProtocolState protocolState : protocolStates)
                tableFilters.add(connectionNewsTable.getNewFilter(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_STATE_COLUMN_NAME, DatabaseFilterType.EQUAL, protocolState.getCode()));

            final DatabaseTableFilterGroup filterGroup = connectionNewsTable.getNewFilterGroup(tableFilters, null, DatabaseFilterOperator.OR);

            connectionNewsTable.setFilterGroup(filterGroup);

            connectionNewsTable.loadToMemory();

            final List<DatabaseTableRecord> records = connectionNewsTable.getRecords();

            final List<CryptoBrokerConnectionRequest> cryptoAddressRequests = new ArrayList<>();

            for (final DatabaseTableRecord record : records)
                cryptoAddressRequests.add(buildConnectionNewRecord(record));

            return cryptoAddressRequests;

        } catch (final CantLoadTableToMemoryException e) {

            throw new CantListPendingConnectionRequestsException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (final InvalidParameterException e) {

            throw new CantListPendingConnectionRequestsException(e, "", "There is a problem with some enum code.");
        }
    }

    public final void createConnectionRequest(final CryptoBrokerConnectionInformation brokerInformation,
                                              final ProtocolState state,
                                              final RequestType type,
                                              final ConnectionRequestAction action) throws CantRequestConnectionException {

        try {

            final CryptoBrokerConnectionRequest connectionNew = new CryptoBrokerConnectionRequest(
                    brokerInformation.getConnectionId(),
                    brokerInformation.getSenderPublicKey(),
                    brokerInformation.getSenderActorType(),
                    brokerInformation.getSenderAlias(),
                    brokerInformation.getSenderImage(),
                    brokerInformation.getDestinationPublicKey(),
                    type,
                    state,
                    action,
                    brokerInformation.getSendingTime()
            );

            final DatabaseTable addressExchangeRequestTable = database.getTable(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_TABLE_NAME);

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
     * @throws CantDenyConnectionRequestException if something goes wrong.
     * @throws ConnectionRequestNotFoundException if we cannot find the request.
     */
    public void denyConnection(final UUID requestId,
                               final ProtocolState state) throws CantDenyConnectionRequestException,
            ConnectionRequestNotFoundException {

        if (requestId == null)
            throw new CantDenyConnectionRequestException(null, "", "The requestId is required, can not be null");

        if (state == null)
            throw new CantDenyConnectionRequestException(null, "", "The state is required, can not be null");

        try {

            final ConnectionRequestAction action = ConnectionRequestAction.DENY;

            final DatabaseTable connectionNewsTable = database.getTable(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_TABLE_NAME);

            connectionNewsTable.addUUIDFilter(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ID_COLUMN_NAME, requestId, DatabaseFilterType.EQUAL);

            connectionNewsTable.loadToMemory();

            final List<DatabaseTableRecord> records = connectionNewsTable.getRecords();

            if (!records.isEmpty()) {

                final DatabaseTableRecord record = records.get(0);

                record.setFermatEnum(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_STATE_COLUMN_NAME, state);
                record.setFermatEnum(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ACTION_COLUMN_NAME, action);

                connectionNewsTable.updateRecord(record);

            } else
                throw new ConnectionRequestNotFoundException(null, new StringBuilder().append("requestId: ").append(requestId).toString(), "Cannot find an actor connection request with that requestId.");

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
     * @throws CantDisconnectException            if something goes wrong.
     * @throws ConnectionRequestNotFoundException if we cannot find the request.
     */
    public void disconnectConnection(final UUID requestId,
                                     final ProtocolState state) throws CantDisconnectException,
            ConnectionRequestNotFoundException {

        if (requestId == null)
            throw new CantDisconnectException(null, "", "The requestId is required, can not be null");

        if (state == null)
            throw new CantDisconnectException(null, "", "The state is required, can not be null");

        try {

            final ConnectionRequestAction action = ConnectionRequestAction.DISCONNECT;

            final DatabaseTable connectionNewsTable = database.getTable(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_TABLE_NAME);

            connectionNewsTable.addUUIDFilter(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ID_COLUMN_NAME, requestId, DatabaseFilterType.EQUAL);

            connectionNewsTable.loadToMemory();

            final List<DatabaseTableRecord> records = connectionNewsTable.getRecords();

            if (!records.isEmpty()) {

                final DatabaseTableRecord record = records.get(0);

                record.setFermatEnum(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_STATE_COLUMN_NAME, state);
                record.setFermatEnum(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ACTION_COLUMN_NAME, action);

                connectionNewsTable.updateRecord(record);

            } else
                throw new ConnectionRequestNotFoundException(null, new StringBuilder().append("requestId: ").append(requestId).toString(), "Cannot find an actor connection request with that requestId.");

        } catch (final CantUpdateRecordException e) {

            throw new CantDisconnectException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot update the record.");
        } catch (final CantLoadTableToMemoryException e) {

            throw new CantDisconnectException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        }
    }


    public CryptoBrokerConnectionRequest getConnectionRequest(final UUID requestId) throws CantFindRequestException, ConnectionRequestNotFoundException {

        if (requestId == null)
            throw new CantFindRequestException(null, "", "The requestId is required, can not be null");

        try {

            final DatabaseTable connectionRequestTable = database.getTable(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_TABLE_NAME);

            connectionRequestTable.addUUIDFilter(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ID_COLUMN_NAME, requestId, DatabaseFilterType.EQUAL);

            connectionRequestTable.loadToMemory();

            final List<DatabaseTableRecord> records = connectionRequestTable.getRecords();

            if (!records.isEmpty())
                return buildConnectionNewRecord(records.get(0));
            else
                throw new ConnectionRequestNotFoundException(null, new StringBuilder().append("requestId: ").append(requestId).toString(), "Cannot find an actor Connection request with that requestId.");

        } catch (final CantLoadTableToMemoryException e) {

            throw new CantFindRequestException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (final InvalidParameterException e) {

            throw new CantFindRequestException(e, "", "Exception reading records of the table Cannot recognize the codes of the currencies.");
        }
    }

    /**
     * change the protocol state
     *
     * @param requestId id of the address exchange request we want to confirm.
     * @param state     protocol state to change
     * @throws CantChangeProtocolStateException   if something goes wrong.
     * @throws ConnectionRequestNotFoundException if i can't find the record.
     */
    public void changeProtocolState(final UUID requestId,
                                    final ProtocolState state) throws CantChangeProtocolStateException,
            ConnectionRequestNotFoundException {

        if (requestId == null)
            throw new CantChangeProtocolStateException(null, "", "The requestId is required, can not be null");

        if (state == null)
            throw new CantChangeProtocolStateException(null, "", "The state is required, can not be null");

        try {

            DatabaseTable actorConnectionRequestTable = database.getTable(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_TABLE_NAME);

            actorConnectionRequestTable.addUUIDFilter(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ID_COLUMN_NAME, requestId, DatabaseFilterType.EQUAL);

            actorConnectionRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = actorConnectionRequestTable.getRecords();

            if (!records.isEmpty()) {
                DatabaseTableRecord record = records.get(0);

                record.setStringValue(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_STATE_COLUMN_NAME, state.getCode());

                actorConnectionRequestTable.updateRecord(record);

            } else
                throw new ConnectionRequestNotFoundException(null, new StringBuilder().append("requestId: ").append(requestId).toString(), "Cannot find an actor Connection request with that requestId.");

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
     * @throws CantConfirmConnectionRequestException if something goes wrong.
     * @throws ConnectionRequestNotFoundException    if i can't find the record.
     */
    public void confirmActorConnectionRequest(final UUID requestId) throws CantConfirmConnectionRequestException,
            ConnectionRequestNotFoundException {

        if (requestId == null) {
            throw new CantConfirmConnectionRequestException(null, "", "The requestId is required, can not be null");
        }

        try {

            ProtocolState state = ProtocolState.DONE;
            ConnectionRequestAction action = ConnectionRequestAction.NONE;

            DatabaseTable actorConnectionRequestTable = database.getTable(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_TABLE_NAME);

            actorConnectionRequestTable.addUUIDFilter(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ID_COLUMN_NAME, requestId, DatabaseFilterType.EQUAL);

            actorConnectionRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = actorConnectionRequestTable.getRecords();

            if (!records.isEmpty()) {
                DatabaseTableRecord record = records.get(0);

                record.setStringValue(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_STATE_COLUMN_NAME, state.getCode());
                record.setStringValue(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ACTION_COLUMN_NAME, action.getCode());

                actorConnectionRequestTable.updateRecord(record);

            } else
                throw new ConnectionRequestNotFoundException(null, new StringBuilder().append("requestId: ").append(requestId).toString(), "Cannot find an address exchange request with that requestId.");

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
     * @throws CantAcceptConnectionRequestException if something goes wrong.
     * @throws ConnectionRequestNotFoundException   if we cannot find the request.
     */
    public void acceptConnection(final UUID requestId,
                                 final ProtocolState state) throws CantAcceptConnectionRequestException,
            ConnectionRequestNotFoundException {

        if (requestId == null)
            throw new CantAcceptConnectionRequestException(null, "", "The requestId is required, can not be null");

        if (state == null)
            throw new CantAcceptConnectionRequestException(null, "", "The state is required, can not be null");

        try {

            final ConnectionRequestAction action = ConnectionRequestAction.ACCEPT;

            final DatabaseTable connectionNewsTable = database.getTable(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_TABLE_NAME);

            connectionNewsTable.addUUIDFilter(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ID_COLUMN_NAME, requestId, DatabaseFilterType.EQUAL);

            connectionNewsTable.loadToMemory();

            final List<DatabaseTableRecord> records = connectionNewsTable.getRecords();

            if (!records.isEmpty()) {

                final DatabaseTableRecord record = records.get(0);

                record.setFermatEnum(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_STATE_COLUMN_NAME, state);
                record.setFermatEnum(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ACTION_COLUMN_NAME, action);

                connectionNewsTable.updateRecord(record);

            } else
                throw new ConnectionRequestNotFoundException(null, new StringBuilder().append("requestId: ").append(requestId).toString(), "Cannot find an actor connection request with that requestId.");

        } catch (final CantUpdateRecordException e) {

            throw new CantAcceptConnectionRequestException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot update the record.");
        } catch (final CantLoadTableToMemoryException e) {

            throw new CantAcceptConnectionRequestException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        }
    }

    public String getDestinationPublicKey(final UUID connectionId) throws CantListPendingConnectionRequestsException,
            ConnectionRequestNotFoundException {

        if (connectionId == null)
            throw new CantListPendingConnectionRequestsException("", "The connectionId is required, can not be null");

        try {
            final DatabaseTable actorConnectionsTable = database.getTable(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_TABLE_NAME);

            actorConnectionsTable.addUUIDFilter(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ID_COLUMN_NAME, connectionId, DatabaseFilterType.EQUAL);

            actorConnectionsTable.loadToMemory();

            final List<DatabaseTableRecord> records = actorConnectionsTable.getRecords();

            if (!records.isEmpty()) {

                final DatabaseTableRecord record = records.get(0);

                return record.getStringValue(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_DESTINATION_PUBLIC_KEY_COLUMN_NAME);

            } else
                throw new ConnectionRequestNotFoundException(
                        new StringBuilder().append("connectionId: ").append(connectionId).toString(),
                        "Cannot find an actor connection request with that requestId."
                );

        } catch (final CantLoadTableToMemoryException cantLoadTableToMemoryException) {

            throw new CantListPendingConnectionRequestsException(
                    cantLoadTableToMemoryException,
                    new StringBuilder().append("connectionId: ").append(connectionId).toString(),
                    "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        }
    }


    public boolean existsConnectionRequest(final UUID requestId) throws CantFindRequestException {

        if (requestId == null)
            throw new CantFindRequestException(null, "", "The requestId is required, can not be null");

        try {

            final DatabaseTable connectionNewsTable = database.getTable(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_TABLE_NAME);

            connectionNewsTable.addUUIDFilter(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ID_COLUMN_NAME, requestId, DatabaseFilterType.EQUAL);

            connectionNewsTable.loadToMemory();

            final List<DatabaseTableRecord> records = connectionNewsTable.getRecords();

            return !records.isEmpty();

        } catch (final CantLoadTableToMemoryException e) {

            throw new CantFindRequestException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        }
    }

    public CryptoBrokerActorNetworkServiceQuotesRequest getQuotesRequest(final UUID requestId) throws CantFindRequestException, QuotesRequestNotFoundException {

        if (requestId == null)
            throw new CantFindRequestException(null, "", "The requestId is required, can not be null");

        try {

            final DatabaseTable quotesRequestTable = database.getTable(CryptoBrokerActorNetworkServiceDatabaseConstants.QUOTES_REQUEST_TABLE_NAME);

            quotesRequestTable.addUUIDFilter(CryptoBrokerActorNetworkServiceDatabaseConstants.QUOTES_REQUEST_REQUEST_ID_COLUMN_NAME, requestId, DatabaseFilterType.EQUAL);

            quotesRequestTable.loadToMemory();

            final List<DatabaseTableRecord> records = quotesRequestTable.getRecords();

            if (!records.isEmpty())
                return buildQuotesRequestObject(records.get(0));
            else
                throw new QuotesRequestNotFoundException(null, "", "Cannot find a quotes request with that id.");

        } catch (final CantLoadTableToMemoryException e) {

            throw new CantFindRequestException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (CantListPendingQuotesRequestsException e) {

            throw new CantFindRequestException(e, "", "Exception in dao trying to list the pending request.");
        } catch (final InvalidParameterException e) {

            throw new CantFindRequestException(e, "", "Exception reading records of the table Cannot recognize the codes of the currencies.");
        }
    }

    public final CryptoBrokerActorNetworkServiceQuotesRequest createQuotesRequest(final UUID requestId,
                                                                                  final String requesterPublicKey,
                                                                                  final Actors requesterActorType,
                                                                                  final String cryptoBrokerPublicKey,
                                                                                  final ProtocolState state,
                                                                                  final RequestType type) throws CantRequestQuotesException {

        try {

            final DatabaseTable quotesRequestTable = database.getTable(CryptoBrokerActorNetworkServiceDatabaseConstants.QUOTES_REQUEST_TABLE_NAME);

            final DatabaseTableRecord quotesRequestRecord = quotesRequestTable.getEmptyRecord();

            final CryptoBrokerActorNetworkServiceQuotesRequest quotesRequest = new CryptoBrokerActorNetworkServiceQuotesRequest(
                    requestId,
                    requesterPublicKey,
                    requesterActorType,
                    cryptoBrokerPublicKey,
                    0,
                    new ArrayList<CryptoBrokerQuote>(),
                    type,
                    state
            );

            quotesRequestRecord.setUUIDValue(CryptoBrokerActorNetworkServiceDatabaseConstants.QUOTES_REQUEST_REQUEST_ID_COLUMN_NAME, quotesRequest.getRequestId());
            quotesRequestRecord.setStringValue(CryptoBrokerActorNetworkServiceDatabaseConstants.QUOTES_REQUEST_REQUESTER_PUBLIC_KEY_COLUMN_NAME, quotesRequest.getRequesterPublicKey());
            quotesRequestRecord.setFermatEnum(CryptoBrokerActorNetworkServiceDatabaseConstants.QUOTES_REQUEST_REQUESTER_ACTOR_TYPE_COLUMN_NAME, quotesRequest.getRequesterActorType());
            quotesRequestRecord.setStringValue(CryptoBrokerActorNetworkServiceDatabaseConstants.QUOTES_REQUEST_CRYPTO_BROKER_PUBLIC_KEY_COLUMN_NAME, quotesRequest.getCryptoBrokerPublicKey());
            quotesRequestRecord.setLongValue(CryptoBrokerActorNetworkServiceDatabaseConstants.QUOTES_REQUEST_UPDATE_TIME_COLUMN_NAME, quotesRequest.getUpdateTime());
            quotesRequestRecord.setFermatEnum(CryptoBrokerActorNetworkServiceDatabaseConstants.QUOTES_REQUEST_TYPE_COLUMN_NAME, quotesRequest.getType());
            quotesRequestRecord.setFermatEnum(CryptoBrokerActorNetworkServiceDatabaseConstants.QUOTES_REQUEST_STATE_COLUMN_NAME, quotesRequest.getState());

            quotesRequestTable.insertRecord(quotesRequestRecord);

            return quotesRequest;

        } catch (final CantInsertRecordException e) {

            throw new CantRequestQuotesException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot insert all the records.");
        }
    }

    public final void answerQuotesRequest(final UUID requestId,
                                          final long updateTime,
                                          final List<CryptoBrokerQuote> quotes,
                                          final ProtocolState state) throws CantAnswerQuotesRequestException, QuotesRequestNotFoundException {

        try {

            final DatabaseTable quotesRequestTable = database.getTable(CryptoBrokerActorNetworkServiceDatabaseConstants.QUOTES_REQUEST_TABLE_NAME);

            quotesRequestTable.addUUIDFilter(CryptoBrokerActorNetworkServiceDatabaseConstants.QUOTES_REQUEST_ID_COLUMN_NAME, requestId, DatabaseFilterType.EQUAL);

            quotesRequestTable.loadToMemory();

            final List<DatabaseTableRecord> records = quotesRequestTable.getRecords();

            DatabaseTableRecord quotesRequestRecord;

            if (!records.isEmpty()) {

                quotesRequestRecord = records.get(0);

                quotesRequestRecord.setFermatEnum(CryptoBrokerActorNetworkServiceDatabaseConstants.QUOTES_REQUEST_STATE_COLUMN_NAME, state);
                quotesRequestRecord.setLongValue(CryptoBrokerActorNetworkServiceDatabaseConstants.QUOTES_REQUEST_UPDATE_TIME_COLUMN_NAME, updateTime);

            } else
                throw new QuotesRequestNotFoundException(null, "", "Cannot find a quotes request with that id.");

            DatabaseTransaction databaseTransaction = database.newTransaction();

            databaseTransaction.addRecordToUpdate(quotesRequestTable, quotesRequestRecord);

            for (final CryptoBrokerQuote quote : quotes) {

                final DatabaseTable quotesTable = database.getTable(CryptoBrokerActorNetworkServiceDatabaseConstants.QUOTES_TABLE_NAME);

                final DatabaseTableRecord quotesRecord = quotesTable.getEmptyRecord();

                quotesRecord.setUUIDValue(CryptoBrokerActorNetworkServiceDatabaseConstants.QUOTES_REQUEST_ID_COLUMN_NAME, requestId);
                quotesRecord.setFermatEnum(CryptoBrokerActorNetworkServiceDatabaseConstants.QUOTES_MERCHANDISE_COLUMN_NAME, quote.getMerchandise());
                quotesRecord.setFermatEnum(CryptoBrokerActorNetworkServiceDatabaseConstants.QUOTES_MERCHANDISE_TYPE_COLUMN_NAME, quote.getMerchandise().getType());
                quotesRecord.setFermatEnum(CryptoBrokerActorNetworkServiceDatabaseConstants.QUOTES_PAYMENT_CURRENCY_COLUMN_NAME, quote.getPaymentCurrency());
                quotesRecord.setFermatEnum(CryptoBrokerActorNetworkServiceDatabaseConstants.QUOTES_PAYMENT_CURRENCY_TYPE_COLUMN_NAME, quote.getPaymentCurrency().getType());
                quotesRecord.setFloatValue(CryptoBrokerActorNetworkServiceDatabaseConstants.QUOTES_PRICE_COLUMN_NAME, quote.getPrice());
                quotesRecord.setStringValue(CryptoBrokerActorNetworkServiceDatabaseConstants.QUOTES_SUPPORTED_PLATFORMS_COLUMN_NAME, quote.getSupportedPlatforms());

                databaseTransaction.addRecordToInsert(quotesTable, quotesRecord);
            }

            database.executeTransaction(databaseTransaction);

        } catch (final CantLoadTableToMemoryException e) {

            throw new CantAnswerQuotesRequestException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (final DatabaseTransactionFailedException e) {

            throw new CantAnswerQuotesRequestException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot insert all the records.");
        }
    }

    public final List<CryptoBrokerExtraData<CryptoBrokerQuote>> listPendingQuotesRequests(final ProtocolState protocolState, final RequestType requestType) throws CantListPendingQuotesRequestsException {

        try {

            final DatabaseTable connectionNewsTable = database.getTable(CryptoBrokerActorNetworkServiceDatabaseConstants.QUOTES_REQUEST_TABLE_NAME);

            connectionNewsTable.addFermatEnumFilter(CryptoBrokerActorNetworkServiceDatabaseConstants.QUOTES_REQUEST_TYPE_COLUMN_NAME, requestType, DatabaseFilterType.EQUAL);
            connectionNewsTable.addFermatEnumFilter(CryptoBrokerActorNetworkServiceDatabaseConstants.QUOTES_REQUEST_STATE_COLUMN_NAME, protocolState, DatabaseFilterType.EQUAL);

            connectionNewsTable.loadToMemory();

            final List<DatabaseTableRecord> records = connectionNewsTable.getRecords();

            final List<CryptoBrokerExtraData<CryptoBrokerQuote>> quotesRequestsList = new ArrayList<>();

            for (final DatabaseTableRecord record : records)
                quotesRequestsList.add(buildQuotesRequestObject(record));

            return quotesRequestsList;

        } catch (final CantLoadTableToMemoryException e) {

            throw new CantListPendingQuotesRequestsException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (final InvalidParameterException e) {

            throw new CantListPendingQuotesRequestsException(e, "", "Exception reading records of the table Cannot recognize the codes of the currencies.");
        }
    }

    public final List<CryptoBrokerActorNetworkServiceQuotesRequest> listPendingQuotesRequestsByProtocolState(final ProtocolState protocolState) throws CantListPendingQuotesRequestsException {

        try {

            final DatabaseTable connectionNewsTable = database.getTable(CryptoBrokerActorNetworkServiceDatabaseConstants.QUOTES_REQUEST_TABLE_NAME);

            connectionNewsTable.addFermatEnumFilter(CryptoBrokerActorNetworkServiceDatabaseConstants.QUOTES_REQUEST_STATE_COLUMN_NAME, protocolState, DatabaseFilterType.EQUAL);

            connectionNewsTable.loadToMemory();

            final List<DatabaseTableRecord> records = connectionNewsTable.getRecords();

            final List<CryptoBrokerActorNetworkServiceQuotesRequest> quotesRequestsList = new ArrayList<>();

            for (final DatabaseTableRecord record : records)
                quotesRequestsList.add(buildQuotesRequestObject(record));

            return quotesRequestsList;

        } catch (final CantLoadTableToMemoryException e) {

            throw new CantListPendingQuotesRequestsException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (final InvalidParameterException e) {

            throw new CantListPendingQuotesRequestsException(e, "", "Exception reading records of the table Cannot recognize the codes of the currencies.");
        }
    }

    private CryptoBrokerActorNetworkServiceQuotesRequest buildQuotesRequestObject(final DatabaseTableRecord record) throws CantListPendingQuotesRequestsException, InvalidParameterException {

        UUID requestId = record.getUUIDValue(CryptoBrokerActorNetworkServiceDatabaseConstants.QUOTES_REQUEST_REQUEST_ID_COLUMN_NAME);
        String requesterPublicKey = record.getStringValue(CryptoBrokerActorNetworkServiceDatabaseConstants.QUOTES_REQUEST_REQUESTER_PUBLIC_KEY_COLUMN_NAME);
        String requesterActorTypeString = record.getStringValue(CryptoBrokerActorNetworkServiceDatabaseConstants.QUOTES_REQUEST_REQUESTER_ACTOR_TYPE_COLUMN_NAME);
        String cryptoBrokerPublicKey = record.getStringValue(CryptoBrokerActorNetworkServiceDatabaseConstants.QUOTES_REQUEST_CRYPTO_BROKER_PUBLIC_KEY_COLUMN_NAME);
        Long updateTime = record.getLongValue(CryptoBrokerActorNetworkServiceDatabaseConstants.QUOTES_REQUEST_UPDATE_TIME_COLUMN_NAME);
        String typeString = record.getStringValue(CryptoBrokerActorNetworkServiceDatabaseConstants.QUOTES_REQUEST_TYPE_COLUMN_NAME);
        String stateString = record.getStringValue(CryptoBrokerActorNetworkServiceDatabaseConstants.QUOTES_REQUEST_STATE_COLUMN_NAME);


        Actors requesterActorType = Actors.getByCode(requesterActorTypeString);
        RequestType type = RequestType.getByCode(typeString);
        ProtocolState state = ProtocolState.getByCode(stateString);


        return new CryptoBrokerActorNetworkServiceQuotesRequest(
                requestId,
                requesterPublicKey,
                requesterActorType,
                cryptoBrokerPublicKey,
                updateTime,
                listQuotes(requestId),
                type,
                state
        );
    }

    private ArrayList<CryptoBrokerQuote> listQuotes(UUID requestId) throws CantListPendingQuotesRequestsException {

        try {
            final DatabaseTable quotesTable = database.getTable(CryptoBrokerActorNetworkServiceDatabaseConstants.QUOTES_TABLE_NAME);

            quotesTable.addUUIDFilter(CryptoBrokerActorNetworkServiceDatabaseConstants.QUOTES_REQUEST_ID_COLUMN_NAME, requestId, DatabaseFilterType.EQUAL);

            quotesTable.loadToMemory();

            final List<DatabaseTableRecord> records = quotesTable.getRecords();

            ArrayList<CryptoBrokerQuote> quotesList = new ArrayList<>();

            for (DatabaseTableRecord record : records) {

                String merchandiseString = record.getStringValue(CryptoBrokerActorNetworkServiceDatabaseConstants.QUOTES_MERCHANDISE_COLUMN_NAME);
                String merchandiseTypeString = record.getStringValue(CryptoBrokerActorNetworkServiceDatabaseConstants.QUOTES_MERCHANDISE_TYPE_COLUMN_NAME);
                String paymentCurrencyString = record.getStringValue(CryptoBrokerActorNetworkServiceDatabaseConstants.QUOTES_PAYMENT_CURRENCY_COLUMN_NAME);
                String paymentCurrencyTypeString = record.getStringValue(CryptoBrokerActorNetworkServiceDatabaseConstants.QUOTES_PAYMENT_CURRENCY_TYPE_COLUMN_NAME);
                Float price = record.getFloatValue(CryptoBrokerActorNetworkServiceDatabaseConstants.QUOTES_PRICE_COLUMN_NAME);
                String supportedPlatforms = record.getStringValue(CryptoBrokerActorNetworkServiceDatabaseConstants.QUOTES_SUPPORTED_PLATFORMS_COLUMN_NAME);

                Currency merchandise = CurrencyHelper.getCurrency(merchandiseTypeString, merchandiseString);
                Currency paymentCurrency = CurrencyHelper.getCurrency(paymentCurrencyTypeString, paymentCurrencyString);

                quotesList.add(
                        new CryptoBrokerQuote(
                                merchandise,
                                paymentCurrency,
                                price,
                                supportedPlatforms
                        )
                );
            }

            return quotesList;

        } catch (final CantLoadTableToMemoryException e) {

            throw new CantListPendingQuotesRequestsException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (final InvalidParameterException e) {

            throw new CantListPendingQuotesRequestsException(e, "", "Exception reading records of the table Cannot recognize the codes of the currencies.");
        }
    }

    public boolean isPendingQuotesRequestUpdates() throws CantListPendingQuotesRequestsException {

        try {

            final DatabaseTable connectionNewsTable = database.getTable(CryptoBrokerActorNetworkServiceDatabaseConstants.QUOTES_REQUEST_TABLE_NAME);

            connectionNewsTable.addFermatEnumFilter(CryptoBrokerActorNetworkServiceDatabaseConstants.QUOTES_REQUEST_TYPE_COLUMN_NAME, RequestType.SENT, DatabaseFilterType.EQUAL);
            connectionNewsTable.addFermatEnumFilter(CryptoBrokerActorNetworkServiceDatabaseConstants.QUOTES_REQUEST_STATE_COLUMN_NAME, ProtocolState.PENDING_LOCAL_ACTION, DatabaseFilterType.EQUAL);

            connectionNewsTable.loadToMemory();

            return !connectionNewsTable.getRecords().isEmpty();

        } catch (final CantLoadTableToMemoryException e) {

            throw new CantListPendingQuotesRequestsException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        }
    }

    public boolean isPendingQuotesRequestsNews() throws CantListPendingQuotesRequestsException {

        try {

            final DatabaseTable connectionNewsTable = database.getTable(CryptoBrokerActorNetworkServiceDatabaseConstants.QUOTES_REQUEST_TABLE_NAME);

            connectionNewsTable.addFermatEnumFilter(CryptoBrokerActorNetworkServiceDatabaseConstants.QUOTES_REQUEST_TYPE_COLUMN_NAME, RequestType.RECEIVED, DatabaseFilterType.EQUAL);
            connectionNewsTable.addFermatEnumFilter(CryptoBrokerActorNetworkServiceDatabaseConstants.QUOTES_REQUEST_STATE_COLUMN_NAME, ProtocolState.PENDING_LOCAL_ACTION, DatabaseFilterType.EQUAL);

            connectionNewsTable.loadToMemory();

            return !connectionNewsTable.getRecords().isEmpty();

        } catch (final CantLoadTableToMemoryException e) {

            throw new CantListPendingQuotesRequestsException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        }
    }

    public void confirmQuotesRequest(final UUID requestId) throws CantConfirmQuotesRequestException,
            QuotesRequestNotFoundException {

        if (requestId == null) {
            throw new CantConfirmQuotesRequestException(null, "", "The requestId is required, can not be null");
        }

        try {

            ProtocolState state = ProtocolState.DONE;

            DatabaseTable actorConnectionRequestTable = database.getTable(CryptoBrokerActorNetworkServiceDatabaseConstants.QUOTES_REQUEST_TABLE_NAME);

            actorConnectionRequestTable.addUUIDFilter(CryptoBrokerActorNetworkServiceDatabaseConstants.QUOTES_REQUEST_REQUEST_ID_COLUMN_NAME, requestId, DatabaseFilterType.EQUAL);

            actorConnectionRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = actorConnectionRequestTable.getRecords();

            if (!records.isEmpty()) {
                DatabaseTableRecord record = records.get(0);

                record.setFermatEnum(CryptoBrokerActorNetworkServiceDatabaseConstants.QUOTES_REQUEST_STATE_COLUMN_NAME, state);

                actorConnectionRequestTable.updateRecord(record);

            } else
                throw new QuotesRequestNotFoundException(null, new StringBuilder().append("requestId: ").append(requestId).toString(), "Cannot find a quotes request with that requestId.");

        } catch (CantUpdateRecordException e) {

            throw new CantConfirmQuotesRequestException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot update the record.");
        } catch (CantLoadTableToMemoryException e) {

            throw new CantConfirmQuotesRequestException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");

        }
    }


    private DatabaseTableRecord buildConnectionNewDatabaseRecord(final DatabaseTableRecord record,
                                                                 final CryptoBrokerConnectionRequest connectionNew) {

        try {

            record.setUUIDValue(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ID_COLUMN_NAME, connectionNew.getRequestId());
            record.setStringValue(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_SENDER_PUBLIC_KEY_COLUMN_NAME, connectionNew.getSenderPublicKey());
            record.setFermatEnum(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_SENDER_ACTOR_TYPE_COLUMN_NAME, connectionNew.getSenderActorType());
            record.setStringValue(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_SENDER_ALIAS_COLUMN_NAME, connectionNew.getSenderAlias());
            record.setStringValue(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_DESTINATION_PUBLIC_KEY_COLUMN_NAME, connectionNew.getDestinationPublicKey());
            record.setFermatEnum(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_TYPE_COLUMN_NAME, connectionNew.getRequestType());
            record.setFermatEnum(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_STATE_COLUMN_NAME, connectionNew.getProtocolState());
            record.setFermatEnum(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ACTION_COLUMN_NAME, connectionNew.getRequestAction());
            record.setLongValue(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_SENT_TIME_COLUMN_NAME, connectionNew.getSentTime());

            if (connectionNew.getSenderImage() != null && connectionNew.getSenderImage().length > 0)
                persistNewUserProfileImage(connectionNew.getSenderPublicKey(), connectionNew.getSenderImage());

            return record;
        } catch (final Exception e) {

            // TODO add better error management, "throws CantBuildDatabaseRecordException".

            System.err.println(new StringBuilder().append("error trying to persist image: ").append(e.getMessage()).toString());
            return record;
        }
    }

    private CryptoBrokerConnectionRequest buildConnectionNewRecord(final DatabaseTableRecord record) throws InvalidParameterException {

        try {
            UUID requestId = record.getUUIDValue(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ID_COLUMN_NAME);
            String senderPublicKey = record.getStringValue(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_SENDER_PUBLIC_KEY_COLUMN_NAME);
            String senderActorTypeString = record.getStringValue(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_SENDER_ACTOR_TYPE_COLUMN_NAME);
            String senderAlias = record.getStringValue(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_SENDER_ALIAS_COLUMN_NAME);
            String destinationPublicKey = record.getStringValue(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_DESTINATION_PUBLIC_KEY_COLUMN_NAME);
            String requestTypeString = record.getStringValue(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_TYPE_COLUMN_NAME);
            String protocolStateString = record.getStringValue(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_STATE_COLUMN_NAME);
            String requestActionString = record.getStringValue(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ACTION_COLUMN_NAME);
            Long sentTime = record.getLongValue(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_SENT_TIME_COLUMN_NAME);

            Actors senderActorType = Actors.getByCode(senderActorTypeString);
            RequestType requestType = RequestType.getByCode(requestTypeString);
            ProtocolState state = ProtocolState.getByCode(protocolStateString);
            ConnectionRequestAction action = ConnectionRequestAction.getByCode(requestActionString);

            byte[] profileImage;

            try {
                profileImage = getProfileImage(senderPublicKey);
            } catch (FileNotFoundException e) {
                profileImage = new byte[0];
            }

            return new CryptoBrokerConnectionRequest(
                    requestId,
                    senderPublicKey,
                    senderActorType,
                    senderAlias,
                    profileImage,
                    destinationPublicKey,
                    requestType,
                    state,
                    action,
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

    private void persistNewUserProfileImage(final String publicKey,
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
            FileNotFoundException {

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

    private String buildProfileImageFileName(final String publicKey) {
        return new StringBuilder().append(PROFILE_IMAGE_FILE_NAME_PREFIX).append("_").append(publicKey).toString();
    }

}
