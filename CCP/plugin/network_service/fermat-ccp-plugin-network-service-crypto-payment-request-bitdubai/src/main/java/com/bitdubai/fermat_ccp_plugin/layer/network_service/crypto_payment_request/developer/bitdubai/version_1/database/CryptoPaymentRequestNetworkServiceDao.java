package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.enums.RequestAction;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.enums.RequestType;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.enums.RequestProtocolState;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.exceptions.CantGetRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.exceptions.RequestNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.interfaces.CryptoPaymentRequest;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.exceptions.CantChangeRequestProtocolStateException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.exceptions.CantChangeRequestSentCountException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.exceptions.CantDeletePaymentRequestException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.exceptions.CantTakeActionException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.exceptions.CantCreateCryptoPaymentRequestException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.exceptions.CantInitializeCryptoPaymentRequestNetworkServiceDatabaseException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.exceptions.CantListRequestsException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.structure.CryptoPaymentRequestNetworkServiceRecord;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.structure.PaymentConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The class <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.database.CryptoPaymentRequestNetworkServiceDao</code>
 * haves all the methods with access to database.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 01/10/2015.
 */
public final class CryptoPaymentRequestNetworkServiceDao {

    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID                 pluginId            ;

    private Database database;

    public CryptoPaymentRequestNetworkServiceDao(final PluginDatabaseSystem pluginDatabaseSystem,
                                                 final UUID                 pluginId            ) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId             = pluginId            ;
    }

    public final void initialize() throws CantInitializeCryptoPaymentRequestNetworkServiceDatabaseException {
        try {

            database = this.pluginDatabaseSystem.openDatabase(
                    this.pluginId,
                    this.pluginId.toString()
            );

        } catch (DatabaseNotFoundException e) {

            try {

                CryptoPaymentRequestNetworkServiceDatabaseFactory databaseFactory = new CryptoPaymentRequestNetworkServiceDatabaseFactory(pluginDatabaseSystem);
                database = databaseFactory.createDatabase(
                        pluginId,
                        pluginId.toString()
                );

            } catch (final CantCreateDatabaseException f) {

                throw new CantInitializeCryptoPaymentRequestNetworkServiceDatabaseException(f, "", "There is a problem and i cannot create the database.");
            } catch (final Exception z) {

                throw new CantInitializeCryptoPaymentRequestNetworkServiceDatabaseException(z, "", "Unhandled Exception.");
            }

        } catch (final CantOpenDatabaseException e) {

            throw new CantInitializeCryptoPaymentRequestNetworkServiceDatabaseException(e, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (final Exception e) {

            throw new CantInitializeCryptoPaymentRequestNetworkServiceDatabaseException(e, "", "Unhandled Exception.");
        }
    }

    public final void createCryptoPaymentRequest(final UUID                        requestId        ,
                                                 final String                      identityPublicKey,
                                                 final Actors                      identityType     ,
                                                 final String                      actorPublicKey   ,
                                                 final Actors                      actorType        ,
                                                 final CryptoAddress               cryptoAddress    ,
                                                 final String                      description      ,
                                                 final long                        amount           ,
                                                 final long                        startTimeStamp   ,
                                                 final RequestType                 type             ,
                                                 final RequestAction               action           ,
                                                 final RequestProtocolState        protocolState    ,
                                                 final BlockchainNetworkType       networkType      ,
                                                 final ReferenceWallet              referenceWallet,
                                                 final int                          sentNumber,
                                                 final String                       messageType,
                                                 final String                       walletPublicKey,
                                                 final CryptoCurrency               cryptoCurrency) throws CantCreateCryptoPaymentRequestException {

        try {

            if(!existRequest(requestId))
            {
                DatabaseTable cryptoPaymentRequestTable = database.getTable(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_TABLE_NAME);

                DatabaseTableRecord entityRecord = cryptoPaymentRequestTable.getEmptyRecord();

                CryptoPaymentRequestNetworkServiceRecord cryptoPaymentRequestRecord = new CryptoPaymentRequestNetworkServiceRecord(
                        requestId        ,
                        identityPublicKey,
                        identityType     ,
                        actorPublicKey   ,
                        actorType        ,
                        description      ,
                        cryptoAddress    ,
                        amount           ,
                        startTimeStamp   ,
                        type             ,
                        action           ,
                        protocolState    ,
                        networkType,
                        referenceWallet,
                        sentNumber,
                        messageType,
                        walletPublicKey,
                        cryptoCurrency);

                cryptoPaymentRequestTable.insertRecord(buildDatabaseRecord(entityRecord, cryptoPaymentRequestRecord));
            }


        } catch (CantInsertRecordException e) {

            throw new CantCreateCryptoPaymentRequestException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.");

        } catch (CantGetRequestException e) {
            throw new CantCreateCryptoPaymentRequestException(e, "", "Exception not handled by the plugin, there is a problem in database.");

        }
    }

    public boolean isPendingRequestByProtocolState(final RequestProtocolState protocolState) throws CantListRequestsException {

        if (protocolState == null)
            throw new CantListRequestsException(null, "", "protocolState, can not be null");

        try {

            DatabaseTable table = database.getTable(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_TABLE_NAME);

            table.addStringFilter(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_PROTOCOL_STATE_COLUMN_NAME, protocolState.getCode(), DatabaseFilterType.EQUAL);

            table.setFilterTop("1");

            table.loadToMemory();

            return (!table.getRecords().isEmpty());

        } catch (CantLoadTableToMemoryException exception) {

            throw new CantListRequestsException(exception, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        }
    }

    public CryptoPaymentRequest getRequestById(final UUID requestId) throws CantGetRequestException  ,
                                                                      RequestNotFoundException {

        if (requestId == null)
            throw new CantGetRequestException("", "requestId, can not be null");

        try {

            DatabaseTable cryptoPaymentRequestTable = database.getTable(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_TABLE_NAME);

            cryptoPaymentRequestTable.addUUIDFilter(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_REQUEST_ID_COLUMN_NAME, requestId, DatabaseFilterType.EQUAL);

            cryptoPaymentRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoPaymentRequestTable.getRecords();


            if (!records.isEmpty())
                return buildCryptoPaymentRequestRecord(records.get(0));
            else
                throw new RequestNotFoundException(null, "RequestID: "+requestId, "Can not find an crypto payment request with the given request id.");


        } catch (CantLoadTableToMemoryException exception) {

            throw new CantGetRequestException(exception, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (InvalidParameterException exception) {

            throw new CantGetRequestException(exception, "", "Check the cause."                                                                                );
        }
    }

    public boolean existRequest(final UUID requestId) throws CantGetRequestException  {


        try {

            DatabaseTable cryptoPaymentRequestTable = database.getTable(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_TABLE_NAME);

            cryptoPaymentRequestTable.addUUIDFilter(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_REQUEST_ID_COLUMN_NAME, requestId, DatabaseFilterType.EQUAL);

            cryptoPaymentRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoPaymentRequestTable.getRecords();


            return !records.isEmpty();

        } catch (CantLoadTableToMemoryException exception) {

            throw new CantGetRequestException(exception, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        }
    }


    public void takeAction(final UUID                       requestId    ,
                           final RequestAction              action       ,
                           final RequestProtocolState       protocolState) throws CantTakeActionException  ,
                                                                            RequestNotFoundException {

        if (requestId == null)
            throw new CantTakeActionException("requestId null "   , "The requestId is required, can not be null"    );

        if (protocolState == null)
            throw new CantTakeActionException("protocolState null", "The protocolState is required, can not be null");

        if (action == null)
            throw new CantTakeActionException("action null"       , "The action is required, can not be null"       );

        try {

            DatabaseTable cryptoPaymentRequestTable = database.getTable(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_TABLE_NAME);

            cryptoPaymentRequestTable.addUUIDFilter(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_REQUEST_ID_COLUMN_NAME, requestId, DatabaseFilterType.EQUAL);

            cryptoPaymentRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoPaymentRequestTable.getRecords();

            if (!records.isEmpty()) {

                DatabaseTableRecord record = records.get(0);

                record.setStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_PROTOCOL_STATE_COLUMN_NAME, protocolState.getCode());
                record.setStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_ACTION_COLUMN_NAME        , action       .getCode());

                cryptoPaymentRequestTable.updateRecord(record);

            } else {
                throw new RequestNotFoundException("RequestId: "+requestId, "Cannot find a CryptoPaymentRequest with the given id.");
            }

        } catch (CantLoadTableToMemoryException e) {

            throw new CantTakeActionException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (CantUpdateRecordException exception) {

            throw new CantTakeActionException(exception, "", "Cant update record exception.");
        }
    }

    public void changeProtocolState(final UUID                 requestId    ,
                                    final RequestProtocolState protocolState) throws CantChangeRequestProtocolStateException,
                                                                               RequestNotFoundException                            {

        if (requestId == null)
            throw new CantChangeRequestProtocolStateException("requestId null "   , "The requestId is required, can not be null"    );

        if (protocolState == null)
            throw new CantChangeRequestProtocolStateException("protocolState null", "The protocolState is required, can not be null");

        try {

            DatabaseTable cryptoPaymentRequestTable = database.getTable(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_TABLE_NAME);

            cryptoPaymentRequestTable.addUUIDFilter(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_REQUEST_ID_COLUMN_NAME, requestId, DatabaseFilterType.EQUAL);

            cryptoPaymentRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoPaymentRequestTable.getRecords();

            if (!records.isEmpty()) {
                DatabaseTableRecord record = records.get(0);

                record.setStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_PROTOCOL_STATE_COLUMN_NAME, protocolState.getCode());

                cryptoPaymentRequestTable.updateRecord(record);
            } else {
                throw new RequestNotFoundException("RequestId: "+requestId, "Cannot find a CryptoPaymentRequest with the given id.");
            }

        } catch (CantLoadTableToMemoryException e) {

            throw new CantChangeRequestProtocolStateException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (CantUpdateRecordException exception) {

            throw new CantChangeRequestProtocolStateException(exception, "", "Cant update record exception.");
        }
    }

    public final List<CryptoPaymentRequest> listRequestsByProtocolState(final RequestProtocolState protocolState) throws CantListRequestsException {

        if (protocolState == null)
            throw new CantListRequestsException("protocolState null", "The protocolState is required, can not be null");

        try {
            DatabaseTable cryptoPaymentRequestTable = database.getTable(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_TABLE_NAME);

            cryptoPaymentRequestTable.addStringFilter(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_PROTOCOL_STATE_COLUMN_NAME, protocolState.getCode(), DatabaseFilterType.EQUAL);

            cryptoPaymentRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoPaymentRequestTable.getRecords();

            List<CryptoPaymentRequest> cryptoPaymentList = new ArrayList<>();

            for (DatabaseTableRecord record : records) {
                cryptoPaymentList.add(buildCryptoPaymentRequestRecord(record));
            }
            return cryptoPaymentList;

        } catch (CantLoadTableToMemoryException e) {

            throw new CantListRequestsException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch(InvalidParameterException exception){

            throw new CantListRequestsException(exception);
        }
    }

    public final List<CryptoPaymentRequest> listUncompletedRequest() throws CantListRequestsException {


        try {
            DatabaseTable cryptoPaymentRequestTable = database.getTable(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_TABLE_NAME);

            cryptoPaymentRequestTable.addStringFilter(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_PROTOCOL_STATE_COLUMN_NAME, RequestProtocolState.DONE.getCode(), DatabaseFilterType.NOT_EQUALS);
            cryptoPaymentRequestTable.addStringFilter(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_MESSAGE_TYPE_COLUMN_NAME, PaymentConstants.OUTGOING_MESSAGE, DatabaseFilterType.EQUAL);

            cryptoPaymentRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoPaymentRequestTable.getRecords();

            List<CryptoPaymentRequest> cryptoPaymentList = new ArrayList<>();

            for (DatabaseTableRecord record : records) {
                cryptoPaymentList.add(buildCryptoPaymentRequestRecord(record));
            }
            return cryptoPaymentList;

        } catch (CantLoadTableToMemoryException e) {

            throw new CantListRequestsException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch(InvalidParameterException exception){

            throw new CantListRequestsException(exception);
        }
    }

    public final List<CryptoPaymentRequest> listRequestsByActorPublicKey(final String actorPublicKey) throws CantListRequestsException {


        try {
            DatabaseTable cryptoPaymentRequestTable = database.getTable(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_TABLE_NAME);

            cryptoPaymentRequestTable.addStringFilter(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_ACTOR_PUBLIC_KEY_COLUMN_NAME, actorPublicKey, DatabaseFilterType.EQUAL);

            cryptoPaymentRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoPaymentRequestTable.getRecords();

            List<CryptoPaymentRequest> cryptoPaymentList = new ArrayList<>();

            for (DatabaseTableRecord record : records) {
                cryptoPaymentList.add(buildCryptoPaymentRequestRecord(record));
            }
            return cryptoPaymentList;

        } catch (CantLoadTableToMemoryException e) {

            throw new CantListRequestsException(e, "Cant List Request by Actor Public Key", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch(InvalidParameterException exception){

            throw new CantListRequestsException(exception);
        }
    }


    public void changeSentNumber(final UUID                 requestId    ,
                                    final int sentNumber) throws CantChangeRequestSentCountException,
                                                                 RequestNotFoundException
    {


        try {

            DatabaseTable cryptoPaymentRequestTable = database.getTable(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_TABLE_NAME);

            cryptoPaymentRequestTable.addUUIDFilter(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_REQUEST_ID_COLUMN_NAME, requestId, DatabaseFilterType.EQUAL);

            cryptoPaymentRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoPaymentRequestTable.getRecords();

            if (!records.isEmpty()) {
                DatabaseTableRecord record = records.get(0);

                record.setIntegerValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_SENT_COUNT_COLUMN_NAME, sentNumber);

                cryptoPaymentRequestTable.updateRecord(record);
            } else {
                throw new RequestNotFoundException("RequestId: "+requestId, "Cannot find a CryptoPaymentRequest with the given id.");
            }

        } catch (CantLoadTableToMemoryException e) {

            throw new CantChangeRequestSentCountException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (CantUpdateRecordException exception) {

            throw new CantChangeRequestSentCountException(exception, "", "Cant update record exception.");
        }
    }


    public void delete(final UUID requestId) throws CantDeletePaymentRequestException,
                                                    RequestNotFoundException
    {


        try {

            DatabaseTable cryptoPaymentRequestTable = database.getTable(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_TABLE_NAME);

            cryptoPaymentRequestTable.addUUIDFilter(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_REQUEST_ID_COLUMN_NAME, requestId, DatabaseFilterType.EQUAL);

            cryptoPaymentRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoPaymentRequestTable.getRecords();

            if (!records.isEmpty()) {
                DatabaseTableRecord record = records.get(0);

                cryptoPaymentRequestTable.deleteRecord(record);
            } else {
                throw new RequestNotFoundException("RequestId: "+requestId, "Cannot find a CryptoPaymentRequest with the given id.");
            }

        } catch (CantLoadTableToMemoryException e) {

            throw new CantDeletePaymentRequestException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");

        } catch (CantDeleteRecordException e) {
            throw new CantDeletePaymentRequestException(e, "", "Cant delete record exception.");
        }
    }


    private DatabaseTableRecord buildDatabaseRecord(DatabaseTableRecord                      record                    ,
                                                    CryptoPaymentRequestNetworkServiceRecord cryptoPaymentRequestRecord) {

        record.setUUIDValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_REQUEST_ID_COLUMN_NAME, cryptoPaymentRequestRecord.getRequestId());
        record.setStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_IDENTITY_PUBLIC_KEY_COLUMN_NAME, cryptoPaymentRequestRecord.getIdentityPublicKey());
        record.setStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_IDENTITY_TYPE_COLUMN_NAME      , cryptoPaymentRequestRecord.getIdentityType()     .getCode()                    );
        record.setStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_ACTOR_PUBLIC_KEY_COLUMN_NAME   , cryptoPaymentRequestRecord.getActorPublicKey()                                 );
        record.setStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_ACTOR_TYPE_COLUMN_NAME         , cryptoPaymentRequestRecord.getActorType()        .getCode()                    );
        record.setStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_DESCRIPTION_COLUMN_NAME        , cryptoPaymentRequestRecord.getDescription()                                    );
        record.setStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_CRYPTO_ADDRESS_COLUMN_NAME     , cryptoPaymentRequestRecord.getCryptoAddress()    .getAddress()                 );
        record.setStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_CRYPTO_CURRENCY_COLUMN_NAME    , cryptoPaymentRequestRecord.getCryptoAddress()    .getCryptoCurrency().getCode());
        record.setStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_DIRECTION_COLUMN_NAME          , cryptoPaymentRequestRecord.getDirection()        .getCode()                    );
        record.setStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_PROTOCOL_STATE_COLUMN_NAME, cryptoPaymentRequestRecord.getProtocolState().getCode());
        record.setStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_ACTION_COLUMN_NAME, cryptoPaymentRequestRecord.getAction().getCode());
        record.setLongValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_AMOUNT_COLUMN_NAME, cryptoPaymentRequestRecord.getAmount());
        record.setLongValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_START_TIME_STAMP_COLUMN_NAME, cryptoPaymentRequestRecord.getStartTimeStamp());
        record.setStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_NETWORK_TYPE_COLUMN_NAME, cryptoPaymentRequestRecord.getNetworkType().getCode());
        record.setStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_WALLET_REFERENCE_TYPE_COLUMN_NAME, cryptoPaymentRequestRecord.getReferenceWallet().getCode());
        record.setIntegerValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_SENT_COUNT_COLUMN_NAME, cryptoPaymentRequestRecord.getSentNumber());
        record.setStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_MESSAGE_TYPE_COLUMN_NAME, cryptoPaymentRequestRecord.getMessageType());
        record.setStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_WALLET_PUBLIC_KEY_COLUMN_NAME, cryptoPaymentRequestRecord.getWalletPublicKey());

        return record;
    }

    private CryptoPaymentRequest buildCryptoPaymentRequestRecord(final DatabaseTableRecord record) throws InvalidParameterException {

        UUID   requestId            = record.getUUIDValue  (CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_REQUEST_ID_COLUMN_NAME         );
        String identityPublicKey    = record.getStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        String identityTypeString   = record.getStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_IDENTITY_TYPE_COLUMN_NAME      );
        String actorPublicKey       = record.getStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_ACTOR_PUBLIC_KEY_COLUMN_NAME   );
        String actorTypeString      = record.getStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_ACTOR_TYPE_COLUMN_NAME         );
        String description          = record.getStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_DESCRIPTION_COLUMN_NAME        );
        String cryptoAddressString  = record.getStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_CRYPTO_ADDRESS_COLUMN_NAME     );
        String cryptoCurrencyString = record.getStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_CRYPTO_CURRENCY_COLUMN_NAME    );
        String typeString           = record.getStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_DIRECTION_COLUMN_NAME          );
        String actionString         = record.getStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_ACTION_COLUMN_NAME             );
        String protocolStateString  = record.getStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_PROTOCOL_STATE_COLUMN_NAME     );
        String networkTypeString    = record.getStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_NETWORK_TYPE_COLUMN_NAME       );
        String referenceWallet      = record.getStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_WALLET_REFERENCE_TYPE_COLUMN_NAME      );
        long   amount               = record.getLongValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_AMOUNT_COLUMN_NAME);
        long   startTimeStamp       = record.getLongValue  (CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_START_TIME_STAMP_COLUMN_NAME   );
        int   sentNumber            = record.getIntegerValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_SENT_COUNT_COLUMN_NAME);
        String   messageType        = record.getStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_MESSAGE_TYPE_COLUMN_NAME);
        String   walletpublickey   = record.getStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_WALLET_PUBLIC_KEY_COLUMN_NAME);

        CryptoCurrency   cryptoCurrency   = CryptoCurrency.getByCode(record.getStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_CRYPTO_CURRENCY_COLUMN_NAME));

        CryptoAddress        cryptoAddress = new CryptoAddress(cryptoAddressString, CryptoCurrency.getByCode(cryptoCurrencyString));

        RequestType type          = RequestType.getByCode(typeString)         ;
        RequestAction         action        = RequestAction        .getByCode(actionString)       ;
        RequestProtocolState  protocolState = RequestProtocolState .getByCode(protocolStateString);
        BlockchainNetworkType networkType   = BlockchainNetworkType.getByCode(networkTypeString)  ;

        Actors identityType = Actors.getByCode(identityTypeString);
        Actors actorType    = Actors.getByCode(actorTypeString   );

        return new CryptoPaymentRequestNetworkServiceRecord(
                requestId        ,
                identityPublicKey,
                identityType     ,
                actorPublicKey   ,
                actorType        ,
                description      ,
                cryptoAddress    ,
                amount           ,
                startTimeStamp   ,
                type             ,
                action           ,
                protocolState    ,
                networkType,
                ReferenceWallet.getByCode(referenceWallet),
                sentNumber,
                messageType,
                walletpublickey,
                cryptoCurrency);
    }

}
