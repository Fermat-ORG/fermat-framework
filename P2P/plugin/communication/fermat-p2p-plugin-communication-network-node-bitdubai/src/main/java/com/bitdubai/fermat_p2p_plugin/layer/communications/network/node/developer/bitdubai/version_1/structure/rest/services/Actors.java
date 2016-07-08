package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.rest.services;

import com.bitdubai.fermat_api.layer.all_definition.location_system.NetworkNodeCommunicationDeviceLocation;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.GsonProvider;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.context.NodeContext;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.context.NodeContextItem;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.daos.DaoFactory;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.ActorsCatalog;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.CheckedInActor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.rest.RestFulServices;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.ClassUtils;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.GZIP;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.rest.Actors</code>
 * <p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 29/06/2016.
 *
 * @author  rrequena
 * @version 1.0
 * @since   Java JDK 1.7
 */
@Path("/admin/actors")
public class Actors implements RestFulServices {

    /**
     * Represent the LOG
     */
    private final Logger LOG = Logger.getLogger(ClassUtils.getShortClassName(Actors.class));

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
    public Actors(){
        super();
        this.daoFactory  = (DaoFactory) NodeContext.get(NodeContextItem.DAO_FACTORY);
        this.gson = GsonProvider.getGson();
    }

    /**
     * Get all check in actors in the  node
     * @return Response
     */
    @GZIP
    @GET
    @Path("/types")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getActorsTypes(){

        LOG.info("Executing getActorsTypes");

        try {

            Map<com.bitdubai.fermat_api.layer.all_definition.enums.Actors, String> types = new HashMap<>();
            for (com.bitdubai.fermat_api.layer.all_definition.enums.Actors actorsType : com.bitdubai.fermat_api.layer.all_definition.enums.Actors.values()) {
                types.put(actorsType, actorsType.getCode());
            }

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("success", Boolean.TRUE);
            jsonObject.addProperty("types", gson.toJson(types));

            return Response.status(200).entity(gson.toJson(jsonObject)).build();

        } catch (Exception e) {

            e.printStackTrace();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("success", Boolean.FALSE);
            jsonObject.addProperty("details", e.getMessage());

            return Response.status(200).entity(gson.toJson(jsonObject)).build();

        }

    }

    /**
     * Get all check in actors in the  node
     * @param offSet
     * @param max
     * @return Response
     */
    @GZIP
    @GET
    @Path("/check_in")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCheckInActors(@QueryParam("actorType") String actorType, @QueryParam("offSet") Integer offSet, @QueryParam("max") Integer max){

        LOG.info("Executing getCheckInActors");
        LOG.info("actorType = "+actorType+" offset = "+offSet+" max = "+max);

        try {

            long total = 0;
            List<String> actorProfilesRegistered = new ArrayList<>();
            List<CheckedInActor> actorsCatalogList;

            if(actorType != null && actorType != "" && !actorType.isEmpty()){
                actorsCatalogList = daoFactory.getCheckedInActorDao().findAll(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_ACTOR_TYPE_COLUMN_NAME, actorType, offSet, max);
                total = daoFactory.getCheckedInActorDao().getAllCount(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_ACTOR_TYPE_COLUMN_NAME, actorType);
            }else {
                actorsCatalogList = daoFactory.getCheckedInActorDao().findAll(offSet, max);
                total = daoFactory.getCheckedInActorDao().getAllCount();
            }

            for (CheckedInActor actor :actorsCatalogList) {
                actorProfilesRegistered.add(buildActorProfileFromCheckedInActor(actor));
            }

            LOG.info("CheckInActors.size() = " + actorProfilesRegistered.size());

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("success", Boolean.TRUE);
            jsonObject.addProperty("identities", gson.toJson(actorProfilesRegistered));
            jsonObject.addProperty("total", total);

            return Response.status(200).entity(gson.toJson(jsonObject)).build();

        } catch (CantReadRecordDataBaseException e) {

            e.printStackTrace();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("success", Boolean.FALSE);
            jsonObject.addProperty("details", e.getMessage());

            return Response.status(200).entity(gson.toJson(jsonObject)).build();

        }

    }

