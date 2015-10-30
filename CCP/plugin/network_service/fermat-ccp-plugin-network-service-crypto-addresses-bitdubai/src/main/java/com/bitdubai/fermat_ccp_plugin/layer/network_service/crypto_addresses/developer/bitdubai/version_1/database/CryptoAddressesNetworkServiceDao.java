package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.database;

/**
 * The class <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.database.CryptoAddressesNetworkServiceDao</code>
 * haves all the methods that interact with the database.
 * Manages the addresses exchange requests by storing them on a Database Table.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 24/09/2015.
 *
 * @version 1.0
 */
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.enums.ProtocolState;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.enums.RequestAction;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.enums.RequestType;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantAcceptAddressExchangeRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantConfirmAddressExchangeRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantDenyAddressExchangeRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantGetPendingAddressExchangeRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantListPendingAddressExchangeRequestsException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.PendingRequestNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.AddressExchangeRequest;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.exceptions.CantChangeProtocolStateException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.exceptions.CantCreateRequestException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.exceptions.CantInitializeCryptoAddressesNetworkServiceDatabaseException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.structure.CryptoAddressesNetworkServiceAddressExchangeRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CryptoAddressesNetworkServiceDao {

    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID                 pluginId            ;

    private Database database;

    public CryptoAddressesNetworkServiceDao(final PluginDatabaseSystem pluginDatabaseSystem,
                                            final UUID                 pluginId            ) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId             = pluginId            ;
    }

    public void initialize() throws CantInitializeCryptoAddressesNetworkServiceDatabaseException {
        try {

            database = this.pluginDatabaseSystem.openDatabase(
                    this.pluginId,
                    this.pluginId.toString()
            );

        } catch (DatabaseNotFoundException e) {

            try {

                CryptoAddressesNetworkServiceDatabaseFactory databaseFactory = new CryptoAddressesNetworkServiceDatabaseFactory(pluginDatabaseSystem);
                database = databaseFactory.createDatabase(
                        pluginId,
                        pluginId.toString()
                );

            } catch (CantCreateDatabaseException f) {

                throw new CantInitializeCryptoAddressesNetworkServiceDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, f, "", "There is a problem and i cannot create the database.");
            } catch (Exception z) {

                throw new CantInitializeCryptoAddressesNetworkServiceDatabaseException(CantInitializeCryptoAddressesNetworkServiceDatabaseException.DEFAULT_MESSAGE, z, "", "Generic Exception.");
            }

        } catch (CantOpenDatabaseException e) {

            throw new CantInitializeCryptoAddressesNetworkServiceDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (Exception e) {

            throw new CantInitializeCryptoAddressesNetworkServiceDatabaseException(CantInitializeCryptoAddressesNetworkServiceDatabaseException.DEFAULT_MESSAGE, e, "", "Generic Exception.");
        }
    }


    public void createAddressExchangeRequest(UUID                  id                         ,
                                             String                walletPublicKey            ,
                                             CryptoCurrency        cryptoCurrency             ,
                                             Actors                identityTypeRequesting     ,
                                             Actors                identityTypeAccepting      ,
                                             String                identityPublicKeyRequesting,
                                             String                identityPublicKeyAccepting ,
                                             ProtocolState         protocolState              ,
                                             RequestType           requestType                ,
                                             RequestAction         requestAction              ,
                                             BlockchainNetworkType blockchainNetworkType      ) throws CantCreateRequestException {

        try {

            CryptoAddressesNetworkServiceAddressExchangeRequest addressExchangeRequest = new CryptoAddressesNetworkServiceAddressExchangeRequest(
                    id                      ,
                    walletPublicKey            ,
                    identityTypeRequesting     ,
                    identityTypeAccepting      ,
                    identityPublicKeyRequesting,
                    identityPublicKeyAccepting ,
                    cryptoCurrency             ,
                    null                       ,
                    protocolState              ,
                    requestType                ,
                    requestAction              ,
                    blockchainNetworkType
            );

            DatabaseTable addressExchangeRequestTable = database.getTable(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_TABLE_NAME);

            DatabaseTableRecord entityRecord = addressExchangeRequestTable.getEmptyRecord();

            entityRecord = buildDatabaseRecord(entityRecord, addressExchangeRequest);

            addressExchangeRequestTable.insertRecord(entityRecord);

        } catch (CantInsertRecordException e) {

            throw new CantCreateRequestException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.");
        }
    }

    /**
     * we'll return to the actor all the pending requests pending a local action.
     * State : PENDING_ACTION.
     *
     * @param actorType  type of actor asking for pending requests
     *
     * @throws CantListPendingAddressExchangeRequestsException      if something goes wrong.
     */
    public List<AddressExchangeRequest> listPendingRequestsByActorType(Actors actorType) throws CantListPendingAddressExchangeRequestsException {

        if (actorType == null)
            throw new CantListPendingAddressExchangeRequestsException(null, "", "actorType, can not be null");

        try {

            ProtocolState protocolState = ProtocolState.PENDING_ACTION;

            DatabaseTable addressExchangeRequestTable = database.getTable(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_TABLE_NAME);

            addressExchangeRequestTable.setStringFilter(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_IDENTITY_TYPE_RESPONDING_COLUMN_NAME, actorType    .getCode(), DatabaseFilterType.EQUAL);
            addressExchangeRequestTable.setStringFilter(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_STATE_COLUMN_NAME                   , protocolState.getCode(), DatabaseFilterType.EQUAL);

            addressExchangeRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = addressExchangeRequestTable.getRecords();

            List<AddressExchangeRequest> addressExchangeRequests = new ArrayList<>();

            for (DatabaseTableRecord record : records) {
                addressExchangeRequests.add(buildAddressExchangeRequestRecord(record));
            }

            return addressExchangeRequests;

        } catch (CantLoadTableToMemoryException exception) {

            throw new CantListPendingAddressExchangeRequestsException(exception, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (InvalidParameterException exception) {

            throw new CantListPendingAddressExchangeRequestsException(exception, "", "Check the cause."                                                                                );
        }
    }

    /**
     * we'll return to the actor all the request in a specific protocol state
     *
     * @param protocolState  that we need.
     *
     * @throws CantListPendingAddressExchangeRequestsException      if something goes wrong.
     */
    public List<AddressExchangeRequest> listPendingRequestsByProtocolState(ProtocolState protocolState) throws CantListPendingAddressExchangeRequestsException {

        if (protocolState == null)
            throw new CantListPendingAddressExchangeRequestsException(null, "", "actorType, can not be null");

        try {

            DatabaseTable addressExchangeRequestTable = database.getTable(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_TABLE_NAME);

            addressExchangeRequestTable.setStringFilter(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_STATE_COLUMN_NAME, protocolState.getCode(), DatabaseFilterType.EQUAL);

            addressExchangeRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = addressExchangeRequestTable.getRecords();

            List<AddressExchangeRequest> addressExchangeRequests = new ArrayList<>();

            for (DatabaseTableRecord record : records) {
                addressExchangeRequests.add(buildAddressExchangeRequestRecord(record));
            }

            return addressExchangeRequests;

        } catch (CantLoadTableToMemoryException exception) {

            throw new CantListPendingAddressExchangeRequestsException(exception, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (InvalidParameterException exception) {

            throw new CantListPendingAddressExchangeRequestsException(exception, "", "Check the cause."                                                                                );
        }
    }

    public AddressExchangeRequest getPendingRequest(UUID requestId) throws CantGetPendingAddressExchangeRequestException,
                                                                           PendingRequestNotFoundException {

        if (requestId == null)
            throw new CantGetPendingAddressExchangeRequestException(null, "", "requestId, can not be null");

        try {

            DatabaseTable addressExchangeRequestTable = database.getTable(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_TABLE_NAME);

            addressExchangeRequestTable.setUUIDFilter(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_ID_COLUMN_NAME, requestId, DatabaseFilterType.EQUAL);

            addressExchangeRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = addressExchangeRequestTable.getRecords();


            if (!records.isEmpty())
                return buildAddressExchangeRequestRecord(records.get(0));
            else
                throw new PendingRequestNotFoundException(null, "RequestID: "+requestId, "Can not find an address exchange request with the given request id.");


        } catch (CantLoadTableToMemoryException exception) {

            throw new CantGetPendingAddressExchangeRequestException(exception, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (InvalidParameterException exception) {

            throw new CantGetPendingAddressExchangeRequestException(exception, "", "Check the cause."                                                                                );
        }
    }

    /**
     * when i accept a request, first, i update the request with the address that i'm returning.
     * then i indicate the ns agent to do the next action:
     * -State : @state
     * -Action: ACCEPT.
     *
     * @param requestId id of the address exchange request we want to confirm.
     *
     * @throws CantAcceptAddressExchangeRequestException      if something goes wrong.
     * @throws PendingRequestNotFoundException                if i can't find the record.
     */
    public void acceptAddressExchangeRequest(final UUID          requestId    ,
                                             final CryptoAddress cryptoAddress,
                                             final ProtocolState state        ) throws CantAcceptAddressExchangeRequestException,
                                                                                       PendingRequestNotFoundException          {

        System.out.println("************ Crypto Addresses -> i'm processing dao acceptance.");

        if (requestId == null)
            throw new CantAcceptAddressExchangeRequestException(null, "", "The requestId is required, can not be null");

        if (cryptoAddress == null)
            throw new CantAcceptAddressExchangeRequestException(null, "", "The cryptoAddress is required, can not be null");

        if (state == null)
            throw new CantAcceptAddressExchangeRequestException(null, "", "The state is required, can not be null");

        try {

            System.out.println("************ Crypto Addresses -> dao validation ok.");

            RequestAction action = RequestAction.ACCEPT;

            DatabaseTable addressExchangeRequestTable = database.getTable(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_TABLE_NAME);

            addressExchangeRequestTable.setUUIDFilter(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_ID_COLUMN_NAME, requestId, DatabaseFilterType.EQUAL);

            addressExchangeRequestTable.loadToMemory();

            System.out.println("************ Crypto Addresses -> load to memory ok.");

            List<DatabaseTableRecord> records = addressExchangeRequestTable.getRecords();

            if (!records.isEmpty()) {
                System.out.println("************ Crypto Addresses -> i will update the record.");
                DatabaseTableRecord record = records.get(0);

                record.setStringValue(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_CRYPTO_ADDRESS_COLUMN_NAME, cryptoAddress.getAddress());
                record.setStringValue(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_STATE_COLUMN_NAME         , state        .getCode()   );
                record.setStringValue(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_ACTION_COLUMN_NAME        , action       .getCode()   );

                addressExchangeRequestTable.updateRecord(record);

                System.out.println("************ Crypto Addresses -> updating ok.");
            } else
                throw new PendingRequestNotFoundException(null, "requestId: "+requestId, "Cannot find an address exchange request with that requestId.");

        } catch (CantUpdateRecordException e) {

            throw new CantAcceptAddressExchangeRequestException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot update the record.");
        } catch (CantLoadTableToMemoryException e) {

            throw new CantAcceptAddressExchangeRequestException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");

        }
    }

    /**
     * when i deny a request i indicate the ns agent to do the next action:
     * State : @state.
     * Action: DENY.
     *
     * @param requestId id of the address exchange request we want to confirm.
     *
     * @throws CantDenyAddressExchangeRequestException      if something goes wrong.
     * @throws PendingRequestNotFoundException              if i can't find the record.
     */
    public void denyAddressExchangeRequest(final UUID          requestId,
                                           final ProtocolState state    ) throws CantDenyAddressExchangeRequestException,
                                                                        PendingRequestNotFoundException        {

        if (requestId == null)
            throw new CantDenyAddressExchangeRequestException(null, "", "The requestId is required, can not be null");

        if (state == null)
            throw new CantDenyAddressExchangeRequestException(null, "", "The state is required, can not be null");

        try {

            RequestAction action = RequestAction.DENY           ;

            DatabaseTable addressExchangeRequestTable = database.getTable(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_TABLE_NAME);

            addressExchangeRequestTable.setUUIDFilter(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_ID_COLUMN_NAME, requestId, DatabaseFilterType.EQUAL);

            addressExchangeRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = addressExchangeRequestTable.getRecords();

            if (!records.isEmpty()) {
                DatabaseTableRecord record = records.get(0);

                record.setStringValue(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_STATE_COLUMN_NAME , state .getCode());
                record.setStringValue(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_ACTION_COLUMN_NAME, action.getCode());

                addressExchangeRequestTable.updateRecord(record);

            } else
                throw new PendingRequestNotFoundException(null, "requestId: "+requestId, "Cannot find an address exchange request with that requestId.");

        } catch (CantUpdateRecordException e) {

            throw new CantDenyAddressExchangeRequestException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot update the record.");
        } catch (CantLoadTableToMemoryException e) {

            throw new CantDenyAddressExchangeRequestException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");

        }
    }

    /**
     * when i confirm a request i put it in the final state, indicating:
     * State : DONE.
     * Action: NONE.
     *
     * @param requestId id of the address exchange request we want to confirm.
     *
     * @throws CantConfirmAddressExchangeRequestException   if something goes wrong.
     * @throws PendingRequestNotFoundException              if i can't find the record.
     */
    public void confirmAddressExchangeRequest(UUID requestId) throws CantConfirmAddressExchangeRequestException,
                                                                     PendingRequestNotFoundException           {

        if (requestId == null) {
            throw new CantConfirmAddressExchangeRequestException(null, "", "The requestId is required, can not be null");
        }

        try {

            ProtocolState state  = ProtocolState.DONE;
            RequestAction action = RequestAction.NONE;

            DatabaseTable addressExchangeRequestTable = database.getTable(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_TABLE_NAME);

            addressExchangeRequestTable.setUUIDFilter(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_ID_COLUMN_NAME, requestId, DatabaseFilterType.EQUAL);

            addressExchangeRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = addressExchangeRequestTable.getRecords();

            if (!records.isEmpty()) {
                DatabaseTableRecord record = records.get(0);

                record.setStringValue(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_STATE_COLUMN_NAME , state .getCode());
                record.setStringValue(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_ACTION_COLUMN_NAME, action.getCode());

                addressExchangeRequestTable.updateRecord(record);

            } else
                throw new PendingRequestNotFoundException(null, "requestId: "+requestId, "Cannot find an address exchange request with that requestId.");

        } catch (CantUpdateRecordException e) {

            throw new CantConfirmAddressExchangeRequestException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot update the record.");
        } catch (CantLoadTableToMemoryException e) {

            throw new CantConfirmAddressExchangeRequestException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");

        }
    }

    /**
     * change the protocol state
     *
     * @param requestId id of the address exchange request we want to confirm.
     * @param state     protocol state to change
     *
     * @throws CantChangeProtocolStateException      if something goes wrong.
     * @throws PendingRequestNotFoundException       if i can't find the record.
     */
    public void changeProtocolState(final UUID          requestId,
                                    final ProtocolState state    ) throws CantChangeProtocolStateException,
                                                                         PendingRequestNotFoundException  {

        if (requestId == null)
            throw new CantChangeProtocolStateException(null, "", "The requestId is required, can not be null");

        if (state == null)
            throw new CantChangeProtocolStateException(null, "", "The state is required, can not be null");

        try {

            DatabaseTable addressExchangeRequestTable = database.getTable(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_TABLE_NAME);

            addressExchangeRequestTable.setUUIDFilter(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_ID_COLUMN_NAME, requestId, DatabaseFilterType.EQUAL);

            addressExchangeRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = addressExchangeRequestTable.getRecords();

            if (!records.isEmpty()) {
                DatabaseTableRecord record = records.get(0);

                record.setStringValue(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_STATE_COLUMN_NAME , state .getCode());

                addressExchangeRequestTable.updateRecord(record);

            } else
                throw new PendingRequestNotFoundException(null, "requestId: "+requestId, "Cannot find an address exchange request with that requestId.");

        } catch (CantUpdateRecordException e) {

            throw new CantChangeProtocolStateException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot update the record.");
        } catch (CantLoadTableToMemoryException e) {

            throw new CantChangeProtocolStateException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");

        }
    }

    private DatabaseTableRecord buildDatabaseRecord(final DatabaseTableRecord                                 record                ,
                                                    final CryptoAddressesNetworkServiceAddressExchangeRequest addressExchangeRequest) {

        record.setUUIDValue  (CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_ID_COLUMN_NAME                            , addressExchangeRequest.getRequestId()                            );
        record.setStringValue(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_WALLET_PUBLIC_KEY_COLUMN_NAME             , addressExchangeRequest.getWalletPublicKey()                      );
        record.setStringValue(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_IDENTITY_TYPE_REQUESTING_COLUMN_NAME      , addressExchangeRequest.getIdentityTypeRequesting()     .getCode());
        record.setStringValue(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_IDENTITY_TYPE_RESPONDING_COLUMN_NAME      , addressExchangeRequest.getIdentityTypeResponding()     .getCode());
        record.setStringValue(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_IDENTITY_PUBLIC_KEY_REQUESTING_COLUMN_NAME, addressExchangeRequest.getIdentityPublicKeyRequesting()          );
        record.setStringValue(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_IDENTITY_PUBLIC_KEY_RESPONDING_COLUMN_NAME, addressExchangeRequest.getIdentityPublicKeyResponding()          );
        record.setStringValue(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_CRYPTO_CURRENCY_COLUMN_NAME               , addressExchangeRequest.getCryptoCurrency()             .getCode());
        record.setStringValue(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_STATE_COLUMN_NAME                         , addressExchangeRequest.getState()                      .getCode());
        record.setStringValue(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_TYPE_COLUMN_NAME                          , addressExchangeRequest.getType()                       .getCode());
        record.setStringValue(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_ACTION_COLUMN_NAME                        , addressExchangeRequest.getAction()                     .getCode());
        record.setStringValue(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_BLOCKCHAIN_NETWORK_TYPE_COLUMN_NAME       , addressExchangeRequest.getBlockchainNetworkType()      .getCode());

        if (addressExchangeRequest.getCryptoAddress() != null)
            record.setStringValue(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_CRYPTO_ADDRESS_COLUMN_NAME, addressExchangeRequest.getCryptoAddress().getAddress());

        return record;
    }

    private AddressExchangeRequest buildAddressExchangeRequestRecord(DatabaseTableRecord record) throws InvalidParameterException {

        UUID   requestId                    = record.getUUIDValue  (CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_ID_COLUMN_NAME                            );
        String walletPublicKey              = record.getStringValue(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_WALLET_PUBLIC_KEY_COLUMN_NAME             );
        String identityTypeRequestingString = record.getStringValue(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_IDENTITY_TYPE_REQUESTING_COLUMN_NAME      );
        String identityTypeRespondingString = record.getStringValue(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_IDENTITY_TYPE_RESPONDING_COLUMN_NAME      );
        String identityPublicKeyRequesting  = record.getStringValue(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_IDENTITY_PUBLIC_KEY_REQUESTING_COLUMN_NAME);
        String identityPublicKeyResponding  = record.getStringValue(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_IDENTITY_PUBLIC_KEY_RESPONDING_COLUMN_NAME);
        String cryptoCurrencyString         = record.getStringValue(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_CRYPTO_CURRENCY_COLUMN_NAME               );
        String cryptoAddressString          = record.getStringValue(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_CRYPTO_ADDRESS_COLUMN_NAME                );
        String stateString                  = record.getStringValue(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_STATE_COLUMN_NAME                         );
        String typeString                   = record.getStringValue(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_TYPE_COLUMN_NAME                          );
        String actionString                 = record.getStringValue(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_ACTION_COLUMN_NAME                        );
        String blockchainNetworkTypeString  = record.getStringValue(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_BLOCKCHAIN_NETWORK_TYPE_COLUMN_NAME       );

        Actors                identityTypeRequesting = Actors               .getByCode(identityTypeRequestingString);
        Actors                identityTypeAccepting  = Actors               .getByCode(identityTypeRespondingString);
        BlockchainNetworkType blockchainNetworkType  = BlockchainNetworkType.getByCode(blockchainNetworkTypeString);
        ProtocolState         state                  = ProtocolState        .getByCode(stateString);
        RequestType           type                   = RequestType          .getByCode(typeString);
        RequestAction         action                 = RequestAction        .getByCode(actionString);
        CryptoCurrency        cryptoCurrency         = CryptoCurrency       .getByCode(cryptoCurrencyString);

        CryptoAddress         cryptoAddress          = new CryptoAddress(cryptoAddressString , cryptoCurrency);

        return new CryptoAddressesNetworkServiceAddressExchangeRequest(
                requestId                  ,
                walletPublicKey            ,
                identityTypeRequesting     ,
                identityTypeAccepting      ,
                identityPublicKeyRequesting,
                identityPublicKeyResponding,
                cryptoCurrency             ,
                cryptoAddress              ,
                state                      ,
                type                       ,
                action                     ,
                blockchainNetworkType
        );
    }
}