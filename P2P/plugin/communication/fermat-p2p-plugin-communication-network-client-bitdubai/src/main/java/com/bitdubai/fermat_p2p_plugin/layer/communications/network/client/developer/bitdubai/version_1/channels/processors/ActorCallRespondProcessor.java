package com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.processors;

import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientActorUnreachableEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientCallConnectedEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.ActorCallMsgRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.ActorsProfileListMsgRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.ResultDiscoveryTraceActor;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.Profile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.endpoints.NetworkClientCommunicationChannel;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.context.ClientContext;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.context.ClientContextItem;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.network_calls.NetworkClientCommunicationCall;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.structure.NetworkClientCommunicationConnection;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.structure.NetworkClientConnectionsManager;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.websocket.Session;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.processors.ActorTraceDiscoveryQueryRespondProcessor</code>
 * process all packages received the type <code>PackageType.ACTOR_CALL_RESPONSE</code><p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 19/05/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class ActorCallRespondProcessor extends PackageProcessor {

    /*
     * Represent the networkClientConnectionsManager
     */
    private NetworkClientConnectionsManager networkClientConnectionsManager;

    /**
     * Constructor whit parameter
     *
     * @param networkClientCommunicationChannel register
     */
    public ActorCallRespondProcessor(final NetworkClientCommunicationChannel networkClientCommunicationChannel) {
        super(
                networkClientCommunicationChannel,
                PackageType.ACTOR_CALL_RESPONSE
        );
        this.networkClientConnectionsManager =  (NetworkClientConnectionsManager) ClientContext.get(ClientContextItem.CLIENTS_CONNECTIONS_MANAGER);
    }

    /**
     * (non-javadoc)
     * @see PackageProcessor#processingPackage(Session, Package)
     */
    @Override
    public void processingPackage(Session session, Package packageReceived) {

        System.out.println("Processing new package received, packageType: " + packageReceived.getPackageType());
        ActorCallMsgRespond actorCallMsgRespond = ActorCallMsgRespond.parseContent(packageReceived.getContent());

        if(actorCallMsgRespond.getStatus() == ActorsProfileListMsgRespond.STATUS.SUCCESS){
            //raise event

            Boolean isOnline = Boolean.FALSE;

            try {

                ResultDiscoveryTraceActor result = actorCallMsgRespond.getTraceActor();

                isOnline = isActorOnline(result.getNodeProfile().getIp() + ":" + result.getNodeProfile().getDefaultPort(), result.getActorProfile());

                if(isOnline){

                    System.out.print("Actor Found in the NODE: " + result.getNodeProfile().toJson());

                    String uriToNode =  result.getNodeProfile().getIp() + ":" + result.getNodeProfile().getDefaultPort();

                    /*
                     * if the connection to the node already exists use the current NetworkClientCommunicationConnection
                     * else request a new NetworkClientCommunicationConnection to that Node
                     */
                    if(networkClientConnectionsManager.getActiveConnectionsToExternalNodes().containsKey(uriToNode)) {

                        NetworkClientCommunicationConnection connection = networkClientConnectionsManager.getActiveConnectionsToExternalNodes().get(uriToNode);

                        NetworkClientCommunicationCall actorCall = new NetworkClientCommunicationCall(
                                actorCallMsgRespond.getNetworkServiceType(),
                                result.getActorProfile(),
                                connection
                        );

                        connection.addCall(actorCall);

                        // if it is connected, then i raise the event with the call
                        if (connection.isConnected()) {
                            /*
                             * Create a raise a new event whit the NETWORK_CLIENT_CALL_CONNECTED
                             */
                            FermatEvent actorCallConnected = getEventManager().getNewEvent(P2pEventType.NETWORK_CLIENT_CALL_CONNECTED);
                            actorCallConnected.setSource(EventSource.NETWORK_CLIENT);

                            ((NetworkClientCallConnectedEvent) actorCallConnected).setNetworkClientCall(actorCall);

                            /*
                             * Raise the event
                             */
                            System.out.println("ActorCallRespondProcessor - Raised a event = P2pEventType.NETWORK_CLIENT_CALL_CONNECTED");
                            getEventManager().raiseEvent(actorCallConnected);
                        }

                    } else {

                        networkClientConnectionsManager.requestConnectionToExternalNode(
                                uriToNode,
                                actorCallMsgRespond.getNetworkServiceType(),
                                result.getActorProfile()
                        );

                    }

                } else {

                    /*
                     * Create a raise a new event whit the NETWORK_CLIENT_ACTOR_UNREACHABLE
                     */
                    FermatEvent actorUnreachable = getEventManager().getNewEvent(P2pEventType.NETWORK_CLIENT_ACTOR_UNREACHABLE);
                    actorUnreachable.setSource(EventSource.NETWORK_CLIENT);

                    ((NetworkClientActorUnreachableEvent) actorUnreachable).setActorProfile(result.getActorProfile());
                    ((NetworkClientActorUnreachableEvent) actorUnreachable).setNetworkServiceType(actorCallMsgRespond.getNetworkServiceType());

                    /*
                     * Raise the event
                     */
                    System.out.println("ActorCallRespondProcessor - Raised a event = P2pEventType.NETWORK_CLIENT_ACTOR_UNREACHABLE");
                    getEventManager().raiseEvent(actorUnreachable);
                }

            } catch (Exception e) {
                e.printStackTrace();
                // todo we should handle the exceptions here.
            }


            if(!isOnline) {
                System.out.print("Actor NOT Found in the list of NODES");
                //raise event not success found actor
            }

        }else{
            //there is some wrong
        }

    }

    private boolean isActorOnline(final String  nodeUrl     ,
                                  final Profile actorProfile) {

        try {
            URL url = new URL("http://" + nodeUrl + "/fermat/rest/api/v1/online/component/actor/" + actorProfile.getIdentityPublicKey());

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String respond = reader.readLine();

            if (conn.getResponseCode() == 200 && respond != null && respond.contains("success")) {
                JsonParser parser = new JsonParser();
                JsonObject respondJsonObject = (JsonObject) parser.parse(respond.trim());

                return respondJsonObject.get("isOnline").getAsBoolean();
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
