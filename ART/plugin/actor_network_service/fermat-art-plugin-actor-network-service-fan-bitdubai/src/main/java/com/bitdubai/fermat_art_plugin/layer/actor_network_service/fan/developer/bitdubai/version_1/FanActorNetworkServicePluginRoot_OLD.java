package com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1;



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
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_art_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_art_api.layer.actor_network_service.enums.ProtocolState;
import com.bitdubai.fermat_art_api.layer.actor_network_service.enums.RequestType;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantAcceptConnectionRequestException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantDenyConnectionRequestException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantDisconnectException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantExposeIdentityException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantListArtistsException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantRequestConnectionException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.ConnectionRequestNotFoundException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.ActorSearch;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistConnectionInformation;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistConnectionRequest;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.util.FanConnectionInformation;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.util.FanConnectionRequest;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.util.FanExposingData;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.database.FanActorNetworkServiceDao;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.database.FanActorNetworkServiceDeveloperDatabaseFactory;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.event_handler.FanCustomeP2PCompletedConnectionRegistrationEventHandler;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.exceptions.CantFindRequestException;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.exceptions.CantHandleNewMessagesException;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.exceptions.CantInitializeDatabaseException;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.messages.InformationMessage;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.messages.NetworkServiceMessage;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.messages.RequestMessage;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.structure.FanActorNetworkServiceManager;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.base.AbstractNetworkServiceBase;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;*/

/**
 * Created by Gabriel Araujo 1/04/2016.
 * @author gaboHub
 * @version 1.0
 * @since Java JDK 1.7
 */
//@PluginInfo(difficulty = PluginInfo.Dificulty.MEDIUM, maintainerMail = "gabe_512@hotmail.com", createdBy = "gabohub", layer = Layers.ACTOR_NETWORK_SERVICE, platform = Platforms.ART_PLATFORM, plugin = Plugins.FAN)
public class FanActorNetworkServicePluginRoot_OLD /*extends AbstractNetworkServiceBase implements DatabaseManagerForDevelopers*/ {


