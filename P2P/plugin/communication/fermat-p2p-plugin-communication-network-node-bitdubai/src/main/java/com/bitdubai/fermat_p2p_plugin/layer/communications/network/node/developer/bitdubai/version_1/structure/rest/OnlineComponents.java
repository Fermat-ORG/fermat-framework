/*
* @#AvailableComponents.java - 2016
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.rest;

import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.caches.ClientsSessionMemoryCache;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.caches.NodeSessionMemoryCache;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.context.NodeContext;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.context.NodeContextItem;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.daos.DaoFactory;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.CheckedInActor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.CheckedInNetworkService;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.RecordNotFoundException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.apache.commons.lang.ClassUtils;
import org.jboss.logging.Logger;

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

    /**
     * Represent the gson
     */
    private Gson gson;

    /**
     * Constructor
     */
    public OnlineComponents(){
        daoFactory = (DaoFactory) NodeContext.get(NodeContextItem.DAO_FACTORY);
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

    @GET
    @Path("/actor/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response isActorOnline(@PathParam("id") String identityPublicKey){

        LOG.info("Executing isActorOnline");
        LOG.info("identityPublicKey = "+identityPublicKey);

        try {

            daoFactory.getCheckedInActorDao().findById(identityPublicKey);

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("success", Boolean.TRUE);
            jsonObject.addProperty("isOnline",Boolean.TRUE);

            return Response.status(200).entity(gson.toJson(jsonObject)).build();

        } catch (RecordNotFoundException recordNotFoundException ) {

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("success", Boolean.TRUE);
            jsonObject.addProperty("isOnline",Boolean.FALSE);

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
