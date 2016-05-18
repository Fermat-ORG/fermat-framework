package com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_cht_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetCompatiblesActorNetworkServiceListException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantInitializeDatabaseException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantStartServiceException;
import com.bitdubai.fermat_cht_api.layer.actor_connection.interfaces.ChatActorConnectionManager;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.interfaces.ChatManager;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Chat;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Message;
import com.bitdubai.fermat_cht_api.layer.middleware.mocks.ChatMock;
import com.bitdubai.fermat_cht_api.layer.middleware.mocks.MessageMock;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.events.IncomingChat;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces.NetworkServiceChatManager;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.database.ChatMiddlewareDatabaseConstants;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.database.ChatMiddlewareDatabaseDao;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.database.ChatMiddlewareDatabaseFactory;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.database.ChatMiddlewareDeveloperDatabaseFactory;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.event_handler.ChatMiddlewareRecorderService;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.exceptions.CantInitializeChatMiddlewareDatabaseException;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.structure.ChatMiddlewareContactFactory;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.structure.ChatMiddlewareManager;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.structure.ChatMiddlewareMonitorAgent;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantRequestListException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUserManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 10/01/16.
 */
@PluginInfo(createdBy = "Manuel Perez", maintainerMail = "franklinmarcano1970@gmail.com",platform = Platforms.CHAT_PLATFORM, layer = Layers.MIDDLEWARE, plugin = Plugins.CHAT_MIDDLEWARE)
public class ChatMiddlewarePluginRoot extends AbstractPlugin implements
        DatabaseManagerForDevelopers,
        LogManagerForDevelopers {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.LOG_MANAGER)
    LogManager logManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.USER, addon = Addons.DEVICE_USER)
    private DeviceUserManager deviceUserManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

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

    public static EventSource EVENT_SOURCE = EventSource.MIDDLEWARE_CHAT_MANAGER;

    public EventManager getEventManager() {
        return eventManager;
    }

    /**
     * Represent the database
     */
    private Database database;

    private ChatMiddlewareDeveloperDatabaseFactory chatMiddlewareDeveloperDatabaseFactory;

    private ChatMiddlewareManager chatMiddlewareManager;

    ChatMiddlewareContactFactory chatMiddlewareContactFactory;

    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

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
            /*
             * Open new database connection
             */
            this.database = this.pluginDatabaseSystem.openDatabase(
                    pluginId,
                    ChatMiddlewareDatabaseConstants.DATABASE_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

            /*
             * The database exists but cannot be open. I can not handle this situation.
             */
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    cantOpenDatabaseException);
//            errorManager.reportUnexpectedPluginException(
//                    Plugins.CHAT_MIDDLEWARE,
//                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
//                    cantOpenDatabaseException);
            throw new CantInitializeDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

        } catch (DatabaseNotFoundException e) {

            /*
             * The database no exist may be the first time the plugin is running on this device,
             * We need to create the new database
             */
            ChatMiddlewareDatabaseFactory chatMiddlewareDatabaseFactory =
                    new ChatMiddlewareDatabaseFactory(pluginDatabaseSystem);

            try {

                /*
                 * We create the new database
                 */
                this.database = chatMiddlewareDatabaseFactory.createDatabase(
                        pluginId,
                        ChatMiddlewareDatabaseConstants.DATABASE_NAME);

            } catch (CantCreateDatabaseException cantOpenDatabaseException) {

                /*
                 * The database cannot be created. I can not handle this situation.
                 */
                reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                        cantOpenDatabaseException);
                throw new CantInitializeDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

            }
        }

    }

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        try{
            returnedClasses.add("com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.ChatMiddlewarePluginRoot");
            return returnedClasses;
        } catch (Exception exception){
            reportError(UnexpectedPluginExceptionSeverity.NOT_IMPORTANT,
                    FermatException.wrapException(exception));
            //I'll return an empty list
            return returnedClasses;
        }

    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {
        try{
            /*
         * I will check the current values and update the LogLevel in those which is different
         */
            for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {

            /*
             * if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
             */
                if (ChatMiddlewarePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                    ChatMiddlewarePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                    ChatMiddlewarePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                } else {
                    ChatMiddlewarePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                }
            }
        } catch (Exception exception){
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(exception));
        }

    }

    ServiceStatus serviceStatus = ServiceStatus.CREATED;

    @Override
    public void start() throws CantStartPluginException {
        try {

            /**
             * Initialize database
             */
            //System.out.println("OPEN_CONTRACT DB");
            initializeDb();

            /*
             * Initialize Developer Database Factory
             */
            chatMiddlewareDeveloperDatabaseFactory = new
                    ChatMiddlewareDeveloperDatabaseFactory(pluginDatabaseSystem,
                    pluginId);
            chatMiddlewareDeveloperDatabaseFactory.initializeDatabase();

            /**
             * Initialize Dao
             */
            ChatMiddlewareDatabaseDao chatMiddlewareDatabaseDao=
                    new ChatMiddlewareDatabaseDao(pluginDatabaseSystem,
                            pluginId,
                            database,
                            this,
                            pluginFileSystem);
            //chatMiddlewareDatabaseDao.initialize();
            /**
             * Init developer database factory
             */
            chatMiddlewareDeveloperDatabaseFactory =
                    new ChatMiddlewareDeveloperDatabaseFactory(
                            pluginDatabaseSystem,
                            pluginId);
            chatMiddlewareDeveloperDatabaseFactory.initializeDatabase();
            //Initialize Contact Factory
            //TODO:Eliminar
            //initializeContactFactory();
            /**
             * Initialize manager
             */
            chatMiddlewareManager =new ChatMiddlewareManager(
                    chatMiddlewareDatabaseDao,
                    this.chatMiddlewareContactFactory,
                    this,
                    this.networkServiceChatManager,
                    this.deviceUserManager,
                    this.networkServiceChatManager,
                    this.broadcaster,
                    chatActorConnectionManager
            );

            /**
             * Init monitor Agent
             */
            ChatMiddlewareMonitorAgent openContractMonitorAgent=new ChatMiddlewareMonitorAgent(
                    pluginDatabaseSystem,
                    logManager,
                    this,
                    eventManager,
                    pluginId,
                    networkServiceChatManager,
                    chatMiddlewareManager,
                    broadcaster,
                    pluginFileSystem,
                    chatActorConnectionManager,
                    chatActorNetworkService);
            openContractMonitorAgent.start();

            /**
             * Init event recorder service.
             */
            ChatMiddlewareRecorderService chatMiddlewareRecorderService=new ChatMiddlewareRecorderService(
                    chatMiddlewareDatabaseDao,
                    eventManager,
                    this,
                    openContractMonitorAgent);
            chatMiddlewareRecorderService.start();





            this.serviceStatus = ServiceStatus.STARTED;
            //Test method
            //testPublicKeys();
            //sendMessageTest();
            //receiveMessageTest();
            //identitiesTest();
            //discoveryTest();
            //getContactTest();
            //getOwnIdentitiesTest();

        } catch (CantInitializeDatabaseException exception) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantStartPluginException(
                    CantStartPluginException.DEFAULT_MESSAGE,
                    FermatException.wrapException(exception),
                    "Starting open contract plugin",
                    "Cannot initialize plugin database");
        } catch (CantInitializeChatMiddlewareDatabaseException exception) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantStartPluginException(
                    CantStartPluginException.DEFAULT_MESSAGE,
                    exception,
                    "Starting open contract plugin",
                    "Unexpected Exception");
        } catch (CantStartServiceException exception) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantStartPluginException(
                    CantStartPluginException.DEFAULT_MESSAGE,
                    exception,
                    "Starting open contract plugin",
                    "Cannot start recorder service");
        } catch (CantSetObjectException exception) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantStartPluginException(
                    CantStartPluginException.DEFAULT_MESSAGE,
                    exception,
                    "Starting open contract plugin",
                    "Cannot set an object");
        } catch (CantStartAgentException exception) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantStartPluginException(
                    CantStartPluginException.DEFAULT_MESSAGE,
                    FermatException.wrapException(exception),
                    "Starting open contract plugin",
                    "Cannot start the monitor agent");
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
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public FermatManager getManager() {
        return chatMiddlewareManager;
    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        try{
            return chatMiddlewareDeveloperDatabaseFactory.getDatabaseList(developerObjectFactory);
        } catch (Exception exception) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    FermatException.wrapException(exception));
            return null;
        }

    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        try{
            return chatMiddlewareDeveloperDatabaseFactory.getDatabaseTableList(developerObjectFactory);
        } catch (Exception exception) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    FermatException.wrapException(exception));
            return null;
        }
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        try{
            return chatMiddlewareDeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (Exception exception) {
            reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    FermatException.wrapException(exception));
            return null;
        }
    }

    public static LogLevel getLogLevelByClass(String className) {
        try{
            /**
             * sometimes the classname may be passed dynamically with an $moretext
             * I need to ignore whats after this.
             */
            String[] correctedClass = className.split((Pattern.quote("$")));
            return ChatMiddlewarePluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e){
            /**
             * If I couldn't get the correct logging level, then I will set it to minimal.
             */
            return DEFAULT_LOG_LEVEL;
        }
    }

    private void testPublicKeys(){
        List<String> publicKey = null;
        try {
            publicKey = networkServiceChatManager.getRegisteredPubliKey();
        } catch (CantRequestListException e) {
            System.out.println("Exception in chat middleware test: "+e.getMessage());
            e.printStackTrace();
        }
        System.out.println("ChatPLuginRoot MY PUBLIC KEY- "+ networkServiceChatManager.getNetWorkServicePublicKey());
        System.out.println("-------------------REGISTED CHAT NETWORK SERVICE PUBLIC KEYS------------------");
        for (String key : publicKey){
            System.out.println(key);
        }
        System.out.println("-------------------REGISTED CHAT NETWORK SERVICE PUBLIC KEYS END------------------");
    }
    private void sendMessageTest(){
        try{
            Chat testChat=new ChatMock();
            testChat.setLocalActorPublicKey(networkServiceChatManager.getNetWorkServicePublicKey());
           // List<String> remotePublicKey = networkServiceChatManager.getRegisteredPubliKey();
            testChat.setRemoteActorPublicKey("04500233060C68AD5ADD20AB786BC0BA1335F1D92786F65FD3685469AADCA9282563864F50827C1F1DF6F778BEAECE80964550701A9B78B220DD215CF434E9D8CA");
            Message testMessage=new MessageMock(UUID.fromString("52d7fab8-a423-458f-bcc9-49cdb3e9ba8f"));
            this.chatMiddlewareManager.saveChat(testChat);
            this.chatMiddlewareManager.saveMessage(testMessage);
        } catch(Exception exception){
            System.out.println("Exception in chat middleware test: "+exception.getMessage());
            exception.printStackTrace();
        }
    }

    private void receiveMessageTest(){
        try {
            /**
             * This test must be finish in an exception, because the network service mock is not
             * created. The objective in this method is raise the incoming chat and handle this
             * event. This exception must be thrown in chat monitor agent.
             */
            FermatEvent fermatEvent = eventManager.getNewEvent(EventType.INCOMING_CHAT);
            IncomingChat incomingChat = (IncomingChat) fermatEvent;
            incomingChat.setSource(EventSource.NETWORK_SERVICE_CHAT);
            incomingChat.setChatId(UUID.fromString("52d7fab8-a423-458f-bcc9-49cdb3e9ba8f"));
            eventManager.raiseEvent(incomingChat);
        } catch(Exception exception){
            System.out.println("Exception in raise event chat middleware test: "+exception.getMessage());
            exception.printStackTrace();
        }
    }

