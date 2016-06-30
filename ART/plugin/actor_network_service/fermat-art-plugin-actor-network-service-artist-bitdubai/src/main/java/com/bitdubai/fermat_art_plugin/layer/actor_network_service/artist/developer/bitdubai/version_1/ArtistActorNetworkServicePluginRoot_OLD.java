package com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1;



/*import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
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
import com.bitdubai.fermat_api.layer.all_definition.util.Validate;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_art_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_art_api.layer.actor_network_service.enums.NotificationDescriptor;
import com.bitdubai.fermat_art_api.layer.actor_network_service.enums.ProtocolState;
import com.bitdubai.fermat_art_api.layer.actor_network_service.enums.RequestType;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantAcceptConnectionRequestException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantDenyConnectionRequestException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantDisconnectException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantExposeIdentityException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantListArtistsException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantRequestConnectionException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantRequestExternalPlatformInformationException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.ConnectionRequestNotFoundException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.ActorSearch;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistConnectionInformation;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistConnectionRequest;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistExposingData;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.database.ArtistActorNetworkServiceDao;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.database.ArtistActorNetworkServiceDeveloperDatabaseFactory;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.event_handler.ArtistCustomeP2PCompletedConnectionRegistrationEventHandler;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.exceptions.CantFindRequestException;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.exceptions.CantHandleNewMessagesException;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.exceptions.CantInitializeDatabaseException;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.messages.InformationMessage;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.messages.NetworkServiceMessage;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.messages.RequestMessage;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.structure.ArtistActorNetworkServiceExternalPlatformInformationRequest;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.structure.ArtistActorNetworkServiceManager;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.base.AbstractNetworkServiceBase;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;*/

/**
 * Created by Gabriel Araujo 1/04/2016.
 * @author gaboHub
 * @version 1.0
 * @since Java JDK 1.7
 */
//@PluginInfo(difficulty = PluginInfo.Dificulty.MEDIUM, maintainerMail = "gabe_512@hotmail.com", createdBy = "gabohub", layer = Layers.ACTOR_NETWORK_SERVICE, platform = Platforms.ART_PLATFORM, plugin = Plugins.ARTIST)
public class ArtistActorNetworkServicePluginRoot_OLD /*extends AbstractNetworkServiceBase implements DatabaseManagerForDevelopers*/ {


