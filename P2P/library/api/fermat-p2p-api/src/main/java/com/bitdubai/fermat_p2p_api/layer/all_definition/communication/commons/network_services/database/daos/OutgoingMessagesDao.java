package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.daos;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.entities.NetworkServiceMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessageContentType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessagesStatus;

import java.sql.Timestamp;
import java.util.UUID;

import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.OUTGOING_MESSAGES_CONTENT_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.OUTGOING_MESSAGES_CONTENT_TYPE_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.OUTGOING_MESSAGES_DELIVERY_TIMESTAMP_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.OUTGOING_MESSAGES_ID_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.OUTGOING_MESSAGES_IS_BETWEEN_ACTORS_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.OUTGOING_MESSAGES_RECEIVER_ACTOR_TYPE_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.OUTGOING_MESSAGES_RECEIVER_CLIENT_PUBLIC_KEY_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.OUTGOING_MESSAGES_RECEIVER_PUBLIC_KEY_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.OUTGOING_MESSAGES_SENDER_ACTOR_TYPE_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.OUTGOING_MESSAGES_SENDER_CLIENT_PUBLIC_KEY_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.OUTGOING_MESSAGES_SENDER_PUBLIC_KEY_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.OUTGOING_MESSAGES_SHIPPING_TIMESTAMP_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.OUTGOING_MESSAGES_STATUS_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.OUTGOING_MESSAGES_TABLE_NAME;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.daos.OutgoingMessage</code>
 * <p/>
 * Created  by Leon Acosta - (laion.cj91@gmail.com) on 13/05/2016.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */
public class OutgoingMessagesDao extends AbstractBaseDao {

    public OutgoingMessagesDao(final Database dataBase) {

        super(
                dataBase                    ,
                OUTGOING_MESSAGES_TABLE_NAME
        );
    }

    @Override
    protected NetworkServiceMessage getEntityFromDatabaseTableRecord(DatabaseTableRecord record) throws InvalidParameterException {


        NetworkServiceMessage networkServiceMessage = new NetworkServiceMessage();

        try {

            networkServiceMessage.setId(UUID.fromString(record.getStringValue(OUTGOING_MESSAGES_ID_COLUMN_NAME)));
            networkServiceMessage.setSenderPublicKey(record.getStringValue(OUTGOING_MESSAGES_SENDER_PUBLIC_KEY_COLUMN_NAME));
            networkServiceMessage.setReceiverPublicKey(record.getStringValue(OUTGOING_MESSAGES_RECEIVER_PUBLIC_KEY_COLUMN_NAME));
            networkServiceMessage.setContent(record.getStringValue(OUTGOING_MESSAGES_CONTENT_COLUMN_NAME));
            networkServiceMessage.setContentType(FermatMessageContentType.getByCode(record.getStringValue(OUTGOING_MESSAGES_CONTENT_TYPE_COLUMN_NAME)));
            networkServiceMessage.setShippingTimestamp(new Timestamp(record.getLongValue(OUTGOING_MESSAGES_SHIPPING_TIMESTAMP_COLUMN_NAME)));

            if (record.getStringValue(OUTGOING_MESSAGES_DELIVERY_TIMESTAMP_COLUMN_NAME) == null)
                networkServiceMessage.setDeliveryTimestamp(new Timestamp(0));
            else
                networkServiceMessage.setDeliveryTimestamp(new Timestamp(record.getLongValue(OUTGOING_MESSAGES_DELIVERY_TIMESTAMP_COLUMN_NAME)));

            networkServiceMessage.setFermatMessagesStatus(FermatMessagesStatus.getByCode(record.getStringValue(OUTGOING_MESSAGES_STATUS_COLUMN_NAME)));

            Boolean isBetweenActors = Boolean.parseBoolean(record.getStringValue(OUTGOING_MESSAGES_IS_BETWEEN_ACTORS_COLUMN_NAME));

            networkServiceMessage.setIsBetweenActors(isBetweenActors);

            if (isBetweenActors) {

                networkServiceMessage.setSenderActorType(record.getStringValue(OUTGOING_MESSAGES_SENDER_ACTOR_TYPE_COLUMN_NAME));
                networkServiceMessage.setReceiverActorType(record.getStringValue(OUTGOING_MESSAGES_RECEIVER_ACTOR_TYPE_COLUMN_NAME));

                networkServiceMessage.setSenderClientPublicKey(record.getStringValue(OUTGOING_MESSAGES_SENDER_CLIENT_PUBLIC_KEY_COLUMN_NAME));
                networkServiceMessage.setReceiverClientPublicKey(record.getStringValue(OUTGOING_MESSAGES_RECEIVER_CLIENT_PUBLIC_KEY_COLUMN_NAME));
            }

        } catch (InvalidParameterException e) {
            //this should not happen, but if it happens return null
            e.printStackTrace();
            return null;
        }

        return networkServiceMessage;
    }

