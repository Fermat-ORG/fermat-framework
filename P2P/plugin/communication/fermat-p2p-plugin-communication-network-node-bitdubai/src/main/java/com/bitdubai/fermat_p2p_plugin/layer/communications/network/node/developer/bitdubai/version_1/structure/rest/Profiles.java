package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.rest;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.DiscoveryQueryParameters;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.GsonProvider;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.context.NodeContext;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.context.NodeContextItem;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.daos.DaoFactory;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.ActorsCatalog;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantReadRecordDataBaseException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.lang.ClassUtils;
import org.jboss.logging.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.rest.Profiles</code>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/05/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
@Path("/profiles")
public class Profiles implements RestFulServices {

    /**
     * Represent the LOG
     */
    private final Logger LOG = Logger.getLogger(ClassUtils.getShortClassName(Profiles.class));

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
    public Profiles(){

    }

    @POST
    @Path("/actors")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getList(@FormParam("client_public_key") String clientIdentityPublicKey, @FormParam("discovery_params") String discoveryParam){

        LOG.info(" --------------------------------------------------------------------- ");
        LOG.info("Profiles - Starting listActors");
        JsonObject jsonObjectRespond = new JsonObject();

        try{

            /*
             * Construct the json object
             */
            DiscoveryQueryParameters discoveryQueryParameters = DiscoveryQueryParameters.parseContent(discoveryParam);

            LOG.debug("clientIdentityPublicKey  = " + clientIdentityPublicKey);
            LOG.debug("discoveryQueryParameters = " + discoveryQueryParameters.toJson());

            /*
             * hold the result list
             */
            List<ActorProfile> resultList = filterActors(discoveryQueryParameters, clientIdentityPublicKey);

            LOG.info("filteredLis.size() =" + resultList.size());

            /*
             * Convert the list to json representation
             */
            String jsonListRepresentation = getGson().toJson(resultList, new TypeToken<List<ActorProfile>>() {
            }.getType());

            /*
             * Create the respond
             */
            jsonObjectRespond.addProperty("data", jsonListRepresentation);


        }catch (Exception e){

            LOG.warn("requested list is not available");
            jsonObjectRespond.addProperty("failure", "Requested list is not available");
            e.printStackTrace();
        }

        String jsonString = getGson().toJson(jsonObjectRespond);

        LOG.debug("jsonString.length() = " + jsonString.length());

        return Response.status(200).entity(jsonString).build();

    }

    /**
     * Filter all network service from data base that mach
     * with the parameters
     *
     * @param discoveryQueryParameters
     * @return List<ActorProfile>
     */
    private List<ActorProfile> filterActors(DiscoveryQueryParameters discoveryQueryParameters, String clientIdentityPublicKey) throws CantReadRecordDataBaseException, InvalidParameterException {

        List<ActorProfile> profileList = new ArrayList<>();

        Map<String, Object> filters = constructFiltersActorTable(discoveryQueryParameters);
        List<ActorsCatalog> actors = getDaoFactory().getActorsCatalogDao().findAll(filters);

        for (ActorsCatalog actorsCatalog : actors) {

            if (clientIdentityPublicKey == null || actorsCatalog.getClientIdentityPublicKey() == null || !actorsCatalog.getClientIdentityPublicKey().equals(clientIdentityPublicKey)) {
                ActorProfile actorProfile = new ActorProfile();
                actorProfile.setIdentityPublicKey(actorsCatalog.getIdentityPublicKey());
                actorProfile.setAlias(actorsCatalog.getAlias());
                actorProfile.setName(actorsCatalog.getName());
                actorProfile.setActorType(actorsCatalog.getActorType());
                actorProfile.setPhoto(actorsCatalog.getPhoto());
                actorProfile.setExtraData(actorsCatalog.getExtraData());
                actorProfile.setLocation(actorsCatalog.getLastLocation());

                profileList.add(actorProfile);
            }
        }

        return profileList;
    }

    /**
     * Construct data base filter from discovery query parameters
     *
     * @param discoveryQueryParameters
     * @return Map<String, Object> filters
     */
    private Map<String, Object> constructFiltersActorTable(DiscoveryQueryParameters discoveryQueryParameters){

        Map<String, Object> filters = new HashMap<>();

        if (discoveryQueryParameters.getIdentityPublicKey() != null){
            filters.put(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_IDENTITY_PUBLIC_KEY_COLUMN_NAME, discoveryQueryParameters.getIdentityPublicKey());
        }

        if (discoveryQueryParameters.getName() != null){
            filters.put(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_NAME_COLUMN_NAME, discoveryQueryParameters.getName());
        }

        if (discoveryQueryParameters.getAlias() != null){
            filters.put(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_ALIAS_COLUMN_NAME, discoveryQueryParameters.getAlias());
        }

        if (discoveryQueryParameters.getActorType() != null){
            filters.put(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_ACTOR_TYPE_COLUMN_NAME, discoveryQueryParameters.getActorType());
        }

        if (discoveryQueryParameters.getExtraData() != null){
            filters.put(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_EXTRA_DATA_COLUMN_NAME, discoveryQueryParameters.getExtraData());
        }

        return filters;
    }

    private DaoFactory getDaoFactory() {

        if (daoFactory == null)
            daoFactory = (DaoFactory) NodeContext.get(NodeContextItem.DAO_FACTORY);

        return daoFactory;

    }

    private Gson getGson() {

        if (gson == null)
            gson = GsonProvider.getGson();

        return gson;

    }

}
