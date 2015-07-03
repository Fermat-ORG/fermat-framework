package com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;

import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.*;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.events.IncomingCryptoTransactionsWaitingTransferenceExtraUserEvent;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.event_handlers.IncomingCryptoTransactionsWaitingTransferenceExtraUserEventHandler;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.exceptions.CantSaveEventException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.exceptions.CantStartServiceException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.interfaces.DealsWithRegistry;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.interfaces.TransactionService;

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

public class IncomingExtraUserEventRecorderService implements DealsWithEvents, DealsWithRegistry, TransactionService {

    /**
     * DealsWithEvents Interface member variables.
     */
    private EventManager eventManager;
    private List<EventListener> listenersAdded = new ArrayList<>();

    /*
     * DealsWithRegistry Interface member variables.
     */
    private IncomingExtraUserRegistry registry;



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
    public void setRegistry(IncomingExtraUserRegistry registry) {
        this.registry = registry;
    }


    /**
     * IncomingCryptoEventRecorder Interface implementation.
     */
    public void incomingCryptoWaitingTransference(IncomingCryptoTransactionsWaitingTransferenceExtraUserEvent event) throws CantSaveEventException {
        this.registry.saveNewEvent(event.getEventType().getCode(), event.getSource().getCode());
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

        eventListener = eventManager.getNewListener(EventType.INCOMING_CRYPTO_TRANSACTIONS_WAITING_TRANSFERENCE_EXTRA_USER);
        eventHandler = new IncomingCryptoTransactionsWaitingTransferenceExtraUserEventHandler();
        ((IncomingCryptoTransactionsWaitingTransferenceExtraUserEventHandler) eventHandler).setIncomingExtraUserEventRecorderService(this);
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
