package com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.AbstractAgent;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.ActorConnectionNotFoundException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantGetActorConnectionException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.SubAppsPublicKeys;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Owner;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.FermatBundle;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
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
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantListChatException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.interfaces.ChatManager;
import com.bitdubai.fermat_cht_api.layer.middleware.enums.ActionState;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.ActionOnline;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Chat;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Message;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.MiddlewareChatManager;
import com.bitdubai.fermat_cht_api.layer.middleware.utils.ChatImpl;
import com.bitdubai.fermat_cht_api.layer.middleware.utils.MessageImpl;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces.MessageMetadata;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces.NetworkServiceChatManager;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.ChatMiddlewarePluginRoot;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.database.ChatMiddlewareDatabaseConstants;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.database.ChatMiddlewareDatabaseDao;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.database.ChatMiddlewareDatabaseFactory;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.exceptions.CantGetPendingActionListException;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.exceptions.CantGetPendingTransactionException;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.exceptions.DatabaseOperationException;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants.APP_ACTIVITY_TO_OPEN_CODE;
import static com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants.APP_NOTIFICATION_PAINTER_FROM;
import static com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants.APP_TO_OPEN_PUBLIC_KEY;
import static com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants.NOTIFICATION_ID;
import static com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants.SOURCE_PLUGIN;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/01/16.
 * Updated by Jose Cardozo josejcb (josejcb89@gmail.com) on 16/03/16.
 */
public class ChatMiddlewareMonitorAgent2 extends AbstractAgent {

    Database database;
    LogManager logManager;
    EventManager eventManager;
    UUID pluginId;
    NetworkServiceChatManager chatNetworkServiceManager;
    MiddlewareChatManager chatMiddlewareManager;
    private final Broadcaster broadcaster;
    ChatMiddlewareDatabaseDao chatMiddlewareDatabaseDao;
    ChatActorConnectionManager chatActorConnectionManager;
    ChatMiddlewarePluginRoot chatMiddlewarePluginRoot;
    ChatManager chatActorNetworkServiceManager;
    PluginDatabaseSystem pluginDatabaseSystem;

    public final int DISCOVER_ITERATION_LIMIT = 2;
    int discoverIteration = 0;
    int iterationNumber = 0;

