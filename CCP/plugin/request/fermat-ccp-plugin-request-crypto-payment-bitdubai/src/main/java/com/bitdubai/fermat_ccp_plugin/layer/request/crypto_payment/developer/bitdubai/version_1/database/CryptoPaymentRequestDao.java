package com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.enums.CryptoPaymentState;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.enums.CryptoPaymentType;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.CantGetCryptoPaymentRequestException;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.CantListCryptoPaymentRequestsException;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.CryptoPaymentRequestNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.interfaces.CryptoPayment;
import com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.exceptions.CantChangeCryptoPaymentRequestStateException;
import com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.exceptions.CantInitializeCryptoPaymentRequestDatabaseException;
import com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.structure.CryptoPaymentRequestRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The class <code>com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.database.CryptoPaymentRequestDao</code>
 * haves all the methods with access to database.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 01/10/2015.
 */
public class CryptoPaymentRequestDao {

    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID                 pluginId            ;

    private Database database;

    public CryptoPaymentRequestDao(final PluginDatabaseSystem pluginDatabaseSystem,
                                   final UUID pluginId) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId             = pluginId            ;
    }

    public void initialize() throws CantInitializeCryptoPaymentRequestDatabaseException {
        try {

            database = this.pluginDatabaseSystem.openDatabase(
                    this.pluginId,
                    this.pluginId.toString()
            );

        } catch (DatabaseNotFoundException e) {

            try {

                CryptoPaymentRequestDatabaseFactory databaseFactory = new CryptoPaymentRequestDatabaseFactory(pluginDatabaseSystem);
                database = databaseFactory.createDatabase(
                        pluginId,
                        pluginId.toString()
                );

            } catch (CantCreateDatabaseException f) {

                throw new CantInitializeCryptoPaymentRequestDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, f, "", "There is a problem and i cannot create the database.");
            } catch (Exception z) {

                throw new CantInitializeCryptoPaymentRequestDatabaseException(z, "", "Generic Exception.");
            }

        } catch (CantOpenDatabaseException e) {

            throw new CantInitializeCryptoPaymentRequestDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (Exception e) {

            throw new CantInitializeCryptoPaymentRequestDatabaseException(e, "", "Generic Exception.");
        }
    }

    public CryptoPayment getPendingRequest(UUID requestId) throws CantGetCryptoPaymentRequestException  ,
                                                                  CryptoPaymentRequestNotFoundException {

        if (requestId == null)
            throw new CantGetCryptoPaymentRequestException("", "requestId, can not be null");

        try {

            DatabaseTable cryptoPaymentRequestTable = database.getTable(CryptoPaymentRequestDatabaseConstants.CRYPTO_PAYMENT_REQUEST_TABLE_NAME);

            cryptoPaymentRequestTable.setUUIDFilter(CryptoPaymentRequestDatabaseConstants.CRYPTO_PAYMENT_REQUEST_REQUEST_ID_COLUMN_NAME, requestId, DatabaseFilterType.EQUAL);

            cryptoPaymentRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoPaymentRequestTable.getRecords();


            if (!records.isEmpty())
                return buildCryptoPaymentRequestRecord(records.get(0));
            else
                throw new CryptoPaymentRequestNotFoundException(null, "RequestID: "+requestId, "Can not find an crypto payment request with the given request id.");


        } catch (CantLoadTableToMemoryException exception) {

            throw new CantGetCryptoPaymentRequestException(exception, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (InvalidParameterException exception) {

            throw new CantGetCryptoPaymentRequestException(exception, "", "Check the cause."                                                                                );
        }
    }

    public void changeState(UUID               requestId         ,
                            CryptoPaymentState cryptoPaymentState) throws CantChangeCryptoPaymentRequestStateException,
                                                                          CryptoPaymentRequestNotFoundException       {

        if (requestId == null)
            throw new CantChangeCryptoPaymentRequestStateException("requestId null ", "The requestId is required, can not be null");

        if (cryptoPaymentState == null)
            throw new CantChangeCryptoPaymentRequestStateException("cryptoPaymentState null", "The cryptoPaymentState is required, can not be null");

        try {

            DatabaseTable cryptoPaymentRequestTable = database.getTable(CryptoPaymentRequestDatabaseConstants.CRYPTO_PAYMENT_REQUEST_TABLE_NAME);

            cryptoPaymentRequestTable.setUUIDFilter(CryptoPaymentRequestDatabaseConstants.CRYPTO_PAYMENT_REQUEST_REQUEST_ID_COLUMN_NAME, requestId, DatabaseFilterType.EQUAL);

            cryptoPaymentRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoPaymentRequestTable.getRecords();

            if (!records.isEmpty()) {
                DatabaseTableRecord record = records.get(0);

                record.setStringValue(CryptoPaymentRequestDatabaseConstants.CRYPTO_PAYMENT_REQUEST_STATE_COLUMN_NAME, cryptoPaymentState.getCode());

                cryptoPaymentRequestTable.updateRecord(record);
            } else {
                throw new CryptoPaymentRequestNotFoundException("RequestId: "+requestId, "Cannot find a CryptoPaymentRequest with the given id.");
            }

        } catch (CantLoadTableToMemoryException e) {

            throw new CantChangeCryptoPaymentRequestStateException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (CantUpdateRecordException exception) {

            throw new CantChangeCryptoPaymentRequestStateException(exception, "", "Cant update record exception.");
        }
    }

    public List<CryptoPayment> listCryptoPaymentRequests(String  walletPublicKey,
                                                         Integer max            ,
                                                         Integer offset         ) throws CantListCryptoPaymentRequestsException {

        try {
            DatabaseTable cryptoPaymentRequestTable = database.getTable(CryptoPaymentRequestDatabaseConstants.CRYPTO_PAYMENT_REQUEST_TABLE_NAME);

            cryptoPaymentRequestTable.setStringFilter(CryptoPaymentRequestDatabaseConstants.CRYPTO_PAYMENT_REQUEST_WALLET_PUBLIC_KEY_COLUMN_NAME, walletPublicKey, DatabaseFilterType.EQUAL);

            cryptoPaymentRequestTable.setFilterTop(max.toString());
            cryptoPaymentRequestTable.setFilterOffSet(offset.toString());

            cryptoPaymentRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoPaymentRequestTable.getRecords();

            List<CryptoPayment> cryptoPaymentList = new ArrayList<>();

            for (DatabaseTableRecord record : records) {
                cryptoPaymentList.add(buildCryptoPaymentRequestRecord(record));
            }
            return cryptoPaymentList;

        } catch (CantLoadTableToMemoryException e) {

            throw new CantListCryptoPaymentRequestsException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch(InvalidParameterException exception){

            throw new CantListCryptoPaymentRequestsException(exception);
        }
    }

    private DatabaseTableRecord buildDatabaseRecord(DatabaseTableRecord        record                    ,
                                                    CryptoPaymentRequestRecord cryptoPaymentRequestRecord) {

        record.setUUIDValue  (CryptoPaymentRequestDatabaseConstants.CRYPTO_PAYMENT_REQUEST_REQUEST_ID_COLUMN_NAME         , cryptoPaymentRequestRecord.getRequestId()                                      );
        record.setStringValue(CryptoPaymentRequestDatabaseConstants.CRYPTO_PAYMENT_REQUEST_WALLET_PUBLIC_KEY_COLUMN_NAME  , cryptoPaymentRequestRecord.getWalletPublicKey()                                );
        record.setStringValue(CryptoPaymentRequestDatabaseConstants.CRYPTO_PAYMENT_REQUEST_IDENTITY_PUBLIC_KEY_COLUMN_NAME, cryptoPaymentRequestRecord.getIdentityPublicKey()                              );
        record.setStringValue(CryptoPaymentRequestDatabaseConstants.CRYPTO_PAYMENT_REQUEST_ACTOR_PUBLIC_KEY_COLUMN_NAME   , cryptoPaymentRequestRecord.getActorPublicKey()                                 );
        record.setStringValue(CryptoPaymentRequestDatabaseConstants.CRYPTO_PAYMENT_REQUEST_DESCRIPTION_COLUMN_NAME        , cryptoPaymentRequestRecord.getDescription()                                    );
        record.setStringValue(CryptoPaymentRequestDatabaseConstants.CRYPTO_PAYMENT_REQUEST_CRYPTO_ADDRESS_COLUMN_NAME     , cryptoPaymentRequestRecord.getCryptoAddress()    .getAddress()                 );
        record.setStringValue(CryptoPaymentRequestDatabaseConstants.CRYPTO_PAYMENT_REQUEST_CRYPTO_CURRENCY_COLUMN_NAME    , cryptoPaymentRequestRecord.getCryptoAddress()    .getCryptoCurrency().getCode());
        record.setStringValue(CryptoPaymentRequestDatabaseConstants.CRYPTO_PAYMENT_REQUEST_TYPE_COLUMN_NAME               , cryptoPaymentRequestRecord.getType()             .getCode()                    );
        record.setStringValue(CryptoPaymentRequestDatabaseConstants.CRYPTO_PAYMENT_REQUEST_STATE_COLUMN_NAME              , cryptoPaymentRequestRecord.getState()            .getCode()                    );
        record.setLongValue  (CryptoPaymentRequestDatabaseConstants.CRYPTO_PAYMENT_REQUEST_AMOUNT_COLUMN_NAME             , cryptoPaymentRequestRecord.getAmount()                                         );
        record.setLongValue  (CryptoPaymentRequestDatabaseConstants.CRYPTO_PAYMENT_REQUEST_START_TIME_STAMP_COLUMN_NAME   , cryptoPaymentRequestRecord.getStartTimeStamp()                                 );
        record.setLongValue  (CryptoPaymentRequestDatabaseConstants.CRYPTO_PAYMENT_REQUEST_END_TIME_STAMP_COLUMN_NAME     , cryptoPaymentRequestRecord.getEndTimeStamp()                                   );

        return record;
    }

    private CryptoPayment buildCryptoPaymentRequestRecord(DatabaseTableRecord record) throws InvalidParameterException {

        UUID   requestId            = record.getUUIDValue  (CryptoPaymentRequestDatabaseConstants.CRYPTO_PAYMENT_REQUEST_REQUEST_ID_COLUMN_NAME         );
        String walletPublicKey      = record.getStringValue(CryptoPaymentRequestDatabaseConstants.CRYPTO_PAYMENT_REQUEST_WALLET_PUBLIC_KEY_COLUMN_NAME  );
        String identityPublicKey    = record.getStringValue(CryptoPaymentRequestDatabaseConstants.CRYPTO_PAYMENT_REQUEST_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        String actorPublicKey       = record.getStringValue(CryptoPaymentRequestDatabaseConstants.CRYPTO_PAYMENT_REQUEST_ACTOR_PUBLIC_KEY_COLUMN_NAME   );
        String description          = record.getStringValue(CryptoPaymentRequestDatabaseConstants.CRYPTO_PAYMENT_REQUEST_DESCRIPTION_COLUMN_NAME        );
        String cryptoAddressString  = record.getStringValue(CryptoPaymentRequestDatabaseConstants.CRYPTO_PAYMENT_REQUEST_CRYPTO_ADDRESS_COLUMN_NAME     );
        String cryptoCurrencyString = record.getStringValue(CryptoPaymentRequestDatabaseConstants.CRYPTO_PAYMENT_REQUEST_CRYPTO_CURRENCY_COLUMN_NAME    );
        String typeString           = record.getStringValue(CryptoPaymentRequestDatabaseConstants.CRYPTO_PAYMENT_REQUEST_TYPE_COLUMN_NAME               );
        String stateString          = record.getStringValue(CryptoPaymentRequestDatabaseConstants.CRYPTO_PAYMENT_REQUEST_STATE_COLUMN_NAME              );

        long   amount               = record.getLongValue  (CryptoPaymentRequestDatabaseConstants.CRYPTO_PAYMENT_REQUEST_AMOUNT_COLUMN_NAME             );
        long   startTimeStamp       = record.getLongValue  (CryptoPaymentRequestDatabaseConstants.CRYPTO_PAYMENT_REQUEST_START_TIME_STAMP_COLUMN_NAME   );
        long   endTimeStamp         = record.getLongValue  (CryptoPaymentRequestDatabaseConstants.CRYPTO_PAYMENT_REQUEST_END_TIME_STAMP_COLUMN_NAME     );

        CryptoAddress cryptoAddress = new CryptoAddress(cryptoAddressString, CryptoCurrency.getByCode(cryptoCurrencyString));

        CryptoPaymentType  type  = CryptoPaymentType   .getByCode(typeString) ;
        CryptoPaymentState state = CryptoPaymentState  .getByCode(stateString);

        return new CryptoPaymentRequestRecord(
                requestId        ,
                walletPublicKey  ,
                identityPublicKey,
                actorPublicKey   ,
                description      ,
                cryptoAddress    ,
                amount           ,
                startTimeStamp   ,
                endTimeStamp     ,
                type             ,
                state
        );
    }

}
