package com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_wallet_user.exceptions.CantCreateIntraUserException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_wallet_user.exceptions.CantGetIntraUserException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_wallet_user.exceptions.IntraUserNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_wallet_user.exceptions.CantListIntraWalletUsersException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.exceptions.RequestNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.enums.ActorProtocolState;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.enums.IntraUserNotificationDescriptor;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantUpdateRecordDataBaseException;

import java.util.List;
import java.util.UUID;

/**
 * Created by mati on 2015.10.16..
 */
public interface DAO {


    public ActorNetworkServiceRecord createNotification(        UUID                        notificationId        ,
                                           String                      senderPublicKey,
                                           Actors senderType     ,
                                           String                      destinationPublicKey   ,
                                           String                      senderAlias,
                                           byte[]                      senderProfileImage,
                                           Actors                      destinationType        ,
                                           IntraUserNotificationDescriptor descriptor      ,
                                           long                        timestamp   ,
                                           ActorProtocolState protocolState    ,
                                           boolean                     flagReaded      ) throws CantCreateIntraUserException;
    public ActorNetworkServiceRecord getNotificationById(final UUID notificationId) throws CantGetIntraUserException, IntraUserNotFoundException;

    public void changeIntraUserNotificationDescriptor(final String                 senderPublicKey    ,
                                                      final IntraUserNotificationDescriptor intraUserNotificationDescriptor) throws CantUpdateRecordDataBaseException, CantUpdateRecordException, RequestNotFoundException;

    public void changeProtocolState(final UUID                 requestId    ,
                                    final ActorProtocolState protocolState) throws CantUpdateRecordDataBaseException, CantUpdateRecordException, RequestNotFoundException;

    public List<ActorNetworkServiceRecord> listRequestsByProtocolStateAndType(final ActorProtocolState protocolState) throws CantListIntraWalletUsersException;

    public List<ActorNetworkServiceRecord> listRequestsByProtocolStateAndType(final ActorProtocolState protocolState,
                                                                              final IntraUserNotificationDescriptor intraUserNotificationDescriptor) throws CantListIntraWalletUsersException;


    public List<ActorNetworkServiceRecord> listUnreadNotifications() throws CantListIntraWalletUsersException;

    public void markReadedNotification(UUID notificationId);


    public void update(ActorNetworkServiceRecord entity) throws CantUpdateRecordDataBaseException;

}
