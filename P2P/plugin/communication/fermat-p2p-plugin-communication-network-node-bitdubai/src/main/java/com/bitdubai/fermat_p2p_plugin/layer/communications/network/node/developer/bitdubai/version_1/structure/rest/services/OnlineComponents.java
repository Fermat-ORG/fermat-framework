package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.rest.services;

import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.NetworkNodePluginRoot;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.caches.ClientsSessionMemoryCache;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.caches.NodeSessionMemoryCache;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.context.NodeContext;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.context.NodeContextItem;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.daos.DaoFactory;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.ActorsCatalog;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.CheckedInNetworkService;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.NodesCatalog;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.RecordNotFoundException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.rest.RestFulServices;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.commons.lang.ClassUtils;
import org.jboss.logging.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.websocket.Session;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.rest.OnlineComponents</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 29/04/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
@Path("/online/component")
public class OnlineComponents implements RestFulServices {

    /**
     * Represent the LOG
     */
    private final Logger LOG = Logger.getLogger(ClassUtils.getShortClassName(OnlineComponents.class));

    /**
     * Represent the daoFactory
     */
    private DaoFactory daoFactory;

    private NetworkNodePluginRoot pluginRoot;

    /**
     * Represent the gson
     */
    private Gson gson;

    /**
     * Constructor
     */
    public OnlineComponents(){
        daoFactory = (DaoFactory) NodeContext.get(NodeContextItem.DAO_FACTORY);
        pluginRoot = (NetworkNodePluginRoot) NodeContext.get(NodeContextItem.PLUGIN_ROOT);
        gson = new Gson();
    }

    @GET
    @Path("/client/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response isClientOnline(@PathParam("id") String identityPublicKey){

        LOG.info("Executing isClientOnline");
        LOG.info("identityPublicKey = "+identityPublicKey);

        try {

            Boolean online = daoFactory.getCheckedInClientDao().exists(identityPublicKey);

            LOG.info("Is online = " + online);

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("success", Boolean.TRUE);
            jsonObject.addProperty("isOnline",online);

            return Response.status(200).entity(gson.toJson(jsonObject)).build();

        } catch (CantReadRecordDataBaseException e) {

            e.printStackTrace();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("success", Boolean.FALSE);
            jsonObject.addProperty("isOnline", Boolean.FALSE);
            jsonObject.addProperty("details", e.getMessage());

            return Response.status(200).entity(gson.toJson(jsonObject)).build();

        }

    }


    @GET
    @Path("/ns/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response isNsOnline(@PathParam("id") String identityPublicKey){

        LOG.info("Executing isOnlineNs");
        LOG.info("identityPublicKey = "+identityPublicKey);

        try {

            CheckedInNetworkService checkedInNetworkService = daoFactory.getCheckedInNetworkServiceDao().findEntityByFilter(
                    CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_NETWORK_SERVICE_IDENTITY_PUBLIC_KEY_COLUMN_NAME,
                    identityPublicKey);

            Boolean online = Boolean.TRUE;

            LOG.info("Is online = " + online);

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("success", Boolean.TRUE);
            jsonObject.addProperty("isOnline",online);

            return Response.status(200).entity(gson.toJson(jsonObject)).build();

        } catch (Exception e) {

            e.printStackTrace();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("success", Boolean.FALSE);
            jsonObject.addProperty("isOnline", Boolean.FALSE);
            jsonObject.addProperty("details", e.getMessage());

            return Response.status(200).entity(gson.toJson(jsonObject)).build();

        }

    }

