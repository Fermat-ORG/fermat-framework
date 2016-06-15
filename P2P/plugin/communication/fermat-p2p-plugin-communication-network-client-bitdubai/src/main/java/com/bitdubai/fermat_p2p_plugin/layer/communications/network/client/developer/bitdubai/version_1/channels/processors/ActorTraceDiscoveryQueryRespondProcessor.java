package com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.processors;

import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientActorFoundEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.ActorsProfileListMsgRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.ResultDiscoveryTraceActor;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.Profile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.endpoints.NetworkClientCommunicationChannel;
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
 * process all packages received the type <code>PackageType.ACTOR_TRACE_DISCOVERY_QUERY_RESPONSE</code><p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/04/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class ActorTraceDiscoveryQueryRespondProcessor extends PackageProcessor {

    /**
     * Constructor whit parameter
     *
     * @param networkClientCommunicationChannel register
     */
    public ActorTraceDiscoveryQueryRespondProcessor(final NetworkClientCommunicationChannel networkClientCommunicationChannel) {
        super(
                networkClientCommunicationChannel,
                PackageType.ACTOR_TRACE_DISCOVERY_QUERY_RESPONSE
        );
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
                goingThroughResultDiscoveryTraceActorList(actorsProfileListMsgRespond.getProfileList(), actorsProfileListMsgRespond.getNetworkServiceTypeIntermediate());

        }else{
            //there is some wrong
        }

    }


    /*
     * we go through ResultDiscoveryTraceActor list when get the Actor
     * then raise event notification
     */
    private synchronized void goingThroughResultDiscoveryTraceActorList(List<ResultDiscoveryTraceActor> resultList, NetworkServiceType networkServiceTypeIntermediate){

        Boolean isOnline = Boolean.FALSE;

        for(ResultDiscoveryTraceActor result : resultList) {

            isOnline = isActorOnline(result.getNodeProfile().getIp() + ":" + result.getNodeProfile().getDefaultPort(), result.getActorProfile());

            if (isOnline) {

                System.out.print("Actor Found in the NODE: " + result.getNodeProfile().toJson());

                String uriToNode = result.getNodeProfile().getIp() + ":" + result.getNodeProfile().getDefaultPort();

                /*
                 * Create a raise a new event whit the platformComponentProfile registered
                 */
                FermatEvent event = getEventManager().getNewEvent(P2pEventType.NETWORK_CLIENT_ACTOR_FOUND);
                event.setSource(EventSource.NETWORK_CLIENT);

                /*
                 * this is to filter the networkservice intermediate
                 */
                ((NetworkClientActorFoundEvent) event).setNetworkServiceTypeIntermediate(networkServiceTypeIntermediate);

                /*
                 * this is to know who is the nodeprofile to send message
                 */
                ((NetworkClientActorFoundEvent) event).setActorProfile(result.getActorProfile());

                /*
                 * this is to filter when the client is checkin in other node
                 */
                ((NetworkClientActorFoundEvent) event).setUriToNode(uriToNode);

                 /*
                 * Raise the event
                 */
                System.out.println("ActorTraceDiscoveryQueryRespondProcessor - Raised a event = P2pEventType.NETWORK_CLIENT_ACTOR_FOUND");
                getEventManager().raiseEvent(event);

                break;

            }
        }

        if (!isOnline) {
            System.out.print("Actor NOT Found in the list of NODES");
            //raise event not success found actor
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
