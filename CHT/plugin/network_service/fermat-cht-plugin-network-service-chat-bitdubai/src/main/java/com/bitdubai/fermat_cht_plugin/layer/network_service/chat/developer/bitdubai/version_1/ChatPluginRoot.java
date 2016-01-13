package com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.DiscoveryQueryParameters;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
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
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkService;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkServiceConnectionManager;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantConfirmTransactionException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantDeliverPendingTransactionsException;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_cht_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.ChatMessageStatus;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.DistributionStatus;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.events.OutgoingChat;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.exceptions.CantInitializeCommunicationNetworkServiceConnectionManagerException;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.exceptions.CantSendChatMessageMetadataException;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.exceptions.CantSendChatMessageNewStatusNotificationException;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces.ChatManager;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces.ChatMetada;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.communications.CommunicationNetworkServiceConnectionManager;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.communications.CommunicationNetworkServiceLocal;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.database.ChatMetaDataDao;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.database.NetworkServiceChatNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.database.NetworkServiceChatNetworkServiceDatabaseFactory;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.database.NetworkServiceChatNetworkServiceDeveloperDatabaseFactory;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.database.OutgoingMessageDao;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.event_handlers.ClientConnectionCloseNotificationEventHandler;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.event_handlers.CompleteComponentConnectionRequestNotificationEventHandler;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.event_handlers.CompleteComponentRegistrationNotificationEventHandler;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.event_handlers.CompleteRequestListComponentRegisteredNotificationEventHandler;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.event_handlers.FailureComponentConnectionRequestNotificationEventHandler;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.event_handlers.NewReceiveMessagesNotificationEventHandler;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.event_handlers.NewSentMessagesNotificationEventHandler;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.event_handlers.VPNConnectionCloseNotificationEventHandler;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.exceptions.CantInitializeChatNetworkServiceDatabaseException;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.structure.ChatMetadaTransactionRecord;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.structure.EncodeMsjContent;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.communications.CommunicationRegistrationProcessNetworkServiceAgent;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.structure.MockChat;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatMessageCommunication;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatMessageCommunicationFactory;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.ClientConnectionCloseNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.VPNConnectionCloseNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessageContentType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessagesStatus;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantRequestListException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Created by Gabriel Araujo on 05/01/16.
 */
