package com.fermat_p2p_layer.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantRegisterProfileException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantSendMessageException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantUpdateRegisteredProfileException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.PackageContent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request.ActorListMsgRequest;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request.CheckInProfileMsgRequest;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request.IsActorOnlineMsgRequest;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request.SubscriberMsgRequest;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request.UnSubscribeMsgRequest;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request.UpdateActorProfileMsgRequest;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.entities.NetworkServiceMessage;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ClientProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.NetworkServiceProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.Profile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageContentType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.fermat_p2p_layer.version_1.P2PLayerPluginRoot;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by mati on 14/08/16.
 * Esta clase deberia guardar la relación entre channel + public key del componente. así se sabe hacia donde va el mensaje y si el canal debe seguir abierto
 */
public class MessageSender {


    private final P2PLayerPluginRoot p2PLayerPluginRoot;

    /**
     * this map is for the outgoing messages quienes no retornó el ack aún,
     * PackageId + NetworkServiceType
     */
    private ConcurrentHashMap<UUID,PackageInformation> messagesSentWaitingForAck;


    public MessageSender(P2PLayerPluginRoot p2PLayerPluginRoot) {
        this.p2PLayerPluginRoot = p2PLayerPluginRoot;
        messagesSentWaitingForAck = new ConcurrentHashMap<>();
    }

    public UUID registerProfile(Profile profile,NetworkServiceType networkServiceType) throws CantRegisterProfileException, CantSendMessageException {
        if (p2PLayerPluginRoot.getNetworkClient().isConnected()) {
            CheckInProfileMsgRequest profileCheckInMsgRequest = new CheckInProfileMsgRequest(profile);
            profileCheckInMsgRequest.setMessageContentType(MessageContentType.JSON);
            PackageType packageType = null;
            if (profile instanceof ActorProfile) {
                packageType = PackageType.CHECK_IN_ACTOR_REQUEST;
            } else if (profile instanceof NetworkServiceProfile) {
                packageType = PackageType.CHECK_IN_NETWORK_SERVICE_REQUEST;
            }
            UUID packageId = p2PLayerPluginRoot.getNetworkClient().sendMessage(profileCheckInMsgRequest, packageType, networkServiceType);
            if (packageId != null)
                messagesSentWaitingForAck.put(packageId,createPackageInformation(networkServiceType,packageType));
            return packageId;
        }
        return null;
    }

    public UUID sendMessage(NetworkServiceMessage networkServiceMessage, NetworkServiceType networkServiceType, String nodeDestinationPublicKey) throws CantSendMessageException {
        //todo: ver porqué el ultimo parametro del metodo sendMessage es el destination del actor,ns o lo que sea. ver si agrego el nodo ahí o que hago
        UUID packageId = p2PLayerPluginRoot.getNetworkClient().sendMessage(networkServiceMessage, PackageType.MESSAGE_TRANSMIT, networkServiceType, networkServiceMessage.getReceiverPublicKey());
        if (packageId != null)
            messagesSentWaitingForAck.put(packageId,createPackageInformation(networkServiceType,PackageType.MESSAGE_TRANSMIT));
        return packageId;
    }

    public UUID sendIsOnlineActorMessage(IsActorOnlineMsgRequest isActorOnlineMsgRequest, NetworkServiceType networkServiceType, String nodeDestinationPublicKey) throws CantSendMessageException {
        UUID packageId = p2PLayerPluginRoot.getNetworkClient().sendMessage(isActorOnlineMsgRequest, PackageType.MESSAGE_TRANSMIT,networkServiceType,nodeDestinationPublicKey);
        if (packageId != null)
            messagesSentWaitingForAck.put(packageId,createPackageInformation(networkServiceType,PackageType.MESSAGE_TRANSMIT));
        return packageId;
    }

    public UUID sendDiscoveryMessage(ActorListMsgRequest networkServiceMessage, NetworkServiceType networkServiceType, String nodeDestinationPublicKey) throws CantSendMessageException {
        //todo: esto deberia ser para todos los discovery y no solo para el actorList
        UUID packageId = p2PLayerPluginRoot.getNetworkClient().sendMessage(networkServiceMessage,PackageType.ACTOR_LIST_REQUEST,networkServiceType,nodeDestinationPublicKey);
        if (packageId != null)
            messagesSentWaitingForAck.put(packageId,createPackageInformation(networkServiceType,PackageType.ACTOR_LIST_REQUEST));
        return packageId;
    }

    /**
     *
     * @param packageId
     * @return network service type
     */
    public PackageInformation packageAck(UUID packageId){
        return messagesSentWaitingForAck.remove(packageId);
    }

    public UUID sendProfileToUpdate(NetworkServiceType networkServiceType,Profile profile) throws CantUpdateRegisteredProfileException, CantSendMessageException {
        PackageType packageType = null;
        PackageContent packageContent = null;
        if (profile instanceof ActorProfile) {
            packageType = PackageType.UPDATE_ACTOR_PROFILE_REQUEST;
            UpdateActorProfileMsgRequest updateActorProfileMsgRequest = new UpdateActorProfileMsgRequest(profile);
            updateActorProfileMsgRequest.setMessageContentType(MessageContentType.JSON);
            packageContent = updateActorProfileMsgRequest;
        } else {
            CantUpdateRegisteredProfileException fermatException = new CantUpdateRegisteredProfileException(
                    "profile:" + profile,
                    "Unsupported profile type."
            );

            p2PLayerPluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    fermatException
            );

            throw fermatException;
        }
        UUID packageId = p2PLayerPluginRoot.getNetworkClient().sendMessage(packageContent, packageType,networkServiceType);
        if (packageId != null)
            messagesSentWaitingForAck.put(packageId,createPackageInformation(networkServiceType,packageType));
        return packageId;

    }

    public UUID subscribeNodeEvent(NetworkServiceType networkServiceType,short eventCode, String actorToFollowPk) throws CantSendMessageException {
        if (p2PLayerPluginRoot.getNetworkClient().isConnected()) {
            SubscriberMsgRequest subscriberMsgRequest = new SubscriberMsgRequest(eventCode,actorToFollowPk);
            UUID packageId = p2PLayerPluginRoot.getNetworkClient().sendMessage(subscriberMsgRequest,PackageType.EVENT_SUBSCRIBER);
            if (packageId != null)
                messagesSentWaitingForAck.put(packageId,createPackageInformation(networkServiceType,PackageType.EVENT_SUBSCRIBER));
            return packageId;
        }else {
            throw new CantSendMessageException("SubscribeNodeEvent, The network client is not connected");
        }
    }


    private PackageInformation createPackageInformation(NetworkServiceType networkServiceType,PackageType packageType){
        return new PackageInformation(networkServiceType,packageType);
    }

    public UUID unSubscribeNodeEvent(NetworkServiceType networkServiceType,UUID eventSubscribedId) throws CantSendMessageException {
        if (p2PLayerPluginRoot.getNetworkClient().isConnected()) {
            UnSubscribeMsgRequest unSubscribeMsgRequest = new UnSubscribeMsgRequest(eventSubscribedId.toString());
            UUID packageId = p2PLayerPluginRoot.getNetworkClient().sendMessage(unSubscribeMsgRequest,PackageType.EVENT_UNSUBSCRIBER);
            if (packageId != null)
                messagesSentWaitingForAck.put(packageId,createPackageInformation(networkServiceType,PackageType.EVENT_UNSUBSCRIBER));
            return packageId;
        }else {
            throw new CantSendMessageException("SubscribeNodeEvent, The network client is not connected");
        }
    }
}
