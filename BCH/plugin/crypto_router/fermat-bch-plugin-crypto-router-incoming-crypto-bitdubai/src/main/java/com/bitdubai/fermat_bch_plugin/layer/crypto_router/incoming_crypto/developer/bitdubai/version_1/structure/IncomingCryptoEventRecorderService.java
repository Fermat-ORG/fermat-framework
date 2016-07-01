package com.bitdubai.fermat_bch_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;

import com.bitdubai.fermat_bch_api.layer.crypto_vault.events.IncomingCryptoOnBlockchainEvent;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.events.IncomingCryptoOnCryptoNetworkEvent;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.events.IncomingCryptoReversedOnBlockchainEvent;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.events.IncomingCryptoReversedOnCryptoNetworkEvent;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.IncomingCryptoTransactionsWaitingTransferenceEvent;
import com.bitdubai.fermat_bch_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.event_handlers.IncomingCryptoOnBlockchainEventHandler;
import com.bitdubai.fermat_bch_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.event_handlers.IncomingCryptoOnCryptoNetworkEventHandler;
import com.bitdubai.fermat_bch_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.event_handlers.IncomingCryptoReversedOnBlockchainEventHandler;
import com.bitdubai.fermat_bch_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.event_handlers.IncomingCryptoReversedOnCryptoNetworkEventHandler;
import com.bitdubai.fermat_bch_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.exceptions.CantSaveEvent;
import com.bitdubai.fermat_bch_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.exceptions.CantStartServiceException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.interfaces.DealsWithRegistry;
import com.bitdubai.fermat_bch_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.interfaces.TransactionService;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;

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

public class IncomingCryptoEventRecorderService implements DealsWithEvents, DealsWithRegistry, TransactionService {

    /**
     * DealsWithEvents Interface member variables.
     */
    private EventManager eventManager;
    private List<FermatEventListener> listenersAdded = new ArrayList<>();

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
    public void setEventManager(EventManager eventManager) {
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
        FermatEventListener fermatEventListener;
        FermatEventHandler fermatEventHandler;


        /**
         * Issue #543
         * Added the handlers for the new four events raised in the vault.
         */

        fermatEventListener = eventManager.getNewListener(EventType.INCOMING_CRYPTO_ON_CRYPTO_NETWORK);
        fermatEventHandler = new IncomingCryptoOnCryptoNetworkEventHandler();
        ((IncomingCryptoOnCryptoNetworkEventHandler) fermatEventHandler).setIncomingCryptoEventRecorderService(this);
        fermatEventListener.setEventHandler(fermatEventHandler);
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        fermatEventListener = eventManager.getNewListener(EventType.INCOMING_CRYPTO_ON_BLOCKCHAIN);
        fermatEventHandler = new IncomingCryptoOnBlockchainEventHandler();
        ((IncomingCryptoOnBlockchainEventHandler) fermatEventHandler).setIncomingCryptoEventRecorderService(this);
        fermatEventListener.setEventHandler(fermatEventHandler);
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        fermatEventListener = eventManager.getNewListener(EventType.INCOMING_CRYPTO_REVERSED_ON_CRYPTO_NETWORK);
        fermatEventHandler = new IncomingCryptoReversedOnCryptoNetworkEventHandler();
        ((IncomingCryptoReversedOnCryptoNetworkEventHandler) fermatEventHandler).setIncomingCryptoEventRecorderService(this);
        fermatEventListener.setEventHandler(fermatEventHandler);
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        fermatEventListener = eventManager.getNewListener(EventType.INCOMING_CRYPTO_REVERSED_ON_BLOCKCHAIN);
        fermatEventHandler = new IncomingCryptoReversedOnBlockchainEventHandler();
        ((IncomingCryptoReversedOnBlockchainEventHandler) fermatEventHandler).setIncomingCryptoEventRecorderService(this);
        fermatEventListener.setEventHandler(fermatEventHandler);
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        this.serviceStatus = ServiceStatus.STARTED;
        
    }

    @Override
    public void stop() {
        removeRegisteredListeners();
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    private void removeRegisteredListeners(){
        for (FermatEventListener fermatEventListener : listenersAdded) {
            eventManager.removeListener(fermatEventListener);
        }
        listenersAdded.clear();
    }

    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }

}
