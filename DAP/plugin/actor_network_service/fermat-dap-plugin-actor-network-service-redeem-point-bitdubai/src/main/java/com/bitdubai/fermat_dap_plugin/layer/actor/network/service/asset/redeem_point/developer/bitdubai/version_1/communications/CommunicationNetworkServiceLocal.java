package com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.bitdubai.version_1.communications;

import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkServiceLocal;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.bitdubai.version_1.AssetRedeemPointActorNetworkServicePluginRoot;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.bitdubai.version_1.database.communications.OutgoingMessageDao;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatMessageCommunication;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatMessageCommunicationFactory;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.NewNetworkServiceMessageReceivedNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessageContentType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessagesStatus;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by franklin on 18/10/15.
 */
public class CommunicationNetworkServiceLocal implements Observer, NetworkServiceLocal {

    /**
     * Represent the profile of the remote network service
     */
    private PlatformComponentProfile remoteNetworkServiceProfile;

    /**
     * Represent the profile of the local network service
     */
    private NetworkServiceType networkServiceTypePluginRoot;

    /**
     * DealsWithErrors Interface member variables.
     */
    private ErrorManager errorManager;

    /**
     * DealWithEvents Interface member variables.
     */
    private EventManager eventManager;

    /**
     * Represent the outgoingMessageDao
     */
    private OutgoingMessageDao outgoingMessageDao;

    /**
     * Represent the lastMessageReceived
     */
    private FermatMessage lastMessageReceived;

    /**
     * Constructor with parameters
     *
     * @param remoteNetworkServiceProfile
     * @param errorManager                  instance
     * @param outgoingMessageDao            instance
     */
    public CommunicationNetworkServiceLocal(PlatformComponentProfile remoteNetworkServiceProfile,
                                            ErrorManager errorManager, EventManager eventManager,
                                            OutgoingMessageDao outgoingMessageDao,
                                            NetworkServiceType networkServiceTypePluginRoot) {
        this.remoteNetworkServiceProfile = remoteNetworkServiceProfile;
        this.errorManager = errorManager;
        this.eventManager = eventManager;
        this.outgoingMessageDao = outgoingMessageDao;
        this.networkServiceTypePluginRoot = networkServiceTypePluginRoot;
    }


    /**
     * (non-javadoc)
     */
    public void sendMessage(final String senderIdentityPublicKey,final String requestIdentityPublickKey, final String messageContent) {
        try {

            FermatMessage fermatMessage  = FermatMessageCommunicationFactory.constructFermatMessage(senderIdentityPublicKey,  //Sender NetworkService
                    requestIdentityPublickKey,   //Receiver
                    messageContent,                //Message Content
                    FermatMessageContentType.TEXT);//Type
            /*
             * Configure the correct status
             */
            ((FermatMessageCommunication)fermatMessage).setFermatMessagesStatus(FermatMessagesStatus.PENDING_TO_SEND);

            /*
             * Save to the data base table
             */
            outgoingMessageDao.create(fermatMessage);

        } catch (Exception e) {
            e.printStackTrace();
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not send message. Error reason: " + e.getMessage()));
        }

    }


    /**
     * Notify the client when a incoming message is receive by the incomingTemplateNetworkServiceMessage
     * ant fire a new event
     *
     * @param incomingMessage received
     */
    private void onMessageReceived(FermatMessage incomingMessage) {

        System.out.println("CommunicationNetworkServiceLocal - onMessageReceived ");
        System.out.println(incomingMessage.getContent());

        /*
         * set the last message received
         */
        this.lastMessageReceived = incomingMessage;

        /**
         * Put the message on a event and fire new event
         */
        FermatEvent fermatEvent = eventManager.getNewEvent(P2pEventType.NEW_NETWORK_SERVICE_MESSAGE_RECEIVE_NOTIFICATION);
        fermatEvent.setSource(AssetRedeemPointActorNetworkServicePluginRoot.EVENT_SOURCE);
        ((NewNetworkServiceMessageReceivedNotificationEvent) fermatEvent).setData(incomingMessage);
        ((NewNetworkServiceMessageReceivedNotificationEvent) fermatEvent).setNetworkServiceTypeApplicant(networkServiceTypePluginRoot);
        eventManager.raiseEvent(fermatEvent);

    }

    /**
     * This method is called automatically when CommunicationNetworkServiceRemoteAgent (Observable object) update the database
     * when new message is received
     *
     * @param observable the observable object
     * @param data       the data update
     */
    @Override
    public void update(Observable observable, Object data) {

        //Validate and process
        if (data instanceof FermatMessage) {
            onMessageReceived((FermatMessage) data);
        }
    }

    /**
     * (non-javadoc)
     * @see NetworkServiceLocal#
     */
    public FermatMessage getLastMessageReceived() {
        return lastMessageReceived;
    }

}
