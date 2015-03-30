package com.bitdubai.fermat_core.layer._13_transaction.incoming_crypto.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer._1_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventSource;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventType;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.events.*;
import com.bitdubai.fermat_core.layer._13_transaction.incoming_crypto.developer.bitdubai.version_1.exceptions.CantStartAgentException;
import com.bitdubai.fermat_core.layer._13_transaction.incoming_crypto.developer.bitdubai.version_1.exceptions.CantStartServiceException;
import com.bitdubai.fermat_core.layer._13_transaction.incoming_crypto.developer.bitdubai.version_1.interfaces.TransactionAgent;

/**
 * Created by ciencias on 3/30/15.
 */


/**
 * Este es un proceso que toma las transacciones registradas en el registry en un estado pendiente de anunciar, 
 * las lee una por una y dispara el evento que corresponda en cada caso.
 *
 * Para cada transaccion, consulta el Address Book enviandole la direccion en la que se recibio la crypto.
 * El Address book devolvera el User al cual esa direccion fue entregada. De esta manera esta clase podra determinar
 * contra que tipo de usuario se esta ejecutando esta transaccion y a partir de ahi podra disparar el evento que 
 * corresponda para cada tipo de usuario.
 *
 * Al ser un Agent, la ejecucion de esta clase es en su propio Thread. Seguir el patron de diseño establecido para esto.
 * *
 * * * * * * * 
 *
 * * * * * * 
 */


public class IncomingCryptoRelayAgent implements DealsWithEvents, TransactionAgent {


    /**
     * DealsWithEvents Interface member variables.
     */
    EventManager eventManager;



    /**
     * DealWithEvents Interface implementation.
     */
    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }


    /**
     * TransactionAgent Interface implementation.
     */
    @Override
    public void start() throws CantStartAgentException {

    }

    @Override
    public void stop() {

    }



    private void eventsToRaise(){

        PlatformEvent platformEvent = eventManager.getNewEvent(EventType.INCOMING_CRYPTO_IDENTIFIED_FROM_EXTRA_USER);
        ((IncomingCryptoIdentifiedFromExtraUserEvent)platformEvent).setSource(EventSource.CRYPTO_ADDRESS_BOOK);
        eventManager.raiseEvent(platformEvent);

        PlatformEvent platformEvent_1 = eventManager.getNewEvent(EventType.INCOMING_CRYPTO_IDENTIFIED_FROM_INTRA_USER);
        ((IncomingCryptoIdentifiedFromIntraUserEvent)platformEvent).setSource(EventSource.CRYPTO_ADDRESS_BOOK);
        eventManager.raiseEvent(platformEvent);

        PlatformEvent platformEvent_2 = eventManager.getNewEvent(EventType.INCOMING_CRYPTO_IDENTIFIED_FROM_DEVICE_USER);
        ((IncomingCryptoIdentifiedFromDeviceUserEvent)platformEvent).setSource(EventSource.CRYPTO_ADDRESS_BOOK);
        eventManager.raiseEvent(platformEvent);

        PlatformEvent platformEvent_3 = eventManager.getNewEvent(EventType.INCOMING_CRYPTO_RECEIVED_FROM_EXTRA_USER);
        ((IncomingCryptoReceivedFromExtraUserEvent)platformEvent).setSource(EventSource.CRYPTO_ADDRESS_BOOK);
        eventManager.raiseEvent(platformEvent);

        PlatformEvent platformEvent_4 = eventManager.getNewEvent(EventType.INCOMING_CRYPTO_RECEIVED_FROM_INTRA_USER);
        ((IncomingCryptoReceivedFromIntraUserEvent)platformEvent).setSource(EventSource.CRYPTO_ADDRESS_BOOK);
        eventManager.raiseEvent(platformEvent);

        PlatformEvent platformEvent_5 = eventManager.getNewEvent(EventType.INCOMING_CRYPTO_RECEIVED_FROM_DEVICE_USER);
        ((IncomingCryptoReceivedFromDeviceUserEvent)platformEvent).setSource(EventSource.CRYPTO_ADDRESS_BOOK);
        eventManager.raiseEvent(platformEvent);

        PlatformEvent platformEvent_6 = eventManager.getNewEvent(EventType.INCOMING_CRYPTO_RECEPTION_CONFIRMED_FROM_DEVICE_USER);
        ((IncomingCryptoReceptionConfirmedFromDeviceUserEvent)platformEvent).setSource(EventSource.CRYPTO_ADDRESS_BOOK);
        eventManager.raiseEvent(platformEvent);

        PlatformEvent platformEvent_7 = eventManager.getNewEvent(EventType.INCOMING_CRYPTO_RECEPTION_CONFIRMED_FROM_INTRA_USER);
        ((IncomingCryptoReceptionConfirmedFromIntraUserEvent)platformEvent).setSource(EventSource.CRYPTO_ADDRESS_BOOK);
        eventManager.raiseEvent(platformEvent);
        eventManager.raiseEvent(platformEvent);

        PlatformEvent platformEvent_8 = eventManager.getNewEvent(EventType.INCOMING_CRYPTO_RECEPTION_CONFIRMED_FROM_EXTRA_USER);
        ((IncomingCryptoReceptionConfirmedFromExtraUserEvent)platformEvent).setSource(EventSource.CRYPTO_ADDRESS_BOOK);
        eventManager.raiseEvent(platformEvent);
        eventManager.raiseEvent(platformEvent);

        PlatformEvent platformEvent_9 = eventManager.getNewEvent(EventType.INCOMING_CRYPTO_REVERSED_FROM_INTRA_USER);
        ((IncomingCryptoReversedFromIntraUserEvent)platformEvent).setSource(EventSource.CRYPTO_ADDRESS_BOOK);
        eventManager.raiseEvent(platformEvent);

        PlatformEvent platformEvent_10 = eventManager.getNewEvent(EventType.INCOMING_CRYPTO_REVERSED_FROM_EXTRA_USER);
        ((IncomingCryptoReversedFromExtraUserEvent)platformEvent).setSource(EventSource.CRYPTO_ADDRESS_BOOK);
        eventManager.raiseEvent(platformEvent);

        PlatformEvent platformEvent_11 = eventManager.getNewEvent(EventType.INCOMING_CRYPTO_REVERSED_FROM_DEVICE_USER);
        ((IncomingCryptoReversedFromDeviceUserEvent)platformEvent).setSource(EventSource.CRYPTO_ADDRESS_BOOK);
        eventManager.raiseEvent(platformEvent);

    }



}
