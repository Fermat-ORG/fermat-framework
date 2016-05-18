package com.bitdubai.fermat_cht_plugin.layer.actor_network_service.chat.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_cht_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantInitializeDatabaseException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.enums.ProtocolState;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.enums.RequestType;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantAcceptConnectionRequestException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantDenyConnectionRequestException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantDisconnectException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantExposeIdentityException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantRequestConnectionException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.ConnectionRequestNotFoundException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.utils.ChatConnectionInformation;
import com.bitdubai.fermat_cht_plugin.layer.actor_network_service.chat.developer.bitdubai.version_1.database.ChatActorNetworkServiceDao;
import com.bitdubai.fermat_cht_plugin.layer.actor_network_service.chat.developer.bitdubai.version_1.database.ChatActorNetworkServiceDeveloperDatabaseFactory;
import com.bitdubai.fermat_cht_plugin.layer.actor_network_service.chat.developer.bitdubai.version_1.event_handler.ChatP2PCompletedConnectionRegistrationEventHandler;
import com.bitdubai.fermat_cht_plugin.layer.actor_network_service.chat.developer.bitdubai.version_1.exceptions.CantHandleNewMessagesException;
import com.bitdubai.fermat_cht_plugin.layer.actor_network_service.chat.developer.bitdubai.version_1.messages.InformationMessage;
import com.bitdubai.fermat_cht_plugin.layer.actor_network_service.chat.developer.bitdubai.version_1.messages.NetworkServiceMessage;
import com.bitdubai.fermat_cht_plugin.layer.actor_network_service.chat.developer.bitdubai.version_1.messages.RequestMessage;
import com.bitdubai.fermat_cht_plugin.layer.actor_network_service.chat.developer.bitdubai.version_1.structure.ChatActorNetworkServiceManager;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.base.AbstractNetworkServiceBase;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by José D. Vilchez A. (josvilchezalmera@gmail.com) on 07/04/16.
 * Edited by Miguel Rincon on 19/04/2016
 */
@PluginInfo(createdBy = "José D. Vilchez A", maintainerMail = "franklinmarcano1970@gmail.com", platform = Platforms.CHAT_PLATFORM, layer = Layers.ACTOR_NETWORK_SERVICE, plugin = Plugins.CHAT_ACTOR_NETWORK_SERVICE)
public class ChatActorNetworkServicePluginRoot extends AbstractNetworkServiceBase implements DatabaseManagerForDevelopers {

    /**
     * Chat Actor Network Service member variables.
     */
    private ChatActorNetworkServiceDao chatActorNetworkServiceDao;

    private ChatActorNetworkServiceManager fermatManager;

    private ExecutorService executor;

    public ChatActorNetworkServicePluginRoot() {

        super(
                new PluginVersionReference(new Version()),
                EventSource.ACTOR_NETWORK_SERVICE_CHAT,
                PlatformComponentType.NETWORK_SERVICE,
                NetworkServiceType.ACTOR_CHAT,
                "Chat Actor Network Service",
                null
        );
    }

    private List<FermatEventListener> listenersAdded;

    @Override
    public FermatManager getManager() {
        return fermatManager;
    }

    @Override
    protected void onStart() throws CantStartPluginException {

        try {

            chatActorNetworkServiceDao = new ChatActorNetworkServiceDao(pluginDatabaseSystem, pluginFileSystem, pluginId);

            chatActorNetworkServiceDao.initialize();

            fermatManager = new ChatActorNetworkServiceManager(
                    getCommunicationsClientConnection(),
                    chatActorNetworkServiceDao,
                    this,
                    getPluginVersionReference()
            );

            executor = Executors.newSingleThreadExecutor();

            FermatEventListener fermatEventListener = eventManager.getNewListener(P2pEventType.COMPLETE_COMPONENT_REGISTRATION_NOTIFICATION);
            fermatEventListener.setEventHandler(new ChatP2PCompletedConnectionRegistrationEventHandler(this));
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

            System.out.println("******* Init Chat Actor Network Service ******");

        } catch (final CantInitializeDatabaseException e) {

            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(e, "", "Problem initializing chat ans dao.");
        }
    }

