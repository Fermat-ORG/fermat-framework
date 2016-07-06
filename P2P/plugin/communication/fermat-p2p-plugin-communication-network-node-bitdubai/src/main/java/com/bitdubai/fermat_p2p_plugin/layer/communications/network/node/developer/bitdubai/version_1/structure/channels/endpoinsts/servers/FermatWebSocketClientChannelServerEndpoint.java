package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.endpoinsts.servers;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.ServerHandshakeRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.HeadersAttName;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.exception.PackageTypeNotSupportedException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.caches.ClientsSessionMemoryCache;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.conf.ClientChannelConfigurator;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.endpoinsts.FermatWebSocketChannelEndpoint;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.clients.ActorCallRequestProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.clients.ActorListRequestProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.clients.ActorTraceDiscoveryQueryRequestProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.clients.AddActorIntoCatalogProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.clients.CheckInActorRequestProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.clients.CheckInClientRequestProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.clients.CheckInNetworkServiceRequestProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.clients.CheckInProfileDiscoveryQueryRequestProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.clients.CheckOutActorRequestProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.clients.CheckOutClientRequestProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.clients.CheckOutNetworkServiceRequestProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.clients.MessageTransmitProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.clients.NearNodeListRequestProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.clients.UpdateActorProfileIntoCatalogProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.clients.UpdateProfileLocationIntoCatalogProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.utils.DatabaseTransactionStatementPair;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.CheckedActorsHistory;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.CheckedInActor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.CheckedInNetworkService;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.CheckedNetworkServicesHistory;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.ClientsConnectionHistory;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.ClientsRegistrationHistory;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.enums.RegistrationResult;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.enums.RegistrationType;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantCreateTransactionStatementPairException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.util.PackageDecoder;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.util.PackageEncoder;

import org.apache.commons.lang.ClassUtils;
import org.jboss.logging.Logger;

import java.io.IOException;
import java.util.List;

import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.PongMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.endpoinsts.servers.FermatWebSocketClientChannelServerEndpoint</code> this
 * is a especial channel to manage all the communication between the clients and the node
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 12/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
@ServerEndpoint(
        value = "/client-channel",
        configurator = ClientChannelConfigurator.class,
        encoders = {PackageEncoder.class},
        decoders = {PackageDecoder.class}
)
public class FermatWebSocketClientChannelServerEndpoint extends FermatWebSocketChannelEndpoint {

    /**
     * Represent the LOG
     */
    private final Logger LOG = Logger.getLogger(ClassUtils.getShortClassName(FermatWebSocketClientChannelServerEndpoint.class));

    /**
     * Represent the clientsSessionMemoryCache instance
     */
    private ClientsSessionMemoryCache clientsSessionMemoryCache;

    /**
     * Constructor
     */
    public FermatWebSocketClientChannelServerEndpoint(){
        super();
        this.clientsSessionMemoryCache = ClientsSessionMemoryCache.getInstance();
    }

    /**
     * (non-javadoc)
     *
     * @see FermatWebSocketChannelEndpoint#initPackageProcessorsRegistration()
     */
    @Override
    protected void initPackageProcessorsRegistration(){

        /*
         * Register all messages processor for this
         * channel
         */
        registerMessageProcessor(new ActorCallRequestProcessor(this));
        registerMessageProcessor(new ActorListRequestProcessor(this));
        registerMessageProcessor(new ActorTraceDiscoveryQueryRequestProcessor(this));
        registerMessageProcessor(new AddActorIntoCatalogProcessor(this));
        registerMessageProcessor(new CheckInActorRequestProcessor(this));
        registerMessageProcessor(new CheckInClientRequestProcessor(this));
        registerMessageProcessor(new CheckInNetworkServiceRequestProcessor(this));
        registerMessageProcessor(new CheckInProfileDiscoveryQueryRequestProcessor(this));
        registerMessageProcessor(new CheckOutActorRequestProcessor(this));
        registerMessageProcessor(new CheckOutClientRequestProcessor(this));
        registerMessageProcessor(new CheckOutNetworkServiceRequestProcessor(this));
        registerMessageProcessor(new MessageTransmitProcessor(this));
        registerMessageProcessor(new NearNodeListRequestProcessor(this));
        registerMessageProcessor(new UpdateActorProfileIntoCatalogProcessor(this));
        registerMessageProcessor(new UpdateProfileLocationIntoCatalogProcessor(this));

    }