    /**
     * Get all check in actors in the  node
     * @param offSet
     * @param max
     * @return Response
     */
    @GZIP
    @GET
    @Path("/catalog")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getActorsCatalog(@QueryParam("actorType") String actorType, @QueryParam("offSet") Integer offSet, @QueryParam("max") Integer max){

        LOG.info("Executing getActorsCatalog");
        LOG.info("actorType = "+actorType+" offset = "+offSet+" max = "+max);

        try {

            long total = 0;
            List<String> actorProfilesRegistered = new ArrayList<>();
            List<ActorsCatalog> actorsCatalogList;

            if(actorType != null && actorType != "" && !actorType.isEmpty()){
                actorsCatalogList = daoFactory.getActorsCatalogDao().findAll(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_TRANSACTION_ACTOR_TYPE_COLUMN_NAME, actorType, offSet, max);
                total = daoFactory.getActorsCatalogDao().getAllCount(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_TRANSACTION_ACTOR_TYPE_COLUMN_NAME, actorType);
            }else {
                actorsCatalogList = daoFactory.getActorsCatalogDao().findAll(offSet, max);
                total = daoFactory.getActorsCatalogDao().getAllCount();
            }

            for (ActorsCatalog actorsCatalog :actorsCatalogList) {
                actorProfilesRegistered.add(buildActorProfileFromActorsCatalog(actorsCatalog));
            }

            LOG.info("ActorsCatalog.size() = " + actorProfilesRegistered.size());

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("success", Boolean.TRUE);
            jsonObject.addProperty("identities", gson.toJson(actorProfilesRegistered));
            jsonObject.addProperty("total", total);

            return Response.status(200).entity(gson.toJson(jsonObject)).build();

        } catch (CantReadRecordDataBaseException e) {

            e.printStackTrace();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("success", Boolean.FALSE);
            jsonObject.addProperty("details", e.getMessage());

            return Response.status(200).entity(gson.toJson(jsonObject)).build();

        }

    }



    /**
     * Get a ActorProfile from a CheckedInActor
     * @param actor
     * @return ActorProfile
     */
    private String buildActorProfileFromCheckedInActor(CheckedInActor actor){

        JsonObject jsonObjectActor = new JsonObject();
        jsonObjectActor.addProperty("ipk", actor.getIdentityPublicKey());
        jsonObjectActor.addProperty("alias", actor.getAlias());
        jsonObjectActor.addProperty("name", actor.getName());
        jsonObjectActor.addProperty("type", actor.getActorType());
        jsonObjectActor.addProperty("photo", Base64.encodeBase64String(actor.getPhoto()));
        jsonObjectActor.addProperty("extraData", actor.getExtraData());
        jsonObjectActor.addProperty("location", gson.toJson(NetworkNodeCommunicationDeviceLocation.getInstance(actor.getLatitude(), actor.getLongitude())));
        return gson.toJson(jsonObjectActor);

    }


    private String buildActorProfileFromActorsCatalog(ActorsCatalog actor){

        JsonObject jsonObjectActor = new JsonObject();
        jsonObjectActor.addProperty("ipk", actor.getIdentityPublicKey());
        jsonObjectActor.addProperty("alias", actor.getAlias());
        jsonObjectActor.addProperty("name", actor.getName());
        jsonObjectActor.addProperty("type",  actor.getActorType());
        jsonObjectActor.addProperty("photo", Base64.encodeBase64String(actor.getPhoto()));
        jsonObjectActor.addProperty("extraData", actor.getExtraData());
        jsonObjectActor.addProperty("location", gson.toJson(NetworkNodeCommunicationDeviceLocation.getInstance(actor.getLastLocation().getLatitude(), actor.getLastLocation().getLongitude())));
        return gson.toJson(jsonObjectActor);
    }

}
