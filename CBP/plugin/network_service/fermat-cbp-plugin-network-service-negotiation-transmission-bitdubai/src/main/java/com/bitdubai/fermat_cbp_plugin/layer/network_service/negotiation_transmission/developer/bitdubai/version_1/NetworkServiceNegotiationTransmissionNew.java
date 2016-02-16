package com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransmissionState;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationType;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.interfaces.NegotiationTransmission;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.database.NegotiationTransmissionNetworkServiceConnectionsDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.database.NegotiationTransmissionNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.database.NegotiationTransmissionNetworkServiceDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.database.NegotiationTransmissionNetworkServiceDeveloperDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.exceptions.CantHandleNewMessagesException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.exceptions.CantInitializeNetworkServiceDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.exceptions.CantRegisterSendNegotiationTransmissionException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.messages.ConfirmMessage;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.messages.NegotiationMessage;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.messages.NegotiationTransmissionMessage;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.IncomingNotificationDao;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.structure.NegotiationTransmissionAgent;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.structure.NegotiationTransmissionImpl;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.base.AbstractNetworkServiceBase;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsClientConnection;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by José Vilchez on 11/02/16.
 */
public class NetworkServiceNegotiationTransmissionNew extends AbstractNetworkServiceBase {

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    /*Represent the dataBase*/
    private Database dataBase;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    //Represent the negotiationTransmissionNetworkServiceDeveloperDatabaseFactory
    private NegotiationTransmissionNetworkServiceDeveloperDatabaseFactory negotiationTransmissionNetworkServiceDeveloperDatabaseFactory;

    /*Represent DAO Database Transmission*/
    private NegotiationTransmissionNetworkServiceDatabaseDao databaseDao;

    /*Represent DAO Incoming Notification*/
    private IncomingNotificationDao incomingNotificationDao;

    /*Represent DAO Database Connections*/
    private NegotiationTransmissionNetworkServiceConnectionsDatabaseDao databaseConnectionsDao;
    /**
     * cacha identities to register
     */
    private List<PlatformComponentProfile> actorsToRegisterCache;

    @NeededPluginReference(platform = Platforms.COMMUNICATION_PLATFORM, layer = Layers.COMMUNICATION, plugin = Plugins.WS_CLOUD_CLIENT)
    private WsCommunicationsCloudClientManager wsCommunicationsCloudClientManager;


    /**
     * Network Service Constructor
     */
    public NetworkServiceNegotiationTransmissionNew() {
        super(new PluginVersionReference(new Version()),
                EventSource.NETWORK_SERVICE_INTRA_ACTOR,
                PlatformComponentType.NETWORK_SERVICE,
                NetworkServiceType.INTRA_USER,
                "Negotiation Transmission Network Service",
                null);
        this.actorsToRegisterCache = new ArrayList<>();

    }

