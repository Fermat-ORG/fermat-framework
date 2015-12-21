package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.close_contract.developer.bitdubai.version_1.event_handler;

import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_cbp_api.all_definition.events.CBPService;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSaveEventException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantStartServiceException;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.events.IncomingConfirmBusinessTransactionContract;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.events.IncomingConfirmBusinessTransactionResponse;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.close_contract.developer.bitdubai.version_1.database.CloseContractBusinessTransactionDao;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 07/12/15.
 */
public class CloseContractRecorderService implements CBPService {
    /**
     * DealsWithEvents Interface member variables.
     */
    private EventManager eventManager;
    private List<FermatEventListener> listenersAdded = new ArrayList<>();
    CloseContractBusinessTransactionDao closeContractBusinessTransactionDao;
    /**
     * TransactionService Interface member variables.
     */
    private ServiceStatus serviceStatus = ServiceStatus.CREATED;

    public CloseContractRecorderService(
            CloseContractBusinessTransactionDao closeContractBusinessTransactionDao,
            EventManager eventManager) throws CantStartServiceException {
        try {
            setDatabaseDao(closeContractBusinessTransactionDao);
            setEventManager(eventManager);
        } catch (CantSetObjectException exception) {
            throw new CantStartServiceException(exception,
                    "Cannot set the close contract database handler",
                    "The database handler is null");
        }
    }

    private void setDatabaseDao(CloseContractBusinessTransactionDao closeContractBusinessTransactionDao)
            throws CantSetObjectException {
        if(closeContractBusinessTransactionDao==null){
            throw new CantSetObjectException("The CloseContractBusinessTransactionDao is null");
        }
        this.closeContractBusinessTransactionDao =closeContractBusinessTransactionDao;
    }

    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    /*public void incomingBusinessTransactionContractHashEventHandler(IncomingBusinessTransactionContractHash event) throws CantSaveEventException {
        //Logger LOG = Logger.getGlobal();
        //LOG.info("EVENT TEST, I GOT AN EVENT:\n"+event);
        this.closeContractBusinessTransactionDao.saveNewEvent(event.getEventType().getCode(), event.getSource().getCode());
        //LOG.info("CHECK THE DATABASE");
    }*/

    public void incomingConfirmBusinessTransactionContractEventHandler(IncomingConfirmBusinessTransactionContract event) throws CantSaveEventException {
        //Logger LOG = Logger.getGlobal();
        //LOG.info("EVENT TEST, I GOT AN EVENT:\n"+event);
        this.closeContractBusinessTransactionDao.saveNewEvent(event.getEventType().getCode(), event.getSource().getCode());
        //LOG.info("CHECK THE DATABASE");
    }

    public void incomingConfirmBusinessTransactionResponse(IncomingConfirmBusinessTransactionResponse event) throws CantSaveEventException {
        //Logger LOG = Logger.getGlobal();
        //LOG.info("EVENT TEST, I GOT AN EVENT:\n"+event);
        this.closeContractBusinessTransactionDao.saveNewEvent(event.getEventType().getCode(), event.getSource().getCode());
        //LOG.info("CHECK THE DATABASE");
    }

    @Override
    public void start() throws CantStartServiceException {
        try {
            /**
             * I will initialize the handling of com.bitdubai.platform events.
             */
            FermatEventListener fermatEventListener;
            FermatEventHandler fermatEventHandler;

            fermatEventListener = eventManager.getNewListener(EventType.INCOMING_CONFIRM_BUSINESS_TRANSACTION_CONTRACT);
            fermatEventHandler = new IncomingConfirmBusinessTransactionContractEventHandler();
            ((IncomingConfirmBusinessTransactionContractEventHandler) fermatEventHandler).setCloseContractRecorderService(this);
            fermatEventListener.setEventHandler(fermatEventHandler);
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

            fermatEventListener = eventManager.getNewListener(EventType.INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE);
            fermatEventHandler = new IncomingConfirmBusinessTransactionResponseEventHandler();
            ((IncomingConfirmBusinessTransactionResponseEventHandler) fermatEventHandler).setCloseContractRecorderService(this);
            fermatEventListener.setEventHandler(fermatEventHandler);
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

            this.serviceStatus = ServiceStatus.STARTED;
        } catch (CantSetObjectException exception){
            throw new CantStartServiceException(
                    exception,
                    "Starting the CloseContractRecorderService",
                    "The CloseContractRecorderService is probably null");
        }

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
