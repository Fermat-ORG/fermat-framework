package com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.database.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.CantCreateNotificationException;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.CantGetNotificationException;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.NotificationNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.exceptions.RequestNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.exceptions.CantConfirmNotificationException;
import com.bitdubai.fermat_cht_api.all_definition.enums.MessageStatus;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CHTException;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.ChatMessageStatus;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.ChatProtocolState;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.DistributionStatus;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces.ChatMetadata;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.structure.ChatMetadataRecord;

import java.util.List;
import java.util.UUID;

/**
 * Created by mati on 2015.10.16..
 */
public interface DAO {


    void createNotification(ChatMetadataRecord chatMetadataRecord) throws CantCreateNotificationException, CantInsertRecordDataBaseException, CantUpdateRecordDataBaseException;

    ChatMetadataRecord getNotificationById(final UUID transactionID) throws CantGetNotificationException, NotificationNotFoundException, CantReadRecordDataBaseException;


    void changeChatMessageState(final String senderPublicKey,
                                final ChatMessageStatus chatMessageStatus) throws CantUpdateRecordDataBaseException, CantUpdateRecordException, RequestNotFoundException, CantReadRecordDataBaseException;

    void changeChatMessageState(final String senderPublicKey,
                                final MessageStatus messageStatus) throws CantUpdateRecordDataBaseException, CantUpdateRecordException, RequestNotFoundException, CantReadRecordDataBaseException;

    void changeChatProtocolState(final UUID requestId,
                                 final ChatProtocolState protocolState) throws CHTException, CantGetNotificationException, NotificationNotFoundException;


    List<ChatMetadataRecord> listRequestsByChatProtocolStateAndDistributionStatus(final ChatProtocolState chatProtocolState, final DistributionStatus distributionStatus) throws CantReadRecordDataBaseException, CantLoadTableToMemoryException;


    List<ChatMetadata> listUnreadNotifications() throws CHTException;

    void markNotificationAsRead(UUID transactionID) throws CantConfirmNotificationException;

    void update(ChatMetadataRecord entity) throws CantUpdateRecordDataBaseException;

}
