package com.bitdubai.fermat_cht_plugin.layer.actor_network_service.chat.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.DeviceDirectory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOperator;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilterGroup;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
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
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetProfileImageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantInitializeDatabaseException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantPersistProfileImageException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.enums.ConnectionRequestAction;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.enums.ProtocolState;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.enums.RequestType;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantAcceptConnectionRequestException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantDenyConnectionRequestException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantDisconnectException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantListPendingConnectionRequestsException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantRequestConnectionException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.ConnectionRequestNotFoundException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.utils.ChatConnectionInformation;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.utils.ChatConnectionRequest;
import com.bitdubai.fermat_cht_plugin.layer.actor_network_service.chat.developer.bitdubai.version_1.exceptions.CantChangeProtocolStateException;
import com.bitdubai.fermat_cht_plugin.layer.actor_network_service.chat.developer.bitdubai.version_1.exceptions.CantConfirmConnectionRequestException;
import com.bitdubai.fermat_cht_plugin.layer.actor_network_service.chat.developer.bitdubai.version_1.exceptions.CantFindRequestException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by José D. Vilchez A. (josvilchezalmera@gmail.com) on 07/04/16.
 * Edited by Miguel Rincon on 19/04/2016
 */
public class ChatActorNetworkServiceDao {

    private ErrorManager errorManager;

    private static final String PROFILE_IMAGE_DIRECTORY_NAME   = DeviceDirectory.LOCAL_USERS.getName() + "/CBP/cryptoBrokerActorNS";
    private static final String PROFILE_IMAGE_FILE_NAME_PREFIX = "profileImage";

    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final PluginFileSystem pluginFileSystem    ;
    private final UUID pluginId            ;

    private Database database;

