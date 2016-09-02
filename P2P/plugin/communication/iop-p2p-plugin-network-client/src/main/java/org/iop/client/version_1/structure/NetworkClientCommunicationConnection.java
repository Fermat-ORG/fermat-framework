package org.iop.client.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.osa_android.ConnectivityManager;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationManager;
import com.bitdubai.fermat_api.layer.osa_android.location_system.exceptions.CantGetDeviceLocationException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantRegisterProfileException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantRequestActorFullPhotoException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantRequestProfileListException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantSendMessageException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantUpdateRegisteredProfileException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.MessageTooBigException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.interfaces.NetworkClientCall;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.interfaces.NetworkClientConnection;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.interfaces.P2PLayerManager;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.DiscoveryQueryParameters;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.PackageContent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request.ActorListMsgRequest;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request.CheckInProfileMsgRequest;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request.UpdateActorProfileMsgRequest;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ClientProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.NetworkServiceProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.NodeProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.Profile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageContentType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationChannels;

import org.glassfish.tyrus.client.ClientManager;
import org.glassfish.tyrus.client.ClientProperties;
import org.iop.client.version_1.IoPClientPluginRoot;
import org.iop.client.version_1.channels.endpoints.NetworkClientCommunicationChannel;
import org.iop.client.version_1.exceptions.CantSendPackageException;
import org.iop.client.version_1.network_calls.NetworkClientCommunicationCall;
import org.iop.client.version_1.structure.Sync.WaiterObjectsBuffer;
import org.iop.client.version_1.util.HardcodeConstants;
import org.iop.client.version_1.util.PackageEncoder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.websocket.CloseReason;
import javax.websocket.EncodeException;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.structure.NetworkClientCommunicationConnection</code>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 14/04/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class NetworkClientCommunicationConnection implements NetworkClientConnection {

    /**
     * Represent the MAX_MESSAGE_BUFFER_SIZE
     */
    public static final int MAX_MESSAGE_BUFFER_SIZE = 65536;

    private String                 nodeUrl               ;
    private URI                    uri                   ;
    private EventManager           eventManager          ;
    private LocationManager        locationManager       ;
    private ECCKeyPair             clientIdentity        ;

    private CopyOnWriteArrayList<NetworkClientCall> activeCalls;

    /**
     * Represent if it must reconnect to the server
     */
    private boolean tryToReconnect;

    /**
     * Represent the webSocketContainer
     */
    private ClientManager container;

    /**
     * Represent the serverIdentity
     */
    private String serverIdentity;

    /*
     * Represent the pluginRoot
     */
    private IoPClientPluginRoot pluginRoot;

    /*
     * Represent the networkClientCommunicationChannel
     */
    private NetworkClientCommunicationChannel networkClientCommunicationChannel;


    /**
     * Message waiting to send
     */
    private ConcurrentLinkedQueue<ByteBuffer> packagesWaitingToSend;

    private ScheduledExecutorService messageSenderExecutor;


   /*
    * is used to validate if it is connection to an external node
    * when receive check-in-client then send register all profile
    */
//    private boolean isExternalNode;

    /*
     * Represent the nodeProfile, it is used to be save
     * into table NodeConnectionHistory when the client is connected
     */
    private NodeProfile nodeProfile;

    private ConnectivityManager connectivityManager;

    private WaiterObjectsBuffer waiterObjectsBuffer;

    /*
     * Represents the P2P layer manager
     */
    private P2PLayerManager p2PLayerManager;

    private PackageEncoder packageEncoder;

    /*
     * Constructor
     */
    public NetworkClientCommunicationConnection(final String nodeUrl                                    ,
                                                final EventManager eventManager                         ,
                                                final LocationManager locationManager                   ,
                                                final ECCKeyPair clientIdentity                         ,
                                                final IoPClientPluginRoot pluginRoot   ,
                                                final Integer nodesListPosition                         ,
                                                final boolean isExternalNode                            ,
                                                final NodeProfile nodeProfile                           ,
                                                ConnectivityManager connectivityManager                 ,
                                                final P2PLayerManager p2PLayerManager){

        URI uri = null;
        try {
            uri = new URI(HardcodeConstants.WS_PROTOCOL + HardcodeConstants.SERVER_IP_DEFAULT+":"+HardcodeConstants.DEFAULT_PORT+ "/iop-node/ws/client-channel");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        this.nodeUrl                = nodeUrl                     ;
        this.uri                    = uri                         ;
        this.eventManager           = eventManager                ;
        this.locationManager        = locationManager             ;
        this.clientIdentity         = clientIdentity              ;
//        this.isExternalNode         = isExternalNode              ;
        this.pluginRoot             = pluginRoot                  ;
        this.nodeProfile            = nodeProfile                 ;

        this.tryToReconnect         = Boolean.TRUE                ;

        this.activeCalls            = new CopyOnWriteArrayList<>();
        this.container              = ClientManager.createClient();
        this.connectivityManager = connectivityManager;

        this.p2PLayerManager = p2PLayerManager;

        this.networkClientCommunicationChannel = new NetworkClientCommunicationChannel(this, isExternalNode);
        this.waiterObjectsBuffer = new WaiterObjectsBuffer();

        packagesWaitingToSend = new ConcurrentLinkedQueue<>();

        packageEncoder = new PackageEncoder();
    }

    /*
     * initialize And Connect to Network Node
     */
    public void initializeAndConnect() {

        System.out.println("*****************************************************************");
        System.out.println("Connecting To Server: " + uri);
        System.out.println("*****************************************************************");

        /*
         * Create a ReconnectHandler
         */
        ClientManager.ReconnectHandler reconnectHandler = new ClientManager.ReconnectHandler() {

            @Override
            public boolean onDisconnect(CloseReason closeReason) {
                    System.out.println("##########################################################################");
                    System.out.println("#  NetworkClientCommunicationConnection  - Disconnect -> Reconnecting... #");
                    System.out.println("##########################################################################");
                    return closeReason.getCloseCode() != CloseReason.CloseCodes.NORMAL_CLOSURE;
            }

            @Override
            public boolean onConnectFailure(Exception exception) {
                    try {

                        // To avoid potential DDoS when you don't limit number of reconnects, wait to the next try.
                        Thread.sleep(5000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println("###############################################################################");
                    System.out.println("#  NetworkClientCommunicationConnection  - Connect Failure -> Reconnecting... #");
                    System.out.println("###############################################################################");

                    return tryToReconnect;
            }

        };

        /*
         * Register the ReconnectHandler
         */
        container.getProperties().put(ClientProperties.RECONNECT_HANDLER, reconnectHandler);
        container.setDefaultMaxBinaryMessageBufferSize(MAX_MESSAGE_BUFFER_SIZE);
        container.setDefaultMaxTextMessageBufferSize(MAX_MESSAGE_BUFFER_SIZE);

        try {

            container.asyncConnectToServer(networkClientCommunicationChannel, uri);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    * initialize And Connect to Network Node
    */
    public void initializeAndConnectToExternalNode(final NetworkServiceType networkServiceType,
                                                   final ActorProfile       actorProfile      ) {

        NetworkClientCommunicationCall actorCall = new NetworkClientCommunicationCall(
                networkServiceType,
                actorProfile,
                this
        );

        this.addCall(actorCall);

        System.out.println("*****************************************************************");
        System.out.println("Connecting To Server: " + uri);
        System.out.println("*****************************************************************");

        try {

            container.connectToServer(networkClientCommunicationChannel, uri);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTryToReconnect(boolean tryToReconnect) {
        this.tryToReconnect = tryToReconnect;
    }

    public boolean isConnected() {

        try {
            if (networkClientCommunicationChannel.getClientConnection() != null)
                return networkClientCommunicationChannel.getClientConnection().isOpen();
        }catch (Exception e){
            return Boolean.FALSE;
        }

        return Boolean.FALSE;
    }

    @Override
    public boolean isRegistered() {

        return networkClientCommunicationChannel.isRegistered();
    }

    @Override
    public String getActorFullPhoto(String publicKey) throws CantRequestActorFullPhotoException {
        return null;
    }

    /*
     * is used to validate if it is a connection to an external node
     */
//    public boolean isExternalNode() {
//        return isExternalNode;
//    }

    /*
     * Register the client in the Network Node
     * (Registra el cliente en el nodo despues del handshake
     */
    public synchronized void registerInNode(){

        // if it is not an external node, then i register it
//        if (!isExternalNode) {
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setIdentityPublicKey(clientIdentity.getPublicKey());
            clientProfile.setDeviceType("");

            try {
                if (locationManager!=null) {
                    if (locationManager.getLocation() != null) {
                        clientProfile.setLocation(locationManager.getLocation());
                    }
                }else {
                    System.out.println("LocationManager null");
                }
            } catch (CantGetDeviceLocationException e) {
                e.printStackTrace();
            }

            try {
                registerProfile(clientProfile);
            } catch (Exception e) {
                e.printStackTrace();
            }
//        } else { // if it is an external node, i will raise the event for all the calls done to this connection.
//
//            for (NetworkClientCall networkClientCall : activeCalls) {
//
//                /*
//                 * Create a raise a new event whit the NETWORK_CLIENT_CALL_CONNECTED
//                 */
//                FermatEvent actorCallConnected = eventManager.getNewEvent(P2pEventType.NETWORK_CLIENT_CALL_CONNECTED);
//                actorCallConnected.setSource(EventSource.NETWORK_CLIENT);
//
//                ((NetworkClientCallConnectedEvent) actorCallConnected).setNetworkClientCall(networkClientCall);
//
//                /*
//                 * Raise the event
//                 */
//                System.out.println("NetworkClientCommunicationConnection - Raised a event = P2pEventType.NETWORK_CLIENT_CALL_CONNECTED");
//                eventManager.raiseEvent(actorCallConnected);
//            }
//        }
    }


    /**
     *  Proceso de regitro
     */
    @Override
    public UUID registerProfile(final Profile profile) throws CantRegisterProfileException {


        CheckInProfileMsgRequest profileCheckInMsgRequest = new CheckInProfileMsgRequest(profile);
        profileCheckInMsgRequest.setMessageContentType(MessageContentType.JSON);

        PackageType packageType;

        if (profile instanceof ActorProfile) {
            packageType = PackageType.CHECK_IN_ACTOR_REQUEST;
            System.out.println("##########################\nRegistering actor profile\n############################");
//            ((ActorProfile) profile).setClientIdentityPublicKey(clientIdentity.getPublicKey());
        }else if (profile instanceof ClientProfile) {
            packageType = PackageType.CHECK_IN_CLIENT_REQUEST;
        }else if (profile instanceof NetworkServiceProfile) {
            packageType = PackageType.CHECK_IN_NETWORK_SERVICE_REQUEST;
            System.out.println("##########################\nRegistering network service profile\n############################");
            ((NetworkServiceProfile) profile).setClientIdentityPublicKey(clientIdentity.getPublicKey());
        } else {
            CantRegisterProfileException fermatException = new CantRegisterProfileException(
                    "profile:" + profile,
                    "Unsupported profile type."
            );

            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    fermatException
            );

            throw fermatException;
        }

        try {

            return sendPackage(profileCheckInMsgRequest, packageType);

        } catch (CantSendPackageException | CantSendMessageException cantSendPackageException) {

            CantRegisterProfileException fermatException = new CantRegisterProfileException(
                    cantSendPackageException,
                    "profile:" + profile,
                    "Cant send package."
            );

            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    fermatException
            );

            throw fermatException;
        }
    }


    private void fullUpdateRegisteredProfile(Profile profile) throws CantUpdateRegisteredProfileException {

        PackageContent profileUpdateMsgRequest;

        PackageType packageType;

        if (profile instanceof ActorProfile) {
            packageType = PackageType.UPDATE_ACTOR_PROFILE_REQUEST;
//            ((ActorProfile) profile).setClientIdentityPublicKey(clientIdentity.getPublicKey());
            profileUpdateMsgRequest = new UpdateActorProfileMsgRequest(profile);
            profileUpdateMsgRequest.setMessageContentType(MessageContentType.JSON);

        } else {
            CantUpdateRegisteredProfileException fermatException = new CantUpdateRegisteredProfileException(
                    "profile:" + profile,
                    "Unsupported profile type."
            );

            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    fermatException
            );

            throw fermatException;
        }

        try {

            sendPackage(profileUpdateMsgRequest, packageType);

        } catch (CantSendPackageException | CantSendMessageException cantSendPackageException) {

            CantUpdateRegisteredProfileException fermatException = new CantUpdateRegisteredProfileException(
                    cantSendPackageException,
                    "profile:" + profile,
                    "Cant send package."
            );

            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    fermatException
            );

            throw fermatException;
        }
    }


    /**
     * Request de lista de cualquier cosa al nodo
     *
     * @param discoveryQueryParameters  parameters for the query
     * @param networkServicePublicKey   network service asking for the list of actors
     *
     * @return
     * @throws CantRequestProfileListException
     */
    @Override
    public UUID discoveryQuery(final DiscoveryQueryParameters discoveryQueryParameters,
                               final String                   networkServicePublicKey ,
                               final String                   requesterPublicKey      ) throws CantRequestProfileListException {

        UUID queryId = UUID.randomUUID();

        //todo: esto cambiarlo por un DiscoveryMsg o algo así
        ActorListMsgRequest actorListMsgRequest = new ActorListMsgRequest(
                networkServicePublicKey,
                discoveryQueryParameters,
                requesterPublicKey
        );

        actorListMsgRequest.setMessageContentType(MessageContentType.JSON);

        try {

            sendPackage(actorListMsgRequest, PackageType.ACTOR_LIST_REQUEST);

        } catch (CantSendPackageException | CantSendMessageException  cantSendPackageException) {

            CantRequestProfileListException fermatException = new CantRequestProfileListException(
                    cantSendPackageException,
                    "discoveryQueryParameters:" + discoveryQueryParameters+" - networkServicePublicKey:" + networkServicePublicKey,
                    "Cant send package."
            );

            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    fermatException
            );

            throw fermatException;
        }

        return queryId;
    }


    public UUID sendPackageMessage(final PackageContent     packageContent              ,
                                   final PackageType        packageType,
                                   final NetworkServiceType networkServiceType          ,
                                   final String             destinationIdentityPublicKey) throws CantSendMessageException, CantSendPackageException {

        System.out.println("******* IS CONNECTED: " + isConnected() + " - TRYING NO SEND");

        if (isConnected()){
            try {
                //todo: esto hay que mejorarlo
                Package pack = Package.createInstance(
                        packageContent.toJson(),
                        packageType,
                        clientIdentity.getPrivateKey(),
                        destinationIdentityPublicKey
                );

                ByteBuffer encodedPackage = packageEncoder.encode(pack);;
                int packSize = encodedPackage.position();
                System.out.println("******* packSize " + packSize);
                //le puse eso porque vi que el websocket le agrega datos, hay que chequear mejor cuantos..
                if (packSize > MAX_MESSAGE_BUFFER_SIZE-7648){
                    throw new MessageTooBigException("Message size is too big, The max size configure is "+MAX_MESSAGE_BUFFER_SIZE);
                }

                packagesWaitingToSend.add(encodedPackage);
                return pack.getPackageId();

            }catch (Exception exception) {
                throw new CantSendMessageException(
                        exception,
                        "packageContent:"+packageContent,
                        "Unhandled error trying to send the message through the session."
                );
            }
        }else {
//            raiseClientConnectionLostNotificationEvent();
            throw new CantSendPackageException(
                    "packageContent: " + packageContent + " - packageType: " + packageType,
                    "Client Connection is Closed."
            );
        }
    }

    public UUID sendPackageMessage(final PackageContent packageContent, final PackageType packageType, final NetworkServiceType networkServiceType) throws CantSendMessageException, CantSendPackageException {
        return sendPackageMessage(packageContent,packageType,networkServiceType,null);
    }

    public UUID sendPackageMessage(final PackageContent     packageContent              ,
                                   final PackageType        packageType) throws CantSendMessageException, CantSendPackageException {
        return sendPackageMessage(packageContent,packageType,null,null);
    }
    private UUID sendPackage(final PackageContent packageContent,
                             final PackageType    packageType   ) throws CantSendPackageException, CantSendMessageException {
        return sendPackageMessage(packageContent,packageType,null,null);
    }


    //todo: ver que el id del mensaje sea unico
    public boolean sendSyncPackageMessage(final PackageContent     packageContent              ,
                                   final NetworkServiceType networkServiceType          ,
                                   final String             destinationIdentityPublicKey,
                                       UUID messageId) throws CantSendMessageException {
        System.out.println("******* IS CONNECTED: " + isConnected() + " - TRYING NO SEND = " + packageContent.toJson());
        if (isConnected()){

            try {
                networkClientCommunicationChannel.getClientConnection().getBasicRemote().sendObject(
                        Package.createInstance(
                                packageContent.toJson(),
                                PackageType.MESSAGE_TRANSMIT,
                                clientIdentity.getPrivateKey(),
                                destinationIdentityPublicKey
                        )
                );

                //lock and wait
                System.out.println("******* wainting for the sync object");
                return (boolean) waiterObjectsBuffer.getBufferObject(messageId.toString());


            } catch (IOException | EncodeException exception){

                throw new CantSendMessageException(
                        exception,
                        "packageContent:"+packageContent,
                        "Error trying to send the message through the session."
                );

            } catch (Exception exception) {

                throw new CantSendMessageException(
                        exception,
                        "packageContent:"+packageContent,
                        "Unhandled error trying to send the message through the session."
                );
            }
        }else{
            System.out.println("******* sendSyncPackageMessage, NODE IS NOT CONNECTED");
        }
        return false;
    }

    public void receiveSyncPackgageMessage(String messageId,boolean status) {
        waiterObjectsBuffer.addFullDataAndNotificateArrive(messageId, status);
    }



//    /**
//     * Notify when the network client connection is lost.
//     */
//    public void raiseClientConnectionLostNotificationEvent() {
//
//        System.out.println("CommunicationsNetworkClientConnection - raiseClientConnectionLostNotificationEvent");
//        FermatEvent platformEvent = eventManager.getNewEvent(P2pEventType.NETWORK_CLIENT_CONNECTION_LOST);
//        platformEvent.setSource(EventSource.NETWORK_CLIENT);
//        ((NetworkClientConnectionLostEvent) platformEvent).setCommunicationChannel(CommunicationChannels.P2P_SERVERS);
//        eventManager.raiseEvent(platformEvent);
//        System.out.println("CommunicationsNetworkClientConnection - Raised Event = P2pEventType.NETWORK_CLIENT_CONNECTION_LOST");
//    }


    @Override
    public CommunicationChannels getCommunicationChannelType() {
        return CommunicationChannels.P2P_SERVERS;
    }

    public void setServerIdentity(String serverIdentity) {
        this.serverIdentity = serverIdentity;
    }

    public URI getUri() {
        return uri;
    }

    public NetworkClientCommunicationChannel getNetworkClientCommunicationChannel() {
        return networkClientCommunicationChannel;
    }

    public synchronized void addCall(NetworkClientCall networkClientCall) {

        this.activeCalls.add(networkClientCall);
    }

    public synchronized void hangUp(NetworkClientCall networkClientCall) {

        this.activeCalls.remove(networkClientCall);

//        if (this.activeCalls.isEmpty() && isExternalNode) {
//
//            NetworkClientConnectionsManager networkClientConnectionsManager =  (NetworkClientConnectionsManager) ClientContext.get(ClientContextItem.CLIENTS_CONNECTIONS_MANAGER);
//
//            try {
//                // if I can, i will close the session of the connection.
//                this.networkClientCommunicationChannel.getClientConnection().close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            networkClientConnectionsManager.getActiveConnectionsToExternalNodes().remove(this.nodeUrl);
//        }
    }

    public String getNodeUrl() {
        return nodeUrl;
    }

    public NodeProfile getNodeProfile() {
        return nodeProfile;
    }

    public synchronized void close() throws IOException {
        if (networkClientCommunicationChannel.getClientConnection().isOpen())
            networkClientCommunicationChannel.getClientConnection().close();
    }



    public void stopConnectionSuperVisorAgent(){
        pluginRoot.stopConnectionSuperVisorAgent();
    }

    public void startConnectionSuperVisorAgent(){
        pluginRoot.startConnectionSuperVisorAgent();
    }

    public void startMessageSenderExecutor() throws Exception {
        System.out.println("******* startMessageSenderExecutor");
        if (messageSenderExecutor!=null) throw new Exception("MessageSenderExecutor no es nulo, está mal esto");
        this.messageSenderExecutor = Executors.newSingleThreadScheduledExecutor();
        messageSenderExecutor.scheduleAtFixedRate(new Runnable() {
            @Override
            public synchronized void run() {
                try {
                    System.out.println("******* MessageSenderExecutor running..");
                    if (isConnected()) {
                        boolean flag = false;
                        while (!flag){
                            ByteBuffer pack = packagesWaitingToSend.poll();
                            if (pack==null){
                                flag = true;
                            }else{
                                try {
                                    networkClientCommunicationChannel.getClientConnection().getBasicRemote().sendBinary(pack);
                                } catch (Exception exception) {
                                    System.err.println("CantSendPackage: block package: " + pack);
                                    exception.printStackTrace();
                                }
                            }
                        }
                    } else {
                        System.err.println("MessageSenderExecutor, connection is close and the executor is on, this is very bad");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, 5, 5, TimeUnit.SECONDS);
    }

    public void stopMessageSenderExecutor() {
        System.out.println("******* stopMessageSenderExecutor");
        this.messageSenderExecutor.shutdownNow();
        this.messageSenderExecutor = null;
    }
}
