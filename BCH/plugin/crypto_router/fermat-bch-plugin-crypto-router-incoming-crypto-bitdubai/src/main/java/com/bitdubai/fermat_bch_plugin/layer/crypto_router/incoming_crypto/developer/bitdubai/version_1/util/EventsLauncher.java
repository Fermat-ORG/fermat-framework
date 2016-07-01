package com.bitdubai.fermat_bch_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.util;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;


import com.bitdubai.fermat_bch_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.exceptions.CryptoStatusNotHandledException;

import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.enums.EventType;
import com.bitdubai.fermat_bch_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.exceptions.SpecialistNotRegisteredException;

import java.util.Set;

/**
 * Created by eze on 12/06/15.
 */

/*
 * The purpose of this class is to identify the events that must be notified and raise them.
 */
public class EventsLauncher implements DealsWithEvents {

    private final EventSource eventSource = EventSource.CRYPTO_ROUTER;

    /*
     * DealsWithEvents Interface member variabñes
     */
    private EventManager eventManager;

    public void sendEvents(Set<SpecialistAndCryptoStatus> specialists) throws SpecialistNotRegisteredException, CryptoStatusNotHandledException {
        for (SpecialistAndCryptoStatus specialistAndCryptoStatus : specialists) {
        // TODO WHEN THS E SPECIALIST INOT FOUNDED I RAISE AN EXCEPTION BUT WHAT DO I DO WITH THE REST OF SPECIALISTS?
            Specialist specialist = specialistAndCryptoStatus.getSpecialist();
            CryptoStatus cryptoStatus = specialistAndCryptoStatus.getCryptoStatus();
            decideTheEventToRaiseAndRaiseIt(specialist, cryptoStatus);
        }
        //System.out.println("TTF - INCOMING CRYPTO RELAY: EVENTSLAUNCHER - EVENTS RAISED");
    }

