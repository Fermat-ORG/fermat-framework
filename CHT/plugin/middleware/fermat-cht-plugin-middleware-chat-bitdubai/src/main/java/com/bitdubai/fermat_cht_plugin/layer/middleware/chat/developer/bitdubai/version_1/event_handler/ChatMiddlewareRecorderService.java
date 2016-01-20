package com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.event_handler;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_cht_api.all_definition.events.CHTService;
import com.bitdubai.fermat_cht_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveEventException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantStartServiceException;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.events.IncomingChat;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.events.OutgoingChat;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.database.ChatMiddlewareDatabaseDao;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.IncomingMoneyNotificationEvent;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/01/15.
 */
public class ChatMiddlewareRecorderService implements CHTService {
    /**
     * DealsWithEvents Interface member variables.
     */
    private EventManager eventManager;
    private List<FermatEventListener> listenersAdded = new ArrayList<>();
    ChatMiddlewareDatabaseDao chatMiddlewareDatabaseDao;
    /**
     * TransactionService Interface member variables.
     */
    private ServiceStatus serviceStatus = ServiceStatus.CREATED;

    public ChatMiddlewareRecorderService(
            ChatMiddlewareDatabaseDao chatMiddlewareDatabaseDao,
            EventManager eventManager) throws CantStartServiceException {
        try {
            setDatabaseDao(chatMiddlewareDatabaseDao);
            setEventManager(eventManager);
        } catch (CantSetObjectException exception) {
            throw new CantStartServiceException(exception,
                    "Cannot set the customer ack offline merchandise database handler",
                    "The database handler is null");
        }
    }

    private void setDatabaseDao(ChatMiddlewareDatabaseDao chatMiddlewareDatabaseDao)
            throws CantSetObjectException {
        if(chatMiddlewareDatabaseDao==null){
            throw new CantSetObjectException("The ChatMiddlewareDatabaseDao is null");
        }
        this.chatMiddlewareDatabaseDao =chatMiddlewareDatabaseDao;
    }

    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    public void outgoingChatEventHandler(OutgoingChat event) throws CantSaveEventException {
        //Logger LOG = Logger.getGlobal();
        //LOG.info("EVENT TEST, I GOT AN EVENT:\n"+event);
        this.chatMiddlewareDatabaseDao.saveNewEvent(
                event.getEventType().getCode(),
                event.getSource().getCode(),
                event.getChatId());
        //LOG.info("CHECK THE DATABASE");
    }

    public void incomingChatEventHandler(IncomingChat event) throws CantSaveEventException {
        //Logger LOG = Logger.getGlobal();
        //LOG.info("EVENT TEST, I GOT AN EVENT:\n"+event);
        this.chatMiddlewareDatabaseDao.saveNewEvent(
                event.getEventType().getCode(),
                event.getSource().getCode(),
                event.getChatId());
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

            fermatEventListener = eventManager.getNewListener(EventType.INCOMING_CHAT);
            fermatEventHandler = new IncomingChatEventHandler();
            ((IncomingChatEventHandler) fermatEventHandler).setChatMiddlewareRecorderService(this);
            fermatEventListener.setEventHandler(fermatEventHandler);
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

            fermatEventListener = eventManager.getNewListener(EventType.OUTGOING_CHAT);
            fermatEventHandler = new OutgoingChatEventHandler();
            ((OutgoingChatEventHandler) fermatEventHandler).setChatMiddlewareRecorderService(this);
            fermatEventListener.setEventHandler(fermatEventHandler);
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

            this.serviceStatus = ServiceStatus.STARTED;
        } catch (CantSetObjectException exception){
            throw new CantStartServiceException(
                    exception,
                    "Starting the ChatMiddlewareRecorderService",
                    "The ChatMiddlewareRecorderService is probably null");
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

