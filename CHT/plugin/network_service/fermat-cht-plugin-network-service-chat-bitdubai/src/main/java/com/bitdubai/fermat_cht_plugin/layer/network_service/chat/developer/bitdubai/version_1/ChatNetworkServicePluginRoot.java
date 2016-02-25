package com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.DiscoveryQueryParameters;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.crypto.util.CryptoHasher;
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
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkServiceConnectionManager;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Action;
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
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_cht_api.all_definition.enums.MessageStatus;
import com.bitdubai.fermat_cht_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.ChatMessageStatus;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.ChatMessageTransactionType;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.DistributionStatus;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.events.OutgoingChat;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.exceptions.CantInitializeCommunicationNetworkServiceConnectionManagerException;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.exceptions.CantSendChatMessageMetadataException;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.exceptions.CantSendChatMessageNewStatusNotificationException;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces.ChatMetadata;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces.NetworkServiceChatManager;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.communications.CommunicationNetworkServiceConnectionManager;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.communications.CommunicationNetworkServiceLocal;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.communications.CommunicationRegistrationProcessNetworkServiceAgent;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.database.communications.ChatMetaDataDao;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.database.communications.CommunicationChatNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.database.communications.CommunicationChatNetworkServiceDatabaseFactory;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.database.communications.CommunicationChatNetworkServiceDeveloperDatabaseFactory;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.database.communications.OutgoingMessageDao;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.event_handlers.ClientConnectionCloseNotificationEventHandler;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.event_handlers.ClientConnectionLooseNotificationEventHandler;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.event_handlers.ClientSuccessfullReconnectNotificationEventHandler;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.event_handlers.CompleteComponentConnectionRequestNotificationEventHandler;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.event_handlers.CompleteComponentRegistrationNotificationEventHandler;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.event_handlers.CompleteRequestListComponentRegisteredNotificationEventHandler;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.event_handlers.NewReceiveMessagesNotificationEventHandler;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.event_handlers.NewSentMessagesNotificationEventHandler;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.event_handlers.VPNConnectionCloseNotificationEventHandler;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.exceptions.CantInitializeChatNetworkServiceDatabaseException;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.structure.ChatMetadataRecord;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.structure.ChatNetworkServiceAgent;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.structure.ChatTransmissionJsonAttNames;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.structure.EncodeMsjContent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.abstract_classes.AbstractNetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.interfaces.NetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatMessageCommunication;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatMessageCommunicationFactory;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.ClientConnectionCloseNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.VPNConnectionCloseNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessageContentType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessagesStatus;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantRegisterComponentException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantRequestListException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Created by Gabriel Araujo on 05/01/16.
 */