    @Override
    protected DatabaseTableRecord getDatabaseTableRecordFromEntity(NetworkServiceMessage entity) {

        DatabaseTableRecord entityRecord = getDatabaseTable().getEmptyRecord();

        entityRecord.setStringValue(OUTGOING_MESSAGES_ID_COLUMN_NAME, entity.getId().toString());
        entityRecord.setStringValue(OUTGOING_MESSAGES_SENDER_PUBLIC_KEY_COLUMN_NAME, entity.getSenderPublicKey());
        entityRecord.setStringValue(OUTGOING_MESSAGES_RECEIVER_PUBLIC_KEY_COLUMN_NAME, entity.getReceiverPublicKey());
        entityRecord.setStringValue(OUTGOING_MESSAGES_CONTENT_COLUMN_NAME, entity.getContent());
        entityRecord.setStringValue(OUTGOING_MESSAGES_CONTENT_TYPE_COLUMN_NAME, entity.getContentType().getCode());

        if (entity.getShippingTimestamp() != null) {
            entityRecord.setLongValue(OUTGOING_MESSAGES_SHIPPING_TIMESTAMP_COLUMN_NAME, entity.getShippingTimestamp().getTime());
        } else {
            entityRecord.setLongValue(OUTGOING_MESSAGES_SHIPPING_TIMESTAMP_COLUMN_NAME, (long) 0);
        }

        if (entity.getDeliveryTimestamp() != null) {
            entityRecord.setLongValue(OUTGOING_MESSAGES_DELIVERY_TIMESTAMP_COLUMN_NAME, entity.getDeliveryTimestamp().getTime());
        } else {
            entityRecord.setLongValue(OUTGOING_MESSAGES_DELIVERY_TIMESTAMP_COLUMN_NAME, (long) 0);
        }

        entityRecord.setStringValue(OUTGOING_MESSAGES_STATUS_COLUMN_NAME, entity.getFermatMessagesStatus().getCode());

        if (entity.isBetweenActors()) {

            entityRecord.setStringValue(OUTGOING_MESSAGES_IS_BETWEEN_ACTORS_COLUMN_NAME, entity.isBetweenActors().toString());

            entityRecord.setStringValue(OUTGOING_MESSAGES_SENDER_CLIENT_PUBLIC_KEY_COLUMN_NAME, entity.getSenderClientPublicKey());
            entityRecord.setStringValue(OUTGOING_MESSAGES_RECEIVER_CLIENT_PUBLIC_KEY_COLUMN_NAME, entity.getReceiverClientPublicKey());

            entityRecord.setStringValue(OUTGOING_MESSAGES_SENDER_ACTOR_TYPE_COLUMN_NAME, entity.getSenderActorType());
            entityRecord.setStringValue(OUTGOING_MESSAGES_RECEIVER_ACTOR_TYPE_COLUMN_NAME, entity.getReceiverActorType());

        }

        return entityRecord;
    }

}
