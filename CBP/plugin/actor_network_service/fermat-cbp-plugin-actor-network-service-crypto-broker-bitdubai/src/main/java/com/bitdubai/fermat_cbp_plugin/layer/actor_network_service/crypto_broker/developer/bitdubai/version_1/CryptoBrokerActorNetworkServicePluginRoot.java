package com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1;

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
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.enums.ProtocolState;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.enums.RequestType;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantAcceptConnectionRequestException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantDenyConnectionRequestException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantDisconnectException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantRequestConnectionException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantRequestQuotesException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.ConnectionRequestNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.QuotesRequestNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.utils.CryptoBrokerConnectionInformation;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.database.CryptoBrokerActorNetworkServiceDao;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.database.CryptoBrokerActorNetworkServiceDeveloperDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.exceptions.CantFindRequestException;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.exceptions.CantHandleNewMessagesException;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.exceptions.CantInitializeDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.messages.InformationMessage;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.messages.NetworkServiceMessage;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.messages.RequestMessage;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.structure.CryptoBrokerActorNetworkServiceManager;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.structure.CryptoBrokerActorNetworkServiceQuotesRequest;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.base.AbstractNetworkServiceBase;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;

import java.util.List;

/**
 * The class <code>com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.CryptoBrokerActorNetworkServicePluginRoot</code>
 * bla bla bla.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/11/2015.

 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */
@PluginInfo(createdBy = "lnacosta", maintainerMail = "laion.cj91@gmail.com", platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.ACTOR_NETWORK_SERVICE, plugin = Plugins.CRYPTO_BROKER)
public class CryptoBrokerActorNetworkServicePluginRoot extends AbstractNetworkServiceBase implements DatabaseManagerForDevelopers {

    /**
     * Crypto Broker Actor Network Service member variables.
     */
    private CryptoBrokerActorNetworkServiceDao cryptoBrokerActorNetworkServiceDao;

    /**
     * Constructor of the Network Service.
     */
    public CryptoBrokerActorNetworkServicePluginRoot() {

        super(
                new PluginVersionReference(new Version()),
                EventSource.ACTOR_NETWORK_SERVICE_CRYPTO_BROKER,
                PlatformComponentType.NETWORK_SERVICE,
                NetworkServiceType.CRYPTO_BROKER,
                "Crypto Broker Actor Network Service",
                null
        );
    }

    /**
     * Service Interface implementation
     */
    @Override
    public void onStart() throws CantStartPluginException {

        try {

            cryptoBrokerActorNetworkServiceDao = new CryptoBrokerActorNetworkServiceDao(pluginDatabaseSystem, pluginFileSystem, pluginId);

            cryptoBrokerActorNetworkServiceDao.initialize();

            fermatManager = new CryptoBrokerActorNetworkServiceManager(
                    getCommunicationsClientConnection(),
                    cryptoBrokerActorNetworkServiceDao ,
                    this,
                    errorManager                       ,
                    getPluginVersionReference()
            );

        } catch(final CantInitializeDatabaseException e) {

            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(e, "", "Problem initializing crypto broker ans dao.");
        }
    }

    private CryptoBrokerActorNetworkServiceManager fermatManager;

    @Override
    public FermatManager getManager() {
        return fermatManager;
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
    }