public class ChatNetworkServicePluginRoot extends AbstractNetworkService implements
        LogManagerForDevelopers,
        DatabaseManagerForDevelopers,
        NetworkServiceChatManager {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.LOG_MANAGER)
    private LogManager logManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededPluginReference(platform = Platforms.COMMUNICATION_PLATFORM, layer = Layers.COMMUNICATION, plugin = Plugins.WS_CLOUD_CLIENT)
    private WsCommunicationsCloudClientManager wsCommunicationsCloudClientManager;


    private List<PlatformComponentProfile> registeredComponentList;


    public EventManager getEventManager() {
        return eventManager;
    }

    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    public ChatNetworkServicePluginRoot() {
        super(new PluginVersionReference(new Version()),
                PlatformComponentType.NETWORK_SERVICE,
                NetworkServiceType.CHAT,
                "Network Service Chat",
                "NetworkServiceChat",
                null,
                EventSource.NETWORK_SERVICE_CHAT);
        this.listenersAdded=new ArrayList<>();
    }

    @Override
    public String toString() {
        return "ChatNetworkServicePluginRoot{" +
                "errorManager=" + errorManager +
                ", eventManager=" + eventManager +
                ", logManager=" + logManager +
                ", pluginDatabaseSystem=" + pluginDatabaseSystem +
                ", wsCommunicationsCloudClientManager=" + wsCommunicationsCloudClientManager +
                ", register=" + register +
                ", dataBase=" + dataBase +
                ", identity=" + identity +
                ", platformComponentProfile=" + platformComponentProfile +
                ", listenersAdded=" + listenersAdded +
                ", chatMetaDataDao=" + chatMetaDataDao +
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
     * Represent the dataBase
     */
    private Database dataBase;

    /**
     * Represent the identity
     */
    private ECCKeyPair identity;

    /**
     * Represent the platformComponentProfile
     */
    private PlatformComponentProfile platformComponentProfile;

    List<FermatEventListener> listenersAdded = new ArrayList<>();
    /**
     * Represent the OutgoinChatMetaDataDao
     */
    private ChatMetaDataDao chatMetaDataDao;

    public ChatMetaDataDao getChatMetaDataDao() {
        return chatMetaDataDao;
    }

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
     * Represents chatNetworkServiceAgent
     */
    private ChatNetworkServiceAgent chatNetworkServiceAgent;

    /**
     * Represent the remoteNetworkServicesRegisteredList
     */
    private List<PlatformComponentProfile> remoteNetworkServicesRegisteredList;

    /**
     * Represent the newLoggingLevel
     */
    static Map<String, LogLevel> newLoggingLevel = new HashMap<>();
    /**
     * Represent the communicationNetworkServiceDeveloperDatabaseFactory
     */
    private CommunicationChatNetworkServiceDeveloperDatabaseFactory communicationNetworkServiceDeveloperDatabaseFactory;

    private CommunicationRegistrationProcessNetworkServiceAgent communicationRegistrationProcessNetworkServiceAgent;

    private NewReceiveMessagesNotificationEventHandler newReceiveMessagesNotificationEventHandler;

    private NewSentMessagesNotificationEventHandler newSentMessagesNotificationEventHandler;
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
                pluginDatabaseSystem == null ||
                errorManager == null ||
                eventManager == null) {

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

            reportUnexpectedException(pluginStartException);
            reportUnexpectedException(pluginStartException);
            throw pluginStartException;


        }

    }

    @Override
    public String getIdentityPublicKey() {
        return this.identity.getPublicKey();
    }

    /**
     * Represents the NewReceiveMessagesNotificationEventHandler
     * @return
     */
    public NewReceiveMessagesNotificationEventHandler getNewReceiveMessagesNotificationEventHandler() {
        return newReceiveMessagesNotificationEventHandler;
    }

    public void setNewReceiveMessagesNotificationEventHandler(NewReceiveMessagesNotificationEventHandler newReceiveMessagesNotificationEventHandler) {
        this.newReceiveMessagesNotificationEventHandler = newReceiveMessagesNotificationEventHandler;
    }

    /**
     * Represents the NewSentMessagesNotificationEventHandler
     * @return
     */
    public NewSentMessagesNotificationEventHandler getNewSentMessagesNotificationEventHandler() {
        return newSentMessagesNotificationEventHandler;
    }

    public void setNewSentMessagesNotificationEventHandler(NewSentMessagesNotificationEventHandler newSentMessagesNotificationEventHandler) {
        this.newSentMessagesNotificationEventHandler = newSentMessagesNotificationEventHandler;
    }

    @Override
    public void handleNewMessages(FermatMessage incomingMessage) {
        JsonObject jsonMsjContent = getNewReceiveMessagesNotificationEventHandler().getParser().parse(incomingMessage.getContent()).getAsJsonObject();
        ChatMessageTransactionType chatMessageTransactionType = getNewReceiveMessagesNotificationEventHandler().getGson().fromJson(jsonMsjContent.get(ChatTransmissionJsonAttNames.MSJ_CONTENT_TYPE), ChatMessageTransactionType.class);
        if (getNewReceiveMessagesNotificationEventHandler().getMessagesProcessorsRegistered().containsKey(chatMessageTransactionType)) {
            getNewReceiveMessagesNotificationEventHandler().getMessagesProcessorsRegistered().get(chatMessageTransactionType).processingMessage(incomingMessage, jsonMsjContent);
        }else{
            System.out.println("ChatNetworkServicePluginRoot - CompleteComponentConnectionRequestNotificationEventHandler - message type no supported = "+chatMessageTransactionType);
        }
    }

    @Override
    public void handleNewSentMessageNotificationEvent(FermatMessage message) {
     //   System.out.println("ChatNetworkServicePluginRoot - NOTIFICACION EVENTO MENSAJE ENVIADO!!!!");
        JsonObject jsonMsjContent = getNewSentMessagesNotificationEventHandler().getParser().parse(message.getContent()).getAsJsonObject();
        UUID chatId = getNewSentMessagesNotificationEventHandler().getGson().fromJson(jsonMsjContent.get(ChatTransmissionJsonAttNames.ID_CHAT), UUID.class);
     //   System.out.println("ChatNetworkServicePluginRoot - ChatId"+chatId.toString());
        OutgoingChat event = (OutgoingChat) this.getEventManager().getNewEvent(EventType.OUTGOING_CHAT);
        event.setChatId(chatId);
        event.setSource(ChatNetworkServicePluginRoot.EVENT_SOURCE);
        this.getEventManager().raiseEvent(event);
       // System.out.println("ChatNetworkServicePluginRoot - OUTGOING_CHAT EVENT FIRED!:"+event);
    }


    @Override
    public List<PlatformComponentProfile> getRemoteNetworkServicesRegisteredList() {
        return remoteNetworkServicesRegisteredList;
    }


    @Override
    public void requestRemoteNetworkServicesRegisteredList(DiscoveryQueryParameters discoveryQueryParameters) {
      //  System.out.println("ChatNetworkServicePluginRoot - requestRemoteNetworkServicesRegisteredList");

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

            FermatException ex = new FermatException(FermatException.DEFAULT_MESSAGE, e, "", "I can't List the resquest");
            reportUnexpectedException(ex);

        } catch (Exception e) {
            FermatException ex = new FermatException(FermatException.DEFAULT_MESSAGE, e, "", "I can't List the resquest");
            reportUnexpectedException(ex);

        }

    }

    public void reportUnexpectedException(FermatException e){
        getErrorManager().reportUnexpectedPluginException(Plugins.CHAT_NETWORK_SERVICE,UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,e);
    }

    @Override
    public List<String> getRegisteredPubliKey() throws CantRequestListException {

        List<String> publicKeys = new ArrayList<>();
        DiscoveryQueryParameters discoveryQueryParameters = wsCommunicationsCloudClientManager.
                getCommunicationsCloudClientConnection().
                constructDiscoveryQueryParamsFactory(PlatformComponentType.NETWORK_SERVICE, //applicant = who made the request
                        NetworkServiceType.CHAT,
                        null,                     // alias
                        null,                    // identityPublicKey
                        null,                     // location
                        null,                     // distance
                        null,                     // name
                        null,                     // extraData.
                        null,                     // offset
                        null,                     // max
                        null,                     // fromOtherPlatformComponentType, when use this filter apply the identityPublicKey
                        null);                    // fromOtherNetworkServiceType,    when use this filter apply the identityPublicKey

        List<PlatformComponentProfile> registedPlatform = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().requestListComponentRegistered(discoveryQueryParameters);
     //   System.out.println("-------------\nNetworkServiceChatNetworkServicePluginRoot: registedPlatform" + registedPlatform.toString());
        for (PlatformComponentProfile platformComponentProfile : registedPlatform) {
            System.out.println("Registered componet:"+platformComponentProfile.getNetworkServiceType() +" - " +platformComponentProfile.getName()+"- "+platformComponentProfile.getIdentityPublicKey());
            publicKeys.add(platformComponentProfile.getIdentityPublicKey());
        }
        return publicKeys;
    }

    /**
     * (non-javadoc)
     *
     * @see NetworkService#getNetworkServiceConnectionManager()
     */
    @Override
    public NetworkServiceConnectionManager getNetworkServiceConnectionManager() {
        return communicationNetworkServiceConnectionManager;
    }

    @Override
    public DiscoveryQueryParameters constructDiscoveryQueryParamsFactory(PlatformComponentType platformComponentType, NetworkServiceType networkServiceType, String alias, String identityPublicKey, Location location, Double distance, String name, String extraData, Integer firstRecord, Integer numRegister, PlatformComponentType fromOtherPlatformComponentType, NetworkServiceType fromOtherNetworkServiceType) {
      //  System.out.println("ChatNetworkServicePluginRoot - constructDiscoveryQueryParamsFactory");
        return wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().constructDiscoveryQueryParamsFactory(platformComponentType, networkServiceType, alias, identityPublicKey, location, distance, name, extraData, firstRecord, numRegister, fromOtherPlatformComponentType, fromOtherNetworkServiceType);
    }

    @Override
    public void handleCompleteComponentRegistrationNotificationEvent(PlatformComponentProfile platformComponentProfileRegistered) {
        System.out.println("ChatPLuginRoot - CommunicationNetworkServiceConnectionManager - Starting method handleCompleteComponentRegistrationNotificationEvent");


        if (platformComponentProfileRegistered.getPlatformComponentType() == PlatformComponentType.COMMUNICATION_CLOUD_CLIENT && this.register) {

            if (communicationRegistrationProcessNetworkServiceAgent.isAlive()) {
                communicationRegistrationProcessNetworkServiceAgent.interrupt();
                communicationRegistrationProcessNetworkServiceAgent = null;
            }


            /*
             * Construct my profile and register me
             */
            PlatformComponentProfile platformComponentProfileToReconnect = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().constructPlatformComponentProfileFactory(this.getIdentityPublicKey(),
                    this.getAlias().toLowerCase(),
                    this.getName(),
                    this.getNetworkServiceType(),
                    this.getPlatformComponentType(),
                    this.getExtraData());

            try {
                    /*
                     * Register me
                     */
                wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().registerComponentForCommunication(this.getNetworkServiceType(), platformComponentProfileToReconnect);
                System.out.println("------------------CHATPLUGINROOT NETWORK SERVICE REGISTED------------------------------------");
            } catch (CantRegisterComponentException e) {
                FermatException ex = new FermatException(FermatException.DEFAULT_MESSAGE, e, "", "CAN'T REGISTER COMPONENT");
                reportUnexpectedException(ex);
            }

        }

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
                    "NETWORK SERVICE CHATPLUGINROOT REGISTERED  -----------------------\n" +
                    "-----------------------\n TO: " + getName());
            communicationRegistrationProcessNetworkServiceAgent.interrupt();
            communicationRegistrationProcessNetworkServiceAgent = null;


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
            chatNetworkServiceAgent = new ChatNetworkServiceAgent(
                    this,
                    getChatMetaDataDao(),
                    communicationNetworkServiceConnectionManager,
                    wsCommunicationsCloudClientManager,
                    platformComponentProfile,
                    errorManager,
                    new ArrayList<PlatformComponentProfile>(),
                    identity,
                    eventManager
            );

            chatNetworkServiceAgent.start();
        }

    }

    @Override
    public void handleFailureComponentRegistrationNotificationEvent(PlatformComponentProfile networkServiceApplicant, PlatformComponentProfile remoteNetworkService) {

    }

    @Override
    public void handleCompleteRequestListComponentRegisteredNotificationEvent(List<PlatformComponentProfile> platformComponentProfileRegisteredList) {
        System.out.println(" ChatNetworkServicePluginRoot - Starting method handleCompleteComponentRegistrationNotificationEvent");

         /*
         * save into the cache
         */
        remoteNetworkServicesRegisteredList = platformComponentProfileRegisteredList;

        System.out.println(" ChatNetworkServicePluginRoot - remoteNetworkServicesRegisteredList.size() " + remoteNetworkServicesRegisteredList.size());
    }

    @Override
    public void handleCompleteComponentConnectionRequestNotificationEvent(PlatformComponentProfile applicantComponentProfile, PlatformComponentProfile remoteComponentProfile) {

       // System.out.println(" ChatNetworkServicePluginRoot - Starting method handleCompleteComponentConnectionRequestNotificationEvent");

        /*
         * Tell the manager to handler the new connection stablished
         */

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
    public void setPlatformComponentProfilePluginRoot(PlatformComponentProfile platformComponentProfilePluginRoot) {
        this.platformComponentProfile = platformComponentProfilePluginRoot;
    }

    /**
     * This method initialize the communicationNetworkServiceConnectionManager.
     * IMPORTANT: Call this method only in the CommunicationRegistrationProcessNetworkServiceAgent, when execute the registration process
     * because at this moment, is create the platformComponentProfile for this component
     */
    @Override
    public void initializeCommunicationNetworkServiceConnectionManager() {
        try {

            this.communicationNetworkServiceConnectionManager = new CommunicationNetworkServiceConnectionManager(platformComponentProfile, identity, wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection(), dataBase, errorManager, eventManager);

        } catch (Exception ex) {
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
            reportUnexpectedException(communicationNetworkServiceConnectionManagerException);

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
        logManager.log(ChatNetworkServicePluginRoot.getLogLevelByClass(this.getClass().getName()), "ChatNetworkServicePluginRoot - Starting", "ChatNetworkServicePluginRoot - Starting", "ChatNetworkServicePluginRoot - Starting");

         /*
         * Validate required resources
         */
        validateInjectedResources();
        try {

            /*
             * Create a new key pair for this execution
             */
            //identity = new ECCKeyPair();
            initializeClientIdentity();
            /*
             * Initialize the data base
             */
            initializeDb();

            /*
            *  initializeListener
             */
            initializeListener();

            /*
             * Initialize Developer Database Factory
             */
            communicationNetworkServiceDeveloperDatabaseFactory = new CommunicationChatNetworkServiceDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId,getErrorManager());
            communicationNetworkServiceDeveloperDatabaseFactory.initializeDatabase();

             /*
             * Verify if the communication cloud client is active
             */
            if (!wsCommunicationsCloudClientManager.isDisable()) {

                /*
                 * Initialize the agent and start
                 */
                communicationRegistrationProcessNetworkServiceAgent = new CommunicationRegistrationProcessNetworkServiceAgent(this, wsCommunicationsCloudClientManager);
                communicationRegistrationProcessNetworkServiceAgent.start();
            }

            /*
             * Its all ok, set the new status
            */
            this.serviceStatus = ServiceStatus.STARTED;


            System.out.print("-----------------------\n ChatNetworkServicePluginRoot NetworkService: Successful start.\n-----------------------\n");
          //  System.out.println("Pk:"+getIdentityPublicKey());

        } catch (CantInitializeChatNetworkServiceDatabaseException exception) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("Database Name: " + CommunicationChatNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The Template Database triggered an unexpected problem that wasn't able to solve by itself";
            CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception, context, possibleCause);

            reportUnexpectedException(pluginStartException);
            throw pluginStartException;
        } catch (Exception exception) {
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Plugin ID: " + pluginId);
            String context = contextBuffer.toString();
            String possibleCause = "The plugin was unable to start";
            CantStartPluginException cantStartPluginException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, FermatException.wrapException(exception), context, possibleCause);
            reportUnexpectedException(cantStartPluginException);
            throw cantStartPluginException;
        }

    }

    private void initializeClientIdentity() throws CantStartPluginException {

        System.out.println("Calling the method - initializeClientIdentity() ");

        try {

            System.out.println("Loading clientIdentity");

             /*
              * Load the file with the clientIdentity
              */
            PluginTextFile pluginTextFile = pluginFileSystem.getTextFile(pluginId, "private", "clientIdentity", FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            String content = pluginTextFile.getContent();

            //System.out.println("content = "+content);

            identity = new ECCKeyPair(content);

        } catch (FileNotFoundException e) {

            /*
             * The file no exist may be the first time the plugin is running on this device,
             * We need to create the new clientIdentity
             */
            try {

                System.out.println("No previous clientIdentity finder - Proceed to create new one");

                /*
                 * Create the new clientIdentity
                 */
                identity = new ECCKeyPair();

                /*
                 * save into the file
                 */
                PluginTextFile pluginTextFile = pluginFileSystem.createTextFile(pluginId, "private", "clientIdentity", FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
                pluginTextFile.setContent(identity.getPrivateKey());
                pluginTextFile.persistToMedia();

            } catch (Exception exception) {
                /*
                 * The file cannot be created. I can not handle this situation.
                 */
                CantStartPluginException cantStartPluginException = new CantStartPluginException(exception.getLocalizedMessage());
                reportUnexpectedException(cantStartPluginException);

                throw cantStartPluginException;
            }


        } catch (CantCreateFileException cantCreateFileException) {

            /*
             * The file cannot be load. I can not handle this situation.
             */
            CantStartPluginException cantStartPluginException = new CantStartPluginException(cantCreateFileException.getLocalizedMessage());
            reportUnexpectedException(cantStartPluginException);

            throw cantStartPluginException;

        }

    }


    /**
     * Static method to get the logging level from any class under root.
     *
     * @param className
     * @return
     */
    public static LogLevel getLogLevelByClass(String className) {
        try {
            /**
             * sometimes the classname may be passed dinamically with an $moretext
             * I need to ignore whats after this.
             */
            String[] correctedClass = className.split((Pattern.quote("$")));
            return ChatNetworkServicePluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e) {
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

            this.dataBase = this.pluginDatabaseSystem.openDatabase(pluginId, CommunicationChatNetworkServiceDatabaseConstants.DATA_BASE_NAME);
            this.chatMetaDataDao = new ChatMetaDataDao(dataBase, pluginDatabaseSystem, pluginId,getErrorManager());

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

            /*
             * The database exists but cannot be open. I can not handle this situation.
             */
            CantInitializeChatNetworkServiceDatabaseException cantInitializeChatNetworkServiceDatabaseException = new CantInitializeChatNetworkServiceDatabaseException(cantOpenDatabaseException.getLocalizedMessage());
            reportUnexpectedException(cantInitializeChatNetworkServiceDatabaseException);
            throw cantInitializeChatNetworkServiceDatabaseException;

        } catch (DatabaseNotFoundException e) {

            /*
             * The database no exist may be the first time the plugin is running on this device,
             * We need to create the new database
             */
            CommunicationChatNetworkServiceDatabaseFactory communicationNetworkServiceDatabaseFactory = new CommunicationChatNetworkServiceDatabaseFactory(pluginDatabaseSystem,getErrorManager());

            try {

                /*
                 * We create the new database
                 */

                this.dataBase = communicationNetworkServiceDatabaseFactory.createDatabase(pluginId, CommunicationChatNetworkServiceDatabaseConstants.DATA_BASE_NAME);
                this.chatMetaDataDao = new ChatMetaDataDao(dataBase, pluginDatabaseSystem, pluginId,getErrorManager());
                this.chatMetaDataDao.initialize();

            } catch (CantCreateDatabaseException cantOpenDatabaseException) {

                /*
                 * The database cannot be created. I can not handle this situation.
                 */
                CantInitializeChatNetworkServiceDatabaseException cantInitializeChatNetworkServiceDatabaseException = new CantInitializeChatNetworkServiceDatabaseException(cantOpenDatabaseException.getLocalizedMessage());
                reportUnexpectedException(cantInitializeChatNetworkServiceDatabaseException);
                throw cantInitializeChatNetworkServiceDatabaseException;

            }
        } catch (Exception exception) {
            CantInitializeChatNetworkServiceDatabaseException cantInitializeChatNetworkServiceDatabaseException = new CantInitializeChatNetworkServiceDatabaseException(exception.getLocalizedMessage());
            reportUnexpectedException(cantInitializeChatNetworkServiceDatabaseException);
            throw cantInitializeChatNetworkServiceDatabaseException;
        }

    }

    @Override
    public String getNetWorkServicePublicKey() {
        return getIdentityPublicKey();
    }

    @Override
    public void sendChatMetadata(String localActorPubKey, String remoteActorPubKey, ChatMetadata chatMetadata) throws CantSendChatMessageMetadataException, IllegalArgumentException {


        ChatMetadataRecord chatMetadataRecord = new ChatMetadataRecord();

        try {

            if (chatMetadata == null) {
                throw new IllegalArgumentException("Argument chatMetadata can not be null");
            }
            if (localActorPubKey == null || localActorPubKey.length() == 0 || localActorPubKey.equals("null")) {
                throw new IllegalArgumentException("Argument localActorPubKey can not be null");
            }
            if (remoteActorPubKey == null || remoteActorPubKey.length() == 0 || remoteActorPubKey.equals("null")) {
                throw new IllegalArgumentException("Argument remoteActorPubKey can not be null");
            }
            System.out.println("ChatPLuginRoot - Starting method sendChatMetadata");


            CommunicationNetworkServiceLocal communicationNetworkServiceLocal = communicationNetworkServiceConnectionManager.getNetworkServiceLocalInstance(remoteActorPubKey);

          //  System.out.println("ChatNetworkServicePluginRoot - communicationNetworkServiceLocal: " + communicationNetworkServiceLocal);

            /*
             * Construct the message content in json format
             */

           // System.out.println("ChatNetworkServicePluginRoot - Message encoded:\n" + msjContent);

            String msgHash = CryptoHasher.performSha256(chatMetadata.getChatId().toString() + chatMetadata.getMessageId().toString());
            chatMetadataRecord.setTransactionId(getChatMetaDataDao().getNewUUID(UUID.randomUUID().toString()));
            chatMetadataRecord.setTransactionHash(msgHash);
            chatMetadataRecord.setChatId(chatMetadata.getChatId());
            chatMetadataRecord.setObjectId(chatMetadata.getObjectId());
            chatMetadataRecord.setLocalActorType(chatMetadata.getLocalActorType());
            chatMetadataRecord.setLocalActorPublicKey(chatMetadata.getLocalActorPublicKey());
            chatMetadataRecord.setRemoteActorType(chatMetadata.getRemoteActorType());
            chatMetadataRecord.setRemoteActorPublicKey(chatMetadata.getRemoteActorPublicKey());
            chatMetadataRecord.setChatName(chatMetadata.getChatName());
            chatMetadataRecord.setChatMessageStatus(chatMetadata.getChatMessageStatus());
            chatMetadataRecord.setMessageStatus(chatMetadata.getMessageStatus());
            chatMetadataRecord.setDate(chatMetadata.getDate());
            chatMetadataRecord.setMessageId(chatMetadata.getMessageId());
            chatMetadataRecord.setMessage(chatMetadata.getMessage());
            chatMetadataRecord.setDistributionStatus(DistributionStatus.SENT);
            chatMetadataRecord.setProcessed(ChatMetadataRecord.NO_PROCESSED);

            if(!chatMetadataRecord.isFilled()){
                throw new CantSendChatMessageMetadataException("Some value of ChatMetadata Is passed NULL");
            }
            String msjContent = EncodeMsjContent.encodeMSjContentChatMetadataTransmit(chatMetadataRecord, chatMetadata.getLocalActorType(), chatMetadata.getRemoteActorType());

           // System.out.println("ChatPLuginRoot - Chat transaction: " + chatMetadataRecord);

            /*
             * Save into data base
             */

            getChatMetaDataDao().create(chatMetadataRecord);


          //  System.out.println("ChatMetadata to send:\n" + chatMetadataRecord);

            /*
             * If not null
             */
            System.out.println("ChatPLuginRoot - Sending message.....");
            if (communicationNetworkServiceLocal != null) {

                //Send the message
                communicationNetworkServiceLocal.sendMessage(chatMetadataRecord.getLocalActorPublicKey(), chatMetadataRecord.getRemoteActorPublicKey(), msjContent);

            } else {

                /*
                 * Created the message
                 */
                FermatMessage fermatMessage = FermatMessageCommunicationFactory.constructFermatMessage(chatMetadataRecord.getLocalActorPublicKey(),//Sender
                        chatMetadataRecord.getRemoteActorPublicKey(), //Receiver
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
                PlatformComponentProfile sender = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().constructBasicPlatformComponentProfileFactory(chatMetadataRecord.getLocalActorPublicKey(), NetworkServiceType.CHAT, chatMetadataRecord.getLocalActorType());

                /*
                 * Create the receiver basic profile
                 */
                PlatformComponentProfile receiver = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().constructBasicPlatformComponentProfileFactory(chatMetadataRecord.getRemoteActorPublicKey(), NetworkServiceType.CHAT, chatMetadataRecord.getRemoteActorType());

                /*
                 * Ask the client to connect
                 */
                communicationNetworkServiceConnectionManager.connectTo(sender, platformComponentProfile, receiver);
            }
            System.out.println("ChatNetworkServicePluginRoot - Message sent.");
        }catch(CantSendChatMessageMetadataException e){
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
            String possibleCause = "Missing Fields.";


            CantSendChatMessageMetadataException pluginStartException = new CantSendChatMessageMetadataException(CantSendChatMessageMetadataException.DEFAULT_MESSAGE, e, context, possibleCause);

            reportUnexpectedException(pluginStartException);

            throw pluginStartException;
        }catch (Exception e) {

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

            if(chatMetadataRecord.isFilled()){
                try {
                    ChatMetadataRecord chatMetadataRecord1 = getChatMetaDataDao().findById(chatMetadataRecord.getTransactionId().toString());
                    if(chatMetadataRecord.equals(chatMetadataRecord1)){
                            chatMetadataRecord.setDistributionStatus(DistributionStatus.DELIVERING);
                            getChatMetaDataDao().update(chatMetadataRecord);
                    }else{
                        chatMetadataRecord.setDistributionStatus(DistributionStatus.DELIVERING);
                        getChatMetaDataDao().create(chatMetadataRecord);
                    }
                } catch (Exception e1) {
                    reportUnexpectedException(FermatException.wrapException(e1));
                }
            }
            CantSendChatMessageMetadataException pluginStartException = new CantSendChatMessageMetadataException(CantSendChatMessageMetadataException.DEFAULT_MESSAGE, e, context, possibleCause);

            reportUnexpectedException(pluginStartException);
            throw pluginStartException;
        }
    }

    @Override
    public void sendMessageStatusUpdate(String localActorPubKey, PlatformComponentType senderType, String remoteActorPubKey, PlatformComponentType receiverType, MessageStatus messageStatus, UUID chatId, UUID messageID) throws CantSendChatMessageNewStatusNotificationException {
        try {

            if (localActorPubKey == null || localActorPubKey.length() == 0) {
                throw new IllegalArgumentException("Argument localActorPubKey can not be null");
            }
            if (senderType == null) {
                throw new IllegalArgumentException("Argument senderType can not be null");
            }
            if (remoteActorPubKey == null || remoteActorPubKey.length() == 0) {
                throw new IllegalArgumentException("Argument remoteActorPubKey can not be null");
            }
            if (receiverType == null) {
                throw new IllegalArgumentException("Argument receiverType can not be null");
            }
            if (messageStatus == null) {
                throw new IllegalArgumentException("Argument newDistributionStatus can not be null");
            }
            if (chatId == null) {
                throw new IllegalArgumentException("Argument chatId can not be null");
            }

            /*
             * ask for a previous connection
             */
            CommunicationNetworkServiceLocal communicationNetworkServiceLocal = communicationNetworkServiceConnectionManager.getNetworkServiceLocalInstance(remoteActorPubKey);

            /*
             * Construct the message content in json format
             */
            String msjContent = EncodeMsjContent.encodeMSjContentTransactionNewStatusNotification(chatId, messageID, messageStatus, senderType, receiverType);

            /*
             * If not null
             */
            if (communicationNetworkServiceLocal != null) {

                //Send the message
                communicationNetworkServiceLocal.sendMessage(localActorPubKey, remoteActorPubKey, msjContent);

            } else {

                /*
                 * Created the message
                 */
                FermatMessage fermatMessage = FermatMessageCommunicationFactory.constructFermatMessage(localActorPubKey,//Sender
                        remoteActorPubKey, //Receiver
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
                PlatformComponentProfile sender = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().constructBasicPlatformComponentProfileFactory(localActorPubKey, NetworkServiceType.CHAT, senderType);

                /*
                 * Create the receiver basic profile
                 */
                PlatformComponentProfile receiver = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().constructBasicPlatformComponentProfileFactory(remoteActorPubKey, NetworkServiceType.CHAT, receiverType);

                /*
                 * Ask the client to connect
                 */
                communicationNetworkServiceConnectionManager.connectTo(sender, platformComponentProfile, receiver);

            }

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

            CantSendChatMessageNewStatusNotificationException pluginStartException = new CantSendChatMessageNewStatusNotificationException(CantSendChatMessageNewStatusNotificationException.DEFAULT_MESSAGE, e, context, possibleCause);
            reportUnexpectedException(pluginStartException);
            throw pluginStartException;
        }
    }


    @Override
    public void sendChatMessageNewStatusNotification(String localActorPubKey, PlatformComponentType senderType, String remoteActorPubKey, PlatformComponentType receiverType, DistributionStatus newDistributionStatus, UUID chatId, UUID messageID) throws CantSendChatMessageNewStatusNotificationException {

        try {

            if (localActorPubKey == null || localActorPubKey.length() == 0) {
                throw new IllegalArgumentException("Argument localActorPubKey can not be null");
            }
            if (senderType == null) {
                throw new IllegalArgumentException("Argument senderType can not be null");
            }
            if (remoteActorPubKey == null || remoteActorPubKey.length() == 0) {
                throw new IllegalArgumentException("Argument remoteActorPubKey can not be null");
            }
            if (receiverType == null) {
                throw new IllegalArgumentException("Argument receiverType can not be null");
            }
            if (newDistributionStatus == null) {
                throw new IllegalArgumentException("Argument newDistributionStatus can not be null");
            }
            if (chatId == null) {
                throw new IllegalArgumentException("Argument chatId can not be null");
            }


            /*
             * ask for a previous connection
             */
            CommunicationNetworkServiceLocal communicationNetworkServiceLocal = communicationNetworkServiceConnectionManager.getNetworkServiceLocalInstance(remoteActorPubKey);

            /*
             * Construct the message content in json format
             */
            String msjContent = EncodeMsjContent.encodeMSjContentTransactionNewStatusNotification(chatId, messageID, newDistributionStatus, senderType, receiverType);

            /*
             * If not null
             */
            if (communicationNetworkServiceLocal != null) {

                //Send the message
                communicationNetworkServiceLocal.sendMessage(localActorPubKey, remoteActorPubKey, msjContent);

            } else {

                /*
                 * Created the message
                 */
                FermatMessage fermatMessage = FermatMessageCommunicationFactory.constructFermatMessage(localActorPubKey,//Sender
                        remoteActorPubKey, //Receiver
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
                PlatformComponentProfile sender = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().constructBasicPlatformComponentProfileFactory(localActorPubKey, NetworkServiceType.CHAT, senderType);

                /*
                 * Create the receiver basic profile
                 */
                PlatformComponentProfile receiver = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().constructBasicPlatformComponentProfileFactory(remoteActorPubKey, NetworkServiceType.CHAT, receiverType);

                /*
                 * Ask the client to connect
                 */
                communicationNetworkServiceConnectionManager.connectTo(sender, platformComponentProfile, receiver);

            }

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

            CantSendChatMessageNewStatusNotificationException pluginStartException = new CantSendChatMessageNewStatusNotificationException(CantSendChatMessageNewStatusNotificationException.DEFAULT_MESSAGE, e, context, possibleCause);

            reportUnexpectedException(pluginStartException);
            throw pluginStartException;
        }
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
        setNewSentMessagesNotificationEventHandler(new NewSentMessagesNotificationEventHandler(this));
        fermatEventListener.setEventHandler(getNewSentMessagesNotificationEventHandler());
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        /*
         * Listen and handle new message receive notification
         */
        fermatEventListener = eventManager.getNewListener(P2pEventType.NEW_NETWORK_SERVICE_MESSAGE_RECEIVE_NOTIFICATION);
        setNewReceiveMessagesNotificationEventHandler(new NewReceiveMessagesNotificationEventHandler(this));
        fermatEventListener.setEventHandler(getNewReceiveMessagesNotificationEventHandler());
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

                /*
         * Listen and handle Client Connection Loose Notification Event
         */
        fermatEventListener = eventManager.getNewListener(P2pEventType.CLIENT_CONNECTION_LOOSE);
        fermatEventListener.setEventHandler(new ClientConnectionLooseNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);


        /*
         * Listen and handle Client Connection Success Reconnect Notification Event
         */
        fermatEventListener = eventManager.getNewListener(P2pEventType.CLIENT_SUCCESS_RECONNECT);
        fermatEventListener.setEventHandler(new ClientSuccessfullReconnectNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

    }

    @Override
    public void handleClientConnectionLooseNotificationEvent(FermatEvent fermatEvent) {

        if (communicationNetworkServiceConnectionManager != null)
            communicationNetworkServiceConnectionManager.stop();
    }

    @Override
    public void handleClientSuccessfullReconnectNotificationEvent(FermatEvent fermatEvent) {


        if (communicationNetworkServiceConnectionManager != null) {
            communicationNetworkServiceConnectionManager.restart();
        }
        if (!this.register) {


            if (communicationRegistrationProcessNetworkServiceAgent.isAlive()) {

                communicationRegistrationProcessNetworkServiceAgent.interrupt();
                communicationRegistrationProcessNetworkServiceAgent = null;
            }

                   /*
                 * Construct my profile and register me
                 */
            PlatformComponentProfile platformComponentProfileToReconnect = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().constructPlatformComponentProfileFactory(this.getIdentityPublicKey(),
                    this.getAlias().toLowerCase(),
                    this.getName(),
                    this.getNetworkServiceType(),
                    this.getPlatformComponentType(),
                    this.getExtraData());

            try {
                    /*
                     * Register me
                     */
                wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().registerComponentForCommunication(this.getNetworkServiceType(), platformComponentProfileToReconnect);

            } catch (CantRegisterComponentException e) {
                reportUnexpectedException(e);
            }

                /*
                 * Configure my new profile
                 */
            this.setPlatformComponentProfilePluginRoot(platformComponentProfileToReconnect);

                /*
                 * Initialize the connection manager
                 */
            this.initializeCommunicationNetworkServiceConnectionManager();
        }
    }


    @Override
    public void confirmReception(UUID transactionID) throws CantConfirmTransactionException {
        try {


            ChatMetadataRecord chatMetadataRecord = getChatMetaDataDao().findById(transactionID.toString());
            chatMetadataRecord.setDistributionStatus(DistributionStatus.DELIVERED);
            chatMetadataRecord.setChatMessageStatus(ChatMessageStatus.CREATED_CHAT);
            chatMetadataRecord.setMessageStatus(MessageStatus.DELIVERED);
            chatMetadataRecord.setProcessed(ChatMetadataRecord.PROCESSED);
            getChatMetaDataDao().update(chatMetadataRecord);

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
            CantConfirmTransactionException cantConfirmTransactionException = new CantConfirmTransactionException(CantConfirmTransactionException.DEFAULT_MESSAGE, e, contextBuffer.toString(), "Database error");
            reportUnexpectedException(cantConfirmTransactionException);
            throw cantConfirmTransactionException;
        }
    }

    @Override
    public List<Transaction<ChatMetadata>> getPendingTransactions(Specialist specialist) throws CantDeliverPendingTransactionsException {
        List<Transaction<ChatMetadata>> pendingTransactions = new ArrayList<>();
        try {

            List<ChatMetadataRecord> pendingChatMetadataTransactions = getChatMetaDataDao().findAll(CommunicationChatNetworkServiceDatabaseConstants.CHAT_PROCCES_STATUS_COLUMN_NAME, ChatMetadataRecord.NO_PROCESSED);
            if (!pendingChatMetadataTransactions.isEmpty()) {
                for (ChatMetadataRecord chatMetadataRecord : pendingChatMetadataTransactions) {
                    Transaction<ChatMetadata> transaction = new Transaction<>(chatMetadataRecord.getTransactionId(),
                            (ChatMetadata) chatMetadataRecord,
                            Action.APPLY,
                            chatMetadataRecord.getDate().getTime());
                    pendingTransactions.add(transaction);

                }

            }

        } catch (CantReadRecordDataBaseException e) {
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
            CantDeliverPendingTransactionsException cantDeliverPendingTransactionsException = new CantDeliverPendingTransactionsException(CantDeliverPendingTransactionsException.DEFAULT_MESSAGE, e, contextBuffer.toString(), "No pending Transaction");
            reportUnexpectedException(cantDeliverPendingTransactionsException);
            throw cantDeliverPendingTransactionsException;
        }
        return pendingTransactions;
    }
}

