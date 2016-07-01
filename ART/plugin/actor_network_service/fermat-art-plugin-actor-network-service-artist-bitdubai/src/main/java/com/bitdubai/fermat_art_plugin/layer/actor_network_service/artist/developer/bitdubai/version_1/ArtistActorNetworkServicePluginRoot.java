package com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_art_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_art_api.layer.actor_network_service.enums.ProtocolState;
import com.bitdubai.fermat_art_api.layer.actor_network_service.enums.RequestType;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantAcceptConnectionRequestException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantDenyConnectionRequestException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantDisconnectException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantRequestConnectionException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantRequestExternalPlatformInformationException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.ConnectionRequestNotFoundException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistConnectionInformation;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistConnectionRequest;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.database.ArtistActorNetworkServiceDao;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.database.ArtistActorNetworkServiceDeveloperDatabaseFactory;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.exceptions.CantFindRequestException;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.exceptions.CantHandleNewMessagesException;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.exceptions.CantInitializeDatabaseException;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.messages.InformationMessage;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.messages.NetworkServiceMessage;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.messages.RequestMessage;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.structure.ArtistActorNetworkServiceExternalPlatformInformationRequest;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.structure.ArtistActorNetworkServiceManager;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.abstract_classes.AbstractActorNetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.exceptions.RecordNotFoundException;

import java.util.List;

public class ArtistActorNetworkServicePluginRoot
        extends AbstractActorNetworkService
        implements DatabaseManagerForDevelopers {

    /**
     * Represents the plugin database DAO.
     */
    ArtistActorNetworkServiceDao artistActorNetworkServiceDao;

    /**
     * Represents the plugin manager
     */
    ArtistActorNetworkServiceManager fermatManager;

    /**
     * Constructor of the Network Service.
     */
    public ArtistActorNetworkServicePluginRoot() {

        super(
                new PluginVersionReference(new Version()),
                EventSource.ACTOR_NETWORK_SERVICE_ARTIST,
                NetworkServiceType.ARTIST_ACTOR
        );
    }

    /**
     * Service Interface implementation
     */
    @Override
    public void onActorNetworkServiceStart() throws CantStartPluginException {

        try {

            artistActorNetworkServiceDao = new ArtistActorNetworkServiceDao(pluginDatabaseSystem, pluginFileSystem, pluginId);

            artistActorNetworkServiceDao.initialize();

            fermatManager = new ArtistActorNetworkServiceManager(
                    artistActorNetworkServiceDao ,
                    this
            );

        } catch(final CantInitializeDatabaseException e) {

            this.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(e, "", "Problem initializing Artist ans dao.");
        }
    }

    @Override
    public FermatManager getManager() {
        return fermatManager;
    }

    @Override
    public synchronized void onSentMessage(com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.entities.NetworkServiceMessage networkServiceMessage2) {
        System.out.println("************ Mensaje supuestamente enviado artist actor network service");

        try {

            String jsonMessage = networkServiceMessage2.getContent();

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
    public void onNewMessageReceived(com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.entities.NetworkServiceMessage fermatMessage) {
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

                    //String destinationPublicKey = artistActorNetworkServiceDao.getDestinationPublicKey(informationMessage.getRequestId());

                    //getCommunicationNetworkServiceConnectionManager().closeConnection(destinationPublicKey);
                    break;

                case CONNECTION_REQUEST:
                    // update the request to processing receive state with the given action.

                    RequestMessage requestMessage = RequestMessage.fromJson(jsonMessage);

                    System.out.println("********************* Content:  " + requestMessage);

                    receiveRequest(requestMessage);

                    //getCommunicationNetworkServiceConnectionManager().closeConnection(requestMessage.getSenderPublicKey());
                    break;

                case INFORMATION_REQUEST:
                    ArtistActorNetworkServiceExternalPlatformInformationRequest
                            informationRequestMessage =
                            ArtistActorNetworkServiceExternalPlatformInformationRequest.fromJson(
                                    jsonMessage);
                    System.out.println("********************* Content:  " + informationRequestMessage);
                    receiveQuotesRequest(informationRequestMessage);

                    //getCommunicationNetworkServiceConnectionManager().
                    //        closeConnection(informationRequestMessage.getRequesterPublicKey());
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
            getNetworkServiceConnectionManager().getIncomingMessagesDao().markAsRead(fermatMessage);
        } catch (CantUpdateRecordDataBaseException | RecordNotFoundException e){
            e.printStackTrace();
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

    /**
     * I indicate to the Agent the action that it must take:
     * - Protocol State: PROCESSING_RECEIVE.
     * - Type          : RECEIVED.
     */
    private void receiveRequest(final RequestMessage requestMessage) throws CantHandleNewMessagesException {

        try {

            if (artistActorNetworkServiceDao.existsConnectionRequest(requestMessage.getRequestId()))
                return;


            final ProtocolState           state  = ProtocolState.PENDING_LOCAL_ACTION;
            final RequestType type   = RequestType  .RECEIVED             ;
            PlatformComponentType platformComponentType = getPlatformComponentType(requestMessage.getSenderActorType());
            final ArtistConnectionInformation connectionInformation = new ArtistConnectionInformation(
                    requestMessage.getRequestId(),
                    requestMessage.getSenderPublicKey(),
                    platformComponentType,
                    requestMessage.getSenderAlias(),
                    requestMessage.getSenderImage(),
                    requestMessage.getDestinationPublicKey(),
                    PlatformComponentType.ART_ARTIST,
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

    private PlatformComponentType getPlatformComponentType(Actors actor){
        PlatformComponentType actorType;
        switch (actor){
            case ART_ARTIST:
                actorType = PlatformComponentType.ART_ARTIST;
                break;
            case ART_FAN:
                actorType = PlatformComponentType.ART_FAN;
                break;
            default:
                actorType = PlatformComponentType.ART_ARTIST;
                break;
        }
        return actorType;
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

    @Override
    public List<DeveloperDatabase> getDatabaseList(
            final DeveloperObjectFactory developerObjectFactory) {

        return new ArtistActorNetworkServiceDeveloperDatabaseFactory(
                pluginDatabaseSystem,
                pluginId
        ).getDatabaseList(
                developerObjectFactory
        );
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(
            final DeveloperObjectFactory developerObjectFactory,
            final DeveloperDatabase      developerDatabase     ) {

        return new ArtistActorNetworkServiceDeveloperDatabaseFactory(
                pluginDatabaseSystem,
                pluginId
        ).getDatabaseTableList(
                developerObjectFactory,
                developerDatabase
        );
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(
            final DeveloperObjectFactory developerObjectFactory,
            final DeveloperDatabase      developerDatabase     ,
            final DeveloperDatabaseTable developerDatabaseTable) {

        return new ArtistActorNetworkServiceDeveloperDatabaseFactory(
                pluginDatabaseSystem,
                pluginId
        ).getDatabaseTableContent(
                developerObjectFactory,
                developerDatabase     ,
                developerDatabaseTable
        );
    }
}