    @Override
    public void pause() {

        fermatManager.setPlatformComponentProfile(null);
        getCommunicationNetworkServiceConnectionManager().pause();

        super.pause();
    }

    @Override
    public void resume() {

        // resume connections manager.
        getCommunicationNetworkServiceConnectionManager().resume();

        super.resume();
    }

    @Override
    public void stop() {

        fermatManager.setPlatformComponentProfile(null);
        getCommunicationNetworkServiceConnectionManager().stop();

        super.stop();
    }

    @Override
    protected void onNetworkServiceRegistered() {
        fermatManager.setPlatformComponentProfile(this.getNetworkServiceProfile());
        runExposeIdentityThread();
    }

    @Override
    public final void onSentMessage(final FermatMessage fermatMessage) {
        System.out.println("************ Mensaje supuestamente enviado chat actor network service");

        try {

            String jsonMessage = fermatMessage.getContent();

            NetworkServiceMessage networkServiceMessage = NetworkServiceMessage.fromJson(jsonMessage);

            System.out.println("********************* Message Sent Type:  " + networkServiceMessage.getMessageType());

            switch (networkServiceMessage.getMessageType()) {

                case CONNECTION_INFORMATION:
                    InformationMessage informationMessage = InformationMessage.fromJson(jsonMessage);

                    chatActorNetworkServiceDao.confirmActorConnectionRequest(informationMessage.getRequestId());
                    break;

                case CONNECTION_REQUEST:
                    // update the request to processing receive state with the given action.

                    RequestMessage requestMessage = RequestMessage.fromJson(jsonMessage);

                    chatActorNetworkServiceDao.confirmActorConnectionRequest(requestMessage.getRequestId());
                    break;

                default:
                    throw new CantHandleNewMessagesException(
                            "message type: " +networkServiceMessage.getMessageType().name(),
                            "Message type not handled."
                    );
            }

        } catch (Exception e) {
            System.out.println(e.toString());
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
    }

    @Override
    protected void onClientSuccessfulReconnect() {
        runExposeIdentityThread();
    }

    @Override
    public void onNewMessagesReceive(FermatMessage fermatMessage) {

        System.out.println("****** CHAT ACTOR NETWORK SERVICE NEW MESSAGE RECEIVED: " + fermatMessage);
        try {

            String jsonMessage = fermatMessage.getContent();

            NetworkServiceMessage networkServiceMessage = NetworkServiceMessage.fromJson(jsonMessage);

            System.out.println("********************* Message Type:  " + networkServiceMessage.getMessageType());

            switch (networkServiceMessage.getMessageType()) {

                case CONNECTION_INFORMATION:
                    InformationMessage informationMessage = InformationMessage.fromJson(jsonMessage);

                    System.out.println("********************* Content:  " + informationMessage);

                    receiveConnectionInformation(informationMessage);

                    String destinationPublicKey = chatActorNetworkServiceDao.getDestinationPublicKey(informationMessage.getRequestId());

                    getCommunicationNetworkServiceConnectionManager().closeConnection(destinationPublicKey);
                    break;

                case CONNECTION_REQUEST:
                    // update the request to processing receive state with the given action.

                    RequestMessage requestMessage = RequestMessage.fromJson(jsonMessage);

                    System.out.println("********************* Content:  " + requestMessage);

                    receiveRequest(requestMessage);

                    getCommunicationNetworkServiceConnectionManager().closeConnection(requestMessage.getSenderPublicKey());
                    break;

                default:
                    throw new CantHandleNewMessagesException(
                            "message type: " +networkServiceMessage.getMessageType().name(),
                            "Message type not handled."
                    );
            }

        } catch (Exception e) {
            System.out.println(e.toString());
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }

        try {
            getCommunicationNetworkServiceConnectionManager().getIncomingMessageDao().markAsRead(fermatMessage);
        } catch (com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.exceptions.CantUpdateRecordDataBaseException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
    }

    /**
     * I indicate to the Agent the action that it must take:
     * - Protocol State: PROCESSING_RECEIVE.    .
     */
    private void receiveConnectionInformation(final InformationMessage informationMessage) throws CantHandleNewMessagesException {

        try {

            ProtocolState state = ProtocolState.PENDING_LOCAL_ACTION;

            switch (informationMessage.getAction()) {

                case ACCEPT:

                    chatActorNetworkServiceDao.acceptConnection(
                            informationMessage.getRequestId(),
                            state
                    );
                    break;

                case DENY:

                    chatActorNetworkServiceDao.denyConnection(
                            informationMessage.getRequestId(),
                            state
                    );
                    break;

                case DISCONNECT:

                    chatActorNetworkServiceDao.disconnectConnection(
                            informationMessage.getRequestId(),
                            state
                    );
                    break;

                default:
                    throw new CantHandleNewMessagesException(
                            "action not supported: " +informationMessage.getAction(),
                            "action not handled."
                    );
            }

            FermatEvent eventToRaise = eventManager.getNewEvent(EventType.CHAT_ACTOR_CONNECTION_UPDATE_CONNECTION);
            eventToRaise.setSource(eventSource);
            eventManager.raiseEvent(eventToRaise);

        } catch(CantAcceptConnectionRequestException | CantDenyConnectionRequestException | ConnectionRequestNotFoundException | CantDisconnectException e) {
            // i inform to error manager the error.
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantHandleNewMessagesException(e, "", "Error in Chat ANS Dao.");
        } catch(Exception e) {

            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantHandleNewMessagesException(e, "", "Unhandled Exception.");
        }
    }



    public final void runExposeIdentityThread(){

        final PluginVersionReference pluginReference = getPluginVersionReference();

        if(fermatManager.areIdentitiesToExpose()){

            executor.submit(new Runnable() {

                @Override

                public void run() {

                    try {

                        Thread.sleep(3000);

                        fermatManager.exposeIdentitiesInWait();

                    } catch (CantExposeIdentityException | InterruptedException e) {

                        errorManager.reportUnexpectedPluginException(pluginReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);

                    } catch (Exception e) {

                        e.printStackTrace();

                    }

                }

            });

        }

    }




    /**
     * I indicate to the Agent the action that it must take:
     * - Protocol State: PROCESSING_RECEIVE.
     * - Type          : RECEIVED.
     */
    private void receiveRequest(final RequestMessage requestMessage) throws CantHandleNewMessagesException {

        try {

            if (chatActorNetworkServiceDao.existsConnectionRequest(requestMessage.getRequestId()))
                return;


            final ProtocolState           state  = ProtocolState.PENDING_LOCAL_ACTION;
            final RequestType type   = RequestType  .RECEIVED             ;

            final ChatConnectionInformation connectionInformation = new ChatConnectionInformation(
                    requestMessage.getRequestId(),
                    requestMessage.getSenderPublicKey(),
                    requestMessage.getSenderActorType(),
                    requestMessage.getSenderAlias(),
                    requestMessage.getSenderImage(),
                    requestMessage.getDestinationPublicKey(),
                    requestMessage.getSentTime()
            );

            chatActorNetworkServiceDao.createConnectionRequest(
                    connectionInformation,
                    state,
                    type,
                    requestMessage.getRequestAction()
            );

            FermatEvent eventToRaise = eventManager.getNewEvent(EventType.CHAT_ACTOR_CONNECTION_REQUEST_NEW);
            eventToRaise.setSource(eventSource);
            eventManager.raiseEvent(eventToRaise);

        } catch(CantRequestConnectionException e) {
            // i inform to error manager the error.
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantHandleNewMessagesException(e, "", "Error in Chat ANS Dao.");
        } catch(Exception e) {

            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantHandleNewMessagesException(e, "", "Unhandled Exception.");
        }
    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        return new ChatActorNetworkServiceDeveloperDatabaseFactory(
                pluginDatabaseSystem,
                pluginId
        ).getDatabaseList(
                developerObjectFactory
        );
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return new ChatActorNetworkServiceDeveloperDatabaseFactory(
                pluginDatabaseSystem,
                pluginId
        ).getDatabaseTableList(
                developerObjectFactory,
                developerDatabase
        );
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        return new ChatActorNetworkServiceDeveloperDatabaseFactory(
                pluginDatabaseSystem,
                pluginId
        ).getDatabaseTableContent(
                developerObjectFactory,
                developerDatabase     ,
                developerDatabaseTable
        );
    }
}
