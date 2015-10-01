package com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.enums.CryptoPaymentState;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.enums.CryptoPaymentType;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.interfaces.CryptoPayment;
import com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.exceptions.CantInitializeCryptoPaymentRequestDatabaseException;
import com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.structure.CryptoPaymentRequestRecord;

import java.util.UUID;

/**
 * The class <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.database.CryptoPaymentRequestNetworkServiceDao</code>
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

    private DatabaseTableRecord buildDatabaseRecord(DatabaseTableRecord        record                    ,
                                                    CryptoPaymentRequestRecord cryptoPaymentRequestRecord) {

        record.setUUIDValue  (CryptoPaymentRequestDatabaseConstants.CRYPTO_ADDRESS_REQUEST_REQUEST_ID_COLUMN_NAME         , cryptoPaymentRequestRecord.getRequestId()                                      );
        record.setStringValue(CryptoPaymentRequestDatabaseConstants.CRYPTO_ADDRESS_REQUEST_WALLET_PUBLIC_KEY_COLUMN_NAME  , cryptoPaymentRequestRecord.getWalletPublicKey()                                );
        record.setStringValue(CryptoPaymentRequestDatabaseConstants.CRYPTO_ADDRESS_REQUEST_IDENTITY_PUBLIC_KEY_COLUMN_NAME, cryptoPaymentRequestRecord.getIdentityPublicKey()                              );
        record.setStringValue(CryptoPaymentRequestDatabaseConstants.CRYPTO_ADDRESS_REQUEST_ACTOR_PUBLIC_KEY_COLUMN_NAME   , cryptoPaymentRequestRecord.getActorPublicKey()                                 );
        record.setStringValue(CryptoPaymentRequestDatabaseConstants.CRYPTO_ADDRESS_REQUEST_DESCRIPTION_COLUMN_NAME        , cryptoPaymentRequestRecord.getDescription()                                    );
        record.setStringValue(CryptoPaymentRequestDatabaseConstants.CRYPTO_ADDRESS_REQUEST_CRYPTO_ADDRESS_COLUMN_NAME     , cryptoPaymentRequestRecord.getCryptoAddress()    .getAddress()                 );
        record.setStringValue(CryptoPaymentRequestDatabaseConstants.CRYPTO_ADDRESS_REQUEST_CRYPTO_CURRENCY_COLUMN_NAME    , cryptoPaymentRequestRecord.getCryptoAddress()    .getCryptoCurrency().getCode());
        record.setStringValue(CryptoPaymentRequestDatabaseConstants.CRYPTO_ADDRESS_REQUEST_TYPE_COLUMN_NAME               , cryptoPaymentRequestRecord.getType()             .getCode()                    );
        record.setStringValue(CryptoPaymentRequestDatabaseConstants.CRYPTO_ADDRESS_REQUEST_STATE_COLUMN_NAME              , cryptoPaymentRequestRecord.getState()            .getCode()                    );
        record.setLongValue  (CryptoPaymentRequestDatabaseConstants.CRYPTO_ADDRESS_REQUEST_AMOUNT_COLUMN_NAME             , cryptoPaymentRequestRecord.getAmount()                                         );
        record.setLongValue  (CryptoPaymentRequestDatabaseConstants.CRYPTO_ADDRESS_REQUEST_START_TIME_STAMP_COLUMN_NAME   , cryptoPaymentRequestRecord.getStartTimeStamp()                                 );
        record.setLongValue  (CryptoPaymentRequestDatabaseConstants.CRYPTO_ADDRESS_REQUEST_END_TIME_STAMP_COLUMN_NAME     , cryptoPaymentRequestRecord.getEndTimeStamp()                                   );

        return record;
    }

    private CryptoPayment buildAddressExchangeRequestRecord(DatabaseTableRecord record) throws InvalidParameterException {

        UUID   requestId            = record.getUUIDValue  (CryptoPaymentRequestDatabaseConstants.CRYPTO_ADDRESS_REQUEST_REQUEST_ID_COLUMN_NAME         );
        String walletPublicKey      = record.getStringValue(CryptoPaymentRequestDatabaseConstants.CRYPTO_ADDRESS_REQUEST_WALLET_PUBLIC_KEY_COLUMN_NAME  );
        String identityPublicKey    = record.getStringValue(CryptoPaymentRequestDatabaseConstants.CRYPTO_ADDRESS_REQUEST_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        String actorPublicKey       = record.getStringValue(CryptoPaymentRequestDatabaseConstants.CRYPTO_ADDRESS_REQUEST_ACTOR_PUBLIC_KEY_COLUMN_NAME   );
        String description          = record.getStringValue(CryptoPaymentRequestDatabaseConstants.CRYPTO_ADDRESS_REQUEST_DESCRIPTION_COLUMN_NAME        );
        String cryptoAddressString  = record.getStringValue(CryptoPaymentRequestDatabaseConstants.CRYPTO_ADDRESS_REQUEST_CRYPTO_ADDRESS_COLUMN_NAME     );
        String cryptoCurrencyString = record.getStringValue(CryptoPaymentRequestDatabaseConstants.CRYPTO_ADDRESS_REQUEST_CRYPTO_CURRENCY_COLUMN_NAME    );
        String typeString           = record.getStringValue(CryptoPaymentRequestDatabaseConstants.CRYPTO_ADDRESS_REQUEST_TYPE_COLUMN_NAME               );
        String stateString          = record.getStringValue(CryptoPaymentRequestDatabaseConstants.CRYPTO_ADDRESS_REQUEST_STATE_COLUMN_NAME              );

        long   amount               = record.getLongValue  (CryptoPaymentRequestDatabaseConstants.CRYPTO_ADDRESS_REQUEST_AMOUNT_COLUMN_NAME             );
        long   startTimeStamp       = record.getLongValue  (CryptoPaymentRequestDatabaseConstants.CRYPTO_ADDRESS_REQUEST_START_TIME_STAMP_COLUMN_NAME   );
        long   endTimeStamp         = record.getLongValue  (CryptoPaymentRequestDatabaseConstants.CRYPTO_ADDRESS_REQUEST_END_TIME_STAMP_COLUMN_NAME     );

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