    private void decideTheEventToRaiseAndRaiseIt(Specialist specialist, CryptoStatus cryptoStatus) throws CryptoStatusNotHandledException, SpecialistNotRegisteredException {
        switch (specialist) {
            case EXTRA_USER_SPECIALIST:
                switch (cryptoStatus) {
                    case ON_CRYPTO_NETWORK:
                        raiseEvent(EventType.INCOMING_CRYPTO_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_EXTRA_USER);
                        break;
                    case ON_BLOCKCHAIN:
                        raiseEvent(EventType.INCOMING_CRYPTO_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_EXTRA_USER);
                        break;
                    case REVERSED_ON_CRYPTO_NETWORK:
                        raiseEvent(EventType.INCOMING_CRYPTO_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_EXTRA_USER);
                        break;
                    case REVERSED_ON_BLOCKCHAIN:
                        raiseEvent(EventType.INCOMING_CRYPTO_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_EXTRA_USER);
                        break;
                    case IRREVERSIBLE:
                        //define what to do.
                        break;
                    default:
                        String message       = "I could not find the event for this crypto status";
                        String context       = "Specialist: " + specialist.name() + " with code: " + specialist.getCode() + FermatException.CONTEXT_CONTENT_SEPARATOR + "Crypto Status: " + cryptoStatus.name() + " with code: " + cryptoStatus.getCode();
                        String possibleCause = "Crypto Status not considered in switch statement";
                        throw new CryptoStatusNotHandledException(message, null, context , possibleCause);
                }
                break;
            case INTRA_USER_SPECIALIST:
                switch (cryptoStatus) {
                    case ON_CRYPTO_NETWORK:
                        System.out.println("INCOMING CRYPTO ROUTER ON CRYPTO NETWORK, LAUNCH EVENT");
                        raiseEvent(EventType.INCOMING_CRYPTO_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_INTRA_USER);
                        break;
                    case ON_BLOCKCHAIN:
                        System.out.println("INCOMING CRYPTO ROUTER ON BLOCKCHAIN, LAUNCH EVENT");
                        raiseEvent(EventType.INCOMING_CRYPTO_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_INTRA_USER);
                        break;
                    case REVERSED_ON_CRYPTO_NETWORK:
                        System.out.println("INCOMING CRYPTO ROUTER REVERSED ON CRYPRO NETWORK, LAUNCH EVENT");
                        raiseEvent(EventType.INCOMING_CRYPTO_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_INTRA_USER);
                        break;
                    case REVERSED_ON_BLOCKCHAIN:
                        System.out.println("INCOMING CRYPTO ROUTER REVERSED ON BLOCKCHAIN, LAUNCH EVENT");
                        raiseEvent(EventType.INCOMING_CRYPTO_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_INTRA_USER);
                        break;
                    case IRREVERSIBLE:
                        System.out.println("INCOMING CRYPTO ROUTER IRREVERSIBLE LAUNCH EVENT");
                        //define what to do.
                        break;
                    default:
                        String message       = "I could not find the event for this crypto status";
                        String context       = "Specialist: " + specialist.name() + " with code: " + specialist.getCode() + FermatException.CONTEXT_CONTENT_SEPARATOR + "Crypto Status: " + cryptoStatus.name() + " with code: " + cryptoStatus.getCode();
                        String possibleCause = "Crypto Status not considered in switch statement";
                        throw new CryptoStatusNotHandledException(message, null, context , possibleCause);
                }
                break;
            case ASSET_ISSUER_SPECIALIST:
                switch (cryptoStatus) {
                    case ON_CRYPTO_NETWORK:
                        raiseEvent(EventType.INCOMING_ASSET_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_ISSUER);
                        System.out.println("Event INCOMING_ASSET_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_ISSUER) raised.");
                        break;
                    case ON_BLOCKCHAIN:
                        raiseEvent(EventType.INCOMING_ASSET_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_ASSET_ISSUER);
                        System.out.println("Event INCOMING_ASSET_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_ASSET_ISSUER raised.");
                        break;
                    case REVERSED_ON_CRYPTO_NETWORK:
                        raiseEvent(EventType.INCOMING_ASSET_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_ISSUER);
                        break;
                    case REVERSED_ON_BLOCKCHAIN:
                        raiseEvent(EventType.INCOMING_ASSET_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_ASSET_ISSUER);
                        break;
                    case IRREVERSIBLE:
                        //define what to do.
                        break;
                    default:
                        String message = "I could not find the event for this crypto status";
                        String context = "Specialist: " + specialist.name() + " with code: " + specialist.getCode() + FermatException.CONTEXT_CONTENT_SEPARATOR + "Crypto Status: " + cryptoStatus.name() + " with code: " + cryptoStatus.getCode();
                        String possibleCause = "Crypto Status not considered in switch statement";
                        throw new CryptoStatusNotHandledException(message, null, context, possibleCause);

                }
                break;
            case ASSET_USER_SPECIALIST:
                switch (cryptoStatus) {
                    case ON_CRYPTO_NETWORK:
                        raiseEvent(EventType.INCOMING_ASSET_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_USER);
                        break;
                    case ON_BLOCKCHAIN:
                        raiseEvent(EventType.INCOMING_ASSET_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_ASSET_USER);
                        break;
                    case REVERSED_ON_CRYPTO_NETWORK:
                        raiseEvent(EventType.INCOMING_ASSET_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_USER);
                        break;
                    case REVERSED_ON_BLOCKCHAIN:
                        raiseEvent(EventType.INCOMING_ASSET_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_ASSET_USER);
                        break;
                    case IRREVERSIBLE:
                        //define what to do.
                        break;
                    default:
                        String message = "I could not find the event for this crypto status";
                        String context = "Specialist: " + specialist.name() + " with code: " + specialist.getCode() + FermatException.CONTEXT_CONTENT_SEPARATOR + "Crypto Status: " + cryptoStatus.name() + " with code: " + cryptoStatus.getCode();
                        String possibleCause = "Crypto Status not considered in switch statement";
                        throw new CryptoStatusNotHandledException(message, null, context, possibleCause);
                }
                break;
            case REDEEM_POINT_SPECIALIST:
                switch (cryptoStatus) {
                    case ON_CRYPTO_NETWORK:
                        raiseEvent(EventType.INCOMING_ASSET_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_REDEEM_POINT);
                        break;
                    case ON_BLOCKCHAIN:
                        raiseEvent(EventType.INCOMING_ASSET_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_REDEEM_POINT);
                        break;
                    case REVERSED_ON_CRYPTO_NETWORK:
                        raiseEvent(EventType.INCOMING_ASSET_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_REDEEM_POINT);
                        break;
                    case REVERSED_ON_BLOCKCHAIN:
                        raiseEvent(EventType.INCOMING_ASSET_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_REDEEM_POINT);
                        break;
                    case IRREVERSIBLE:
                        //define what to do.
                        break;
                    default:
                        String message = "I could not find the event for this crypto status";
                        String context = "Specialist: " + specialist.name() + " with code: " + specialist.getCode() + FermatException.CONTEXT_CONTENT_SEPARATOR + "Crypto Status: " + cryptoStatus.name() + " with code: " + cryptoStatus.getCode();
                        String possibleCause = "Crypto Status not considered in switch statement";
                        throw new CryptoStatusNotHandledException(message, null, context, possibleCause);
                }
                break;
            case ASSET_REDEMPTION_SPECIALIST:
                switch (cryptoStatus) {
                    case ON_CRYPTO_NETWORK:
                        raiseEvent(EventType.INCOMING_ASSET_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_REDEMPTION);
                        break;
                    case ON_BLOCKCHAIN:
                        raiseEvent(EventType.INCOMING_ASSET_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_REDEMPTION);
                        break;
                    case REVERSED_ON_CRYPTO_NETWORK:
                        raiseEvent(EventType.INCOMING_ASSET_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_REDEMPTION);
                        break;
                    case REVERSED_ON_BLOCKCHAIN:
                        raiseEvent(EventType.INCOMING_ASSET_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_REDEMPTION);
                        break;
                    case IRREVERSIBLE:
                        //define what to do.
                        break;
                    default:
                        String message = "I could not find the event for this crypto status";
                        String context = "Specialist: " + specialist.name() + " with code: " + specialist.getCode() + FermatException.CONTEXT_CONTENT_SEPARATOR + "Crypto Status: " + cryptoStatus.name() + " with code: " + cryptoStatus.getCode();
                        String possibleCause = "Crypto Status not considered in switch statement";
                        throw new CryptoStatusNotHandledException(message, null, context, possibleCause);
                }
                break;
            case UNKNOWN_SPECIALIST:
                /**
                 * with an unknown specialist, there is nothing left to do.
                 */
                break;
            default:
                String message       = "I could not find the event for this specialist";
                String context       = "Specialist: " + specialist.name() + " with code: " + specialist.getCode() + FermatException.CONTEXT_CONTENT_SEPARATOR + "Crypto Status: " + cryptoStatus.name() + " with code: " + cryptoStatus.getCode();
                String possibleCause = "Specialist not considered in switch statement";
                throw new SpecialistNotRegisteredException(message, null, context, possibleCause);
        }
    }

    private void raiseEvent(EventType eventType){
        FermatEvent eventToRaise = eventManager.getNewEvent(eventType);
        eventToRaise.setSource(this.eventSource);
        eventManager.raiseEvent(eventToRaise);
    }

    /*
     * DealsWithEvents Interface methods implementation
     */
    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }
}
