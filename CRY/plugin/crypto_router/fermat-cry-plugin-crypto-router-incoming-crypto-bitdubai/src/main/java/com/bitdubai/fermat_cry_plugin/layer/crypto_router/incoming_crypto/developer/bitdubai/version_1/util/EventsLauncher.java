package com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.util;

import com.bitdubai.fermat_api.layer.all_definition.event.EventSource;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.PlatformEvent;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;

import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.exceptions.SpecialistNotRegisteredException;

import java.util.EnumSet;

/**
 * Created by eze on 12/06/15.
 */

/*
 * The purpose of this class is to identify the events that must be notified and raise them.
 */
public class EventsLauncher implements DealsWithEvents {

    /*
     * DealsWithEvents Interface member variabñes
     */
    private EventManager eventManager;

    public void sendEvents(EnumSet<Specialist> specialists) throws SpecialistNotRegisteredException {
        for(Specialist specialist : specialists) {
// TODO WHEN THE SPECIALIST IS NOT FOUNDED I RAISE AN EXCEPTION BUT WHAT DO I DO WHIT THE REST OF SPECIALISTS?
            switch (specialist) {
                case EXTRA_USER_SPECIALIST:
                    /**
                     * Issue #553
                     */
                    //PlatformEvent platformEvent = eventManager.getNewEvent(EventType.INCOMING_CRYPTO_TRANSACTIONS_WAITING_TRANSFERENCE_EXTRA_USER);
                    //platformEvent.setSource(EventSource.CRYPTO_ROUTER);
                    //eventManager.raiseEvent(platformEvent);

                    PlatformEvent platformEventOnCryptoNetwork = eventManager.getNewEvent(EventType.INCOMING_CRYPTO_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_EXTRA_USER);
                    platformEventOnCryptoNetwork.setSource(EventSource.CRYPTO_ROUTER);
                    eventManager.raiseEvent(platformEventOnCryptoNetwork);

                    PlatformEvent platformEventOnBlockchain = eventManager.getNewEvent(EventType.INCOMING_CRYPTO_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_EXTRA_USER);
                    platformEventOnBlockchain.setSource(EventSource.CRYPTO_ROUTER);
                    eventManager.raiseEvent(platformEventOnBlockchain);

                    PlatformEvent platformEventReversedOnCryptoNetwork = eventManager.getNewEvent(EventType.INCOMING_CRYPTO_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_EXTRA_USER);
                    platformEventReversedOnCryptoNetwork.setSource(EventSource.CRYPTO_ROUTER);
                    eventManager.raiseEvent(platformEventReversedOnCryptoNetwork);

                    PlatformEvent platformEventReversedOnBlockChain = eventManager.getNewEvent(EventType.INCOMING_CRYPTO_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_EXTRA_USER);
                    platformEventReversedOnBlockChain.setSource(EventSource.CRYPTO_ROUTER);
                    eventManager.raiseEvent(platformEventReversedOnBlockChain);


                    System.out.println("TTF - INCOMING CRYPTO RELAY: EVENTSLAUNCHER - EVENTS RAISED");
                    break;
                default:
                    throw new SpecialistNotRegisteredException("I could not found the event for this specialist", null,"Soecialist: " + specialist.name() +" with code: " + specialist.getCode(),"Specialist not considered in switch statement");
            }
        }
    }

    /*
     * DealsWithEvents Interface methods implementation
     */
    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }
}
