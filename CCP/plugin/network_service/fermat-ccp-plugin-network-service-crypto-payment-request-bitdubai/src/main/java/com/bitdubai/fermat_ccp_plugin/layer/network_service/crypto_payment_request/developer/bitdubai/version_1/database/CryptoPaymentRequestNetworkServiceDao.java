package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.enums.RequestProtocolState;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.interfaces.CryptoPaymentRequest;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.enums.CryptoPaymentState;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.enums.CryptoPaymentType;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.exceptions.CantInitializeCryptoPaymentRequestNetworkServiceDatabaseException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.structure.CryptoPaymentRequestNetworkServiceRecord;

import java.util.UUID;

/**
 * The class <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.database.CryptoPaymentRequestNetworkServiceDao</code>
 * haves all the methods with access to database.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 01/10/2015.
 */
public class CryptoPaymentRequestNetworkServiceDao {

    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID                 pluginId            ;

    private Database database;

    public CryptoPaymentRequestNetworkServiceDao(final PluginDatabaseSystem pluginDatabaseSystem,
                                                 final UUID                 pluginId            ) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId             = pluginId            ;
    }

    public void initialize() throws CantInitializeCryptoPaymentRequestNetworkServiceDatabaseException {
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

            } catch (CantCreateDatabaseException f) {

                throw new CantInitializeCryptoPaymentRequestNetworkServiceDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, f, "", "There is a problem and i cannot create the database.");
            } catch (Exception z) {

                throw new CantInitializeCryptoPaymentRequestNetworkServiceDatabaseException(z, "", "Generic Exception.");
            }

        } catch (CantOpenDatabaseException e) {

            throw new CantInitializeCryptoPaymentRequestNetworkServiceDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (Exception e) {

            throw new CantInitializeCryptoPaymentRequestNetworkServiceDatabaseException(e, "", "Generic Exception.");
        }
    }

    private DatabaseTableRecord buildDatabaseRecord(DatabaseTableRecord                      record                    ,
                                                    CryptoPaymentRequestNetworkServiceRecord cryptoPaymentRequestRecord) {

        record.setUUIDValue  (CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_REQUEST_ID_COLUMN_NAME         , cryptoPaymentRequestRecord.getRequestId()                                      );
        record.setStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_WALLET_PUBLIC_KEY_COLUMN_NAME  , cryptoPaymentRequestRecord.getWalletPublicKey()                                );
        record.setStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_IDENTITY_PUBLIC_KEY_COLUMN_NAME, cryptoPaymentRequestRecord.getIdentityPublicKey()                              );
        record.setStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_ACTOR_PUBLIC_KEY_COLUMN_NAME   , cryptoPaymentRequestRecord.getActorPublicKey()                                 );
        record.setStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_DESCRIPTION_COLUMN_NAME        , cryptoPaymentRequestRecord.getDescription()                                    );
        record.setStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_CRYPTO_ADDRESS_COLUMN_NAME     , cryptoPaymentRequestRecord.getCryptoAddress()    .getAddress()                 );
        record.setStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_CRYPTO_CURRENCY_COLUMN_NAME    , cryptoPaymentRequestRecord.getCryptoAddress()    .getCryptoCurrency().getCode());
        record.setStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_TYPE_COLUMN_NAME               , cryptoPaymentRequestRecord.getType()             .getCode()                    );
        record.setStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_STATE_COLUMN_NAME              , cryptoPaymentRequestRecord.getState()            .getCode()                    );
        record.setStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_PROTOCOL_STATE_COLUMN_NAME     , cryptoPaymentRequestRecord.getProtocolState()    .getCode()                    );
        record.setLongValue  (CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_AMOUNT_COLUMN_NAME             , cryptoPaymentRequestRecord.getAmount()                                         );
        record.setLongValue  (CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_START_TIME_STAMP_COLUMN_NAME   , cryptoPaymentRequestRecord.getStartTimeStamp()                                 );
        record.setLongValue  (CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_END_TIME_STAMP_COLUMN_NAME     , cryptoPaymentRequestRecord.getEndTimeStamp()                                   );

        return record;
    }

    private CryptoPaymentRequest buildAddressExchangeRequestRecord(DatabaseTableRecord record) throws InvalidParameterException {

        UUID   requestId            = record.getUUIDValue  (CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_REQUEST_ID_COLUMN_NAME         );
        String walletPublicKey      = record.getStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_WALLET_PUBLIC_KEY_COLUMN_NAME  );
        String identityPublicKey    = record.getStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        String actorPublicKey       = record.getStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_ACTOR_PUBLIC_KEY_COLUMN_NAME   );
        String description          = record.getStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_DESCRIPTION_COLUMN_NAME        );
        String cryptoAddressString  = record.getStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_CRYPTO_ADDRESS_COLUMN_NAME     );
        String cryptoCurrencyString = record.getStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_CRYPTO_CURRENCY_COLUMN_NAME    );
        String typeString           = record.getStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_TYPE_COLUMN_NAME               );
        String stateString          = record.getStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_STATE_COLUMN_NAME              );
        String protocolStateString  = record.getStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_PROTOCOL_STATE_COLUMN_NAME     );

        long   amount               = record.getLongValue  (CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_AMOUNT_COLUMN_NAME             );
        long   startTimeStamp       = record.getLongValue  (CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_START_TIME_STAMP_COLUMN_NAME   );
        long   endTimeStamp         = record.getLongValue  (CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_END_TIME_STAMP_COLUMN_NAME     );

        CryptoAddress        cryptoAddress = new CryptoAddress(cryptoAddressString, CryptoCurrency.getByCode(cryptoCurrencyString));

        CryptoPaymentType    type          = CryptoPaymentType   .getByCode(typeString)         ;
        CryptoPaymentState   state         = CryptoPaymentState  .getByCode(stateString)        ;
        RequestProtocolState protocolState = RequestProtocolState.getByCode(protocolStateString);

        return new CryptoPaymentRequestNetworkServiceRecord(
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
                state            ,
                protocolState
        );
    }

}
