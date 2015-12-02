package com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.bitdubai.version_1.AssetRedeemPointActorNetworkServicePluginRoot;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.NewNetworkServiceMessageSentNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;

/**
 * Created by Nerio on 26/11/15.
 */
public class NewSentMessagesNotificationEventHandler implements FermatEventHandler {

    /**
     * Constructor with parameter
     *
     * @param
     */
    public NewSentMessagesNotificationEventHandler() {

    }

    /**
     * (non-Javadoc)
     *
     * @param platformEvent
     * @throws Exception
     * @see FermatEventHandler#handleEvent(FermatEvent)
     */
    @Override
    public void handleEvent(FermatEvent platformEvent) throws FermatException {

        if (platformEvent.getSource() == AssetRedeemPointActorNetworkServicePluginRoot.EVENT_SOURCE) {

            System.out.println("CompleteComponentConnectionRequestNotificationEventHandler - handleEvent platformEvent =" + platformEvent.toString());
            System.out.println("ACTOR NETWORK SERVICE ASSET REDEEM POINT - NOTIFICACION EVENTO MENSAJE ENVIADO!!!!");
            NewNetworkServiceMessageSentNotificationEvent newNetworkServiceMessageSentNotificationEvent = (NewNetworkServiceMessageSentNotificationEvent) platformEvent;
            FermatMessage fermatMessage = (FermatMessage) newNetworkServiceMessageSentNotificationEvent.getData();

        }
    }
}