//    private void identitiesTest(){
//        try{
//            List<ActorAssetUser> identitiesList=this.assetUserActorNetworkServiceManager.getListActorAssetUserRegistered();
//            int counter=0;
//            for(ActorAssetUser actor : identitiesList){
//                System.out.println("Identities Test: Init*****");
//                System.out.println("Identities Test: Actor "+counter);
//                System.out.println("Identities Test: Name -- "+actor.getName());
//                System.out.println("Identities Test: PK -- "+actor.getActorPublicKey());
//                System.out.println("Identities Test: PublicLinkedIdentity -- "+actor.getPublicLinkedIdentity());
//                counter++;
//            }
//        } catch (Exception exception){
//            System.out.println("Exception in raise event chat middleware identities test: "+exception.getMessage());
//            exception.printStackTrace();
//        }
//
//    }

//    private void discoveryTest(){
//        try{
//            List<ContactConnection> contactList=this.chatMiddlewareContactFactory.discoverDeviceActors();
//            System.out.println("Discovery Test: Init*****");
//            int counter=0;
//            for(ContactConnection contact : contactList){
//                System.out.println("Discovery Test: Contact "+counter+"\n"+contact);
//                counter++;
//            }
//        } catch (Exception exception){
//            System.out.println("Exception in raise event chat middleware discovery test: "+exception.getMessage());
//            exception.printStackTrace();
//        }
//    }



}
