package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.daos;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.entities.NetworkServiceMessage;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.exceptions.RecordNotFoundException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageContentType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessagesStatus;

import java.sql.Timestamp;
import java.util.UUID;

import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.INCOMING_MESSAGES_CONTENT_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.INCOMING_MESSAGES_CONTENT_TYPE_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.INCOMING_MESSAGES_DELIVERY_TIMESTAMP_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.INCOMING_MESSAGES_ID_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.INCOMING_MESSAGES_RECEIVER_PUBLIC_KEY_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.INCOMING_MESSAGES_SENDER_PUBLIC_KEY_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.INCOMING_MESSAGES_SHIPPING_TIMESTAMP_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.INCOMING_MESSAGES_STATUS_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_NAME;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.daos.IncomingMessagesDao</code>
 * <p/>
 * Created  by Leon Acosta - (laion.cj91@gmail.com) on 13/05/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class IncomingMessagesDao extends AbstractBaseDao<NetworkServiceMessage> {

    public IncomingMessagesDao(final Database dataBase) {

        super(
                dataBase                        ,
                INCOMING_MESSAGES_TABLE_NAME    ,
                INCOMING_MESSAGES_ID_COLUMN_NAME
        );
    }

    public void markAsRead(NetworkServiceMessage fermatMessage) throws CantUpdateRecordDataBaseException, RecordNotFoundException {

        if (fermatMessage == null) {
            throw new IllegalArgumentException("The id is required, can not be null");
        }
        fermatMessage.setFermatMessagesStatus(FermatMessagesStatus.READ);
        update(fermatMessage);

    }

    @Override
    protected NetworkServiceMessage getEntityFromDatabaseTableRecord(DatabaseTableRecord record) throws InvalidParameterException {


        NetworkServiceMessage networkServiceMessage = new NetworkServiceMessage();

        try {

            networkServiceMessage.setId(UUID.fromString(record.getStringValue(INCOMING_MESSAGES_ID_COLUMN_NAME)));
            networkServiceMessage.setSenderPublicKey(record.getStringValue(INCOMING_MESSAGES_SENDER_PUBLIC_KEY_COLUMN_NAME));
            networkServiceMessage.setReceiverPublicKey(record.getStringValue(INCOMING_MESSAGES_RECEIVER_PUBLIC_KEY_COLUMN_NAME));
            networkServiceMessage.setContent(record.getStringValue(INCOMING_MESSAGES_CONTENT_COLUMN_NAME));
            networkServiceMessage.setMessageContentType(MessageContentType.getByCode(record.getStringValue(INCOMING_MESSAGES_CONTENT_TYPE_COLUMN_NAME)));
            networkServiceMessage.setShippingTimestamp(new Timestamp(record.getLongValue(INCOMING_MESSAGES_SHIPPING_TIMESTAMP_COLUMN_NAME)));

            if (record.getStringValue(INCOMING_MESSAGES_DELIVERY_TIMESTAMP_COLUMN_NAME) == null)
                networkServiceMessage.setDeliveryTimestamp(new Timestamp(0));
            else
                networkServiceMessage.setDeliveryTimestamp(new Timestamp(record.getLongValue(INCOMING_MESSAGES_DELIVERY_TIMESTAMP_COLUMN_NAME)));

            networkServiceMessage.setFermatMessagesStatus(FermatMessagesStatus.getByCode(record.getStringValue(INCOMING_MESSAGES_STATUS_COLUMN_NAME)));

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

        entityRecord.setStringValue(INCOMING_MESSAGES_ID_COLUMN_NAME, entity.getId().toString());
        entityRecord.setStringValue(INCOMING_MESSAGES_SENDER_PUBLIC_KEY_COLUMN_NAME, entity.getSenderPublicKey());
        entityRecord.setStringValue(INCOMING_MESSAGES_RECEIVER_PUBLIC_KEY_COLUMN_NAME, entity.getReceiverPublicKey());
        entityRecord.setStringValue(INCOMING_MESSAGES_CONTENT_COLUMN_NAME, entity.getContent());
        entityRecord.setStringValue(INCOMING_MESSAGES_CONTENT_TYPE_COLUMN_NAME, entity.getMessageContentType().getCode());

        if (entity.getShippingTimestamp() != null) {
            entityRecord.setLongValue(INCOMING_MESSAGES_SHIPPING_TIMESTAMP_COLUMN_NAME, entity.getShippingTimestamp().getTime());
        } else {
            entityRecord.setLongValue(INCOMING_MESSAGES_SHIPPING_TIMESTAMP_COLUMN_NAME, (long) 0);
        }

        if (entity.getDeliveryTimestamp() != null) {
            entityRecord.setLongValue(INCOMING_MESSAGES_DELIVERY_TIMESTAMP_COLUMN_NAME, entity.getDeliveryTimestamp().getTime());
        } else {
            entityRecord.setLongValue(INCOMING_MESSAGES_DELIVERY_TIMESTAMP_COLUMN_NAME, (long) 0);
        }

        entityRecord.setStringValue(INCOMING_MESSAGES_STATUS_COLUMN_NAME, entity.getFermatMessagesStatus().getCode());

        return entityRecord;
    }

}
