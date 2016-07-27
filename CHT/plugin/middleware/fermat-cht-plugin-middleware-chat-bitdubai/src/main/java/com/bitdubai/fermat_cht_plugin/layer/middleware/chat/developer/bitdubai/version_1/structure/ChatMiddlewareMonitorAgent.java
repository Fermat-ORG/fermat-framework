package com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantListActorConnectionsException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.SubAppsPublicKeys;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_cht_api.all_definition.agent.CHTTransactionAgent;
import com.bitdubai.fermat_cht_api.all_definition.enums.ChatStatus;
import com.bitdubai.fermat_cht_api.all_definition.enums.MessageStatus;
import com.bitdubai.fermat_cht_api.all_definition.enums.TypeChat;
import com.bitdubai.fermat_cht_api.all_definition.enums.TypeMessage;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantInitializeCHTAgent;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveActionException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSendChatMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.SendStatusUpdateMessageNotificationException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cht_api.all_definition.util.ChatBroadcasterConstants;
import com.bitdubai.fermat_cht_api.layer.actor_connection.interfaces.ChatActorConnectionManager;
import com.bitdubai.fermat_cht_api.layer.actor_connection.interfaces.ChatActorConnectionSearch;
import com.bitdubai.fermat_cht_api.layer.actor_connection.utils.ChatActorConnection;
import com.bitdubai.fermat_cht_api.layer.actor_connection.utils.ChatLinkedActorIdentity;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.interfaces.ChatManager;
import com.bitdubai.fermat_cht_api.layer.middleware.enums.ActionState;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.ActionOnline;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Chat;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.GroupMember;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Message;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.MiddlewareChatManager;
import com.bitdubai.fermat_cht_api.layer.middleware.utils.ChatImpl;
import com.bitdubai.fermat_cht_api.layer.middleware.utils.MessageImpl;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.ChatMessageStatus;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.DistributionStatus;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.exceptions.CantSendChatMessageMetadataException;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces.ChatMetadata;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces.NetworkServiceChatManager;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.ChatMiddlewarePluginRoot;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.database.ChatMiddlewareDatabaseConstants;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.database.ChatMiddlewareDatabaseDao;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.database.ChatMiddlewareDatabaseFactory;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.exceptions.CantGetPendingActionListException;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.exceptions.CantGetPendingTransactionException;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.exceptions.DatabaseOperationException;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/01/16.
 * Updated by Jose Cardozo josejcb (josejcb89@gmail.com) on 16/03/16.
 */