    /*ArtistActorNetworkServiceDao artistActorNetworkServiceDao;
    
    ArtistActorNetworkServiceManager artistActorNetworkServiceManager;
    *//**
     * Hold the listeners references
     *//*
    private List<FermatEventListener> listenersAdded;

    private ExecutorService executor;

    *//**
     * Constructor of the Network Service.
     *//*
    public ArtistActorNetworkServicePluginRoot_OLD() {

        super(
                new PluginVersionReference(new Version()),
                EventSource.ACTOR_NETWORK_SERVICE_ARTIST,
                PlatformComponentType.NETWORK_SERVICE,
                NetworkServiceType.ARTIST_ACTOR,
                "Artist",
                null
        );

        listenersAdded = new ArrayList<>();
        executor = Executors.newSingleThreadExecutor();
    }

    *//**
     * Service Interface implementation
     *//*
    @Override
    public void onStart() throws CantStartPluginException {

        try {

            artistActorNetworkServiceDao = new ArtistActorNetworkServiceDao(pluginDatabaseSystem, pluginFileSystem, pluginId);

            artistActorNetworkServiceDao.initialize();

            artistActorNetworkServiceManager = new ArtistActorNetworkServiceManager(
                    getCommunicationsClientConnection(),
                    artistActorNetworkServiceDao ,
                    this,
                    errorManager                       ,
                    getPluginVersionReference()
            );

            FermatEventListener fermatEventListener = eventManager.getNewListener(P2pEventType.COMPLETE_COMPONENT_REGISTRATION_NOTIFICATION);
            fermatEventListener.setEventHandler(new ArtistCustomeP2PCompletedConnectionRegistrationEventHandler(this));
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

        } catch(final CantInitializeDatabaseException e) {

            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(e, "", "Problem initializing artist ans dao.");
        }
    }

    @Override
    public FermatManager getManager() {
        return artistActorNetworkServiceManager;
    }

    @Override
    public void pause() {

        artistActorNetworkServiceManager.setPlatformComponentProfile(null);
        getCommunicationNetworkServiceConnectionManager().pause();
        executor.shutdownNow();
        super.pause();
    }

    @Override
    public void resume() {

        // resume connections manager.
        getCommunicationNetworkServiceConnectionManager().resume();
        executor = Executors.newSingleThreadExecutor();
        super.resume();
    }

    @Override
    public void stop() {

        artistActorNetworkServiceManager.setPlatformComponentProfile(null);
        getCommunicationNetworkServiceConnectionManager().stop();
        executor.shutdownNow();
        super.stop();
    }

    @Override
    public final void onSentMessage(final FermatMessage fermatMessage) {
        System.out.println("************ Mensaje supuestamente enviado artist actor network service");

        try {

            String jsonMessage = fermatMessage.getContent();

            NetworkServiceMessage networkServiceMessage = NetworkServiceMessage.fromJson(jsonMessage);

            System.out.println("********************* Message Sent Type:  " + networkServiceMessage.getMessageType());

            switch (networkServiceMessage.getMessageType()) {

                case CONNECTION_INFORMATION:
                    InformationMessage informationMessage = InformationMessage.fromJson(jsonMessage);

                    artistActorNetworkServiceDao.confirmActorConnectionRequest(informationMessage.getRequestId());
                    break;

                case CONNECTION_REQUEST:
                    // update the request to processing receive state with the given action.

                    RequestMessage requestMessage = RequestMessage.fromJson(jsonMessage);

                    artistActorNetworkServiceDao.confirmActorConnectionRequest(requestMessage.getRequestId());
                    break;
                case INFORMATION_REQUEST:

                    ArtistActorNetworkServiceExternalPlatformInformationRequest quotesRequestMessage
                            = ArtistActorNetworkServiceExternalPlatformInformationRequest.fromJson(
                            jsonMessage);
                    artistActorNetworkServiceDao.confirmInformationRequest(
                            quotesRequestMessage.getRequestId());
                    break;
                default:
                    throw new CantHandleNewMessagesException(
                            "message type: " +networkServiceMessage.getMessageType().name(),
                            "Message type not handled."
                    );
            }

        } catch (Exception e) {
            System.out.println(e.toString());
            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
    }
    @Override
    public void onNewMessagesReceive(FermatMessage fermatMessage) {

        System.out.println("****** ARTIST ACTOR NETWORK SERVICE NEW MESSAGE RECEIVED: " + fermatMessage);
        try {

            String jsonMessage = fermatMessage.getContent();

            NetworkServiceMessage networkServiceMessage = NetworkServiceMessage.fromJson(jsonMessage);

            System.out.println("********************* Message Type:  " + networkServiceMessage.getMessageType());

            switch (networkServiceMessage.getMessageType()) {

                case CONNECTION_INFORMATION:
                    InformationMessage informationMessage = InformationMessage.fromJson(jsonMessage);

                    System.out.println("********************* Content:  " + informationMessage);

                    receiveConnectionInformation(informationMessage);

                    String destinationPublicKey = artistActorNetworkServiceDao.getDestinationPublicKey(informationMessage.getRequestId());

                    getCommunicationNetworkServiceConnectionManager().closeConnection(destinationPublicKey);
                    break;

                case CONNECTION_REQUEST:
                    // update the request to processing receive state with the given action.

                    RequestMessage requestMessage = RequestMessage.fromJson(jsonMessage);

                    System.out.println("********************* Content:  " + requestMessage);

                    receiveRequest(requestMessage);

                    getCommunicationNetworkServiceConnectionManager().closeConnection(requestMessage.getSenderPublicKey());
                    break;

                case INFORMATION_REQUEST:
                    ArtistActorNetworkServiceExternalPlatformInformationRequest
                            informationRequestMessage =
                            ArtistActorNetworkServiceExternalPlatformInformationRequest.fromJson(
                                    jsonMessage);
                    System.out.println("********************* Content:  " + informationRequestMessage);
                    receiveQuotesRequest(informationRequestMessage);

                    getCommunicationNetworkServiceConnectionManager().
                            closeConnection(informationRequestMessage.getRequesterPublicKey());
                    break;
                default:
                    throw new CantHandleNewMessagesException(
                            "message type: " +networkServiceMessage.getMessageType().name(),
                            "Message type not handled."
                    );
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            errorManager.reportUnexpectedPluginException(
                    this.getPluginVersionReference(),
                    UnexpectedPluginExceptionSeverity.
                            DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
        }
        try {
            getCommunicationNetworkServiceConnectionManager().getIncomingMessageDao().markAsRead(fermatMessage);
        } catch (com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.exceptions.CantUpdateRecordDataBaseException e) {
            e.printStackTrace();
        }
    }

    private void receiveQuotesRequest(
            final ArtistActorNetworkServiceExternalPlatformInformationRequest informationRequest)
            throws CantHandleNewMessagesException {
        try {
            ArtistConnectionRequest informationRequestInDatabase =
                    artistActorNetworkServiceDao.getConnectionRequest(
                            informationRequest.getRequestId());
            if (informationRequestInDatabase.getRequestType() == RequestType.SENT) {
                artistActorNetworkServiceDao.answerInformationRequest(
                        informationRequest.getRequestId(),
                        informationRequest.getUpdateTime(),
                        informationRequest.listInformation(),
                        ProtocolState.PENDING_LOCAL_ACTION
                );
                FermatEvent eventToRaise = eventManager.getNewEvent(
                        EventType.ARTIST_CONNECTION_REQUEST_UPDATES);
                eventToRaise.setSource(eventSource);
                eventManager.raiseEvent(eventToRaise);
            }
        } catch (CantFindRequestException quotesRequestNotFoundException) {

            try {
                final ProtocolState state = ProtocolState.PENDING_LOCAL_ACTION;
                final RequestType type = RequestType.RECEIVED;

                artistActorNetworkServiceDao.createExternalPlatformInformationRequest(
                        informationRequest.getRequestId(),
                        informationRequest.getRequesterPublicKey(),
                        informationRequest.getRequesterActorType(),
                        informationRequest.getArtistPublicKey(),
                        state,
                        type
                );

                FermatEvent eventToRaise = eventManager.getNewEvent(
                        EventType.ARTIST_CONNECTION_REQUEST_UPDATES);
                eventToRaise.setSource(eventSource);
                eventManager.raiseEvent(eventToRaise);
            } catch (CantRequestExternalPlatformInformationException e) {
                errorManager.reportUnexpectedPluginException(
                        this.getPluginVersionReference(),
                        UnexpectedPluginExceptionSeverity.
                                DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantHandleNewMessagesException(e, "", "Error in Crypto Broker ANS Dao.");
            }
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(
                    this.getPluginVersionReference(),
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantHandleNewMessagesException(
                    e,
                    "",
                    "Unhandled Exception.");
        }
    }

    *//**
     * I indicate to the Agent the action that it must take:
     * - Protocol State: PROCESSING_RECEIVE.    .
     *//*
    private void receiveConnectionInformation(final InformationMessage informationMessage) throws CantHandleNewMessagesException {

        try {

            ProtocolState state = ProtocolState.PENDING_LOCAL_ACTION;

            switch (informationMessage.getAction()) {

                case ACCEPT:

                    artistActorNetworkServiceDao.acceptConnection(
                            informationMessage.getRequestId(),
                            state
                    );
                    break;

                case DENY:

                    artistActorNetworkServiceDao.denyConnection(
                            informationMessage.getRequestId(),
                            state
                    );
                    break;

                case DISCONNECT:

                    artistActorNetworkServiceDao.disconnectConnection(
                            informationMessage.getRequestId(),
                            state
                    );
                    break;
                case CANCEL:
                    artistActorNetworkServiceDao.cancelConnection(informationMessage.getRequestId(),
                            state);
                    break;
                default:
                    throw new CantHandleNewMessagesException(
                            "action not supported: " +informationMessage.getAction(),
                            "action not handled."
                    );
            }

            FermatEvent eventToRaise = eventManager.getNewEvent(EventType.ARTIST_CONNECTION_REQUEST_UPDATES);
            eventToRaise.setSource(eventSource);
            eventManager.raiseEvent(eventToRaise);

        } catch(CantAcceptConnectionRequestException | CantDenyConnectionRequestException | ConnectionRequestNotFoundException | CantDisconnectException e) {
            // i inform to error manager the error.
            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantHandleNewMessagesException(e, "", "Error in Artist ANS Dao.");
        } catch(Exception e) {

            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantHandleNewMessagesException(e, "", "Unhandled Exception.");
        }
    }

    *//**
     * I indicate to the Agent the action that it must take:
     * - Protocol State: PROCESSING_RECEIVE.
     * - Type          : RECEIVED.
     *//*
    private void receiveRequest(final RequestMessage requestMessage) throws CantHandleNewMessagesException {

        try {

            if (artistActorNetworkServiceDao.existsConnectionRequest(requestMessage.getRequestId()))
                return;


            final ProtocolState           state  = ProtocolState.PENDING_LOCAL_ACTION;
            final RequestType type   = RequestType  .RECEIVED             ;

            final ArtistConnectionInformation connectionInformation = new ArtistConnectionInformation(
                    requestMessage.getRequestId(),
                    requestMessage.getSenderPublicKey(),
                    requestMessage.getSenderActorType(),
                    requestMessage.getSenderAlias(),
                    requestMessage.getSenderImage(),
                    requestMessage.getDestinationPublicKey(),
                    requestMessage.getDestinationActorType(),
                    requestMessage.getSentTime()
            );

            artistActorNetworkServiceDao.createConnectionRequest(
                    connectionInformation,
                    state,
                    type,
                    requestMessage.getRequestAction(),
                    1
            );

            FermatEvent eventToRaise = eventManager.getNewEvent(EventType.ARTIST_CONNECTION_REQUEST_NEWS);
            eventToRaise.setSource(eventSource);
            eventManager.raiseEvent(eventToRaise);

        } catch(CantRequestConnectionException e) {
            // i inform to error manager the error.
            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantHandleNewMessagesException(e, "", "Error in Crypto Broker ANS Dao.");
        } catch(Exception e) {

            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantHandleNewMessagesException(e, "", "Unhandled Exception.");
        }
    }

    private void checkFailedDeliveryTime(String destinationPublicKey)
    {
        try{

            List<ArtistConnectionRequest> actorNetworkServiceRecordList = artistActorNetworkServiceDao.getConnectionRequestByDestinationPublicKey(destinationPublicKey);

            //if I try to send more than 5 times I put it on hold
            for (ArtistConnectionRequest record : actorNetworkServiceRecordList) {

                if(!record.getProtocolState().getCode().equals(ProtocolState.WAITING_RECEIPT_CONFIRMATION.getCode()))
                {
                    if(record.getSentCount() > 10 )
                    {
                        //  if(record.getSentCount() > 20)
                        //  {
                        //reprocess at two hours
                        //  reprocessTimer =  2 * 3600 * 1000;
                        // }

                        artistActorNetworkServiceDao.delete(record.getRequestId());
                    }
                    else
                    {
                        record.setSentCount(record.getSentCount() + 1);
                        artistActorNetworkServiceDao.updateConnectionRequest(record);
                    }
                }
            }


        }
        catch(Exception e)
        {
            System.out.println("INTRA USER NS EXCEPCION VERIFICANDO WAIT MESSAGE");
            e.printStackTrace();
        }

    }
    @Override
    protected void onNetworkServiceRegistered() {

        artistActorNetworkServiceManager.setPlatformComponentProfile(this.getNetworkServiceProfile());

        runExposeIdentityThread();
        //testCreateAndList();

    }

    public final void runExposeIdentityThread(){

        final PluginVersionReference pluginReference = getPluginVersionReference();

        if(artistActorNetworkServiceManager.areIdentitiesToExpose()){
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(3000);
                        artistActorNetworkServiceManager.exposeIdentitiesInWait();
                    } catch (CantExposeIdentityException | InterruptedException e) {
                        errorManager.reportUnexpectedPluginException(pluginReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
    @Override
    protected void onClientSuccessfulReconnect() {
        runExposeIdentityThread();
    }


    @Override
    protected void onFailureComponentConnectionRequest(final PlatformComponentProfile remoteParticipant) {

        if(isRegister()){
            final PluginVersionReference pluginVersionReference = getPluginVersionReference();
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(90000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    checkFailedDeliveryTime(remoteParticipant.getIdentityPublicKey());
                    try {
                        ArtistConnectionRequest artistConnectionRequest = artistActorNetworkServiceDao.getConnectionRequestByDestinationPublicKey(remoteParticipant.getIdentityPublicKey()).get(0);
                        final ArtistConnectionInformation connectionInformation = new ArtistConnectionInformation(
                                artistConnectionRequest.getRequestId(),
                                artistConnectionRequest.getSenderPublicKey(),
                                artistConnectionRequest.getSenderActorType(),
                                artistConnectionRequest.getSenderAlias(),
                                artistConnectionRequest.getSenderImage(),
                                artistConnectionRequest.getDestinationPublicKey(),
                                artistConnectionRequest.getDestinationActorType(),
                                artistConnectionRequest.getSentTime()
                        );
                        if(connectionInformation.getConnectionId() != null)
                            artistActorNetworkServiceManager.sendFailedMessage(connectionInformation);
                    } catch (CantFindRequestException e) {
                        e.printStackTrace();
                        errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                    } catch (ConnectionRequestNotFoundException e) {
                        e.printStackTrace();
                        errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                    }
                }
            });
        }
    }

    private void testCreateAndList(){
        ECCKeyPair identity = new ECCKeyPair();
        try {
            artistActorNetworkServiceManager.exposeIdentity(new ArtistExposingData(identity.getPublicKey(), "El Gabo artist", ""));
            ActorSearch<ArtistExposingData> artistActorNetworkServiceSearch = artistActorNetworkServiceManager.getSearch();
            List<ArtistExposingData> artistExposingDatas = artistActorNetworkServiceSearch.getResult();
            for (ArtistExposingData artistExposingData:
                    artistExposingDatas) {
                System.out.println("#############################\nArtistas registrados:"+artistExposingData.getAlias()+"\nPublicKey:"+artistExposingData.getPublicKey());
            }
        } catch (CantExposeIdentityException e) {
            e.printStackTrace();
            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (CantListArtistsException e) {
            e.printStackTrace();
            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }

    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        return new ArtistActorNetworkServiceDeveloperDatabaseFactory(
                pluginDatabaseSystem,
                pluginId
        ).getDatabaseList(
                developerObjectFactory
        );
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return new ArtistActorNetworkServiceDeveloperDatabaseFactory(
                pluginDatabaseSystem,
                pluginId
        ).getDatabaseTableList(
                developerObjectFactory,
                developerDatabase
        );
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        return new ArtistActorNetworkServiceDeveloperDatabaseFactory(
                pluginDatabaseSystem,
                pluginId
        ).getDatabaseTableContent(
                developerObjectFactory,
                developerDatabase     ,
                developerDatabaseTable
        );
    }*/

}