public class ChatPluginRoot extends AbstractPlugin implements
        NetworkService,
        LogManagerForDevelopers,
        DatabaseManagerForDevelopers,
        ChatManager{

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.LOG_MANAGER)
    private LogManager logManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededPluginReference(platform = Platforms.COMMUNICATION_PLATFORM, layer = Layers.COMMUNICATION, plugin = Plugins.WS_CLOUD_CLIENT)
    private WsCommunicationsCloudClientManager wsCommunicationsCloudClientManager;


    private List<PlatformComponentProfile> registeredComponentList;


    public EventManager getEventManager() {
        return eventManager;
    }

    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    public ChatPluginRoot() {
        super(new PluginVersionReference(new Version()));
        this.listenersAdded = new ArrayList<>();
        this.platformComponentType = PlatformComponentType.NETWORK_SERVICE;
        this.networkServiceType = NetworkServiceType.CHAT;
        this.name = "Network Service Chat";
        this.alias = "NetworkServiceChat";

        this.extraData = null;
    }

    @Override
    public String toString() {
        return "ChatPluginRoot{" +
                "errorManager=" + errorManager +
                ", eventManager=" + eventManager +
                ", logManager=" + logManager +
                ", pluginDatabaseSystem=" + pluginDatabaseSystem +
                ", wsCommunicationsCloudClientManager=" + wsCommunicationsCloudClientManager +
                ", register=" + register +
                ", name='" + name + '\'' +
                ", alias='" + alias + '\'' +
                ", extraData='" + extraData + '\'' +
                ", dataBase=" + dataBase +
                ", identity=" + identity +
                ", networkServiceType=" + networkServiceType +
                ", platformComponentProfile=" + platformComponentProfile +
                ", listenersAdded=" + listenersAdded +
                ", chatMetaDataDao=" + chatMetaDataDao +
                ", platformComponentType=" + platformComponentType +
                ", communicationNetworkServiceConnectionManager=" + communicationNetworkServiceConnectionManager +
                ", remoteNetworkServicesRegisteredList=" + remoteNetworkServicesRegisteredList +
                ", communicationNetworkServiceDeveloperDatabaseFactory=" + communicationNetworkServiceDeveloperDatabaseFactory +
                '}';
    }

    /**
     * Represent the register
     */
    private boolean register;

    /**
     * Represent the name
     */
    private String name;

    /**
     * Represent the alias
     */
    private String alias;

    /**
     * Represent the extraData
     */
    private String extraData;

    /**
     * Represent the dataBase
     */
    private Database dataBase;

    /**
     * Represent the identity
     */
    private ECCKeyPair identity;

    /**
     * Represent the networkServiceType
     */
    private NetworkServiceType networkServiceType;

    /**
     * Represent the platformComponentProfile
     */
    private PlatformComponentProfile platformComponentProfile;

    List<FermatEventListener> listenersAdded = new ArrayList<>();
    /**
     * Represent the ChatMetaDataDao
     */
    private ChatMetaDataDao chatMetaDataDao;

    public ChatMetaDataDao getChatMetaDataDao() {
        return chatMetaDataDao;
    }

    /**
     * Represent the platformComponentType
     */
    private PlatformComponentType platformComponentType;

    /**
     * Represent the EVENT_SOURCE
     */
    public final static EventSource EVENT_SOURCE = EventSource.NETWORK_SERVICE_CHAT;

    public ErrorManager getErrorManager() {
        return errorManager;
    }

    /**
     * Represent the communicationNetworkServiceConnectionManager

     */
    private CommunicationNetworkServiceConnectionManager communicationNetworkServiceConnectionManager;


    /**
     *  Represent the remoteNetworkServicesRegisteredList
     */
    private List<PlatformComponentProfile> remoteNetworkServicesRegisteredList;

    /**
     * Represent the newLoggingLevel
     */
    static Map<String, LogLevel> newLoggingLevel = new HashMap<>();
    /**
     *   Represent the communicationNetworkServiceDeveloperDatabaseFactory
     */
    private NetworkServiceChatNetworkServiceDeveloperDatabaseFactory communicationNetworkServiceDeveloperDatabaseFactory;


    /**
     * This method validate is all required resource are injected into
     * the plugin root by the platform
     *
     * @throws CantStartPluginException
     */
    private void validateInjectedResources() throws CantStartPluginException {

         /*
         * If all resources are inject
         */
        if (wsCommunicationsCloudClientManager == null ||
                pluginDatabaseSystem  == null ||
                errorManager      == null ||
                eventManager  == null) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("wsCommunicationsCloudClientManager: " + wsCommunicationsCloudClientManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("pluginDatabaseSystem: " + pluginDatabaseSystem);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("errorManager: " + errorManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("eventManager: " + eventManager);

            String context = contextBuffer.toString();
            String possibleCause = "No all required resource are injected";
            CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, null, context, possibleCause);

            errorManager.reportUnexpectedPluginException(Plugins.CHAT_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);
            throw pluginStartException;


        }

    }
    @Override
    public String getIdentityPublicKey() {
        return this.identity.getPublicKey();
    }

    @Override
    public String getAlias() {
        return this.alias;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getExtraData() {
        return this.extraData;
    }

    @Override
    public PlatformComponentProfile getPlatformComponentProfilePluginRoot() {
        return platformComponentProfile;
    }

    @Override
    public PlatformComponentType getPlatformComponentType() {
        return platformComponentType;
    }

    @Override
    public NetworkServiceType getNetworkServiceType() {
        return networkServiceType;
    }

    @Override
    public List<PlatformComponentProfile> getRemoteNetworkServicesRegisteredList() {
        return remoteNetworkServicesRegisteredList;
    }


    @Override
    public void requestRemoteNetworkServicesRegisteredList(DiscoveryQueryParameters discoveryQueryParameters) {
        System.out.println("ChatPluginRoot - TemplateNetworkServiceRoot - requestRemoteNetworkServicesRegisteredList");

         /*
         * Request the list of component registers
         */
        try {

            wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().requestListComponentRegistered(platformComponentProfile, discoveryQueryParameters);

        } catch (CantRequestListException e) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("wsCommunicationsCloudClientManager: " + wsCommunicationsCloudClientManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("pluginDatabaseSystem: " + pluginDatabaseSystem);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("errorManager: " + errorManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("eventManager: " + eventManager);

            String context = contextBuffer.toString();
            String possibleCause = "Plugin was not registered";

            FermatException ex = new FermatException(FermatException.DEFAULT_MESSAGE, e,"","I can't List the resquest");
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, ex);

        }catch (Exception e) {
            FermatException ex = new FermatException(FermatException.DEFAULT_MESSAGE, e,"","I can't List the resquest");
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, ex);

        }

    }
    /**
     * (non-javadoc)
     * @see NetworkService#getNetworkServiceConnectionManager()
     */
    @Override
    public NetworkServiceConnectionManager getNetworkServiceConnectionManager() {
        return communicationNetworkServiceConnectionManager;
    }

    @Override
    public DiscoveryQueryParameters constructDiscoveryQueryParamsFactory(PlatformComponentType platformComponentType, NetworkServiceType networkServiceType, String alias, String identityPublicKey, Location location, Double distance, String name, String extraData, Integer firstRecord, Integer numRegister, PlatformComponentType fromOtherPlatformComponentType, NetworkServiceType fromOtherNetworkServiceType) {
        System.out.println("ChatPluginRoot - constructDiscoveryQueryParamsFactory");
        return wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().constructDiscoveryQueryParamsFactory(platformComponentType, networkServiceType, alias, identityPublicKey, location, distance, name, extraData, firstRecord, numRegister, fromOtherPlatformComponentType, fromOtherNetworkServiceType);
    }

    @Override
    public void handleCompleteComponentRegistrationNotificationEvent(PlatformComponentProfile platformComponentProfileRegistered) {

        toString();
        System.out.println("ChatPLuginRoot - CommunicationNetworkServiceConnectionManager - Starting method handleCompleteComponentRegistrationNotificationEvent");

        /*
         * If the component registered have my profile and my identity public key
         */
        if (platformComponentProfileRegistered.getPlatformComponentType() == PlatformComponentType.NETWORK_SERVICE &&
                platformComponentProfileRegistered.getNetworkServiceType() == NetworkServiceType.CHAT &&
                platformComponentProfileRegistered.getIdentityPublicKey().equals(identity.getPublicKey())) {

            /*
             * Mark as register
             */
            this.register = Boolean.TRUE;
            System.out.print("-----------------------\n" +
                    "CHAT NETWORK SERVICE REGISTERED  -----------------------\n" +
                    "-----------------------\n TO: " + getName());

            /*-------------------------------------------------------------------------------------------------
             * This is for test and example of how to use
             * Construct the filter
             */
            DiscoveryQueryParameters discoveryQueryParameters = wsCommunicationsCloudClientManager.
                    getCommunicationsCloudClientConnection().
                    constructDiscoveryQueryParamsFactory(PlatformComponentType.NETWORK_SERVICE, //applicant = who made the request
                            NetworkServiceType.CHAT,
                            this.getAlias(),                     // alias
                            identity.getPublicKey(),                     // identityPublicKey
                            null,                     // location
                            null,                     // distance
                            this.getName(),                     // name
                            null,                     // extraData
                            null,                     // offset
                            null,                     // max
                            null,                     // fromOtherPlatformComponentType, when use this filter apply the identityPublicKey
                            null);                    // fromOtherNetworkServiceType,    when use this filter apply the identityPublicKey

            /*
             * Request the list of component registers
             */
            requestRemoteNetworkServicesRegisteredList(discoveryQueryParameters);

        }
    }

    @Override
    public void handleFailureComponentRegistrationNotificationEvent(PlatformComponentProfile networkServiceApplicant, PlatformComponentProfile remoteNetworkService) {

    }

    @Override
    public void handleCompleteRequestListComponentRegisteredNotificationEvent(List<PlatformComponentProfile> platformComponentProfileRegisteredList) {
        System.out.println(" ChatPluginRoot - Starting method handleCompleteComponentRegistrationNotificationEvent");

         /*
         * save into the cache
         */
        remoteNetworkServicesRegisteredList = platformComponentProfileRegisteredList;

        System.out.println(" ChatPluginRoot - remoteNetworkServicesRegisteredList.size() " + remoteNetworkServicesRegisteredList.size());
    }

    @Override
    public void handleCompleteComponentConnectionRequestNotificationEvent(PlatformComponentProfile applicantComponentProfile, PlatformComponentProfile remoteComponentProfile) {

        System.out.println(" ChatPluginRoot - Starting method handleCompleteComponentConnectionRequestNotificationEvent");

        /*
         * Tell the manager to handler the new connection stablished
         */
        initializeCommunicationNetworkServiceConnectionManager();
        communicationNetworkServiceConnectionManager.handleEstablishedRequestedNetworkServiceConnection(remoteComponentProfile);
    }

    @Override
    public void handleClientConnectionCloseNotificationEvent(FermatEvent fermatEvent) {
        if (fermatEvent instanceof ClientConnectionCloseNotificationEvent) {
            this.register = false;

            if (communicationNetworkServiceConnectionManager != null)
                communicationNetworkServiceConnectionManager.closeAllConnection();
        }
    }

    @Override
    public void handleVpnConnectionCloseNotificationEvent(FermatEvent fermatEvent) {

        if (fermatEvent instanceof VPNConnectionCloseNotificationEvent) {

            VPNConnectionCloseNotificationEvent vpnConnectionCloseNotificationEvent = (VPNConnectionCloseNotificationEvent) fermatEvent;

            if (vpnConnectionCloseNotificationEvent.getNetworkServiceApplicant() == getNetworkServiceType()) {

                if (communicationNetworkServiceConnectionManager != null)
                    communicationNetworkServiceConnectionManager.closeConnection(vpnConnectionCloseNotificationEvent.getRemoteParticipant().getIdentityPublicKey());

            }

        }
    }

    @Override
    public boolean isRegister() {
        return this.register;
    }

    @Override
    public void setPlatformComponentProfilePluginRoot(PlatformComponentProfile platformComponentProfile) {
        this.platformComponentProfile = platformComponentProfile;
    }

    /**
     * This method initialize the communicationNetworkServiceConnectionManager.
     * IMPORTANT: Call this method only in the CommunicationRegistrationProcessNetworkServiceAgent, when execute the registration process
     * because at this moment, is create the platformComponentProfile for this component
     */
    @Override
    public void initializeCommunicationNetworkServiceConnectionManager() {
        try{

            this.communicationNetworkServiceConnectionManager = new CommunicationNetworkServiceConnectionManager(platformComponentProfile, identity, wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection(), dataBase, errorManager, eventManager);

        }catch(Exception ex){
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantInitializeCommunicationNetworkServiceConnectionManagerException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("wsCommunicationsCloudClientManager: " + wsCommunicationsCloudClientManager);
            contextBuffer.append(CantInitializeCommunicationNetworkServiceConnectionManagerException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("pluginDatabaseSystem: " + pluginDatabaseSystem);
            contextBuffer.append(CantInitializeCommunicationNetworkServiceConnectionManagerException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("errorManager: " + errorManager);
            contextBuffer.append(CantInitializeCommunicationNetworkServiceConnectionManagerException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("eventManager: " + eventManager);

            String context = contextBuffer.toString();
            String possibleCause = "BAD ARGUMENTS";

            CantInitializeCommunicationNetworkServiceConnectionManagerException communicationNetworkServiceConnectionManagerException = new CantInitializeCommunicationNetworkServiceConnectionManagerException(CantInitializeCommunicationNetworkServiceConnectionManagerException.DEFAULT_MESSAGE, ex, context, possibleCause);
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, communicationNetworkServiceConnectionManagerException);

        }
    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        return communicationNetworkServiceDeveloperDatabaseFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return communicationNetworkServiceDeveloperDatabaseFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        return communicationNetworkServiceDeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
    }

    @Override
    public List<String> getClassesFullPath() {
        return null;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {

    }

    public void start() throws CantStartPluginException {
        logManager.log(ChatPluginRoot.getLogLevelByClass(this.getClass().getName()), "ChatNetworkServicePluginRoot - Starting", "ChatNetworkServicePluginRoot - Starting", "ChatNetworkServicePluginRoot - Starting");

         /*
         * Validate required resources
         */
        validateInjectedResources();
        System.out.println("ChatPluginRoot - Injected resources");

        try {

            /*
             * Create a new key pair for this execution
             */
            identity = new ECCKeyPair();
            System.out.println("ChatPluginRoot - identity:"+identity.toString());

            /*
             * Initialize the data base
             */
            initializeDb();

            System.out.println("ChatPluginRoot - DAtabase Ready!");

            /*
            *  initializeListener
             */
            initializeListener();
            System.out.println("ChatPluginRoot - Events handlers ready!");

            /*
             *  Initialize initializeCommunicationNetworkServiceConnectionManager
             */
            initializeCommunicationNetworkServiceConnectionManager();

            /*
             * Initialize Developer Database Factory
             */
            System.out.println("ChatPluginRoot - PluginId:"+pluginId.toString());
            System.out.println("ChatPluginRoot - PluginId:"+pluginDatabaseSystem.toString());

            communicationNetworkServiceDeveloperDatabaseFactory = new NetworkServiceChatNetworkServiceDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
            communicationNetworkServiceDeveloperDatabaseFactory.initializeDatabase();
            System.out.println("ChatPluginRoot - communicationNetworkServiceDeveloperDatabaseFactory ready!");


             /*
             * Verify if the communication cloud client is active
             */
            if (!wsCommunicationsCloudClientManager.isDisable()) {

                /*
                 * Initialize the agent and start
                 */
                CommunicationRegistrationProcessNetworkServiceAgent communicationRegistrationProcessNetworkServiceAgent = new CommunicationRegistrationProcessNetworkServiceAgent(this, wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection());
                communicationRegistrationProcessNetworkServiceAgent.start();
            }

            /*
             * Its all ok, set the new status
            */
            this.serviceStatus = ServiceStatus.STARTED;
            System.out.println("ChatPluginRoot - Is ready to go");

            System.out.println("ChatPLuginRoot - Sending Chat TEST");

            MockChat mockChat = new MockChat();

            mockChat.setIdChat(UUID.randomUUID());
            mockChat.setIdObjecto(UUID.randomUUID());
            mockChat.setLocalActorType("CHAT");
            mockChat.setLocalActorPubKey("0470B2649FE0625CE409E7D83A3C9676196A07E1E77FC393AA1C94126354830761FF05655C92FB5E31BA8F41A22BEBE626470C09607FDCCBAD5BB4545BCBECBAD4");
            mockChat.setRemoteActorType("CRYPTO_BROKER");
            mockChat.setRemoteActorPubKey("04F32B7FBABCA4DDD2854FA4879DB73C11C0F15A68C29C1E2DF8C10B5EEE9521D80404C98C453D8119DF27B7D5E6304D2FCB92EC072C904D59433A206729CFE836");
            mockChat.setChatName("Chat de prueba");
            mockChat.setChatMessageStatus(ChatMessageStatus.CREATED_CHAT);
            mockChat.setDate(new Timestamp(145517));
            mockChat.setIdMessage(UUID.randomUUID());
            mockChat.setMessage("This is a test message to test funcionality");
            mockChat.setDistributionStatus(DistributionStatus.DELIVERING);

            sendChatMetadata(mockChat.getLocalActorPubKey(), mockChat.getRemoteActorPubKey(), mockChat);
            raiseTestEvent();


        }catch (CantInitializeChatNetworkServiceDatabaseException exception) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("Database Name: " + NetworkServiceChatNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The Template Database triggered an unexpected problem that wasn't able to solve by itself";
            CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception, context, possibleCause);

            errorManager.reportUnexpectedPluginException(Plugins.CHAT_NETWORK_SERVICE,UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);
            throw pluginStartException;
        }catch (Exception exception){
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Plugin ID: " + pluginId);
            String context = contextBuffer.toString();
            String possibleCause = "The plugin was unable to start\n"+exception.getMessage();
            throw new CantStartPluginException(exception.getMessage(), FermatException.wrapException(exception), context, possibleCause);
        }

    }

    private void raiseTestEvent(){
        FermatEvent fermatEvent = eventManager.getNewEvent(EventType.OUTGOING_CHAT);
        OutgoingChat outgoingChat = (OutgoingChat) fermatEvent;
        outgoingChat.setSource(EventSource.NETWORK_SERVICE_CHAT);
        eventManager.raiseEvent(outgoingChat);

    }
    /**
     * Static method to get the logging level from any class under root.
     * @param className
     * @return
     */
    public static LogLevel getLogLevelByClass(String className){
        try{
            /**
             * sometimes the classname may be passed dinamically with an $moretext
             * I need to ignore whats after this.
             */
            String[] correctedClass = className.split((Pattern.quote("$")));
            return ChatPluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e){
            /**
             * If I couldn't get the correct loggin level, then I will set it to minimal.
             */
            return DEFAULT_LOG_LEVEL;
        }
    }
    /**
     * This method initialize the database
     *
     * @throws CantInitializeChatNetworkServiceDatabaseException
     */
    private void initializeDb() throws CantInitializeChatNetworkServiceDatabaseException {

        try {
            /*
             * Open new database connection
             */
            this.dataBase = this.pluginDatabaseSystem.openDatabase(pluginId, NetworkServiceChatNetworkServiceDatabaseConstants.DATA_BASE_NAME);
            this.chatMetaDataDao = new ChatMetaDataDao(dataBase);


        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

            /*
             * The database exists but cannot be open. I can not handle this situation.
             */
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeChatNetworkServiceDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

        } catch (DatabaseNotFoundException e) {

            /*
             * The database no exist may be the first time the plugin is running on this device,
             * We need to create the new database
             */
            NetworkServiceChatNetworkServiceDatabaseFactory communicationNetworkServiceDatabaseFactory = new NetworkServiceChatNetworkServiceDatabaseFactory(pluginDatabaseSystem);

            try {

                /*
                 * We create the new database
                 */
                this.dataBase = communicationNetworkServiceDatabaseFactory.createDatabase(pluginId, NetworkServiceChatNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            } catch (CantCreateDatabaseException cantOpenDatabaseException) {

                /*
                 * The database cannot be created. I can not handle this situation.
                 */
                errorManager.reportUnexpectedPluginException(Plugins.CHAT_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantOpenDatabaseException);
                throw new CantInitializeChatNetworkServiceDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

            }
        } catch (Exception exception){
            throw new CantInitializeChatNetworkServiceDatabaseException(CantInitializeChatNetworkServiceDatabaseException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }

    }

    @Override
    public void sendChatMetadata(String localActorPubKey, String remoteActorPubKey, ChatMetada chatMetada) throws CantSendChatMessageMetadataException {
        try {
            System.out.println("ChatPLuginRoot - Starting method sendChatMetadata");

            /*
            * Initialize as null so if the instance is one I can't handle then it will break the app fast instead of causing weird bugs.
            */

            System.out.println("ChatPLuginRoot - Actor Sender: PK : " + localActorPubKey+ " - Type: " + chatMetada.getLocalActorType());
            System.out.println("ChatPLuginRoot - Actor Receiver: PK : " + remoteActorPubKey+ " - Type: " +   chatMetada.getRemoteActorType());

            /*
             * ask for a previous connection
             */
            CommunicationNetworkServiceLocal communicationNetworkServiceLocal = communicationNetworkServiceConnectionManager.getNetworkServiceLocalInstance(remoteActorPubKey);
            System.out.println("ChatPluginRoot - communicationNetworkServiceLocal: "+communicationNetworkServiceLocal);

            String msjContent = EncodeMsjContent.encodeMSjContentChatMetadataTransmit(chatMetada, PlatformComponentType.getByCode(chatMetada.getLocalActorType()), PlatformComponentType.getByCode(chatMetada.getRemoteActorType()));
            System.out.println("ChatPluginRoot - Message encoded:\n"+msjContent);
            /*
             * Construct the message content in json format
             */
            ChatMetadaTransactionRecord chatMetadaTransactionRecord = new ChatMetadaTransactionRecord();
            chatMetadaTransactionRecord.setIdChat(chatMetada.getIdChat());
            chatMetadaTransactionRecord.setIdObjecto(chatMetada.getIdObjecto());
            chatMetadaTransactionRecord.setLocalActorType(chatMetada.getLocalActorType());
            chatMetadaTransactionRecord.setLocalActorPubKey(chatMetada.getLocalActorPubKey());
            chatMetadaTransactionRecord.setRemoteActorType(chatMetada.getRemoteActorType());
            chatMetadaTransactionRecord.setRemoteActorPubKey(chatMetada.getRemoteActorPubKey());
            chatMetadaTransactionRecord.setChatName(chatMetada.getChatName());
            chatMetadaTransactionRecord.setChatMessageStatus(chatMetada.getChatMessageStatus());
            chatMetadaTransactionRecord.setDate(chatMetada.getDate());
            chatMetadaTransactionRecord.setIdMessage(chatMetada.getIdMessage());
            chatMetadaTransactionRecord.setMessage(chatMetada.getMessage());
            chatMetadaTransactionRecord.setDistributionStatus(DistributionStatus.DELIVERING);

            System.out.println("ChatPLuginRoot - Chat transaction: " + chatMetadaTransactionRecord);

            /*
             * Save into data base
             */
            getChatMetaDataDao().create(chatMetadaTransactionRecord);

            /*
             * If not null
             */
            System.out.println("ChatPLuginRoot - Sending message.....");
            if (communicationNetworkServiceLocal != null) {

                //Send the message
                communicationNetworkServiceLocal.sendMessage(chatMetadaTransactionRecord.getLocalActorPubKey(), chatMetadaTransactionRecord.getLocalActorPubKey(), msjContent);

            } else {

                /*
                 * Created the message
                 */
                FermatMessage fermatMessage = FermatMessageCommunicationFactory.constructFermatMessage(chatMetadaTransactionRecord.getLocalActorPubKey(),//Sender
                        chatMetadaTransactionRecord.getRemoteActorPubKey(), //Receiver
                        msjContent, //Message Content
                        FermatMessageContentType.TEXT);//Type
                /*
                 * Configure the correct status
                 */
                ((FermatMessageCommunication) fermatMessage).setFermatMessagesStatus(FermatMessagesStatus.PENDING_TO_SEND);

                /*
                 * Save to the data base table
                 */
                OutgoingMessageDao outgoingMessageDao = communicationNetworkServiceConnectionManager.getOutgoingMessageDao();
                outgoingMessageDao.create(fermatMessage);

                /*
                 * Create the sender basic profile
                 */
                PlatformComponentProfile sender = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().constructBasicPlatformComponentProfileFactory(chatMetadaTransactionRecord.getLocalActorPubKey(), NetworkServiceType.CHAT, PlatformComponentType.getByCode(chatMetadaTransactionRecord.getLocalActorType()));

                /*
                 * Create the receiver basic profile
                 */
                PlatformComponentProfile receiver = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().constructBasicPlatformComponentProfileFactory(chatMetadaTransactionRecord.getRemoteActorPubKey(), NetworkServiceType.CHAT, PlatformComponentType.getByCode(chatMetadaTransactionRecord.getRemoteActorType()));

                /*
                 * Ask the client to connect
                 */
                communicationNetworkServiceConnectionManager.connectTo(sender, platformComponentProfile, receiver);
            }
            System.out.println("ChatPluginRoot - Message sent.");
        } catch (Exception e) {

            StringBuilder contextBuffer = new StringBuilder();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("wsCommunicationsCloudClientManager: " + wsCommunicationsCloudClientManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("pluginDatabaseSystem: " + pluginDatabaseSystem);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("errorManager: " + errorManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("eventManager: " + eventManager);

            String context = contextBuffer.toString();
            String possibleCause = "Plugin was not registered";

            CantSendChatMessageMetadataException pluginStartException = new CantSendChatMessageMetadataException(e.getMessage(), e, context, possibleCause);

            errorManager.reportUnexpectedPluginException(Plugins.CHAT_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, pluginStartException);

            throw pluginStartException;
        }
    }

    @Override
    public void sendChatMessageNewStatusNotification(UUID localActorPubKey, PlatformComponentType senderType, UUID remoteActorPubKey, PlatformComponentType receiverType, DistributionStatus newDistributionStatus) throws CantSendChatMessageNewStatusNotificationException {

    }


    /**
     * Initialize the event listener and configure
     */
    private void initializeListener() {

         /*
         * Listen and handle Complete Component Registration Notification Event
         */

        System.out.println("ChatPLuginRooot: Listen and handle Complete Component Registration Notification Event");
        FermatEventListener fermatEventListener = eventManager.getNewListener(P2pEventType.COMPLETE_COMPONENT_REGISTRATION_NOTIFICATION);
        fermatEventListener.setEventHandler(new CompleteComponentRegistrationNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

         /*
         * Listen and handle Complete Request List Component Registered Notification Event
         */
        fermatEventListener = eventManager.getNewListener(P2pEventType.COMPLETE_REQUEST_LIST_COMPONENT_REGISTERED_NOTIFICATION);
        fermatEventListener.setEventHandler(new CompleteRequestListComponentRegisteredNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        /*
         * Listen and handle Complete Request List Component Registered Notification Event
         */
        fermatEventListener = eventManager.getNewListener(P2pEventType.COMPLETE_COMPONENT_CONNECTION_REQUEST_NOTIFICATION);
        fermatEventListener.setEventHandler(new CompleteComponentConnectionRequestNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        /*
         * Listen and handle new message sent
         */
        fermatEventListener = eventManager.getNewListener(P2pEventType.NEW_NETWORK_SERVICE_MESSAGE_SENT_NOTIFICATION);
        fermatEventListener.setEventHandler(new NewSentMessagesNotificationEventHandler());
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        /*
         * Listen and handle new message receive notification
         */
        fermatEventListener = eventManager.getNewListener(P2pEventType.NEW_NETWORK_SERVICE_MESSAGE_RECEIVE_NOTIFICATION);
        fermatEventListener.setEventHandler(new NewReceiveMessagesNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

             /*
         * Listen and handle VPN Connection Close Notification Event
         */
        fermatEventListener = eventManager.getNewListener(P2pEventType.VPN_CONNECTION_CLOSE);
        fermatEventListener.setEventHandler(new VPNConnectionCloseNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

              /*
         * Listen and handle Client Connection Close Notification Event
         */
        fermatEventListener = eventManager.getNewListener(P2pEventType.CLIENT_CONNECTION_CLOSE);
        fermatEventListener.setEventHandler(new ClientConnectionCloseNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        /**
         *  failure connection
         */

        fermatEventListener = eventManager.getNewListener(P2pEventType.FAILURE_COMPONENT_CONNECTION_REQUEST_NOTIFICATION);
        fermatEventListener.setEventHandler(new FailureComponentConnectionRequestNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

    }

    @Override
    public void handleClientConnectionLooseNotificationEvent(FermatEvent fermatEvent) {
        // TODO COMPLETE -.
    }

    @Override
    public void handleClientSuccessfullReconnectNotificationEvent(FermatEvent fermatEvent) {
        // TODO COMPLETE -.
    }


    @Override
    public void confirmReception(UUID transactionID) throws CantConfirmTransactionException {

    }

    @Override
    public List<Transaction<ChatMetada>> getPendingTransactions(Specialist specialist) throws CantDeliverPendingTransactionsException {
        return null;
    }


}
