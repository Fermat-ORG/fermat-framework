package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantRegisterProfileException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantSendMessageException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantUpdateRegisteredProfileException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request.ActorListMsgRequest;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request.IsActorOnlineMsgRequest;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.enums.UpdateTypes;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.abstract_classes.AbstractNetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.entities.NetworkServiceMessage;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.NetworkServiceProfile;

import javax.annotation.Nullable;
import java.util.UUID;

/**
 * Created by Matias Furszyfer on 2016.07.06..
 */
public interface P2PLayerManager {

    void register(AbstractNetworkService abstractNetworkService);

    void register(NetworkChannel networkChannel);

    UUID register(ActorProfile profile, NetworkServiceProfile networkServiceProfileRequester) throws CantRegisterProfileException, CantSendMessageException;

    void update(ActorProfile profile, UpdateTypes type,NetworkServiceType networkServiceType) throws CantUpdateRegisteredProfileException;

    void setNetworkServicesRegisteredFalse();

    //todo: ver que poner en el destinationPublicKey, yo creo que ahí deberia ir el homeNode pero tengo que ver eso

    /**
     * The parameter layerMonitoring sets if the Layer is going to monitoring the resend message process.
     * @param packageContent
     * @param networkServiceType
     * @param nodeDestinationPublicKey
     * @param layerMonitoring sets if the Layer is going to monitoring the resend message process.
     * @return
     * @throws CantSendMessageException
     */
    UUID sendMessage(NetworkServiceMessage packageContent, NetworkServiceType networkServiceType, @Nullable  String nodeDestinationPublicKey, boolean layerMonitoring) throws CantSendMessageException;

    /**
     *
     * @param actorListMsgRequest
     * @param networkServiceType
     * @param nodeDestinationKey if this is nul the default value is the homeNode
     */
    UUID sendDiscoveryMessage(ActorListMsgRequest actorListMsgRequest, NetworkServiceType networkServiceType,@Nullable String nodeDestinationKey) throws CantSendMessageException;

    /**
     * This method sends an IsActorOnlineMsgRequest
     * @param isActorOnlineMsgRequest
     * @param networkServiceType
     * @param nodeDestinationPublicKey if this is nul the default value is the homeNode
     * @return
     * @throws CantSendMessageException
     */
    UUID sendIsOnlineActorMessage(
            IsActorOnlineMsgRequest isActorOnlineMsgRequest,
            NetworkServiceType networkServiceType,
            @Nullable  String nodeDestinationPublicKey) throws CantSendMessageException;


    //Subscribers

    /**
     *
     * @return
     */
    UUID subscribeActorOnlineEvent(NetworkServiceType networkServiceType,String actorToFollowPk) throws CantSendMessageException;


    UUID unSubscribeActorOnlineEvent(NetworkServiceType networkServiceType,UUID eventSubscribedId) throws CantSendMessageException;
}