    /**
     * We'll ask the node if an actor is online.
     *
     * We'll check if the actor is registered.
     *    - If it is registered We'll return: isOnline:TRUE, sameNode:TRUE
     * If the actor is not registered, I will check the Actor Catalog, then:
     *    - If it is persisted in the same node, then We'll return:  isOnline:FALSE, sameNode:TRUE
     * If it is persisted in other node, then We'll check in the other node, then we will return: isOnline:(isOnline), sameNode:FALSE
     *
     * If an exception occurs We'll return its message in the json, like "details".
     *
     * @param identityPublicKey of the actor
     *
     * @return a json response.
     */
    @GET
    @Path("/actor/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response isActorOnline(@PathParam("id") String identityPublicKey){

        LOG.info("Executing isActorOnline");
        LOG.info("identityPublicKey = "+identityPublicKey);

        try{

            try {

                daoFactory.getCheckedInActorDao().findById(identityPublicKey);

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("success" , Boolean.TRUE);
                jsonObject.addProperty("isOnline", Boolean.TRUE);
                jsonObject.addProperty("sameNode", Boolean.TRUE);

                return Response.status(200).entity(gson.toJson(jsonObject)).build();

            } catch (RecordNotFoundException recordNotFoundException ) {

                try {
                    String nodePublicKey = getNodePublicKeyFromActor(identityPublicKey);

                    if (nodePublicKey.equals(pluginRoot.getIdentity().getPublicKey())) {

                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("success", Boolean.TRUE);
                        jsonObject.addProperty("isOnline", Boolean.FALSE);
                        jsonObject.addProperty("sameNode", Boolean.TRUE);

                        return Response.status(200).entity(gson.toJson(jsonObject)).build();

                    } else {

                        String nodeUrl = getNodeUrl(nodePublicKey);

                        Boolean isOnline = isActorOnline(identityPublicKey, nodeUrl);

                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("success", Boolean.TRUE);
                        jsonObject.addProperty("isOnline", isOnline);
                        jsonObject.addProperty("sameNode", Boolean.FALSE);

                        return Response.status(200).entity(gson.toJson(jsonObject)).build();

                    }
                } catch (RecordNotFoundException exception) {

                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("success" , Boolean.FALSE);
                    jsonObject.addProperty("isOnline", Boolean.FALSE);
                    jsonObject.addProperty("sameNode", Boolean.FALSE);
                    jsonObject.addProperty("details" , "Actor not found in catalog.");

                    return Response.status(200).entity(gson.toJson(jsonObject)).build();
                }
            }


        } catch (Exception e) {

            e.printStackTrace();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("success" , Boolean.FALSE);
            jsonObject.addProperty("isOnline", Boolean.FALSE);
            jsonObject.addProperty("sameNode", Boolean.FALSE);
            jsonObject.addProperty("details" , e.getMessage());

            return Response.status(200).entity(gson.toJson(jsonObject)).build();

        }

    }

    private String getNodePublicKeyFromActor(final String publicKey) throws RecordNotFoundException {

        try {

            ActorsCatalog actorsCatalog = daoFactory.getActorsCatalogDao().findById(publicKey);
            return actorsCatalog.getNodeIdentityPublicKey();

        } catch (Exception exception) {

            throw new RuntimeException("Problem trying to find the actor in the catalog: "+exception.getMessage());
        }
    }

    private String getNodeUrl(final String publicKey) {

        try {

            NodesCatalog nodesCatalog = daoFactory.getNodesCatalogDao().findById(publicKey);
            return nodesCatalog.getIp()+":"+nodesCatalog.getDefaultPort();

        } catch (RecordNotFoundException exception) {

            throw new RuntimeException("Node not found in catalog: "+exception.getMessage());
        } catch (Exception exception) {

            throw new RuntimeException("Problem trying to find the node in the catalog: "+exception.getMessage());
        }
    }

    private Boolean isActorOnline(final String publicKey,
                                  final String nodeUrl  ) {

        try {
            URL url = new URL("http://" + nodeUrl + "/fermat/rest/api/v1/online/component/actor/" + publicKey);

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


    @GET
    @Path("/sessions/nodes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getActiveNodeSessionData(){

        LOG.info("Executing getActiveNodeSessionData");

        try {

            Map<String, Session> sessionMap = NodeSessionMemoryCache.getNodeSessions();
            List<JsonObject> sessions = new ArrayList<>();

            for (String key : sessionMap.keySet()) {
                Session session = sessionMap.get(key);
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("publicKey", key);
                jsonObject.addProperty("sessionId", session.getId());
                sessions.add(jsonObject);
            }

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("success", Boolean.TRUE);
            jsonObject.addProperty("sessions",gson.toJson(sessions));

            return Response.status(200).entity(gson.toJson(jsonObject)).build();

        } catch (Exception e) {

            e.printStackTrace();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("success", Boolean.FALSE);
            jsonObject.addProperty("details", e.getMessage());

            return Response.status(200).entity(gson.toJson(jsonObject)).build();

        }

    }

    @GET
    @Path("/sessions/clients")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getActiveClientSessionData(){

        LOG.info("Executing getActiveClientSessionData");

        try {

            Map<String, Session> sessionMap = ClientsSessionMemoryCache.getClientSessionsByPk();
            List<JsonObject> sessions = new ArrayList<>();

            for (String key : sessionMap.keySet()) {
                Session session = sessionMap.get(key);
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("publicKey", key);
                jsonObject.addProperty("sessionId", session.getId());
                sessions.add(jsonObject);
            }

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("success", Boolean.TRUE);
            jsonObject.addProperty("sessions",gson.toJson(sessions));

            return Response.status(200).entity(gson.toJson(jsonObject)).build();

        } catch (Exception e) {

            e.printStackTrace();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("success", Boolean.FALSE);
            jsonObject.addProperty("details", e.getMessage());

            return Response.status(200).entity(gson.toJson(jsonObject)).build();

        }

    }

}