    public ChatMiddlewareMonitorAgent2(long sleepTime,
                                       TimeUnit timeUnit,
                                       long initDelayTime, PluginDatabaseSystem pluginDatabaseSystem,
                                       LogManager logManager,
                                       ChatMiddlewarePluginRoot chatMiddlewarePluginRoot,
                                       EventManager eventManager,
                                       UUID pluginId,
                                       NetworkServiceChatManager chatNetworkServiceManager,
                                       MiddlewareChatManager chatMiddlewareManager,
                                       Broadcaster broadcaster,
                                       ChatActorConnectionManager chatActorConnectionManager,
                                       ChatManager chatActorNetworkServiceManager) throws CantSetObjectException {
        super(sleepTime, timeUnit, initDelayTime);
        this.eventManager = eventManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.chatMiddlewarePluginRoot = chatMiddlewarePluginRoot;
        this.pluginId = pluginId;
        this.logManager = logManager;
        this.chatNetworkServiceManager = chatNetworkServiceManager;
        this.chatMiddlewareManager = chatMiddlewareManager;
        this.broadcaster = broadcaster;
        this.chatActorConnectionManager = chatActorConnectionManager;
        this.chatActorNetworkServiceManager = chatActorNetworkServiceManager;

        /**
         * Init the plugin database dao
         */

        try {
            Initialize();
        } catch (CantInitializeCHTAgent exception) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
        }
    }


    @Override
    protected void agentJob() {
        try {
            logManager.log(ChatMiddlewarePluginRoot.getLogLevelByClass(this.getClass().getName()),
                    "Iteration number " + iterationNumber, null, null);
            doTheMainTask();
        } catch (DatabaseOperationException e) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
        } catch (CantSendChatMessageException e) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
        }
    }

    @Override
    protected void onErrorOccur(Exception e) {        chatMiddlewarePluginRoot.reportError(
                UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                new Exception("UserLevelBusinessTransactionCustomerBrokerPurchaseMonitorAgent2 Error"));
    }

    public void Initialize() throws CantInitializeCHTAgent {
        try {

            database = this.pluginDatabaseSystem.openDatabase(pluginId,
                    ChatMiddlewareDatabaseConstants.DATABASE_NAME);
        } catch (DatabaseNotFoundException databaseNotFoundException) {

            ChatMiddlewareDatabaseFactory chatMiddlewareDatabaseFactory = new ChatMiddlewareDatabaseFactory(this.pluginDatabaseSystem);
            try {
                database = chatMiddlewareDatabaseFactory.createDatabase(pluginId, ChatMiddlewareDatabaseConstants.DATABASE_NAME);
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                throw new CantInitializeCHTAgent(cantCreateDatabaseException,
                        "Initialize Monitor Agent - trying to create the plugin database",
                        "Please, check the cause");
            }
        } catch (CantOpenDatabaseException exception) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw new CantInitializeCHTAgent(exception,
                    "Initialize Monitor Agent - trying to open the plugin database",
                    "Please, check the cause");
        } catch (Exception exception) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(exception));
            throw new CantInitializeCHTAgent(
                    FermatException.wrapException(exception),
                    "Initialize Monitor Agent - trying to open the plugin database",
                    "Unexpected exception");
        } finally {
            chatMiddlewareDatabaseDao = new ChatMiddlewareDatabaseDao(
                    pluginDatabaseSystem,
                    pluginId,
                    database,
                    chatMiddlewarePluginRoot);
        }
    }

    private void doTheMainTask() throws
            DatabaseOperationException,
            CantSendChatMessageException {

        //TODO: to implement
        try {

            if (discoverIteration == 0) {
//                sendChatBroadcasting();
                resetWritingStatus();
                checkOnlineStatus();
            }
            if (discoverIteration == 2) {
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

    public void checkIncomingMessageFail(MessageMetadata messageMetadata){
        try {
            Message message = chatMiddlewareDatabaseDao.getMessageByMessageId(messageMetadata.getMessageId());
            message.setStatus(MessageStatus.CANNOT_SEND);
            chatMiddlewareDatabaseDao.saveMessage(message);
            FermatBundle fermatBundle2 = new FermatBundle();
            fermatBundle2.put(SOURCE_PLUGIN, Plugins.CHAT_MIDDLEWARE.getCode());
            fermatBundle2.put(Broadcaster.PUBLISH_ID, SubAppsPublicKeys.CHT_OPEN_CHAT.getCode());
            fermatBundle2.put(Broadcaster.NOTIFICATION_TYPE, ChatBroadcasterConstants.CHAT_UPDATE_VIEW);
            broadcaster.publish(BroadcasterType.UPDATE_VIEW, SubAppsPublicKeys.CHT_OPEN_CHAT.getCode(), fermatBundle2);
        } catch (CantGetMessageException e) {
            e.printStackTrace();
        } catch (DatabaseOperationException e) {
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
    public void checkIncomingChat(MessageMetadata messageMetadata) throws CantGetPendingTransactionException, CantListChatException, UnexpectedResultReturnedFromDatabaseException {

        try {

            System.out.println("12345 CHECKING INCOMING CHAT");

            ChatLinkedActorIdentity identity = new ChatLinkedActorIdentity(messageMetadata.getRemoteActorPublicKey(), Actors.CHAT);
            ConnectionState connectionState = chatActorConnectionManager.getSearch(identity).getConnectionState(messageMetadata.getLocalActorPublicKey());

            System.out.println("12345 CHECKING CONTACT EXIST: connection state = " + connectionState);

            if(connectionState.equals(ConnectionState.CONNECTED)) {

                saveMessage(messageMetadata);

                FermatBundle fermatBundle = new FermatBundle();
                fermatBundle.put(SOURCE_PLUGIN, Plugins.CHAT_MIDDLEWARE.getCode());
                fermatBundle.put(APP_NOTIFICATION_PAINTER_FROM, new Owner(SubAppsPublicKeys.CHT_OPEN_CHAT.getCode()));
                fermatBundle.put(APP_TO_OPEN_PUBLIC_KEY, SubAppsPublicKeys.CHT_OPEN_CHAT.getCode());
                fermatBundle.put(NOTIFICATION_ID, ChatBroadcasterConstants.CHAT_NEW_INCOMING_MESSAGE_NOTIFICATION);
                fermatBundle.put(APP_ACTIVITY_TO_OPEN_CODE, Activities.CHT_CHAT_OPEN_CHATLIST.getCode());
                fermatBundle.put(Broadcaster.NOTIFICATION_TYPE, ChatBroadcasterConstants.CHAT_NEW_INCOMING_MESSAGE);
                broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, fermatBundle);

                FermatBundle fermatBundle2 = new FermatBundle();
                fermatBundle2.put(SOURCE_PLUGIN, Plugins.CHAT_MIDDLEWARE.getCode());
                fermatBundle2.put(Broadcaster.PUBLISH_ID, SubAppsPublicKeys.CHT_OPEN_CHAT.getCode());
                fermatBundle2.put(Broadcaster.NOTIFICATION_TYPE, ChatBroadcasterConstants.CHAT_UPDATE_VIEW);
                broadcaster.publish(BroadcasterType.UPDATE_VIEW, SubAppsPublicKeys.CHT_OPEN_CHAT.getCode(), fermatBundle2);

                FermatBundle fermatBundle3 = new FermatBundle();
                fermatBundle3.put(SOURCE_PLUGIN, Plugins.CHAT_MIDDLEWARE.getCode());
                fermatBundle3.put(Broadcaster.PUBLISH_ID, SubAppsPublicKeys.CHT_OPEN_CHAT.getCode());
                fermatBundle3.put(Broadcaster.NOTIFICATION_TYPE, ChatBroadcasterConstants.CHAT_LIST_UPDATE_VIEW);
                broadcaster.publish(BroadcasterType.UPDATE_VIEW, SubAppsPublicKeys.CHT_OPEN_CHAT.getCode(), fermatBundle3);

            }else
                System.out.println("12345 CONTACT IS NOT CONNECTED");
        } catch (CantGetActorConnectionException e) {
            throw new CantGetPendingTransactionException(
                    e,
                    "Checking if the actors are connected.",
                    "Unexpected error in database operation"
            );
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
    public void checkIncomingStatus(MessageMetadata messageMetadata) throws
            CantGetPendingTransactionException,
            UnexpectedResultReturnedFromDatabaseException {
        try {

            System.out.println("12345 CHECKING INCOMING STATUS INSIDE IF MESSAGE == " + messageMetadata.getMessage() + " MESSAGE STATUS == " + messageMetadata.getMessageStatus());

            updateMessageStatus(messageMetadata);
            if (messageMetadata.getMessageStatus() != MessageStatus.READ) {
                FermatBundle fermatBundle3 = new FermatBundle();
                fermatBundle3.put(SOURCE_PLUGIN, Plugins.CHAT_MIDDLEWARE.getCode());
                fermatBundle3.put(Broadcaster.PUBLISH_ID, SubAppsPublicKeys.CHT_OPEN_CHAT.getCode());
                fermatBundle3.put(Broadcaster.NOTIFICATION_TYPE, ChatBroadcasterConstants.CHAT_LIST_UPDATE_VIEW);
                broadcaster.publish(BroadcasterType.UPDATE_VIEW, SubAppsPublicKeys.CHT_OPEN_CHAT.getCode(), fermatBundle3);
            }

            FermatBundle fermatBundle2 = new FermatBundle();
            fermatBundle2.put(SOURCE_PLUGIN, Plugins.CHAT_MIDDLEWARE.getCode());
            fermatBundle2.put(Broadcaster.PUBLISH_ID, SubAppsPublicKeys.CHT_OPEN_CHAT.getCode());
            fermatBundle2.put(Broadcaster.NOTIFICATION_TYPE, ChatBroadcasterConstants.CHAT_UPDATE_VIEW);
            broadcaster.publish(BroadcasterType.UPDATE_VIEW, SubAppsPublicKeys.CHT_OPEN_CHAT.getCode(), fermatBundle2);

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

            List<ActionOnline> onlineActions = chatMiddlewareDatabaseDao.getOnlineActionsByActiveState();

            if (onlineActions == null || onlineActions.isEmpty()) return;


            for (ActionOnline actionOnline : onlineActions) {
                boolean isOnline = chatActorNetworkServiceManager.isActorOnline(actionOnline.getPublicKey());
                actionOnline.setValue(isOnline);
                if (isOnline) actionOnline.setLastOn(false);
                if (!isOnline && actionOnline.getLastOn() != true) {
                    DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
                    Date today = Calendar.getInstance().getTime();
                    actionOnline.setLastConnection(df.format(today));
                    actionOnline.setLastOn(true);
                }
                chatMiddlewareDatabaseDao.saveOnlineAction(actionOnline);
            }
            FermatBundle fermatBundle2 = new FermatBundle();
            fermatBundle2.put(SOURCE_PLUGIN, Plugins.CHAT_MIDDLEWARE.getCode());
            fermatBundle2.put(Broadcaster.PUBLISH_ID, SubAppsPublicKeys.CHT_OPEN_CHAT.getCode());
            fermatBundle2.put(Broadcaster.NOTIFICATION_TYPE, ChatBroadcasterConstants.CHAT_UPDATE_VIEW);
            broadcaster.publish(BroadcasterType.UPDATE_VIEW, SubAppsPublicKeys.CHT_OPEN_CHAT.getCode(), fermatBundle2);

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

    public void resetWritingStatus() {
        try {

            boolean changes = false;

            //Resetear los writing state enviados
            List<UUID> chatsId = chatMiddlewareDatabaseDao.getWritingActions();

            if (chatsId != null && !chatsId.isEmpty()) {
                for (UUID chatId : chatsId) {
                    chatMiddlewareDatabaseDao.saveWritingAction(chatId, ActionState.NONE);
                }
            }

            //Resetear los writing state recibidos
            List<Chat> chats = chatMiddlewareDatabaseDao.getChatListByWriting();

            if (chats == null || chats.isEmpty()) return;

            for (Chat chat : chats) {
                chat.setIsWriting(false);
                chatMiddlewareDatabaseDao.saveChat(chat);
                changes = true;
            }

            if (changes) {

                FermatBundle fermatBundle2 = new FermatBundle();
                fermatBundle2.put(SOURCE_PLUGIN, Plugins.CHAT_MIDDLEWARE.getCode());
                fermatBundle2.put(Broadcaster.PUBLISH_ID, SubAppsPublicKeys.CHT_OPEN_CHAT.getCode());
                fermatBundle2.put(Broadcaster.NOTIFICATION_TYPE, ChatBroadcasterConstants.CHAT_UPDATE_VIEW);
                broadcaster.publish(BroadcasterType.UPDATE_VIEW, SubAppsPublicKeys.CHT_OPEN_CHAT.getCode(), fermatBundle2);

                FermatBundle fermatBundle3 = new FermatBundle();
                fermatBundle3.put(SOURCE_PLUGIN, Plugins.CHAT_MIDDLEWARE.getCode());
                fermatBundle3.put(Broadcaster.PUBLISH_ID, SubAppsPublicKeys.CHT_OPEN_CHAT.getCode());
                fermatBundle3.put(Broadcaster.NOTIFICATION_TYPE, ChatBroadcasterConstants.CHAT_LIST_UPDATE_VIEW);
                broadcaster.publish(BroadcasterType.UPDATE_VIEW, SubAppsPublicKeys.CHT_OPEN_CHAT.getCode(), fermatBundle3);
            }


        } catch (DatabaseOperationException e) {
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

    public void resetOnlineStatus() {
        try {

            boolean changes = false;

            List<ActionOnline> actionOnlines;
            actionOnlines = chatMiddlewareDatabaseDao.getOnlineActionsByOnline();

            if (actionOnlines == null || actionOnlines.isEmpty()) return;

            for (ActionOnline actionOnline : actionOnlines) {
                actionOnline.setValue(false);
                DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
                Date today = Calendar.getInstance().getTime();
                actionOnline.setLastConnection(df.format(today));
                chatMiddlewareDatabaseDao.saveOnlineAction(actionOnline);
                changes = true;
            }

            if (changes) {
                FermatBundle fermatBundle2 = new FermatBundle();
                fermatBundle2.put(SOURCE_PLUGIN, Plugins.CHAT_MIDDLEWARE.getCode());
                fermatBundle2.put(Broadcaster.PUBLISH_ID, SubAppsPublicKeys.CHT_OPEN_CHAT.getCode());
                fermatBundle2.put(Broadcaster.NOTIFICATION_TYPE, ChatBroadcasterConstants.CHAT_UPDATE_VIEW);
                broadcaster.publish(BroadcasterType.UPDATE_VIEW, SubAppsPublicKeys.CHT_OPEN_CHAT.getCode(), fermatBundle2);
            }
        } catch (CantGetPendingActionListException e) {
            e.printStackTrace();
        } catch (CantSaveActionException e) {
            e.printStackTrace();
        }
    }

    public void checkIncomingWritingStatus(String senderPk) throws
            CantGetPendingTransactionException,
            UnexpectedResultReturnedFromDatabaseException {
        try {

            Chat chat = chatMiddlewareDatabaseDao.getChatByRemotePublicKey(senderPk);
            if (chat != null) {
                System.out.println("12345 Saving is writing chat " + chat.getChatId());
                chat.setIsWriting(true);
                chatMiddlewareDatabaseDao.saveChat(chat);
                System.out.println("12345 chat saved");
            }

            FermatBundle fermatBundle2 = new FermatBundle();
            fermatBundle2.put(SOURCE_PLUGIN, Plugins.CHAT_MIDDLEWARE.getCode());
            fermatBundle2.put(Broadcaster.PUBLISH_ID, SubAppsPublicKeys.CHT_OPEN_CHAT.getCode());
            fermatBundle2.put(Broadcaster.NOTIFICATION_TYPE, ChatBroadcasterConstants.CHAT_UPDATE_VIEW);
            broadcaster.publish(BroadcasterType.UPDATE_VIEW, SubAppsPublicKeys.CHT_OPEN_CHAT.getCode(), fermatBundle2);

            FermatBundle fermatBundle3 = new FermatBundle();
            fermatBundle3.put(SOURCE_PLUGIN, Plugins.CHAT_MIDDLEWARE.getCode());
            fermatBundle3.put(Broadcaster.PUBLISH_ID, SubAppsPublicKeys.CHT_OPEN_CHAT.getCode());
            fermatBundle3.put(Broadcaster.NOTIFICATION_TYPE, ChatBroadcasterConstants.CHAT_LIST_UPDATE_VIEW);
            broadcaster.publish(BroadcasterType.UPDATE_VIEW, SubAppsPublicKeys.CHT_OPEN_CHAT.getCode(), fermatBundle3);

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

    /**
     * This method saves the new chat in database
     *
     * @param messageMetadata
     * @throws DatabaseOperationException
     */
    private void saveMessage(MessageMetadata messageMetadata)
            throws DatabaseOperationException, CantGetChatException,
            CantSaveChatException,
            CantSaveMessageException,
            CantGetMessageException, SendStatusUpdateMessageNotificationException {

        Chat chat = chatMiddlewareDatabaseDao.getChatByRemotePublicKey(messageMetadata.getLocalActorPublicKey());

        if (chat == null) {
            chat = new ChatImpl();
            chat.setChatId(UUID.randomUUID());
            chat.setObjectId(UUID.randomUUID());
            chat.setLocalActorPublicKey(messageMetadata.getRemoteActorPublicKey());
            chat.setRemoteActorPublicKey(messageMetadata.getLocalActorPublicKey());
            Long dv = System.currentTimeMillis();
            chat.setDate(new Timestamp(dv));
            chat.setTypeChat(TypeChat.INDIVIDUAL);
        }
        chat.setLastMessageDate(new Timestamp(System.currentTimeMillis()));//updating date of last message arrived in chat

        chat.setStatus(ChatStatus.VISSIBLE);

        chatMiddlewareDatabaseDao.saveChat(chat);

//        if (messageRecorded == null) {
        /**
         * In this case, the message is not created in database, so, is an incoming message,
         * I need to create a new message
         */
        Message messageRecorded = getMessageFromChatMetadata(
                messageMetadata);
        if (messageRecorded == null) return;
//        }
        messageRecorded.setChatId(chat.getChatId());
        messageRecorded.setStatus(MessageStatus.RECEIVE);
        chatMiddlewareDatabaseDao.saveMessage(messageRecorded);
        chatMiddlewareManager.sendDeliveredMessageNotification(messageRecorded);
        //End: Implementation trying to avoid messages in wrong chat
    }

    /**
     * This method creates a new Message from incoming metadata
     *
     * @param messageMetadata
     * @return
     */
    private Message getMessageFromChatMetadata(final MessageMetadata messageMetadata) throws CantGetMessageException {

        if (messageMetadata == null)
            throw new CantGetMessageException("The chat metadata from network service is null");

        try {
            Chat chatFromDatabase = chatMiddlewareDatabaseDao.getChatByRemotePublicKey(messageMetadata.getLocalActorPublicKey());

            ChatLinkedActorIdentity chatLinkedActorIdentity = new ChatLinkedActorIdentity(
                    chatFromDatabase.getLocalActorPublicKey(),
                    Actors.CHAT
            );

            final ChatActorConnectionSearch search = chatActorConnectionManager.getSearch(chatLinkedActorIdentity);
            ChatActorConnection actorConnection;

            try {
                actorConnection = search.findByPublicKey(chatFromDatabase.getRemoteActorPublicKey());
            } catch (ActorConnectionNotFoundException ex) {

                return null;
            }

            return new MessageImpl(
                    chatFromDatabase.getChatId(),
                    messageMetadata,
                    MessageStatus.CREATED,
                    TypeMessage.INCOMMING,
                    actorConnection.getConnectionId()//TODO:Revisar esto si afecta el envio ya que el public es un string//UUID.fromString(actorConnection.getPublicKey())
            );

        } catch (DatabaseOperationException e) {
            throw new CantGetMessageException(e,
                    "Getting message from ChatMetadata",
                    "Unexpected exception in database");
        } catch (CantGetChatException e) {
            throw new CantGetMessageException(e,
                    "Getting message from ChatMetadata",
                    "Cannot get the chat");
        } catch (CantGetActorConnectionException e) {
            throw new CantGetMessageException(e,
                    "Getting message from ChatMetadata",
                    "Cannot get the ActorConnection");
        }
    }

    /**
     * This method updates a message record in database.
     *
     * @param messageMetadata,
     * @throws DatabaseOperationException
     * @throws CantSaveMessageException
     * @throws CantGetMessageException
     */
    private void updateMessageStatus(
            MessageMetadata messageMetadata) throws
            DatabaseOperationException,
            CantSaveMessageException,
            CantGetMessageException {
        System.out.println("12345 UPDATING MESSAGE STATUS");
        UUID messageId = messageMetadata.getMessageId();
        Message messageRecorded = chatMiddlewareDatabaseDao.getMessageByMessageId(messageId);
        if (messageRecorded == null) {
            /**
             * In this case, the message is not created in database, so, is an incoming message,
             * I need to create a new message
             */

            System.out.println("************* MESSAGE DOES NOT EXIST");
            messageRecorded = getMessageFromChatMetadata(
                    messageMetadata);
            if (messageRecorded == null) return;
        }
        if (messageRecorded.getStatus().equals(MessageStatus.READ))
            return;

        messageRecorded.setStatus(messageMetadata.getMessageStatus());
        chatMiddlewareDatabaseDao.saveMessage(messageRecorded);
        System.out.println("12345 MESSAGE STATUS UPDATED");
    }

}

