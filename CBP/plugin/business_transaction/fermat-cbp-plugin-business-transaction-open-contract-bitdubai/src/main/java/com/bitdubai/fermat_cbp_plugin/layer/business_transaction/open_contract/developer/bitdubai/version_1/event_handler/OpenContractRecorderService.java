package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.event_handler;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_cbp_api.all_definition.events.CBPService;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSaveEventException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantStartServiceException;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.events.IncomingBusinessTransactionContractHash;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.events.IncomingConfirmBusinessTransactionContract;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.events.IncomingConfirmBusinessTransactionResponse;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.OpenContractPluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.database.OpenContractBusinessTransactionDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 30/11/15.
 */
public class OpenContractRecorderService implements CBPService {
    /**
     * DealsWithEvents Interface member variables.
     */
    private EventManager eventManager;
    private List<FermatEventListener> listenersAdded = new ArrayList<>();
    OpenContractBusinessTransactionDao openContractBusinessTransactionDao;
    private OpenContractPluginRoot pluginRoot;

    /**
     * TransactionService Interface member variables.
     */
    private ServiceStatus serviceStatus = ServiceStatus.CREATED;

    public OpenContractRecorderService(
            OpenContractBusinessTransactionDao openContractBusinessTransactionDao,
            EventManager eventManager, OpenContractPluginRoot pluginRoot) throws CantStartServiceException {
        try {
            this.pluginRoot = pluginRoot;
            setDatabaseDao(openContractBusinessTransactionDao);
            setEventManager(eventManager);
        } catch (CantSetObjectException exception) {
            this.pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantStartServiceException(exception,
                    "Cannot set the open contract database handler",
                    "The database handler is null");
        } catch (Exception e) {
            this.pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new CantStartServiceException(CantStartServiceException.DEFAULT_MESSAGE, FermatException.wrapException(e),
                    "Cannot set the customer online payment database handler",
                    "Unexpected error");
        }
    }

    private void setDatabaseDao(OpenContractBusinessTransactionDao openContractBusinessTransactionDao)
            throws CantSetObjectException {
        if (openContractBusinessTransactionDao == null) {
            throw new CantSetObjectException("The OpenContractBusinessTransactionDao is null");
        }
        this.openContractBusinessTransactionDao = openContractBusinessTransactionDao;
    }

    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    public void incomingBusinessTransactionContractHashEventHandler(IncomingBusinessTransactionContractHash event) throws CantSaveEventException {
        try {
            //Logger LOG = Logger.getGlobal();
            //LOG.info("EVENT TEST, I GOT AN EVENT:\n"+event);
            if (event.getRemoteBusinessTransaction().getCode().equals(Plugins.OPEN_CONTRACT.getCode())) {
                this.openContractBusinessTransactionDao.saveNewEvent(event.getEventType().getCode(), event.getSource().getCode());
                //LOG.info("CHECK THE DATABASE");
            }
        } catch (CantSaveEventException exception) {
            this.pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantSaveEventException(CantSaveEventException.DEFAULT_MESSAGE, exception, "incoming new Contract Status Update Event Handler CantSaveException", "");
        } catch (Exception exception) {
            this.pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantSaveEventException(CantSaveEventException.DEFAULT_MESSAGE, exception,
                    "Unexpected error",
                    "Check the cause");
        }
    }

    public void incomingConfirmBusinessTransactionContractEventHandler(IncomingConfirmBusinessTransactionContract event) throws CantSaveEventException {
        try {
            //Logger LOG = Logger.getGlobal();
            //LOG.info("EVENT TEST, I GOT AN EVENT:\n"+event);
            if (event.getRemoteBusinessTransaction().getCode().equals(Plugins.OPEN_CONTRACT.getCode())) {
                this.openContractBusinessTransactionDao.saveNewEvent(event.getEventType().getCode(), event.getSource().getCode());
                //LOG.info("CHECK THE DATABASE");
            }
        } catch (CantSaveEventException exception) {
            this.pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantSaveEventException(CantSaveEventException.DEFAULT_MESSAGE, exception, "incoming Confirm Business Transaction Event Handler Response CantSaveException", "");
        } catch (Exception exception) {
            this.pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantSaveEventException(CantSaveEventException.DEFAULT_MESSAGE, exception,
                    "Unexpected error",
                    "Check the cause");
        }
    }

    public void incomingConfirmBusinessTransactionResponse(IncomingConfirmBusinessTransactionResponse event) throws CantSaveEventException {
        try {
            //Logger LOG = Logger.getGlobal();
            //LOG.info("EVENT TEST, I GOT AN EVENT:\n"+event);
            if (event.getRemoteBusinessTransaction().getCode().equals(Plugins.OPEN_CONTRACT.getCode())) {
                this.openContractBusinessTransactionDao.saveNewEvent(event.getEventType().getCode(), event.getSource().getCode());
                //LOG.info("CHECK THE DATABASE");
            }
        } catch (CantSaveEventException exception) {
            this.pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantSaveEventException(CantSaveEventException.DEFAULT_MESSAGE, exception, "incoming Confirm Business Transaction Response CantSaveException", "");
        } catch (Exception exception) {
            this.pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantSaveEventException(CantSaveEventException.DEFAULT_MESSAGE, exception,
                    "Unexpected error",
                    "Check the cause");
        }
    }

    @Override
    public void start() throws CantStartServiceException {
//TODO: finish this
        try {
            /**
             * I will initialize the handling of com.bitdubai.platform events.
             */
            FermatEventListener fermatEventListener;
            FermatEventHandler fermatEventHandler;
            fermatEventListener = eventManager.getNewListener(EventType.INCOMING_BUSINESS_TRANSACTION_CONTRACT_HASH);
            fermatEventHandler = new IncomingBusinessTransactionContractHashEventHandler();
            ((IncomingBusinessTransactionContractHashEventHandler) fermatEventHandler).setOpenContractRecorderService(this);
            fermatEventListener.setEventHandler(fermatEventHandler);
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

            fermatEventListener = eventManager.getNewListener(EventType.INCOMING_CONFIRM_BUSINESS_TRANSACTION_CONTRACT);
            fermatEventHandler = new IncomingConfirmBusinessTransactionContractEventHandler();
            ((IncomingConfirmBusinessTransactionContractEventHandler) fermatEventHandler).setOpenContractRecorderService(this);
            fermatEventListener.setEventHandler(fermatEventHandler);
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

            fermatEventListener = eventManager.getNewListener(EventType.INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE);
            fermatEventHandler = new IncomingConfirmBusinessTransactionResponseEventHandler();
            ((IncomingConfirmBusinessTransactionResponseEventHandler) fermatEventHandler).setOpenContractRecorderService(this);
            fermatEventListener.setEventHandler(fermatEventHandler);
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

            this.serviceStatus = ServiceStatus.STARTED;
        } catch (CantSetObjectException exception) {
            this.pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantStartServiceException(
                    exception,
                    "Starting the OpenContractRecorderService",
                    "The OpenContractRecorderService is probably null");
        } catch (Exception exception) {
            this.pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantStartServiceException(CantStartServiceException.DEFAULT_MESSAGE, exception,
                    "Starting the CustomerOnlinePaymentRecorderService",
                    "Unexpected error");
        }

    }

    @Override
    public void stop() {
        try {
            removeRegisteredListeners();
            this.serviceStatus = ServiceStatus.STOPPED;
        } catch (Exception exception) {
            this.pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(exception));
        }
    }

    private void removeRegisteredListeners() {
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
