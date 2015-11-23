package com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.event_handlers.communication;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.enums.ActorProtocolState;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.IntraActorNetworkServicePluginRoot;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.communications.CommunicationNetworkServiceConnectionManager;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.ActorNetworkServiceRecord;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.ActorNetworkServiceRecordedAgent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.event_handlers.AbstractCommunicationBaseEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.NewNetworkServiceMessageSentNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.google.gson.Gson;

import org.apache.commons.collections.bag.SynchronizedSortedBag;

/**
 * Created by Joaquin C. on 23/11/15.
 */
public class NewSentMessageNotificationEventHandler extends AbstractCommunicationBaseEventHandler<NewNetworkServiceMessageSentNotificationEvent> {

    /**
     * Represent the communicationNetworkServiceConnectionManager
     */
    private CommunicationNetworkServiceConnectionManager communicationNetworkServiceConnectionManager;


    /**
     * Agent
     */
    private ActorNetworkServiceRecordedAgent actorNetworkServiceRecordedAgent;


    /**
         * Constructor with parameter
         *
         * @param
         */
    public NewSentMessageNotificationEventHandler(IntraActorNetworkServicePluginRoot intraActorNetworkServicePluginRoot) {
        super(intraActorNetworkServicePluginRoot);
    }

        /**
         * (non-Javadoc)
         *
         * @see FermatEventHandler#handleEvent(FermatEvent)
         *
         * @param event
         * @throws Exception
         */
        @Override
        public void processEvent(NewNetworkServiceMessageSentNotificationEvent event) {

            FermatMessage message = (FermatMessage) event.getData();
            Gson gson = new Gson();

            try {
                message.toJson();

                ActorNetworkServiceRecord actorNetworkServiceRecord = gson.fromJson(message.getContent(), ActorNetworkServiceRecord.class);


                if (actorNetworkServiceRecord.getActorProtocolState().getCode().equals(ActorProtocolState.DONE)) {
                    // close connection, sender is the destination
                    communicationNetworkServiceConnectionManager.closeConnection(actorNetworkServiceRecord.getActorSenderPublicKey());
                    actorNetworkServiceRecordedAgent.getPoolConnectionsWaitingForResponse().remove(actorNetworkServiceRecord.getActorSenderPublicKey());

                }


            } catch (Exception e) {
                //quiere decir que no estoy reciviendo metadata si no una respuesta
                System.out.print("EXCEPCION DENTRO DEL PROCCESS EVENT");
                e.printStackTrace();

            }

        }
}