    /**
     *  Method called to handle a new connection
     *
     * @param session connected
     * @param endpointConfig created
     * @throws IOException
     */
    @OnOpen
    public void onConnect(Session session, EndpointConfig endpointConfig) throws IOException {

        LOG.info(" New connection stablished: " + session.getId());

        try {

            /*
             * Get the node identity
             */
            setChannelIdentity((ECCKeyPair) endpointConfig.getUserProperties().get(HeadersAttName.REMOTE_NPKI_ATT_HEADER_NAME));
            endpointConfig.getUserProperties().remove(HeadersAttName.REMOTE_NPKI_ATT_HEADER_NAME);

            /*
             * Get the client public key identity
             */
            String cpki = (String) endpointConfig.getUserProperties().get(HeadersAttName.CPKI_ATT_HEADER_NAME);

            /*
             * Configure the session and mach the session with the client public key identity
             */
            session.setMaxIdleTimeout(FermatWebSocketChannelEndpoint.MAX_IDLE_TIMEOUT);
            session.setMaxTextMessageBufferSize(FermatWebSocketChannelEndpoint.MAX_MESSAGE_SIZE);
            clientsSessionMemoryCache.add(cpki, session);

            /*
             * Construct packet SERVER_HANDSHAKE_RESPONSE
             */
            ServerHandshakeRespond serverHandshakeRespond = new ServerHandshakeRespond(ServerHandshakeRespond.STATUS.SUCCESS, ServerHandshakeRespond.STATUS.SUCCESS.toString(), cpki);
            Package packageRespond = Package.createInstance(serverHandshakeRespond.toJson(), NetworkServiceType.UNDEFINED, PackageType.SERVER_HANDSHAKE_RESPONSE, getChannelIdentity().getPrivateKey(), cpki);

            /*
             * Send the respond
             */
            session.getAsyncRemote().sendObject(packageRespond);

            /*
             * Create a new ClientsConnectionHistory
             */
            ClientsConnectionHistory clientsConnectionHistory = new ClientsConnectionHistory();
            clientsConnectionHistory.setIdentityPublicKey(cpki);
            clientsConnectionHistory.setStatus(ClientsConnectionHistory.STATUS_SUCCESS);

        }catch (Exception e){
            LOG.error(e);
            session.close(new CloseReason(CloseReason.CloseCodes.PROTOCOL_ERROR, e.getMessage()));
        }

    }

    /**
     * Method called to handle a new message received
     *
     * @param packageReceived new
     * @param session sender
     */
    @OnMessage
    public void newPackageReceived(Package packageReceived, Session session) {

        LOG.info("New package received (" + packageReceived.getPackageType().name() + ")");
        LOG.info("Session: " + session.getId());

        try {

            /*
             * Process the new package received
             */
            processMessage(packageReceived, session);

        }catch (PackageTypeNotSupportedException p){
            LOG.warn(p.getMessage());
        }

    }

    @OnMessage
    public void onPongMessage(PongMessage message) {
        LOG.debug("Pong message receive from server = " + message);
    }

    /**
     * Method called to handle a connection close
     *
     * @param closeReason message with the details.
     * @param session     closed session.
     */
    @OnClose
    public void onClose(final CloseReason closeReason,
                        final Session     session    ) {

        LOG.info("Closed session : " + session.getId() + " Code: (" + closeReason.getCloseCode() + ") - reason: " + closeReason.getReasonPhrase());

        try {

            /*
             * if the client is checked in, i will delete the record
             * if not, i will register the inconsistency
             */
            String clientPublicKey = clientsSessionMemoryCache.get(session);

            if (getDaoFactory().getCheckedInClientDao().exists(clientPublicKey)) {

                // create transaction for
                DatabaseTransaction databaseTransaction = getDaoFactory().getCheckedInClientDao().getNewTransaction();
                DatabaseTransactionStatementPair pair;

                pair = getDaoFactory().getCheckedInClientDao().createDeleteTransactionStatementPair(clientPublicKey);
                databaseTransaction.addRecordToDelete(pair.getTable(), pair.getRecord());

                pair = insertClientsRegistrationHistory(
                        clientPublicKey,
                        RegistrationResult.SUCCESS,
                        closeReason.toString()
                );
                databaseTransaction.addRecordToInsert(pair.getTable(), pair.getRecord());

                List<CheckedInNetworkService> listCheckedInNetworkService = getDaoFactory().getCheckedInNetworkServiceDao().
                                         findAll(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_NETWORK_SERVICE_CLIENT_IDENTITY_PUBLIC_KEY_COLUMN_NAME,
                                                 clientPublicKey);

                if(listCheckedInNetworkService != null){

                    for(CheckedInNetworkService checkedInNetworkService : listCheckedInNetworkService){

                        /*
                         * DELETE from table CheckedInNetworkService
                         */
                        pair = getDaoFactory().getCheckedInNetworkServiceDao().createDeleteTransactionStatementPair(checkedInNetworkService.getId());
                        databaseTransaction.addRecordToDelete(pair.getTable(), pair.getRecord());

                        LOG.info("DELETE checkedInNetworkService " + checkedInNetworkService.getClientIdentityPublicKey());

                        /*
                         * Create a new row into the CheckedNetworkServicesHistory
                         */
                        pair = insertCheckedNetworkServicesHistory(checkedInNetworkService);
                        databaseTransaction.addRecordToInsert(pair.getTable(), pair.getRecord());

                    }
                }

               /*
                * get the list of CheckedInActor where is the ClientIdentityPublicKey
                */
                List<CheckedInActor> listCheckedInActor = getDaoFactory().getCheckedInActorDao().
                        findAll(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_CLIENT_IDENTITY_PUBLIC_KEY_COLUMN_NAME,
                                clientPublicKey);

                if(listCheckedInActor != null){

                    for(CheckedInActor actor : listCheckedInActor){

                        /*
                         * DELETE from table CheckedInActor
                         */
                        pair = getDaoFactory().getCheckedInActorDao().createDeleteTransactionStatementPair(actor.getId());
                        databaseTransaction.addRecordToDelete(pair.getTable(), pair.getRecord());

                        LOG.info("DELETE Actor " + actor.toString());

                        /*
                         * Create a new row into the table CheckedActorsHistory
                         */
                        pair = insertCheckedActorsHistory(actor);
                        databaseTransaction.addRecordToInsert(pair.getTable(), pair.getRecord());

                    }

                }

                databaseTransaction.execute();

            } else {

                insertClientsRegistrationHistory(
                        clientPublicKey,
                        RegistrationResult.IGNORED,
                        "There's no client registered with the given public key, indicated closed reason: " + closeReason.toString()
                );
            }

        } catch (Exception exception) {

            exception.printStackTrace();
        }
    }

