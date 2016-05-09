package org.fermat.fermat_dap_plugin.layer.network.service.asset.transmission.developer.version_2;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import org.fermat.fermat_dap_api.layer.all_definition.enums.DAPMessageSubject;
import org.fermat.fermat_dap_api.layer.all_definition.enums.DAPMessageType;
import org.fermat.fermat_dap_api.layer.all_definition.enums.EventType;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.DAPMessage;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.exceptions.CantGetDAPMessagesException;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.exceptions.CantSendMessageException;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.exceptions.CantUpdateMessageStatusException;
import org.fermat.fermat_dap_api.layer.all_definition.util.ActorUtils;
import org.fermat.fermat_dap_api.layer.dap_actor.DAPActor;
import org.fermat.fermat_dap_api.layer.dap_network_services.asset_transmission.exceptions.CantSendDigitalAssetMetadataException;
import org.fermat.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.AssetTransmissionNetworkServiceManager;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageStatus;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.base.AbstractNetworkServiceBase;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 10/02/16. - Jose Briceño josebricenor@gmail.com (22/02/16)
 */
@PluginInfo(difficulty = PluginInfo.Dificulty.HIGH,
        maintainerMail = "nerioindriago@gmail.com",
        createdBy = "victor",
        layer = Layers.NETWORK_SERVICE,
        platform = Platforms.DIGITAL_ASSET_PLATFORM,
        plugin = Plugins.BITDUBAI_DAP_ASSET_TRANSMISSION_NETWORK_SERVICE)
public class AssetTransmissionNetworkServicePluginRoot extends AbstractNetworkServiceBase implements AssetTransmissionNetworkServiceManager, DatabaseManagerForDevelopers {

    //VARIABLE DECLARATION
    /**
     * Represent the dataBase
     */
    private Database dataBase;

    /**
     * DAO
     */
    private org.fermat.fermat_dap_plugin.layer.network.service.asset.transmission.developer.version_2.database.communications.DAPMessageDAO dapMessageDAO;
    /**
     * Represent the communicationNetworkServiceDeveloperDatabaseFactory
     */
    private org.fermat.fermat_dap_plugin.layer.network.service.asset.transmission.developer.version_2.database.communications.CommunicationNetworkServiceDeveloperDatabaseFactory communicationNetworkServiceDeveloperDatabaseFactory;

    public final static EventSource EVENT_SOURCE = EventSource.NETWORK_SERVICE_ASSET_TRANSMISSION;

    private ExecutorService executorService;

    //CONSTRUCTORS

    /**
     * Constructor without parameters
     */
    public AssetTransmissionNetworkServicePluginRoot() {
        super(new PluginVersionReference(new Version()),
                EventSource.NETWORK_SERVICE_ASSET_TRANSMISSION,
                PlatformComponentType.NETWORK_SERVICE,
                NetworkServiceType.ASSET_TRANSMISSION,
                "Asset Transmission Network Service",
                null);
    }

    //PUBLIC METHODS

