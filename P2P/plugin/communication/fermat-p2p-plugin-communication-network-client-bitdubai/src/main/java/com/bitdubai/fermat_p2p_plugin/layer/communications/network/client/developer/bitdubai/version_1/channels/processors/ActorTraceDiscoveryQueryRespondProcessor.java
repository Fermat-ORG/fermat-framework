package com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.processors;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.ActorsProfileListMsgRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.ResultDiscoveryTraceActor;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.structure.ClientsConnectionsManager;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.context.ClientContext;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.context.ClientContextItem;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.websocket.Session;

/**
 * The Class <code>ActorTraceDiscoveryQueryRespondProcessor</code>
 * process all packages received the type <code>PackageType.ACTOR_TRACE_DISCOVERY_QUERY_RESPOND</code><p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/04/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class ActorTraceDiscoveryQueryRespondProcessor extends PackageProcessor {

    /*
     * Represent the clientsConnectionsManager
     */
    private ClientsConnectionsManager clientsConnectionsManager;

    /**
     * Constructor whit parameter
     *
     * @param communicationsNetworkClientChannel register
     */
    public ActorTraceDiscoveryQueryRespondProcessor(final com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.endpoints.CommunicationsNetworkClientChannel communicationsNetworkClientChannel) {
        super(
                communicationsNetworkClientChannel,
                PackageType.ACTOR_TRACE_DISCOVERY_QUERY_RESPOND
        );
        this.clientsConnectionsManager =  (ClientsConnectionsManager) ClientContext.get(ClientContextItem.CLIENTS_CONNECTIONS_MANAGER);
    }

    /**
     * (non-javadoc)
     * @see PackageProcessor#processingPackage(Session, Package)
     */
    @Override
    public void processingPackage(Session session, Package packageReceived) {

        System.out.println("Processing new package received, packageType: "+packageReceived.getPackageType());
        ActorsProfileListMsgRespond actorsProfileListMsgRespond = ActorsProfileListMsgRespond.parseContent(packageReceived.getContent());

        System.out.println(actorsProfileListMsgRespond.toJson());

        if(actorsProfileListMsgRespond.getStatus() == ActorsProfileListMsgRespond.STATUS.SUCCESS){
            //raise event

            if(actorsProfileListMsgRespond.getProfileList() != null && actorsProfileListMsgRespond.getProfileList().size() > 0)
                goingThroughResultDiscoveryTraceActorList(actorsProfileListMsgRespond.getProfileList());

        }else{
            //there is some wrong
        }

    }

    /*
     * we go through ResultDiscoveryTraceActor list when get the Actor
     * then raise event notification
     */
    private synchronized void goingThroughResultDiscoveryTraceActorList(List<ResultDiscoveryTraceActor> resultList){

        Boolean isOnline = Boolean.FALSE;

        for(ResultDiscoveryTraceActor result : resultList){

            try {

                URL url = new URL("http://" + result.getNodeProfile().getIp() + ":" + result.getNodeProfile().getDefaultPort() +
                        "/fermat/rest/api/v1/online/component/actor/" + result.getActorProfile().getIdentityPublicKey());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String respond = reader.readLine();

                if (conn.getResponseCode() == 200 && respond != null && respond.contains("success")) {
                    /*
                    * Decode into a json Object
                    */
                    JsonParser parser = new JsonParser();
                    JsonObject respondJsonObject = (JsonObject) parser.parse(respond.trim());

                    isOnline = respondJsonObject.get("isOnline").getAsBoolean();

                    if(isOnline){

                        System.out.print("Actor Found in the NODE: " + result.getNodeProfile().toJson());

                        String uriToNode =  result.getNodeProfile().getIp() + ":" + result.getNodeProfile().getDefaultPort();

                        /*
                         * if exist conenction to node use the actual NetworkClientCommunicationConnection
                         * else then request a new NetworkClientCommunicationConnection to that Node
                         */
                        if(clientsConnectionsManager.getListConnectionActiveToNode().containsKey(uriToNode)){

                            /*
                             * set the ListActorConnectIntoNode with IdentityPublicKey of Actor and
                             * the uriToNode to can find the NetworkClientCommunicationConnection in the
                             * ListConnectionActiveToNode
                             */
                            clientsConnectionsManager.getListActorConnectIntoNode().put(
                                    result.getActorProfile().getIdentityPublicKey(),
                                    uriToNode
                            );

                            /*
                             * set the ListConnectionActiveToNode with uriToNode and the
                             * NetworkClientCommunicationConnection respective
                             */
                            clientsConnectionsManager.getListConnectionActiveToNode().put(
                                    uriToNode,
                                    clientsConnectionsManager.getListConnectionActiveToNode().get(uriToNode)
                            );

                        }else{

                            // request connection to the Node extern in the clientsConnectionsManager
                            clientsConnectionsManager.requestConnectionToNodeExtern(
                                    result.getActorProfile().getIdentityPublicKey(),
                                    uriToNode);

                        }

                        //raise event success found actor

                        break;
                    }

                }

            } catch (Exception e) {
               // e.printStackTrace();
            }

        }

        if(!isOnline) {
            System.out.print("Actor NOT Found in the list of NODES");
            //raise event not success found actor
        }

    }

}