    /**
     * Create a new row into the table ClientsRegistrationHistory
     *
     * @param publicKey of the client.
     * @param result    of the registration.
     * @param detail    of the registration.
     *
     * @throws CantInsertRecordDataBaseException if something goes wrong.
     */
    private DatabaseTransactionStatementPair insertClientsRegistrationHistory(final String             publicKey,
                                                  final RegistrationResult result   ,
                                                  final String             detail   ) throws CantCreateTransactionStatementPairException {

        /*
         * Create the ClientsRegistrationHistory
         */
        ClientsRegistrationHistory clientsRegistrationHistory = new ClientsRegistrationHistory();
        clientsRegistrationHistory.setIdentityPublicKey(publicKey);
        clientsRegistrationHistory.setType(RegistrationType.CHECK_OUT);
        clientsRegistrationHistory.setResult(result);
        clientsRegistrationHistory.setDetail(detail);

        /*
         * Create statement.
         */
        return getDaoFactory().getClientsRegistrationHistoryDao().createInsertTransactionStatementPair(clientsRegistrationHistory);
    }

    /*
     * Create a new row into the table CheckedNetworkServicesHistory
     */
    private DatabaseTransactionStatementPair insertCheckedNetworkServicesHistory(CheckedInNetworkService checkedInNetworkService) throws CantCreateTransactionStatementPairException{

        CheckedNetworkServicesHistory checkedNetworkServicesHistory = new CheckedNetworkServicesHistory();
        checkedNetworkServicesHistory.setIdentityPublicKey(checkedInNetworkService.getIdentityPublicKey());
        checkedNetworkServicesHistory.setClientIdentityPublicKey(checkedInNetworkService.getClientIdentityPublicKey());
        checkedNetworkServicesHistory.setNetworkServiceType(checkedInNetworkService.getNetworkServiceType());
        checkedNetworkServicesHistory.setCheckType(CheckedNetworkServicesHistory.CHECK_TYPE_OUT);
        checkedNetworkServicesHistory.setLastLatitude(checkedInNetworkService.getLatitude());
        checkedNetworkServicesHistory.setLastLongitude(checkedInNetworkService.getLongitude());

        /*
         * Create statement.
         */
        return getDaoFactory().getCheckedNetworkServicesHistoryDao().createInsertTransactionStatementPair(checkedNetworkServicesHistory);

    }

    /*
     * Create statement.
     */
    private DatabaseTransactionStatementPair insertCheckedActorsHistory(CheckedInActor actor) throws CantCreateTransactionStatementPairException {

        CheckedActorsHistory checkedActorsHistory = new CheckedActorsHistory();
        checkedActorsHistory.setIdentityPublicKey(actor.getIdentityPublicKey());
        checkedActorsHistory.setActorType(actor.getActorType());
        checkedActorsHistory.setAlias(actor.getAlias());
        checkedActorsHistory.setName(actor.getName());
        checkedActorsHistory.setPhoto(actor.getPhoto());
        checkedActorsHistory.setExtraData(actor.getExtraData());
        checkedActorsHistory.setCheckType(CheckedActorsHistory.CHECK_TYPE_OUT);
        checkedActorsHistory.setLastLatitude(actor.getLatitude());
        checkedActorsHistory.setLastLongitude(actor.getLongitude());

       /*
        * Create statement.
        */
        return getDaoFactory().getCheckedActorsHistoryDao().createInsertTransactionStatementPair(checkedActorsHistory);

    }


}
