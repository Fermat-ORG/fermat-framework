package com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;

import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.IncomingCryptoOnBlockchainEvent;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.IncomingCryptoOnCryptoNetworkEvent;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.IncomingCryptoReversedOnBlockchainEvent;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.IncomingCryptoReversedOnCryptoNetworkEvent;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.IncomingCryptoTransactionsWaitingTransferenceEvent;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.event_handlers.IncomingCryptoOnBlockchainEventHandler;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.event_handlers.IncomingCryptoOnCryptoNetworkEventHandler;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.event_handlers.IncomingCryptoReversedOnBlockchainEventHandler;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.event_handlers.IncomingCryptoReversedOnCryptoNetworkEventHandler;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.exceptions.CantSaveEvent;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.exceptions.CantStartServiceException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.interfaces.DealsWithRegistry;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.interfaces.TransactionService;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ciencias on 3/30/15.
 * Modified by Arturo Vallone 25/04/2015
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

public class IncomingCryptoEventRecorderService implements com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.DealsWithEvents, DealsWithRegistry, TransactionService {

    /**
     * DealsWithEvents Interface member variables.
     */
    private com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager eventManager;
    private List<com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventListener> listenersAdded = new ArrayList<>();

    /*
     * DealsWithRegistry Interface member variables.
     */
    private IncomingCryptoRegistry registry;



    /**
     * TransactionService Interface member variables.
     */
    private ServiceStatus serviceStatus = ServiceStatus.CREATED;


    /**
     * DealWithEvents Interface implementation.
     */
    @Override
    public void setEventManager(com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager eventManager) {
        this.eventManager = eventManager;
    }

    /**
     * DealWithEvents Interface implementation.
     */
    @Override
    public void setRegistry(IncomingCryptoRegistry registry) {
        this.registry = registry;
    }


    /**
     * IncomingCryptoEventRecorder Interface implementation.
     */
    @Deprecated
    public void incomingCryptoWaitingTransference(IncomingCryptoTransactionsWaitingTransferenceEvent event) throws CantSaveEvent {
        this.registry.saveNewEvent(event.getEventType().getCode(), event.getSource().getCode());
        //System.out.println("TTF - INCOMING CRYPTO EVENTRECORDER: NEW EVENT REGISTERED");
    }

    public void incomingCryptoWaitingTransference(IncomingCryptoOnCryptoNetworkEvent event) throws CantSaveEvent {
        this.registry.saveNewEvent(event.getEventType().getCode(), event.getSource().getCode());
        //System.out.println("TTF - INCOMING CRYPTO EVENTRECORDER: NEW EVENT REGISTERED");
    }

    public void incomingCryptoWaitingTransference(IncomingCryptoOnBlockchainEvent event) throws CantSaveEvent {
        this.registry.saveNewEvent(event.getEventType().getCode(), event.getSource().getCode());
        //System.out.println("TTF - INCOMING CRYPTO EVENTRECORDER: NEW EVENT REGISTERED");
    }

    public void incomingCryptoWaitingTransference(IncomingCryptoReversedOnCryptoNetworkEvent event) throws CantSaveEvent {
        this.registry.saveNewEvent(event.getEventType().getCode(), event.getSource().getCode());
        //System.out.println("TTF - INCOMING CRYPTO EVENTRECORDER: NEW EVENT REGISTERED");
    }

    public void incomingCryptoWaitingTransference(IncomingCryptoReversedOnBlockchainEvent event) throws CantSaveEvent {
        this.registry.saveNewEvent(event.getEventType().getCode(), event.getSource().getCode());
        //System.out.println("TTF - INCOMING CRYPTO EVENTRECORDER: NEW EVENT REGISTERED");
    }


    /**
     * TransactionService interface implementation.
     */
    @Override
    public void start() throws CantStartServiceException {

        /**
         * I will initialize the handling of com.bitdubai.platform events.
         */
        com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventListener eventListener;
        com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventHandler eventHandler;

        /*eventListener = eventManager.getNewListener(EventType.INCOMING_CRYPTO_TRANSACTIONS_WAITING_TRANSFERENCE);
        eventHandler = new IncomingCryptoTransactionsWaitingTransferenceEventHandler();
        ((IncomingCryptoTransactionsWaitingTransferenceEventHandler) eventHandler).setIncomingCryptoEventRecorderService(this);
        eventListener.setEventHandler(eventHandler);
        eventManager.addListener(eventListener);
        listenersAdded.add(eventListener);*/

        /**
         * Issue #543
         * Added the handlers for the new four events raised in the vault.
         */

        eventListener = eventManager.getNewListener(EventType.INCOMING_CRYPTO_ON_CRYPTO_NETWORK);
        eventHandler = new IncomingCryptoOnCryptoNetworkEventHandler();
        ((IncomingCryptoOnCryptoNetworkEventHandler) eventHandler).setIncomingCryptoEventRecorderService(this);
        eventListener.setEventHandler(eventHandler);
        eventManager.addListener(eventListener);
        listenersAdded.add(eventListener);

        eventListener = eventManager.getNewListener(EventType.INCOMING_CRYPTO_ON_BLOCKCHAIN);
        eventHandler = new IncomingCryptoOnBlockchainEventHandler();
        ((IncomingCryptoOnBlockchainEventHandler) eventHandler).setIncomingCryptoEventRecorderService(this);
        eventListener.setEventHandler(eventHandler);
        eventManager.addListener(eventListener);
        listenersAdded.add(eventListener);

        eventListener = eventManager.getNewListener(EventType.INCOMING_CRYPTO_REVERSED_ON_CRYPTO_NETWORK);
        eventHandler = new IncomingCryptoReversedOnCryptoNetworkEventHandler();
        ((IncomingCryptoReversedOnCryptoNetworkEventHandler) eventHandler).setIncomingCryptoEventRecorderService(this);
        eventListener.setEventHandler(eventHandler);
        eventManager.addListener(eventListener);
        listenersAdded.add(eventListener);

        eventListener = eventManager.getNewListener(EventType.INCOMING_CRYPTO_REVERSED_ON_BLOCKCHAIN);
        eventHandler = new IncomingCryptoReversedOnBlockchainEventHandler();
        ((IncomingCryptoReversedOnBlockchainEventHandler) eventHandler).setIncomingCryptoEventRecorderService(this);
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
        for (com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventListener eventListener : listenersAdded) {
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
