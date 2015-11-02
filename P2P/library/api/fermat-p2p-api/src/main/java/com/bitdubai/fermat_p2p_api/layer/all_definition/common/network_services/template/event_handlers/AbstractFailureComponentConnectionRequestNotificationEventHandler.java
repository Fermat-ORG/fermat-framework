package com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.events.exceptions.UnexpectedEventException;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.abstract_classes.AbstractNetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.FailureComponentConnectionRequestNotificationEvent;

/**
 * The abstract Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.event_handlers.AbstractFailureComponentConnectionRequestNotificationEventHandler</code>
 * contains all the basic functionality of a FailureComponentConnectionRequestNotificationEventHandler.
 *
 * The method <code>handleFailureComponentRegistrationNotificationEvent</code> can be override to modify its behavior.
 *
 * Created by lnacosta (laion.cj91@gmail.com) on 30/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class AbstractFailureComponentConnectionRequestNotificationEventHandler implements FermatEventHandler {

    protected final AbstractNetworkService networkService;

    /**
     * Constructor with parameter.
     */
    public AbstractFailureComponentConnectionRequestNotificationEventHandler(final AbstractNetworkService networkService) {

        this.networkService = networkService;
    }

    @Override
    public final void handleEvent(FermatEvent fermatEvent) throws FermatException {

        if (networkService.isStarted()) {

            if (fermatEvent instanceof FailureComponentConnectionRequestNotificationEvent) {
                FailureComponentConnectionRequestNotificationEvent failureComponentConnectionRequestNotificationEvent = (FailureComponentConnectionRequestNotificationEvent) fermatEvent;

                if (failureComponentConnectionRequestNotificationEvent.getNetworkServiceApplicant().getPlatformComponentType() == networkService.getPlatformComponentType() &&
                        failureComponentConnectionRequestNotificationEvent.getNetworkServiceApplicant().getNetworkServiceType() == networkService.getNetworkServiceType()) {

                    this.handleFailureComponentRegistrationNotificationEvent(failureComponentConnectionRequestNotificationEvent.getNetworkServiceApplicant(), failureComponentConnectionRequestNotificationEvent.getRemoteParticipant());

                }
            } else {
                P2pEventType eventExpected = P2pEventType.FAILURE_COMPONENT_CONNECTION_REQUEST_NOTIFICATION;
                String context = "Event received: " + fermatEvent.getEventType().toString() + " - " + fermatEvent.getEventType().getCode()+"\n"+
                                 "Event expected: " + eventExpected.toString()              + " - " + eventExpected.getCode();
                throw new UnexpectedEventException(context);
            }
        }
    }

    protected void handleFailureComponentRegistrationNotificationEvent(final PlatformComponentProfile networkServiceApplicant,
                                                                       final PlatformComponentProfile remoteNetworkService   ) throws FermatException {

        this.networkService.handleFailureComponentRegistrationNotificationEvent(
                networkServiceApplicant,
                remoteNetworkService
        );
    }

}
