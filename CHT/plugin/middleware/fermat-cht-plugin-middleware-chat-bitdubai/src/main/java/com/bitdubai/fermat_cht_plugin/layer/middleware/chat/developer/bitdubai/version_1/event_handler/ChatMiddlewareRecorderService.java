package com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.event_handler;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_cht_api.all_definition.events.CHTService;
import com.bitdubai.fermat_cht_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveEventException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantStartServiceException;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.events.IncomingChat;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.events.IncomingNewChatStatusUpdate;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.events.IncomingNewOnlineStatusUpdate;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.events.IncomingNewWritingStatusUpdate;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.events.OutgoingChat;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.ChatMiddlewarePluginRoot;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.database.ChatMiddlewareDatabaseDao;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.structure.ChatMiddlewareMonitorAgent;
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
    private ChatMiddlewareDatabaseDao chatMiddlewareDatabaseDao;
    private ChatMiddlewarePluginRoot chatMiddlewarePluginRoot;
    private ChatMiddlewareMonitorAgent chatMiddlewareMonitorAgent;
    /**
     * TransactionService Interface member variables.
     */
    private ServiceStatus serviceStatus = ServiceStatus.CREATED;

    public ChatMiddlewareRecorderService(
            ChatMiddlewareDatabaseDao chatMiddlewareDatabaseDao,
            EventManager eventManager,
            ChatMiddlewarePluginRoot chatMiddlewarePluginRoot,
            ChatMiddlewareMonitorAgent chatMiddlewareMonitorAgent) throws CantStartServiceException {
        try {
            this.chatMiddlewareMonitorAgent = chatMiddlewareMonitorAgent;
            setDatabaseDao(chatMiddlewareDatabaseDao);
            setEventManager(eventManager);
            this.chatMiddlewarePluginRoot=chatMiddlewarePluginRoot;
        } catch (CantSetObjectException exception) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(exception));
            throw new CantStartServiceException(exception,
                    "Cannot set the customer ack offline merchandise database handler",
                    "The database handler is null");
        } catch(Exception exception){
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(exception));
            throw new CantStartServiceException(exception,
                    "Unexpected error",
                    "Unexpected exception");
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
        try{
            this.chatMiddlewareDatabaseDao.saveNewEvent(
                    event.getEventType().getCode(),
                    event.getSource().getCode(),
                    event.getChatId());
        } catch (Exception exception) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(exception));
            throw new CantSaveEventException(
                    exception,
                    "Saving OutgoingChat event",
                    "Unexpected Exception");
        }
        //LOG.info("CHECK THE DATABASE");
    }

    public void incomingChatEventHandler(IncomingChat event) throws CantSaveEventException {
        //Logger LOG = Logger.getGlobal();
        //LOG.info("EVENT TEST, I GOT AN EVENT:\n"+event);
        try{
//            this.chatMiddlewareDatabaseDao.saveNewEvent(
//                    event.getEventType().getCode(),
//                    event.getSource().getCode(),
//                    event.getChatId());
            chatMiddlewareMonitorAgent.checkIncomingChat(event.getChatMetadata());
        } catch (Exception exception) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(exception));
            throw new CantSaveEventException(
                    exception,
                    "Saving OutgoingChat event",
                    "Unexpected Exception");
        }

        //LOG.info("CHECK THE DATABASE");
    }

    public void IncomingNewChatStatusUpdateEventHandler(IncomingNewChatStatusUpdate event) throws CantSaveEventException {
        //Logger LOG = Logger.getGlobal();
        //LOG.info("EVENT TEST, I GOT AN EVENT:\n"+event);
        try{
//            this.chatMiddlewareDatabaseDao.saveNewEvent(
//                    event.getEventType().getCode(),
//                    event.getSource().getCode(),
//                    event.getChatId());
            chatMiddlewareMonitorAgent.checkIncomingStatus(event.getChatMetadata());
        } catch (Exception exception) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(exception));
            throw new CantSaveEventException(
                    exception,
                    "Saving OutgoingChat event",
                    "Unexpected Exception");
        }

        //LOG.info("CHECK THE DATABASE");
    }

    public void IncomingNewWritingStatusUpdateEventHandler(IncomingNewWritingStatusUpdate event) throws CantSaveEventException {
        try{
            chatMiddlewareMonitorAgent.checkIncomingWritingStatus(event.getChatId());
        } catch (Exception exception) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(exception));
            throw new CantSaveEventException(
                    exception,
                    "Saving OutgoingChat event",
                    "Unexpected Exception");
        }
    }

    public void IncomingNewOnlineStatusUpdateEventHandler(IncomingNewOnlineStatusUpdate event) throws CantSaveEventException {
        try{
            chatMiddlewareMonitorAgent.checkIncomingOnlineStatus(event.getChatId());
        } catch (Exception exception) {
//            errorManager.reportUnexpectedPluginException(
//                    Plugins.CHAT_MIDDLEWARE,
//                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
//                    FermatException.wrapException(exception));
            throw new CantSaveEventException(
                    exception,
                    "Saving OutgoingChat event",
                    "Unexpected Exception");
        }
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

            fermatEventListener = eventManager.getNewListener(EventType.INCOMING_STATUS);
            fermatEventHandler = new IncomingNewChatStatusUpdateEventHandler();
            ((IncomingNewChatStatusUpdateEventHandler) fermatEventHandler).setChatMiddlewareRecorderService(this);
            fermatEventListener.setEventHandler(fermatEventHandler);
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

            fermatEventListener = eventManager.getNewListener(EventType.INCOMING_WRITING_STATUS);
            fermatEventHandler = new IncomingNewWritingStatusUpdateEventHandler();
            ((IncomingNewWritingStatusUpdateEventHandler) fermatEventHandler).setChatMiddlewareRecorderService(this);
            fermatEventListener.setEventHandler(fermatEventHandler);
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

            fermatEventListener = eventManager.getNewListener(EventType.INCOMING_ONLINE_STATUS);
            fermatEventHandler = new IncomingNewOnlineStatusUpdateEventHandler();
            ((IncomingNewOnlineStatusUpdateEventHandler) fermatEventHandler).setChatMiddlewareRecorderService(this);
            fermatEventListener.setEventHandler(fermatEventHandler);
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

            this.serviceStatus = ServiceStatus.STARTED;
        } catch (CantSetObjectException exception){
            throw new CantStartServiceException(
                    exception,
                    "Starting the ChatMiddlewareRecorderService",
                    "The ChatMiddlewareRecorderService is probably null");
        } catch (Exception exception){
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(exception));
            throw new CantStartServiceException(
                    exception,
                    "Starting the ChatMiddlewareRecorderService",
                    "Unexpected Exception");
        }

    }

    @Override
    public void stop() {
        try{
            removeRegisteredListeners();
            this.serviceStatus = ServiceStatus.STOPPED;
        } catch (Exception exception){
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(exception));
        }
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

