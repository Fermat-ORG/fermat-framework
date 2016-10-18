package com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_cht_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantInitializeDatabaseException;
import com.bitdubai.fermat_cht_api.layer.actor_connection.interfaces.ChatActorConnectionManager;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.interfaces.ChatManager;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces.NetworkServiceChatManager;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.database.ChatMiddlewareDatabaseConstants;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.database.ChatMiddlewareDatabaseDao;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.database.ChatMiddlewareDatabaseFactory;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.database.ChatMiddlewareDeveloperDatabaseFactory;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.event_handler.ChangedMessageStatusUpdateEventHandler;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.event_handler.IncomingMessageEventHandler;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.event_handler.IncomingNewWritingStatusUpdateEventHandler;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.structure.ChatMiddlewareEventActions;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.structure.ChatMiddlewareManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 10/01/16.
 */
@PluginInfo(createdBy = "Manuel Perez", maintainerMail = "franklinmarcano1970@gmail.com", platform = Platforms.CHAT_PLATFORM, layer = Layers.MIDDLEWARE, plugin = Plugins.CHAT_MIDDLEWARE)
public class ChatMiddlewarePluginRoot extends AbstractPlugin implements DatabaseManagerForDevelopers {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededPluginReference(platform = Platforms.CHAT_PLATFORM, layer = Layers.NETWORK_SERVICE, plugin = Plugins.CHAT_NETWORK_SERVICE)
    private NetworkServiceChatManager networkServiceChatManager;

    @NeededPluginReference(platform = Platforms.CHAT_PLATFORM, layer = Layers.ACTOR_CONNECTION, plugin = Plugins.CHAT_ACTOR_CONNECTION)
    private ChatActorConnectionManager chatActorConnectionManager;

    @NeededPluginReference(platform = Platforms.CHAT_PLATFORM, layer = Layers.ACTOR_NETWORK_SERVICE, plugin = Plugins.CHAT_ACTOR_NETWORK_SERVICE)
    private ChatManager chatActorNetworkService;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_BROADCASTER_SYSTEM)
    Broadcaster broadcaster;

    /**
     * Represent the database
     */
    private Database database;

    private ChatMiddlewareDeveloperDatabaseFactory chatMiddlewareDeveloperDatabaseFactory;

    private ChatMiddlewareManager chatMiddlewareManager;

    public ChatMiddlewarePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    /**
     * This method initialize the database
     *
     * @throws CantInitializeDatabaseException
     */
    private void initializeDb() throws CantInitializeDatabaseException {

        try {

            this.database = this.pluginDatabaseSystem.openDatabase(pluginId, ChatMiddlewareDatabaseConstants.DATABASE_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

        } catch (DatabaseNotFoundException e) {

            ChatMiddlewareDatabaseFactory chatMiddlewareDatabaseFactory = new ChatMiddlewareDatabaseFactory(pluginDatabaseSystem);

            try {

                this.database = chatMiddlewareDatabaseFactory.createDatabase(pluginId, ChatMiddlewareDatabaseConstants.DATABASE_NAME);

            } catch (CantCreateDatabaseException cantOpenDatabaseException) {

                reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
                throw new CantInitializeDatabaseException(cantOpenDatabaseException.getLocalizedMessage());
            }
        }
    }

    private List<FermatEventListener> listenersAdded = new ArrayList<>();

    private void initializeListeners(ChatMiddlewareEventActions eventActions) {

        FermatEventListener INCOMING_MESSAGEListener = eventManager.getNewListener(EventType.INCOMING_MESSAGE);
        FermatEventHandler INCOMING_MESSAGEHandler = new IncomingMessageEventHandler(this, eventActions);
        INCOMING_MESSAGEListener.setEventHandler(INCOMING_MESSAGEHandler);
        eventManager.addListener(INCOMING_MESSAGEListener);
        listenersAdded.add(INCOMING_MESSAGEListener);

        FermatEventListener CHANGED_MESSAGE_STATUSListener = eventManager.getNewListener(EventType.CHANGED_MESSAGE_STATUS);
        FermatEventHandler CHANGED_MESSAGE_STATUSHandler = new ChangedMessageStatusUpdateEventHandler(this, eventActions);
        CHANGED_MESSAGE_STATUSListener.setEventHandler(CHANGED_MESSAGE_STATUSHandler);
        eventManager.addListener(CHANGED_MESSAGE_STATUSListener);
        listenersAdded.add(CHANGED_MESSAGE_STATUSListener);

        FermatEventListener INCOMING_WRITING_STATUSListener = eventManager.getNewListener(EventType.INCOMING_WRITING_STATUS);
        FermatEventHandler INCOMING_WRITING_STATUSHandler = new IncomingNewWritingStatusUpdateEventHandler(this, eventActions);
        INCOMING_WRITING_STATUSListener.setEventHandler(INCOMING_WRITING_STATUSHandler);
        eventManager.addListener(INCOMING_WRITING_STATUSListener);
        listenersAdded.add(INCOMING_WRITING_STATUSListener);
    }

    @Override
    public void start() throws CantStartPluginException {

        try {

            /**
             * Initialize database
             */
            initializeDb();

            /**
             * Initialize Dao
             */
            ChatMiddlewareDatabaseDao chatMiddlewareDatabaseDao = new ChatMiddlewareDatabaseDao(database, this);

            /**
             * Init developer database factory
             */
            chatMiddlewareDeveloperDatabaseFactory = new ChatMiddlewareDeveloperDatabaseFactory(database);

            /**
             * Initialize manager
             */
            chatMiddlewareManager = new ChatMiddlewareManager(
                    chatMiddlewareDatabaseDao,
                    this,
                    this.networkServiceChatManager,
                    this.networkServiceChatManager,
                    this.broadcaster,
                    chatActorConnectionManager
            );

            ChatMiddlewareEventActions eventActions = new ChatMiddlewareEventActions(
                    chatMiddlewareDatabaseDao,
                    broadcaster,
                    chatActorConnectionManager,
                    networkServiceChatManager
            );

            initializeListeners(eventActions);

            this.serviceStatus = ServiceStatus.STARTED;

        } catch (CantInitializeDatabaseException exception) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantStartPluginException(
                    CantStartPluginException.DEFAULT_MESSAGE,
                    FermatException.wrapException(exception),
                    "Starting open contract plugin",
                    "Cannot initialize plugin database");
        } catch (Exception exception) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantStartPluginException(
                    CantStartPluginException.DEFAULT_MESSAGE,
                    FermatException.wrapException(exception),
                    "Starting open contract plugin",
                    "Unexpected Exception");
        }
    }

    @Override
    public void pause() {
        this.serviceStatus = ServiceStatus.PAUSED;
    }

    @Override
    public void resume() {
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {

        for (FermatEventListener fermatEventListener : listenersAdded) {
            eventManager.removeListener(fermatEventListener);
        }
        listenersAdded.clear();

        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public FermatManager getManager() {
        return chatMiddlewareManager;
    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        try {
            return chatMiddlewareDeveloperDatabaseFactory.getDatabaseList(developerObjectFactory);
        } catch (Exception exception) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    FermatException.wrapException(exception));
            return null;
        }

    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        try {
            return chatMiddlewareDeveloperDatabaseFactory.getDatabaseTableList(developerObjectFactory);
        } catch (Exception exception) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    FermatException.wrapException(exception));
            return null;
        }
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        try {
            return chatMiddlewareDeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (Exception exception) {
            reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    FermatException.wrapException(exception));
            return null;
        }
    }
}