    @Override
    public final void onSentMessage(final FermatMessage fermatMessage) {
        System.out.println("************ Mensaje supuestamente enviado crypto broker actor network service");

        try {

            String jsonMessage = fermatMessage.getContent();

            NetworkServiceMessage networkServiceMessage = NetworkServiceMessage.fromJson(jsonMessage);

            System.out.println("********************* Message Sent Type:  " + networkServiceMessage.getMessageType());

            switch (networkServiceMessage.getMessageType()) {

                case CONNECTION_INFORMATION:
                    InformationMessage informationMessage = InformationMessage.fromJson(jsonMessage);

                    cryptoBrokerActorNetworkServiceDao.confirmActorConnectionRequest(informationMessage.getRequestId());
                    break;

                case CONNECTION_REQUEST:
                    // update the request to processing receive state with the given action.

                    RequestMessage requestMessage = RequestMessage.fromJson(jsonMessage);

                    cryptoBrokerActorNetworkServiceDao.confirmActorConnectionRequest(requestMessage.getRequestId());
                    break;

                case QUOTES_REQUEST:

                    CryptoBrokerActorNetworkServiceQuotesRequest quotesRequestMessage = CryptoBrokerActorNetworkServiceQuotesRequest.fromJson(jsonMessage);

                    cryptoBrokerActorNetworkServiceDao.confirmQuotesRequest(quotesRequestMessage.getRequestId());
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

        System.out.println("****** CRYPTO BROKER ACTOR NETWORK SERVICE NEW MESSAGE RECEIVED: " + fermatMessage);
        try {

            String jsonMessage = fermatMessage.getContent();

            NetworkServiceMessage networkServiceMessage = NetworkServiceMessage.fromJson(jsonMessage);

            System.out.println("********************* Message Type:  " + networkServiceMessage.getMessageType());

            switch (networkServiceMessage.getMessageType()) {

                case CONNECTION_INFORMATION:
                    InformationMessage informationMessage = InformationMessage.fromJson(jsonMessage);

                    System.out.println("********************* Content:  " + informationMessage);

                    receiveConnectionInformation(informationMessage);

                    String destinationPublicKey = cryptoBrokerActorNetworkServiceDao.getDestinationPublicKey(informationMessage.getRequestId());

                    getCommunicationNetworkServiceConnectionManager().closeConnection(destinationPublicKey);
                    break;

                case CONNECTION_REQUEST:
                    // update the request to processing receive state with the given action.

                    RequestMessage requestMessage = RequestMessage.fromJson(jsonMessage);

                    System.out.println("********************* Content:  " + requestMessage);

                    receiveRequest(requestMessage);

                    getCommunicationNetworkServiceConnectionManager().closeConnection(requestMessage.getSenderPublicKey());
                    break;

                case QUOTES_REQUEST:

                    CryptoBrokerActorNetworkServiceQuotesRequest quotesRequestMessage = CryptoBrokerActorNetworkServiceQuotesRequest.fromJson(jsonMessage);

                    System.out.println("********************* Content:  " + quotesRequestMessage);

                    receiveQuotesRequest(quotesRequestMessage);

                    getCommunicationNetworkServiceConnectionManager().closeConnection(quotesRequestMessage.getRequesterPublicKey());
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

    private void receiveQuotesRequest(final CryptoBrokerActorNetworkServiceQuotesRequest quotesRequestReceived) throws CantHandleNewMessagesException {

        try {

            CryptoBrokerActorNetworkServiceQuotesRequest quotesRequestInDatabase = cryptoBrokerActorNetworkServiceDao.getQuotesRequest(quotesRequestReceived.getRequestId());

            if (quotesRequestInDatabase.getType() == RequestType.SENT) {
                cryptoBrokerActorNetworkServiceDao.answerQuotesRequest(
                        quotesRequestReceived.getRequestId(),
                        quotesRequestReceived.getUpdateTime(),
                        quotesRequestReceived.listInformation(),
                        ProtocolState.PENDING_LOCAL_ACTION
                );
                FermatEvent eventToRaise = eventManager.getNewEvent(EventType.CRYPTO_BROKER_QUOTES_REQUEST_UPDATES);
                eventToRaise.setSource(eventSource);
                eventManager.raiseEvent(eventToRaise);
            }

        } catch (QuotesRequestNotFoundException quotesRequestNotFoundException) {

            try {
                final ProtocolState state = ProtocolState.PENDING_LOCAL_ACTION;
                final RequestType type = RequestType.RECEIVED;

                cryptoBrokerActorNetworkServiceDao.createQuotesRequest(
                        quotesRequestReceived.getRequestId(),
                        quotesRequestReceived.getRequesterPublicKey(),
                        quotesRequestReceived.getRequesterActorType(),
                        quotesRequestReceived.getCryptoBrokerPublicKey(),
                        state,
                        type
                );

                FermatEvent eventToRaise = eventManager.getNewEvent(EventType.CRYPTO_BROKER_QUOTES_REQUEST_NEWS);
                eventToRaise.setSource(eventSource);
                eventManager.raiseEvent(eventToRaise);
            } catch (CantRequestQuotesException cantRequestQuotesException) {

                errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantRequestQuotesException);
                throw new CantHandleNewMessagesException(cantRequestQuotesException, "", "Error in Crypto Broker ANS Dao.");
            }

        } catch (CantFindRequestException e) {
            // i inform to error manager the error.
            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantHandleNewMessagesException(e, "", "Error in Crypto Broker ANS Dao.");
        } catch (Exception e) {

            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantHandleNewMessagesException(e, "", "Unhandled Exception.");
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

                    cryptoBrokerActorNetworkServiceDao.acceptConnection(
                            informationMessage.getRequestId(),
                            state
                    );
                    break;

                case DENY:

                    cryptoBrokerActorNetworkServiceDao.denyConnection(
                            informationMessage.getRequestId(),
                            state
                    );
                    break;

                case DISCONNECT:

                    cryptoBrokerActorNetworkServiceDao.disconnectConnection(
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

            FermatEvent eventToRaise = eventManager.getNewEvent(EventType.CRYPTO_BROKER_CONNECTION_REQUEST_UPDATES);
            eventToRaise.setSource(eventSource);
            eventManager.raiseEvent(eventToRaise);

        } catch(CantAcceptConnectionRequestException | CantDenyConnectionRequestException | ConnectionRequestNotFoundException | CantDisconnectException e) {
            // i inform to error manager the error.
            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantHandleNewMessagesException(e, "", "Error in Crypto Broker ANS Dao.");
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

            if (cryptoBrokerActorNetworkServiceDao.existsConnectionRequest(requestMessage.getRequestId()))
                return;


            final ProtocolState           state  = ProtocolState.PENDING_LOCAL_ACTION;
            final RequestType             type   = RequestType  .RECEIVED             ;

            final CryptoBrokerConnectionInformation connectionInformation = new CryptoBrokerConnectionInformation(
                    requestMessage.getRequestId(),
                    requestMessage.getSenderPublicKey(),
                    requestMessage.getSenderActorType(),
                    requestMessage.getSenderAlias(),
                    requestMessage.getSenderImage(),
                    requestMessage.getDestinationPublicKey(),
                    requestMessage.getSentTime()
            );

            cryptoBrokerActorNetworkServiceDao.createConnectionRequest(
                    connectionInformation,
                    state,
                    type,
                    requestMessage.getRequestAction()
            );

            FermatEvent eventToRaise = eventManager.getNewEvent(EventType.CRYPTO_BROKER_CONNECTION_REQUEST_NEWS);
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
    public List<DeveloperDatabase> getDatabaseList(final DeveloperObjectFactory developerObjectFactory) {

        return new CryptoBrokerActorNetworkServiceDeveloperDatabaseFactory(
                pluginDatabaseSystem,
                pluginId
        ).getDatabaseList(
                developerObjectFactory
        );
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(final DeveloperObjectFactory developerObjectFactory,
                                                             final DeveloperDatabase      developerDatabase     ) {

        return new CryptoBrokerActorNetworkServiceDeveloperDatabaseFactory(
                pluginDatabaseSystem,
                pluginId
        ).getDatabaseTableList(
                developerObjectFactory,
                developerDatabase
        );
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(final DeveloperObjectFactory developerObjectFactory,
                                                                      final DeveloperDatabase      developerDatabase     ,
                                                                      final DeveloperDatabaseTable developerDatabaseTable) {

        return new CryptoBrokerActorNetworkServiceDeveloperDatabaseFactory(
                pluginDatabaseSystem,
                pluginId
        ).getDatabaseTableContent(
                developerObjectFactory,
                developerDatabase     ,
                developerDatabaseTable
        );
    }
}