    /**
     * This method is called when the network service method
     * AbstractPlugin#start() is called
     */
    @Override
    protected void onStart() throws CantStartPluginException {
        System.out.println("AssetTransmissionNetworkService - Starting");

        try {

            /*
             * Initialize Developer Database Factory
             */
            communicationNetworkServiceDeveloperDatabaseFactory = new org.fermat.fermat_dap_plugin.layer.network.service.asset.transmission.developer.version_2.database.communications.CommunicationNetworkServiceDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
            dataBase = communicationNetworkServiceDeveloperDatabaseFactory.initializeDatabase();
            dapMessageDAO = new org.fermat.fermat_dap_plugin.layer.network.service.asset.transmission.developer.version_2.database.communications.DAPMessageDAO(dataBase);
            executorService = Executors.newFixedThreadPool(3);
            /*
             * Set the new status
            */
            this.serviceStatus = ServiceStatus.STARTED;

        } catch (org.fermat.fermat_dap_plugin.layer.network.service.asset.transmission.developer.version_2.exceptions.CantInitializeDAPMessageNetworkServiceDatabaseException exception) {

            StringBuilder contextBuffer = new StringBuilder();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("Database Name: " + org.fermat.fermat_dap_plugin.layer.network.service.asset.transmission.developer.version_2.database.communications.CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The Asset User Actor Network Service Database triggered an unexpected problem that wasn't able to solve by itself";
            CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception, context, possibleCause);

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_TRANSMISSION_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);

            throw pluginStartException;
        }
    }

    /**
     * This method is automatically called when the network service receive
     * a new message
     *
     * @param newFermatMessageReceive
     */
    @Override
    public void onNewMessagesReceive(FermatMessage newFermatMessageReceive) { //Logica tomada del handler NewReceiveMessagesNotificationEventHandler
        System.out.println("NEW MESSAGE RECEIVED!!!");
        FermatEvent event = getEventManager().getNewEvent(EventType.RECEIVE_NEW_DAP_MESSAGE);
        event.setSource(AssetTransmissionNetworkServicePluginRoot.EVENT_SOURCE);
        getEventManager().raiseEvent(event);

        try {
            dapMessageDAO.create(DAPMessage.fromXML(newFermatMessageReceive.getContent()), MessageStatus.NEW_RECEIVED);
        } catch (org.fermat.fermat_dap_plugin.layer.network.service.asset.transmission.developer.version_2.exceptions.CantInsertRecordDataBaseException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is automatically called when the network service receive
     * a new message was sent
     *
     * @param messageSent
     */
    @Override
    public void onSentMessage(FermatMessage messageSent) {
        System.out.print("ASSET TRANSMISSION - NOTIFICACION EVENTO MENSAJE ENVIADO!!!!");
    }

    /**
     * This method is automatically called when the network service is registered
     */

    @Override
    protected void onNetworkServiceRegistered() {

    }

    /**
     * This method is automatically called when the client connection is close
     */
    @Override
    protected void onClientConnectionClose() {

    }

    /**
     * This method is automatically called when the client connection is Successful reconnect
     */
    @Override
    protected void onClientSuccessfulReconnect() {

    }

    /**
     * This method is automatically called when the client connection is loose
     */
    @Override
    protected void onClientConnectionLoose() {

    }

    /**
     * This method is automatically called when a component connection request is fail
     *
     * @param remoteParticipant
     */
    @Override
    protected void onFailureComponentConnectionRequest(PlatformComponentProfile
                                                               remoteParticipant) {

    }

    /**
     * This method is automatically called when the list of platform Component Profile Registered
     * is received for the network service
     *
     * @param remotePlatformComponentProfileRegisteredList
     */
    @Override
    protected void onReceivePlatformComponentProfileRegisteredList
    (CopyOnWriteArrayList<PlatformComponentProfile> remotePlatformComponentProfileRegisteredList) {

    }

    /**
     * This method is automatically called when the request to update a actor profile is complete
     *
     * @param platformComponentProfileUpdate
     */
    @Override
    protected void onCompleteActorProfileUpdate(PlatformComponentProfile
                                                        platformComponentProfileUpdate) {

    }

    /**
     * This method is automatically called when the request of component registrations is fail
     *
     * @param platformComponentProfile
     */
    @Override
    protected void onFailureComponentRegistration(PlatformComponentProfile
                                                          platformComponentProfile) {

    }

    /**
     * @param dapMessage the message to be sent, this message has to contain both the actor
     *                   that sent the message and the actor that will receive the message.
     * @throws CantSendMessageException
     */
    @Override
    public void sendMessage(final DAPMessage dapMessage) throws CantSendMessageException {

        try {
            DAPActor actorSender = dapMessage.getActorSender();
            DAPActor actorReceiver = dapMessage.getActorReceiver();
            PlatformComponentType senderType = ActorUtils.getPlatformComponentType(actorSender);
            PlatformComponentType receiverType = ActorUtils.getPlatformComponentType(actorReceiver);

            System.out.println("AssetTransmissionNetworkServicePluginRoot - Actor Sender: PK : " + actorSender.getActorPublicKey() + " - Type: " + senderType.getCode());
            System.out.println("AssetTransmissionNetworkServicePluginRoot - Actor Receiver: PK : " + actorReceiver.getActorPublicKey() + " - Type: " + receiverType.getCode());
            /*
             * If not null
             */
            System.out.println("AssetTransmissionNetworkServicePluginRoot - Sending message.....");
            /*
             * Save to the data base table
             */
            final PlatformComponentProfile sender = getProfileSenderToRequestConnection(actorSender.getActorPublicKey(), NetworkServiceType.UNDEFINED, senderType);
            final PlatformComponentProfile receiver = getProfileSenderToRequestConnection(actorReceiver.getActorPublicKey(), NetworkServiceType.UNDEFINED, receiverType);
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        sendNewMessage(sender, receiver, dapMessage.toXML());
                    } catch (com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.exceptions.CantSendMessageException e) {
                        errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_TRANSMISSION_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                    }
                }
            });

            System.out.println("AssetTransmissionNetworkServicePluginRoot - Message sent.");
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

            CantSendMessageException cantSendMessage = new CantSendMessageException(CantSendDigitalAssetMetadataException.DEFAULT_MESSAGE, FermatException.wrapException(e), context, possibleCause);

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_TRANSMISSION_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantSendMessage);

            throw cantSendMessage;
        }
    }

    /**
     * This method retrieves the list of new incoming and unread DAP Messages for a specific type.
     *
     * @param type The {@link DAPMessageType} of message to search for.
     * @return {@link List} instance filled with all the {@link DAPMessage} that were found.
     * @throws CantGetDAPMessagesException If there was an error while querying for the list.
     */
    @Override
    public List<DAPMessage> getUnreadDAPMessagesByType(DAPMessageType type) throws CantGetDAPMessagesException {
        if (dapMessageDAO == null) return Collections.EMPTY_LIST;
        try {
            return dapMessageDAO.findUnreadByType(type.getCode());
        } catch (org.fermat.fermat_dap_plugin.layer.network.service.asset.transmission.developer.version_2.exceptions.CantReadRecordDataBaseException e) {
            throw new CantGetDAPMessagesException();
        }
    }

    @Override
    public List<DAPMessage> getUnreadDAPMessageBySubject(DAPMessageSubject subject) throws CantGetDAPMessagesException {
        if (dapMessageDAO == null) return Collections.EMPTY_LIST;
        try {
            return dapMessageDAO.findUnreadBySubject(subject.getCode());
        } catch (org.fermat.fermat_dap_plugin.layer.network.service.asset.transmission.developer.version_2.exceptions.CantReadRecordDataBaseException e) {
            throw new CantGetDAPMessagesException();
        }
    }

    @Override
    public void confirmReception(DAPMessage message) throws CantUpdateMessageStatusException {
        try {
            dapMessageDAO.confirmDAPMessageReception(message);
        } catch (org.fermat.fermat_dap_plugin.layer.network.service.asset.transmission.developer.version_2.exceptions.CantUpdateRecordDataBaseException e) {
            throw new CantUpdateMessageStatusException();
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
}