    public ChatActorNetworkServiceDao(final PluginDatabaseSystem pluginDatabaseSystem,
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
                    ChatActorNetworkServiceDatabaseConstants.CHAT_ACTOR_NETWORK_SERVICE_DATABASE_NAME
            );

        } catch (final DatabaseNotFoundException e) {

            try {

                ChatActorNetworkServiceDatabaseFactory cryptoBrokerActorNetworkServiceDatabaseFactory = new ChatActorNetworkServiceDatabaseFactory(pluginDatabaseSystem);
                database = cryptoBrokerActorNetworkServiceDatabaseFactory.createDatabase(
                        pluginId,
                        ChatActorNetworkServiceDatabaseConstants.CHAT_ACTOR_NETWORK_SERVICE_DATABASE_NAME
                );

            } catch (final CantCreateDatabaseException f) {
                errorManager.reportUnexpectedPluginException(Plugins.CHAT_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, f);

                throw new CantInitializeDatabaseException(f, "", "There was a problem and we cannot create the database.");
            } catch (final Exception z) {
                errorManager.reportUnexpectedPluginException(Plugins.CHAT_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, z);

                throw new CantInitializeDatabaseException(z, "", "Unhandled Exception.");
            }

        } catch (final CantOpenDatabaseException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);

            throw new CantInitializeDatabaseException(e, "", "Exception not handled by the plugin, there was a problem and we cannot open the database.");
        } catch (final Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);

            throw new CantInitializeDatabaseException(e, "", "Unhandled Exception.");
        }
    }

    /**
     * Return all the pending requests depending on the action informed through parameters.
     **
     * @return a list of ChatConnectionRequest instances.
     *
     * @throws CantListPendingConnectionRequestsException  if something goes wrong.
     */
    public final List<ChatConnectionRequest> listPendingConnectionUpdates() throws CantListPendingConnectionRequestsException {

        try {

            final ProtocolState protocolState = ProtocolState.PENDING_LOCAL_ACTION;

            final DatabaseTable connectionNewsTable = database.getTable(ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_TABLE_NAME);

            connectionNewsTable.addFermatEnumFilter(ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_STATE_COLUMN_NAME, protocolState, DatabaseFilterType.EQUAL);

            List<ConnectionRequestAction> actions = new ArrayList<>();

            actions.add(ConnectionRequestAction.ACCEPT);
            actions.add(ConnectionRequestAction.DENY);
            actions.add(ConnectionRequestAction.DISCONNECT);

            final List<DatabaseTableFilter> tableFilters = new ArrayList<>();

            for(final ConnectionRequestAction action : actions)
                tableFilters.add(connectionNewsTable.getNewFilter(ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ACTION_COLUMN_NAME, DatabaseFilterType.EQUAL, action.getCode()));

            final DatabaseTableFilterGroup filterGroup = connectionNewsTable.getNewFilterGroup(tableFilters, null, DatabaseFilterOperator.OR);

            connectionNewsTable.setFilterGroup(filterGroup);

            connectionNewsTable.loadToMemory();

            final List<DatabaseTableRecord> records = connectionNewsTable.getRecords();

            final List<ChatConnectionRequest> cryptoAddressRequests = new ArrayList<>();

            for (final DatabaseTableRecord record : records)
                cryptoAddressRequests.add(buildConnectionNewRecord(record));

            return cryptoAddressRequests;

        } catch (final CantLoadTableToMemoryException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);

            throw new CantListPendingConnectionRequestsException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (final InvalidParameterException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);

            throw new CantListPendingConnectionRequestsException(e, "", "There is a problem with some enum code."                                                                                );
        }
    }





    public final List<ChatConnectionRequest> listAllPendingRequestsByActorType(final Actors actorType, final List<ConnectionRequestAction> actions) throws CantListPendingConnectionRequestsException {

        try {

            final ProtocolState protocolState = ProtocolState.PENDING_LOCAL_ACTION;

            final DatabaseTable connectionNewsTable = database.getTable(ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_TABLE_NAME);

            connectionNewsTable.addFermatEnumFilter(ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_STATE_COLUMN_NAME, protocolState, DatabaseFilterType.EQUAL);
            connectionNewsTable.addFermatEnumFilter(ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_SENDER_ACTOR_TYPE_COLUMN_NAME, actorType, DatabaseFilterType.EQUAL);

            final List<DatabaseTableFilter> tableFilters = new ArrayList<>();

            for(final ConnectionRequestAction action : actions)
                tableFilters.add(connectionNewsTable.getNewFilter(ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ACTION_COLUMN_NAME, DatabaseFilterType.EQUAL, action.getCode()));

            final DatabaseTableFilterGroup filterGroup = connectionNewsTable.getNewFilterGroup(tableFilters, null, DatabaseFilterOperator.OR);

            connectionNewsTable.setFilterGroup(filterGroup);

            connectionNewsTable.loadToMemory();

            final List<DatabaseTableRecord> records = connectionNewsTable.getRecords();

            final List<ChatConnectionRequest> cryptoAddressRequests = new ArrayList<>();

            for (final DatabaseTableRecord record : records)
                cryptoAddressRequests.add(buildConnectionNewRecord(record));

            return cryptoAddressRequests;

        } catch (final CantLoadTableToMemoryException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);

            throw new CantListPendingConnectionRequestsException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (final InvalidParameterException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);

            throw new CantListPendingConnectionRequestsException(e, "", "There is a problem with some enum code."                                                                                );
        }
    }


        public final List<ChatConnectionRequest> listPendingConnectionNews(final Actors actorType) throws CantListPendingConnectionRequestsException {

        try {

            final ProtocolState protocolState = ProtocolState.PENDING_LOCAL_ACTION;

            final ConnectionRequestAction action = ConnectionRequestAction.REQUEST;

            final DatabaseTable connectionNewsTable = database.getTable(ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_TABLE_NAME);

            connectionNewsTable.addFermatEnumFilter(ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_STATE_COLUMN_NAME, protocolState, DatabaseFilterType.EQUAL);

            if (actorType != null)
                connectionNewsTable.addFermatEnumFilter(ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_SENDER_ACTOR_TYPE_COLUMN_NAME, actorType, DatabaseFilterType.EQUAL);

            connectionNewsTable.addFermatEnumFilter(ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ACTION_COLUMN_NAME, action, DatabaseFilterType.EQUAL);

            connectionNewsTable.loadToMemory();

            final List<DatabaseTableRecord> records = connectionNewsTable.getRecords();

            final List<ChatConnectionRequest> cryptoAddressRequests = new ArrayList<>();

            for (final DatabaseTableRecord record : records)
                cryptoAddressRequests.add(buildConnectionNewRecord(record));

            return cryptoAddressRequests;

        } catch (final CantLoadTableToMemoryException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);

            throw new CantListPendingConnectionRequestsException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (final InvalidParameterException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);

            throw new CantListPendingConnectionRequestsException(e, "", "There is a problem with some enum code."                                                                                );
        }
    }


    /**
     * Return all the pending requests depending on the protocol state informed through parameters.
     *
     * @param protocolState  the protocol state that we need to bring.
     *
     * @return a list of ChatConnectionRequest instances.
     *
     * @throws CantListPendingConnectionRequestsException  if something goes wrong.
     */
    public final List<ChatConnectionRequest> listAllRequestByProtocolState(final ProtocolState protocolState) throws CantListPendingConnectionRequestsException {

        try {

            final DatabaseTable connectionNewsTable = database.getTable(ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_TABLE_NAME);

            connectionNewsTable.addFermatEnumFilter(ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_STATE_COLUMN_NAME, protocolState, DatabaseFilterType.EQUAL);

            connectionNewsTable.loadToMemory();

            final List<DatabaseTableRecord> records = connectionNewsTable.getRecords();

            final List<ChatConnectionRequest> cryptoAddressRequests = new ArrayList<>();

            for (final DatabaseTableRecord record : records)
                cryptoAddressRequests.add(buildConnectionNewRecord(record));

            return cryptoAddressRequests;

        } catch (final CantLoadTableToMemoryException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);

            throw new CantListPendingConnectionRequestsException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (final InvalidParameterException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);

            throw new CantListPendingConnectionRequestsException(e, "", "There is a problem with some enum code."                                                                                );
        }
    }


    /**
     * Return all the pending requests depending on the protocol state informed through parameters.
     *
     * @param protocolStates  list of the protocol states that we need to bring.
     *
     * @return a list of ChatConnectionRequest instances.
     *
     * @throws CantListPendingConnectionRequestsException  if something goes wrong.
     */
    public final List<ChatConnectionRequest> listAllRequestByProtocolStates(final List<ProtocolState> protocolStates) throws CantListPendingConnectionRequestsException {

        try {

            final DatabaseTable connectionNewsTable = database.getTable(ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_TABLE_NAME);

            final List<DatabaseTableFilter> tableFilters = new ArrayList<>();

            for(final ProtocolState protocolState : protocolStates)
                tableFilters.add(connectionNewsTable.getNewFilter(ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_STATE_COLUMN_NAME, DatabaseFilterType.EQUAL, protocolState.getCode()));

            final DatabaseTableFilterGroup filterGroup = connectionNewsTable.getNewFilterGroup(tableFilters, null, DatabaseFilterOperator.OR);

            connectionNewsTable.setFilterGroup(filterGroup);

            connectionNewsTable.loadToMemory();

            final List<DatabaseTableRecord> records = connectionNewsTable.getRecords();

            final List<ChatConnectionRequest> cryptoAddressRequests = new ArrayList<>();

            for (final DatabaseTableRecord record : records)
                cryptoAddressRequests.add(buildConnectionNewRecord(record));

            return cryptoAddressRequests;

        } catch (final CantLoadTableToMemoryException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);

            throw new CantListPendingConnectionRequestsException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (final InvalidParameterException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);

            throw new CantListPendingConnectionRequestsException(e, "", "There is a problem with some enum code."                                                                                );
        }
    }


    public final void createConnectionRequest(final ChatConnectionInformation chatActor,
                                              final ProtocolState                     state            ,
                                              final RequestType                       type             ,
                                              final ConnectionRequestAction           action           ) throws CantRequestConnectionException {

        try {

            final ChatConnectionRequest connectionNew = new ChatConnectionRequest(
                    chatActor.getConnectionId()        ,
                    chatActor.getSenderPublicKey()     ,
                    chatActor.getSenderActorType()     ,
                    chatActor.getSenderAlias()         ,
                    chatActor.getSenderImage()         ,
                    chatActor.getDestinationPublicKey(),
                    type                                       ,
                    state                                      ,
                    action                                     ,
                    chatActor.getSendingTime()
            );

            final DatabaseTable addressExchangeRequestTable = database.getTable(ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_TABLE_NAME);

            DatabaseTableRecord entityRecord = addressExchangeRequestTable.getEmptyRecord();

            DatabaseTableFilter filter = addressExchangeRequestTable.getEmptyTableFilter();
            filter.setType(DatabaseFilterType.EQUAL);
            filter.setValue(chatActor.getConnectionId().toString());
            filter.setColumn(ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ID_COLUMN_NAME);

            entityRecord = buildConnectionNewDatabaseRecord(entityRecord, connectionNew);

            if(isNewRecord(addressExchangeRequestTable,filter))
                addressExchangeRequestTable.insertRecord(entityRecord);
            else
                addressExchangeRequestTable.updateRecord(entityRecord);

        } catch (final CantInsertRecordException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);

            throw new CantRequestConnectionException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.");
        } catch (CantLoadTableToMemoryException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
        } catch (CantUpdateRecordException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
        }
    }

    private boolean isNewRecord(DatabaseTable table, DatabaseTableFilter filter) throws CantLoadTableToMemoryException {
        table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());
        table.loadToMemory();
        return table.getRecords().isEmpty();
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

            final DatabaseTable connectionNewsTable = database.getTable(ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_TABLE_NAME);

            connectionNewsTable.addUUIDFilter(ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ID_COLUMN_NAME, requestId, DatabaseFilterType.EQUAL);

            connectionNewsTable.loadToMemory();

            final List<DatabaseTableRecord> records = connectionNewsTable.getRecords();

            if (!records.isEmpty()) {

                final DatabaseTableRecord record = records.get(0);

                record.setFermatEnum(ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_STATE_COLUMN_NAME , state );
                record.setFermatEnum(ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ACTION_COLUMN_NAME, action);

                connectionNewsTable.updateRecord(record);

            } else
                throw new ConnectionRequestNotFoundException(null, "requestId: "+requestId, "Cannot find an actor connection request with that requestId.");

        } catch (final CantUpdateRecordException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);

            throw new CantDenyConnectionRequestException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot update the record.");
        } catch (final CantLoadTableToMemoryException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);

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

            final DatabaseTable connectionNewsTable = database.getTable(ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_TABLE_NAME);

            connectionNewsTable.addUUIDFilter(ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ID_COLUMN_NAME, requestId, DatabaseFilterType.EQUAL);

            connectionNewsTable.loadToMemory();

            final List<DatabaseTableRecord> records = connectionNewsTable.getRecords();

            if (!records.isEmpty()) {

                final DatabaseTableRecord record = records.get(0);

                record.setFermatEnum(ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_STATE_COLUMN_NAME , state );
                record.setFermatEnum(ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ACTION_COLUMN_NAME, action);

                connectionNewsTable.updateRecord(record);

            } else
                throw new ConnectionRequestNotFoundException(null, "requestId: "+requestId, "Cannot find an actor connection request with that requestId.");

        } catch (final CantUpdateRecordException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);

            throw new CantDisconnectException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot update the record.");
        } catch (final CantLoadTableToMemoryException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);

            throw new CantDisconnectException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        }
    }


    public ChatConnectionRequest getConnectionRequest(final UUID requestId) throws CantFindRequestException, ConnectionRequestNotFoundException {

        if (requestId == null)
            throw new CantFindRequestException(null, "", "The requestId is required, can not be null");

        try {

            final DatabaseTable connectionRequestTable = database.getTable(ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_TABLE_NAME);

            connectionRequestTable.addUUIDFilter(ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ID_COLUMN_NAME, requestId, DatabaseFilterType.EQUAL);

            connectionRequestTable.loadToMemory();

            final List<DatabaseTableRecord> records = connectionRequestTable.getRecords();

            if (!records.isEmpty())
                return buildConnectionNewRecord(records.get(0));
            else
                throw new ConnectionRequestNotFoundException(null, "requestId: "+requestId, "Cannot find an actor Connection request with that requestId.");

        } catch (final CantLoadTableToMemoryException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);

            throw new CantFindRequestException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (final InvalidParameterException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);

            throw new CantFindRequestException(e, "", "Exception reading records of the table Cannot recognize the codes of the currencies.");
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

            DatabaseTable actorConnectionRequestTable = database.getTable(ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_TABLE_NAME);

            actorConnectionRequestTable.addUUIDFilter(ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ID_COLUMN_NAME, requestId, DatabaseFilterType.EQUAL);

            actorConnectionRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = actorConnectionRequestTable.getRecords();

            if (!records.isEmpty()) {
                DatabaseTableRecord record = records.get(0);

                record.setStringValue(ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_STATE_COLUMN_NAME, state.getCode());

                actorConnectionRequestTable.updateRecord(record);

            } else
                throw new ConnectionRequestNotFoundException(null, "requestId: "+requestId, "Cannot find an actor Connection request with that requestId.");

        } catch (CantUpdateRecordException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);

            throw new CantChangeProtocolStateException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot update the record.");
        } catch (CantLoadTableToMemoryException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);

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

            DatabaseTable actorConnectionRequestTable = database.getTable(ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_TABLE_NAME);

            actorConnectionRequestTable.addUUIDFilter(ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ID_COLUMN_NAME, requestId, DatabaseFilterType.EQUAL);

            actorConnectionRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = actorConnectionRequestTable.getRecords();

            if (!records.isEmpty()) {
                DatabaseTableRecord record = records.get(0);

                record.setStringValue(ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_STATE_COLUMN_NAME, state.getCode());
                record.setStringValue(ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ACTION_COLUMN_NAME, action.getCode());

                actorConnectionRequestTable.updateRecord(record);

            } else
                throw new ConnectionRequestNotFoundException(null, "requestId: "+requestId, "Cannot find an address exchange request with that requestId.");

        } catch (CantUpdateRecordException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);

            throw new CantConfirmConnectionRequestException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot update the record.");
        } catch (CantLoadTableToMemoryException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);

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

            final DatabaseTable connectionNewsTable = database.getTable(ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_TABLE_NAME);

            connectionNewsTable.addUUIDFilter(ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ID_COLUMN_NAME, requestId, DatabaseFilterType.EQUAL);

            connectionNewsTable.loadToMemory();

            final List<DatabaseTableRecord> records = connectionNewsTable.getRecords();

            if (!records.isEmpty()) {

                final DatabaseTableRecord record = records.get(0);

                record.setFermatEnum(ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_STATE_COLUMN_NAME, state);
                record.setFermatEnum(ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ACTION_COLUMN_NAME, action);

                connectionNewsTable.updateRecord(record);

            } else
                throw new ConnectionRequestNotFoundException(null, "requestId: "+requestId, "Cannot find an actor connection request with that requestId.");

        } catch (final CantUpdateRecordException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);

            throw new CantAcceptConnectionRequestException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot update the record.");
        } catch (final CantLoadTableToMemoryException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);

            throw new CantAcceptConnectionRequestException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        }
    }


    public String getDestinationPublicKey(final UUID connectionId) throws CantListPendingConnectionRequestsException  ,
            ConnectionRequestNotFoundException {

        if (connectionId == null)
            throw new CantListPendingConnectionRequestsException("", "The connectionId is required, can not be null");

        try {
            final DatabaseTable actorConnectionsTable = database.getTable(ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_TABLE_NAME);

            actorConnectionsTable.addUUIDFilter(ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ID_COLUMN_NAME, connectionId, DatabaseFilterType.EQUAL);

            actorConnectionsTable.loadToMemory();

            final List<DatabaseTableRecord> records = actorConnectionsTable.getRecords();

            if (!records.isEmpty()) {

                final DatabaseTableRecord record = records.get(0);

                return record.getStringValue(ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_DESTINATION_PUBLIC_KEY_COLUMN_NAME);

            } else
                throw new ConnectionRequestNotFoundException(
                        "connectionId: "+connectionId,
                        "Cannot find an actor connection request with that requestId."
                );

        } catch (final CantLoadTableToMemoryException cantLoadTableToMemoryException) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantLoadTableToMemoryException);

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

            final DatabaseTable connectionNewsTable = database.getTable(ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_TABLE_NAME);

            connectionNewsTable.addUUIDFilter(ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ID_COLUMN_NAME, requestId, DatabaseFilterType.EQUAL);

            connectionNewsTable.loadToMemory();

            final List<DatabaseTableRecord> records = connectionNewsTable.getRecords();

            return !records.isEmpty();

        } catch (final CantLoadTableToMemoryException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);

            throw new CantFindRequestException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        }
    }


    private DatabaseTableRecord buildConnectionNewDatabaseRecord(final DatabaseTableRecord           record       ,
                                                                 final ChatConnectionRequest connectionNew) {

        try {

            record.setUUIDValue  (ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ID_COLUMN_NAME            , connectionNew.getRequestId())           ;
            record.setStringValue(ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_SENDER_PUBLIC_KEY_COLUMN_NAME     , connectionNew.getSenderPublicKey())     ;
            record.setFermatEnum (ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_SENDER_ACTOR_TYPE_COLUMN_NAME     , connectionNew.getSenderActorType())     ;
            record.setStringValue(ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_SENDER_ALIAS_COLUMN_NAME          , connectionNew.getSenderAlias())         ;
            record.setStringValue(ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_DESTINATION_PUBLIC_KEY_COLUMN_NAME, connectionNew.getDestinationPublicKey());
            record.setFermatEnum (ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_TYPE_COLUMN_NAME          , connectionNew.getRequestType())         ;
            record.setFermatEnum (ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_STATE_COLUMN_NAME         , connectionNew.getProtocolState())       ;
            record.setFermatEnum (ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ACTION_COLUMN_NAME        , connectionNew.getRequestAction())       ;
            record.setLongValue  (ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_SENT_TIME_COLUMN_NAME             , connectionNew.getSentTime())            ;

            if (connectionNew.getSenderImage() != null && connectionNew.getSenderImage().length > 0)
                persistNewUserProfileImage(connectionNew.getSenderPublicKey(), connectionNew.getSenderImage());

            return record;
        } catch (final Exception e) {

            // TODO add better error management, "throws CantBuildDatabaseRecordException".
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);

            System.err.println("error trying to persist image:"+e.getMessage());
            return record;
        }
    }

    private ChatConnectionRequest buildConnectionNewRecord(final DatabaseTableRecord record) throws InvalidParameterException {

        try {
            UUID   requestId             = record.getUUIDValue  (ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ID_COLUMN_NAME            );
            String senderPublicKey       = record.getStringValue(ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_SENDER_PUBLIC_KEY_COLUMN_NAME);
            String senderActorTypeString = record.getStringValue(ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_SENDER_ACTOR_TYPE_COLUMN_NAME     );
            String senderAlias           = record.getStringValue(ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_SENDER_ALIAS_COLUMN_NAME          );
            String destinationPublicKey  = record.getStringValue(ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_DESTINATION_PUBLIC_KEY_COLUMN_NAME);
            String requestTypeString     = record.getStringValue(ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_TYPE_COLUMN_NAME          );
            String protocolStateString   = record.getStringValue(ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_STATE_COLUMN_NAME         );
            String requestActionString   = record.getStringValue(ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ACTION_COLUMN_NAME        );
            Long   sentTime              = record.getLongValue  (ChatActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_SENT_TIME_COLUMN_NAME             );

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

            return new ChatConnectionRequest(
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
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);

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
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);

            throw new CantPersistProfileImageException(
                    "Error persist file.",
                    e,
                    null,
                    ""
            );

        } catch (final CantCreateFileException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);

            throw new CantPersistProfileImageException(
                    "Error creating file.",
                    e,
                    null,
                    ""
            );
        } catch (final Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);

            throw new CantPersistProfileImageException(
                    "",
                    e,
                    "Unhandled Exception.",
                    ""
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
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);

            throw new CantGetProfileImageException(
                    "Error loaded file.",
                    e,
                    null,
                    ""
            );

        } catch (final FileNotFoundException | CantCreateFileException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);

            throw new FileNotFoundException(e, "", null);
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);

            throw new CantGetProfileImageException(
                    "",
                    e,
                    "Unhandled Exception",
                    ""
            );
        }
    }
    private String buildProfileImageFileName(final String publicKey) {
        return PROFILE_IMAGE_FILE_NAME_PREFIX + "_" + publicKey;
    }
}
