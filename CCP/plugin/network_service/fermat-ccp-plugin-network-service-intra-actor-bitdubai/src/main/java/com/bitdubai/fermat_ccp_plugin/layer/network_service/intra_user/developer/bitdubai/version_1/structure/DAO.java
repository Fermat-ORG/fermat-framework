package com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.CantCreateNotificationException;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.CantGetNotificationException;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.NotificationNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.exceptions.CantListIntraWalletUsersException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.exceptions.RequestNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.enums.ActorProtocolState;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.enums.NotificationDescriptor;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.exceptions.CantConfirmNotificationException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.interfaces.IntraUserNotification;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantUpdateRecordDataBaseException;

import java.util.List;
import java.util.UUID;

/**
 * Created by mati on 2015.10.16..
 */
public interface DAO {


    ActorNetworkServiceRecord createNotification(UUID notificationId,
                                                 String senderPublicKey,
                                                 Actors senderType,
                                                 String destinationPublicKey,
                                                 String senderAlias,
                                                 String senderPhrase,
                                                 byte[] senderProfileImage,
                                                 Actors destinationType,
                                                 NotificationDescriptor descriptor,
                                                 long timestamp,
                                                 ActorProtocolState protocolState,
                                                 boolean flagReaded,
                                                 int sentCount,
                                                 UUID responseToNotificationId,
                                                 String city, String country) throws CantCreateNotificationException;

    ActorNetworkServiceRecord getNotificationById(final UUID notificationId) throws CantGetNotificationException, NotificationNotFoundException;



    void changeIntraUserNotificationDescriptor(final String senderPublicKey,
                                               final NotificationDescriptor notificationDescriptor) throws CantUpdateRecordDataBaseException, CantUpdateRecordException, RequestNotFoundException;

    void changeProtocolState(final UUID requestId,
                             final ActorProtocolState protocolState) throws Exception;


    List<ActorNetworkServiceRecord> listRequestsByProtocolStateAndType(final ActorProtocolState protocolState,
                                                                       final NotificationDescriptor notificationDescriptor) throws CantListIntraWalletUsersException;


    List<IntraUserNotification> listUnreadNotifications() throws CantListIntraWalletUsersException;

    void markNotificationAsRead(UUID notificationId) throws CantConfirmNotificationException;


    void update(ActorNetworkServiceRecord entity) throws CantUpdateRecordDataBaseException;

}
