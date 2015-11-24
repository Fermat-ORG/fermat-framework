package com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.events.exceptions.UnexpectedEventException;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.abstract_classes.AbstractNetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteComponentRegistrationNotificationEvent;

/**
 * The abstract Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.event_handlers.AbstractCompleteComponentRegistrationNotificationEventHandler</code>
 * contains all the basic functionality of a CompleteComponentRegistrationNotificationEventHandler.
 *
 * The method <code>handleCompleteComponentConnectionRequestNotificationEvent</code> can be override to modify its behavior.
 *
 * Created by lnacosta (laion.cj91@gmail.com) on 30/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public abstract class AbstractCompleteComponentRegistrationNotificationEventHandler implements FermatEventHandler {

    protected final AbstractNetworkService networkService;

    /**
     * Constructor with parameter.
     */
    public AbstractCompleteComponentRegistrationNotificationEventHandler(final AbstractNetworkService networkService) {
        this.networkService = networkService;
    }

    @Override
    public final void handleEvent(FermatEvent fermatEvent) throws FermatException {

        if (networkService.isStarted()) {

            if (fermatEvent instanceof CompleteComponentRegistrationNotificationEvent) {

                CompleteComponentRegistrationNotificationEvent completeComponentRegistrationNotificationEvent = (CompleteComponentRegistrationNotificationEvent) fermatEvent;

                if (completeComponentRegistrationNotificationEvent.getPlatformComponentProfileRegistered().getNetworkServiceType() == networkService.getNetworkServiceType())
                     this.handleCompleteComponentRegistrationNotificationEvent(completeComponentRegistrationNotificationEvent.getPlatformComponentProfileRegistered());

            } else {
                P2pEventType eventExpected = P2pEventType.COMPLETE_COMPONENT_REGISTRATION_NOTIFICATION;
                String context = "Event received: " + fermatEvent.getEventType().toString() + " - " + fermatEvent.getEventType().getCode()+"\n"+
                                 "Event expected: " + eventExpected.toString()              + " - " + eventExpected.getCode();
                throw new UnexpectedEventException(context);
            }
        }
    }

    protected void handleCompleteComponentRegistrationNotificationEvent(final PlatformComponentProfile platformComponentProfileRegistered) throws FermatException {

        this.networkService.handleCompleteComponentRegistrationNotificationEvent(
                platformComponentProfileRegistered
        );
    }
}
