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
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.enums.AddressExchangeRequestState;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantAcceptAddressExchangeRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantConfirmAddressExchangeRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantDenyAddressExchangeRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantGetPendingAddressExchangeRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantListPendingAddressExchangeRequestsException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantSendAddressExchangeRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.PendingRequestNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.AddressExchangeRequest;
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


    public void sendAddressExchangeRequest(String                walletPublicKey            ,
                                           CryptoAddress         cryptoAddressFromRequest   ,
                                           Actors                identityTypeRequesting     ,
                                           Actors                identityTypeAccepting      ,
                                           String                identityPublicKeyRequesting,
                                           String                identityPublicKeyAccepting ,
                                           BlockchainNetworkType blockchainNetworkType      ) throws CantSendAddressExchangeRequestException {

        try {

            UUID newId = UUID.randomUUID();

            CryptoAddress emptyAddress = new CryptoAddress("", cryptoAddressFromRequest.getCryptoCurrency());

            AddressExchangeRequestState creationState = AddressExchangeRequestState.PENDING_REMOTE_RESPONSE;

            CryptoAddressesNetworkServiceAddressExchangeRequest addressExchangeRequest = new CryptoAddressesNetworkServiceAddressExchangeRequest(
                    newId                      ,
                    walletPublicKey            ,
                    identityTypeRequesting     ,
                    identityTypeAccepting      ,
                    identityPublicKeyRequesting,
                    identityPublicKeyAccepting ,
                    cryptoAddressFromRequest   ,
                    emptyAddress               ,
                    blockchainNetworkType      ,
                    creationState
            );

            DatabaseTable addressExchangeRequestTable = database.getTable(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_TABLE_NAME);
            DatabaseTableRecord entityRecord = addressExchangeRequestTable.getEmptyRecord();

            entityRecord = buildDatabaseRecord(entityRecord, addressExchangeRequest);

            addressExchangeRequestTable.insertRecord(entityRecord);

        } catch (CantInsertRecordException e) {

            throw new CantSendAddressExchangeRequestException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.");
        }
    }

    public List<AddressExchangeRequest> listPendingRequests(Actors                      actorType                  ,
                                                            AddressExchangeRequestState addressExchangeRequestState) throws CantListPendingAddressExchangeRequestsException {

        if (actorType == null)
            throw new CantListPendingAddressExchangeRequestsException(null, "", "actorType, can not be null");

        try {

            DatabaseTable addressExchangeRequestTable = database.getTable(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_TABLE_NAME);

            addressExchangeRequestTable.setStringFilter(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_IDENTITY_TYPE_ACCEPTING_COLUMN_NAME, actorType.getCode(), DatabaseFilterType.EQUAL);

            if (addressExchangeRequestState != null)
                addressExchangeRequestTable.setStringFilter(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_STATE_COLUMN_NAME, addressExchangeRequestState.getCode(), DatabaseFilterType.EQUAL);

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

            DatabaseTable addressExchangeRequestTable = database.getTable(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_TABLE_NAME);

            addressExchangeRequestTable.setUUIDFilter(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_REQUEST_ID_COLUMN_NAME, requestId, DatabaseFilterType.EQUAL);

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

    public void confirmAddressExchangeRequest(UUID requestId) throws CantConfirmAddressExchangeRequestException,
                                                                     PendingRequestNotFoundException           {

        if (requestId == null) {
            throw new CantConfirmAddressExchangeRequestException(null, "", "The requestId is required, can not be null");
        }

        try {
            DatabaseTable addressExchangeRequestTable = database.getTable(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_TABLE_NAME);

            addressExchangeRequestTable.setUUIDFilter(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_REQUEST_ID_COLUMN_NAME, requestId, DatabaseFilterType.EQUAL);

            addressExchangeRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = addressExchangeRequestTable.getRecords();

            // todo maybe i have to check if the request is in the proper state
            if (!records.isEmpty())
                addressExchangeRequestTable.deleteRecord(records.get(0));
             else
                throw new PendingRequestNotFoundException(null, "requestId: "+requestId, "Cannot find an address exchange request with that requestId.");

        } catch (CantDeleteRecordException e) {

            throw new CantConfirmAddressExchangeRequestException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot delete the record.");
        } catch (CantLoadTableToMemoryException e) {

            throw new CantConfirmAddressExchangeRequestException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");

        }
    }

    private DatabaseTableRecord buildDatabaseRecord(DatabaseTableRecord                                 record                ,
                                                    CryptoAddressesNetworkServiceAddressExchangeRequest addressExchangeRequest) {

        record.setUUIDValue  (CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_REQUEST_ID_COLUMN_NAME                    , addressExchangeRequest.getRequestId()                                                );
        record.setStringValue(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_WALLET_PUBLIC_KEY_COLUMN_NAME             , addressExchangeRequest.getWalletPublicKey()                                          );
        record.setStringValue(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_IDENTITY_TYPE_REQUESTING_COLUMN_NAME      , addressExchangeRequest.getIdentityTypeRequesting()     .getCode()                    );
        record.setStringValue(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_IDENTITY_TYPE_ACCEPTING_COLUMN_NAME       , addressExchangeRequest.getIdentityTypeAccepting()      .getCode()                    );
        record.setStringValue(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_IDENTITY_PUBLIC_KEY_REQUESTING_COLUMN_NAME, addressExchangeRequest.getIdentityPublicKeyRequesting()                              );
        record.setStringValue(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_IDENTITY_PUBLIC_KEY_ACCEPTING_COLUMN_NAME , addressExchangeRequest.getIdentityPublicKeyAccepting()                               );
        record.setStringValue(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_CRYPTO_ADDRESS_FROM_REQUEST_COLUMN_NAME   , addressExchangeRequest.getCryptoAddressFromRequest()   .getAddress()                 );
        record.setStringValue(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_CRYPTO_ADDRESS_FROM_RESPONSE_COLUMN_NAME  , addressExchangeRequest.getCryptoAddressFromResponse()  .getAddress()                 );
        record.setStringValue(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_CRYPTO_CURRENCY_COLUMN_NAME               , addressExchangeRequest.getCryptoAddressFromRequest()   .getCryptoCurrency().getCode());
        record.setStringValue(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_BLOCKCHAIN_NETWORK_TYPE_COLUMN_NAME       , addressExchangeRequest.getBlockchainNetworkType()      .getCode()                    );
        record.setStringValue(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_STATE_COLUMN_NAME                         , addressExchangeRequest.getState()                      .getCode()                    );

        return record;
    }

    private AddressExchangeRequest buildAddressExchangeRequestRecord(DatabaseTableRecord record) throws InvalidParameterException {

        UUID   requestId                       = record.getUUIDValue  (CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_REQUEST_ID_COLUMN_NAME                    );
        String walletPublicKey                 = record.getStringValue(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_WALLET_PUBLIC_KEY_COLUMN_NAME             );
        String identityTypeRequestingString    = record.getStringValue(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_IDENTITY_TYPE_REQUESTING_COLUMN_NAME      );
        String identityTypeAcceptingString     = record.getStringValue(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_IDENTITY_TYPE_ACCEPTING_COLUMN_NAME       );
        String identityPublicKeyRequesting     = record.getStringValue(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_IDENTITY_PUBLIC_KEY_REQUESTING_COLUMN_NAME);
        String identityPublicKeyAccepting      = record.getStringValue(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_IDENTITY_PUBLIC_KEY_ACCEPTING_COLUMN_NAME );
        String cryptoAddressFromRequestString  = record.getStringValue(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_CRYPTO_ADDRESS_FROM_REQUEST_COLUMN_NAME   );
        String cryptoAddressFromResponseString = record.getStringValue(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_CRYPTO_ADDRESS_FROM_RESPONSE_COLUMN_NAME  );
        String cryptoCurrencyString            = record.getStringValue(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_CRYPTO_CURRENCY_COLUMN_NAME               );
        String blockchainNetworkTypeString     = record.getStringValue(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_BLOCKCHAIN_NETWORK_TYPE_COLUMN_NAME       );
        String stateString                     = record.getStringValue(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_STATE_COLUMN_NAME                         );

        Actors                      identityTypeRequesting = Actors                     .getByCode(identityTypeRequestingString              );
        Actors                      identityTypeAccepting  = Actors                     .getByCode(identityTypeAcceptingString               );
        BlockchainNetworkType       blockchainNetworkType  = BlockchainNetworkType      .getByCode(blockchainNetworkTypeString               );
        AddressExchangeRequestState state                  = AddressExchangeRequestState.getByCode(stateString                               );
        CryptoCurrency              cryptoCurrency         = CryptoCurrency             .getByCode(cryptoCurrencyString                      );

        CryptoAddress               cryptoAddressFromRequest  = new CryptoAddress(cryptoAddressFromRequestString , cryptoCurrency);
        CryptoAddress               cryptoAddressFromResponse = new CryptoAddress(cryptoAddressFromResponseString, cryptoCurrency);

        return new CryptoAddressesNetworkServiceAddressExchangeRequest(
                requestId                  ,
                walletPublicKey            ,
                identityTypeRequesting     ,
                identityTypeAccepting      ,
                identityPublicKeyRequesting,
                identityPublicKeyAccepting ,
                cryptoAddressFromRequest   ,
                cryptoAddressFromResponse  ,
                blockchainNetworkType      ,
                state
        );
    }
}