    /*FanActorNetworkServiceDao fanActorNetworkServiceDao;
    
    FanActorNetworkServiceManager fanActorNetworkServiceManager;

    *//**
     * Hold the listeners references
     *//*
    private List<FermatEventListener> listenersAdded;

    private ExecutorService executor;
    *//**
     * Constructor of the Network Service.
     *//*
    public FanActorNetworkServicePluginRoot_OLD() {

        super(
                new PluginVersionReference(new Version()),
                EventSource.ACTOR_NETWORK_SERVICE_FAN,
                PlatformComponentType.NETWORK_SERVICE,
                NetworkServiceType.FAN_ACTOR,
                "Fan",
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

            fanActorNetworkServiceDao = new FanActorNetworkServiceDao(pluginDatabaseSystem, pluginFileSystem, pluginId);

            fanActorNetworkServiceDao.initialize();

            fanActorNetworkServiceManager = new FanActorNetworkServiceManager(
                    getCommunicationsClientConnection(),
                    fanActorNetworkServiceDao,
                    this,
                    errorManager                       ,
                    getPluginVersionReference()
            );

            FermatEventListener fermatEventListener = eventManager.getNewListener(P2pEventType.COMPLETE_COMPONENT_REGISTRATION_NOTIFICATION);
            fermatEventListener.setEventHandler(new FanCustomeP2PCompletedConnectionRegistrationEventHandler(this));
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

        } catch(final CantInitializeDatabaseException e) {

            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(e, "", "Problem initializing artist ans dao.");
        }
    }
    private void checkFailedDeliveryTime(String destinationPublicKey)
    {
        try{

            List<FanConnectionRequest> fanConnectionRequests = fanActorNetworkServiceDao.getConnectionRequestByDestinationPublicKey(destinationPublicKey);

            //if I try to send more than 5 times I put it on hold
            for (FanConnectionRequest record : fanConnectionRequests) {

                if(!record.getProtocolState().getCode().equals(ProtocolState.WAITING_RECEIPT_CONFIRMATION.getCode()))
                {
                    if(record.getSentCount() > 10 )
                    {
                        //  if(record.getSentCount() > 20)
                        //  {
                        //reprocess at two hours
                        //  reprocessTimer =  2 * 3600 * 1000;
                        // }

                        fanActorNetworkServiceDao.delete(record.getRequestId());
                    }
                    else
                    {
                        record.setSentCount(record.getSentCount() + 1);
                        fanActorNetworkServiceDao.updateConnectionRequest(record);
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
    public FermatManager getManager() {
        return fanActorNetworkServiceManager;
    }

    @Override
    public void pause() {

        fanActorNetworkServiceManager.setPlatformComponentProfile(null);
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

        fanActorNetworkServiceManager.setPlatformComponentProfile(null);
        getCommunicationNetworkServiceConnectionManager().stop();
        executor.shutdownNow();
        super.stop();
    }

    @Override
    public final void onSentMessage(final FermatMessage fermatMessage) {
        System.out.println("************ Mensaje supuestamente enviado fan actor network service");

        try {

            String jsonMessage = fermatMessage.getContent();

            NetworkServiceMessage networkServiceMessage = NetworkServiceMessage.fromJson(jsonMessage);

            System.out.println("********************* Message Sent Type:  " + networkServiceMessage.getMessageType());

            switch (networkServiceMessage.getMessageType()) {

                case CONNECTION_INFORMATION:
                    InformationMessage informationMessage = InformationMessage.fromJson(jsonMessage);

                    fanActorNetworkServiceDao.confirmActorConnectionRequest(informationMessage.getRequestId());
                    break;

                case CONNECTION_REQUEST:
                    // update the request to processing receive state with the given action.

                    RequestMessage requestMessage = RequestMessage.fromJson(jsonMessage);

                    fanActorNetworkServiceDao.confirmActorConnectionRequest(requestMessage.getRequestId());
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

        System.out.println("****** FAN ACTOR NETWORK SERVICE NEW MESSAGE RECEIVED: " + fermatMessage);
        try {

            String jsonMessage = fermatMessage.getContent();

            NetworkServiceMessage networkServiceMessage = NetworkServiceMessage.fromJson(jsonMessage);

            System.out.println("********************* Message Type:  " + networkServiceMessage.getMessageType());

            switch (networkServiceMessage.getMessageType()) {

                case CONNECTION_INFORMATION:
                    InformationMessage informationMessage = InformationMessage.fromJson(jsonMessage);

                    System.out.println("********************* Content:  " + informationMessage);

                    receiveConnectionInformation(informationMessage);

                    String destinationPublicKey = fanActorNetworkServiceDao.getDestinationPublicKey(informationMessage.getRequestId());

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
            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }

        try {
            getCommunicationNetworkServiceConnectionManager().getIncomingMessageDao().markAsRead(fermatMessage);
        } catch (com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.exceptions.CantUpdateRecordDataBaseException e) {
            e.printStackTrace();
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

                    fanActorNetworkServiceDao.acceptConnection(
                            informationMessage.getRequestId(),
                            state
                    );
                    break;

                case DENY:

                    fanActorNetworkServiceDao.denyConnection(
                            informationMessage.getRequestId(),
                            state
                    );
                    break;

                case DISCONNECT:

                    fanActorNetworkServiceDao.disconnectConnection(
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

            if (fanActorNetworkServiceDao.existsConnectionRequest(requestMessage.getRequestId()))
                return;


            final ProtocolState           state  = ProtocolState.PENDING_LOCAL_ACTION;
            final RequestType type   = RequestType  .RECEIVED             ;

            final FanConnectionInformation connectionInformation = new FanConnectionInformation(
                    requestMessage.getRequestId(),
                    requestMessage.getSenderPublicKey(),
                    requestMessage.getSenderActorType(),
                    requestMessage.getSenderAlias(),
                    requestMessage.getSenderImage(),
                    requestMessage.getDestinationPublicKey(),
                    requestMessage.getDestinationActorType(),
                    requestMessage.getSentTime()
            );

            int sentCount = 1;
            fanActorNetworkServiceDao.createConnectionRequest(
                    connectionInformation,
                    state,
                    type,
                    requestMessage.getRequestAction(),
                    sentCount
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
    @Override
    protected void onNetworkServiceRegistered() {

        fanActorNetworkServiceManager.setPlatformComponentProfile(this.getNetworkServiceProfile());
        runExposeIdentityThread();
        //testCreateAndList();

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
                        FanConnectionRequest fanConnectionRequest = fanActorNetworkServiceDao.getConnectionRequestByDestinationPublicKey(remoteParticipant.getIdentityPublicKey()).get(0);
                        final FanConnectionInformation connectionInformation = new FanConnectionInformation(
                                fanConnectionRequest.getRequestId(),
                                fanConnectionRequest.getSenderPublicKey(),
                                fanConnectionRequest.getSenderActorType(),
                                fanConnectionRequest.getSenderAlias(),
                                fanConnectionRequest.getSenderImage(),
                                fanConnectionRequest.getDestinationPublicKey(),
                                fanConnectionRequest.getDestinationActorType(),
                                fanConnectionRequest.getSentTime()
                        );
                        if(connectionInformation.getConnectionId() != null)
                            fanActorNetworkServiceManager.sendFailedMessage(connectionInformation);
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

    public final void runExposeIdentityThread(){

        final PluginVersionReference pluginReference = getPluginVersionReference();

        if(fanActorNetworkServiceManager.areIdentityToExpose()){
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(3000);
                        fanActorNetworkServiceManager.exposeIdentitiesInWait();
                    } catch (CantExposeIdentityException | InterruptedException e) {
                        errorManager.reportUnexpectedPluginException(pluginReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }


    *//*private void testCreateAndList() {
        ECCKeyPair identity = new ECCKeyPair();
        try {
            fanActorNetworkServiceManager.exposeIdentity(new FanExposingData(identity.getPublicKey(), "El Gabo fan", ""));
            ActorSearch<FanExposingData> artistActorNetworkServiceSearch = fanActorNetworkServiceManager.getSearch();
            List<FanExposingData> artistExposingDatas = artistActorNetworkServiceSearch.getResult(PlatformComponentType.ART_ARTIST);
            for (FanExposingData artistExposingData :
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

    }*//*

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        return new FanActorNetworkServiceDeveloperDatabaseFactory(
                pluginDatabaseSystem,
                pluginId
        ).getDatabaseList(
                developerObjectFactory
        );
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return new FanActorNetworkServiceDeveloperDatabaseFactory(
                pluginDatabaseSystem,
                pluginId
        ).getDatabaseTableList(
                developerObjectFactory,
                developerDatabase
        );
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        return new FanActorNetworkServiceDeveloperDatabaseFactory(
                pluginDatabaseSystem,
                pluginId
        ).getDatabaseTableContent(
                developerObjectFactory,
                developerDatabase     ,
                developerDatabaseTable
        );
    }*/

}
