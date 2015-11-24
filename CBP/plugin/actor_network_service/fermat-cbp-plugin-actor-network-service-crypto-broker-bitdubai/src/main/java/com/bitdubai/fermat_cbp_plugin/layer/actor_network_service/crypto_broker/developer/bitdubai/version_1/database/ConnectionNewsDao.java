package com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.enums.ConnectionRequestAction;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.enums.ProtocolState;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.enums.RequestType;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.utils.CryptoBrokerConnectionNew;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.exceptions.CantInitializeDatabaseException;

import java.util.UUID;

/**
 * The class <code>com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.database.ConnectionNewsDao</code>
 * contains all the methods that interact with the database.
 * Manages the actor connection requests by storing them on a Database Table.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/11/2015.
 *
 * @version 1.0
 */
public final class ConnectionNewsDao {

    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID                 pluginId            ;

    private Database database;

    public ConnectionNewsDao(final PluginDatabaseSystem pluginDatabaseSystem,
                             final UUID                 pluginId            ) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId             = pluginId            ;
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

    private DatabaseTableRecord buildDatabaseRecord(final DatabaseTableRecord       record       ,
                                                    final CryptoBrokerConnectionNew connectionNew) {

        record.setUUIDValue  (CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ID_COLUMN_NAME            , connectionNew.getRequestId()           );
        record.setStringValue(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_SENDER_PUBLIC_KEY_COLUMN_NAME     , connectionNew.getSenderPublicKey()     );
        record.setFermatEnum (CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_SENDER_ACTOR_TYPE_COLUMN_NAME     , connectionNew.getSenderActorType()     );
        record.setStringValue(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_SENDER_ALIAS_COLUMN_NAME          , connectionNew.getSenderAlias()         );
        record.setStringValue(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_DESTINATION_PUBLIC_KEY_COLUMN_NAME, connectionNew.getDestinationPublicKey());
        record.setFermatEnum (CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_TYPE_COLUMN_NAME          , connectionNew.getRequestType()         );
        record.setFermatEnum (CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_STATE_COLUMN_NAME         , connectionNew.getProtocolState()       );
        record.setFermatEnum (CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ACTION_COLUMN_NAME        , connectionNew.getRequestAction()       );
        record.setLongValue  (CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_SENT_TIME_COLUMN_NAME             , connectionNew.getSentTime()            );

        return record;
    }

    private CryptoBrokerConnectionNew buildConnectionNewRecord(final DatabaseTableRecord record) throws InvalidParameterException {

        UUID   requestId                    = record.getUUIDValue  (CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ID_COLUMN_NAME            );
        String senderPublicKey              = record.getStringValue(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_SENDER_PUBLIC_KEY_COLUMN_NAME     );
        String senderActorTypeString        = record.getStringValue(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_SENDER_ACTOR_TYPE_COLUMN_NAME     );
        String senderAlias                  = record.getStringValue(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_SENDER_ALIAS_COLUMN_NAME          );
        String destinationPublicKey         = record.getStringValue(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_DESTINATION_PUBLIC_KEY_COLUMN_NAME);
        String requestTypeString            = record.getStringValue(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_TYPE_COLUMN_NAME          );
        String protocolStateString          = record.getStringValue(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_STATE_COLUMN_NAME         );
        String requestActionString          = record.getStringValue(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ACTION_COLUMN_NAME        );
        Long   sentTime                     = record.getLongValue  (CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_SENT_TIME_COLUMN_NAME             );

        Actors                  senderActorType  = Actors                 .getByCode(senderActorTypeString);
        RequestType             requestType      = RequestType            .getByCode(requestTypeString    );
        ProtocolState           state            = ProtocolState          .getByCode(protocolStateString  );
        ConnectionRequestAction action           = ConnectionRequestAction.getByCode(requestActionString  );

        return new CryptoBrokerConnectionNew(
                requestId           ,
                senderPublicKey     ,
                senderActorType     ,
                senderAlias         ,
                null                ,
                destinationPublicKey,
                requestType         ,
                state               ,
                action              ,
                sentTime
        );
    }
}
