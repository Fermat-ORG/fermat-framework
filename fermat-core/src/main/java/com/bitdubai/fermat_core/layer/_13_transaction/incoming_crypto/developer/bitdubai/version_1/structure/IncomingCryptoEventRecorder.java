package com.bitdubai.fermat_core.layer._13_transaction.incoming_crypto.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.*;
import com.bitdubai.fermat_core.layer._13_transaction.incoming_crypto.developer.bitdubai.version_1.event_handlers.IncomingCryptoIdentifiedEventHandler;
import com.bitdubai.fermat_core.layer._13_transaction.incoming_crypto.developer.bitdubai.version_1.event_handlers.IncomingCryptoReceivedEventHandler;
import com.bitdubai.fermat_core.layer._13_transaction.incoming_crypto.developer.bitdubai.version_1.event_handlers.IncomingCryptoReceptionConfirmedEventHandler;
import com.bitdubai.fermat_core.layer._13_transaction.incoming_crypto.developer.bitdubai.version_1.event_handlers.IncomingCryptoReversedEventHandler;
import com.bitdubai.fermat_core.layer._13_transaction.incoming_crypto.developer.bitdubai.version_1.exceptions.CantSaveEvent;
import com.bitdubai.fermat_core.layer._13_transaction.incoming_crypto.developer.bitdubai.version_1.exceptions.CantStartAgentException;
import com.bitdubai.fermat_core.layer._13_transaction.incoming_crypto.developer.bitdubai.version_1.exceptions.CantStartServiceException;
import com.bitdubai.fermat_core.layer._13_transaction.incoming_crypto.developer.bitdubai.version_1.interfaces.TransactionService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ciencias on 3/30/15.
 */

/**
 * Esta es la clase que escucha los eventos relacionados a Incoming Crypto disparados por cualquier plugin que reciba
 * crypto currencies. 
 *
 * Cuando detecta un evento lo escribe en su tabla de base de datos.
 *
 *
 *
 * * * * * * * *
 */

public class IncomingCryptoEventRecorder implements DealsWithEvents, TransactionService {
    
    /**
     * DealsWithEvents Interface member variables.
     */
    EventManager eventManager;
    List<EventListener> listenersAdded = new ArrayList<>();

    /**
     * TransactionService Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;
    
    
    /**
     * IncomingCryptoEventRecorder Interface implementation.
     */
    
    public void incomingCryptoIdentified() throws CantSaveEvent {
        
    }

    public void incomingCryptoReceived()  throws CantSaveEvent {

    }

    public void incomingCryptoReceptionConfirmed()  throws CantSaveEvent {

    }

    public void incomingCryptoReversed() throws CantSaveEvent {

    }

    /**
     * DealWithEvents Interface implementation.
     */
    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    
    /**
     * TransactionService interface implementation.
     */
    @Override
    public void start() throws CantStartServiceException {

        /**
         * I will initialize the handling of com.bitdubai.platform events.
         */

        EventListener eventListener;
        EventHandler eventHandler;

        eventListener = eventManager.getNewListener(EventType.INCOMING_CRYPTO_IDENTIFIED);
        eventHandler = new IncomingCryptoIdentifiedEventHandler();
        ((IncomingCryptoIdentifiedEventHandler) eventHandler).setIncomingCryptoEventRecorder(this);
        eventListener.setEventHandler(eventHandler);
        eventManager.addListener(eventListener);
        listenersAdded.add(eventListener);

        eventListener = eventManager.getNewListener(EventType.INCOMING_CRYPTO_RECEIVED);
        eventHandler = new IncomingCryptoReceivedEventHandler();
        ((IncomingCryptoReceivedEventHandler) eventHandler).setIncomingCryptoEventRecorder(this);
        eventListener.setEventHandler(eventHandler);
        eventManager.addListener(eventListener);
        listenersAdded.add(eventListener);

        eventListener = eventManager.getNewListener(EventType.INCOMING_CRYPTO_RECEPTION_CONFIRMED);
        eventHandler = new IncomingCryptoReceptionConfirmedEventHandler();
        ((IncomingCryptoReceptionConfirmedEventHandler) eventHandler).setIncomingCryptoEventRecorder(this);
        eventListener.setEventHandler(eventHandler);
        eventManager.addListener(eventListener);
        listenersAdded.add(eventListener);


        eventListener = eventManager.getNewListener(EventType.INCOMING_CRYPTO_REVERSED);
        eventHandler = new IncomingCryptoReversedEventHandler();
        ((IncomingCryptoReversedEventHandler) eventHandler).setIncomingCryptoEventRecorder(this);
        eventListener.setEventHandler(eventHandler);
        eventManager.addListener(eventListener);
        listenersAdded.add(eventListener);

        this.serviceStatus = ServiceStatus.STARTED;
        
    }

    @Override
    public void stop() {

        /**
         * I will remove all the event listeners registered with the event manager.
         */

        for (EventListener eventListener : listenersAdded) {
            eventManager.removeListener(eventListener);
        }

        listenersAdded.clear();
        
        this.serviceStatus = ServiceStatus.STOPPED;
        
    }

    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }


}