    @Override
    protected void onStart() {

        try {

            //Initialize Developer Database Factory
            negotiationTransmissionNetworkServiceDeveloperDatabaseFactory = new NegotiationTransmissionNetworkServiceDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
            negotiationTransmissionNetworkServiceDeveloperDatabaseFactory.initializeDatabase();

            //Initialize DAO
            databaseDao = new NegotiationTransmissionNetworkServiceDatabaseDao(pluginDatabaseSystem, pluginId);
            databaseConnectionsDao = new NegotiationTransmissionNetworkServiceConnectionsDatabaseDao(pluginDatabaseSystem, pluginId);
            incomingNotificationDao = new IncomingNotificationDao(dataBase, getPluginFileSystem(),pluginId);
        } catch (CantInitializeNetworkServiceDatabaseException e) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("Database Name: " + NegotiationTransmissionNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The Template Database triggered an unexpected problem that wasn't able to solve by itself";
            CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, e, context, possibleCause);

            errorManager.reportUnexpectedPluginException(Plugins.NEGOTIATION_TRANSMISSION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);

        }
        System.out.print("-----------------------\n Negotiation Transmission: Successful start.\n-----------------------\n");


    }

    @Override
    public void onNewMessagesReceive(FermatMessage fermatMessage) {

        try {

            Gson gson = new Gson();
            NegotiationTransmissionMessage negotiationTransmissionMessage = gson.fromJson(fermatMessage.getContent(), NegotiationTransmissionMessage.class);

            switch (negotiationTransmissionMessage.getMessageType()) {
                case TRANSMISSION_NEGOTIATION:
                    NegotiationMessage negotiationMessage = gson.fromJson(fermatMessage.getContent(), NegotiationMessage.class);
                    receiveNegotiation(negotiationMessage);
                    break;

                case TRANSMISSION_CONFIRM:
                    ConfirmMessage confirmMessage = gson.fromJson(fermatMessage.getContent(), ConfirmMessage.class);
                    receiveConfirm(confirmMessage);
                    break;

                default:
                    throw new CantHandleNewMessagesException("message type: " + negotiationTransmissionMessage.getMessageType().name(), "Message type not handled.");
            }

        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.NEGOTIATION_TRANSMISSION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
        }

    }

    @Override
    public void onSentMessage(FermatMessage messageSent) {

    }

    @Override
    protected void onNetworkServiceRegistered() {

        try {
            for (PlatformComponentProfile platformComponentProfile : actorsToRegisterCache) {
                getWsCommunicationsCloudClientManager().getCommunicationsCloudClientConnection().registerComponentForCommunication(getNetworkServiceProfile().getNetworkServiceType(), platformComponentProfile);
                System.out.println("NetworkServiceNegotiationTransmissionPluginRoot - Trying to register to: " + platformComponentProfile.getAlias());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onClientConnectionClose() {

    }

    @Override
    protected void onClientSuccessfulReconnect() {

    }

    @Override
    protected void onClientConnectionLoose() {

    }

    @Override
    protected void onFailureComponentConnectionRequest(PlatformComponentProfile remoteParticipant) {

    }

    @Override
    protected void onReceivePlatformComponentProfileRegisteredList(CopyOnWriteArrayList<PlatformComponentProfile> remotePlatformComponentProfileRegisteredList) {

    }

    @Override
    protected void onCompleteActorProfileUpdate(PlatformComponentProfile platformComponentProfileUpdate) {

    }

    @Override
    protected void onFailureComponentRegistration(PlatformComponentProfile platformComponentProfile) {

    }

    @Override
    public PlatformComponentProfile getProfileSenderToRequestConnection(String identityPublicKeySender) {
        return null;
    }

    @Override
    public PlatformComponentProfile getProfileDestinationToRequestConnection(String identityPublicKeyDestination) {
        return null;
    }

    @Override
    protected void reprocessMessages() {

    }

    @Override
    protected void reprocessMessages(String identityPublicKey) {

    }

    @Override
    public ErrorManager getErrorManager() {
        return null;
    }

    @Override
    public EventManager getEventManager() {
        return null;
    }

    public WsCommunicationsCloudClientManager getWsCommunicationsCloudClientManager() {
        return wsCommunicationsCloudClientManager;
    }

    public PluginFileSystem getPluginFileSystem() {
        return pluginFileSystem;
    }

    private void receiveNegotiation(NegotiationMessage negotiationMessage) throws CantHandleNewMessagesException {

        try {

            System.out.print("\n**** 12) MOCK NEGOTIATION TRANSACTION - NEGOTIATION TRANSMISSION - PLUGIN ROOT - RECEIVE NEGOTIATION ****\n");

            NegotiationType negotiationType;

            if (negotiationMessage.getNegotiationType().getCode().equals(NegotiationType.PURCHASE.getCode())) {
                negotiationType = NegotiationType.SALE;
            } else {
                negotiationType = NegotiationType.PURCHASE;
            }

            NegotiationTransmission negotiationTransmission = new NegotiationTransmissionImpl(
                    negotiationMessage.getTransmissionId(),
                    negotiationMessage.getTransactionId(),
                    negotiationMessage.getNegotiationId(),
                    negotiationMessage.getNegotiationTransactionType(),
                    negotiationMessage.getPublicKeyActorSend(),
                    negotiationMessage.getActorSendType(),
                    negotiationMessage.getPublicKeyActorReceive(),
                    negotiationMessage.getActorReceiveType(),
                    negotiationMessage.getTransmissionType(),
                    negotiationMessage.getTransmissionState(),
                    negotiationType,
                    negotiationMessage.getNegotiationXML(),
                    negotiationMessage.getTimestamp()
            );

            System.out.print("\n**** 12) MOCK NEGOTIATION TRANSMISSION - NEGOTIATION TRANSMISSION - PLUGIN ROOT - RECEIVE NEGOTIATION DATE: ****\n" +
                            "- ActorReceive = " + negotiationTransmission.getPublicKeyActorReceive() +
                            "- ActorSend = " + negotiationTransmission.getPublicKeyActorSend()
            );

            incomingNotificationDao.createNotification(negotiationTransmission, NegotiationTransmissionState.PENDING_ACTION);
//            databaseDao.registerSendNegotiatioTransmission(negotiationTransmission, NegotiationTransmissionState.PENDING_ACTION);

//        } catch (CantRegisterSendNegotiationTransmissionException e) {
//            throw new CantHandleNewMessagesException(CantHandleNewMessagesException.DEFAULT_MESSAGE, e, "ERROR RECEIVE NEGOTIATION", "");
        }
        catch (Exception e) {
            throw new CantHandleNewMessagesException(e.getMessage(), FermatException.wrapException(e), "Network Service Negotiation Transmission", "Cant Construc Negotiation Transmission, unknown failure.");
        }

    }

    private void receiveConfirm(ConfirmMessage confirmMessage) throws CantHandleNewMessagesException {

            UUID transmissionId = confirmMessage.getTransmissionId();
            incomingNotificationDao.confirmReception(transmissionId);

    }

}