public class ChatMiddlewareMonitorAgent implements
        CHTTransactionAgent,
        DealsWithLogger,
        DealsWithEvents,
        DealsWithPluginDatabaseSystem,
        DealsWithPluginIdentity {

    Database database;
    MonitorAgent monitorAgent;
    Thread agentThread;
    LogManager logManager;
    EventManager eventManager;
    PluginDatabaseSystem pluginDatabaseSystem;
    UUID pluginId;
    NetworkServiceChatManager chatNetworkServiceManager;
    MiddlewareChatManager chatMiddlewareManager;
    private final Broadcaster broadcaster;
    private PluginFileSystem pluginFileSystem;
    ChatMiddlewareDatabaseDao chatMiddlewareDatabaseDao;
    public final String BROADCAST_CODE = "13";
    ChatActorConnectionManager chatActorConnectionManager;
    ChatMiddlewarePluginRoot chatMiddlewarePluginRoot;
    ChatManager chatActorNetworkServiceManager;

    public ChatMiddlewareMonitorAgent(PluginDatabaseSystem pluginDatabaseSystem,
                                      LogManager logManager,
                                      ChatMiddlewarePluginRoot chatMiddlewarePluginRoot,
                                      EventManager eventManager,
                                      UUID pluginId,
                                      NetworkServiceChatManager chatNetworkServiceManager,
                                      MiddlewareChatManager chatMiddlewareManager,
                                      Broadcaster broadcaster, PluginFileSystem pluginFileSystem,
                                      ChatActorConnectionManager chatActorConnectionManager,
                                      ChatManager chatActorNetworkServiceManager) throws CantSetObjectException {
        this.eventManager = eventManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.chatMiddlewarePluginRoot = chatMiddlewarePluginRoot;
        this.pluginId = pluginId;
        this.logManager = logManager;
        this.chatNetworkServiceManager = chatNetworkServiceManager;
        this.chatMiddlewareManager = chatMiddlewareManager;
        this.broadcaster = broadcaster;
        this.pluginFileSystem = pluginFileSystem;
        this.chatActorConnectionManager = chatActorConnectionManager;
        this.chatActorNetworkServiceManager = chatActorNetworkServiceManager;
    }

    @Override
    public void start() throws CantStartAgentException {

        //Logger LOG = Logger.getGlobal();
        //LOG.info("Open contract monitor agent starting");
        monitorAgent = new MonitorAgent();

        this.monitorAgent.setPluginDatabaseSystem(this.pluginDatabaseSystem);
        this.monitorAgent.setChatMiddlewarePluginRoot(chatMiddlewarePluginRoot);

        /**
         * Init the plugin database dao
         */

        try {
            this.monitorAgent.Initialize();
        } catch (CantInitializeCHTAgent exception) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
        }

        this.agentThread = new Thread(monitorAgent);
        this.agentThread.start();

    }

    @Override
    public void stop() {
        this.agentThread.interrupt();
    }

    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    /**
     * Private class which implements runnable and is started by the Agent
     * Based on MonitorAgent created by Rodrigo Acosta
     */
    private class MonitorAgent implements DealsWithPluginDatabaseSystem, Runnable {
        PluginDatabaseSystem pluginDatabaseSystem;
        public final int SLEEP_TIME = 5000; //2000;
        public final int DISCOVER_ITERATION_LIMIT = 2;
        int discoverIteration = 0;
        int iterationNumber = 0;
        boolean threadWorking;
        ChatMiddlewarePluginRoot chatMiddlewarePluginRoot;

        public void setChatMiddlewarePluginRoot(ChatMiddlewarePluginRoot chatMiddlewarePluginRoot) {
            this.chatMiddlewarePluginRoot = chatMiddlewarePluginRoot;
        }

        @Override
        public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
            this.pluginDatabaseSystem = pluginDatabaseSystem;
        }

        @Override
        public void run() {

            threadWorking = true;
            //logManager.log(null,
            logManager.log(ChatMiddlewarePluginRoot.getLogLevelByClass(this.getClass().getName()),
                    "Chat Middleware Agent: running...", null, null);
            while (threadWorking) {
                /**
                 * Increase the iteration counter
                 */
                iterationNumber++;
                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException interruptedException) {
                    return;
                }

                /**
                 * now I will check if there are pending transactions to raise the event
                 */
                try {
                    //logManager.log(null,
                    logManager.log(ChatMiddlewarePluginRoot.getLogLevelByClass(this.getClass().getName()),
                            "Iteration number " + iterationNumber, null, null);
                    doTheMainTask();
                } catch (Exception e) {
                    chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                            e);
                }

            }

        }

        public void Initialize() throws CantInitializeCHTAgent {
            try {

                database = this.pluginDatabaseSystem.openDatabase(pluginId,
                        ChatMiddlewareDatabaseConstants.DATABASE_NAME);
            } catch (DatabaseNotFoundException databaseNotFoundException) {

                //Logger LOG = Logger.getGlobal();
                //LOG.info("Database in Open Contract monitor agent doesn't exists");
                ChatMiddlewareDatabaseFactory chatMiddlewareDatabaseFactory =
                        new ChatMiddlewareDatabaseFactory(this.pluginDatabaseSystem);
                try {
                    database = chatMiddlewareDatabaseFactory.createDatabase(pluginId,
                            ChatMiddlewareDatabaseConstants.DATABASE_NAME);
                } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                    chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                            cantCreateDatabaseException);
                    throw new CantInitializeCHTAgent(cantCreateDatabaseException,
                            "Initialize Monitor Agent - trying to create the plugin database",
                            "Please, check the cause");
                }
            } catch (CantOpenDatabaseException exception) {
                chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                        exception);
                throw new CantInitializeCHTAgent(exception,
                        "Initialize Monitor Agent - trying to open the plugin database",
                        "Please, check the cause");
            } catch (Exception exception) {
                chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                        FermatException.wrapException(exception));
                throw new CantInitializeCHTAgent(
                        FermatException.wrapException(exception),
                        "Initialize Monitor Agent - trying to open the plugin database",
                        "Unexpected exception");
            }
        }

        private void doTheMainTask() throws
                DatabaseOperationException,
                CantSendChatMessageException {

            //TODO: to implement
            try {

                if (discoverIteration == 0) {
                    sendChatBroadcasting();
                    resetWritingStatus();
                    checkOnlineStatus();
                }
                if(discoverIteration == 2){
                    resetOnlineStatus();
                }
                discoverIteration++;
                if (discoverIteration == DISCOVER_ITERATION_LIMIT) {
                    discoverIteration = 0;
                }

            } catch (Exception exception) {
                chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                        FermatException.wrapException(exception));
                throw new DatabaseOperationException(
                        DatabaseOperationException.DEFAULT_MESSAGE,
                        FermatException.wrapException(exception),
                        "Executing Monitor Agent",
                        "Unexpected exception");
            }


        }


    }

    private void sendChatBroadcasting(){
        Date date = new Date();
        chatMiddlewareDatabaseDao = new ChatMiddlewareDatabaseDao(
                pluginDatabaseSystem,
                pluginId,
                database,
                chatMiddlewarePluginRoot,
                pluginFileSystem);

        try {
            List<Chat> chats = chatMiddlewareDatabaseDao.getChats(null);
            for(Chat chat : chats){
                if (chat.getTypeChat() == TypeChat.REBROADCASTING)
                {
                    long tsTime1 = chat.getDate().getTime();
                    long tsTime2 = date.getTime();

                    if (tsTime1 >= tsTime2)
                    {
                        //if (chat.getScheduledDelivery()) {
                        //Enviar el mensaje pasando como argumento el objeto chat con todos los datos
                        //Buscar el mensaje creado de ese chat guardado cuando se creo la redifusion
                        List<Message> messages = chatMiddlewareDatabaseDao.getMessagesByChatId(chat.getChatId());
                        chat.setMessagesAsociated(messages);
                        //Buscar los miembros de ese chat en group member para asociarlo al objeto chat leido
                        List<GroupMember> groupMembers = chatMiddlewareDatabaseDao.getGroupsMemberByGroupId(chat.getChatId());
                        //Enviar el mensaje
                        for (GroupMember groupMember : groupMembers) {
                            chat.setRemoteActorPublicKey(groupMember.getActorPublicKey());
                            chat.setLocalActorType(PlatformComponentType.ACTOR_CHAT);
                            chat.setRemoteActorType(PlatformComponentType.ACTOR_CHAT);
                            System.out.println("ChatMetadata to send:\n" + constructChatMetadata(chat, messages.get(0)));
                            try {
                                chatNetworkServiceManager.sendChatMetadata(
                                        chat.getLocalActorPublicKey(),
                                        chat.getRemoteActorPublicKey(),
                                        constructChatMetadata(chat, messages.get(0))
                                );
                            }catch (IllegalArgumentException e) {
                                /**
                                 * In this case, any argument in chat or message was null or not properly set.
                                 * I'm gonna change the status to CANNOT_SEND to avoid send this message.
                                 */
                                messages.get(0).setStatus(MessageStatus.CANNOT_SEND);
                            }
                        }
                        messages.get(0).setStatus(MessageStatus.SEND);
                        chatMiddlewareDatabaseDao.saveMessage(messages.get(0));
                        //}
                    }
                }
            }
        } catch (DatabaseOperationException e) {
            e.printStackTrace();
        } catch (CantGetChatException e) {
            e.printStackTrace();
        } catch (CantGetMessageException e) {
            e.printStackTrace();
        } catch (CantSendChatMessageMetadataException e) {
            e.printStackTrace();
        } catch (CantSaveMessageException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method checks the incoming chat event and acts according to this.
     *
     * @throws CantGetPendingTransactionException
     */
    public void checkIncomingChat(ChatMetadata chatMetadata)
            throws CantGetPendingTransactionException,
            UnexpectedResultReturnedFromDatabaseException {
        try {
            chatMiddlewareDatabaseDao = new ChatMiddlewareDatabaseDao(
                    pluginDatabaseSystem,
                    pluginId,
                    database,
                    chatMiddlewarePluginRoot,
                    pluginFileSystem);

            System.out.println("12345 CHECKING INCOMING CHAT");
                    saveChat(chatMetadata);

                    saveMessage(chatMetadata);

                    //TODO TEST NOTIFICATION TO PIP REVISAR ESTO CREO QUE NO FUNCIONANDO
            broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, SubAppsPublicKeys.CHT_OPEN_CHAT.getCode(), ChatBroadcasterConstants.CHAT_NEW_INCOMING_MESSAGE);
            broadcaster.publish(BroadcasterType.UPDATE_VIEW, BROADCAST_CODE);

        } catch (DatabaseOperationException e) {
            throw new CantGetPendingTransactionException(
                    e,
                    "Checking the incoming chat pending transactions",
                    "Unexpected error in database operation"
            );
        } catch (CantSaveMessageException e) {
            throw new CantGetPendingTransactionException(
                    e,
                    "Checking the incoming chat pending transactions",
                    "Cannot save message from database"
            );
        } catch (CantGetMessageException e) {
            throw new CantGetPendingTransactionException(
                    e,
                    "Checking the incoming chat pending transactions",
                    "Cannot get the message from database"
            );

        } catch (CantGetChatException e) {
            throw new CantGetPendingTransactionException(
                    e,
                    "Checking the incoming chat pending transactions",
                    "Cannot get chat"
            );
        } catch (CantSaveChatException e) {
            throw new CantGetPendingTransactionException(
                    e,
                    "Checking the incoming chat pending transactions",
                    "Cannot save chat"
            );
        } catch (SendStatusUpdateMessageNotificationException e) {
            e.printStackTrace();
        }

    }

    /**
     * This method checks the incoming status event and acts according to this.
     *
     * @throws CantGetPendingTransactionException
     */
    public void checkIncomingStatus(ChatMetadata chatMetadata) throws
            CantGetPendingTransactionException,
            UnexpectedResultReturnedFromDatabaseException {
        try {
            chatMiddlewareDatabaseDao = new ChatMiddlewareDatabaseDao(
                    pluginDatabaseSystem,
                    pluginId,
                    database,
                    chatMiddlewarePluginRoot,
                    pluginFileSystem);

                    System.out.println("12345 CHECKING INCOMING STATUS INSIDE IF MESSAGE == "+chatMetadata.getMessage() + " MESSAGE STATUS == "+chatMetadata.getMessageStatus());

//                    if (!checkChatMetadata(chatMetadata)) return;

            //Check if metadata exists in database
//            Chat chat = chatMiddlewareDatabaseDao.getChatByRemotePublicKey(chatMetadata.getLocalActorPublicKey());
//            if(chat!=null){
//                Message message = chatMiddlewareDatabaseDao.getMessageByMessageId(chatMetadata.getMessageId());
//                if(message !=null){
                    updateMessageStatus(chatMetadata);
                    broadcaster.publish(BroadcasterType.UPDATE_VIEW, BROADCAST_CODE);
//                }
//            }

//        } catch (CantGetChatException e) {
//            throw new CantGetPendingTransactionException(
//                    e,
//                    "Checking the incoming status pending transactions",
//                    "Cannot get the chat from database"
//            );
        } catch (DatabaseOperationException e) {
            throw new CantGetPendingTransactionException(
                    e,
                    "Checking the incoming status pending transactions",
                    "Unexpected error in database operation"
            );
        } catch (CantGetMessageException e) {
            throw new CantGetPendingTransactionException(
                    e,
                    "Checking the incoming status pending transactions",
                    "Cannot get the message from database"
            );
        } catch (CantSaveMessageException e) {
            throw new CantGetPendingTransactionException(
                    e,
                    "Checking the incoming status pending transactions",
                    "Cannot update message from database"
            );
        }
    }

    public void checkOnlineStatus() throws
            CantGetPendingTransactionException,
            UnexpectedResultReturnedFromDatabaseException {
        try {
            chatMiddlewareDatabaseDao = new ChatMiddlewareDatabaseDao(
                    pluginDatabaseSystem,
                    pluginId,
                    database,
                    chatMiddlewarePluginRoot,
                    pluginFileSystem);

            List<ActionOnline> onlineActions = chatMiddlewareDatabaseDao.getOnlineActionsByActiveState();

            if(onlineActions==null || onlineActions.isEmpty()) return;

            System.out.println("12345 CHECKING ONLINE STATUS");

//            ChatSearch chatActorSearch = chatActorNetworkServiceManager.getSearch();

            for(ActionOnline actionOnline : onlineActions){
//                boolean isOnline = chatActorSearch.getResult(actionOnline.getPublicKey()) != null;
                boolean isOnline = chatActorNetworkServiceManager.isActorOnline(actionOnline.getPublicKey());
                actionOnline.setValue(isOnline);
                System.out.println("12345 is online " + isOnline);
                if(isOnline) actionOnline.setLastOn(false);
                if(!isOnline && actionOnline.getLastOn()!=true){
                    DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
                    Date today = Calendar.getInstance().getTime();
                    actionOnline.setLastConnection(df.format(today));
                    actionOnline.setLastOn(true);
                }
                chatMiddlewareDatabaseDao.saveOnlineAction(actionOnline);
            }

            broadcaster.publish(BroadcasterType.UPDATE_VIEW, BROADCAST_CODE);
        } catch (CantGetPendingActionListException e) {
            throw new CantGetPendingTransactionException(
                    e,
                    "Checking the incoming status pending transactions",
                    "Cannot update message from database"
            );
        } catch (CantSaveActionException e) {
            throw new CantGetPendingTransactionException(
                    e,
                    "Checking the incoming status pending transactions",
                    "Cannot update message from database"
            );
        }
    }

    public void resetWritingStatus(){
        try {
            chatMiddlewareDatabaseDao = new ChatMiddlewareDatabaseDao(
                    pluginDatabaseSystem,
                    pluginId,
                    database,
                    chatMiddlewarePluginRoot,
                    pluginFileSystem);

            boolean changes=false;

            //Resetear los writing state enviados
            List<UUID> chatsId = chatMiddlewareDatabaseDao.getWritingActions();
//            System.out.println("12345 Chats to reset enviados "+ chatsId.size());

            if(chatsId != null && !chatsId.isEmpty()) {
                for (UUID chatId : chatsId) {
                    chatMiddlewareDatabaseDao.saveWritingAction(chatId, ActionState.NONE);
//                    System.out.println("12345 Action writing Updated " + chatId);
                    changes = true;
                }
            }

            //Resetear los writing state recibidos
            List<Chat> chats = chatMiddlewareDatabaseDao.getChatListByWriting();
//            System.out.println("12345 Chats to reset recibidos "+ chatsId.size());

            if(chats == null && chats.isEmpty()) return;

            for(Chat chat : chats){
                chat.setIsWriting(false);
                chatMiddlewareDatabaseDao.saveChat(chat);
//                System.out.println("12345 Chat writing Updated "+chat.getChatId());
                changes = true;
            }

            if(changes)
            broadcaster.publish(BroadcasterType.UPDATE_VIEW, BROADCAST_CODE);

        }catch(DatabaseOperationException e){
            e.printStackTrace();
        } catch (CantGetChatException e) {
            e.printStackTrace();
        } catch (CantSaveChatException e) {
            e.printStackTrace();
        } catch (CantGetPendingActionListException e) {
            e.printStackTrace();
        } catch (CantSaveActionException e) {
            e.printStackTrace();
        }
    }

    public void resetOnlineStatus(){
        try {
        chatMiddlewareDatabaseDao = new ChatMiddlewareDatabaseDao(
                pluginDatabaseSystem,
                pluginId,
                database,
                chatMiddlewarePluginRoot,
                pluginFileSystem);

        boolean changes=false;


        List<ActionOnline> actionOnlines = null;
            actionOnlines = chatMiddlewareDatabaseDao.getOnlineActionsByOnline();

        if(actionOnlines == null || actionOnlines.isEmpty()) return;

        for(ActionOnline actionOnline : actionOnlines){
            actionOnline.setValue(false);
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
            Date today = Calendar.getInstance().getTime();
            actionOnline.setLastConnection(df.format(today));
            chatMiddlewareDatabaseDao.saveOnlineAction(actionOnline);
            changes = true;
        }

        if(changes)
            broadcaster.publish(BroadcasterType.UPDATE_VIEW, BROADCAST_CODE);

        } catch (CantGetPendingActionListException e) {
            e.printStackTrace();
        } catch (CantSaveActionException e) {
            e.printStackTrace();
        }
    }

//    public void checkOutgoingWritingStatus(){
//        try {
//            chatMiddlewareDatabaseDao = new ChatMiddlewareDatabaseDao(
//                    pluginDatabaseSystem,
//                    pluginId,
//                    database,
//                    errorManager,
//                    pluginFileSystem);
//
//            List<UUID> chatIds = chatMiddlewareDatabaseDao.getWritingActions();
//
//            if(chatIds == null || chatIds.isEmpty()) return;
//
//            for(UUID chatId : chatIds){
//                Chat chat = chatMiddlewareDatabaseDao.getChatByChatId(chatId);
//                chat.setIsWriting(false);
//                chatMiddlewareDatabaseDao.saveChat(chat);
//            }
//
////            broadcaster.publish(BroadcasterType.UPDATE_VIEW, BROADCAST_CODE);
//        }catch(DatabaseOperationException e){
//            e.printStackTrace();
//        } catch (CantGetChatException e) {
//            e.printStackTrace();
//        } catch (CantSaveChatException e) {
//            e.printStackTrace();
//        } catch (CantGetPendingActionListException e) {
//            e.printStackTrace();
//        }
//    }

    public void checkIncomingWritingStatus(UUID chatId) throws
            CantGetPendingTransactionException,
            UnexpectedResultReturnedFromDatabaseException {
        try {
            chatMiddlewareDatabaseDao = new ChatMiddlewareDatabaseDao(
                    pluginDatabaseSystem,
                    pluginId,
                    database,
                    chatMiddlewarePluginRoot,
                    pluginFileSystem);

            Chat chat = chatMiddlewareDatabaseDao.getChatByChatId(chatId);
            if(chat != null) {
                System.out.println("12345 Saving is writing chat "+chat.getChatId());
                chat.setIsWriting(true);
                chatMiddlewareDatabaseDao.saveChat(chat);
                System.out.println("12345 chat saved");
            }

            broadcaster.publish(BroadcasterType.UPDATE_VIEW, BROADCAST_CODE);

        } catch (CantSaveChatException e) {
            throw new CantGetPendingTransactionException(
                    e,
                    "Checking the incoming status pending transactions",
                    "Cannot update message from database"
            );
        } catch (DatabaseOperationException e) {
            throw new CantGetPendingTransactionException(
                    e,
                    "Checking the incoming status pending transactions",
                    "Cannot update message from database"
            );
        } catch (CantGetChatException e) {
            throw new CantGetPendingTransactionException(
                    e,
                    "Checking the incoming status pending transactions",
                    "Cannot update message from database"
            );
        }
    }

//    public void checkIncomingOnlineStatus(UUID chatId) throws
//            CantGetPendingTransactionException,
//            UnexpectedResultReturnedFromDatabaseException {
//        try {
//            chatMiddlewareDatabaseDao = new ChatMiddlewareDatabaseDao(
//                    pluginDatabaseSystem,
//                    pluginId,
//                    database,
//                    errorManager,
//                    pluginFileSystem);
//
//            Chat chat = chatMiddlewareDatabaseDao.getChatByChatId(chatId);
//            chat.setIsOnline(true);
//            chatMiddlewareDatabaseDao.saveChat(chat);
//
//            broadcaster.publish(BroadcasterType.UPDATE_VIEW, BROADCAST_CODE);
//
//        } catch (CantSaveChatException e) {
//            throw new CantGetPendingTransactionException(
//                    e,
//                    "Checking the incoming status pending transactions",
//                    "Cannot update message from database"
//            );
//        } catch (DatabaseOperationException e) {
//            throw new CantGetPendingTransactionException(
//                    e,
//                    "Checking the incoming status pending transactions",
//                    "Cannot update message from database"
//            );
//        } catch (CantGetChatException e) {
//            throw new CantGetPendingTransactionException(
//                    e,
//                    "Checking the incoming status pending transactions",
//                    "Cannot update message from database"
//            );
//        }
//    }

    /**
     * This method returns true if the chat and the message exists in database.
     * This throws an exception instead return false if any element does not exists in database
     * because this is not should happen.
     *
     * @param chatMetadata
     * @return
     * @throws CantGetChatException
     * @throws CantGetPendingTransactionException
     */
    private boolean checkChatMetadata(ChatMetadata chatMetadata) throws
            CantGetChatException,
            CantGetPendingTransactionException {
        UUID chatId = chatMetadata.getChatId();
        UUID messageId;
        if (chatMiddlewareDatabaseDao.chatIdExists(
                chatId)) {
            messageId = chatMetadata.getMessageId();
            if (chatMiddlewareDatabaseDao.messageIdExists(messageId)) {
                return true;
            } else {
                //TODO: I need to study how can I handle this case.
                return false;
//                    throw new CantGetPendingTransactionException(
//                            "The Message Id "+messageId+" does not exists in database");
            }
        } else {
            //This is a case that in this version I cannot handle
            throw new CantGetPendingTransactionException(
                    "The Chat Id " + chatId + " does not exists in database");
        }
    }

    /**
     * This method saves a new message in database
     *
     * @param chatMetadata
     * @throws DatabaseOperationException
     * @throws CantSaveMessageException
     * @throws CantGetMessageException
     */
    private void saveMessage(
            ChatMetadata chatMetadata) throws
            DatabaseOperationException,
            CantSaveMessageException,
            CantGetMessageException, SendStatusUpdateMessageNotificationException {
        UUID messageId = chatMetadata.getMessageId();
        System.out.println("12345 SAVING MESSAGE");
//        Message messageRecorded = chatMiddlewareDatabaseDao.getMessageByMessageId(messageId);
                Message messageRecorded = null;
//        if (messageRecorded == null) {
            /**
             * In this case, the message is not created in database, so, is an incoming message,
             * I need to create a new message
             */
            messageRecorded = getMessageFromChatMetadata(
                    chatMetadata);
            if (messageRecorded == null) return;
//        }

        messageRecorded.setStatus(MessageStatus.RECEIVE);
        chatMiddlewareDatabaseDao.saveMessage(messageRecorded);
        chatMiddlewareManager.sendDeliveredMessageNotification(messageRecorded);
    }

    /**
     * This method saves the new chat in database
     *
     * @param chatMetadata
     * @throws DatabaseOperationException
     */
    private void saveChat(ChatMetadata chatMetadata) throws DatabaseOperationException, CantGetChatException, CantSaveChatException {
        String localPublicKey;
        PlatformComponentType localType;
        String remotePublicKey;
        PlatformComponentType remoteType;
        System.out.println("12345 SAVING CHAT");
        Chat chat = chatMiddlewareDatabaseDao.getChatByChatId(chatMetadata.getChatId());
        if(chat==null)
            chat = chatMiddlewareDatabaseDao.getChatByRemotePublicKey(chatMetadata.getLocalActorPublicKey());

        // change to put in the remote device in the correct place of table chat
        if (chat == null) {
            chat = getChatFromChatMetadata(chatMetadata);
        } else {
            localPublicKey = chatMetadata.getRemoteActorPublicKey();
            if (!localPublicKey.equals(chat.getRemoteActorPublicKey())) {
                chat.setLocalActorPublicKey(localPublicKey);
            }
        }
        chat.setLastMessageDate(new Timestamp(System.currentTimeMillis()));//updating date of last message arrived in chat

        chat.setStatus(ChatStatus.VISSIBLE);

        chatMiddlewareDatabaseDao.saveChat(chat);
    }


    /**
     * This method creates a new Message from incoming metadata
     *
     * @param chatMetadata
     * @return
     */
    private Message getMessageFromChatMetadata(ChatMetadata chatMetadata)
            throws
            CantGetMessageException {
        if (chatMetadata == null) {
            throw new CantGetMessageException("The chat metadata from network service is null");
        }
        try {
            Chat chatFromDatabase = chatMiddlewareDatabaseDao.getChatByRemotePublicKey(chatMetadata.getLocalActorPublicKey());

            ChatLinkedActorIdentity chatLinkedActorIdentity = new ChatLinkedActorIdentity(
                chatFromDatabase.getLocalActorPublicKey(),
                Actors.CHAT
                );
            final ChatActorConnectionSearch search = chatActorConnectionManager.getSearch(chatLinkedActorIdentity);
            List<ChatActorConnection> chatActorConnections = search.getResult();
            ChatActorConnection actorConnection = null;

            for(ChatActorConnection chatActorConnection : chatActorConnections){
                if(chatActorConnection.getPublicKey().equals(chatFromDatabase.getRemoteActorPublicKey())) {
                    actorConnection = chatActorConnection;
                    break;
                }
            }
            if(actorConnection == null){
                return null;
            }

            Message message = new MessageImpl(
                    chatFromDatabase.getChatId(),
                    chatMetadata,
                    MessageStatus.CREATED,
                    TypeMessage.INCOMMING,
                    actorConnection.getConnectionId()//TODO:Revisar esto si afecta el envio ya que el public es un string//UUID.fromString(actorConnection.getPublicKey())
            );
            return message;
        } catch (DatabaseOperationException e) {
            throw new CantGetMessageException(e,
                    "Getting message from ChatMetadata",
                    "Unexpected exception in database");
        } catch (CantGetChatException e) {
            throw new CantGetMessageException(e,
                    "Getting message from ChatMetadata",
                    "Cannot get the chat");
        } catch (CantListActorConnectionsException e) {
            throw new CantGetMessageException(e,
                    "Getting message from ChatMetadata",
                    "Cannot get the ActorConnection");
        }
    }


    /**
     * THis Method creates a new Chat from incoming Metadata
     *
     * @param chatMetadata
     * @return
     */
    private Chat getChatFromChatMetadata(ChatMetadata chatMetadata) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        Date parsedDate = null;
        try {
            parsedDate = dateFormat.parse(chatMetadata.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());

        return new ChatImpl(
                chatMetadata.getChatId(),
                chatMetadata.getObjectId(),
                chatMetadata.getRemoteActorType(),
                chatMetadata.getRemoteActorPublicKey(),
                chatMetadata.getLocalActorType(),
                chatMetadata.getLocalActorPublicKey(),
                chatMetadata.getChatName(),
                ChatStatus.VISSIBLE,
                timestamp,
                timestamp,
                TypeChat.INDIVIDUAL, //TODO:Revisar
                false //TODO:Revisar
        );
    }

    /**
     * This method updates a message record in database.
     *
     * @param chatMetadata,
     * @throws DatabaseOperationException
     * @throws CantSaveMessageException
     * @throws CantGetMessageException
     */
    private void updateMessageStatus(
            ChatMetadata chatMetadata) throws
            DatabaseOperationException,
            CantSaveMessageException,
            CantGetMessageException {
        System.out.println("12345 UPDATING MESSAGE STATUS");
        UUID messageId = chatMetadata.getMessageId();
        Message messageRecorded = chatMiddlewareDatabaseDao.getMessageByMessageId(messageId);
        if (messageRecorded == null) {
            /**
             * In this case, the message is not created in database, so, is an incoming message,
             * I need to create a new message
             */
            messageRecorded = getMessageFromChatMetadata(
                    chatMetadata);
            if (messageRecorded == null) return;
        }
        if (messageRecorded.getStatus().equals(MessageStatus.READ))
            return;

        messageRecorded.setStatus(chatMetadata.getMessageStatus());
        chatMiddlewareDatabaseDao.saveMessage(messageRecorded);
        System.out.println("12345 MESSAGE STATUS UPDATED");
    }

    /**
     * This method return a ChatMetadata from a Chat and Message objects.
     *
     * @param chat
     * @param message
     * @return
     */
    private ChatMetadata constructChatMetadata(
            Chat chat,
            Message message) {
        ChatMetadata chatMetadata;
        Timestamp timestamp = new Timestamp(message.getMessageDate().getTime());
        String timeStamp = new SimpleDateFormat("MM/dd/yyyy HH:mm").format(timestamp);
            chatMetadata = new ChatMetadataRecord(
                    chat.getChatId(),
                    chat.getObjectId(),
                    chat.getLocalActorType(),
                    chat.getLocalActorPublicKey(),
                    chat.getRemoteActorType(),
                    chat.getRemoteActorPublicKey(),
                    chat.getChatName(),
                    ChatMessageStatus.READ_CHAT,
                    MessageStatus.SEND,
                    timeStamp,
                    message.getMessageId(),
                    message.getMessage(),
                    DistributionStatus.OUTGOING_MSG,
                    chat.getTypeChat(),
                    chat.getGroupMembersAssociated()
            );
        return chatMetadata;
    }
}

