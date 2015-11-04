package com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.events.exceptions.UnexpectedEventException;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.abstract_classes.AbstractNetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteComponentConnectionRequestNotificationEvent;

/**
 * The abstract Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.event_handlers.AbstractCompleteComponentConnectionRequestNotificationEventHandler</code>
 * contains all the basic functionality of a CompleteComponentConnectionRequestNotificationEventHandler.
 *
 * The method <code>handleCompleteComponentConnectionRequestNotificationEvent</code> can be override to modify its behavior.
 *
 * Created by lnacosta (laion.cj91@gmail.com) on 30/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public abstract class AbstractCompleteComponentConnectionRequestNotificationEventHandler implements FermatEventHandler {

    protected final AbstractNetworkService networkService;

    /**
     * Constructor with parameter.
     */
    public AbstractCompleteComponentConnectionRequestNotificationEventHandler(final AbstractNetworkService networkService) {

        this.networkService = networkService;
    }

    @Override
    public final void handleEvent(FermatEvent fermatEvent) throws FermatException {


        if (networkService.isStarted()) {

            if (fermatEvent instanceof CompleteComponentConnectionRequestNotificationEvent) {

                CompleteComponentConnectionRequestNotificationEvent completeComponentConnectionRequestNotificationEvent = (CompleteComponentConnectionRequestNotificationEvent) fermatEvent;

                if(completeComponentConnectionRequestNotificationEvent.getNetworkServiceTypeApplicant() == networkService.getPlatformComponentProfilePluginRoot().getNetworkServiceType()){
                    this.networkService.handleCompleteComponentConnectionRequestNotificationEvent(completeComponentConnectionRequestNotificationEvent.getApplicantComponent(), completeComponentConnectionRequestNotificationEvent.getRemoteComponent());
                }

            } else {
                P2pEventType eventExpected = P2pEventType.COMPLETE_COMPONENT_CONNECTION_REQUEST_NOTIFICATION;
                String context = "Event received: " + fermatEvent.getEventType().toString() + " - " + fermatEvent.getEventType().getCode()+"\n"+
                                 "Event expected: " + eventExpected.toString()              + " - " + eventExpected.getCode();
                throw new UnexpectedEventException(context);
            }
        }
    }

    protected void handleCompleteComponentConnectionRequestNotificationEvent(final PlatformComponentProfile applicantComponentProfile,
                                                                             final PlatformComponentProfile remoteComponentProfile   ) throws FermatException {

        this.networkService.handleCompleteComponentConnectionRequestNotificationEvent(
                applicantComponentProfile,
                remoteComponentProfile
        );
    }
}
